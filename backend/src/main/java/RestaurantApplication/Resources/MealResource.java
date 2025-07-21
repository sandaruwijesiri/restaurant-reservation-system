package RestaurantApplication.Resources;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import RestaurantApplication.Entities.Meal;
import RestaurantApplication.Entities.MealPortion;
import RestaurantApplication.Services.MealService;
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

        return MealService.getMealsAvailable();
    }
}
 