package com.thtf.quartz.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.thtf.entity.BasGate;
import com.thtf.entity.BasItem;
import com.thtf.entity.EpcItemType;
import com.thtf.quartz.DataAccessObject;
import com.thtf.quartz.DataHisObject;
import com.thtf.service.imp.BasicServiceImp;
import com.thtf.util.DateUtil;
import com.thtf.util.StringUtil;

/**
 * @author 孙刚 E-mail:sungang01@thtf.com.cn
 * @version 创建时间：2011-05-14 上午09:19:29 类说明
 */
public class DataSynService extends BasicServiceImp{

	private static Logger logger = Logger.getLogger(DataSynService.class);
	
	private double rate = 0.5d;
	private Map<String, String> itemTypes = null;
	private List<DataAccessObject> dataAccessObjects = null;
	
	private TargetDataSynService targetDataSynService;
	
	public void setTargetDataSynService(TargetDataSynService targetDataSynService) {
		this.targetDataSynService = targetDataSynService;
	}

	public void synAllDataEveryHour() {
		Date date = new Date();
		this.insertData(date);
		targetDataSynService.deleteDataAccess(dataAccessObjects);
	}
	
	@Transactional
	private void insertData(Date date){
		itemTypes = targetDataSynService.getEpcItemType();
		insertGate();
		insertItem();
//		this.insertFakeData(date);
		dataAccessObjects = targetDataSynService.getDataAccess(date);
		if(dataAccessObjects != null){
			for (DataAccessObject da : dataAccessObjects) {
				if(targetDataSynService.checkTimestamp(da)){
					rate = this.getRateByItemType(date, da.getGateId());
					this.getBefDataHisObjectsSum(da);
					this.getAftDataHisObjectsSum(da);
					this.getDataHisObjectsAVG(da);
					logger.info("insertData:----insert----getTimestamp:"+da.getTimestamp()+"----getGateId:"+da.getGateId());
				}
			}
			logger.info("insertData:----dataAccessObjects.size():"+dataAccessObjects.size()+"----timestamp:"+date);
		}
	}

	private void insertGate(){
		List<Object[]> gates = targetDataSynService.getGate();
		if(gates!=null){
			BasGate basGate = null;
			for(Object[] o:gates){
				String jpql = "from BasGate t where t.projectId = ?";
				List<BasGate> list = this.getByQuery(jpql, new Object[]{o[0]+""});
				if(list!=null&&list.size()>0){
					break;
				}
				basGate = new BasGate();
				basGate.setName(o[2]+"");
				basGate.setGateDesc(o[1]+"");
				basGate.setProjectId(o[0]+"");
				this.save(basGate);
			}
		}
	}
	private void insertItem(){
		List<Object[]> itemsDD = targetDataSynService.getItemDD();
		List<Object[]> itemsOther = targetDataSynService.getItemOther();
		String jpql = " select count(t.id) from BasItem t where t.name = ?";
		if(itemsDD!=null){
			BasItem basItem = null;
			for(Object[] o : itemsDD){
				List<Long> list = this.getByQuery(jpql, new Object[]{o[0]+""});
				if(list!=null&&list.size()>0){
					if(list.get(0)>0){
						logger.info("insertItem:basItem true:"+o[0]);
					}else{
						basItem = new BasItem();
						basItem.setBasGate(this.getBasGate(o[2]+""));
						basItem.setBasItemDesc(o[1]+"");
						if((o[0]+"").indexOf("_DD")>-1){
							basItem.setEpcItemType(this.getEpcItemType("DD"));
						}else if((o[0]+"").indexOf("_DL")>-1){
							basItem.setEpcItemType(this.getEpcItemType("DL"));
						}else if((o[0]+"").indexOf("_DY")>-1){
							basItem.setEpcItemType(this.getEpcItemType("DY"));
						}else if((o[0]+"").indexOf("_GL")>-1){
							basItem.setEpcItemType(this.getEpcItemType("GL"));
						}
						basItem.setName(o[0]+"");
						this.save(basItem);
						logger.info("insertItem:basItem false:"+basItem.getName());
					}
				}
			}
			System.out.println("同步basItem 数量 ："+itemsDD.size());
		}
		
		if(itemsOther!=null){
			BasItem basItem = null;
			BasItem basItemChild = null;
			for(Object[] o : itemsOther){
				String s = o[0]+"";
				List<Long> list = this.getByQuery(jpql, new Object[]{o[0]+""});
				if(list!=null&&list.size()>0){
					if(list.get(0)>0){
						logger.info("insertItem:basItemChild true:"+o[0]);
					}else{
						String jp = "from BasItem t where t.name like '"+s.substring(0, s.indexOf("_"))+"'";
						List<BasItem> ls = this.getByQuery(jpql, null);
						if(ls!=null&&ls.size()>0){
							basItem = ls.get(0);
							
							basItemChild = new BasItem();
							basItemChild.setBasGate(basItem.getBasGate());
							basItemChild.setBasItemDesc(o[1]+"");
							if((o[0]+"").indexOf("_DD")>-1){
								basItemChild.setEpcItemType(this.getEpcItemType("DD"));
							}else if((o[0]+"").indexOf("_DL")>-1){
								basItemChild.setEpcItemType(this.getEpcItemType("DL"));
							}else if((o[0]+"").indexOf("_DY")>-1){
								basItemChild.setEpcItemType(this.getEpcItemType("DY"));
							}else if((o[0]+"").indexOf("_GL")>-1){
								basItemChild.setEpcItemType(this.getEpcItemType("GL"));
							}
							basItemChild.setName(o[0]+"");
							basItemChild.setBasItem(basItem);
							this.save(basItemChild);
							logger.info("insertItem:basItemChild false:"+s+"------basItem true:"+basItem.getName());
						}else{
							logger.info("insertItem:basItemChild false:"+s+"------basItem false");
						}
					}
				}
			}
			System.out.println("同步basItemChild 数量 ："+itemsOther.size());
		}
	}
	
