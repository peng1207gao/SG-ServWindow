package com.sgcc.dlsc.contractManage.coContractbackupInfo.bizc;


import java.util.List;
import java.util.Map;


import com.sgcc.uap.rest.support.IDRequestObject;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;
import com.sgcc.dlsc.contractManage.coContractbackupInfo.vo.CoContractbackupinfoVO;
import com.sgcc.dlsc.entity.po.CoContractbaseinfo;
import com.sgcc.dlsc.entity.po.CoContractchangerecordinfo;

/**
 * 单表场景逻辑构件
 *
 */
public interface ICoContractbackupInfoBizc {

	/**
	 * 输电方删除一条记录
	 * @param checkedId
	 * @return 
	 */
	public int sdfDelete(String checkedId);
	
	public int fjByList(List list);
	
	public List fjList(String marketid);

	public boolean update(CoContractbaseinfo coContractbaseinfo);
	
	public CoContractbaseinfo findById(String checkedId);
	
	public boolean save(CoContractchangerecordinfo ccc);
	/**
	 * 保存更新方法
	 * @param ll
	 * @return list
	 */
	public List<CoContractbackupinfoVO> saveOrUpdate(List<Map> ll);
			
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
}
