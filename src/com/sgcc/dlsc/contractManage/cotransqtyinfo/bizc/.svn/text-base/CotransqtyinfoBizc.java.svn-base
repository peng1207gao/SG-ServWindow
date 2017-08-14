package com.sgcc.dlsc.contractManage.cotransqtyinfo.bizc;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.sgcc.dlsc.commons.bean.FCcategories;
import com.sgcc.dlsc.commons.bean.FCchart;
import com.sgcc.dlsc.commons.bean.FCdataset;
import com.sgcc.dlsc.commons.bean.VIParamBean;
import com.sgcc.dlsc.commons.bean.VIReturnBean;
import com.sgcc.dlsc.commons.bizc.IComm_VerticalIntegration_Bizc;
import com.sgcc.dlsc.commons.util.DataTypeTrans;
import com.sgcc.dlsc.commons.util.DateUtil;
import com.sgcc.dlsc.constants.DataConstants;
import com.sgcc.dlsc.contractManage.cotransqtyinfo.vo.CoTransqtyinfoTransfer;
import com.sgcc.dlsc.contractManage.cotransqtyinfo.vo.CoTransqtyinfoVO;
import com.sgcc.dlsc.contractManage.cotransqtyinfo.vo.CoTransqtyslaveinfoTransfer;
import com.sgcc.dlsc.contractManage.cotransqtyinfo.vo.CoTransqtyslaveinfoVO;
import com.sgcc.dlsc.entity.po.CoContracttypeinfo;
import com.sgcc.dlsc.entity.po.CoTransqtyinfo;
import com.sgcc.dlsc.entity.po.CoTransqtyslaveinfo;
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
import com.sgcc.uap.rest.utils.RestUtils;
/**
 * 用户定义逻辑构件
 * 
 * @author mengke 
 * 
 */
public class CotransqtyinfoBizc implements ICotransqtyinfoBizc{

	@Autowired
	private IHibernateDao hibernateDao;
	
	@Autowired
	private IDataDictionaryBizC dataDictionaryBizC;
	
