package com.thtf.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * EpcUnit entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "epc_unit", catalog = "epc")
public class EpcUnit extends BasicObject {

	// Fields
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private Integer area;
	private EpcPlaceCode  epcPlaceCode;
	private Set<BasGate> basGates = new HashSet<BasGate>(0);
	private Set<EpcSubitemRule> epcSubitemRules = new HashSet<EpcSubitemRule>(0);
	private Set<EpcSubitemfloor> epcSubitemfloors = new HashSet<EpcSubitemfloor>(
			0);
	private Set<EpcUser> epcUsers = new HashSet<EpcUser>(0);
	// Constructors

	/** default constructor */
	public EpcUnit() {
	}

	/** full constructor */
	public EpcUnit(String name, Integer area,EpcPlaceCode epcPlaceCode, Set<BasGate> basGates,
			Set<EpcSubitemRule> epcSubitemRules, Set<EpcSubitemfloor> epcSubitemfloors,
			Set<EpcUser> epcUsers) {
		this.name = name;
		this.area = area;
		this.epcPlaceCode = epcPlaceCode;
		this.basGates = basGates;
		this.epcSubitemRules = epcSubitemRules;
		this.epcSubitemfloors = epcSubitemfloors;
		this.epcUsers = epcUsers;
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

	@Column(name = "NAME")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "AREA")
	public Integer getArea() {
		return this.area;
	}

	public void setArea(Integer area) {
		this.area = area;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PLACE_ID")
	public EpcPlaceCode getEpcPlaceCode() {
		return epcPlaceCode;
	}

	public void setEpcPlaceCode(EpcPlaceCode epcPlaceCode) {
		this.epcPlaceCode = epcPlaceCode;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "epcUnit")
	public Set<BasGate> getBasGates() {
		return this.basGates;
	}

	public void setBasGates(Set<BasGate> basGates) {
		this.basGates = basGates;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "epcUnit")
	public Set<EpcSubitemRule> getEpcSubitemRules() {
		return epcSubitemRules;
	}

	public void setEpcSubitemRules(Set<EpcSubitemRule> epcSubitemRules) {
		this.epcSubitemRules = epcSubitemRules;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "epcUnit")
	public Set<EpcSubitemfloor> getEpcSubitemfloors() {
		return this.epcSubitemfloors;
	}

	public void setEpcSubitemfloors(Set<EpcSubitemfloor> epcSubitemfloors) {
		this.epcSubitemfloors = epcSubitemfloors;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "USER_UNIT",  
        joinColumns = {@JoinColumn(name = "UNIT_ID", referencedColumnName = "id")},  
        inverseJoinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "id")})  
	public Set<EpcUser> getEpcUsers() {
		return epcUsers;
	}

	public void setEpcUsers(Set<EpcUser> epcUsers) {
		this.epcUsers = epcUsers;
	}
	
}