package RestaurantApplication.DTOs;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminBookingViewDTO {
    
  @NotNull
  private List<String> types;
  @NotNull
  private List<Integer> totalAmount;
  @NotNull
  private List<int[]> bookedAmount;
}
