package com.sgcc.dlsc.txtManage.cocontractparameter.bizc;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.sgcc.uap.rest.support.IDRequestObject;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;
import com.sgcc.dlsc.entity.po.CoContractparameter;
import com.sgcc.dlsc.txtManage.cocontractparameter.vo.CoContractparameterVO;

/**
 * 单表场景逻辑构件
 *
 */
public interface ICocontractparameterBizc {

	/**
	 * 复制标签
	 * @param tempparamid marketId userId bookmark
	 * @return true/false
	 * */
	public boolean copyParam(String marketId, String userId, String tempparamid, String bookmark);
	
	/**
	 * 执行数据维护，先删除后插入
	 * @description 方法描述
	 * @param templateCode 合同范本ID
	 * @param marketId 市场成员ID
	 * @param userId 用户ID
	 * @param dicValue 数据值
	 * @return
	 * @author wanshulu
	 * @date 2013-5-18
	 */
	public boolean updateCoParam(String templateCode, String marketId, String userId, String dicValue)throws SQLException;
	
	/**
	 * 保存更新方法
	 * @param ll
	 * @return list
	 */
	public List<CoContractparameterVO> saveOrUpdate(List<Map> ll);
			
	/**
	 * 删除
	 * @param idObject
	 */
	public void remove(IDRequestObject idObject);
	
	/**
	 * 查询页面详细配置信息
	 * @param templateCode
	 * @return List
	 */
	public List findAllParamDic(String templateCode);
	
	/**
	 * 查询
	 * @param queryCondition
	 * @param marketId
	 * @return QueryResultObject
	 */
	public QueryResultObject query(String contracttemplateId, String marketId,  int pageIndex, int pageSize);
	
	/**
	 * 查询单条记录
	 * @param id
	 * @return QueryResultObject
	 */
	public QueryResultObject queryById(String id);
}
