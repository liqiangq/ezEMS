package com.thtf.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "epc_place_code", catalog = "epc")
public class EpcPlaceCode  extends BasicObject {
	// Fields
	private static final long serialVersionUID = 1L;
	private String code;
	private String name;
	
	public EpcPlaceCode(String code,String name){
		this.code = code;
		this.name = name;
	}
	public EpcPlaceCode(){
	}

	@Id
	@Column(name = "CODE", unique = true, nullable = false)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Column(name = "NAME")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
