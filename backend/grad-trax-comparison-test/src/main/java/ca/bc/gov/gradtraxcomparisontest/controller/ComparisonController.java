package ca.bc.gov.gradtraxcomparisontest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ComparisonController {

    @GetMapping("{studentId}")
    public ResponseEntity<String> compareWithTrax(@PathVariable String studentId) throws InterruptedException {
        System.out.println("Processing: " + studentId);
        Thread.sleep(2000);
        return ResponseEntity.ok("Processed: " + studentId);
    }

}
