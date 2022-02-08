package ca.bc.gov.gradtraxcomparisontest.controller;

import ca.bc.gov.gradtraxcomparisontest.model.CompareWithTraxResponse;
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

    @GetMapping("{studentId}/{program}")
    public ResponseEntity<CompareWithTraxResponse> compareWithTrax(@PathVariable String studentId, @PathVariable String program) throws InterruptedException {
        System.out.println("Processing: " + studentId + " with program: " + program);
        CompareWithTraxResponse compareWithTraxResponse = new CompareWithTraxResponse();
        compareWithTraxResponse.setStudentId(studentId);
        compareWithTraxResponse.setResponseCode(200);
        compareWithTraxResponse.setMessage("Completed comparison.");
        Thread.sleep(2000);
        return ResponseEntity.ok(compareWithTraxResponse);
    }

}
