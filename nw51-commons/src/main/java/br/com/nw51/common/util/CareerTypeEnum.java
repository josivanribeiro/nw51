package br.com.nw51.common.util;

/**
 * CareerType enum.
 * 
 * @author Josivan Silva
 *
 */
public enum CareerTypeEnum {

	MOBILE (1, "Mobile"),
	DEVELOPMENT (2, "Desenvolvimento"),
	INFRASTRUCTURE (3, "Infraestrutura"),
	DATA_SCIENCE (4, "Data Science");
	
	private int id;
	private String name;
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	private CareerTypeEnum (int id, String name) {
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
		for (CareerTypeEnum careerType : CareerTypeEnum.values()) {
			if (id == careerType.getId()) {
				name = careerType.getName();
				break;
			}
		}
		return name;
	}
	
}
