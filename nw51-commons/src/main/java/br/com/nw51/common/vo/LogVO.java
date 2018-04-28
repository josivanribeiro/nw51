package br.com.nw51.common.vo;

import java.util.Date;
import br.com.nw51.common.util.Utils;

/**
 * Value Object class for Log.
 * 
 * @author Josivan Silva
 *
 */

public class LogVO {
	
	private Long logId;
	private StudentVO studentVO;
	private String operation;
	private String input;
	private String output;
	private String error;
	private String message;
	private Date logTime;
	private int status;
	
	public Long getLogId() {
		return logId;
	}
	public void setLogId(Long logId) {
		this.logId = logId;
	}
	public StudentVO getStudentVO() {
		return studentVO;
	}
	public void setStudentVO(StudentVO studentVO) {
		this.studentVO = studentVO;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getInput() {
		return input;
	}
	public void setInput(String input) {
		this.input = input;
	}
	public String getOutput() {
		return output;
	}
	public void setOutput(String output) {
		this.output = output;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getMessage() {
		return message;
	}
	public String getShortMessage() {
		if (Utils.isNonEmpty (message) && message.length() > 20) {
			message = message.substring (0, 20) + "...";
		}		
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getLogTime() {
		return logTime;
	}
	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
