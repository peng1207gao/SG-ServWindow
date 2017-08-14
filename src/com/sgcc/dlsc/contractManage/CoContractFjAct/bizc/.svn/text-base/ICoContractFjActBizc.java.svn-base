package com.sgcc.dlsc.contractManage.CoContractFjAct.bizc;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sgcc.dlsc.contractManage.CoContractFjAct.vo.CoContractaffixinfoVO;
import com.sgcc.uap.rest.support.IDRequestObject;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

/**
 * 单表场景逻辑构件
 *
 */
public interface ICoContractFjActBizc {

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
	public QueryResultObject queryById(String id);
	
	/**
	 * 统计数量
	 * @description 方法描述
	 * @param contractid
	 * @return
	 * @author mengke
	 * @date 2013-4-16
	 */
	public int getCount(String contractid);
	
	
	/**
	 * 获取文件
	 * @description 方法描述
	 * @param pkVal
	 * @return
	 * @author wenwu
	 * @date 2013-4-16
	 */
	public List getFile(String pkVal);
	
	/**
	  * 合同关联附件-查询
	 * @param pageIndex(第几页)
	 * @param pageSize(总共几页)
	 * @param contractid
	 * @return
	 */
	public QueryResultObject findAffList(int pageIndex,int pageSize,
			String contractid);
	
	 //下载附件
	 public String downAffix(String guid,
				HttpServletRequest request, HttpServletResponse response);
}
