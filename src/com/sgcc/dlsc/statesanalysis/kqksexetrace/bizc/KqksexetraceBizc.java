package com.sgcc.dlsc.statesanalysis.kqksexetrace.bizc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.sgcc.dlsc.commons.bean.FCcategories;
import com.sgcc.dlsc.commons.bean.FCchart;
import com.sgcc.dlsc.commons.bean.FCdataset;
import com.sgcc.dlsc.entity.po.CoContractbaseinfo;
import com.sgcc.dlsc.statesanalysis.kqksexetrace.vo.CoContractbaseinfoTransfer;
import com.sgcc.dlsc.statesanalysis.kqksexetrace.vo.CoContractbaseinfoVO;
import com.sgcc.uap.bizc.sysbizc.datadictionary.IDataDictionaryBizC;
import com.sgcc.uap.persistence.IHibernateDao;
import com.sgcc.uap.persistence.criterion.QueryCriteria;
import com.sgcc.uap.rest.support.IDRequestObject;
import com.sgcc.uap.rest.support.QueryFilter;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;
import com.sgcc.uap.rest.utils.CrudUtils;
import com.sgcc.uap.rest.utils.JsonUtils;
import com.sgcc.uap.rest.utils.RestUtils;
/**
 * 用户定义逻辑构件
 * 
 * @author mengke 
 * 
 */
