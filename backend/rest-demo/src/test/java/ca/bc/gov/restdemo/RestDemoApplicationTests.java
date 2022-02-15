package ca.bc.gov.restdemo;

import ca.bc.gov.restdemo.controllers.ExceptionHandlerController;
import ca.bc.gov.restdemo.controllers.RestDemoController;
import ca.bc.gov.restdemo.exceptions.NotFoundException;
import ca.bc.gov.restdemo.services.RestDemoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
class RestDemoApplicationTests {

    @MockBean
    RestDemoService restDemoService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetReturnsNotFound() throws Exception {
        String id = UUID.randomUUID().toString();
        String message = "Object with id: " + id + " not found.";

        Mockito.when(restDemoService.getDemoObject(id)).thenThrow(new NotFoundException(message));
        mockMvc.perform(get("/" + id))
                .andExpect(status().isNotFound()).andExpect(jsonPath("$.message", is(message)));

    }

}
