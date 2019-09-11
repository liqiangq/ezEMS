package com.thtf.service.imp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.transaction.annotation.Transactional;

import com.thtf.dto.BasItemDTO;
import com.thtf.dto.SubItemDTO;
import com.thtf.entity.BasItem;
import com.thtf.entity.EpcSubitem;
import com.thtf.entity.EpcSubitemRule;
import com.thtf.entity.EpcUnit;
import com.thtf.service.ItemService;
import com.thtf.service.SubitemRuleService;
import com.thtf.service.SubitemService;
import com.thtf.service.TotalEnergyService;
import com.thtf.service.UnitService;
import com.thtf.util.DateUtil;

@RemotingDestination
public class TotalEnergyServiceImp extends BasicServiceImp implements
		TotalEnergyService {
	
	
	private UnitService unitService;
	private ItemService itemService;
	private SubitemRuleService subitemRuleService; 
	private SubitemService subitemService;
	
	
	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

	public void setSubitemRuleService(SubitemRuleService subitemRuleService) {
		this.subitemRuleService = subitemRuleService;
	}

	public void setSubitemService(SubitemService subitemService) {
		this.subitemService = subitemService;
	}

	/**
	 * 总能耗
	 */
	

	@Transactional
	@Override
	public Double totalContemporaryComparison(String date, String dateType,
			Long unitId) {
		Double d = 0.00;		
		d = this.totalEnergy(date, dateType, unitId);
		double suart = 0d;
		double b = this.totalEnergyBeforeData(date, dateType, unitId);		
		if (b != 0 && d != null)
			suart = (d - b) / b;
		else
			suart = 0d;
		return suart;
	}

	
	

	@Transactional
	@Override
	public Double squareMeterPower(String date, String dateType, Long unitId) {

		Double d = 0.0;
		Double s = 0.0;
		
		StringBuffer sql_b2 = new StringBuffer();
		sql_b2.append("select a.TotalArea as TotalArea  from epc_subitemfloor f ,epc_subitemfloor_assist a where");
		sql_b2.append(" f.id=a.sub_floor_id and f.unit_id=" + unitId
				+ " and f.itemfloor_id is NULL");
		
		try {
			/*EpcUnit epcUnit = unitService.getById(unitId);
			if(epcUnit!=null&&epcUnit.getArea()!=null){
				s = epcUnit.getArea().doubleValue();
			}*/
			
			List<String> list = this.excuteQuerySQL(sql_b2.toString());
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					s = Double.valueOf(list.get(i));
				}
			}

			Double b = this.totalEnergy(date, dateType, unitId);

			if (b != null && s != null && s != 0d) {
				BigDecimal b1 = new BigDecimal(Double.toString(b));
				BigDecimal b2 = new BigDecimal(Double.toString(s));
				d = b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
			} else {
				d = 0d;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return d;

	}

	@Transactional
	@Override
	public Double squareContemporaryComparison(String date, String dateType,
			Long unitId) {
		// TODO Auto-generated method stub
		Double b = this.totalEnergyBeforeData(date, dateType, unitId);

		Double t = this.totalEnergy(date,
				dateType, unitId);

		Double square = 0d;
		try {
			if (b != null && b != 0d && t != null) {
				
				square = (t - b) / b;
			} else {
				square = 0d;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return square;
	}
	
	@Transactional
	@Override
	public Double totalContempRate(String date, String dateType, Long unitId) {
		
		Double d = 0.00;
		d = this.totalRate(date, dateType, unitId);
		double suart = 0d;
		double b = this.totalRateBeforeData(date, dateType, unitId);

		if (b != 0 && d != null)
			suart = (d - b) / b;
		else
			suart = 0d;
		
		return suart;
	}
	
	
	@Transactional
	@Override
	public Double totalRate( String date, String dateType,Long unitId) {
		// TODO Auto-generated method stub
		List<SubItemDTO> ls = new ArrayList<SubItemDTO>();
		Double d =0d;
		SubItemDTO s = null;
		String jpql = "from EpcSubitem t where t.epcSubitem is null";
		EpcUnit epcUnit = unitService.getById(unitId);
		List<EpcSubitem> list = super.getByQuery(jpql, null);
		if (list != null && list.size() > 0) {
			for (EpcSubitem e : list) {
				d = this.buildDTOGplotRate(e, epcUnit, "show", date, dateType);
				//ls.add(s);
			}
		}
		return d;
	}
	

	private Double totalRateBeforeData( String date, String dateType,Long unitId) {
		// TODO Auto-generated method stub
		List<SubItemDTO> ls = new ArrayList<SubItemDTO>();
		Double d =0d;
		SubItemDTO s = null;
		String jpql = "from EpcSubitem t where t.epcSubitem is null";
		EpcUnit epcUnit = unitService.getById(unitId);
		List<EpcSubitem> list = super.getByQuery(jpql, null);
		if (list != null && list.size() > 0) {
			for (EpcSubitem e : list) {
				d = this.buildDTOGplotRateBeforeData(e, epcUnit, "show", date, dateType);
				
			}
		}
		return d;
	}
	
	/**
	 * 总能耗
	 */
	@Transactional
	@Override
	public Double totalEnergy( String date, String dateType,Long unitId) {
		// TODO Auto-generated method stub
		List<SubItemDTO> ls = new ArrayList<SubItemDTO>();
		Double d =0d;
		SubItemDTO s = null;
		String jpql = "from EpcSubitem t where t.epcSubitem is null";
		EpcUnit epcUnit = unitService.getById(unitId);
		List<EpcSubitem> list = super.getByQuery(jpql, null);
		if (list != null && list.size() > 0) {
			for (EpcSubitem e : list) {
				d = this.buildDTOGplot(e, epcUnit, "show", date, dateType);
				//ls.add(s);
			}
		}
		return d;
	}
	@Transactional
	@Override
	public Double totalEnergyBeforeData( String date, String dateType,Long unitId) {
		// TODO Auto-generated method stub
		List<SubItemDTO> ls = new ArrayList<SubItemDTO>();
		Double d =0d;
		SubItemDTO s = null;
		String jpql = "from EpcSubitem t where t.epcSubitem is null";
		EpcUnit epcUnit = unitService.getById(unitId);
		List<EpcSubitem> list = super.getByQuery(jpql, null);
		if (list != null && list.size() > 0) {
			for (EpcSubitem e : list) {
				d = this.buildDTOGplotBeforeData(e, epcUnit, "show", date, dateType);
				//ls.add(s);
			}
		}
		return d;
	}
	
	/**
	 * 组建SubItemDTO
	 * 
	 * @param epcSubitem
	 *            实体
	 * @param type
	 *            显示类型：show-显示拓扑，edit-用于编辑
	 * @param date
	 *            时间
	 * @param dateType
	 *            时间类型：year，month，day
	 * @return
	 */
	private Double buildDTOGplot(EpcSubitem epcSubitem, EpcUnit epcUnit,
			String type, String date, String dateType) {
		try {
			Double dd =0d;
			SubItemDTO subItemDTO = null;
			if (epcSubitem != null) {
				EpcSubitemRule epcSubitemRule = subitemRuleService
						.getEpcSubitemRule(epcUnit.getId(), epcSubitem.getId());
		
				List<Long> ids = this.getChildrenSubitems(epcSubitem.getId());
			
				List<BasItem> basItems = itemService.findBasItems(ids, epcUnit.getId());
				
				subItemDTO = new SubItemDTO();
				subItemDTO.setId(epcSubitem.getId());
				subItemDTO.setName(epcSubitem.getName());
				subItemDTO.setBasItems(this.buildDTO(basItems));
			
				if ("show".equals(type)) {// 用于显示拓扑图
					Set<EpcSubitem> set = epcSubitem.getEpcSubitems();
					List<SubItemDTO> list = null;
					
					if (epcSubitemRule != null) {
						if (dateType.equals("month")) {
							int year = Integer.parseInt(date.split("-")[0]);
							int month = Integer.parseInt(date.split("-")[1]);
							int days = DateUtil.days(year, month);
							subItemDTO.setRule((epcSubitemRule == null ? 0
									: epcSubitemRule.getRule())
									* days);
							subItemDTO.setPowerRule((epcSubitemRule == null ? 0
									: epcSubitemRule.getPowerRule())
									* days);
						} else if (dateType.equals("year")) {							
							int year = Integer.parseInt(DateUtil.YearStr(date,"yyyy"));
							int days = 365;
							if (year % 4 == 0 && year % 100 != 0
									|| year % 400 == 0)
								days = 366;
							else
								days = 365;
							subItemDTO.setRule((epcSubitemRule == null ? 0
									: epcSubitemRule.getRule())
									* days);
							subItemDTO.setPowerRule((epcSubitemRule == null ? 0
									: epcSubitemRule.getPowerRule())
									* days);
						} else {
							subItemDTO.setRule(epcSubitemRule == null ? 0
									: epcSubitemRule.getRule());
							subItemDTO.setPowerRule(epcSubitemRule == null ? 0
									: epcSubitemRule.getPowerRule());
						}
					}
					subItemDTO.setChildren(list);
					
					dd = this.getEnergy(basItems, date,	dateType);
					if(dd == null)
						dd = 0d;
					
				}

			}
			return dd;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private Double buildDTOGplotBeforeData(EpcSubitem epcSubitem, EpcUnit epcUnit,
			String type, String date, String dateType) {
		try {
			Double dd =0d;
			SubItemDTO subItemDTO = null;
			if (epcSubitem != null) {
				EpcSubitemRule epcSubitemRule = subitemRuleService
						.getEpcSubitemRule(epcUnit.getId(), epcSubitem.getId());
		
				List<Long> ids = this.getChildrenSubitems(epcSubitem.getId());
			
				List<BasItem> basItems = itemService.findBasItems(ids, epcUnit.getId());
				
				subItemDTO = new SubItemDTO();
				subItemDTO.setId(epcSubitem.getId());
				subItemDTO.setName(epcSubitem.getName());
				subItemDTO.setBasItems(this.buildDTO(basItems));
			
				if ("show".equals(type)) {// 用于显示拓扑图
					Set<EpcSubitem> set = epcSubitem.getEpcSubitems();
					List<SubItemDTO> list = null;					
					if (epcSubitemRule != null) {
						if (dateType.equals("month")) {
							int year = Integer.parseInt(date.split("-")[0]);
							int month = Integer.parseInt(date.split("-")[1]);
							int days = DateUtil.days(year, month);
							subItemDTO.setRule((epcSubitemRule == null ? 0
									: epcSubitemRule.getRule())
									* days);
							subItemDTO.setPowerRule((epcSubitemRule == null ? 0
									: epcSubitemRule.getPowerRule())
									* days);
						} else if (dateType.equals("year")) {
							int year = Integer.parseInt(DateUtil.YearStr(date,"yyyy"));
							int days = 365;
							if (year % 4 == 0 && year % 100 != 0
									|| year % 400 == 0)
								days = 366;
							else
								days = 365;
							subItemDTO.setRule((epcSubitemRule == null ? 0
									: epcSubitemRule.getRule())
									* days);
							subItemDTO.setPowerRule((epcSubitemRule == null ? 0
									: epcSubitemRule.getPowerRule())
									* days);
						} else {
							subItemDTO.setRule(epcSubitemRule == null ? 0
									: epcSubitemRule.getRule());
							subItemDTO.setPowerRule(epcSubitemRule == null ? 0
									: epcSubitemRule.getPowerRule());
						}
					}
					subItemDTO.setChildren(list);
					
					dd = this.getEnergyBeforeData(basItems, date,	dateType);
					if(dd == null)
						dd=0d;
				}

			}
			return dd;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private Double buildDTOGplotRateBeforeData(EpcSubitem epcSubitem, EpcUnit epcUnit,
			String type, String date, String dateType) {
		try {
			Double dd =0d;
			SubItemDTO subItemDTO = null;
			if (epcSubitem != null) {
				EpcSubitemRule epcSubitemRule = subitemRuleService
						.getEpcSubitemRule(epcUnit.getId(), epcSubitem.getId());
		
				List<Long> ids = this.getChildrenSubitems(epcSubitem.getId());
			
				List<BasItem> basItems = itemService.findBasItems(ids, epcUnit.getId());
				
				subItemDTO = new SubItemDTO();
				subItemDTO.setId(epcSubitem.getId());
				subItemDTO.setName(epcSubitem.getName());
				subItemDTO.setBasItems(this.buildDTO(basItems));
			
				if ("show".equals(type)) {// 用于显示拓扑图
					Set<EpcSubitem> set = epcSubitem.getEpcSubitems();
					List<SubItemDTO> list = null;					
					if (epcSubitemRule != null) {
						if (dateType.equals("month")) {
							int year = Integer.parseInt(date.split("-")[0]);
							int month = Integer.parseInt(date.split("-")[1]);
							int days = DateUtil.days(year, month);
							subItemDTO.setRule((epcSubitemRule == null ? 0
									: epcSubitemRule.getRule())
									* days);
							subItemDTO.setPowerRule((epcSubitemRule == null ? 0
									: epcSubitemRule.getPowerRule())
									* days);
						} else if (dateType.equals("year")) {
							int year = Integer.parseInt(DateUtil.YearStr(date,"yyyy"));
							int days = 365;
							if (year % 4 == 0 && year % 100 != 0
									|| year % 400 == 0)
								days = 366;
							else
								days = 365;
							subItemDTO.setRule((epcSubitemRule == null ? 0
									: epcSubitemRule.getRule())
									* days);
							subItemDTO.setPowerRule((epcSubitemRule == null ? 0
									: epcSubitemRule.getPowerRule())
									* days);
						} else {
							subItemDTO.setRule(epcSubitemRule == null ? 0
									: epcSubitemRule.getRule());
							subItemDTO.setPowerRule(epcSubitemRule == null ? 0
									: epcSubitemRule.getPowerRule());
						}
					}
					subItemDTO.setChildren(list);
							
					
					dd = this.getRateBeforeData(basItems, date, dateType);
					if(dd==null)
						dd = 0d;
				}

			}
			return dd;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	private Double buildDTOGplotRate(EpcSubitem epcSubitem, EpcUnit epcUnit,
			String type, String date, String dateType) {
		try {
			Double dd =0d;
			SubItemDTO subItemDTO = null;
			if (epcSubitem != null) {
				EpcSubitemRule epcSubitemRule = subitemRuleService
						.getEpcSubitemRule(epcUnit.getId(), epcSubitem.getId());
		
				List<Long> ids = this.getChildrenSubitems(epcSubitem.getId());
			
				List<BasItem> basItems = itemService.findBasItems(ids, epcUnit.getId());
				
				subItemDTO = new SubItemDTO();
				subItemDTO.setId(epcSubitem.getId());
				subItemDTO.setName(epcSubitem.getName());
				subItemDTO.setBasItems(this.buildDTO(basItems));
			
				if ("show".equals(type)) {// 用于显示拓扑图
					Set<EpcSubitem> set = epcSubitem.getEpcSubitems();
					List<SubItemDTO> list = null;					
					if (epcSubitemRule != null) {
						if (dateType.equals("month")) {
							int year = Integer.parseInt(date.split("-")[0]);
							int month = Integer.parseInt(date.split("-")[1]);
							int days = DateUtil.days(year, month);
							subItemDTO.setRule((epcSubitemRule == null ? 0
									: epcSubitemRule.getRule())
									* days);
							subItemDTO.setPowerRule((epcSubitemRule == null ? 0
									: epcSubitemRule.getPowerRule())
									* days);
						} else if (dateType.equals("year")) {
							int year = Integer.parseInt(DateUtil.YearStr(date,"yyyy"));
							int days = 365;
							if (year % 4 == 0 && year % 100 != 0
									|| year % 400 == 0)
								days = 366;
							else
								days = 365;
							subItemDTO.setRule((epcSubitemRule == null ? 0
									: epcSubitemRule.getRule())
									* days);
							subItemDTO.setPowerRule((epcSubitemRule == null ? 0
									: epcSubitemRule.getPowerRule())
									* days);
						} else {
							subItemDTO.setRule(epcSubitemRule == null ? 0
									: epcSubitemRule.getRule());
							subItemDTO.setPowerRule(epcSubitemRule == null ? 0
									: epcSubitemRule.getPowerRule());
						}
					}
					subItemDTO.setChildren(list);
				
					dd = this.getRate(basItems, date, dateType);
					if(dd==null)
						dd = 0d;
				}

			}
			return dd;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 根据时间得到用电量
	 * 
	 * @param subItemDTO
	 * @param date
	 *            时间
	 * @param dateType
	 *            时间类型：year，month，day
	 * @return
	 */
	private Double getEnergy(List<BasItem> basItems, String date,
			String dateType) {
		String hh = DateUtil.date2String(new Date(), "HH");
		String dd = DateUtil.date2String(new Date(), "dd");
		String mm = DateUtil.date2String(new Date(), "MM");
		String yy = DateUtil.date2String(new Date(), "yyyy");
		Double d = null;
		StringBuffer sb = new StringBuffer();
		if (basItems != null) {
			sb.append("select sum(value) from epc_stat_qoe where (1=0 ");
			
			for (BasItem b : basItems) {				
				sb.append(" or item_name='");
				sb.append(b.getName());
				sb.append("' ");
			}
			sb.append(") and ");
			if ("day".equals(dateType)) {
				sb.append("  date_time>='"	+ DateUtil.strDateStr(date, "yyyy-MM-dd") + " 00' and  ");
				//System.out.println(date.substring(8,10)+"-----"+DateUtil.strDateStr(date, "dd")+"==========="+dd);
				if(dd.equals(date.substring(8,10))){
					
				//System.out.println(date.substring(8,10)+"-----"+DateUtil.strDateStr(date, "dd")+"==========="+dd);
					sb.append("date_time<='" + DateUtil.strDateStr(date, "yyyy-MM-dd ")+hh+":59:59'");
				}else{
					
					sb.append("date_time<='"	+ DateUtil.strDateStr(date, "yyyy-MM-dd ")+"23:59:59'");
				}
				//sb.append(" substring(date_time,1,10)='" + date + "' ");
			} else if ("month".equals(dateType)) {
				if(mm.equals(date.substring(5, 7))){
					sb.append(" substring(date_time,1,10)>='" + date.substring(0, 7)+ "-01' and ");
					sb.append(" substring(date_time,1,10)<='" + date.substring(0, 7)+ "-"+dd+"' ");
				}else{
					sb.append(" substring(date_time,1,7)='" + date.substring(0, 7) + "' ");
				}
			} else if ("year".equals(dateType)) {
				if(yy.equals(date.substring(0, 4))){
					sb.append(" substring(date_time,1,7)>='" + date.substring(0, 4)+ "-01' and ");
					sb.append(" substring(date_time,1,7)<='" + date.substring(0, 4)+ "-"+mm+"' ");
				}else{
					sb.append(" substring(date_time,1,4)='" + date.substring(0, 4)+ "' ");
				}
			}
			try {
				List list = this.dao.excuteQuerySQL(sb.toString());
				if (list != null && list.size() > 0) {
					d = (Double) list.get(0);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return d;
	}
	
	private Double getEnergyBeforeData(List<BasItem> basItems, String date,
			String dateType) {
		String hh = DateUtil.date2String(new Date(), "HH");
		String dd = DateUtil.date2String(new Date(), "dd");
		String mm = DateUtil.date2String(new Date(), "MM");
		String yy = DateUtil.date2String(new Date(), "yyyy");
		Double d = null;
		StringBuffer sb = new StringBuffer();
		if (basItems != null) {
			sb.append("select sum(value) from epc_stat_qoe where (1=0 ");
			
			for (BasItem b : basItems) {				
				sb.append(" or item_name='");
				sb.append(b.getName());
				sb.append("' ");
			}
			sb.append(") and ");
			if ("day".equals(dateType)) {
				sb.append("  date_time>='"	+ DateUtil.beforeDateStr(date, "yyyy-MM-dd") + " 00' and  ");
				if(dd.equals(date.substring(8,10))){
					sb.append("date_time<='" + DateUtil.beforeDateStr(date, "yyyy-MM-dd ")+hh+":59:59'");
				}else{
					sb.append("date_time<='"	+ DateUtil.beforeDateStr(date, "yyyy-MM-dd ")+"23:59:59'");
				}
				//sb.append(" substring(date_time,1,10)='" + date + "' ");
			} else if ("month".equals(dateType)) {
				//System.out.println(mm+"===========================================================");
				if(mm.equals(date.substring(5, 7))){
					sb.append(" substring(date_time,1,10)>='" + DateUtil.beforeMonthStr(date.substring(0, 7),"yyyy-MM")+ "-01' and ");
					sb.append(" substring(date_time,1,10)<='" + DateUtil.beforeMonthStr(date.substring(0, 7),"yyyy-MM")+ "-"+dd+"' ");
				}else{
					sb.append(" substring(date_time,1,7)='" + DateUtil.beforeMonthStr(date.substring(0, 7),"yyyy-MM")+ "'");
				}
			} else if ("year".equals(dateType)) {
				if(yy.equals(date.substring(0, 4))){
					sb.append(" substring(date_time,1,7)>='" + DateUtil.beforeYearStr(date.substring(0, 4), "yyyy")+ "-01' and ");
					sb.append(" substring(date_time,1,7)<='" + DateUtil.beforeYearStr(date.substring(0, 4), "yyyy")+ "-"+mm+"'");
				}else{
					sb.append(" substring(date_time,1,4)='" + DateUtil.beforeYearStr(date.substring(0, 4), "yyyy")+ "'");
				}
			}
			try {
				List list = this.dao.excuteQuerySQL(sb.toString());
				if (list != null && list.size() > 0) {
					d = (Double) list.get(0);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return d;
	}
	/**
	 * 得到subitem下所有子节点
	 * 
	 * @param subitemId
	 * @return
	 */
	private List<Long> getChildrenSubitems(Long subitemId) {
		List<Long> ids = new ArrayList<Long>();
		EpcSubitem subitem = subitemService.getById(subitemId);
		
		ids.add(subitemId);
		Set<EpcSubitem> set = subitem.getEpcSubitems();
		for (EpcSubitem es : set) {
			ids.add(es.getId());
			ids.addAll(this.getChildrenSubitems(es.getId()));
		}
		
		return ids;
	}
	
	/**
	 * 组建BasItemDTO列表
	 * 
	 * @param set
	 * @return
	 */
	private List<BasItemDTO> buildDTO(List<BasItem> set) {
		List<BasItemDTO> list = null;
		if (set != null && set.size() > 0) {
			list = new ArrayList<BasItemDTO>();
			BasItemDTO basItemDTO = null;
			for (BasItem b : set) {
				basItemDTO = new BasItemDTO();
				basItemDTO.setId(b.getId());
				basItemDTO.setName(b.getName());
				basItemDTO.setDescription(b.getBasItemDesc());
				if (b.getEpcItemType() != null)
					basItemDTO.setItemTypeCode(b.getEpcItemType().getCode());
				list.add(basItemDTO);
			}
		}
		return list;
	}
	
	
	
	@Transactional
	@Override
	public List<Long> findChildrenItems_R(Long subitemId, Long unitId) {
		// TODO Auto-generated method stub
		List<Long> itemIds = null;
		List<Long> ids = this.getChildrenSubitems(subitemId);
		ids.add(subitemId);
		List<BasItem> basItems = itemService.findBasItems(ids, unitId);		
		if (basItems != null) {
			itemIds = new ArrayList<Long>();
			for (BasItem bi : basItems) {
				itemIds.add(bi.getId());
			}
		}		
		return itemIds;
	}
	
	
	/**
	 * 根据时间得到用电量
	 * 
	 * @param subItemDTO
	 * @param date
	 *            时间
	 * @param dateType
	 *            时间类型：year，month，day
	 * @return
	 */
	private Double getRate(List<BasItem> basItems, String date, String dateType) {
		Double d = null;
		String hh = DateUtil.date2String(new Date(), "HH");
		String dd = DateUtil.date2String(new Date(), "dd");
		String mm = DateUtil.date2String(new Date(), "MM");
		String yy = DateUtil.date2String(new Date(), "yyyy");
		StringBuffer sb = new StringBuffer();
		if (basItems != null) {
			sb.append("select sum(rate) from epc_stat_qoe where (1=0 ");
			for (BasItem b : basItems) {
				sb.append(" or item_name='");
				sb.append(b.getName());
				sb.append("' ");
			}
			sb.append(") and ");
			if ("day".equals(dateType)) {
				sb.append("  date_time>='"	+ DateUtil.strDateStr(date, "yyyy-MM-dd") + " 00' and  ");
				if(dd.equals(date.substring(8,10))){
					sb.append("date_time<='" + DateUtil.strDateStr(date, "yyyy-MM-dd ")+hh+":59:59'");
				}else{
					sb.append("date_time<='"	+ DateUtil.strDateStr(date, "yyyy-MM-dd ")+"23:59:59'");
				}
			} else if ("month".equals(dateType)) {
				if(mm.equals(date.substring(5, 7))){
					sb.append(" substring(date_time,1,10)>='" + date.substring(0, 7)+ "-01' and ");
					sb.append(" substring(date_time,1,10)<='" + date.substring(0, 7)+ "-"+dd+"' ");
				}else{
					sb.append(" substring(date_time,1,7)='" + date.substring(0, 7) + "' ");
				}
			} else if ("year".equals(dateType)) {
				if(yy.equals(date.substring(0, 4))){
					sb.append(" substring(date_time,1,7)>='" + date.substring(0, 4)+ "-01' and ");
					sb.append(" substring(date_time,1,7)<='" + date.substring(0, 4)+ "-"+mm+"' ");
				}else{
					sb.append(" substring(date_time,1,4)='" + date.substring(0, 4)+ "' ");
				}
			}
			try {
				List list = this.dao.excuteQuerySQL(sb.toString());
				if (list != null && list.size() > 0) {
					d = (Double) list.get(0);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return d;
	}
	
	private Double getRateBeforeData(List<BasItem> basItems, String date,
			String dateType) {
		Double d = null;
		String hh = DateUtil.date2String(new Date(), "HH");
		String dd = DateUtil.date2String(new Date(), "dd");
		String mm = DateUtil.date2String(new Date(), "MM");
		String yy = DateUtil.date2String(new Date(), "yyyy");
		StringBuffer sb = new StringBuffer();
		if (basItems != null) {
			sb.append("select sum(rate) from epc_stat_qoe where (1=0 ");
			
			for (BasItem b : basItems) {				
				sb.append(" or item_name='");
				sb.append(b.getName());
				sb.append("' ");
			}
			sb.append(") and ");
			if ("day".equals(dateType)) {
				sb.append("  date_time>='"	+ DateUtil.beforeDateStr(date, "yyyy-MM-dd") + " 00' and  ");
				if(dd.equals(date.substring(8,10))){
					sb.append("date_time<='" + DateUtil.beforeDateStr(date, "yyyy-MM-dd ")+hh+":59:59'");
				}else{
					sb.append("date_time<='"	+ DateUtil.beforeDateStr(date, "yyyy-MM-dd ")+"23:59:59'");
				}
				//sb.append(" substring(date_time,1,10)='" + date + "' ");
			} else if ("month".equals(dateType)) {
				//System.out.println(mm+"===========================================================");
				if(mm.equals(date.substring(5, 7))){
					sb.append(" substring(date_time,1,10)>='" + DateUtil.beforeMonthStr(date.substring(0, 7),"yyyy-MM")+ "-01' and ");
					sb.append(" substring(date_time,1,10)<='" + DateUtil.beforeMonthStr(date.substring(0, 7),"yyyy-MM")+ "-"+dd+"' ");
				}else{
					sb.append(" substring(date_time,1,7)='" + DateUtil.beforeMonthStr(date.substring(0, 7),"yyyy-MM")+ "'");
				}
			} else if ("year".equals(dateType)) {
				if(yy.equals(date.substring(0, 4))){
					sb.append(" substring(date_time,1,7)>='" + DateUtil.beforeYearStr(date.substring(0, 4), "yyyy")+ "-01' and ");
					sb.append(" substring(date_time,1,7)<='" + DateUtil.beforeYearStr(date.substring(0, 4), "yyyy")+ "-"+mm+"'");
				}else{
					sb.append(" substring(date_time,1,4)='" + DateUtil.beforeYearStr(date.substring(0, 4), "yyyy")+ "'");
				}
			}
			try {
				List list = this.dao.excuteQuerySQL(sb.toString());
				if (list != null && list.size() > 0) {
					d = (Double) list.get(0);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return d;
	}


}
