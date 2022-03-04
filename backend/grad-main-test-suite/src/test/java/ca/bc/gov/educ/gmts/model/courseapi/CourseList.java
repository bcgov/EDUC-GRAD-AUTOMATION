package ca.bc.gov.educ.gmts.model.courseapi;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class CourseList {

	List<String> courseCodes;		
}
