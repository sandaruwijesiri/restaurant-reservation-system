package RestaurantApplication.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TableAvailabilityDTO {
    
  @NotNull
  public String startTime;
  @NotNull
  public String endTime;
}
