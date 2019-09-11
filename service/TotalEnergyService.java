package com.thtf.service;

import java.util.List;

import com.thtf.dto.SubItemDTO;

public interface TotalEnergyService extends BasicService {
	//总能耗
	Double totalEnergy(String date,String dateType,Long unitId);
	
	//Double totalEnergy2( String date, String dateType,Long unitId);
	Double totalEnergyBeforeData( String date, String dateType,Long unitId);
	//相对日期的增加或减少
	Double totalContemporaryComparison(String date,String dateType,Long unitId);
	
	//总电费
	Double totalRate(String date,String dateType,Long unitId);
	//相对日期的增加或减少
	Double totalContempRate(String date,String dateType,Long unitId);
	
	//平方米能耗
	Double squareMeterPower(String date,String dateType,Long unitId);
	
	Double squareContemporaryComparison(String date,String dateType,Long unitId);
	
	 List<Long> findChildrenItems_R(Long subitemId, Long unitId);
}
