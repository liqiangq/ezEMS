package com.thtf.entity;

import java.sql.Time;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * EpcPowerRate entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "epc_power_rate", catalog = "epc")
public class EpcPowerRate extends BasicObject{

	// Fields
	private static final long serialVersionUID = 1L;
	private Long id;
	private EpcPowerPlan epcPowerPlan;
	private Integer type;
	private Time begin;
	private Time end;
	private Double rate;

	// Constructors

	/** default constructor */
	public EpcPowerRate() {
	}

	/** full constructor */
	public EpcPowerRate(EpcPowerPlan epcPowerPlan, Integer type, Time begin,
			Time end, Double rate) {
		this.epcPowerPlan = epcPowerPlan;
		this.type = type;
		this.begin = begin;
		this.end = end;
		this.rate = rate;
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
	@JoinColumn(name = "PLAN_ID")
	public EpcPowerPlan getEpcPowerPlan() {
		return this.epcPowerPlan;
	}

	public void setEpcPowerPlan(EpcPowerPlan epcPowerPlan) {
		this.epcPowerPlan = epcPowerPlan;
	}

	@Column(name = "TYPE")
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "BEGIN", length = 0)
	public Time getBegin() {
		return this.begin;
	}

	public void setBegin(Time begin) {
		this.begin = begin;
	}

	@Column(name = "END", length = 0)
	public Time getEnd() {
		return this.end;
	}

	public void setEnd(Time end) {
		this.end = end;
	}

	@Column(name = "RATE", precision = 5)
	public Double getRate() {
		return this.rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

}