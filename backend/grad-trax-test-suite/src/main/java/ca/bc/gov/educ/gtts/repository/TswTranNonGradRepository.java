package ca.bc.gov.educ.gtts.repository;

import ca.bc.gov.educ.gtts.model.entity.TswTranNonGradEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TswTranNonGradRepository extends CrudRepository<TswTranNonGradEntity, String> {

    @Query(value = "SELECT e FROM TswTranNonGradEntity e WHERE e.studNo = ?1")
    Iterable<TswTranNonGradEntity> findByPen(String pen);

}
