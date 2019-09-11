package com.thtf.quartz.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.thtf.quartz.DataAccessObject;
import com.thtf.service.imp.BasicServiceImp;
import com.thtf.util.DateUtil;

/**
 * @author 孙刚 E-mail:sungang01@thtf.com.cn
 * @version 创建时间：2011-05-14 上午09:19:29 类说明
 */
public class TargetDataSynService extends BasicServiceImp{

	private static Logger logger = Logger.getLogger(TargetDataSynService.class);
	
	
	public Map<String, String> getEpcItemType() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("DD", "epc_stat_qoe");
		map.put("DL", "epc_stat_cur");
		map.put("DY", "epc_stat_vol");
		map.put("GL", "epc_stat_eco");
		return map;
	}

	public List<Object[]> getGate(){
		String sql = "select m2m_projectId,m2m_node_system_desc,m2m_node_system_name from node_gate";
		return this.excuteQuerySQL(sql);
	}
	
	public List<Object[]> getItemDD(){
		String sql = "select m2m_id,m2m_node_item_desc,m2m_projectId from node_item where m2m_id like '%_DD%' ";
		return this.excuteQuerySQL(sql);
	}
	public List<Object[]> getItemOther(){
		String sql = "select m2m_id,m2m_node_item_desc,m2m_projectId from node_item where m2m_id like '%_DL%' or m2m_id like '%_DY%' or m2m_id like '%_GL%' order by m2m_id";
		return this.excuteQuerySQL(sql);
	}
	
	public List<DataAccessObject> getDataAccess(Date date) {
		List<DataAccessObject> list = new ArrayList<DataAccessObject>();
		String sql = "select id, gateId,timestamp from data_access where timestamp < '"+DateUtil.date2String(date, "yyyy-MM-dd HH")+"'";
		List<Object[]> result = this.excuteQuerySQL(sql);
		DataAccessObject dao = null;
		if(result != null){
			for (Object[] o : result) {
				String timestamp = DateUtil.string2String(o[2]+"", "yyyy-MM-dd HH", "yyyyMMddHH");
				dao = new DataAccessObject();
				dao.setId(Long.parseLong(o[0] + ""));
				dao.setGateId(o[1] + "");
				dao.setTimestamp(timestamp);
				dao.setBefTimestamp(DateUtil.beforeDate2String(timestamp,
						Calendar.HOUR_OF_DAY, -1, "yyyyMMddHH"));
				dao.setAftTimestamp(DateUtil.beforeDate2String(timestamp,
						Calendar.HOUR_OF_DAY, 1, "yyyyMMddHH"));
				list.add(dao);
			}
		}
		logger.info("getDataAccess:"+sql);
		return list;
	}

	public String getBefDataHisObjectsSum(DataAccessObject da) {
		String sql = null;
		if(this.checkBefTimestamp(da)){
			sql = "select t1.m2m_item_id,(t1.m2m_pv-(case when t.m2m_pv is null then t1.m2m_pv else t.m2m_pv end)) as value ,t1.m2m_quality from (select * from (select * from node_item_value_hist_"
					+ da.getTimestamp()
					+ " where m2m_item_id like '%_DD%' order by m2m_timestamp) b where b.m2m_projectId='"
					+ da.getGateId()
					+ "' group by b.m2m_item_id ) t1 left join  (select * from (select * from node_item_value_hist_"
					+ da.getBefTimestamp()
					+ " where m2m_item_id like '%_DD%' order by m2m_timestamp ) a where a.m2m_projectId='"
					+ da.getGateId()
					+ "' group by a.m2m_item_id ) t on t1.m2m_item_id = t.m2m_item_id ";
		}
		logger.info("getBefDataHisObjectsSum:"+sql);
		return sql;
	}
	
	public String getAftDataHisObjectsSum(DataAccessObject da) {
		String sql = null;
		if(!this.checkAftTimestamp(da))
			sql = "select t1.m2m_item_id,(t1.m2m_pv-(case when t.m2m_pv is null then t1.m2m_pv else t.m2m_pv end)) as value ,t1.m2m_quality from (select * from (select * from node_item_value_hist_"
				+ da.getTimestamp()
				+ " where m2m_item_id like '%_DD%' order by m2m_timestamp desc) b where b.m2m_projectId='"
				+ da.getGateId()
				+ "' group by b.m2m_item_id ) t1 left join  (select * from (select * from node_item_value_hist_"
				+ da.getTimestamp()
				+ " where m2m_item_id like '%_DD%' order by m2m_timestamp ) a where a.m2m_projectId='"
				+ da.getGateId()
				+ "' group by a.m2m_item_id ) t on t1.m2m_item_id = t.m2m_item_id";
		else{
			sql = "select t1.m2m_item_id,(t1.m2m_pv-(case when t.m2m_pv is null then t1.m2m_pv else t.m2m_pv end)) as value ,t1.m2m_quality from (select * from (select * from node_item_value_hist_"
				+ da.getAftTimestamp()
				+ " where m2m_item_id like '%_DD%' order by m2m_timestamp) b where b.m2m_projectId='"
				+ da.getGateId()
				+ "' group by b.m2m_item_id ) t1 left join  (select * from (select * from node_item_value_hist_"
				+ da.getTimestamp()
				+ " where m2m_item_id like '%_DD%' order by m2m_timestamp ) a where a.m2m_projectId='"
				+ da.getGateId()
				+ "' group by a.m2m_item_id ) t on t1.m2m_item_id = t.m2m_item_id";
		}
		logger.info("getAftDataHisObjectsSum:"+sql);
		return sql;
	}
	
	public String getDataHisObjectsAVG(DataAccessObject da) {
		String sql = "select t1.m2m_item_id,AVG(t1.m2m_pv) as value ,t1.m2m_quality from node_item_value_hist_"
			+ da.getTimestamp()
			+ " t1 where t1.m2m_projectId = '"
			+ da.getGateId()
			+ "' and ( t1.m2m_item_id like '%_DL%' or t1.m2m_item_id like '%_DY%' or t1.m2m_item_id like '%_GL%') group by t1.m2m_item_id ";
		logger.info("getDataHisObjectsAVG:"+sql);
		return sql;
	}
	
	private boolean checkBefTimestamp(DataAccessObject da){
		boolean checkBef = false;
		String checkBefSQL = "show tables like 'node_item_value_hist_"+da.getBefTimestamp()+"'";
		List checkBefResult = this.excuteQuerySQL(checkBefSQL);
		if(checkBefResult!=null&&checkBefResult.size()>0){
			checkBef = true;
		}
		logger.info("checkBefTimestamp:"+checkBefSQL+"-----result:"+checkBef);
		return checkBef;
	}
	
	private boolean checkAftTimestamp(DataAccessObject da){
		boolean checkBef = false;
		String checkBefSQL = "show tables like 'node_item_value_hist_"+da.getAftTimestamp()+"'";
		List checkBefResult = this.excuteQuerySQL(checkBefSQL);
		if(checkBefResult!=null&&checkBefResult.size()>0){
			checkBef = true;
		}
		logger.info("checkAftTimestamp:"+checkBefSQL+"-----result:"+checkBef);
		return checkBef;
	}
	public boolean checkTimestamp(DataAccessObject da){
		boolean check = false;
		String checkSQL = "show tables like 'node_item_value_hist_"+da.getTimestamp()+"'";
		List checkResult = this.excuteQuerySQL(checkSQL);
		if(checkResult!=null&&checkResult.size()>0){
			check = true;
		}
		logger.info("checkTimestamp:"+checkSQL+"-----result:"+check);
		return check;
	}
	
	@Transactional(value="targetTransactionManager")
	public void deleteDataAccess(List<DataAccessObject> list) {
		if(list!=null&&list.size()>0){
			for(DataAccessObject da:list){
				String sql = "delete from DATA_ACCESS where id = "+da.getId();
				this.excuteUpdateSQL(sql);
				logger.info("deleteDataAccess:"+sql);
			}
		}
	}

}
