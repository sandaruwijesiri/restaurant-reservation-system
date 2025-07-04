package RestaurantApplication.Entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Meal extends PanacheEntityBase{
    @Id
    @Column(nullable=false)
    public Integer id;
    @Column(nullable=false)
    public String title;
    @Column(nullable=false)
    public String description;
    @Column(nullable=false)
    public String imagePath;
}
