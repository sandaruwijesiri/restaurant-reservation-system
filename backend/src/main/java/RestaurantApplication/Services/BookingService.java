package RestaurantApplication.Services;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import RestaurantApplication.DTOs.AdminBookingViewDTO;
import RestaurantApplication.DTOs.BookingDTO;
import RestaurantApplication.DTOs.BookingDTO.TableItemDTO;
import RestaurantApplication.DTOs.GetReservationsDTO;
import RestaurantApplication.DTOs.TableAvailabilityDTO;
import RestaurantApplication.Entities.Booking;
import RestaurantApplication.Entities.BookingMeal;
import RestaurantApplication.Entities.BookingTable;
import RestaurantApplication.Entities.Customer;
import RestaurantApplication.Entities.MealPortion;
import RestaurantApplication.Entities.PhysicalTable;
import RestaurantApplication.Entities.TableInfo;
import RestaurantApplication.Repositories.BookingRepository;
import RestaurantApplication.Repositories.TableRepository;
import RestaurantApplication.Utilities.Utilities.TableData;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class BookingService {
    @Inject
    TableService tableService;

    @Inject
    TableRepository tableRepository;

    @Inject
    EntityManager entityManager;

    
    public Response getReservations(String dateString){
        OffsetDateTime date = OffsetDateTime.parse(dateString);
        OffsetDateTime dayStart = date
                                    .toLocalDate()                     // strips time
                                    .atStartOfDay()                    // sets time to 00:00
                                    .atOffset(date.getOffset());  // reapply original offset

        OffsetDateTime dayEnd = date
                                    .toLocalDate()
                                    .atTime(LocalTime.MAX)            // 23:59:59.999999999
                                    .atOffset(date.getOffset());


        List<TableInfo> tableInformation = tableRepository.getAllTables();

        HashMap<String, Integer> allTables = new HashMap<>();
        HashMap<String, int[]> bookedTableCount = new HashMap<>();
        for(TableInfo tableInfo: tableInformation){
            TableData tableData = TableData.fromTableInfo(tableInfo);
            allTables.put(tableData.title, tableData.availableCount);
            bookedTableCount.put(tableData.title, new int[24]);
        }

        
        List<Object[]> bookedTables = tableRepository.getBookedTables(dayStart, dayEnd);

        for(Object[] object : bookedTables){
            String type = (String) object[0];
            Instant startInstant = (Instant) object[2];
            Instant endInstant = (Instant) object[3];
            OffsetDateTime start = startInstant.atOffset(ZoneId.systemDefault().getRules().getOffset(startInstant));
            OffsetDateTime end = endInstant.atOffset(ZoneId.systemDefault().getRules().getOffset(endInstant));

            int[] bookingData = bookedTableCount.get(type);
            for(int i=start.getHour();i<end.getHour();++i){
                bookingData[i]++;
            }
            
        }

        List<String> types = new ArrayList<>();
        List<Integer> tableCounts = new ArrayList<>();
        List<int[]> bookings = new ArrayList<>();
        
        for(String type:allTables.keySet()){
            types.add(type);
            tableCounts.add(allTables.get(type));
            bookings.add(bookedTableCount.get(type));
        }
        
        AdminBookingViewDTO adminBookingViewDTO = new AdminBookingViewDTO();
        adminBookingViewDTO.setTypes(types);
        adminBookingViewDTO.setTotalAmount(tableCounts);
        adminBookingViewDTO.setBookedAmount(bookings);

        return Response.ok(adminBookingViewDTO).build();

    }

    public Response addReservation(@Valid BookingDTO dto) {

        boolean tablesSelected = false;
        for(TableItemDTO t:dto.getTableData()){
            if(t.selectedIndex!=0){
                tablesSelected = true;
                break;
            }
        }
        if(!tablesSelected){
            return Response.status(Response.Status.CONFLICT)
            .entity("Please select tables.")
            .build();
        }

        acquireLock();

        TableAvailabilityDTO tableAvailabilityDTO = new TableAvailabilityDTO();
        tableAvailabilityDTO.setStartTime(dto.getStartTime());
        tableAvailabilityDTO.setEndTime(dto.getEndTime());
        List<RestaurantApplication.Utilities.Utilities.TableData> availableTables = tableService.getAvailableTables(tableAvailabilityDTO);

        availableTables.forEach((t)-> System.out.println(t.title + " : " + t.availableCount));

        HashMap<String, Integer> tableHashMap = new HashMap<>();
        HashMap<String, List<Integer>> tableIds = new HashMap<>();
        availableTables.forEach((t) -> {
            tableHashMap.put(t.title, t.availableCount);
            tableIds.put(t.title, t.availableTableIds);
        });


        boolean canReserve = true;
        for(int i=0;i<dto.getTableData().length;++i){
            TableItemDTO thisTable = dto.getTableData()[i];
            int selectedIndex = thisTable.selectedIndex;
            int request = selectedIndex == 0 ? 0 : Integer.valueOf(thisTable.dropDown[selectedIndex]);

            System.out.println(thisTable.title + " : " + request);

            if(tableHashMap.get(thisTable.title) == null || tableHashMap.get(thisTable.title) < request){
                canReserve = false;
                break;
            }
            tableHashMap.put(thisTable.title, request);
        }

        if(canReserve){

            BookingRepository.reserve(dto, tableHashMap, tableIds);

            releaseLock();

            return Response.ok(
                "Success"
                ).build();
        }else{
            releaseLock();

            return Response.status(Response.Status.CONFLICT)
            .entity("Reservation not possible - tables already taken.")
            .build();
        }
    }
    
    private static long reservationLock = 12345l;
    private void acquireLock(){
        entityManager.createNativeQuery("SELECT pg_advisory_lock(:key)")
            .setParameter("key", reservationLock)
            .getSingleResult();
    }
    private void releaseLock(){
        entityManager.createNativeQuery("SELECT pg_advisory_unlock(:key)")
            .setParameter("key", reservationLock)
            .getSingleResult();
    }
}
