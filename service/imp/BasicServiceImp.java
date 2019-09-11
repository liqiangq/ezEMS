package com.thtf.service.imp;

import java.util.List;

import com.thtf.commons.GenericsUtils;
import com.thtf.commons.PageUtil;
import com.thtf.dao.BasicDao;
import com.thtf.entity.BasicObject;
import com.thtf.service.BasicService;

public class BasicServiceImp<T extends BasicObject, M extends java.io.Serializable>
		implements BasicService<T, M> {

	private Class<T> entityClass;

	@SuppressWarnings("unchecked")
	public BasicServiceImp() {
		// TODO Auto-generated constructor stub
		entityClass = GenericsUtils.getSuperClassGenricType(getClass());
	}

	protected BasicDao<T, M> dao;

	public void setDao(BasicDao<T, M> dao) {
		this.dao = dao;
	}

	@Override
	public void delete(T t) {
		// TODO Auto-generated method stub
		try {
			dao.delete(t);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void deleteById(M id) {
		// TODO Auto-generated method stub
		try {
			dao.deleteById(id, entityClass);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void deleteAll(List<T> list) {
		// TODO Auto-generated method stub
		try {
			dao.deleteAll(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<Object[]> excuteQuerySQL(String sql) {
		// TODO Auto-generated method stub
		List<Object[]> list = null;
		try {
			list = dao.excuteQuerySQL(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int excuteUpdateByParam(String hql, Object[] o) {
		// TODO Auto-generated method stub
		int i = 0;
		try {
			i = dao.excuteUpdateByParam(hql, o);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	@Override
	public int excuteUpdateSQL(String sql) {
		// TODO Auto-generated method stub
		int i = 0;
		try {
			i = dao.excuteUpdateSQL(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	@Override
	public List<T> getAll(String sortOrder, String orderBy) {
		// TODO Auto-generated method stub
		List<T> list = null;
		try {
			list= dao.getAll(sortOrder, orderBy, entityClass);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public PageUtil<T> getAllByPagination(int start, int pageSize,
			String sortOrder, String orderBy) {
		// TODO Auto-generated method stub
		PageUtil<T> page = null;
		try {
			int dataCount;
			dataCount = dao.getDataCount(entityClass);
			if (dataCount == 0)
				return new PageUtil<T>();
			List<T> list = dao.getAllByPagination(start, pageSize, sortOrder,
					orderBy, entityClass);
			page = new PageUtil<T>(pageSize, start, (start / pageSize + 1), list,
					dataCount, sortOrder, orderBy);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return page;
	}

	@Override
	public T getById(M id) {
		// TODO Auto-generated method stub
		T t = null;
		try {
			t = dao.getById(id, entityClass);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t;
	}

	@Override
	public List<T> getByQuery(String hql, Object[] o) {
		// TODO Auto-generated method stub
		List<T> list = null;
		try {
			list = dao.getByQuery(hql, o);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public PageUtil<T> getByQueryPagination(int start, int pageSize,
			String hql, Object[] o) {
		// TODO Auto-generated method stub
		PageUtil<T> page = null;
		try {
			int dataCount = dao.getDataCount(hql, o);
			if (dataCount == 0)
				return new PageUtil<T>();
			List<T> list = dao.getByQueryPagination(start, pageSize, hql, o);
			page = new PageUtil<T>(pageSize, start, (start / pageSize + 1), list,
					dataCount);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return page;
	}

	@Override
	public void save(T t) {
		// TODO Auto-generated method stub
		try {
			dao.save(t);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void saveAll(List<T> list) {
		// TODO Auto-generated method stub
		try {
			dao.saveAll(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void update(T t) {
		// TODO Auto-generated method stub
		try {
			dao.update(t);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void updateAll(List<T> list) {
		// TODO Auto-generated method stub
		try {
			dao.updateAll(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
