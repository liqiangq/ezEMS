package com.thtf.dto;

import java.sql.Time;

import com.thtf.entity.BasicObject;

public class PowerRateDTO extends BasicObject {
	private static final long serialVersionUID = 1L;
	private Long id;//主键
	private Integer type;//类型:峰\谷\平\尖峰
	private Time begin;//每天的开始时间
	private Time end;//每天的结束时间
	private Double rate;//价格
	private Long planId;//所属计划的主键
	private String planName;//所属计划的名称
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Time getBegin() {
		return begin;
	}
	public void setBegin(Time begin) {
		this.begin = begin;
	}
	public Time getEnd() {
		return end;
	}
	public void setEnd(Time end) {
		this.end = end;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	public Long getPlanId() {
		return planId;
	}
	public void setPlanId(Long planId) {
		this.planId = planId;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	
}
