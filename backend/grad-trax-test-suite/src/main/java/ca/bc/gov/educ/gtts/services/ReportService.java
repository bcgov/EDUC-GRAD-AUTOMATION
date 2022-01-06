package ca.bc.gov.educ.gtts.services;

import org.javers.core.diff.Diff;

/**
 * A reporting tool for creating detailed reports based on various data inputs
 */
public interface ReportService {

    /**
     * Reports differences based on a Diff object
     * @param ref an optional reference for reporting
     * @param diff a diff object
     */
    void reportDifferences(String ref, Diff diff);

}
