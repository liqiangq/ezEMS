package com.thtf.service;


import com.thtf.dto.SubItemFloorAssistDTO;
import com.thtf.entity.EpcSubitemfloorAssist;

public interface SubitemfloorAssistService extends BasicService<EpcSubitemfloorAssist, Long> {
	/**
	 * 得到功能结构树，适用于拓扑图
	 * @param unitId
	 * @param date
	 * @param dateType
	 * @return
	 */
	//List<SubItemDTO> findTree(Long unitId,String date,String dateType);
	/**
	 * 得到分项结构树，适用于树状图，树状列表
	 * @param unitId
	 * @return
	 */
	//List<SubItemDTO> findTree(Long unitId);
	/**
	 * 根据编号得到子节点的所有BasItem的name
	 * @param id
	 * @return
	 */
	//List<String> findChildrenItems(Long id); 
	
	/**
	 * 保存功能结构
	 * @param subitemDTO
	 * @return
	 */
	boolean saveSubitemAssist(SubItemFloorAssistDTO subitemFloorAssistDTO);
	/**
	 * 
	 * @param subFloorId
	 * @return
	 */
	SubItemFloorAssistDTO subItemFloorAssist(Long subFloorId);
	
	/**
	 * 修改功能结构
	 * @param subitemDTO
	 * @return
	 */
	boolean updateSubitem(SubItemFloorAssistDTO subitemFloorAssistDTO);
	
	/**
	 * 删除功能结构
	 * @param id
	 * @return
	 */
	boolean deleteSubitemfloorAssist(Long id);
}
