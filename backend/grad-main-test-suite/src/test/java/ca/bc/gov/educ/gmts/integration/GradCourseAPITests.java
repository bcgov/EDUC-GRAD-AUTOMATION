package ca.bc.gov.educ.gmts.integration;

import ca.bc.gov.educ.gmts.config.RequiredProperties;
import ca.bc.gov.educ.gmts.config.TestConfig;
import ca.bc.gov.educ.gmts.exceptions.GenericHTTPRequestServiceException;
import ca.bc.gov.educ.gmts.exceptions.NotFoundException;
import ca.bc.gov.educ.gmts.model.courseapi.CourseRestrictions;
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
        this.requestService = new GenericHTTPRequestServiceImpl();

    }

    @AfterClass
    public void tearDown() {
        // tear down stuff here
        this.requestService = null;
    }

    /**
     * Tests /api/v1/course/check-french-immersion-course/pen/{pen}
     */
    @Test
    public void testCourseRestriction(){
        String url = TestConfig.getInstance().getApiEndPoint(RequiredProperties.STUDENT_COURSE_API_URL) + "/api/v1/course/course-restriction?courseCode=ACSF" + "&courseLevel=11";
        try {
            CourseRestrictions restrictions = requestService.get(url, CourseRestrictions.class);
        } catch (GenericHTTPRequestServiceException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        assertTrue(true);
    }

}
