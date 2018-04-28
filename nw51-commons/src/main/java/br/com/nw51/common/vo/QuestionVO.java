package br.com.nw51.common.vo;

/**
 * Value Object class for Question.
 * 
 * @author Josivan Silva
 *
 */

public class QuestionVO {
	
	private Integer questionId;
	private ExerciseVO exerciseVO;
	private String enunciation;
	private String alternativeA;
	private String alternativeB;
	private String alternativeC;
	private String alternativeD;
	private String alternativeE;
	private String alternativeCorrect;
	
	public Integer getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}
	public ExerciseVO getExerciseVO() {
		return exerciseVO;
	}
	public void setExerciseVO(ExerciseVO exerciseVO) {
		this.exerciseVO = exerciseVO;
	}
	public String getEnunciation() {
		return enunciation;
	}
	public String getShortEnunciation() {
		if (enunciation != null && enunciation.length() > 70) {
			return enunciation.substring (0, 70) + "...";
		} else {
			return enunciation;
		}		
	}
	public void setEnunciation(String enunciation) {
		this.enunciation = enunciation;
	}
	public String getAlternativeA() {
		return alternativeA;
	}
	public void setAlternativeA(String alternativeA) {
		this.alternativeA = alternativeA;
	}
	public String getAlternativeB() {
		return alternativeB;
	}
	public void setAlternativeB(String alternativeB) {
		this.alternativeB = alternativeB;
	}
	public String getAlternativeC() {
		return alternativeC;
	}
	public void setAlternativeC(String alternativeC) {
		this.alternativeC = alternativeC;
	}
	public String getAlternativeD() {
		return alternativeD;
	}
	public void setAlternativeD(String alternativeD) {
		this.alternativeD = alternativeD;
	}
	public String getAlternativeE() {
		return alternativeE;
	}
	public void setAlternativeE(String alternativeE) {
		this.alternativeE = alternativeE;
	}
	public String getAlternativeCorrect() {
		return alternativeCorrect;
	}
	public void setAlternativeCorrect(String alternativeCorrect) {
		this.alternativeCorrect = alternativeCorrect;
	}	
}
