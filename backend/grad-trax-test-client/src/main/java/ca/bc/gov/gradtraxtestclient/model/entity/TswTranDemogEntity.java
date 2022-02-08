package ca.bc.gov.gradtraxtestclient.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TSW_TRAN_DEMOG")
public class TswTranDemogEntity {

    @Id
    @Column(name = "STUD_NO", unique = true, updatable = false)
    String studNo;
    @Column(name = "TRAN_TYPE", updatable = false)
    String tranType;
    @Column(name = "LOGO_TYPE", updatable = false)
    String logoType;
    @Column(name = "ARCHIVE_FLAG", updatable = false)
    String archiveFlag;
    @Column(name = "MINCODE", updatable = false)
    String minCode;
    @Column(name = "SCHOOL_NM", updatable = false)
    String schoolNm;
    @Column(name = "SCH_ADDRESS1", updatable = false)
    String schAddressOne;
    @Column(name = "SCH_ADDRESS2", updatable = false)
    String schAddressTwo;
    @Column(name = "SCH_ADDRESS3", updatable = false)
    String schAddressThree;
    @Column(name = "SCH_ADDRESS4", updatable = false)
    String schAddressFour;
    @Column(name = "SCH_ADDRESS5", updatable = false)
    String schAddressFive;
    @Column(name = "LOCAL_ID", updatable = false)
    String localId;
    @Column(name = "EARLY_ADMIT", updatable = false)
    String earlyAdmit;
    @Column(name = "BIRTHDATE", updatable = false)
    Integer birthDate;
    @Column(name = "GRAD_REQT_YEAR", updatable = false)
    String gradReqtYear;
    @Column(name = "STUD_GRADE", updatable = false)
    String grade;
    @Column(name = "STUD_CITIZ", updatable = false)
    String citizenship;
    @Column(name = "STUD_GENDER", updatable = false)
    String gender;
    @Column(name = "PROGRAM_CODE", updatable = false)
    String programCode;
    @Column(name = "LAST_NAME", updatable = false)
    String lastName;
    @Column(name = "MIDDLE_NAME", updatable = false)
    String middleName;
    @Column(name = "FIRST_NAME", updatable = false)
    String firstName;
    @Column(name = "GRAD_DATE", updatable = false)
    String gradDate;
    @Column(name = "GRAD_FLAG", updatable = false)
    String gradFlag;
    @Column(name = "GRAD_MSG", updatable = false)
    String gradMessage;
    @Column(name = "TOTAL_CREDITS", updatable = false)
    String totalCredits;
    @Column(name = "UPDATE_DT", updatable = false)
    Integer updateDate;
    @Column(name = "GRAD_MSG_TXT", updatable = false)
    String gradMessageText;
    @Column(name = "CURRENT_FORMER_FLAG", updatable = false)
    String currentFormerFlag;

}
