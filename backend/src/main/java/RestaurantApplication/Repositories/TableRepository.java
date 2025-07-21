package RestaurantApplication.Repositories;

import java.time.OffsetDateTime;
import java.util.List;

import RestaurantApplication.Entities.PhysicalTable;
import RestaurantApplication.Entities.TableInfo;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@ApplicationScoped
public class TableRepository {
    
    @Inject
    EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public List<Object[]> getBookedTables(OffsetDateTime startTime, OffsetDateTime endTime){
        return entityManager.createNativeQuery(
            "SELECT pt.type, pt.id, b.starttime, b.endtime " +
            "FROM ((booking b " +
            "JOIN bookingtable bt ON b.id = bt.bookingid) " +
            "JOIN physicaltable pt ON bt.tableid = pt.id) " +
            "WHERE (b.endtime > ?1 and b.starttime < ?2)"
        )
        .setParameter(1,startTime)
        .setParameter(2,endTime)
        .getResultList();
    }

    public List<TableInfo> getAllTables(){
        return TableInfo.listAll();
    }

    @Transactional
    public void addTable(@Valid TableInfo tableInfo){
        tableInfo.persist();
        for(int i=0;i<tableInfo.getNoOfTables();++i){
            PhysicalTableRepository.addPhysicalTable(tableInfo.getTitle());
        }
    }

    public boolean tableTypeExists(String type){
        return TableInfo.count("title", type)>0;
    }
}
