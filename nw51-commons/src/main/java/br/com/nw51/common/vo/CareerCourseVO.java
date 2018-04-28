package br.com.nw51.common.vo;

/**
 * Value Object class for CareerCourse.
 * 
 * @author Josivan Silva
 *
 */
public class CareerCourseVO {
	
	private CareerVO careerVO;
	private CourseVO courseVO;
	private int sequenceNumber;
	
	public CareerVO getCareerVO() {
		return careerVO;
	}
	public void setCareerVO(CareerVO careerVO) {
		this.careerVO = careerVO;
	}
	public CourseVO getCourseVO() {
		return courseVO;
	}
	public void setCourseVO(CourseVO courseVO) {
		this.courseVO = courseVO;
	}
	public int getSequenceNumber() {
		return sequenceNumber;
	}
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	
}
