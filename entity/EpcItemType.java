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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * EpcItemType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "epc_item_type", catalog = "epc")
public class EpcItemType extends BasicObject{

	// Fields
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String code;
	private Set<BasItem> basItems = new HashSet<BasItem>(0);

	// Constructors

	/** default constructor */
	public EpcItemType() {
	}

	/** full constructor */
	public EpcItemType(String name, Set<BasItem> basItems) {
		this.name = name;
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

	@Column(name = "NAME")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "epcItemType")
	public Set<BasItem> getBasItems() {
		return this.basItems;
	}

	public void setBasItems(Set<BasItem> basItems) {
		this.basItems = basItems;
	}
	@Column(name = "CODE")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}