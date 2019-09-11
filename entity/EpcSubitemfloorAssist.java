package com.thtf.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "epc_subitemfloor_assist", catalog = "epc")
public class EpcSubitemfloorAssist extends BasicObject {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String constructionYears;   
	private String buildingLayers;      
	private String buildingFunction;    
	private String totalArea;           
	private String airConditionedArea;  
	private String radiatorArea;        
	private String airConditioningForm; 
	private String radiatorAreaForm;    
	private String shapeCoefficient;	  
	private String buildingStructure;   
	private String exWallsForm;         
	private String exWallRadiator;	    
	private String windowsType;         
	private String glassType;           
	private String windowMaterials;
	private EpcSubitemfloor epcSubitemfloor;
	
	
	/** default constructor */
	public EpcSubitemfloorAssist() {
	}

	/** full constructor */
	public EpcSubitemfloorAssist(String constructionYears,String buildingLayers,String buildingFunction,    
			String totalArea,String airConditionedArea,String radiatorArea,String airConditioningForm, 
			String radiatorAreaForm,String shapeCoefficient,String buildingStructure,String  exWallsForm,         
			String exWallRadiator,String windowsType,String glassType,String windowMaterials) {
		this.constructionYears = constructionYears;
		this.buildingLayers = buildingLayers;
		this.buildingFunction = buildingFunction;
		this.totalArea = totalArea;
		this.airConditionedArea = airConditionedArea;
		this.radiatorArea = radiatorArea;
		this.airConditioningForm = airConditioningForm;
		this.radiatorAreaForm = radiatorAreaForm;
		this.shapeCoefficient = shapeCoefficient;
		this.buildingStructure = buildingStructure;
		this.exWallsForm = exWallsForm;
		this.exWallRadiator = exWallRadiator;
		this.windowsType = windowsType;
		this.glassType = glassType;
		this.windowMaterials = windowMaterials;
		
	}
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "CONSTRUCTION_YEARS")
	public String getConstructionYears() {
		return constructionYears;
	}
	public void setConstructionYears(String constructionYears) {
		this.constructionYears = constructionYears;
	}
	
	@Column(name = "BUILDING_LAYERS")
	public String getBuildingLayers() {
		return buildingLayers;
	}
	public void setBuildingLayers(String buildingLayers) {
		this.buildingLayers = buildingLayers;
	}
	
	@Column(name = "BUILDINGFUNCTION")
	public String getBuildingFunction() {
		return buildingFunction;
	}
	public void setBuildingFunction(String buildingFunction) {
		this.buildingFunction = buildingFunction;
	}
	
	@Column(name = "TOTALAREA")
	public String getTotalArea() {
		return totalArea;
	}
	public void setTotalArea(String totalArea) {
		this.totalArea = totalArea;
	}
	
	@Column(name = "AIRCONDITIONED_AREA")
	public String getAirConditionedArea() {
		return airConditionedArea;
	}
	public void setAirConditionedArea(String airConditionedArea) {
		this.airConditionedArea = airConditionedArea;
	}
	
	@Column(name = "RADIATOR_AREA")
	public String getRadiatorArea() {
		return radiatorArea;
	}
	public void setRadiatorArea(String radiatorArea) {
		this.radiatorArea = radiatorArea;
	}
	
	@Column(name = "AIRCONDITIONING_FORM")
	public String getAirConditioningForm() {
		return airConditioningForm;
	}
	public void setAirConditioningForm(String airConditioningForm) {
		this.airConditioningForm = airConditioningForm;
	}
	
	@Column(name = "RADIATORAREA_FORM")
	public String getRadiatorAreaForm() {
		return radiatorAreaForm;
	}
	public void setRadiatorAreaForm(String radiatorAreaForm) {
		this.radiatorAreaForm = radiatorAreaForm;
	}
	
	@Column(name = "SHAPE_COEFFICIENT")
	public String getShapeCoefficient() {
		return shapeCoefficient;
	}
	public void setShapeCoefficient(String shapeCoefficient) {
		this.shapeCoefficient = shapeCoefficient;
	}
	
	@Column(name = "BUILDING_STRUCTURE")
	public String getBuildingStructure() {
		return buildingStructure;
	}
	public void setBuildingStructure(String buildingStructure) {
		this.buildingStructure = buildingStructure;
	}
	
	@Column(name = "EXWALLS_FORM")
	public String getExWallsForm() {
		return exWallsForm;
	}
	public void setExWallsForm(String exWallsForm) {
		this.exWallsForm = exWallsForm;
	}
	
	@Column(name = "EXWALL_RADIATOR")
	public String getExWallRadiator() {
		return exWallRadiator;
	}
	public void setExWallRadiator(String exWallRadiator) {
		this.exWallRadiator = exWallRadiator;
	}
	
	@Column(name = "WINDOWS_TYPE")
	public String getWindowsType() {
		return windowsType;
	}
	public void setWindowsType(String windowsType) {
		this.windowsType = windowsType;
	}
	
	@Column(name = "GLASS_TYPE")
	public String getGlassType() {
		return glassType;
	}
	public void setGlassType(String glassType) {
		this.glassType = glassType;
	}
	
	@Column(name = "WINDOW_MATERIALS")
	public String getWindowMaterials() {
		return windowMaterials;
	}
	public void setWindowMaterials(String windowMaterials) {
		this.windowMaterials = windowMaterials;
	}


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SUB_FLOOR_ID")
	public EpcSubitemfloor getEpcSubitemfloor() {
		return epcSubitemfloor;
	}

	public void setEpcSubitemfloor(EpcSubitemfloor epcSubitemfloor) {
		this.epcSubitemfloor = epcSubitemfloor;
	}  
	
	
}
