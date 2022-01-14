package ca.bc.gov.educ.gtts.batch;

import ca.bc.gov.educ.gtts.services.ReportService;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class GradTraxCompareWriter implements ItemWriter<String> {

    @Autowired
    ReportService reportService;

    @Override
    public void write(List<? extends String> list) throws Exception {
        if(!list.isEmpty()){
            reportService.report(list.get(0));
        }
    }

}
