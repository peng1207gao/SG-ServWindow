package com.sgcc.dlsc.contractManage.cocontractaccessory.bizc;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sgcc.uap.rest.support.IDRequestObject;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;
import com.sgcc.dlsc.contractManage.cocontractaccessory.vo.CoBaGeneratorVO;
import com.sgcc.dlsc.contractManage.cocontractaccessory.vo.CoContractaccessoryVO;

/**
 * 单表场景逻辑构件
 *
 */
public interface ICocontractaccessoryBizc {

	/**
	 * 保存更新方法
	 * @param ll
	 * @return list
	 */
	public List<CoContractaccessoryVO> saveOrUpdate(List<CoContractaccessoryVO> vos,HttpServletRequest request);
			
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
	 * 初始化字典值
	 * @return QueryResultObject
	 */
	public QueryResultObject initDict();
	
	/**
	 * 检索机组信息
	 * @description 方法描述
	 * @param contractrole
	 * @param contractid
	 * @return
	 * @author mengke
	 * @date 2013-4-16
	 */
	public List<CoBaGeneratorVO> getGenerator(RequestCondition params);

	public List getDropDownList(String params);

}
