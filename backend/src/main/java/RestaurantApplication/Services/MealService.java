package RestaurantApplication.Services;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import RestaurantApplication.Entities.Meal;
import RestaurantApplication.Entities.MealPortion;
import RestaurantApplication.Repositories.MealRepository;
import RestaurantApplication.Utilities.Utilities.MealPortionValues;
import RestaurantApplication.Utilities.Utilities.MealResponseItem;
import jakarta.ws.rs.core.Response;

public class MealService {
    public static Response getMealsAvailable(){
        
        DecimalFormat formatter = new DecimalFormat("#,##0.00");

        List<Meal> meals = MealRepository.getAll();
        List<MealResponseItem> responseData = new ArrayList<>();
        meals.forEach(
            (meal) -> {
                List<MealPortion> mealPortions = MealPortionService.getMealPortionsByTitle(meal);
                String[] portions = new String[mealPortions.size()];
                String[] prices = new String[mealPortions.size()];
                
                for(int i=0;i<mealPortions.size();++i){
                    portions[i] = mealPortions.get(i).getPortion();
                    prices[i] = formatter.format(mealPortions.get(i).getPrice());

                    if(portions[i].equals(MealPortionValues.MEDIUM.value) && i>0 && portions[i-1].equals(MealPortionValues.MEDIUM_LARGE.value)){
                        String temp = portions[i];
                        portions[i] = portions[i-1];
                        portions[i-1] = temp;

                        temp = prices[i];
                        prices[i] = prices[i-1];
                        prices[i-1] = temp;
                    }
                }
 
                MealResponseItem mealResponseItem = new MealResponseItem();
                mealResponseItem.title = meal.getTitle();
                mealResponseItem.description = meal.getDescription();
                mealResponseItem.imagePath = meal.getImagePath();
                mealResponseItem.portions = portions;
                mealResponseItem.prices = prices;

                responseData.add(mealResponseItem);

        });
        return Response.ok(responseData).build();

    }
}
