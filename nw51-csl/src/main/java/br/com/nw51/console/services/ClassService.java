package br.com.nw51.console.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.nw51.common.util.WorkloadHoursTypeEnum;
import br.com.nw51.common.vo.AttachmentVO;
import br.com.nw51.common.vo.ClassPlanToolVO;
import br.com.nw51.common.vo.ClassPlanVO;
import br.com.nw51.common.vo.ClassVO;
import br.com.nw51.common.vo.CourseVO;
import br.com.nw51.common.vo.SlideVO;
import br.com.nw51.console.dao.AttachmentDAO;
import br.com.nw51.console.dao.ClassDAO;
import br.com.nw51.console.dao.ClassPlanDAO;
import br.com.nw51.console.dao.ClassPlanToolDAO;
import br.com.nw51.console.dao.ContentItemDAO;
import br.com.nw51.console.dao.CourseDAO;
import br.com.nw51.console.dao.DataAccessException;
import br.com.nw51.console.dao.ExerciseDAO;
import br.com.nw51.console.dao.SlideDAO;

/**
 * Business Service class for Class.
 * 
 * @author Josivan Silva
 *
 */
public class ClassService {

	static Logger logger = Logger.getLogger (ClassService.class.getName());
	private CourseDAO courseDAO = new CourseDAO();
	private ClassDAO classDAO = new ClassDAO();
	private ClassPlanDAO classPlanDAO = new ClassPlanDAO();
	private ClassPlanToolDAO classPlanToolDAO = new ClassPlanToolDAO();
	private SlideDAO slideDAO = new SlideDAO();
	private ExerciseDAO exerciseDAO = new ExerciseDAO();
	private AttachmentDAO attachmentDAO = new AttachmentDAO();
	private ContentItemDAO contentItemDAO = new ContentItemDAO ();
		
	public ClassService() {
		
	}
	
