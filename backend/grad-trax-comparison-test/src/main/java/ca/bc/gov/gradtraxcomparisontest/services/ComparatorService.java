package ca.bc.gov.gradtraxcomparisontest.services;

import ca.bc.gov.gradtraxcomparisontest.model.dto.TraxGradComparatorDto;
import org.javers.core.diff.Diff;

/**
 * Specialized comparison service for different object types
 */
public interface ComparatorService {

    // returns a Diff object
    Diff compareTraxGradDTOs(TraxGradComparatorDto dto1, TraxGradComparatorDto dto2);

}
