package RestaurantApplication.Services;

import RestaurantApplication.DTOs.CustomerDTO;
import RestaurantApplication.Entities.Customer;
import RestaurantApplication.Repositories.CustomerRepository;
import jakarta.validation.Valid;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

public class CustomerService {
    public static Response register(@Valid CustomerDTO dto){
        if (CustomerRepository.exists(dto)) {
            throw new WebApplicationException("Email already exists", Status.CONFLICT);
        }
        CustomerRepository.addCustomer(dto);
        return Response.ok().build();
    }
}
