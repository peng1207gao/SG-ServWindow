package com.sgcc.dlsc.contractManage.coContractgateinfo.bizc;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sgcc.uap.rest.support.IDRequestObject;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;
import com.sgcc.dlsc.entity.po.CoContractgateinfo;
import com.sgcc.dlsc.contractManage.coContractgateinfo.vo.CoContractgateinfoVO;

/**
 * 单表场景逻辑构件
 *
 */
public interface ICoContractgateinfoBizc {

	/**
	 * 保存更新方法
	 * @param ll
	 * @return list
	 */
	public List<CoContractgateinfoVO> saveOrUpdate(List<Map> ll,HttpServletRequest request);
			
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
	public QueryResultObject queryById(String id, String marketId);

	public List getBusiunitSel(String participantid);

	public QueryResultObject findCoContractList(int pageIndex, int pageSize,
			String contractID, String marketId);
	/**
	 * 
	 * @description 方法描述
	 * @param contractid
	 * @return
	 * @author xuzihu
	 * @date 2014-7-15
	 */
	public List getMarketparent(String contractid);
	/**
	 * 
	 * @description 方法描述
	 * @param participantid
	 * @return
	 * @author xuzihu
	 * @date 2014-7-16
	 */
	public List getGateItems(String participantid);
	/**
	 * 
	 * @description 方法描述
	 * @return
	 * @author xuzihu
	 * @date 2014-7-16
	 */
	public List getTieline(String marketid);
	/**
	 * 
	 * @description 方法描述
	 * @param participantid
	 * @return
	 * @author xuzihu
	 * @date 2014-7-16
	 */
	public List getTielinebyparticipantid(String participantid);
	/**
	 * 
	 * @description 方法描述
	 * @param tielineid
	 * @return
	 * @author xuzihu
	 * @date 2014-7-16
	 */
	public List getGateidbytieline(String tielineid);
}
