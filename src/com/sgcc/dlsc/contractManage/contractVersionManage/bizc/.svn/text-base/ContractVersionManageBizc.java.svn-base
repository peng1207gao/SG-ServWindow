package com.sgcc.dlsc.contractManage.contractVersionManage.bizc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.sgcc.dlsc.commons.bean.VIParamBean;
import com.sgcc.dlsc.commons.bean.VIReturnBean;
import com.sgcc.dlsc.commons.bizc.IComm_VerticalIntegration_Bizc;
import com.sgcc.dlsc.commons.util.ComUtils;
import com.sgcc.dlsc.commons.util.DateUtil;
import com.sgcc.dlsc.commons.util.ToolsSys;
import com.sgcc.dlsc.commons.util.UserInfoUtil;
import com.sgcc.dlsc.constants.DataConstants;
import com.sgcc.dlsc.contractManage.IdeaConManage.vo.CoContractbaseinfoTransfer;
import com.sgcc.dlsc.contractManage.IdeaConManage.vo.CoContractbaseinfoVO;
import com.sgcc.dlsc.entity.po.CoContractbaseinfo;
import com.sgcc.dlsc.entity.po.CoContractchangerecordinfo;
import com.sgcc.isc.core.orm.identity.User;
import com.sgcc.isc.service.adapter.utils.JsonUtil;
import com.sgcc.uap.bizc.sysbizc.datadictionary.IDataDictionaryBizC;
import com.sgcc.uap.integrate.isc.wrapper.factory.AdapterWrapperFactory;
import com.sgcc.uap.persistence.IHibernateDao;
import com.sgcc.uap.rest.support.DicItems;
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
 * @author juguanghui 
 * 
 */
public class ContractVersionManageBizc implements IContractVersionManageBizc{

	@Autowired
	private IHibernateDao hibernateDao;
	
	@Autowired
	private IDataDictionaryBizC dataDictionaryBizC;
	
	@Autowired
	private IComm_VerticalIntegration_Bizc commVerticalIntegrationBizc;
	
