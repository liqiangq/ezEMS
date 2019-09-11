package com.thtf.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * EpcUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "epc_user", catalog = "epc")
public class EpcUser extends BasicObject {
	
	// Fields
	private static final long serialVersionUID = 1L;
	private Long id;
	private EpcRole epcRole;
	private String loginName;
	private String password;
	private Set<EpcReport> epcReports = new HashSet<EpcReport>(0);
	private List<EpcUnit> epcUnits = new ArrayList<EpcUnit>(0);
	// Constructors

	/** default constructor */
	public EpcUser() {
	}

	/** full constructor */
	public EpcUser( EpcRole epcRole,
			String loginName, String password, Set<EpcReport> epcReports,
			List<EpcUnit> epcUnits) {
//		this.epcGroup = epcGroup;
		this.epcRole = epcRole;
		this.loginName = loginName;
		this.password = password;
		this.epcReports = epcReports;
		this.epcUnits = epcUnits;
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
	@JoinColumn(name = "ROLE_ID")
	public EpcRole getEpcRole() {
		return this.epcRole;
	}

	public void setEpcRole(EpcRole epcRole) {
		this.epcRole = epcRole;
	}

	@Column(name = "LOGIN_NAME")
	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Column(name = "PASSWORD")
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "epcUser")
	public Set<EpcReport> getEpcReports() {
		return this.epcReports;
	}

	public void setEpcReports(Set<EpcReport> epcReports) {
		this.epcReports = epcReports;
	}

	@ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.REFRESH}, fetch = FetchType.LAZY)
	@JoinTable(name = "user_unit", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "unit_id")) 
	@OrderBy("id asc")
	public List<EpcUnit> getEpcUnits() {
		return epcUnits;
	}

	public void setEpcUnits(List<EpcUnit> epcUnits) {
		this.epcUnits = epcUnits;
	}

}