package br.com.nw51.common.vo;

import java.util.List;

/**
 * Value Object class for Class.
 * 
 * @author Josivan Silva
 *
 */
public class ClassVO {
	
	private Integer classId;
	private CourseVO courseVO;
	private ClassPlanVO classPlanVO;
	private String name;
	private String description;
	private String notes;
	private List<SlideVO> slideVOList;
	private List<AttachmentVO> attachmentVOList;
	private ContentItemVO contentItemVO;
	
	public Integer getClassId() {
		return classId;
	}
	public void setClassId(Integer classId) {
		this.classId = classId;
	}
	public CourseVO getCourseVO() {
		return courseVO;
	}
	public void setCourseVO(CourseVO courseVO) {
		this.courseVO = courseVO;
	}
	public ClassPlanVO getClassPlanVO() {
		return classPlanVO;
	}
	public void setClassPlanVO(ClassPlanVO classPlanVO) {
		this.classPlanVO = classPlanVO;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public List<SlideVO> getSlideVOList() {
		return slideVOList;
	}
	public void setSlideVOList(List<SlideVO> slideVOList) {
		this.slideVOList = slideVOList;
	}
	public List<AttachmentVO> getAttachmentVOList() {
		return attachmentVOList;
	}
	public void setAttachmentVOList(List<AttachmentVO> attachmentVOList) {
		this.attachmentVOList = attachmentVOList;
	}
	public ContentItemVO getContentItemVO() {
		return contentItemVO;
	}
	public void setContentItemVO(ContentItemVO contentItemVO) {
		this.contentItemVO = contentItemVO;
	}	
}
