package com.thtf.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.transaction.annotation.Transactional;
import com.thtf.dto.PowerPlanDTO;
import com.thtf.dto.UserDTO;
import com.thtf.entity.EpcPlaceCode;
import com.thtf.entity.EpcPowerPlan;
import com.thtf.entity.EpcUser;
import com.thtf.service.PlaceCodeService;
import com.thtf.service.PowerPlanService;
import com.thtf.service.PowerRateService;
@RemotingDestination
public class PowerPlanServiceImp extends BasicServiceImp<EpcPowerPlan, Long> implements
		PowerPlanService {
	private PlaceCodeService placeCodeService;
	private PowerRateService powerRateService;
	
	public PlaceCodeService getPlaceCodeService() {
		return placeCodeService;
	}

	public void setPlaceCodeService(PlaceCodeService placeCodeService) {
		this.placeCodeService = placeCodeService;
	}

	public PowerRateService getPowerRateService() {
		return powerRateService;
	}

	public void setPowerRateService(PowerRateService powerRateService) {
		this.powerRateService = powerRateService;
	}

	@Transactional
	@Override
	public List<PowerPlanDTO> getAll() {
		List<PowerPlanDTO> powerPlanDTOList = new ArrayList<PowerPlanDTO>();
		
		 List<EpcPowerPlan> epcPowerPlanList = this.getAll("asc", "id");		
	 		for(int i=0;i<epcPowerPlanList.size();i++){
	 			EpcPowerPlan epcPowerPlan = epcPowerPlanList.get(i);
	 			powerPlanDTOList.add(buildDTO(epcPowerPlan));
			}
			return powerPlanDTOList;
	
	}
	
	private PowerPlanDTO buildDTO(EpcPowerPlan epcPowerPlan) {
		PowerPlanDTO powerPlanDTO = null;
		if(epcPowerPlan != null){
			powerPlanDTO = new PowerPlanDTO();
			powerPlanDTO.setId(epcPowerPlan.getId());
			powerPlanDTO.setName(epcPowerPlan.getName());
			EpcPlaceCode epcPlaceCode= epcPowerPlan.getEpcPlaceCode();
			powerPlanDTO.setPlaceid(epcPlaceCode.getCode());
			powerPlanDTO.setPlaceName(epcPlaceCode.getName());
			powerPlanDTO.setBegin(epcPowerPlan.getBegin());
			powerPlanDTO.setEnd(epcPowerPlan.getEnd());
		}
		return powerPlanDTO;
		
	}

	@Transactional
	@Override
	public boolean savePower(PowerPlanDTO powerPlanDTO) {
		EpcPowerPlan p = this.buildEntity(powerPlanDTO);
		p.setId(null);
		super.save(p);
		return true;


	}
	
	@Transactional
	@Override
	public boolean updatePowerPlan(PowerPlanDTO powerPlanDTO) {
		EpcPowerPlan powerPlan = this.buildEntity(powerPlanDTO);
		super.update(powerPlan);
		return true;
	}


	
	private EpcPowerPlan buildEntity(PowerPlanDTO powerPlanDTO){
		EpcPowerPlan epcPowerPlan = null;
		if(powerPlanDTO != null){
			epcPowerPlan = new EpcPowerPlan();
			epcPowerPlan.setId(powerPlanDTO.getId());
			epcPowerPlan.setName(powerPlanDTO.getName());
			epcPowerPlan.setBegin(powerPlanDTO.getBegin());
			epcPowerPlan.setEnd(powerPlanDTO.getEnd());
			epcPowerPlan.setEpcPlaceCode(placeCodeService.getByid(powerPlanDTO.getPlaceid()));			
		}
		return epcPowerPlan;
		
	}
	@Transactional
	@Override
	public boolean deletePlan(List<Integer> ids) {
		for (int i = 0; i < ids.size(); i++) {
			Long id = ids.get(i).longValue();
			powerRateService.delbyplanid(id);
			super.deleteById(id);
		}
		
		return true;
	}

	
	


}
