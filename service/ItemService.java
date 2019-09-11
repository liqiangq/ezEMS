package com.thtf.service;

import java.util.List;
import java.util.Map;

import com.thtf.dto.BasItemDTO;
import com.thtf.entity.BasItem;

/**
 * @author 孙刚 E-mail:sungang01@thtf.com.cn
 * @version 创建时间：2010-9-2 下午04:37:48
 * 类说明
 */
public interface ItemService extends BasicService<BasItem, Long> {
	/**
	 * 得到设备结构,适用于结构树及树状列表
	 * @param unitId 所在单位
	 * @param itemTypeCode 设备类型,
	 * @param showChildren 是否显示子项,true-显示,false-不显示
	 * @return
	 */
	List<BasItemDTO> findTree(Long unitId,String itemTypeCode,boolean showChildren);
	/**
	 * 修改设备
	 * @param itemId 目标设备编号
	 * @param ids 挂载设备编号集合
	 * @return
	 */
	boolean updateItem(Long itemId,List<String> ids);


	/**
	 * 得到未被设置的设备，适用于分项、功能区、设备维护挂载
	 * @param unitId 所在单位
	 * @param itemTypeCode 设备类型,
	 * @param useType 用处：subitem-分项,subitemfloor-功能区,item-电表设置
	 * @return
	 */
	List<BasItemDTO> findTree(Long unitId,String itemTypeCode,String useType);
	
	List<BasItemDTO> findTreeSubItem(Long unitId, String itemTypeCode,String useType);
	/**
	 * 得到设备类型
	 * 
	 */
	Map getItemTypeMap();
	
	/**
	 * 修改电表类型
	 * @param subitemDTO
	 * @return
	 */
	boolean updateBasItem(BasItemDTO basItemDTO);
	/**
	 * 查找subitem下符合unitId的所有basitem的id
	 * @param subitemId
	 * @param unitId
	 * @return
	 */
	List<BasItem> findBasItems(List<Long> subitemIds,Long unitId);
	List<BasItem> findBasItems(Long subitemIds,Long unitId);
	/**
	 * 根据地区查找所有的BasItem
	 * @param code 地区编号
	 * @return
	 */
	List<BasItem> findBasItemsByPlaceCode(String code);
	
}
