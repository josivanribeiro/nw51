package br.com.nw51.common.vo;

import java.util.Date;

import br.com.nw51.common.util.CourseTypeEnum;

/**
 * Value Object class for Course.
 * 
 * @author Josivan Silva
 *
 */
public class CourseVO {
	
	private Integer courseId;
	private TeacherVO teacherVO;
	private TeachingPlanVO teachingPlanVO; 
	private int courseType;
	private String name;
	private Date creationDate;
	private Date lastUpdateDate;
	private int status;
	
	public Integer getCourseId() {
		return courseId;
	}
	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}
	public TeacherVO getTeacherVO() {
		return teacherVO;
	}
	public void setTeacherVO(TeacherVO teacherVO) {
		this.teacherVO = teacherVO;
	}
	public TeachingPlanVO getTeachingPlanVO() {
		return teachingPlanVO;
	}
	public void setTeachingPlanVO(TeachingPlanVO teachingPlanVO) {
		this.teachingPlanVO = teachingPlanVO;
	}
	public int getCourseType() {
		return courseType;
	}
	public void setCourseType(int courseType) {
		this.courseType = courseType;
	}
	public String getCourseTypeName() {
		return CourseTypeEnum.getNameById (courseType);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}	
}
