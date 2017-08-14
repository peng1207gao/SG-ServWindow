package com.sgcc.dlsc.contractManage.cocontractaccessory.bizc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sgcc.dlsc.commons.bean.VIParamBean;
import com.sgcc.dlsc.commons.bean.VIReturnBean;
import com.sgcc.dlsc.commons.bizc.IComm_VerticalIntegration_Bizc;
import com.sgcc.dlsc.commons.util.DateUtil;
import com.sgcc.dlsc.commons.util.UserInfoUtil;
import com.sgcc.dlsc.constants.DataConstants;
import com.sgcc.dlsc.contractManage.cocontractaccessory.vo.CoBaGeneratorTransfer;
import com.sgcc.dlsc.contractManage.cocontractaccessory.vo.CoBaGeneratorVO;
import com.sgcc.dlsc.contractManage.cocontractaccessory.vo.CoContractaccessoryTransfer;
import com.sgcc.dlsc.contractManage.cocontractaccessory.vo.CoContractaccessoryVO;
import com.sgcc.dlsc.entity.po.BaGenerator;
import com.sgcc.dlsc.entity.po.CoContractaccessory;
import com.sgcc.isc.core.orm.identity.User;
import com.sgcc.uap.bizc.sysbizc.datadictionary.IDataDictionaryBizC;
import com.sgcc.uap.persistence.IHibernateDao;
import com.sgcc.uap.persistence.criterion.QueryCriteria;
import com.sgcc.uap.rest.support.DicItems;
import com.sgcc.uap.rest.support.IDRequestObject;
import com.sgcc.uap.rest.support.QueryFilter;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;
import com.sgcc.uap.rest.utils.CrudUtils;
import com.sgcc.uap.rest.utils.JsonUtils;
import com.sgcc.uap.rest.utils.RestUtils;

/**
 * 合同机组信息 {@link CoContractaccessory}的逻辑构件
 * 
 * @author djdeng
 * 
 */
public class CocontractaccessoryBizc implements ICocontractaccessoryBizc {

	@Autowired
	private IHibernateDao hibernateDao;

	@Autowired
	private IDataDictionaryBizC dataDictionaryBizC;
	
	@Autowired
	private IComm_VerticalIntegration_Bizc commVerticalIntegrationBizc;
	
	public IComm_VerticalIntegration_Bizc getCommVerticalIntegrationBizc() {
		return commVerticalIntegrationBizc;
	}

	public void setCommVerticalIntegrationBizc(
			IComm_VerticalIntegration_Bizc commVerticalIntegrationBizc) {
		this.commVerticalIntegrationBizc = commVerticalIntegrationBizc;
	}

	private Log logger = LogFactory.getLog(getClass());

