package com.thtf.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.transaction.annotation.Transactional;

import com.thtf.dto.BasItemDTO;
import com.thtf.entity.BasItem;
import com.thtf.entity.EpcSubitem;
import com.thtf.service.ItemService;
import com.thtf.service.ItemTypeService;



/**
 * @author 孙刚 E-mail:sungang01@thtf.com.cn
 * @version 创建时间：2010-9-3 上午10:48:21 类说明
 */
@RemotingDestination
public class ItemServiceImp extends BasicServiceImp<BasItem, Long> implements
		ItemService {
	   private ItemTypeService itemTypeService;
		
		public void setItemTypeService(ItemTypeService itemTypeService) {
			this.itemTypeService = itemTypeService;
		}


	@Transactional
	@Override
	public List<BasItemDTO> findTree(Long unitId, String itemTypeCode,boolean showChildren) {
		// TODO Auto-generated method stub
		List<BasItemDTO> ls = null;
		BasItemDTO s = null;
		String sql = "from BasItem t where t.basGate.epcUnit.id=? and t.epcItemType.code = ?";
		List<BasItem> list = getByQuery(sql, new Object[] { unitId,itemTypeCode });
		if (list != null) {
			ls = new ArrayList<BasItemDTO>();
			for (BasItem e : list) {
				s = this.buildDTO(e,showChildren);
				ls.add(s);
			}
		}
		return ls;
	}
	/**
	 * 实现有待改进
	 */
	@Transactional
	@Override
	public boolean updateItem(Long itemId, List<String> ids) {
		// TODO Auto-generated method stub
		BasItem basItem = this.getById(itemId);
		String d_jpql = "from BasItem t where t.basItem.id=? ";
		List<BasItem> d_list = getByQuery(d_jpql, new Object[]{itemId});
		if(d_list!=null)
		for (BasItem bi : d_list) {
			bi.setBasItem(null);
		}
		
		StringBuffer u_jpql = new StringBuffer();
		u_jpql.append("from BasItem t where t.id=-1 ");
		if(ids!=null){
			for (String id : ids) {
				u_jpql.append(" or t.id = "+ id);
			}
		}
		List<BasItem> u_list = getByQuery(u_jpql.toString(), null);
		if(u_list!=null){
			for (BasItem bi : u_list) {
				bi.setBasItem(basItem);
			}
			return true;
		}
		return false;
	}
	@Transactional
	@Override
	public List<BasItemDTO> findTree(Long unitId, String itemTypeCode,String useType) {
		// param useType 用处：subitem-分项,subitemfloor-功能区,item-电表设置
		List<BasItemDTO> ls = null;
		
		BasItemDTO s = null;
		String jpql = "from BasItem t where t.basGate.epcUnit.id=? and t.epcItemType.code = ? ";
		if("subitem".equals(useType)){
			jpql += " and t.epcSubitem is null ";
		}else if("subitemfloor".equals(useType)){
			jpql += " and t.epcSubitemfloor is null ";
		}else if("item".equals(useType)){
			jpql += " and t.basItem is null ";
		}
		
		List<BasItem> list = getByQuery(jpql, new Object[] { unitId,itemTypeCode });
		if (list != null) {
			ls = new ArrayList<BasItemDTO>();
			for (BasItem e : list) {
				s = this.buildDTO(e,false);
				ls.add(s);
			}
		}
		return ls;
	}
	/**
	 * 分项计量应用
	 */
	@Transactional
	@Override
	public List<BasItemDTO> findTreeSubItem(Long unitId, String itemTypeCode,String useType) {
		// param useType 用处：subitem-分项,subitemfloor-功能区,item-电表设置
		List<BasItemDTO> ls = null;
		BasItemDTO s = null;
		String jpql = "from BasItem t where t.basGate.epcUnit.id=? and  ( t.epcItemType.code='DD' )  ";
		if("subitem".equals(useType)){
			jpql += " and t.epcSubitem is null ";
		}else if("subitemfloor".equals(useType)){
			jpql += " and t.epcSubitemfloor is null ";
		}else if("item".equals(useType)){
			jpql += " and t.basItem is null ";
		}
		
		List<BasItem> list = getByQuery(jpql, new Object[] { unitId });
		if (list != null) {
			ls = new ArrayList<BasItemDTO>();
			for (BasItem e : list) {
				s = this.buildDTO(e,false);
				ls.add(s);
			}
		}
		return ls;
	}
	
	private BasItemDTO buildDTO(BasItem basItem,boolean showChildren) {

		BasItemDTO basItemDTO = null;
		if (basItem != null) {
			basItemDTO = new BasItemDTO();
			basItemDTO.setId(basItem.getId());
			basItemDTO.setDescription(basItem.getBasItemDesc());
			basItemDTO.setName(basItem.getBasItemDesc());
			basItemDTO.setItemTypeCode(basItem.getEpcItemType().getCode());
			basItemDTO.setItemTypeName(basItem.getEpcItemType().getName());
			if(showChildren){
				Set<BasItem> set = basItem.getBasItems();
				List<BasItemDTO> list = null;
				if (set != null && set.size() > 0) {
					list = new ArrayList<BasItemDTO>();
					for (BasItem e : set) {
						list.add(this.buildDTO(e,showChildren));
					}
				}
				basItemDTO.setChildren(list);
			}
		}
		return basItemDTO;
	}
	
	@Transactional
	@Override
	public Map getItemTypeMap() {
		Map map = new HashMap();
		try{
		map =itemTypeService.getAll();
		}catch(Exception e){
			e.getStackTrace();
		}
		return map;
	}


	@Transactional
	@Override
	public boolean updateBasItem(BasItemDTO basItemDTO) {
		if(basItemDTO==null)
			return false;
		
		BasItem basItem = this.getById(basItemDTO.getId());
		String d_jpql = "update BasItem t set t.basItem=? where t.basItem.id=?";
		excuteUpdateByParam(d_jpql, new Object[]{null,basItemDTO.getId()});
		StringBuffer u_jpql = new StringBuffer();
		List<BasItemDTO> items = basItemDTO.getChildren();
		if(items!=null){
			u_jpql.append("update BasItem t set t.basItem=? where t.id=-1 ");
			for (BasItemDTO bid : items) {
				u_jpql.append(" or t.id = "+ bid.getId());
				
			}
			excuteUpdateByParam(u_jpql.toString(), new Object[]{basItem});
		}
		return true;
	}


	@Override
	public List<BasItem> findBasItems(List<Long> subitemIds, Long unitId) {
		// TODO Auto-generated method stub
		if(subitemIds==null||subitemIds.size()<1){
			return null;
		}
		StringBuffer jpql = new StringBuffer();
		jpql.append("from BasItem t where t.basGate.epcUnit.id="+unitId+" and (");
		for(int i = 0 ; i<subitemIds.size() ;i++){
			if(i!=0)
				jpql.append(" or ");
			jpql.append(" t.epcSubitem.id="+subitemIds.get(i));
		}
		jpql.append(" )");
		
		return this.getByQuery(jpql.toString(), null);
	}
	
	

	@Override
	public List<BasItem> findBasItemsByPlaceCode(String code) {
		// TODO Auto-generated method stub
		String jpql = "from BasItem t where t.basGate.epcUnit.epcPlaceCode.code=?";
		return this.getByQuery(jpql,new String[]{code});
	}


	@Override
	public List<BasItem> findBasItems(Long subitemIds, Long unitId) {
		// TODO Auto-generated method stub
		if(subitemIds==null){
			return null;
		}
		StringBuffer jpql = new StringBuffer();
		jpql.append("from BasItem t where t.basGate.epcUnit.id="+unitId+" and t.epcSubitem.id="+subitemIds);
		
	
		return this.getByQuery(jpql.toString(), null);
	}
	
}