public class KqksexetraceBizc implements IKqksexetraceBizc{

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
	public QueryResultObject query(RequestCondition queryCondition,String marketId) {
		List<QueryFilter> filterList = queryCondition.getQueryFilter(CoContractbaseinfoVO.class);
		String lineName = "";//合同名称
		String startTime = "";//纸质合同编号
		String endTime = "";//运行单位编码
		String type = "";
		if(filterList!=null&&filterList.size()>0){
			for(int i=0;i<filterList.size();i++){
				QueryFilter filter = filterList.get(i);
				String colName = filter.getFieldName();
				if(colName.equals("papercontractname")){
					lineName = ((filter.getValue()==null||filter.getValue().equals("null"))?"":filter.getValue().toString());
				}else if(colName.equals("signeddate")){
					startTime = ((filter.getValue()==null||filter.getValue().equals("null"))?"":filter.getValue().toString());
				}else if(colName.equals("backupdate")){
					endTime = ((filter.getValue()==null||filter.getValue().equals("null"))?"":filter.getValue().toString());
				}else if(colName.equals("expend5")){
					type = ((filter.getValue()==null||filter.getValue().equals("null"))?"":filter.getValue().toString());
				}
			}
		}
		List<CoContractbaseinfoVO> result = new ArrayList<CoContractbaseinfoVO>();
		List list = new ArrayList();//存放查询结果
		List paramList = new ArrayList();//存放查询条件
		List list1 = new ArrayList();//存放查询结果数
		int count = 0;//记录数
		StringBuffer sql = new StringBuffer();
		sql.append("		SELECT T.PAPERCONTRACTNAME,                                   ")
		.append("		       T.CONTRACTQTY,                                             ")
		.append("		       CASE WHEN S.ENERGY_T IS NULL THEN 0 ELSE SUM(S.ENERGY_T) END AS WCDL, ")
		.append("		       CASE WHEN (T.CONTRACTQTY=0 OR T.CONTRACTQTY IS NULL) THEN 0 ELSE ")
		.append("       ROUND(100*(CASE WHEN (S.ENERGY_T=0 OR S.ENERGY_T IS NULL) THEN 0 ELSE SUM(S.ENERGY_T) END) / T.CONTRACTQTY,2) END AS WCL                     ")
		.append("		  FROM CO_CONTRACTBASEINFO T                                      ")
		.append("		  LEFT JOIN SE_TIELINE_RESULT_N S                                 ")
		.append("		    ON T.CONTRACTID = S.CONTRACT_ID                               ")
		.append("		  LEFT JOIN BA_INTERTIE TT                                        ")
		.append("		    ON TT.LINKID = S.TIELINE_ID                                   ");
		if("purchase".equals(type)){
			sql.append("		  LEFT JOIN BA_MARKETPARTICIPANT Q                                ")
			.append("		    ON Q.PARTICIPANTID = T.PURCHASER                              ");
		}else if("sell".equals(type)){
			sql.append("		  LEFT JOIN BA_MARKETPARTICIPANT Q                                ")
			.append("		    ON Q.PARTICIPANTID = T.SELLER                              ");
		}else if("trans".equals(type)){
			sql.append(" LEFT JOIN CO_TRANSQTYINFO B ON B.CONTRACTID = T.CONTRACTID  ")
			.append(" LEFT JOIN BA_MARKETPARTICIPANT Q ON Q.PARTICIPANTID = B.TRANSMISSION ");
		}
		sql.append("		 WHERE Q.MARKETID = ? AND 1=1                                     ");
		paramList.add(marketId);
		if(!lineName.equals("")){//联络线名称
			sql.append("		   AND TT.LINKNAME = ?                                    ");
			paramList.add(lineName);
		}else if(!startTime.equals("")){//开始时间
			sql.append("		   AND (T.CONTRACTSTARTDATE >= TO_DATE(?,'YYYY-MM-DD') OR T.CONTRACTSTARTDATE IS NULL)   ");
			paramList.add(startTime);
		}else if(!endTime.equals("")){//结束时间
			sql.append("		   AND (T.CONTRACTENDDATE <= TO_DATE(?,'YYYY-MM-DD') OR T.CONTRACTENDDATE IS NULL)       ");
			paramList.add(endTime);
		}
		sql.append("		 GROUP BY T.PAPERCONTRACTNAME, T.CONTRACTQTY,S.ENERGY_T                      ");
		sql.append("		 ORDER BY WCL DESC ");
		String sql2 = "SELECT COUNT(*) FROM ("+sql.toString()+") TS ";
		list1 = hibernateDao.executeSqlQuery(sql2, paramList.toArray());
		list = hibernateDao.executeSqlQuery(sql.toString(),paramList.toArray(),
			Integer.parseInt(queryCondition.getPageIndex()),Integer.parseInt(queryCondition.getPageSize()));
		count = list1.size()>0 ? Integer.parseInt(list1.get(0).toString()) : 0;//查询结果总数
		if(list!=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				CoContractbaseinfoVO vo = new CoContractbaseinfoVO();
				Object[] obj = (Object[])list.get(i);
				vo.setPapercontractname(obj[0]==null?"":obj[0].toString());//合同名称
				vo.setContractqty(obj[1]==null?null:new BigDecimal(obj[1].toString()));//合同电量
				vo.setEnergy(obj[2]==null?null:new BigDecimal(obj[2].toString()));//完成电量
				vo.setPapercontractcode(obj[3]==null?"":obj[3].toString()+"%");//完成率
				result.add(vo);
			}
		}
		return RestUtils.wrappQueryResult(result, count);
	}
	
	/**
	 * 
	 * @description 联络线名称智能查询
	 * @param term
	 * @param marketId
	 * @return
	 * @author mengke 
	 * @date 2014-3-25
	 */
	public List getLineName(String term,String marketId){
		String parentMarketId = "";
		Object[] obj = new Object[]{marketId};
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT S.PARENTMARKETID FROM BA_MARKET S WHERE S.MARKETID = ?");//查询本场景的父场景id
		List list2 = hibernateDao.executeSqlQuery(sql.toString(), obj);
		if(list2!=null&&list2.size()>0){
			Object pObj = (Object)list2.get(0);
			parentMarketId = pObj==null?"":pObj.toString();//本场景的父场景id
		}
		sql.setLength(0);
		sql.append("SELECT T.LINKNAME FROM BA_INTERTIE T WHERE T.MARKETID in (?,?,?) AND T.LINKNAME LIKE ? ");//联络线包括本场景的、上级场景的和总部的
		if(term.length()>100){
			term = term.substring(0, 100);
		}
		term = "%"+term+"%";
		List list1=  hibernateDao.executeSqlQuery(sql.toString(), new Object[]{marketId,parentMarketId,"91812",term});
		return list1;
	}
	
	public String getDataChart(String params, String marketId){
		Map map = new HashMap();
		String lineName = "";
		String startTime = "";
		String endTime = "";
		String type = "";
		try {
			map = JsonUtils.getObjectMapper().readValue(params, Map.class);
			lineName = map.get("lineName")==null?"":map.get("lineName").toString();//联络线名称
			startTime = map.get("startTime")==null?"":map.get("startTime").toString();
			endTime = map.get("endTime")==null?"":map.get("endTime").toString();
			type = map.get("type")==null?"":map.get("type").toString();
		}catch (Exception e) {
			e.printStackTrace();
		}
		FCchart chart = new FCchart();
		chart.setDecimals("2");							//功能属性类-------------设置四舍五入的小数点精度。范围0-10
		chart.setNumberSuffix("%");
		List paramsList = new ArrayList();//存放参数
		List list = new ArrayList();//存放结果
		StringBuffer sql = new StringBuffer();
		sql.append("	SELECT ROWNUM,TT.PAPERCONTRACTNAME,TT.WCL FROM (SELECT T.PAPERCONTRACTNAME,CASE WHEN (T.CONTRACTQTY=0 OR T.CONTRACTQTY IS NULL) THEN 0 ELSE ")
		.append("     ROUND(100*(CASE WHEN (S.ENERGY_T = 0 OR S.ENERGY_T IS NULL) THEN 0 ELSE SUM(S.ENERGY_T) END) / T.CONTRACTQTY,2) END AS WCL  ")
		.append("	  FROM CO_CONTRACTBASEINFO T                                                                          ")
		.append("	  LEFT JOIN SE_TIELINE_RESULT_N S                                                                     ")
		.append("	    ON T.CONTRACTID = S.CONTRACT_ID                                                                   ")
		.append("	  LEFT JOIN BA_INTERTIE TT                                                                            ")
		.append("	    ON TT.LINKID = S.TIELINE_ID                                                                       ");
		if("purchase".equals(type)){
			sql.append("		  LEFT JOIN BA_MARKETPARTICIPANT Q                                ")
			.append("		    ON Q.PARTICIPANTID = T.PURCHASER                              ");
		}else if("sell".equals(type)){
			sql.append("		  LEFT JOIN BA_MARKETPARTICIPANT Q                                ")
			.append("		    ON Q.PARTICIPANTID = T.SELLER                              ");
		}else if("trans".equals(type)){
			sql.append(" LEFT JOIN CO_TRANSQTYINFO B ON B.CONTRACTID = T.CONTRACTID  ")
			.append(" LEFT JOIN BA_MARKETPARTICIPANT Q ON Q.PARTICIPANTID = B.TRANSMISSION ");
		}
		sql.append("	 WHERE Q.MARKETID = ?                                                                                 ")
		.append("	   AND 1 = 1                                                                        ");
		paramsList.add(marketId);
		if(!"".equals(lineName)){//联络线名称
			sql.append("	   AND TT.LINKNAME = ?                                                                        ");
			paramsList.add(lineName);
		}else if(!startTime.equals("")){//开始时间
			sql.append("		   AND (T.CONTRACTSTARTDATE >= TO_DATE(?,'YYYY-MM-DD') OR T.CONTRACTSTARTDATE IS NULL)   ");
			paramsList.add(startTime);
		}else if(!endTime.equals("")){//结束时间
			sql.append("		   AND (T.CONTRACTENDDATE <= TO_DATE(?,'YYYY-MM-DD') OR T.CONTRACTENDDATE IS NULL)       ");
			paramsList.add(endTime);
		}
		sql.append("	   GROUP BY T.PAPERCONTRACTNAME,T.CONTRACTQTY,S.ENERGY_T                                                       ")
		.append("	   ORDER BY WCL DESC) TT WHERE ROWNUM < 11  ");
		list = hibernateDao.executeSqlQuery(sql.toString(), paramsList.toArray());
		FCcategories fccatagories = new FCcategories();
		fccatagories.addCategoryByDBList(list, 0);
		
		FCdataset fcdataset = new FCdataset();
		fcdataset.addSetByDBList(list, 2);//完成率
		 
		return chart.toXml(fccatagories.toXml() + fcdataset.toXml());
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
