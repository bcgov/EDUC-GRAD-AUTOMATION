package ca.bc.gov.educ.gmts.integration;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Integration testing for grad-program-api
 */
public class GradProgramAPITests {

    @BeforeClass
    public void setup() {
        // set up
    }

    @AfterClass
    public void tearDown() {
        // tear down
    }

    /**
     * Tests /api/v1/program/gradrequirementtype
     */
    @Test
    public void testGetGradRequirementType(){
        assertTrue(true);
    }

}
