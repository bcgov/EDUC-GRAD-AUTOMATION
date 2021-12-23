package ca.bc.gov.educ.gtts.model.dto.grad.algorithm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentAssessments {
    private List<StudentAssessment> studentAssessmentList;
}
