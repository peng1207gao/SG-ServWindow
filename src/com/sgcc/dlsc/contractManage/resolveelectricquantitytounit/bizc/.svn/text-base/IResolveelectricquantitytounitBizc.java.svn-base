package com.sgcc.dlsc.contractManage.resolveelectricquantitytounit.bizc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sgcc.uap.rest.support.IDRequestObject;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

/**
 * 单表场景逻辑构件
 *
 */
public interface IResolveelectricquantitytounitBizc {
	/**
	 * 保存更新方法
	 * @param marketid
	 * @param request
	 * @param items 电量被 修改过的记录
	 * @return list
	 */
	public String saveOrUpdate(String marketid, HttpServletRequest request);
			
	/**
	 * 删除
	 * @param idObject
	 */
	public void remove(IDRequestObject idObject);
	
	/**
	 * 查询
	 * @param queryCondition
	 * @return QueryResultObject
	 */
	public QueryResultObject query(RequestCondition queryCondition);
	/**
	 * 查询单条记录
	 * @param id
	 * @return QueryResultObject
	 */
	public QueryResultObject queryById(String id);

	public QueryResultObject contractAccessory(RequestCondition params);

	public String update(List<Map> items);
	
	public List getUnitList(String params, String isUnit);

	public QueryResultObject getUnitGrid(RequestCondition params);

}
