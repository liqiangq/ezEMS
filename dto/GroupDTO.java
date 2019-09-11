package com.thtf.dto;

import com.thtf.entity.BasicObject;

public class GroupDTO extends BasicObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;//主键
	private String name;//名称
	private Long unitId;//所在单位的主键
	private String unitName;//所在单位的名称
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
	public Long getUnitId() {
		return unitId;
	}
	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	
}
