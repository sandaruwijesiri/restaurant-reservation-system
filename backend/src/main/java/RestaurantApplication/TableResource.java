package RestaurantApplication;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import RestaurantApplication.DTOs.CustomerDTO;
import RestaurantApplication.DTOs.TableAvailabilityDTO;
import RestaurantApplication.Entities.Customer;
import RestaurantApplication.Entities.TableInfo;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@RequestScoped
@Path("/tables")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TableResource {
    
    @Inject
    EntityManager entityManager;

    @POST
    public Response getAvailableTables(@Valid TableAvailabilityDTO dto) {

        class TableData{
            public String title;
            public String description;
            public String pricePerHour;
            public String imagePath;
            public int seatCount;
            public int availableCount;
        }
        DecimalFormat formatter = new DecimalFormat("#,##0.00");

        OffsetDateTime startTime = OffsetDateTime.parse(dto.startTime);
        OffsetDateTime endTime = OffsetDateTime.parse(dto.endTime);
        
        @SuppressWarnings("unchecked")
        List<Object[]> results = entityManager.createNativeQuery(
            "SELECT pt.type, COUNT(pt.type) " +
            "FROM ((booking b " +
            "JOIN bookingtable bt ON b.id = bt.bookingid) " +
            "JOIN physicaltable pt ON bt.tableid = pt.id) " +
            "WHERE (b.endtime > ?1 and b.starttime < ?2)" +
            "GROUP BY pt.type"
        )
        .setParameter(1,startTime)
        .setParameter(2,endTime)
        .getResultList();

        HashMap<String, Integer> tablesUnavailable = new HashMap<>();
        for(Object[] object : results){
            tablesUnavailable.put((String) object[0], (Integer) object[1]);
        }

        @SuppressWarnings("unchecked")
        List<Object[]> tableInformation = entityManager.createNativeQuery(
            "SELECT title, description, priceperhour, imagepath, nooftables, seatcount " +
            "FROM tableinfo"
        ).getResultList();

        HashMap<String, TableData> allTables = new HashMap<>();
        for(Object[] tableType: tableInformation){
            TableData res = new TableData();
            res.title = (String) tableType[0];
            res.description = (String) tableType[1];
            res.pricePerHour = formatter.format((BigDecimal) tableType[2]);
            res.imagePath = (String) tableType[3];
            res.availableCount = (Integer) tableType[4];
            res.seatCount = (Integer) tableType[5];
            allTables.put(res.title, res);
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
        
        Collections.sort(availability, new Comparator<TableData>(){
            @Override
            public int compare(TableData t1, TableData t2){
                return t1.seatCount - t2.seatCount;
            }
        });

        
        return Response.ok(availability).build();
    }
}
