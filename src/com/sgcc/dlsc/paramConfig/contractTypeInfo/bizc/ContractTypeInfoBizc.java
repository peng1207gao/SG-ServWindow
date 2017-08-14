package com.sgcc.dlsc.paramConfig.contractTypeInfo.bizc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.sgcc.dlsc.commons.bean.VIParamBean;
import com.sgcc.dlsc.commons.bean.VIReturnBean;
import com.sgcc.dlsc.commons.bizc.IComm_VerticalIntegration_Bizc;
import com.sgcc.dlsc.commons.util.DataTypeTrans;
import com.sgcc.dlsc.commons.util.DateUtil;
import com.sgcc.dlsc.commons.util.Guid;
import com.sgcc.dlsc.commons.util.ToolsSys;
import com.sgcc.dlsc.commons.util.UserInfoUtil;
import com.sgcc.dlsc.constants.DataConstants;
import com.sgcc.dlsc.entity.po.CoContractmembership;
import com.sgcc.dlsc.entity.po.CoContracttypeinfo;
import com.sgcc.dlsc.paramConfig.cocontractmembership.vo.CoContractmembershipTransfer;
import com.sgcc.dlsc.paramConfig.cocontractmembership.vo.CoContractmembershipVO;
import com.sgcc.dlsc.paramConfig.contractTypeInfo.vo.CoContracttypeinfoTransfer;
import com.sgcc.dlsc.paramConfig.contractTypeInfo.vo.CoContracttypeinfoVO;
import com.sgcc.isc.core.orm.identity.User;
import com.sgcc.uap.bizc.sysbizc.datadictionary.IDataDictionaryBizC;
import com.sgcc.uap.integrate.isc.wrapper.factory.AdapterWrapperFactory;
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
public class ContractTypeInfoBizc implements IContractTypeInfoBizc{

	@Autowired
	private IHibernateDao hibernateDao;
	
	@Autowired
	private IDataDictionaryBizC dataDictionaryBizC;
	
	@Autowired
	private IComm_VerticalIntegration_Bizc commVerticalIntegrationBizc;
	
