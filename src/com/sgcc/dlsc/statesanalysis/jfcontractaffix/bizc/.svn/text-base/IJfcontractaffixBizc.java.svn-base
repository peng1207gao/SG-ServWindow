package com.sgcc.dlsc.statesanalysis.jfcontractaffix.bizc;

import java.util.List;
import java.util.Map;

import com.sgcc.dlsc.statesanalysis.jfcontractaffix.vo.SjjcJfDljyhtwbVO;
import com.sgcc.uap.rest.support.IDRequestObject;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

/**
 * 单表场景逻辑构件
 *
 */
public interface IJfcontractaffixBizc {

	/**
	 * 保存更新方法
	 * @param ll
	 * @return list
	 */
	public List<SjjcJfDljyhtwbVO> saveOrUpdate(List<Map> ll);
			
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
	
	/**
	 * 获取运行单位下拉框数据
	 * @description 方法描述
	 * @return
	 * @author mengke
	 * @date 2014-3-14
	 */
	public List getWorkingUnit();
	
	/**
	 * 
	 * @description 运行单位默认为当前登录场景
	 * @param marketId
	 * @return
	 * @author mengke
	 * @date 2014-3-31
	 */
	public Map<String,String> initMarket(String marketId);
}
