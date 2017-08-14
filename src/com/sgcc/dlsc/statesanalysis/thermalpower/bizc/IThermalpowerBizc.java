package com.sgcc.dlsc.statesanalysis.thermalpower.bizc;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sgcc.dlsc.statesanalysis.thermalpower.vo.SeTielineResultNVO;
import com.sgcc.uap.rest.support.IDRequestObject;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

/**
 * 单表场景逻辑构件
 *
 */
public interface IThermalpowerBizc {

	/**
	 * 保存更新方法
	 * @param ll
	 * @return list
	 */
	public List<SeTielineResultNVO> saveOrUpdate(List<Map> ll);
			
	/**
	 * 删除
	 * @param idObject
	 */
	public void remove(IDRequestObject idObject);
	/**
	 * 查询单条记录
	 * @param id
	 * @return QueryResultObject
	 */
	public QueryResultObject queryById(String id);

	public String getTopTenChart(String loginMarketId);

	public String getLastTenChart(String loginMarketId);
	
	/**
	 * 查询 查询 火电统调电厂完成率前10名 表格数据
	 * @param queryCondition
	 * @return QueryResultObject
	 */
	public QueryResultObject queryTopTen(RequestCondition queryCondition, String marketId);

	/**
	 * 查询 火电统调电厂完成率后10名 表格数据
	 * @param params
	 * @param loginMarketId
	 * @return
	 */
	public QueryResultObject queryLastTen(RequestCondition params,
			String loginMarketId);
	/**
	 * 查询 火电统调电厂完成率平均进度 表格数据
	 * 
	 */
	public QueryResultObject queryAverageData(RequestCondition params,
			String loginMarketId);

	public String getAverageDataChart(String param, String loginMarketId2);
}
