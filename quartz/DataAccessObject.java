package com.thtf.quartz;

import com.thtf.entity.BasicObject;

public class DataAccessObject extends BasicObject{
	private static final long serialVersionUID = 1L;
	private Long id;
	private String gateId;
	private String timestamp;
	private String befTimestamp;
	private String aftTimestamp;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getGateId() {
		return gateId;
	}
	public void setGateId(String gateId) {
		this.gateId = gateId;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getBefTimestamp() {
		return befTimestamp;
	}
	public void setBefTimestamp(String befTimestamp) {
		this.befTimestamp = befTimestamp;
	}
	public String getAftTimestamp() {
		return aftTimestamp;
	}
	public void setAftTimestamp(String aftTimestamp) {
		this.aftTimestamp = aftTimestamp;
	}
	
}
