package ca.bc.gov.gradtraxcomparisontest;

import ca.bc.gov.gradtraxcomparisontest.exception.TraxBatchServiceException;
import ca.bc.gov.gradtraxcomparisontest.services.CompareServiceTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class GradTraxComparisonTestApplication {

    @Autowired
    CompareServiceTest compareServiceTest;

    public static void main(String[] args) {
        SpringApplication.run(GradTraxComparisonTestApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void fireTest() {
        try {
            compareServiceTest.beginTest();
        } catch (TraxBatchServiceException e) {
            e.printStackTrace();
        }
    }


}
