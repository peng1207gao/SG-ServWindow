package com.sgcc.dlsc.contractManage.resolveelectricquantitytounit.bizc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sgcc.dlsc.commons.bean.VIParamBean;
import com.sgcc.dlsc.commons.bean.VIReturnBean;
import com.sgcc.dlsc.commons.bizc.IComm_VerticalIntegration_Bizc;
import com.sgcc.dlsc.commons.util.DateUtil;
import com.sgcc.dlsc.commons.util.Guid;
import com.sgcc.dlsc.commons.util.UserInfoUtil;
import com.sgcc.dlsc.constants.DataConstants;
import com.sgcc.dlsc.contractManage.resolveelectricquantitytounit.vo.CaculateEcoUnitBean;
import com.sgcc.dlsc.contractManage.resolveelectricquantitytounit.vo.CoBusiGeneratorQtyVO;
import com.sgcc.dlsc.contractManage.resolveelectricquantitytounit.vo.CoContractenergyinfoTransfer;
import com.sgcc.dlsc.contractManage.resolveelectricquantitytounit.vo.CoContractenergyinfoVO;
import com.sgcc.dlsc.contractManage.resolveelectricquantitytounit.vo.ContractUnitBean;
import com.sgcc.dlsc.entity.po.CoBusigeneratorqty;
import com.sgcc.dlsc.entity.po.CoContractenergyinfo;
import com.sgcc.isc.core.orm.identity.User;
import com.sgcc.uap.persistence.IHibernateDao;
import com.sgcc.uap.rest.support.IDRequestObject;
import com.sgcc.uap.rest.support.QueryFilter;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;
import com.sgcc.uap.rest.utils.JsonUtils;
import com.sgcc.uap.rest.utils.RestUtils;
/**
 * 用户定义逻辑构件
 * 
 * @author juguanghui 
 * 
 */
public class ResolveelectricquantitytounitBizc implements IResolveelectricquantitytounitBizc{

	@Autowired
	private IHibernateDao hibernateDao;
	
	private ArrayList<CoBusiGeneratorQtyVO> listDataForSave = new ArrayList<CoBusiGeneratorQtyVO>();//待保存记录
	
	private final static Log resolveLog = LogFactory.getLog(ResolveelectricquantitytounitBizc.class);
	
	@Autowired
	private IComm_VerticalIntegration_Bizc commVerticalIntegrationBizc;
	
