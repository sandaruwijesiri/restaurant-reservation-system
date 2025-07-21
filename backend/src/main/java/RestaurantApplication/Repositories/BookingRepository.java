package RestaurantApplication.Repositories;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;

import RestaurantApplication.DTOs.BookingDTO;
import RestaurantApplication.DTOs.GetReservationsDTO;
import RestaurantApplication.DTOs.BookingDTO.TableItemDTO;
import RestaurantApplication.Entities.Booking;
import RestaurantApplication.Entities.BookingMeal;
import RestaurantApplication.Entities.BookingTable;
import RestaurantApplication.Entities.Customer;
import RestaurantApplication.Entities.MealPortion;
import RestaurantApplication.Entities.PhysicalTable;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

public class BookingRepository {

    @Transactional
    public static void reserve(BookingDTO dto, HashMap<String, Integer> tableHashMap, HashMap<String, List<Integer>> tableIds){
        boolean noTables = true;
        for(TableItemDTO t:dto.getTableData()){
            if(t.selectedIndex!=0){
                noTables=false;
                break;
            }
        }

        if(noTables){
            return;
        }
        
        Booking booking = new Booking();
        booking.setCustomer(CustomerRepository.findById(dto.getEmail()));
        booking.setStartTime(OffsetDateTime.parse(dto.getStartTime()));
        booking.setEndTime(OffsetDateTime.parse(dto.getEndTime()));
        booking.setTotalPrice(new BigDecimal(100));
        booking.persist();

        for(String tableType: tableHashMap.keySet()){
            int requestedTableCount = tableHashMap.get(tableType);
            List<Integer> availablePhysicalTables = tableIds.get(tableType);
            for(int i=0;i<requestedTableCount;++i){
                BookingTable bookingTable = new BookingTable();
                bookingTable.setBooking(booking);
                bookingTable.setTable(PhysicalTableRepository.findById(availablePhysicalTables.get(i)));
                bookingTable.persist();
            }
        }

        TableItemDTO[] mealData = dto.getMealData();
        for(TableItemDTO item:mealData){
            if(item.selectedIndex!=0){
                BookingMeal bookingMeal = new BookingMeal();
                bookingMeal.setBooking(booking);
                bookingMeal.setMealPortion(MealPortion.find(
                    "meal.title = ?1 and portion = ?2",
                    item.title,
                    item.dropDown[item.selectedIndex]
                ).firstResult());
                bookingMeal.persist();
            }
        }
    }
}
