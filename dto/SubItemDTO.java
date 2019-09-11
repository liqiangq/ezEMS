package com.thtf.dto;

import java.util.List;

import com.thtf.entity.BasicObject;
/**
 * 用于EpcSubItem与EpcSubItemFloor两个对象的DTO模型
 * @author SunGang
 *
 */
public class SubItemDTO extends BasicObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;//主键
	private String name;//名称
	private String remark;//描述
	private Long unitId;
	private String unitName;
	private Long parentId;
	private List<BasItemDTO> basItems;//包含的bas的item集合
	private List<SubItemDTO> children;//子节点集合
	
	private Double energy; //总能耗
	private Double rate; //电费
	private Double rule;//能耗标尺
	private Double powerRule;//电费标尺
	
	
	public Double getPowerRule() {
		return powerRule;
	}
	public void setPowerRule(Double powerRule) {
		this.powerRule = powerRule;
	}
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<BasItemDTO> getBasItems() {
		return basItems;
	}
	public void setBasItems(List<BasItemDTO> basItems) {
		this.basItems = basItems;
	}
	public List<SubItemDTO> getChildren() {
		return children;
	}
	public void setChildren(List<SubItemDTO> children) {
		this.children = children;
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
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public Double getEnergy() {
		return energy;
	}
	public void setEnergy(Double energy) {
		this.energy = energy;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	public Double getRule() {
		return rule;
	}
	public void setRule(Double rule) {
		this.rule = rule;
	}
	
	
}
