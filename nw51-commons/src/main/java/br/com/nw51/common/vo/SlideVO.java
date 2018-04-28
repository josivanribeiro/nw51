package br.com.nw51.common.vo;

/**
 * Value Object class for Slide.
 * 
 * @author Josivan Silva
 *
 */
public class SlideVO {
		
	private Integer slideId;
	private ClassVO classVO;
	private String name;
	private String content;
	private int status;
	private Integer sequenceNumber;
	
	public Integer getSlideId() {
		return slideId;
	}
	
	public void setSlideId(Integer slideId) {
		this.slideId = slideId;
	}
	
	public ClassVO getClassVO() {
		return classVO;
	}
	
	public void setClassVO(ClassVO classVO) {
		this.classVO = classVO;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}

	public Integer getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(Integer sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}	
	
}
