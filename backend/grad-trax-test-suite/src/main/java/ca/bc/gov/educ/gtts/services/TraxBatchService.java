package ca.bc.gov.educ.gtts.services;

import ca.bc.gov.educ.gtts.exception.TraxBatchServiceException;
import ca.bc.gov.educ.gtts.model.dto.students.api.GraduationStudentRecord;

import java.util.List;
import java.util.function.Predicate;

/**
 * Trax batch service
 */
public interface TraxBatchService {

    void runTest(List<String> pens) throws TraxBatchServiceException;

    void runTest() throws TraxBatchServiceException;

    void runTest(Predicate<GraduationStudentRecord> optionalFilter) throws TraxBatchServiceException;

}
