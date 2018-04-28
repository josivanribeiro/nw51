package br.com.nw51.console.services;

import java.util.List;

import org.apache.log4j.Logger;

import br.com.nw51.common.vo.PaymentFilterVO;
import br.com.nw51.common.vo.PaymentVO;
import br.com.nw51.console.dao.DataAccessException;
import br.com.nw51.console.dao.PaymentDAO;

/**
 * Business Service class for Payment.
 * 
 * @author Josivan Silva
 *
 */
public class PaymentService {

	static Logger logger = Logger.getLogger (PaymentService.class.getName());
	private PaymentDAO paymentDAO = new PaymentDAO();
		
	public PaymentService() {
		
	}
	
	/**
	 * Inserts a new payment.
	 * 
	 * @param paymentVO the payment.
	 * @return the number of affected rows.
	 * @throws BusinessException
	 */
	public int insert (PaymentVO paymentVO) throws BusinessException {
		int affectedRows = 0;
		try {
			affectedRows = paymentDAO.insert (paymentVO);
			logger.debug ("affectedRows [" + affectedRows + "]");
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while inserting the payment. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return affectedRows;
	}
	
	/**
	 * Finds a payment by its id.
	 * 
	 * @param paymentVO the payment.
	 * @return the found payment.
	 * @throws BusinessException
	 */
	public PaymentVO findById (PaymentVO paymentVO) throws BusinessException {
		PaymentVO foundPaymentVO = null;
		try {
			foundPaymentVO = paymentDAO.findById (paymentVO);
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding the payment by id. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return foundPaymentVO;
	}
	
	/**
	 * Finds the payments by filter.
	 * 
	 * @return a list of payments.
	 * @throws BusinessException
	 */
	public List<PaymentVO> findByFilter (PaymentFilterVO paymentFilterVO) throws BusinessException {
		List<PaymentVO> paymentVOList = null;
		try {
			paymentVOList = paymentDAO.findByFilter (paymentFilterVO);
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding the payments by filter. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return paymentVOList;
	}
	
	/**
	 * Gets the row count of payments.
	 * 
	 * @return the row count.
	 * @throws BusinessException
	 */
	public int rowCount () throws BusinessException {
		int count = 0;
		try {
			count = paymentDAO.rowCount();
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while getting the row count of payments. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return count;
	}
	
}
