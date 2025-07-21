package RestaurantApplication.DTOs;

import java.time.OffsetDateTime;

import jakarta.validation.constraints.NotNull;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetReservationsDTO {
    
  @NotNull
  private String title;
  @NotNull
  private Integer reserved;
  @NotNull
  private Integer available;
}
