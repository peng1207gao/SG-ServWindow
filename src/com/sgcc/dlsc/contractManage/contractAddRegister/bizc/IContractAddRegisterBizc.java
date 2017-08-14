package com.sgcc.dlsc.contractManage.contractAddRegister.bizc;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sgcc.uap.rest.support.QueryResultObject;

import com.sgcc.dlsc.contractManage.coContractbaseinfoV2Act.vo.CoContractbaseinfoVO;

/**
 * 单表场景逻辑构件
 *
 */
public interface IContractAddRegisterBizc {

	/**
	 * 保存更新方法
	 * @param ll
	 * @return list
	 */
	public List<CoContractbaseinfoVO> saveOrUpdate(List<Map> ll,HttpServletRequest request);
	/**
	 * 查询单条记录
	 * @param id
	 * @return QueryResultObject
	 */
	public QueryResultObject queryById(String id);
	
	/**
	 * 
	 * @description 获取批复电价
	 * @param params
	 * @return
	 * @author mengke
	 * @date 2014-6-5
	 */
	public List getPfdjList(String params);
	
	/**
	 * 
	 * @description 获取厂用电率
	 * @param perchaserId
	 * @param marketId
	 * @return
	 * @author mengke
	 * @date 2014-7-15
	 */
	public List getCydl(String participantid,String marketId);
}
