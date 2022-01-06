package ca.bc.gov.educ.gtts.services;

import org.javers.core.diff.Diff;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {

    @Override
    public void reportDifferences(String ref, Diff diff) {
        if(diff.hasChanges()){
            //TODO: output to file or other means
            System.out.println(ref + " had the following reported differences: " + diff.changesSummary());
        }
    }
}
