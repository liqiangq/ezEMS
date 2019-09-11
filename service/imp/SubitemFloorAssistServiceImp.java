package com.thtf.service.imp;


import java.util.List;

import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.transaction.annotation.Transactional;

import com.thtf.commons.PageUtil;
import com.thtf.dto.SubItemFloorAssistDTO;
import com.thtf.entity.EpcSubitemfloorAssist;
import com.thtf.entity.EpcUser;
import com.thtf.service.SubitemfloorAssistService;
import com.thtf.service.SubitemfloorService;
@RemotingDestination
public class SubitemFloorAssistServiceImp extends
BasicServiceImp<EpcSubitemfloorAssist, Long> implements SubitemfloorAssistService {
	private SubitemfloorService subitemfloorService;

	public SubitemfloorService getSubitemfloorService() {
		return subitemfloorService;
	}

	public void setSubitemfloorService(SubitemfloorService subitemfloorService) {
		this.subitemfloorService = subitemfloorService;
	}

	@Override
	public boolean deleteSubitemfloorAssist(Long id) {
		// TODO Auto-generated method stub
		String sql ="DELETE FROM epc_subitemfloor_assist WHERE SUB_FLOOR_ID="+id;
		super.excuteUpdateSQL(sql);
		return true;
	}
	
	
	@Transactional
	@Override
	public boolean saveSubitemAssist(SubItemFloorAssistDTO subItemFloorAssistDTO) {
		EpcSubitemfloorAssist es =  buildEntityAssist(subItemFloorAssistDTO);
		es.setId(null);
		save(es);	
		return true;
	}
	private EpcSubitemfloorAssist buildEntityAssist(SubItemFloorAssistDTO subItemFloorAssistDTO) {
		EpcSubitemfloorAssist es = null;
		if (subItemFloorAssistDTO != null) {			
			es = new EpcSubitemfloorAssist();
			es.setId(subItemFloorAssistDTO.getId());
//			es.setSubFloorId(subItemFloorAssistDTO.getSubFloorId());
			es.setAirConditionedArea(subItemFloorAssistDTO.getAirConditionedArea());
			es.setAirConditioningForm(subItemFloorAssistDTO.getAirConditioningForm());
			es.setBuildingFunction(subItemFloorAssistDTO.getBuildingFunction());
			es.setBuildingLayers(subItemFloorAssistDTO.getBuildingLayers());
			es.setBuildingStructure(subItemFloorAssistDTO.getBuildingStructure());
			es.setConstructionYears(subItemFloorAssistDTO.getConstructionYears());
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

	
	@Transactional
	@Override
	public SubItemFloorAssistDTO subItemFloorAssist(Long subFloorId)  {
		// TODO Auto-generated method stub
		
		SubItemFloorAssistDTO s = null;
		String jpql = "from EpcSubitemfloorAssist t where t.epcSubitemfloor.id=? ";
		
		List<EpcSubitemfloorAssist> list = super.getByQuery(jpql, new Object[]{subFloorId});
		if(list!=null&&list.size()>0){
			for (EpcSubitemfloorAssist e : list) {
				s = new SubItemFloorAssistDTO();
				s = this.buildAssistDTO(e);
			}
		}
		return s;
	}
	
	private SubItemFloorAssistDTO buildAssistDTO(EpcSubitemfloorAssist es) {
		SubItemFloorAssistDTO subItemFloorAssistDTO = null;
		if (es != null) {
			subItemFloorAssistDTO = new SubItemFloorAssistDTO();
			subItemFloorAssistDTO.setId(es.getId());

			subItemFloorAssistDTO.setAirConditionedArea(es.getAirConditionedArea());
			subItemFloorAssistDTO.setAirConditioningForm(es.getAirConditioningForm());
			subItemFloorAssistDTO.setBuildingFunction(es.getBuildingFunction());
			subItemFloorAssistDTO.setBuildingLayers(es.getBuildingLayers());
			subItemFloorAssistDTO.setBuildingStructure(es.getBuildingStructure());
			subItemFloorAssistDTO.setConstructionYears(es.getConstructionYears());
			subItemFloorAssistDTO.setExWallRadiator(es.getExWallRadiator());
			subItemFloorAssistDTO.setExWallsForm(es.getExWallsForm());
			subItemFloorAssistDTO.setGlassType(es.getGlassType());
			subItemFloorAssistDTO.setRadiatorArea(es.getRadiatorArea());
			subItemFloorAssistDTO.setRadiatorAreaForm(es.getRadiatorAreaForm());
			subItemFloorAssistDTO.setShapeCoefficient(es.getShapeCoefficient());
			subItemFloorAssistDTO.setTotalArea(es.getTotalArea());
			subItemFloorAssistDTO.setWindowMaterials(es.getWindowMaterials());
			subItemFloorAssistDTO.setWindowsType(es.getWindowsType());			
				}
		return subItemFloorAssistDTO;
	}
	@Override
	public boolean updateSubitem(SubItemFloorAssistDTO subitemFloorAssistDTO) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void delete(EpcSubitemfloorAssist t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll(List<EpcSubitemfloorAssist> list) {
		// TODO Auto-generated method stub

	}


	@Override
	public List<Object[]> excuteQuerySQL(String sql) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int excuteUpdateByParam(String hql, Object[] o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int excuteUpdateSQL(String sql) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<EpcSubitemfloorAssist> getAll(String sortOrder, String orderBy) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageUtil<EpcSubitemfloorAssist> getAllByPagination(int start,
			int pageSize, String sortOrder, String orderBy) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EpcSubitemfloorAssist getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EpcSubitemfloorAssist> getByQuery(String hql, Object[] o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageUtil<EpcSubitemfloorAssist> getByQueryPagination(int start,
			int pageSize, String hql, Object[] o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(EpcSubitemfloorAssist t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveAll(List<EpcSubitemfloorAssist> list) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(EpcSubitemfloorAssist t) {
	//	EpcSubitemfloorAssist epcSubitemfloorAssist = this.buildEntity(t);
//		super.deleteById(epcUser.getId());
//		epcUser.setId(null);
		super.update(t);
	//	return true;

	}

	@Override
	public void updateAll(List<EpcSubitemfloorAssist> list) {
		// TODO Auto-generated method stub

	}

}
