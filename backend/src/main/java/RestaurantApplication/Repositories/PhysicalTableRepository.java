package RestaurantApplication.Repositories;

import java.util.List;

import RestaurantApplication.Entities.PhysicalTable;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@ApplicationScoped
public class PhysicalTableRepository {
    @Inject
    EntityManager entityManager;

    public List<Integer> getAvailablePhysicalTableIDs(String tableType, List<Integer> unavailablePhysicalTables){
        Query query = entityManager.createQuery(
            "SELECT id FROM PhysicalTable WHERE type = :type" + (unavailablePhysicalTables.isEmpty() ? "" : " AND (id NOT IN :excludedids)")
        )
        .setParameter("type", tableType);
        
        if(!unavailablePhysicalTables.isEmpty()){
            query.setParameter("excludedids", unavailablePhysicalTables);
        }
        return query.getResultList();
    }

    public static PhysicalTable findById(Integer Id){
        return PhysicalTable.findById(Id);
    }

    public static void addPhysicalTable(String type){
        PhysicalTable physicalTable = new PhysicalTable();
        physicalTable.setType(type);
        physicalTable.persist();
    }
}
