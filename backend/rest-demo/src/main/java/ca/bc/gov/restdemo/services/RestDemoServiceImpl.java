package ca.bc.gov.restdemo.services;

import ca.bc.gov.restdemo.exceptions.ConflictException;
import ca.bc.gov.restdemo.exceptions.NotFoundException;
import ca.bc.gov.restdemo.exceptions.ServiceUnavailableException;
import ca.bc.gov.restdemo.exceptions.UnrecoverableException;
import ca.bc.gov.restdemo.model.DemoObject;
import org.springframework.stereotype.Service;
import java.util.UUID;

/**
 * Implementation of our RestDemoService
 */
@Service
public class RestDemoServiceImpl implements RestDemoService {


    @Override
    public DemoObject createDemoObject(DemoObject demoObject) throws ConflictException, ServiceUnavailableException, UnrecoverableException {
        if(demoObject.getId() != null){
            // normally this would be handled by a repository service "existsById(String id)", etc.
            // object came in with an id already.... throw a conflict
            throw new ConflictException("Demo object with id: " + demoObject.getId() + " already exists.");
        }
        // If upstream service not available, throw ServiceUnavailableException
        // if not found, throw NotFoundException
        // if all hell breaks loose, throw an UnrecoverableException
        demoObject.setId(UUID.randomUUID());
        return demoObject;
    }

    @Override
    public DemoObject getDemoObject(String id) throws ServiceUnavailableException, NotFoundException, UnrecoverableException, ConflictException {
        // If upstream service not available, throw ServiceUnavailableException
        // if not found, throw NotFoundException
        // if all hell breaks loose, throw an UnrecoverableException
        return DemoObject.builder().id(convertToUUIDFromString(id)).name("my demo object").build();
    }

    @Override
    public boolean exists(UUID id) {
        return false;
    }

    @Override
    public DemoObject updateDemoObject(DemoObject demoObject) throws ServiceUnavailableException, NotFoundException, UnrecoverableException {
        // If upstream service not available, throw ServiceUnavailableException
        // if not found, throw NotFoundException
        // if all hell breaks loose, throw an UnrecoverableException
        return null;
    }

    @Override
    public void deleteDemoObject(String id) throws ServiceUnavailableException, UnrecoverableException, ConflictException {
        // If upstream service not available, throw ServiceUnavailableException
        // if all hell breaks loose, throw an UnrecoverableException
        // normally, a repo or upstream service handles this. If not found, not big deal, as object was requested for deletion.
    }

    /**
     * Helper method for converting to a valid UUID from a String
     * @param id
     * @return
     * @throws ConflictException
     */
    private UUID convertToUUIDFromString(String id) throws ConflictException {
        UUID uId;
        try {
            uId = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new ConflictException(id + " is not a valid UUID format");
        }
        return uId;
    }
}
