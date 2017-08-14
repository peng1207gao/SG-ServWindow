package com.sgcc.dlsc.contractManage.CoContransEnergyInfo.bizc;


import java.util.List;
import java.util.Map;

import com.sgcc.uap.rest.support.IDRequestObject;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;
import com.sgcc.dlsc.contractManage.CoContransEnergyInfo.vo.CoContransenergyinfoVO;
import com.sgcc.dlsc.entity.po.CoContransenergyinfo;

/**
 * 单表场景逻辑构件
 *
 */
public interface ICoContransEnergyInfoBizc {

	//保存记录
	public CoContransenergyinfoVO save(Map map);
	
	//更新记录
	public CoContransenergyinfoVO update(Map<String, ?> map,String poName,String id);

	/**
	 * 
	 * @description 判断合同是否有对应的输电方
	 * @return
	 * @author xiabaike
	 */
	public boolean judgeExist(String contractId,String guid,String sdf);
	
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
	 * @param contractId
	 * @param pageNo
	 * @param pageSize
	 * @return QueryResultObject
	 */
	public QueryResultObject query(String contractId,int pageNo,int pageSize);
	
	/**
	 * 查询单条记录
	 * @param id
	 * @return QueryResultObject
	 */
	public QueryResultObject queryById(String id, String marketId);
}
