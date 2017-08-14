package com.sgcc.dlsc.contractManage.coContractgateinfo.bizc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.sgcc.dlsc.commons.util.DateUtil;
import com.sgcc.dlsc.commons.util.UserInfoUtil;
import com.sgcc.dlsc.contractManage.coContractgateinfo.vo.CoContractgateinfoTransfer;
import com.sgcc.dlsc.contractManage.coContractgateinfo.vo.CoContractgateinfoVO;
import com.sgcc.dlsc.entity.po.CoContractgateinfo;
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
 * @author thinpad 
 * 
 */
public class CoContractgateinfoBizc implements ICoContractgateinfoBizc{

	@Autowired
	private IHibernateDao hibernateDao;
	
	@Autowired
	private IDataDictionaryBizC dataDictionaryBizC;
	/**
	 * 保存更新
	 * 
	 * @param list
	 */
	 public List<CoContractgateinfoVO> saveOrUpdate(List<Map> list,HttpServletRequest request) {
		 
		List<CoContractgateinfoVO> voList = new ArrayList<CoContractgateinfoVO>();
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
			String poName = CoContractgateinfo.class.getName();
			if(map.containsKey("guid")){
				String id = (String)map.get("guid");
				CoContractgateinfoVO vo = update(map,poName,id);
				voList.add(vo);
			}else{
				CoContractgateinfoVO coContractgateinfoVO = save(map,request);
				voList.add(coContractgateinfoVO);
			}
		}
		return voList;
	 }
	 
	//保存记录
	private CoContractgateinfoVO save(Map map,HttpServletRequest request) {
		String marketId = "";
		CoContractgateinfoVO coContractgateinfoVo = new CoContractgateinfoVO();
		try {
			BeanUtils.populate(coContractgateinfoVo, map);
		} catch (Exception e) {
		}
		CoContractgateinfo coContractgateinfo = CoContractgateinfoTransfer.toPO(coContractgateinfoVo);
		try {
			marketId = UserInfoUtil.getLoginUserMarket(request);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		coContractgateinfo.setMarketid(marketId);
		hibernateDao.saveOrUpdateObject(coContractgateinfo);
		coContractgateinfoVo = CoContractgateinfoTransfer.toVO(coContractgateinfo);
		if(map.containsKey("mxVirtualId")){
			coContractgateinfoVo.setMxVirtualId(String.valueOf(map.get("mxVirtualId")));
		}
		return coContractgateinfoVo;
	}
	
	//更新记录
	private CoContractgateinfoVO update(Map<String, ?> map,String poName,String id) {
		
		CoContractgateinfoVO coContractgateinfoVo = new CoContractgateinfoVO();
		//更新操作
		try {
			BeanUtils.populate(coContractgateinfoVo, map);
		} catch (Exception e) {
		}
		Object[] objArray = CrudUtils.generateHql(CoContractgateinfo.class, map, "guid");
		hibernateDao.update((String) objArray[0], (Object[]) objArray[1]);
		return coContractgateinfoVo;
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
			hibernateDao.update("delete from " + CoContractgateinfo.class.getName() + " where guid = ?" ,new Object[]{id});
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
		List<CoContractgateinfo> result = null;
		int count = 0;
		qc.addFrom(CoContractgateinfo.class);
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
		List<QueryFilter> wheres = queryCondition.getQueryFilter(CoContractgateinfoVO.class);
		if (wheres != null && wheres.size() > 0) {
			CrudUtils.addQCWhere(qc, wheres, CoContractgateinfo.class.getName());

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
		CoContractgateinfo coContractgateinfo = (CoContractgateinfo) hibernateDao.getObject(CoContractgateinfo.class,id);
		CoContractgateinfoVO vo = null;
		if (coContractgateinfo != null) {
			vo = CoContractgateinfoTransfer.toVO(coContractgateinfo);
		}
		return RestUtils.wrappQueryResult(vo).addDicItems(wrapDictList(marketId));
	}
	// 将字典对象封装为list
	private List<DicItems> wrapDictList(String marketId) {
		List<DicItems> dicts = new ArrayList<DicItems>();

		dicts.add(translateFromFile("participantid", marketId));
		dicts.add(translateFromFile1("gateid",marketId));
		dicts.add(translateFromFile2("busiunitid", marketId));
		dicts.add(translateFromFile3("displaytype"));
		return dicts;
	}
	// 从属性文件中查询字典
	private DicItems translateFromFile(String fieldName, String marketId) {
		List<Map<String, String>> list = new ArrayList();
		Object[] obj = new Object[1];
		StringBuffer sql = new StringBuffer();
		sql.append(" select participantid, participantname from Ba_Marketparticipant");// where marketid=? 
		List tlist  = hibernateDao.executeSqlQuery(sql.toString(), new Object[]{});//marketId
		for(int i=0;i<tlist.size();i++){
			Map<String, String> map = new HashMap();
			Object[] robj = (Object[])tlist.get(i);
			String value = robj[0]==null?"":robj[0].toString();
			String name = robj[1]==null?"":robj[1].toString();
			map.put("value", value);
			map.put("text", name);
			list.add(map);
		}
		DicItems dict = new DicItems();
		dict.setName(fieldName);
		dict.setValues(list);
		return dict;
	}
	// 从属性文件中查询字典
	private DicItems translateFromFile1(String fieldName,String marketId) {
		List<Map<String, String>> list = new ArrayList();
		Object[] obj = new Object[1];
		StringBuffer sql = new StringBuffer();
//		sql.append(" select gateid, gatename from Ba_Gate where marketid=?");
		sql.append(" select s.meter_id,s.meter_name from SE_METER_INFO s where s.market_id=?");
		List tlist  = hibernateDao.executeSqlQuery(sql.toString(),new Object[]{marketId});
		for(int i=0;i<tlist.size();i++){
			Map<String, String> map = new HashMap();
			Object[] robj = (Object[])tlist.get(i);
			String value = robj[0]==null?"":robj[0].toString();
			String name = robj[1]==null?"":robj[1].toString();
			map.put("value", value);
			map.put("text", name);
			list.add(map);
		}
		DicItems dict = new DicItems();
		dict.setName(fieldName);
		dict.setValues(list);
		return dict;
	}
	// 从属性文件中查询字典
	private DicItems translateFromFile2(String fieldName, String marketId) {
		List<Map<String, String>> list = new ArrayList();
		Object[] obj = new Object[1];
		StringBuffer sql = new StringBuffer();
		sql.append(" (select busiunitid, busiunitname from Ba_Busiunit where marketid=?) union  (select distinct t.tieline_id,t.tieline_name from se_meter_info t where t.market_id=? )");
		List tlist  = hibernateDao.executeSqlQuery(sql.toString(), new Object[]{marketId,marketId});
		for(int i=0;i<tlist.size();i++){
			Map<String, String> map = new HashMap();
			Object[] robj = (Object[])tlist.get(i);
			String value = robj[0]==null?"":robj[0].toString();
			String name = robj[1]==null?"":robj[1].toString();
			map.put("value", value);
			map.put("text", name);
			list.add(map);
		}
		DicItems dict = new DicItems();
		dict.setName(fieldName);
		dict.setValues(list);
		return dict;
	}
	// 从属性文件中查询字典
	private DicItems translateFromFile3(String fieldName) {
		List<Map<String, String>> list = new ArrayList();
		Object[] obj = new Object[1];
		StringBuffer sql = new StringBuffer();
		sql.append(" select value,name from ba_gencode where type='78' ");
		List tlist  = hibernateDao.executeSqlQuery(sql.toString());
		for(int i=0;i<tlist.size();i++){
			Map<String, String> map = new HashMap();
			Object[] robj = (Object[])tlist.get(i);
			String value = robj[0]==null?"":robj[0].toString();
			String name = robj[1]==null?"":robj[1].toString();
			map.put("value", value);
			map.put("text", name);
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

	public List getBusiunitSel(String participantid) {
		
		String sql = "select busiunitid, busiunitname from Ba_Busiunit where participantid=? ";
		return hibernateDao.executeSqlQuery(sql,new Object[]{participantid});
	}

	public QueryResultObject findCoContractList(int pageIndex, int pageSize,
			String contractID, String marketId) {
		QueryResultObject result = new QueryResultObject();//新建返回值对象
		int size = 0;	//定义数据条数
		String sql = "select a.contractid,       a.marketid,       a.gateid,              a.displaytype,       a.participantid,       a.busiunitid,       a.updatetime,       a.updatepersonid,       a.guid,       " +
				"b.busiunitname,       c.gatename,       d.participantname  from Co_Contractgateinfo a   LEFT JOIN Ba_Busiunit B  ON b.busiunitid = a.busiunitid   LEFT JOIN  Ba_Gate   C  ON a.gateid = c.gateid   LEFT JOIN  Ba_Marketparticipant d ON a.participantid = d.participantid "+
				"  WHERE A.MARKETID='"+marketId+"' and a.contractid = '"+contractID+"'";	//得到要查询的sql语句
		String sql2 = "SELECT COUNT(*) G FROM ( "+ sql + " ) T ";		//拼接查询数据条数的sql语句
		List list = hibernateDao.executeSqlQuery(sql2.toString());
		if(list.size() > 0){	//如果有查询结果，给数据条数变量赋值
			size = Integer.parseInt(list.get(0).toString());
		}
		result.setItemCount(size);	//给返回对象赋数据的数量
		list = hibernateDao.executeSqlQuery(sql.toString(),pageIndex,pageSize);	//查询分页的数据
		List<CoContractgateinfoVO> voList = new ArrayList<CoContractgateinfoVO>();		//返回的数据结果集
		for(int i = 0;i < list.size();i++){	//遍历要展示的数据
 			Object[] objs = (Object[])list.get(i);	//得到第i条数据
 			CoContractgateinfoVO vo = new CoContractgateinfoVO();//数据结果集中的一条数据
 			//给每个变量赋值
 			vo.setContractid(objs[0] == null ? "" : objs[0].toString());
 			vo.setMarketid(objs[1] == null ? "" : objs[1].toString());
 			vo.setGateid(objs[2] == null ? "" : objs[2].toString());
 			vo.setGatename(objs[10] == null ? "" : objs[10].toString());
 			vo.setDisplaytype(objs[3] == null ? null : new BigDecimal(objs[3].toString()));
			vo.setParticipantid(objs[4] == null ? "" : objs[4].toString());
 			vo.setBusiunitid(objs[5] == null ? "" : objs[5].toString());
 			vo.setUpdatetime(objs[6] == null ? null : DateUtil.getUtilDate(objs[6].toString(), "yyyy-MM-dd"));
 			vo.setUpdatepersonid(objs[7] == null ? "" : objs[7].toString());
 			vo.setGuid(objs[8] == null ? "" : objs[8].toString());
 			
 			voList.add(vo);	//把数据添加到结果集中
		}
		result.setItems(voList);	//给返回对象赋分页查询数据
		

		return RestUtils.wrappQueryResult(voList).addDicItems(wrapDictList(marketId));	//返回返回值
	}

	/**
	 * 
	 * @description 方法描述
	 * @param contractid
	 * @return
	 * @author xuzihu 
	 * @date 2014-7-15
	 */
	public List getMarketparent(String contractid) {
		String sql = "select co.purchaser,co.seller from co_contractbaseinfo co where co.contractid=?";
		List list = hibernateDao.executeSqlQuery(sql, new Object[]{contractid});
		return list;
	}
	/**
	 * 
	 * @description 方法描述
	 * @param participantid
	 * @return
	 * @author xuzihu 
	 * @date 2014-7-16
	 */
	public List getGateItems(String participantid) {
		String sql = "select s.meter_id,s.meter_name from SE_METER_INFO s where s.device_id=?";
		return hibernateDao.executeSqlQuery(sql,new Object[]{participantid});
	}

	/**
	 * 
	 * @description 方法描述
	 * @param marketid
	 * @return
	 * @author xuzihu 
	 * @date 2014-7-16
	 */
	public List getTieline(String marketid) {
		String sql = "select distinct t.tieline_id,t.tieline_name from se_meter_info t where t.market_id=?";
		return hibernateDao.executeSqlQuery(sql,new Object[]{marketid});
	}

	/**
	 * 
	 * @description 方法描述
	 * @param participantid
	 * @return
	 * @author xuzihu 
	 * @date 2014-7-16
	 */
	public List getTielinebyparticipantid(String participantid) {
		String sql = "select distinct t.tieline_id,t.tieline_name from se_meter_info t where t.device_id=?";
		return hibernateDao.executeSqlQuery(sql,new Object[]{participantid});
	}

	/**
	 * 
	 * @description 方法描述
	 * @param tielineid
	 * @return
	 * @author xuzihu 
	 * @date 2014-7-16
	 */
	public List getGateidbytieline(String tielineid) {
		String sql = "select s.meter_id,s.meter_name from SE_METER_INFO s where s.tieline_id=?";
		return hibernateDao.executeSqlQuery(sql,new Object[]{tielineid});
	}
}
