package ca.bc.gov.educ.gtts.batch;

import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class GradTraxCompareWriter implements ItemWriter<String> {

    @Override
    public void write(List<? extends String> list) throws Exception {
        if(!list.isEmpty()){
            System.out.println("Writing: " + list.get(0));
        }
    }

}
