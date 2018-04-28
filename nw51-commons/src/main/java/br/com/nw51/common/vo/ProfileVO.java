package br.com.nw51.common.vo;

import java.util.List;

/**
 * Value Object class for Profile.
 * 
 * @author Josivan Silva
 *
 */
public class ProfileVO {

	private Integer profileId;
	private String name;
	private List<ProfileRoleVO> profileRoleVOList;
	private boolean selected;
		
	public Integer getProfileId() {
		return profileId;
	}
	public void setProfileId(Integer profileId) {
		this.profileId = profileId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<ProfileRoleVO> getProfileRoleVOList() {
		return profileRoleVOList;
	}
	public void setProfileRoleVOList(List<ProfileRoleVO> profileRoleVOList) {
		this.profileRoleVOList = profileRoleVOList;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
