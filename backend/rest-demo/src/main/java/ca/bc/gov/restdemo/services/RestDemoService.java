package ca.bc.gov.restdemo.services;

import ca.bc.gov.restdemo.exceptions.ConflictException;
import ca.bc.gov.restdemo.exceptions.NotFoundException;
import ca.bc.gov.restdemo.exceptions.ServiceUnavailableException;
import ca.bc.gov.restdemo.exceptions.UnrecoverableException;
import ca.bc.gov.restdemo.model.DemoObject;
import java.util.UUID;

public interface RestDemoService {

    // create
    DemoObject createDemoObject(DemoObject demoObject) throws ConflictException, ServiceUnavailableException, UnrecoverableException;

    // read
    DemoObject getDemoObject(String id) throws ServiceUnavailableException, NotFoundException, UnrecoverableException, ConflictException;

    boolean exists(UUID id);

    // update
    DemoObject updateDemoObject(DemoObject demoObject) throws ServiceUnavailableException, NotFoundException, UnrecoverableException;

    // delete
    void deleteDemoObject(String id) throws ServiceUnavailableException, UnrecoverableException, ConflictException;

}
