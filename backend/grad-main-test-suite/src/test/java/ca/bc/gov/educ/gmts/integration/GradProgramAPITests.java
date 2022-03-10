package ca.bc.gov.educ.gmts.integration;

import ca.bc.gov.educ.gmts.config.RequiredProperties;
import ca.bc.gov.educ.gmts.config.TestConfig;
import ca.bc.gov.educ.gmts.model.programapi.GraduationProgramCode;
import ca.bc.gov.educ.gmts.utils.URLUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.URISyntaxException;

import static io.restassured.RestAssured.*;

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
