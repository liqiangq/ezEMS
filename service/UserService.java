package com.thtf.service;

import java.util.List;

import com.thtf.dto.UserDTO;
import com.thtf.entity.EpcUser;
/**
 * @author 孙刚 E-mail:sungang01@thtf.com.cn
 * @version 创建时间：2010-8-31 下午03:05:52
 * 类说明
 */
public interface UserService extends BasicService<EpcUser, Long> {
	/**
	 * 用户登录验证
	 * @param loginName
	 * @param password
	 * @return dto模型
	 */
	UserDTO login(String loginName,String password);
	
	/**
	 * 保存用户
	 * @param user
	 * @return
	 */
	boolean saveUser(UserDTO user);
	
	/**
	 * 根据用户权限，显示用户列表
	 * @param user
	 * @return
	 */
	List<UserDTO> findListUsers(UserDTO user);
	
	/**
	 * 修改用户
	 * @param user
	 * @return
	 */
	boolean updateUser(UserDTO user);
	
	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	boolean deleteUser(Long id);

	/**
	 * 获取 所有的用户
	 * @param id
	 * @return
	 */
	
	List<UserDTO> getAllList() ;
	
	/**
	 * 删除多个用户
	 * @param id
	 * @return
	 */
	boolean deleteUsers(List<String> ids);
	
	
}
