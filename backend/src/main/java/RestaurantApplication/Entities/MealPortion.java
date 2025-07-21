package RestaurantApplication.Entities;

import java.math.BigDecimal;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class MealPortion extends PanacheEntityBase{
    
    @Id
    @Column(nullable=false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "title",                // the FK column
        referencedColumnName = "title",// referenced PK column
        nullable = false
    )
    private Meal meal;

    @Column(nullable=false)
    private String portion;
    @Column(nullable=false)
    private BigDecimal price;
}
