package RestaurantApplication.Resources;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import io.quarkus.hibernate.orm.panache.Panache;
import RestaurantApplication.DTOs.BookingDTO;
import RestaurantApplication.DTOs.CustomerDTO;
import RestaurantApplication.DTOs.DateWrapperDTO;
import RestaurantApplication.DTOs.GetReservationsDTO;
import RestaurantApplication.DTOs.TableAvailabilityDTO;
import RestaurantApplication.DTOs.BookingDTO.TableItemDTO;
import RestaurantApplication.Entities.Booking;
import RestaurantApplication.Entities.BookingMeal;
import RestaurantApplication.Entities.BookingTable;
import RestaurantApplication.Entities.Customer;
import RestaurantApplication.Entities.MealPortion;
import RestaurantApplication.Entities.PhysicalTable;
import RestaurantApplication.Services.BookingService;
import RestaurantApplication.Services.TableService;
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
    TableService tableService;
    
    @Inject
    BookingService bookingService;

    @POST
    public Response addReservation(@Valid BookingDTO dto) {

        return bookingService.addReservation(dto);
    }

    @POST
    @Path("/reservations")
    public Response getReservations(DateWrapperDTO dto){
        return bookingService.getReservations(dto.getDate());
    }
}
