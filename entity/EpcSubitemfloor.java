package com.thtf.entity;


import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.OrderBy;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * EpcSubitemfloor entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "epc_subitemfloor", catalog = "epc")
public class EpcSubitemfloor extends BasicObject{

	// Fields
	private static final long serialVersionUID = 1L;
	private Long id;
	private EpcUnit epcUnit;
	private EpcSubitemfloor epcSubitemfloor;
	private String name;
	private String remark;
	private Double rule;
	private Double powerRule;


	private Set<BasItem> basItems = new HashSet<BasItem>(0);
	private Set<EpcSubitemfloor> epcSubitemfloors = new HashSet<EpcSubitemfloor>(
			0);
	private Set<EpcSubitemfloorAssist> epcSubitemfloorAssists = new HashSet<EpcSubitemfloorAssist>(0);
	// Constructors

	/** default constructor */
	public EpcSubitemfloor() {
	}

	/** full constructor */
	public EpcSubitemfloor(EpcUnit epcUnit, EpcSubitemfloor epcSubitemfloor,
			String name, String remark, Set<BasItem> basItems,
			Set<EpcSubitemfloor> epcSubitemfloors) {
		this.epcUnit = epcUnit;
		this.epcSubitemfloor = epcSubitemfloor;
		this.name = name;
		this.remark = remark;
		this.basItems = basItems;
		this.epcSubitemfloors = epcSubitemfloors;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UNIT_ID")
	public EpcUnit getEpcUnit() {
		return this.epcUnit;
	}

	public void setEpcUnit(EpcUnit epcUnit) {
		this.epcUnit = epcUnit;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ITEMFLOOR_ID")
	public EpcSubitemfloor getEpcSubitemfloor() {
		return this.epcSubitemfloor;
	}

	public void setEpcSubitemfloor(EpcSubitemfloor epcSubitemfloor) {
		this.epcSubitemfloor = epcSubitemfloor;
	}

	@Column(name = "NAME")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "REMARK")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@OneToMany(cascade = {CascadeType.REFRESH,CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE}, fetch = FetchType.LAZY, mappedBy = "epcSubitemfloor")
	@org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.ALL)
	public Set<BasItem> getBasItems() {
		return this.basItems;
	}

	public void setBasItems(Set<BasItem> basItems) {
		this.basItems = basItems;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "epcSubitemfloor")
	@OrderBy("id")
	public Set<EpcSubitemfloor> getEpcSubitemfloors() {
		return this.epcSubitemfloors;
	}

	public void setEpcSubitemfloors(Set<EpcSubitemfloor> epcSubitemfloors) {
		this.epcSubitemfloors = epcSubitemfloors;
	}

	public Double getRule() {
		return rule;
	}
	@Column(name = "RULE")
	public void setRule(Double rule) {
		this.rule = rule;
	}
	
	public Double getPowerRule() {
		return powerRule;
	}
	
	@Column(name = "POWERRULE")
	public void setPowerRule(Double powerRule) {
		this.powerRule = powerRule;
	}
	
//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "epcSubitemfloor")
	@OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE}, fetch = FetchType.LAZY, mappedBy = "epcSubitemfloor")
	public Set<EpcSubitemfloorAssist> getEpcSubitemfloorAssists() {
		return epcSubitemfloorAssists;
	}
	
	public void setEpcSubitemfloorAssists(
			Set<EpcSubitemfloorAssist> epcSubitemfloorAssists) {
		this.epcSubitemfloorAssists = epcSubitemfloorAssists;
	}

}