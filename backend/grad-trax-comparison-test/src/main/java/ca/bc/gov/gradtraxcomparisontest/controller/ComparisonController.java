package ca.bc.gov.gradtraxcomparisontest.controller;

import ca.bc.gov.gradtraxcomparisontest.exception.GenericHTTPRequestServiceException;
import ca.bc.gov.gradtraxcomparisontest.exception.NotFoundException;
import ca.bc.gov.gradtraxcomparisontest.model.CompareWithTraxResponse;
import ca.bc.gov.gradtraxcomparisontest.services.TraxGradDataComparisonService;
import org.javers.core.diff.Diff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.resource.HttpResource;

import javax.servlet.http.HttpServletResponse;
import java.net.http.HttpResponse;

@RestController
@RequestMapping("/")
public class ComparisonController {

    TraxGradDataComparisonService traxGradDataComparisonService;

    @Autowired
    public ComparisonController(TraxGradDataComparisonService traxGradDataComparisonService) {
        this.traxGradDataComparisonService = traxGradDataComparisonService;
    }

    @GetMapping("{studentId}/{program}")
    public ResponseEntity<CompareWithTraxResponse> compareWithTrax(@PathVariable String studentId, @PathVariable String program) throws InterruptedException {
        //System.out.println("Processing: " + studentId + " with program: " + program);
        CompareWithTraxResponse compareWithTraxResponse = new CompareWithTraxResponse();
        compareWithTraxResponse.setStudentId(studentId);
        try {
            Diff diff = traxGradDataComparisonService.compareWithTraxData(studentId, program);
            if(diff.hasChanges()){
                compareWithTraxResponse.setResponseCode(409);
                compareWithTraxResponse.setMessage(diff.prettyPrint());
            } else {
                compareWithTraxResponse.setResponseCode(200);
            }
        } catch (GenericHTTPRequestServiceException e) {
            compareWithTraxResponse.setResponseCode(500);
            compareWithTraxResponse.setMessage(e.getMessage());
        } catch (NotFoundException e) {
            compareWithTraxResponse.setResponseCode(404);
        }
        //Thread.sleep(2000);
        return ResponseEntity.ok(compareWithTraxResponse);
    }

}
