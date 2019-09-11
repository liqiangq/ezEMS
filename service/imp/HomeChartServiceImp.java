package com.thtf.service.imp;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.transaction.annotation.Transactional;

import com.thtf.dto.UnitDTO;
import com.thtf.service.SubitemService;
import com.thtf.service.SubitemfloorService;
import com.thtf.util.DateUtil;
import com.thtf.util.StringUtil;
import com.thtf.util.ofc.ChartsModuleUtil;
import com.thtf.util.ofc.ElementsModuleUtil;
import com.thtf.util.ofc.ItemObject;
import com.thtf.util.ofc.JSONUtil_OFC;

@RemotingDestination
public class HomeChartServiceImp extends BasicServiceImp implements
		com.thtf.service.HomeChartService {
	 private SubitemService subitemService;	
	    private SubitemfloorService subitemfloorService;
	    
		public void setSubitemService(SubitemService subitemService) {
			this.subitemService = subitemService;
		}
		public void setSubitemfloorService(SubitemfloorService subitemfloorService) {
			this.subitemfloorService = subitemfloorService;
		}

		@Transactional
		@Override
		public String getChartData(String date, String dateType,String dataType, List<String> ids,
			String chartType, String idsType,Long unitId) {
			if(dataType==null){
				dataType="energy";
			}
			List<Map<String, String>> result = new ArrayList<Map<String,String>>();
			if(StringUtil.isNull(date)){
				date = DateUtil.date2String(new Date(), "yyyy-MM-dd");
			}
			if(StringUtil.isNull(dateType)){
				dateType = "day";
			}
			if(StringUtil.isNull(chartType)){
				chartType = "bar";
			}
			if(StringUtil.isNull(idsType)){
				idsType = "item";
			}
			
			List<String> itemIds = null;
			boolean isShowName = true;
			String sql = "";
			String tableName = "epc_stat_qoe";
			if("subitem".equals(idsType)){//分项电量
				isShowName = false;
				if(ids!=null&&ids.size()>0){
					for (String id : ids) {
						itemIds = subitemService.findChildrenItems(Long.parseLong(id),unitId);
						sql = this.buildSQL(date, dateType,tableName, itemIds,isShowName,subitemService.getById(Long.parseLong(id)).getName(),chartType,dataType);
						result.addAll(this.getResultByItemsIds(sql,dataType));
					}
				}
			}else if("subitemfloor".equals(idsType)){//功能区电量
				isShowName = false;
				if(ids!=null&&ids.size()>0){
					for (String id : ids) {
						itemIds = subitemfloorService.findChildrenItems(Long.parseLong(id));
						sql = this.buildSQL(date, dateType,tableName, itemIds,isShowName,subitemfloorService.getById(Long.parseLong(id)).getName(),chartType,dataType);
						result.addAll(this.getResultByItemsIds(sql,dataType));
					}
				}
			}else if("item".equals(idsType)){//详细支路电量
				itemIds = ids;
				sql = this.buildSQL(date, dateType,tableName, itemIds,isShowName,null,chartType,dataType);
				result = this.getResultByItemsIds(sql,dataType);
			}else if("cur".equals(idsType)){//电流
				tableName = "epc_stat_cur";
				itemIds = ids;			
				sql = this.buildSQL(date, dateType,tableName, itemIds,isShowName,null,chartType,dataType);
				result = this.getResultByItemsIds(sql,dataType);
			}else if("vol".equals(idsType)){//电压
				tableName = "epc_stat_vol";
				itemIds = ids;
				sql = this.buildSQL(date, dateType,tableName, itemIds,isShowName,null,chartType,dataType);
				result = this.getResultByItemsIds(sql,dataType);
			}else if("eco".equals(idsType)){//功率
				tableName = "epc_stat_eco";
				isShowName = false;
				/*itemIds = ids;
				System.out.println("+111111111111111111111111++++++++++++++>"+ids.size());
				for(int i = 0 ; i< itemIds.size() ; i++){
					
					System.out.println("+++23333++++++++++++>"+itemIds.get(i));
				}
				sql = this.buildSQL(date, dateType,tableName, itemIds,isShowName,null,chartType,dataType);
				System.out.println("----->"+sql);
				result = this.getResultByItemsIds(sql,dataType);	*/	
				if(ids!=null&&ids.size()>0){
					for (String id : ids) {
						itemIds = subitemService.findChildrenItems(Long.parseLong(id),unitId,"GL");
						/*for(int i = 0 ; i< itemIds.size() ; i++){
							
							System.out.println("+++eco++++++++++++>"+itemIds.get(i));
						}*/
						sql = this.buildSQL(date, dateType,tableName, itemIds,isShowName,subitemService.getById(Long.parseLong(id)).getName(),chartType,dataType);
						result.addAll(this.getResultByItemsIds(sql,dataType));
					}
				}
			}else if("gl".equals(idsType)){//能耗
				tableName = "epc_stat_qoe";
				isShowName = false;
				if(ids!=null&&ids.size()>0){
					for (String id : ids) {
						itemIds = subitemService.findChildrenItems(Long.parseLong(id),unitId);
						/*for(int i = 0 ; i< itemIds.size() ; i++){
							
							System.out.println("+++gl++++++++++++>"+itemIds.get(i));
						}*/
						sql = this.buildSQL(date, dateType,tableName, itemIds,isShowName,subitemService.getById(Long.parseLong(id)).getName(),chartType,dataType);
						result.addAll(this.getResultByItemsIds(sql,dataType));
					}
				}
			}
			String chartTitle=null;
			if(dataType.equals("energy")){
				chartTitle="";
			}else if(dataType.equals("rate")){
				chartTitle="电费分析";
			}else if(dataType.equals("rule")){
				chartTitle="标准煤分析";
			}
			ChartsModuleUtil cmu = this.buildModule(date, result, dateType,chartType,chartTitle);
			String json = JSONUtil_OFC.getJSON(cmu, chartType);
			return json;
		}
		
		/**
		 * 组装sql
		 * @param date 时间 2010-10-10
		 * @param dateType 时间类型
		 * @param tableName 表名
		 * @param itemIds epc_item中id
		 * @param isShowName 是否显示item的名字，subitem与subitemfloor不显示
		 * @param chartType 统计图类型 bar pie line
		 * @return
		 */
		private String buildSQL(String date, String dateType,String tableName, List<String> itemIds,boolean isShowName,String itemName,String chartType,String  dataType){
			StringBuffer sql = new StringBuffer();
			
			if(dataType==null){
				dataType="energy";
			}
			
			StringBuffer param = new StringBuffer();
			StringBuffer where = new StringBuffer();
			//subitem-item-cur-vol-subitemfloor
			param.append("select ");
			where.append(" where ");
			
			String strType=" t1.value ";
			if(dataType.equals("rate")){
	           strType=" t1.rate ";
			}
			
			if(!isShowName){
				StringBuffer s_param = new StringBuffer();
				param.append(" t3.name "); 
				param.append(","); 
				param.append(" b1.* "); 
				if("day".equals(dateType)){
					if("pie".equals(chartType)){
						s_param.append(" CONCAT(sum(t1.value),'-',GROUP_CONCAT(t1.error)) ");
					}else{
						//for(int i =0 ; i<24 ; i++){
						for(int i =0 ; i<24 ; i++){
							if(i!=0)
								s_param.append(",");
							if(i<10)
								s_param.append(" CONCAT(sum((CASE when substring(t1.date_time,12,2)='0"+i+"' then "+strType+" end) ),'-',GROUP_CONCAT(CASE when substring(t1.date_time,1,13)='"+date+" 0"+i+"' then t1.error end)) as '0"+i+"' ");
							else
								s_param.append(" CONCAT(sum((CASE when substring(t1.date_time,12,2)='"+i+"' then "+strType+" end) ),'-',GROUP_CONCAT(CASE when substring(t1.date_time,1,13)='"+date+" "+i+"' then t1.error end)) as '"+i+"' ");
						}
					}
					where.append(" substring(t1.date_time,1,10)='"+date+"' ");
				}else if("month".equals(dateType)){
					if("pie".equals(chartType)){
						s_param.append(" CONCAT(sum("+strType+"),'-',GROUP_CONCAT(t1.error)) ");
					}else{
						for (int i = 1; i < DateUtil.maxMonth(date, "yyyy-MM-dd")+1 ; i++){
							if(i!=1)
								s_param.append(",");
							if(i<10)
								s_param.append(" CONCAT(sum((CASE when substring(t1.date_time,9,2)='0"+i+"' then "+strType+" end) ),'-',GROUP_CONCAT(CASE when substring(t1.date_time,1,10)='"+date.substring(0, 7)+"-0"+i+"' then t1.error end)) as '0"+i+"' ");
							else
								s_param.append(" CONCAT(sum((CASE when substring(t1.date_time,9,2)='"+i+"' then "+strType+" end) ),'-',GROUP_CONCAT(CASE when substring(t1.date_time,1,10)='"+date.substring(0, 7)+"-"+i+"' then t1.error end)) as '"+i+"' ");
						}
					}
					where.append(" substring(t1.date_time,1,7)='"+date.substring(0, 7)+"' ");
				}else if("year".equals(dateType)){
					if("pie".equals(chartType)){
						s_param.append(" CONCAT(sum("+strType+"),'-',GROUP_CONCAT(t1.error)) ");
					}else{
						for (int i = 1; i < 13 ; i++){
							if(i!=1)
								s_param.append(",");
							if(i<10)
								s_param.append(" CONCAT(sum((CASE when substring(t1.date_time,6,2)='0"+i+"' then "+strType+" end) ),'-',GROUP_CONCAT(CASE when substring(t1.date_time,1,7)='"+date.substring(0, 4)+"-0"+i+"' then t1.error end)) as '0"+i+"' ");
							else
								s_param.append(" CONCAT(sum((CASE when substring(t1.date_time,6,2)='"+i+"' then "+strType+" end) ),'-',GROUP_CONCAT(CASE when substring(t1.date_time,1,7)='"+date.substring(0, 4)+"-"+i+"' then t1.error end)) as '"+i+"' ");
						}
					}
					where.append(" substring(t1.date_time,1,4)='"+date.substring(0, 4)+"' ");
				}
				where.append(" and t1.ITEM_NAME=t2.name ");
				where.append(" and t2.id in ( '-1' ");
				if(itemIds!=null&&itemIds.size()>0){
					for(int i = 0 ; i< itemIds.size() ; i++){
						where.append(",");
						where.append("'"+itemIds.get(i)+"'");
						//System.out.println(itemIds.get(i));
					}
				}
				where.append(" ) ");
				sql.append(param);
				sql.append(" from ");
				sql.append("( select ");
				sql.append(s_param);
				sql.append(" from ");
				sql.append(tableName+" t1 , bas_item t2 ");
				sql.append(where);
				sql.append(" ) b1 ");
				sql.append(", (select '"+itemName+"' as name ) t3 ");
				sql.append(" group by t3.name ");
			}else{
				if(isShowName)
					param.append(" t2.BAS_ITEM_DESC "); 
				else
					param.append(" t3.name "); 
				if("day".equals(dateType)){
					if("pie".equals(chartType)){
						param.append(" , CONCAT(sum("+strType+"),'-',GROUP_CONCAT(t1.error)) ");
					}else{
						//for(int i =0 ; i<24 ; i++){
						for(int i =0 ; i<24 ; i++){
							if(i<10)
								param.append(" ,CONCAT(sum((CASE when substring(t1.date_time,12,2)='0"+i+"' then "+strType+" end) ),'-',GROUP_CONCAT(CASE when substring(t1.date_time,1,13)='"+date+" 0"+i+"' then t1.error end)) as '0"+i+"' ");
							else
								param.append(" ,CONCAT(sum((CASE when substring(t1.date_time,12,2)='"+i+"' then "+strType+" end) ),'-',GROUP_CONCAT(CASE when substring(t1.date_time,1,13)='"+date+" "+i+"' then t1.error end)) as '"+i+"' ");
						}
					}
					where.append(" substring(t1.date_time,1,10)='"+date+"' ");
				}else if("month".equals(dateType)){
					if("pie".equals(chartType)){
						param.append(" , CONCAT(sum("+strType+"),'-',GROUP_CONCAT(t1.error)) ");
					}else{
						//算当月最大天数
						for (int i = 1; i < DateUtil.maxMonth(date, "yyyy-MM-dd")+1 ; i++){
							if(i<10)
								param.append(" ,CONCAT(sum((CASE when substring(t1.date_time,9,2)='0"+i+"' then "+strType+" end) ),'-',GROUP_CONCAT(CASE when substring(t1.date_time,1,10)='"+date.substring(0, 7)+"-0"+i+"' then t1.error end)) as '0"+i+"' ");
							else
								param.append(" ,CONCAT(sum((CASE when substring(t1.date_time,9,2)='"+i+"' then "+strType+" end) ),'-',GROUP_CONCAT(CASE when substring(t1.date_time,1,10)='"+date.substring(0, 7)+"-"+i+"' then t1.error end)) as '"+i+"' ");
						}
					}
					where.append(" substring(t1.date_time,1,7)='"+date.substring(0, 7)+"' ");
				}else if("year".equals(dateType)){
					if("pie".equals(chartType)){
						param.append(" , CONCAT(sum("+strType+"),'-',GROUP_CONCAT(t1.error)) ");
					}else{
						for (int i = 1; i < 13 ; i++){
							if(i<10)
								param.append(" ,CONCAT(sum((CASE when substring(t1.date_time,6,2)='0"+i+"' then "+strType+" end) ),'-',GROUP_CONCAT(CASE when substring(t1.date_time,1,7)='"+date.substring(0, 4)+"-"+i+"' then t1.error end)) as '0"+i+"' ");
							else
								param.append(" ,CONCAT(sum((CASE when substring(t1.date_time,6,2)='"+i+"' then "+strType+" end) ),'-',GROUP_CONCAT(CASE when substring(t1.date_time,1,7)='"+date.substring(0, 4)+"-"+i+"' then t1.error end)) as '"+i+"' ");
						}
					}
					where.append(" substring(t1.date_time,1,4)='"+date.substring(0, 4)+"' ");
				}
				where.append(" and t1.ITEM_NAME=t2.name ");
				if(itemIds!=null&&itemIds.size()>0){
					where.append(" and t2.id in ( '-1' ");
					for(int i = 0 ; i< itemIds.size() ; i++){
						where.append(",");
						where.append("'"+itemIds.get(i)+"'");
					}
					where.append(" ) ");
				}
				sql.append(param);
				sql.append(" from ");
				sql.append(tableName+" t1 , bas_item t2 ");
				sql.append(where);
				sql.append(" group by t2.BAS_ITEM_DESC ");
			}
			
			return sql.toString();
		}
		
		
		private List<Map<String, String>> getResultByItemsIds(String sql,String dataType){
			List<Map<String, String>> result = null;
			List<Object[]> list = this.excuteQuerySQL(sql);
			
			double rateId=1.0;
	        if(dataType.equals("rule")){
				rateId=0.1229;
			}

			if(list!=null){
				result = new ArrayList<Map<String,String>>();
				Object[] o = null;
				Map<String, String> map = null;
				for(int i = 0 ; i< list.size() ; i++){
					o = list.get(i);
					map = new HashMap<String, String>();
					for(int j = 0; j < o.length ; j++){
						if(j==0)
							map.put("name", o[j]+"");
						else{
							if(o[j]==null)
								map.put(j+"", 0+"");
							else{
								String number=o[j].toString().split("-")[0];
								if(number!=null){
									double doublenumber=Double.parseDouble(number)*rateId;
									DecimalFormat df = new DecimalFormat( "#.##" ); 
									map.put(j + "", df.format(doublenumber) + "-"+o[j].toString().split("-")[1]);
								}else{
									double doublenumber=Double.parseDouble(o[j].toString())*rateId;
									DecimalFormat df = new DecimalFormat( "#.##" ); 
									map.put(j + "", df.format(doublenumber) + "");
								}
							}
						}
					}
					result.add(map);
				}
			}
			return result;
		}
		/**
		 * 
		 */
		private ChartsModuleUtil buildModule(String date, List<Map<String, String>> result,String dateType,String chartType,String chartTitle){
			ChartsModuleUtil c =  new ChartsModuleUtil();
			c.setTitle(chartTitle);
			c.setYlegend("");
			c.setYaxis("时间");
			c.setXaxis("计量");			
			List<String> xaxisValues = new ArrayList<String>();
			String xtext = null;
			if("day".equals(dateType)){
				for(int i = 0 ; i < 24 ; i++){
				
					String hour = "";
						hour = i+"";
					xaxisValues.add(hour);
				}
				xtext = "点";
			}else if("month".equals(dateType)){
				for(int i = 1 ; i < DateUtil.maxMonth(date, "yyyy-MM-dd")+1 ; i++){
					String day = "";
						day = i+"";
					xaxisValues.add(day);
				}
				xtext = "号";
			}else if("year".equals(dateType)){
				for(int i = 1 ; i < 13 ; i++){
					String month = "";
						month = i+"";
					xaxisValues.add(month);
				}
				xtext = "月";
			}
			List<ItemObject> values = null;
			List<ElementsModuleUtil> elements = new ArrayList<ElementsModuleUtil>();
			ElementsModuleUtil em = null;
			int max = 0;
			for(int i = 0 ; i < result.size() ; i++){
				Map<String,String> map = result.get(i);
				int d = 0;
				values = new ArrayList<ItemObject>();
				em = new ElementsModuleUtil();
				em.setText(map.get("name"));
				if(!"pie".equals(chartType)){
					for(String s:xaxisValues){//设置统计X轴内容
						String sp = "";
						if("day".equals(dateType)){
							sp = map.get((Integer.parseInt(s)+1)+"");
						}else{
							sp = map.get(s);
						}
						if(sp == null||"0".equals(sp)||"".equals(sp))
							sp = "0.0-Right";
						String[] sv = sp.split("-");
						
						int _d = (int) Double.parseDouble(sv[0]);
						values.add(new ItemObject(s,sv[0],sv[1]));
						if(d<_d)
							d = _d;
					}
				}else{ //pie 图，独立处理
					String sp = map.get("1");
					if(sp == null||"0".equals(sp)||"".equals(sp))
						sp = "0.0-Right";
					String[] sv = sp.split("-");
					values.add(new ItemObject(null,sv[0],sv[1]));
				}
				em.setValues(values);
				elements.add(em);
				if(max<d){
					max = d;
				}
			}
			c.setXaxisValues(xaxisValues);
			c.setElements(elements);
			if(max<=0)
				max = 10;
			max = (int) (max*1.1);
			//System.out.println("-------------------------"+max);
			c.setMin("0");
			c.setMax(max+1+"");
			c.setSteps(max/10+1+"");
			return c;
		}
		
		/*@Override
		public String getChartData(String date, String dateType,String dataType, String chartType,
				Long subitemId, List<UnitDTO> units) {
			
			if(dataType==null){
				dataType="energy";
			}
			List<Map<String, String>> result = new ArrayList<Map<String,String>>();
			// TODO Auto-generated method stub
			if(StringUtil.isNull(date)){
				date = DateUtil.date2String(new Date(), "yyyy-MM-dd");
			}
			if(StringUtil.isNull(dateType)){
				dateType = "day";
			}
			if(StringUtil.isNull(chartType)){
				chartType = "bar";
			}
			
			String chartTitle=null;
			if(dataType.equals("energy")){
				chartTitle="能耗分析";
			}else if(dataType.equals("rate")){
				chartTitle="电费分析";
			}else if(dataType.equals("rule")){
				chartTitle="标准煤分析";
			}
			if(units!=null){
				for(UnitDTO unit:units){
					List<String> itemIds = subitemService.findChildrenItems(subitemId,unit.getId());
					String sql = this.buildSQL(date, dateType,"epc_stat_qoe", itemIds,false,unit.getName(),chartType,dataType); 
					result.addAll(this.getResultByItemsIds(sql,dataType));
				}
			}
			ChartsModuleUtil cmu = this.buildModule(date, result, dateType,chartType,chartTitle);
			String json = JSONUtil_OFC.getJSON(cmu, chartType);
			return json;
		}*/
}
