package RestaurantApplication.DTOs;

import java.util.Arrays;
import java.util.List;

import jakarta.validation.constraints.NotNull;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingDTO {
    
  @NotNull
  private String email;
  @NotNull
  private String startTime;
  @NotNull
  private String endTime;
  
  @NotNull
  private TableItemDTO[] tableData;
  @NotNull
  private TableItemDTO[] mealData;

  @Override
  public String toString() {
      // TODO Auto-generated method stub
      return "email: " + email + "\nstartTime: " + startTime + "\nendTime: " + endTime + "\tableData: " + Arrays.toString(tableData) + "\nmealData: " + Arrays.toString(mealData);
  }

   public static class TableItemDTO {
        public String title;
        public String description;
        public String image;
        public String[] dropDown;     // Can hold both String and Number
        public Integer selectedIndex;     // Nullable index

        @Override
        public String toString() {
            // TODO Auto-generated method stub
            return "title: " + title + "\ndescription: " + description + "\nimage: " + image + "\ndropDown: " + Arrays.toString(dropDown) + "\nselectedIndex: " + selectedIndex; 
        }
    }
}