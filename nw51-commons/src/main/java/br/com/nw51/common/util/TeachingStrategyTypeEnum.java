package br.com.nw51.common.util;

/**
 * TeachingStrategyType enum.
 * 
 * @author Josivan Silva
 *
 */
public enum TeachingStrategyTypeEnum {
	
	AULA_EXPOSITIVA_DIALOGADA (1,"Aula Expositiva Dialogada"),
	AULA_EXPOSITIVA (2,"Aula Expositiva"), 
	AULA_DE_DEMONSTRACAO (3, "Aula de Demonstração"),
	AULA_PRATICA (4, "Aula Prática");
	
	private int id;
	private String name;
		
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}		

	private TeachingStrategyTypeEnum (int id, String name) {
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
		for (TeachingStrategyTypeEnum workloadHoursType : TeachingStrategyTypeEnum.values()) {			
			if (id == workloadHoursType.getId()) {
				name = workloadHoursType.getName();
				break;
			}			
		}
		return name;
	}
	
}
