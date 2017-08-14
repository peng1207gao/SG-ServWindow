package com.sgcc.dlsc.contractManage.contractaffixinfo.bizc;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sgcc.dlsc.contractManage.contractaffixinfo.vo.CoContractaffixinfoVO;
import com.sgcc.uap.rest.support.IDRequestObject;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

/**
 * 单表场景逻辑构件
 *
 */
public interface IContractaffixinfoBizc {

	/**
	 * 保存更新方法
	 * @param ll
	 * @return list
	 */
	public List<CoContractaffixinfoVO> saveOrUpdate(List<Map> ll);
			
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
	public QueryResultObject queryById(String id,String uploadPerson,String uploadTime,String marketId);
	
	/**
	 * 附件下载
	 * @description 方法描述
	 * @param guid
	 * @param request
	 * @param response
	 * @return
	 * @author mengke
	 * @date 2014-2-16
	 */
	public String downAffix(String guid,HttpServletRequest request,HttpServletResponse response);
}
