package com.thtf.commons;

import java.util.List;

import com.thtf.entity.BasicObject;

public class PageUtil<T extends BasicObject> {
	public static int DEFAULT_PAGE_SIZE = 10;

	private int pageSize = DEFAULT_PAGE_SIZE; // 每页的记录数

	private int start; // 当前页第一条数据在List中的位置,从0开始

	private int pageNum; // 当前页数

	private List<T> data; // 当前页中存放的记录,类型一般为List

	private int totalCount; // 总记录数

	private String sortOrder;// 排序方式 true 'ASC' or false 'DESC'

	private String sortName;// 排序字段

	
	public PageUtil() {
		super();
	}
	/**
	 * 用于存放页面传递参数
	 * @param pageSize
	 * @param start
	 * @param isAsc
	 * @param sortName
	 */
	public PageUtil(int pageSize, int start, String sortOrder, String sortName) {
		this.pageSize = pageSize;
		this.start = start;
		this.sortOrder= sortOrder;
		this.sortName = sortName;
	}

	public PageUtil(int pageSize, int start, int pageNum, List<T> data,
			int totalCount) {
		this.pageSize = pageSize;
		this.start = start;
		this.pageNum = pageNum;
		this.data = data;
		this.totalCount = totalCount;
	}
	public PageUtil(int pageSize, int start, int pageNum, List<T> data,
			int totalCount, String sortOrder, String sortName) {
		this.pageSize = pageSize;
		this.start = start;
		this.pageNum = pageNum;
		this.data = data;
		this.totalCount = totalCount;
		this.sortOrder = sortOrder;
		this.sortName = sortName;
	}

	/**
	 * 每页记录数
	 * 
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 每页记录数
	 * 
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public void setPageSize(String pageSize) {
		if(pageSize!=null&&!"".equals(pageSize)){
			this.pageSize = Integer.parseInt(pageSize);
		}
	}
	/**
	 * 当前页第一条数据在List中的位置,从0开始
	 * 
	 * @return
	 */
	public int getStart() {
		return start;
	}

	/**
	 * 当前页第一条数据在List中的位置,从0开始
	 * 
	 * @param start
	 */
	public void setStart(int start) {
		this.start = start;
	}

	/**
	 * 当前页数
	 * 
	 * @return
	 */
	public int getPageNum() {
		return pageNum;
	}

	/**
	 * 当前页数
	 * 
	 * @param pageNum
	 */
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public void setPageNum(String pageNum) {
		if(pageNum!=null&&!"".equals(pageNum)){
			this.pageNum = Integer.parseInt(pageNum);
		}
	}

	/**
	 * 当前页中存放的记录,类型一般为List
	 * 
	 * @return
	 */
	public List<T> getData() {
		return data;
	}

	/**
	 * 当前页中存放的记录,类型一般为List
	 * 
	 * @param data
	 */
	public void setData(List<T> data) {
		this.data = data;
	}

	/**
	 * 总记录数
	 * 
	 * @return
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * 总记录数
	 * 
	 * @param totalCount
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * 排序方式 true 'ASC' or false 'DESC'
	 * 
	 * @return
	 */
	public String getSortOrder() {
		return sortOrder;
	}

	/**
	 * 排序方式 true 'ASC' or false 'DESC'
	 * 
	 * @param isAsc
	 */
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	/**
	 * 排序字段名
	 * 
	 * @return
	 */
	public String getSortName() {
		return sortName;
	}

	/**
	 * 排序字段名
	 * 
	 * @param sortName
	 */
	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

}
