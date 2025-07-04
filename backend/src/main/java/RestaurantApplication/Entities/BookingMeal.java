package RestaurantApplication.Entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class BookingMeal extends PanacheEntityBase{
    @Id
    @Column(nullable=false)
    public Integer id;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "bookingId",                // the FK column
        referencedColumnName = "id",// referenced PK column
        nullable = false
    )
    public Booking booking;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "mealId",                // the FK column
        referencedColumnName = "id",// referenced PK column
        nullable = false
    )
    public Meal meal;
}
