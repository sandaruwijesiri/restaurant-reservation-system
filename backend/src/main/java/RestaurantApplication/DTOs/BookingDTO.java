package RestaurantApplication.DTOs;

import java.util.Arrays;
import java.util.List;

import jakarta.validation.constraints.NotNull;

public class BookingDTO {
    
  @NotNull
  public String startTime;
  @NotNull
  public String endTime;
  
  @NotNull
  public TableItemDTO[] tableData;
  @NotNull
  public TableItemDTO[] mealData;

  @Override
  public String toString() {
      // TODO Auto-generated method stub
      return "startTime: " + startTime + "\nendTime: " + endTime + "\tableData: " + Arrays.toString(tableData) + "\nmealData: " + Arrays.toString(mealData);
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
            return"title: " + title + "\ndescription: " + description + "\nimage: " + image + "\ndropDown: " + Arrays.toString(dropDown) + "\nselectedIndex: " + selectedIndex; 
        }
    }
}