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
 * EpcSubitem entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "epc_subitem", catalog = "epc")
public class EpcSubitem extends BasicObject {

	// Fields
	private static final long serialVersionUID = 1L;
	private Long id;
	private EpcSubitem epcSubitem;
	private String name;
	private String remark;
	private Set<BasItem> basItems = new HashSet<BasItem>(0);
	private Set<EpcSubitem> epcSubitems = new HashSet<EpcSubitem>(0);
	private Set<EpcSubitemRule> epcSubitemRules = new HashSet<EpcSubitemRule>(0);

	// Constructors

	/** default constructor */
	public EpcSubitem() {
	}

	/** full constructor */
	public EpcSubitem(EpcSubitem epcSubitem, String name,
			String remark, Set<BasItem> basItems, Set<EpcSubitem> epcSubitems,Set<EpcSubitemRule> epcSubitemRules) {
		this.epcSubitem = epcSubitem;
		this.name = name;
		this.remark = remark;
		this.basItems = basItems;
		this.epcSubitems = epcSubitems;
		this.epcSubitemRules = epcSubitemRules;
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
	@JoinColumn(name = "SUBITEM_ID")
	public EpcSubitem getEpcSubitem() {
		return this.epcSubitem;
	}

	public void setEpcSubitem(EpcSubitem epcSubitem) {
		this.epcSubitem = epcSubitem;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "epcSubitem")
	public Set<BasItem> getBasItems() {
		return this.basItems;
	}

	public void setBasItems(Set<BasItem> basItems) {
		this.basItems = basItems;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "epcSubitem")
	@JoinColumn(name="SUBITEM_ID")
	@OrderBy("id")
	public Set<EpcSubitem> getEpcSubitems() {
		return this.epcSubitems;
	}

	public void setEpcSubitems(Set<EpcSubitem> epcSubitems) {
		this.epcSubitems = epcSubitems;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "epcSubitem")
	public Set<EpcSubitemRule> getEpcSubitemRules() {
		return epcSubitemRules;
	}

	public void setEpcSubitemRules(Set<EpcSubitemRule> epcSubitemRules) {
		this.epcSubitemRules = epcSubitemRules;
	}
	
	
}