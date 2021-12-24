package ca.bc.gov.educ.gtts.services;

import ca.bc.gov.educ.gtts.exception.GenericHTTPRequestServiceException;
import ca.bc.gov.educ.gtts.exception.NotFoundException;
import ca.bc.gov.educ.gtts.model.dto.GradSearchStudent;
import ca.bc.gov.educ.gtts.model.dto.grad.algorithm.GraduationData;

import java.util.UUID;

/**
 * @author CDITCHER
 * Service for interacting with GRAD apis
 */
public interface GradService {

    GradSearchStudent getStudentByPen(String pen) throws GenericHTTPRequestServiceException, NotFoundException;

    GraduationData graduateStudent(String studentID, String program) throws GenericHTTPRequestServiceException, NotFoundException;

    GraduationData runProjectedGraduation(String studentID, String program) throws GenericHTTPRequestServiceException, NotFoundException;

}
