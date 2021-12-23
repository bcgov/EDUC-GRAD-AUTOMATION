package ca.bc.gov.educ.gtts.model.dto.grad.algorithm;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ExceptionMessage {

	private String exceptionName;
	private String exceptionDetails;
}
