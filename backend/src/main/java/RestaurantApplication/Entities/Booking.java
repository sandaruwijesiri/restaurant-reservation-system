package RestaurantApplication.Entities;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Booking extends PanacheEntityBase{
    @Id
    @Column(nullable=false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "email",                // the FK column
        referencedColumnName = "email",// referenced PK column
        nullable = false
    )
    private Customer customer;

    @Column(nullable=false)
    private OffsetDateTime startTime;
    @Column(nullable=false)
    private OffsetDateTime endTime;
    @Column(nullable=false)
    private BigDecimal totalPrice;
}
