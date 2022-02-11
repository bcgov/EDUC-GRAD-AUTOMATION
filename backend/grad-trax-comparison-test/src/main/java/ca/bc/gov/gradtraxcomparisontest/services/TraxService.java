package ca.bc.gov.gradtraxcomparisontest.services;

import ca.bc.gov.gradtraxcomparisontest.exception.NotFoundException;
import ca.bc.gov.gradtraxcomparisontest.model.dto.TraxStudentDto;
import ca.bc.gov.gradtraxcomparisontest.model.entity.TswTranDemogEntity;
import ca.bc.gov.gradtraxcomparisontest.model.entity.TswTranNonGradEntity;

import java.util.List;

/**
 * Service layer for interacting with TRAX data
 */
public interface TraxService {

    List<TswTranNonGradEntity> getNonGradReasons(String pen);

    TswTranDemogEntity getTransDemogEntity(String pen);

    TraxStudentDto findTraxStudentByPEN(String pen) throws NotFoundException;

}
