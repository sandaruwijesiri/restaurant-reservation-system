package RestaurantApplication.Entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Customer extends PanacheEntityBase{
    @Id
    @Column(length = 255, nullable=false)
    private String email;
    @Column(length = 15, nullable=false)
    private String firstName;
    @Column(length = 20, nullable=false)
    private String lastName;
    @Column(length = 10, nullable=false)
    private String phoneNumber;
}
