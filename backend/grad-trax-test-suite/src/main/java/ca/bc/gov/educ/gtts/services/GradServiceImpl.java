package ca.bc.gov.educ.gtts.services;

import ca.bc.gov.educ.gtts.config.GttsProperties;
import ca.bc.gov.educ.gtts.exception.GenericHTTPRequestServiceException;
import ca.bc.gov.educ.gtts.exception.NotFoundException;
import ca.bc.gov.educ.gtts.model.dto.GradSearchStudent;
import ca.bc.gov.educ.gtts.utilities.JSONUtilities;
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


}
