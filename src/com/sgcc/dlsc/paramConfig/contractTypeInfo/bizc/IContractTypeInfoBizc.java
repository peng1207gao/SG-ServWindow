package com.sgcc.dlsc.paramConfig.contractTypeInfo.bizc;

import java.util.List;
import java.util.Map;

import com.sgcc.dlsc.paramConfig.contractTypeInfo.vo.CoContracttypeinfoVO;
import com.sgcc.isc.core.orm.identity.User;
import com.sgcc.uap.rest.support.IDRequestObject;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

/**
 * 单表场景逻辑构件
 *
 */
public interface IContractTypeInfoBizc {

	/**
	 * 保存更新方法
	 * @param ll
	 * @return list
	 */
	public List<CoContracttypeinfoVO> saveOrUpdate(List<Map> ll,String marketId,String updatepersonId,User user);
			
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
	 * 查询下拉列表树的根节点
	 * @description 方法描述
	 * @param queryCondition
	 * @return
	 * @author mengke
	 * @date 2014-2-18
	 */
	public QueryResultObject queryTree(RequestCondition queryCondition);
	
	/**
	 * 查询下拉列表树的子节点
	 * @description 方法描述
	 * @param id
	 * @param params
	 * @return
	 * @author mengke
	 * @date 2014-2-18
	 */
	public QueryResultObject queryDepTreeChild(String id,RequestCondition params);
	
	/**
	 * 生效合同类型
	 * @description 方法描述
	 * @param contractTypeId
	 * @return
	 * @author mengke
	 * @date 2014-2-19
	 */
	public List effectContract(String contractTypeId);
	
	/**
	 * 失效合同类型
	 * @description 方法描述
	 * @param contractTypeId
	 * @return
	 * @author mengke
	 * @date 2014-2-19
	 */
	public List invalidContract(String contractTypeId,String marketId);
	
	/**
	 * 
	 * @description 获取已经存在的合同类型名称
	 * @param marketId
	 * @return
	 * @author mengke
	 * @date 2014-3-17
	 */
	public List getTypeName(String marketId,String typename,String supertypeid,String objID);
	
}
