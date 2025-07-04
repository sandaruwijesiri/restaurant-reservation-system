package RestaurantApplication.Entities;

import java.math.BigDecimal;

import RestaurantApplication.Entities.CompositeKeys.MealPortionId;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

@Entity
public class MealPortion extends PanacheEntityBase{
    @EmbeddedId
    public MealPortionId id;

    
    // This tells Hibernate: use id.title as the FK to Meal.title
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("title")                   // matches the field name in MealPortionId
    @JoinColumn(name = "title")        // the actual column in meal_portion
    public Meal meal;

    @Column(nullable=false)
    public BigDecimal price;
}