	private String marketId = "";//场景id
	private String startTime = "";//开始时间
	private String endTime = "";//结束时间
	/**
	 * 保存更新
	 * 
	 * @param list
	 */
	 public List<CoContracttypeinfoVO> saveOrUpdate(List<Map> list,String marketId,String updatepersonId,User user) {
		 
		List<CoContracttypeinfoVO> voList = new ArrayList<CoContracttypeinfoVO>();
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
			String poName = CoContracttypeinfo.class.getName();
			if(map.containsKey("contracttypeid")){
				String id = (String)map.get("contracttypeid");
				map.remove("updatepersonid");
				map.put("updatepersonid", updatepersonId);
				CoContracttypeinfoVO vo = update(map,poName,id,user);
				voList.add(vo);
			}else{
				CoContracttypeinfoVO coContracttypeinfoVO = save(map,marketId,updatepersonId,user);
				voList.add(coContracttypeinfoVO);
			}
		}
		return voList;
	 }
	 
	//保存记录
	private CoContracttypeinfoVO save(Map map,String marketId,String updatepersonId,User user) {
		
		CoContracttypeinfoVO coContracttypeinfoVo = new CoContracttypeinfoVO();
		try {
			map.put("marketid", marketId);
			map.put("updatepersonid", updatepersonId);
			map.put("effectiveflag", new BigDecimal("0"));
			map.put("updatetime",DateUtil.getNowDate("yyyy-MM-dd"));
			BeanUtils.populate(coContracttypeinfoVo, map);
		} catch (Exception e) {
		}
		CoContracttypeinfo coContracttypeinfo = CoContracttypeinfoTransfer.toPO(coContracttypeinfoVo);
		hibernateDao.saveOrUpdateObject(coContracttypeinfo);
		CoContracttypeinfo po = coContracttypeinfo;
		coContracttypeinfoVo = CoContracttypeinfoTransfer.toVO(coContracttypeinfo);
		if(map.containsKey("mxVirtualId")){
			coContracttypeinfoVo.setMxVirtualId(String.valueOf(map.get("mxVirtualId")));
		}
		//设计决定将合同准入成员按钮去掉，改为在合同类型新增保存后同时将合同准入成员保存
		String contractTypeId = po.getContracttypeid();//获取合同类型保存后的合同类型id
		String participantId = null;
		//查询出该登录场景下的所有的市场成员(保存为购电方1和售电方2)
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT T.PARTICIPANTID FROM BA_MARKETPARTICIPANT T WHERE T.MARKETID = ?");
		List list1 = hibernateDao.executeSqlQuery(sql.toString(), new Object[]{marketId});
		//查询出该登录场景下的所有的电网企业(保存为输电方3)
		sql.setLength(0);
		sql.append("SELECT T.PARTICIPANTID FROM BA_MARKETPARTICIPANT T WHERE T.MARKETID = ? AND T.PARTICIPANTTYPE = '0'");
		List list2 = hibernateDao.executeSqlQuery(sql.toString(), new Object[]{marketId});
		CoContractmembershipVO coContractmembershipVO = new CoContractmembershipVO();
		CoContractmembership coContractmembership = new CoContractmembership();
		Map htzrMap = new HashMap();
		htzrMap.put("contracttypeid", contractTypeId);
		htzrMap.put("marketid", marketId);
		htzrMap.put("participantid", "");
		htzrMap.put("displaytype", "");
		htzrMap.put("guid", Guid.create());
		htzrMap.put("updatetime", DateUtil.getNowDate("yyyy-MM-dd"));
		htzrMap.put("updatepersonid", updatepersonId);
		//保存购电方
		htzrMap.remove("displaytype");
		htzrMap.put("displaytype", 1);
		if(list1!=null && list1.size()>0){
			for(int i=0;i<list1.size();i++){
				participantId = list1.get(i).toString();
				htzrMap.remove("guid");
				htzrMap.put("guid", Guid.create());
				htzrMap.remove("participantid");
				htzrMap.put("participantid", participantId);
				try {
					BeanUtils.populate(coContractmembershipVO, htzrMap);
				} catch (Exception e) {
					// TODO: handle exception
				}
				coContractmembership = CoContractmembershipTransfer.toPO(coContractmembershipVO);
				hibernateDao.saveObject(coContractmembership);
			}
		}
		
		//保存售电方
		htzrMap.remove("displaytype");
		htzrMap.put("displaytype", 2);
		if(list1!=null && list1.size()>0){
			for(int i=0;i<list1.size();i++){
				participantId = list1.get(i).toString();
				htzrMap.remove("guid");
				htzrMap.put("guid", Guid.create());
				htzrMap.remove("participantid");
				htzrMap.put("participantid", participantId);
				try {
					BeanUtils.populate(coContractmembershipVO, htzrMap);
				} catch (Exception e) {
					// TODO: handle exception
				}
				coContractmembership = CoContractmembershipTransfer.toPO(coContractmembershipVO);
				hibernateDao.saveObject(coContractmembership);
			}
		}
		
		//保存输电方
		htzrMap.remove("displaytype");
		htzrMap.put("displaytype", 3);
		if(list2!=null && list2.size()>0){
			for(int i=0;i<list2.size();i++){
				participantId = list2.get(i).toString();
				htzrMap.remove("guid");
				htzrMap.put("guid", Guid.create());
				htzrMap.remove("participantid");
				htzrMap.put("participantid", participantId);
				try {
					BeanUtils.populate(coContractmembershipVO, htzrMap);
				} catch (Exception e) {
					// TODO: handle exception
				}
				coContractmembership = CoContractmembershipTransfer.toPO(coContractmembershipVO);
				hibernateDao.saveObject(coContractmembership);
			}
		}
		
		//纵向传输
		VIParamBean vip = new VIParamBean();
		vip.setMarketid_source(marketId);
		vip.setMarketid_target(DataConstants.marketid_target);//目的场景
		vip.setTableName("co_contracttypeinfo");//表名
		vip.setWhereCondtion("contracttypeid='"+po.getContracttypeid()+"'");
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
		return coContracttypeinfoVo;
	}
	
	//更新记录
	private CoContracttypeinfoVO update(Map<String, ?> map,String poName,String id,User user) {
		
		CoContracttypeinfoVO coContracttypeinfoVo = new CoContracttypeinfoVO();
		Set<String> set = map.keySet();
		CoContracttypeinfo po = hibernateDao.getObject(CoContracttypeinfo.class, id);
		po = (CoContracttypeinfo) DataTypeTrans.getObjectForMapSet2Po(map, po, CoContracttypeinfo.class);
		hibernateDao.updateObject(po);
		//纵向传输
		VIParamBean vip = new VIParamBean();
		vip.setMarketid_source(marketId);
		vip.setMarketid_target(DataConstants.marketid_target);//目的场景
		vip.setTableName("co_contracttypeinfo");//表名
		vip.setWhereCondtion("contracttypeid='"+po.getContracttypeid()+"'");
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
		return coContracttypeinfoVo;
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
			hibernateDao.update("delete from " + CoContracttypeinfo.class.getName() + " where contracttypeid = ?" ,new Object[]{id});
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
		List<CoContracttypeinfo> result = null;
		int count = 0;
		qc.addFrom(CoContracttypeinfo.class);
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
	
	/**
	 * 查询下拉树列表的根节点
	 * @description 方法描述
	 * @param queryCondition
	 * @return
	 * @author mengke 
	 * @date 2014-2-18
	 */
	@SuppressWarnings("unchecked")
	public QueryResultObject queryTree(RequestCondition queryCondition) {
		List<QueryFilter> filterList = queryCondition.getQueryFilter(CoContracttypeinfoVO.class);
		if(filterList != null && filterList.size() > 0){
			for(int i=0;i<filterList.size();i++){
				QueryFilter filter = filterList.get(i);
				String colName = filter.getFieldName();
				if(colName.equals("marketid")){
					marketId = (filter.getValue()==null?"":filter.getValue().toString());//获取场景id
				}else if(colName.equals("starteffectivedate")){
					startTime = (filter.getValue()==null?"":filter.getValue().toString());//获取开始时间
				}else if(colName.equals("endeffectivedate")){
					endTime = (filter.getValue()==null?"":filter.getValue().toString());//获取结束时间
				}
			}
		}
		List list2 = new ArrayList();	//定义参数
		list2.add("91812");
		StringBuffer sql = new StringBuffer();
		sql.append("from ").append(CoContracttypeinfo.class.getName()).append(" where marketid = ? and supertypeid is null and 1=1 ");
		if (ToolsSys.isnull(startTime)){//startTime不为空
			sql.append(" and (starteffectivedate is null or starteffectivedate >= to_date(?,'yyyy-MM-dd'))");
			list2.add(startTime);
		}
		if(ToolsSys.isnull(endTime)){//endTime不为空
			sql.append(" and (endeffectivedate is null or endeffectivedate <= to_date(?,'yyyy-MM-dd'))");
			list2.add(endTime);
		}
		List list = hibernateDao.findAll(sql.toString(), list2.toArray());
		List voList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			CoContracttypeinfo contracttype = (CoContracttypeinfo)list.get(i);
			Map item = new HashMap();
			item.put("contracttypeid", contracttype.getContracttypeid());
			item.put("typename", contracttype.getTypename());
			item.put("supertypeid", contracttype.getSupertypeid());
			item.put("isdefine", contracttype.getIsdefine());
			item.put("starteffectivedate", contracttype.getStarteffectivedate());
			item.put("endeffectivedate", contracttype.getEndeffectivedate());
			item.put("updatetime", contracttype.getUpdatetime());
			
			item.put("marketid", contracttype.getMarketid());
			item.put("effectiveflag", contracttype.getEffectiveflag());
			item.put("description", contracttype.getDescription());
			item.put("hasChildren",hasChildren(contracttype.getContracttypeid(),marketId));
			if(ToolsSys.isnull(contracttype.getUpdatepersonid())){
				try {
					List listUser = AdapterWrapperFactory.getIdentityService().getUsersByLoginCode(contracttype.getUpdatepersonid());//.getUsersByLoginCode(contracttype.getUpdatepersonid());
					if(listUser != null && listUser.size() > 0){
						User user = (User) listUser.get(0);
						item.put("updatepersonid", user.getName());
					}
					else{
						item.put("updatepersonid", contracttype.getUpdatepersonid());
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			voList.add(item);
		}
		QueryResultObject queryResult = RestUtils.wrappQueryResult(voList, voList.size());
		queryResult.addDicItems(wrapDictList(marketId));
		return queryResult;
	}
	
	/**
	 * 判断是否有子节点，有子节点返回true，没有返回false
	 * @description 方法描述
	 * @param contracttypeid
	 * @param marketId
	 * @return
	 * @author mengke
	 * @date 2014-2-18
	 */
	private Boolean hasChildren(String contracttypeid,String marketId){
		List<CoContracttypeinfo> list = new ArrayList<CoContracttypeinfo>();
		StringBuffer sql = new StringBuffer();
		sql.append("from ").append(CoContracttypeinfo.class.getName()).append(" where marketid = ? and supertypeid = ?  and 1=1 ");
		Object[] obj = new Object[]{marketId,contracttypeid};
		list = hibernateDao.findAll(sql.toString(), obj);
		return !list.isEmpty();
	}
	
	/**
	 * 创建合同类型数据字典、父合同类型数据字典、创建维护部门数据字典
	 * @description 方法描述
	 * @param marketId
	 * @return
	 * @author mengke
	 * @date 2014-2-18
	 */
	private List<DicItems> wrapDictList(String marketId) {
		List<DicItems> dicts = new ArrayList<DicItems>();
		dicts.add(queryContractType("contracttypeid",marketId));
		dicts.add(querySuperType("supertypeid",marketId));//父合同类型数据字典
		dicts.add(queryUpdateDep("marketid"));//维护部门数据字典
		return dicts;
	}
	
	/**
	 * 创建合同类型数据字典
	 * @description 方法描述
	 * @param fieldName
	 * @param marketId
	 * @return
	 * @author mengke
	 * @date 2014-2-18
	 */
	private DicItems queryContractType(String fieldName,String marketId){
		List<Map<String, String>> listDic = new ArrayList<Map<String, String>>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT T.CONTRACTTYPEID,T.TYPENAME FROM CO_CONTRACTTYPEINFO T WHERE T.MARKETID = ?");
		List voList = hibernateDao.executeSqlQuery(sql.toString(), new Object[]{marketId});
		if(voList!=null && voList.size()>0){
			for(int i = 0;i<voList.size();i++){
				Map<String, String> map = new HashMap<String, String>();
				Object[] obj = (Object[])voList.get(i);
				String value = obj[0]==null?"":obj[0].toString();//合同类型id
				String name = obj[1]==null?"":obj[1].toString();//合同类型名称
				map.put("value", value);
				map.put("text", name);
				listDic.add(map);
			}
		}
		
		sql.setLength(0);
		sql.append("SELECT T.CONTRACTTYPEID,T.TYPENAME FROM CO_CONTRACTTYPEINFO T WHERE T.MARKETID = ?");
		List voList1 = hibernateDao.executeSqlQuery(sql.toString(), new Object[]{"91812"});
		if(voList1!=null && voList1.size()>0){
			for(int i = 0;i<voList1.size();i++){
				Map<String, String> map = new HashMap<String, String>();
				Object[] obj = (Object[])voList1.get(i);
				String value = obj[0]==null?"":obj[0].toString();//合同类型id
				String name = obj[1]==null?"":obj[1].toString();//合同类型名称
				map.put("value", value);
				map.put("text", name);
				listDic.add(map);
			}
		}
		DicItems dic = new DicItems();
		dic.setName(fieldName);
		dic.setValues(listDic);
		return dic;
	}
	
	/**
	 * 创建父合同类型数据字典
	 * @description 方法描述
	 * @param fieldName
	 * @param marketId
	 * @return
	 * @author mengke
	 * @date 2014-2-18
	 */
	private DicItems querySuperType(String fieldName,String marketId){
		List<Map<String, String>> listDic = new ArrayList<Map<String, String>>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT T.SUPERTYPEID,S.TYPENAME FROM CO_CONTRACTTYPEINFO T ")
		.append("LEFT JOIN CO_CONTRACTTYPEINFO S ON T.SUPERTYPEID = S.CONTRACTTYPEID ")
		.append("WHERE T.MARKETID = ?");
		List voList = hibernateDao.executeSqlQuery(sql.toString(), new Object[]{marketId});
		if(voList!=null && voList.size()>0){
			for(int i = 0;i<voList.size();i++){
				Map<String, String> map = new HashMap<String, String>();
				Object[] obj = (Object[])voList.get(i);
				String value = obj[0]==null?"":obj[0].toString();//父合同类型id
				String name = obj[1]==null?"":obj[1].toString();//父合同类型名称
				map.put("value", value);
				map.put("text", name);
				listDic.add(map);
			}
		}
		DicItems dic = new DicItems();
		dic.setName(fieldName);
		dic.setValues(listDic);
		return dic;
	}
	
	/**
	 * 创建维护部门数据字典
	 * @description 方法描述
	 * @param fieldName
	 * @return
	 * @author mengke
	 * @date 2014-2-18
	 */
	private DicItems queryUpdateDep(String fieldName){
		List<Map<String, String>> listDic = new ArrayList<Map<String, String>>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT DISTINCT S.MARKETID, T.MARKETNAME FROM CO_CONTRACTTYPEINFO S ")
		.append("LEFT JOIN BA_MARKET T ON S.MARKETID = T.MARKETID");
		List voList = hibernateDao.executeSqlQuery(sql.toString());
		if(voList!=null && voList.size()>0){
			for(int i = 0;i<voList.size();i++){
				Map<String, String> map = new HashMap<String, String>();
				Object[] obj = (Object[])voList.get(i);
				String value = obj[0]==null?"":obj[0].toString();//场景id
				String name = obj[1]==null?"":obj[1].toString();//场景名称
				map.put("value", value);
				map.put("text", name);
				listDic.add(map);
			}
		}
		DicItems dic = new DicItems();
		dic.setName(fieldName);
		dic.setValues(listDic);
		return dic;
	}
	
	/**
	 * 查询下拉树列表的子节点
	 * @description 方法描述
	 * @param id
	 * @param queryCondition
	 * @return
	 * @author mengke 
	 * @date 2014-2-18
	 */
	@SuppressWarnings("unchecked")
	public QueryResultObject queryDepTreeChild(String id,RequestCondition queryCondition){
		
		List list2 = new ArrayList();	//定义参数
		list2.add(marketId);
		list2.add(id);
		StringBuffer sql = new StringBuffer();
		sql.append("from ").append(CoContracttypeinfo.class.getName()).append(" where marketid = ? and supertypeid = ? and 1=1 ");
		if (ToolsSys.isnull(startTime)){
			sql.append(" and (starteffectivedate is null or starteffectivedate >= to_date(?,'yyyy-MM-dd'))");
			list2.add(startTime);
		}
		if(ToolsSys.isnull(endTime)){
			sql.append(" and (endeffectivedate is null or endeffectivedate <= to_date(?,'yyyy-MM-dd'))");
			list2.add(endTime);
		}
		Object[] obj = new Object[]{marketId,id,startTime,endTime};
		List list = hibernateDao.findAll(sql.toString(), list2.toArray());
		List voList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			CoContracttypeinfo contracttype = (CoContracttypeinfo)list.get(i);
			Map item = new HashMap();
			item.put("contracttypeid", contracttype.getContracttypeid());
			item.put("typename", contracttype.getTypename());
			item.put("supertypeid", contracttype.getSupertypeid());
			item.put("isdefine", contracttype.getIsdefine());
			item.put("starteffectivedate", contracttype.getStarteffectivedate());
			item.put("endeffectivedate", contracttype.getEndeffectivedate());
			item.put("updatetime", contracttype.getUpdatetime());
			if(ToolsSys.isnull(contracttype.getUpdatepersonid())){
				try {
					List listUser = AdapterWrapperFactory.getIdentityService().getUsersByLoginCode(contracttype.getUpdatepersonid());//.getUsersByLoginCode(contracttype.getUpdatepersonid());
					if(listUser != null && listUser.size() > 0){
						User user = (User) listUser.get(0);
						item.put("updatepersonid", user.getName());
					}
					else{
						item.put("updatepersonid", contracttype.getUpdatepersonid());
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			item.put("marketid", contracttype.getMarketid());
			item.put("effectiveflag", contracttype.getEffectiveflag());
			item.put("description", contracttype.getDescription());
			item.put("hasChildren",hasChildren(contracttype.getContracttypeid(),marketId));
			voList.add(item);
		}
		QueryResultObject queryResult = RestUtils.wrappQueryResult(voList, voList.size());
		queryResult.addDicItems(wrapDictList(marketId));
		return queryResult;
	}
	
	/**
	 * 生效合同类型
	 * @description 方法描述
	 * @param contractTypeId
	 * @return
	 * @author mengke 
	 * @date 2014-2-19
	 */
	public List effectContract(String contractTypeId){
		List resultlist = new ArrayList();
		String result = "";//存放生效结果
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT S.EFFECTIVEFLAG FROM CO_CONTRACTTYPEINFO S WHERE S.CONTRACTTYPEID = ?");
		Object[] objs = new Object[]{contractTypeId};
		List list = hibernateDao.executeSqlQuery(sql.toString(), objs);
		String now = DateUtil.getNowDate("yyyy-MM-dd");
		if(list!=null && list.size()>0){
			Object obj = list.get(0);
			String flag = obj==null?"":obj.toString();//取出查询的该合同类型的是否生效字段值
			if(flag.equals("0")){
				result = "该合同类型已经生效！";
			}else{
				sql.setLength(0);
				sql.append("UPDATE CO_CONTRACTTYPEINFO S SET S.EFFECTIVEFLAG = TO_NUMBER('0'),")
				.append("S.STARTEFFECTIVEDATE = TO_DATE('").append(now).append("','yyyy-MM-dd'),")
				.append("S.ENDEFFECTIVEDATE   = TO_DATE('','YYYY-MM-DD') WHERE S.CONTRACTTYPEID = ?");
				int k = hibernateDao.executeSqlUpdate(sql.toString(), objs);
				if(k>0){
					result = "生效成功！";
				}else{
					result = "生效失败！";
				}
			}
		}
		resultlist.add(result);
		return resultlist;
	}
	
	/**
	 * 失效合同类型
	 * @description 方法描述
	 * @param contractTypeId
	 * @param marketId
	 * @return
	 * @author mengke 
	 * @date 2014-2-19
	 */
	public List invalidContract(String contractTypeId,String marketId){
		List resultList = new ArrayList();
		String result = "";//存放失效结果
		int k = 0;//子合同类型的是否失效更新条数
		int j = 0;//父合同类型的是否失效更新条数
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT S.EFFECTIVEFLAG FROM CO_CONTRACTTYPEINFO S WHERE S.CONTRACTTYPEID = ?");
		Object[] objs1 = new Object[]{contractTypeId};
		List list = hibernateDao.executeSqlQuery(sql.toString(), objs1);
		if(list!=null && list.size()>0){
			Object obj1 = list.get(0);
			String flag = obj1==null?"":obj1.toString();//取出查询的该合同类型的是否生效字段值
			if(flag.equals("1")){
				result = "该合同类型已经失效！";
			}else{
				sql.setLength(0);
				sql.append("UPDATE CO_CONTRACTTYPEINFO S SET S.EFFECTIVEFLAG = TO_NUMBER('1'),S.ENDEFFECTIVEDATE = ")
				.append("TO_DATE('").append(DateUtil.getNowDate("yyyy-MM-dd"))
				.append("','yyyy-MM-dd')").append(" WHERE S.CONTRACTTYPEID = ?");
				j = hibernateDao.executeSqlUpdate(sql.toString(), objs1);
				if(j>0){
					result = "合同类型失效成功！";
				}else{
					result = "合同类型失效失败！";
				}
			}
			sql.setLength(0);
			sql.append("SELECT S.CONTRACTTYPEID,S.TYPENAME FROM CO_CONTRACTTYPEINFO S WHERE S.MARKETID = ? AND S.SUPERTYPEID = ?");
			Object[] objs2 = new Object[]{marketId,contractTypeId};
			list = hibernateDao.executeSqlQuery(sql.toString(), objs2);
			if(list!=null && list.size()>0){
				for(int i=0;i<list.size();i++){
					Object[] obj2 = (Object[])list.get(i);
					String typeId = obj2[0]==null?"":obj2[0].toString();//取出子合同类型id
					invalidContract(typeId,marketId);
				}
			}
		}
		resultList.add(result);
		return resultList;
	}
	
	/**
	 * 
	 * @description 获取已经存在的合同类型名称
	 * @param marketId
	 * @return
	 * @author mengke
	 * @date 2014-3-17
	 */
	public List getTypeName(String marketId,String typename,String supertypeid,String objID){
		StringBuffer sql = new StringBuffer();
		Object[] obj = {};
		
		if(ToolsSys.isnull(supertypeid)){//supertypeid不为空时，此时添加的是子合同类型，则只判断该父类型下的子合同类型名称是否重复
			sql.append("SELECT T.TYPENAME FROM CO_CONTRACTTYPEINFO T WHERE T.MARKETID IN ('91812',?) AND T.SUPERTYPEID=? AND T.TYPENAME=?" );
			obj = new Object[]{marketId,supertypeid,typename};
			if(ToolsSys.isnull(objID)){//objID不为空说明此时是编辑操作，所以判断除自身以外的合同类型名称是否重复
				sql.append(" AND CONTRACTTYPEID NOT IN (?)");
				obj = new Object[]{marketId,supertypeid,typename,objID};
			}
		} 
		else{//supertypeid为空时，此时添加的是父合同类型，则需判断该父合同类型名称是否全局重复
			sql.append("SELECT T.TYPENAME FROM CO_CONTRACTTYPEINFO T WHERE T.MARKETID IN ('91812',?) AND T.TYPENAME=? AND T.SUPERTYPEID IS NULL" );
			obj = new Object[]{marketId,typename};
			if(ToolsSys.isnull(objID)){//objID不为空说明此时是编辑操作，所以判断除自身以外的合同类型名称是否重复
				sql.append(" AND CONTRACTTYPEID NOT IN (?)");
				obj = new Object[]{marketId,typename,objID};
			}
		}
		
		List list = hibernateDao.executeSqlQuery(sql.toString(), obj);
		return list;
	}
	
	private QueryCriteria wrapQuery(RequestCondition queryCondition,
			QueryCriteria qc) {
		List<QueryFilter> wheres = queryCondition.getQueryFilter(CoContracttypeinfoVO.class);
		if (wheres != null && wheres.size() > 0) {
			CrudUtils.addQCWhere(qc, wheres, CoContracttypeinfo.class.getName());

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
		CoContracttypeinfo coContracttypeinfo = (CoContracttypeinfo) hibernateDao.getObject(CoContracttypeinfo.class,id);
		CoContracttypeinfoVO vo = null;
		if (coContracttypeinfo != null) {
			vo = CoContracttypeinfoTransfer.toVO(coContracttypeinfo);
		}
		return RestUtils.wrappQueryResult(vo).addDicItems(wrapDictList());
	}
	
	// 将字典对象封装为list
	private List<DicItems> wrapDictList() {
		List<DicItems> dicts = new ArrayList<DicItems>();
        dicts.add(this.translateFromDB("supertypeid",CoContracttypeinfo.class.getName(),"contracttypeid","typename"));
		return dicts;
	}
	
	//从数据库中查找数据字典 
	private DicItems translateFromDB(String fieldName, String poName,String keyField, String valueField) {
		List<Map<String, String>> list = dataDictionaryBizC.translateFromDB
			(poName, "value", "text", keyField, valueField,"1=1");
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
