package RestaurantApplication.Repositories;

import RestaurantApplication.DTOs.CustomerDTO;
import RestaurantApplication.Entities.Customer;
import jakarta.validation.Valid;

public class CustomerRepository {
    public static boolean exists(@Valid CustomerDTO dto){
        return exists(dto.getEmail());
    }
    public static boolean exists(String email){
        return Customer.findByIdOptional(email).isPresent();
    }
    public static void addCustomer(@Valid CustomerDTO dto){
        Customer c = dto.toEntity();
        c.persist();
    }
    public static Customer findById(String Id){
        return Customer.findById(Id);
    }
}
