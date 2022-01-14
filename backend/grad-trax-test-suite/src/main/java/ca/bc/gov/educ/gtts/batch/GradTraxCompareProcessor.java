package ca.bc.gov.educ.gtts.batch;

import org.springframework.batch.item.ItemProcessor;

public class GradTraxCompareProcessor implements ItemProcessor<String, String> {


    @Override
    public String process(String s) throws Exception {
        System.out.println("Processing: " + s + " from reader.");
        s = s + " processed";
        return s;
    }

}
