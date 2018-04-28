package br.com.nw51.common.vo;

/**
 * Value Object class for Course Filter.
 * 
 * @author Josivan Silva
 *
 */
public class CourseFilterVO {
	
	private String name;
	private Integer courseType;
	private Integer teacherId;
	private String startDate;
	private String endDate;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getCourseType() {
		return courseType;
	}
	public void setCourseType(Integer courseType) {
		this.courseType = courseType;
	}
	public Integer getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString () {
		StringBuilder sb = new StringBuilder();
		sb.append ("course filter name [" + this.name + "]\n");
		sb.append ("course filter courseType [" + this.courseType + "]\n");
		sb.append ("course filter teacherId [" + this.teacherId + "]\n");
		sb.append ("filter startDate [" + this.startDate + "]\n");
		sb.append ("filter endDate [" + this.endDate + "]\n");
		return sb.toString();
	}
}
