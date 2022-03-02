package ca.bc.gov.educ.gmts;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Integration testing for grad-course-api
 */
public class GradCourseAPITests {

    @BeforeClass
    public void setup() {
        // set up
    }

    @AfterClass
    public void tearDown() {
        // tear down
    }

    /**
     * Tests /api/v1/course/check-french-immersion-course/pen/{pen}
     */
    @Test
    public void testCheckFrenchImmersionCourse(){
        assertTrue(true);
    }

}
