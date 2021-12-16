package ca.bc.gov.educ.gtts;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class GttsApplicationTests {

	@Autowired
	ApplicationContext ctx;



	/*@Test
	void testIntegrationTraxBatch() throws Exception {
		CommandLineRunner runner = ctx.getBean(CommandLineRunner.class);
		runner.run ( "integration", "traxbatch");
		// TODO: add assertion
	}*/

}
