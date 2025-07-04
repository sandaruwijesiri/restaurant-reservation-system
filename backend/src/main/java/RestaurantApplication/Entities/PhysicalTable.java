package RestaurantApplication.Entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class PhysicalTable extends PanacheEntityBase{
    @Id
    @Column(nullable=false)
    public Integer id;
    @Column(nullable=false)
    public String type;
}
