package ca.bc.gov.educ.gmts.integration;

import ca.bc.gov.educ.gmts.config.RequiredProperties;
import ca.bc.gov.educ.gmts.config.TestConfig;
import ca.bc.gov.educ.gmts.exceptions.GenericHTTPRequestServiceException;
import ca.bc.gov.educ.gmts.exceptions.NotFoundException;
import ca.bc.gov.educ.gmts.model.courseapi.CourseRestriction;
import ca.bc.gov.educ.gmts.model.courseapi.CourseRestrictions;
import ca.bc.gov.educ.gmts.services.GenericHTTPRequestService;
import ca.bc.gov.educ.gmts.services.GenericHTTPRequestServiceImpl;
import org.testng.Assert;
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
     * Tests /api/v1/course/course-restriction
     */
    @Test
    public void testGetCourseRestrictionsWithParams_ExpectModel() throws GenericHTTPRequestServiceException, NotFoundException {
        String mainCourse = "ACSF";
        String mainCourseLevel = "11";
        String url = TestConfig.getInstance().getApiEndPoint(RequiredProperties.STUDENT_COURSE_API_URL) + "/api/v1/course/course-restriction?courseCode=" + mainCourse + "&courseLevel=" + mainCourseLevel;
        CourseRestrictions restrictions = requestService.get(url, CourseRestrictions.class);
        if(restrictions != null && !restrictions.getCourseRestrictionList().isEmpty()){
            CourseRestriction restriction = restrictions.getCourseRestrictionList().get(0);
            assertTrue(
                    restriction.getMainCourse().equalsIgnoreCase(mainCourse) &&
                    restriction.getMainCourseLevel().equalsIgnoreCase(mainCourseLevel));
        } else {
            Assert.fail();
        }

    }

    /**
     * Tests /api/v1/course/course-restriction
     */
    @Test
    public void testGetAllCourseRestrictions_ExpectList() throws GenericHTTPRequestServiceException, NotFoundException {
        String url = TestConfig.getInstance().getApiEndPoint(RequiredProperties.STUDENT_COURSE_API_URL) + "/api/v1/course/course-restriction";
        CourseRestrictions restrictions = requestService.get(url, CourseRestrictions.class);
        assertTrue(restrictions != null && restrictions.getCourseRestrictionList().size() > 0);
    }

}