	private EpcItemType getEpcItemType(String code){
		EpcItemType itemType = null;
		String jpql = "from EpcItemType t where t.code = '"+code+"'";
		List<EpcItemType> list = this.getByQuery(jpql, null);
		if(list!=null&&list.size()>0){
			itemType = list.get(0);
		}
		return itemType;
	}
	
	private BasGate getBasGate(String projectId){
		BasGate basGate = null;
		String jpql = "from BasGate t where t.projectId = '"+projectId+"'";
		List<BasGate> list = this.getByQuery(jpql, null);
		if(list!=null&&list.size()>0){
			basGate = list.get(0);
		}
		return basGate;
	}
	
	private void insertFakeData(Date date){
		DataHisObject dataHisObject = null;
		String excuteSQL = "";
		String jpql = "SELECT t.name FROM BasItem t";
		List<String> list = this.getByQuery(jpql, null);
		if(list!=null){
			for(String o : list){
				if(!StringUtil.isNull(o)){
					dataHisObject = new DataHisObject();
					
					if(o.indexOf("_DD")>-1){
						dataHisObject.setTableCode(itemTypes.get("DD"));
					}else if(o.indexOf("_DL")>-1){
						dataHisObject.setTableCode(itemTypes.get("DL"));
					}else if(o.indexOf("_DY")>-1){
						dataHisObject.setTableCode(itemTypes.get("DY"));
					}else if(o.indexOf("_GL")>-1){
						dataHisObject.setTableCode(itemTypes.get("GL"));
					}else{
						break;
					}
					
					dataHisObject.setDataTime(DateUtil.date2String(date, "yyyy-MM-dd HH"));
					dataHisObject.setError("fake");
					dataHisObject.setItemName(o);
					dataHisObject.setMax(0d);
					dataHisObject.setMin(0d);
					dataHisObject.setRate(0d);
					dataHisObject.setValue(0d);
					excuteSQL = this.buildFakeInsertSQL(dataHisObject);
					this.excuteUpdateSQL(excuteSQL);
//					this.insertLog(dataHisObject);
				}
			}
			logger.info("insertFakeData:----date:"+date+"-----items:"+list.size());
		}
	}
	
