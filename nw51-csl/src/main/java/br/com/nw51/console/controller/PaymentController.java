package br.com.nw51.console.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;

import br.com.nw51.common.util.Utils;
import br.com.nw51.common.vo.PaymentFilterVO;
import br.com.nw51.common.vo.PaymentVO;
import br.com.nw51.console.services.BusinessException;
import br.com.nw51.console.services.PaymentService;

/**
 * Payment Controller.
 * 
 * @author Josivan Silva
 *
 */
@ManagedBean
@RequestScoped
public class PaymentController extends AbstractController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger (PaymentController.class.getName());
	private PaymentService paymentService = new PaymentService();
	
	private String fullNameFilter;
	private String cpfFilter;
	private String startPaymentDateFilter;
	private String endPaymentDateFilter;	
	
	private List<PaymentVO> paymentVOList;
	private Long paymentIdForm;
	private String fullNameForm;
	private String cpfForm;
	private String descriptionForm;
	private String amountForm;
	private Date paymentDateForm;
	private String paymentForMonthYearForm;
	
	public String getFullNameFilter() {
		return fullNameFilter;
	}

	public void setFullNameFilter(String fullNameFilter) {
		this.fullNameFilter = fullNameFilter;
	}

	public String getCpfFilter() {
		return cpfFilter;
	}

	public void setCpfFilter(String cpfFilter) {
		this.cpfFilter = cpfFilter;
	}

	public String getStartPaymentDateFilter() {
		return startPaymentDateFilter;
	}

	public void setStartPaymentDateFilter(String startPaymentDateFilter) {
		this.startPaymentDateFilter = startPaymentDateFilter;
	}

	public String getEndPaymentDateFilter() {
		return endPaymentDateFilter;
	}

	public void setEndPaymentDateFilter(String endPaymentDateFilter) {
		this.endPaymentDateFilter = endPaymentDateFilter;
	}

	public List<PaymentVO> getPaymentVOList() {
		return paymentVOList;
	}

	public void setPaymentVOList(List<PaymentVO> paymentVOList) {
		this.paymentVOList = paymentVOList;
	}

	public Long getPaymentIdForm() {
		return paymentIdForm;
	}

	public void setPaymentIdForm(Long paymentIdForm) {
		this.paymentIdForm = paymentIdForm;
	}

	public String getFullNameForm() {
		return fullNameForm;
	}

	public void setFullNameForm(String fullNameForm) {
		this.fullNameForm = fullNameForm;
	}
	
	public String getCpfForm() {
		return cpfForm;
	}

	public void setCpfForm (String cpfForm) {
		this.cpfForm = cpfForm;
	}

	public String getDescriptionForm() {
		return descriptionForm;
	}

	public void setDescriptionForm(String descriptionForm) {
		this.descriptionForm = descriptionForm;
	}

	public String getAmountForm() {
		return amountForm;
	}

	public void setAmountForm (String amountForm) {
		this.amountForm = amountForm;
	}

	public Date getPaymentDateForm() {
		return paymentDateForm;
	}

	public void setPaymentDateForm(Date paymentDateForm) {
		this.paymentDateForm = paymentDateForm;
	}

	public String getPaymentForMonthYearForm() {
		return paymentForMonthYearForm;
	}

	public void setPaymentForMonthYearForm(String paymentForMonthYearForm) {
		this.paymentForMonthYearForm = paymentForMonthYearForm;
	}

	@PostConstruct
    public void init() {
		
    }
	
	/**
	 * Finds the payments by filter.
	 */
	public void findByFilter () {
		PaymentFilterVO paymentFilterVO = new PaymentFilterVO();
		paymentVOList = new ArrayList<PaymentVO>();
		if (isValidFilter()) {
			paymentFilterVO.setFullName (this.fullNameFilter);
			paymentFilterVO.setStartDate (this.startPaymentDateFilter);
			paymentFilterVO.setEndDate (this.endPaymentDateFilter);
			paymentFilterVO.setCpf (this.getCpfFilter());
			try {
				paymentVOList = paymentService.findByFilter (paymentFilterVO);
				logger.debug ("paymentVOList.size() [" + paymentVOList.size() + "]");
			} catch (BusinessException e) {
				String error = "An error occurred while find the payments by filter. " + e.getMessage();
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
		if (Utils.isNonEmpty (startPaymentDateFilter) 
				&& Utils.isNonEmpty (endPaymentDateFilter)) {			
			if (!Utils.isValidDateInterval (startPaymentDateFilter, endPaymentDateFilter)) {
				isValid = false;
				super.addWarnMessage ("messagesPayments", " Início Data de Pagamento é maior do que Fim Data de Pagamento.");
				logger.warn ("The start date is greater than the end date.");
			}			
		} else if (Utils.isNonEmpty (cpfFilter)) {
			String cpf = Utils.removeCpfMask (cpfFilter);
			if (!Utils.isCPF (cpf)) {
				isValid = false;
				super.addWarnMessage ("messagesPayments", " CPF inválido.");
				logger.warn ("Invalid CPF.");
			}
		}
		return isValid;
	}

	/**
	 * Gets the count of payments.
	 */
	public int getPaymentsCount() { 
		int count = 0;
		try {
			count = paymentService.rowCount();
		} catch (BusinessException e) {
			String error = "An error occurred while getting the row count of payments. " + e.getMessage();
			logger.error (error);
		}
		return count;
	}
	
	/**
	 * Redirects to payments page.
	 */
	public String goToPayments() {
		logger.debug ("Starting goToPayments.");
		String toPage = "payments?faces-redirect=true";
	    this.findByFilter();
	    return toPage;
	}
		
	/**
	 * Performs the payment save operation.
	 */
	public void save (PaymentVO paymentVO) {
		try {
			int affectedRows = paymentService.insert (paymentVO);
			if (affectedRows > 0) {
				logger.info ("The payment has been successfully inserted.");
			}
		} catch (BusinessException e) {
			String error = "An error occurred while saving the payment. " + e.getMessage();
			logger.error (error);
		}		
	}
			
	
	/**
	 * Loads a payment given its id.
	 */
	public void loadById () {
		logger.debug ("Starting loadById.");
		PaymentVO foundPaymentVO = null;
		if (this.paymentIdForm != null && this.paymentIdForm > 0) {
			PaymentVO paymentVO = new PaymentVO ();
			paymentVO.setPaymentId (this.paymentIdForm);
	        try {
	        	foundPaymentVO = this.paymentService.findById (paymentVO);
	        	
	        	this.paymentIdForm = foundPaymentVO.getPaymentId();
				this.fullNameForm = foundPaymentVO.getStudentVO().getFullName();
				this.cpfForm = foundPaymentVO.getStudentVO().getCpf();
				this.descriptionForm = foundPaymentVO.getDescription();
				this.amountForm = foundPaymentVO.getAmount();
				this.paymentDateForm = foundPaymentVO.getPaymentDate();
				this.paymentForMonthYearForm = foundPaymentVO.getPaymentForMonthYear();
				
				logger.debug ("this.paymentIdForm " + this.paymentIdForm);
			} catch (BusinessException e) {
				String error = "An error occurred while finding the payment by id. " + e.getMessage();
				logger.error (error);
			}
		}
    }
	
	/**
	 * Performs the search according with the specified filter.
	 * 
	 * @param event the action listener event.
	 */
	public void findByFilterListener (ActionEvent event) {
		logger.debug ("Start executing the method findByFilterListener().");
		findByFilter ();
		logger.debug ("Finish executing the method findByFilterListener().");
	}
	
	/**
	 * Validates the user insert/update form.
	 * 
	 */
	/*private boolean isValidForm () {						
		if (!Utils.isNonEmpty (this.emailForm)
				|| !Utils.isNonEmpty (this.pwdForm)
				|| this.genderForm == null
				|| !Utils.isNonEmpty (this.fullNameForm)
				|| !Utils.isNonEmpty (this.cpfForm)) {			
			super.addWarnMessage ("Preencha os campos corretamente.");
			return false;
		}
		return true;
    }*/
	
	/**
	 * Resets the insert/update form.
	 */
	/*private void resetForm () {
		this.studentIdForm = null;		
	}*/	
	
}
