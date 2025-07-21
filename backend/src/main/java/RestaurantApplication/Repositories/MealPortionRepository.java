package RestaurantApplication.Repositories;

import java.util.List;

import RestaurantApplication.Entities.Meal;
import RestaurantApplication.Entities.MealPortion;

public class MealPortionRepository {
    public static List<MealPortion> getMealPortionsByTitle(Meal meal){
        return MealPortion.find("meal = ?1 order by portion desc", meal).list();
    }
}
