package br.com.nw51.console.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;

import br.com.nw51.common.util.Utils;
import br.com.nw51.common.vo.LogFilterVO;
import br.com.nw51.common.vo.LogVO;
import br.com.nw51.console.services.BusinessException;
import br.com.nw51.console.services.LogService;

/**
 * Log Controller.
 * 
 * @author Josivan Silva
 *
 */
@ManagedBean
@RequestScoped
public class LogController extends AbstractController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger (LogController.class.getName());
	private LogService logService = new LogService();
		
	private String operationFilter;
	private String cpfFilter;
	private String startLogDateFilter;
	private String endLogDateFilter;
	private int statusFilter = -1;
			
	private List<LogVO> logVOList;
		
	private Long logIdForm;
	private String studentNameForm;
	private String operationForm;
	private String inputForm;
	private String outputForm;
	private String errorForm;
	private String messageForm;
	private Date logTimeForm;
	private int statusForm;
		
	private Map<String,Object> statusMap;
	
	public LogService getLogService() {
		return logService;
	}

	public void setLogService(LogService logService) {
		this.logService = logService;
	}

	public String getOperationFilter() {
		return operationFilter;
	}

	public void setOperationFilter(String operationFilter) {
		this.operationFilter = operationFilter;
	}

	public String getCpfFilter() {
		return cpfFilter;
	}

	public void setCpfFilter(String cpfFilter) {
		this.cpfFilter = cpfFilter;
	}

	public String getStartLogDateFilter() {
		return startLogDateFilter;
	}

	public void setStartLogDateFilter(String startLogDateFilter) {
		this.startLogDateFilter = startLogDateFilter;
	}

	public String getEndLogDateFilter() {
		return endLogDateFilter;
	}

	public void setEndLogDateFilter(String endLogDateFilter) {
		this.endLogDateFilter = endLogDateFilter;
	}

	public int getStatusFilter() {
		return statusFilter;
	}

	public void setStatusFilter(int statusFilter) {
		this.statusFilter = statusFilter;
	}

	public List<LogVO> getLogVOList() {
		return logVOList;
	}

	public void setLogVOList(List<LogVO> logVOList) {
		this.logVOList = logVOList;
	}

	public Long getLogIdForm() {
		return logIdForm;
	}

	public void setLogIdForm(Long logIdForm) {
		this.logIdForm = logIdForm;
	}

	public String getStudentNameForm() {
		return studentNameForm;
	}

	public void setStudentNameForm(String studentNameForm) {
		this.studentNameForm = studentNameForm;
	}

	public String getOperationForm() {
		return operationForm;
	}

	public void setOperationForm(String operationForm) {
		this.operationForm = operationForm;
	}

	public String getInputForm() {
		return inputForm;
	}

	public void setInputForm(String inputForm) {
		this.inputForm = inputForm;
	}

	public String getOutputForm() {
		return outputForm;
	}

	public void setOutputForm(String outputForm) {
		this.outputForm = outputForm;
	}

	public String getErrorForm() {
		return errorForm;
	}

	public void setErrorForm(String errorForm) {
		this.errorForm = errorForm;
	}

	public String getMessageForm() {
		return messageForm;
	}

	public void setMessageForm(String messageForm) {
		this.messageForm = messageForm;
	}

	public Date getLogTimeForm() {
		return logTimeForm;
	}

	public void setLogTimeForm(Date logTimeForm) {
		this.logTimeForm = logTimeForm;
	}

	public int getStatusForm() {
		return statusForm;
	}

	public void setStatusForm(int statusForm) {
		this.statusForm = statusForm;
	}

	public Map<String, Object> getStatusMap() {
		return statusMap;
	}

	public void setStatusMap(Map<String, Object> statusMap) {
		this.statusMap = statusMap;
	}

	@PostConstruct
    public void init() {
		this.initStatusSelect();
    }	
	
	/**
	 * Finds the logs by filter.
	 */
	public void findByFilter () {
		LogFilterVO logFilterVO = new LogFilterVO();
		logVOList = new ArrayList<LogVO>();
		if (isValidFilter()) {
			logFilterVO.setOperation (operationFilter);
			logFilterVO.setCpf (cpfFilter);
			logFilterVO.setStartDate (this.startLogDateFilter);
			logFilterVO.setEndDate (this.endLogDateFilter);
			logFilterVO.setStatus (statusFilter);
			try {
				logVOList = logService.findByFilter (logFilterVO);
				logger.debug ("logVOList.size() [" + logVOList.size() + "]");
			} catch (BusinessException e) {
				String error = "An error occurred while find the logs by filter. " + e.getMessage();
				logger.error (error);
			}
		}
	}
	
	/**
	 * Validates the search filter.
	 * 
	 * @return the operation result.
	 */
	private boolean isValidFilter() {
		boolean isValid = true;
		if (Utils.isNonEmpty (startLogDateFilter) 
				&& Utils.isNonEmpty (endLogDateFilter)) {			
			if (!Utils.isValidDateInterval (startLogDateFilter, endLogDateFilter)) {
				isValid = false;
				super.addWarnMessage ("messagesLogs", " Início Data de Log é maior do que Fim Data de Log.");
				logger.warn ("The start date is greater than the end date.");
			}
		}  else if (Utils.isNonEmpty (cpfFilter)) {
			String cpf = Utils.removeCpfMask (cpfFilter);
			if (!Utils.isCPF (cpf)) {
				isValid = false;
				super.addWarnMessage ("messagesLogs", " CPF inválido.");
				logger.warn ("Invalid CPF.");
			}
		}
		return isValid;
	}

	/**
	 * Gets the count of logs.
	 */
	public int getLogsCount() { 
		int count = 0;
		try {
			count = logService.rowCount();
		} catch (BusinessException e) {
			String error = "An error occurred while getting the row count of logs. " + e.getMessage();
			logger.error (error);
		}
		return count;
	}
	
	/**
	 * Redirects to logs page.
	 */
	public String goToLogs() {
		logger.debug ("Starting goToLogs.");
		String toPage = "logs?faces-redirect=true";
	    this.findByFilter();
	    return toPage;
	}
	
	/**
	 * Loads a log given its id.
	 */
	public void loadById () {
		logger.debug ("Starting loadById.");
		LogVO foundLogVO = null;
		if (this.logIdForm != null && this.logIdForm > 0) {
			LogVO logVO = new LogVO ();
			logVO.setLogId (this.logIdForm);
	        try {
	        	foundLogVO = this.logService.findById (logVO);
	        	this.logIdForm = foundLogVO.getLogId();
	        	if (foundLogVO.getStudentVO() != null 
	        			&& Utils.isNonEmpty (foundLogVO.getStudentVO().getFullName())) {
	        		this.studentNameForm = foundLogVO.getStudentVO().getFullName();	
	        	}
	        	this.operationForm = foundLogVO.getOperation();
	        	this.inputForm = foundLogVO.getInput();
	        	
	        	if (Utils.isNonEmpty (foundLogVO.getOutput())) {
	        		this.outputForm = foundLogVO.getOutput();
	        	}
	        	
	        	if (Utils.isNonEmpty (foundLogVO.getError())) {
	        		this.errorForm = foundLogVO.getError();
	        	}
	        	
	        	this.messageForm = foundLogVO.getMessage();
	        	this.logTimeForm = foundLogVO.getLogTime();
	        	this.statusForm = foundLogVO.getStatus();
	        	        	
	        	logger.debug ("this.logIdForm " + this.logIdForm);				
			} catch (BusinessException e) {
				String error = "An error occurred while finding the log by id. " + e.getMessage();
				logger.error (error);
			}
		}
    }
	
	/**
	 * Performs the search according with the specified filter and pagination.
	 * 
	 * @param event the action listener event.
	 */
	public void findByFilterListener (ActionEvent event) {
		logger.debug ("Start executing the method findByFilterListener().");
		findByFilter ();
		logger.debug ("Finish executing the method findByFilterListener().");
	}
	
	/**
	 * Initializes the status select.
	 */
	private void initStatusSelect() {
		this.statusMap = new LinkedHashMap<String,Object>();
		statusMap.put ("", -1); //label, value
		statusMap.put ("Erro", 0);
		statusMap.put ("Sucesso", 1);				
	}
	
}
