package com.sgcc.dlsc.statesanalysis.jfcontractstate.bizc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.sgcc.dlsc.entity.po.SjjcJfHtlzxx;
import com.sgcc.dlsc.statesanalysis.jfcontractstate.vo.SjjcJfHtlzxxTransfer;
import com.sgcc.dlsc.statesanalysis.jfcontractstate.vo.SjjcJfHtlzxxVO;
import com.sgcc.uap.bizc.sysbizc.datadictionary.IDataDictionaryBizC;
import com.sgcc.uap.persistence.IHibernateDao;
import com.sgcc.uap.persistence.criterion.QueryCriteria;
import com.sgcc.uap.rest.support.IDRequestObject;
import com.sgcc.uap.rest.support.QueryFilter;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;
import com.sgcc.uap.rest.utils.CrudUtils;
import com.sgcc.uap.rest.utils.RestUtils;
/**
 * 用户定义逻辑构件
 * 
 * @author mengke 
 * 
 */
public class JfcontractstateBizc implements IJfcontractstateBizc{

	@Autowired
	private IHibernateDao hibernateDao;
	
	@Autowired
	private IDataDictionaryBizC dataDictionaryBizC;
	/**
	 * 保存更新
	 * 
	 * @param list
	 */
	 public List<SjjcJfHtlzxxVO> saveOrUpdate(List<Map> list) {
		 
		List<SjjcJfHtlzxxVO> voList = new ArrayList<SjjcJfHtlzxxVO>();
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
			String poName = SjjcJfHtlzxx.class.getName();
			if(map.containsKey("objId")){
				String id = (String)map.get("objId");
				SjjcJfHtlzxxVO vo = update(map,poName,id);
				voList.add(vo);
			}else{
				SjjcJfHtlzxxVO sjjcJfHtlzxxVO = save(map);
				voList.add(sjjcJfHtlzxxVO);
			}
		}
		return voList;
	 }
	 
	//保存记录
	private SjjcJfHtlzxxVO save(Map map) {
		
		SjjcJfHtlzxxVO sjjcJfHtlzxxVo = new SjjcJfHtlzxxVO();
		try {
			BeanUtils.populate(sjjcJfHtlzxxVo, map);
		} catch (Exception e) {
		}
		SjjcJfHtlzxx sjjcJfHtlzxx = SjjcJfHtlzxxTransfer.toPO(sjjcJfHtlzxxVo);
		hibernateDao.saveOrUpdateObject(sjjcJfHtlzxx);
		sjjcJfHtlzxxVo = SjjcJfHtlzxxTransfer.toVO(sjjcJfHtlzxx);
		if(map.containsKey("mxVirtualId")){
			sjjcJfHtlzxxVo.setMxVirtualId(String.valueOf(map.get("mxVirtualId")));
		}
		return sjjcJfHtlzxxVo;
	}
	
	//更新记录
	private SjjcJfHtlzxxVO update(Map<String, ?> map,String poName,String id) {
		
		SjjcJfHtlzxxVO sjjcJfHtlzxxVo = new SjjcJfHtlzxxVO();
		//更新操作
		try {
			BeanUtils.populate(sjjcJfHtlzxxVo, map);
		} catch (Exception e) {
		}
		Object[] objArray = CrudUtils.generateHql(SjjcJfHtlzxx.class, map, "objId");
		hibernateDao.update((String) objArray[0], (Object[]) objArray[1]);
		return sjjcJfHtlzxxVo;
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
			hibernateDao.update("delete from " + SjjcJfHtlzxx.class.getName() + " where objId = ?" ,new Object[]{id});
		}
	}
	
	/**
	 * 根据输入条件查询记录
	 * 
	 * @param queryCondition
	 * @return
	 */
	public QueryResultObject query(RequestCondition queryCondition) {
		List<QueryFilter> filterList = queryCondition.getQueryFilter(SjjcJfHtlzxxVO.class);
		String contractName = "";//合同名称
		String contractNo = "";//纸质合同编号
		String marketId = "";//运行单位编码
		if(filterList!=null&&filterList.size()>0){
			for(int i=0;i<filterList.size();i++){
				QueryFilter filter = filterList.get(i);
				String colName = filter.getFieldName();
				if(colName.equals("contractname")){//获取参数业务单元类型
					contractName = ((filter.getValue()==null||filter.getValue().equals("null"))?"":filter.getValue().toString());
				}else if(colName.equals("contractno")){
					contractNo = ((filter.getValue()==null||filter.getValue().equals("null"))?"":filter.getValue().toString());
				}else if(colName.equals("companyid")){
					marketId = ((filter.getValue()==null||filter.getValue().equals("null"))?"":filter.getValue().toString());
				}
			}
		}
		List<SjjcJfHtlzxxVO> result = new ArrayList<SjjcJfHtlzxxVO>();
		List list = new ArrayList();//存放查询结果
		List list1 = new ArrayList();//存放查询记录数
		List paramsList = new ArrayList();//存放参数
		int count = 0;//记录数
		StringBuffer sql = new StringBuffer();
		sql.append("	SELECT T.CONTRACTNO,                            ")
		.append("	       T.CONTRACTNAME,                              ")
		.append("	       MAX(T.CONTRACTSTATE),                        ")
		.append("	       T.COMPANYNAME                                ")
		.append("	  FROM SJJC_JF_HTLZXX T                             ")
		.append("	 WHERE 1 = 1                                        ");
		if(!contractName.equals("")){
			sql.append("AND T.CONTRACTNAME = ? ");
			paramsList.add(contractName);
		}
		if(!contractNo.equals("")){
			sql.append("AND T.CONTRACTNO = ? ");
			paramsList.add(contractNo);
		}
		if(!marketId.equals("")){
			sql.append("AND T.COMPANYID = ? ");
			paramsList.add(marketId);
		}
		sql.append(" GROUP BY T.CONTRACTNO,T.CONTRACTNAME,T.COMPANYNAME ");
		String sql2 = "SELECT COUNT(*) FROM ("+sql.toString()+") TT ";
		if(paramsList.size()>0){
			list1 = hibernateDao.executeSqlQuery(sql2, paramsList.toArray());
			list = hibernateDao.executeSqlQuery(sql.toString(),paramsList.toArray(),
				Integer.parseInt(queryCondition.getPageIndex()),Integer.parseInt(queryCondition.getPageSize()));
		}else{//无查询条件
			list1 = hibernateDao.executeSqlQuery(sql2);
			list = hibernateDao.executeSqlQuery(sql.toString(),
					Integer.parseInt(queryCondition.getPageIndex()),Integer.parseInt(queryCondition.getPageSize()));
		}
		count = list1.size()>0 ? Integer.parseInt(list1.get(0).toString()) : 0;
		if(list!=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				SjjcJfHtlzxxVO vo = new SjjcJfHtlzxxVO();
				Object[] obj = (Object[])list.get(i);
				vo.setContractno(obj[0]==null?"":obj[0].toString());
				vo.setContractname(obj[1]==null?"":obj[1].toString());
				vo.setContractstate(obj[2]==null?null:new BigDecimal(obj[2].toString()));
				vo.setCompanyname(obj[3]==null?"":obj[3].toString());
				result.add(vo);
			}
		}
		return RestUtils.wrappQueryResult(result, count);
	}
	
	private QueryCriteria wrapQuery(RequestCondition queryCondition,
			QueryCriteria qc) {
		List<QueryFilter> wheres = queryCondition.getQueryFilter(SjjcJfHtlzxxVO.class);
		if (wheres != null && wheres.size() > 0) {
			CrudUtils.addQCWhere(qc, wheres, SjjcJfHtlzxx.class.getName());

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
		SjjcJfHtlzxx sjjcJfHtlzxx = (SjjcJfHtlzxx) hibernateDao.getObject(SjjcJfHtlzxx.class,id);
		SjjcJfHtlzxxVO vo = null;
		if (sjjcJfHtlzxx != null) {
			vo = SjjcJfHtlzxxTransfer.toVO(sjjcJfHtlzxx);
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
