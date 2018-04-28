package br.com.nw51.common.vo;

/**
 * Value Object class for Tool.
 * 
 * @author Josivan Silva
 *
 */
public class ToolVO {
	
	private Integer toolId;
	private String name;
	private boolean selected;
	
	public Integer getToolId() {
		return toolId;
	}
	public void setToolId(Integer toolId) {
		this.toolId = toolId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}		
}
