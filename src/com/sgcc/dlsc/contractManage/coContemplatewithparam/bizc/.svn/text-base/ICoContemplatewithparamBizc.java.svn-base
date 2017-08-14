package com.sgcc.dlsc.contractManage.coContemplatewithparam.bizc;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sgcc.uap.rest.support.IDRequestObject;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;
import com.sgcc.dlsc.entity.po.CoContemplatewithparam;
import com.sgcc.dlsc.contractManage.coContemplatewithparam.vo.CoContemplatewithparamVO;

/**
 * 单表场景逻辑构件
 *
 */
public interface ICoContemplatewithparamBizc {

	/**
	 * 保存更新方法
	 * @param ll
	 * @return list
	 */
	public List<CoContemplatewithparamVO> saveOrUpdate(List<Map> ll);
			
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

	public String downLoadFile(String guid, HttpServletRequest request,	HttpServletResponse response);
}
