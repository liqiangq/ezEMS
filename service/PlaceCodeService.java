package com.thtf.service;

import java.util.List;
import java.util.Map;

import com.thtf.dto.PlaceCodeDTO;
import com.thtf.dto.UnitDTO;
import com.thtf.entity.EpcPlaceCode;


public interface PlaceCodeService extends BasicService<EpcPlaceCode, Long> {
	
	boolean savePlaceCode(PlaceCodeDTO placeCode);
	
	 List<PlaceCodeDTO> getAll() ;
	 
	 EpcPlaceCode getByid(String id);
	 

}
