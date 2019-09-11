package com.thtf.dto;

import java.util.List;

import com.thtf.entity.BasicObject;
import com.thtf.entity.EpcUnit;

/**
 * User的业务模型
 * 
 * @author SunGang
 * 
 */
public class UserDTO extends BasicObject{
	private static final long serialVersionUID = 1L;
	private Long id;
	private String loginName; // 登录名
	private String password; // 密码
	private List<Long> unitId; // 单位编号
	private List<UnitDTO> units; // 单位
	private String unitName; // 单位编号
	private Long roleId;
	private String roleName;
	private List<ResourceDTO> resources; // 资源编号
	
	
	public List<UnitDTO> getUnits() {
		return units;
	}

	public void setUnits(List<UnitDTO> units) {
		this.units = units;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Long> getUnitId() {
		return unitId;
	}

	public void setUnitId(List<Long> unitId) {
		this.unitId = unitId;
	}

	public List<ResourceDTO> getResources() {
		return resources;
	}

	public void setResources(List<ResourceDTO> resources) {
		this.resources = resources;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

}
