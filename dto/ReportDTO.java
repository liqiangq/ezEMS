package com.thtf.dto;

import java.util.Date;
import java.util.List;

import com.thtf.entity.BasicObject;

public class ReportDTO extends BasicObject {
	private static final long serialVersionUID = 1L;
	private Long id;//主键
	private String name;//名称
	private Date date;//创建时间
	private String dateType;//时间类型：year\month\day
	private String reportType;//统计类型：pdf,xls
	private String remark;//描述
	private Long userId;//用户id
	private String email;
	private Long unitId;//单位id
	private String dataType;//数据类型：energy、rate、rule
	
	private List<String> itemIds; 

	private String itemType;//设备类型：subitem、subitemfloor、item、cur、vol
	private String sendType;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getDateType() {
		return dateType;
	}
	public void setDateType(String dateType) {
		this.dateType = dateType;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<String> getItemIds() {
		return itemIds;
	}
	public void setItemIds(List<String> itemIds) {
		this.itemIds = itemIds;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public String getSendType() {
		return sendType;
	}
	public void setSendType(String sendType) {
		this.sendType = sendType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getUnitId() {
		return unitId;
	}
	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
}
