package com.sgcc.dlsc.contractManage.CoContractbaseInfo.bizc;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.sgcc.uap.persistence.IHibernateDao;
import com.sgcc.uap.persistence.criterion.QueryCriteria;

import com.sgcc.uap.bizc.sysbizc.datadictionary.IDataDictionaryBizC;
import com.sgcc.uap.rest.support.DicItems;

import com.sgcc.uap.rest.support.IDRequestObject;
import com.sgcc.uap.rest.support.QueryFilter;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;
import com.sgcc.uap.rest.utils.CrudUtils;
import com.sgcc.uap.rest.utils.RestUtils;

//引入po,vo,transefer
import com.sgcc.dlsc.contractManage.CoContractbaseInfo.vo.CoContractbaseinfoTransfer;
import com.sgcc.dlsc.contractManage.CoContractbaseInfo.vo.CoContractbaseinfoVO;
import com.sgcc.dlsc.entity.po.CoContractbaseinfo;
/**
 * 用户定义逻辑构件
 * 
 * @author xiabaike 
 * 
 */
public class CoContractbaseInfoBizc implements ICoContractbaseInfoBizc{

	@Autowired
	private IHibernateDao hibernateDao;
	
	@Autowired
	private IDataDictionaryBizC dataDictionaryBizC;
	/**
	 * 保存更新
	 * 
	 * @param list
	 */
	 public List<CoContractbaseinfoVO> saveOrUpdate(List<Map> list) {
		 
		List<CoContractbaseinfoVO> voList = new ArrayList<CoContractbaseinfoVO>();
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
			String poName = CoContractbaseinfo.class.getName();
			if(map.containsKey("contractid")){
				String id = (String)map.get("contractid");
				CoContractbaseinfoVO vo = update(map,poName,id);
				voList.add(vo);
			}else{
				CoContractbaseinfoVO coContractbaseinfoVO = save(map);
				voList.add(coContractbaseinfoVO);
			}
		}
		return voList;
	 }
	 
	//保存记录
	private CoContractbaseinfoVO save(Map map) {
		
		CoContractbaseinfoVO coContractbaseinfoVo = new CoContractbaseinfoVO();
		try {
			BeanUtils.populate(coContractbaseinfoVo, map);
		} catch (Exception e) {
		}
		CoContractbaseinfo coContractbaseinfo = CoContractbaseinfoTransfer.toPO(coContractbaseinfoVo);
		hibernateDao.saveOrUpdateObject(coContractbaseinfo);
		coContractbaseinfoVo = CoContractbaseinfoTransfer.toVO(coContractbaseinfo);
		if(map.containsKey("mxVirtualId")){
			coContractbaseinfoVo.setMxVirtualId(String.valueOf(map.get("mxVirtualId")));
		}
		return coContractbaseinfoVo;
	}
	
	//更新记录
	private CoContractbaseinfoVO update(Map<String, ?> map,String poName,String id) {
		
		CoContractbaseinfoVO coContractbaseinfoVo = new CoContractbaseinfoVO();
		//更新操作
		try {
			BeanUtils.populate(coContractbaseinfoVo, map);
		} catch (Exception e) {
		}
		Object[] objArray = CrudUtils.generateHql(CoContractbaseinfo.class, map, "contractid");
		hibernateDao.update((String) objArray[0], (Object[]) objArray[1]);
		return coContractbaseinfoVo;
	}
	
	/**
	 * 删除
	 * 
	 * @param idObject
	 */
	public void remove(IDRequestObject idObject) {
		String[] ids = idObject.getIds();
		for (int i = 0; i < ids.length; i++) {
			String id = ids[i];
			hibernateDao.update("delete from " + CoContractbaseinfo.class.getName() + " where contractid = ?" ,new Object[]{id});
		}
	}
	
	/**
	 * 根据输入条件查询记录
	 * 
	 * @param queryCondition
	 * @return
	 */
	public QueryResultObject query(RequestCondition queryCondition) {

		QueryCriteria qc = new QueryCriteria();
		List<CoContractbaseinfo> result = null;
		int count = 0;
		qc.addFrom(CoContractbaseinfo.class);
		if (queryCondition != null) {
			qc = wrapQuery(queryCondition, qc);
			count = getRecordCount(qc);
			qc = wrapPage(queryCondition, qc);
			result = hibernateDao.findAllByCriteria(qc);

		} else {
			result = hibernateDao.findAllByCriteria(qc);
			count = getRecordCount(qc);
		}
		return RestUtils.wrappQueryResult(result, count);
		
		
	}
	private QueryCriteria wrapQuery(RequestCondition queryCondition,
			QueryCriteria qc) {
		List<QueryFilter> wheres = queryCondition.getQueryFilter(CoContractbaseinfoVO.class);
		if (wheres != null && wheres.size() > 0) {
			CrudUtils.addQCWhere(qc, wheres, CoContractbaseinfo.class.getName());

		}

		String orders = queryCondition.getSorter();
		if (orders != null) {
			qc.addOrder(orders.replaceAll("&", ","));
		}
		return qc;
	}
	
	private QueryCriteria wrapPage(RequestCondition queryCondition,
			QueryCriteria qc) {
		int pageIndex = 1, pageSize = 1;
		if (queryCondition.getPageIndex() != null
				&& queryCondition.getPageSize() != null) {
			pageIndex = Integer.valueOf(queryCondition.getPageIndex());
			pageSize = Integer.valueOf(queryCondition.getPageSize());
			qc.addPage(pageIndex, pageSize);
		}
		return qc;
	}
	
	/**
	 * 查询单条记录
	 * @param id
	 * @return QueryResultObject
	 */
	public QueryResultObject queryById(String id) {
		CoContractbaseinfo coContractbaseinfo = (CoContractbaseinfo) hibernateDao.getObject(CoContractbaseinfo.class,id);
		CoContractbaseinfoVO vo = null;
		if (coContractbaseinfo != null) {
			vo = CoContractbaseinfoTransfer.toVO(coContractbaseinfo);
		}
		return RestUtils.wrappQueryResult(vo);
	}

	
	// 获取总记录数
	private int getRecordCount(QueryCriteria qc) {
		int count = 0;
		count = hibernateDao.findAllByCriteriaPageCount(qc,1);
		return count;
	}

	public void setHibernateDao(IHibernateDao hibernateDao) {
		this.hibernateDao = hibernateDao;
	}
}
