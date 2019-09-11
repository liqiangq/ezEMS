package com.thtf.service;

import java.util.List;

import com.thtf.dto.SubItemDTO;
import com.thtf.dto.SubItemFloorAssistDTO;
import com.thtf.entity.EpcSubitemfloor;

/**
 * @author 孙刚 E-mail:sungang01@thtf.com.cn
 * @version 创建时间：2010-9-2 下午03:40:11
 * 类说明
 */
public interface SubitemfloorService extends BasicService<EpcSubitemfloor, Long> {
	
	
	/**
	 * 得到功能结构树，适用于拓扑图
	 * @param unitId
	 * @param date
	 * @param dateType
	 * @return
	 */
	List<SubItemDTO> findTree(Long unitId,String date,String dateType);
	/**
	 * 得到分项结构树，适用于树状图，树状列表
	 * @param unitId
	 * @return
	 */
	List<SubItemDTO> findTree(Long unitId);
	
	/**
	 * 根据编号得到子节点的所有BasItem的name
	 * @param id
	 * @return
	 */
	List<String> findChildrenItems(Long id); 
	
	/**
	 * 保存功能结构
	 * @param subitemDTO
	 * @return
	 */
	boolean saveSubitem(SubItemDTO subitemDTO);
	boolean saveSubItemFloorAssist(SubItemDTO subitemDTO,SubItemFloorAssistDTO subItemFloorAssistDTO);
	/**
	 * 修改功能结构
	 * @param subitemDTO
	 * @return
	 */
	boolean updateSubitem(SubItemDTO subitemDTO);
	boolean updateSubitemAssist(SubItemDTO subitemDTO,SubItemFloorAssistDTO subItemFloorAssistDTO);
	/**
	 * 删除功能结构
	 * @param id
	 * @return
	 */
	boolean deleteSubitem(Long id);
	
}
