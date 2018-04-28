package br.com.nw51.console.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.nw51.common.util.Utils;
import br.com.nw51.common.vo.CertificateFilterVO;
import br.com.nw51.common.vo.CertificateVO;
import br.com.nw51.common.vo.CourseVO;
import br.com.nw51.common.vo.StudentCourseVO;
import br.com.nw51.common.vo.StudentVO;

/**
 * DAO class for Certificate.
 * 
 * @author Josivan Silva
 *
 */
public class CertificateDAO extends AbstractDAO {

	static Logger logger = Logger.getLogger (CertificateDAO.class.getName());
	
	public CertificateDAO () {
		
	}
		
	/**
	 * Inserts a new certificate.
	 * 
	 * @param certificateVO the certificate.
	 * @return the certificate id.
	 * @throws DataAccessException
	 */
	public Long insert (CertificateVO certificateVO) throws DataAccessException {
		Long newCertificateId = null;
		StringBuilder sbSql = null;
		sbSql = new StringBuilder();
		sbSql.append ("INSERT INTO CERTIFICATE (STUDENT_COURSE_ID, CREATION_DATE) ");
		sbSql.append ("VALUES (" +  certificateVO.getStudentCourseVO().getStudentCourseId() + ", ");
		sbSql.append ("NOW())");
		logger.debug ("sql: " + sbSql.toString());		
		newCertificateId = insertDbWithLongKey (sbSql.toString());
		return newCertificateId;
	}
		
	/**
	 * Find a certificate by its id.
	 * 
	 * @param certificateVO the certificate.
	 * @return a certificate.
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public CertificateVO findById (CertificateVO certificateVO) throws DataAccessException {
		CertificateVO foundCertificateVO = null;
		String sql           = null;
		List<Object> rowList = null;
		sql = "SELECT CER.CERTIFICATE_ID, CER.STUDENT_COURSE_ID, CER.CREATION_DATE, "
			  + "C.NAME, S.FULL_NAME "
			  + "FROM CERTIFICATE CER "
			  + "INNER JOIN STUDENT_COURSE SC ON SC.STUDENT_COURSE_ID = CER.STUDENT_COURSE_ID "
			  + "INNER JOIN COURSE C ON C.COURSE_ID = SC.COURSE_ID "
			  + "INNER JOIN STUDENT S ON S.STUDENT_ID = SC.STUDENT_ID "
			  + "WHERE CER.CERTIFICATE_ID = " + certificateVO.getCertificateId();		
		logger.debug ("sql: " + sql);		
		rowList = selectDb (sql, 5);
		if (!rowList.isEmpty() && rowList.size() > 0) {
			for (Object columnList : rowList) {
				List<Object> columns = (List<Object>)columnList;			
				Long certificateId   = (Long) columns.get (0);
				Long studentCourseId = (Long) columns.get (1);
				Date creationDate    = (Date) columns.get (2);
				String courseName    = (String) columns.get (3);
				String studentName   = (String) columns.get (4);
								
				foundCertificateVO = new CertificateVO();
				foundCertificateVO.setCertificateId (certificateId);
				
				StudentCourseVO studentCourseVO = new StudentCourseVO ();
				studentCourseVO.setStudentCourseId (studentCourseId);
				
				CourseVO courseVO = new CourseVO ();
				courseVO.setName (courseName);
				
				StudentVO studentVO = new StudentVO();
				studentVO.setFullName (studentName);
				
				studentCourseVO.setCourseVO (courseVO);
				studentCourseVO.setStudentVO (studentVO);				
				
				foundCertificateVO.setStudentCourseVO (studentCourseVO);
				foundCertificateVO.setCreationDate (creationDate);				
			}
		}
		return foundCertificateVO;
	}
		
	/**
	 * Finds the certificates by filter.
	 * 
	 * @return a list of certificates.
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<CertificateVO> findByFilter (CertificateFilterVO filter) throws DataAccessException {
		String sql            = null;
		List<Object> rowList  = null;
		List<CertificateVO> certificateList = new ArrayList<CertificateVO>();
		String whereClause    = "";
		boolean hasWhereClause = false;
		if (filter.getCourseId() != null && filter.getCourseId() > 0) {
			whereClause = "WHERE C.COURSE_ID = " + filter.getCourseId();
			hasWhereClause = true;
		}
		if (Utils.isNonEmpty (filter.getCpf())) {
			if (hasWhereClause) {
				whereClause += "AND ";
			} else {
				whereClause += "WHERE ";
				hasWhereClause = true;
			}
			whereClause += "S.CPF = '" + filter.getCpf() + "'";
		}
		if (Utils.isNonEmpty (filter.getStartDate()) 
				&& Utils.isNonEmpty (filter.getEndDate())) {
			if (hasWhereClause) {
				whereClause += "AND ";
			} else {
				whereClause += "WHERE ";
				hasWhereClause = true;
			}
			whereClause += "CER.CREATION_DATE >= STR_TO_DATE ('" + filter.getStartDate() + " 00:00:00', '%d/%m/%Y %H:%i:%s') AND CER.CREATION_DATE <= STR_TO_DATE ('" + filter.getEndDate() + " 23:59:59', '%d/%m/%Y %H:%i:%s')";
		}				
		sql = "SELECT CER.CERTIFICATE_ID, CER.CREATION_DATE, "
			+ "C.NAME, S.FULL_NAME "
		    + "FROM CERTIFICATE CER "
			+ "INNER JOIN STUDENT_COURSE SC ON SC.STUDENT_COURSE_ID = CER.STUDENT_COURSE_ID "
			+ "INNER JOIN COURSE C ON C.COURSE_ID = SC.COURSE_ID "
			+ "INNER JOIN STUDENT S ON S.STUDENT_ID = SC.STUDENT_ID "
			+ whereClause + " ORDER BY CER.CERTIFICATE_ID";				  
		logger.debug ("sql: " + sql);				
		rowList = selectDb (sql, 4);
		if (!rowList.isEmpty() && rowList.size() > 0) {
			for (Object columnList : rowList) {	
				List<Object> columns = (List<Object>)columnList;
				Long certificateId   = (Long) columns.get (0);
				Date creationDate    = (Date) columns.get (1);
				String courseName    = (String) columns.get (2);
				String studentName   = (String) columns.get (3);
								
				CertificateVO certificateVO = new CertificateVO();
				certificateVO.setCertificateId (certificateId);
				
				StudentCourseVO studentCourseVO = new StudentCourseVO ();
								
				CourseVO courseVO = new CourseVO ();
				courseVO.setName (courseName);
				
				StudentVO studentVO = new StudentVO();
				studentVO.setFullName (studentName);
				
				studentCourseVO.setCourseVO (courseVO);
				studentCourseVO.setStudentVO (studentVO);				
				
				certificateVO.setStudentCourseVO (studentCourseVO);
				certificateVO.setCreationDate (creationDate);
													
				certificateList.add (certificateVO);
			}
		}
		return certificateList;
	}
	
	/**
	 * Gets the row count of certificates.
	 * 
	 * @return the row count.
	 * @throws DataAccessException
	 */
	public int rowCount () throws DataAccessException {
		int count = 0;
		String sql = null;
		sql = "SELECT COUNT(*) FROM CERTIFICATE";
		logger.debug ("sql: " + sql);
		count = selectRowCount (sql);
		return count;
	}
	
}
