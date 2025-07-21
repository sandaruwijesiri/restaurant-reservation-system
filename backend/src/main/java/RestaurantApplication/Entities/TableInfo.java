package RestaurantApplication.Entities;

import java.math.BigDecimal;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class TableInfo extends PanacheEntityBase{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable=false)
    private Integer id;
    @Column(nullable=false)
    private String title;
    @Column(nullable=false)
    private Integer seatCount;
    @Column(nullable=false)
    private Integer noOfTables;
    @Column(nullable=false)
    private BigDecimal priceperhour;
    @Column(nullable=false)
    private String imagepath;
    @Column(nullable=false)
    private String description;
}
