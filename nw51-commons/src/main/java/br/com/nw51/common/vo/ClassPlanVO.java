package br.com.nw51.common.vo;

import java.util.List;

/**
 * Value Object class for Class Plan.
 * 
 * @author Josivan Silva
 *
 */
public class ClassPlanVO {
	
	private Integer classPlanId;
	private Float averageTime;
	private String skills;
	private List<ClassPlanToolVO> classPlanToolVOList;
	
	public Integer getClassPlanId() {
		return classPlanId;
	}
	public void setClassPlanId(Integer classPlanId) {
		this.classPlanId = classPlanId;
	}
	public List<ClassPlanToolVO> getClassPlanToolVOList() {
		return classPlanToolVOList;
	}
	public void setClassPlanToolVOList(List<ClassPlanToolVO> classPlanToolVOList) {
		this.classPlanToolVOList = classPlanToolVOList;
	}
	public Float getAverageTime() {
		return averageTime;
	}
	public void setAverageTime (Float averageTime) {
		this.averageTime = averageTime;
	}
	public String getSkills() {
		return skills;
	}
	public void setSkills(String skills) {
		this.skills = skills;
	}
}
