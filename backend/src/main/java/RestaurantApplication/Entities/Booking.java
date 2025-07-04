package RestaurantApplication.Entities;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Booking extends PanacheEntityBase{
    @Id
    @Column(nullable=false)
    public Integer id;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "nic",                // the FK column
        referencedColumnName = "nic",// referenced PK column
        nullable = false
    )
    public Customer customer;

    @Column(nullable=false)
    public OffsetDateTime startTime;
    @Column(nullable=false)
    public OffsetDateTime endTime;
    @Column(nullable=false)
    public BigDecimal totalPrice;
}
