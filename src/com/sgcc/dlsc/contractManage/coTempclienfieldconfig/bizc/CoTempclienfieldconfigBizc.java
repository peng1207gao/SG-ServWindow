package com.sgcc.dlsc.contractManage.coTempclienfieldconfig.bizc;

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
import com.sgcc.dlsc.entity.po.CoTempclienfieldconfig;
import com.sgcc.dlsc.contractManage.coTempclienfieldconfig.vo.CoTempclienfieldconfigTransfer;
import com.sgcc.dlsc.contractManage.coTempclienfieldconfig.vo.CoTempclienfieldconfigVO;
/**
 * 用户定义逻辑构件
 * 
 * @author thinpad 
 * 
 */
public class CoTempclienfieldconfigBizc implements ICoTempclienfieldconfigBizc{

	@Autowired
	private IHibernateDao hibernateDao;
	
	@Autowired
	private IDataDictionaryBizC dataDictionaryBizC;
	/**
	 * 保存更新
	 * 
	 * @param list
	 */
	 public List<CoTempclienfieldconfigVO> saveOrUpdate(List<Map> list) {
		 
		List<CoTempclienfieldconfigVO> voList = new ArrayList<CoTempclienfieldconfigVO>();
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
			String poName = CoTempclienfieldconfig.class.getName();
			if(map.containsKey("sheetid")){
				String id = (String)map.get("sheetid");
				CoTempclienfieldconfigVO vo = update(map,poName,id);
				voList.add(vo);
			}else{
				CoTempclienfieldconfigVO coTempclienfieldconfigVO = save(map);
				voList.add(coTempclienfieldconfigVO);
			}
		}
		return voList;
	 }
	 
	//保存记录
	private CoTempclienfieldconfigVO save(Map map) {
		
		CoTempclienfieldconfigVO coTempclienfieldconfigVo = new CoTempclienfieldconfigVO();
		try {
			BeanUtils.populate(coTempclienfieldconfigVo, map);
		} catch (Exception e) {
		}
		CoTempclienfieldconfig coTempclienfieldconfig = CoTempclienfieldconfigTransfer.toPO(coTempclienfieldconfigVo);
		hibernateDao.saveOrUpdateObject(coTempclienfieldconfig);
		coTempclienfieldconfigVo = CoTempclienfieldconfigTransfer.toVO(coTempclienfieldconfig);
		if(map.containsKey("mxVirtualId")){
			coTempclienfieldconfigVo.setMxVirtualId(String.valueOf(map.get("mxVirtualId")));
		}
		return coTempclienfieldconfigVo;
	}
	
	//更新记录
	private CoTempclienfieldconfigVO update(Map<String, ?> map,String poName,String id) {
		
		CoTempclienfieldconfigVO coTempclienfieldconfigVo = new CoTempclienfieldconfigVO();
		//更新操作
		try {
			BeanUtils.populate(coTempclienfieldconfigVo, map);
		} catch (Exception e) {
		}
		Object[] objArray = CrudUtils.generateHql(CoTempclienfieldconfig.class, map, "sheetid");
		hibernateDao.update((String) objArray[0], (Object[]) objArray[1]);
		return coTempclienfieldconfigVo;
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
			hibernateDao.update("delete from " + CoTempclienfieldconfig.class.getName() + " where sheetid = ?" ,new Object[]{id});
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
		List<CoTempclienfieldconfig> result = null;
		int count = 0;
		qc.addFrom(CoTempclienfieldconfig.class);
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
		List<QueryFilter> wheres = queryCondition.getQueryFilter(CoTempclienfieldconfigVO.class);
		if (wheres != null && wheres.size() > 0) {
			CrudUtils.addQCWhere(qc, wheres, CoTempclienfieldconfig.class.getName());

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
		CoTempclienfieldconfig coTempclienfieldconfig = (CoTempclienfieldconfig) hibernateDao.getObject(CoTempclienfieldconfig.class,id);
		CoTempclienfieldconfigVO vo = null;
		if (coTempclienfieldconfig != null) {
			vo = CoTempclienfieldconfigTransfer.toVO(coTempclienfieldconfig);
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
