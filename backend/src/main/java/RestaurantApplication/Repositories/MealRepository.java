package RestaurantApplication.Repositories;

import java.util.List;

import RestaurantApplication.Entities.Meal;

public class MealRepository {
    public static List<Meal> getAll(){
        return Meal.listAll();
    }
}
