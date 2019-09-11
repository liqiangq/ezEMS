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
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * EpcRole entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "epc_role", catalog = "epc")
public class EpcRole extends BasicObject{

	// Fields
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private Set<EpcUser> epcUsers = new HashSet<EpcUser>(0);
	private List<EpcResource> epcResources = new ArrayList<EpcResource>(0);

	// Constructors

	/** default constructor */
	public EpcRole() {
	}

	/** full constructor */
	public EpcRole(String name, Set<EpcUser> epcUsers,
			List<EpcResource> epcResources) {
		this.name = name;
		this.epcUsers = epcUsers;
		this.epcResources = epcResources;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "epcRole")
	public Set<EpcUser> getEpcUsers() {
		return this.epcUsers;
	}

	public void setEpcUsers(Set<EpcUser> epcUsers) {
		this.epcUsers = epcUsers;
	}

	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinTable(name="EPC.ROLE_RESOURCE",joinColumns={@JoinColumn(name="ROLE_ID")},inverseJoinColumns={@JoinColumn(name="RESOURCE_ID")})
	@OrderBy("id")
	public List<EpcResource> getEpcResources() {
		return this.epcResources;
	}

	public void setEpcResources(List<EpcResource> epcResources) {
		this.epcResources = epcResources;
	}

}