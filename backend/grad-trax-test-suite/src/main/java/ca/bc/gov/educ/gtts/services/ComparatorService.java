package ca.bc.gov.educ.gtts.services;

import ca.bc.gov.educ.gtts.model.dto.TraxGradComparatorDto;
import org.javers.core.diff.Diff;

/**
 * Specialized comparison service for different object types
 */
public interface ComparatorService {

    // returns a Diff object
    Diff compareTraxGradDTOs(TraxGradComparatorDto dto1, TraxGradComparatorDto dto2);

}
