package com.sgcc.dlsc.contractManage.contractAddRegister.bizc;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.sgcc.uap.persistence.IHibernateDao;
import com.sgcc.uap.persistence.criterion.QueryCriteria;

import com.sgcc.uap.bizc.sysbizc.datadictionary.IDataDictionaryBizC;

import com.sgcc.uap.rest.support.QueryFilter;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;
import com.sgcc.uap.rest.utils.CrudUtils;
import com.sgcc.uap.rest.utils.JsonUtils;
import com.sgcc.uap.rest.utils.RestUtils;

import com.sgcc.dlsc.commons.bean.VIParamBean;
import com.sgcc.dlsc.commons.bean.VIReturnBean;
import com.sgcc.dlsc.commons.bizc.IComm_VerticalIntegration_Bizc;
import com.sgcc.dlsc.commons.util.ComUtils;
import com.sgcc.dlsc.commons.util.DateUtil;
import com.sgcc.dlsc.commons.util.Guid;
import com.sgcc.dlsc.commons.util.ToolsSys;
import com.sgcc.dlsc.commons.util.UserInfoUtil;
import com.sgcc.dlsc.constants.DataConstants;
import com.sgcc.dlsc.contractManage.coContractbaseinfoV2Act.vo.CoContractbaseinfoTransfer;
import com.sgcc.dlsc.contractManage.coContractbaseinfoV2Act.vo.CoContractbaseinfoVO;
import com.sgcc.dlsc.entity.po.CoContractbaseinfo;
import com.sgcc.dlsc.entity.po.CoContractchangerecordinfo;
import com.sgcc.isc.core.orm.identity.User;
/**
 * 
 * @title ContractAddRegisterBizc
 * @description 描述
 * @author liling
 */
