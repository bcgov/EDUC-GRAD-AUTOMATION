package ca.bc.gov.educ.gtts.batch;

import org.springframework.batch.item.support.ListItemReader;

import java.util.List;

/**
 * A simple ListItemReader for returning items from the list one at a time
 */
public class GradTraxCompareReader extends ListItemReader<String> {

    public GradTraxCompareReader(List<String> list) {
        super(list);
    }

}
