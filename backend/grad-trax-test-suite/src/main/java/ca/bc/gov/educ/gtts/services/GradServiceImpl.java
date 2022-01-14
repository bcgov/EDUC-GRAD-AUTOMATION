package ca.bc.gov.educ.gtts.services;

import ca.bc.gov.educ.gtts.config.GttsProperties;
import ca.bc.gov.educ.gtts.exception.GenericHTTPRequestServiceException;
import ca.bc.gov.educ.gtts.exception.NotFoundException;
import ca.bc.gov.educ.gtts.model.dto.GradSearchStudent;
import ca.bc.gov.educ.gtts.model.dto.grad.algorithm.ExceptionMessage;
import ca.bc.gov.educ.gtts.model.dto.grad.algorithm.GraduationData;
import ca.bc.gov.educ.gtts.utilities.JSONUtilities;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Scope("prototype")
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
    public GraduationData graduateStudent(String studentID, String program) throws GenericHTTPRequestServiceException, NotFoundException {
        String url = gttsProperties.getAndExpandEndPoint("grad-algorithm-api-graduate", Map.of("studentId", studentID.toString(), "gradProgram", program));
        GraduationData graduationData = requestService.get(url, GraduationData.class);
        if(graduationData == null){
            throw new NotFoundException("Student with id: " + studentID.toString() + " not found during graduateStudent");
        }
        return graduationData;
    }

    @Override
    public GraduationData runProjectedGraduation(String studentID, String program) throws GenericHTTPRequestServiceException, NotFoundException {
        String url = gttsProperties.getAndExpandEndPoint("grad-algorithm-api-projected", Map.of("studentId", studentID, "gradProgram", program, "isProjected", "true"));
        GraduationData projectedGraduationData = requestService.get(url, GraduationData.class);
        if(projectedGraduationData == null){
            throw new NotFoundException("Student with id: " + studentID.toString() + " not found during graduateStudent");
        }
        return projectedGraduationData;
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
