package br.com.nw51.console.controller;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import br.com.nw51.common.util.CourseTypeEnum;
import br.com.nw51.common.util.TeachingStrategyTypeEnum;
import br.com.nw51.console.util.Utils;
import br.com.nw51.common.util.WorkloadHoursTypeEnum;
import br.com.nw51.common.vo.ContentItemVO;
import br.com.nw51.common.vo.CourseFilterVO;
import br.com.nw51.common.vo.CourseVO;
import br.com.nw51.common.vo.TeacherVO;
import br.com.nw51.common.vo.TeachingPlanVO;
import br.com.nw51.console.services.BusinessException;
import br.com.nw51.console.services.CourseService;
import br.com.nw51.console.services.TeacherService;

/**
 * Course Controller.
 * 
 * @author Josivan Silva
 *
 */
@ManagedBean
@ViewScoped
public class CourseController extends AbstractController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger (CourseController.class.getName());
	private CourseService courseService = new CourseService();
	private TeacherService teacherService = new TeacherService();
	
	private String nameFilter;
	private Integer teacherIdFilter;
	private Integer courseTypeFilter;
	private String startCreationDateFilter;
	private String endCreationDateFilter;	
	/*Teaching Plan*/
	private Integer teachingPlanIdForm;
	private int workloadHoursTheoryTypeForm;
	private int workloadHoursPracticeTypeForm;
	private String summaryForm;
	private String generalGoalForm;
	private String specificGoalsForm;
	private int teachingStrategyTypeForm;
	private String resourcesForm;
	private String evaluationForm;
	private String bibliographyForm;
	private List<ContentItemVO> contentItemVOList;
	/*Course*/
	private List<CourseVO> courseVOList;
	private Integer courseIdForm;
	private Integer teacherIdForm;
	private Integer courseTypeForm;
	private String nameForm;
	private Date creationDateForm;
	private Date lastUpdateDateForm;
	private boolean statusForm  = true;
	
	private Map<String,Object> courseTypeMap;
	private Map<String,Object> teacherIdMap;
	private Map<String,Object> workloadHoursTheoryTypeMap;
	private Map<String,Object> workloadHoursPracticeTypeMap;
	private Map<String,Object> teachingStrategyTypeMap;
	private Map<String,Object> contentItemMap;
	
	/*Content Item*/
	private TreeNode root;
	private Integer contentItemIdForm;
	private String contentItemNumerationFatherForm;
	private String contentItemNumerationForm;
	private String contentItemNameForm;
		
	private boolean isContentItemAddedOrDeleted = false;
	
	
	public String getNameFilter() {
		return nameFilter;
	}

	public void setNameFilter(String nameFilter) {
		this.nameFilter = nameFilter;
	}

	public Integer getTeacherIdFilter() {
		return teacherIdFilter;
	}

	public void setTeacherIdFilter(Integer teacherIdFilter) {
		this.teacherIdFilter = teacherIdFilter;
	}

	public Integer getCourseTypeFilter() {
		return courseTypeFilter;
	}

	public void setCourseTypeFilter(Integer courseTypeFilter) {
		this.courseTypeFilter = courseTypeFilter;
	}

	public String getStartCreationDateFilter() {
		return startCreationDateFilter;
	}

	public void setStartCreationDateFilter(String startCreationDateFilter) {
		this.startCreationDateFilter = startCreationDateFilter;
	}

	public String getEndCreationDateFilter() {
		return endCreationDateFilter;
	}

	public void setEndCreationDateFilter(String endCreationDateFilter) {
		this.endCreationDateFilter = endCreationDateFilter;
	}

	public Integer getTeachingPlanIdForm() {
		return teachingPlanIdForm;
	}

	public void setTeachingPlanIdForm(Integer teachingPlanIdForm) {
		this.teachingPlanIdForm = teachingPlanIdForm;
	}

	public int getWorkloadHoursTheoryTypeForm() {
		return workloadHoursTheoryTypeForm;
	}

	public void setWorkloadHoursTheoryTypeForm(int workloadHoursTheoryTypeForm) {
		this.workloadHoursTheoryTypeForm = workloadHoursTheoryTypeForm;
	}

	public int getWorkloadHoursPracticeTypeForm() {
		return workloadHoursPracticeTypeForm;
	}

	public void setWorkloadHoursPracticeTypeForm(int workloadHoursPracticeTypeForm) {
		this.workloadHoursPracticeTypeForm = workloadHoursPracticeTypeForm;
	}

	public String getSummaryForm() {
		return summaryForm;
	}

	public void setSummaryForm(String summaryForm) {
		this.summaryForm = summaryForm;
	}

	public String getGeneralGoalForm() {
		return generalGoalForm;
	}

	public void setGeneralGoalForm(String generalGoalForm) {
		this.generalGoalForm = generalGoalForm;
	}

	public String getSpecificGoalsForm() {
		return specificGoalsForm;
	}

	public void setSpecificGoalsForm(String specificGoalsForm) {
		this.specificGoalsForm = specificGoalsForm;
	}

	public int getTeachingStrategyTypeForm() {
		return teachingStrategyTypeForm;
	}

	public void setTeachingStrategyTypeForm(int teachingStrategyTypeForm) {
		this.teachingStrategyTypeForm = teachingStrategyTypeForm;
	}

	public String getResourcesForm() {
		return resourcesForm;
	}

	public void setResourcesForm(String resourcesForm) {
		this.resourcesForm = resourcesForm;
	}

	public String getEvaluationForm() {
		return evaluationForm;
	}

	public void setEvaluationForm(String evaluationForm) {
		this.evaluationForm = evaluationForm;
	}

	public String getBibliographyForm() {
		return bibliographyForm;
	}

	public void setBibliographyForm(String bibliographyForm) {
		this.bibliographyForm = bibliographyForm;
	}

	public List<CourseVO> getCourseVOList() {
		return courseVOList;
	}

	public void setCourseVOList(List<CourseVO> courseVOList) {
		this.courseVOList = courseVOList;
	}

	public Integer getCourseIdForm() {
		return courseIdForm;
	}

	public void setCourseIdForm(Integer courseIdForm) {
		this.courseIdForm = courseIdForm;
	}

	public Integer getTeacherIdForm() {
		return teacherIdForm;
	}

	public void setTeacherIdForm(Integer teacherIdForm) {
		this.teacherIdForm = teacherIdForm;
	}

	public Integer getCourseTypeForm() {
		return courseTypeForm;
	}

	public void setCourseTypeForm(Integer courseTypeForm) {
		this.courseTypeForm = courseTypeForm;
	}

	public String getNameForm() {
		return nameForm;
	}

	public void setNameForm(String nameForm) {
		this.nameForm = nameForm;
	}

	public Date getCreationDateForm() {
		return creationDateForm;
	}

	public void setCreationDateForm(Date creationDateForm) {
		this.creationDateForm = creationDateForm;
	}

	public Date getLastUpdateDateForm() {
		return lastUpdateDateForm;
	}

	public void setLastUpdateDateForm(Date lastUpdateDateForm) {
		this.lastUpdateDateForm = lastUpdateDateForm;
	}

	public boolean isStatusForm() {
		return statusForm;
	}

	public void setStatusForm(boolean statusForm) {
		this.statusForm = statusForm;
	}

	public Map<String, Object> getCourseTypeMap() {
		return courseTypeMap;
	}

	public void setCourseTypeMap(Map<String, Object> courseTypeMap) {
		this.courseTypeMap = courseTypeMap;
	}

	public Map<String, Object> getTeacherIdMap() {
		return teacherIdMap;
	}

	public void setTeacherIdMap(Map<String, Object> teacherIdMap) {
		this.teacherIdMap = teacherIdMap;
	}
	
	public Map<String, Object> getWorkloadHoursTheoryTypeMap() {
		return workloadHoursTheoryTypeMap;
	}

	public void setWorkloadHoursTheoryTypeMap(Map<String, Object> workloadHoursTheoryTypeMap) {
		this.workloadHoursTheoryTypeMap = workloadHoursTheoryTypeMap;
	}

	public Map<String, Object> getWorkloadHoursPracticeTypeMap() {
		return workloadHoursPracticeTypeMap;
	}

	public void setWorkloadHoursPracticeTypeMap(Map<String, Object> workloadHoursPracticeTypeMap) {
		this.workloadHoursPracticeTypeMap = workloadHoursPracticeTypeMap;
	}

	public Map<String, Object> getTeachingStrategyTypeMap() {
		return teachingStrategyTypeMap;
	}

	public void setTeachingStrategyTypeMap(Map<String, Object> teachingStrategyTypeMap) {
		this.teachingStrategyTypeMap = teachingStrategyTypeMap;
	}
	
	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}
	
	public Map<String, Object> getContentItemMap() {
		return contentItemMap;
	}

	public void setContentItemMap(Map<String, Object> contentItemMap) {
		this.contentItemMap = contentItemMap;
	}

	public Integer getContentItemIdForm() {
		return contentItemIdForm;
	}

	public void setContentItemIdForm(Integer contentItemIdForm) {
		this.contentItemIdForm = contentItemIdForm;
	}

	public String getContentItemNumerationFatherForm() {
		return contentItemNumerationFatherForm;
	}

	public void setContentItemNumerationFatherForm (String contentItemNumerationFatherForm) {
		this.contentItemNumerationFatherForm = contentItemNumerationFatherForm;
	}

	public String getContentItemNumerationForm() {
		return contentItemNumerationForm;
	}

	public void setContentItemNumerationForm(String contentItemNumerationForm) {
		this.contentItemNumerationForm = contentItemNumerationForm;
	}

	public String getContentItemNameForm() {
		return contentItemNameForm;
	}

	public void setContentItemNameForm(String contentItemNameForm) {
		this.contentItemNameForm = contentItemNameForm;
	}
	
	@PostConstruct
    public void init() {
		super.checkReadOnly ("papel_curso_manter");
		logger.debug("this.isReadOnly(): " + super.isReadOnly());
		initCourseTypeSelect();
		initTeacherSelect();
		initWorkloadHoursTheoryTypeSelect();
		initWorkloadHoursPracticeTypeSelect();
		initTeachingStrategyTypeSelect();
		this.root = getDefaultRoot ();		
    }
	
	/**
	 * Finds all the courses.
	 */
	public void findByFilter () {
		CourseFilterVO courseFilterVO = new CourseFilterVO();
		courseVOList = new ArrayList<CourseVO>();
		if (isValidFilter()) {
			courseFilterVO.setName (this.nameFilter);
			courseFilterVO.setTeacherId (this.teacherIdFilter);
			courseFilterVO.setCourseType (this.courseTypeFilter);
			courseFilterVO.setStartDate (this.startCreationDateFilter);
			courseFilterVO.setEndDate (this.endCreationDateFilter);
			try {
				courseVOList = courseService.findByFilter (courseFilterVO);
				logger.debug ("courseVOList.size() [" + courseVOList.size() + "]");
			} catch (BusinessException e) {
				String error = "An error occurred while find the courses by filter. " + e.getMessage();
				logger.error (error);
			}
		}
	}
	
	/**
	 * Gets the count of courses.
	 */
	public int getCoursesCount() {
		int count = 0;
		try {
			count = courseService.rowCount();
		} catch (BusinessException e) {
			String error = "An error occurred while getting the row count of courses. " + e.getMessage();
			logger.error (error);
		}
		return count;		
	}
	
	/**
	 * Redirects to courses page.
	 */
	public String goToCourses() {
		logger.debug ("Starting goToCourses method");
		String toPage = "courses?faces-redirect=true";
	    this.findByFilter();
	    return toPage;
	}
	
	/**
	 * Redirects to add course page.
	 */
	public String goToAddCourse() {
	    String toPage = "updateCourse?faces-redirect=true";
	    logger.debug ("Starting goToAddCourse method");
	    resetForm ();
	    logger.debug ("courseIdForm " + this.courseIdForm);
	    return toPage;
	}
	
	/**
	 * Redirects to add class page.
	 */
	public String goToUpdateClass (Integer contentItemId, String numeration, String name, Integer classId) {
		logger.debug ("Starting goToAddClass method");
		String toPage = null;
		try {
			toPage = "updateClass?id="+ classId +"&courseId=" + this.courseIdForm 
				   + "&contentItemId=" + contentItemId 
				   + "&contentItemNumeration=" + URLEncoder.encode (numeration, "UTF-8")
			       +"&contentItemName=" + URLEncoder.encode (name, "UTF-8") +"&faces-redirect=true";
		} catch (UnsupportedEncodingException e) {
			String error = "An error occurred while encoding the URL. " + e.getMessage();
			logger.error (error);
		}
	    logger.debug ("courseIdForm " + this.courseIdForm);
	    if (this.courseIdForm == null || this.courseIdForm == 0) {
			super.addWarnMessage ("formUpdateCourse:messagesUpdateCourse", " É necessário salvar o Curso antes de adicionar ou atualizar Aula.");
			return null;
	    }
	    return toPage;
	}
	
	/**
	 * Performs the course save operation.
	 */
	public void save (ActionEvent actionEvent) {
		logger.debug ("Starting save.");

		if (isValidForm () && isValidContentItems ()) { 
			CourseVO courseVO = new CourseVO ();
			courseVO.setCourseId (courseIdForm);
			
			TeacherVO teacherVO = new TeacherVO();
			teacherVO.setTeacherId (teacherIdForm);
			
			courseVO.setTeacherVO (teacherVO);
			
			TeachingPlanVO teachingPlanVO = new TeachingPlanVO();
			teachingPlanVO.setTeachingPlanId (teachingPlanIdForm);
			teachingPlanVO.setWorkloadHoursTheoryType (workloadHoursTheoryTypeForm);
			teachingPlanVO.setWorkloadHoursPracticeType (workloadHoursPracticeTypeForm);
			teachingPlanVO.setSummary (summaryForm);
			teachingPlanVO.setGeneralGoal (generalGoalForm);
			teachingPlanVO.setSpecificGoals (specificGoalsForm);
			teachingPlanVO.setTeachingStrategyType (teachingStrategyTypeForm);
			teachingPlanVO.setResources (resourcesForm);
			teachingPlanVO.setEvaluation (evaluationForm);
			teachingPlanVO.setBibliography (bibliographyForm);
			updateContentItemVOList ();
			teachingPlanVO.setContentItemVOList (contentItemVOList);
			
			courseVO.setTeachingPlanVO (teachingPlanVO);
			
			courseVO.setCourseType (courseTypeForm);
			courseVO.setName (nameForm);
			int status = (statusForm) ? 1 : 0;
			courseVO.setStatus (status);
			try {
				if (this.courseIdForm != null && this.courseIdForm > 0) {
					int affectedRows = courseService.update (courseVO);
					if (affectedRows > 0) {
						super.addInfoMessage ("formUpdateCourse:messagesUpdateCourse", " Curso atualizado com sucesso.");
						logger.info ("The course [" + courseVO.getName() + "] has been successfully updated.");						
					}
				} else {					
					this.courseIdForm = courseService.insert (courseVO);
					if (this.courseIdForm > 0) {
						super.addInfoMessage ("formUpdateCourse:messagesUpdateCourse", " Curso adicionado com sucesso.");
						logger.info ("The course [" + courseVO.getName() + "] has been successfully inserted.");
					}
				}
				resetForm ();
			} catch (BusinessException e) {
				String error = "An error occurred while saving the course. " + e.getMessage();
				logger.error (error);
			}			
		}
	}
	
	/**
	 * Performs the course/teachingPlan delete operation.
	 */
	public void delete (ActionEvent actionEvent) {
		logger.debug ("Starting delete");
		if (this.courseIdForm != null && this.courseIdForm > 0) {
			CourseVO courseVO = new CourseVO ();
			courseVO.setCourseId (this.courseIdForm);
			courseVO.setName (this.nameForm);
			
			TeachingPlanVO teachingPlanVO = new TeachingPlanVO ();
			teachingPlanVO.setTeachingPlanId (teachingPlanIdForm);
			
			courseVO.setTeachingPlanVO (teachingPlanVO);
			try {
				int affectedRows = courseService.delete (courseVO);
				if (affectedRows > 0) {
					super.addInfoMessage ("formUpdateCourse:messagesUpdateCourse", " Curso excluído com sucesso.");
					logger.info ("The course [" + courseVO.getName() + "] has been successfully deleted.");
				}
			} catch (BusinessException e) {
				String error = "An error occurred while deleting the course. " + e.getMessage();
				logger.error (error);
			}
		}
		this.resetForm();
	}
	
	/**
	 * Loads a course given its id.
	 */
	public void loadById () {
		logger.debug ("Starting loadById");
		CourseVO foundCourseVO = null;
		if (this.courseIdForm != null && this.courseIdForm > 0) {
			
			CourseVO courseVO = new CourseVO ();
			courseVO.setCourseId (this.courseIdForm);
			
			TeachingPlanVO teachingPlanVO = new TeachingPlanVO();
			teachingPlanVO.setTeachingPlanId (teachingPlanIdForm);
			
			courseVO.setTeachingPlanVO (teachingPlanVO);
			
	        try {
	        	foundCourseVO = this.courseService.findById (courseVO);
	        	
	        	this.courseIdForm = foundCourseVO.getCourseId();
	        	this.teacherIdForm = foundCourseVO.getTeacherVO().getTeacherId();
	        	
	        	this.teachingPlanIdForm = foundCourseVO.getTeachingPlanVO().getTeachingPlanId();
	        	this.workloadHoursTheoryTypeForm = foundCourseVO.getTeachingPlanVO().getWorkloadHoursTheoryType();
	        	this.workloadHoursPracticeTypeForm = foundCourseVO.getTeachingPlanVO().getWorkloadHoursPracticeType();
	        	this.summaryForm = foundCourseVO.getTeachingPlanVO().getSummary();
	        	this.generalGoalForm = foundCourseVO.getTeachingPlanVO().getGeneralGoal();
	        	this.specificGoalsForm = foundCourseVO.getTeachingPlanVO().getSpecificGoals();
	        	this.teachingStrategyTypeForm = foundCourseVO.getTeachingPlanVO().getTeachingStrategyType();
	        	this.resourcesForm = foundCourseVO.getTeachingPlanVO().getResources();
	        	this.evaluationForm = foundCourseVO.getTeachingPlanVO().getEvaluation();
	        	this.bibliographyForm = foundCourseVO.getTeachingPlanVO().getBibliography();
	        	
	        	logger.debug ("isContentItemAddedOrDeleted: " + isContentItemAddedOrDeleted);
	        	if (!isContentItemAddedOrDeleted) {
	        		loadRoot (foundCourseVO.getTeachingPlanVO().getContentItemVOList());
	        		initContentItemSelect();
	        	}
	        	
				this.courseTypeForm = foundCourseVO.getCourseType();
				this.nameForm = foundCourseVO.getName();
				this.creationDateForm = foundCourseVO.getCreationDate();
				this.lastUpdateDateForm = foundCourseVO.getLastUpdateDate();
				this.statusForm = (foundCourseVO.getStatus() == 1) ? true : false;
								
				logger.debug ("foundCourseVO.getName() " + foundCourseVO.getName());
			} catch (BusinessException e) {
				String error = "An error occurred while finding the course/teachingPlan by id. " + e.getMessage();
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
		logger.debug ("Starting findByFilterListener().");
		findByFilter ();
		logger.debug ("Finishing findByFilterListener().");
	}
	
	/**
	 * Saves a new contentItem.
	 */
	@SuppressWarnings("unused")
	public void saveContentItemListener (ActionEvent event) {
		logger.debug ("Starting saveContentItemListener().");
		TreeNode nodeFather = null;
		if (isValidContentItemForm ()) {						
			
			if (Utils.isNonEmpty (contentItemNumerationFatherForm)) {
				nodeFather = getTreeNodeByNumeration (root, contentItemNumerationFatherForm);	
			}
			
			TeachingPlanVO teachingPlanVO = new TeachingPlanVO();
			teachingPlanVO.setTeachingPlanId (teachingPlanIdForm);
			
			ContentItemVO contentItemVO = new ContentItemVO ();
			contentItemVO.setTeachingPlanVO (teachingPlanVO);			
			contentItemVO.setNumeration (contentItemNumerationForm);
			contentItemVO.setName (contentItemNameForm);
			
			if (nodeFather != null) {
				ContentItemVO contentItemVOFather = (ContentItemVO) nodeFather.getData();
				contentItemVO.setContentItemFather (contentItemVOFather);
				TreeNode newNode = new DefaultTreeNode (contentItemVO, nodeFather);
			} else {
				TreeNode newNode = new DefaultTreeNode (contentItemVO, root);
			}
									
			super.addInfoMessage ("formUpdateCourse:messagesUpdateCourse", " Item adicionado com sucesso.");
			logger.info ("The contentItem [" + contentItemVO.getName() + "] has been successfully inserted.");
						
			printNodeNames ();
			initContentItemSelect();
			Utils.setExpandedRecursively (root, true);
			isContentItemAddedOrDeleted = true;
			resetContentItemForm ();
		}
		logger.debug ("Finishing saveContentItemListener().");
	}
	
	/**
	 * Deletes a contentItem given the contentItemId.
	 * 
	 * @param numeration the numeration.
	 */
	public void deleteContentItem (String numeration) {
		logger.debug ("Starting deleteContentItem().");
		logger.debug ("numeration to delete: " + numeration);
		TreeNode nodeToDelete = getTreeNodeByNumeration (root, numeration);
		boolean isDeleted = Utils.removeTreeNode (root, nodeToDelete);
		logger.debug ("isDeleted: " + isDeleted);
		printNodeNames ();
		initContentItemSelect();
		isContentItemAddedOrDeleted = true;
		logger.debug ("Finishing deleteContentItem().");
	}
	
	/**
	 * Checks if a contentItem can be associated to a class.
	 * 
	 * @param numeration the numeration.
	 * @param contentItemId the contentItemId.
	 * @return the operation result.
	 */
	public boolean isClassAssociated (String numeration, Integer contentItemId) {
		boolean success = false;
		TreeNode treeNode = getTreeNodeByNumeration (root, numeration);
		if (treeNode.getParent() != null
				&& treeNode.getParent().getParent() != null 
				&& contentItemId != null
				&& contentItemId > 0) {
			success = true;
		}
		return success;
	}
	
	/**
	 * Prints all the node names.
	 */
	private void printNodeNames () {
		logger.debug ("Starting method printNodeNames().");
		List<TreeNode> nodeList = Utils.getTreeNodeAsList (root, new ArrayList<TreeNode>());
		for (TreeNode treeNode : nodeList) {			
			ContentItemVO item = (ContentItemVO) treeNode.getData();
			logger.debug("contentItemVO.getName(): " + item.getName());
			logger.debug("contentItemVO.getNumeration(): " + item.getNumeration());
			if (item.getContentItemFather() != null) {
				logger.debug("contentItemFather().getName(): " + item.getContentItemFather().getName());
				logger.debug("contentItemFather().getNumeration(): " + item.getContentItemFather().getNumeration());
			}
		}
		logger.debug ("Finishing method printNodeNames().");
	}
	
	/**
	 * Gets a treeNode given its contentItem numeration.
	 * 
	 * @param node the treeNode. 
	 * @param numeration the numeration.
	 * @return the found treeNode.
	 */
	private TreeNode getTreeNodeByNumeration (TreeNode node, String numeration) {
		TreeNode foundTreeNode = null;
		List<TreeNode> nodeList = Utils.getTreeNodeAsList (root, new ArrayList<TreeNode>());
		for (TreeNode treeNode : nodeList) {
			ContentItemVO contentItemVO = (ContentItemVO) treeNode.getData();
			if (contentItemVO.getNumeration().equals(numeration.trim())) {
				foundTreeNode = treeNode;
				break;
			}
		}
        return foundTreeNode;
    }
	
	/**
	 * Updates the contentItemVO list with the last treeNode updates.
	 */
	private void updateContentItemVOList () {
		this.contentItemVOList = new ArrayList<ContentItemVO>();
		List<TreeNode> nodeList = Utils.getTreeNodeAsList (root, new ArrayList<TreeNode>());
		for (TreeNode treeNode : nodeList) {
			ContentItemVO contentItemVO = (ContentItemVO) treeNode.getData();
			if (treeNode.getParent() != null) {
				ContentItemVO contentItemFather = (ContentItemVO) treeNode.getParent().getData();
				contentItemVO.setContentItemFather(contentItemFather);
			}			
			contentItemVOList.add (contentItemVO);
		}		
		printNodeNames();		
	}
	
	/**
	 * loads the root treeNode.
	 */
	private void loadRoot (List<ContentItemVO> contentItemVOList) {		
		root = this.createContentItems (this.root, contentItemVOList);		
	}
	
	/**
	 * Gets the default treeNode root.
	 * 
	 * @return the default root.
	 */
	private TreeNode getDefaultRoot () {
		TreeNode root;
		ContentItemVO contentItemVO = new ContentItemVO();
		contentItemVO.setContentItemId (null);
		contentItemVO.setNumeration (null);
		contentItemVO.setName ("root");
		root = new DefaultTreeNode (contentItemVO, null);
		return root;
	}
	
	/**
	 * Create the treeNode contentItems.
	 * 
	 * @param root the treeNode root.
	 * @param contentItemVOList the contentItemVO list.
	 * 
	 * @return the treeNode of contentItems.
	 */
	private TreeNode createContentItems (TreeNode root, List<ContentItemVO> contentItemVOList) {
		List<ContentItemVO> contentItemVOListLevel1 = null;		
		root = getDefaultRoot ();		
		contentItemVOListLevel1 = getContentItemsLevel1 (contentItemVOList);
		for (ContentItemVO contentItemVOLevel1 : contentItemVOListLevel1) {
			TreeNode nodeLevel1 = new DefaultTreeNode (contentItemVOLevel1, root);			
			List<ContentItemVO> contentItemVOListLevel2 = getContentItemsByNumerationFather (contentItemVOLevel1.getNumeration(), contentItemVOList);			
			for (ContentItemVO contentItemVOLevel2 : contentItemVOListLevel2) {				
				TreeNode nodeLevel2 = new DefaultTreeNode (contentItemVOLevel2, nodeLevel1);				
				List<ContentItemVO> contentItemVOListLevel3 = getContentItemsByNumerationFather (contentItemVOLevel2.getNumeration(), contentItemVOList);				
				for (ContentItemVO contentItemVOLevel3 : contentItemVOListLevel3) {					
					TreeNode nodeLevel3 = new DefaultTreeNode (contentItemVOLevel3, nodeLevel2);
					List<ContentItemVO> contentItemVOListLevel4 = getContentItemsByNumerationFather (contentItemVOLevel3.getNumeration(), contentItemVOList);
					for (ContentItemVO contentItemVOLevel4 : contentItemVOListLevel4) {					
						TreeNode nodeLevel4 = new DefaultTreeNode (contentItemVOLevel4, nodeLevel3);
					}
				}
			}
		}		
		Utils.setExpandedRecursively (root, true);
		return root;
	}
	
	/**
	 * Gets the contentItems of level1.
	 * 
	 * @param contentItemVOList the contentItem list.
	 * @return a contentItemVO list.
	 */
	private List<ContentItemVO> getContentItemsLevel1 (List<ContentItemVO> contentItemVOList) {
		List<ContentItemVO> contentItemVOListLevel1 = new ArrayList<ContentItemVO>();		
		for (ContentItemVO contentItemVO : contentItemVOList) {			
			if (contentItemVO.getContentItemFather() == null) {				
				contentItemVOListLevel1.add (contentItemVO);				
			}
		}
		return contentItemVOListLevel1;
	}
	
	/**
	 * Gets the contentItems by numerationFather.
	 * 
	 * @param numerationFather the numeration father.
	 * @param contentItemVOList the contentItem list.
	 * @return a contentItemVO list.
	 */
	private List<ContentItemVO> getContentItemsByNumerationFather (String numerationFather, List<ContentItemVO> contentItemVOList) {
		List<ContentItemVO> contentItemVOListChildren = new ArrayList<ContentItemVO>();		
		for (ContentItemVO contentItemVO : contentItemVOList) {			
			if (contentItemVO.getContentItemFather() != null
					&& Utils.isNonEmpty (contentItemVO.getContentItemFather().getNumeration())
					&& contentItemVO.getContentItemFather().getNumeration().equals (numerationFather)) {				
				contentItemVOListChildren.add (contentItemVO);				
			}
		}		
		return contentItemVOListChildren;
	}
	
	/**
	 * Validates the search filter.
	 * 
	 * @return the operation result.
	 */
	private boolean isValidFilter() {
		boolean isValid = true;
		if (Utils.isNonEmpty (startCreationDateFilter) 
				&& Utils.isNonEmpty (endCreationDateFilter)) {			
			if (!Utils.isValidDateInterval (startCreationDateFilter, endCreationDateFilter)) {
				isValid = false;
				super.addWarnMessage ("messagesCourses", " Início Data de Criação é maior do que Fim Data de Criação.");
				logger.warn ("The start date is greater than the end date.");
			}			
		}
		return isValid;
	}
	
	/**
	 * Validates the course/teachingPlan insert/update form.
	 * 
	 */
	private boolean isValidForm () {
		if (!Utils.isNonEmpty (this.nameForm)
				|| (this.courseTypeForm == null || this.courseTypeForm == 0)
				|| (this.teacherIdForm == null || this.teacherIdForm == 0)
				|| this.workloadHoursTheoryTypeForm == 0
				|| this.workloadHoursPracticeTypeForm == 0
				|| !Utils.isNonEmpty (this.summaryForm)
				|| !Utils.isNonEmpty (this.generalGoalForm)
				|| !Utils.isNonEmpty (this.specificGoalsForm)
				|| this.teachingStrategyTypeForm == 0
				|| !Utils.isNonEmpty (this.resourcesForm)
				|| !Utils.isNonEmpty (this.evaluationForm)
				|| !Utils.isNonEmpty (this.bibliographyForm)
				|| (this.root == null || this.root.getChildCount() == 0)) {
			super.addWarnMessage ("formUpdateCourse:messagesUpdateCourse", " Preencha os campos corretamente.");
			return false;
		}
		return true;
    }
	
	/**
	 * Validates the contentItem insert form.
	 * 
	 */
	private boolean isValidContentItemForm () {
		if (!Utils.isNonEmpty (this.contentItemNumerationForm)
				|| !Utils.isNonEmpty (this.contentItemNameForm)) {
			super.addWarnMessage ("formUpdateCourse:messagesAddContentItem", " Preencha os campos corretamente.");
			return false;
		} else if (!Utils.isValidNumeration (contentItemNumerationForm)) {
			super.addWarnMessage ("formUpdateCourse:messagesAddContentItem", " Preencha a Numeração corretamente.");
			return false;
		} else if (!Utils.isNumerationWithinLimit (contentItemNumerationForm)) {
			super.addWarnMessage ("formUpdateCourse:messagesAddContentItem", " São permitidos até 4 níveis, por exemplo, 1.1.1.1.");
			return false;
		} else if (isDuplicatedNumeration (contentItemNumerationForm)) {
			super.addWarnMessage ("formUpdateCourse:messagesAddContentItem", " Numeração já existente, preencha corretamente.");
			return false;
		}
		
		return true;
    }
	
	/**
	 * Checks if all the contentItems are correct.
	 * @return
	 */
	private boolean isValidContentItems () {
		boolean success = true;
		List<TreeNode> nodeList = Utils.getTreeNodeAsList (root, new ArrayList<TreeNode>());
		for (TreeNode treeNode : nodeList) {
			ContentItemVO contentItemVO = (ContentItemVO) treeNode.getData();
			if (!Utils.isNonEmpty (contentItemVO.getNumeration()) 
					|| !Utils.isNonEmpty (contentItemVO.getName())) {
				super.addWarnMessage ("formUpdateCourse:messagesAddContentItem", " Item de Conteúdo incorreto (Numeração ou Nome), preencha corretamente.");
				success = false;
				break;
			}
		}
		return success;
	}
	
	/**
	 * Checks if the numeration is duplicated.
	 * 
	 * @param numeration the numeration.
	 * @return the operation result.
	 */
	private boolean isDuplicatedNumeration (String numeration) {
		boolean isDuplicated = false;
		List<TreeNode> nodeList = Utils.getTreeNodeAsList (root, new ArrayList<TreeNode>());
		for (TreeNode treeNode : nodeList) {
			ContentItemVO contentItemVO = (ContentItemVO) treeNode.getData();
			if (contentItemVO.getNumeration().equals (numeration.trim())) {
				isDuplicated = true;
				logger.debug("contentItem name: " + contentItemVO.getName());
				logger.debug("contentItem numeration: " + numeration);
				break;
			}
		}
        return isDuplicated;
	}
	
	/**
	 * Resets the insert/update form.
	 */
	private void resetForm () {
		this.courseIdForm = null;
		this.teacherIdForm = null;
		
		this.teachingPlanIdForm = null;
    	this.workloadHoursTheoryTypeForm = 0;
    	this.workloadHoursPracticeTypeForm = 0;
    	this.summaryForm = null;
    	this.generalGoalForm = null;
    	this.specificGoalsForm = null;
    	this.teachingStrategyTypeForm = 0;
    	this.resourcesForm = null;
    	this.evaluationForm = null;
    	this.bibliographyForm = null;
		
		this.nameForm = "";
		this.courseTypeForm = null;
		this.creationDateForm = null;
		this.lastUpdateDateForm = null;
		this.statusForm = true;
		
		if (root.getChildCount() > 0) {
			root.getChildren().clear();
		}
		
		isContentItemAddedOrDeleted = false;
	}
	
	/**
	 * Resets the contentItem insert form.
	 */
	private void resetContentItemForm () {
		contentItemIdForm = null;
		contentItemNumerationFatherForm = null;
		contentItemNumerationForm = null;
		contentItemNameForm = null;
	}
	
	/**
	 * Initializes the courseType select.
	 */
	private void initCourseTypeSelect() {
		this.courseTypeMap = new LinkedHashMap<String,Object>();
		courseTypeMap.put ("", ""); //label, value
		for (CourseTypeEnum courseType : CourseTypeEnum.values()) {			
			courseTypeMap.put (courseType.getName(), courseType.getId());						
		}		
	}
	
	/**
	 * Initializes the teacher select.
	 */
	private void initTeacherSelect() {
		List<TeacherVO> teacherVOList = null;
		teacherIdMap = new LinkedHashMap<String,Object>();
		teacherIdMap.put ("", ""); //label, value
		try {
			teacherVOList = this.teacherService.findAll();			
			if (teacherVOList != null && teacherVOList.size() > 0) {				
				for (TeacherVO teacherVO : teacherVOList) {
					teacherIdMap.put (teacherVO.getFullName(), teacherVO.getTeacherId());
				}
			}			
		} catch (BusinessException e) {
			String error = "An error occurred while finding all the teachers. " + e.getMessage();
			logger.error (error);
		}
	}
	
	/**
	 * Initializes the workloadHoursTheoryType select.
	 */
	private void initWorkloadHoursTheoryTypeSelect() {
		this.workloadHoursTheoryTypeMap = new LinkedHashMap<String,Object>();
		workloadHoursTheoryTypeMap.put ("", ""); //label, value
		for (WorkloadHoursTypeEnum workloadHoursType : WorkloadHoursTypeEnum.values()) {			
			workloadHoursTheoryTypeMap.put (workloadHoursType.getName(), workloadHoursType.getId());						
		}
	}
	
	/**
	 * Initializes the workloadHoursPracticeType select.
	 */
	private void initWorkloadHoursPracticeTypeSelect() {
		this.workloadHoursPracticeTypeMap = new LinkedHashMap<String,Object>();
		workloadHoursPracticeTypeMap.put ("", ""); //label, value
		for (WorkloadHoursTypeEnum workloadHoursType : WorkloadHoursTypeEnum.values()) {			
			workloadHoursPracticeTypeMap.put (workloadHoursType.getName(), workloadHoursType.getId());						
		}
	}
	
	/**
	 * Initializes the teachingStrategyType select.
	 */
	private void initTeachingStrategyTypeSelect() {
		this.teachingStrategyTypeMap = new LinkedHashMap<String,Object>();
		teachingStrategyTypeMap.put ("", ""); //label, value
		for (TeachingStrategyTypeEnum teachingStrategyType : TeachingStrategyTypeEnum.values()) {			
			teachingStrategyTypeMap.put (teachingStrategyType.getName(), teachingStrategyType.getId());						
		}
	}
	
	/**
	 * Initializes the contentItem select.
	 */
	private void initContentItemSelect() {
		this.contentItemMap = new LinkedHashMap<String,Object>();
		contentItemMap.put ("", ""); //label, value		
		List<TreeNode> nodeList = Utils.getTreeNodeAsList (root, new ArrayList<TreeNode>());		
		for (TreeNode treeNode : nodeList) {			
			ContentItemVO contentItemVO = (ContentItemVO) treeNode.getData();
			contentItemMap.put (contentItemVO.getName(), contentItemVO.getNumeration());
		}
	}
	
}
