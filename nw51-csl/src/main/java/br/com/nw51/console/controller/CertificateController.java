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
import br.com.nw51.common.vo.CertificateFilterVO;
import br.com.nw51.common.vo.CertificateVO;
import br.com.nw51.common.vo.CourseFilterVO;
import br.com.nw51.common.vo.CourseVO;
import br.com.nw51.console.services.BusinessException;
import br.com.nw51.console.services.CertificateService;
import br.com.nw51.console.services.CourseService;

/**
 * Certificate Controller.
 * 
 * @author Josivan Silva
 *
 */
@ManagedBean
@RequestScoped
public class CertificateController extends AbstractController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger (CertificateController.class.getName());
	private CertificateService certificateService = new CertificateService();
	private CourseService courseService = new CourseService();
	
	private Long courseIdFilter;
	private String cpfFilter;
	private String startCreationDateFilter;
	private String endCreationDateFilter;
	
	private List<CertificateVO> certificateVOList;
	
	private Long certificateIdForm;
	private String courseNameForm;
	private String studentNameForm;
	private Date creationDateForm;
		
	private Map<String,Object> courseMap;
	
	public CertificateService getCertificateService() {
		return certificateService;
	}
	public void setCertificateService(CertificateService certificateService) {
		this.certificateService = certificateService;
	}
	public Long getCourseIdFilter() {
		return courseIdFilter;
	}
	public void setCourseIdFilter(Long courseIdFilter) {
		this.courseIdFilter = courseIdFilter;
	}
	public String getCpfFilter() {
		return cpfFilter;
	}
	public void setCpfFilter(String cpfFilter) {
		this.cpfFilter = cpfFilter;
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
	public List<CertificateVO> getCertificateVOList() {
		return certificateVOList;
	}
	public void setCertificateVOList(List<CertificateVO> certificateVOList) {
		this.certificateVOList = certificateVOList;
	}
	public Long getCertificateIdForm() {
		return certificateIdForm;
	}
	public void setCertificateIdForm(Long certificateIdForm) {
		this.certificateIdForm = certificateIdForm;
	}
	public String getCourseNameForm() {
		return courseNameForm;
	}
	public void setCourseNameForm(String courseNameForm) {
		this.courseNameForm = courseNameForm;
	}
	public String getStudentNameForm() {
		return studentNameForm;
	}
	public void setStudentNameForm(String studentNameForm) {
		this.studentNameForm = studentNameForm;
	}
	public Date getCreationDateForm() {
		return creationDateForm;
	}
	public void setCreationDateForm(Date creationDateForm) {
		this.creationDateForm = creationDateForm;
	}
	public Map<String, Object> getCourseMap() {
		return courseMap;
	}
	public void setCourseMap(Map<String, Object> courseMap) {
		this.courseMap = courseMap;
	}

	@PostConstruct
    public void init() {
		initCourseSelect();
    }	
	
	/**
	 * Finds the certificates by filter.
	 */
	public void findByFilter () {
		CertificateFilterVO certificateFilterVO = new CertificateFilterVO();
		certificateVOList = new ArrayList<CertificateVO>();
		if (isValidFilter()) {
			certificateFilterVO.setCourseId (courseIdFilter);
			certificateFilterVO.setCpf (cpfFilter);
			certificateFilterVO.setStartDate (this.startCreationDateFilter);
			certificateFilterVO.setEndDate (this.endCreationDateFilter);
			try {
				certificateVOList = certificateService.findByFilter (certificateFilterVO);
				logger.debug ("certificateVOList.size() [" + certificateVOList.size() + "]");
			} catch (BusinessException e) {
				String error = "An error occurred while find the courses by filter. " + e.getMessage();
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
		if (Utils.isNonEmpty (startCreationDateFilter) 
				&& Utils.isNonEmpty (endCreationDateFilter)) {			
			if (!Utils.isValidDateInterval (startCreationDateFilter, endCreationDateFilter)) {
				isValid = false;
				super.addWarnMessage ("messagesCertificates"," Início Data de Criação é maior do que Fim Data de Criação.");
				logger.warn ("The start date is greater than the end date.");
			}
		}  else if (Utils.isNonEmpty (cpfFilter)) {
			String cpf = Utils.removeCpfMask (cpfFilter);
			if (!Utils.isCPF (cpf)) {
				isValid = false;
				super.addWarnMessage ("messagesCertificates", " CPF inválido.");
				logger.warn ("Invalid CPF.");
			}
		}
		return isValid;
	}

	/**
	 * Gets the count of certificates.
	 */
	public int getCertificatesCount() { 
		int count = 0;
		try {
			count = certificateService.rowCount();
		} catch (BusinessException e) {
			String error = "An error occurred while getting the row count of certificates. " + e.getMessage();
			logger.error (error);
		}
		return count;
	}
	
	/**
	 * Redirects to certificates page.
	 */
	public String goToCertificates() {
		logger.info ("\nStarting goToCertificates method");
		String toPage = "certificates?faces-redirect=true";
	    this.findByFilter();
	    return toPage;
	}
	
	/**
	 * Performs the certificate save operation.
	 */
	public void save (CertificateVO certificateVO) {
		try {									
			this.certificateIdForm = certificateService.insert (certificateVO);
			if (this.certificateIdForm != null && this.certificateIdForm > 0) {
				logger.info ("The certificate with id [" + this.certificateIdForm + "] has been successfully inserted.");
			}
		} catch (BusinessException e) {
			String error = "An error occurred while saving the certificate. " + e.getMessage();
			logger.error (error);
		}
	}
	
	/**
	 * Loads a certificate given its id.
	 */
	public void loadById () {
		logger.debug ("Starting loadById");
		CertificateVO foundCertificateVO = null;
		if (this.certificateIdForm != null && this.certificateIdForm > 0) {
			CertificateVO certificateVO = new CertificateVO ();
			certificateVO.setCertificateId (this.certificateIdForm);
	        try {
	        	foundCertificateVO = this.certificateService.findById (certificateVO);
	        	
	        	this.certificateIdForm = foundCertificateVO.getCertificateId();
	        	this.courseNameForm    = foundCertificateVO.getStudentCourseVO().getCourseVO().getName();
	        	this.studentNameForm   = foundCertificateVO.getStudentCourseVO().getStudentVO().getFullName();
	        	this.creationDateForm  = foundCertificateVO.getCreationDate();
	        	logger.debug ("this.certificateIdForm " + this.certificateIdForm);				
			} catch (BusinessException e) {
				String error = "An error occurred while finding the certificate by id. " + e.getMessage();
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
		logger.info ("Start executing the method findByFilterListener().");
		findByFilter ();
		logger.info ("Finish executing the method findByFilterListener().");
	}
	
	/**
	 * Initializes the courseType select.
	 */
	private void initCourseSelect() {
		List<CourseVO> courseVOList = null;
		this.courseMap = new LinkedHashMap<String,Object>();
		courseMap.put ("", ""); //label, value		
		try {
			courseVOList = courseService.findByFilter (new CourseFilterVO());
		} catch (BusinessException e) {
			String error = "An error occurred while find the courses by filter. " + e.getMessage();
			logger.error (error);
		}		
		if (courseVOList != null && courseVOList.size() > 0) {
			for (CourseVO courseVO : courseVOList) {
				courseMap.put (courseVO.getName(), courseVO.getCourseId());
			}
		}				
	}
	
}
