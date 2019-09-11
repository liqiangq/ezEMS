package com.thtf.service;

import java.util.List;

import com.thtf.dto.UnitDTO;

public interface ChartService extends BasicService{
	
	/**
	 * 
	 * @param date 时间  精确到日
	 * @param dateType 时间类型 year month day
	 * @param ids 设备编号
	 * @param chartType 统计图类型 bar pie line
	 * @param idsType 编号类型：subitem-分项,subitemfloor-功能区,item-设备,cur-电压,vol-电流
	 * @param unitId 单位编号
	 * @return
	 */

	String getChartData(String date,String dateType,String dataType,List<String> ids,String chartType,String idsType,Long unitId);
	
	/**
	 * 分项能耗对比-单位对比
	 * @param date 时间
	 * @param dateType 时间类型
	 * @param chartType 统计图类型
	 * @param subitemId 对比的分项id
	 * @param units 单位集合
	 * @return
	 */
	String getChartData(String date,String dateType,String dataType,String chartType,Long subitemId,List<UnitDTO> units);
	

}
