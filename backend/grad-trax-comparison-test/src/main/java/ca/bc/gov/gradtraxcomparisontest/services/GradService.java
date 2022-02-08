package ca.bc.gov.gradtraxcomparisontest.services;

import ca.bc.gov.gradtraxcomparisontest.exception.GenericHTTPRequestServiceException;
import ca.bc.gov.gradtraxcomparisontest.exception.NotFoundException;
import ca.bc.gov.gradtraxcomparisontest.model.dto.GradSearchStudent;
import ca.bc.gov.gradtraxcomparisontest.model.dto.grad.algorithm.GraduationData;
import ca.bc.gov.gradtraxcomparisontest.model.dto.students.api.GraduationStudentRecord;

import java.util.List;

/**
 * @author CDITCHER
 * Service for interacting with GRAD apis
 */
public interface GradService {

    GradSearchStudent getStudentByPen(String pen) throws GenericHTTPRequestServiceException, NotFoundException;

    GradSearchStudent getStudentByID(String id) throws GenericHTTPRequestServiceException, NotFoundException;

    GraduationData graduateStudent(String studentID, String program) throws GenericHTTPRequestServiceException, NotFoundException;

    GraduationData runProjectedGraduation(String studentID, String program) throws GenericHTTPRequestServiceException, NotFoundException;

    List<GraduationStudentRecord> getProjectedGradStudentList() throws GenericHTTPRequestServiceException, NotFoundException;

    List<GraduationStudentRecord> getGradStudentList() throws GenericHTTPRequestServiceException, NotFoundException;

}
