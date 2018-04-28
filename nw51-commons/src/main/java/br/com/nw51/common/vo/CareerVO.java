package br.com.nw51.common.vo;

import java.util.List;

/**
 * Value Object class for Career.
 * 
 * @author Josivan Silva
 *
 */
public class CareerVO {
	
	private Integer careerId;
	private String name;
	private int careerType;
	private String description;
	private int status;
	private List<CareerCourseVO> careerCourseVOList;
	
	public Integer getCareerId() {
		return careerId;
	}
	public void setCareerId(Integer careerId) {
		this.careerId = careerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}	
	public int getCareerType() {
		return careerType;
	}
	public void setCareerType(int careerType) {
		this.careerType = careerType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public List<CareerCourseVO> getCareerCourseVOList() {
		return careerCourseVOList;
	}
	public void setCareerCourseVOList(List<CareerCourseVO> careerCourseVOList) {
		this.careerCourseVOList = careerCourseVOList;
	}
}
