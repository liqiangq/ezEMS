package com.thtf.service;

import java.util.List;

import com.thtf.dto.SubItemDTO;
import com.thtf.entity.EpcSubitem;

/**
 * @author 孙刚 E-mail:sungang01@thtf.com.cn
 * @version 创建时间：2010-9-2 下午03:38:40
 * 类说明
 */
public interface SubitemService extends BasicService<EpcSubitem, Long> {
	
	/**
	 * 得到分项结构树，适用于拓扑图
	 * @param unitId
	 * @param date
	 * @param dateType
	 * @return
	 */
	List<SubItemDTO> findTree(Long unitId,String date,String dateType);
	/**
	 * 分项计量架构
	 * @param unitId
	 * @param date
	 * @param dateType
	 * @return
	 */
	List<SubItemDTO> findTreeGplot(Long unitId,String date,String dateType);
	/**
	 * 得到分项结构树，适用于树状图，树状列表
	 * @param unitId
	 * @return
	 */
	List<SubItemDTO> findTree(Long unitId);
	List<SubItemDTO> findTreeManage(Long unitId);
	/**
	 * 根据编号得到子节点的所有BasItem的name
	 * @param subitemId 分项编号
	 * @return unitId 单位编号
	 */
	List<String> findChildrenItems(Long subitemId,Long unitId); 
	/**
	 * 首页的功率能耗趋势图
	 * @param subitemId
	 * @param unitId
	 * @param gl 表示epc_subitem 表中的功率
	 * GLZ
	 * @return
	 */
	List<String> findChildrenItems(Long subitemId,Long unitId,String gl); 
//	List<String> findChildrenItems(Long id); 
	/**
	 * 保存分项
	 * @param subitemDTO
	 * @return
	 */
//	boolean saveSubitem(SubItemDTO subitemDTO);
	/**
	 * 修改分项
	 * @param subitemDTO
	 * @return
	 */
	boolean updateSubitem(SubItemDTO subitemDTO);
	
	/**
	 * 删除分项
	 * @param id
	 * @return
	 */
	public List<Long> findChildrenItems_R(Long subitemId, Long unitId) ;
//	boolean deleteSubitem(Long id);
	
//	List<String> getSubitemId(Long unitId) ;
}
