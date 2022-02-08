package ca.bc.gov.gradtraxtestclient.services;

import ca.bc.gov.gradtraxtestclient.exception.GenericHTTPRequestServiceException;
import ca.bc.gov.gradtraxtestclient.exception.NotFoundException;
import ca.bc.gov.gradtraxtestclient.model.dto.grad.algorithm.GradSearchStudent;
import ca.bc.gov.gradtraxtestclient.model.dto.grad.algorithm.GraduationData;
import ca.bc.gov.gradtraxtestclient.model.dto.students.api.GraduationStudentRecord;

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
