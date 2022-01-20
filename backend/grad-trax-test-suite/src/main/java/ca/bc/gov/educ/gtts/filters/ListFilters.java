package ca.bc.gov.educ.gtts.filters;

import ca.bc.gov.educ.gtts.model.dto.students.api.GraduationStudentRecord;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class ListFilters {

    /**
     * Filters GraduationStudentRecordLists based on passed in Lambdas
     * @param filter a predicate
     * @param graduationStudentRecordList the list to filter
     * @return the filtered list
     */
    public List<GraduationStudentRecord> filterGraduationStudentRecordList(Predicate<GraduationStudentRecord> filter, List<GraduationStudentRecord> graduationStudentRecordList) {
        return graduationStudentRecordList.stream()
                .filter(filter)
                .collect(Collectors.toList());
    }

}
