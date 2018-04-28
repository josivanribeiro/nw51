package br.com.nw51.student.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.nw51.common.vo.CourseVO;
import br.com.nw51.common.vo.StudentVO;
import br.com.nw51.common.vo.StudyPlanFilterVO;
import br.com.nw51.common.vo.StudyPlanVO;
import br.com.nw51.student.dao.DataAccessException;
import br.com.nw51.student.dao.StudyPlanDAO;

/**
 * Business Service class for StudyPlan.
 * 
 * @author Josivan Silva
 *
 */
public class StudyPlanService {

	static Logger logger = Logger.getLogger (StudyPlanService.class.getName());	
	private StudyPlanDAO studyPlanDAO = new StudyPlanDAO();
		
	public StudyPlanService() {
		
    }
	
	/**
	 * Inserts the studyPlans.
	 * 
	 * @param studyPlanVOList the studyPlan list.
	 * @return the boolean indicating success or not.
	 * @throws BusinessException
	 */
	public boolean insert (List<StudyPlanVO> studyPlanVOList) throws BusinessException {
		boolean success = false;
		try {			
			for (StudyPlanVO studyPlanVO : studyPlanVOList) {
				studyPlanDAO.insert (studyPlanVO);
			}
			success = true;
			logger.debug ("success [" + success + "]");
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while inserting the study plans. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return success;
	}
	
	/**
	 * Updates the studyPlan.
	 * 
	 * @param studyPlanVO the studyPlan.
	 * @return the operation result.
	 * @throws BusinessException
	 */
	public boolean update (List<StudyPlanVO> studyPlanVOList, List<StudyPlanVO> studyPlanVOToRemoveList, StudentVO studentVO, CourseVO courseVO) throws BusinessException {
		boolean success = false;
		if (studyPlanVOToRemoveList != null && studyPlanVOToRemoveList.size() > 0) {
			try {
				for (StudyPlanVO studyPlanVO : studyPlanVOToRemoveList) {					
					studyPlanDAO.delete (studyPlanVO);					
				}
			} catch (DataAccessException e) {
				String errorMessage = "A business exception error occurred while deleting the study plans. " + e.getMessage();
				logger.error (errorMessage);
				throw new BusinessException (errorMessage, e.getCause());
			}
		}		
		try {
			for (StudyPlanVO studyPlanVO : studyPlanVOList) {
				
				logger.debug("\nstudyPlanVO.getStudyPlanId(): " + studyPlanVO.getStudyPlanId());
				logger.debug("\nstudyPlanVO.isNew(): " + studyPlanVO.isNew());
				logger.debug("\nstudyPlanVO.isChanged(): " + studyPlanVO.isChanged());
				
				if (studyPlanVO.isNew()) {
					studyPlanDAO.insert (studyPlanVO);
				} else if (studyPlanVO.isChanged()) {
					studyPlanDAO.update (studyPlanVO);
				}
			}
			success = true;
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while inserting/updating the study plans. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return success;
	}
	
	/**
	 * Deletes the studyPlan.
	 * 
	 * @param studyPlanVO the studyPlan.
	 * @return the number of affected rows.
	 * @throws BusinessException
	 */
	public int delete (StudyPlanVO studyPlanVO) throws BusinessException {
		int affectedRows = 0;
		try {
			affectedRows = studyPlanDAO.delete (studyPlanVO);			
			logger.debug ("affectedRows [" + affectedRows + "]");
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while deleting the study plan. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return affectedRows;
	}
	
	/**
	 * Finds a studyPlan by its id.
	 * 
	 * @param studyPlanVO the studyPlan.
	 * @return the found studyPlan.
	 * @throws BusinessException
	 */
	public StudyPlanVO findById (StudyPlanVO studyPlanVO) throws BusinessException {
		StudyPlanVO foundStudyPlanVO = null;
		try {
			foundStudyPlanVO = studyPlanDAO.findById (studyPlanVO);
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding the studyPlan by id. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return foundStudyPlanVO;
	}
	
	/**
	 * Finds studyPlans by studentId and courseId.
	 * 
	 * @param studentVO the student.
	 * @param courseVO the course.
	 * @return a list of studyPlans.
	 * @throws BusinessException
	 */
	public List<StudyPlanVO> findByStudentAndCourse (StudentVO studentVO, CourseVO courseVO) throws BusinessException {
		List<StudyPlanVO> studyPlanVOList = new ArrayList<StudyPlanVO>();
		try {
			studyPlanVOList = studyPlanDAO.findByStudentAndCourse (studentVO, courseVO);
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding studyPlans by studentId and courseId. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return studyPlanVOList;
	}
	
	/**
	 * Checks if a class period already exists or not.
	 * 
	 * @param studyPlanVO the studyPlan.
	 * @return a flag indicating success or false.
	 * @throws BusinessException
	 */
	public boolean existsClassPeriod (StudyPlanVO studyPlanVO) throws BusinessException {
		boolean exists = false;
		try {
			exists = studyPlanDAO.existsClassPeriod (studyPlanVO);
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while checking if class period exists. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return exists;
	}
	
}
