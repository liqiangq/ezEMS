package com.thtf.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * EpcPowerPlan entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "epc_power_plan", catalog = "epc")
public class EpcPowerPlan extends BasicObject{

	// Fields
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private Date begin;
	private Date end;
	private EpcPlaceCode epcPlaceCode;
	private Set<EpcPowerRate> epcPowerRates = new HashSet<EpcPowerRate>(0);

	// Constructors

	/** default constructor */
	public EpcPowerPlan() {
	}

	/** full constructor */
	public EpcPowerPlan(String name, Date begin, Date end,
			Set<EpcPowerRate> epcPowerRates) {
		this.name = name;
		this.begin = begin;
		this.end = end;
		this.epcPowerRates = epcPowerRates;
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

	@Temporal(TemporalType.DATE)
	@Column(name = "BEGIN", length = 0)
	public Date getBegin() {
		return this.begin;
	}

	public void setBegin(Date begin) {
		this.begin = begin;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "END", length = 0)
	public Date getEnd() {
		return this.end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "epcPowerPlan")
	public Set<EpcPowerRate> getEpcPowerRates() {
		return this.epcPowerRates;
	}

	public void setEpcPowerRates(Set<EpcPowerRate> epcPowerRates) {
		this.epcPowerRates = epcPowerRates;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PLACE_ID")
	public EpcPlaceCode getEpcPlaceCode() {
		return epcPlaceCode;
	}

	public void setEpcPlaceCode(EpcPlaceCode epcPlaceCode) {
		this.epcPlaceCode = epcPlaceCode;
	}

}