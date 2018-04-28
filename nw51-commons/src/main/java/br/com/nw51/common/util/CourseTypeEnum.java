package br.com.nw51.common.util;

/**
 * CourseType enum.
 * 
 * @author Josivan Silva
 *
 */
public enum CourseTypeEnum {

	ARQUITETURA (1,"Arquitetura"), 
	MOBILE (2, "Mobile"),
	DEVELOPMENT (3, "Desenvolvimento"),
	INFRASTRUCTURE (4, "Infraestrutura"),
	AGILE_METHODS (5, "Métodos Ágeis"),
	DEVOPS (6, "DevOps"),
	DATA_SCIENCE (7, "Data Science");
	
	private int id;
	private String name;
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	private CourseTypeEnum (int id, String name) {
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
		for (CourseTypeEnum courseType : CourseTypeEnum.values()) {			
			if (id == courseType.getId()) {
				name = courseType.getName();
				break;
			}			
		}
		return name;
	}
	
}
