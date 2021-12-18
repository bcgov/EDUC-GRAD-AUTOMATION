package ca.bc.gov.educ.gtts.services;

import ca.bc.gov.educ.gtts.exception.NotFoundException;
import ca.bc.gov.educ.gtts.model.dto.TraxStudentDto;

/**
 * Service layer for interacting with TRAX data
 */
public interface TraxService {

    TraxStudentDto findTraxStudentByPEN(String pen) throws NotFoundException;

}
