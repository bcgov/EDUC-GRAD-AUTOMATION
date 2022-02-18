package ca.bc.gov.restdemo.controllers;

import ca.bc.gov.restdemo.exceptions.ConflictException;
import ca.bc.gov.restdemo.exceptions.NotFoundException;
import ca.bc.gov.restdemo.exceptions.ServiceUnavailableException;
import ca.bc.gov.restdemo.exceptions.UnrecoverableException;
import ca.bc.gov.restdemo.model.DemoObject;
import ca.bc.gov.restdemo.services.RestDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

/**
 * Controller class for our REST end-points.
 */
@RestController
@RequestMapping("/")
public class RestDemoController {

    RestDemoService demoService;

    @Autowired
    public RestDemoController(RestDemoService demoService) {
        this.demoService = demoService;
    }

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = "application/json",
            produces ="application/json"
    )
    ResponseEntity<?> createDemoObject(@RequestBody DemoObject demoObject) throws UnrecoverableException, ConflictException, ServiceUnavailableException {
        // TODO: Add @Valid to demo object
        // create the object
        demoObject = demoService.createDemoObject(demoObject);
        // all good, return 201 CREATED with location header to new resource
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(demoObject.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @RequestMapping(
            path = "/{id}",
            method = RequestMethod.GET,
            produces ="application/json"
    )
        ResponseEntity<?> readDemoObject(@PathVariable String id) throws ConflictException, UnrecoverableException, ServiceUnavailableException, NotFoundException {
        return ResponseEntity.ok(demoService.getDemoObject(id));
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            produces = "application/json",
            consumes = "application/json"
    )
    ResponseEntity<?> updateDemoObject(@RequestBody DemoObject demoObject) throws UnrecoverableException, NotFoundException, ServiceUnavailableException {
        return ResponseEntity.ok(demoService.updateDemoObject(demoObject));
    }

    @RequestMapping(
            path = "{id}",
            method = RequestMethod.DELETE
    )
    ResponseEntity<?> deleteDemoObject(String id) throws UnrecoverableException, ConflictException, ServiceUnavailableException {
        demoService.deleteDemoObject(id);
        return ResponseEntity.ok("Object with id: " + id + " deleted");
    }

}
