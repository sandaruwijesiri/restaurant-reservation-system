package RestaurantApplication;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import RestaurantApplication.Entities.Meal;
import RestaurantApplication.Entities.MealPortion;
import jakarta.persistence.Column;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/meals")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MealResource {
    
    @GET
    public Response getMealData(){

        DecimalFormat formatter = new DecimalFormat("#,##0.00");

        List<Meal> meals = Meal.listAll();
        List<MealResponseItem> responseData = new ArrayList<>();
        meals.forEach(
            (meal) -> {
                List<MealPortion> mealPortions = MealPortion.find("meal = ?1 order by portion desc", meal).list();
                String[] portions = new String[mealPortions.size()];
                String[] prices = new String[mealPortions.size()];
                
                for(int i=0;i<mealPortions.size();++i){
                    portions[i] = mealPortions.get(i).portion;
                    prices[i] = formatter.format(mealPortions.get(i).price);

                    if(portions[i].equals("Medium") && i>0 && portions[i-1].equals("Medium-Large")){
                        String temp = portions[i];
                        portions[i] = portions[i-1];
                        portions[i-1] = temp;

                        temp = prices[i];
                        prices[i] = prices[i-1];
                        prices[i-1] = temp;
                    }
                }
 
                MealResponseItem mealResponseItem = new MealResponseItem();
                mealResponseItem.title = meal.title;
                mealResponseItem.description = meal.description;
                mealResponseItem.imagePath = meal.imagePath;
                mealResponseItem.portions = portions;
                mealResponseItem.prices = prices;

                responseData.add(mealResponseItem);

        });

        return Response.ok(responseData).build();
    }

    class MealResponseItem{
        public String title;
        public String description;
        public String imagePath;
        public String[] portions;
        public String[] prices;
    }
}
 