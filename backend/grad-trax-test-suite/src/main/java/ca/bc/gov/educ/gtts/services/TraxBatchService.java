package ca.bc.gov.educ.gtts.services;

import ca.bc.gov.educ.gtts.exception.TraxBatchServiceException;

import java.util.List;

/**
 * Trax batch service
 */
public interface TraxBatchService {

    boolean runTest(List<String> pens) throws TraxBatchServiceException;

    boolean runTest() throws TraxBatchServiceException;

}
