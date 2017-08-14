package com.sgcc.dlsc.txtManage.templatemanage.bizc;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sgcc.dlsc.txtManage.templatemanage.vo.CoContemplatewithparamVO;
import com.sgcc.dlsc.txtManage.templatemanage.vo.CoContracttemplateVO;
import com.sgcc.uap.rest.support.IDRequestObject;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

/**
 * 单表场景逻辑构件
 *
 */
public interface ITemplatemanageBizc {

	/**
	 * 保存更新方法
	 * @param ll
	 * @return list
	 */
	public List<CoContemplatewithparamVO> saveOrUpdate(List<Map> ll,String marketId);
			
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
	public QueryResultObject query(RequestCondition queryCondition,String marketId);
	/**
	 * 查询单条记录
	 * @param id
	 * @return QueryResultObject
	 */
	public QueryResultObject queryById(String id);
	
	/**
	 * 
	 * @description 生效范本（此方法中设计处理blob类型的字段，需要通过事务处理，故在方法名开始加save）
	 * @param id
	 * @return
	 * @author mengke
	 * @date 2014-5-22
	 */
	public String saveEffectContractTemplate(String id);
	
	/**
	 * 
	 * @description 获取合同范本附件名称
	 * @param id
	 * @return
	 * @author mengke
	 * @date 2014-5-26
	 */
	public String getFjmc(String id);
	
	/**
	 * 
	 * @description 附件下载 数据库保存blob文件
	 * @param guid
	 * @param request
	 * @param response
	 * @return
	 * @author mengke
	 * @date 2014-2-16
	 */
	public String downAffix(String guid,HttpServletRequest request,HttpServletResponse response);
	
	/**
	 * 
	 * @description 附件下载 数据库保存文件路径
	 * @param guid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author mengke
	 * @date 2014-6-19
	 */
	public String downAffix1(String guid,HttpServletRequest request,
			HttpServletResponse response)throws Exception;
	
	/**
	 * 
	 * @description 判断是否有可下载的范本
	 * @param guid
	 * @return
	 * @author mengke
	 * @date 2014-6-19
	 */
	public String isokdown(String guid);
}