public class ContractAddRegisterBizc implements IContractAddRegisterBizc{

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
	 public List<CoContractbaseinfoVO> saveOrUpdate(List<Map> list,HttpServletRequest request) {
		
		List<CoContractbaseinfoVO> voList = new ArrayList<CoContractbaseinfoVO>();
		String username = "";
		try {
			username = UserInfoUtil.getLoginUser(request).getUserName();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//用户id
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
			Date date = null;
			String poName = CoContractbaseinfo.class.getName();
			String signstate = "";//签订状态
			String backupstate = "";//备案状态
			
			if(map.containsKey("signstate")){
				if(map.get("signstate") !=null &&!"".equals(map.get("signstate"))){
					signstate = map.get("signstate").toString();
				}else {
					map.put("signstate", new BigDecimal("2"));
					signstate = map.get("signstate").toString();
				}
			}
			if(map.containsKey("backupstate")){
				if(map.get("backupstate") !=null &&!"".equals(map.get("backupstate"))){
					backupstate = map.get("backupstate").toString();
				}
			}
			
			if("1".equals(backupstate)){
				map.put("signstate", new BigDecimal("1"));
				map.put("flowflag", new BigDecimal("5"));
			}else if("".equals(backupstate) || "2".equals(backupstate) || "3".equals(backupstate)){
				if("1".equals(signstate)){
					map.put("flowflag", new BigDecimal("4"));
				}else if("".equals(signstate) || "2".equals(signstate)){
					map.put("flowflag", new BigDecimal("1"));
				}
			}
			
			if(map.containsKey("contractid")){
				String id = (String)map.get("contractid");
				map.put("updatepersonid", username);
				
				CoContractbaseinfoVO vo = update(map,poName,id,request);
				voList.add(vo);
			}else{
				String marketId = "";
				try {
					marketId = UserInfoUtil.getLoginUserMarket(request);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				map.put("marketid", marketId);
				map.put("isdelete", 0);
				
				map.put("updatepersonid", username);
				CoContractbaseinfoVO CoContractbaseinfoVO = save(map,request);
				voList.add(CoContractbaseinfoVO);
			}
		}
		return voList;
	 }
	 
	//保存记录
	private CoContractbaseinfoVO save(Map map,HttpServletRequest request) {
		
		CoContractbaseinfoVO CoContractbaseinfoVO = new CoContractbaseinfoVO();
		try {
			String contractTypeId = map.get("contracttype")==null?"":map.get("contracttype").toString();
			StringBuffer sql = new StringBuffer();
			sql.append("select t.contracttypeid from co_contracttypeinfo t where t.contracttypeid = '402881da3c3847ba013c3854017a0001' or t.supertypeid='402881da3c3847ba013c3854017a0001'");
			List list = hibernateDao.executeSqlQuery(sql.toString());
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					if(list.get(i).toString().equals(contractTypeId)){
						map.put("transactiontype", "200100");
					}
				}
			}
			
			Date date = null;
			String temp = "";
			if(map.get("updatetime") !=null &&!"".equals(map.get("updatetime"))){
				date = DateUtil.getUtilDate(map.get("updatetime").toString(), "yyyy-MM-dd hh:MM:ss");
			}
			else{
				date = DateUtil.getUtilDate(DateUtil.getNowDate("yyyy-MM-dd hh:MM:ss"), "yyyy-MM-dd hh:MM:ss");
//				date = new java.sql.Timestamp(System.currentTimeMillis());
			}
			map.put("updatetime", date);
			if(map.get("contractstartdate") !=null &&!"".equals(map.get("contractstartdate"))){
				date = DateUtil.getUtilDate(map.get("contractstartdate").toString(), "yyyy-MM-dd");
			}
			else{
				date = new Date();
			}
			map.put("contractstartdate", date);
			if(map.get("contractenddate") !=null &&!"".equals(map.get("contractenddate"))){
				date = DateUtil.getUtilDate(map.get("contractenddate").toString(), "yyyy-MM-dd");
			}
			else{
				date = new Date();
			}
			map.put("contractenddate", date);
			if(map.get("signeddate") !=null &&!"".equals(map.get("signeddate"))){
				date = DateUtil.getUtilDate(map.get("signeddate").toString(), "yyyy-MM-dd");
				map.put("signeddate", date);
			}
			if(map.get("backupdate") !=null &&!"".equals(map.get("backupdate"))){
				date = DateUtil.getUtilDate(map.get("backupdate").toString(), "yyyy-MM-dd");
				map.put("backupdate", date);
			}
			if(map.get("purchaserconftime") !=null &&!"".equals(map.get("purchaserconftime"))){
				date = DateUtil.getUtilDate(map.get("purchaserconftime").toString(), "yyyy-MM-dd");
				map.put("purchaserconftime", date);
			}
			if(map.get("purchaserconftime") !=null &&!"".equals(map.get("purchaserconftime"))){
				date = DateUtil.getUtilDate(map.get("purchaserconftime").toString(), "yyyy-MM-dd");
				map.put("purchaserconftime", date);
			}
			if(map.get("sellerconftime") !=null &&!"".equals(map.get("sellerconftime"))){
				date = DateUtil.getUtilDate(map.get("sellerconftime").toString(), "yyyy-MM-dd");
				map.put("sellerconftime", date);
			}
			if(map.get("transconftime") !=null &&!"".equals(map.get("transconftime"))){
				date = DateUtil.getUtilDate(map.get("transconftime").toString(), "yyyy-MM-dd");
				map.put("transconftime", date);
			}
			if(map.get("expend3") !=null &&!"".equals(map.get("expend3"))){
				date = DateUtil.getUtilDate(map.get("expend3").toString(), "yyyy-MM-dd");
				map.put("expend3", date);
			}
			if(map.get("expend4") !=null &&!"".equals(map.get("expend4"))){
				date = DateUtil.getUtilDate(map.get("expend4").toString(), "yyyy-MM-dd");
				map.put("expend4", date);
			}
			map.put("versionid",Guid.create());
			BeanUtils.populate(CoContractbaseinfoVO, map);
		} catch (Exception e) {
		}
		CoContractbaseinfo coContractbaseinfo = CoContractbaseinfoTransfer.toPO(CoContractbaseinfoVO);
		coContractbaseinfo.setIsdelete(new BigDecimal("0"));
		coContractbaseinfo.setCoVersion("V1");
		Date date1 = new java.sql.Timestamp(System.currentTimeMillis());
		coContractbaseinfo.setUpdatetime(date1);
		hibernateDao.saveOrUpdateObject(coContractbaseinfo);
		//对于UAP开发的合同，改为新增时保存历史，变更时保存历史，最新历史记录即与合同最新基础信息相同
		CoContractchangerecordinfo po = new CoContractchangerecordinfo();//历史表
		//更新操作
		//更新历史，存储poco到历史表
		ComUtils.obj2obj(coContractbaseinfo, po);
		po.setCmd("新增操作");
		po.setOperator(coContractbaseinfo.getUpdatepersonid());
		po.setOperatingdescription("合同变更说明");
		po.setOperatedate(new java.sql.Timestamp(System.currentTimeMillis()));
		po.setOrderno(new BigDecimal("1"));
//		po.setCoVersion(this.excCoVersion(po.getCoVersion()));
		po.setVersionid(null);
		//保存历史
		hibernateDao.saveOrUpdateObject(po);
		CoContractbaseinfoVO = CoContractbaseinfoTransfer.toVO(coContractbaseinfo);
		if(map.containsKey("mxVirtualId")){
			CoContractbaseinfoVO.setMxVirtualId(String.valueOf(map.get("mxVirtualId")));
		}
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
		vip.setTableName("co_contractbaseinfo");//表名
		vip.setWhereCondtion("contractid='"+po.getContractid()+"'");//过滤条件
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
		return CoContractbaseinfoVO;
	}
	
	//更新记录
	private CoContractbaseinfoVO update(Map map,String poName,String id,HttpServletRequest request) {
		String username="";
		String signstate = "";//签订状态
		String backupstate = "";//备案状态
		CoContractbaseinfoVO CoContractbaseinfoVO = new CoContractbaseinfoVO();
		//变更时，对空数据进行校验
		Set set = map.keySet();
		ArrayList<String> keyList = new ArrayList<String>();
		try {
			username = UserInfoUtil.getLoginUserId(request);//用户id
			for (Object object : set) {
				String key = (String) object;
				if(map.get(key)==null){
					keyList.add(key);
				}
			}
			for (String string : keyList) {
				map.remove(string);//移除变更时为空的key
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//对移除后的数据重新校验，如只存在主键即为size=1，则不执行更新
		Set set1 = map.keySet();
		if(set1 != null && set1.size() > 1){
			Object[] objArray = CrudUtils.generateHql(CoContractbaseinfo.class, map, "contractid");
			hibernateDao.update((String) objArray[0], (Object[]) objArray[1]);
		}
		CoContractbaseinfo pojo = hibernateDao.getObject(CoContractbaseinfo.class, id);
		pojo.setCoVersion(this.excCoVersion(pojo.getCoVersion()));//版本管理
		
		//同步签订状态、备案状态、合同状态三个字段
		signstate = pojo.getSignstate()==null?"":pojo.getSignstate().toString();//签订状态
		backupstate = pojo.getBackupstate()==null?"":pojo.getBackupstate().toString();//备案状态
		if("1".equals(backupstate)){
			pojo.setSignstate(new BigDecimal("1"));//已签订
			pojo.setFlowflag(new BigDecimal("5"));//已备案
		}else if("".equals(backupstate) || "2".equals(backupstate) || "3".equals(backupstate)){
			if("1".equals(signstate)){
				pojo.setFlowflag(new BigDecimal("4"));//已签订
			}else if("".equals(signstate) || "2".equals(signstate)){
				pojo.setFlowflag(new BigDecimal("1"));//起草中
			}
		}
		
		Date date1 = new java.sql.Timestamp(System.currentTimeMillis());
//		pojo.setUpdatetime(date1);
		for (String string : keyList) {
			try{
				Field field = CoContractbaseinfo.class.getDeclaredField(string);
				field.setAccessible(true);
				field.set(pojo, null);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		hibernateDao.updateObject(pojo);
		//对于UAP开发的合同，改为新增时保存历史，变更时保存历史，最新历史记录即与合同最新基础信息相同
		//检索未存储前记录poco
		CoContractbaseinfo poco = hibernateDao.getObject(CoContractbaseinfo.class, id);
		CoContractchangerecordinfo po = new CoContractchangerecordinfo();//历史表
		//更新操作
			
		//更新历史，存储poco到历史表
		ComUtils.obj2obj(poco, po);
		po.setCmd("更新操作");
		po.setOperator(username);
		po.setOperatingdescription("合同变更说明");
		po.setOperatedate(date1);
		po.setUpdatetime(date1);
		po.setOrderno(new BigDecimal("1"));
		po.setVersionid(null);
		//保存历史
		hibernateDao.saveOrUpdateObject(po);
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
		vip.setTableName("co_contractbaseinfo");//表名
		vip.setWhereCondtion("contractid='"+po.getContractid()+"'");//过滤条件
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
		try {
			Date date = null;
			if(map.get("updatetime") !=null &&!"".equals(map.get("updatetime"))){
				date = DateUtil.getUtilDate(map.get("updatetime").toString(), "yyyy-MM-dd hh:MM:ss");
			}
//			else{
////				date = DateUtil.getUtilDate(DateUtil.getNowDate("yyyy-MM-dd hh:MM:ss"), "yyyy-MM-dd hh:MM:ss");
//				date = new java.sql.Timestamp(System.currentTimeMillis());
//			}
			map.put("updatetime", date);
			if(map.get("contractstartdate") !=null &&!"".equals(map.get("contractstartdate"))){
				date = DateUtil.getUtilDate(map.get("contractstartdate").toString(), "yyyy-MM-dd");
				map.remove("contractstartdate");
				map.put("contractstartdate", date);
			}
			if(map.get("contractenddate") !=null &&!"".equals(map.get("contractenddate"))){
				date = DateUtil.getUtilDate(map.get("contractenddate").toString(),"yyyy-MM-dd");//
				map.put("contractenddate", date);
			}
			if(map.get("signeddate") !=null &&!"".equals(map.get("signeddate"))){
				date = DateUtil.getUtilDate(map.get("signeddate").toString(), "yyyy-MM-dd");
				map.put("signeddate", date);
			}
			if(map.get("backupdate") !=null &&!"".equals(map.get("backupdate"))){
				date = DateUtil.getUtilDate(map.get("backupdate").toString(), "yyyy-MM-dd");
				map.put("backupdate", date);
			}
			if(map.get("purchaserconftime") !=null &&!"".equals(map.get("purchaserconftime"))){
				date = DateUtil.getUtilDate(map.get("purchaserconftime").toString(), "yyyy-MM-dd");
				map.put("purchaserconftime", date);
			}
			if(map.get("purchaserconftime") !=null &&!"".equals(map.get("purchaserconftime"))){
				date = DateUtil.getUtilDate(map.get("purchaserconftime").toString(), "yyyy-MM-dd");
				map.put("purchaserconftime", date);
			}
			if(map.get("sellerconftime") !=null &&!"".equals(map.get("sellerconftime"))){
				date = DateUtil.getUtilDate(map.get("sellerconftime").toString(), "yyyy-MM-dd");
				map.put("sellerconftime", date);
			}
			if(map.get("transconftime") !=null &&!"".equals(map.get("transconftime"))){
				date = DateUtil.getUtilDate(map.get("transconftime").toString(), "yyyy-MM-dd");
				map.put("transconftime", date);
			}
			if(map.get("expend3") !=null &&!"".equals(map.get("expend3"))){
				date = DateUtil.getUtilDate(map.get("expend3").toString(), "yyyy-MM-dd");
				map.put("expend3", date);
			}
			if(map.get("expend4") !=null &&!"".equals(map.get("expend4"))){
				date = DateUtil.getUtilDate(map.get("expend4").toString(), "yyyy-MM-dd");
				map.put("expend4", date);
			}
			BeanUtils.populate(CoContractbaseinfoVO, map);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return CoContractbaseinfoVO;
	}
	/**
	 * 获得合同版本
	 * @description 方法描述
	 * @param coVersion
	 * @return
	 * @author liling
	 * @date 2014-3-11
	 */
	private String excCoVersion(String coVersion){
		if(ToolsSys.isnull(coVersion)){//非空
			coVersion = coVersion.substring(1, coVersion.length());
			coVersion = "V"+ (Integer.parseInt(coVersion) + 1);
		}else{//空值
			coVersion = "V1";
		}
		return coVersion;
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
		return RestUtils.wrappQueryResult(vo);//.addDicItems(wrapDictList());
	}
	
	/**
	 * 
	 * @description 获取批复电价
	 * @param params
	 * @return
	 * @author mengke 
	 * @date 2014-6-5
	 */
	public List getPfdjList(String params){
		Map map = null;//存放参数
		String sellerId = null;//售电方成员id
		
		try {
			map = JsonUtils.getObjectMapper().readValue(params, Map.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(map != null){
			sellerId = map.get("sellerId")==null?"":map.get("sellerId").toString();
		}
		StringBuffer sql = new StringBuffer();
		sql.append("	select s.busiunitname, t.efficive_time, t.t_price, ")
		.append(" s.busiunitname||to_char(t.efficive_time,'yyyy/mm/dd')||'生效电价'||t.t_price as pfdj ")
		.append("	  from SE_PRICE_INFO t                            ")
		.append("	  left join BA_BusiUnit s                         ")
		.append("	    on t.sbs_unit_id = s.busiunitid               ")
		.append("	 where t.sbs_unit_type = '1'                      ")
		.append("	   and t.pricetype_id = '99'                      ")
		.append("	   and s.bidtype = '4'                            ")
		.append("	   and s.participantid = ?         ");
		List list = hibernateDao.executeSqlQuery(sql.toString(), new Object[]{sellerId});
		return list;
	}
	
	/**
	 * 
	 * @description 获取厂用电率
	 * @param perchaserId
	 * @param marketId
	 * @return
	 * @author mengke
	 * @date 2014-7-15
	 */
	public List getCydl(String participantid,String marketId){
		StringBuffer sql = new StringBuffer();
		sql.append("			select max(tt.ratio)                                ")
		.append("			  from SC_SELFUSE_RATIO tt                              ")
		.append("			  left join ba_busiunit bb                              ")
		.append("			    on bb.busiunitid = tt.planunit_id                   ")
		.append("			 where tt.market_id = ?                                 ")
		.append("			 and tt.decom_type = 0                                  ")
		.append("			 and to_char(tt.limit_date,'yyyy-mm-dd') = ?            ")
		.append("			 and bb.bidtype = '2'                                   ")
		.append("			 and bb.participantid = ?                               ");
		String date = DateUtil.getNowDate("yyyy-MM-dd");
		String[] dates = date.split("-");
		date = dates[0]+"-01-01";//当年一月一日
		Object[] objs = new Object[]{marketId,date,participantid};
		List list = hibernateDao.executeSqlQuery(sql.toString(), objs);
		return list;
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

	public IComm_VerticalIntegration_Bizc getCommVerticalIntegrationBizc() {
		return commVerticalIntegrationBizc;
	}

	public void setCommVerticalIntegrationBizc(
			IComm_VerticalIntegration_Bizc commVerticalIntegrationBizc) {
		this.commVerticalIntegrationBizc = commVerticalIntegrationBizc;
	}

}
