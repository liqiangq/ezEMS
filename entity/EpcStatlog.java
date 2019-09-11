package com.thtf.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * EpcStatlog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "epc_statlog", catalog = "epc")
public class EpcStatlog extends BasicObject{

	// Fields
	private static final long serialVersionUID = 1L;
	private Long id;
	private String tableName;
	private Integer type;
	private String insterTime;

	// Constructors

	/** default constructor */
	public EpcStatlog() {
	}

	/** full constructor */
	public EpcStatlog(String tableName, Integer type, String insterTime) {
		this.tableName = tableName;
		this.type = type;
		this.insterTime = insterTime;
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

	@Column(name = "TABLE_NAME")
	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Column(name = "TYPE")
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "INSTER_TIME")
	public String getInsterTime() {
		return this.insterTime;
	}

	public void setInsterTime(String insterTime) {
		this.insterTime = insterTime;
	}

}