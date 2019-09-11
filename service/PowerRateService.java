package com.thtf.service;


import java.util.List;


import com.thtf.dto.PowerRateDTO;
import com.thtf.entity.EpcPowerRate;

public interface PowerRateService extends BasicService<EpcPowerRate, Long> {
	
	public List<PowerRateDTO> getAll() ;
	 
	public List<PowerRateDTO> getAllByplanId(Long planId) ;
	
	public boolean saveRate(PowerRateDTO powerRateDTO) ;
	
	public boolean delbyplanid(Long planid);
	public boolean deleteByid(Long id);

	boolean deletePowerRates(List<String> ids);
}
