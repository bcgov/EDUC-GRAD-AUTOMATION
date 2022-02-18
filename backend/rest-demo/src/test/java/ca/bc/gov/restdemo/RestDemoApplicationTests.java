package ca.bc.gov.restdemo;

import ca.bc.gov.restdemo.exceptions.ConflictException;
import ca.bc.gov.restdemo.exceptions.NotFoundException;
import ca.bc.gov.restdemo.exceptions.ServiceUnavailableException;
import ca.bc.gov.restdemo.exceptions.UnrecoverableException;
import ca.bc.gov.restdemo.model.DemoObject;
import ca.bc.gov.restdemo.services.RestDemoServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
class RestDemoApplicationTests {

    @MockBean
    RestDemoServiceImpl restDemoService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Tests that when an Object is requested that is not found
     * endpoint returns 404 with message
     * @throws Exception
     */
    @Test
    void testGetDemoObject_ReturnsNotFound() throws Exception {
        String id = UUID.randomUUID().toString();
        String message = "Object with id: " + id + " not found.";
        Mockito.when(restDemoService.getDemoObject(id)).thenThrow(new NotFoundException(message));
        mockMvc.perform(get("/" + id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is(message)))
                .andDo(print());

    }

    /**
     * Tests when garbage data is passed, a 400 Bad Request
     * is returned with a message
     * @throws Exception
     */
    @Test
    void testGetDemoObject_withBadUUID_ReturnsBadRequest() throws Exception {
        String id = "someGarbage";
        String message = "not a valid UUID format";
        Mockito.when(restDemoService.getDemoObject(id)).thenThrow(new ConflictException(message));
        mockMvc.perform(get("/" + id))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is(message)))
                .andDo(print());
    }

    /**
     * When an upstream service is unavailable, return a 503 Service Unavailabe
     * @throws Exception
     */
    @Test
    void testGetDemoObject_whenUpstreamServiceUnavailable() throws Exception {
        String id = UUID.randomUUID().toString();
        String message = "Cannot retrieve object with id: " + id + ". Could not connect to upstream service.";
        Mockito.when(restDemoService.getDemoObject(id)).thenThrow(new ServiceUnavailableException(message));
        mockMvc.perform(get("/" + id))
                .andExpect(status().isServiceUnavailable())
                .andExpect(jsonPath("$.message", is(message)))
                .andDo(print());
    }

    /**
     * When an unrecoverable event occurs, return a 500
     * @throws Exception
     */
    @Test
    void testGetDemoObject_whenSomethingGoesHorriblyWrong() throws Exception {
        String id = UUID.randomUUID().toString();
        String message = "Cannot get object with id: " + id + ". There was an unrecoverable error.";
        Mockito.when(restDemoService.getDemoObject(id)).thenThrow(new UnrecoverableException(message));
        mockMvc.perform(get("/" + id))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message", is(message)))
                .andDo(print());
    }

    /**
     * Error thrown when trying to create an object that already exists
     * @throws Exception
     */
    @Test
    void testCreateDemoObject_objectAlreadyExists() throws Exception {
        UUID id = UUID.randomUUID();
        DemoObject myDemoObject = DemoObject.builder().name("myDemoObject").id(id).build();
        String json = objectMapper.writeValueAsString(myDemoObject);
        Mockito.when(restDemoService.createDemoObject(myDemoObject)).thenCallRealMethod();
        mockMvc.perform(post("/").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Demo object with id: " + id + " already exists.")))
                .andDo(print());
    }

    /**
     * Tests that status is created when creating an object
     * @throws Exception
     */
    @Test
    void testCreateDemoObject_ok() throws Exception {
        DemoObject myDemoObject = DemoObject.builder().name("myDemoObject").build();
        String json = objectMapper.writeValueAsString(myDemoObject);
        Mockito.when(restDemoService.createDemoObject(myDemoObject)).thenCallRealMethod();
        mockMvc.perform(post("/").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated())
                .andDo(print());
    }

}
