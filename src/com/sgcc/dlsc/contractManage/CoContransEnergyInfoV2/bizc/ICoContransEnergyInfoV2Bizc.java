package com.sgcc.dlsc.contractManage.CoContransEnergyInfoV2.bizc;

import java.util.List;
import java.util.Map;

import com.sgcc.uap.rest.support.IDRequestObject;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;
import com.sgcc.dlsc.entity.po.CoContransenergyinfo;
import com.sgcc.dlsc.contractManage.CoContransEnergyInfoV2.vo.CoContransenergyinfoVO;

/**
 * 单表场景逻辑构件
 *
 */
public interface ICoContransEnergyInfoV2Bizc {

	/**
	 * 保存更新方法
	 * @param ll
	 * @return list
	 */
	public List<CoContransenergyinfoVO> saveOrUpdate(List<Map> ll);
			
	/**
	 * 删除
	 * @param idObject
	 */
	public void remove(IDRequestObject idObject);
	
	/**
	 * 查询
	 * @param contractId pageIndex  pageSize
	 * @return QueryResultObject
	 * @throws Exception 
	 */
	public QueryResultObject query(String contractId, int pageIndex, int pageSize) throws Exception;
	/**
	 * 查询单条记录
	 * @param id
	 * @return QueryResultObject
	 */
	public QueryResultObject queryById(String id, String marketId);
}
