package br.com.nw51.common.util;

/**
 * ExerciseType enum.
 * 
 * @author Josivan Silva
 *
 */
public enum ExerciseTypeEnum {

	EXERCICIO (3,"Exercício"), 
	DESAFIO (4, "Desafio");
	
	private int id;
	private String name;
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	private ExerciseTypeEnum (int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	/**
	 * Gets the name by id.
	 * 
	 * @param id the id.
	 * @return the name.
	 */
	public static String getNameById (int id) {
		String name = null;
		for (ExerciseTypeEnum exerciseType : ExerciseTypeEnum.values()) {			
			if (id == exerciseType.getId()) {
				name = exerciseType.getName();
				break;
			}			
		}
		return name;
	}
	
}