	private double getRateByItemType(Date date,String projectId) {
		double d = 0.5d;
		String sql = "SELECT epc_power_rate.RATE FROM bas_gate Inner Join epc_unit ON bas_gate.UNIT_ID = epc_unit.ID Inner Join epc_power_plan ON epc_unit.PLACE_ID = epc_power_plan.PLACE_ID Inner Join epc_power_rate ON epc_power_plan.ID = epc_power_rate.PLAN_ID WHERE bas_gate.PROJECTID = '"
				+ projectId
				+ "' and epc_power_rate.BEGIN <  '"
				+ DateUtil.date2String(date, "HH:mm:ss")
				+ "' AND epc_power_rate.END >  '"
				+ DateUtil.date2String(date, "HH:mm:ss")
				+ "' AND epc_power_plan.BEGIN <  '"
				+ DateUtil.date2String(date, "yyyy-MM-dd")
				+ "' AND epc_power_plan.END >  '"
				+ DateUtil.date2String(date, "yyyy-MM-dd") + "'";
		List list = this.excuteQuerySQL(sql);
		if(list!=null&&list.size()>0){
			d = Double.parseDouble(list.get(0)+"");
		}
		return d;
	}

	
	private void getBefDataHisObjectsSum(DataAccessObject da) {
		String sql = targetDataSynService.getBefDataHisObjectsSum(da);
		this.excuteDataSyn(sql, da,"BEF");
	}
	
	private void getAftDataHisObjectsSum(DataAccessObject da) {
		String sql = targetDataSynService.getAftDataHisObjectsSum(da);
		if(sql!=null)
			this.excuteDataSyn(sql, da,"NOW");
	}
	
	private void getDataHisObjectsAVG(DataAccessObject da) {
		String sql = targetDataSynService.getDataHisObjectsAVG(da);
		this.excuteDataSyn(sql, da,"NOW");
	}
	
	private void excuteDataSyn(String sql,DataAccessObject da,String dateType){
		List<Object[]> result = targetDataSynService.excuteQuerySQL(sql);
		String excuteSQL = null;
		DataHisObject dho = null;
		for (Object[] o : result) {
			dho = new DataHisObject();
			
			if(((String)o[0]).indexOf("_DD")>-1){
				dho.setTableCode(itemTypes.get("DD"));
			}else if(((String)o[0]).indexOf("_DL")>-1){
				dho.setTableCode(itemTypes.get("DL"));
			}else if(((String)o[0]).indexOf("_DY")>-1){
				dho.setTableCode(itemTypes.get("DY"));
			}else if(((String)o[0]).indexOf("_GL")>-1){
				dho.setTableCode(itemTypes.get("GL"));
			}else{
				break;
			}
			
			if("BEF".equals(dateType)){
				dho.setDataTime(DateUtil.string2String(da.getBefTimestamp(),
						"yyyyMMddHH", "yyyy-MM-dd HH"));
			}else if("NOW".equals(dateType)){
				dho.setDataTime(DateUtil.string2String(da.getTimestamp(),
						"yyyyMMddHH", "yyyy-MM-dd HH"));
			}
			
			dho.setError(o[2] + "");
			if(o[0]!=null){
				String itemName = o[0]+"";
				dho.setItemName(itemName.substring(0, itemName.length()-6)+ "");
			}
			dho.setMax(0d);
			dho.setMin(0d);
			dho.setValue(Double.valueOf(o[1] + ""));
			dho.setRate(rate*dho.getValue());
			if(checkInsertOrUpdate(dho)){
				excuteSQL = this.buildUpdateSQL(dho);
			}else{
				excuteSQL = this.buildInsertSQL(dho);
			}
			this.excuteUpdateSQL(excuteSQL);
			this.insertLog(dho);
			
			logger.info("excuteDataSyn:----item:"+dho.getItemName()+"-----value:"+Double.valueOf(o[1] + "")+"-----Timestamp"+dho.getDataTime()+"----dateType:"+dateType);
		}
	}

	private boolean checkInsertOrUpdate(DataHisObject dho) {
		String sql = "select count(id) from " + dho.getTableCode()
				+ " where item_name='" + dho.getItemName()
				+ "' and date_time='" + dho.getDataTime() + "'";
		List list = this.excuteQuerySQL(sql);
		if(list!=null&&list.size()==1){
			if(Integer.parseInt(list.get(0)+"")>0){
				return true; //update
			}
		}
		return false; //insert
	}