	/**
	 * 保存更新
	 * 
	 * @param list
	 * @param request
	 * @param items 电量被修改过的记录
	 */
	 public String saveOrUpdate(String marketid, HttpServletRequest request) {
		ArrayList<CoBusigeneratorqty> poList = new ArrayList<CoBusigeneratorqty>();
		if(listDataForSave == null || listDataForSave.size() == 0){
//			if(items != null && items.size() != 0){
//				update(items);//更新修改过的电量值
//			}
			return "empty";
		}
		ArrayList<CoBusiGeneratorQtyVO> list = listDataForSave;
		
		Set<String> contractidSet = new HashSet<String>();
		
		if(list != null && list.size() != 0){
			for(int j = 0; j < list.size(); j++){
				CoBusiGeneratorQtyVO busiGeratorQtyVO = list.get(j);
				contractidSet.add(busiGeratorQtyVO.getContractid());
				
				ArrayList<CoBusigeneratorqty> qtyList = CoContractenergyinfoTransfer.energyinfo2BuisGneratorQTY(busiGeratorQtyVO, marketid);
				poList.addAll(qtyList);
			}
			
			StringBuffer ids= new StringBuffer();
			Iterator<String> it = contractidSet.iterator();
			while(it.hasNext()){
				ids.append("'");
				ids.append(it.next());
				ids.append("',");
			}
			ids.append("''");
			String sql = " delete from co_busigeneratorqty where contractid in (" + ids +")";
			hibernateDao.executeSqlUpdate(sql);
			
//			for(int i = 0; i < list.size(); i++){
//				CoBusiGeneratorQtyVO busiGeratorQtyVO = list.get(i);
//				ArrayList<CoBusigeneratorqty> qtyList = CoContractenergyinfoTransfer.energyinfo2BuisGneratorQTY(busiGeratorQtyVO, marketid);
//				poList.addAll(qtyList);
//			}
			try {
				hibernateDao.saveAllObjectWithoutCache(poList);
				listDataForSave.clear();//保存过后，清空
				
				Set<String> set = new HashSet<String>();
				Iterator<CoBusigeneratorqty> poListItra = poList.iterator();
				while(poListItra.hasNext()){
					CoBusigeneratorqty qty = poListItra.next();
					set.add(qty.getContractid());
				}
				Iterator<String> contractIdIter = set.iterator(); 
				while(contractIdIter.hasNext()){
					String contractid = contractIdIter.next();
					//纵向传输
//					String marketId = map.get("marketId").toString();
					VIParamBean vip = new VIParamBean();
					vip.setMarketid_source(marketid);
					vip.setMarketid_target(DataConstants.marketid_target);//目的场景
					vip.setTableName("co_busigeneratorqty");//表名
					vip.setWhereCondtion("contractid='"+contractid+"'");
					User user;
					try {
						user = UserInfoUtil.getLoginUser(request);
						vip.setUserId(user.getId());
						vip.setUserName(user.getUserName());//拼音
					} catch (Exception e) {
						e.printStackTrace();
					}
					vip.setDate(DateUtil.getNowDate("yyyy-MM-dd"));
					VIReturnBean vir = commVerticalIntegrationBizc.executeVerticalIntegrationHSXByLogObjId(vip);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return "fail";
			}
		} else {
			return "fail";
		}
		return "success";
	 }
	 
	 /**
	  * 更新界面修改过的电量值
	  * @param items
	  */
	 public String update(List<Map> items){
		try {
			for (int k = 0; k < items.size(); k++) {
				Map map = items.get(k);
				String contractid = map.get("contractid") == null?"":map.get("contractid").toString();
				String timeno = map.get("timeno") == null?"":map.get("timeno").toString();
				String purchaerUnitId = map.get("purchaseunitid") == null?"":map.get("purchaseunitid").toString();
				String sellerUnitId = map.get("sellerunitid") == null?"":map.get("sellerunitid").toString();
				String period = map.get("period") == null?"":map.get("period").toString();
				String value = map.get("value") == null?"":map.get("value").toString();
				
				String sql = "update co_busigeneratorqty set genqty = ?, netqty = ? " +
							"where contractid = ? and timeno = ? " +
							"and purchaseunit = ? and sellerunit = ? " +
							"and period = ?";   
				Object [] obj = new Object[]{value,value,contractid,timeno,purchaerUnitId,sellerUnitId,period};
				hibernateDao.executeSqlUpdate(sql, obj);
			}
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
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
			hibernateDao.update("delete from " + CoContractenergyinfo.class.getName() + " where guid = ?" ,new Object[]{id});
		}
	}
	
	/**
	 * 根据输入条件查询记录
	 * 
	 * @param queryCondition
	 * @return
	 */
	public QueryResultObject query(RequestCondition queryCondition) {
		Object obj = queryCondition.getFilter();
		try {
			Map map = JsonUtils.getObjectMapper().readValue(obj.toString(), Map.class);
			ArrayList<String> contractidList = null;
			if(map != null ){
				contractidList = map.get("contractids")==null?null:(ArrayList<String>)map.get("contractids");//获取前台传入参数
			}
			if(contractidList != null && contractidList.size() != 0){
				return getContractInfo(contractidList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//全局变量，存放电量分解之前contractid,以及对应的尖（峰，平，谷，总）电量和
	//Map<contractid,Map<"totalValue",totalValue>>
	Map<String, Map<String,Double>> accessoryMap = new HashMap<String, Map<String,Double>>();
	
	//co_contractenergyinfo 缓存
	ArrayList<CoContractenergyinfoVO> energyInfoList = new ArrayList<CoContractenergyinfoVO>();
	/** 
	 * 通过contracid查出表中数据
	 * @param contractIdList
	 */
	private QueryResultObject getContractInfo(ArrayList contractidList) {
		energyInfoList.clear();//移除内容
		String sql = getEnergyInfoSql();
		String contractbasesql = getContractInfoSQL();//获取co_contractbaseinfo表中合同电量信息
		for(int i = 0 ; i < contractidList.size(); i++){
			
			Object [] paramObj = {contractidList.get(i)}; 
			List resultList = hibernateDao.executeSqlQuery(sql, paramObj);
			if(resultList == null || resultList.size() == 0){//通过contractid没有在co_contractenergyinfo表中查到 尖 峰 平 谷 总的数据信息，界面只显示总电量
//				Object [] energyInfoParam = {contractidList.get(i)};
				resultList = hibernateDao.executeSqlQuery(contractbasesql, paramObj);
				for(int k = 0; k <resultList.size(); k++){
					CoContractenergyinfoVO energyInfoVo = new CoContractenergyinfoVO();
					Object[] obj = (Object [])resultList.get(k);
					if(obj != null){
						energyInfoVo.setContractid(validateString(obj[0]));
						energyInfoVo.setContractname(validateString(obj[1]));
						energyInfoVo.setStartdate(validateDate(obj[2]));
						energyInfoVo.setEnddate(validateDate(obj[3]));
						energyInfoVo.setTotalpower(validateString(obj[4]));
						energyInfoVo.setTimeno(new BigDecimal("1"));
						energyInfoList.add(energyInfoVo);
					}
				}
			} else {//通过contractid 能够在co_Contractenergyinfo中查找到数据
				for(int j = 0; j <resultList.size(); j++){
					CoContractenergyinfoVO energyInfoVo = new CoContractenergyinfoVO();
					Object[] obj = (Object [])resultList.get(j);
					if(obj != null){
						energyInfoVo.setContractid(validateString(obj[0]));
						energyInfoVo.setStartdate(validateDate(obj[1]));
						energyInfoVo.setEnddate(validateDate(obj[2]));
						energyInfoVo.setContractname(validateString(obj[3]));
						energyInfoVo.setTotalpower(validateString(obj[4]));
						energyInfoVo.setPeakpower(validateString(obj[5]));
						energyInfoVo.setToppower(validateString(obj[6]));
						energyInfoVo.setFlatpower(validateString(obj[7]));
						energyInfoVo.setValleypower(validateString(obj[8]));
						energyInfoVo.setTimeno(validateBigDecimal(obj[9]));
						
						energyInfoList.add(energyInfoVo);
					}
				}
			}
		}
		return RestUtils.wrappQueryResult(energyInfoList, energyInfoList.size());
	}
	
	private double string2Double(String value){
		if(value != null && value.length() != 0){
			try {
				return Double.parseDouble(value);
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
			
		} else {
			return 0;
		}
	}
	
	private Date validateDate(Object obj){
		return obj==null?null:DateUtil.getUtilDate(obj.toString(), "yyyy-MM-dd");
	}
	
	private BigDecimal validateBigDecimal(Object obj){
		return obj==null?null:new BigDecimal(obj.toString());
	}
	
	private String validateString(Object obj){
		return obj==null?"":obj.toString();
	}
	
	/***
	 * 查询co_contractenergyinfo表中数据的SQL
	 */
	private String getEnergyInfoSql(){
		StringBuffer sql = new StringBuffer();
		sql.append("select");
		sql.append(" contractid,startdate,enddate,contractname,");
		sql.append(" (select energy from CO_ContractEnergyInfo b where period='1' and b.contractid = a.contractid and b.startdate = a.startdate and b.enddate = a.enddate and whereinsert = '1') totalpower,");
		sql.append(" (select energy from CO_ContractEnergyInfo b where period='2' and b.contractid = a.contractid and b.startdate = a.startdate and b.enddate = a.enddate and whereinsert = '1') peakpower,");
		sql.append(" (select energy from CO_ContractEnergyInfo b where period='3' and b.contractid = a.contractid and b.startdate = a.startdate and b.enddate = a.enddate and whereinsert = '1') toppower,");
		sql.append(" (select energy from CO_ContractEnergyInfo b where period='4' and b.contractid = a.contractid and b.startdate = a.startdate and b.enddate = a.enddate and whereinsert = '1') flatpower,");
		sql.append(" (select energy from CO_ContractEnergyInfo b where period='5' and b.contractid = a.contractid and b.startdate = a.startdate and b.enddate = a.enddate and whereinsert = '1') valleypower,");
		sql.append(" timeno ");
		sql.append(" from");
		sql.append(" (select energy.energy,energy.contractid,energy.startdate,energy.enddate,base.contractname,timeno");
		sql.append("  from (select * from  CO_ContractEnergyInfo  where  period is not null and contractid = ? and whereinsert = '1') energy ,co_contractbaseinfo base ");
		sql.append("  where energy.contractid =base.contractid ) a");
		sql.append(" group by contractid,startdate,enddate,contractname,timeno");
		sql.append(" order by contractname");
		return sql.toString();
	}
	
	/**
	 *  查询co_contractbaseinfo表中数据SQL
	 */
	private String getContractInfoSQL(){
		StringBuffer sql = new StringBuffer();
		sql.append("select contractid,contractname,contractstartdate,contractenddate,contractqty ");
		sql.append(" from co_contractbaseinfo ");
		sql.append(" where contractid=?");
		return sql.toString();
	}

	/**
	 * 查询单条记录
	 * @param id
	 * @return QueryResultObject
	 */
	public QueryResultObject queryById(String id) {
		CoContractenergyinfo coContractenergyinfo = (CoContractenergyinfo) hibernateDao.getObject(CoContractenergyinfo.class,id);
		CoContractenergyinfoVO vo = null;
		if (coContractenergyinfo != null) {
			vo = CoContractenergyinfoTransfer.toVO(coContractenergyinfo);
		}
		return RestUtils.wrappQueryResult(vo);
	}

	public void setHibernateDao(IHibernateDao hibernateDao) {
		this.hibernateDao = hibernateDao;
	}
	/**
	 * 分解电量到经济机组逻辑
	 */
	public QueryResultObject contractAccessory(RequestCondition queryCondition) {
		Object obj = queryCondition.getFilter();
		int pageIndex  = Integer.valueOf(queryCondition.getPageIndex());
		int pageSize  = Integer.valueOf(queryCondition.getPageSize());
		try {
			Map map = JsonUtils.getObjectMapper().readValue(obj.toString(), Map.class);
			ArrayList<String> contractidList = null;
			boolean isclear = false;
			boolean chooseUnit = false;
			if(map != null ){
				contractidList = map.get("contractids")==null?null:(ArrayList<String>)map.get("contractids");//获取前台传入参数
				isclear = Boolean.parseBoolean((map.get("clear")==null?"false":map.get("clear").toString()));
				chooseUnit = Boolean.parseBoolean((map.get("chooseunit")==null?"false":map.get("chooseunit").toString()));
			}
			
			if(chooseUnit){
				ArrayList contractunits = map.get("checkeditems") == null?null:(ArrayList)map.get("checkeditems");
				if(contractunits != null){
					return getContractAccessoryInfoWithChooseUnit(contractunits,pageIndex,pageSize);
				}
			} else {
				if(isclear && contractidList != null && contractidList.size() != 0){//刷新下方表格数据
					return getBusigeneratorqtyInfo(contractidList,pageIndex,pageSize);
				}
				if(contractidList != null){
					return getContractAccessoryInfo(contractidList,pageIndex,pageSize);//获取经济机组信息
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private QueryResultObject getContractAccessoryInfoWithChooseUnit(ArrayList contractunits, int pageIndex, int pageSize){ 
		Map<String, ContractUnitBean> contractUnit = new HashMap<String, ContractUnitBean>();//存储选中的机组信息
		
		Set<String> contractSet = new TreeSet<String>();//合同id集合。多个相同合同id，只保留一个
		
		for(int i = 0; i < contractunits.size(); i++){
			String param = contractunits.get(i).toString();
			String [] contractInfo = param.split("&");//参数结构：合同id&合同角色&经济机组id "contractid&contractrole&ecounitid"
			
			String contractid = contractInfo[0].toString();
			String contractrole = contractInfo[1].toString();
			String ecounitid = contractInfo[2].toString();
			
			if(contractSet.contains(contractid)){//集合中已经包含contractid,去除map中的bean
				ContractUnitBean unitBean = contractUnit.get(contractid);
				if("1".equals(contractrole)){//购电方
					unitBean.getPurchaserList().add(ecounitid);//保存购电方经济机组id
				} else if ("2".equals(contractrole)){//售电方
					unitBean.getSellerList().add(ecounitid);//保存售电方经济机组id
				}
			} else {
				ContractUnitBean unitBean = new ContractUnitBean();
				unitBean.setContractid(contractid);
				if("1".equals(contractrole)){//购电方
					unitBean.getPurchaserList().add(ecounitid);//保存购电方经济机组id
				} else if ("2".equals(contractrole)){//售电方
					unitBean.getSellerList().add(ecounitid);//保存售电方经济机组id
				}
				contractSet.add(contractid);
				contractUnit.put(contractid, unitBean);
			}
		}
		
		listDataForSave.clear();
		for(int i = 0 ; i< energyInfoList.size(); i++){
			Set contractidSet = contractUnit.keySet();
			if(contractidSet.contains(energyInfoList.get(i).getContractid())){
				boolean isBigUser = isBigUser(energyInfoList.get(i).getContractid());
				ArrayList<CoBusiGeneratorQtyVO> contractAccessoryList = getContractAccessoryData(energyInfoList.get(i), contractUnit.get(energyInfoList.get(i).getContractid()), isBigUser);//获取每个contractid的分解结果
				if(contractAccessoryList != null){
					for(int j = 0; j < contractAccessoryList.size(); j++){
						listDataForSave.add(contractAccessoryList.get(j));
					}
				}
			}
		}
		
		//用于分页处理 
		int fromIndex = (pageIndex - 1) * pageSize;
		int toIndex = 0;
		if((pageIndex * pageSize - 1) >= listDataForSave.size()){
			toIndex = listDataForSave.size();
		} else {
			toIndex = (pageIndex * pageSize);
		}
		return RestUtils.wrappQueryResult(listDataForSave.subList(fromIndex, toIndex), listDataForSave.size());
	}
	
	
	private QueryResultObject getBusigeneratorqtyInfo(ArrayList contractidList, int pageIndex, int pageSize) {
		StringBuffer contractids = new StringBuffer();
		for(int i = 0; i < contractidList.size(); i++){
			contractids.append("'");
			contractids.append(contractidList.get(i));
			contractids.append("',");
		}
		contractids.append("''");
	  
		StringBuffer sql = new StringBuffer();
		
		sql.append("select contractid,purchaseunit,sellerunit,timeno,startdate,enddate,b.purchasename,b.sellername, ");
		sql.append("(select genqty from co_busigeneratorqty a where a.contractid = b.contractid and a.purchaseunit = b.purchaseunit and a.sellerunit = b.sellerunit and a.timeno = b.timeno and period = '1') total, ");
		sql.append("(select genqty from co_busigeneratorqty a where a.contractid = b.contractid and a.purchaseunit = b.purchaseunit and a.sellerunit = b.sellerunit and a.timeno = b.timeno and period = '2') peak, ");
		sql.append("(select genqty from co_busigeneratorqty a where a.contractid = b.contractid and a.purchaseunit = b.purchaseunit and a.sellerunit = b.sellerunit and a.timeno = b.timeno and period = '3') top, ");
		sql.append("(select genqty from co_busigeneratorqty a where a.contractid = b.contractid and a.purchaseunit = b.purchaseunit and a.sellerunit = b.sellerunit and a.timeno = b.timeno and period = '4') flat, ");
		sql.append("(select genqty from co_busigeneratorqty a where a.contractid = b.contractid and a.purchaseunit = b.purchaseunit and a.sellerunit = b.sellerunit and a.timeno = b.timeno and period = '5') vallay ");
		sql.append("from (select contractid,purchaseunit,sellerunit,timeno,startdate,enddate,period,genqty,econ1.ecounitname purchasename,econ2.ecounitname sellername,busigenerator.guid ");
		sql.append("from co_busigeneratorqty busigenerator inner join ba_economicunit econ1 on econ1.ecounitid = busigenerator.purchaseunit ");
		sql.append("inner join ba_economicunit econ2 on econ2.ecounitid = busigenerator.sellerunit ");
		sql.append("where contractid in (" + contractids.toString() + ") ) b ");
		sql.append("group by contractid,purchaseunit,sellerunit,timeno,startdate,enddate,b.purchasename,b.sellername ");
		sql.append("order by timeno ");

		List list = hibernateDao.executeSqlQuery(sql.toString(), pageIndex, pageSize);
		ArrayList<CoBusiGeneratorQtyVO> returnList = new ArrayList<CoBusiGeneratorQtyVO>();
		if(list != null){
			for(int i = 0; i < list.size(); i++){
				CoBusiGeneratorQtyVO qtyVo = new CoBusiGeneratorQtyVO();
				
				Object [] obj = (Object [])list.get(i);
				qtyVo.setContractid(validateString(obj[0]));
				qtyVo.setPurchaseunitid(validateString(obj[1]));
				qtyVo.setSellerunitid(validateString(obj[2]));
				qtyVo.setTimeno(validateBigDecimal(obj[3]));
				qtyVo.setStartdate(validateDate(obj[4]));
				qtyVo.setEnddate(validateDate(obj[5]));
				qtyVo.setPurchaseunit(validateString(obj[6]));
				qtyVo.setSellerunit(validateString(obj[7]));
				qtyVo.setTotalValue(validateString(obj[8]));
				qtyVo.setPeakValue(validateString(obj[9]));
				qtyVo.setTopValue(validateString(obj[10]));
				qtyVo.setFlatValue(validateString(obj[11]));
				qtyVo.setValleyValue(validateString(obj[12]));
				
				qtyVo.setGuid(Guid.create());//2014.04.10 add 向页面返回guid,实现页面可表格可编辑，要将主键返回表格中 
				returnList.add(qtyVo);
			}
		}
		
		int itemCount = hibernateDao.queryForIntWithSql("select count(1) from (" + sql.toString() + ")");
		return RestUtils.wrappQueryResult(returnList , itemCount);
	}

	/**
	 * 判断合同类型是否是大用户合同或者其子类型合同
	 * @param contractid
	 * @return
	 */
	private boolean isBigUser(String contractid){
		
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
	} 
	
	/**
	 * 通过contractid获取经济机组信息
	 * 二、大用户合同分解电量到经济机组需按如下规则调整
	 * 1、判断选择的多个合同中是否存在大用户合同（含子类型）
	 * 2、如若不含大用户合同则执行原有算法
	 * 3、如包含大用户合同，则使用新算法具体如下
     * 	a, 大用户售电方沿用原有算法寻找经济机组
     * 	b, 购电方需要通过市场成员关联经济机组表，寻找经济机组，如若没有数据，
     *  	则通过市场成员关联用电单元，在通过用电单元寻找经济机组，如若依然没有数据，则沿用原有大用户经济机组分解算法
     * 	c, 如寻找到购电方经济机组，且售电方经济机组也正确，则按照发电权经济机组分解算法执行
     *	d, 在指定经济机组时，也按找上述逻辑寻找大用户合同购电方经济机组。
	 * @param contractidArray
	 * @return
	 */
	private QueryResultObject getContractAccessoryInfo(List contractidArray, int pageIndex, int pageSize) {
		
		/*
		 * 过滤大用户类型合同
		 */
		Set<String> bigUserSet = new HashSet<String>();//存储此次分解合同中，合同类型为【大合同】及其子类型为大合同的合同id
		for(int i = 0; i < contractidArray.size(); i++){
			String contractid = contractidArray.get(i).toString();
			if(isBigUser(contractid)){
				bigUserSet.add(contractid);
			}
		}
		
		listDataForSave.clear();
		for(int i = 0 ; i< energyInfoList.size(); i++){
			CoContractenergyinfoVO energyInfo = energyInfoList.get(i);
			ArrayList<CoBusiGeneratorQtyVO> contractAccessoryList = getContractAccessoryData(energyInfo, null, bigUserSet.contains(energyInfo.getContractid()));////3、如包含大用户合同，则使用新算法//2、如若不含大用户合同则执行原有算法
			if(contractAccessoryList != null){
				for(int j = 0; j < contractAccessoryList.size(); j++){
					listDataForSave.add(contractAccessoryList.get(j));
				}
			}
		}
		//用于分页处理 
		int fromIndex = (pageIndex - 1) * pageSize;
		int toIndex = 0;
		if((pageIndex * pageSize - 1) >= listDataForSave.size()){
			toIndex = listDataForSave.size();
		} else {
			toIndex = (pageIndex * pageSize);
		}
		return RestUtils.wrappQueryResult(listDataForSave.subList(fromIndex, toIndex), listDataForSave.size());
	}
	private CoBusiGeneratorQtyVO getCoBusiGeneratorQtyVO(String contractid,String participantid ,Object [] obj,BigDecimal role){
		CoBusiGeneratorQtyVO qtyVO = new CoBusiGeneratorQtyVO();
		qtyVO.setContractid(contractid);
		qtyVO.setContractrole(role);
		qtyVO.setParticipantid(participantid);
		qtyVO.setEcounitid(validateString(obj[0]));
		if(role.compareTo(new BigDecimal("1")) == 0){
			qtyVO.setPurchaseunit(validateString(obj[1]));
		} else {
			qtyVO.setSellerunit(validateString(obj[1]));
		}
		
		return qtyVO;
	}

	/**
	 *  1、通过合同id找到合同的购电方和售电方
	 *  2、通过市场成员找到经济机组
	 *  3、分解经济机组电量
	 */
	private ArrayList<CoBusiGeneratorQtyVO> getUnitWithParticipantid(CoContractenergyinfoVO energyInfo, ContractUnitBean unitBean, boolean isBigUser){
		String contractid = energyInfo.getContractid();
		String [] param = participantInContract(contractid);
		String purchaserId =  param[0];
		String sellerId = param[1];
		String purchaserType = param[2];
		String sellerType = param[3];
		
		double purchaserCapacitTotal = 0;//购方权益容量总和
		double sellerCapacityTotal = 0;//售方权益容量总和
		
		List purchaserUnitList = hibernateDao.executeSqlQuery(ecounitSQL, new Object[]{purchaserId});//购方经济机组
		ArrayList<CoBusiGeneratorQtyVO> purchaserList = new ArrayList<CoBusiGeneratorQtyVO>();
		
		if(purchaserUnitList != null && purchaserUnitList.size() != 0){
			for(int i = 0; i <purchaserUnitList.size(); i++){
				Object [] obj = (Object [])purchaserUnitList.get(i);
				
				if(unitBean == null){//正常点击【分解】按钮逻辑
					CoBusiGeneratorQtyVO qtyVO = getCoBusiGeneratorQtyVO(contractid,purchaserId, obj, new BigDecimal("1"));
					
					if(participantType(purchaserType)){//判断用户类型是否是大用户或电网企业
						qtyVO.setEcounitcapacity("1");
						purchaserCapacitTotal = 1;
					} else {
						qtyVO.setEcounitcapacity(validateString(obj[2]));
						purchaserCapacitTotal += string2Double(validateString(obj[2]));
					}
					purchaserList.add(qtyVO);
				} else{//选择机组逻辑
					ArrayList<String> pList = unitBean.getPurchaserList();//选中的购电方经济机组
					if(pList == null || pList.size() == 0){
						CoBusiGeneratorQtyVO qtyVO = getCoBusiGeneratorQtyVO(contractid,purchaserId, obj, new BigDecimal("1"));
						
						if(participantType(purchaserType)){//判断用户类型是否是大用户或电网企业
							qtyVO.setEcounitcapacity("1");
//							if( i == 0 ){
								purchaserCapacitTotal = 1;
//							}
						} else {
							qtyVO.setEcounitcapacity(validateString(obj[2]));
							purchaserCapacitTotal += string2Double(validateString(obj[2]));
						}
						purchaserList.add(qtyVO);
					} else {
						Iterator<String> pit = pList.iterator();
						while(pit.hasNext()){
							String ecounitid = pit.next();
							if(validateString(obj[0]).equals(ecounitid)){//从查出的购电方集合中筛选。筛选出界面中选中的经济机组
								
								CoBusiGeneratorQtyVO qtyVO = getCoBusiGeneratorQtyVO(contractid,purchaserId, obj, new BigDecimal("1"));
								
								if(participantType(purchaserType)){//判断用户类型是否是大用户或电网企业
									qtyVO.setEcounitcapacity("1");
//									if( i == 0 ){
										purchaserCapacitTotal = 1;
//									}
								} else {
									qtyVO.setEcounitcapacity(validateString(obj[2]));
									purchaserCapacitTotal += string2Double(validateString(obj[2]));
								}
								purchaserList.add(qtyVO);
							}
						}
					}
				}
			}
		} else {
			if(isBigUser){
				List mktEcounitList = hibernateDao.executeSqlQuery(mktecounitSQL, new Object[]{purchaserId});//购方经济机组
				if(mktEcounitList != null && mktEcounitList.size() != 0){
					for(int i = 0; i <mktEcounitList.size(); i++){
						Object [] obj = (Object [])mktEcounitList.get(i);
						
						if(unitBean == null){//正常点击【分解】按钮逻辑
							CoBusiGeneratorQtyVO qtyVO = getCoBusiGeneratorQtyVO(contractid,purchaserId, obj, new BigDecimal("1"));
							
							if(participantType(purchaserType)){//判断用户类型是否是大用户或电网企业
								qtyVO.setEcounitcapacity("1");
//								if( i == 0 ){
									purchaserCapacitTotal = 1;
//								}
							} else {
								qtyVO.setEcounitcapacity(validateString(obj[2]));
								purchaserCapacitTotal += string2Double(validateString(obj[2]));
							}
							purchaserList.add(qtyVO);
						} else{//选择机组逻辑
							ArrayList<String> pList = unitBean.getPurchaserList();//选中的购电方经济机组
							if(pList == null || pList.size() == 0){
								CoBusiGeneratorQtyVO qtyVO = getCoBusiGeneratorQtyVO(contractid,purchaserId, obj, new BigDecimal("1"));
								
								if(participantType(purchaserType)){//判断用户类型是否是大用户或电网企业
									qtyVO.setEcounitcapacity("1");
//									if( i == 0 ){
										purchaserCapacitTotal = 1;
//									}
								} else {
									qtyVO.setEcounitcapacity(validateString(obj[2]));
									purchaserCapacitTotal += string2Double(validateString(obj[2]));
								}
								purchaserList.add(qtyVO);
							} else {
								Iterator<String> pit = pList.iterator();
								while(pit.hasNext()){
									String ecounitid = pit.next();
									if(validateString(obj[0]).equals(ecounitid)){//从查出的购电方集合中筛选。筛选出界面中选中的经济机组
										
										CoBusiGeneratorQtyVO qtyVO = getCoBusiGeneratorQtyVO(contractid,purchaserId, obj, new BigDecimal("1"));
										
										if(participantType(purchaserType)){//判断用户类型是否是大用户或电网企业
											qtyVO.setEcounitcapacity("1");
//											if( i == 0 ){
												purchaserCapacitTotal = 1;
//											}
										} else {
											qtyVO.setEcounitcapacity(validateString(obj[2]));
											purchaserCapacitTotal += string2Double(validateString(obj[2]));
										}
										purchaserList.add(qtyVO);
									}
								}
							}
						}
					}
				}
			}
		}
		List sellerUnitList = hibernateDao.executeSqlQuery(ecounitSQL, new Object[]{sellerId});//售方经济机组
		ArrayList<CoBusiGeneratorQtyVO> sellerList = new ArrayList<CoBusiGeneratorQtyVO>();
		for(int j = 0; j <sellerUnitList.size(); j++){
			Object [] obj = (Object [])sellerUnitList.get(j);
			
			if(unitBean == null){//正常点击【分解】按钮逻辑
				CoBusiGeneratorQtyVO qtyVO = getCoBusiGeneratorQtyVO(contractid, sellerId, obj, new BigDecimal("2"));
				if(participantType(sellerType)){//判断用户类型是否是大用户或电网企业
					qtyVO.setEcounitcapacity("1");
//					if(j == 0){//只第一次赋值即可
						sellerCapacityTotal = 1;
//					}
				} else {
					qtyVO.setEcounitcapacity(validateString(obj[2]));
					sellerCapacityTotal += string2Double(validateString(obj[2]));
				}
				sellerList.add(qtyVO);
			} else{//选择机组逻辑
				ArrayList<String> sList = unitBean.getSellerList();//选中的售电方经济机组
				
				if(sList == null || sList.size() == 0){
					CoBusiGeneratorQtyVO qtyVO = getCoBusiGeneratorQtyVO(contractid, sellerId, obj, new BigDecimal("2"));
					if(participantType(sellerType)){//判断用户类型是否是大用户或电网企业
						qtyVO.setEcounitcapacity("1");
//						if(j == 0){//只第一次赋值即可
							sellerCapacityTotal = 1;
//						}
					} else {
						qtyVO.setEcounitcapacity(validateString(obj[2]));
						sellerCapacityTotal += string2Double(validateString(obj[2]));
					}
					sellerList.add(qtyVO);
				} else {
					Iterator<String> sit = sList.iterator();
					while(sit.hasNext()){
						String ecounitid = sit.next();
						if(validateString(obj[0]).equals(ecounitid)){//从查出的售电方集合中筛选。筛选出界面中选中的经济机组
							CoBusiGeneratorQtyVO qtyVO = getCoBusiGeneratorQtyVO(contractid, sellerId, obj, new BigDecimal("2"));
							if(participantType(sellerType)){//判断用户类型是否是大用户或电网企业
								qtyVO.setEcounitcapacity("1");
//								if(j == 0){//只第一次赋值即可
									sellerCapacityTotal = 1;
//								}
							} else {
								qtyVO.setEcounitcapacity(validateString(obj[2]));
								sellerCapacityTotal += string2Double(validateString(obj[2]));
							}
							sellerList.add(qtyVO);
						}
					}
				}
			}
		}
		return calculateCapacity(purchaserList,sellerList,purchaserCapacitTotal,sellerCapacityTotal, energyInfo);
	}
	
	/**
	 * 
	 * @param energyInfo
	 * @param unitBean
	 * @param isBigUser 是否是大用户合同
	 * @return
	 */
	private ArrayList<CoBusiGeneratorQtyVO> getContractAccessoryData(CoContractenergyinfoVO energyInfo, ContractUnitBean unitBean, boolean isBigUser) {
		
		String contractid = energyInfo.getContractid();
		if(contractid == null){
			return null;
		}
		StringBuffer sql = new StringBuffer();
		sql.append("select acc.contractid,acc.contractrole,acc.participantid,econ.ecounitid,econ.ecounitname,econ.ecounitcapacity");
		sql.append(" from co_contractaccessory acc,ba_economicunit econ ");
		sql.append(" where contractid = ? and acc.participantid = econ.generatorid ");//经济机组与物理机组一对一，所以用acc.participantid = econ.generatorid
		sql.append(" group by acc.contractid,acc.contractrole,acc.participantid,econ.ecounitid,econ.ecounitname,econ.ecounitcapacity ");
		Object [] param = {contractid};
		List result = hibernateDao.executeSqlQuery(sql.toString(), param);
		if(result == null || result.size() == 0){
			resolveLog.info("co_contractaccessory表中即没有购电方机组也没有售电方机组");
			return getUnitWithParticipantid(energyInfo, unitBean, isBigUser);
		} else {
			ArrayList<CoBusiGeneratorQtyVO> purchaserList = new ArrayList<CoBusiGeneratorQtyVO>();
			ArrayList<CoBusiGeneratorQtyVO> sellerList = new ArrayList<CoBusiGeneratorQtyVO>();
			double purchaserCapacityTotal = 0;//购方权益容量总和
			double sellerCapacityTotal = 0;//售方权益容量总和
			
			String [] participantParam = participantInContract(contractid);
			String purchaserId =  participantParam[0] ;
			String purchaserType = participantParam[2];
			String sellerId = participantParam[1];
			String sellerType = participantParam[3];//售电方市场成员类型
			
			for(int i = 0; i < result.size(); i++){
				Object [] obj = (Object [])result.get(i);
				String contractRole = validateString(obj[1]);//经济机组角色（购方经济机组，售房经济机组）
				
				CoBusiGeneratorQtyVO qtyVO = new CoBusiGeneratorQtyVO();
				qtyVO.setContractid(validateString(obj[0]));
				qtyVO.setContractrole(validateBigDecimal(obj[1]));
				qtyVO.setParticipantid(validateString(obj[2]));
				qtyVO.setEcounitid(validateString(obj[3]));
				
				if("1".equals(contractRole)){//购方经济机组
					if(participantType(purchaserType)){//判断用户类型是否是大用户或电网企业
						qtyVO.setEcounitcapacity("1");
						purchaserCapacityTotal = 1;
					} else {
						qtyVO.setEcounitcapacity(validateString(obj[5]));
						purchaserCapacityTotal += string2Double(validateString(obj[5]));
					}
					qtyVO.setPurchaseunit(validateString(obj[4]));
					purchaserList.add(qtyVO);
				} else if ("2".equals(contractRole)){//售方经济机组
					if(participantType(sellerType)){//判断用户类型是否是大用户或电网企业
						qtyVO.setEcounitcapacity("1");
						sellerCapacityTotal = 1;
					} else {
						qtyVO.setEcounitcapacity(validateString(obj[5]));
						sellerCapacityTotal += string2Double(validateString(obj[5]));
					}
					qtyVO.setSellerunit(validateString(obj[4]));
					sellerList.add(qtyVO);
				}
			}
			if(purchaserList == null || purchaserList.size() == 0){//如果co_contractaccessory表中没有维护contract与购方经济机组的对应关系
				resolveLog.info("co_contractaccessory表中没有维护购电方机组");
				CaculateEcoUnitBean bean = getPurchaserUnit(energyInfo, sellerList, sellerCapacityTotal, isBigUser); 
				return clearUpUnit(bean,unitBean);
			} else if(sellerList == null || sellerList.size() == 0){
				resolveLog.info("co_contractaccessory表中没有维护售电方机组");
				CaculateEcoUnitBean bean = getSellerUnit(energyInfo, purchaserList, purchaserCapacityTotal);
				return clearUpUnit(bean,unitBean);
			} else {
				resolveLog.info("通过co_contractaccessory表中查询到购电方机组和售电方机组");
				
				CaculateEcoUnitBean ecoUnitBean = new CaculateEcoUnitBean();
				ecoUnitBean.setPurchaserList(purchaserList);
				ecoUnitBean.setSellerList(sellerList);
				ecoUnitBean.setPurchaserCapacitTotal(purchaserCapacityTotal);
				ecoUnitBean.setSellerCapacityTotal(sellerCapacityTotal);
				ecoUnitBean.setEnergyInfo(energyInfo);
				ecoUnitBean.setSellerIsBigUser(participantType(sellerType));
				ecoUnitBean.setPurchaserIsBigUser(participantType(purchaserType));
				return clearUpUnit(ecoUnitBean,unitBean);
			}
		}
	}
	
	/**
	 * 通过此方法，过滤没有选中的机组信息，分解电量到选中的经济机组当中
	 * @param bean
	 * @param unitBean
	 * @return
	 */
	private ArrayList<CoBusiGeneratorQtyVO> clearUpUnit(CaculateEcoUnitBean bean, ContractUnitBean unitBean){
		CoContractenergyinfoVO energyInfo = bean.getEnergyInfo();
		ArrayList<CoBusiGeneratorQtyVO> purchaserList = bean.getPurchaserList();
		ArrayList<CoBusiGeneratorQtyVO> sellerList = bean.getSellerList(); 
		double purchaserCapacityTotal = bean.getPurchaserCapacitTotal();
		double sellerCapacityTotal = bean.getSellerCapacityTotal();
		
		if(unitBean == null){//点击【分解】按钮的逻辑
			return calculateCapacity(purchaserList,sellerList,purchaserCapacityTotal,sellerCapacityTotal, energyInfo);
		} else {//选择机组，点击【确定】的逻辑
			ArrayList<CoBusiGeneratorQtyVO> clearedPurchaserList = new ArrayList<CoBusiGeneratorQtyVO>();//整理后的购方List
			ArrayList<CoBusiGeneratorQtyVO> clearedSellerList = new ArrayList<CoBusiGeneratorQtyVO>();//整理后的购方List
			
			ArrayList<String> purchaserids = unitBean.getPurchaserList();//选中的购方经济机组id
			if(purchaserids == null || purchaserids.size() == 0){//用户没有选择购方经济机组的情况
				clearedPurchaserList = purchaserList;
			} else {
				Iterator<String> pit = purchaserids.iterator();//选中的购方经济机组id
				
				boolean isBigUser = bean.isPurchaserIsBigUser();
				purchaserCapacityTotal = 0;//选择机组，点击【确定】的逻辑的时候，purchaserCapacityTotal 赋值0重新计算
				while(pit.hasNext()){
					String ecoUnitId = pit.next();
					Iterator<CoBusiGeneratorQtyVO> vos = purchaserList.iterator();//根据合同id查询出来的购方经济机组的总集合
					while(vos.hasNext()){
						CoBusiGeneratorQtyVO qtyVO = vos.next();
						if(qtyVO.getEcounitid().equals(ecoUnitId)){
							if(isBigUser){//判断用户类型是否是大用户或电网企业
								qtyVO.setEcounitcapacity("1");
//								if( i == 0 ){
									purchaserCapacityTotal = 1;
//								}
							} else {
								qtyVO.setEcounitcapacity(qtyVO.getEcounitcapacity());
								purchaserCapacityTotal += string2Double(qtyVO.getEcounitcapacity());
							}
							clearedPurchaserList.add(qtyVO);
						}
					}
				}
			}
			
			ArrayList<String> sellerIds = unitBean.getSellerList();
			if(sellerIds == null || sellerIds.size() == 0){//用户没有选择售方经济机组的情况
				clearedSellerList = sellerList;
			} else {
				Iterator<String> sit = sellerIds.iterator();//选中的购方经济机组id
				
				boolean isBigUser = bean.isSellerIsBigUser();
				sellerCapacityTotal = 0;//选择机组，点击【确定】的逻辑的时候，sellerCapacityTotal 赋值0重新计算
				while(sit.hasNext()){
					String ecoUnitId = sit.next();
					Iterator<CoBusiGeneratorQtyVO> vos = sellerList.iterator();//根据合同id查询出来的购方经济机组的总集合
					while(vos.hasNext()){
						CoBusiGeneratorQtyVO qtyVO = vos.next();
						if(qtyVO.getEcounitid().equals(ecoUnitId)){
							if(isBigUser){//判断用户类型是否是大用户或电网企业
								qtyVO.setEcounitcapacity("1");
//								if( i == 0 ){
									sellerCapacityTotal = 1;
//								}
							} else {
								qtyVO.setEcounitcapacity(qtyVO.getEcounitcapacity());
								sellerCapacityTotal += string2Double(qtyVO.getEcounitcapacity());
							}
							clearedSellerList.add(qtyVO);
						}
					}
				}
			}
			return calculateCapacity(clearedPurchaserList,clearedSellerList,purchaserCapacityTotal,sellerCapacityTotal, energyInfo);
		}
	}
	
	private CaculateEcoUnitBean getSellerUnit(CoContractenergyinfoVO energyInfo,
			ArrayList<CoBusiGeneratorQtyVO> purchaserList,
			double purchaserCapacityTotal) {
		String contractid = energyInfo.getContractid();
		String [] param = participantInContract(contractid);
		String sellerId = param[1];
		String sellerType = param[3];//售电方市场成员类型
		
		double sellerCapacityTotal = 0;//售方权益容量总和
		
		List sellerUnitList = hibernateDao.executeSqlQuery(ecounitSQL, new Object[]{sellerId});//售方经济机组
		ArrayList<CoBusiGeneratorQtyVO> sellerList = new ArrayList<CoBusiGeneratorQtyVO>();
		for(int j = 0; j <sellerUnitList.size(); j++){
			Object [] obj = (Object [])sellerUnitList.get(j);
			CoBusiGeneratorQtyVO qtyVO = new CoBusiGeneratorQtyVO();
			qtyVO.setContractid(contractid);
			qtyVO.setContractrole(new BigDecimal("2"));
			qtyVO.setParticipantid(sellerId);
			qtyVO.setEcounitid(validateString(obj[0]));
			qtyVO.setSellerunit(validateString(obj[1]));
			
			if(participantType(sellerType)){//判断用户类型是否是大用户或电网企业
				qtyVO.setEcounitcapacity("1");
//				if(j == 0){//只第一次赋值即可
					sellerCapacityTotal = 1;
//				}
			} else {
				qtyVO.setEcounitcapacity(validateString(obj[2]));
				sellerCapacityTotal += string2Double(validateString(obj[2]));
			}
			
			sellerList.add(qtyVO);
		}
		
		CaculateEcoUnitBean unitBean = new CaculateEcoUnitBean();
		unitBean.setPurchaserList(purchaserList);
		unitBean.setSellerList(sellerList);
		unitBean.setPurchaserCapacitTotal(purchaserCapacityTotal);
		unitBean.setSellerCapacityTotal(sellerCapacityTotal);
		unitBean.setEnergyInfo(energyInfo);
		unitBean.setSellerIsBigUser(participantType(sellerType));
		
		return unitBean;
	}
	
	//通过合同找到购电方和售电方的市场成员id
//	String purchaserAndSellerSQl = "select base.purchaser, base.seller, participant1.participanttype purchasertype, participant2.participanttype sellertype" +
//			" from co_contractbaseinfo base, ba_marketparticipant participant1, ba_marketparticipant participant2" +
//			" where participant1.participantid = base.purchaser" +
//			" and participant2.participantid = base.seller" +
//			" and contractid = ?";
	
	String purchaserAndSellerSQl = "select base.purchaser, base.seller, participant1.participanttype purchasertype, participant2.participanttype sellertype " +
			"from co_contractbaseinfo  base " +
			"left join ba_marketparticipant participant1 on participant1.participantid = base.purchaser " +
			"left join ba_marketparticipant participant2 on participant2.participantid = base.seller " +
			"where base.contractid = ? ";
	
	//通过合同数据中关联市场成员，查找经济机组
//	String ecounitSQL = " select econ.ecounitid, econ.ecounitname, econ.ecounitcapacity  " +
//			" from ba_economicunit econ" + 
//			" where  econ.participantid =  ? ";
	
	//通过合同数据中关联市场成员，查找经济机组和物理机组
//	String ecounitSQL = " select econ.ecounitid, econ.ecounitname, econ.ecounitcapacity, generator.generatorname, generator.generatorid " +
//			"from ba_generator generator,ba_marketparticipant market, ba_economicunit econ " + 
//			"where market.participantid = ? and generator.plantid = market.participantid and generator.generatorid = econ.generatorid";
	//通过合同数据中关联市场成员，查找经济机组和物理机组
	String ecounitSQL = "select econ.ecounitid, econ.ecounitname, econ.ecounitcapacity, generator.generatorname, generator.generatorid " +
	" from (select * from ba_marketparticipant market where market.participantid = ?) market, "+
	" ba_generator         generator, " +
	" ba_economicunit      econ " +
	" where econ.participantid = market.participantid " +
	" and generator.generatorid(+) = econ.generatorid ";
	
	String mktecounitSQL = "select econ.ecounitid, econ.ecounitname, econ.ecounitcapacity " +
			"from ba_mktaadminload mkt, ba_marketparticipant market, ba_economicunit econ " +
			"where market.participantid = ? and mkt.participant_id = market.participantid and mkt.load_id = econ.generatorid ";
	
	/**
	 * 查询合同购电方id、售电方id、购电方市场成员类型、售电方市场成员类型
	 * @param contractid
	 * @return
	 */
	private String [] participantInContract(String contractid){
		String [] param = new String[4];
		
		List purchaserAndSeller = hibernateDao.executeSqlQuery(purchaserAndSellerSQl, new Object[]{contractid});
		String purchaserId = "";
		String sellerId = "";
		String purchaserType = null;//购电方市场成员类型
		String sellerType = null;	//售电方市场成员类型
		if(purchaserAndSeller != null && purchaserAndSeller.size() != 0){
			Object [] params = (Object [])purchaserAndSeller.get(0);
			purchaserId = validateString(params[0]);
			sellerId = validateString(params[1]);
			purchaserType = validateString(params[2]);
			sellerType = validateString(params[3]);
			
			param[0] = purchaserId;
			param[1] = sellerId;
			param[2] = purchaserType;
			param[3] = sellerType;
		}
		return param;
	}
	
	private CaculateEcoUnitBean getPurchaserUnit(
			CoContractenergyinfoVO energyInfo, ArrayList<CoBusiGeneratorQtyVO> sellerList, double sellerCapacityTotal, boolean isBigUser) {
		String contractid = energyInfo.getContractid();
		String [] param = participantInContract(contractid);
		String purchaserId =  param[0] ;
		String purchaserType = param[2];
		
		double purchaserCapacityTotal = 0;//购方权益容量总和
		
		List purchaserUnitList = hibernateDao.executeSqlQuery(ecounitSQL, new Object[]{purchaserId});//购方经济机组
		ArrayList<CoBusiGeneratorQtyVO> purchaserList = new ArrayList<CoBusiGeneratorQtyVO>();
		if(purchaserUnitList != null && purchaserUnitList.size() != 0){//购电方需要通过市场成员关联经济机组表，寻找经济机组
			for(int i = 0; i <purchaserUnitList.size(); i++){
				Object [] obj = (Object [])purchaserUnitList.get(i);
				CoBusiGeneratorQtyVO qtyVO = new CoBusiGeneratorQtyVO();
				qtyVO.setContractid(contractid);
				qtyVO.setContractrole(new BigDecimal("1"));
				qtyVO.setParticipantid(purchaserId);
				qtyVO.setEcounitid(validateString(obj[0]));
				qtyVO.setPurchaseunit(validateString(obj[1]));
				
				if(participantType(purchaserType)){//判断用户类型是否是大用户或电网企业
					qtyVO.setEcounitcapacity("1");
//					if( i == 0 ){
						purchaserCapacityTotal = 1;
//					}
				} else {
					qtyVO.setEcounitcapacity(validateString(obj[2]));
					purchaserCapacityTotal += string2Double(validateString(obj[2]));
				}
				purchaserList.add(qtyVO);
			}
		} else {//通过市场成员关联用电单元，在通过用电单元寻找经济机组
			if(isBigUser){
				List mktEcounitList = hibernateDao.executeSqlQuery(mktecounitSQL, new Object[]{purchaserId});//购方经济机组
				if(mktEcounitList != null && mktEcounitList.size() != 0){
					for(int i = 0; i <mktEcounitList.size(); i++){
						Object [] obj = (Object [])mktEcounitList.get(i);
						CoBusiGeneratorQtyVO qtyVO = new CoBusiGeneratorQtyVO();
						qtyVO.setContractid(contractid);
						qtyVO.setContractrole(new BigDecimal("1"));
						qtyVO.setParticipantid(purchaserId);
						qtyVO.setEcounitid(validateString(obj[0]));
						qtyVO.setPurchaseunit(validateString(obj[1]));
						
						if(participantType(purchaserType)){//判断用户类型是否是大用户或电网企业
							qtyVO.setEcounitcapacity("1");
//							if( i == 0 ){
								purchaserCapacityTotal = 1;
//							}
						} else {
							qtyVO.setEcounitcapacity(validateString(obj[2]));
							purchaserCapacityTotal += string2Double(validateString(obj[2]));
						}
						purchaserList.add(qtyVO);
					}
				}
			}
		}
		
		CaculateEcoUnitBean unitBean = new CaculateEcoUnitBean();
		unitBean.setPurchaserList(purchaserList);
		unitBean.setSellerList(sellerList);
		unitBean.setPurchaserCapacitTotal(purchaserCapacityTotal);
		unitBean.setSellerCapacityTotal(sellerCapacityTotal);
		unitBean.setEnergyInfo(energyInfo);
		unitBean.setPurchaserIsBigUser(participantType(purchaserType));
		
		return unitBean;
	}
	
	/**
	 * //判断用户类型是否是大用户或电网企业
	 * @param participantType
	 * @return
	 */
	private boolean participantType(String participantType){
		if(participantType != null && ("0".equals(participantType) || "1".equals(participantType))){
			return true;
		} else {
			return false;
		}
	}
	
	
	/**
	 * 通过权益容量 计算合同 尖峰平谷总 电量值
	 * 1、获取购方权益容量总和	A
	 * 2、获取售方权益容量总和	B
	 * 3、通过公式计算电量
	 * 		A1/A  * B1/B * 总电量（尖总电量，峰总电量……）
	 * 
	 * 		A1:购方其中一个经济机组权益容量
	 * 		B1:售方其中一个经济机组权益容量
	 * 
	 * @param purchaserList
	 * @param sellerList
	 * @param energyInfo 
	 */
	private ArrayList<CoBusiGeneratorQtyVO> calculateCapacity(List<CoBusiGeneratorQtyVO> purchaserList,List<CoBusiGeneratorQtyVO> sellerList,
			double purchaserCapacityTotal,double sellerCapacityTotal, CoContractenergyinfoVO energyInfoVo) {
		ArrayList<CoBusiGeneratorQtyVO> result = new ArrayList<CoBusiGeneratorQtyVO>();
		
		Double totalValue = 0d;
		Double peakValue = 0d;
		Double topValue = 0d;
		Double flatValue = 0d;
		Double valleyValue = 0d;
		
//		for(int k = 0 ; k < energyInfoList.size(); k++){ 
//			CoContractenergyinfoVO energyInfoVo = energyInfoList.get(k);
			Date startDate = energyInfoVo.getStartdate();
			Date endDate = energyInfoVo.getEnddate();
			BigDecimal timeNo = energyInfoVo.getTimeno();
			
			totalValue = change2Double(energyInfoVo.getTotalpower());//某一时间段 总电量 
			peakValue = change2Double(energyInfoVo.getPeakpower());//某一时间段 尖电量
			topValue = change2Double(energyInfoVo.getToppower());//某一时间段 峰电量
			flatValue = change2Double(energyInfoVo.getFlatpower());//某一时间段 平电量
			valleyValue = change2Double(energyInfoVo.getValleypower());//某一时间段 谷电量

			if((purchaserList != null && purchaserList.size() != 0) && (sellerList != null && sellerList.size() !=0)){
				Double oneToNminus1TotalPower = 0d;//计算 1 到 n-1 总电量之和
				Double oneToNminus1PeakPower = 0d;//计算 1 到 n-1 尖电量之和
				Double oneToNminus1TopPower = 0d;
				Double oneToNminus1FlatPower = 0d;
				Double oneToNminus1ValleyPower = 0d;
				for(int i = 0 ; i< purchaserList.size(); i++){//循环购方List
					CoBusiGeneratorQtyVO purchaserVo = purchaserList.get(i);
					double purchaserCapacityValue = string2Double(purchaserVo.getEcounitcapacity());//每条记录的权益容量
					
					for(int j = 0; j < sellerList.size(); j++){//循环 售方List，通过计算公式计算结果
						CoBusiGeneratorQtyVO vo = new CoBusiGeneratorQtyVO();//与前台对应的VO
						CoBusiGeneratorQtyVO sellerVo = sellerList.get(j);
						double sellerCapacityValue = string2Double(sellerVo.getEcounitcapacity());
						
						Double total = formula(purchaserCapacityValue, purchaserCapacityTotal, sellerCapacityValue, sellerCapacityTotal, totalValue);//计算过后的总电量
						Double peak = formula(purchaserCapacityValue, purchaserCapacityTotal, sellerCapacityValue, sellerCapacityTotal, peakValue);//计算过后的尖电量
						Double top = formula(purchaserCapacityValue, purchaserCapacityTotal, sellerCapacityValue, sellerCapacityTotal, topValue);//计算过后的峰电量
						Double flat = formula(purchaserCapacityValue, purchaserCapacityTotal, sellerCapacityValue, sellerCapacityTotal, flatValue);//计算过后的平电量
						Double valley = formula(purchaserCapacityValue, purchaserCapacityTotal, sellerCapacityValue, sellerCapacityTotal, valleyValue);//计算过后的谷电量
						
						if((i + 1) == purchaserList.size() && (j + 1) ==sellerList.size()){
							vo.setTotalValue(total == null ? "" : String.valueOf(getRound(totalValue - oneToNminus1TotalPower)));
							vo.setPeakValue(peak == null ? "" : String.valueOf(getRound(peakValue - oneToNminus1PeakPower)));
							vo.setTopValue(top == null ? "" : String.valueOf(getRound(topValue - oneToNminus1TopPower)));
							vo.setFlatValue(flat == null ? "" : String.valueOf(getRound(flatValue - oneToNminus1FlatPower)));
							vo.setValleyValue(valley == null ? "" : String.valueOf(getRound(valleyValue - oneToNminus1ValleyPower)));
						} else {
							vo.setTotalValue(total == null ? "" : String.valueOf(total));
							vo.setPeakValue(peak == null ? "" : String.valueOf(peak));
							vo.setTopValue(top == null ? "" : String.valueOf(top));
							vo.setFlatValue(flat == null ? "" : String.valueOf(flat));
							vo.setValleyValue(valley == null ? "" : String.valueOf(valley));
							oneToNminus1TotalPower += (total == null ? 0 : total);
							oneToNminus1PeakPower += (peak == null ? 0 : peak);
							oneToNminus1TopPower += (top == null ? 0 : top);
							oneToNminus1FlatPower += (flat == null ? 0 : flat);
							oneToNminus1ValleyPower += (valley == null ? 0 : valley);
						}
						
						vo.setPurchaseunit(purchaserVo.getPurchaseunit());
						vo.setSellerunit(sellerVo.getSellerunit());
						vo.setPurchaseunitid(purchaserVo.getEcounitid());//经济机组id
						vo.setSellerunitid(sellerVo.getEcounitid());//经济机组id
						vo.setContractid(sellerVo.getContractid());
						vo.setStartdate(startDate);//开始时间
						vo.setEnddate(endDate);//结束时间
						vo.setTimeno(timeNo);//时间段序号
						vo.setGuid(Guid.create());
						result.add(vo);
					}
				}
			} 
//		}
		return result;
	}
	
	/**
	 * 计算公式方法
	 * A1/A  * B1/B * 总电量（尖总电量，峰总电量……）
	 * @param 购方一个经济机组权益容量，没有购方经济机组参数为1d
	 * @param 购方经济机组权益容量之和，没有购方经济机组参数为1d
	 * @param 售方一个经济机组权益容量
	 * @param 售方经济机组权益容量之和
	 * @param 总电量（尖总电量，峰总电量……）
	 * @return
	 */
	private Double formula(Double pNumerator, Double pDenominator,Double sNumerator, Double sDenominator, Double total){
		if(total == null){
			return null;
		}
		if(pDenominator != 0 && sDenominator!=0){//权益总量之和作为分母，不能为0
			Double power = (pNumerator/pDenominator) * (sNumerator/sDenominator) * total;//计算后电量值
			return getRound(power);
		} else {
			return null;
		}
	}
	
	/**
	 * 截取小数点后四位，跟数据库字段类型对应
	 * @return
	 */
	private Double getRound(Double value){
		java.text.DecimalFormat df =new java.text.DecimalFormat("#.0000");  
		return Double.valueOf(df.format(value));
	}
	
	private Double change2Double(String param){
		if(param != null && !"".equals(param) && param.length() != 0){
			return Double.parseDouble(param);
		} else {
			return null;
		}
	}
	
	/**
	 * 选择
	 */
	public QueryResultObject getUnitGrid(RequestCondition params) {
		ArrayList totalResult = new ArrayList();
		List<QueryFilter> filterList = params.getQueryFilter();
		
		String unitid = null;
		String ecounitid = null;
		
		for(int i = 0; i < filterList.size(); i++){
			QueryFilter filter = filterList.get(i);
			if("contractids".equals(filter.getFieldName())){
				String contractids = filter.getValue().toString();
				String str = contractids.replace("\"", "");
				String [] contractidArray = str.substring(1, str.length() - 1).split(",");

				if(contractidArray != null && contractidArray.length != 0){
					for(int j = 0 ; j< contractidArray.length; j++){
						String contractid = contractidArray[j];
						boolean isBigUser = isBigUser(contractid);
						ArrayList<CoBusiGeneratorQtyVO> contractAccessoryList = getContractUnit(contractidArray[j], isBigUser);//获取每个contractid的分解结果
						if(contractAccessoryList != null){
							totalResult.addAll(contractAccessoryList);
						}
					}
				}
			} else if ("unit".equals(filter.getFieldName())){
				unitid = (String) filter.getValue();
			} else if ("ecounit".equals(filter.getFieldName())){
				ecounitid = (String) filter.getValue();
			}
		}
		
		if(unitid != null && !"null".equals(unitid)){
			Iterator<CoBusiGeneratorQtyVO> it = totalResult.iterator();
			while(it.hasNext()){
				CoBusiGeneratorQtyVO vtyVO = it.next();
				if(!unitid.equals(vtyVO.getUnitid())){
					it.remove();
				}
			}
		}
		
		if(ecounitid != null && !"null".equals(ecounitid)){
			Iterator<CoBusiGeneratorQtyVO> it = totalResult.iterator();
			while(it.hasNext()){
				CoBusiGeneratorQtyVO vtyVO = it.next();
				if(!ecounitid.equals(vtyVO.getEcounitid())){
					it.remove();
				}
			}
		}
		
		int pageIndex  = Integer.valueOf(params.getPageIndex());
		int pageSize  = Integer.valueOf(params.getPageSize());
		//用于分页处理 
		int fromIndex = (pageIndex - 1) * pageSize;
		int toIndex = 0;
		if((pageIndex * pageSize - 1) >= totalResult.size()){
			toIndex = totalResult.size();
		} else {
			toIndex = (pageIndex * pageSize);
		}
		
		return RestUtils.wrappQueryResult(totalResult.subList(fromIndex, toIndex), totalResult.size());
	}
	
	private ArrayList<CoBusiGeneratorQtyVO> getContractUnit(String contractid, boolean isBigUser) {
		if(contractid == null){
			return null;
		}
		StringBuffer sql = new StringBuffer();
		
		sql.append("select acc.contractid,acc.contractrole,acc.participantid,econ.ecounitid,econ.ecounitname,econ.ecounitcapacity, ");
		sql.append(" generator.generatorname, generator.generatorid ");
		sql.append("from co_contractaccessory acc, ba_generator generator,ba_economicunit econ ");
		sql.append("where acc.participantid = econ.generatorid and econ.generatorid = generator.generatorid ");//经济机组与物理机组一对一，所以用acc.participantid = econ.generatorid
		sql.append("and contractid = ? ");
		sql.append("group by acc.contractid,acc.contractrole,acc.participantid,econ.ecounitid,econ.ecounitname,econ.ecounitcapacity, generator.generatorname, generator.generatorid");
		
		Object [] param = {contractid};
		List result = hibernateDao.executeSqlQuery(sql.toString(), param);
		if(result == null || result.size() == 0){
			resolveLog.info("co_contractaccessory表中即没有购电方机组也没有售电方机组-");
			return getUnitWithParticipantid(contractid, isBigUser);
		} else {
			ArrayList<CoBusiGeneratorQtyVO> purchaserList = new ArrayList<CoBusiGeneratorQtyVO>();
			ArrayList<CoBusiGeneratorQtyVO> sellerList = new ArrayList<CoBusiGeneratorQtyVO>();
			
			for(int i = 0; i < result.size(); i++){
				Object [] obj = (Object [])result.get(i);
				String contractRole = validateString(obj[1]);//经济机组角色（购方经济机组，售房经济机组）
				
				CoBusiGeneratorQtyVO qtyVO = new CoBusiGeneratorQtyVO();
				qtyVO.setContractid(validateString(obj[0]));
				qtyVO.setContractrole(validateBigDecimal(obj[1]));
				qtyVO.setParticipantid(validateString(obj[2]));
				qtyVO.setEcounitid(validateString(obj[3]));
				qtyVO.setEcounitcapacity(validateString(obj[5]));
				qtyVO.setEcounit(validateString(obj[4]));	//经济机组
				qtyVO.setUnit(validateString(obj[6]));		//物理机组
				qtyVO.setUnitid(validateString(obj[7]));	//物理机组id

				if("1".equals(contractRole)){//购方经济机组
					qtyVO.setPurchaseunit(validateString(obj[4]));
					purchaserList.add(qtyVO);
				} else if ("2".equals(contractRole)){//售方经济机组
					qtyVO.setSellerunit(validateString(obj[4]));
					sellerList.add(qtyVO);
				}
			}
			if(purchaserList == null || purchaserList.size() == 0){//如果co_contractaccessory表中没有维护contract与购方经济机组的对应关系
				resolveLog.info("co_contractaccessory表中没有维护购电方机组-");
				return getPurchaserUnit(contractid, sellerList, isBigUser);
			} else if(sellerList == null || sellerList.size() == 0){
				resolveLog.info("co_contractaccessory表中没有维护售电方机组-");
				return getSellerUnit(contractid, purchaserList);
			} else {
				resolveLog.info("通过co_contractaccessory表中查询到购电方机组和售电方机组-");
				ArrayList<CoBusiGeneratorQtyVO> allList = new ArrayList<CoBusiGeneratorQtyVO>();
				allList.addAll(sellerList);
				allList.addAll(purchaserList);
				return allList;
			}
		}
	}
	
	/** 选择机组功能
	 *  1、通过合同id找到合同的购电方和售电方
	 *  2、通过市场成员找到经济机组
	 *  3、分解经济机组电量
	 */
	private ArrayList<CoBusiGeneratorQtyVO> getUnitWithParticipantid(String contractid,boolean isBigUser){
		String [] param = participantInContract(contractid);
		String purchaserId =  param[0];
		String sellerId = param[1];
		 //TODO  select econ.ecounitid, econ.ecounitname, econ.ecounitcapacity, generator.generatorname, generator.generatorid 
		List purchaserUnitList = hibernateDao.executeSqlQuery(ecounitSQL, new Object[]{purchaserId});//购方经济机组和物理机组
		ArrayList<CoBusiGeneratorQtyVO> purchaserList = new ArrayList<CoBusiGeneratorQtyVO>();
		if(purchaserUnitList != null && purchaserUnitList.size() != 0){
			for(int i = 0; i <purchaserUnitList.size(); i++){
				Object [] obj = (Object [])purchaserUnitList.get(i);
				CoBusiGeneratorQtyVO qtyVO = new CoBusiGeneratorQtyVO();
				qtyVO.setContractid(contractid);
				qtyVO.setContractrole(new BigDecimal("1"));
				qtyVO.setParticipantid(purchaserId);
				qtyVO.setEcounitid(validateString(obj[0])); //经济机组id
				qtyVO.setEcounit(validateString(obj[1]));	//经济机组
				qtyVO.setEcounitcapacity(validateString(obj[2]));//经济机组权益容量
				qtyVO.setUnit(validateString(obj[3]));		//物理机组
				qtyVO.setUnitid(validateString(obj[4]));	//物理机组id
				
				purchaserList.add(qtyVO);
			}
		} else {
			if(isBigUser){
				List mktEcounitList = hibernateDao.executeSqlQuery(mktecounitSQL, new Object[]{purchaserId});//购方经济机组
				if(mktEcounitList != null && mktEcounitList.size() != 0){
					for(int i = 0; i <mktEcounitList.size(); i++){
						Object [] obj = (Object [])mktEcounitList.get(i);
						CoBusiGeneratorQtyVO qtyVO = new CoBusiGeneratorQtyVO();
						qtyVO.setContractid(contractid);
						qtyVO.setContractrole(new BigDecimal("1"));
						qtyVO.setParticipantid(purchaserId);
						qtyVO.setEcounitid(validateString(obj[0])); //经济机组id
						qtyVO.setEcounit(validateString(obj[1]));	//经济机组
						qtyVO.setEcounitcapacity(validateString(obj[2]));//经济机组权益容量
						
						purchaserList.add(qtyVO);
					}
				}
			}
		}
	
		List sellerUnitList = hibernateDao.executeSqlQuery(ecounitSQL, new Object[]{sellerId});//售方经济机组
		ArrayList<CoBusiGeneratorQtyVO> sellerList = new ArrayList<CoBusiGeneratorQtyVO>();
		for(int j = 0; j <sellerUnitList.size(); j++){
			Object [] obj = (Object [])sellerUnitList.get(j);
			CoBusiGeneratorQtyVO qtyVO = new CoBusiGeneratorQtyVO();
			qtyVO.setContractid(contractid);
			qtyVO.setContractrole(new BigDecimal("2"));
			qtyVO.setParticipantid(sellerId);
			qtyVO.setEcounitid(validateString(obj[0])); //经济机组id
			qtyVO.setEcounit(validateString(obj[1]));	//经济机组
			qtyVO.setEcounitcapacity(validateString(obj[2]));//经济机组权益容量
			qtyVO.setUnit(validateString(obj[3]));		//物理机组
			qtyVO.setUnitid(validateString(obj[4]));	//物理机组id
			sellerList.add(qtyVO);
		}
		ArrayList<CoBusiGeneratorQtyVO> allList = new ArrayList<CoBusiGeneratorQtyVO>();
		allList.addAll(purchaserList);
		allList.addAll(sellerList);
		
		return allList;
	}
	
	
	private ArrayList<CoBusiGeneratorQtyVO> getPurchaserUnit(
			String contractid, ArrayList<CoBusiGeneratorQtyVO> sellerList, boolean isBigUser) {
		String [] param = participantInContract(contractid);
		String purchaserId =  param[0] ;
		
		List purchaserUnitList = hibernateDao.executeSqlQuery(ecounitSQL, new Object[]{purchaserId});//购方经济机组
		ArrayList<CoBusiGeneratorQtyVO> purchaserList = new ArrayList<CoBusiGeneratorQtyVO>();
		if(purchaserUnitList != null && purchaserUnitList.size() != 0){
			for(int i = 0; i <purchaserUnitList.size(); i++){
				Object [] obj = (Object [])purchaserUnitList.get(i);
				CoBusiGeneratorQtyVO qtyVO = new CoBusiGeneratorQtyVO();
				qtyVO.setContractid(contractid);
				qtyVO.setContractrole(new BigDecimal("1"));
				qtyVO.setParticipantid(purchaserId);
				qtyVO.setEcounitid(validateString(obj[0])); //经济机组id
				qtyVO.setEcounit(validateString(obj[1]));	//经济机组
				qtyVO.setEcounitcapacity(validateString(obj[2]));//经济机组权益容量
				qtyVO.setUnit(validateString(obj[3]));		//物理机组
				qtyVO.setUnitid(validateString(obj[4]));	//物理机组id
				purchaserList.add(qtyVO);
			}
		} else {
			if(isBigUser){
				List mktEcounitList = hibernateDao.executeSqlQuery(mktecounitSQL, new Object[]{purchaserId});//购方经济机组
				if(mktEcounitList != null && mktEcounitList.size() != 0){
					for(int i = 0; i <mktEcounitList.size(); i++){
						Object [] obj = (Object [])mktEcounitList.get(i);
						CoBusiGeneratorQtyVO qtyVO = new CoBusiGeneratorQtyVO();
						qtyVO.setContractid(contractid);
						qtyVO.setContractrole(new BigDecimal("1"));
						qtyVO.setParticipantid(purchaserId);
						qtyVO.setEcounitid(validateString(obj[0])); //经济机组id
						qtyVO.setEcounit(validateString(obj[1]));	//经济机组
						qtyVO.setEcounitcapacity(validateString(obj[2]));//经济机组权益容量
						
						purchaserList.add(qtyVO);
					}
				}
			}
		}
		
		ArrayList<CoBusiGeneratorQtyVO> allList = new ArrayList<CoBusiGeneratorQtyVO>();
		allList.addAll(sellerList);
		allList.addAll(purchaserList);
		return allList;
	}
	
	private ArrayList<CoBusiGeneratorQtyVO> getSellerUnit(String contractid,
			ArrayList<CoBusiGeneratorQtyVO> purchaserList) {
		String [] param = participantInContract(contractid);
		String sellerId = param[1];
		String sellerType = param[3];//售电方市场成员类型
		
		double sellerCapacityTotal = 0;//售方权益容量总和
		
		List sellerUnitList = hibernateDao.executeSqlQuery(ecounitSQL, new Object[]{sellerId});//售方经济机组
		ArrayList<CoBusiGeneratorQtyVO> sellerList = new ArrayList<CoBusiGeneratorQtyVO>();
		for(int j = 0; j <sellerUnitList.size(); j++){
			Object [] obj = (Object [])sellerUnitList.get(j);
			CoBusiGeneratorQtyVO qtyVO = new CoBusiGeneratorQtyVO();
			qtyVO.setContractid(contractid);
			qtyVO.setContractrole(new BigDecimal("2"));
			qtyVO.setParticipantid(sellerId);
			qtyVO.setEcounitid(validateString(obj[0])); //经济机组id
			qtyVO.setEcounit(validateString(obj[1]));	//经济机组
			qtyVO.setEcounitcapacity(validateString(obj[2]));//经济机组权益容量
			qtyVO.setUnit(validateString(obj[3]));		//物理机组
			qtyVO.setUnitid(validateString(obj[4]));	//物理机组id
			if(participantType(sellerType)){//大用户和电网企业权益容量按1计算
				qtyVO.setEcounitcapacity("1");
//				if(j == 0){//只第一次赋值即可
					sellerCapacityTotal = 1;
//				}
			} else {
				qtyVO.setEcounitcapacity(validateString(obj[2]));
				sellerCapacityTotal += string2Double(validateString(obj[2]));
			}
			
			sellerList.add(qtyVO);
		}
		ArrayList<CoBusiGeneratorQtyVO> allList = new ArrayList<CoBusiGeneratorQtyVO>();
		allList.addAll(sellerList);
		allList.addAll(purchaserList);
		return allList;
	}

	public List getUnitList(String params, String isUnit) {
		ArrayList totalResult = new ArrayList();
		List<Map<String, String>> namelist = new ArrayList<Map<String, String>>();
		Map map = null;
		try {
			map = JsonUtils.getObjectMapper().readValue(params, Map.class);
			ArrayList contractidList = (ArrayList) (map.get("contractids")==null?"":map.get("contractids"));//@获取合同id
			if(contractidList != null){
				for(int i = 0 ; i< contractidList.size(); i++){
					String contractid = contractidList.get(i).toString();
					boolean isBigUser = isBigUser(contractid);
					ArrayList<CoBusiGeneratorQtyVO> contractAccessoryList = getContractUnit(contractid, isBigUser);//获取每个contractid的分解结果
					if(contractAccessoryList != null){
						totalResult.addAll(contractAccessoryList);
					}
				}
			}
			
			if(totalResult != null && totalResult.size() != 0){
				Iterator<CoBusiGeneratorQtyVO> it = totalResult.iterator();
				while(it.hasNext()){
					Map<String, String> unitMap = new HashMap<String, String>();
					CoBusiGeneratorQtyVO vo = it.next();
					if(Boolean.parseBoolean(isUnit)){
						if(vo.getUnit() != null && vo.getUnit().length() != 0){
							unitMap.put("name", vo.getUnit());//封装下拉列表数据
							unitMap.put("value", vo.getUnitid());
							namelist.add(unitMap);
						}
					} else {
						if(vo.getEcounit() != null && vo.getEcounit().length() != 0){
							unitMap.put("name", vo.getEcounit());//封装下拉列表数据
							unitMap.put("value", vo.getEcounitid());
							namelist.add(unitMap);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return namelist;
	}
}
