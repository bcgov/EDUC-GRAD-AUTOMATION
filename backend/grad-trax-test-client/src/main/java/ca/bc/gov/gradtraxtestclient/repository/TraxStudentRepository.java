package ca.bc.gov.gradtraxtestclient.repository;

import ca.bc.gov.gradtraxtestclient.model.entity.TraxStudentEntity;
import org.springframework.data.repository.CrudRepository;

public interface TraxStudentRepository extends CrudRepository<TraxStudentEntity, String> {

}
