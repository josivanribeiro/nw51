package br.com.nw51.common.vo;

import java.util.Date;

/**
 * Value Object class for StudentCourse.
 * 
 * @author Josivan Silva
 *
 */
public class StudentCourseVO {
	
	private Long studentCourseId;
	private StudentVO studentVO;
	private CourseVO courseVO;
	private Date startDate;
	private int progress;
	private Date endDate;	
	private Date expirationDate;
	
	public Long getStudentCourseId() {
		return studentCourseId;
	}
	public void setStudentCourseId(Long studentCourseId) {
		this.studentCourseId = studentCourseId;
	}
	public StudentVO getStudentVO() {
		return studentVO;
	}
	public void setStudentVO(StudentVO studentVO) {
		this.studentVO = studentVO;
	}
	public CourseVO getCourseVO() {
		return courseVO;
	}
	public void setCourseVO(CourseVO courseVO) {
		this.courseVO = courseVO;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public int getProgress() {
		return progress;
	}
	public void setProgress(int progress) {
		this.progress = progress;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Date getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	
}
