package ca.bc.gov.educ.gtts.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Represents the composite ID for consuming entity
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TswTranNonGradCompositeId implements Serializable {

    private String studNo;
    private String nonGradCode;

}
