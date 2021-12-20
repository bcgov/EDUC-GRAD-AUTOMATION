package ca.bc.gov.educ.gtts.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(TswTranNonGradCompositeId.class)
@Table(name = "TSW_TRAN_NONGRAD")
public class TswTranNonGradEntity {

    @Id
    @Column(name = "STUD_NO", updatable = false)
    String studNo;
    @Id
    @Column(name = "NON_GRAD_CODE", updatable = false)
    String nonGradCode;
    @Column(name = "NON_GRAD_DESC", updatable = false)
    String nonGradDescription;
    @Column(name = "UPDATE_DT", updatable = false)
    Integer updateDate;

}
