package ca.bc.gov.educ.gmts.integration;

import ca.bc.gov.educ.gmts.config.RequiredProperties;
import ca.bc.gov.educ.gmts.config.TestConfig;
import ca.bc.gov.educ.gmts.utils.URLUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.URISyntaxException;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertTrue;

/**
 * Integration testing for grad-program-api
 */
public class GradProgramAPITests {

    private String TOKEN;
    private static final String PROGRAMS_PATH = "/api/v1/program/programs";

    @BeforeClass
    public void setup() {
        // set up
        this.TOKEN = TestConfig.getInstance().getAccessToken();
    }

    @AfterClass
    public void tearDown() {
        // tear down
    }

    /**
     * Tests /api/v1/program/gradrequirementtype
     */
    @Test
    public void testGetGradRequirementType() throws URISyntaxException {
        String url = URLUtils.getURL(RequiredProperties.STUDENT_PROGRAM_API_URL, PROGRAMS_PATH, null);
        given().auth().oauth2(TOKEN).get(url).then().statusCode(200);
        assertTrue(true);
    }

}
