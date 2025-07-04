package RestaurantApplication.DTOs;

import RestaurantApplication.Entities.Customer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CustomerDTO{
  @NotNull @Size(min=10, max=12)
  public String nic;
  @NotBlank @Size(max=15)
  public String firstName;
  @NotBlank @Size(max=20)
  public String lastName;
  @NotBlank @Size(min = 10, max=10)
  public String phoneNumber;
  @NotBlank @Size(max=30)
  public String email;
  @NotBlank @Size(min = 10, max=10)
  public String password;

  
    /**  
     * Create a DTO from a JPA entity  
     */
    public static CustomerDTO fromEntity(Customer c) {
        CustomerDTO dto = new CustomerDTO();
        dto.nic       = c.nic;
        dto.firstName = c.firstName;
        dto.lastName  = c.lastName;
        dto.phoneNumber       = c.phoneNumber;
        dto.email = c.email;
        dto.password  = c.password;
        return dto;
    }

    /**  
     * Create a JPA entity from this DTO  
     */
    public Customer toEntity() {
        Customer c = new Customer();
        c.nic           = this.nic;
        c.firstName     = this.firstName;
        c.lastName      = this.lastName;
        c.phoneNumber   = this.phoneNumber;
        c.email         = this.email;
        c.password      = this.password;
        return c;
    }
}
