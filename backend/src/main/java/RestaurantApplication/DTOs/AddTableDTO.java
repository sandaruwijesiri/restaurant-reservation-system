package RestaurantApplication.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddTableDTO {
    
  @NotNull
  private String title;
  @NotNull
  private String description;
  @NotNull
  private Integer amountOfTables;
  @NotNull
  private Integer seatCount;
  @NotNull
  private Integer price;
}
