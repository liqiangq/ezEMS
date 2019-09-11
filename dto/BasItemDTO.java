package com.thtf.dto;

import java.util.List;

import com.thtf.entity.BasicObject;

public class BasItemDTO extends BasicObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id; // 主键
	private String name; //bas中item的描述,item的名称
	private String description; //bas中basItemDesc
	private String itemTypeCode;//bas中item的类型
	private String itemTypeName;//bas中item的类型名称
	private List<BasItemDTO> children;//bas中item所包含的子设备,例如电流\电压等
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getItemTypeCode() {
		return itemTypeCode;
	}
	public String getItemTypeName() {
		return itemTypeName;
	}
	public void setItemTypeName(String itemTypeName) {
		this.itemTypeName = itemTypeName;
	}
	public void setItemTypeCode(String itemTypeCode) {
		this.itemTypeCode = itemTypeCode;
	}
	public List<BasItemDTO> getChildren() {
		return children;
	}
	public void setChildren(List<BasItemDTO> children) {
		this.children = children;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
