package ca.bc.gov.educ.gtts.services;

import ca.bc.gov.educ.gtts.exception.GenericHTTPRequestServiceException;
import ca.bc.gov.educ.gtts.exception.NotFoundException;
import ca.bc.gov.educ.gtts.model.student.GradSearchStudent;

/**
 * @author CDITCHER
 * Service for interacting with GRAD apis
 */
public interface GradService {

    GradSearchStudent getStudentByPen(String pen) throws GenericHTTPRequestServiceException, NotFoundException;

}
