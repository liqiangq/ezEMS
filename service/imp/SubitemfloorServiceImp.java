package com.thtf.service.imp;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.transaction.annotation.Transactional;

import com.thtf.dto.BasItemDTO;
import com.thtf.dto.SubItemDTO;
import com.thtf.dto.SubItemFloorAssistDTO;
import com.thtf.entity.BasItem;

import com.thtf.entity.EpcSubitemRule;
import com.thtf.entity.EpcSubitemfloor;
import com.thtf.entity.EpcSubitemfloorAssist;
import com.thtf.service.ItemService;
import com.thtf.service.SubitemfloorAssistService;
import com.thtf.service.SubitemfloorService;
import com.thtf.service.UnitService;
import com.thtf.util.DateUtil;

/**
 * @author 孙刚 E-mail:sungang01@thtf.com.cn
 * @version 创建时间：2010-9-3 上午10:39:05 类说明
 */
@RemotingDestination
public class SubitemfloorServiceImp extends
		BasicServiceImp<EpcSubitemfloor, Long> implements SubitemfloorService {
	private ItemService itemService;
	private UnitService unitService;
	private SubitemfloorAssistService subitemFloorAssistService;

	private List<BasItemDTO> basItems;
	private List<String> list;

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

	public void setSubitemFloorAssistService(
			SubitemfloorAssistService subitemFloorAssistService) {
		this.subitemFloorAssistService = subitemFloorAssistService;
	}

	@Override
	public boolean deleteSubitem(Long id) {
		// TODO Auto-generated method stub
		super.deleteById(id);
		subitemFloorAssistService.deleteSubitemfloorAssist(id);
		return true;
	}

	@Transactional
	@Override
	public List<SubItemDTO> findTree(Long unitId, String date, String dateType) {
		// TODO Auto-generated method stub
		List<SubItemDTO> ls = new ArrayList<SubItemDTO>();
		SubItemDTO s = null;
		String jpql = "from EpcSubitemfloor t where t.epcUnit.id=? and t.epcSubitemfloor is null";
		List<EpcSubitemfloor> list = super.getByQuery(jpql,
				new Object[] { unitId });
		if (list != null && list.size() > 0) {
			for (EpcSubitemfloor e : list) {
				s = this.buildDTO(e, "show", date, dateType);
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
		String jpql = "from EpcSubitemfloor t where t.epcUnit.id=? and t.epcSubitemfloor is null";
		List<EpcSubitemfloor> list = super.getByQuery(jpql,
				new Object[] { unitId });
		if (list != null && list.size() > 0) {
			for (EpcSubitemfloor e : list) {
				s = this.buildDTO(e, "edit", null, null);
				ls.add(s);
			}
		}
		return ls;
	}

	@Transactional
	@Override
	public boolean saveSubitem(SubItemDTO subitemDTO) {
		// TODO Auto-generated method stub
		EpcSubitemfloor es = buildEntity(subitemDTO);
		es.setId(null);
		save(es);
		StringBuffer u_jpql = new StringBuffer();
		List<BasItemDTO> items = subitemDTO.getBasItems();
		if (items != null) {
			u_jpql
					.append("update BasItem t set t.epcSubitemfloor=? where t.id=-1 ");
			for (BasItemDTO bid : items) {
				u_jpql.append(" or t.id = " + bid.getId());
			}
			excuteUpdateByParam(u_jpql.toString(), new Object[] { es });
		}
		return true;
	}

	@Transactional
	@Override
	public boolean saveSubItemFloorAssist(SubItemDTO subitemDTO,
			SubItemFloorAssistDTO subItemFloorAssistDTO) {
		EpcSubitemfloorAssist efa = buildEntityAssist(subItemFloorAssistDTO);
		EpcSubitemfloor es = buildEntity(subitemDTO);
		efa.setId(null);
		efa.setEpcSubitemfloor(es);
		Set<EpcSubitemfloorAssist> set = new HashSet<EpcSubitemfloorAssist>();
		set.add(efa);
		es.setEpcSubitemfloorAssists(set);
		es.setId(null);
		super.save(es);
		return true;
	}

	@Transactional
	@Override
	public boolean updateSubitemAssist(SubItemDTO subitemDTO,
			SubItemFloorAssistDTO subItemFloorAssistDTO) {
		// TODO Auto-generated method stub
		System.out.println("subitemDTO" + subitemDTO + "subItemFloorAssistDTO"
				+ subItemFloorAssistDTO);
		if (subitemDTO == null)
			return false;
		EpcSubitemfloor es = this.getById(subitemDTO.getId());
		es.setRule(subitemDTO.getRule());
		es.setPowerRule(subitemDTO.getPowerRule());
		es.setName(subitemDTO.getName());
		es.setRemark(subitemDTO.getRemark());
		String d_jpql = "update BasItem t set t.epcSubitemfloor=? where t.epcSubitemfloor.id=?";
		excuteUpdateByParam(d_jpql, new Object[] { null, subitemDTO.getId() });
		StringBuffer u_jpql = new StringBuffer();
		List<BasItemDTO> items = subitemDTO.getBasItems();
		if (items != null) {
			u_jpql
					.append("update BasItem t set t.epcSubitemfloor=? where t.id=-1 ");
			for (BasItemDTO bid : items) {
				u_jpql.append(" or t.id = " + bid.getId());
			}
			excuteUpdateByParam(u_jpql.toString(), new Object[] { es });
		}

		if (subItemFloorAssistDTO != null) {
			EpcSubitemfloorAssist efa = buildEntityAssist(subItemFloorAssistDTO);
			efa.setEpcSubitemfloor(es);
			Set<EpcSubitemfloorAssist> set = new HashSet<EpcSubitemfloorAssist>();
			set.add(efa);
			es.setEpcSubitemfloorAssists(set);
			super.update(es);
		}

		return true;
	}

	@Transactional
	@Override
	public boolean updateSubitem(SubItemDTO subitemDTO) {
		// TODO Auto-generated method stub
		if (subitemDTO == null)
			return false;
		EpcSubitemfloor es = this.getById(subitemDTO.getId());
		es.setRule(subitemDTO.getRule());
		es.setPowerRule(subitemDTO.getPowerRule());
		es.setName(subitemDTO.getName());
		es.setRemark(subitemDTO.getRemark());
		String d_jpql = "update BasItem t set t.epcSubitemfloor=? where t.epcSubitemfloor.id=?";
		excuteUpdateByParam(d_jpql, new Object[] { null, subitemDTO.getId() });
		StringBuffer u_jpql = new StringBuffer();
		List<BasItemDTO> items = subitemDTO.getBasItems();
		if (items != null) {
			u_jpql
					.append("update BasItem t set t.epcSubitemfloor=? where t.id=-1 ");
			for (BasItemDTO bid : items) {
				u_jpql.append(" or t.id = " + bid.getId());
			}
			excuteUpdateByParam(u_jpql.toString(), new Object[] { es });
		}

		return true;
	}

	/**
	 * 组建SubItemDTO
	 * 
	 * @param EpcSubitemfloor
	 *            实体
	 * @param type
	 *            显示类型：show-显示拓扑，edit-用于编辑
	 * @param date
	 *            时间
	 * @param dateType
	 *            时间类型：year，month，day
	 * @return
	 */
	private SubItemDTO buildDTO(EpcSubitemfloor es, String type, String date,
			String dateType) {

		SubItemDTO subItemDTO = null;
		if (es != null) {
			subItemDTO = new SubItemDTO();
			subItemDTO.setId(es.getId());
			subItemDTO.setName(es.getName());
			
			subItemDTO.setBasItems(this.buildDTO(es.getBasItems()));

			if ("edit".equals(type)) {// 用于维护操作
				if (es.getEpcSubitemfloor() != null)
					subItemDTO.setParentId(es.getEpcSubitemfloor().getId());
				subItemDTO.setRemark(es.getRemark());
				subItemDTO.setUnitId(es.getEpcUnit().getId());
				subItemDTO.setUnitName(es.getEpcUnit().getName());
				subItemDTO.setRule(es.getRule()==null?0:es.getRule());
				subItemDTO.setPowerRule(es.getPowerRule()==null?0:es.getPowerRule());
				Set<EpcSubitemfloor> set = es.getEpcSubitemfloors();
				List<SubItemDTO> list = null;
				if (set != null && set.size() > 0) {
					list = new ArrayList<SubItemDTO>();
					for (EpcSubitemfloor e : set) {
						list.add(this.buildDTO(e, "edit", null, null));
					}
				}
				subItemDTO.setChildren(list);
			} else if ("show".equals(type)) {// 用于显示拓扑图
				Set<EpcSubitemfloor> set = es.getEpcSubitemfloors();
				List<SubItemDTO> list = null;
				if (set != null && set.size() > 0) {
					list = new ArrayList<SubItemDTO>();
					for (EpcSubitemfloor e : set) {
						list.add(this.buildDTO(e, "show", date, dateType));
					}
				}
				subItemDTO.setChildren(list);
				if (dateType.equals("month")) {
					int year = Integer.parseInt(date.split("-")[0]);
					int month = Integer.parseInt(date.split("-")[1]);
					int days = DateUtil.days(year, month);
					subItemDTO.setRule((es.getRule()==null?0:es.getRule()) * days);
					subItemDTO.setPowerRule((es.getPowerRule()==null?0:es.getPowerRule()) * days);
				} else if (dateType.equals("year")) {
					int year = Integer.parseInt(date);
					int days = 365;
					if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0)
						days = 366;
					else
						days = 365;
					subItemDTO.setRule((es.getRule()==null?0:es.getRule())  * days);
					subItemDTO.setPowerRule((es.getPowerRule()==null?0:es.getPowerRule()) * days);
				} else {
					subItemDTO.setRule(es.getRule()==null?0:es.getRule());
					subItemDTO.setPowerRule(es.getPowerRule()==null?0:es.getPowerRule());
				}
				
				subItemDTO.setEnergy(this.getEnergy(subItemDTO, date, dateType));
				subItemDTO.setRate(this.getRate(subItemDTO, date, dateType));
			}

		}
		return subItemDTO;
	}

	/**
	 * 根据时间得到电费
	 * 
	 * @param subItemDTO
	 * @param date
	 *            时间
	 * @param dateType
	 *            时间类型：year，month，day
	 * @return
	 */
	private Double getRate(SubItemDTO subItemDTO, String date, String dateType) {
		Double d = null;
		StringBuffer sb = null;
		if (subItemDTO != null) {
			basItems = new ArrayList<BasItemDTO>();
			if (subItemDTO.getBasItems() != null
					&& subItemDTO.getBasItems().size() > 0)
				basItems.addAll(subItemDTO.getBasItems());
			List<SubItemDTO> cl = subItemDTO.getChildren();
			if (cl != null && cl.size() > 0)
				for (SubItemDTO si : cl) {
					this.getChildrenBasItemDTO(si);
				}
			sb = new StringBuffer();
			sb.append("select sum(rate) from epc_stat_qoe where (1=0 ");
			for (BasItemDTO b : basItems) {
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
	private Double getEnergy(SubItemDTO subItemDTO, String date, String dateType) {
		Double d = null;
		StringBuffer sb = null;
		if (subItemDTO != null) {
			basItems = new ArrayList<BasItemDTO>();
			if (subItemDTO.getBasItems() != null
					&& subItemDTO.getBasItems().size() > 0)
				basItems.addAll(subItemDTO.getBasItems());
			List<SubItemDTO> cl = subItemDTO.getChildren();
			if (cl != null && cl.size() > 0)
				for (SubItemDTO si : cl) {
					this.getChildrenBasItemDTO(si);
				}
			sb = new StringBuffer();
			sb.append("select sum(value) from epc_stat_qoe where (1=0 ");
			for (BasItemDTO b : basItems) {
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

	private void getChildrenBasItemDTO(SubItemDTO subItemDTO) {
		if (subItemDTO != null) {
			if (subItemDTO.getBasItems() != null
					&& subItemDTO.getBasItems().size() > 0)
				basItems.addAll(subItemDTO.getBasItems());
			List<SubItemDTO> cl = subItemDTO.getChildren();
			if (cl != null && cl.size() > 0) {
				for (SubItemDTO si : cl) {
					this.getChildrenBasItemDTO(si);
				}
			}
		}
	}

	/**
	 * 组建BasItemDTO列表
	 * 
	 * @param set
	 * @return
	 */
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

	/**
	 * 组建BasItem集合
	 * 
	 * @param list
	 * @return
	 */
	private Set<BasItem> buildEntity(List<BasItemDTO> list) {
		Set<BasItem> set = null;
		if (list != null && list.size() > 0) {
			set = new HashSet<BasItem>();
			String hql = " from BasItem t where t.id in (-1";
			StringBuffer sb = new StringBuffer();
			for (BasItemDTO b : list) {
				sb.append("," + b.getId());
			}
			hql += sb.toString() + ")";
			List<BasItem> bis = itemService.getByQuery(hql, null);
			for (BasItem basItem : bis) {
				set.add(basItem);
			}
		}
		return set;
	}

	/**
	 * 组建EpcSubitem
	 * 
	 * @param subItemDTO
	 * @return
	 */
	private EpcSubitemfloor buildEntity(SubItemDTO subItemDTO) {
		EpcSubitemfloor es = null;
		if (subItemDTO != null) {
			es = new EpcSubitemfloor();
			es.setId(subItemDTO.getId());
			es.setName(subItemDTO.getName());
			es.setRemark(subItemDTO.getRemark());
			es.setRule(subItemDTO.getRule());
			es.setPowerRule(subItemDTO.getPowerRule());
			es.setEpcSubitemfloor(super.getById(subItemDTO.getParentId()));
			es.setBasItems(buildEntity(subItemDTO.getBasItems()));
			es.setEpcUnit(unitService.getById(subItemDTO.getUnitId()));
		}
		return es;
	}

	@Transactional
	@Override
	public List<String> findChildrenItems(Long id) {
		// TODO Auto-generated method stub
		EpcSubitemfloor esf = this.getById(id);
		list = new ArrayList<String>();
		Set<BasItem> set = esf.getBasItems();
		if (set != null) {
			for (BasItem b : set) {
				list.add(b.getId().toString());
			}
		}
		this.childrenItems(esf.getEpcSubitemfloors());
		return list;
	}

	private List<String> childrenItems(Set<EpcSubitemfloor> set) {
		if (set != null && set.size() > 0) {
			for (EpcSubitemfloor e : set) {
				Set<BasItem> bs = e.getBasItems();
				if (bs != null) {
					for (BasItem b : bs) {
						list.add(b.getId().toString());
					}
				}
				this.childrenItems(e.getEpcSubitemfloors());
			}
		}
		return list;
	}

	private EpcSubitemfloorAssist buildEntityAssist(
			SubItemFloorAssistDTO subItemFloorAssistDTO) {
		EpcSubitemfloorAssist es = null;
		if (subItemFloorAssistDTO != null) {
			es = new EpcSubitemfloorAssist();
			es.setId(subItemFloorAssistDTO.getId());
			// es.setSubFloorId(subItemFloorAssistDTO.getSubFloorId());
			es.setAirConditionedArea(subItemFloorAssistDTO
					.getAirConditionedArea());
			es.setAirConditioningForm(subItemFloorAssistDTO
					.getAirConditioningForm());
			es.setBuildingFunction(subItemFloorAssistDTO.getBuildingFunction());
			es.setBuildingLayers(subItemFloorAssistDTO.getBuildingLayers());
			es.setBuildingStructure(subItemFloorAssistDTO
					.getBuildingStructure());
			es.setConstructionYears(subItemFloorAssistDTO
					.getConstructionYears());
			es.setExWallRadiator(subItemFloorAssistDTO.getExWallRadiator());
			es.setExWallsForm(subItemFloorAssistDTO.getExWallsForm());
			es.setGlassType(subItemFloorAssistDTO.getGlassType());
			es.setRadiatorArea(subItemFloorAssistDTO.getRadiatorArea());
			es.setRadiatorAreaForm(subItemFloorAssistDTO.getRadiatorAreaForm());
			es.setShapeCoefficient(subItemFloorAssistDTO.getShapeCoefficient());
			es.setTotalArea(subItemFloorAssistDTO.getTotalArea());
			es.setWindowMaterials(subItemFloorAssistDTO.getWindowMaterials());
			es.setWindowsType(subItemFloorAssistDTO.getWindowsType());
		}
		return es;
	}
}
