package com.thtf.service.imp;

import java.util.ArrayList;

import java.util.List;


import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.transaction.annotation.Transactional;


import com.thtf.dto.PlaceCodeDTO;

import com.thtf.entity.EpcPlaceCode;

import com.thtf.service.PlaceCodeService;

@RemotingDestination
public class PlaceCodeServiceImp extends BasicServiceImp<EpcPlaceCode, Long> implements
		PlaceCodeService {

	@Transactional
	@Override
	public boolean savePlaceCode(PlaceCodeDTO placeCode) {
		EpcPlaceCode epcPlaceCode = new EpcPlaceCode();
		epcPlaceCode.setCode(placeCode.getCode());
		epcPlaceCode.setName(placeCode.getName());
		this.save(epcPlaceCode);		
		return true;
	}
	
	@Transactional
	@Override
	public List<PlaceCodeDTO> getAll() {
		List<PlaceCodeDTO> placeCodeDTOList = new ArrayList<PlaceCodeDTO>();
		//System.out.println("placeCodeDTOList::getAll()");
		List<EpcPlaceCode> epcPlaceCodeList = this.getAll("asc", "code");		
 		for(int i=0;i<epcPlaceCodeList.size();i++){
 			EpcPlaceCode epcPlaceCode = epcPlaceCodeList.get(i);
 			placeCodeDTOList.add(buildDTO(epcPlaceCode));
		}
 		//System.out.println("placeCodeDTOList::"+placeCodeDTOList);
		return placeCodeDTOList;
	}

	private PlaceCodeDTO buildDTO(EpcPlaceCode epcPlaceCode) {
		PlaceCodeDTO placeCodeDTO = null;
		if(epcPlaceCode != null){
			placeCodeDTO = new PlaceCodeDTO();
			placeCodeDTO.setCode(epcPlaceCode.getCode());
			placeCodeDTO.setName(epcPlaceCode.getName());
			
		}
		return placeCodeDTO;
		
	}

	@Transactional
	@Override
	public EpcPlaceCode getByid(String id) {
		
		String hql = " from EpcPlaceCode t where t.code='"+id+"'";
		StringBuffer sb = new StringBuffer();
		
		List<EpcPlaceCode> bis = super.getByQuery(hql, null);
		EpcPlaceCode p=  new EpcPlaceCode();
		for (EpcPlaceCode epcPlaceCode : bis) {
			p = epcPlaceCode;
		}
		return p;
	}




	

}
