package com.thtf.service.imp;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.transaction.annotation.Transactional;

import com.thtf.dto.ReportDTO;
import com.thtf.entity.EpcReport;
import com.thtf.entity.EpcUser;
import com.thtf.service.ReportService;
import com.thtf.service.SubitemService;
import com.thtf.service.SubitemfloorService;
import com.thtf.service.UserService;
import com.thtf.util.DateUtil;
import com.thtf.util.StringUtil;

/**
 * @author 孙刚 E-mail:sungang01@thtf.com.cn
 * @version 创建时间：2010-9-12 上午10:10:26 类说明
 */
@RemotingDestination
public class ReportServiceImp extends BasicServiceImp<EpcReport, Long>
		implements ReportService {

	private SubitemfloorService subitemfloorService;
	private SubitemService subitemService;
	private UserService userService;

	public void setSubitemfloorService(SubitemfloorService subitemfloorService) {
		this.subitemfloorService = subitemfloorService;
	}

	public void setSubitemService(SubitemService subitemService) {
		this.subitemService = subitemService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Transactional
	@Override
	public List<Map<String, String>> report(String date, String dateType,
			String itemType,String rateType, List<String> ids ,Long unitId) {
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();

		if (StringUtil.isNull(date)) {
			date = DateUtil.date2String(new Date(), "yyyy-MM-dd");
		}
		if (StringUtil.isNull(dateType)) {
			dateType = "day";
		}
		if (StringUtil.isNull(itemType)) {
			itemType = "item";
		}

		// reportType 报表类型：subitem-item-cur-vol-subitemfloor
		List<String> itemIds = null;
		boolean isShowName = true;
		String sql = "";
		String tableName = "epc_stat_qoe";
		if ("subitem".equals(itemType)) {// 分项电量
			isShowName = false;
			if (ids != null && ids.size() > 0) {
				for (String id : ids) {
					itemIds = subitemService.findChildrenItems(Long
							.parseLong(id),unitId);
					sql = this.buildSQL(date, dateType, tableName, itemIds,
							isShowName, subitemService.getById(
									Long.parseLong(id)).getName(),rateType);
					result.addAll(this.getResultByItemsIds(sql,rateType));
				}
			}
		} else if ("subitemfloor".equals(itemType)) {// 功能区电量
			isShowName = false;
			if (ids != null && ids.size() > 0) {
				for (String id : ids) {
					itemIds = subitemfloorService.findChildrenItems(Long
							.parseLong(id));
					sql = this.buildSQL(date, dateType, tableName, itemIds,
							isShowName, subitemfloorService.getById(
									Long.parseLong(id)).getName(),rateType);
					result.addAll(this.getResultByItemsIds(sql,rateType));
				}
			}
		} else if ("item".equals(itemType)) {// 支路电量
			itemIds = ids;
			sql = this.buildSQL(date, dateType, tableName, itemIds, isShowName,
					null,rateType);
			result = this.getResultByItemsIds(sql,rateType);
		} 
		return result;
	}

	/**
	 * 组装sql
	 * 
	 * @param date
	 *            时间 2010-10-10
	 * @param dateType
	 *            时间类型
	 * @param tableName
	 *            表名
	 * @param itemIds
	 *            epc_item中id
	 * @param isShowName
	 *            是否显示item的名字，subitem与subitemfloor不显示
	 * @param rateType 查询类型：energy-rate-rule
	 *             是否显示的类型：包括能耗，电费，标准煤
	 * @return
	 */
	private String buildSQL(String date, String dateType, String tableName,
			List<String> itemIds, boolean isShowName, String itemName,String rateType) {
		StringBuffer sql = new StringBuffer();

		StringBuffer param = new StringBuffer();
		StringBuffer where = new StringBuffer();
		String strType=" then t1.value end) )as ";
		if(rateType.equals("rate")){
           strType=" then t1.rate end) )as ";
		}
		// subitem-item-cur-vol-subitemfloor
		param.append("select ");
		where.append(" where ");
		if (!isShowName) {
			StringBuffer s_param = new StringBuffer();
			param.append(" t3.name ");
			param.append(",");
			param.append(" b1.* ");
			if ("day".equals(dateType)) {
				for (int i = 0; i < 24; i++) {
					if (i != 0)
						s_param.append(",");
					if (i < 10)
						s_param
								.append(" sum((CASE when substring(t1.date_time,12,2)='0"
										+ i
										+ "' "+strType+" '0"
										+ i
										+ "' ");
					else
						s_param
								.append(" sum((CASE when substring(t1.date_time,12,2)='"
										+ i
										+ "' "+strType+" '"
										+ i
										+ "' ");
				}
				where.append(" substring(t1.date_time,1,10)='" + date + "' ");
			} else if ("month".equals(dateType)) {
				// 算当月最大天数
				for (int i = 1; i < DateUtil.maxMonth(date, "yyyy-MM-dd")+1; i++) {
					if (i != 1)
						s_param.append(",");
					if (i < 10)
						s_param
								.append(" sum((CASE when substring(t1.date_time,9,2)='0"
										+ i
										+ "'"+strType+" '0"
										+ i
										+ "' ");
					else
						s_param
								.append(" sum((CASE when substring(t1.date_time,9,2)='"
										+ i
										+ "' "+strType+" '"
										+ i
										+ "' ");
				}
				where.append(" substring(t1.date_time,1,7)='"
						+ date.substring(0, 7) + "' ");
			} else if ("year".equals(dateType)) {
				for (int i = 1; i < 13; i++) {
					if (i != 1)
						s_param.append(",");
					if (i < 10)
						s_param
								.append(" sum((CASE when substring(t1.date_time,6,2)='0"
										+ i
										+ "' "+strType+" '0"
										+ i
										+ "' ");
					else
						s_param
								.append(" sum((CASE when substring(t1.date_time,6,2)='"
										+ i
										+ "' "+strType+" '"
										+ i
										+ "' ");
				}
				where.append(" substring(t1.date_time,1,4)='"
						+ date.substring(0, 4) + "' ");
			}
			where.append(" and t1.ITEM_NAME=t2.name ");
			where.append(" and t2.id in ( '-1' ");
			if (itemIds != null && itemIds.size() > 0) {
				for (int i = 0; i < itemIds.size(); i++) {
					where.append(",");
					where.append("'" + itemIds.get(i) + "'");
				}
			}
			where.append(" ) ");
			sql.append(param);
			sql.append(" from ");
			sql.append("( select ");
			sql.append(s_param);
			sql.append(" from ");
			sql.append(tableName + " t1 , bas_item t2 ");
			sql.append(where);
			sql.append(" ) b1 ");
			sql.append(", (select '" + itemName + "' as name ) t3 ");
			sql.append(" group by t3.name ");
		} else {
			if (isShowName)
				param.append(" t2.BAS_ITEM_DESC ");
			else
				param.append(" t3.name ");
			if ("day".equals(dateType)) {
				for (int i = 0; i < 24; i++) {
					if (i < 10)
						param
								.append(" ,sum((CASE when substring(t1.date_time,12,2)='0"
										+ i
										+ "' "+strType+" '0"
										+ i
										+ "' ");
					else
						param
								.append(" ,sum((CASE when substring(t1.date_time,12,2)='"
										+ i
										+ "' "+strType+" '"
										+ i
										+ "' ");
				}
				where.append(" substring(t1.date_time,1,10)='" + date + "' ");
			} else if ("month".equals(dateType)) {
				// 算当月最大天数
				Calendar cal = Calendar.getInstance();
				cal.setTime(DateUtil.string2Date(date, "yyyy-MM-dd"));
				cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
				cal.set(Calendar.DAY_OF_MONTH, 1);
				cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
				int maxDay = cal.get(Calendar.DAY_OF_MONTH);
				for (int i = 1; i < maxDay+1; i++) {
					if (i < 10)
						param
								.append(" ,sum((CASE when substring(t1.date_time,9,2)='0"
										+ i
										+ "' "+strType+" '0"
										+ i
										+ "' ");
					else
						param
								.append(" ,sum((CASE when substring(t1.date_time,9,2)='"
										+ i
										+ "' "+strType+" '"
										+ i
										+ "' ");
				}
				where.append(" substring(t1.date_time,1,7)='"
						+ date.substring(0, 7) + "' ");
			} else if ("year".equals(dateType)) {
				for (int i = 1; i < 13; i++) {
					if (i < 10)
						param
								.append(" ,sum((CASE when substring(t1.date_time,6,2)='0"
										+ i
										+ "' "+strType+" '0"
										+ i
										+ "' ");
					else
						param
								.append(" ,sum((CASE when substring(t1.date_time,6,2)='"
										+ i
										+ "' "+strType+" '"
										+ i
										+ "' ");
				}
				where.append(" substring(t1.date_time,1,4)='"
						+ date.substring(0, 4) + "' ");
			}
			where.append(" and t1.ITEM_NAME=t2.name ");
			if (itemIds != null && itemIds.size() > 0) {
				where.append(" and t2.id in ( '-1' ");
				for (int i = 0; i < itemIds.size(); i++) {
					where.append(",");
					where.append("'" + itemIds.get(i) + "'");
				}
				where.append(" ) ");
			}
			sql.append(param);
			sql.append(" from ");
			sql.append(tableName + " t1 , bas_item t2 ");
			sql.append(where);
			sql.append(" group by t2.BAS_ITEM_DESC ");
		}

		return sql.toString();
	}

	private List<Map<String, String>> getResultByItemsIds(String sql,String rateType) {
		List<Map<String, String>> result = null;
		List<Object[]> list = this.excuteQuerySQL(sql);
		
		double rateId=1.0;
        if(rateType.equals("rule")){
			rateId=0.1229;
		}
		
		if (list != null) {
			result = new ArrayList<Map<String, String>>();
			Object[] o = null;
			Map<String, String> map = null;
			for (int i = 0; i < list.size(); i++) {
				o = list.get(i);
				map = new HashMap<String, String>();
				for (int j = 0; j < o.length; j++) {
					if (j == 0)
						map.put("name", o[j] + "");
					else {
						if (o[j] == null)
							map.put(j + "", 0 + "");
						else{
							double doublenumber=Double.parseDouble(o[j].toString())*rateId;
							DecimalFormat df = new DecimalFormat( "#.##" ); 
							map.put(j + "", df.format(doublenumber) + "");
						}
					}
				}
				result.add(map);
			}
		}
		return result;
	}

	@Transactional
	@Override
	public boolean deleteEpcReport(Long id) {
		// TODO Auto-generated method stub
		super.deleteById(id);
		return true;
	}

	@Transactional
	@Override
	public boolean deleteEpcReports(Long id) {
		this.deleteById(id);
		return true;
	}

	@Transactional
	@Override
	public List<ReportDTO> getAllList(Long id) {
		String jpql = "from EpcReport t where t.epcUser.id=? ";
		List<EpcReport> list = super.getByQuery(jpql, new Object[] { id });
		List<ReportDTO> reportList = new ArrayList<ReportDTO>();
		for (EpcReport u : list) {
			reportList.add(this.buildDTO(u));
		}
		return reportList;
	}

	/**
	 * 组装DTO
	 * 
	 * id;//主键 private String name;//名称 private Date date;//创建时间 private Integer
	 * dateType;//时间类型：年、月、日 private Integer reprotType;//统计类型：按时间点统计、只统计一次
	 * private String remark;//描述
	 * 
	 * @param epcUser
	 */
	private ReportDTO buildDTO(EpcReport epcReport) {
		ReportDTO reportDTO = null;
		if (epcReport != null) {
			reportDTO = new ReportDTO();
			reportDTO.setId(epcReport.getId());
			reportDTO.setName(epcReport.getName());
			reportDTO.setRemark(epcReport.getRemark());
			reportDTO.setDate(epcReport.getDate());
			reportDTO.setUserId(epcReport.getEpcUser().getId());
			reportDTO.setDateType(epcReport.getDateType());
			reportDTO.setDataType(epcReport.getDataType());
			reportDTO.setReportType(epcReport.getReportType());
			reportDTO.setEmail(epcReport.getEmail());
			reportDTO.setItemIds(splitItemIds(epcReport.getItemIds())); // 需要修改
			reportDTO.setItemType(epcReport.getItemType());
			reportDTO.setSendType(epcReport.getSendType());
			reportDTO.setUnitId(epcReport.getUnitId());
			
		}
		return reportDTO;
	}

	@Transactional
	@Override
	public boolean saveEpcReport(ReportDTO report) {
		// TODO Auto-generated method stub
		EpcReport er = this.buildEpcReport(report);
		er.setId(null);
		er.setDate(new Date());
		this.save(er);
		return true;
	}
	@Transactional
	@Override
	public boolean updateEpcReport(ReportDTO report){
		EpcReport er = this.buildEpcReport(report);
		this.update(er);
		return true;
	}
	
	private List<String> splitItemIds(String itemIds) {
		List<String> list = new ArrayList<String>();
		if (itemIds != null) {
			String[] ls = itemIds.split(",");
			if (ls != null)
				for (String s : ls) {
					if(s!=null&&!"".equals(s))
						list.add(s);
				}
		}
		return list;
	}
	private EpcReport buildEpcReport(ReportDTO report){
		EpcReport er = null;
		if(report!=null){
			er = new EpcReport();
			er.setId(report.getId());
			er.setName(report.getName());
			er.setRemark(report.getRemark());
			er.setDate(report.getDate());
			er.setEpcUser(this.getEpcUser(report.getUserId()+""));
			er.setDateType(report.getDateType());
			er.setReportType(report.getReportType());
			er.setEmail(report.getEmail());
			er.setItemIds(buildItemIds(report.getItemIds())); // 需要修改
			er.setItemType(report.getItemType());
			er.setSendType(report.getSendType());
			er.setUnitId(report.getUnitId());
			er.setDataType(report.getDataType());
		}
		return er;
	}
	private EpcUser getEpcUser(String id){
		return userService.getById(Long.parseLong(id));
	}
	private String buildItemIds(List<String> ids){
		StringBuffer sb = new StringBuffer();
		if(ids!=null){
			for(String s:ids){
				sb.append(s);
				sb.append(",");
			}
		}
		return sb.toString();
	}

}
