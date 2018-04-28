package br.com.nw51.common.vo;

import java.util.List;

/**
 * Value Object class for TeachingPlan.
 * 
 * @author Josivan Silva
 *
 */

public class TeachingPlanVO {
	
	private Integer teachingPlanId;
	private int workloadHoursTheoryType;
	private int workloadHoursPracticeType;
	private String summary;
	private String generalGoal;
	private String specificGoals;
	private int teachingStrategyType;
	private String resources;
	private String evaluation;
	private String bibliography;
	private List<ContentItemVO> contentItemVOList;
	
	public Integer getTeachingPlanId() {
		return teachingPlanId;
	}
	public void setTeachingPlanId(Integer teachingPlanId) {
		this.teachingPlanId = teachingPlanId;
	}
	public int getWorkloadHoursTheoryType() {
		return workloadHoursTheoryType;
	}
	public void setWorkloadHoursTheoryType(int workloadHoursTheoryType) {
		this.workloadHoursTheoryType = workloadHoursTheoryType;
	}
	public int getWorkloadHoursPracticeType() {
		return workloadHoursPracticeType;
	}
	public void setWorkloadHoursPracticeType(int workloadHoursPracticeType) {
		this.workloadHoursPracticeType = workloadHoursPracticeType;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getGeneralGoal() {
		return generalGoal;
	}
	public void setGeneralGoal(String generalGoal) {
		this.generalGoal = generalGoal;
	}
	public String getSpecificGoals() {
		return specificGoals;
	}
	public void setSpecificGoals(String specificGoals) {
		this.specificGoals = specificGoals;
	}
	public int getTeachingStrategyType() {
		return teachingStrategyType;
	}
	public void setTeachingStrategyType(int teachingStrategyType) {
		this.teachingStrategyType = teachingStrategyType;
	}
	public String getResources() {
		return resources;
	}
	public void setResources(String resources) {
		this.resources = resources;
	}
	public String getEvaluation() {
		return evaluation;
	}
	public void setEvaluation(String evaluation) {
		this.evaluation = evaluation;
	}
	public String getBibliography() {
		return bibliography;
	}
	public void setBibliography(String bibliography) {
		this.bibliography = bibliography;
	}
	public List<ContentItemVO> getContentItemVOList() {
		return contentItemVOList;
	}
	public void setContentItemVOList(List<ContentItemVO> contentItemVOList) {
		this.contentItemVOList = contentItemVOList;
	}	
}
