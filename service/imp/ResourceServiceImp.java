package com.thtf.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.transaction.annotation.Transactional;

import com.thtf.dto.ResourceDTO;
import com.thtf.entity.EpcResource;
import com.thtf.service.ResourceService;

/**
 * @author 孙刚 E-mail:sungang01@thtf.com.cn
 * @version 创建时间：2010-9-2 下午01:20:16
 * 类说明
 */
@RemotingDestination
public class ResourceServiceImp extends BasicServiceImp<EpcResource, Long>
		implements ResourceService {

	@Transactional
	@Override
	public List<ResourceDTO> findTree() {
		String hql = "from EpcResource where resource_id is null";
		List<EpcResource> resourceList = this.getByQuery(hql,null);
		List<ResourceDTO> dtoList = new ArrayList<ResourceDTO>();
		for(int i = 0;i<resourceList.size();i++){
			dtoList.add(buildTree(resourceList.get(i)));
		}
		return dtoList;
	}
	
	private ResourceDTO buildTree(EpcResource resource){
		ResourceDTO rdto = new ResourceDTO();
		rdto.setId(resource.getId());
		rdto.setName(resource.getName());
		rdto.setState(0);
		List<ResourceDTO> dtoList =getChildren(rdto.getId());
		if(dtoList.size()>0){
			rdto.setChildren(dtoList);
		}
		return rdto;
	}
	
	private List<ResourceDTO> getChildren(Long id){
		List<ResourceDTO> dtoList = new ArrayList<ResourceDTO>();
		String hql = "from EpcResource where resource_id="+id;
		List<EpcResource> resourceList = this.getByQuery(hql,null);
		if(resourceList!=null){
			for(int i = 0;i<resourceList.size();i++){
				ResourceDTO resdto = new ResourceDTO();
				resdto.setId(resourceList.get(i).getId());
				resdto.setName(resourceList.get(i).getName());
				resdto.setState(0);
				resdto.setChildren(new ArrayList<ResourceDTO>());
				dtoList.add(resdto);
			}
		}
		return dtoList; 
	}

	@Transactional
	@Override
	public List<ResourceDTO> findCheckedTree(List ids) {
		// TODO Auto-generated method stub
	
		List<ResourceDTO> resources = this.findTree();
		List<ResourceDTO> resourcesTree = new ArrayList<ResourceDTO> ();
		for(int i=0;i<resources.size();i++){
			ResourceDTO resource = resources.get(i);
			List<ResourceDTO> children = resource.getChildren();
			List<ResourceDTO> childTree = new ArrayList<ResourceDTO> ();
			for(int j=0;j<children.size();j++){
				ResourceDTO child = children.get(j);
				
				if(ids.contains(Integer.parseInt(child.getId().toString()))){
					child.setState(1);
				}else{
					child.setState(0);
				}
				childTree.add(child);
			}
			resource.setChildren(childTree);
			if(ids.contains(Integer.parseInt(resource.getId().toString()))){
				resource.setState(1);
			}else{
				resource.setState(0);
			}
			
			resourcesTree.add(resource);
		}
		return resourcesTree;
	}


}
