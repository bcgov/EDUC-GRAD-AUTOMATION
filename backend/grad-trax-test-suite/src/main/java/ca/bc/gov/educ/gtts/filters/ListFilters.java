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

    /**
     * Convenience method for returning a predicate filter to eliminate sccp and noprog programs from the list
     * @return filtered list without sccp and noprog programs
     */
    public static Predicate<GraduationStudentRecord> filterSccpAndNonGradPrograms(){
        return g -> !g.getProgram().toLowerCase().contains("sccp") && !g.getProgram().toLowerCase().contains("noprog");
    }

}
