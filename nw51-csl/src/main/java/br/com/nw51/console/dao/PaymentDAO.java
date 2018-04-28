package br.com.nw51.console.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.nw51.common.util.Utils;
import br.com.nw51.common.vo.PaymentFilterVO;
import br.com.nw51.common.vo.PaymentVO;
import br.com.nw51.common.vo.StudentVO;

/**
 * DAO class for Payment.
 * 
 * @author Josivan Silva
 *
 */
public class PaymentDAO extends AbstractDAO {

	static Logger logger = Logger.getLogger (PaymentDAO.class.getName());
	
	public PaymentDAO () {
		
	}
	
	/**
	 * Inserts a new payment.
	 * 
	 * @param paymentVO the payment.
	 * @return the number of affected rows.
	 * @throws DataAccessException
	 */
	public int insert (PaymentVO paymentVO) throws DataAccessException {
		int affectedRows = 0;
		StringBuilder sbSql = null;
		sbSql = new StringBuilder();
		sbSql.append ("INSERT INTO PAYMENT (STUDENT_ID, DESCRIPTION, AMOUNT, PAYMENT_DATE, PAYMENT_FOR_MONTH_YEAR) ");
		sbSql.append ("VALUES (" +  paymentVO.getStudentVO().getStudentId() + ", ");
		sbSql.append ("'" + paymentVO.getDescription() + "', ");
		sbSql.append (paymentVO.getAmount() + ", ");
		sbSql.append ("NOW(), ");
		sbSql.append (paymentVO.getPaymentForMonthYear() + ")");
		affectedRows = updateDb (sbSql.toString());
		return affectedRows;
	}
	
	/**
	 * Find a payment by its id.
	 * 
	 * @param paymentVO the payment.
	 * @return a payment.
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public PaymentVO findById (PaymentVO paymentVO) throws DataAccessException {
		PaymentVO foundPaymentVO = null;
		String sql               = null;
		List<Object> rowList     = null;
		
		sql = "SELECT P.PAYMENT_ID, S.STUDENT_ID, S.CPF, S.FULL_NAME, P.DESCRIPTION, P.AMOUNT, P.PAYMENT_DATE, P.PAYMENT_FOR_MONTH_YEAR ";
		sql += "FROM PAYMENT P ";
		sql += "INNER JOIN STUDENT S ON S.STUDENT_ID = P.STUDENT_ID ";
		sql += "WHERE PAYMENT_ID = " + paymentVO.getPaymentId() + " ORDER BY P.PAYMENT_ID";
		
		rowList = selectDb (sql, 8);		
		if (!rowList.isEmpty() && rowList.size() > 0) {			
			for (Object columnList : rowList) {				
				List<Object> columns = (List<Object>)columnList;					
				Long paymentId             = (Long) columns.get (0);
				Long studentId             = (Long) columns.get (1);
				String cpf                 = (String) columns.get (2);
				String fullName            = (String) columns.get (3);
				String description         = (String) columns.get (4);
				Double amount              = (Double) columns.get (5);
				Date paymentDate           = (Date) columns.get (6);
				String paymentForMonthYear = (String) columns.get (7);
				
				foundPaymentVO = new PaymentVO();
				foundPaymentVO.setPaymentId (paymentId);
				
				StudentVO studentVO = new StudentVO ();
				studentVO.setStudentId (studentId);
				studentVO.setCpf (cpf);
				studentVO.setFullName (fullName);
				
				foundPaymentVO.setStudentVO (studentVO);
				foundPaymentVO.setDescription (description);
				foundPaymentVO.setAmount (Utils.formatDoubleToReal (amount));
				foundPaymentVO.setPaymentDate (paymentDate);
				foundPaymentVO.setPaymentForMonthYear (paymentForMonthYear);
			}
		}
		return foundPaymentVO;
	}
		
	/**
	 * Finds the payments by filter.
	 * 
	 * @return a list of payments.
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<PaymentVO> findByFilter (PaymentFilterVO filter) throws DataAccessException {
		String sql            = null;
		List<Object> rowList  = null;
		List<PaymentVO> paymentList = new ArrayList<PaymentVO>();
		String whereClause    = "";
		boolean hasWhereClause = false;
		if (Utils.isNonEmpty (filter.getFullName())) {
			whereClause = "WHERE S.FULL_NAME LIKE '%" + filter.getFullName() + "%' ";
			hasWhereClause = true;
		}
		if (Utils.isNonEmpty (filter.getStartDate()) 
				&& Utils.isNonEmpty (filter.getEndDate())) {
			if (hasWhereClause) {
				whereClause += "AND ";
			} else {
				whereClause += "WHERE ";
				hasWhereClause = true;
			}
			whereClause += "P.PAYMENT_DATE >= STR_TO_DATE ('" + filter.getStartDate() + " 00:00:00', '%d/%m/%Y %H:%i:%s') AND P.PAYMENT_DATE <= STR_TO_DATE ('" + filter.getEndDate() + " 23:59:59', '%d/%m/%Y %H:%i:%s')";
		}
		if (Utils.isNonEmpty (filter.getCpf())) {
			if (hasWhereClause) {
				whereClause += "AND ";
			} else {
				whereClause += "WHERE ";				
			}
			whereClause += "S.CPF = '" + filter.getCpf() + "'";
		}
		sql = "SELECT P.PAYMENT_ID, S.STUDENT_ID, S.CPF, S.FULL_NAME, P.DESCRIPTION, P.AMOUNT, P.PAYMENT_DATE, P.PAYMENT_FOR_MONTH_YEAR ";
		sql += "FROM PAYMENT P ";
		sql += "INNER JOIN STUDENT S ON S.STUDENT_ID = P.STUDENT_ID ";
		sql += whereClause + " ORDER BY P.PAYMENT_ID";
		
		logger.debug ("sql: " + sql);
				
		rowList = selectDb (sql, 8);
		if (!rowList.isEmpty() && rowList.size() > 0) {
			for (Object columnList : rowList) {	
				List<Object> columns    = (List<Object>)columnList;					
				Long paymentId          = (Long) columns.get (0);
				Long studentId          = (Long) columns.get (1);
				String cpf              = (String) columns.get (2);
				String fullName         = (String) columns.get (3);
				String description      = (String) columns.get (4);
				Double amount           = (Double) columns.get (5);
				Date paymentDate        = (Date) columns.get (6);
				String paymentForMonthYear = (String) columns.get (7);
								
				PaymentVO paymentVO = new PaymentVO();
				paymentVO.setPaymentId (paymentId);
				
				StudentVO studentVO = new StudentVO();
				studentVO.setStudentId (studentId);
				studentVO.setCpf (cpf);
				studentVO.setFullName (fullName);
				
				paymentVO.setStudentVO (studentVO);
				paymentVO.setDescription (description);
				paymentVO.setAmount (Utils.formatDoubleToReal(amount));
				paymentVO.setPaymentDate (paymentDate);
				paymentVO.setPaymentForMonthYear (paymentForMonthYear);
																	
				paymentList.add (paymentVO);
			}
		}
		return paymentList;
	}
	
	/**
	 * Gets the row count of payments.
	 * 
	 * @return the row count.
	 * @throws DataAccessException
	 */
	public int rowCount () throws DataAccessException {
		int count = 0;
		String sql = null;
		sql = "SELECT COUNT(*) FROM PAYMENT";
		logger.debug ("sql: " + sql);
		count = selectRowCount (sql);
		return count;
	}
	
}
