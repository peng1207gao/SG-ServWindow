package com.sgcc.dlsc.statesanalysis.kqksexetrace.bizc;

import java.util.List;
import java.util.Map;

import com.sgcc.dlsc.statesanalysis.kqksexetrace.vo.CoContractbaseinfoVO;
import com.sgcc.uap.rest.support.IDRequestObject;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

/**
 * 单表场景逻辑构件
 *
 */
public interface IKqksexetraceBizc {

	/**
	 * 保存更新方法
	 * @param ll
	 * @return list
	 */
	public List<CoContractbaseinfoVO> saveOrUpdate(List<Map> ll);
			
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
	public QueryResultObject query(RequestCondition queryCondition,String marketId);
	
	/**
	 * 查询单条记录
	 * @param id
	 * @return QueryResultObject
	 */
	public QueryResultObject queryById(String id);
	
	/**
	 * 
	 * @description 联络线名称智能查询
	 * @param term
	 * @param marketId
	 * @return
	 * @author mengke
	 * @date 2014-4-28
	 */
	public List getLineName(String term,String marketId);
	
	/**
	 * 
	 * @description 获取图表数据
	 * @param params
	 * @param loginMarketId
	 * @return
	 * @author mengke
	 * @date 2014-4-29
	 */
	public String getDataChart(String params, String marketId);
}
