package com.thtf.dto;

import java.util.Date;

import com.thtf.entity.BasicObject;

public class PowerPlanDTO extends BasicObject {
	private static final long serialVersionUID = 1L;
	private Long id;//主键
	private String name;//名称
	private Date begin;//开始时间
	private Date end;//结束时间
	private String placeid;
	private String placeName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPlaceName() {
		return placeName;
	}
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBegin() {
		return begin;
	}
	public void setBegin(Date begin) {
		this.begin = begin;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public String getPlaceid() {
		return placeid;
	}
	public void setPlaceid(String placeid) {
		this.placeid = placeid;
	}
	
}
