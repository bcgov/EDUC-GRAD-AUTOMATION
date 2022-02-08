package ca.bc.gov.gradtraxcomparisontest.model;

import lombok.Data;

@Data
public class CompareWithTraxResponse {

    private int responseCode;
    private String studentId;
    private String message;

}
