package com.thtf.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;

import com.thtf.entity.BasicObject;

public interface BasicDao<T extends BasicObject, M extends Serializable> {
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
	List<T> getAll(String sortOrder, String orderBy, Class<T> entityClass)
			throws Exception;

	/**
	 * 执行hql查询
	 * 
	 * @param hql
	 *            查询语句
	 * @param o
	 *            条件参数
	 * @return
	 */
	List<T> getByQuery(String hql, Object[] o) throws Exception;

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
	List<T> getAllByPagination(int start, int pageSize, String sortOrder,
			String orderBy, Class<T> entityClass) throws Exception;

	/**
	 * 根据hql分页查询
	 * 
	 * @param start
	 * @param pageSize
	 * @param hql
	 * @param o
	 * @return
	 */
	List<T> getByQueryPagination(int start, int pageSize, String hql, Object[] o)
			throws Exception;

	/**
	 * 
	 * @param id
	 * @return
	 */
	T getById(M id, Class<T> entityClass) throws Exception;

	/**
	 * 
	 * @param t
	 */
	
	void save(T t) throws Exception;

	/**
	 * 
	 * @param list
	 */
	void saveAll(List<T> list) throws Exception;

	/**
	 * 
	 * @param t
	 */
	void update(T t) throws Exception;

	/**
	 * 
	 * @param list
	 */
	void updateAll(List<T> list) throws Exception;

	/**
	 * 
	 * @param t
	 */
	void delete(T t) throws Exception;
	/**
	 * 
	 * @param id
	 * @param entityClass
	 * @throws Exception
	 */
	void deleteById(M id,Class<T> entityClass) throws Exception;
	/**
	 * 
	 * @param list
	 */
	void deleteAll(List<T> list) throws Exception;

	/**
	 * 执行hql修改
	 * 
	 * @param hql
	 * @param o
	 * @return
	 */
	int excuteUpdateByParam(String hql, Object[] o) throws Exception;

	/**
	 * 执行sql修改、删除
	 * 
	 * @param sql
	 * @param o
	 * @return
	 */
	int excuteUpdateSQL(String sql) throws Exception;

	/**
	 * 执行sql查询
	 * 
	 * @param sql
	 * @return
	 */
	List<Object[]> excuteQuerySQL(String sql) throws Exception;

	/**
	 * 得到总记录数
	 * 
	 * @param entityClass
	 * @return
	 */
	int getDataCount(Class<T> entityClass) throws Exception;

	/**
	 * 根据HQL得到总记录数
	 * 
	 * @param hql
	 * @param o
	 * @return
	 */
	int getDataCount(String hql, Object[] o) throws Exception;

	/**
	 * this session must be closed after using
	 */
	Session getSessionMustBeClosed() throws Exception;
}