	private String buildUpdateSQL(DataHisObject dho){
		StringBuffer sql = new StringBuffer();
		if (dho != null) {
			sql.append("update ");
			sql.append(dho.getTableCode());
			sql.append(" set value=");
			sql.append(dho.getValue());
			sql.append(", max = ");
			sql.append(dho.getMax());
			sql.append(", min =");
			sql.append(dho.getMin());
			sql.append(", error ='");
			sql.append(dho.getError());
			sql.append("'");
			if (dho.getTableCode().equals("epc_stat_qoe")&&dho.getRate() != null) {
				sql.append(", rate =");
				sql.append(dho.getRate());
			}
			sql.append(" where item_name='");
			sql.append(dho.getItemName());
			sql.append("'");
			sql.append(" and date_time='");
			sql.append(dho.getDataTime());
			sql.append("'");
		}
		return sql.toString();
	}
	
	private String buildFakeInsertSQL(DataHisObject dho) {
		StringBuffer sql = new StringBuffer();
		if (dho != null) {
			sql.append("insert into ");
			sql.append(dho.getTableCode());
			sql.append(" (item_name,date_time,value,max,min,error");
			if (dho.getTableCode().equals("epc_stat_qoe")&&dho.getRate() != null) {
				sql.append(",rate");
			}
			sql.append(") ");
			sql.append(" select ");
			
			sql.append("'");
			sql.append(dho.getItemName());
			sql.append("','");
			sql.append(dho.getDataTime());
			sql.append("',");
			sql.append(dho.getValue());
			sql.append(",");
			sql.append(dho.getMax());
			sql.append(",");
			sql.append(dho.getMin());
			sql.append(",'");
			sql.append(dho.getError());
			sql.append("'");
			if (dho.getTableCode().equals("epc_stat_qoe")&&dho.getRate() != null) {
				sql.append(",");
				sql.append(dho.getRate());
			}
			
			sql.append(" from dual where not exists (select * from ");
			sql.append(dho.getTableCode());
			sql.append(" where item_name='");
			sql.append(dho.getItemName());
			sql.append("' and date_time='");
			sql.append(dho.getDataTime());
			sql.append("' and value=");
			sql.append(dho.getValue());
			;sql.append(" and max=");
			sql.append(dho.getMax());
			sql.append(" and min=");
			sql.append(dho.getMin());
			sql.append(" and error='");
			sql.append(dho.getError());
			sql.append("'");
			if (dho.getTableCode().equals("epc_stat_qoe")&&dho.getRate() != null) {
				sql.append(" and rate=");
				sql.append(dho.getRate());
			}
			sql.append(")");

		}
		return sql.toString();
	}
	
	private String buildInsertSQL(DataHisObject dho) {
		StringBuffer sql = new StringBuffer();
		if (dho != null) {
			sql.append("insert into ");
			sql.append(dho.getTableCode());
			sql.append(" (item_name,date_time,value,max,min,error");
			if (dho.getTableCode().equals("epc_stat_qoe")&&dho.getRate() != null) {
				sql.append(",rate");
			}
			sql.append(") values ('");
			sql.append(dho.getItemName());
			sql.append("','");
			sql.append(dho.getDataTime());
			sql.append("',");
			sql.append(dho.getValue());
			sql.append(",");
			sql.append(dho.getMax());
			sql.append(",");
			sql.append(dho.getMin());
			sql.append(",'");
			sql.append(dho.getError());
			sql.append("'");

			if (dho.getTableCode().equals("epc_stat_qoe")&&dho.getRate() != null) {
				sql.append(",");
				sql.append(dho.getRate());
			}
			sql.append(")");

		}
		return sql.toString();
	}
	
	private void insertLog(DataHisObject dho) {
		String sql = "insert into epc_statlog (TABLE_NAME ,INSTER_TIME,TYPE) values ('"
				+ dho.getTableCode()
				+ "','"
				+ dho.getDataTime()
				+ "',";
				if(dho.getError().equals("FAKE")){
					sql+="-1)";
				}else{
					sql+="0)";
				}
				
		this.excuteUpdateSQL(sql);
	}
}
