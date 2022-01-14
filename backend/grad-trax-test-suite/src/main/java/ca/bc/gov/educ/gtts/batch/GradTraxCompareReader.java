package ca.bc.gov.educ.gtts.batch;

import ca.bc.gov.educ.gtts.services.ReportService;
import org.springframework.aop.support.AopUtils;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;

import java.util.LinkedList;
import java.util.List;

/**
 * A simple ListItemReader for returning items from the list one at a time
 */
public class GradTraxCompareReader<T> implements ItemReader<T> {

    @Autowired
    ReportService reportService;

    private List<T> list;

    public GradTraxCompareReader(List<T> list) {
        if (AopUtils.isAopProxy(list)) {
            this.list = list;
        } else {
            this.list = new LinkedList(list);
        }

    }

    @Nullable
    public T read() {
        synchronized (this) {
            if(!this.list.isEmpty()){
                String thing = (String) this.list.get(0);
                //reportService.report("Pulling: " + thing);
                return this.list.remove(0);
            }
            return null;
        }
    }
}
