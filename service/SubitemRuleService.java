package com.thtf.service;

import com.thtf.entity.EpcSubitemRule;
import com.thtf.entity.SubitemRulePK;

/**
 * @author 孙刚 E-mail:sungang01@thtf.com.cn
 * @version 创建时间：2010-12-22 上午09:36:37
 * 类说明
 */
public interface SubitemRuleService extends BasicService<EpcSubitemRule, SubitemRulePK> {
	/**
	 * 得到标尺
	 * @param unitId 单位编号
	 * @param subitemId 分项编号
	 * @return
	 */
	EpcSubitemRule getEpcSubitemRule(Long unitId,Long subitemId);
}
