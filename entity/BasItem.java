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

import org.hibernate.annotations.Cascade;

/**
 * BasItem entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bas_item", catalog = "epc")
public class BasItem extends BasicObject{

	// Fields
	private static final long serialVersionUID = 1L;
	private Long id;
	private BasItem basItem;
	private EpcSubitemfloor epcSubitemfloor;
	private EpcItemType epcItemType;
	private BasGate basGate;
	private EpcSubitem epcSubitem;
	private String name;
	private String basItemDesc;
	private Set<BasItem> basItems = new HashSet<BasItem>(0);

	// Constructors

	/** default constructor */
	public BasItem() {
	}

	/** full constructor */
	public BasItem(BasItem basItem, EpcSubitemfloor epcSubitemfloor,
			EpcItemType epcItemType, BasGate basGate, EpcSubitem epcSubitem,
			String name, String basItemDesc, Set<BasItem> basItems) {
		this.basItem = basItem;
		this.epcSubitemfloor = epcSubitemfloor;
		this.epcItemType = epcItemType;
		this.basGate = basGate;
		this.epcSubitem = epcSubitem;
		this.name = name;
		this.basItemDesc = basItemDesc;
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
	@JoinColumn(name = "BASITEM_ID")
	public BasItem getBasItem() {
		return this.basItem;
	}

	public void setBasItem(BasItem basItem) {
		this.basItem = basItem;
	}

	@ManyToOne(fetch = FetchType.LAZY,cascade={CascadeType.REFRESH,CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE})
	@org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.ALL)
	@JoinColumn(name = "SUBITEMFLOOR_ID")
	public EpcSubitemfloor getEpcSubitemfloor() {
		return this.epcSubitemfloor;
	}

	public void setEpcSubitemfloor(EpcSubitemfloor epcSubitemfloor) {
		this.epcSubitemfloor = epcSubitemfloor;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ITEM_TYPE")
	public EpcItemType getEpcItemType() {
		return this.epcItemType;
	}

	public void setEpcItemType(EpcItemType epcItemType) {
		this.epcItemType = epcItemType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GATE_ID")
	public BasGate getBasGate() {
		return this.basGate;
	}

	public void setBasGate(BasGate basGate) {
		this.basGate = basGate;
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

	@Column(name = "BAS_ITEM_DESC")
	public String getBasItemDesc() {
		return this.basItemDesc;
	}

	public void setBasItemDesc(String basItemDesc) {
		this.basItemDesc = basItemDesc;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "basItem")
	public Set<BasItem> getBasItems() {
		return this.basItems;
	}

	public void setBasItems(Set<BasItem> basItems) {
		this.basItems = basItems;
	}

}