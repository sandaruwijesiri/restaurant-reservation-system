package RestaurantApplication;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import RestaurantApplication.DTOs.TableAvailabilityDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class HelperMethods {
    
    @Inject
    EntityManager entityManager;

    public List<TableData> getAvailableTables(@Valid TableAvailabilityDTO dto) {

        DecimalFormat formatter = new DecimalFormat("#,##0.00");

        OffsetDateTime startTime = OffsetDateTime.parse(dto.startTime);
        OffsetDateTime endTime = OffsetDateTime.parse(dto.endTime);
        
        @SuppressWarnings("unchecked")
        List<Object[]> results = entityManager.createNativeQuery(
            "SELECT pt.type, pt.id " +
            "FROM ((booking b " +
            "JOIN bookingtable bt ON b.id = bt.bookingid) " +
            "JOIN physicaltable pt ON bt.tableid = pt.id) " +
            "WHERE (b.endtime > ?1 and b.starttime < ?2)"
        )
        .setParameter(1,startTime)
        .setParameter(2,endTime)
        .getResultList();



        HashMap<String, Integer> tablesUnavailable = new HashMap<>();
        for(Object[] object : results){
            String type = (String) object[0];
            tablesUnavailable.put(type, tablesUnavailable.getOrDefault(type, 0) + 1);
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

        List<Integer> unavailablePhysicalTables = new ArrayList<>();
        for(Object[] o:results){
            unavailablePhysicalTables.add((Integer) o[1]);
        }

        availability.forEach((table) -> {
            Query query = entityManager.createQuery(
                "SELECT id FROM PhysicalTable WHERE type = :type" + (unavailablePhysicalTables.isEmpty() ? "" : " AND (id NOT IN :excludedids)")
            )
            .setParameter("type", table.title);
            
            if(!unavailablePhysicalTables.isEmpty()){
                query.setParameter("excludedids", unavailablePhysicalTables);            
            }
            
            table.availableTableIds = query.getResultList();

        });
        
        Collections.sort(availability, new Comparator<TableData>(){
            @Override
            public int compare(TableData t1, TableData t2){
                return t1.seatCount - t2.seatCount;
            }
        });

        return availability;
    }

    public static class TableData{
        public String title;
        public String description;
        public String pricePerHour;
        public String imagePath;
        public int seatCount;
        public int availableCount;
        public List<Integer> availableTableIds;
    }
}