	/**
	 * 判断传入contractid的合同是否是大用户合同，属于大用户合同或者其子合同类型，界面列表下拉框中不显示购电方
	 * 其他情况显示购电方和售电方 
	 */
	public List getDropDownList(String params){
		List<Map<String, String>> dropDownlist = new ArrayList<Map<String, String>>();
		
		Map map = null;
		try {
			map = JsonUtils.getObjectMapper().readValue(params, Map.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(map != null){
			String contractid = map.get("contractid")==null?"":map.get("contractid").toString();
			
			if(isBigUser(contractid)){//传入合同的合同类型在    大用户合同及其子合同类型 中
				Map<String, String> sellerDropDownMap = new HashMap<String, String>();
				sellerDropDownMap.put("name", "售电方");//封装下拉列表数据
				sellerDropDownMap.put("value", "2");
				dropDownlist.add(sellerDropDownMap);
			}else if(isFdqContract(contractid)){//合同类型时发电权合同时，购电方改为被替代方，售电方改为替代方
				Map<String, String> purchaserDropDownMap = new HashMap<String, String>();
				purchaserDropDownMap.put("name", "被替代方");//封装下拉列表数据
				purchaserDropDownMap.put("value", "1");
				
				Map<String, String> sellerDropDownMap = new HashMap<String, String>();
				sellerDropDownMap.put("name", "替代方");//封装下拉列表数据
				sellerDropDownMap.put("value", "2");
				dropDownlist.add(purchaserDropDownMap);
				dropDownlist.add(sellerDropDownMap);
			}else {
				Map<String, String> purchaserDropDownMap = new HashMap<String, String>();
				purchaserDropDownMap.put("name", "购电方");//封装下拉列表数据
				purchaserDropDownMap.put("value", "1");
				
				Map<String, String> sellerDropDownMap = new HashMap<String, String>();
				sellerDropDownMap.put("name", "售电方");//封装下拉列表数据
				sellerDropDownMap.put("value", "2");
				dropDownlist.add(purchaserDropDownMap);
				dropDownlist.add(sellerDropDownMap);
			}
		}
		return dropDownlist;
		
	}
	
	
	/**
	 * 保存更新
	 * 
	 * @param list
	 */
	public List<CoContractaccessoryVO> saveOrUpdate(
			List<CoContractaccessoryVO> vos,HttpServletRequest request) {
		List<CoContractaccessoryVO> voList = new ArrayList<CoContractaccessoryVO>();
		for (CoContractaccessoryVO vo : vos) {
			CoContractaccessoryVO coContractaccessoryVO = save(vo,request);
			voList.add(coContractaccessoryVO);
		}
		return voList;
	}

	// 保存记录
	private CoContractaccessoryVO save(
			CoContractaccessoryVO coContractaccessoryVo,HttpServletRequest request) {
		CoContractaccessory coContractaccessory = CoContractaccessoryTransfer
				.toPO(coContractaccessoryVo);
		hibernateDao.saveOrUpdateObject(coContractaccessory);
		coContractaccessoryVo = CoContractaccessoryTransfer
				.toVO(coContractaccessory);
		//纵向传输
		String marketId = "";
		try {
			marketId = UserInfoUtil.getLoginUserMarket(request);//当前登录场景
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		VIParamBean vip = new VIParamBean();
		vip.setMarketid_source(marketId);
		vip.setMarketid_target(DataConstants.marketid_target);//目的场景
		vip.setTableName("CO_CONTRACTACCESSORY");//表名
		vip.setWhereCondtion("GUID='"+coContractaccessory.getGuid()+"'");
		User user;
		try {
			user = UserInfoUtil.getLoginUser(request);
			vip.setUserId(user.getId());
			vip.setUserName(user.getUserName());//拼音
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		vip.setDate(DateUtil.getNowDate("yyyy-MM-dd"));
		VIReturnBean vir = commVerticalIntegrationBizc.executeVerticalIntegrationHSXByLogObjId(vip);
		return coContractaccessoryVo;
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
			hibernateDao.update(
					"delete from " + CoContractaccessory.class.getName()
							+ " where guid = ?", new Object[] { id });
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
		List<CoContractaccessory> result = null;
		int count = 0;
		qc.addFrom(CoContractaccessory.class);
		if (queryCondition != null) {
			qc = wrapQuery(queryCondition, qc);
			count = getRecordCount(qc);
			qc = wrapPage(queryCondition, qc);
			result = hibernateDao.findAllByCriteria(qc);
		} else {
			result = hibernateDao.findAllByCriteria(qc);
			count = getRecordCount(qc);
		}
		
		/*
		 * 合同类型属于 大用户合同，界面不显示购电方信息
		 */
		// 获取contractid
		List<QueryFilter> wheres = queryCondition.getQueryFilter(CoContractaccessoryVO.class);
		String contractid = null;
		if(wheres != null && wheres.size() != 0){
			QueryFilter filter = wheres.get(0);
			contractid = filter.getValue().toString();
		}
		if(contractid != null){
			
			if(result != null && isBigUser(contractid)){
				Iterator<CoContractaccessory> it = result.iterator();
				while(it.hasNext()){
					CoContractaccessory contractassessory = it.next();
					if(new BigDecimal(1).compareTo(contractassessory.getContractrole()) == 0){
						it.remove();
						count--;
					}
				}
			}
		}
		
		QueryResultObject queryResult =  RestUtils.wrappQueryResult(
				result, count).addDicItems(wrapDictList(contractid));
		
		addGeneratorDicItems(queryResult, queryCondition);
		
		return queryResult;

	}
	
	/**
	 * 判断合同类型是否是大用户合同或者其子类型合同
	 * @param contractid
	 * @return
	 */
	private boolean isBigUser(String contractid){
//		查询大用户合同类型id
//		String contracttypeSQL = "SELECT CC.CONTRACTTYPE FROM CO_CONTRACTBASEINFO CC " +
//				"LEFT JOIN CO_CONTRACTTYPEINFO CCT ON CC.CONTRACTTYPE = CCT.CONTRACTTYPEID " +
//				"WHERE cc.contractid = ?";
//		List typeList = hibernateDao.executeSqlQuery(contracttypeSQL,new Object[]{contractid});
//		String sql = "select contracttypeid from co_contracttypeinfo type " +
//				"where type.contracttypeid ='402881da3c3847ba013c3854017a0001' " +
//				"or type.supertypeid = '402881da3c3847ba013c3854017a0001'";
//		List list = hibernateDao.executeSqlQuery(sql);
		
		StringBuffer isBigUserSQL = new StringBuffer();
		isBigUserSQL.append("SELECT CC.CONTRACTTYPE FROM CO_CONTRACTBASEINFO CC ");
		isBigUserSQL.append("LEFT JOIN CO_CONTRACTTYPEINFO CCT ON CC.CONTRACTTYPE = CCT.CONTRACTTYPEID ");
		isBigUserSQL.append("WHERE cc.contractid = ? ");
		isBigUserSQL.append("and cc.CONTRACTTYPE in ( select contracttypeid from co_contracttypeinfo type ");
		isBigUserSQL.append("where type.contracttypeid ='402881da3c3847ba013c3854017a0001' ");
		isBigUserSQL.append("or type.supertypeid = '402881da3c3847ba013c3854017a0001') ");
		
		List list = hibernateDao.executeSqlQuery(isBigUserSQL.toString(), new Object[]{contractid});
		if(list == null || list.size() == 0){
			return false;
		} else {
			return true;
		}
		
//		return list.contains(typeList.get(0));
	} 
	
	/**
	 * 
	 * @description 判断是不是发电权合同
	 * @param contractid
	 * @return
	 * @author mengke
	 * @date 2014-7-4
	 */
	private boolean isFdqContract(String contractid){
		StringBuffer sql = new StringBuffer();
		sql.append("select t.contracttype from co_contractbaseinfo t where t.contractid=?");
		List list1 = hibernateDao.executeSqlQuery(sql.toString(), new Object[]{contractid});
		sql.setLength(0);
		sql.append("select s.contracttypeid from co_contracttypeinfo s where s.contracttypeid = '4DC24C12-413C-5A67-BEBE-AB07C0C' ")
		.append(" or s.supertypeid = '4DC24C12-413C-5A67-BEBE-AB07C0C' ");
		List list2= hibernateDao.executeSqlQuery(sql.toString());
		return list2.contains(list1.get(0));
	}
	
	/**
	 * 根据合同Id查询，机组的Id和名称
	 * 
	 * @param queryResult 查询结果
	 * 
	 * @param queryCondition  请求对象
	 */
	private void addGeneratorDicItems(QueryResultObject queryResult, 
			RequestCondition queryCondition) {
		String contractid = getRequestFilterValueByName(queryCondition, "contractid");
		
		if (StringUtils.isBlank(contractid) && logger.isErrorEnabled()) {
			logger.error("合同Id为空，无法查询机组信息");
		}
		
		//
		DicItems items = translateFromDB("participantid", BaGenerator.class.getName(),
				"generatorid", "generatorname",
				"generatorid in (select participantid from " 
				+ CoContractaccessory.class.getName() 
				+ " where contractid='"+contractid+"')");
		
		queryResult.addDicItems(items);
	}
	
	/**
	 * 从请求对象中获得指定参数的值
	 * 
	 * @param queryCondition 请求对象
	 * 
	 * @param filterName 参数名称
	 * 
	 * @return 参数值
	 */
	private String getRequestFilterValueByName(
			RequestCondition queryCondition, String filterName) {
		
		List<QueryFilter> queryFilters = queryCondition.getQueryFilter();
		
		for (QueryFilter qf : queryFilters) {
			if (qf.getFieldName().equals(filterName)) {
				return qf.getValue().toString();
			}
		}
		return null;
	}

	/**
	 * 封装查询过滤条件
	 * 
	 * @param queryCondition
	 * @param qc
	 * @return
	 */
	private QueryCriteria wrapQuery(RequestCondition queryCondition,
			QueryCriteria qc) {
		List<QueryFilter> wheres = queryCondition
				.getQueryFilter(CoContractaccessoryVO.class);
		if (wheres != null && wheres.size() > 0) {
			CrudUtils.addQCWhere(qc, wheres,
					CoContractaccessory.class.getName());
		}

		String orders = queryCondition.getSorter();
		if (orders != null) {
			qc.addOrder(orders.replaceAll("&", ","));
		}
		return qc;
	}

	/**
	 * 封装分页查询信息
	 * 
	 * @param queryCondition
	 * @param qc
	 * @return
	 */
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
	 * 
	 * @param id
	 * @return QueryResultObject
	 */
	public QueryResultObject queryById(String id) {
		CoContractaccessory coContractaccessory = (CoContractaccessory) hibernateDao
				.getObject(CoContractaccessory.class, id);

		CoContractaccessoryVO vo = null;
		if (coContractaccessory != null) {
			vo = CoContractaccessoryTransfer.toVO(coContractaccessory);
		}
		return RestUtils.wrappQueryResult(vo).addDicItems(wrapDictList(""));
	}

	/**
	 * 初始化字典值
	 * 
	 * @return QueryResultObject
	 */
	public QueryResultObject initDict() {
		QueryResultObject query = new QueryResultObject();
		return query.addDicItems(wrapDictList(""));
	}

	// 将字典对象封装为list
	private List<DicItems> wrapDictList(String contractid) {
		List<DicItems> dicts = new ArrayList<DicItems>();
		if(!"".equals(contractid)&&contractid!=null){
			if(isFdqContract(contractid)){
				dicts.add(translateFromFile("contractrole", "CO.CONTRACTROLE1"));
			}else {
				dicts.add(translateFromFile("contractrole", "CO.CONTRACTROLE"));
			}
		}else{
			dicts.add(translateFromFile("contractrole", "CO.CONTRACTROLE"));
		}
		return dicts;
	}

	// 从属性文件中查询字典
	private DicItems translateFromFile(String fieldName, String dicId) {
		List<Map<String, String>> list = dataDictionaryBizC.translateFromFile(
				dicId, "value", "text");
		DicItems dict = new DicItems();
		dict.setName(fieldName);
		dict.setValues(list);
		return dict;
	}

	// 从数据库中查询字典
	private DicItems translateFromDB(String fieldName, String poName,
			String keyField, String valueField, String filter) {
		List<Map<String, String>> list = dataDictionaryBizC.translateFromDB(
				poName, "value", "text", keyField, valueField, filter);
		DicItems dict = new DicItems();
		dict.setName(fieldName);
		dict.setValues(list);
		return dict;
	}

	/**
	 * 
	 */
	public List<CoBaGeneratorVO> getGenerator(RequestCondition params) {
		//
		Map<String, String> wheres = getGeneratorQueryFilter(params);
		String contractrole = wheres.get("contractrole");
		String contractid = wheres.get("contractid");

		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT G.GENERATORNAME, G.GENERATORID");
		sql.append(" FROM CO_CONTRACTBASEINFO T,BA_GENERATOR G");
		sql.append(" WHERE 1=1");
		
		//1 是购电方， 2 是售电方
		if("1".equals(contractrole)){
			sql.append(" AND G.PLANTID = T.PURCHASER");
		}
		if("2".equals(contractrole)){
			sql.append(" AND G.PLANTID = T.SELLER");
		}
		sql.append(" AND T.CONTRACTID='").append(contractid).append("'");
		
		//排除已经关联的机组
		sql.append(" AND G.GENERATORID NOT IN (");
		sql.append("   SELECT CC.PARTICIPANTID FROM CO_CONTRACTACCESSORY CC");
		sql.append("   WHERE CC.CONTRACTID = '").append(contractid).append("'");
		sql.append("   AND CC.CONTRACTROLE='").append(contractrole).append("'");
		sql.append(" )");
		
		List<Object[]> objs = hibernateDao.executeSqlQuery(sql.toString());
		return CoBaGeneratorTransfer.toVO(objs,contractrole, contractid);
	}

	/**
	 * 
	 * @param params
	 * @return
	 */
	private Map<String, String> getGeneratorQueryFilter(RequestCondition params) {
		Map<String, String> filterMap = new HashMap<String, String>();
		List<QueryFilter> filters = params
				.getQueryFilter(CoBaGeneratorVO.class);
		if (filters != null && !filters.isEmpty()) {
			for (QueryFilter filter : filters) {
				filterMap.put(filter.getFieldName(), (String) filter.getValue());
			}
		}
		return filterMap;
	}

	/**
	 * 注入数据字典构件
	 * 
	 * @param dataDictionaryBizC
	 */
	public void setDataDictionaryBizC(IDataDictionaryBizC dataDictionaryBizC) {
		this.dataDictionaryBizC = dataDictionaryBizC;
	}

	// 获取总记录数
	private int getRecordCount(QueryCriteria qc) {
		int count = 0;
		count = hibernateDao.findAllByCriteriaPageCount(qc, 1);
		return count;
	}

	public void setHibernateDao(IHibernateDao hibernateDao) {
		this.hibernateDao = hibernateDao;
	}
}
