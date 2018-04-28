package br.com.nw51.common.vo;

/**
 * Value Object class for Student Filter.
 * 
 * @author Josivan Silva
 *
 */
public class StudentFilterVO {
	
	private String email;
	private String fullName;
	private String cpf;
	private String startDate;
	private String endDate;
		
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
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
		sb.append ("filter email [" + this.email + "]\n");
		sb.append ("filter fullname [" + this.fullName + "]\n");
		sb.append ("filter cpf [" + this.cpf + "]\n");
		sb.append ("filter startDate [" + this.startDate + "]\n");
		sb.append ("filter endDate [" + this.endDate + "]\n");
		return sb.toString();
	}
}
