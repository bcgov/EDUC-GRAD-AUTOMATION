package ca.bc.gov.educ.gmts.model.programapi;

import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Component
public class GraduationProgramCode extends BaseModel {

	private String programCode;
	private String programName;
	private String description;
	private int displayOrder; 
	private Date effectiveDate;
	private Date expiryDate;
			
}
