package com.thtf.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * BasGate entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bas_gate", catalog = "epc")
public class BasGate extends BasicObject {

	// Fields
	private static final long serialVersionUID = 1L;
	private Long id;
	private EpcUnit epcUnit;
	private String name;
	private String gateDesc;
	private String projectId;
	private Set<BasItem> basItems = new HashSet<BasItem>(0);

	// Constructors

	/** default constructor */
	public BasGate() {
	}

	/** full constructor */
	public BasGate(EpcUnit epcUnit, String name, String gateDesc,
			Set<BasItem> basItems) {
		this.epcUnit = epcUnit;
		this.name = name;
		this.gateDesc = gateDesc;
		this.basItems = basItems;
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

	@Column(name = "NAME")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "GATE_DESC")
	public String getGateDesc() {
		return this.gateDesc;
	}

	public void setGateDesc(String gateDesc) {
		this.gateDesc = gateDesc;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "basGate")
	public Set<BasItem> getBasItems() {
		return this.basItems;
	}

	public void setBasItems(Set<BasItem> basItems) {
		this.basItems = basItems;
	}

	@Column(name = "PROJECTID")
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	
}