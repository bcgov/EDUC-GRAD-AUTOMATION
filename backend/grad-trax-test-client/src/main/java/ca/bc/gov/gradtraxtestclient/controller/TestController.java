package ca.bc.gov.gradtraxtestclient.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TestController {

    @GetMapping("{code}")
    public ResponseEntity<String> testGetMethod(@PathVariable String code) throws InterruptedException {
        System.out.println("Processing: " + code);
        Thread.sleep(2000);
        return ResponseEntity.ok("Processed: " + code);
    }

}
