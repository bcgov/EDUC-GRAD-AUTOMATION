package ca.bc.gov.educ.gtts;

import ca.bc.gov.educ.gtts.model.student.GradSearchStudent;
import ca.bc.gov.educ.gtts.services.GradService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GradServiceTests {

	@Autowired
	GradService gradService;

	public GradServiceTests() {}


	@Test
	public void testIntegrationTraxBatch() throws Exception {
		String testPen = "107223315";
		GradSearchStudent gradSearchStudent = gradService.getStudentByPen(testPen);
		Assert.assertEquals(gradSearchStudent.getPen(), testPen);
	}

}
