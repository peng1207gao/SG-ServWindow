package com.sgcc.dlsc.contractManage.IdeaConManage.bizc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
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
import com.sgcc.dlsc.commons.util.DateUtil;
import com.sgcc.dlsc.commons.util.ToolsSys;
import com.sgcc.dlsc.commons.util.UserInfoUtil;
import com.sgcc.dlsc.constants.DataConstants;
import com.sgcc.dlsc.contractManage.IdeaConManage.vo.CoContractbaseinfoTransfer;
import com.sgcc.dlsc.contractManage.IdeaConManage.vo.CoContractbaseinfoVO;
import com.sgcc.dlsc.entity.po.CoContractbaseinfo;
import com.sgcc.isc.core.orm.identity.User;
import com.sgcc.uap.bizc.sysbizc.datadictionary.IDataDictionaryBizC;
import com.sgcc.uap.persistence.IHibernateDao;
import com.sgcc.uap.rest.support.DicItems;
import com.sgcc.uap.rest.support.IDRequestObject;
import com.sgcc.uap.rest.support.QueryFilter;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;
import com.sgcc.uap.rest.support.TreeNode;
import com.sgcc.uap.rest.utils.CrudUtils;
import com.sgcc.uap.rest.utils.JsonUtils;
import com.sgcc.uap.rest.utils.RestUtils;
/**
 * 用户定义逻辑构件
 * 
 * @author DELL 
 * 
 */
public class IdeaConManageBizc implements IIdeaConManageBizc{

	@Autowired
	private IHibernateDao hibernateDao;
	
	@Autowired
	private IDataDictionaryBizC dataDictionaryBizC;
	
