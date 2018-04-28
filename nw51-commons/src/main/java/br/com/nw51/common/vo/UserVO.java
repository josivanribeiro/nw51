package br.com.nw51.common.vo;

import java.io.Serializable;
import java.util.List;
import br.com.nw51.common.util.Utils;

/**
 * Value Object class for User.
 * 
 * @author Josivan Silva
 *
 */
public class UserVO implements Serializable {

	private Integer userId;
	private String email;
	private String pwd;
	private int state;//(0=NotLogged,1=Logged,2=Blocked)
	private int loginAttempts;
	private int unauthorizedAccessAttempts;
	private boolean status;//(0=Inactive,1=Active)
	private List<UserProfileVO> userProfileVOList;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getEmail() {
		return email;
	}
	public String getFormattedEmail() {
		return Utils.getFormattedEmail (email);
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
	public int getUnauthorizedAccessAttempts() {
		return unauthorizedAccessAttempts;
	}
	public void setUnauthorizedAccessAttempts (int unauthorizedAccessAttempts) {
		this.unauthorizedAccessAttempts = unauthorizedAccessAttempts;
	}	
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}	
	public List<UserProfileVO> getUserProfileVOList() {
		return userProfileVOList;
	}
	public void setUserProfileVOList(List<UserProfileVO> userProfileVOList) {
		this.userProfileVOList = userProfileVOList;
	}		
}
