package RestaurantApplication.Entities.CompositeKeys;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;

@Embeddable
public class MealPortionId implements Serializable{

    public String title;
    public String portion;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MealPortionId)) return false;
        MealPortionId that = (MealPortionId) o;
        return Objects.equals(title, that.title) &&
               Objects.equals(portion, that.portion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, portion);
    }
}
