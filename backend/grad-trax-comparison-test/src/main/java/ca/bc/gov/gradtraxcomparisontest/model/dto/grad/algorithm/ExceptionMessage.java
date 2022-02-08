package ca.bc.gov.gradtraxcomparisontest.model.dto.grad.algorithm;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ExceptionMessage {

	private String exceptionName;
	private String exceptionDetails;
}
