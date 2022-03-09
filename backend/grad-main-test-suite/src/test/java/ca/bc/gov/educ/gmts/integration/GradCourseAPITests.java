package ca.bc.gov.educ.gmts.integration;

import ca.bc.gov.educ.gmts.config.RequiredProperties;
import ca.bc.gov.educ.gmts.config.TestConfig;
import ca.bc.gov.educ.gmts.exceptions.GenericHTTPRequestServiceException;
import ca.bc.gov.educ.gmts.exceptions.NotFoundException;
import ca.bc.gov.educ.gmts.model.courseapi.CourseRestriction;
import ca.bc.gov.educ.gmts.model.courseapi.CourseRestrictions;
import ca.bc.gov.educ.gmts.services.GenericHTTPRequestService;
import ca.bc.gov.educ.gmts.services.GenericHTTPRequestServiceImpl;
import ca.bc.gov.educ.gmts.utils.URLUtils;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertTrue;

/**
 * Integration testing for grad-course-api
 */
public class GradCourseAPITests {

    GenericHTTPRequestService requestService;
    private static final String COURSE_RESTRICTION_PATH = "/api/v1/course/course-restriction";

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
    public void testGetCourseRestrictionsWithParams_ExpectModel() throws GenericHTTPRequestServiceException, NotFoundException, URISyntaxException {
        String courseCode = "courseCode";
        String courseLevel = "courseLevel";
        Map<String, String> params = new HashMap<>(){
            {
                put(courseCode, "ACSF");
                put(courseLevel, "11");
            }
        };
        String url = URLUtils.getURL(RequiredProperties.STUDENT_COURSE_API_URL, COURSE_RESTRICTION_PATH, params);
        CourseRestrictions restrictions = requestService.get(url, CourseRestrictions.class);
        if(restrictions != null && !restrictions.getCourseRestrictionList().isEmpty()){
            CourseRestriction restriction = restrictions.getCourseRestrictionList().get(0);
            assertTrue(
                    restriction.getMainCourse().equalsIgnoreCase(params.get(courseCode)) &&
                    restriction.getMainCourseLevel().equalsIgnoreCase(params.get(courseLevel))
            );
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

    @Test
    public void testGetCourseRestrictionByRestrictedCourseCodeAndLevel_ExpectObject() throws URISyntaxException, GenericHTTPRequestServiceException, NotFoundException {
        Map<String, String> params = new HashMap<>(){
            {
                put("courseCode", "GRPR");
                put("courseLevel", "11");
                put("restrictedCourseCode", "GRPRF");
                put("restrictedCourseLevel", "11");
            }
        };
        String url = URLUtils.getURL(RequiredProperties.STUDENT_COURSE_API_URL, COURSE_RESTRICTION_PATH, params);
        CourseRestrictions restrictions = requestService.get(url, CourseRestrictions.class);
        if(restrictions != null && !restrictions.getCourseRestrictionList().isEmpty()){
            CourseRestriction restriction = restrictions.getCourseRestrictionList().get(0);
            assertTrue(
                    restriction.getMainCourse().equalsIgnoreCase(params.get("courseCode")) &&
                            restriction.getMainCourseLevel().equalsIgnoreCase(params.get("courseLevel")) &&
                            restriction.getRestrictedCourse().equalsIgnoreCase(params.get("restrictedCourseCode")) &&
                            restriction.getRestrictedCourseLevel().equalsIgnoreCase(params.get("restrictedCourseLevel"))
            );
        } else {
            Assert.fail();
        }
    }

}
