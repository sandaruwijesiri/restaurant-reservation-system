package RestaurantApplication.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TableAvailabilityDTO {
    
  @NotNull
  private String startTime;
  @NotNull
  private String endTime;
}
