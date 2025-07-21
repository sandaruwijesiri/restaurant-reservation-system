package RestaurantApplication.Services;

import java.util.List;

import RestaurantApplication.Entities.Meal;
import RestaurantApplication.Entities.MealPortion;
import RestaurantApplication.Repositories.MealPortionRepository;

public class MealPortionService {
    public static List<MealPortion> getMealPortionsByTitle(Meal meal){
        return MealPortionRepository.getMealPortionsByTitle(meal);
    }
}
