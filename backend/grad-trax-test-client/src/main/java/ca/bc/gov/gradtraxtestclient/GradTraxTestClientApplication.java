package ca.bc.gov.gradtraxtestclient;

import ca.bc.gov.gradtraxtestclient.exception.TraxBatchServiceException;
import ca.bc.gov.gradtraxtestclient.services.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class GradTraxTestClientApplication {

    @Autowired
    TestService testService;

    public static void main(String[] args) {
        SpringApplication.run(GradTraxTestClientApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void fireTest() {
        try {
            testService.beginTest();
        } catch (TraxBatchServiceException e) {
            e.printStackTrace();
        }
    }

}
