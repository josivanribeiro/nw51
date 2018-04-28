package br.com.nw51.common.vo;

import java.io.Serializable;
import java.util.Date;

import br.com.nw51.common.vo.AddressVO;

/**
 * Value Object class for Student.
 * 
 * @author Josivan Silva
 *
 */
public class StudentVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long studentId;
	private String email;
	private String pwd;
	private String gender;
	private String fullName;
	private String cpf;
	private String phone;
	private String mobile;
	private AddressVO addressVO;
	private Byte[] imageProfile;
	private Date joinDate;
	private Date expirationDate;
	private Date lastUpdateDate;
	private Date lastLoginDate;
	private String lastLoginIp;
	private int state;//(0=NotLogged,1=Logged,2=Blocked)
	private int loginAttempts;
	private boolean socialProgram;//(0=NotIncluded,1=Included)
	private boolean status;//(0=Inactive,1=Active)
	
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId (Long studentId) {
		this.studentId = studentId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getGender() {
		return gender;
	}
	public void setGender (String gender) {
		this.gender = gender;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public AddressVO getAddressVO() {
		return addressVO;
	}
	public void setAddressVO(AddressVO addressVO) {
		this.addressVO = addressVO;
	}
	public Byte[] getImageProfile() {
		return imageProfile;
	}
	public void setImageProfile(Byte[] imageProfile) {
		this.imageProfile = imageProfile;
	}
	public Date getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}
	public Date getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public Date getLastLoginDate() {
		return lastLoginDate;
	}
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	public String getLastLoginIp() {
		return lastLoginIp;
	}
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getLoginAttempts() {
		return loginAttempts;
	}
	public void setLoginAttempts(int loginAttempts) {
		this.loginAttempts = loginAttempts;
	}
	public boolean isSocialProgram() {
		return socialProgram;
	}
	public void setSocialProgram(boolean socialProgram) {
		this.socialProgram = socialProgram;
	}
	public boolean getStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}	
}
