package RestaurantApplication;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import RestaurantApplication.HelperMethods.TableData;
import io.quarkus.hibernate.orm.panache.Panache;
import RestaurantApplication.DTOs.BookingDTO;
import RestaurantApplication.DTOs.CustomerDTO;
import RestaurantApplication.DTOs.TableAvailabilityDTO;
import RestaurantApplication.DTOs.BookingDTO.TableItemDTO;
import RestaurantApplication.Entities.Booking;
import RestaurantApplication.Entities.BookingMeal;
import RestaurantApplication.Entities.BookingTable;
import RestaurantApplication.Entities.Customer;
import RestaurantApplication.Entities.MealPortion;
import RestaurantApplication.Entities.PhysicalTable;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/booking")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookingResource {
    @Inject
    HelperMethods helperMethods;
    
    @Inject
    EntityManager entityManager;

    @POST
    public Response addReservation(@Valid BookingDTO dto) {

        boolean tablesSelected = false;
        for(TableItemDTO t:dto.tableData){
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
        tableAvailabilityDTO.startTime = dto.startTime;
        tableAvailabilityDTO.endTime = dto.endTime;
        List<TableData> availableTables = helperMethods.getAvailableTables(tableAvailabilityDTO);

        availableTables.forEach((t)-> System.out.println(t.title + " : " + t.availableCount));

        HashMap<String, Integer> tableHashMap = new HashMap<>();
        HashMap<String, List<Integer>> tableIds = new HashMap<>();
        availableTables.forEach((t) -> {
            tableHashMap.put(t.title, t.availableCount);
            tableIds.put(t.title, t.availableTableIds);
        });


        boolean canReserve = true;
        for(int i=0;i<dto.tableData.length;++i){
            TableItemDTO thisTable = dto.tableData[i];
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

            class RunDBOperations{
                @Transactional
                public static void runDatabaseOperations(BookingDTO dto, HashMap<String, Integer> tableHashMap, HashMap<String, List<Integer>> tableIds){

                    boolean noTables = true;
                    for(TableItemDTO t:dto.tableData){
                        if(t.selectedIndex!=0){
                            noTables=false;
                            break;
                        }
                    }

                    if(noTables){
                        return;
                    }
                    
                    Booking booking = new Booking();
                    booking.customer = Customer.findById("test2");
                    booking.startTime = OffsetDateTime.parse(dto.startTime);
                    booking.endTime = OffsetDateTime.parse(dto.endTime);
                    booking.totalPrice = new BigDecimal(100);
                    booking.persist();

                    for(String tableType: tableHashMap.keySet()){
                        int requestedTableCount = tableHashMap.get(tableType);
                        List<Integer> availablePhysicalTables = tableIds.get(tableType);
                        for(int i=0;i<requestedTableCount;++i){
                            BookingTable bookingTable = new BookingTable();
                            bookingTable.booking = booking;
                            bookingTable.table = PhysicalTable.findById(availablePhysicalTables.get(i));
                            bookingTable.persist();
                        }
                    }

                    TableItemDTO[] mealData = dto.mealData;
                    for(TableItemDTO item:mealData){
                        if(item.selectedIndex!=0){
                            BookingMeal bookingMeal = new BookingMeal();
                            bookingMeal.booking = booking;
                            bookingMeal.mealPortion = MealPortion.find(
                                "meal.title = ?1 and portion = ?2",
                                item.title,
                                item.dropDown[item.selectedIndex]
                            ).firstResult();
                            bookingMeal.persist();
                        }
                    }
                }
            }

            RunDBOperations.runDatabaseOperations(dto, tableHashMap, tableIds);

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
