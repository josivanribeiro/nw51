package br.com.nw51.common.vo;

import br.com.nw51.common.util.ExerciseTypeEnum;

/**
 * Value Object class for Exercise.
 * 
 * @author Josivan Silva
 *
 */
public class ExerciseVO {
	
	private Integer exerciseId;
	private ClassVO classVO;
	private int exerciseType;
	private String name;
	private Integer sequenceNumber;
	
	public Integer getExerciseId() {
		return exerciseId;
	}
	public void setExerciseId(Integer exerciseId) {
		this.exerciseId = exerciseId;
	}
	public ClassVO getClassVO() {
		return classVO;
	}
	public void setClassVO(ClassVO classVO) {
		this.classVO = classVO;
	}
	public int getExerciseType() {
		return exerciseType;
	}
	public String getExerciseTypeName() {
		return ExerciseTypeEnum.getNameById (exerciseType);
	}
	public void setExerciseType(int exerciseType) {
		this.exerciseType = exerciseType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSequenceNumber() {
		return sequenceNumber;
	}
	public void setSequenceNumber(Integer sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}	
}
