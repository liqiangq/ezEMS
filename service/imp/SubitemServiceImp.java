package com.thtf.service.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.transaction.annotation.Transactional;

import com.thtf.dto.BasItemDTO;
import com.thtf.dto.SubItemDTO;
import com.thtf.dto.UnitDTO;
import com.thtf.entity.BasItem;
import com.thtf.entity.EpcSubitem;
import com.thtf.entity.EpcSubitemRule;
import com.thtf.entity.EpcUnit;
import com.thtf.entity.SubitemRulePK;
import com.thtf.service.ItemService;
import com.thtf.service.SubitemRuleService;
import com.thtf.service.SubitemService;
import com.thtf.service.UnitService;
import com.thtf.util.DateUtil;

/**
 * @author 孙刚 E-mail:sungang01@thtf.com.cn
 * @version 创建时间：2010-9-2 下午04:08:55 类说明
 */
@RemotingDestination
public class SubitemServiceImp extends BasicServiceImp<EpcSubitem, Long>
		implements SubitemService {

	private ItemService itemService;
	private UnitService unitService;
	private SubitemRuleService subitemRuleService;

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

	public void setSubitemRuleService(SubitemRuleService subitemRuleService) {
		this.subitemRuleService = subitemRuleService;
	}

	@Transactional
	@Override
	public List<SubItemDTO> findTree(Long unitId, String date, String dateType) {
		// TODO Auto-generated method stub
		List<SubItemDTO> ls = new ArrayList<SubItemDTO>();
		SubItemDTO s = null;
		String jpql = "from EpcSubitem t where t.epcSubitem is null";
		EpcUnit epcUnit = unitService.getById(unitId);
		List<EpcSubitem> list = super.getByQuery(jpql, null);
		if (list != null && list.size() > 0) {
			for (EpcSubitem e : list) {
				s = this.buildDTO(e, epcUnit, "show", date, dateType);
				ls.add(s);
			}
		}
		return ls;
	}
	@Transactional
	@Override
	public List<SubItemDTO> findTreeGplot(Long unitId, String date, String dateType) {
		// TODO Auto-generated method stub
		List<SubItemDTO> ls = new ArrayList<SubItemDTO>();
		SubItemDTO s = null;
		String jpql = "from EpcSubitem t where t.epcSubitem is null";
		EpcUnit epcUnit = unitService.getById(unitId);
		List<EpcSubitem> list = super.getByQuery(jpql, null);
		if (list != null && list.size() > 0) {
			for (EpcSubitem e : list) {
				s = this.buildDTOGplot(e, epcUnit, "show", date, dateType);
				ls.add(s);
			}
		}
		return ls;
	}

	@Transactional
	@Override
	public List<SubItemDTO> findTree(Long unitId) {
		// TODO Auto-generated method stub
		List<SubItemDTO> ls = new ArrayList<SubItemDTO>();
		SubItemDTO s = null;
		String jpql = "from EpcSubitem t where t.epcSubitem is null";
		List<EpcSubitem> list = super.getByQuery(jpql, null);
		EpcUnit epcUnit = unitService.getById(unitId);
		if (list != null && list.size() > 0) {
			for (EpcSubitem e : list) {
				s = this.buildDTO(e, epcUnit, "edit", null, null);
				ls.add(s);
			}
		}
		return ls;
	}
	@Transactional
	@Override
	public List<SubItemDTO> findTreeManage(Long unitId) {
		// TODO Auto-generated method stub
		List<SubItemDTO> ls = new ArrayList<SubItemDTO>();
		SubItemDTO s = null;
		String jpql = "from EpcSubitem t where t.epcSubitem is null";
		List<EpcSubitem> list = super.getByQuery(jpql, null);
		EpcUnit epcUnit = unitService.getById(unitId);
		if (list != null && list.size() > 0) {
			for (EpcSubitem e : list) {
				s = this.buildDTOManage(e, epcUnit, "edit", null, null);
				ls.add(s);
			}
		}
		return ls;
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
	private SubItemDTO buildDTOGplot(EpcSubitem epcSubitem, EpcUnit epcUnit,
			String type, String date, String dateType) {
		try {
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
				if ("edit".equals(type)) {// 用于维护操作
					if (epcSubitem.getEpcSubitem() != null)
						subItemDTO.setParentId(epcSubitem.getEpcSubitem()
								.getId());
					subItemDTO.setRemark(epcSubitem.getRemark());
					subItemDTO.setUnitId(epcUnit.getId());
					subItemDTO.setUnitName(epcUnit.getName());
					subItemDTO.setRule(epcSubitemRule == null ? 0
							: epcSubitemRule.getRule());
					subItemDTO.setPowerRule(epcSubitemRule == null ? 0
							: epcSubitemRule.getPowerRule());
					Set<EpcSubitem> set = epcSubitem.getEpcSubitems();
					List<SubItemDTO> list = null;
					if (set != null && set.size() > 0) {
						list = new ArrayList<SubItemDTO>();
						for (EpcSubitem e : set) {
							list.add(this.buildDTO(e, epcUnit, "edit", null,
									null));
						}
					}
					subItemDTO.setChildren(list);
				} else if ("show".equals(type)) {// 用于显示拓扑图
					Set<EpcSubitem> set = epcSubitem.getEpcSubitems();
					List<SubItemDTO> list = null;
					if (set != null && set.size() > 0) {
						list = new ArrayList<SubItemDTO>();
						for (EpcSubitem e : set) {
							list.add(this.buildDTOGplot(e, epcUnit, "show", date,
									dateType));
						}
					}
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
							int year = Integer.parseInt(date);
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
					subItemDTO.setEnergy(this.getEnergy(basItems, date,
							dateType));
					subItemDTO.setRate(this.getRate(basItems, date, dateType));
				}

			}
			return subItemDTO;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
	private SubItemDTO buildDTOManage(EpcSubitem epcSubitem, EpcUnit epcUnit,
			String type, String date, String dateType) {
		try {
			SubItemDTO subItemDTO = null;
			if (epcSubitem != null) {
				EpcSubitemRule epcSubitemRule = subitemRuleService
						.getEpcSubitemRule(epcUnit.getId(), epcSubitem.getId());
				List<Long> ids = this.getChildrenSubitems(epcSubitem.getId());			
				List<BasItem> basItems = itemService.findBasItems(epcSubitem.getId(), epcUnit.getId());
				subItemDTO = new SubItemDTO();
				subItemDTO.setId(epcSubitem.getId());
				subItemDTO.setName(epcSubitem.getName());
				subItemDTO.setBasItems(this.buildDTO(basItems));
				if ("edit".equals(type)) {// 用于维护操作
					if (epcSubitem.getEpcSubitem() != null)
						subItemDTO.setParentId(epcSubitem.getEpcSubitem().getId());
					subItemDTO.setRemark(epcSubitem.getRemark());
					subItemDTO.setUnitId(epcUnit.getId());
					subItemDTO.setUnitName(epcUnit.getName());
					subItemDTO.setRule(epcSubitemRule == null ? 0 : epcSubitemRule.getRule());
					subItemDTO.setPowerRule(epcSubitemRule == null ? 0 : epcSubitemRule.getPowerRule());
					Set<EpcSubitem> set = epcSubitem.getEpcSubitems();
					List<SubItemDTO> list = null;
					if (set != null && set.size() > 0) {
						list = new ArrayList<SubItemDTO>();
						for (EpcSubitem e : set) {
							list.add(this.buildDTOManage(e, epcUnit, "edit", null,null));
						}
					}
					subItemDTO.setChildren(list);
				} else if ("show".equals(type)) {// 用于显示拓扑图
					Set<EpcSubitem> set = epcSubitem.getEpcSubitems();
					List<SubItemDTO> list = null;
					if (set != null && set.size() > 0) {
						list = new ArrayList<SubItemDTO>();
						for (EpcSubitem e : set) {
							list.add(this.buildDTO(e, epcUnit, "show", date,
									dateType));
						}
					}
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
							int year = Integer.parseInt(date);
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
				}

			}
			return subItemDTO;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	private SubItemDTO buildDTO(EpcSubitem epcSubitem, EpcUnit epcUnit,
			String type, String date, String dateType) {
		try {
			SubItemDTO subItemDTO = null;
			List<BasItem> basItems = new ArrayList<BasItem>();
			if (epcSubitem != null) {
				EpcSubitemRule epcSubitemRule = subitemRuleService
						.getEpcSubitemRule(epcUnit.getId(), epcSubitem.getId());
				
				//代权限操作
				List<Long> ids =this.findChildrenItems_R(epcSubitem.getId(),epcUnit.getId());				
				 basItems = itemService.findBasItems(ids, epcUnit.getId());				
				subItemDTO = new SubItemDTO();
				subItemDTO.setId(epcSubitem.getId());
				subItemDTO.setName(epcSubitem.getName());
				subItemDTO.setBasItems(this.buildDTO(basItems));				
				if ("edit".equals(type)) {// 用于维护操作
					if (epcSubitem.getEpcSubitem() != null)
						subItemDTO.setParentId(epcSubitem.getEpcSubitem().getId());
					subItemDTO.setRemark(epcSubitem.getRemark());
					subItemDTO.setUnitId(epcUnit.getId());
					subItemDTO.setUnitName(epcUnit.getName());
					subItemDTO.setRule(epcSubitemRule == null ? 0 : epcSubitemRule.getRule());
					subItemDTO.setPowerRule(epcSubitemRule == null ? 0 : epcSubitemRule.getPowerRule());
					Set<EpcSubitem> set = epcSubitem.getEpcSubitems();
					List<SubItemDTO> list = null;
					if (set != null && set.size() > 0) {
						list = new ArrayList<SubItemDTO>();
						for (EpcSubitem e : set) {
							list.add(this.buildDTO(e, epcUnit, "edit", null,null));
						}
					}
					subItemDTO.setChildren(list);
				} else if ("show".equals(type)) {// 用于显示拓扑图					
					Set<EpcSubitem> set = epcSubitem.getEpcSubitems();
					List<SubItemDTO> list = null;
					if (set != null && set.size() > 0) {
						list = new ArrayList<SubItemDTO>();
						for (EpcSubitem e : set) {
							list.add(this.buildDTO(e, epcUnit, "show", date,
									dateType));
						}
					}
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
							int year = Integer.parseInt(date);
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
					subItemDTO.setEnergy(this.getEnergy(basItems, date,dateType));
					subItemDTO.setRate(this.getRate(basItems, date, dateType));
				}

			}
			return subItemDTO;
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
				sb.append(" substring(date_time,1,10)='" + date + "' ");
			} else if ("month".equals(dateType)) {
				sb.append(" substring(date_time,1,7)='" + date.substring(0, 7)
						+ "' ");
			} else if ("year".equals(dateType)) {
				sb.append(" substring(date_time,1,4)='" + date.substring(0, 4)
						+ "' ");
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
				sb.append(" substring(date_time,1,10)='" + date + "' ");
			} else if ("month".equals(dateType)) {
				sb.append(" substring(date_time,1,7)='" + date.substring(0, 7)
						+ "' ");
			} else if ("year".equals(dateType)) {
				sb.append(" substring(date_time,1,4)='" + date.substring(0, 4)
						+ "' ");
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

	private List<BasItemDTO> buildDTO(Set<BasItem> set) {
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
	public List<String> findChildrenItems(Long subitemId, Long unitId) {
		// TODO Auto-generated method stub
		List<String> itemIds = null;
		List<Long> ids = this.getChildrenSubitems(subitemId);
		ids.add(subitemId);
		List<BasItem> basItems = itemService.findBasItems(ids, unitId);		
		if (basItems != null) {
			itemIds = new ArrayList<String>();
			for (BasItem bi : basItems) {
				itemIds.add(bi.getId()+"");
			}
		}
		
		return itemIds;
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
	@Transactional
	@Override
	public List<String> findChildrenItems(Long subitemId, Long unitId,String gl) {
		// TODO Auto-generated method stub
		List<String> itemIds = null;
		List<Long> ids = this.getChildrenSubitems(subitemId);
		ids.add(subitemId);
		List<BasItem> basItems = itemService.findBasItems(ids, unitId);		
		if (basItems != null) {
			itemIds = new ArrayList<String>();
			String ss = null;
			String[] sb = null;
			for (BasItem bi : basItems) {
				for(BasItem bi2 :bi.getBasItems() ){
					sb = bi2.getName().split("_");
					ss = sb[sb.length - 1];				
					if (ss.startsWith("GL")) {
						itemIds.add(bi2.getId() + "");
					}
				}
			}			
		}
		
		return itemIds;
	}

	/**
	 * 得到subitem下所有子节点
	 * 
	 * @param subitemId
	 * @return
	 */
	private List<Long> getChildrenSubitems(Long subitemId) {
		List<Long> ids = new ArrayList<Long>();
		EpcSubitem subitem = this.getById(subitemId);
		
		ids.add(subitemId);
		Set<EpcSubitem> set = subitem.getEpcSubitems();
		for (EpcSubitem es : set) {
			ids.add(es.getId());
			ids.addAll(this.getChildrenSubitems(es.getId()));
		}
		
		return ids;
	}

	@Transactional
	@Override
	public boolean updateSubitem(SubItemDTO subitemDTO) {
		if (subitemDTO == null)
			return false;
		EpcSubitemRule epcSubitemRule = null;
		try {
			epcSubitemRule = subitemRuleService.getEpcSubitemRule(subitemDTO
					.getUnitId(), subitemDTO.getId());

		} catch (Exception e) {

		}
		String s="0";
		String gateId = "0";
		try {
			String sql = "select id from bas_gate where unit_id="+subitemDTO.getUnitId();
			
			List l = this.dao.excuteQuerySQL(sql);
			if (l != null && l.size() > 0) {
				s +=",";
				s +=  l.get(0).toString();
			}
			gateId = s;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (epcSubitemRule != null) {
			SubitemRulePK subitemRule = new SubitemRulePK();
			subitemRule.setSubitemId(subitemDTO.getId());
			subitemRule.setUnitId(subitemDTO.getUnitId());
			epcSubitemRule.setId(subitemRule);
			epcSubitemRule.setPowerRule(subitemDTO.getPowerRule());
			epcSubitemRule.setRule(subitemDTO.getRule());
			subitemRuleService.update(epcSubitemRule);
		} else {
			epcSubitemRule = new EpcSubitemRule();
			epcSubitemRule.setEpcSubitem(this.getById(subitemDTO.getId()));
			SubitemRulePK subitemRule = new SubitemRulePK();
			subitemRule.setSubitemId(subitemDTO.getId());
			subitemRule.setUnitId(subitemDTO.getUnitId());
			epcSubitemRule.setId(subitemRule);
			epcSubitemRule.setEpcUnit(unitService.getById(subitemDTO.getUnitId()));
			epcSubitemRule.setPowerRule(subitemDTO.getPowerRule());
			epcSubitemRule.setRule(subitemDTO.getRule());
			subitemRuleService.save(epcSubitemRule);
		}

		EpcSubitem es = this.getById(subitemDTO.getId());
		String d_jpql = "update BasItem t set t.epcSubitem=? where  t.basGate.id in ("+gateId+") and t.epcSubitem.id=?";
		excuteUpdateByParam(d_jpql, new Object[] { null, subitemDTO.getId() });

		StringBuffer u_jpql = new StringBuffer();
		List<BasItemDTO> items = subitemDTO.getBasItems();
		if (items != null) {
			u_jpql.append("update BasItem t set t.epcSubitem=? where  t.id=-1 ");
			
			for (BasItemDTO bid : items) {
				u_jpql.append(" or t.id = " + bid.getId());
				
			}
			
			excuteUpdateByParam(u_jpql.toString(), new Object[] { es });
		}
		///查询更新一组数据 如：001@IDCS Client@DB14%
		return true;
	}
}
