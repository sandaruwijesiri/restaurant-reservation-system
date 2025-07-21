package RestaurantApplication.Utilities;

import java.text.DecimalFormat;
import java.util.List;

import RestaurantApplication.Entities.TableInfo;

public class Utilities {

    public static class MealResponseItem{
        public String title;
        public String description;
        public String imagePath;
        public String[] portions;
        public String[] prices;
    }    

    public static class TableData{
        public String title;
        public String description;
        public String pricePerHour;
        public String imagePath;
        public int seatCount;
        public int availableCount;
        public List<Integer> availableTableIds;

        public static TableData fromTableInfo(TableInfo tableInfo){
            
            DecimalFormat formatter = new DecimalFormat("#,##0.00");
            TableData tableData = new TableData();
            tableData.title = tableInfo.getTitle();
            tableData.description = tableInfo.getDescription();
            tableData.pricePerHour = formatter.format(tableInfo.getPriceperhour());
            tableData.imagePath = tableInfo.getImagepath();
            tableData.availableCount = tableInfo.getNoOfTables();
            tableData.seatCount = tableInfo.getSeatCount();
            return tableData;
        }
    }
    
    public static enum MealPortionValues{
        SMALL("Small"),
        MEDIUM("Medium"),
        MEDIUM_LARGE("Medium-Large"),
        LARGE("Large");

        public final String value;

        MealPortionValues(String value) {
            this.value = value;
        }
    }
}
