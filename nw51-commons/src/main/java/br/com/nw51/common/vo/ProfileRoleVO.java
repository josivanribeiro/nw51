package br.com.nw51.common.vo;

/**
 * Value Object class for ProfileRole.
 * 
 * @author Josivan Silva
 *
 */
public class ProfileRoleVO {

	private ProfileVO profileVO;
	private RoleVO roleVO;
	
	public ProfileVO getProfileVO() {
		return profileVO;
	}
	public void setProfileVO(ProfileVO profileVO) {
		this.profileVO = profileVO;
	}
	public RoleVO getRoleVO() {
		return roleVO;
	}
	public void setRoleVO(RoleVO roleVO) {
		this.roleVO = roleVO;
	}
	
}
