package com.thtf.service.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.transaction.annotation.Transactional;

import com.thtf.entity.EpcItemType;
import com.thtf.service.ItemTypeService;

/**
 * @author 孙刚 E-mail:sungang01@thtf.com.cn
 * @version 创建时间：2010-9-2 下午02:04:31 类说明
 */
@RemotingDestination
public class ItemTypeServiceImp extends BasicServiceImp<EpcItemType, Long>
		implements ItemTypeService {

	@Transactional
	@Override
	public Map getAll() {
		List<EpcItemType> epcItemTypeList = this.getAll("asc", "id");
		Map map = new HashMap();
		for (int i = 0; i < epcItemTypeList.size(); i++) {
			EpcItemType epcItemType = epcItemTypeList.get(i);
			map.put(epcItemType.getCode(), epcItemType.getName());
		}
		return map;
	}

}
