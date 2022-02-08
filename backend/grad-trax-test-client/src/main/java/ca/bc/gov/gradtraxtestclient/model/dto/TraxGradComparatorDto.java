package ca.bc.gov.gradtraxtestclient.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Acts as a bridge object containing fields that are
 * identical between TRAX and GRAD models. This is used
 * for data comparisons between the data the systems are
 * producing.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TraxGradComparatorDto {

    // From TSW_TRAN_DEMOG
    private String pen;
    private String gradReqtYear;
    private String gradDate;
    private String gradFlag;
    private Integer updateDate;

    // From TSW_TRAN_NONGRAD
    List<TraxGradComparatorNonGradDto> traxGradComparatorNonGradDtoList = new ArrayList<>();

}
