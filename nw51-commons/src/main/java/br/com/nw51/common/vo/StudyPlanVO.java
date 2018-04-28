package br.com.nw51.common.vo;

import java.util.Date;

/**
 * Value Object class for Study Plan.
 * 
 * @author Josivan Silva
 *
 */
public class StudyPlanVO {

	private Long studyPlanId;
	private StudentVO studentVO;
	private CourseVO courseVO;
	private Date startClassDatetime;
	private Date endClassDatetime;
	private boolean isNew;
	private boolean isChanged;

	public Long getStudyPlanId() {
		return studyPlanId;
	}

	public void setStudyPlanId(Long studyPlanId) {
		this.studyPlanId = studyPlanId;
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

	public Date getStartClassDatetime() {
		return startClassDatetime;
	}

	public void setStartClassDatetime(Date startClassDatetime) {
		this.startClassDatetime = startClassDatetime;
	}

	public Date getEndClassDatetime() {
		return endClassDatetime;
	}

	public void setEndClassDatetime(Date endClassDatetime) {
		this.endClassDatetime = endClassDatetime;
	}

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	public boolean isChanged() {
		return isChanged;
	}

	public void setChanged(boolean isChanged) {
		this.isChanged = isChanged;
	}	

}
