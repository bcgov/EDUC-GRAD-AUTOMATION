package ca.bc.gov.educ.gmts.model.programapi;

import java.util.List;

import lombok.Data;

@Data
public class GradProgramAlgorithmData {

	private List<ProgramRequirement> programRules;
	private List<OptionalProgramRequirement> optionalProgramRules;
	private GraduationProgramCode gradProgram;
}
