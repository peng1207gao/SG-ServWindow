package com.sgcc.dlsc.statesanalysis.jfcontractstate.bizc;

import java.util.List;
import java.util.Map;

import com.sgcc.dlsc.statesanalysis.jfcontractstate.vo.SjjcJfHtlzxxVO;
import com.sgcc.uap.rest.support.IDRequestObject;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

/**
 * 单表场景逻辑构件
 *
 */
public interface IJfcontractstateBizc {

	/**
	 * 保存更新方法
	 * @param ll
	 * @return list
	 */
	public List<SjjcJfHtlzxxVO> saveOrUpdate(List<Map> ll);
			
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
}