	@Autowired
	private IComm_VerticalIntegration_Bizc commVerticalIntegrationBizc;
	/**
	 * 判断将要关联的两个合同是否已经存在上下级关系
	 * 如果存在则不能设置为上级合同
	 * @return
	 */
	private Boolean isContractInRelation(String fatherId, String contractId){
		StringBuffer str = new StringBuffer();
		str.append("SELECT cc.contractid");
		str.append(" FROM co_contractbaseinfo cc");
		str.append(" START WITH cc.CONTRACTID = '" + fatherId + "'");
		str.append(" CONNECT BY PRIOR cc.superexecid = cc.CONTRACTID");
		str.append(" union ");
		str.append("SELECT cc.contractid");
		str.append(" FROM co_contractbaseinfo cc");
		str.append(" START WITH cc.superexecid = '" + fatherId + "'");
		str.append(" CONNECT BY PRIOR cc.CONTRACTID = cc.superexecid");
		
		List list = hibernateDao.executeSqlQuery(str.toString());
		for(int i = 0; i < list.size(); i++){
			Object obj = list.get(i);
			if(contractId.equalsIgnoreCase(obj.toString())){
				return true;
			}
		}
		return false;
	}
	/**
	 * 取消合同关联
	 */
	public QueryResultObject cancleIdeaRelation(String id,
			String isRootNode, HttpServletRequest request){
		if("true".equals(isRootNode)){
			String sql = "select contractid,superexecid from co_contractbaseinfo where superexecid = ?";
			Object [] params = {id};
			List list = hibernateDao.executeSqlQuery(sql, params);
			if(list != null && list.size() != 0){
				for(int i = 0; i<list.size(); i++){
					Object [] obj = (Object [])list.get(i);
					String contractid = obj[0] == null?"":obj[0].toString();
					CoContractbaseinfo coContractbaseinfo = (CoContractbaseinfo) hibernateDao.getObject(CoContractbaseinfo.class, contractid);
					coContractbaseinfo.setSuperexecid("");
					hibernateDao.updateObject(coContractbaseinfo);
					verticalIntegration(coContractbaseinfo, request);//纵向传输
				}
			}
		} else {
			CoContractbaseinfo coContractbaseinfo = (CoContractbaseinfo) hibernateDao.getObject(CoContractbaseinfo.class, id);
			coContractbaseinfo.setSuperexecid("");
			hibernateDao.updateObject(coContractbaseinfo);
			verticalIntegration(coContractbaseinfo, request);//纵向传输
		}
		return null;
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
		String signstate = "";
		String purchaser = "";
		String seller =  "";
		String sequenceid = "";
		String contractname ="";
		
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
				}else if(colName.equals("signstate")){
					signstate = (filter.getValue()==null?"":filter.getValue().toString());
					valueMap.put("signstate", signstate);
				}else if(colName.equals("purchaser")){
					purchaser = (filter.getValue()==null?"":filter.getValue().toString());
					valueMap.put("purchaser", purchaser);
				}else if(colName.equals("seller")){
					seller = (filter.getValue()==null?"":filter.getValue().toString());
					valueMap.put("seller", seller);
				}else if(colName.equals("sequenceid")){
					sequenceid = (filter.getValue()==null?"":filter.getValue().toString());
					valueMap.put("sequenceid", sequenceid);
				}else if(colName.equals("contractname")){
					contractname = (filter.getValue()==null?"":filter.getValue().toString());
					valueMap.put("contractname", contractname);
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
				contractVO.setIsrelation(validateBigDecimal(obj[3]));
				contractVO.setContracttype(validateString(obj[4]));
				contractVO.setMarketname(validateString(obj[16]));
				contractVO.setExectype(validateString(obj[13]));
				contractVO.setPurchaser(validateString(obj[5]));
				contractVO.setSeller(validateString(obj[6]));
				contractVO.setContractstartdate(validateDate(obj[7]));
				contractVO.setContractenddate(validateDate(obj[8]));
				contractVO.setEnergy(validateBigDecimal(obj[9]));
				contractVO.setBackupstate(validateString(obj[12]));
				contractVO.setSignstate(validateString(obj[11]));
				contractVO.setFlowstate(validateString(obj[15]));
				contractVO.setMarketid(validateString(obj[17]));
				contractVO.setSupercontractname(validateString(obj[14]));
				contractVO.setSupercontractid(validateString(obj[18]));
				contractVO.setChildnum(validateString(obj[19]));
				contractVO.setSuperexecid(validateString(obj[20]));
				
				result.add(contractVO);
			}
			String tt = "SELECT COUNT(1) FROM ("+sql.toString()+") OOX ";
			List list2=hibernateDao.executeSqlQuery(tt);
			if(list2!=null ){
				count = Integer.parseInt(list2.get(0).toString());
			}
		}
		return RestUtils.wrappQueryResult(result, count).addDicItems(wrapDictList());
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
	private Date validateDate(Object obj){
		return obj==null?null:DateUtil.getUtilDate(obj.toString(), "yyyy-MM-dd");
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
	public QueryResultObject queryById(String contractid) {
		String sql = findDetailSQL();
		Object [] param = {contractid};
		List list = hibernateDao.executeSqlQuery(sql, param);
		
		CoContractbaseinfoVO contractVO = new CoContractbaseinfoVO();

		if(list!=null && list.size()>0){
			Object[] obj = (Object[]) list.get(0);//获取第一个记录
			
			contractVO.setContractid(validateString(obj[0]));
			contractVO.setPapercontractcode(validateString(obj[1]));
			contractVO.setContractname(validateString(obj[2]));
			contractVO.setContracttype(validateString(obj[3]));
			contractVO.setPurchaser(validateString(obj[4]));
			contractVO.setSeller(validateString(obj[5]));
			contractVO.setContractstartdate(validateDate(obj[6]));
			contractVO.setContractenddate(validateDate(obj[7]));
			contractVO.setEnergy(validateBigDecimal(obj[8]));
//			contractVO.setBackupstate(validateString(obj[9]));//备案状态
//			contractVO.setSignstate(validateString(obj[10]));
//			contractVO.setFlowstate(validateString(obj[11]));
			contractVO.setMaintainperson(validateString(obj[12]));
			contractVO.setMaintaintime(validateString(obj[13]));
			contractVO.setMaintainunit(validateString(obj[14]));
			contractVO.setSupercontractname(validateString(obj[15]));
			contractVO.setContractprice(validateBigDecimal(obj[16]));
			contractVO.setContractstate(validateString(obj[17]));
			contractVO.setSuperexecid(validateString(obj[18]));
		
		}
		return RestUtils.wrappQueryResult(contractVO);
	}

	public void setHibernateDao(IHibernateDao hibernateDao) {
		this.hibernateDao = hibernateDao;
	}
	
	/**
	 * 查询详细信息
	 * @description 方法描述
	 * @param detailid
	 * @return
	 * @author mengke 
	 * @date 2013-4-16
	 */
	public String findDetailSQL() {
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct cc.contractid,cc.papercontractcode,cc.contractname,cct.typename,bm.participantname as pp, bmm.participantname as qq, ");
		sql.append(" cc.contractstartdate,cc.contractenddate,cc.contractqty,tt.name as aa ,ttt.name as bb, ");
//		sql.append(" tttt.name as dd,po.organ_name,cc.updatetime,ttttt.name  as ee ,mm.contractname as mmn,CC.CONTRACTPRICE");
		sql.append(" tttt.name as dd, '',cc.updatetime,ttttt.name  as ee ,mm.contractname as mmn,CC.CONTRACTPRICE, gencode.name as contractstate, ");
		sql.append(" cc.superexecid ");
		sql.append(" from co_contractbaseinfo cc left join co_contractbusiflowinfo ccb  on cc.contractid = ccb.contractid ");
		sql.append(" left join co_contracttypeinfo cct on cc.contracttype = cct.contracttypeid ");
		sql.append(" left join ba_marketparticipant bm on   cc.purchaser = bm.PARTICIPANTID  ");
		sql.append(" left join ba_marketparticipant bmm on   cc.seller = bmm.PARTICIPANTID ");
		sql.append(" left join ba_gencode tt on  tt.type='26'  and cc.backupstate = tt.value ");
		sql.append(" left join ba_gencode ttt on ttt.type='24' and cc.signstate = ttt.value ");
		sql.append(" left join ba_gencode tttt on  tttt.type='27' and cc.prepcontractflag = tttt.value ");
//		sql.append(" left join sgsotower.pub_organ po on cc.updatepersonid = po.organ_code ");
		sql.append(" left join ba_gencode ttttt on  ttttt.type='1'  and  cc.marketid = ttttt.value ");
		sql.append(" left join ba_gencode gencode on gencode.type = '67' and cc.flowflag = gencode.value ");
		sql.append(" left join co_contractbaseinfo mm on  mm.contractid  = cc.supercontractid ");
		sql.append(" left join co_contransenergyinfo ccte on cc.contractid = ccte.contractid ");
		sql.append(" where cc.isdelete='0' AND ");
		sql.append(" cc.contractid = ?");
		sql.append(" order by  cc.contractid desc ");
		
		return sql.toString();
	}
	/**
	 * 修改合同【合同执行类型】
	 */
	public QueryResultObject updateContractInfo(String contractid, String exetype, HttpServletRequest request){
		
		try {
			CoContractbaseinfo coContractbaseinfo = hibernateDao.getObject(CoContractbaseinfo.class,contractid);
			if(exetype != null){
				coContractbaseinfo.setExectype(new BigDecimal(exetype));
				hibernateDao.updateObject(coContractbaseinfo);
				verticalIntegration(coContractbaseinfo, request);
			} 
			// TODO 返回值进一步确定
			CoContractbaseinfoVO vo = null;
			if (coContractbaseinfo != null) {
				vo = CoContractbaseinfoTransfer.toVO(coContractbaseinfo);
			}
			return RestUtils.wrappQueryResult(vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
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
		String conType =  map.get("contracttype");//合同类型
		String timetype = map.get("timetype");//时间类型
		String startTime = map.get("contractstartdate");//开始时间
		String endTime = map.get("contractenddate");//截止时间
		String conCircle = map.get("contractcyc");//合同周期
		String signState = map.get("signstate");//合同状态
		String sequence = map.get("sequenceid");//合同序列
		String purchaser = map.get("purchaser");//购电方
		String seller = map.get("seller");//售点方
		String isrelation = map.get("isrelation");//关联权限
	    String contractid = map.get("contractid");
	    String isDefine = map.get("marketid");//marketid
	    String contractname = map.get("contractname");
	    
	  //合同类型改成多选，故先将合同类型拼成sql可以使用的字符串
		if(ToolsSys.isnull(conType)){
			String[] contracttypes = conType.split(",");
			for(int i=0;i<contracttypes.length;i++){
				if(i==0){
					conType = "'"+contracttypes[i]+"'";
				}else {
					conType = conType + ",'"+contracttypes[i]+"'";
				}
			}
		}

		StringBuffer sql = new StringBuffer();
		sql.append("WITH AA AS(SELECT * FROM CO_CONTRACTBASEINFO WHERE isdelete = '0' AND marketid='"+marketid+"')");
		sql.append(" SELECT DISTINCT CC.CONTRACTID,CC.PAPERCONTRACTCODE,CC.CONTRACTNAME,CC.isRelation,CCT.TYPENAME,BM.PARTICIPANTNAME AS PP, ");
		sql.append(" BMM.PARTICIPANTNAME AS QQ ,CC.CONTRACTSTARTDATE,CC.CONTRACTENDDATE,CC.CONTRACTQTY,CC.ContractPrice,ttt.name as qd,tt.name as bea,bgyx.name as yx,mm.contractname as mmn,cg.name as flow,bg.marketname,cc.marketid,");
		sql.append(" cc.supercontractid, ");
		sql.append(" (SELECT COUNT(*) FROM aa WHERE aa.supercontractid = cc.contractid ) AS CHILDNUM,");
		sql.append(" cc.superexecid, ");
		sql.append(" (SELECT COUNT(*) FROM aa WHERE aa.superexecid = cc.contractid) AS ideaCHILDNUM");
		sql.append(" FROM CO_CONTRACTBASEINFO CC ");
//		sql.append(" LEFT JOIN CO_CONTRACTBUSIFLOWINFO CCB ON CC.CONTRACTID = CCB.CONTRACTID LEFT JOIN  ba_gencode T ON T.type='28' AND CCB.BUSIFLOWSTATE = T.VALUE ");
		//sql.append(" LEFT JOIN CO_ContractEnergyInfo CE ON CE.CONTRACTID = CC.CONTRACTID ");
		sql.append(" INNER JOIN CO_CONTRACTTYPEINFO CCT ON CC.CONTRACTTYPE = CCT.CONTRACTTYPEID ");
/*		sql.append(" LEFT JOIN BA_MARKETPARTICIPANT BM ON  BM.PARTICIPANTTYPE IN ('0','2') AND CC.PURCHASER = BM.PARTICIPANTNAME  ");
		sql.append(" LEFT JOIN BA_MARKETPARTICIPANT BMM ON  BMM.PARTICIPANTTYPE IN ('0','1')  AND CC.SELLER = BMM.PARTICIPANTNAME ");*/
		sql.append(" LEFT JOIN BA_MARKETPARTICIPANT BM ON CC.PURCHASER = BM.PARTICIPANTID  ");
		sql.append(" LEFT JOIN BA_MARKETPARTICIPANT BMM ON CC.SELLER = BMM.PARTICIPANTID ");
		sql.append(" LEFT JOIN ba_gencode TT ON  TT.TYPE='26'  AND CC.BACKUPSTATE = TT.VALUE ");
		sql.append(" LEFT JOIN ba_gencode TTT ON TTT.TYPE='24' AND CC.SIGNSTATE = TTT.VALUE ");
//		sql.append(" LEFT JOIN ba_gencode TTTT ON  TTTT.TYPE='27' AND CC.PREPCONTRACTFLAG = TTTT.VALUE ");
//		sql.append(" LEFT JOIN ba_gencode TTTTT ON  TTTTT.type='1'  AND  CC.MARKETID = TTTTT.VALUE ");
		sql.append(" LEFT JOIN ba_gencode bgyx ON bgyx.TYPE='68' AND CC.execType = bgyx.VALUE ");
		sql.append(" LEFT JOIN ba_gencode cg  ON  cg.type='67' and cg.VALUE=CC.flowflag  ");
		sql.append(" LEFT JOIN CO_ContracSequenceInfo CCE ON  CCE.sequenceID=CC.sequenceID  ");
		//合同序列待定，做成模糊查询还是什么的？
		sql.append(" left join co_contractbaseinfo mm on  mm.contractid  = cc.supercontractid ");
//		sql.append(" left join CO_CONTRACTBASEINFO_share cs on cs.dataid=cc.CONTRACTID");
		sql.append(" left join Ba_market bg on cc.marketid = bg.marketcode");
		sql.append(" WHERE");
//		sql.append(" CC.ISDELETE = '0'  ");
		sql.append(" 1 = 1");
		if(marketid != null && !"".equals(marketid) && !"null".equals(marketid)){
		    sql.append(" and cc.marketid='"+marketid+"'");
	    }else {//用当前登录用户marketid
	    	sql.append(" and cc.marketid='"+loginMarketId+"'");   
	    }
		
		if(ToolsSys.isnull(contractname)){//@判断是否为空
			sql.append(" and cc.contractName like '%").append(contractname).append("%' ");
		}
		
		if(conType!=null && !"".equals(conType) && !"null".equals(conType)){
			sql.append(" AND CC.CONTRACTTYPE in (").append(conType).append(") ");
		}
//		if(startTime!=null && !startTime.equals("")){
//			sql.append(" AND CC.CONTRACTSTARTDATE ='").append(startTime).append("' ");
//		}
//		if(endTime!=null && !endTime.equals("")){
//			sql.append(" AND CC.CONTRACTENDDATE = '").append(endTime).append("' ");
//		}
//		if(timetype!=null && !"".equals(timetype) && !"null".equals(timetype)){
//			if("0".equals(timetype)){//合同开始时间
//				if((startTime!=null && !startTime.equals(""))&& (endTime==null || endTime.equals(""))){
//		        	sql.append(" AND TO_CHAR(CC.contractStartDate,'YYYY-MM-DD')>= '").append(startTime).append("' ");
//		        }else if((endTime!=null && !endTime.equals(""))&& (startTime==null || startTime.equals("")) ){
//		        	sql.append(" AND TO_CHAR(CC.contractStartDate,'YYYY-MM-DD')<= '").append(endTime).append("' ");
//		        }else if((startTime!=null && !startTime.equals(""))&& (endTime!=null &&! endTime.equals(""))){
//		        	sql.append(" AND TO_CHAR(CC.contractStartDate,'YYYY-MM-DD')>= '").append(startTime).append("' and TO_CHAR(CC.contractStartDate,'YYYY-MM-DD')<= '").append(endTime).append("' ");
//		        }
//			}
//			if("1".equals(timetype)){//合同录入时间
//				if((startTime!=null && !"".equals(startTime))&& (endTime==null || "".equals(endTime))){
//		        	sql.append(" AND TO_CHAR(CC.updateTime,'YYYY-MM-DD')<= '").append(startTime).append("' ");
//		        }else if((endTime!=null && !"".equals(endTime))&& (startTime==null || "".equals(startTime)) ){
//		        	sql.append(" AND TO_CHAR(CC.updateTime,'YYYY-MM-DD')>= '").append(endTime).append("' ");
//		        }else if((startTime!=null && !"".equals(startTime))&& (endTime!=null &&! "".equals(endTime))){
//		        	sql.append(" AND TO_CHAR(CC.updateTime,'YYYY-MM-DD')<= '").append(startTime).append("' OR TO_CHAR(CC.updateTime,'YYYY-MM-DD')>= '").append(endTime).append("' ");
//		        }
//			}
//			if("2".equals(timetype)){//合同签订时间
//		        if((startTime!=null && !"".equals(startTime))&& (endTime==null || "".equals(endTime))){
//		        	sql.append(" AND TO_CHAR(CC.signedDate,'YYYY-MM-DD')>= '").append(startTime).append("' ");
//		        }else if((endTime!=null && !"".equals(endTime))&& (startTime==null || "".equals(startTime)) ){
//		        	sql.append(" AND TO_CHAR(CC.signedDate,'YYYY-MM-DD')<= '").append(endTime).append("' ");
//		        }else if((startTime!=null && !"".equals(startTime))&& (endTime!=null &&! "".equals(endTime))){
//		        	sql.append(" AND TO_CHAR(CC.signedDate,'YYYY-MM-DD')>= '").append(startTime).append("' OR TO_CHAR(CC.signedDate,'YYYY-MM-DD')<= '").append(endTime).append("' ");
//		        }
//			}
//		}
		
		//开始时间不为空，结束时间为空时，结束日期小于开始日期
		if(ToolsSys.isnull(startTime) && !ToolsSys.isnull(endTime)){//@判断是否为空
			sql.append(" AND TO_CHAR(").append ("CC.CONTRACTENDDATE").append(",'YYYY-MM-DD') >= '").append(startTime).append("' ");
		}else if(!ToolsSys.isnull(startTime) && ToolsSys.isnull (endTime)){//@判断是否为空
			sql.append(" AND TO_CHAR(").append ("CC.CONTRACTSTARTDATE").append(",'YYYY-MM-DD') <= '").append(endTime).append("' ");
		}else if(ToolsSys.isnull(startTime) && ToolsSys.isnull (endTime)){//@判断是否为空
			sql.append(" AND (TO_CHAR(").append ("CC.CONTRACTENDDATE").append(",'YYYY-MM-DD') >= '").append(startTime).append("' AND  TO_CHAR(").append
			("CC.CONTRACTSTARTDATE").append(",'YYYY-MM-DD') <= '").append (endTime).append("') ");
		}else{
			sql.append(" AND TO_CHAR(").append ("cc.CONTRACTSTARTDATE").append(",'YYYY') = TO_CHAR(sysdate,'YYYY') ").append(" AND TO_CHAR(").append ("cc.CONTRACTENDDATE").append(",'YYYY') = TO_CHAR(sysdate,'YYYY') ");
		}
		
		if(conCircle!=null && !"".equals(conCircle) && !"null".equals(conCircle)){//@判断合同周期是否为空
			sql.append(" AND CC.CONTRACTCYC ='").append(conCircle).append("' ");
		}
		//modified by juguanghui 2012-02-25 （合同状态字段 统一取 flowflag） start
		
//		if(signState!=null && !"".equals(signState) && !"null".equals(signState)){//@判断签订状态是否为空
//		if("0".equals(signState)){//预合同
//			sql.append(" AND CC.prepContractFlag = '1' ");
//		}else if("1".equals(signState)){//流转中	backupState = 3
//			sql.append(" AND CC.prepContractFlag = '2'");
//			sql.append(" AND CC.backupState = '3' ");
//		}else if("2".equals(signState)){//未签订	signState = 2
//			sql.append(" AND CC.prepContractFlag = '2'");
//			sql.append(" AND CC.signState = '2' ");
//		}else if("3".equals(signState)){//已签订	signState = 1
//			sql.append(" AND CC.prepContractFlag = '2'");
//			sql.append(" AND CC.signState = '1' ");
//		}else if("4".equals(signState)){//未备案	backupState = 2
//			sql.append(" AND CC.prepContractFlag = '2'");
//			sql.append(" AND CC.backupState = '2' ");
//		}else if("5".equals(signState)){//已备案	backupState = 1
//			sql.append(" AND CC.prepContractFlag = '2'");
//			sql.append(" AND CC.backupState = '1' ");
//		}
//		}
		if(signState!=null && !"".equals(signState) && !"null".equals(signState)){//@判断签订状态是否为空
			if("1".equals(signState)){//起草中
				sql.append(" AND CC.flowflag = '1' ");
			}else if("2".equals(signState)){//流转审核
				sql.append(" AND CC.flowflag = '2' ");
			}else if("3".equals(signState)){//审批中	
				sql.append(" AND CC.flowflag = '3' ");
			}else if("4".equals(signState)){//已签订	
				sql.append(" AND CC.flowflag = '4' ");
			}else if("5".equals(signState)){//已备案	
				sql.append(" AND CC.flowflag = '5' ");
			}
		}
		//modified by juguanghui 2012-02-25 （合同状态字段 统一取 flowflag） end
		
		if(purchaser!=null && !"".equals(purchaser) && !"null".equals(purchaser)){
			sql.append(" AND CC.PURCHASER = '").append(purchaser).append("' ");
		}
		if(seller!=null && !"".equals(seller) && !"null".equals(seller)){
			sql.append(" AND CC.SELLER ='").append(seller).append("' ");
		}
		if(sequence!=null && !"".equals(sequence) && !"null".equals(sequence)){
			sql.append(" AND CCE.sequenceid like '%").append(sequence).append("%' ");
		}
		if(isrelation!=null && !"".equals(isrelation) && !"null".equals(isrelation)){
			sql.append(" AND cc.isRelation = '").append(isrelation).append("' ");
		}
		if(contractid!=null && !"".equals(contractid) && !"null".equals(contractid)){
			sql.append(" AND cc.contractid != '").append(contractid).append("' ");
		}
		sql.append(" order by qq, papercontractcode");
		return sql.toString();
		
	}
	
	/**
	 * 根据字典类型查询对应的字典列表
	 * @param type
	 * @return
	 */
	public List getDictionaryByType(String type){
		String sql = "SELECT BG.VALUE,BG.NAME FROM BA_GENCODE BG WHERE BG.TYPE= ? ORDER BY BG.VALUE"; 
		List list = hibernateDao.executeSqlQuery(sql,new Object[]{type});
		return list;

	}
	
	/**
	 * 
	 * @description 查询购电方列表
	 * @return
	 * @author juguanghui
	 * @date 2014-1-23
	 */
	public List qryBuyerList(String term){
		List list = null;
		if(term == null || "".equals(term.trim()) || "null".equals(term.trim())){
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT BM.PARTICIPANTID, BM.PARTICIPANTNAME FROM BA_MARKETPARTICIPANT BM ");
			sql.append(" WHERE BM.PARTICIPANTTYPE IN ('0','2')");
			sql.append(" order by BM.PARTICIPANTNAME ");
			list = hibernateDao.executeSqlQuery(sql.toString());//@查询信息
//			return list;
		} else {
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT BM.PARTICIPANTID, BM.PARTICIPANTNAME FROM BA_MARKETPARTICIPANT BM ");
			sql.append(" WHERE BM.PARTICIPANTTYPE IN ('0','2') and BM.PARTICIPANTNAME like ?");
			sql.append(" order by BM.PARTICIPANTNAME ");
			list = hibernateDao.executeSqlQuery(sql.toString(),new Object[]{"%" + term + "%"});//@查询信息
			
		}
		return list;
	}
	
//	/**
//	 * 
//	 * @description 查询购电方列表
//	 * @return
//	 * @author juguanghui
//	 * @date 2014-1-23
//	 */
//	public List qryBuyerList(String term){
//		StringBuffer sql = new StringBuffer();
//		sql.append(" SELECT BM.PARTICIPANTID,BM.PARTICIPANTNAME FROM BA_MARKETPARTICIPANT BM WHERE BM.PARTICIPANTTYPE IN ('0','2') order by BM.PARTICIPANTNAME ");
//		List list = hibernateDao.executeSqlQuery(sql.toString());//@查询信息
//		return list;
//	}
	/**
	 * 
	 * @description 查询售电方列表
	 * @return
	 * @author gaoming
	 * @date 2014-1-23
	 */
	public List qrySellerList(){
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT BM.PARTICIPANTID,BM.PARTICIPANTNAME FROM BA_MARKETPARTICIPANT BM WHERE BM.PARTICIPANTTYPE IN ('0','1') order by BM.PARTICIPANTNAME ");
		List list = hibernateDao.executeSqlQuery(sql.toString());//@查询信息
		return list;
	}
	
	public QueryResultObject getDetailContractInfo(String id){
		CoContractbaseinfo coContractbaseinfo = (CoContractbaseinfo) hibernateDao.getObject(CoContractbaseinfo.class,id);
		CoContractbaseinfoVO vo = null;
		if (coContractbaseinfo != null) {
			vo = CoContractbaseinfoTransfer.toVO(coContractbaseinfo);
		}
		return RestUtils.wrappQueryResult(vo);
		
	}
    
	/**
	 * 获取树结构合同数据
	 * @description 方法描述
	 * @param nodeId
	 * @param treeid
	 * @return
	 * @author juguanghui
	 * @date 2014-02-09
	 */
    public List getContractTree(String nodeId,String treeid, String sceneType){
    	String str;
    	if("ideaContract".equalsIgnoreCase(sceneType)){
    		if(nodeId.length() == 0){	
    			str="SELECT cc.contractid,cc.contractname,(SELECT COUNT(*) FROM CO_CONTRACTBASEINFO aa"+
                     " WHERE aa.SUPEREXECID = cc.contractid and aa.isdelete='0') AS CHILDNUM ,cc.isdelete" +
                     " FROM co_contractbaseinfo cc  START WITH cc.CONTRACTID = ? " +
    				 " CONNECT BY PRIOR cc.SUPEREXECID = cc.CONTRACTID"; 
    			Object treeObj [] = {treeid};
    			List sqlList = hibernateDao.executeSqlQuery(str, treeObj);//@查询信息
    			List finList=new ArrayList();
    			for(int i=0;i<sqlList.size();i++){
    				Object[] obj = (Object[]) sqlList.get(i);
    				if(obj[3].toString().equals("0")){
    					finList.add(obj);
    					if(i>0){
    					finList.remove(i-1);
    					}
    				}else{
    					break;
    				}
    			}
    			return finList;
    		}else {
    			str="SELECT cc.contractid,cc.contractname,(SELECT COUNT(*) FROM CO_CONTRACTBASEINFO aa"+
                     " WHERE aa.SUPEREXECID = cc.contractid and aa.isdelete='0') AS CHILDNUM  "+
                     " FROM CO_CONTRACTBASEINFO cc WHERE CC.ISDELETE = '0' ";  
    			str += " AND cc.SUPEREXECID  = ?" ;
    			Object nodeObj [] = {nodeId};
    			List sqlList = hibernateDao.executeSqlQuery(str, nodeObj);//@查询信息
        		return sqlList;
    		}
    	} else if ("multiLevel".equalsIgnoreCase(sceneType)){
    		if(nodeId.length() == 0){	
    			str="SELECT cc.contractid,cc.contractname,(SELECT COUNT(*) FROM CO_CONTRACTBASEINFO aa"+
                     " WHERE aa.supercontractid = cc.contractid and aa.isdelete='0') AS CHILDNUM ,cc.isdelete" +
                     " FROM co_contractbaseinfo cc  START WITH cc.CONTRACTID = ? " +
    				 " CONNECT BY PRIOR cc.supercontractid = cc.CONTRACTID";
    			Object treeObj [] = {treeid};
    			List sqlList = hibernateDao.executeSqlQuery(str, treeObj);//@查询信息
    			List finList=new ArrayList();
    			for(int i=0;i<sqlList.size();i++){
    				Object[] obj = (Object[]) sqlList.get(i);
    				if(obj[3].toString().equals("0")){
    					finList.add(obj);
    					if(i>0){
    					finList.remove(i-1);
    					}
    				}else{
    					break;
    				}
    			}
    			return finList;
    		}else {
    			str="SELECT cc.contractid,cc.contractname,(SELECT COUNT(*) FROM CO_CONTRACTBASEINFO aa"+
                     " WHERE aa.supercontractid = cc.contractid and aa.isdelete='0') AS CHILDNUM  "+
                     " FROM CO_CONTRACTBASEINFO cc WHERE CC.ISDELETE = '0' ";  
    			str += " AND cc.supercontractid  = ? " ;
    			Object nodeObj [] = {nodeId};
    			List sqlList = hibernateDao.executeSqlQuery(str, nodeObj);//@查询信息
        		return sqlList;
			}
    	}
		return null;
    }
    
    /**
	 * 设置
	 * @description 方法描述
	 * @return
	 * @author juguanghui
	 * @date 2014-02-08
	 */
	public String setUp(String params, HttpServletRequest request){
		Map map = null;
		try {
			map = JsonUtils.getObjectMapper().readValue(params, Map.class);
			String contractid = map.get("contractid")==null?"":map.get("contractid").toString();//@获取合同id
			String objIdUp = map.get("objIdUp")==null?"":map.get("objIdUp").toString();//@获取父合同的合同id
			
			if(isContractInRelation(objIdUp, contractid)){//判断要关联的合同是否已经存在合同上下级关系)
				return "inRelation";
			}
			
			CoContractbaseinfo coContractbaseinfo = (CoContractbaseinfo) hibernateDao.getObject(CoContractbaseinfo.class,contractid);//@根据合同id查询合同基本信息
			coContractbaseinfo.setSuperexecid(objIdUp);
			hibernateDao.updateObject(coContractbaseinfo);//@更新合同信息
			
			verticalIntegration(coContractbaseinfo, request);//纵向传输
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	
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
//		System.out.println(vir.isSuc());
	}
	
	/**
	 * 设置
	 * @description 方法描述
	 * @return
	 * @author juguanghui
	 * @date 2014-02-08
	 */
	public String setDown(String params, HttpServletRequest request){
		Map map = null;
		try {
			map = JsonUtils.getObjectMapper().readValue(params, Map.class);
			String contractid = map.get("contractid")==null?"":map.get("contractid").toString();//@获取合同id
			String objIdUp = map.get("objIdUp")==null?"":map.get("objIdUp").toString();//@获取父合同的合同id
			
			if(isContractInRelation(objIdUp, contractid)){//判断要关联的合同是否已经存在合同上下级关系)
				return "inRelation";
			}
			
			CoContractbaseinfo coContractbaseinfo = (CoContractbaseinfo) hibernateDao.getObject(CoContractbaseinfo.class,contractid);//@根据合同id查询合同基本信息
			coContractbaseinfo.setSuperexecid(objIdUp);
			hibernateDao.updateObject(coContractbaseinfo);//@更新合同信息
			verticalIntegration(coContractbaseinfo, request);//纵向传输
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}


	public QueryResultObject cancleMultiLevelRelation(String id,
			String isRootNode) {
		if("true".equals(isRootNode)){
			String sql = "select contractid,supercontractid from co_contractbaseinfo where supercontractid = ?";
			Object [] params = {id};
			List list = hibernateDao.executeSqlQuery(sql, params);
			if(list != null && list.size() != 0){
				for(int i = 0; i<list.size(); i++){
					Object [] obj = (Object [])list.get(i);
					String contractid = obj[0] == null?"":obj[0].toString();
					CoContractbaseinfo coContractbaseinfo = (CoContractbaseinfo) hibernateDao.getObject(CoContractbaseinfo.class, contractid);
					coContractbaseinfo.setSupercontractid("");
					hibernateDao.updateObject(coContractbaseinfo);
				}
			}
		} else {
			CoContractbaseinfo coContractbaseinfo = (CoContractbaseinfo) hibernateDao.getObject(CoContractbaseinfo.class, id);
			coContractbaseinfo.setSupercontractid("");
			hibernateDao.updateObject(coContractbaseinfo);
		}
		return null;
	}
	
	public ArrayList<TreeNode> getContractTreeRoot(String contractid, String sceneType) {
		String treeSQL = getTreeRootSQL(sceneType);
		List list = hibernateDao.executeSqlQuery(treeSQL, new Object[]{contractid,contractid});
		
		ArrayList<TreeNode> nodelist = new ArrayList<TreeNode>();
		TreeNode rootNode = new TreeNode();
		if(list != null && list.size() != 0){
			for(int i = 0; i < list.size(); i++){
				TreeNode node = new TreeNode();
				Object [] obj = (Object []) list.get(i);
				node.setId(validateString(obj[0])); //主键的get方法
				boolean hasChild = hasChild(contractid, sceneType);
				node.setHasChildren(hasChild);
				node.setText(validateString(obj[1]));//显示字段的get方法
				node.setItemType(sceneType); //根节点的itemType
				nodelist.add(node);
			}
		}
		return nodelist;
	}
	
	private boolean hasChild(String contractid, String sceneType){
		String sql = null;
		if("ideaContract".equals(sceneType)){
			sql = "SELECT cc.contractid, cc.contractname, cc.superexecid FROM co_contractbaseinfo cc where cc.superexecid = ?";
		} else if ("multiLevel".equals(sceneType)){
			sql = "SELECT cc.contractid, cc.contractname, cc.superexecid FROM co_contractbaseinfo cc where cc.supercontractid = ?";
		}
		List list = hibernateDao.executeSqlQuery(sql, new Object[]{contractid});
		if(list != null && list.size() != 0){
			return true;
		} else {
			return false;
		}
	}
	
	private String getTreeRootSQL(String sceneType){
		StringBuffer bf = new StringBuffer();
		if("ideaContract".equals(sceneType)){
			bf.append("select contractid, contractname, superexecid from  ");
			bf.append("( ");
			bf.append("SELECT cc.contractid, cc.contractname, cc.superexecid ");
			bf.append("FROM co_contractbaseinfo cc ");
			bf.append("START WITH cc.CONTRACTID = ? ");
			bf.append("CONNECT BY PRIOR cc.SUPEREXECID = cc.CONTRACTID ");
			bf.append("union ");
			bf.append("SELECT cc.contractid, cc.contractname, cc.superexecid ");
			bf.append("FROM co_contractbaseinfo cc ");
			bf.append("START WITH cc.CONTRACTID = ? ");
			bf.append("CONNECT BY PRIOR cc.CONTRACTID = cc.SUPEREXECID ");
			bf.append(") ");
			bf.append("where superexecid is null ");
		} else if("multiLevel".equals(sceneType)){
			bf.append("select contractid, contractname, superexecid from  ");
			bf.append("( ");
			bf.append("SELECT cc.contractid, cc.contractname, cc.superexecid ");
			bf.append("FROM co_contractbaseinfo cc ");
			bf.append("START WITH cc.CONTRACTID = ? ");
			bf.append("CONNECT BY PRIOR cc.SUPEREXECID = cc.CONTRACTID ");
			bf.append("union ");
			bf.append("SELECT cc.contractid, cc.contractname, cc.superexecid ");
			bf.append("FROM co_contractbaseinfo cc ");
			bf.append("START WITH cc.CONTRACTID = ? ");
			bf.append("CONNECT BY PRIOR cc.CONTRACTID = cc.SUPEREXECID ");
			bf.append(") ");
			bf.append("where superexecid is null ");
		}
		return bf.toString();
	}
	public Collection<? extends TreeNode> getNodesList(String contractid, String sceneType) {
		List<TreeNode> nodelist = new ArrayList<TreeNode>();
		
		String sql = null;
		if("ideaContract".equals(sceneType)){
			sql = "SELECT cc.contractid, cc.contractname, cc.superexecid FROM co_contractbaseinfo cc where cc.superexecid = ?";
		} else if ("multiLevel".equals(sceneType)){
			sql = "SELECT cc.contractid, cc.contractname, cc.superexecid FROM co_contractbaseinfo cc where cc.supercontractid = ?";
		}
		List list = hibernateDao.executeSqlQuery(sql, new Object[]{contractid});
		
		if(list != null && list.size() != 0){
			boolean hasChild=false;
			for (int i = 0; i < list.size(); i++) {
				Object [] obj = (Object [])list.get(i);
				TreeNode treeNode = new TreeNode();
				treeNode.setId(validateString(obj[0]));
			    hasChild = hasChild(validateString(obj[0]), sceneType);
				treeNode.setHasChildren(hasChild);
		        treeNode.setText(validateString(obj[1]));	//显示字段
				treeNode.setItemType(sceneType);
				nodelist.add(treeNode);
			}
			return nodelist;
		}
		return nodelist;
	}
}