	/**
	 * Inserts a new class.
	 * 
	 * @param classVO the class.
	 * @return the class id.
	 * @throws BusinessException
	 */
	public Integer insert (ClassVO classVO) throws BusinessException {
		Integer newClassId = 0;
		Integer newClassPlanId = 0;
		try {
			newClassPlanId = classPlanDAO.insert (classVO.getClassPlanVO());
			
			if (newClassPlanId != null && newClassPlanId > 0) {
				
				for (ClassPlanToolVO classPlanToolVO : classVO.getClassPlanVO().getClassPlanToolVOList()) {
					ClassPlanVO classPlanVO = new ClassPlanVO ();
					classPlanVO.setClassPlanId (newClassPlanId);
					classPlanToolVO.setClassPlanVO (classPlanVO);
					classPlanToolDAO.insert (classPlanToolVO);
				}				
			}
			
		} catch (DataAccessException e) {
				String errorMessage = "A business exception error occurred while inserting the class plan/class plan tool. " + e.getMessage();
				logger.error (errorMessage);
				throw new BusinessException (errorMessage, e.getCause());
		}
		try {
			if (newClassPlanId > 0) {
				classVO.getClassPlanVO().setClassPlanId (newClassPlanId);
				newClassId = classDAO.insert (classVO);
				classVO.setClassId (newClassId);
				
				courseDAO.updateLastUpdateDate (classVO.getCourseVO());
				
				if (classVO.getAttachmentVOList() != null && classVO.getAttachmentVOList().size() > 0) {
					
					updateClassIdInAttachmentList (classVO.getAttachmentVOList(), classVO);					
					for (AttachmentVO attachmentVO : classVO.getAttachmentVOList()) {
						attachmentDAO.insert (attachmentVO);
					}
				}
				
				classVO.getContentItemVO().setClassVO (classVO);
				contentItemDAO.updateClass (classVO.getContentItemVO());				
			}
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while inserting the class/attachment. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return newClassId;
	}
	
	/**
	 * Updates a class.
	 * 
	 * @param classVO the class.
	 * @return the number of affected rows.
	 * @throws BusinessException
	 */
	public int update (ClassVO classVO) throws BusinessException {
		int classAffectedRows = 0;
		int classPlanAffectedRows = 0;
		
		try {
			classPlanAffectedRows = classPlanDAO.update (classVO.getClassPlanVO());			
			if (classPlanAffectedRows > 0) {
				classPlanToolDAO.deleteByClassPlan (classVO.getClassPlanVO());
				for (ClassPlanToolVO classPlanToolVO : classVO.getClassPlanVO().getClassPlanToolVOList()) {
					classPlanToolVO.setClassPlanVO (classVO.getClassPlanVO());
					classPlanToolDAO.insert (classPlanToolVO);
				}
			}			
			logger.debug ("classPlanAffectedRows [" + classPlanAffectedRows + "]");
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while updating the teaching plan. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		
		try {			
			if (classPlanAffectedRows > 0) {
				
				attachmentDAO.deleteByClass (classVO);
				
				if (classVO.getAttachmentVOList() != null && classVO.getAttachmentVOList().size() > 0) {
					updateClassIdInAttachmentList (classVO.getAttachmentVOList(), classVO);
					for (AttachmentVO attachmentVO : classVO.getAttachmentVOList()) {
						attachmentDAO.insert (attachmentVO);
					}
				}
				
				classAffectedRows = classDAO.update (classVO);
				
				courseDAO.updateLastUpdateDate (classVO.getCourseVO());
				
				classVO.getContentItemVO().setClassVO (classVO);
				contentItemDAO.updateClass (classVO.getContentItemVO());
				
				logger.debug ("classAffectedRows [" + classAffectedRows + "]");
			}
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while updating the class. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return classAffectedRows;
	}
	
	/**
	 * Updates the average time of all classPlans of classes of a course.
	 * @param courseVO the course.
	 * @param averageTime the new average time.
	 * @throws BusinessException
	 */
	public void updateAverageTime (CourseVO courseVO, Float averageTime) throws BusinessException {
		try {			
			List<ClassVO> classVOList = classDAO.findByCourseId (courseVO);			
			if (classVOList != null && classVOList.size() > 0) {				
				for (ClassVO classVO : classVOList) {					
					classVO.getClassPlanVO().setAverageTime (averageTime);					
					classPlanDAO.updateAverageTime (classVO.getClassPlanVO());
					logger.debug ("classVO.getClassId [" + classVO.getClassId() + "]");
					logger.debug ("getClassPlanVO().getClassPlanId [" + classVO.getClassPlanVO().getClassPlanId() + "]");
					logger.debug ("getClassPlanVO().getAverageTime() [" + classVO.getClassPlanVO().getAverageTime() + "]");
				}
			}			
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while find classes by course or updating average time. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
	}
	
	/**
	 * Deletes a class.
	 * 
	 * @param classVO the class.
	 * @return the number of affected rows.
	 * @throws BusinessException
	 */
	public int delete (ClassVO classVO) throws BusinessException {
		int classPlanAffectedRows = 0;
		int classAffectedRows = 0;
		try {	
			
			contentItemDAO.updateClass (classVO.getContentItemVO());
			
			classAffectedRows = classDAO.delete (classVO);
			
			courseDAO.updateLastUpdateDate (classVO.getCourseVO());			
			
			logger.debug ("classAffectedRows [" + classAffectedRows + "]");
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while deleting the class. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		try {
			if (classAffectedRows > 0) {
				classPlanAffectedRows = classPlanDAO.delete (classVO.getClassPlanVO());
			}
			logger.debug ("classPlanAffectedRows [" + classPlanAffectedRows + "]");
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while deleting the class plan. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return classAffectedRows;
	}
	
	/**
	 * Deletes an attachment.
	 */
	public int deleteAttachment (AttachmentVO attachmentVO, CourseVO courseVO) throws BusinessException {
		int affectedRows = 0;
		try {	
			affectedRows = attachmentDAO.delete (attachmentVO);
			
			courseDAO.updateLastUpdateDate (courseVO);
			
			logger.debug ("affectedRows [" + affectedRows + "]");
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while deleting the attachment. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return affectedRows;
	}
	
	/**
	 * Finds a class by its id.
	 * 
	 * @param ClassVO the class.
	 * @return the found class.
	 * @throws BusinessException
	 */
	public ClassVO findById (ClassVO classVO) throws BusinessException {
		ClassVO foundClassVO = null;
		List<AttachmentVO> attachmentVOList = null;
		ClassPlanVO foundClassPlanVO = null;
		try {
			foundClassVO = classDAO.findById (classVO);
			
			attachmentVOList = attachmentDAO.findAttachmentsByClass (foundClassVO);
			foundClassVO.setAttachmentVOList (attachmentVOList);
			
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding the class by id. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}		
		try {
			foundClassPlanVO = classPlanDAO.findById (foundClassVO.getClassPlanVO());
			
			List<ClassPlanToolVO> classPlanToolList = classPlanToolDAO.findByClassPlan (foundClassVO.getClassPlanVO());
			if (classPlanToolList != null && classPlanToolList.size() > 0) {
				foundClassPlanVO.setClassPlanToolVOList (classPlanToolList);
			}
			
			if (foundClassPlanVO != null) {
				foundClassVO.setClassPlanVO (foundClassPlanVO);
			}			
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding the class plan by id. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}		
		return foundClassVO;
	}
	
	/**
	 * Finds the slides by class id.
	 * 
	 * @return a list of slides.
	 * @throws BusinessException
	 */
	public List<SlideVO> findSlidesByClassId (ClassVO classVO) throws BusinessException {
		List<SlideVO> slideVOList = null;
		try {
			slideVOList = slideDAO.findByClass (classVO);
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding the slides by class id. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return slideVOList;
	}
	
	/**
	 * Finds attachments by class id.
	 * 
	 * @param ClassVO the class.
	 * @return the list of attachments.
	 * @throws BusinessException
	 */
	public List<AttachmentVO> findAttachmentsByClass (ClassVO classVO) throws BusinessException {
		List<AttachmentVO> attachmentVOList = null;		
		try {
			attachmentVOList = attachmentDAO.findAttachmentsByClass (classVO);			
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding the attachments by class id. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}				
		return attachmentVOList;
	}
	
	/**
	 * Calculates the class average time.
	 * 
	 * @param classVO the class.
	 * @return the average time.
	 * @throws BusinessException
	 */
	public Float calculateAverageTime (ClassVO classVO) throws BusinessException {
		logger.debug ("Starting calculateAverageTime");
		CourseVO courseVO    = null;
		Integer totalMinutes = 0;
		int totalClasses     = 0;
		Float averageTime    = null;
		try {
			courseVO = courseDAO.findById (classVO.getCourseVO());
			totalClasses = classDAO.countClassesByCourse (classVO);
						
			Integer workloadHoursTheoryType = courseVO.getTeachingPlanVO().getWorkloadHoursTheoryType();
			Integer workloadHoursPracticeType = courseVO.getTeachingPlanVO().getWorkloadHoursPracticeType();
			
			int totalHoursTheory = WorkloadHoursTypeEnum.getHoursById (workloadHoursTheoryType);
			int totalHoursPractice = WorkloadHoursTypeEnum.getHoursById (workloadHoursPracticeType);
			
			totalMinutes = (totalHoursTheory + totalHoursPractice) * 60;
			
			if (totalClasses == 0) {
				totalClasses = 1;
			} else {
				totalClasses += 1;
			}
			
			BigDecimal total = new BigDecimal (totalMinutes);
			BigDecimal classes = new BigDecimal (totalClasses);
			BigDecimal result = total.divide (classes, 2, RoundingMode.HALF_EVEN);
			
			averageTime = result.floatValue();
						
			logger.debug ("totalClasses: " + totalClasses);
			//logger.debug ("workloadHoursType: " + workloadHoursType);
			logger.debug ("averageTime: " + averageTime);
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding the course by id or getting count classes by course. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		logger.debug ("Finishing calculateAverageTime");
		return averageTime;
	}
	
	/**
	 * Updates classId in the list of attachments.
	 * 
	 * @param attachmentVOList the attachment list.
	 * @param classVO the classId.
	 */
	private void updateClassIdInAttachmentList (List<AttachmentVO> attachmentVOList, ClassVO classVO) {
		for (AttachmentVO attachmentVO : attachmentVOList) {
			
			ClassVO newClassVO = new ClassVO();
			newClassVO.setClassId (classVO.getClassId());
			
			attachmentVO.setClassVO (newClassVO);
		}
	}
	
	/**
	 * Finds the next sequence number for Slide or Exercise.
	 *  
	 * @return the sequence number.
	 * @throws BusinessException
	 */
	public Integer findNextSequenceNumber () throws BusinessException {
		Integer slideSequenceNumber    = null;
		Integer exerciseSequenceNumber = null;
		Integer sequenceNumber         = null;
		try {
			slideSequenceNumber = slideDAO.findNextSequenceNumber();
			exerciseSequenceNumber = exerciseDAO.findNextSequenceNumber();
			sequenceNumber = (slideSequenceNumber > exerciseSequenceNumber) ? slideSequenceNumber : exerciseSequenceNumber;
			sequenceNumber++;
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding the next sequence number. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}				
		return sequenceNumber;
	}
	
}
