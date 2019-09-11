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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * EpcResource entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "epc_resource", catalog = "epc")
public class EpcResource extends BasicObject {

	// Fields
	private static final long serialVersionUID = 1L;
	private Long id;
	private EpcResource epcResource;
	private String name;
	private String code;
	private String url;
	private String icon;
	private String type;
	private Set<EpcRole> epcRoles = new HashSet<EpcRole>(0);
	private List<EpcResource> epcResources = new ArrayList<EpcResource>(0);

	// Constructors

	/** default constructor */
	public EpcResource() {
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
	@JoinColumn(name = "RESOURCE_ID")
	public EpcResource getEpcResource() {
		return this.epcResource;
	}

	public void setEpcResource(EpcResource epcResource) {
		this.epcResource = epcResource;
	}

	@Column(name = "NAME")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "CODE")
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	public Set<EpcRole> getEpcRoles() {
		return this.epcRoles;
	}
	
	public void setEpcRoles(Set<EpcRole> epcRoles) {
		this.epcRoles = epcRoles;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "epcResource")
	@OrderBy("id")
	public List<EpcResource> getEpcResources() {
		return this.epcResources;
	}

	public void setEpcResources(List<EpcResource> epcResources) {
		this.epcResources = epcResources;
	}

}