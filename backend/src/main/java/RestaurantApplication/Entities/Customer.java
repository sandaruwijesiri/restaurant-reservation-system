package RestaurantApplication.Entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Customer extends PanacheEntityBase{
    @Id
    @Column(length = 12, nullable=false)
    public String nic;
    @Column(length = 15, nullable=false)
    public String firstName;
    @Column(length = 20, nullable=false)
    public String lastName;
    @Column(length = 10, nullable=false)
    public String phoneNumber;
    @Column(length = 30, nullable=false)
    public String email;
    @Column(length = 10, nullable=false)
    public String password;
}
