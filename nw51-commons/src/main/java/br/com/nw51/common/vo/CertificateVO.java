package br.com.nw51.common.vo;

import java.util.Date;

/**
 * Value Object class for Certificate.
 * 
 * @author Josivan Silva
 *
 */
public class CertificateVO {
	
	private Long certificateId;
	private StudentCourseVO studentCourseVO;
	private Date creationDate;
	
	public Long getCertificateId() {
		return certificateId;
	}
	public void setCertificateId(Long certificateId) {
		this.certificateId = certificateId;
	}
	public StudentCourseVO getStudentCourseVO() {
		return studentCourseVO;
	}
	public void setStudentCourseVO(StudentCourseVO studentCourseVO) {
		this.studentCourseVO = studentCourseVO;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
}
