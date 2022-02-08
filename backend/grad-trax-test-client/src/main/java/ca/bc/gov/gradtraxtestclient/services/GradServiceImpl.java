package ca.bc.gov.gradtraxtestclient.services;


import ca.bc.gov.gradtraxtestclient.config.GttsProperties;
import ca.bc.gov.gradtraxtestclient.exception.GenericHTTPRequestServiceException;
import ca.bc.gov.gradtraxtestclient.exception.NotFoundException;
import ca.bc.gov.gradtraxtestclient.model.dto.grad.algorithm.GradSearchStudent;
import ca.bc.gov.gradtraxtestclient.model.dto.grad.algorithm.GraduationData;
import ca.bc.gov.gradtraxtestclient.model.dto.students.api.GraduationStudentRecord;
import ca.bc.gov.gradtraxtestclient.utilities.JSONUtilities;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class GradServiceImpl implements GradService {

    private GenericHTTPRequestService requestService;
    private GttsProperties gttsProperties;

    @Autowired
    public GradServiceImpl(GttsProperties gttsProperties, GenericHTTPRequestService genericHTTPRequestService) {
        this.gttsProperties = gttsProperties;
        this.requestService = genericHTTPRequestService;
    }

    @Override
    public GradSearchStudent getStudentByPen(String pen) throws GenericHTTPRequestServiceException, NotFoundException {
        String url = gttsProperties.getAndExpandEndPoint("students-api-search-by-pen", Map.of("pen", pen));
        List students = requestService.get(url, ArrayList.class);
        List<GradSearchStudent> gradSearchStudent = JSONUtilities.serializeListOfJSONToObjectList(students, new TypeReference<List<GradSearchStudent>>(){});
        if(gradSearchStudent == null || gradSearchStudent.isEmpty()){
            throw new NotFoundException("Student with PEN: " + pen + " not found in GRAD");
        }
        return gradSearchStudent.get(0);
    }

    @Override
    public GradSearchStudent getStudentByID(String id) throws GenericHTTPRequestServiceException, NotFoundException {
        String url = gttsProperties.getAndExpandEndPoint("students-api-search-by-id", Map.of("stdid", id));
        GradSearchStudent gradSearchStudent = requestService.get(url, GradSearchStudent.class);
        if(gradSearchStudent == null){
            throw new NotFoundException("Student with id: " + id + " not found.");
        }
        return gradSearchStudent;
    }

    @Override
    public GraduationData graduateStudent(String studentID, String program) throws GenericHTTPRequestServiceException, NotFoundException {
        String url = gttsProperties.getAndExpandEndPoint("grad-algorithm-api-graduate", Map.of("studentId", studentID.toString(), "gradProgram", program));
        return getGraduationData(studentID, url);
    }

    @Override
    public GraduationData runProjectedGraduation(String studentID, String program) throws GenericHTTPRequestServiceException, NotFoundException {
        String url = gttsProperties.getAndExpandEndPoint("grad-algorithm-api-projected", Map.of("studentId", studentID, "gradProgram", program, "isProjected", "true"));
        return getGraduationData(studentID, url);
    }

    private GraduationData getGraduationData(String studentID, String url) throws GenericHTTPRequestServiceException, NotFoundException {
        GraduationData projectedGraduationData = requestService.get(url, GraduationData.class);
        if(projectedGraduationData == null){
            throw new NotFoundException("Student with id: " + studentID + " not found during graduateStudent");
        }
        if(projectedGraduationData.getException() != null){
            throw new GenericHTTPRequestServiceException("Error running projected grad: " + projectedGraduationData.getException().getExceptionDetails());
        }
        return projectedGraduationData;
    }

    @Override
    public List<GraduationStudentRecord> getProjectedGradStudentList() throws GenericHTTPRequestServiceException, NotFoundException {
        String url = gttsProperties.getEndPoint("students-api-projectedgrad-list");
        return getGradStudentList(url);
    }

    @Override
    public List<GraduationStudentRecord> getGradStudentList() throws GenericHTTPRequestServiceException, NotFoundException {
        String url = gttsProperties.getEndPoint("students-api-grad-list");
        return getGradStudentList(url);
    }

    private List<GraduationStudentRecord> getGradStudentList(String url) throws NotFoundException, GenericHTTPRequestServiceException {
        List records = requestService.get(url, ArrayList.class);
        List<GraduationStudentRecord> gradStudentRecords = JSONUtilities.serializeListOfJSONToObjectList(records, new TypeReference<List<GraduationStudentRecord>>(){});
        if(gradStudentRecords == null || gradStudentRecords.isEmpty()){
            throw new NotFoundException("No records were returned from StudentsAPI for (projected) graduation.");
        }
        return gradStudentRecords;
    }

    // below are for returning data from grad algorithm
    // TODO: update using internal webClient
    /**public GraduationData runGradAlgorithm(UUID studentID, String program, String accessToken, ExceptionMessage exception) {
        try {
            return webClient.get().uri(String.format(educGraduationApiConstants.getGradAlgorithmEndpoint(),studentID,program)).headers(h -> h.setBearerAuth(accessToken)).retrieve().bodyToMono(GraduationData.class).block();
        }catch(Exception e) {
            exception.setExceptionName("GRAD-ALGORITHM-API IS DOWN");
            exception.setExceptionDetails(e.getLocalizedMessage());
            return null;
        }
    }

    public GraduationData runProjectedAlgorithm(UUID studentID, String program,String accessToken) {
        return webClient.get().uri(String.format(educGraduationApiConstants.getGradProjectedAlgorithmEndpoint(), studentID,program, true)).headers(h -> h.setBearerAuth(accessToken)).retrieve().bodyToMono(GraduationData.class).block();
    }**/


}