	@Autowired
	private IComm_VerticalIntegration_Bizc commVerticalIntegrationBizc;
	/**
	 * 保存更新
	 * 
	 * @param list
	 */
	 public List<CoTransqtyinfoVO> saveOrUpdate(List<Map> list,String marketId,User user) {
		 
		List<CoTransqtyinfoVO> voList = new ArrayList<CoTransqtyinfoVO>();
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
			String poName = CoTransqtyinfo.class.getName();
			if(map.containsKey("transinfoid")){
				String id = (String)map.get("transinfoid");
				CoTransqtyinfoVO vo = update(map,poName,id,marketId,user);//list中包含transinfoid，进行更新操作
				voList.add(vo);
			}else{
				CoTransqtyinfoVO coTransqtyinfoVO = save(map,marketId,user);//list中不包含transinfoid，进行更新操作
				voList.add(coTransqtyinfoVO);
			}
		}
		return voList;
	 }
	 
	//保存记录
	private CoTransqtyinfoVO save(Map map,String marketId,User user) {
		
		CoTransqtyinfoVO coTransqtyinfoVo = new CoTransqtyinfoVO();
		try {
			map.put("isincludetax", "1");
			String starttime = map.get("starttime")==null?"":map.get("starttime").toString();
			Date startDate = DateUtil.getUtilDate(starttime, "yyyy-MM-dd");
			map.remove("starttime");
			map.put("starttime", startDate);
			String endtime = map.get("endtime")==null?"":map.get("endtime").toString();
			Date endDate = DateUtil.getUtilDate(endtime, "yyyy-MM-dd");
			map.remove("endtime");
			map.put("endtime", endDate);

			BeanUtils.populate(coTransqtyinfoVo, map);
		} catch (Exception e) {
		}
		CoTransqtyinfo coTransqtyinfo = CoTransqtyinfoTransfer.toPO(coTransqtyinfoVo);
		hibernateDao.saveOrUpdateObject(coTransqtyinfo);
		CoTransqtyinfo po = coTransqtyinfo;
		coTransqtyinfoVo = CoTransqtyinfoTransfer.toVO(coTransqtyinfo);
		if(map.containsKey("mxVirtualId")){
			coTransqtyinfoVo.setMxVirtualId(String.valueOf(map.get("mxVirtualId")));
		}
		//纵向传输
		VIParamBean vip = new VIParamBean();
		vip.setMarketid_source(marketId);
		vip.setMarketid_target(DataConstants.marketid_target);//目的场景
		vip.setTableName("co_transqtyinfo");//表名
		vip.setWhereCondtion("transinfoid='"+po.getTransinfoid()+"'");
		vip.setUserId(user.getId());
		vip.setUserName(user.getUserName());//拼音
		vip.setDate(DateUtil.getNowDate("yyyy-MM-dd"));
		try {
			VIReturnBean vir = commVerticalIntegrationBizc
					.executeVerticalIntegrationHSXByLogObjId(vip);
			System.out.println(vir.isSuc() + "  " + vir.getFailinfo());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return coTransqtyinfoVo;
	}
	
	//更新记录
	private CoTransqtyinfoVO update(Map<String, ?> map,String poName,String id,String marketId,User user) {
		
		CoTransqtyinfoVO coTransqtyinfoVo = new CoTransqtyinfoVO();
		Set set = map.keySet();
		ArrayList<String> keyList = new ArrayList<String>();
		//更新操作
		try {
			for(Object object : set){
				String key = (String)object;
				if(map.get(key)==null){
					keyList.add(key);
				}
			}
			for(String key : keyList){
				map.remove(key);
			}
			BeanUtils.populate(coTransqtyinfoVo, map);
		} catch (Exception e) {
		}
		Set set1 = map.keySet();
		if(set1 != null && set1.size() > 1){
			Object[] objArray = CrudUtils.generateHql(CoTransqtyinfo.class, map, "transinfoid");
			hibernateDao.update((String) objArray[0], (Object[]) objArray[1]);
		}
		CoTransqtyinfo po = hibernateDao.getObject(CoTransqtyinfo.class, id);
		for(String key : keyList){
			try{
				Field field = CoTransqtyinfo.class.getDeclaredField(key);
				field.setAccessible(true);
				field.set(po, null);
				hibernateDao.updateObject(po);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		//纵向传输
		VIParamBean vip = new VIParamBean();
		vip.setMarketid_source(marketId);
		vip.setMarketid_target(DataConstants.marketid_target);//目的场景
		vip.setTableName("co_transqtyinfo");//表名
		vip.setWhereCondtion("transinfoid='"+po.getTransinfoid()+"'");
		vip.setUserId(user.getId());
		vip.setUserName(user.getUserName());//拼音
		vip.setDate(DateUtil.getNowDate("yyyy-MM-dd"));
		try {
			VIReturnBean vir = commVerticalIntegrationBizc
					.executeVerticalIntegrationHSXByLogObjId(vip);
			System.out.println(vir.isSuc() + "  " + vir.getFailinfo());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return coTransqtyinfoVo;
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
			hibernateDao.update("delete from " + CoTransqtyinfo.class.getName() + " where transinfoid = ?" ,new Object[]{id});
		}
	}
	
	/**
	 * 根据输入条件查询记录
	 * 
	 * @param queryCondition
	 * @return
	 */
	public QueryResultObject query(RequestCondition queryCondition) {

		List<QueryFilter> filterList = queryCondition.getQueryFilter(CoTransqtyinfoVO.class);
		String contractid = "";//合同id
		if(filterList != null && filterList.size() > 0){
			for(int i=0;i<filterList.size();i++){
				QueryFilter filter = filterList.get(i);
				String colName = filter.getFieldName();
				if( colName.equals("contractid")){//获取合同id
					contractid = (filter.getValue()==null?"":filter.getValue().toString());
				}
			}
		}
		List<CoTransqtyinfoVO> result = new ArrayList<CoTransqtyinfoVO>();
		StringBuffer sql = new StringBuffer();
		sql.append("	SELECT T1.TRANSINFOID,                    ")
		.append("	       T5.PARTICIPANTNAME,                    ")
		.append("	       T1.PASSAGEWAYNAME,                     ")
		.append("	       T1.PASSAGEWAYNO,                       ")
		.append("	       T2.LINKNAME,                           ")
		.append("	       T1.LINKNO,                             ")
		.append("	       T3.GATENAME        AS STARTGATENAME,   ")
		.append("	       T4.GATENAME        AS ENDGATENAME,     ")
		.append("	       T1.TRANSQTY,                           ")
		.append("	       T1.LOSSRATE,                           ")
		.append("	       T1.STARTPRICE,                         ")
		.append("	       T1.ENDPRICE,                           ")
		.append("	       T1.TRANSPRICE,                         ")
		.append("	       T6.NAME,                               ")
		.append("	       T1.SEPRATE,                            ")
		.append("	       T1.POWER,                              ")
		.append("	       T1.PEAKREGURATE,                       ")
		.append("	       T1.TRANSCAP,                           ")
		.append("	       T1.CAPPRICE,                           ")
		.append("	       T1.STARTTIME,                          ")
		.append("	       T1.ENDTIME,                            ")
		.append("	       T1.EXPLANATION                         ")
		.append("	  FROM CO_TRANSQTYINFO T1                     ")
		.append("	  LEFT JOIN BA_INTERTIE T2                    ")
		.append("	    ON T1.LINKID = T2.LINKID                  ")
		.append("	  LEFT JOIN BA_GATE T3                        ")
		.append("	    ON T1.STARTGATEID = T3.GATEID             ")
		.append("	  LEFT JOIN BA_GATE T4                        ")
		.append("	    ON T1.ENDGATEID = T4.GATEID               ")
		.append("	  LEFT JOIN BA_MARKETPARTICIPANT T5           ")
		.append("	    ON T1.TRANSMISSION = T5.PARTICIPANTID     ")
		.append("	  LEFT JOIN BA_GENCODE T6                     ")
		.append("	    ON T1.ISINCLUDETAX = T6.VALUE             ")
		.append("	 WHERE T1.CONTRACTID = ?  AND T6.TYPE = '74'  ");
		
		int count = 0;//查询结果数
		Object[] objs = new Object[]{contractid};
		
		String sql2 = "SELECT COUNT(*) FROM ("+sql.toString()+") T ";
		List list = hibernateDao.executeSqlQuery(sql2, objs);
		count = list.size() > 0 ? Integer.parseInt(list.get(0).toString()) : 0;
		list = hibernateDao.executeSqlQuery(sql.toString(), objs,Integer.parseInt(queryCondition.getPageIndex()),Integer.parseInt(queryCondition.getPageSize()));
		if(list!=null && list.size()>0){//封装VO
			for(int i=0;i<list.size();i++){
				Object[] obj = (Object[])list.get(i);
				CoTransqtyinfoVO ct = new CoTransqtyinfoVO();
				ct.setTransinfoid(obj[0]==null?"":obj[0].toString());
				ct.setTransmission(obj[1]==null?"":obj[1].toString());
				ct.setPassagewayname(obj[2]==null?"":obj[2].toString());
				ct.setPassagewayno(obj[3]==null?null:new BigDecimal(obj[3].toString()));
				ct.setLinkid(obj[4]==null?"":obj[4].toString());
				ct.setLinkno(obj[5]==null?null:new BigDecimal(obj[5].toString()));
				ct.setStartgateid(obj[6]==null?"":obj[6].toString());
				ct.setEndgateid(obj[7]==null?"":obj[7].toString());
				ct.setTransqty(obj[8]==null?null:new BigDecimal(obj[8].toString()));
				ct.setLossrate(obj[9]==null?null:new BigDecimal(obj[9].toString()));
				ct.setStartprice(obj[10]==null?null:new BigDecimal(obj[10].toString()));
				ct.setEndprice(obj[11]==null?null:new BigDecimal(obj[11].toString()));
				ct.setTransprice(obj[12]==null?null:new BigDecimal(obj[12].toString()));
				ct.setSpare1(obj[13]==null?"":obj[13].toString());
				ct.setSeprate(obj[14]==null?null:new BigDecimal(obj[14].toString()));
				ct.setPower(obj[15]==null?null:new BigDecimal(obj[15].toString()));
				ct.setPeakregurate(obj[16]==null?null:new BigDecimal(obj[16].toString()));
				ct.setTranscap(obj[17]==null?null:new BigDecimal(obj[17].toString()));
				ct.setCapprice(obj[18]==null?null:new BigDecimal(obj[18].toString()));
				ct.setStarttime(obj[19]==null?null:DateUtil.getUtilDate(obj[19].toString(), "yyyy-MM-dd"));
				ct.setEndtime(obj[20]==null?null:DateUtil.getUtilDate(obj[20].toString(), "yyyy-MM-dd"));
				ct.setExplanation(obj[21]==null?"":obj[21].toString());
				result.add(ct);
			}
		}
		return RestUtils.wrappQueryResult(result, count);
	}
	
//	private QueryCriteria wrapQuery(RequestCondition queryCondition,
//			QueryCriteria qc) {
//		List<QueryFilter> wheres = queryCondition.getQueryFilter(CoTransqtyinfoVO.class);
//		if (wheres != null && wheres.size() > 0) {
//			CrudUtils.addQCWhere(qc, wheres, CoTransqtyinfo.class.getName());
//
//		}
//
//		String orders = queryCondition.getSorter();
//		if (orders != null) {
//			qc.addOrder(orders.replaceAll("&", ","));
//		}
//		return qc;
//	}
//	
//	private QueryCriteria wrapPage(RequestCondition queryCondition,
//			QueryCriteria qc) {
//		int pageIndex = 1, pageSize = 1;
//		if (queryCondition.getPageIndex() != null
//				&& queryCondition.getPageSize() != null) {
//			pageIndex = Integer.valueOf(queryCondition.getPageIndex());
//			pageSize = Integer.valueOf(queryCondition.getPageSize());
//			qc.addPage(pageIndex, pageSize);
//		}
//		return qc;
//	}
	private QueryCriteria wrapQuery(RequestCondition queryCondition,
			QueryCriteria qc) {
		List<QueryFilter> wheres = queryCondition.getQueryFilter(CoTransqtyslaveinfoVO.class);
		if (wheres != null && wheres.size() > 0) {
			CrudUtils.addQCWhere(qc, wheres, CoTransqtyslaveinfo.class.getName());

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
	public QueryResultObject queryById(String id,String marketId) {
		CoTransqtyinfo coTransqtyinfo = (CoTransqtyinfo) hibernateDao.getObject(CoTransqtyinfo.class,id);
		CoTransqtyinfoVO vo = null;
		if (coTransqtyinfo != null) {
			vo = CoTransqtyinfoTransfer.toVO(coTransqtyinfo);
		}
		return RestUtils.wrappQueryResult(vo).addDicItems(wrapDictList(marketId));
	}
	
	/**
	 * 
	 * @description 查询电力信息单条记录
	 * @param id
	 * @param marketId
	 * @return
	 * @author mengke 
	 * @date 2014-2-11
	 */
	public QueryResultObject queryElecById(String id,String marketId) {
		CoTransqtyslaveinfo coTransqtyslaveinfo = (CoTransqtyslaveinfo) hibernateDao.getObject(CoTransqtyslaveinfo.class,id);
		CoTransqtyslaveinfoVO vo = null;
		if (coTransqtyslaveinfo != null) {
			vo = CoTransqtyslaveinfoTransfer.toVO(coTransqtyslaveinfo);
		}
		return RestUtils.wrappQueryResult(vo).addDicItems(wrapDictList(marketId));
	}

	/**
	 * 从数据字典中读取联络线内容、是否数据字典、输电类型数据字典、开始时间数据字典、结束时间数据字典
	 * @description 方法描述
	 * @param marketId
	 * @return
	 * @author mengke
	 * @date 2014-2-10
	 */
	private List<DicItems> wrapDictList(String marketId) {
		List<DicItems> dicts = new ArrayList<DicItems>();

        dicts.add(linkLine("linkid",marketId));//联络线数据字典
//        dicts.add(translateFromFile("isincludetax", "EMP.ISINCLUDETAX"));//是否
        dicts.add(isincludetax("isincludetax", "74"));//是否
        dicts.add(qtyType("qtytype","33"));//输电类型数据字典
        dicts.add(getTime("starttime","97"));//开始时间数据字典
        dicts.add(getTime("endtime","97"));//结束时间数据字典
		return dicts;
	}
	
	private List<DicItems> wrapDictList1() {
		List<DicItems> dicts = new ArrayList<DicItems>();

//        dicts.add(translateFromFile("isincludetax", "EMP.ISINCLUDETAX"));//是否
        dicts.add(isincludetax("isincludetax", "74"));//是否
        dicts.add(qtyType("qtytype","33"));//输电类型数据字典
        dicts.add(getTime("starttime","97"));//开始时间数据字典
        dicts.add(getTime("endtime","97"));//结束时间数据字典
		return dicts;
	}
	
	/**
	 * 创建联络线数据字典
	 * @description 方法描述
	 * @param fieldName
	 * @param marketId
	 * @return
	 * @author mengke
	 * @date 2014-2-10
	 */
	private DicItems linkLine(String fieldName,String marketId) {
		String parentMarketId = "";
		List<Map<String, String>> list = new ArrayList();
		Object[] obj = new Object[]{marketId};
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT S.PARENTMARKETID FROM BA_MARKET S WHERE S.MARKETID = ?");//查询本场景的父场景id
		List list2 = hibernateDao.executeSqlQuery(sql.toString(), obj);
		if(list2!=null&&list2.size()>0){
			Object pObj = (Object)list2.get(0);
			parentMarketId = pObj==null?"":pObj.toString();//本场景的父场景id
		}
		sql.setLength(0);
		sql.append("SELECT T.LINKID,T.LINKNAME FROM BA_INTERTIE T WHERE T.MARKETID in (?,?,?)");//联络线包括本场景的、上级场景的和总部的
		List list1=  hibernateDao.executeSqlQuery(sql.toString(), new Object[]{marketId,parentMarketId,"91812"});
		for(int i=0;i<list1.size();i++){
			Map<String, String> map = new HashMap();
			Object[] robj = (Object[])list1.get(i);
			String value = robj[0]==null?"":robj[0].toString();//联络线id
			String name = robj[1]==null?"":robj[1].toString();//联络线名称
			map.put("value", value);//实际值
			map.put("text", name);//显示值
			list.add(map);
		}
		DicItems dict = new DicItems();//封装成数据字典
		dict.setName(fieldName);
		dict.setValues(list);
		return dict;
	}
	
	/**
	 * 从属性文件中读取数据字典，标识是否得数据字典
	 * @description 方法描述
	 * @param fieldName
	 * @param dicId
	 * @return
	 * @author mengke
	 * @date 2014-2-10
	 */
	private DicItems translateFromFile(String fieldName, String dicId) {
		List<Map<String, String>> list = dataDictionaryBizC.translateFromFile(
				dicId, "value", "text");
		DicItems dict = new DicItems();//封装成数据字典
		dict.setName(fieldName);
		dict.setValues(list);
		return dict;
	}
	
	/**
	 * 根据联络线id查询起点关口
	 * @description 方法描述
	 * @param linkid 联络线id
	 * @return
	 * @author mengke 
	 * @date 2014-2-10
	 */
	public List listStartGate(String linkid){
		List<Map<String,String>> listMap = new ArrayList<Map<String, String>>();
		Object[] obj = new Object[]{linkid};
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT T.GATENAME,S.STARTGATEID FROM BA_GATE T ")
		.append("LEFT JOIN BA_INTERTIE S ON T.GATEID = S.STARTGATEID WHERE S.LINKID = ?");
		List list = hibernateDao.executeSqlQuery(sql.toString(), obj);
		return list;
	}
	
	/**
	 * 根据联络线id查询终点关口
	 * @description 方法描述
	 * @param linkid
	 * @return
	 * @author mengke 
	 * @date 2014-2-10
	 */
	public List listEndGate(String linkid){
		Object[] obj = new Object[]{linkid};
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT T.GATENAME,S.ENDGATEID FROM BA_GATE T ")
		.append("LEFT JOIN BA_INTERTIE S ON T.GATEID = S.ENDGATEID WHERE S.LINKID = ?");
		List list = hibernateDao.executeSqlQuery(sql.toString(), obj);
		return list;
	}
	
	/**
	 * 
	 * @description 查询电力信息
	 * @param queryCondition
	 * @return
	 * @author mengke 
	 * @date 2014-3-24
	 */
//	public QueryResultObject queryElecInfo(RequestCondition queryCondition){
//		List<QueryFilter> filterList = queryCondition.getQueryFilter(CoTransqtyslaveinfoVO.class);//获取参数
//		String transInfoId = "";//输电信息id
//		if(filterList != null && filterList.size() > 0){//遍历参数
//			for(int i=0;i<filterList.size();i++){
//				QueryFilter filter = filterList.get(i);
//				String colName = filter.getFieldName();
//				if( colName.equals("transinfoid")){//获取输电信息id
//					transInfoId = (filter.getValue()==null?"":filter.getValue().toString());
//				}
//			}
//		}
//		List<CoTransqtyslaveinfoVO> result = new ArrayList<CoTransqtyslaveinfoVO>();
//		StringBuffer sql = new StringBuffer();
//		sql.append("SELECT CO.GUID, BA1.NAME AS N1, BA2.NAME AS N2, CO.POWER, BA.NAME AS N3 ")
//		.append("FROM CO_TRANSQTYSLAVEINFO CO LEFT JOIN BA_GENCODE BA ")
//		.append("ON CO.QTYTYPE = BA.VALUE AND BA.TYPE = '33' LEFT JOIN BA_GENCODE BA1 ")
//		.append("ON CO.STARTTIME = BA1.VALUE AND BA1.TYPE = '97' LEFT JOIN BA_GENCODE BA2 ")
//		.append("ON CO.ENDTIME = BA2.VALUE AND BA2.TYPE = '97' WHERE CO.TRANSINFOID = ?");
//		
//		int count = 0;//查询结果数
//		Object[] objs = new Object[]{transInfoId};
//		
//		String sql2 = "SELECT COUNT(*) FROM ("+sql.toString()+") T ";
//		List list = hibernateDao.executeSqlQuery(sql2, objs);
//		count = list.size() > 0 ? Integer.parseInt(list.get(0).toString()) : 0;
//		list = hibernateDao.executeSqlQuery(sql.toString(), objs,Integer.parseInt(queryCondition.getPageIndex()),Integer.parseInt(queryCondition.getPageSize()));
//		if(list!=null && list.size()>0){//封装VO
//			for(int i=0;i<list.size();i++){
//				Object[] obj = (Object[])list.get(i);
//				CoTransqtyslaveinfoVO ct = new CoTransqtyslaveinfoVO();
//				ct.setGuid(obj[0]==null?"":obj[0].toString());
//				ct.setStarttime(obj[1]==null?"":obj[1].toString());
//				ct.setEndtime(obj[2]==null?"":obj[2].toString());
//				ct.setPower(obj[3]==null?null:new BigDecimal(obj[3].toString()));
//				ct.setQtytype(obj[4]==null?"":obj[4].toString());
//				result.add(ct);
//			}
//		}
//		return RestUtils.wrappQueryResult(result, count).addDicItems(wrapDictList1());
//	}
	public QueryResultObject queryElecInfo(RequestCondition queryCondition){
		QueryCriteria qc = new QueryCriteria();
		List<CoTransqtyslaveinfo> result = null;
		int count = 0;
		qc.addFrom(CoTransqtyslaveinfo.class);
		if (queryCondition != null) {
			qc = wrapQuery(queryCondition, qc);
			count = getRecordCount(qc);
			qc = wrapPage(queryCondition, qc);
			result = hibernateDao.findAllByCriteria(qc);

		} else {
			result = hibernateDao.findAllByCriteria(qc);
			count = getRecordCount(qc);
		}
		return RestUtils.wrappQueryResult(result, count).addDicItems(wrapDictList1());
	}
	/**
	 * 初始化字典值
	 * 
	 * @return QueryResultObject
	 */
	public QueryResultObject initDict() {
		QueryResultObject queryElecInfo = new QueryResultObject();
		return queryElecInfo.addDicItems(wrapDictList1());
	}
	/**
	 * 
	 * @description 创建是否含税数据字典
	 * @param fieldName
	 * @param type
	 * @return
	 * @author mengke
	 * @date 2014-6-14
	 */
	private DicItems isincludetax(String fieldName,String type){
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Object[] obj = new Object[]{type};
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT T.VALUE,T.NAME FROM BA_GENCODE T WHERE T.TYPE = ?");
		List list1=  hibernateDao.executeSqlQuery(sql.toString(),obj);
		for(int i=0;i<list1.size();i++){
			Map<String, String> map = new HashMap<String, String>();
			Object[] robj = (Object[])list1.get(i);
			String value = robj[0]==null?"":robj[0].toString();//类型id
			String name = robj[1]==null?"":robj[1].toString();//类型名称
			map.put("value", value);//实际值
			map.put("text", name);//显示值
			list.add(map);
		}
		DicItems dict = new DicItems();//封装数据字典
		dict.setName(fieldName);
		dict.setValues(list);
		return dict;
	}
	
	/**
	 * 创建峰谷类型数据字典
	 * @description 方法描述
	 * @param fieldName
	 * @param type
	 * @return
	 * @author mengke
	 * @date 2014-2-11
	 */
	private DicItems qtyType(String fieldName,String type){
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Object[] obj = new Object[]{type};
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT T.VALUE,T.NAME FROM BA_GENCODE T WHERE T.TYPE = ?");
		List list1=  hibernateDao.executeSqlQuery(sql.toString(),obj);
		for(int i=0;i<list1.size();i++){
			Map<String, String> map = new HashMap<String, String>();
			Object[] robj = (Object[])list1.get(i);
			String value = robj[0]==null?"":robj[0].toString();//类型id
			String name = robj[1]==null?"":robj[1].toString();//类型名称
			map.put("value", value);//实际值
			map.put("text", name);//显示值
			list.add(map);
		}
		DicItems dict = new DicItems();//封装数据字典
		dict.setName(fieldName);
		dict.setValues(list);
		return dict;
	}
	
	/**
	 * 创建开始时间和结束时间数据字典
	 * @description 方法描述
	 * @param fieldName
	 * @param type
	 * @return
	 * @author mengke
	 * @date 2014-2-11
	 */
	private DicItems getTime(String fieldName,String type){
		List<Map<String, String>> list = new ArrayList();
		Object[] obj = new Object[]{type};
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT T.VALUE,T.NAME FROM BA_GENCODE T WHERE T.TYPE = ? ORDER BY T.NAME");
		List list1=  hibernateDao.executeSqlQuery(sql.toString(),obj);
		for(int i=0;i<list1.size();i++){
			Map<String, String> map = new HashMap();
			Object[] robj = (Object[])list1.get(i);
			String value = robj[0]==null?"":robj[0].toString();//开始时间和结束时间在统一编码表中的value
			String name = robj[1]==null?"":robj[1].toString();//开始时间和结束时间在统一编码表中的name
			map.put("value", value);//实际值
			map.put("text", name);//显示值
			list.add(map);
		}
		DicItems dict = new DicItems();//封装数据字典
		dict.setName(fieldName);
		dict.setValues(list);
		return dict;
	}
	
	/**
	 * 
	 * @description 根据合同id查询合同开始时间和结束时间
	 * @param contractid
	 * @return
	 * @author mengke
	 * @date 2014-3-17
	 */
	public List getContractDate(String contractid){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT TO_CHAR(S.CONTRACTSTARTDATE, 'YYYY-MM-DD'),")
		.append("TO_CHAR(S.CONTRACTENDDATE, 'YYYY-MM-DD') ")
		.append("FROM CO_CONTRACTBASEINFO S WHERE S.CONTRACTID = ?");
		Object[] obj = new Object[]{contractid};
		List list = hibernateDao.executeSqlQuery(sql.toString(), obj);
		return list;
	}
	
	/**
	 * 保存更新
	 * 
	 * @param list
	 */
	 public List<CoTransqtyslaveinfoVO> saveOrUpdateElec(List<Map> list,String marketId,User user) {
		 
		List<CoTransqtyslaveinfoVO> voList = new ArrayList<CoTransqtyslaveinfoVO>();
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
			String poName = CoTransqtyslaveinfo.class.getName();
			if(map.containsKey("guid")){
				String id = (String)map.get("guid");
				CoTransqtyslaveinfoVO vo = updateElec(map,poName,id,marketId,user);
				voList.add(vo);
			}else{
				CoTransqtyslaveinfoVO coTransqtyslaveinfoVO = saveElec(map,marketId,user);
				voList.add(coTransqtyslaveinfoVO);
			}
		}
		return voList;
	 }
	 
	//保存记录
	private CoTransqtyslaveinfoVO saveElec(Map map,String marketId,User user) {
		
		CoTransqtyslaveinfoVO coTransqtyslaveinfoVo = new CoTransqtyslaveinfoVO();
		
		try {
			String starttime = map.get("startdate")==null?"":map.get("startdate").toString();
			Date startDate = DateUtil.getUtilDate(starttime, "yyyy-MM-dd");
			map.remove("startdate");
			map.put("startdate", startDate);
			String endtime = map.get("enddate")==null?"":map.get("enddate").toString();
			Date endDate = DateUtil.getUtilDate(endtime, "yyyy-MM-dd");
			map.remove("enddate");
			map.put("enddate", endDate);

			BeanUtils.populate(coTransqtyslaveinfoVo, map);
		} catch (Exception e) {
		}
		CoTransqtyslaveinfo coTransqtyslaveinfo =CoTransqtyslaveinfoTransfer.toPO(coTransqtyslaveinfoVo);
//		CoTransqtyslaveinfo coTransqtyslaveinfo = new CoTransqtyslaveinfo();//
//		coTransqtyslaveinfo = (CoTransqtyslaveinfo) DataTypeTrans.getObjectForMapSet2Po(map, coTransqtyslaveinfo, CoTransqtyslaveinfo.class);
		hibernateDao.saveOrUpdateObject(coTransqtyslaveinfo);
		CoTransqtyslaveinfo po = coTransqtyslaveinfo;
		coTransqtyslaveinfoVo = CoTransqtyslaveinfoTransfer.toVO(coTransqtyslaveinfo);
		if(map.containsKey("mxVirtualId")){
			coTransqtyslaveinfoVo.setMxVirtualId(String.valueOf(map.get("mxVirtualId")));
		}
//		//纵向传输
//		VIParamBean vip = new VIParamBean();
//		vip.setMarketid_source(marketId);
//		vip.setMarketid_target(DataConstants.marketid_target);//目的场景
//		vip.setTableName("co_transqtyslaveinfo");//表名
//		vip.setWhereCondtion("guid='"+po.getGuid()+"'");
//		vip.setUserId(user.getId());
//		vip.setUserName(user.getUserName());//拼音
//		vip.setDate(DateUtil.getNowDate("yyyy-MM-dd"));
//		try {
//			VIReturnBean vir = commVerticalIntegrationBizc
//					.executeVerticalIntegrationHSXByLogObjId(vip);
//			System.out.println(vir.isSuc() + "  " + vir.getFailinfo());
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
		return coTransqtyslaveinfoVo;
	}
	
	//更新记录
	private CoTransqtyslaveinfoVO updateElec(Map<String, ?> map,String poName,String id,String marketId,User user) {
		
		CoTransqtyslaveinfoVO coTransqtyslaveinfoVo = new CoTransqtyslaveinfoVO();
		//更新操作
//		try {
//			BeanUtils.populate(coTransqtyslaveinfoVo, map);
//		} catch (Exception e) {
//		}
//		Object[] objArray = CrudUtils.generateHql(CoTransqtyslaveinfo.class, map, "guid");
//		hibernateDao.update((String) objArray[0], (Object[]) objArray[1]);
		CoTransqtyslaveinfo po = hibernateDao.getObject(CoTransqtyslaveinfo.class, id);
		po = (CoTransqtyslaveinfo) DataTypeTrans.getObjectForMapSet2Po(map, po, CoTransqtyslaveinfo.class);
		hibernateDao.updateObject(po);
//		//纵向传输arg0
//		VIParamBean vip = new VIParamBean();
//		vip.setMarketid_source(marketId);
//		vip.setMarketid_target(DataConstants.marketid_target);//目的场景
//		vip.setTableName("co_transqtyslaveinfo");//表名
//		vip.setWhereCondtion("guid='"+po.getGuid()+"'");
//		vip.setUserId(user.getId());
//		vip.setUserName(user.getUserName());//拼音
//		vip.setDate(DateUtil.getNowDate("yyyy-MM-dd"));
//		try {
//			VIReturnBean vir = commVerticalIntegrationBizc
//					.executeVerticalIntegrationHSXByLogObjId(vip);
//			System.out.println(vir.isSuc() + "  " + vir.getFailinfo());
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
		return coTransqtyslaveinfoVo;
	}
	
	/**
	 * 删除
	 * 
	 * @param idObject
	 */
	public void removeElec(IDRequestObject idObject) {
		String[] ids = idObject.getIds();
		for (int i = 0; i < ids.length; i++) {
			String id = ids[i];
			hibernateDao.update("delete from " + CoTransqtyslaveinfo.class.getName() + " where guid = ?" ,new Object[]{id});
		}
	}
	
	/**
	 * 
	 * @description 根据输电信息id查询已经选中的输电方
	 * @param transid
	 * @return
	 * @author mengke
	 * @date 2014-3-20
	 */
	public List getSeleSdf(String transid){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT T.TRANSMISSION,S.PARTICIPANTNAME FROM CO_TRANSQTYINFO T LEFT JOIN BA_MARKETPARTICIPANT S ")
		.append("ON S.PARTICIPANTID = T.TRANSMISSION WHERE T.TRANSINFOID = ?");
		Object[] obj = new Object[]{transid};
		List list = hibernateDao.executeSqlQuery(sql.toString(), obj);
		return list;
	}
	
	/**
	 * 
	 * @description 查询电力曲线
	 * @param queryCondition
	 * @return
	 * @author mengke 
	 * @date 2014-6-16
	 */
	public QueryResultObject queryDlqx(RequestCondition queryCondition){
		List<QueryFilter> filterList = queryCondition.getQueryFilter(CoTransqtyslaveinfoVO.class);//获取参数
		String transInfoId = "";//输电信息id
		if(filterList != null && filterList.size() > 0){//遍历参数
			for(int i=0;i<filterList.size();i++){
				QueryFilter filter = filterList.get(i);
				String colName = filter.getFieldName();
				if( colName.equals("transinfoid")){//获取输电信息id
					transInfoId = (filter.getValue()==null?"":filter.getValue().toString());
				}
			}
		}
		List<CoTransqtyslaveinfoVO> result = new ArrayList<CoTransqtyslaveinfoVO>();
		StringBuffer sql = new StringBuffer();
//		sql.append("SELECT T.GUID,T.BIGHT,T.STRATDATE,T.ENDDATE FROM CO_TRANSQTYSLAVEINFO T WHERE T.TRANSINFOID = ?");
		sql.append("SELECT DISTINCT T.BIGHT,T.STRATDATE,T.ENDDATE FROM CO_TRANSQTYSLAVEINFO T WHERE T.TRANSINFOID = ?");
		int count = 0;//查询结果数
		Object[] objs = new Object[]{transInfoId};
		
		String sql2 = "SELECT COUNT(*) FROM ("+sql.toString()+") T ";
		List list = hibernateDao.executeSqlQuery(sql2, objs);
		count = list.size() > 0 ? Integer.parseInt(list.get(0).toString()) : 0;
		list = hibernateDao.executeSqlQuery(sql.toString(), objs,Integer.parseInt(queryCondition.getPageIndex()),Integer.parseInt(queryCondition.getPageSize()));
		if(list!=null && list.size()>0){//封装VO
			for(int i=0;i<list.size();i++){
				Object[] obj = (Object[])list.get(i);
				CoTransqtyslaveinfoVO ct = new CoTransqtyslaveinfoVO();
//				ct.setGuid(obj[0]==null?"":obj[0].toString());
				ct.setBight(obj[0]==null?"":obj[0].toString());
				ct.setStartdate(obj[1]==null?null:DateUtil.getUtilDate(obj[1].toString(),"yyyy-MM-dd"));
				ct.setEnddate(obj[2]==null?null:DateUtil.getUtilDate(obj[2].toString(),"yyyy-MM-dd"));
				result.add(ct);
			}
		}
		return RestUtils.wrappQueryResult(result, count);
	}
	
	public void removeDlqx(String transInfoId,List list) {
		for (int i = 0; i < list.size(); i++) {
			String bight = (String) list.get(i);
			hibernateDao.update("delete from " + CoTransqtyslaveinfo.class.getName() + " where transinfoid = ? and bight = ?" ,new Object[]{transInfoId,bight});
		}
	}
	/**
	 *注入数据字典构件
	 * 
	 * @param dataDictionaryBizC
	 */
	public void setDataDictionaryBizC(IDataDictionaryBizC dataDictionaryBizC) {
		this.dataDictionaryBizC = dataDictionaryBizC;
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

	public List getDlqxName(String transInfoId) {
		String sql = "select max(bight) from (SELECT DISTINCT T.BIGHT FROM CO_TRANSQTYSLAVEINFO T  where t.transinfoid=?)";
		List list = new ArrayList();
		List listDlqx = hibernateDao.executeSqlQuery(sql, new Object[]{transInfoId});
		if(listDlqx.get(0)==null){
			list.add("曲线-1");
		}else{
			String name = listDlqx.get(0).toString();
			String[] strs = name.split("-");
			int aa=Integer.parseInt(strs[1])+1;
			name = strs[0]+"-"+aa;
			list.add(name);
		}
		return list;
	}

	public String getPie3DXml(Map map) {
		String transInfoId = map.get("transInfoId")==null?"":map.get("transInfoId").toString();
		List list = getDlqxList(transInfoId);
		List listX = getListTime();
		FCchart chart = new FCchart();
		chart.setCaption("典型电力曲线图");
		chart.setShowValues("0");
		chart.setLabelDisplay("STAGGER");
		FCcategories categories = new FCcategories();
		categories.addCategoryByDBList(listX, 0);
		String str="";
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				FCdataset dataset = new FCdataset();
				dataset.setSeriesName(list.get(i).toString());
				String[] strs = getListDates(transInfoId,list.get(i).toString());
				for(int j=0;j<strs.length;j++){
					dataset.addSet(strs[j]);
				}
//				dataset.addSetByDBList(getListDates(transInfoId,list.get(i).toString()), 0);
				str=str+dataset.toXml();
			}
		}
		
		
		return chart.toXml(categories.toXml()+str);
	}

	private String[] getListDates(String transInfoId, String bight) {
		List list = this.getListData(transInfoId, bight);
		String[] strs = new String[97];
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				Object[] objs = (Object[]) list.get(i);
				int st = Integer.parseInt(objs[0].toString());
				int ed = Integer.parseInt(objs[1].toString());
				for(int j=st;j<=ed;j++){
					strs[j-1]=objs[2]==null?"0":objs[2].toString();
				}
				
			}
		}
		List retlist = new ArrayList();
		for(int i=0;i<97;i++){
			if(strs[i]==null||strs[i].equals("")){
				strs[i]="0";
			}
			retlist.add(strs[i]);
		}
		return strs;
	}

	private List getDlqxList(String transInfoId) {
		String sql ="SELECT DISTINCT T.BIGHT FROM CO_TRANSQTYSLAVEINFO T  where t.transinfoid=?";
		List list = hibernateDao.executeSqlQuery(sql, new Object[]{transInfoId});
		return list;
	}
	private List getListTime() {
		String sql ="select g.value,g.name from ba_gencode g where g.type='97' order by g.name";
		List list = hibernateDao.executeSqlQuery(sql);
		return list;
	}
	private List getListData(String transInfoId,String bight) {
		String sql ="SELECT  T.STARTTIME,T.ENDTIME,T.POWER FROM CO_TRANSQTYSLAVEINFO T WHERE t.transinfoid=?  AND T.BIGHT=? ORDER BY T.STARTTIME";
		List list = hibernateDao.executeSqlQuery(sql, new Object[]{transInfoId,bight});
		return list;
	}
}
