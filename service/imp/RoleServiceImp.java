package com.thtf.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.transaction.annotation.Transactional;

import com.thtf.dto.RoleDTO;
import com.thtf.entity.EpcResource;
import com.thtf.entity.EpcRole;
import com.thtf.service.ResourceService;
import com.thtf.service.RoleService;
import com.thtf.service.UnitService;

/**
 * @author 孙刚 E-mail:sungang01@thtf.com.cn
 * @version 创建时间：2010-9-2 下午02:03:23
 * 类说明
 */
@RemotingDestination
public class RoleServiceImp extends BasicServiceImp<EpcRole, Long> implements
		RoleService {
	
	private ResourceService resourceService;
	
	public ResourceService getResourceService() {
		return resourceService;
	}

	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	@Transactional
	@Override
	public Map<Long,String> getAll() {
		List<EpcRole> epcRoleTypeList = this.getAll("asc", "id");
		Map<Long,String> map = new HashMap<Long,String>();  
 		for(int i=0;i<epcRoleTypeList.size();i++){
 			EpcRole epcRole=epcRoleTypeList.get(i);
 			map.put(epcRole.getId(), epcRole.getName());
		}
		return map;
	}

	@Override
	public boolean deleteRole(Long id) {
		// TODO Auto-generated method stub
		deleteById(id);
		return true;
	}

	@Override
	public boolean saveRole(RoleDTO role) {
		// TODO Auto-generated method stub
		EpcRole epcRole = buildEpcRole(role);
		epcRole.setId(null);
		save(epcRole);
		return true;
	}

	@Override
	public boolean updateRole(RoleDTO role) {
		// TODO Auto-generated method stub
		EpcRole epcRole = buildEpcRole(role);
		
		update(epcRole);
		return true;
	}
	
	private EpcRole buildEpcRole(RoleDTO role){
		EpcRole epcRole = new EpcRole();
		epcRole.setName(role.getName());
		epcRole.setId(role.getId());
		List resourceIds = role.getResourceIds();
		List<EpcResource> resourecesList = new ArrayList<EpcResource>();
		
		for(int i = 0;i<resourceIds.size();i++){
			EpcResource resourece = resourceService.getById(Long.parseLong(resourceIds.get(i).toString()));
			List<EpcResource> epcResources=resourece.getEpcResources();
			resourece.setEpcResources(epcResources);
			Set<EpcRole>  epcRoleSet=resourece.getEpcRoles();
			resourece.setEpcRoles(epcRoleSet);
			resourecesList.add(resourece); 
		}
		epcRole.setEpcResources(resourecesList);
		return epcRole;
	}

	@Transactional
	@Override
	public List<RoleDTO> getAllList() {
		List<EpcRole> epcRoleTypeList = this.getAll("asc", "id");
		List<RoleDTO> roles = new ArrayList<RoleDTO>();  
			for(int i=0;i<epcRoleTypeList.size();i++){
				EpcRole epcRole=epcRoleTypeList.get(i);
				roles.add(buildDTO(epcRole));
		}
		return roles;
	}
	
	@Transactional
	@Override
	public boolean deleteRoles(List<String> ids){
		for (int i = 0; i < ids.size(); i++) {
			Long id = Long.parseLong(ids.get(i));
			this.deleteById(id);
		}
		return true;
	}
	
	private RoleDTO buildDTO(EpcRole role){
		RoleDTO roledto = new RoleDTO();
		roledto.setId(role.getId());
		roledto.setName(role.getName());
		//roledto.setResources(role.getEpcResources());
		List<Long> rid = new ArrayList<Long>();
		List<EpcResource> resources = role.getEpcResources();
		String resourceName = "";
		for(int i =0;i<resources.size();i++){
			EpcResource res = resources.get(i);
			rid.add(res.getId());
			if(res.getEpcResources().size()>0){
				resourceName +=res.getName()+"...";
			}
		}
		roledto.setResourceName(resourceName);
		roledto.setResourceIds(rid);
		return roledto;
	}
	
}
