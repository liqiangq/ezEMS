package com.thtf.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * EpcStatVol entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "epc_stat_vol", catalog = "epc")
public class EpcStatVol extends BasicObject{

	// Fields
	private static final long serialVersionUID = 1L;
	private Long id;
	private String itemName;
	private String dateTime;
	private String value;
	private String max;
	private String min;
	private String error;

	// Constructors

	/** default constructor */
	public EpcStatVol() {
	}

	/** full constructor */
	public EpcStatVol(String itemName, String dateTime, String value,
			String max, String min) {
		this.itemName = itemName;
		this.dateTime = dateTime;
		this.value = value;
		this.max = max;
		this.min = min;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "ITEM_NAME")
	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Column(name = "DATE_TIME")
	public String getDateTime() {
		return this.dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	@Column(name = "VALUE")
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Column(name = "MAX")
	public String getMax() {
		return this.max;
	}

	public void setMax(String max) {
		this.max = max;
	}

	@Column(name = "MIN")
	public String getMin() {
		return this.min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	@Column(name = "ERROR")
	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}