package com.sgcc.dlsc.contractManage.cocontranslossinfo.bizc;

import java.util.List;
import java.util.Map;

import com.sgcc.uap.rest.support.IDRequestObject;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;
import com.sgcc.dlsc.contractManage.cocontranslossinfo.vo.CoContranslossinfoVO;
import com.sgcc.dlsc.entity.po.CoContranslossinfo;

/**
 * 线损信息信息 {@link CoContranslossinfo}维护逻辑构件
 *
 * @author djdeng
 */
public interface ICoContranslossinfoBizc {

	/**
	 * 保存更新方法
	 * 
	 * @param contranslossinfos
	 * 
	 * @return list
	 */
	public List<CoContranslossinfoVO> saveOrUpdate(List<Map> contranslossinfos);
			
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
	 * 根据合同Id查询合同的信息
	 * 
	 * @param contractid 合同Id
	 * @return 合同信息
	 * @see CoContranslossinfoVO
	 */
	public QueryResultObject queryContractById(String contractid);
	
	/**
	 * 
	 * @description 根据linkID查询需要的信息，并返回一个字符串
	 * 
	 * @param linkId
	 * @return
	 * @author gaoming
	 * @date 2013-1-18
	 */
	public String findByLinkId(String linkId);
}
