package com.thtf.service;

import java.util.List;
import java.util.Map;

import com.thtf.dto.RoleDTO;
import com.thtf.entity.EpcRole;

/**
 * @author 孙刚 E-mail:sungang01@thtf.com.cn
 * @version 创建时间：2010-9-2 下午01:25:53
 * 类说明
 */
public interface RoleService extends BasicService<EpcRole, Long> {
	Map<Long,String> getAll() ;
	List<RoleDTO> getAllList() ;
	boolean saveRole(RoleDTO role);
	boolean deleteRole(Long id);
	boolean updateRole(RoleDTO role);
	boolean deleteRoles(List<String> ids);
}
