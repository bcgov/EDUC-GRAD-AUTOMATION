package ca.bc.gov.educ.gtts.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TswTranNonGradId implements Serializable {

    private String studNo;
    private String nonGradCode;

}
