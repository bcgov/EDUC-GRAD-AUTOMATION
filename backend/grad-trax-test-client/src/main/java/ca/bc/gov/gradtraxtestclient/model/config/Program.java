package ca.bc.gov.gradtraxtestclient.model.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Program {

    String programName;
    Map<String, String> ruleMap;

}
