package com.thtf.service.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.transaction.annotation.Transactional;

import com.thtf.dto.BasItemDTO;
import com.thtf.dto.PlaceCodeDTO;
import com.thtf.dto.SubItemDTO;
import com.thtf.dto.UnitDTO;
import com.thtf.dto.UserDTO;
import com.thtf.entity.BasGate;
import com.thtf.entity.BasItem;
import com.thtf.entity.EpcSubitemfloor;
import com.thtf.entity.EpcUnit;
import com.thtf.entity.EpcUser;
import com.thtf.service.PlaceCodeService;
import com.thtf.service.UnitService;

/**
 * @author 孙刚 E-mail:sungang01@thtf.com.cn
 * @version 创建时间：2010-9-2 下午02:49:17 类说明
 */
@RemotingDestination
public class UnitServiceImp extends BasicServiceImp<EpcUnit, Long> implements
		UnitService {
	
	private PlaceCodeService placeCodeService;
	
	public PlaceCodeService getPlaceCodeService() {
		return placeCodeService;
	}

	public void setPlaceCodeService(PlaceCodeService placeCodeService) {
		this.placeCodeService = placeCodeService;
	}

	@Transactional
	@Override
	public List<UnitDTO> getAll() {
		// TODO Auto-generated method stub
		List<UnitDTO> ls = null;
		UnitDTO s = null;
		List<EpcUnit> list = this.getAll("asc", "id");
		if (list != null) {
			ls = new ArrayList<UnitDTO>();
			for (EpcUnit e : list) {
				s = this.buildDTO(e);
				ls.add(s);
			}
		}
		return ls;
	}

	private UnitDTO buildDTO(EpcUnit epcUnit) {
		UnitDTO unitDTO = null;
		if (epcUnit != null) {
			unitDTO = new UnitDTO();
			unitDTO.setId(epcUnit.getId());
			unitDTO.setArea(epcUnit.getArea());
			unitDTO.setName(epcUnit.getName());
			unitDTO.setPlaceId(epcUnit.getEpcPlaceCode().getCode());
			Set<BasGate> set = epcUnit.getBasGates();
			List<String> list = null;
			if (set != null && set.size() > 0) {
				list = new ArrayList<String>();
				for (BasGate e : set) {
					list.add(e.getName());
				}
			}
			unitDTO.setGateNames(list);
		}
		return unitDTO;
	}

	@Transactional
	@Override
	public List<String> findGateName() {
		String sb = null;
		List reList = new ArrayList();
		try {
			sb = "select name from bas_gate where unit_id is null";
			List list = this.dao.excuteQuerySQL(sb);
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					reList.add(list.get(i));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reList;

	}

	@Transactional
	@Override
	public boolean deleteUnits(ArrayList ids) {
		for (int i = 0; i < ids.size(); i++) {
			Long id = Long.parseLong(ids.get(i).toString());
			this.deleteById(id);
		}
		return true;
	}

	@Transactional
	@Override
	public boolean updateUnit(UnitDTO unit) {
		// TODO Auto-generated method stub
		if (unit == null)
			return false;
		EpcUnit eu = this.getById(unit.getId());
		eu.setName(unit.getName());
		eu.setArea(unit.getArea());
		eu.setEpcPlaceCode(placeCodeService.getByid(unit.getPlaceId()));
		this.update(eu);
		List<String> items = unit.getGateNames();
		String sb = null;
		String beginsb = null;
		if (items != null) {
			try {
				beginsb = "update  bas_gate set unit_id=" + null
						+ " where unit_id=" + unit.getId();
				this.dao.excuteUpdateSQL(beginsb);
			} catch (Exception exc) {
				exc.getStackTrace();
			}
			for (int i = 0; i < items.size(); i++) {
				try {
					sb = "update  bas_gate set unit_id=" + unit.getId()
							+ " where name='" + items.get(i) + "'";
					this.dao.excuteUpdateSQL(sb);
				} catch (Exception ex) {
					ex.getStackTrace();
				}
			}

		}
		return true;
	}

	private EpcUnit buildEntity(UnitDTO unit) {
		EpcUnit epcUnit = null;
		if (epcUnit != null) {
			epcUnit = new EpcUnit();
			epcUnit.setId(unit.getId());
			epcUnit.setName(unit.getName());
			epcUnit.setArea(unit.getArea());
		}
		return epcUnit;
	}

	@Transactional
	@Override
	public boolean saveUnit(UnitDTO unit) {
		EpcUnit eu = new EpcUnit();
		eu.setName(unit.getName());
		eu.setArea(unit.getArea());
		eu.setEpcPlaceCode(placeCodeService.getByid(unit.getPlaceId()));
		this.save(eu);

		String sb = null;
		Long unitId = null;

		try {
			sb = "select id from epc_unit where name='" + unit.getName() + "'";
			List list = this.dao.excuteQuerySQL(sb);
			if (list != null && list.size() > 0) {
				unitId = Long.parseLong(list.get(0).toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<String> items = unit.getGateNames();
		String sb1 = null;
		if (items != null) {
			for (int i = 0; i < items.size(); i++) {
				try {
					sb1 = "update  bas_gate set unit_id=" + unitId
							+ " where name='" + items.get(i) + "'";
					this.dao.excuteUpdateSQL(sb1);
				} catch (Exception ex) {
					ex.getStackTrace();
				}
			}
		}
		return true;
	}
	@Transactional
	@Override
	public boolean saveUnitAndPlace(UnitDTO unit, PlaceCodeDTO planCodeDTO) {
		// TODO Auto-generated method stub
		this.saveUnit(unit);
		placeCodeService.savePlaceCode(planCodeDTO);
		return true;
	}

	@Transactional
	@Override
	public List<UnitDTO> findTree(Long userID) {
		// TODO Auto-generated method stub
		String sb = "select id from epc_unit eu where id not in(select unit_id from user_unit where user_id="+userID+")";
		List li = new ArrayList<UnitDTO>();
		System.out.println(sb);
		try {
			List listids = this.dao.excuteQuerySQL(sb);
			if (listids != null) {
				for (int i=0 ;i<listids.size();i++) {
					EpcUnit eu = (EpcUnit)this.getById(Long.parseLong((listids.get(i)).toString()));
					li.add(this.buildDTO(eu));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return li;
	}
	
}
