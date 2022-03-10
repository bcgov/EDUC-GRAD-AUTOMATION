package ca.bc.gov.educ.gmts.integration;

import ca.bc.gov.educ.gmts.config.RequiredProperties;
import ca.bc.gov.educ.gmts.config.TestConfig;
import ca.bc.gov.educ.gmts.exceptions.GenericHTTPRequestServiceException;
import ca.bc.gov.educ.gmts.exceptions.NotFoundException;
import ca.bc.gov.educ.gmts.model.courseapi.CourseList;
import ca.bc.gov.educ.gmts.model.courseapi.CourseRestriction;
import ca.bc.gov.educ.gmts.model.courseapi.CourseRestrictions;
import ca.bc.gov.educ.gmts.model.programapi.GraduationProgramCode;
import ca.bc.gov.educ.gmts.services.GenericHTTPRequestService;
import ca.bc.gov.educ.gmts.services.GenericHTTPRequestServiceImpl;
import ca.bc.gov.educ.gmts.utils.URLUtils;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertTrue;

/**
 * Integration testing for grad-course-api
 */
public class GradCourseAPITests {

    GenericHTTPRequestService requestService;
    private String TOKEN;
    private static final String BASE_PATH = "/api/v1/course/";
    private static final String COURSE_RESTRICTION_PATH = BASE_PATH + "course-restriction";
    private static final String COURSE_RESTRICTION_SEARCH_PATH = BASE_PATH + "courserestrictionsearch";
    private static final String COURSE_RESTRICTION_COURSE_LIST_PATH = COURSE_RESTRICTION_PATH + "/course-list";
    private static final String RESTRICTION_PATH = BASE_PATH + "restriction";

    @BeforeClass
    public void setup() {
        // set up
        this.requestService = new GenericHTTPRequestServiceImpl();
        this.TOKEN = TestConfig.getInstance().getAccessToken();
    }

    @AfterClass
    public void tearDown() {
        // tear down stuff here
        this.requestService = null;
    }

    /**
     * Tests /api/v1/course/course-restriction?courseCode=&courseLevel=
     * Find all course restrictions by course code and level
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
     * Find all course restrictions by course code and level (no params)
     */
    @Test
    public void testGetAllCourseRestrictions_ExpectList() throws GenericHTTPRequestServiceException, NotFoundException, URISyntaxException {
        String url = URLUtils.getURL(RequiredProperties.STUDENT_COURSE_API_URL, COURSE_RESTRICTION_PATH, null);
        CourseRestrictions restrictions = requestService.get(url, CourseRestrictions.class);
        assertTrue(restrictions != null && restrictions.getCourseRestrictionList().size() > 0);
    }

    /**
     * Tests /api/v1/course/course-restriction?courseCode=&courseLevel=&restrictedCourseCode&restrictedCourseLevel=
     * Find Course Restriction by Course Code, Course Level, Restricted Course Code, and Restricted Course Level
     * @throws URISyntaxException
     * @throws GenericHTTPRequestServiceException
     * @throws NotFoundException
     */
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


    /**
     * Tests /api/v1/course/courserestrictionsearch with no params
     * @throws URISyntaxException
     */
    @Test
    public void testCourseRestrictionSearchWithNoParams_ExpectBadRequest() throws URISyntaxException {
        String url = URLUtils.getURL(RequiredProperties.STUDENT_COURSE_API_URL, COURSE_RESTRICTION_SEARCH_PATH, null);
        Response response = given().auth()
                .oauth2(TOKEN)
                .get(url);
        Assert.assertEquals(response.statusCode(), 400);
    }

    /**
     * /api/v1/course/course-restriction/course-list
     * @throws URISyntaxException
     */
    @Test
    public void testCourseRestrictionsSearch_ExpectObject() throws URISyntaxException {
        Map<String, String> params = new HashMap<>(){
            {
                put("mainCourseCode", "IMANF");
                put("mainCourseLevel", "11");
            }
        };
        String url = URLUtils.getURL(RequiredProperties.STUDENT_COURSE_API_URL, COURSE_RESTRICTION_SEARCH_PATH, params);
        CourseRestriction[] courseRestrictions = given().auth().oauth2(TOKEN).get(url).as(CourseRestriction[].class);
        if(courseRestrictions != null && courseRestrictions.length > 0){
            CourseRestriction restriction = courseRestrictions[0];
            Assert.assertEquals(restriction.getMainCourse(), "IMANF");
        } else {
            Assert.fail("Returned restrictions were either null or empty.");
        }
    }

    /**
     * Tests /api/v1/course/course-restriction/course-list
     * Find all Course Restrictions by Course Code list
     * @throws URISyntaxException
     */
    @Test
    public void testGetCourseRestrictionsListByCourse() throws URISyntaxException {
        String url = URLUtils.getURL(RequiredProperties.STUDENT_COURSE_API_URL, COURSE_RESTRICTION_COURSE_LIST_PATH, null);
        List<String> codes = new ArrayList<>(){
            {
                add("QBI");
                add("QFMP");
            }
        };
        CourseList courseList = new CourseList();
        courseList.setCourseCodes(codes);
        Response response = given()
                .auth()
                .oauth2(TOKEN)
                .header("Content-type", "application/json")
                .and()
                .body(courseList)
                .when()
                .post(url)
                .then()
                .extract().response();
        CourseRestrictions restrictions = response.getBody().as(CourseRestrictions.class);
        Assert.assertTrue(response.statusCode() == 200 && !restrictions.getCourseRestrictionList().isEmpty());
    }

    /**
     * Tests /api/v1/course/restriction?pageNo=0&pageSize=150
     * Find all course restrictions (with paging)
     */
    @Test
    public void testGetCourseRestrictionSearch() throws URISyntaxException {
        Map<String, String> params = new HashMap<>(){
            {
                put("pageNo", "0");
                put("pageSize", "9");
            }
        };
        String url = URLUtils.getURL(RequiredProperties.STUDENT_COURSE_API_URL, RESTRICTION_PATH, params);
        CourseRestriction[] restrictions = given().auth().oauth2(TOKEN).get(url).as(CourseRestriction[].class);
        if(restrictions != null && restrictions.length > 0){
            Assert.assertEquals(restrictions.length, 10);
        } else {
            Assert.fail("Returned restrictions list was empty.");
        }
    }


}
