package RestaurantApplication.Services;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import RestaurantApplication.DTOs.AddTableDTO;
import RestaurantApplication.DTOs.TableAvailabilityDTO;
import RestaurantApplication.Entities.TableInfo;
import RestaurantApplication.Repositories.TableRepository;
import RestaurantApplication.Utilities.Utilities.TableData;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@ApplicationScoped
public class TableService {
    
    @Inject
    TableRepository tableRepository;
    @Inject
    PhysicalTableService physicalTableService;

    public List<TableData> getAvailableTables(@Valid TableAvailabilityDTO dto) {

        OffsetDateTime startTime = OffsetDateTime.parse(dto.getStartTime());
        OffsetDateTime endTime = OffsetDateTime.parse(dto.getEndTime());
        
        List<Object[]> results = tableRepository.getBookedTables(startTime, endTime);

        HashMap<String, Integer> tablesUnavailable = new HashMap<>();
        for(Object[] object : results){
            String type = (String) object[0];
            tablesUnavailable.put(type, tablesUnavailable.getOrDefault(type, 0) + 1);
        }

        List<TableInfo> tableInformation = tableRepository.getAllTables();

        HashMap<String, TableData> allTables = new HashMap<>();
        for(TableInfo tableInfo: tableInformation){
            TableData tableData = TableData.fromTableInfo(tableInfo);
            allTables.put(tableData.title, tableData);
        }

        for(String type : tablesUnavailable.keySet()){
            if(allTables.containsKey(type)){
                TableData tableData = allTables.get(type);
                tableData.availableCount -= tablesUnavailable.get(type);
            }
        }
        
        List<TableData> availability = new ArrayList<>();
        for(String title:allTables.keySet()){
            if(allTables.get(title).availableCount>0){
                availability.add(allTables.get(title));
            }
        }

        List<Integer> unavailablePhysicalTables = new ArrayList<>();
        for(Object[] o:results){
            unavailablePhysicalTables.add((Integer) o[1]);
        }

        availability.forEach((table) -> {
            table.availableTableIds = physicalTableService.getAvailablePhysicalTableIDs(table.title, unavailablePhysicalTables);
        });
        
        Collections.sort(availability, new Comparator<TableData>(){
            @Override
            public int compare(TableData t1, TableData t2){
                return t1.seatCount - t2.seatCount;
            }
        });

        return availability;
    }

    
    public Response addTable(@Valid AddTableDTO dto) {

        if(tableRepository.tableTypeExists(dto.getTitle())){
            return Response.status(Status.BAD_REQUEST).entity("Table type already exists.").build();
        }

        TableInfo tableInfo = new TableInfo();
        tableInfo.setTitle(dto.getTitle());
        tableInfo.setDescription(dto.getDescription());
        tableInfo.setNoOfTables(dto.getAmountOfTables());
        tableInfo.setSeatCount(dto.getSeatCount());
        tableInfo.setPriceperhour(new BigDecimal(dto.getPrice()));
        tableInfo.setImagepath("http://localhost:8080/assets/images/tables/" + dto.getTitle() + ".jpg");
        tableRepository.addTable(tableInfo);

        return Response.ok().build();
    }
}
