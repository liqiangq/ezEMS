package com.thtf.service;

import java.util.List;

import com.thtf.commons.PageUtil;
import com.thtf.entity.BasicObject;
public interface BasicService<T extends BasicObject, M extends java.io.Serializable> {
	/**
	 * 排序查询全部记录
	 * 
	 * @param entityClass
	 *            类
	 * @param isAsc
	 *            顺序
	 * @param orderBy
	 *            排序属性
	 * @return
	 */
	List<T> getAll(String sortOrder, String orderBy) ;

	/**
	 * 执行hql查询
	 * 
	 * @param hql
	 *            查询语句
	 * @param o
	 *            条件参数
	 * @return
	 */
	List<T> getByQuery(String hql, Object[] o) ;

	/**
	 * 分页查询排序所有记录
	 * 
	 * @param start
	 * @param pageSize
	 * @param entityClass
	 * @param isAsc
	 * @param orderBy
	 * @return
	 */
	PageUtil<T> getAllByPagination(int start, int pageSize, String sortOrder,
			String orderBy) ;

	/**
	 * 根据hql分页查询
	 * 
	 * @param start
	 * @param pageSize
	 * @param hql
	 * @param o
	 * @return
	 */
	PageUtil<T> getByQueryPagination(int start, int pageSize, String hql,
			Object[] o) ;

	/**
	 * 
	 * @param id
	 * @return
	 */
	T getById(M id) ;

	/**
	 * 
	 * @param t
	 */
	void save(T t) ;

	/**
	 * 
	 * @param list
	 */
	void saveAll(List<T> list) ;

	/**
	 * 
	 * @param t
	 */
	void update(T t) ;

	/**
	 * 
	 * @param list
	 */
	void updateAll(List<T> list) ;

	/**
	 * 
	 * @param t
	 */
	void delete(T t) ;

	void deleteById(M id) ;

	/**
	 * 
	 * @param list
	 */
	void deleteAll(List<T> list) ;

	/**
	 * 执行hql修改
	 * 
	 * @param hql
	 * @param o
	 * @return
	 */
	int excuteUpdateByParam(String hql, Object[] o) ;

	/**
	 * 执行sql修改、删除
	 * 
	 * @param sql
	 * @param o
	 * @return
	 */
	int excuteUpdateSQL(String sql) ;

	/**
	 * 执行sql查询
	 * 
	 * @param sql
	 * @return
	 */
	List<Object[]> excuteQuerySQL(String sql) ;
}
