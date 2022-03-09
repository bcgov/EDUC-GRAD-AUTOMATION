package ca.bc.gov.educ.gmts.integration;

import ca.bc.gov.educ.gmts.config.RequiredProperties;
import ca.bc.gov.educ.gmts.config.TestConfig;
import ca.bc.gov.educ.gmts.model.programapi.GraduationProgramCode;
import ca.bc.gov.educ.gmts.utils.URLUtils;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.URISyntaxException;
import java.util.List;

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
     * Tests /api/v1/program/programs
     */
    @Test
    public void testGetAllPrograms() throws URISyntaxException {
        String url = URLUtils.getURL(RequiredProperties.STUDENT_PROGRAM_API_URL, PROGRAMS_PATH, null);
        GraduationProgramCode[] codes = given().auth()
                .oauth2(TOKEN)
                .get(url)
                .as(GraduationProgramCode[].class);
        Assert.assertTrue(codes.length > 0);
    }

}
