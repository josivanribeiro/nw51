package br.com.nw51.common.vo;

import java.io.Serializable;

/**
 * Value Object class for ContentItem.
 * 
 * @author Josivan Silva
 *
 */
public class ContentItemVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer contentItemId;
	private ContentItemVO contentItemFather;
	private TeachingPlanVO teachingPlanVO;
	private ClassVO classVO;
	private String numeration;
	private String name;
	
	public Integer getContentItemId() {
		return contentItemId;
	}
	public void setContentItemId(Integer contentItemId) {
		this.contentItemId = contentItemId;
	}
	public ContentItemVO getContentItemFather() {
		return contentItemFather;
	}
	public void setContentItemFather(ContentItemVO contentItemFather) {
		this.contentItemFather = contentItemFather;
	}
	public TeachingPlanVO getTeachingPlanVO() {
		return teachingPlanVO;
	}
	public void setTeachingPlanVO(TeachingPlanVO teachingPlanVO) {
		this.teachingPlanVO = teachingPlanVO;
	}
	public ClassVO getClassVO() {
		return classVO;
	}
	public void setClassVO(ClassVO classVO) {
		this.classVO = classVO;
	}
	public String getNumeration() {
		return numeration;
	}
	public void setNumeration(String numeration) {
		this.numeration = numeration;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
