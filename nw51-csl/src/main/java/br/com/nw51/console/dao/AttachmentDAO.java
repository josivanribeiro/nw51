package br.com.nw51.console.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.nw51.console.util.Utils;
import br.com.nw51.common.vo.AttachmentVO;
import br.com.nw51.common.vo.ClassVO;

/**
 * DAO class for Attachment.
 * 
 * @author Josivan Silva
 *
 */
public class AttachmentDAO extends AbstractDAO {

	static Logger logger = Logger.getLogger (AttachmentDAO.class.getName());
	
	public AttachmentDAO () {
		
	}
	
	/**
	 * Inserts a new attachment.
	 * 
	 * @param attachmentVO the attachment.
	 * @return the number of affected rows.
	 * @throws DataAccessException
	 */
	public int insert (AttachmentVO attachmentVO) throws DataAccessException {
		Connection conn         = null;
		PreparedStatement pstmt = null;
		String sql              = null;
		int affectedRows = 0;
		try {
			conn = this.getConnection();
			sql = "INSERT INTO ATTACHMENT (CLASS_ID, NAME, TYPE, SIZE, FILE, CREATION_DATE) VALUES(?,?,?,?,?,?)";
			pstmt = conn.prepareStatement (sql);
			pstmt.setInt (1, attachmentVO.getClassVO().getClassId());
			pstmt.setString (2, attachmentVO.getName());
			pstmt.setString (3, attachmentVO.getType());
			pstmt.setString (4, attachmentVO.getSize());
			pstmt.setBinaryStream (5, attachmentVO.getFile());
			pstmt.setDate (6, Utils.getCurrentDate());
			affectedRows = pstmt.executeUpdate();			
			logger.debug ("affectedRows: " + affectedRows);
		} catch (SQLException e) {
		    String error = "An error occurred while executing the insert attachment sql statement. " + e.getMessage();
		    logger.error (error);
		    throw new DataAccessException (error);
		} finally {
			this.closePreparedStatement (pstmt);
			this.closeConnection (conn);
		}
		return affectedRows;
	}
	
	/**
	 * Deletes an attachment.
	 * 
	 * @param attachmentVO the attachment.
	 * @return the number of affected rows.
	 * @throws DataAccessException
	 */
	public int delete (AttachmentVO attachmentVO) throws DataAccessException {
		String sql       = null;
		int affectedRows = 0;
		sql = "DELETE FROM ATTACHMENT WHERE ATTACHMENT_ID = " + attachmentVO.getAttachmentId();
		affectedRows = updateDb (sql);
		return affectedRows;
	}
	
	/**
	 * Deletes attachments by class id.
	 * 
	 * @param classVO the class.
	 * @return the number of affected rows.
	 * @throws DataAccessException
	 */
	public int deleteByClass (ClassVO classVO) throws DataAccessException {
		String sql       = null;
		int affectedRows = 0;
		sql = "DELETE FROM ATTACHMENT WHERE CLASS_ID = " + classVO.getClassId();
		affectedRows = updateDb (sql);
		return affectedRows;
	}
	
	/**
	 * Find an Attachment by its id.
	 * 
	 * @param attachmentVO the attachment.
	 * @return an attachment.
	 * @throws DataAccessException
	 */
	public AttachmentVO findById (AttachmentVO attachmentVO) throws DataAccessException {
		AttachmentVO foundAttachmentVO = null;
		Connection conn                = null;
		PreparedStatement pstmt        = null;
		ResultSet rs                   = null;
		String sql                     = null;
		try {
			conn = super.getConnection();
			sql = "SELECT ATTACHMENT_ID, CLASS_ID, NAME, TYPE, SIZE, FILE, CREATION_DATE FROM ATTACHMENT WHERE ATTACHMENT_ID = " + attachmentVO.getAttachmentId();
			pstmt = conn.prepareStatement (sql);
			rs = pstmt.executeQuery();
		    while (rs.next()) {		    	
		    	Integer attachmentId = rs.getInt (1);
				Integer classId      = rs.getInt (2);
				String  name         = rs.getString (3);
				String  type         = rs.getString (4);
				String  size         = rs.getString (5);
				InputStream file     = rs.getBinaryStream (6);
				Date  creationDate   = rs.getDate (7);
		        
				foundAttachmentVO = new AttachmentVO();
				foundAttachmentVO.setAttachmentId (attachmentId);
				ClassVO classVO = new ClassVO ();
				classVO.setClassId (classId);
				foundAttachmentVO.setClassVO (classVO);
				foundAttachmentVO.setName (name);
				foundAttachmentVO.setType (type);
				foundAttachmentVO.setSize (size);
				foundAttachmentVO.setFile (file);
				foundAttachmentVO.setCreationDate (creationDate);
		    }
		    
		} catch (SQLException e) {
		    String error = "An error occurred while executing the sql select statement. " + e.getMessage();
		    logger.error (error);
		    throw new DataAccessException (error);
		} finally {
			this.closeResultSet (rs);
			this.closePreparedStatement (pstmt);
			this.closeConnection (conn);
		}
		return foundAttachmentVO;
	}
	
	/**
	 * Finds attachments by class.
	 * 
	 * @return a list of attachments.
	 * @throws DataAccessException
	 */
	public List<AttachmentVO> findAttachmentsByClass (ClassVO classVO) throws DataAccessException {
		List<AttachmentVO> attachmentVOList = new ArrayList<AttachmentVO>();
		Connection conn                     = null;
		PreparedStatement pstmt             = null;
		ResultSet rs                        = null;
		String sql                          = null;
		try {
			conn = super.getConnection();
			sql = "SELECT ATTACHMENT_ID, CLASS_ID, NAME, TYPE, SIZE, FILE, CREATION_DATE FROM ATTACHMENT WHERE CLASS_ID = ?";
			pstmt = conn.prepareStatement (sql);
			pstmt.setInt (1, classVO.getClassId());
			rs = pstmt.executeQuery();
		    while (rs.next()) {		    	
		    	Integer attachmentId = rs.getInt (1);
				Integer classId      = rs.getInt (2);
				String  name         = rs.getString (3);
				String  type         = rs.getString (4);
				String  size         = rs.getString (5);
				InputStream file     = rs.getBinaryStream (6);
				Date  creationDate   = rs.getDate (7);
		        
				AttachmentVO attachmentVO = new AttachmentVO();
				attachmentVO.setAttachmentId (attachmentId);
				ClassVO newClassVO = new ClassVO ();
				newClassVO.setClassId (classId);
				attachmentVO.setClassVO (classVO);
				attachmentVO.setName (name);
				attachmentVO.setType (type);
				attachmentVO.setSize (size);
				attachmentVO.setFile (file);
				attachmentVO.setCreationDate (creationDate);
				
				attachmentVOList.add (attachmentVO);
		    }
		    
		} catch (SQLException e) {
		    String error = "An error occurred while finding attachments by class. " + e.getMessage();
		    logger.error (error);
		    throw new DataAccessException (error);
		} finally {
			this.closeResultSet (rs);
			this.closePreparedStatement (pstmt);
			this.closeConnection (conn);
		}
		return attachmentVOList;
	}
	
}
