package com.thtf.service;

import java.util.List;
import java.util.Map;

import com.thtf.dto.ReportDTO;
import com.thtf.entity.EpcReport;

/**
 * @author 孙刚 E-mail:sungang01@thtf.com.cn
 * @version 创建时间：2010-9-12 上午10:09:38
 * 类说明
 */
public interface ReportService extends BasicService<EpcReport,Long> {
	/**
	 * 报表
	 * @param date 统计时间
	 * @param dateType 时间类型：year-month-day
	 * @param itemType 编号类型：subitem-item-cur-vol-subitemfloor
	 * @param ids 编号集合
	 * @param rateType 查询类型：energy-rate-rule
	 * @param unitId 单位编号
	 * @return 报表的数据集合
	 */
	List<Map<String,String>> report(String date,String dateType,String itemType,String rateType,List<String> ids,Long unitId);

	/**
	 * 删除报表计划
	 * @param id 报表计划id
	 * @return 是否成功
	 */
	boolean deleteEpcReport(Long id);

	/**
	 * 获取 所有的报表
	 * @param userId 用户编号
	 * @return DTO集合
	 */
	
	List<ReportDTO> getAllList(Long userId) ;
	
	/**
	 * 删除多个报表计划
	 * @param id 报表编号
	 * @return 是否成功
	 */
	boolean deleteEpcReports(Long id);
	
	/**
	 * 保存报表计划
	 * @param report DTO
	 * @return 是否成功
	 */
	boolean saveEpcReport(ReportDTO report);
	/**
	 * 修改报表计划
	 * @param report DTO
	 * @return
	 */
	boolean updateEpcReport(ReportDTO report);
}
