package RestaurantApplication.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
  @NotNull
  private String email;
  @NotBlank @Size(min = 10, max=10)
  private String password;
    
}
