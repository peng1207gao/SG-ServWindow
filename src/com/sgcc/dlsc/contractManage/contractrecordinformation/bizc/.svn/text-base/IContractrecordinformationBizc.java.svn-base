package com.sgcc.dlsc.contractManage.contractrecordinformation.bizc;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.sgcc.uap.rest.support.IDRequestObject;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;
import com.sgcc.dlsc.contractManage.contractrecordinformation.vo.ContractRecordInformationVO;

/**
 * 单表场景逻辑构件
 *
 */
public interface IContractrecordinformationBizc {

	/**
	 * 查询合同备案信息
	 * @param queryCondition
	 * @author Jingbo.Fu
	 * @date 2014-02-11
	 * @return
	 */
	public QueryResultObject queryContractRecordInfo(RequestCondition queryCondition,String contractId);
	/**
	 * 签订合同信息
	 * @param contractId
	 * @return
	 * @author fujingbo
	 */
	public Map<String, List> getContractSignInfo(String contractId);
	/**
	 * 保存合同签订信息
	 * @param contractId
	 * @param signPer
	 * @param signDate
	 * @param signStatus
	 * @author fujingbo
	 * @return
	 */
	public int saveSignContract(String contractId,String signPer,String signDate,int signStatus);
	/**
	 * 保存更新方法
	 * @param ll
	 * @return list
	 */
	public List<ContractRecordInformationVO> saveOrUpdate(List<Map> ll);
			
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
