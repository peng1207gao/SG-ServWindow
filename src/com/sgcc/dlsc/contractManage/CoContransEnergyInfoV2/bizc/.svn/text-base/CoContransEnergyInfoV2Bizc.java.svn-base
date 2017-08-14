package com.sgcc.dlsc.contractManage.CoContransEnergyInfoV2.bizc;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.sgcc.dlsc.entity.po.CoContransenergyinfo;
import com.sgcc.dlsc.commons.util.DateUtil;
import com.sgcc.dlsc.contractManage.CoContransEnergyInfoV2.vo.CoContransenergyinfoTransfer;
import com.sgcc.dlsc.contractManage.CoContransEnergyInfoV2.vo.CoContransenergyinfoVO;
/**
 * 用户定义逻辑构件
 * 
 * @author xiabaike 
 * 
 */
public class CoContransEnergyInfoV2Bizc implements ICoContransEnergyInfoV2Bizc{

	@Autowired
	private IHibernateDao hibernateDao;
	
	@Autowired
	private IDataDictionaryBizC dataDictionaryBizC;
	/**
	 * 保存更新
	 * 
	 * @param list
	 */
	 public List<CoContransenergyinfoVO> saveOrUpdate(List<Map> list) {
		  
		List<CoContransenergyinfoVO> voList = new ArrayList<CoContransenergyinfoVO>();
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
			String poName = CoContransenergyinfo.class.getName();
			if(map.containsKey("guid")){
				String id = (String)map.get("guid");
				CoContransenergyinfoVO vo = update(map,poName,id);
				voList.add(vo);
			}else{
				CoContransenergyinfoVO coContransenergyinfoVO = save(map);
				voList.add(coContransenergyinfoVO);
			}
		}
		return voList;
	 }
	 
	//保存记录
	private CoContransenergyinfoVO save(Map map) {
		
		CoContransenergyinfoVO coContransenergyinfoVo = new CoContransenergyinfoVO();
		try {
 			if(map.get("startdate") != null && !"".equals(map.get("startdate"))){
 				Date startDate = null;
 				startDate = DateUtil.getUtilDate(map.get("startdate").toString(), "yyyy-MM-dd");
				map.put("startdate", startDate);
			}
 			if(map.get("enddate") != null && !"".equals(map.get("enddate"))){
 				Date endDate = null;
 				endDate = DateUtil.getUtilDate(map.get("enddate").toString(), "yyyy-MM-dd");
				map.put("enddate", endDate);
			}
			BeanUtils.populate(coContransenergyinfoVo, map);
		} catch (Exception e) {
		}
		CoContransenergyinfo coContransenergyinfo = CoContransenergyinfoTransfer.toPO(coContransenergyinfoVo);
//		coContransenergyinfo.setSettletype(new BigDecimal(map.get("name1").toString()));
//		coContransenergyinfo.setIsincludetax(new BigDecimal(map.get("name").toString()));
		hibernateDao.saveOrUpdateObject(coContransenergyinfo);
		coContransenergyinfoVo = CoContransenergyinfoTransfer.toVO(coContransenergyinfo);
		if(map.containsKey("mxVirtualId")){
			coContransenergyinfoVo.setMxVirtualId(String.valueOf(map.get("mxVirtualId")));
		}
		return coContransenergyinfoVo;
	}
	
	//更新记录
	private CoContransenergyinfoVO update(Map map,String poName,String id) {
		
		CoContransenergyinfoVO coContransenergyinfoVo = new CoContransenergyinfoVO();
		//更新操作
		if(map.get("startdate") != null && !"".equals(map.get("startdate"))){
				Date startDate = null;
				startDate = DateUtil.getUtilDate(map.get("startdate").toString(), "yyyy-MM-dd");
			map.put("startdate", startDate);
		}
		if(map.get("enddate") != null && !"".equals(map.get("enddate"))){
			Date endDate = null;
			endDate = DateUtil.getUtilDate(map.get("enddate").toString(), "yyyy-MM-dd");
		map.put("enddate", endDate);
		}
		Object[] objArray = CrudUtils.generateHql(CoContransenergyinfo.class, map, "guid");
		hibernateDao.update((String) objArray[0], (Object[]) objArray[1]);
		try {
			BeanUtils.populate(coContransenergyinfoVo, map);
		} catch (Exception e) {
		}
		return coContransenergyinfoVo;
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
			hibernateDao.update("delete from " + CoContransenergyinfo.class.getName() + " where guid = ?" ,new Object[]{id});
		}
	}
	
	/**
	 * 根据输入条件查询记录
	 * 
	 * @param queryCondition
	 * @return
	 * @throws Exception 
	 */
	public QueryResultObject query(String contractId, int pageIndex, int pageSize) throws Exception {

		QueryResultObject result = new QueryResultObject();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT CCTE.GUID,CCTE.TRANSMISSION,BM.PARTICIPANTNAME,BG1.NAME AS NAME1,CCTE.COST,CCTE.PRICE,CCTE.TRANSPHONE,BG2.NAME,CCTE.TAXRATE,CCTE.ENERGYPERCENT,CCTE.LOSS,TO_CHAR(CCTE.STARTDATE,'YYYY-MM-DD'),TO_CHAR(CCTE.ENDDATE,'YYYY-MM-DD') ");
		sql.append("  FROM CO_ConTransEnergyInfo CCTE LEFT JOIN BA_MARKETPARTICIPANT BM ON CCTE.TRANSMISSION = BM.PARTICIPANTID ");
		sql.append(" LEFT JOIN BA_GENCODE BG1 ON CCTE.SETTLETYPE = BG1.VALUE AND BG1.TYPE='73' ");
		sql.append(" LEFT JOIN BA_GENCODE BG2 ON CCTE.ISINCLUDETAX = BG2.VALUE AND BG2.TYPE='74' ");
		sql.append(" WHERE 1=1 ");
		if(contractId!=null && !contractId.equals("")){
			sql.append(" AND CCTE.CONTRACTID = '").append(contractId).append("' ");
		}
		String sql2 = "SELECT COUNT(*) C FROM ( "+ sql.toString() + " ) T ";		//拼接查询数据条数的sql语句
		List list = hibernateDao.executeSqlQuery(sql2.toString());
		int size = 0;	//定义数据条数
		if(list.size() > 0){	//如果有查询结果，给数据条数变量赋值
			size = Integer.parseInt(list.get(0).toString());
		}
		result.setItemCount(size);	//给返回对象赋数据的数量
		list = hibernateDao.executeSqlQuery(sql.toString(),pageIndex,pageSize);	//查询分页的数据
		List<CoContransenergyinfoVO> voList = new ArrayList<CoContransenergyinfoVO>();		//返回的数据结果集
		for(int i = 0;i < list.size();i++){	//遍历要展示的数据
 			Object[] objs = (Object[])list.get(i);	//得到第i条数据
 			CoContransenergyinfoVO vo = new CoContransenergyinfoVO();	//数据结果集中的一条数据
 			//给每个变量赋值
 			//GUID,TRANSMISSION,PARTICIPANTNAME,NAME1 ,COST,PRICE,TRANSPHONE,NAME,TAXRATE,ENERGYPERCENT,
 			//LOSS,STARTDATE,ENDDATE
 			vo.setGuid(objs[0] == null ? "" : objs[0].toString());
 			vo.setTransmission(objs[1] == null ? "" : objs[1].toString());
 			vo.setParticipantname(objs[2] == null ? "" : objs[2].toString());
 			vo.setName1(objs[3] == null ? "" : objs[3].toString());
 			vo.setCost(objs[4] == null ? null : new BigDecimal(objs[4].toString()));
 			vo.setPrice(objs[5] == null ? null : new BigDecimal(objs[5].toString()));
 			vo.setTransphone(objs[6] == null ? "" : objs[6].toString());
 			vo.setName(objs[7] == null ? "" : objs[7].toString());
 			vo.setTaxrate(objs[8] == null ? null : new BigDecimal(objs[8].toString()));
 			vo.setEnergypercent(objs[9] == null ? null : new BigDecimal(objs[9].toString()));
 			vo.setLoss(objs[10] == null ? null : new BigDecimal(objs[10].toString()));
 			//Date date = new SimpleDateFormat("yyyy-MM-dd").parse(objs[11].toString());
 			vo.setStartdate(objs[11] == null ? null : DateUtil.getUtilDate(objs[11].toString(), "yyyy-MM-dd"));//DateUtil.getUtilDate(objs[11].toString(), "yyyy-MM-dd"));
 			//Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(objs[12].toString());
 			vo.setEnddate(objs[12] == null ? null : DateUtil.getUtilDate(objs[12].toString(), "yyyy-MM-dd"));//DateUtil.getUtilDate(objs[12].toString(), "yyyy-MM-dd"));
 			voList.add(vo);	//把数据添加到结果集中
 		}
		result.setItems(voList);	//给返回对象赋分页查询数据
		return result;
	}
	
	private QueryCriteria wrapQuery(RequestCondition queryCondition,
			QueryCriteria qc) {
		List<QueryFilter> wheres = queryCondition.getQueryFilter(CoContransenergyinfoVO.class);
		if (wheres != null && wheres.size() > 0) {
			CrudUtils.addQCWhere(qc, wheres, CoContransenergyinfo.class.getName());

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
	public QueryResultObject queryById(String id, String marketId) {
		CoContransenergyinfo coContransenergyinfo = (CoContransenergyinfo) hibernateDao.getObject(CoContransenergyinfo.class,id);
		CoContransenergyinfoVO vo = null;
		if (coContransenergyinfo != null) {
			vo = CoContransenergyinfoTransfer.toVO(coContransenergyinfo);
		}
		return RestUtils.wrappQueryResult(vo).addDicItems(wrapDictListNew(marketId));
	}

	private List<DicItems> wrapDictListNew(String marketId) {// 将字典对象封装为list
		List<DicItems> dicts = new ArrayList<DicItems>();
	    dicts.add(translateFromFile("transmission",marketId));
	    
	    dicts.add(translateFromFile("participantname",marketId));
	    dicts.add(sdfsqfs("settletype",marketId));
	    dicts.add(jjsx("isincludetax",marketId));
	    return dicts;
	}
	
	// 从属性文件中查询字典-------输电方
	private DicItems translateFromFile(String fieldName,String marketId) {
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		Object[] obj = {};
		StringBuffer sql = new StringBuffer();
		obj = new Object[]{"1",marketId};
		sql.append("SELECT T.PARTICIPANTID,T.PARTICIPANTNAME FROM BA_MARKETPARTICIPANT T WHERE T.PARTICIPANTTYPE=? AND T.MARKETID=?");
				
		List tlist = hibernateDao.executeSqlQuery(sql.toString(),obj);
		for(int i=0;i<tlist.size();i++){
			Map<String, String> map = new HashMap<String,String>();
			Object[] robj = (Object[])tlist.get(i);
			String value = robj[0]==null?"":robj[0].toString();
			String name = robj[1]==null?"":robj[1].toString();
			map.put("value", value);
			map.put("text", name);
//			map.put(value, name);
			list.add(map);
		}
		DicItems dict = new DicItems();
		dict.setName(fieldName);
		dict.setValues(list);
		return dict;
	}
	
	// 从属性文件中查询字典--------输电费用收取方式
	private DicItems sdfsqfs(String fieldName,String marketId) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		//Object[] obj = {};
		StringBuffer sql = new StringBuffer();
		//obj = new Object[]{"1",marketId};
		sql.append("select ba.value, ba.name from ba_gencode ba where ba.type = '73'");
					
		List tlist = hibernateDao.executeSqlQuery(sql.toString());
		for(int i=0;i<tlist.size();i++){
			Map<String, String> map = new HashMap<String, String>();
			Object[] robj = (Object[])tlist.get(i);
			String value = robj[0]==null?"":robj[0].toString();
			String name = robj[1]==null?"":robj[1].toString();
			map.put("value", value);
			map.put("text", name);
//			map.put(value, name);
			list.add(map);
		}
		DicItems dict = new DicItems();
		dict.setName(fieldName);
		dict.setValues(list);
		return dict;
	}
	
	// 从属性文件中查询字典--------经济属性
	private DicItems jjsx(String fieldName,String marketId) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		//Object[] obj = {};
		StringBuffer sql = new StringBuffer();
		//obj = new Object[]{"1",marketId};
		sql.append("select ba.value, ba.name from ba_gencode ba where ba.type = '74'");
						
		List tlist = hibernateDao.executeSqlQuery(sql.toString());
		for(int i=0;i<tlist.size();i++){
			Map<String, String> map = new HashMap<String, String>();
			Object[] robj = (Object[])tlist.get(i);
			String value = robj[0]==null?"":robj[0].toString();
			String name = robj[1]==null?"":robj[1].toString();
			map.put("value", value);
			map.put("text", name);
//			map.put(value, name);
			list.add(map);
		}
		DicItems dict = new DicItems();
		dict.setName(fieldName);
		dict.setValues(list);
		return dict;
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
