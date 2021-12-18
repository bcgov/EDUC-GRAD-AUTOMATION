package ca.bc.gov.educ.gtts.model.dto;

public class GradSearchStudent {

	private String studentID;
	private String pen;
	private String legalFirstName;
	private String legalMiddleNames;
	private String legalLastName;
	private String dob;
	private String sexCode;
	private String genderCode;
	private String usualFirstName;
	private String usualMiddleNames;
	private String usualLastName;
	private String email;
	private String emailVerified;
	private String deceasedDate;
	private String postalCode;
	private String mincode;
	private String localID;
	private String gradeCode;
	private String gradeYear;
	private String demogCode;
	private String statusCode;
	private String memo;
	private String trueStudentID;
	private String program;
	private String schoolOfRecord;
	private String schoolOfRecordName;
	private String schoolOfRecordindependentAffiliation;
	private String studentGrade;
	private String studentStatus;

	public GradSearchStudent() {
	}

	public GradSearchStudent(String studentID, String pen, String legalFirstName, String legalMiddleNames, String legalLastName, String dob, String sexCode, String genderCode, String usualFirstName, String usualMiddleNames, String usualLastName, String email, String emailVerified, String deceasedDate, String postalCode, String mincode, String localID, String gradeCode, String gradeYear, String demogCode, String statusCode, String memo, String trueStudentID, String program, String schoolOfRecord, String schoolOfRecordName, String schoolOfRecordindependentAffiliation, String studentGrade, String studentStatus) {
		this.studentID = studentID;
		this.pen = pen;
		this.legalFirstName = legalFirstName;
		this.legalMiddleNames = legalMiddleNames;
		this.legalLastName = legalLastName;
		this.dob = dob;
		this.sexCode = sexCode;
		this.genderCode = genderCode;
		this.usualFirstName = usualFirstName;
		this.usualMiddleNames = usualMiddleNames;
		this.usualLastName = usualLastName;
		this.email = email;
		this.emailVerified = emailVerified;
		this.deceasedDate = deceasedDate;
		this.postalCode = postalCode;
		this.mincode = mincode;
		this.localID = localID;
		this.gradeCode = gradeCode;
		this.gradeYear = gradeYear;
		this.demogCode = demogCode;
		this.statusCode = statusCode;
		this.memo = memo;
		this.trueStudentID = trueStudentID;
		this.program = program;
		this.schoolOfRecord = schoolOfRecord;
		this.schoolOfRecordName = schoolOfRecordName;
		this.schoolOfRecordindependentAffiliation = schoolOfRecordindependentAffiliation;
		this.studentGrade = studentGrade;
		this.studentStatus = studentStatus;
	}


	public String getStudentID() {
		return studentID;
	}

	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}

	public String getPen() {
		return pen;
	}

	public void setPen(String pen) {
		this.pen = pen;
	}

	public String getLegalFirstName() {
		return legalFirstName;
	}

	public void setLegalFirstName(String legalFirstName) {
		this.legalFirstName = legalFirstName;
	}

	public String getLegalMiddleNames() {
		return legalMiddleNames;
	}

	public void setLegalMiddleNames(String legalMiddleNames) {
		this.legalMiddleNames = legalMiddleNames;
	}

	public String getLegalLastName() {
		return legalLastName;
	}

	public void setLegalLastName(String legalLastName) {
		this.legalLastName = legalLastName;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getSexCode() {
		return sexCode;
	}

	public void setSexCode(String sexCode) {
		this.sexCode = sexCode;
	}

	public String getGenderCode() {
		return genderCode;
	}

	public void setGenderCode(String genderCode) {
		this.genderCode = genderCode;
	}

	public String getUsualFirstName() {
		return usualFirstName;
	}

	public void setUsualFirstName(String usualFirstName) {
		this.usualFirstName = usualFirstName;
	}

	public String getUsualMiddleNames() {
		return usualMiddleNames;
	}

	public void setUsualMiddleNames(String usualMiddleNames) {
		this.usualMiddleNames = usualMiddleNames;
	}

	public String getUsualLastName() {
		return usualLastName;
	}

	public void setUsualLastName(String usualLastName) {
		this.usualLastName = usualLastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(String emailVerified) {
		this.emailVerified = emailVerified;
	}

	public String getDeceasedDate() {
		return deceasedDate;
	}

	public void setDeceasedDate(String deceasedDate) {
		this.deceasedDate = deceasedDate;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getMincode() {
		return mincode;
	}

	public void setMincode(String mincode) {
		this.mincode = mincode;
	}

	public String getLocalID() {
		return localID;
	}

	public void setLocalID(String localID) {
		this.localID = localID;
	}

	public String getGradeCode() {
		return gradeCode;
	}

	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
	}

	public String getGradeYear() {
		return gradeYear;
	}

	public void setGradeYear(String gradeYear) {
		this.gradeYear = gradeYear;
	}

	public String getDemogCode() {
		return demogCode;
	}

	public void setDemogCode(String demogCode) {
		this.demogCode = demogCode;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getTrueStudentID() {
		return trueStudentID;
	}

	public void setTrueStudentID(String trueStudentID) {
		this.trueStudentID = trueStudentID;
	}

	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
	}

	public String getSchoolOfRecord() {
		return schoolOfRecord;
	}

	public void setSchoolOfRecord(String schoolOfRecord) {
		this.schoolOfRecord = schoolOfRecord;
	}

	public String getSchoolOfRecordName() {
		return schoolOfRecordName;
	}

	public void setSchoolOfRecordName(String schoolOfRecordName) {
		this.schoolOfRecordName = schoolOfRecordName;
	}

	public String getSchoolOfRecordindependentAffiliation() {
		return schoolOfRecordindependentAffiliation;
	}

	public void setSchoolOfRecordindependentAffiliation(String schoolOfRecordindependentAffiliation) {
		this.schoolOfRecordindependentAffiliation = schoolOfRecordindependentAffiliation;
	}

	public String getStudentGrade() {
		return studentGrade;
	}

	public void setStudentGrade(String studentGrade) {
		this.studentGrade = studentGrade;
	}

	public String getStudentStatus() {
		return studentStatus;
	}

	public void setStudentStatus(String studentStatus) {
		this.studentStatus = studentStatus;
	}


	  
	  
}
