package com.thtf.service;

import java.util.Map;

import com.thtf.entity.EpcItemType;
/**
 * @author 孙刚 E-mail:sungang01@thtf.com.cn
 * @version 创建时间：2010-9-2 上午11:33:26
 * 类说明
 */
public interface ItemTypeService extends BasicService<EpcItemType, Long> {
	/*
	 * 获得itemtypeMap
	 */
	Map getAll() ;
}
