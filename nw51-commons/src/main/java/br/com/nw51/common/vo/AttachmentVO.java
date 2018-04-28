package br.com.nw51.common.vo;

import java.io.InputStream;
import java.util.Date;

/**
 * Value Object class for Attachment.
 * 
 * @author Josivan Silva
 *
 */
public class AttachmentVO {
	
	private Integer attachmentId;
	private ClassVO classVO;
	private String name;
	private String type;
	private String size;
	private InputStream file;
	private Date creationDate;
	
	public Integer getAttachmentId() {
		return attachmentId;
	}
	public void setAttachmentId(Integer attachmentId) {
		this.attachmentId = attachmentId;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public InputStream getFile() {
		return file;
	}
	public void setFile (InputStream file) {
		this.file = file;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
}
