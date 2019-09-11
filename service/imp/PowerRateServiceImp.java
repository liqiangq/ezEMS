package com.thtf.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.util.DateUtil;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.transaction.annotation.Transactional;

import com.thtf.dto.PowerRateDTO;
import com.thtf.entity.EpcPowerRate;
import com.thtf.service.PowerPlanService;
import com.thtf.service.PowerRateService;

@RemotingDestination
public class PowerRateServiceImp extends BasicServiceImp<EpcPowerRate, Long>
		implements PowerRateService {
	private PowerPlanService powerPlanService;

	public void setPowerPlanService(PowerPlanService powerPlanService) {
		this.powerPlanService = powerPlanService;
	}

	@Transactional
	@Override
	public List<PowerRateDTO> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	@Override
	public List<PowerRateDTO> getAllByplanId(Long planId) {
		String hql = " from EpcPowerRate t where t.epcPowerPlan.id=? ";
		List<PowerRateDTO> ls = new ArrayList<PowerRateDTO>();
		PowerRateDTO s = null;
		List<EpcPowerRate> list = super
				.getByQuery(hql, new Object[] { planId });
		if (list != null && list.size() > 0) {
			for (EpcPowerRate e : list) {
				s = this.buildDTO(e);
				ls.add(s);
			}
		}
		return ls;
	}

	private PowerRateDTO buildDTO(EpcPowerRate epcPowerRate) {
		PowerRateDTO powerRateDTO = null;
		if (epcPowerRate != null) {
			powerRateDTO = new PowerRateDTO();
			powerRateDTO.setId(epcPowerRate.getId());
			powerRateDTO.setRate(epcPowerRate.getRate());
			powerRateDTO.setType(epcPowerRate.getType());
			powerRateDTO.setBegin(epcPowerRate.getBegin());
			powerRateDTO.setEnd(epcPowerRate.getEnd());
			powerRateDTO.setPlanId(epcPowerRate.getEpcPowerPlan().getId());
		}
		return powerRateDTO;
	}

	@Transactional
	@Override
	public boolean saveRate(PowerRateDTO powerRateDTO) {
		EpcPowerRate p = this.buildEntity(powerRateDTO);
		p.setId(null);
		super.save(p);
		this.updateRateForQOE(p,"save");
		return true;
	}

	private EpcPowerRate buildEntity(PowerRateDTO powerRateDTO) {
		EpcPowerRate r = new EpcPowerRate();
		if (powerRateDTO != null) {
			r.setId(powerRateDTO.getId());
			r.setBegin(powerRateDTO.getBegin());
			r.setEnd(powerRateDTO.getEnd());
			r.setType(powerRateDTO.getType());
			r.setRate(powerRateDTO.getRate());
			r.setEpcPowerPlan(powerPlanService
					.getById(powerRateDTO.getPlanId()));
		}
		return r;

	}

	@Transactional
	@Override
	public boolean delbyplanid(Long planid) {
		// TODO Auto-generated method stub
		String sql = "DELETE  from epc_power_rate  where PLAN_ID=" + planid;
		super.excuteUpdateSQL(sql);
		return true;
	}

	@Transactional
	@Override
	public boolean deleteByid(Long id) {
		this.updateRateForQOE(this.getById(id),"delete");
		super.deleteById(id);
		return true;
	}

	@Transactional
	@Override
	public boolean deletePowerRates(List<String> ids) {
		for (int i = 0; i < ids.size(); i++) {
			Long id = Long.parseLong(ids.get(i).toString());
			this.deleteById(id);
		}
		return true;
	}

	private void updateRateForQOE(EpcPowerRate epr,String type) {
		StringBuffer sb = new StringBuffer();
		double rate = epr.getRate();
		String code = epr.getEpcPowerPlan().getEpcPlaceCode().getCode();
		sb.append("update epc_stat_qoe set ");
		if("save".equals(type))
			sb.append("rate = epc_stat_qoe.VALUE*" + rate);
		else if("delete".equals(type)){
			sb.append("rate = epc_stat_qoe.VALUE*" + 0);
		}
		sb.append(" where epc_stat_qoe.ITEM_NAME in (");
		sb.append("SELECT ");
		sb.append(" bas_item.NAME ");
		sb.append(" FROM ");
		sb.append(" bas_item ");
		sb.append(" Left Outer Join bas_gate ON bas_gate.ID = bas_item.GATE_ID ");
		sb.append(" Left Outer Join epc_unit ON epc_unit.ID = bas_gate.UNIT_ID ");
		sb.append(" Left Outer Join epc_place_code ON epc_unit.PLACE_ID = epc_place_code.CODE ");
		sb.append(" Left Outer Join epc_item_type ON bas_item.ITEM_TYPE = epc_item_type.ID ");
		sb.append(" WHERE ");
		sb.append(" epc_place_code.CODE =  '"+code+"' AND ");
		sb.append(" epc_item_type.CODE =  'DD' ");
		sb.append(" )");
		sb.append(" AND ");
		sb.append("( DATE_FORMAT(epc_stat_qoe.DATE_TIME,'%W %M %Y') between '");
		sb.append( DateUtil.formatDate(epr.getEpcPowerPlan().getBegin(), "yyyy-mm-dd"));
		sb.append("' and '");
		sb.append( DateUtil.formatDate(epr.getEpcPowerPlan().getEnd(), "yyyy-mm-dd"));
		sb.append("') AND ");
		sb.append("( DATE_FORMAT(epc_stat_qoe.DATE_TIME,'%H:%i:%s') between '");
		sb.append( DateUtil.formatDate(epr.getBegin(), "hh:MM:ss"));
		sb.append("' and '");
		sb.append( DateUtil.formatDate(epr.getEnd(), "hh:MM:ss"));
		sb.append("')");
		System.out.println(sb.toString());
		this.excuteUpdateSQL(sb.toString());
	}
}
