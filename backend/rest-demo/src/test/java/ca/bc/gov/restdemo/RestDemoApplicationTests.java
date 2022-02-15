package ca.bc.gov.restdemo;

import ca.bc.gov.restdemo.controllers.RestDemoController;
import ca.bc.gov.restdemo.services.RestDemoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(RestDemoController.class)
class RestDemoApplicationTests {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    RestDemoService restDemoService;

    @Test
    void testGetReturnsNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/" + UUID.randomUUID()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
