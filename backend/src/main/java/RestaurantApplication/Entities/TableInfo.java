package RestaurantApplication.Entities;

import java.math.BigDecimal;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class TableInfo extends PanacheEntityBase{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable=false)
    public Integer id;
    @Column(nullable=false)
    public String title;
    @Column(nullable=false)
    public Integer seatCount;
    @Column(nullable=false)
    public Integer noOfTables;
    @Column(nullable=false)
    public BigDecimal priceperhour;
    @Column(nullable=false)
    public String imagepath;
    @Column(nullable=false)
    public String description;
}
