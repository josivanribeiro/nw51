package br.com.nw51.console.services;

import java.util.List;

import org.apache.log4j.Logger;

import br.com.nw51.common.vo.CertificateFilterVO;
import br.com.nw51.common.vo.CertificateVO;
import br.com.nw51.console.dao.CertificateDAO;
import br.com.nw51.console.dao.DataAccessException;

/**
 * Business Service class for Certificate.
 * 
 * @author Josivan Silva
 *
 */
public class CertificateService {

	static Logger logger = Logger.getLogger (CertificateService.class.getName());
	private CertificateDAO certificateDAO = new CertificateDAO();
			
	public CertificateService() {
		
	}
	
	/**
	 * Inserts a new course.
	 * 
	 * @param certificateVO the course.
	 * @return the certificate id.
	 * @throws BusinessException
	 */
	public Long insert (CertificateVO certificateVO) throws BusinessException {
		Long newCertificateId = null;
		try {
			newCertificateId = certificateDAO.insert (certificateVO);
			logger.debug ("newCertificateId [" + newCertificateId + "]");
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while inserting the certificate. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return newCertificateId;
	}
	
	/**
	 * Finds a certificate by its id.
	 * 
	 * @param CertificateVO the certificate.
	 * @return the found certificate.
	 * @throws BusinessException
	 */
	public CertificateVO findById (CertificateVO certificateVO) throws BusinessException {
		CertificateVO foundCertificateVO = null;
		try {
			foundCertificateVO = certificateDAO.findById (certificateVO);
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding the course by id. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return foundCertificateVO;
	}
	
	/**
	 * Finds the certificates by filter.
	 * 
	 * @return a list of certificates.
	 * @throws BusinessException
	 */
	public List<CertificateVO> findByFilter (CertificateFilterVO certificateFilterVO) throws BusinessException {
		List<CertificateVO> certificateVOList = null;
		try {
			certificateVOList = certificateDAO.findByFilter (certificateFilterVO);
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding the certificates by filter. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return certificateVOList;
	}
	
	/**
	 * Gets the row count of certificates.
	 * 
	 * @return the row count.
	 * @throws BusinessException
	 */
	public int rowCount () throws BusinessException {
		int count = 0;
		try {
			count = certificateDAO.rowCount();
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while getting the row count of certificates. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return count;
	}
	
}
