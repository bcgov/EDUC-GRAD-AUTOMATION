package ca.bc.gov.educ.gtts.services;

import ca.bc.gov.educ.gtts.exception.NotFoundException;
import ca.bc.gov.educ.gtts.model.dto.TraxGradComparatorDto;
import ca.bc.gov.educ.gtts.model.dto.TraxStudentDto;
import ca.bc.gov.educ.gtts.model.entity.TswTranDemogEntity;
import ca.bc.gov.educ.gtts.model.entity.TswTranNonGradEntity;

import java.util.List;

/**
 * Service layer for interacting with TRAX data
 */
public interface TraxService {

    List<TswTranNonGradEntity> getNonGradReasons(String pen);

    TswTranDemogEntity getTransDemogEntity(String pen);

    TraxStudentDto findTraxStudentByPEN(String pen) throws NotFoundException;

}
