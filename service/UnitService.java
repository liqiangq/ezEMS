package com.thtf.service;

import java.util.ArrayList;
import java.util.List;

import com.thtf.dto.PlaceCodeDTO;
import com.thtf.dto.UnitDTO;
import com.thtf.entity.EpcUnit;

/**
 * @author 孙刚 E-mail:sungang01@thtf.com.cn
 * @version 创建时间：2010-9-2 下午02:48:39
 * 类说明
 */
public interface UnitService extends BasicService<EpcUnit, Long> {

	/**
 * 获取 所有的Unit
 * 
 * @return
 */

   List<UnitDTO> getAll();
	/**
	 * 获取 GateName
	 * 
	 * @return
	 */
   List<String> findGateName();	
	/**
	 * 删除多个Unit
	 * @param id
	 * @return
	 */
	boolean deleteUnits(ArrayList  ids);
	
	/**
	 * 修改Unit
	 * @param user
	 * @return
	 */
	boolean updateUnit(UnitDTO unit);
	
	/**
	 * 保存设备类型
	 * @param subitemDTO
	 * @return
	 */
	boolean saveUnit(UnitDTO unit);
	
	boolean saveUnitAndPlace(UnitDTO unit,PlaceCodeDTO planCodeDTO);
	
	/**
	 * 得到用户未设置的单位List
	 * @param userID
	 * @return
	 */
	List<UnitDTO> findTree(Long userID);
}
