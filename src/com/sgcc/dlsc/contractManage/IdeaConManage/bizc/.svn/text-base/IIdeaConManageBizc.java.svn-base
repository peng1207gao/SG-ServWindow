package com.sgcc.dlsc.contractManage.IdeaConManage.bizc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sgcc.dlsc.contractManage.IdeaConManage.vo.CoContractbaseinfoVO;
import com.sgcc.uap.rest.support.IDRequestObject;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;
import com.sgcc.uap.rest.support.TreeNode;

/**
 * 单表场景逻辑构件
 *
 */
public interface IIdeaConManageBizc {

	/**
	 * 保存更新方法
	 * @param ll
	 * @return list
	 */
	public List<CoContractbaseinfoVO> saveOrUpdate(List<Map> ll);
			
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
	public QueryResultObject query(RequestCondition queryCondition, String loginMarketId);
	/**
	 * 查询单条记录
	 * @param id
	 * @return QueryResultObject
	 */
	public QueryResultObject queryById(String id);

	public List getDictionaryByType(String string);

	public List qryBuyerList(String term);

	public List qrySellerList();

	public QueryResultObject getDetailContractInfo(String id);

	public QueryResultObject updateContractInfo(String id, String exetype, HttpServletRequest request);

	public String setUp(String params, HttpServletRequest request);
	
	public String setDown(String params, HttpServletRequest request);

	public List getContractTree(String string, String treeid, String sceneType);

	public QueryResultObject cancleIdeaRelation(String contractid,
			String isRootNode, HttpServletRequest request);

	public QueryResultObject cancleMultiLevelRelation(String contractid,
			String isRootNode);

	public ArrayList<TreeNode> getContractTreeRoot(String treeid, String sceneType);

	public Collection<? extends TreeNode> getNodesList(String contractid,
			String itemType);
	
}
