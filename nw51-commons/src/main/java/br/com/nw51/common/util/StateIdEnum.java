package br.com.nw51.common.util;

/**
 * StateId enum.
 * 
 * @author Josivan Silva
 *
 */
public enum StateIdEnum {

	ACRE (1,"Acre"), 
	ALAGOAS (2, "Alagoas"),
	AMAPA (3, "Amapá"),
	AMAZONAS (4, "Amazonas"),
	BAHIA (5, "Bahia"),
	CEARA (6, "Ceará"),
	DISTRITO_FEDERAL (7, "Distrito Federal"),
	ESPIRITO_SANTO (8, "Espírito Santo"),
	GOIAS (9, "Goiás"),
	MARANHAO (10, "Maranhão"),
	MATO_GROSSO (11, "Mato Grosso"),
	MATO_GROSSO_DO_SUL (12, "Mato Grosso do Sul"),
	MINAS_GERAIS (13, "Minas Gerais"),
	PARA (14, "Pará"),
	PARAIBA (15, "Paraíba"),
	PARANA (16, "Paraná"),
	PERNAMBUCO (17, "Pernambuco"),
	PIAUI (18, "Piauí"),
	RIO_DE_JANEIRO (19, "Rio de Janeiro"),
	RIO_GRANDE_DO_NORTE (20, "Rio Grande do Norte"),
	RIO_GRANDE_DO_SUL (21, "Rio Grande do Sul"),
	RONDONIA (22, "Rondônia"),
	RORAIMA (23, "Roraima"),
	SANTA_CATARINA (24, "Santa Catarina"),
	SAO_PAULO (25, "São Paulo"),
	SERGIPE (26, "Sergipe"),
	TOCANTINS (27, "Tocantins");
	
	private int id;
	private String name;
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	private StateIdEnum (int id, String name) {
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
		for (StateIdEnum item : StateIdEnum.values()) {			
			if (id == item.getId()) {
				name = item.getName();
				break;
			}
		}
		return name;
	}
	
}
