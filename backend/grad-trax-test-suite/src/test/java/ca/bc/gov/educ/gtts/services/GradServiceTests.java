package ca.bc.gov.educ.gtts.services;

import ca.bc.gov.educ.gtts.config.GttsProperties;
import ca.bc.gov.educ.gtts.model.dto.GradSearchStudent;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GradServiceTests {

	static {
		System.setProperty("OS_HOSTED_URL", "my.os.hosted.url");
		System.setProperty("KEYCLOAK_AUTH_HOST", "https://my.keycloak.auth.host.ca");
		System.setProperty("STUDENT_API_CLIENT_ID", "my-api-client-id");
		System.setProperty("STUDENT_API_CLIENT_SECRET", "00000000-XXXX-0000-XXXX-000000000000");
		System.setProperty("STUDENT_API_USERNAME", "fakeUserName");
		System.setProperty("STUDENT_API_PASSWORD", "bogusPassword");
	}

	@Before
	public void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Autowired
	@InjectMocks
	GradServiceImpl gradService;
	@Mock
	GenericHTTPRequestServiceImpl requestService;
	@Autowired
	GttsProperties gttsProperties;
	static String testPen="123456789";

	public GradServiceTests() {}


	@Test
	public void testGetStudentByPEN() throws Exception {
		String url = gttsProperties.getAndExpandEndPoint("students-api-search-by-pen", Map.of("pen", testPen));
		when(requestService.get(url, ArrayList.class)).thenReturn(getMockStudentMap());
		GradSearchStudent gradSearchStudent = gradService.getStudentByPen(testPen);
		Assert.assertEquals(gradSearchStudent.getPen(), testPen);
	}

	private static ArrayList<Map<String, String>> getMockStudentMap(){
		ArrayList<Map<String, String>> list = new ArrayList<>();
		Map<String, String> studentMap = new LinkedHashMap<>();
		studentMap.put("studentID", "ac339d70-7649-1a2e-8176-4a0b17dhry45");
		studentMap.put("pen", testPen);
		studentMap.put("legalFirstName", "Totally");
		studentMap.put("legalMiddleNames", "Fake");
		studentMap.put("legalLastName", "Name");
		studentMap.put("dob", "1989-11-30");
		list.add(studentMap);
		return list;
	}

}
