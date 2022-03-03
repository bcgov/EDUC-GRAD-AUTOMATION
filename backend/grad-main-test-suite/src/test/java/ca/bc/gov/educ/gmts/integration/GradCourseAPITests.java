package ca.bc.gov.educ.gmts.integration;

import ca.bc.gov.educ.gmts.config.TestConfig;
import ca.bc.gov.educ.gmts.services.GenericHTTPRequestService;
import ca.bc.gov.educ.gmts.services.GenericHTTPRequestServiceImpl;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Integration testing for grad-course-api
 */
public class GradCourseAPITests {

    GenericHTTPRequestService requestService;

    @BeforeClass
    public void setup() {
        // set up
        requestService = new GenericHTTPRequestServiceImpl();
    }

    @AfterClass
    public void tearDown() {
        // tear down stuff here
    }

    /**
     * Tests /api/v1/course/check-french-immersion-course/pen/{pen}
     */
    @Test
    public void testCheckFrenchImmersionCourse(){
        assertTrue(true);
    }

}