	/**
	 * 根据条件查询合同信息
	 * @description 方法描述
	 * @param pageNo
	 * @param pageSize
	 * @param map
	 * @return
	 * @author mengke 
	 * @date 2013-4-16
	 */
	public String getContractListByMCondition(Map<String, String> map, String loginMarketId) {
		String marketid = map.get("marketid");//marketid
		String contracttype =  map.get("contracttype");//合同类型
		String timetype = map.get("timetype");//时间类型
		String startDate = map.get("contractstartdate");//开始时间
		String endDate = map.get("contractenddate");//截止时间
		String contractcyc = map.get("contractcyc");//合同周期
		String prepcontractflag = map.get("prepcontractflag");//合同状态
		String sequence = map.get("sequenceid");//合同序列
		String purchaser = map.get("purchaser");//购电方
		String seller = map.get("seller");//售点方
		
		//合同类型改成多选，故先将合同类型拼成sql可以使用的字符串
		if(ToolsSys.isnull(contracttype)){
			String[] contracttypes = contracttype.split(",");
			for(int i=0;i<contracttypes.length;i++){
				if(i==0){
					contracttype = "'"+contracttypes[i]+"'";
				}else {
					contracttype = contracttype + ",'"+contracttypes[i]+"'";
				}
			}
		}

		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT CC.CONTRACTID,CC.PAPERCONTRACTCODE,CC.CONTRACTNAME,CCT.TYPENAME,BM.PARTICIPANTNAME AS PP, ");
		sql.append(" BMM.PARTICIPANTNAME AS QQ ,CC.CONTRACTSTARTDATE,CC.CONTRACTENDDATE,CC.CONTRACTQTY,TT.NAME AS AA ,TTT.NAME AS BB, ");
//		sql.append(" T.NAME AS CC ,TTTT.NAME AS DD,PO.ORGAN_NAME,CC.UPDATETIME,TTTTT.NAME AS EE,CCTE.PRICE,count(ccai.affixname)");
//		sql.append(" T.NAME AS CC ,TTTT.NAME AS DD,'',CC.UPDATETIME,TTTTT.NAME AS EE,CCTE.PRICE,count(ccai.affixname)");
		sql.append(" T.NAME AS CC ,TTTT.NAME AS DD, cc.updatepersonid , CC.UPDATETIME,TTTTT.NAME AS EE,CC.CONTRACTPRICE,count(ccai.affixname)");
		sql.append(" FROM CO_CONTRACTBASEINFO CC LEFT JOIN CO_CONTRACTBUSIFLOWINFO CCB ");
		sql.append(" ON CC.CONTRACTID = CCB.CONTRACTID LEFT JOIN  ba_gencode T ON T.type='67' AND CC.flowflag = T.VALUE ");
		sql.append(" LEFT JOIN CO_CONTRACTTYPEINFO CCT ON CC.CONTRACTTYPE = CCT.CONTRACTTYPEID ");
		sql.append(" LEFT JOIN BA_MARKETPARTICIPANT BM ON CC.PURCHASER = BM.PARTICIPANTID  ");
		sql.append(" LEFT JOIN BA_MARKETPARTICIPANT BMM ON CC.SELLER = BMM.PARTICIPANTID ");
		sql.append(" LEFT JOIN ba_gencode TT ON TT.TYPE='26'  AND CC.BACKUPSTATE = TT.VALUE ");
		sql.append(" LEFT JOIN ba_gencode TTT ON TTT.TYPE='24' AND CC.SIGNSTATE = TTT.VALUE ");
		sql.append(" LEFT JOIN ba_gencode TTTT ON TTTT.TYPE='27' AND CC.PREPCONTRACTFLAG = TTTT.VALUE ");
//		sql.append(" LEFT JOIN SGSOTOWER.PUB_ORGAN PO ON CC.UPDATEPERSONID = PO.ORGAN_CODE ");
		sql.append(" LEFT JOIN ba_gencode TTTTT ON TTTTT.type='1' AND CC.MARKETID = TTTTT.VALUE ");
//		sql.append(" LEFT JOIN CO_CONTRACTENERGYINFO CCTE ON CC.CONTRACTID = CCTE.CONTRACTID AND CCTE.PERIOD='1'");
		sql.append(" left join co_contractaffixinfo ccai on cc.contractid = ccai.contractid");
		sql.append(" WHERE ");
//		sql.append(" CC.ISDELETE = '0' ");//2014.05.19 
		sql.append(" 1 = 1");
		if(isNull(marketid)){//市场
			sql.append(" AND CC.MARKETID ='").append(marketid).append("' ");
		} else {//用当前登录用户marketid
	    	sql.append(" and cc.marketid='"+loginMarketId+"'");   
	    }
		if(isNull(contracttype)){//合同类型
			sql.append(" AND CC.CONTRACTTYPE in (").append(contracttype).append(") ");
		}
		String dateType = "CC.contractStartDate";//确定查询时间的类型
		if(isNull(timetype) && "0".equals(timetype)){//合同开始时间
			dateType = "CC.contractStartDate";
		} else if (isNull(timetype) && "1".equals(timetype)){//合同录入时间
			dateType = "CC.updateTime";
		} else if (isNull(timetype) && "2".equals(timetype)){//合同签订时间
			dateType = "CC.signedDate";
		}
		//开始时间不为空，结束时间为空时，结束日期小于开始日期
		if(isNull(startDate) && !isNull(endDate)){
			sql.append(" AND TO_CHAR(").append(dateType).append(",'YYYY-MM-DD') >= '").append(startDate).append("' ");
		}else if(!isNull(startDate) && isNull(endDate)){
			sql.append(" AND TO_CHAR(").append(dateType).append(",'YYYY-MM-DD') <= '").append(endDate).append("' ");
		}else if(isNull(startDate) && isNull(endDate)){
			sql.append(" AND (TO_CHAR(").append(dateType).append(",'YYYY-MM-DD') >= '").append(startDate)
			.append("' AND TO_CHAR(").append(dateType).append(",'YYYY-MM-DD') <= '").append(endDate).append("') ");
		}
		if(isNull(contractcyc)){//合同周期
			sql.append(" AND CC.CONTRACTCYC ='").append(contractcyc).append("' ");
		}
		// modified by juguanghui 2014-02-24 start (合同状态 取流转状态内容)
//		if(isNull(prepcontractflag)){//合同状态
//			sql.append(" AND CC.PREPCONTRACTFLAG ='").append(prepcontractflag).append("' ");
//		}
		if(isNull(prepcontractflag)){//合同状态
			sql.append(" AND CC.flowflag ='").append(prepcontractflag).append("' ");
		}
		// modified by juguanghui 2014-02-24 end
		if(isNull(purchaser)){//购电方
			sql.append(" AND CC.PURCHASER = '").append(purchaser).append("' ");
		}
		if(isNull(seller)){//售电方
			sql.append(" AND CC.SELLER ='").append(seller).append("' ");
		}
		if(isNull(sequence)){//合同序列
			sql.append(" and CC.SEQUENCEID = '").append(sequence).append("' ");
		}
		sql.append(" group by CC.CONTRACTID, CC.PAPERCONTRACTCODE, CC.CONTRACTNAME,");
		sql.append(" CCT.TYPENAME, BM.PARTICIPANTNAME,BMM.PARTICIPANTNAME ,CC.CONTRACTSTARTDATE,CC.CONTRACTENDDATE, CC.CONTRACTQTY, TT.NAME,TTT.NAME,T.NAME,TTTT.NAME, cc.updatepersonid, CC.UPDATETIME,TTTTT.NAME, CC.CONTRACTPRICE ");
		sql.append(" order by qq,papercontractcode");
		return sql.toString();
	}
	
