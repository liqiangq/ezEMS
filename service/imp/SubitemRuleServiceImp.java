package com.thtf.service.imp;

import java.util.List;

import com.thtf.entity.EpcSubitemRule;
import com.thtf.entity.SubitemRulePK;
import com.thtf.service.SubitemRuleService;

/**
 * @author 孙刚 E-mail:sungang01@thtf.com.cn
 * @version 创建时间：2010-12-22 上午09:37:51 类说明
 */
public class SubitemRuleServiceImp extends
		BasicServiceImp<EpcSubitemRule, SubitemRulePK> implements
		SubitemRuleService {

	public EpcSubitemRule getEpcSubitemRule(Long unitId, Long subitemId) {
		EpcSubitemRule epcSubitemRule = null;
		String jpql = "from EpcSubitemRule t where t.id.unitId=? and t.id.subitemId=?";
		List<EpcSubitemRule> list = this.getByQuery(jpql, new Long[] { unitId,
				subitemId });
		if (list != null && list.size() > 0) {
			epcSubitemRule = list.get(0);
		}
		return epcSubitemRule;
	}
	
}
