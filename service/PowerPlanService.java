package com.thtf.service;

import java.util.List;


import com.thtf.dto.PowerPlanDTO;
import com.thtf.entity.EpcPowerPlan;

public interface PowerPlanService extends BasicService<EpcPowerPlan, Long> {
	
	 List<PowerPlanDTO> getAll() ;
	 
	 public boolean savePower(PowerPlanDTO powerPlanDTO) ;
	 
	 public boolean deletePlan(List<Integer> ids);

	 boolean updatePowerPlan(PowerPlanDTO powerPlanDTO);
}