	public static Boolean isNull(String str) {
		if (str == null || "".equals(str)) {
			return false;
		}
		return true;
	}
	
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
	public QueryResultObject query(RequestCondition queryCondition, String loginMarketId) {

		List<CoContractbaseinfoVO> result = new ArrayList<CoContractbaseinfoVO>();
		
		String marketid = "";
		String contracttype = "";
		String timetype = "";
		String contractstartdate = "";
		String contractenddate = "";
		String contractcyc = "";
		String prepcontractflag = "";//合同状态
		String purchaser = "";
		String seller =  "";
		String sequenceid = "";
		
		List<QueryFilter> list1 = queryCondition.getQueryFilter(CoContractbaseinfoVO.class);
		int PageIndex  = Integer.valueOf(queryCondition.getPageIndex());
		int PageSize  = Integer.valueOf(queryCondition.getPageSize());
		Map<String,String> valueMap = new HashMap<String,String>();
		if(list1 != null){
			for (int i = 0; i < list1.size(); i++) {
				QueryFilter filter = list1.get(i);
				String colName = filter.getFieldName();
				if(colName.equals("marketid")){
					marketid = (filter.getValue()==null?"":filter.getValue().toString());
					valueMap.put("marketid", marketid);
				}else if(colName.equals("contracttype")){
					contracttype = (filter.getValue()==null?"":filter.getValue().toString());
					valueMap.put("contracttype", contracttype);
				}else if(colName.equals("timetype")){
					timetype = (filter.getValue()==null?"":filter.getValue().toString());
					valueMap.put("timetype", timetype);
				}else if(colName.equals("contractstartdate")){
					contractstartdate = (filter.getValue()==null?"":filter.getValue().toString());
					valueMap.put("contractstartdate", contractstartdate);
				}else if(colName.equals("contractenddate")){
					contractenddate = (filter.getValue()==null?"":filter.getValue().toString());
					valueMap.put("contractenddate", contractenddate);
				}else if(colName.equals("contractcyc")){
					contractcyc = (filter.getValue()==null?"":filter.getValue().toString());
					valueMap.put("contractcyc", contractcyc);
				}else if(colName.equals("prepcontractflag")){
					prepcontractflag = (filter.getValue()==null?"":filter.getValue().toString());
					valueMap.put("prepcontractflag", prepcontractflag);
				}else if(colName.equals("purchaser")){
					purchaser = (filter.getValue()==null?"":filter.getValue().toString());
					valueMap.put("purchaser", purchaser);
				}else if(colName.equals("seller")){
					seller = (filter.getValue()==null?"":filter.getValue().toString());
					valueMap.put("seller", seller);
				}else if(colName.equals("sequenceid")){
					sequenceid = (filter.getValue()==null?"":filter.getValue().toString());
					valueMap.put("sequenceid", sequenceid);
				}
			}
		}
		String sql = getContractListByMCondition(valueMap, loginMarketId);
		
		List list = hibernateDao.executeSqlQuery(sql, PageIndex, PageSize);
		
		int count = 0;
		if(list!=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				Object[] obj = (Object[]) list.get(i);
				CoContractbaseinfoVO contractVO = new CoContractbaseinfoVO();
				
			    contractVO.setContractid(validateString(obj[0]));
				contractVO.setPapercontractcode(validateString(obj[1]));
				contractVO.setContractname(validateString(obj[2]));
				contractVO.setContracttype(validateString(obj[3]));
				contractVO.setPurchaser(validateString(obj[4]));
				contractVO.setSeller(validateString(obj[5]));
				contractVO.setContractstartdate(validateDate(obj[6]));
				contractVO.setContractenddate(validateDate(obj[7]));
				contractVO.setEnergy(validateBigDecimal(obj[8]));
				contractVO.setSignstate(validateString(obj[9]));
				contractVO.setBackupstate(validateString(obj[10]));
				contractVO.setFlowstate(validateString(obj[11]));
				contractVO.setContractstate(validateString(obj[11]));//合同状态显示 流转状态的值
				
//				String strName = obj[13]==null?"":UserInfoUtil.getNameByLoginCode(obj[13].toString());
//				
//				if(strName != null && strName.length()!=0){
//					contractVO.setMaintainperson(strName);
//				} 
				
				contractVO.setMaintainperson(getUpdatePerson(validateString(obj[13])));
				
				contractVO.setMaintaintime(validateString(obj[14]));
				contractVO.setMaintainunit(validateString(obj[15]));
				contractVO.setContractprice(validateBigDecimal(obj[16]));
				
				result.add(contractVO);
			}
			String tt = "SELECT COUNT(*) FROM ("+sql.toString()+") OOX ";
			List list2=hibernateDao.executeSqlQuery(tt);
			if(list2!=null ){
				count = Integer.parseInt(list2.get(0).toString());
			}
		}
		return RestUtils.wrappQueryResult(result, count);
	}
	
	private String getUpdatePerson(String updatePersonId){
		List listUser;
		try {
			listUser = AdapterWrapperFactory.getIdentityService().getUsersByLoginCode(updatePersonId);
			if(listUser != null && listUser.size() > 0){
				User user = (User) listUser.get(0);
				return user.getName();
			} else {
				return updatePersonId;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return updatePersonId;
	}
	
	private Date validateDate(Object obj){
		return obj==null?null:DateUtil.getUtilDate(obj.toString(), "yyyy-MM-dd");
	}

	private Date validateDateTime(Object obj){
		return obj==null?null:DateUtil.getUtilDate(obj.toString(), "yyyy-MM-dd HH:mm:ss");
	}
	
	private BigDecimal validateBigDecimal(Object obj){
		return obj==null?null:new BigDecimal(obj.toString());
	}
	
	private String validateString(Object obj){
		return obj==null?"":obj.toString();
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

	public void setHibernateDao(IHibernateDao hibernateDao) {
		this.hibernateDao = hibernateDao;
	}

	public QueryResultObject getHistoryVersion(RequestCondition params) {
		if(params.getFilter() != null){
			Map<String, String> map;
			try {
				map = JsonUtil.jsonToObject(params.getFilter().toString(), Map.class);//获取前台filter中的contractid
				String contractid = map.get("contractid");
				if(contractid != null && contractid.length() != 0){
					QueryResultObject queryResult = queryHisVersion(contractid);
					return queryResult;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 查询历史版本信息
	 * @param id
	 * @return QueryResultObject
	 */
	public QueryResultObject queryHisVersion(String contractID) {
		StringBuffer sql= new StringBuffer();
//		sql.append("select co.contractID,co.paperContractCode,co.contractName,co.version,co.updateTime,pu.user_name,co.description from CO_ContractChangeRecordInfo co ");
//		sql.append(" left join SGSOTOWER.PUB_USERS pu on co.updatepersonid=pu.user_id  where contractID='"+contractID+"'");
//		sql.append(" order by co.updateTime");
		sql.append("select co.contractID,co.paperContractCode,co.contractName,co.co_version,to_char(co.updatetime,'yyyy-MM-dd HH24:MI:SS') updatetime,co.description,co.updatepersonid from CO_ContractChangeRecordInfo co ");
		sql.append(" where contractID= ?");
		sql.append(" order by co.updateTime");
		Object [] param = {contractID};
		List result = hibernateDao.executeSqlQuery(sql.toString(), param);
		
		ArrayList dataList = new ArrayList();
		if(result != null){
			for (int i = 0; i < result.size(); i++) {
				Object [] obj = (Object[])result.get(i);
				
				CoContractbaseinfoVO coContractbaseinfoVO = new CoContractbaseinfoVO();
				coContractbaseinfoVO.setContractid(validateString(obj[0]));
				coContractbaseinfoVO.setPapercontractcode(validateString(obj[1]));
				coContractbaseinfoVO.setContractname(validateString(obj[2]));
				coContractbaseinfoVO.setVersion(validateString(obj[3]));
				coContractbaseinfoVO.setUpdatetime(validateDateTime(obj[4]));
				coContractbaseinfoVO.setDescription(validateString(obj[5]));
				coContractbaseinfoVO.setUpdateperson(getUpdatePerson(validateString(obj[6])));
				dataList.add(coContractbaseinfoVO);
			}
		}
		return RestUtils.wrappQueryResult(dataList);
	}

	/**
	 * 合同比较，获取合同版本信息
	 */
	public QueryResultObject hisVersionCompared(RequestCondition params) {
		if(params.getFilter() != null){
			Map<String, String> map;
			try {
				map = JsonUtil.jsonToObject(params.getFilter().toString(), Map.class);//获取前台filter中的contractid
				String contractid = map.get("contractid");
				int pageIndex = Integer.parseInt(params.getPageIndex());
				int pageSize = Integer.parseInt(params.getPageSize());
				
				if(contractid != null && contractid.length() != 0){
					String sql = findContractSQL();
					return findContract(sql.toString(), contractid, pageIndex, pageSize);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public QueryResultObject findContract(String sql, String contractid, int pageIndex, int pageSize){
		Object [] param = {contractid};
		List result = hibernateDao.executeSqlQuery(sql, param, pageIndex, pageSize);
		
		if(result != null){
			ArrayList<CoContractbaseinfoVO> dataList = new ArrayList<CoContractbaseinfoVO>();
			for (int i = 0; i < result.size(); i++) {
				Object obj [] = (Object [])result.get(i);
				
				CoContractbaseinfoVO coContractbaseinfoVO = new CoContractbaseinfoVO();
				coContractbaseinfoVO.setContractid(validateString(obj[0]));
				coContractbaseinfoVO.setDescription(validateString(obj[1]));
				coContractbaseinfoVO.setUpdatetime(validateDateTime(obj[2]));
				coContractbaseinfoVO.setVersion(validateString(obj[3]));
				coContractbaseinfoVO.setContractname(validateString(obj[4]));
				coContractbaseinfoVO.setPapercontractcode(validateString(obj[5]));
				coContractbaseinfoVO.setSigneddate(validateDate(obj[6]));
				coContractbaseinfoVO.setSignedperson(validateString(obj[7]));
				coContractbaseinfoVO.setSignedsite(validateString(obj[8]));
				coContractbaseinfoVO.setPurchaseparticipantname(validateString(obj[9]));
				coContractbaseinfoVO.setSellparticipantname(validateString(obj[10]));
				coContractbaseinfoVO.setContractstartdate(validateDate(obj[11]));
				coContractbaseinfoVO.setContractenddate(validateDate(obj[12]));
				coContractbaseinfoVO.setSignstate(validateString(obj[13]));
				coContractbaseinfoVO.setBackupstate(validateString(obj[14]));
				coContractbaseinfoVO.setIsrelation(validateBigDecimal(obj[15]));
				coContractbaseinfoVO.setExectype(validateString(obj[16]));
//				coContractbaseinfoVO.setContractsettle(validateString(obj[17]));
//				coContractbaseinfoVO.setSequenceid(validateString(obj[18]));
//				coContractbaseinfoVO.setFlowstate(validateString(obj[19]));
				coContractbaseinfoVO.setContractprice(validateBigDecimal(obj[20]));
				coContractbaseinfoVO.setEnergy(validateBigDecimal(obj[21]));
//				coContractbaseinfoVO.setPurchaserperson(validateString(obj[22]));
//				coContractbaseinfoVO.setPurchaserphone(validateString(obj[23]));
//				coContractbaseinfoVO.setPurchaserconftime(validateDate(obj[24]));
//				coContractbaseinfoVO.setSellerperson(validateString(obj[25]));
//				coContractbaseinfoVO.setSellerphone(validateString(obj[26]));
//				coContractbaseinfoVO.setSellerconftime(validateDate(obj[27]));
				coContractbaseinfoVO.setVersionid(validateString(obj[28]));
				coContractbaseinfoVO.setContractprepare(validateString(obj[30]));
				coContractbaseinfoVO.setContractcycle(validateString(obj[31]));
			
//				String strName = obj[29]==null?"":UserInfoUtil.getNameByLoginCode(obj[29].toString());
//				
//				if(strName != null && strName.length()!=0){
//					coContractbaseinfoVO.setMaintainperson(strName);
//				} 
				coContractbaseinfoVO.setMaintainperson(getUpdatePerson(validateString(obj[29])));
				
				dataList.add(coContractbaseinfoVO);
			}
			return RestUtils.wrappQueryResult(dataList, dataList.size()).addDicItems(wrapDictList());
		}
		return null;
	}
	
	/**
	 *  合同对比
	 */
	public String findContractSQL() {
		StringBuffer sql= new StringBuffer();
		sql.append("select co.contractid,co.description,to_char(co.updatetime,'yyyy-MM-dd HH24:MI:SS') updatetime,co.co_version version,co.contractName,co.paperContractCode,co.signedDate,co.signedperson,co.signedSite,");
		sql.append(" mp.participantname as purchaseparticipantname,mp1.participantname as sellparticipantname ,co.contractStartDate,co.contractEndDate,");
		sql.append("gc1.name as signstate,gc2.name as backupstate,gc.value as isrelation,gc3.name as exectype,gc4.name as contractsettle,sequenceinfo.sequencename,gc5.name as name5,co.ContractPrice,co.Contractqty ");
		sql.append(",co.purchaserPerson,co.purchaserPhone,co.purchaserConfTime,co.sellerPerson,co.sellerPhone,co.sellerConfTime, co.VERSIONID, co.updatepersonid, gc6.name prepcontractflag, gc7.name contractcyc ");
		sql.append("from CO_ContractChangeRecordInfo co ");
		sql.append(" left join ba_marketparticipant mp on mp.participantid=co.purchaser left join  ba_marketparticipant mp1 on mp1.participantid=co.seller ");
		sql.append("left join   CO_CONTRACSEQUENCEINFO sequenceinfo on sequenceinfo.sequenceid = co.sequenceid ");//签订状态
		sql.append("left join   ba_gencode gc1 on gc1.value=co.Signstate and gc1.type='24'   ");//签订状态
		sql.append("left join   ba_gencode gc2 on gc2.value=co.backupState and gc2.type='26'  ");// --备案状态
		sql.append("left join   ba_gencode gc on gc.value=co.isRelation and gc.type='30'   ");//--关联权限
		sql.append("left join   ba_gencode gc3 on gc3.value=co.execType and gc3.type='68'  ");// --合同执行类型
		sql.append("left join   ba_gencode gc4 on gc4.value=co.settleSide and gc4.type='76' ");//  --合同结算方
		sql.append("left join   ba_gencode gc5 on gc5.value=co.flowFlag and gc5.type='67'  ");//--合同流程状态
		sql.append("left join   ba_gencode gc6 on gc6.value=co.prepcontractflag and gc6.type='27'  ");//--预合同标志
		sql.append("left join   ba_gencode gc7 on gc7.value=co.contractcyc and gc7.type='23'  ");//--合同周期
		sql.append(" where contractID= ?");
		sql.append(" order by co.updateTime desc");
		return sql.toString();
	}
	
	// 将字典对象封装为list
	private List<DicItems> wrapDictList() {
		List<DicItems> dicts = new ArrayList<DicItems>();
        dicts.add(translateFromDB("isrelation", "com.sgcc.dlsc.entity.po.BaGencode", "value", "name"));
		return dicts;
	}
	
	// 从数据库中查询字典
	private DicItems translateFromDB(String fieldName, String poName,String keyField, String valueField) {
		List<Map<String, String>> list = dataDictionaryBizC.translateFromDB(
				poName, "value", "text", keyField, valueField,"type='30'");
		DicItems dict = new DicItems();
		dict.setName(fieldName);
		dict.setValues(list);
		return dict;
	}
	
	/**
	 * 回滚操作  
	 * 1、将最新合同记录保存为 历史版本信息
	 * 2、查找选中历史版本信息
	 * 3、将选中历史版本信息回滚，作为最新合同版本
	 * 
	 */
	public String rollBack(String params, HttpServletRequest request) {
		Map map = null;
		String coVersionHis = "";
		try {
			map = JsonUtils.getObjectMapper().readValue(params, Map.class);
			String versionId = map.get("versionId")==null?"":map.get("versionId").toString();//@获取合同id
			String contractId = map.get("contractid")==null?"":map.get("contractid").toString();//@获取合同id
			
			CoContractbaseinfo coContractbaseinfo = (CoContractbaseinfo) hibernateDao.getObject(CoContractbaseinfo.class,contractId);//@根据合同id查询合同基本信息
			if(coContractbaseinfo == null){
				return "fail";
			}
			else{
				String coVersion = coContractbaseinfo.getCoVersion();
				if(ToolsSys.isnull(coVersion)){//非空
					coVersionHis = coVersion;
//					coVersion = coVersion.substring(1, coVersion.length());
//					coVersionHis = "V"+ (Integer.parseInt(coVersion) + 1);
				}else{//空值
					coVersionHis = coVersion;
				}
			}
			
			CoContractchangerecordinfo coContractchangerecordinfo = new CoContractchangerecordinfo();
//			ComUtils comUtils = new ComUtils();
//			comUtils.obj2obj(coContractbaseinfo, coContractchangerecordinfo);
//			coContractchangerecordinfo.setCmd("更新操作");
//			User userInfo = UserInfoUtil.getLoginUser(request);
//			coContractchangerecordinfo.setOperator(userInfo.getId());//@设置操作人
//			coContractchangerecordinfo.setOperatingdescription("合同变更说明");//@设置合同变更信息
//			java.util.Date currentTime = new java.sql.Timestamp(System.currentTimeMillis());//整理时间，opereatedate字段保存中 带有 时：分：秒
//			coContractchangerecordinfo.setOperatedate(currentTime);
//			coContractchangerecordinfo.setOrderno(new BigDecimal("1"));
//			hibernateDao.saveObject(coContractchangerecordinfo);//@保存信息
//			
			coContractchangerecordinfo = hibernateDao.getObject(CoContractchangerecordinfo.class,versionId);//@查询合同变更记录
			if (coContractchangerecordinfo==null) {
				return "fail";
			}
			ComUtils.obj2obj(coContractchangerecordinfo, coContractbaseinfo);
			coContractbaseinfo.setCoVersion(coVersionHis);
			try {
				hibernateDao.updateObject(coContractbaseinfo);//@更新
				verticalIntegration(coContractbaseinfo, request);
			} catch (Exception e) {
				e.printStackTrace();
				return "fail";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	
	}
	
	/**
	 * 纵向传输
	 * @param coContractbaseinfo
	 * @param request
	 */
	private void verticalIntegration(CoContractbaseinfo coContractbaseinfo, HttpServletRequest request){
		String contractid = coContractbaseinfo.getContractid();
		//纵向传输
		String marketid = null;
		try {
			marketid = UserInfoUtil.getLoginUserMarket(request);
		} catch (Exception e1) {
			e1.printStackTrace();
		}//从数据库中获取当前登录用户
		VIParamBean vip = new VIParamBean();
		if(marketid != null){
			vip.setMarketid_source(marketid);
		}
		vip.setMarketid_target(DataConstants.marketid_target);//目的场景
		vip.setTableName("co_contractbaseinfo");//表名
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
}
