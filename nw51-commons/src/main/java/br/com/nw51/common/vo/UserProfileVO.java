package br.com.nw51.common.vo;

/**
 * Value Object class for UserProfile.
 * 
 * @author Josivan Silva
 *
 */
public class UserProfileVO {

	private UserVO userVO;
	private ProfileVO profileVO;
		
	public UserVO getUserVO() {
		return userVO;
	}
	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}
	public ProfileVO getProfileVO() {
		return profileVO;
	}
	public void setProfileVO(ProfileVO profileVO) {
		this.profileVO = profileVO;
	}	
	
}
