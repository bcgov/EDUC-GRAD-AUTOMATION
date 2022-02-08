package ca.bc.gov.gradtraxcomparisontest.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TraxStudentDto {

    private String studNo;
    private String archiveFlag;
    private String studSurname;
    private String studGiven;
    private String studMiddle;
    private String address1;
    private String address2;
    private String city;
    private String provCode;
    private String cntryCode;
    private String postal;
    private String studBirth;
    private String studSex;
    private String studCitiz;
    private String studGrade;
    private String mincode;
    private String studLocalId;
    private String studTrueNo;
    private String studSin;
    private String prgmCode;
    private String prgmCode2;
    private String prgmCode3;
    private String studPsiPermit;
    private String studRsrchPermit;
    private String studStatus;
    private String studConsedFlag;
    private String yrEnter11;
    private Long gradDate;
    private String dogwoodFlag;
    private String honourFlag;
    private String mincodeGrad;
    private String frenchDogwood;
    private String prgmCode4;
    private String prgmCode5;
    private Long sccDate;
    private String gradReqtYear;
    private Long slpDate;
    private String mergedFromPen;
    private String gradReqtYearAtGrad;
    private String studGradeAtGrad;
    private Long xcriptActvDate;
    private String allowedAdult;
    private String ssaNominationDate;
    private String adjTestYear;
    private String graduatedAdult;
    private String supplierNo;
    private String siteNo;
    private String emailAddress;
    private String englishCert;
    private String frenchCert;
    private Long englishCertDate;
    private Long frenchCertDate;

}
