package ca.bc.gov.gradtraxcomparisontest.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TraxGradComparatorNonGradDto {

    String pen;
    String nonGradCode;
    String nonGradDescription;

}
