package br.com.nw51.common.util;

/**
 * WorkloadHoursType enum.
 * 
 * @author Josivan Silva
 *
 */
public enum WorkloadHoursTypeEnum {
	
	WORKLOAD_2H (1,"2 Horas", 2),
	WORKLOAD_5H (2,"5 Horas", 5), 
	WORKLOAD_10H (3, "10 Horas", 10),
	WORKLOAD_15H (4, "15 Horas", 15),
	WORKLOAD_20H (5, "20 Horas", 20),
	WORKLOAD_25H (6, "25 Horas", 25),
	WORKLOAD_30H (7, "30 Horas", 30),
	WORKLOAD_35H (8, "35 Horas", 35),
	WORKLOAD_40H (9, "40 Horas", 40),
	WORKLOAD_45H (10, "45 Horas", 45),
	WORKLOAD_50H (11, "50 Horas", 50);
	
	private int id;
	private String name;
	private Integer hours;
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public Integer getHours() {
		return hours;
	}

	private WorkloadHoursTypeEnum (int id, String name, Integer hours) {
		this.id = id;
		this.name = name;
		this.hours = hours;
	}
	
	/**
	 * Gets the name by id.
	 * 
	 * @param id the id.
	 * @return the name.
	 */
	public static String getNameById (int id) {
		String name = null;
		for (WorkloadHoursTypeEnum workloadHoursType : WorkloadHoursTypeEnum.values()) {			
			if (id == workloadHoursType.getId()) {
				name = workloadHoursType.getName();
				break;
			}			
		}
		return name;
	}
	
	/**
	 * Gets the hours by id.
	 * 
	 * @param id the id.
	 * @return the hours.
	 */
	public static Integer getHoursById (int id) {
		Integer hours = 0;
		for (WorkloadHoursTypeEnum workloadHoursType : WorkloadHoursTypeEnum.values()) {			
			if (id == workloadHoursType.getId()) {
				hours = workloadHoursType.getHours();
				break;
			}			
		}
		return hours;
	}
	
}
