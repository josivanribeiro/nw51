package br.com.nw51.common.vo;

import java.util.Date;

/**
 * Value Object class for Payment.
 * 
 * @author Josivan Silva
 *
 */
public class PaymentVO {

	private Long paymentId;
	private StudentVO studentVO;
	private String description;
	private String amount;
	private Date paymentDate;
	private String paymentForMonthYear;
	
	public Long getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}
	public StudentVO getStudentVO() {
		return studentVO;
	}
	public void setStudentVO(StudentVO studentVO) {
		this.studentVO = studentVO;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount (String amount) {
		this.amount = amount;
	}
	public Date getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	public String getPaymentForMonthYear() {
		return paymentForMonthYear;
	}
	public void setPaymentForMonthYear (String paymentForMonthYear) {
		this.paymentForMonthYear = paymentForMonthYear;
	}
	
}
