package br.com.nw51.common.vo;

/**
 * Value Object class for Certificate Filter.
 * 
 * @author Josivan Silva
 *
 */
public class CertificateFilterVO {
	
	private Long courseId;
	private String cpf;
	private String startDate;
	private String endDate;
	
	public Long getCourseId() {
		return courseId;
	}
	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString () {
		StringBuilder sb = new StringBuilder();
		sb.append ("certificate filter courseId [" + this.courseId + "]\n");
		sb.append ("course filter student cpf [" + this.cpf + "]\n");
		sb.append ("filter startDate [" + this.startDate + "]\n");
		sb.append ("filter endDate [" + this.endDate + "]\n");
		return sb.toString();
	}
	
}
