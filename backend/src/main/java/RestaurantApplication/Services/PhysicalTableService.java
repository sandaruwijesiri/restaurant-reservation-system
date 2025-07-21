package RestaurantApplication.Services;

import java.util.List;

import RestaurantApplication.Entities.TableInfo;
import RestaurantApplication.Repositories.PhysicalTableRepository;
import RestaurantApplication.Repositories.TableRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@ApplicationScoped
public class PhysicalTableService {

    @Inject
    PhysicalTableRepository physicalTableRepository;

    public List<Integer> getAvailablePhysicalTableIDs(String tableType, List<Integer> unavailablePhysicalTables){
        
        return physicalTableRepository.getAvailablePhysicalTableIDs(tableType, unavailablePhysicalTables);
    }
}
