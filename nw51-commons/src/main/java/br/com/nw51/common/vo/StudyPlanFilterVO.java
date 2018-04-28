package br.com.nw51.common.vo;

/**
 * Value Object class for StudentPlanFilter.
 * 
 * @author Josivan Silva
 *
 */
public class StudyPlanFilterVO {

	private Long studentId;
	private String date;
	
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}	
	
}
