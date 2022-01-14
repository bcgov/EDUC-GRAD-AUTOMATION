package ca.bc.gov.educ.gtts.services;

import org.javers.core.diff.Diff;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {

    @Override
    public void reportDifferences(String ref, Diff diff) {
        if(diff.hasChanges()){
            //TODO: output to file or other means
            threadSafePrintln(ref + " had the following reported differences: " + diff.prettyPrint());
        }
    }

    @Override
    public void report(String s) {
        threadSafePrintln(s);
    }

    private void threadSafePrintln(String s){
        synchronized (System.out) {
            System.out.println(s);
        }
    }
}
