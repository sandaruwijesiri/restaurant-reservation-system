package RestaurantApplication.DTOs;

import RestaurantApplication.Entities.Customer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDTO{
  @NotBlank @Size(max=15)
  private String firstName;
  @NotBlank @Size(max=20)
  private String lastName;
  @NotBlank @Size(min = 10, max=10)
  private String phoneNumber;
  @NotBlank @Size(max=30)
  private String email;

  
    /**  
     * Create a DTO from a JPA entity  
     */
    public static CustomerDTO fromEntity(Customer c) {
        CustomerDTO dto = new CustomerDTO();
        dto.firstName = c.getFirstName();
        dto.lastName  = c.getLastName();
        dto.phoneNumber = c.getPhoneNumber();
        dto.email = c.getEmail();
        return dto;
    }

    /**  
     * Create a JPA entity from this DTO  
     */
    public Customer toEntity() {
        Customer c = new Customer();
        c.setFirstName(this.firstName);
        c.setLastName(this.lastName);
        c.setPhoneNumber(this.phoneNumber);
        c.setEmail(this.email);
        return c;
    }
}
