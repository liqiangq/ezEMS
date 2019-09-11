package com.thtf.service;

import java.util.List;

import com.thtf.dto.ResourceDTO;
import com.thtf.entity.EpcResource;

/**
 * @author 孙刚 E-mail:sungang01@thtf.com.cn
 * @version 创建时间：2010-9-2 下午01:16:50
 * 类说明
 */
public interface ResourceService extends BasicService<EpcResource, Long> {
	List<ResourceDTO> findTree();
	List<ResourceDTO> findCheckedTree(List ids);
}
