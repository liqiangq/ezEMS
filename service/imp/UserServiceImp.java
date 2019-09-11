package com.thtf.service.imp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.transaction.annotation.Transactional;


import com.thtf.dto.ResourceDTO;
import com.thtf.dto.UnitDTO;
import com.thtf.dto.UserDTO;

import com.thtf.entity.BasGate;
import com.thtf.entity.EpcReport;
import com.thtf.entity.EpcResource;
import com.thtf.entity.EpcRole;
import com.thtf.entity.EpcUnit;
import com.thtf.entity.EpcUser;
import com.thtf.service.RoleService;
import com.thtf.service.UnitService;
import com.thtf.service.UserService;

/**
 * @author 孙刚 E-mail:sungang01@thtf.com.cn
 * @version 创建时间：2010-8-31 下午03:05:52 类说明
 */
@RemotingDestination
public class UserServiceImp extends BasicServiceImp<EpcUser, Long> implements
		UserService {

	private RoleService roleService;
	private UnitService unitService;

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public UnitService getUnitService() {
		return unitService;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	@Transactional
	@Override
	public List<UserDTO> findListUsers(UserDTO user) {
		// TODO Auto-generated method stub
		String jpql = "from EpcUser t where t.epcGroup.id=?";
		List<EpcUser> list = super.getByQuery(jpql,
				new Object[] { user.getId() });
		List<UserDTO> userList = new ArrayList<UserDTO>();
		for (EpcUser u : list) {
			userList.add(this.buildDTO(u));
		}
		return userList;
	}

	@Transactional
	@Override
	public UserDTO login(String loginName, String password) {
		// TODO Auto-generated method stub
		UserDTO userDTO = null;
		String jpql = "from EpcUser t where t.loginName=? and t.password=?";
		List<EpcUser> userList = this.getByQuery(jpql, new String[] {
				loginName, password });
		if (userList != null & userList.size() > 0) {
			EpcUser epcUser = userList.get(0);
			userDTO = new UserDTO();
			userDTO = this.buildDTO(epcUser);
		}
		return userDTO;
	}

	@Transactional
	@Override
	public boolean saveUser(UserDTO userDTO) {
		// TODO Auto-generated method stub
		EpcUser epcUser = this.buildEntity(userDTO);
		epcUser.setId(null);
		save(epcUser);
		return true;
	}

	@Transactional
	@Override
	public boolean updateUser(UserDTO userDTO) {
		// TODO Auto-generated method stub
		EpcUser epcUser = this.buildEntity(userDTO);
		update(epcUser);
		return true;
	}

	@Transactional
	@Override
	public boolean deleteUser(Long id) {
		// TODO Auto-generated method stub
		super.deleteById(id);
		return true;
	}

	@Transactional
	@Override
	public List<UserDTO> getAllList() {
		// TODO Auto-generated method stub
		List<UserDTO> userDTOList = new ArrayList<UserDTO>();
		List<EpcUser> epcUserList = this.getAll("asc", "id");
		for (int i = 0; i < epcUserList.size(); i++) {
			EpcUser epcUser = epcUserList.get(i);
			userDTOList.add(buildDTO(epcUser));
		}
		return userDTOList;
	}

	/**
	 * 组装DTO
	 * 
	 * @param epcUser
	 */
	private UserDTO buildDTO(EpcUser epcUser) {
		UserDTO userDTO = null;
		if (epcUser != null) {
			userDTO = new UserDTO();
			userDTO.setId(epcUser.getId());
			userDTO.setLoginName(epcUser.getLoginName());
			userDTO.setPassword(epcUser.getPassword());
			userDTO.setRoleId(epcUser.getEpcRole().getId());
			userDTO.setRoleName((roleService.getById(epcUser.getEpcRole().getId())).getName());
			List<EpcUnit> units = epcUser.getEpcUnits();
			List<UnitDTO> uns = new ArrayList<UnitDTO>();
			if (units != null) {
				List<Long> unitIds = new ArrayList<Long>();
				StringBuffer unitNames = new StringBuffer();;
				EpcUnit epcUtil = null;
				for (Iterator<EpcUnit> it = units.iterator(); it.hasNext();) {
					epcUtil = it.next();
					unitIds.add(epcUtil.getId());
					uns.add(this.buildDTO(epcUtil));
					unitNames.append(epcUtil.getName()+",");
				}
				
				userDTO.setUnits(uns);
				userDTO.setUnitId(unitIds);
				userDTO.setUnitName(unitNames.toString().substring(0, unitNames.length()-1));
			}

			EpcRole epcRole = epcUser.getEpcRole();
			if (epcRole != null) {
				List<EpcResource> set = epcRole.getEpcResources();
				List<ResourceDTO> resources = null;
				if (set != null) {
					resources = new ArrayList<ResourceDTO>();
					ResourceDTO re = null;
					for (EpcResource epcResource : set) {

						if(epcResource.getEpcResource()==null){
							re = new ResourceDTO();
							re.setCode(epcResource.getCode());
							re.setIcon(epcResource.getIcon());
							re.setId(epcResource.getId());
							re.setName(epcResource.getName());
							re.setType(epcResource.getType());
							re.setUrl(epcResource.getUrl());
							re.setChildren(this.buildDTO(epcResource.getEpcResources()));
							resources.add(re);
						}
					}
				}
				userDTO.setResources(resources);
			}
		}
		return userDTO;
	}
	private UnitDTO buildDTO(EpcUnit epcUnit) {

		UnitDTO unitDTO = null;
		if (epcUnit != null) {
			unitDTO = new UnitDTO();
			unitDTO.setId(epcUnit.getId());
			unitDTO.setArea(epcUnit.getArea());
			unitDTO.setName(epcUnit.getName());
			unitDTO.setPlaceId(epcUnit.getEpcPlaceCode().getCode());
			Set<BasGate> set = epcUnit.getBasGates();
			List<String> list = null;
			if (set != null && set.size() > 0) {
				list = new ArrayList<String>();
				for (BasGate e : set) {
					list.add(e.getName());
				}
			}
			unitDTO.setGateNames(list);
		}
		return unitDTO;
	}

	private EpcUser buildEntity(UserDTO userDTO) {
		EpcUser epcUser = null;
		if (userDTO != null) {
			epcUser = new EpcUser();
			epcUser.setId(userDTO.getId());
			epcUser.setLoginName(userDTO.getLoginName());
			epcUser.setPassword(userDTO.getPassword());
			epcUser.setEpcRole(roleService.getById(userDTO.getRoleId()));
			
			List unitIds = userDTO.getUnitId();
			if (unitIds != null) {
				List<EpcUnit> set = new ArrayList<EpcUnit>();
				for (int i=0;i<unitIds.size();i++) {
					Long l = Long.parseLong(unitIds.get(i).toString());
					set.add(unitService.getById(l));
				}
				epcUser.setEpcUnits(set);
				
			}

			epcUser.setEpcReports(null);
		}
		return epcUser;
	}
	private List<ResourceDTO> buildDTO(List<EpcResource> set) {
		List<ResourceDTO> resources = null;
		if (set != null) {
			resources = new ArrayList<ResourceDTO>();
			ResourceDTO re = null;
			for (EpcResource epcResource : set) {
				re = new ResourceDTO();
				re.setCode(epcResource.getCode());
				re.setIcon(epcResource.getIcon());
				re.setId(epcResource.getId());
				re.setName(epcResource.getName());
				re.setType(epcResource.getType());
				re.setUrl(epcResource.getUrl());
				re.setChildren(this.buildDTO(epcResource.getEpcResources()));
				resources.add(re);
			}
		}
		return resources;
	}

	@Transactional
	@Override
	public boolean deleteUsers(List<String> ids) {
		for (int i = 0; i < ids.size(); i++) {
			Long id = Long.parseLong(ids.get(i));			
			this.deleteById(id);
		}
		return true;
	}
}
