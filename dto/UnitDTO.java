package com.thtf.dto;

import java.util.List;

import com.thtf.entity.BasicObject;

public class UnitDTO extends BasicObject {
	private static final long serialVersionUID = 1L;
	private Long id;//主键
	private String name;//单位名称
	private Integer area;//单位面积
	private String placeId; //低于编号
	private List<String> gateNames;//包含gate的名字的集合
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
	public Integer getArea() {
		return area;
	}
	public void setArea(Integer area) {
		this.area = area;
	}
	public List<String> getGateNames() {
		return gateNames;
	}
	public void setGateNames(List<String> gateNames) {
		this.gateNames = gateNames;
	}
	public String getPlaceId() {
		return placeId;
	}
	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}
	
}
