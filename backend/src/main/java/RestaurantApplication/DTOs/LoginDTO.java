package RestaurantApplication.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class LoginDTO {
  @NotNull @Size(min=10, max=12)
  public String nic;
  @NotBlank @Size(min = 10, max=10)
  public String password;
    
}
