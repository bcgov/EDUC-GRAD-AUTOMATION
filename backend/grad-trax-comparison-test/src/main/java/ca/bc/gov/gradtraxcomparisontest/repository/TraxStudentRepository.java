package ca.bc.gov.gradtraxcomparisontest.repository;

import ca.bc.gov.gradtraxcomparisontest.model.entity.TraxStudentEntity;
import org.springframework.data.repository.CrudRepository;

public interface TraxStudentRepository extends CrudRepository<TraxStudentEntity, String> {

}
