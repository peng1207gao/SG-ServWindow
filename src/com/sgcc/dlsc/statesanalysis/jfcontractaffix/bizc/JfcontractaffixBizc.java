package com.sgcc.dlsc.statesanalysis.jfcontractaffix.bizc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.sgcc.dlsc.commons.util.MatchMarket;
import com.sgcc.dlsc.entity.po.SjjcJfDljyhtwb;
import com.sgcc.dlsc.statesanalysis.jfcontractaffix.vo.SjjcJfDljyhtwbTransfer;
import com.sgcc.dlsc.statesanalysis.jfcontractaffix.vo.SjjcJfDljyhtwbVO;
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
public class JfcontractaffixBizc implements IJfcontractaffixBizc{

	@Autowired
	private IHibernateDao hibernateDao;
	
	@Autowired
	private IDataDictionaryBizC dataDictionaryBizC;
	/**
	 * 保存更新
	 * 
	 * @param list
	 */
	 public List<SjjcJfDljyhtwbVO> saveOrUpdate(List<Map> list) {
		 
		List<SjjcJfDljyhtwbVO> voList = new ArrayList<SjjcJfDljyhtwbVO>();
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
			String poName = SjjcJfDljyhtwb.class.getName();
			if(map.containsKey("objId")){
				String id = (String)map.get("objId");
				SjjcJfDljyhtwbVO vo = update(map,poName,id);
				voList.add(vo);
			}else{
				SjjcJfDljyhtwbVO sjjcJfDljyhtwbVO = save(map);
				voList.add(sjjcJfDljyhtwbVO);
			}
		}
		return voList;
	 }
	 
	//保存记录
	private SjjcJfDljyhtwbVO save(Map map) {
		
		SjjcJfDljyhtwbVO sjjcJfDljyhtwbVo = new SjjcJfDljyhtwbVO();
		try {
			BeanUtils.populate(sjjcJfDljyhtwbVo, map);
		} catch (Exception e) {
		}
		SjjcJfDljyhtwb sjjcJfDljyhtwb = SjjcJfDljyhtwbTransfer.toPO(sjjcJfDljyhtwbVo);
		hibernateDao.saveOrUpdateObject(sjjcJfDljyhtwb);
		sjjcJfDljyhtwbVo = SjjcJfDljyhtwbTransfer.toVO(sjjcJfDljyhtwb);
		if(map.containsKey("mxVirtualId")){
			sjjcJfDljyhtwbVo.setMxVirtualId(String.valueOf(map.get("mxVirtualId")));
		}
		return sjjcJfDljyhtwbVo;
	}
	
	//更新记录
	private SjjcJfDljyhtwbVO update(Map<String, ?> map,String poName,String id) {
		
		SjjcJfDljyhtwbVO sjjcJfDljyhtwbVo = new SjjcJfDljyhtwbVO();
		//更新操作
		try {
			BeanUtils.populate(sjjcJfDljyhtwbVo, map);
		} catch (Exception e) {
		}
		Object[] objArray = CrudUtils.generateHql(SjjcJfDljyhtwb.class, map, "objId");
		hibernateDao.update((String) objArray[0], (Object[]) objArray[1]);
		return sjjcJfDljyhtwbVo;
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
			hibernateDao.update("delete from " + SjjcJfDljyhtwb.class.getName() + " where objId = ?" ,new Object[]{id});
		}
	}
	
	/**
	 * 根据输入条件查询记录
	 * 
	 * @param queryCondition
	 * @return
	 */
	public QueryResultObject query(RequestCondition queryCondition) {
		List<QueryFilter> filterList = queryCondition.getQueryFilter(SjjcJfDljyhtwbVO.class);
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
		List<SjjcJfDljyhtwbVO> result = new ArrayList<SjjcJfDljyhtwbVO>();
		List list = new ArrayList();//存放查询结果
		List list1 = new ArrayList();//存放查询记录数
		List paramsList = new ArrayList();//存放参数
		int count = 0;//记录数
		StringBuffer sql = new StringBuffer();
		sql.append("	SELECT T.CONTRACTNO, T.CONTRACTNAME, T.COMPANYNAME, COUNT(S.CONTRACTID) ,T.CONTRACTID  ")
		.append("		FROM SJJC_JF_DLJYHTWB T                                                 ")
		.append("		LEFT JOIN CO_CONTRACTAFFIXINFO S                                        ")
		.append("		ON S.CONTRACTID = T.CONTRACTID                                        ")
		.append("		WHERE 1=1                                                             ");

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
		sql.append("	    GROUP BY T.CONTRACTNO,T.CONTRACTNAME, T.COMPANYNAME ,T.CONTRACTID    ");
		sql.append("	    ORDER BY nlssort(T.CONTRACTNO, 'NLS_SORT=SCHINESE_PINYIN_M')          ");
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
				SjjcJfDljyhtwbVO vo = new SjjcJfDljyhtwbVO();
				Object[] obj = (Object[])list.get(i);
				vo.setContractno(obj[0]==null?"":obj[0].toString());
				vo.setContractname(obj[1]==null?"":obj[1].toString());
				vo.setCompanyname(obj[2]==null?"":obj[2].toString());
				vo.setAppendixname(obj[3]==null?"":obj[3].toString());
				vo.setContractid(obj[4]==null?"":obj[4].toString());
				result.add(vo);
			}
		}
		return RestUtils.wrappQueryResult(result, count);
	}
	
	/**
	 * 获取运行单位下拉框数据
	 * @description 方法描述
	 * @return
	 * @author mengke 
	 * @date 2014-3-14
	 */
	public List getWorkingUnit(){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT T.CODE,T.NAME FROM SJJC_UNIT_CODE T");
		List list = hibernateDao.executeSqlQuery(sql.toString());
		return list;
	}
	
	/**
	 * 
	 * @description 运行单位默认为当前登录场景
	 * @param marketId
	 * @return
	 * @author mengke 
	 * @date 2014-3-31
	 */
	public Map<String,String> initMarket(String marketId){
		Map<String,String> map = MatchMarket.initMarket(hibernateDao, marketId);
		return map;
	}
	
	private QueryCriteria wrapQuery(RequestCondition queryCondition,
			QueryCriteria qc) {
		List<QueryFilter> wheres = queryCondition.getQueryFilter(SjjcJfDljyhtwbVO.class);
		if (wheres != null && wheres.size() > 0) {
			CrudUtils.addQCWhere(qc, wheres, SjjcJfDljyhtwb.class.getName());

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
		SjjcJfDljyhtwb sjjcJfDljyhtwb = (SjjcJfDljyhtwb) hibernateDao.getObject(SjjcJfDljyhtwb.class,id);
		SjjcJfDljyhtwbVO vo = null;
		if (sjjcJfDljyhtwb != null) {
			vo = SjjcJfDljyhtwbTransfer.toVO(sjjcJfDljyhtwb);
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
