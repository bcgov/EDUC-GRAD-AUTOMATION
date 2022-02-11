package ca.bc.gov.gradtraxcomparisontest.services;

import ca.bc.gov.gradtraxcomparisontest.exception.GenericHTTPRequestServiceException;
import ca.bc.gov.gradtraxcomparisontest.exception.NotFoundException;
import org.javers.core.diff.Diff;

public interface TraxGradDataComparisonService {

    Diff compareWithTraxData(String studentId, String program) throws GenericHTTPRequestServiceException, NotFoundException;

}
