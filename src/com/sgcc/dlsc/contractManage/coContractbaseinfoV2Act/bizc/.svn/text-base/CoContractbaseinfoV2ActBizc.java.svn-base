package com.sgcc.dlsc.contractManage.coContractbaseinfoV2Act.bizc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.sgcc.dlsc.commons.util.ComUtils;
import com.sgcc.dlsc.commons.util.DateUtil;
import com.sgcc.dlsc.commons.util.Guid;
import com.sgcc.dlsc.commons.util.ToolsSys;
import com.sgcc.dlsc.contractManage.coContractbaseinfoV2Act.vo.CoContractbaseinfoTransfer;
import com.sgcc.dlsc.contractManage.coContractbaseinfoV2Act.vo.CoContractbaseinfoVO;
import com.sgcc.dlsc.contractManage.contractmonthmanage.bizc.ContractmonthmanageBizc;
import com.sgcc.dlsc.entity.po.CoContractaccessory;
import com.sgcc.dlsc.entity.po.CoContractbackupinfo;
import com.sgcc.dlsc.entity.po.CoContractbaseinfo;
import com.sgcc.dlsc.entity.po.CoContractchangerecordinfo;
import com.sgcc.dlsc.entity.po.CoContractenergyinfo;
import com.sgcc.dlsc.entity.po.CoContractgateinfo;
import com.sgcc.dlsc.entity.po.CoContransenergyinfo;
import com.sgcc.dlsc.entity.po.CoContranslossinfo;
import com.sgcc.dlsc.entity.po.CoTransqtyinfo;
import com.sgcc.dlsc.entity.po.CoTransqtyslaveinfo;
import com.sgcc.uap.bizc.sysbizc.datadictionary.IDataDictionaryBizC;
import com.sgcc.uap.persistence.IHibernateDao;
import com.sgcc.uap.persistence.criterion.QueryCriteria;
import com.sgcc.uap.rest.support.IDRequestObject;
import com.sgcc.uap.rest.support.QueryFilter;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;
import com.sgcc.uap.rest.utils.CrudUtils;
import com.sgcc.uap.rest.utils.RestUtils;
/**
 * 用户定义逻辑构件
 * 
 * @author zhen 
 * 
 */
public class CoContractbaseinfoV2ActBizc implements ICoContractbaseinfoV2ActBizc{

	@Autowired
	private IHibernateDao dao;
	
	@Autowired
	private IDataDictionaryBizC dataDictionaryBizC;
	
	
	
	 /**
	 * 获取合同终止前的合同流转状态
	 * @throws  
	 * @author wewu
	 */
	public String getFlowState(String coContractId){
		
		String sql = " select decode(t.flowflag,null,0,flowflag) from CO_ContractChangeRecordInfo t where t.contractid = ?  and rownum=1 order by t.updatetime desc ";
		List list = dao.executeSqlQuery(sql, new Object[]{coContractId});
		if(list==null||list.size()==0){
			return "0";
		}
		return list.get(0).toString();
	}
	
	/**
	 * 重置终止合同
	 * isdelete 改为 : 0  isend 改为：0
	 * @return true/false
	 * 
	 * @author wewu
	 */
	public boolean update(CoContractbaseinfo coContractbaseinfo) {
		dao.saveOrUpdateObject(coContractbaseinfo);
		return true;
	}
	
	/**
	 * 根据主键查询合同
	 * checkedId 主键编号
	 * @return CoContractbaseinfo
	 * @throws  
	 * @author wewu
	 */
	public CoContractbaseinfo findById(String checkedId){
		CoContractbaseinfo ccb = dao.getObject(CoContractbaseinfo.class, checkedId);
		return ccb;
	}
	
	
	/**
	 * 
	 * @description 统计信息
	 * @param marketid
	 * @param contracttype
	 * @param searchDateType
	 * @param startDate
	 * @param endDate
	 * @param contractcyc
	 * @param prepcontractflag
	 * @param purchaser
	 * @param seller
	 * @param sequenceid
	 * @param flowflag
	 * @param marketId
	 * @param date
	 * @param papercontractcode
	 * @param syscontractcode
	 * @return
	 * @author zhangzhen
	 * @date 2014-1-26
	 */
	public String[] getCoStatistics12(String contractName,String marketid, String contracttype,
			String searchDateType, String startDate, String endDate,
			String contractcyc, String prepcontractflag, String purchaser,
			String seller, String sequenceid, String flowflag, String marketId2,
			String date, String papercontractcode, String syscontractcode,String powertype) {
			String searchField = "CC.CONTRACTSTARTDATE";//合同开始日期
			if ("1".equals(searchDateType)) {//合同信息录入日期
				searchField = "CC.UPDATETIME";
			}else if ("2".equals(searchDateType)) {//合同签订日期
				searchField = "CC.SIGNEDDATE";
			}
			double[] retValue = new double[10];
			String[] retVal = new String[10];
			
			// Edit by WuYujian-- add params[papercontractcode, syscontractcode]
			List<Object[]> list1  = getNum(contractName,marketid,date, contracttype, searchField, startDate, endDate, contractcyc, prepcontractflag, purchaser, seller,sequenceid,flowflag,marketId2,papercontractcode,syscontractcode,powertype);
			retValue[1]=0;retValue[2]=0;retValue[3]=0;retValue[4]=0;
			String name = "";
			for(int m=0;m<list1.size();m++){
				Object[] obj = (Object[])list1.get(m);
				name = obj[1]==null?"":obj[1].toString();
				if(name.equals("已备案")){
					retValue[1] = obj[0]==null?0:Double.parseDouble(obj[0].toString());
				}else if(name.equals("已签订")){
					retValue[2] = obj[0]==null?0:Double.parseDouble(obj[0].toString());
				}else if(name.equals("起草中")){
					retValue[4] = obj[0]==null?0:Double.parseDouble(obj[0].toString());
				}else{
					retValue[3] = obj[0]==null?0:Double.parseDouble(obj[0].toString());
				}
			}
			
			// Edit by WuYujian-- add params[papercontractcode, syscontractcode]
			List<Object[]> list2 = getSumCount(contractName,marketid, contracttype, searchField, startDate, endDate, contractcyc, prepcontractflag, purchaser, seller,sequenceid,flowflag,marketId2,papercontractcode,syscontractcode,powertype);
			if(list2!=null && list2.size()>0){
				Object obj = (Object)list2.get(0);
				retValue[0] = obj==null?0:Double.parseDouble(obj.toString());//合同总数
			}
			
			// Edit by WuYujian-- add params[papercontractcode, syscontractcode]
			List<Object[]> list = getCount(contractName,marketid, contracttype, searchField, startDate, endDate, contractcyc, prepcontractflag, purchaser, seller,sequenceid,flowflag,marketId2,papercontractcode,syscontractcode,powertype);
			if(list != null && list.size() > 0){
				double price = 0,count = 0,avgprice = 0,sumprice = 0,sumcount=0,ysum=0,yysum=0,maxprice=0,minprice=0;
				Object[] obj = (Object[])list.get(0);
	//			retValue[1] = obj[0]==null?0: Double.parseDouble(obj[0].toString());//已备案
	//			retValue[2] = obj[1]==null?0:Double.parseDouble(obj[1].toString());//已签订
	//			retValue[3] = obj[2]==null?0:Double.parseDouble(obj[2].toString());//流转中
	//			retValue[4] = obj[3]==null?0:Double.parseDouble(obj[3].toString());//起草中
				ysum = obj[0]==null?0:Double.parseDouble(obj[0].toString());//应签合同总电量
	//			ysum = obj[0]==null?0:(new BigDecimal(obj[0].toString())).floatValue();//应签合同总电量
				yysum = obj[1]==null?0:Double.parseDouble(obj[1].toString());//已签合同总电量
				maxprice = obj[2]==null?0:Double.parseDouble(obj[2].toString());//最高电价
				minprice = obj[3]==null?0:Double.parseDouble(obj[3].toString());//最低电价
//				retValue[5] = Math.round(ysum*1000)/1000.0;
//				retValue[6] = Math.round(yysum*1000)/1000.0;
				retValue[5] = Math.round(ysum);
				retValue[6] = Math.round(yysum);
				retValue[7] = Math.round(maxprice * 10)/10.0;
				retValue[8] = Math.round(minprice*10)/10.0;
//				retValue[7] = obj[2]==null?0:Double.parseDouble(obj[2].toString());//最高电价
//				retValue[8] = obj[3]==null?0:Double.parseDouble(obj[3].toString());//最低电价
				
				
				// Edit by WuYujian-- add params[papercontractcode, syscontractcode]
				List sqllist = getPrice(contractName,marketid, contracttype, searchField, startDate, endDate, contractcyc, prepcontractflag, purchaser, seller,sequenceid,flowflag,marketId2,papercontractcode,syscontractcode,powertype);
				for(int i=0;i<sqllist.size();i++){
					if(sqllist.get(i) != null){
						avgprice = ((BigDecimal)sqllist.get(i)).doubleValue();
					}
				
				}
//				for(int i=0;i<sqllist.size();i++){
//					Object[] value = (Object[])sqllist.get(i);
//					count = value[0]==null?0:Double.parseDouble(obj[0].toString());
//					price = value[1]==null?0:Double.parseDouble(value[1].toString());
//					sumprice = count * price +sumprice;
//					sumcount = count + sumcount;
//				}
//				if(sumcount == 0){
//					avgprice = 0;
//				}else{
//					avgprice = sumprice/sumcount;
//				}
				retValue[9] = Math.round(avgprice * 10)/10.0;
				//retValue[0] = obj[4]==null?0:Double.parseDouble(obj[4].toString());//合同总数
			}
			java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
			nf.setGroupingUsed(false);
			for (int i = 0; i < retValue.length; i++) {
				retVal[i]=nf.format(retValue[i]);
			}
		return retVal;
	}


		
	/**
	 * 
	 * @description 合同导出的查询功能
	 * @param marketid
	 * @param contracttype
	 * @param searchDateType
	 * @param startDate
	 * @param endDate
	 * @param contractcyc
	 * @param prepcontractflag
	 * @param purchaser
	 * @param seller
	 * @param sequenceid
	 * @param flowflag
	 * @param marketid1
	 * @param contractName
	 * @param papercontractcode
	 * @param syscontractcode
	 * @return
	 * @author zhangzhen
	 * @date 2014-1-24
	 */
	public List findCContractList(String marketid, String contracttype,
			String searchDateType, String startDate, String endDate,
			String contractcyc, String prepcontractflag, String purchaser,
			String seller, String sequenceid, String flowflag, String marketid1,
			String contractName, String papercontractcode, String syscontractcode,String powertype) {
		
			String searchField = "CC.CONTRACTSTARTDATE";//合同开始日期
			if ("1".equals(searchDateType)) {//合同信息录入日期
				searchField = "CC.UPDATETIME";
			}else if ("2".equals(searchDateType)) {//合同签订日期
				searchField = "CC.SIGNEDDATE";
			}
			
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT CC.CONTRACTID,CC.PAPERCONTRACTCODE,CC.CONTRACTNAME, CCT.TYPENAME,BM.PARTICIPANTNAME AS PP, BMM.PARTICIPANTNAME AS QQ, ");
			//两个时间格式添加
			sql.append(" TO_CHAR(CC.CONTRACTSTARTDATE, 'YYYY-MM-DD'),TO_CHAR(CC.CONTRACTENDDATE, 'YYYY-MM-DD'),  ");	
			//一个时间格式添加
			sql.append(" CC.CONTRACTQTY,CC.CONTRACTPRICE,T.NAME AS CC,TTTT.NAME AS DD,'' ORGAN_NAME,TO_CHAR(CC.UPDATETIME, 'YYYY-MM-DD HH24:MI:SS'),TTTTT.MARKETNAME AS EE");
			sql.append(" FROM  CO_CONTRACTBASEINFO CC LEFT JOIN  CO_CONTRACTBUSIFLOWINFO CCB ");
			sql.append(" ON CC.CONTRACTID = CCB.CONTRACTID LEFT JOIN   ba_gencode T ON T.type='67' AND CC.flowflag = T.VALUE ");
			sql.append(" LEFT JOIN  CO_CONTRACTTYPEINFO CCT ON CC.CONTRACTTYPE = CCT.CONTRACTTYPEID ");
			sql.append(" LEFT JOIN  BA_MARKETPARTICIPANT BM ON CC.PURCHASER = BM.PARTICIPANTID  ");
			sql.append(" LEFT JOIN  BA_MARKETPARTICIPANT BMM ON CC.SELLER = BMM.PARTICIPANTID ");
			sql.append(" LEFT JOIN  ba_gencode TT ON TT.TYPE='26'  AND CC.BACKUPSTATE = TT.VALUE ");
			sql.append(" LEFT JOIN  ba_gencode TTT ON TTT.TYPE='24' AND CC.SIGNSTATE = TTT.VALUE ");
			sql.append(" LEFT JOIN  ba_gencode TTTT ON TTTT.TYPE='27' AND CC.PREPCONTRACTFLAG = TTTT.VALUE ");
			//sql.append(" LEFT JOIN SGSOTOWER.PUB_ORGAN PO ON CC.UPDATEPERSONID = PO.ORGAN_CODE ");
			sql.append(" LEFT JOIN  BA_MARKET TTTTT ON CC.MARKETID = TTTTT.MARKETID ");
			sql.append(" LEFT JOIN  CO_CONTRACTENERGYINFO CCTE ON CC.CONTRACTID = CCTE.CONTRACTID AND CCTE.PERIOD='1'");
			sql.append(" left join  co_contractaffixinfo ccai on cc.contractid = ccai.contractid");
			sql.append(" WHERE 1=1 ");
//			if(!ToolsSys.isnull(marketid)){
//				sql.append(" AND (CC.ISDELETE = '0' ");
//				sql.append(" AND CC.MARKETID='").append(marketid1).append("' ");
//				sql.append(" or CC.contractid in (select cbs.dataid from  co_contractbaseinfo_share cbs where cbs.marketid='"+marketid1+"'))");
//			}else{
//				sql.append(" AND (CC.ISDELETE = '0' ");
//				if(ToolsSys.isnull(marketid1)){//市场
//					if(!"91812".equals(marketid1)){//增加国网总部权限，能够查到各分部、省公司签订、录入情况。lvwentao确认100%修改2013-11-25
//						if(marketid.equals(marketid1)){
//							sql.append(" AND CC.MARKETID ='").append(marketid).append("' )");
//						}else{
//							sql.append(" AND CC.contractid in (select cbs.dataid from  co_contractbaseinfo_share cbs where cbs.smarketid='"+marketid+"' AND cbs.marketid='"+marketid1+"'))");
//						}
//					}
//					else{
//						sql.append(")");
//					}
//				}
//			}
			
			if(!ToolsSys.isnull(marketid)){
//				sql.append(" AND (CC.ISDELETE = '0' ");
//				sql.append(" AND CC.MARKETID='").append(marketid1).append("') ");
				sql.append(" AND (CC.MARKETID='").append(marketid1).append("') ");
			}else{
//				sql.append(" AND (CC.ISDELETE = '0' ");
//				sql.append(" AND CC.MARKETID='").append(marketid).append("') ");
				sql.append(" AND (CC.MARKETID='").append(marketid).append("') ");
				
			}
			
			if(ToolsSys.isnull(contracttype)){//合同类型 
				if(!ToolsSys.isnull(marketid)){
					sql.append(" and (cc.contracttype = '").append(contracttype).append("' or cc.contracttype in (select c.contracttypeid from  co_contracttypeinfo c where c.effectiveflag = '0' and  c.MARKETID='").append(marketid1);
					sql.append("' connect by prior c.contracttypeid = c.supertypeid start with c.contracttypeid = '").append(contracttype).append("'))");
				
				}else{
					sql.append(" and (cc.contracttype = '").append(contracttype).append("' or cc.contracttype in (select c.contracttypeid from  co_contracttypeinfo c where c.effectiveflag = '0' and  c.MARKETID='").append(marketid);
					sql.append("' connect by prior c.contracttypeid = c.supertypeid start with c.contracttypeid = '").append(contracttype).append("'))");
				}
			}
			
			//开始时间不为空，结束时间为空时，结束日期小于开始日期
			if(ToolsSys.isnull(startDate) && !ToolsSys.isnull(endDate)){//@判断是否为空
				sql.append(" AND TO_CHAR(").append ("CC.CONTRACTENDDATE").append(",'YYYY-MM-DD') >= '").append(startDate).append("' ");
			}else if(!ToolsSys.isnull(startDate) && ToolsSys.isnull (endDate)){//@判断是否为空
				sql.append(" AND TO_CHAR(").append ("CONTRACTSTARTDATE").append(",'YYYY-MM-DD') <= '").append(endDate).append("' ");
			}else if(ToolsSys.isnull(startDate) && ToolsSys.isnull (endDate)){//@判断是否为空
				sql.append(" AND (TO_CHAR(").append ("CC.CONTRACTENDDATE").append(",'YYYY-MM-DD') >= '").append(startDate).append("' AND  TO_CHAR(").append

		("CC.CONTRACTSTARTDATE").append(",'YYYY-MM-DD') <= '").append (endDate).append("') ");
			}
			if(ToolsSys.isnull(contractcyc)){//合同周期
				sql.append(" AND CC.CONTRACTCYC ='").append(contractcyc).append("' ");
			}
			if(ToolsSys.isnull(prepcontractflag)){//合同状态
				sql.append(" AND CC.PREPCONTRACTFLAG ='").append(prepcontractflag).append("' ");
			}
			if(ToolsSys.isnull(purchaser)){//购电方
				sql.append(" AND CC.PURCHASER = '").append(purchaser).append("' ");
			}
			if(ToolsSys.isnull(seller)){//售电方
				sql.append(" AND CC.SELLER ='").append(seller).append("' ");
			}
			if(ToolsSys.isnull(sequenceid)){//合同序列
				sql.append(" and CC.SEQUENCEID = '").append(sequenceid).append("' ");
			}
			if(ToolsSys.isnull(flowflag)){//@判断是否为空
				if(flowflag.equals("6")){
					sql.append(" and cc.flowflag in ( ").append(flowflag).append(" ) ");
				}else {
					sql.append(" and cc.ISDELETE = '0' ");
					sql.append(" and cc.flowflag in ( ").append(flowflag).append(" ) ");
				}
				
			}else{
				sql.append(" and cc.ISDELETE = '0' ");
			}
			if(ToolsSys.isnull(contractName)){//@判断是否为空
				sql.append(" and cc.contractName like '%").append(contractName).append("%' ");
			}
			
			// ================= Begin =================
			// ============ Author:WuYujian ============
			if(ToolsSys.isnull(papercontractcode)){//纸质合同编号
				sql.append(" and cc.papercontractcode like '%").append(papercontractcode).append("%' ");
			}
			
			if(ToolsSys.isnull(syscontractcode)){//系统合同编号
				sql.append(" and cc.expend5 like '%").append(syscontractcode).append("%' ");
			}
			
			if(ToolsSys.isnull(powertype)){//售电方发电类型
				sql.append(" and CC.SELLER in ( select b.participantid from BA_MARKETPARTICIPANT b where  b.powertype =").append(powertype).append(" ) ");
			}
			
			// ================= End =================
			
			sql.append(" group by CC.CONTRACTID, CC.PAPERCONTRACTCODE, CC.CONTRACTNAME,");
			sql.append(" CCT.TYPENAME, BM.PARTICIPANTNAME,BMM.PARTICIPANTNAME ,CC.CONTRACTSTARTDATE,CC.CONTRACTENDDATE, CC.CONTRACTQTY, TT.NAME,TTT.NAME,T.NAME,TTTT.NAME,CC.UPDATETIME,TTTTT.MARKETNAME,CC.MARKETID,CC.CONTRACTPRICE,CC.CONTRACTTYPE,BMM.powertype ORDER BY BMM.powertype,nlssort(BMM.PARTICIPANTNAME, 'NLS_SORT = SCHINESE_PINYIN_M')  nulls last,CC.PAPERCONTRACTCODE");
			// sql.append(" ORDER BY CC.UPDATETIME DESC ");// Edit by WuYujian
			// 增加排序的SQL语句
	//		if (ToolsSys.isnull(orderField) && ToolsSys.isnull(orderType)) {
	//			sql.append(" ORDER BY " + orderField + " " + orderType);
	//		}
			return dao.executeSqlQuery(sql.toString());
		
	}

	

	/**
	 * 
	 * @description 查询合同序列
	 * @param marketid
	 * @return
	 * @author zhangzhen
	 * @date 2014-1-23
	 */
	public List querySequence(String marketid,String contracttype) {
		if(contracttype!=null && !"".equals(contracttype)){
			String[] contracttypes = contracttype.split(",");
			for(int i=0;i<contracttypes.length;i++){
				if(i==0){
					contracttype = "'"+contracttypes[i]+"'";
				}else {
					contracttype = contracttype + ",'"+contracttypes[i]+"'";
				}
			}
		}
		String sql = "select ccsi.sequenceid,ccsi.sequencename from  Co_Contracsequenceinfo ccsi where 1=1";
		if(marketid!=null && !"".equals(marketid)){
			sql += " and ccsi.marketid = '"+marketid+"'";
		}
		if(contracttype!=null && !"".equals(contracttype)){
			sql += " and ccsi.sequenceid in (select a.sequenceid from CO_TYPESEQUENCERELATION a where a.contracttypeid in ("+contracttype+"))" ;
		}
		sql += " order by  ccsi.updatetime desc nulls last";
		return dao.executeSqlQuery(sql);
	}

	/**
	 * 
	 * @description 得到准入成员(类型：1,购电方，2,售点方，3,输电方)
	 * @param marketId
	 * @param displayType
	 * @param coType
	 * @return
	 * @author zhangzhen
	 * @date 2014-1-22
	 */
	public List getParticipantBySel(String marketId, int displayType,
			String coType) {
		String sql = "SELECT CCT.PARTICIPANTID, BMP.PARTICIPANTNAME FROM  CO_CONTRACTMEMBERSHIP CCT,  BA_MARKETPARTICIPANT BMP WHERE CCT.PARTICIPANTID = BMP.PARTICIPANTID AND CCT.DISPLAYTYPE=? ";
		if (ToolsSys.isnull(marketId)) {
			sql = sql + " AND CCT.MARKETID='"+marketId+"'";
		}
		if (ToolsSys.isnull(coType)) {
			sql = sql + " AND CCT.CONTRACTTYPEID='"+coType+"'";
		}
		sql = sql + " group by CCT.PARTICIPANTID, BMP.PARTICIPANTNAME" + " order by NLSSORT(BMP.PARTICIPANTNAME,'NLS_SORT=SCHINESE_PINYIN_M')";//Edit by WuYujian
		return dao.executeSqlQuery(sql,new Object[]{displayType});
	}
	
	
	/**
	  * 合同附件维护-查询
	 * @param pageIndex(第几页)
	 * @param pageSize(总共几页)
	 * @param marketid
	 * @param contracttype
	 * @param searchDateType
	 * @param startDate
	 * @param endDate
	 * @param contractcyc
	 * @param prepcontractflag
	 * @param purchaser
	 * @param seller
	 * @param sequenceid
	 * @param marketid1
	 * @return
	 */
	public QueryResultObject findFjContractList(int pageIndex,int pageSize,
			String marketid, String contracttype, String searchDateType,String flowflag,
			String startDate, String endDate, String contractcyc,
			String prepcontractflag, String purchaser, String seller,
			String sequenceid,String marketid1) {
		String searchField = "CC.CONTRACTSTARTDATE";//合同开始日期
		if ("1".equals(searchDateType)) {//合同信息录入日期
			searchField = "CC.UPDATETIME";
		}else if ("2".equals(searchDateType)) {//合同签订日期
			searchField = "CC.SIGNEDDATE";
		}
		
		QueryResultObject result = new QueryResultObject();//新建返回值对象
		int size = 0;	//定义数据条数
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT CC.CONTRACTID,CC.PAPERCONTRACTCODE,CC.CONTRACTNAME,CCT.TYPENAME,BM.PARTICIPANTNAME AS PP, ");
		sql.append(" BMM.PARTICIPANTNAME AS QQ ,CC.CONTRACTSTARTDATE,CC.CONTRACTENDDATE,CC.CONTRACTQTY,TT.NAME AS AA ,TTT.NAME AS BB, ");
		sql.append(" T.NAME AS CC ,TTTT.NAME AS DD,'' ORGAN_NAME,CC.UPDATETIME,TTTTT.NAME AS EE,CCTE.PRICE,count(ccai.affixname),CC.CONTRACTTYPE");
		sql.append(" FROM  CO_CONTRACTBASEINFO CC LEFT JOIN  CO_CONTRACTBUSIFLOWINFO CCB ");
		sql.append(" ON CC.CONTRACTID = CCB.CONTRACTID LEFT JOIN   ba_gencode T ON T.type='67' AND CC.flowflag = T.VALUE ");
		sql.append(" LEFT JOIN  CO_CONTRACTTYPEINFO CCT ON CC.CONTRACTTYPE = CCT.CONTRACTTYPEID ");
		sql.append(" LEFT JOIN  BA_MARKETPARTICIPANT BM ON CC.PURCHASER = BM.PARTICIPANTID  ");
		sql.append(" LEFT JOIN  BA_MARKETPARTICIPANT BMM ON CC.SELLER = BMM.PARTICIPANTID ");
		sql.append(" LEFT JOIN  ba_gencode TT ON TT.TYPE='26'  AND CC.BACKUPSTATE = TT.VALUE ");
		sql.append(" LEFT JOIN  ba_gencode TTT ON TTT.TYPE='24' AND CC.SIGNSTATE = TTT.VALUE ");
		sql.append(" LEFT JOIN  ba_gencode TTTT ON TTTT.TYPE='27' AND CC.PREPCONTRACTFLAG = TTTT.VALUE ");
		//sql.append(" LEFT JOIN SGSOTOWER.PUB_ORGAN PO ON CC.UPDATEPERSONID = PO.ORGAN_CODE ");
		sql.append(" LEFT JOIN  ba_gencode TTTTT ON TTTTT.type='1' AND CC.MARKETID = TTTTT.VALUE ");
		sql.append(" LEFT JOIN  CO_CONTRACTENERGYINFO CCTE ON CC.CONTRACTID = CCTE.CONTRACTID AND CCTE.PERIOD='1'");
		sql.append(" left join  co_contractaffixinfo ccai on cc.contractid = ccai.contractid");
		sql.append(" WHERE 1=1 ");
//		if(!ToolsSys.isnull(marketid)){
//			sql.append(" AND (CC.ISDELETE = '0' ");
//			sql.append(" AND CC.MARKETID='").append(marketid1).append("' ");
//			sql.append(" or CC.contractid in (select cbs.dataid from  co_contractbaseinfo_share cbs where cbs.marketid='"+marketid1+"'))");
//			//sql.append(" and cc.contractid not in (select ccc.contractid from  co_contractchangerecordinfo ccc where ccc.marketid='"+marketid1+"'))");
//		}else{
//			sql.append(" AND (CC.ISDELETE = '0' ");
//			if(ToolsSys.isnull(marketid1)){//市场
//				if(marketid.equals(marketid1)){
//					sql.append(" AND CC.MARKETID ='").append(marketid).append("' )");
//				}else{
//					sql.append(" AND CC.contractid in (select cbs.dataid from  co_contractbaseinfo_share cbs where cbs.smarketid='"+marketid+"' AND cbs.marketid='"+marketid1+"'))");
//				}
//			}
////			sql.append(" or CC.contractid in (select cbs.dataid from  co_contractbaseinfo_share cbs where cbs.marketid='"+marketid+"')");
////			sql.append(" and cc.contractid not in (select ccc.contractid from  co_contractchangerecordinfo ccc where ccc.marketid='"+marketid+"'))");
//		}
		
		if(!ToolsSys.isnull(marketid)){
			sql.append(" AND (CC.ISDELETE = '0' ");
			sql.append(" AND CC.MARKETID='").append(marketid1).append("') ");
		}else{
			sql.append(" AND (CC.ISDELETE = '0' ");
			sql.append(" AND CC.MARKETID='").append(marketid).append("') ");
			
		}
		
		if(ToolsSys.isnull(contracttype)){//合同类型
			sql.append(" AND CC.CONTRACTTYPE = '").append(contracttype).append("' ");
		}
		//开始时间不为空，结束时间为空时，结束日期小于开始日期
		if(ToolsSys.isnull(startDate) && !ToolsSys.isnull(endDate)){//@判断是否为空
			sql.append(" AND TO_CHAR(").append ("CC.CONTRACTENDDATE").append(",'YYYY-MM-DD') >= '").append(startDate).append("' ");
		}else if(!ToolsSys.isnull(startDate) && ToolsSys.isnull (endDate)){//@判断是否为空
			sql.append(" AND TO_CHAR(").append ("CONTRACTSTARTDATE").append(",'YYYY-MM-DD') <= '").append(endDate).append("' ");
		}else if(ToolsSys.isnull(startDate) && ToolsSys.isnull (endDate)){//@判断是否为空
			sql.append(" AND (TO_CHAR(").append ("CC.CONTRACTENDDATE").append(",'YYYY-MM-DD') >= '").append(startDate).append("' AND  TO_CHAR(").append

	("CC.CONTRACTSTARTDATE").append(",'YYYY-MM-DD') <= '").append (endDate).append("') ");
		}
		if(ToolsSys.isnull(contractcyc)){//合同周期
			sql.append(" AND CC.CONTRACTCYC ='").append(contractcyc).append("' ");
		}
		if(ToolsSys.isnull(prepcontractflag)){//合同状态
			sql.append(" AND CC.PREPCONTRACTFLAG ='").append(prepcontractflag).append("' ");
		}
		if(ToolsSys.isnull(flowflag)){//@判断是否为空
			if(flowflag.equals("6")){
				sql.append(" and cc.flowflag in ( ").append(flowflag).append(" ) ");
			}else {
				sql.append(" and cc.ISDELETE = '0' ");
				sql.append(" and cc.flowflag in ( ").append(flowflag).append(" ) ");
			}
			
		}else{
			sql.append(" and cc.ISDELETE = '0' ");
		}
		if(ToolsSys.isnull(purchaser)){//购电方
			sql.append(" AND CC.PURCHASER = '").append(purchaser).append("' ");
		}
		if(ToolsSys.isnull(seller)){//售电方
			sql.append(" AND CC.SELLER ='").append(seller).append("' ");
		}
		if(ToolsSys.isnull(sequenceid)){//合同序列
			sql.append(" and CC.SEQUENCEID = '").append(sequenceid).append("' ");
		}
		sql.append(" group by CC.CONTRACTID, CC.PAPERCONTRACTCODE, CC.CONTRACTNAME,");
		sql.append(" CCT.TYPENAME, BM.PARTICIPANTNAME,BMM.PARTICIPANTNAME ,CC.CONTRACTSTARTDATE,CC.CONTRACTENDDATE, CC.CONTRACTQTY, TT.NAME,TTT.NAME,T.NAME,TTTT.NAME,CC.UPDATETIME,TTTTT.NAME, CCTE.PRICE,CC.CONTRACTTYPE ");
		sql.append(" ORDER BY CC.UPDATETIME DESC nulls last,CC.CONTRACTNAME ");
//		if (ToolsSys.isnull(orderField) && ToolsSys.isnull(orderType)) {//@判断是否为空
//			sql.append(", ").append(orderField).append(" ").append(orderType);
//		}
		
		String sql2 = "SELECT COUNT(*) C FROM ( "+ sql + " ) T ";		//拼接查询数据条数的sql语句
		List list = dao.executeSqlQuery(sql2.toString());
		if(list.size() > 0){	//如果有查询结果，给数据条数变量赋值
			size = Integer.parseInt(list.get(0).toString());
		}
		result.setItemCount(size);	//给返回对象赋数据的数量
		list = dao.executeSqlQuery(sql.toString(),pageIndex,pageSize);	//查询分页的数据
		
		List<CoContractbaseinfoVO> voList = new ArrayList<CoContractbaseinfoVO>();		//返回的数据结果集
 		for(int i = 0;i < list.size();i++){	//遍历要展示的数据
 			Object[] objs = (Object[])list.get(i);	//得到第i条数据
 			CoContractbaseinfoVO vo = new CoContractbaseinfoVO();	//数据结果集中的一条数据
 			//给每个变量赋值
 			vo.setCol0(objs[0] == null ? "" : objs[0].toString());
 			vo.setCol1(objs[1] == null ? "" : objs[1].toString());
 			vo.setCol2(objs[2] == null ? "" : objs[2].toString());
 			vo.setCol3(objs[3] == null ? "" : objs[3].toString());
 			vo.setCol4(objs[4] == null ? "" : objs[4].toString());
 			vo.setCol5(objs[5] == null ? "" : objs[5].toString());
 			vo.setCol6(objs[8] == null ? "" : objs[8].toString());
 			vo.setCol7(objs[16] == null ? "" : objs[16].toString());
 			vo.setCol8(objs[6] == null ? "" : objs[6].toString());
 			vo.setCol9(objs[7] == null ? "" : objs[7].toString());
 			vo.setCol10(objs[11] == null ? "" : objs[11].toString());
 			vo.setCol11(objs[12] == null ? "" : objs[12].toString());
 			vo.setCol12(objs[13] == null ? "" : objs[13].toString());
 			vo.setCol13(objs[14] == null ? "" : objs[14].toString());
 			vo.setCol14(objs[15] == null ? "" : objs[15].toString());
 			vo.setCol15(objs[9] == null ? "" : objs[9].toString());
 			vo.setCol16(objs[10] == null ? "" : objs[10].toString());
 			vo.setCol17(objs[17] == null ? "" : objs[17].toString());
 			vo.setContracttype(objs[18] == null ? "" : objs[18].toString());
 			voList.add(vo);	//把数据添加到结果集中
 		}
		result.setItems(voList);	//给返回对象赋分页查询数据
		
		return result;	//返回返回值
		
	}
	
	/**
	 * 查询外网合同
	 * @description 方法描述
	 * @param pageNo
	 * @param pageSize
	 * @param contracttype
	 * @param searchField
	 * @param startDate
	 * @param endDate
	 * @param contractcyc
	 * @param sequenceid
	 * @param signstate
	 * @param backupstate
	 * @param corporationid
	 * @return
	 * @author mengke 
	 * @date 2013-4-16
	 */
	public QueryResultObject findOutWebContractList(int pageNo, int pageSize,
			String contracttype, String searchDateType,
			String startDate, String endDate,String contractcyc, String sequenceid,
			String signstate, String backupstate,String corporationid,String marketId) {
		String searchField = "CC.CONTRACTSTARTDATE";//合同开始日期
		if ("1".equals(searchDateType)) {//合同信息录入日期
			searchField = "CC.UPDATETIME";
		}else if ("2".equals(searchDateType)) {//合同签订日期
			searchField = "CC.SIGNEDDATE";
		}
		
		QueryResultObject result = new QueryResultObject();//新建返回值对象
		int size = 0;	//定义数据条数
		// TODO Auto-generated method stub
		
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
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT CC.CONTRACTID,CC.PAPERCONTRACTCODE,CC.CONTRACTNAME,CCT.TYPENAME,BM.PARTICIPANTNAME AS PP, ");
		sql.append(" BMM.PARTICIPANTNAME AS QQ ,TO_CHAR(CC.CONTRACTSTARTDATE,'YYYY-MM-DD'),TO_CHAR(CC.CONTRACTENDDATE,'YYYY-MM-DD'),CC.CONTRACTQTY,TT.NAME AS AA ,TTT.NAME AS BB, ");
		sql.append(" T.NAME AS CC ,TTTT.NAME AS DD,'' ORGAN_NAME,TO_CHAR( CC.UPDATETIME,'YYYY-MM-DD HH24:MI:SS'),TTTTT.NAME AS EE,CONTRACTPRICE,CC.CONTRACTTYPE");
		sql.append(" FROM  CO_CONTRACTBASEINFO CC LEFT JOIN  CO_CONTRACTBUSIFLOWINFO CCB ");
		sql.append(" ON CC.CONTRACTID = CCB.CONTRACTID LEFT JOIN   ba_gencode T ON T.type='67' AND CC.flowflag = T.VALUE ");
		sql.append(" LEFT JOIN  CO_CONTRACTTYPEINFO CCT ON CC.CONTRACTTYPE = CCT.CONTRACTTYPEID ");
		sql.append(" LEFT JOIN  BA_MARKETPARTICIPANT BM ON CC.PURCHASER = BM.PARTICIPANTID  ");
		sql.append(" LEFT JOIN  BA_MARKETPARTICIPANT BMM ON CC.SELLER = BMM.PARTICIPANTID ");
		sql.append(" LEFT JOIN  ba_gencode TT ON TT.TYPE='26'  AND CC.BACKUPSTATE = TT.VALUE ");
		sql.append(" LEFT JOIN  ba_gencode TTT ON TTT.TYPE='24' AND CC.SIGNSTATE = TTT.VALUE ");
		sql.append(" LEFT JOIN  ba_gencode TTTT ON TTTT.TYPE='27' AND CC.PREPCONTRACTFLAG = TTTT.VALUE ");
		//sql.append(" LEFT JOIN SGSOTOWER.PUB_ORGAN PO ON CC.UPDATEPERSONID = PO.ORGAN_CODE ");
		sql.append(" LEFT JOIN  ba_gencode TTTTT ON TTTTT.type='1' AND CC.MARKETID = TTTTT.VALUE ");
		//sql.append(" LEFT JOIN  CO_CONTRACTENERGYINFO CCTE ON CC.CONTRACTID = CCTE.CONTRACTID AND CCTE.PERIOD='1'");
		sql.append(" WHERE ");
		sql.append(" CC.ISDELETE = '0'" );
		sql.append(" and (BM.PARTICIPANTID  in (SELECT T1.PARTICIPANTID FROM  BA_MARKETPARTICIPANT T1 WHERE T1.PARTICIPANTCODE = '"+ corporationid +"') or BMM.PARTICIPANTID in (SELECT T1.PARTICIPANTID FROM  BA_MARKETPARTICIPANT T1 WHERE T1.PARTICIPANTCODE = '"+ corporationid +"'))");
//		if(ToolsSys.isnull(contracttype)){//合同类型
//			//sql.append(" AND CC.CONTRACTTYPE = '").append(contracttype).append("' ");
//			sql.append(" and cc.contracttype in (select c.contracttypeid from  co_contracttypeinfo c where c.effectiveflag = '0'");
//			sql.append(" connect by prior c.contracttypeid = c.supertypeid start with c.contracttypeid = '").append(contracttype).append("')");
//		}
		
		if(ToolsSys.isnull(contracttype)){//合同类型 
			sql.append(" and (cc.contracttype in (").append(contracttype).append(") or cc.contracttype in (select c.contracttypeid from  co_contracttypeinfo c where c.effectiveflag = '0' and  c.MARKETID='").append(marketId);
			sql.append("' connect by prior c.contracttypeid = c.supertypeid start with c.contracttypeid in (").append(contracttype).append(")))");
			
		}
		
		//开始时间不为空，结束时间为空时，结束日期小于开始日期
		if(ToolsSys.isnull(startDate) && !ToolsSys.isnull(endDate)){//@判断是否为空
			sql.append(" AND TO_CHAR(").append ("CC.CONTRACTENDDATE").append(",'YYYY-MM-DD') >= '").append(startDate).append("' ");
		}else if(!ToolsSys.isnull(startDate) && ToolsSys.isnull (endDate)){//@判断是否为空
			sql.append(" AND TO_CHAR(").append ("CONTRACTSTARTDATE").append(",'YYYY-MM-DD') <= '").append(endDate).append("' ");
		}else if(ToolsSys.isnull(startDate) && ToolsSys.isnull (endDate)){//@判断是否为空
			sql.append(" AND (TO_CHAR(").append ("CC.CONTRACTENDDATE").append(",'YYYY-MM-DD') >= '").append(startDate).append("' AND  TO_CHAR(").append

	("CC.CONTRACTSTARTDATE").append(",'YYYY-MM-DD') <= '").append (endDate).append("') ");
		}
		if(ToolsSys.isnull(contractcyc)){//合同周期
			sql.append(" AND CC.CONTRACTCYC ='").append(contractcyc).append("' ");
		}
		if(ToolsSys.isnull(sequenceid)){//合同序列
			sql.append(" and CC.SEQUENCEID = '").append(sequenceid).append("' ");
		}
		if(ToolsSys.isnull(signstate)){//@判断是否为空
			sql.append(" and cc.signstate = '").append(signstate).append("' ");
		}
		if(ToolsSys.isnull(backupstate)){//@判断是否为空
			sql.append(" and cc.backupstate = '").append(backupstate).append("' ");
		}
		sql.append(" group by CC.CONTRACTID, CC.PAPERCONTRACTCODE, CC.CONTRACTNAME,");
		sql.append(" CCT.TYPENAME, BM.PARTICIPANTNAME,BMM.PARTICIPANTNAME ,CC.CONTRACTSTARTDATE,CC.CONTRACTENDDATE, CC.CONTRACTQTY, TT.NAME,TTT.NAME,T.NAME,TTTT.NAME,CC.UPDATETIME,TTTTT.NAME,CONTRACTPRICE,CC.CONTRACTTYPE");
		sql.append(" ORDER BY nlssort(BMM.PARTICIPANTNAME, 'NLS_SORT = SCHINESE_PINYIN_M') nulls last,CC.PAPERCONTRACTCODE ");
//		if (ToolsSys.isnull(orderField) && ToolsSys.isnull(orderType)) {//@判断是否为空
//			sql.append(", ").append(orderField).append(" ").append(orderType);
//		}
		
		String sql2 = "SELECT COUNT(*) C FROM ( "+ sql + " ) T ";		//拼接查询数据条数的sql语句
		List list = dao.executeSqlQuery(sql2.toString());
		if(list.size() > 0){	//如果有查询结果，给数据条数变量赋值
			size = Integer.parseInt(list.get(0).toString());
		}
		result.setItemCount(size);	//给返回对象赋数据的数量
		list = dao.executeSqlQuery(sql.toString(),pageNo,pageSize);	//查询分页的数据
		
		List<CoContractbaseinfoVO> voList = new ArrayList<CoContractbaseinfoVO>();		//返回的数据结果集
		java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
		nf.setGroupingUsed(false);
 		for(int i = 0;i < list.size();i++){	//遍历要展示的数据
 			Object[] objs = (Object[])list.get(i);	//得到第i条数据
 			CoContractbaseinfoVO vo = new CoContractbaseinfoVO();	//数据结果集中的一条数据
 			//给每个变量赋值
 			vo.setCol0(objs[0] == null ? "" : objs[0].toString());
 			vo.setCol1(objs[1] == null ? "" : objs[1].toString());
 			vo.setCol2(objs[2] == null ? "" : objs[2].toString());
 			vo.setCol3(objs[3] == null ? "" : objs[3].toString());
 			vo.setCol4(objs[4] == null ? "" : objs[4].toString());
 			vo.setCol5(objs[5] == null ? "" : objs[5].toString());
// 			vo.setCol6(objs[8] == null ? "" : nf.format(Math.round(Double.parseDouble(objs[8].toString()) * 1000)/1000.0));
// 			vo.setCol7(objs[16] == null ? "" : nf.format(Math.round(Double.parseDouble(objs[16].toString()) * 10)/10.0));
// 			vo.setEnergy(objs[8] == null ? null : new BigDecimal(nf.format(Math.round(Double.parseDouble(objs[8].toString()) * 1000)/1000.0)));
 			vo.setEnergy(objs[8] == null ? null : new BigDecimal(objs[8].toString()));
// 			vo.setContractprice(objs[16] == null ? null : new BigDecimal(nf.format(Math.round(Double.parseDouble(objs[16].toString()) * 10)/10.0)));
 			vo.setContractprice(objs[16] == null ? null : new BigDecimal(objs[16].toString()));
 			vo.setCol8(objs[6] == null ? "" : objs[6].toString());
 			vo.setCol9(objs[7] == null ? "" : objs[7].toString());
 			vo.setCol10(objs[11] == null ? "" : objs[11].toString());
 			vo.setCol11(objs[12] == null ? "" : objs[12].toString());
 			vo.setCol12(objs[13] == null ? "" : objs[13].toString());
 			vo.setCol13(objs[14] == null ? "" : objs[14].toString());
 			vo.setCol14(objs[15] == null ? "" : objs[15].toString());
 			vo.setContracttype(objs[17] == null ? "" : objs[17].toString());
 			//vo.setCol15(objs[17] == null ? "" : objs[17].toString());
 			voList.add(vo);	//把数据添加到结果集中
 		}
		result.setItems(voList);	//给返回对象赋分页查询数据
		
		return result;	//返回返回值
		
	}
	

	/**
	 * 合同管理查询功能(原来的方法去掉了四个参数pageNo、
	 * 						pageSize、orderField、orderType)
	 * 这个方法的sql语句的编写和sotower里边的不同是添加了三个时间转换格式
	 * @param marketid
	 * @param contracttype
	 * @param searchDateType
	 * @param startDate
	 * @param endDate
	 * @param contractcyc
	 * @param prepcontractflag
	 * @param purchaser
	 * @param seller
	 * @param sequenceid
	 * @param flowflag
	 * @param marketid1
	 * @param contractName
	 * @param papercontractcode
	 * @param syscontractcode
	 * @return
	 */
	public QueryResultObject findCoContractList11(int pageIndex,int pageSize,String marketid, String contracttype,
			String searchDateType, String startDate, String endDate,
			String contractcyc, String prepcontractflag, String purchaser,
			String seller, String sequenceid, String flowflag, String marketid1,
			String contractName, String papercontractcode, String syscontractcode,String powertype) {
		QueryResultObject result = new QueryResultObject();//新建返回值对象
		int size = 0;	//定义数据条数
		
		String sql = getSearchSql(marketid, contracttype, searchDateType, startDate, endDate,
				contractcyc, prepcontractflag, purchaser, seller, sequenceid, flowflag, marketid1,
				contractName, papercontractcode, syscontractcode,powertype);	//得到要查询的sql语句
		String sql2 = "SELECT COUNT(*) C FROM ( "+ sql + " ) T ";		//拼接查询数据条数的sql语句
		List list = dao.executeSqlQuery(sql2.toString());
		if(list.size() > 0){	//如果有查询结果，给数据条数变量赋值
			size = Integer.parseInt(list.get(0).toString());
		}
		result.setItemCount(size);	//给返回对象赋数据的数量
		list = dao.executeSqlQuery(sql.toString(),pageIndex,pageSize);	//查询分页的数据
		
		List<CoContractbaseinfoVO> voList = new ArrayList<CoContractbaseinfoVO>();		//返回的数据结果集
		java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
		nf.setGroupingUsed(false);
		for(int i = 0;i < list.size();i++){	//遍历要展示的数据
 			Object[] objs = (Object[])list.get(i);	//得到第i条数据
 			CoContractbaseinfoVO vo = new CoContractbaseinfoVO();	//数据结果集中的一条数据
 			//给每个变量赋值
 			vo.setCol0(objs[0] == null ? "" : objs[0].toString());
 			vo.setCol1(objs[1] == null ? "" : objs[1].toString());
 			vo.setCol2(objs[2] == null ? "" : objs[2].toString());
 			vo.setCol3(objs[3] == null ? "" : objs[3].toString());
 			vo.setCol4(objs[4] == null ? "" : objs[4].toString());
 			vo.setCol5(objs[5] == null ? "" : objs[5].toString());
// 			vo.setCol6(objs[8] == null ? "" : nf.format(Math.round(Double.parseDouble(objs[8].toString()) * 1000)/1000.0));
// 			vo.setCol7(objs[19] == null ? "" : nf.format(Math.round(Double.parseDouble(objs[19].toString()) * 10)/10.0));
// 			vo.setEnergy(objs[8] == null ? null :new BigDecimal(nf.format(Math.round(Double.parseDouble(objs[8].toString()) * 1000)/1000.0)));
 			vo.setEnergy(objs[8] == null ? null :new BigDecimal(objs[8].toString()));
// 			vo.setContractprice(objs[19] == null ? null: new BigDecimal(nf.format(Math.round(Double.parseDouble(objs[19].toString()) * 10)/10.0)));
 			vo.setContractprice(objs[19] == null ? null: new BigDecimal(objs[19].toString()));
 			vo.setCol8(objs[6] == null ? "" : objs[6].toString());
 			vo.setCol9(objs[7] == null ? "" : objs[7].toString());
 			vo.setCol10(objs[11] == null ? "" : objs[11].toString());
 			vo.setCol11(objs[12] == null ? "" : objs[12].toString());
 			vo.setCol12(objs[13] == null ? "" : objs[13].toString());
 			vo.setCol13(objs[14] == null ? "" : objs[14].toString());
 			vo.setCol14(objs[15] == null ? "" : objs[15].toString());
 			vo.setCol15(objs[17] == null ? "" : objs[17].toString());
 			vo.setContracttype(objs[20] == null ? "" : objs[20].toString());
 			vo.setCol16(objs[21] == null ? "" : objs[21].toString());
 			voList.add(vo);	//把数据添加到结果集中
 		}
		result.setItems(voList);	//给返回对象赋分页查询数据
		
		return result;	//返回返回值
		
	}
	
	
	/**
	 * 合同分月管理查询功能(原来的方法去掉了四个参数pageNo、
	 * 						pageSize、orderField、orderType)
	 * 这个方法的sql语句的编写和sotower里边的不同是添加了三个时间转换格式
	 * @param marketid
	 * @param contracttype
	 * @param searchDateType
	 * @param startDate
	 * @param endDate
	 * @param contractcyc
	 * @param prepcontractflag
	 * @param purchaser
	 * @param seller
	 * @param sequenceid
	 * @param flowflag
	 * @param marketid1
	 * @param contractName
	 * @param papercontractcode
	 * @param syscontractcode
	 * @return
	 */
	public QueryResultObject queryMonthManage(int pageIndex,int pageSize,String marketid, String contracttype,
			String searchDateType, String startDate, String endDate,
			String contractcyc, String prepcontractflag, String purchaser,
			String seller, String sequenceid, String flowflag, String marketid1,
			String contractName, String papercontractcode, String syscontractcode,String powertype) {
		QueryResultObject result = new QueryResultObject();//新建返回值对象
		int size = 0;	//定义数据条数
		String searchField = "CC.CONTRACTSTARTDATE";//合同开始日期
		if ("1".equals(searchDateType)) {//合同信息录入日期
			searchField = "CC.UPDATETIME";
		}else if ("2".equals(searchDateType)) {//合同签订日期
			searchField = "CC.SIGNEDDATE";
		}
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT CC.CONTRACTID,CC.PAPERCONTRACTCODE,CC.CONTRACTNAME,CCT.TYPENAME,BM.PARTICIPANTNAME AS PP, ");
		//两个时间格式添加
		sql.append(" BMM.PARTICIPANTNAME AS QQ , TO_CHAR(CC.CONTRACTSTARTDATE,'MM'),TO_CHAR(CC.CONTRACTENDDATE,'MM'),CC.CONTRACTQTY,TT.NAME AS AA ,TTT.NAME AS BB, ");	
		//一个时间格式添加
		sql.append(" T.NAME AS CC ,TTTT.NAME AS DD,'' ORGAN_NAME,TO_CHAR( CC.UPDATETIME,'YYYY-MM-DD HH24:MI:SS'),TTTTT.MARKETNAME AS EE,'',count(ccai.affixname),CC.MARKETID,CC.CONTRACTPRICE,CC.CONTRACTTYPE,BMM.aliasname ");
		sql.append(" ,decode(cmq.monthqtytype,'1','总电量','2','替代方发电量','3','替代方上网电量','4','被替代方发电量','5','被替代方上网电量','总电量'),cmq.JANQTY,cmq.FEBQTY,cmq.MARQTY,cmq.APRILQTY,cmq.MAYQTY,cmq.JUNQTY,cmq.JULYQTY,cmq.AUGQTY,cmq.SEPQTY,cmq.OCTQTY,cmq.NOVQTY,cmq.DECQTY, cmq.monthqtytype ");
		sql.append(" FROM  CO_CONTRACTBASEINFO CC LEFT JOIN  CO_CONTRACTBUSIFLOWINFO CCB ");
		sql.append(" ON CC.CONTRACTID = CCB.CONTRACTID LEFT JOIN   ba_gencode T ON T.type='67' AND CC.flowflag = T.VALUE ");
		sql.append(" LEFT JOIN  CO_CONTRACTTYPEINFO CCT ON CC.CONTRACTTYPE = CCT.CONTRACTTYPEID ");
		sql.append(" LEFT JOIN  BA_MARKETPARTICIPANT BM ON CC.PURCHASER = BM.PARTICIPANTID  ");
		sql.append(" LEFT JOIN  BA_MARKETPARTICIPANT BMM ON CC.SELLER = BMM.PARTICIPANTID ");
		sql.append(" LEFT JOIN  ba_gencode TT ON TT.TYPE='26'  AND CC.BACKUPSTATE = TT.VALUE ");
		sql.append(" LEFT JOIN  ba_gencode TTT ON TTT.TYPE='24' AND CC.SIGNSTATE = TTT.VALUE ");
		sql.append(" LEFT JOIN  ba_gencode TTTT ON TTTT.TYPE='27' AND CC.PREPCONTRACTFLAG = TTTT.VALUE ");
		sql.append(" LEFT JOIN  BA_MARKET TTTTT ON CC.MARKETID = TTTTT.MARKETID ");
		sql.append(" left join  co_contractaffixinfo ccai on cc.contractid = ccai.contractid");
		sql.append(" left join  co_contractmonthqty cmq on cc.contractid = cmq.contractid");
		sql.append(" WHERE 1=1 ");
		if(!ToolsSys.isnull(marketid)){
			sql.append(" AND (CC.MARKETID='").append(marketid1).append("') ");
		}else{
			sql.append(" AND (CC.MARKETID='").append(marketid).append("') ");
			
		}
		if(ToolsSys.isnull(contracttype)){//合同类型 
			if(!ToolsSys.isnull(marketid)){
				sql.append(" and (cc.contracttype = '").append(contracttype).append("' or cc.contracttype in (select c.contracttypeid from  co_contracttypeinfo c where c.effectiveflag = '0' and  c.MARKETID='").append(marketid1);
				sql.append("' connect by prior c.contracttypeid = c.supertypeid start with c.contracttypeid = '").append(contracttype).append("'))");
			
			}else{
				sql.append(" and (cc.contracttype = '").append(contracttype).append("' or cc.contracttype in (select c.contracttypeid from  co_contracttypeinfo c where c.effectiveflag = '0' and  c.MARKETID='").append(marketid);
				sql.append("' connect by prior c.contracttypeid = c.supertypeid start with c.contracttypeid = '").append(contracttype).append("'))");
			}
		}
		
		//开始时间不为空，结束时间为空时，结束日期小于开始日期
		if(ToolsSys.isnull(startDate) && !ToolsSys.isnull(endDate)){//@判断是否为空
			sql.append(" AND TO_CHAR(").append ("CC.CONTRACTENDDATE").append(",'YYYY-MM-DD') >= '").append(startDate).append("' ");
		}else if(!ToolsSys.isnull(startDate) && ToolsSys.isnull (endDate)){//@判断是否为空
			sql.append(" AND TO_CHAR(").append ("CONTRACTSTARTDATE").append(",'YYYY-MM-DD') <= '").append(endDate).append("' ");
		}else if(ToolsSys.isnull(startDate) && ToolsSys.isnull (endDate)){//@判断是否为空
			sql.append(" AND (TO_CHAR(").append ("CC.CONTRACTENDDATE").append(",'YYYY-MM-DD') >= '").append(startDate).append("' AND  TO_CHAR(").append

	("CC.CONTRACTSTARTDATE").append(",'YYYY-MM-DD') <= '").append (endDate).append("') ");
		}
		if(ToolsSys.isnull(contractcyc)){//合同周期
			sql.append(" AND CC.CONTRACTCYC ='").append(contractcyc).append("' ");
		}
		if(ToolsSys.isnull(prepcontractflag)){//合同状态
			sql.append(" AND CC.PREPCONTRACTFLAG ='").append(prepcontractflag).append("' ");
		}
		if(ToolsSys.isnull(purchaser)){//购电方
			sql.append(" AND CC.PURCHASER = '").append(purchaser).append("' ");
		}
		if(ToolsSys.isnull(seller)){//售电方
			sql.append(" AND CC.SELLER ='").append(seller).append("' ");
		}
		if(ToolsSys.isnull(sequenceid)){//合同序列
			sql.append(" and CC.SEQUENCEID = '").append(sequenceid).append("' ");
		}
		if(ToolsSys.isnull(flowflag)){//@判断是否为空
			if(flowflag.equals("6")){
				sql.append(" and cc.flowflag in ( ").append(flowflag).append(" ) ");
			}else {
				sql.append(" and cc.ISDELETE = '0' ");
				sql.append(" and cc.flowflag in ( ").append(flowflag).append(" ) ");
			}
			
		}else{
			sql.append(" and cc.ISDELETE = '0' ");
		}
		if(ToolsSys.isnull(contractName)){//@判断是否为空
			sql.append(" and cc.contractName like '%").append(contractName).append("%' ");
		}
		
		if(ToolsSys.isnull(papercontractcode)){//纸质合同编号
			sql.append(" and cc.papercontractcode like '%").append(papercontractcode).append("%' ");
		}
		
		if(ToolsSys.isnull(syscontractcode)){//系统合同编号
			sql.append(" and cc.expend5 like '%").append(syscontractcode).append("%' ");
		}
		
		if(ToolsSys.isnull(powertype)){//售电方发电类型
			sql.append(" and CC.SELLER in ( select b.participantid from BA_MARKETPARTICIPANT b where  b.powertype =").append(powertype).append(" ) ");
		}
		sql.append(" group by CC.CONTRACTID, CC.PAPERCONTRACTCODE, CC.CONTRACTNAME,");
		sql.append(" CCT.TYPENAME, BM.PARTICIPANTNAME,BMM.PARTICIPANTNAME ,CC.CONTRACTSTARTDATE,CC.CONTRACTENDDATE, CC.CONTRACTQTY, TT.NAME,TTT.NAME,T.NAME,TTTT.NAME,CC.UPDATETIME,TTTTT.MARKETNAME,CC.MARKETID,CC.CONTRACTPRICE,CC.CONTRACTTYPE,BMM.aliasname,BMM.powertype,cmq.monthqtytype,cmq.JANQTY,cmq.FEBQTY,cmq.MARQTY,cmq.APRILQTY,cmq.MAYQTY,cmq.JUNQTY,cmq.JULYQTY,cmq.AUGQTY,cmq.SEPQTY,cmq.OCTQTY,cmq.NOVQTY,cmq.DECQTY ORDER BY BMM.powertype,nlssort(BMM.PARTICIPANTNAME, 'NLS_SORT = SCHINESE_PINYIN_M')  nulls last,CC.PAPERCONTRACTCODE");
		
		
		String sql2 = "SELECT COUNT(*) C FROM ( "+ sql.toString() + " ) T ";		//拼接查询数据条数的sql语句
		List list = dao.executeSqlQuery(sql2.toString());
		if(list.size() > 0){	//如果有查询结果，给数据条数变量赋值
			size = Integer.parseInt(list.get(0).toString());
		}
		result.setItemCount(size);	//给返回对象赋数据的数量
		list = dao.executeSqlQuery(sql.toString(),pageIndex,pageSize);	//查询分页的数据
		
		List<CoContractbaseinfoVO> voList = new ArrayList<CoContractbaseinfoVO>();		//返回的数据结果集
		java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
		nf.setGroupingUsed(false);
		
		StringBuffer qtyCountSQL = new StringBuffer();
		qtyCountSQL.append("select count(1) from (" + ContractmonthmanageBizc.getResolveResult() + ")");
		
		for(int i = 0;i < list.size();i++){	//遍历要展示的数据
 			Object[] objs = (Object[])list.get(i);	//得到第i条数据
 			CoContractbaseinfoVO vo = new CoContractbaseinfoVO();	//数据结果集中的一条数据
 			
 			List countResult = dao.executeSqlQuery(qtyCountSQL.toString(), new Object[]{objs[0].toString(), objs[35]==null?"":objs[35].toString()});
 			
 			//给每个变量赋值
 			vo.setResolveresult(countResult.get(0).toString());
 			vo.setCol0(objs[0] == null ? "" : objs[0].toString());
 			vo.setPapercontractcode(objs[1] == null ? "" : objs[1].toString());
 			vo.setContractname(objs[2] == null ? "" : objs[2].toString());
 			vo.setContracttype(objs[20] == null ? "" : objs[20].toString());
 			vo.setCol14(objs[3] == null ? "" : objs[3].toString());
 			vo.setPurchaser(objs[4] == null ? "" : objs[4].toString());
 			vo.setSeller(objs[5] == null ? "" : objs[5].toString());
 			vo.setEnergy(objs[8] == null ? null :new BigDecimal(nf.format(Math.round(Double.parseDouble(objs[8].toString()) * 1000)/1000.0)));
 			vo.setCol18(objs[7] == null ? "" : objs[7].toString());
 			vo.setCol17(objs[6] == null ? "" : objs[6].toString());
 			vo.setCol16(objs[21] == null ? "" : objs[21].toString());
 			vo.setCol15(objs[22] == null ? "" : objs[22].toString());
 			vo.setCol1(objs[23] == null ? "" : objs[23].toString());
 			vo.setCol2(objs[24] == null ? "" : objs[24].toString());
 			vo.setCol3(objs[25] == null ? "" : objs[25].toString());
 			vo.setCol4(objs[26] == null ? "" : objs[26].toString());
 			vo.setCol5(objs[27] == null ? "" : objs[27].toString());
 			vo.setCol6(objs[28] == null ? "" : objs[28].toString());
 			vo.setCol7(objs[29] == null ? "" : objs[29].toString());
 			vo.setCol8(objs[30] == null ? "" : objs[30].toString());
 			vo.setCol9(objs[31] == null ? "" : objs[31].toString());
 			vo.setCol10(objs[32] == null ? "" : objs[32].toString());
 			vo.setCol11(objs[33] == null ? "" : objs[33].toString());
 			vo.setCol12(objs[34] == null ? "" : objs[34].toString());
 			vo.setCol13(objs[34] == null ? "" : objs[34].toString());
 			vo.setMonthqtytype(objs[35] == null ? "" : objs[35].toString());
 			voList.add(vo);	//把数据添加到结果集中
 		}
		result.setItems(voList);	//给返回对象赋分页查询数据
		
		return result;	//返回返回值
	}
	
	/**
	 * 已终止合同查询功能(原来的方法去掉了四个参数pageNo、
	 * 						pageSize、orderField、orderType)
	 * 这个方法的sql语句的编写和sotower里边的不同是添加了三个时间转换格式
	 * @param marketid
	 * @param contracttype
	 * @param searchDateType
	 * @param startDate
	 * @param endDate
	 * @param contractcyc
	 * @param prepcontractflag
	 * @param purchaser
	 * @param seller
	 * @param sequenceid
	 * @param flowflag
	 * @param marketid1
	 * @param contractName
	 * @param papercontractcode
	 * @param syscontractcode
	 * @return
	 */
	public QueryResultObject findCoContractList12(int pageIndex,int pageSize,String marketid, String contracttype,
			String searchDateType, String startDate, String endDate,
			String contractcyc, String prepcontractflag, String purchaser,
			String seller, String sequenceid, String flowflag, String marketid1,
			String contractName, String papercontractcode, String syscontractcode) {
		QueryResultObject result = new QueryResultObject();//新建返回值对象
		int size = 0;	//定义数据条数
		
		String searchField = "CC.CONTRACTSTARTDATE";//合同开始日期
		if ("1".equals(searchDateType)) {//合同信息录入日期
			searchField = "CC.UPDATETIME";
		}else if ("2".equals(searchDateType)) {//合同签订日期
			searchField = "CC.SIGNEDDATE";
		}
		
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
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT CC.CONTRACTID,CC.PAPERCONTRACTCODE,CC.CONTRACTNAME,CCT.TYPENAME,BM.PARTICIPANTNAME AS PP, ");
		//两个时间格式添加
		sql.append(" BMM.PARTICIPANTNAME AS QQ , TO_CHAR(CC.CONTRACTSTARTDATE,'YYYY-MM-DD'),TO_CHAR(CC.CONTRACTENDDATE,'YYYY-MM-DD'),CC.CONTRACTQTY,TT.NAME AS AA ,TTT.NAME AS BB, ");	
		//一个时间格式添加
		sql.append(" T.NAME AS CC ,TTTT.NAME AS DD,'' ORGAN_NAME,TO_CHAR( CC.UPDATETIME,'YYYY-MM-DD HH24:MI:SS'),TTTTT.MARKETNAME AS EE,ROUND(DECODE(SUM(CCTE.SELLERENERGY),0,0,SUM(CCTE.PRICE * CCTE.SELLERENERGY) / SUM(CCTE.SELLERENERGY)), 1) AS AVG_PRICE,count(ccai.affixname),CC.MARKETID,CC.CONTRACTPRICE,CC.CONTRACTTYPE");
		sql.append(" FROM  CO_CONTRACTBASEINFO CC LEFT JOIN  CO_CONTRACTBUSIFLOWINFO CCB ");
		sql.append(" ON CC.CONTRACTID = CCB.CONTRACTID LEFT JOIN   ba_gencode T ON T.type='67' AND CC.flowflag = T.VALUE ");
		sql.append(" LEFT JOIN  CO_CONTRACTTYPEINFO CCT ON CC.CONTRACTTYPE = CCT.CONTRACTTYPEID ");
		sql.append(" LEFT JOIN  BA_MARKETPARTICIPANT BM ON CC.PURCHASER = BM.PARTICIPANTID  ");
		sql.append(" LEFT JOIN  BA_MARKETPARTICIPANT BMM ON CC.SELLER = BMM.PARTICIPANTID ");
		sql.append(" LEFT JOIN  ba_gencode TT ON TT.TYPE='26'  AND CC.BACKUPSTATE = TT.VALUE ");
		sql.append(" LEFT JOIN  ba_gencode TTT ON TTT.TYPE='24' AND CC.SIGNSTATE = TTT.VALUE ");
		sql.append(" LEFT JOIN  ba_gencode TTTT ON TTTT.TYPE='27' AND CC.PREPCONTRACTFLAG = TTTT.VALUE ");
		//sql.append(" LEFT JOIN SGSOTOWER.PUB_ORGAN PO ON CC.UPDATEPERSONID = PO.ORGAN_CODE ");
		sql.append(" LEFT JOIN  BA_MARKET TTTTT ON CC.MARKETID = TTTTT.MARKETID ");
		sql.append(" LEFT JOIN  CO_CONTRACTENERGYINFO CCTE ON CC.CONTRACTID = CCTE.CONTRACTID AND CCTE.PERIOD='1'");
		sql.append(" left join  co_contractaffixinfo ccai on cc.contractid = ccai.contractid");
		sql.append(" WHERE 1=1 ");
//		if(!ToolsSys.isnull(marketid)){
//			sql.append(" AND (CC.ISDELETE = '0' ");
//			sql.append(" AND CC.MARKETID='").append(marketid1).append("' ");
//			sql.append(" or CC.contractid in (select cbs.dataid from  co_contractbaseinfo_share cbs where cbs.marketid='"+marketid1+"'))");
//		}else{
//			sql.append(" AND (CC.ISDELETE = '0' ");
//			if(ToolsSys.isnull(marketid1)){//市场
//				if(!"91812".equals(marketid1)){//增加国网总部权限，能够查到各分部、省公司签订、录入情况。lvwentao确认100%修改2013-11-25
//					if(marketid.equals(marketid1)){
//						sql.append(" AND CC.MARKETID ='").append(marketid).append("' )");
//					}else{
//						sql.append(" AND CC.contractid in (select cbs.dataid from  co_contractbaseinfo_share cbs where cbs.smarketid='"+marketid+"' AND cbs.marketid='"+marketid1+"'))");
//					}
//				}
//				else{
//					sql.append(")");
//				}
//			}
//		}
		if(!ToolsSys.isnull(marketid)){
			sql.append(" AND (CC.ISDELETE = '1' ");
			sql.append(" AND CC.MARKETID='").append(marketid1).append("') ");
		}else{
			sql.append(" AND (CC.ISDELETE = '1' ");
			sql.append(" AND CC.MARKETID='").append(marketid).append("') ");
			
		}
		
		
//		if(ToolsSys.isnull(contracttype)){//合同类型
//			sql.append(" and cc.contracttype in (select c.contracttypeid from  co_contracttypeinfo c where c.effectiveflag = '0'");
//			sql.append(" connect by prior c.contracttypeid = c.supertypeid start with c.contracttypeid = '").append(contracttype).append("')");
//		}
		
		if(ToolsSys.isnull(contracttype)){//合同类型 
			if(!ToolsSys.isnull(marketid)){
				sql.append(" and (cc.contracttype in (").append(contracttype).append(") or cc.contracttype in (select c.contracttypeid from  co_contracttypeinfo c where c.effectiveflag = '0' and  c.MARKETID='").append(marketid1);
				sql.append("' connect by prior c.contracttypeid = c.supertypeid start with c.contracttypeid in (").append(contracttype).append(")))");
			
			}else{
				sql.append(" and (cc.contracttype in (").append(contracttype).append(") or cc.contracttype in (select c.contracttypeid from  co_contracttypeinfo c where c.effectiveflag = '0' and  c.MARKETID='").append(marketid);
				sql.append("' connect by prior c.contracttypeid = c.supertypeid start with c.contracttypeid in (").append(contracttype).append(")))");
			}
		}
		
		//开始时间不为空，结束时间为空时，结束日期小于开始日期
		if(ToolsSys.isnull(startDate) && !ToolsSys.isnull(endDate)){//@判断是否为空
			sql.append(" AND TO_CHAR(").append ("CC.CONTRACTENDDATE").append(",'YYYY-MM-DD') >= '").append(startDate).append("' ");
		}else if(!ToolsSys.isnull(startDate) && ToolsSys.isnull (endDate)){//@判断是否为空
			sql.append(" AND TO_CHAR(").append ("CONTRACTSTARTDATE").append(",'YYYY-MM-DD') <= '").append(endDate).append("' ");
		}else if(ToolsSys.isnull(startDate) && ToolsSys.isnull (endDate)){//@判断是否为空
			sql.append(" AND (TO_CHAR(").append ("CC.CONTRACTENDDATE").append(",'YYYY-MM-DD') >= '").append(startDate).append("' AND  TO_CHAR(").append

	("CC.CONTRACTSTARTDATE").append(",'YYYY-MM-DD') <= '").append (endDate).append("') ");
		}
		if(ToolsSys.isnull(contractcyc)){//合同周期
			sql.append(" AND CC.CONTRACTCYC ='").append(contractcyc).append("' ");
		}
		if(ToolsSys.isnull(prepcontractflag)){//合同状态
			sql.append(" AND CC.PREPCONTRACTFLAG ='").append(prepcontractflag).append("' ");
		}
		if(ToolsSys.isnull(purchaser)){//购电方
			sql.append(" AND CC.PURCHASER = '").append(purchaser).append("' ");
		}
		if(ToolsSys.isnull(seller)){//售电方
			sql.append(" AND CC.SELLER ='").append(seller).append("' ");
		}
		if(ToolsSys.isnull(sequenceid)){//合同序列
			sql.append(" and CC.SEQUENCEID = '").append(sequenceid).append("' ");
		}
		if(ToolsSys.isnull(flowflag)){//@判断是否为空
			if(flowflag.equals("6")){
				sql.append(" and cc.flowflag in ( ").append(flowflag).append(" ) ");
			}else {
				sql.append(" and cc.ISDELETE = '0' ");
				sql.append(" and cc.flowflag in ( ").append(flowflag).append(" ) ");
			}
			
		}
		if(ToolsSys.isnull(contractName)){//@判断是否为空
			sql.append(" and cc.contractName like '%").append(contractName).append("%' ");
		}
		
		// ================= Begin =================
		// ============ Author:WuYujian ============
		if(ToolsSys.isnull(papercontractcode)){//纸质合同编号
			sql.append(" and cc.papercontractcode like '%").append(papercontractcode).append("%' ");
		}
		
		if(ToolsSys.isnull(syscontractcode)){//系统合同编号
			sql.append(" and cc.expend5 like '%").append(syscontractcode).append("%' ");
		}
		// ================= End =================
		
		sql.append(" group by CC.CONTRACTID, CC.PAPERCONTRACTCODE, CC.CONTRACTNAME,");
		sql.append(" CCT.TYPENAME, BM.PARTICIPANTNAME,BMM.PARTICIPANTNAME ,CC.CONTRACTSTARTDATE,CC.CONTRACTENDDATE, CC.CONTRACTQTY, TT.NAME,TTT.NAME,T.NAME,TTTT.NAME,CC.UPDATETIME,TTTTT.MARKETNAME,CC.MARKETID,CC.CONTRACTPRICE,CC.CONTRACTTYPE ");
		// sql.append(" ORDER BY CC.UPDATETIME DESC ");// Edit by WuYujian
		// 增加排序的SQL语句
//		if (ToolsSys.isnull(orderField) && ToolsSys.isnull(orderType)) {
//			sql.append(" ORDER BY " + orderField + " " + orderType);
//		}
		String sql2 = "SELECT COUNT(*) C FROM ( "+ sql + " ) T ";		//拼接查询数据条数的sql语句
		List list = dao.executeSqlQuery(sql2.toString());
		if(list.size() > 0){	//如果有查询结果，给数据条数变量赋值
			size = Integer.parseInt(list.get(0).toString());
		}
		result.setItemCount(size);	//给返回对象赋数据的数量
		list = dao.executeSqlQuery(sql.toString(),pageIndex,pageSize);	//查询分页的数据
		
		List<CoContractbaseinfoVO> voList = new ArrayList<CoContractbaseinfoVO>();		//返回的数据结果集
		java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
		nf.setGroupingUsed(false);
		for(int i = 0;i < list.size();i++){	//遍历要展示的数据
 			Object[] objs = (Object[])list.get(i);	//得到第i条数据
 			CoContractbaseinfoVO vo = new CoContractbaseinfoVO();	//数据结果集中的一条数据
 			//给每个变量赋值
 			vo.setCol0(objs[0] == null ? "" : objs[0].toString());
 			vo.setCol1(objs[1] == null ? "" : objs[1].toString());
 			vo.setCol2(objs[2] == null ? "" : objs[2].toString());
 			vo.setCol3(objs[3] == null ? "" : objs[3].toString());
 			vo.setCol4(objs[4] == null ? "" : objs[4].toString());
 			vo.setCol5(objs[5] == null ? "" : objs[5].toString());
// 			vo.setCol6(objs[8] == null ? "" : nf.format(Math.round(Double.parseDouble(objs[8].toString()) * 1000)/1000.0));
// 			vo.setCol7(objs[19] == null ? "" : nf.format(Math.round(Double.parseDouble(objs[19].toString()) * 10)/10.0));
// 			vo.setEnergy(objs[8] == null ? null : new BigDecimal(nf.format(Math.round(Double.parseDouble(objs[8].toString()) * 1000)/1000.0)));
 			vo.setEnergy(objs[8] == null ? null : new BigDecimal(objs[8].toString()));
// 			vo.setContractprice(objs[19] == null ? null : new BigDecimal(nf.format(Math.round(Double.parseDouble(objs[19].toString()) * 10)/10.0)));
 			vo.setContractprice(objs[19] == null ? null : new BigDecimal(objs[19].toString()));
 			vo.setCol8(objs[6] == null ? "" : objs[6].toString());
 			vo.setCol9(objs[7] == null ? "" : objs[7].toString());
 			vo.setCol10(objs[11] == null ? "" : objs[11].toString());
 			vo.setCol11(objs[12] == null ? "" : objs[12].toString());
 			vo.setCol12(objs[13] == null ? "" : objs[13].toString());
 			vo.setCol13(objs[14] == null ? "" : objs[14].toString());
 			vo.setCol14(objs[15] == null ? "" : objs[15].toString());
 			vo.setCol15(objs[17] == null ? "" : objs[17].toString());
 			vo.setContracttype(objs[20] == null ? "" : objs[20].toString());
 			voList.add(vo);	//把数据添加到结果集中
 		}
		result.setItems(voList);	//给返回对象赋分页查询数据
		
		return result;	//返回返回值
		
	}
	
	
	/**
	 * 
	 * @description 拼接查询sql的查询语句
	 * @param marketid
	 * @param contracttype
	 * @param searchDateType
	 * @param startDate
	 * @param endDate
	 * @param contractcyc
	 * @param prepcontractflag
	 * @param purchaser
	 * @param seller
	 * @param sequenceid
	 * @param flowflag
	 * @param marketid1
	 * @param contractName
	 * @param papercontractcode
	 * @param syscontractcode
	 * @return
	 * @author zhangzhen
	 * @date 2014-1-25
	 */
	private String getSearchSql(String marketid, String contracttype,
			String searchDateType, String startDate, String endDate,
			String contractcyc, String prepcontractflag, String purchaser,
			String seller, String sequenceid, String flowflag, String marketid1,
			String contractName, String papercontractcode, String syscontractcode,String powertype){
		String searchField = "CC.CONTRACTSTARTDATE";//合同开始日期
		if ("1".equals(searchDateType)) {//合同信息录入日期
			searchField = "CC.UPDATETIME";
		}else if ("2".equals(searchDateType)) {//合同签订日期
			searchField = "CC.SIGNEDDATE";
		}
		
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
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT CC.CONTRACTID,CC.PAPERCONTRACTCODE,CC.CONTRACTNAME,CCT.TYPENAME,BM.PARTICIPANTNAME AS PP, ");
		//两个时间格式添加
		sql.append(" BMM.PARTICIPANTNAME AS QQ , TO_CHAR(CC.CONTRACTSTARTDATE,'YYYY-MM-DD'),TO_CHAR(CC.CONTRACTENDDATE,'YYYY-MM-DD'),CC.CONTRACTQTY,TT.NAME AS AA ,TTT.NAME AS BB, ");	
		//一个时间格式添加
//		sql.append(" T.NAME AS CC ,TTTT.NAME AS DD,'' ORGAN_NAME,TO_CHAR( CC.UPDATETIME,'YYYY-MM-DD HH24:MI:SS'),TTTTT.MARKETNAME AS EE,ROUND(DECODE(SUM(CCTE.SELLERENERGY),0,0,SUM(CCTE.PRICE * CCTE.SELLERENERGY) / SUM(CCTE.SELLERENERGY)), 1) AS AVG_PRICE,count(ccai.affixname),CC.MARKETID,CC.CONTRACTPRICE,CC.CONTRACTTYPE");
		sql.append(" T.NAME AS CC ,TTTT.NAME AS DD,'' ORGAN_NAME,TO_CHAR( CC.UPDATETIME,'YYYY-MM-DD HH24:MI:SS'),TTTTT.MARKETNAME AS EE,'',count(ccai.affixname),CC.MARKETID,CC.CONTRACTPRICE,CC.CONTRACTTYPE,BMM.aliasname ");
		sql.append(" FROM  CO_CONTRACTBASEINFO CC LEFT JOIN  CO_CONTRACTBUSIFLOWINFO CCB ");
		sql.append(" ON CC.CONTRACTID = CCB.CONTRACTID LEFT JOIN   ba_gencode T ON T.type='67' AND CC.flowflag = T.VALUE ");
		sql.append(" LEFT JOIN  CO_CONTRACTTYPEINFO CCT ON CC.CONTRACTTYPE = CCT.CONTRACTTYPEID ");
		sql.append(" LEFT JOIN  BA_MARKETPARTICIPANT BM ON CC.PURCHASER = BM.PARTICIPANTID  ");
		sql.append(" LEFT JOIN  BA_MARKETPARTICIPANT BMM ON CC.SELLER = BMM.PARTICIPANTID ");
		sql.append(" LEFT JOIN  ba_gencode TT ON TT.TYPE='26'  AND CC.BACKUPSTATE = TT.VALUE ");
		sql.append(" LEFT JOIN  ba_gencode TTT ON TTT.TYPE='24' AND CC.SIGNSTATE = TTT.VALUE ");
		sql.append(" LEFT JOIN  ba_gencode TTTT ON TTTT.TYPE='27' AND CC.PREPCONTRACTFLAG = TTTT.VALUE ");
		//sql.append(" LEFT JOIN SGSOTOWER.PUB_ORGAN PO ON CC.UPDATEPERSONID = PO.ORGAN_CODE ");
		sql.append(" LEFT JOIN  BA_MARKET TTTTT ON CC.MARKETID = TTTTT.MARKETID ");
//		sql.append(" LEFT JOIN  CO_CONTRACTENERGYINFO CCTE ON CC.CONTRACTID = CCTE.CONTRACTID AND CCTE.PERIOD='1'");
		sql.append(" left join  co_contractaffixinfo ccai on cc.contractid = ccai.contractid");
		sql.append(" WHERE 1=1 ");
//		if(!ToolsSys.isnull(marketid)){
//			sql.append(" AND (CC.ISDELETE = '0' ");
//			sql.append(" AND CC.MARKETID='").append(marketid1).append("' ");
//			sql.append(" or CC.contractid in (select cbs.dataid from  co_contractbaseinfo_share cbs where cbs.marketid='"+marketid1+"'))");
//		}else{
//			sql.append(" AND (CC.ISDELETE = '0' ");
//			if(ToolsSys.isnull(marketid1)){//市场
//				if(!"91812".equals(marketid1)){//增加国网总部权限，能够查到各分部、省公司签订、录入情况。lvwentao确认100%修改2013-11-25
//					if(marketid.equals(marketid1)){
//						sql.append(" AND CC.MARKETID ='").append(marketid).append("' )");
//					}else{
//						sql.append(" AND CC.contractid in (select cbs.dataid from  co_contractbaseinfo_share cbs where cbs.smarketid='"+marketid+"' AND cbs.marketid='"+marketid1+"'))");
//					}
//				}
//				else{
//					sql.append(")");
//				}
//			}
//		}
		if(!ToolsSys.isnull(marketid)){
//			sql.append(" AND (CC.ISDELETE = '0' ");
//			sql.append(" AND CC.MARKETID='").append(marketid1).append("') ");
			sql.append(" AND (CC.MARKETID='").append(marketid1).append("') ");
		}else{
//			sql.append(" AND (CC.ISDELETE = '0' ");
//			sql.append(" AND CC.MARKETID='").append(marketid).append("') ");
			sql.append(" AND (CC.MARKETID='").append(marketid).append("') ");
			
		}
		
		
//		if(ToolsSys.isnull(contracttype)){//合同类型
//			sql.append(" and cc.contracttype in (select c.contracttypeid from  co_contracttypeinfo c where c.effectiveflag = '0'");
//			sql.append(" connect by prior c.contracttypeid = c.supertypeid start with c.contracttypeid = '").append(contracttype).append("')");
//		}
		
		if(ToolsSys.isnull(contracttype)){//合同类型 
			if(!ToolsSys.isnull(marketid)){
				sql.append(" and (cc.contracttype in (").append(contracttype).append(") or cc.contracttype in (select c.contracttypeid from  co_contracttypeinfo c where c.effectiveflag = '0' and  c.MARKETID='").append(marketid1);
				sql.append("' connect by prior c.contracttypeid = c.supertypeid start with c.contracttypeid in (").append(contracttype).append(")))");
			
			}else{
				sql.append(" and (cc.contracttype in (").append(contracttype).append(") or cc.contracttype in (select c.contracttypeid from  co_contracttypeinfo c where c.effectiveflag = '0' and  c.MARKETID='").append(marketid);
				sql.append("' connect by prior c.contracttypeid = c.supertypeid start with c.contracttypeid in (").append(contracttype).append(")))");
			}
		}
		
		//开始时间不为空，结束时间为空时，结束日期小于开始日期
		if(ToolsSys.isnull(startDate) && !ToolsSys.isnull(endDate)){//@判断是否为空
			sql.append(" AND TO_CHAR(").append ("CC.CONTRACTENDDATE").append(",'YYYY-MM-DD') >= '").append(startDate).append("' ");
		}else if(!ToolsSys.isnull(startDate) && ToolsSys.isnull (endDate)){//@判断是否为空
			sql.append(" AND TO_CHAR(").append ("CONTRACTSTARTDATE").append(",'YYYY-MM-DD') <= '").append(endDate).append("' ");
		}else if(ToolsSys.isnull(startDate) && ToolsSys.isnull (endDate)){//@判断是否为空
			sql.append(" AND (TO_CHAR(").append ("CC.CONTRACTENDDATE").append(",'YYYY-MM-DD') >= '").append(startDate).append("' AND  TO_CHAR(").append

	("CC.CONTRACTSTARTDATE").append(",'YYYY-MM-DD') <= '").append (endDate).append("') ");
		}
		if(ToolsSys.isnull(contractcyc)){//合同周期
			sql.append(" AND CC.CONTRACTCYC ='").append(contractcyc).append("' ");
		}
		if(ToolsSys.isnull(prepcontractflag)){//合同状态
			sql.append(" AND CC.PREPCONTRACTFLAG ='").append(prepcontractflag).append("' ");
		}
		if(ToolsSys.isnull(purchaser)){//购电方
			sql.append(" AND CC.PURCHASER = '").append(purchaser).append("' ");
		}
		if(ToolsSys.isnull(seller)){//售电方
			sql.append(" AND CC.SELLER ='").append(seller).append("' ");
		}
		if(ToolsSys.isnull(sequenceid)){//合同序列
			sql.append(" and CC.SEQUENCEID = '").append(sequenceid).append("' ");
		}
		if(ToolsSys.isnull(flowflag)){//@判断是否为空
			if(flowflag.equals("6")){
				sql.append(" and cc.flowflag in ( ").append(flowflag).append(" ) ");
			}else {
				sql.append(" and cc.ISDELETE = '0' ");
				sql.append(" and cc.flowflag in ( ").append(flowflag).append(" ) ");
			}
			
		}else{
			sql.append(" and cc.ISDELETE = '0' ");
		}
		if(ToolsSys.isnull(contractName)){//@判断是否为空
			sql.append(" and cc.contractName like '%").append(contractName).append("%' ");
		}
		
		// ================= Begin =================
		// ============ Author:WuYujian ============
		if(ToolsSys.isnull(papercontractcode)){//纸质合同编号
			sql.append(" and cc.papercontractcode like '%").append(papercontractcode).append("%' ");
		}
		
		if(ToolsSys.isnull(syscontractcode)){//系统合同编号
			sql.append(" and cc.expend5 like '%").append(syscontractcode).append("%' ");
		}
		
		if(ToolsSys.isnull(powertype)){//售电方发电类型
			sql.append(" and CC.SELLER in ( select b.participantid from BA_MARKETPARTICIPANT b where  b.powertype =").append(powertype).append(" ) ");
		}
		
		// ================= End =================
		
		sql.append(" group by CC.CONTRACTID, CC.PAPERCONTRACTCODE, CC.CONTRACTNAME,");
		sql.append(" CCT.TYPENAME, BM.PARTICIPANTNAME,BMM.PARTICIPANTNAME ,CC.CONTRACTSTARTDATE,CC.CONTRACTENDDATE, CC.CONTRACTQTY, TT.NAME,TTT.NAME,T.NAME,TTTT.NAME,CC.UPDATETIME,TTTTT.MARKETNAME,CC.MARKETID,CC.CONTRACTPRICE,CC.CONTRACTTYPE,BMM.aliasname,BMM.powertype ORDER BY BMM.powertype,nlssort(BMM.PARTICIPANTNAME, 'NLS_SORT = SCHINESE_PINYIN_M')  nulls last,CC.PAPERCONTRACTCODE");
		// sql.append(" ORDER BY CC.UPDATETIME DESC ");// Edit by WuYujian
		// 增加排序的SQL语句
//		if (ToolsSys.isnull(orderField) && ToolsSys.isnull(orderType)) {
//			sql.append(" ORDER BY " + orderField + " " + orderType);
//		}
		return sql.toString();
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
		dao.saveOrUpdateObject(coContractbaseinfo);
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
		dao.update((String) objArray[0], (Object[]) objArray[1]);
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
			dao.update("delete from " + CoContractbaseinfo.class.getName() + " where contractid = ?" ,new Object[]{id});
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
		List<CoContractbaseinfo> result = null;
		int count = 0;
		qc.addFrom(CoContractbaseinfo.class);
		if (queryCondition != null) {
			qc = wrapQuery(queryCondition, qc);
			count = getRecordCount(qc);
			qc = wrapPage(queryCondition, qc);
			result = dao.findAllByCriteria(qc);

		} else {
			result = dao.findAllByCriteria(qc);
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
		CoContractbaseinfo coContractbaseinfo = (CoContractbaseinfo) dao.getObject(CoContractbaseinfo.class,id);
		CoContractbaseinfoVO vo = null;
		if (coContractbaseinfo != null) {
			vo = CoContractbaseinfoTransfer.toVO(coContractbaseinfo);
		}
		return RestUtils.wrappQueryResult(vo);
	}

	
	// 获取总记录数
	private int getRecordCount(QueryCriteria qc) {
		int count = 0;
		count = dao.findAllByCriteriaPageCount(qc,1);
		return count;
	}

	public void setHibernateDao(IHibernateDao dao) {
		this.dao = dao;
	}
	//获取不同状态下的合同电量和最大最小电价
			private List<Object[]> getCount(String contractName,String marketid,
					String contracttype, String searchField, String startDate,
					String endDate, String contractcyc, String prepcontractflag,
					String purchaser, String seller, String sequenceid, String flowflag,String marketid1, String papercontractcode, String syscontractcode,String powertype) {// Edit by WuYujian-- add params[papercontractcode, syscontractcode]
					
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
					StringBuffer sql = new StringBuffer();
					StringBuffer s = new StringBuffer();
//					if(!ToolsSys.isnull(marketid)){//@判断是否为空
//						s.append(" CC.ISDELETE = '0' ");
//						s.append(" AND CC.MARKETID='").append(marketid1).append("' ");
//						s.append(" or CC.contractid in (select cbs.dataid from  co_contractbaseinfo_share cbs where cbs.marketid='"+marketid1+"')");
//					}else{
//						s.append(" CC.ISDELETE = '0' ");
//						if(ToolsSys.isnull(marketid1)){//市场
//							if(marketid.equals(marketid1)){
//								s.append(" AND CC.MARKETID ='").append(marketid).append("' ");
//							}else{
//								s.append(" AND CC.contractid in (select cbs.dataid from  co_contractbaseinfo_share cbs where cbs.smarketid='"+marketid+"' AND cbs.marketid='"+marketid1+"')");
//							}
//						}
//					}
					
					if(!ToolsSys.isnull(marketid)){
//						s.append(" CC.ISDELETE = '0' ");
//						s.append(" AND CC.MARKETID='").append(marketid1).append("' ");
						s.append(" CC.MARKETID='").append(marketid1).append("' ");
					}else{
//						s.append(" CC.ISDELETE = '0' ");
//						s.append(" AND CC.MARKETID='").append(marketid).append("' ");
						s.append(" CC.MARKETID='").append(marketid).append("' ");
						
					}
					
//					if(ToolsSys.isnull(contracttype)){//合同类型
//						s.append(" and CC.contracttype in (select c.contracttypeid from  co_contracttypeinfo c where c.effectiveflag = '0'");
//						s.append(" connect by prior c.contracttypeid = c.supertypeid start with c.contracttypeid = '").append(contracttype).append("')");
//					}
					
					if(ToolsSys.isnull(contracttype)){//合同类型 
						if(!ToolsSys.isnull(marketid)){
							s.append(" and (cc.contracttype in (").append(contracttype).append(") or cc.contracttype in (select c.contracttypeid from  co_contracttypeinfo c where c.effectiveflag = '0' and  c.MARKETID='").append(marketid1);
							s.append("' connect by prior c.contracttypeid = c.supertypeid start with c.contracttypeid in (").append(contracttype).append(")))");
						
						}else{
							s.append(" and (cc.contracttype in (").append(contracttype).append(") or cc.contracttype in (select c.contracttypeid from  co_contracttypeinfo c where c.effectiveflag = '0' and  c.MARKETID='").append(marketid);
							s.append("' connect by prior c.contracttypeid = c.supertypeid start with c.contracttypeid in (").append(contracttype).append(")))");
						}
					}
					//开始时间不为空，结束时间为空时，结束日期小于开始日期
					if(ToolsSys.isnull(startDate) && !ToolsSys.isnull(endDate)){//@判断是否为空
						s.append(" AND TO_CHAR(").append ("CC.CONTRACTENDDATE").append(",'YYYY-MM-DD') >= '").append(startDate).append("' ");
					}else if(!ToolsSys.isnull(startDate) && ToolsSys.isnull (endDate)){//@判断是否为空
						s.append(" AND TO_CHAR(").append ("CONTRACTSTARTDATE").append(",'YYYY-MM-DD') <= '").append(endDate).append("' ");
					}else if(ToolsSys.isnull(startDate) && ToolsSys.isnull (endDate)){//@判断是否为空
						s.append(" AND (TO_CHAR(").append ("CC.CONTRACTENDDATE").append(",'YYYY-MM-DD') >= '").append(startDate).append("' AND  TO_CHAR(").append

				("CC.CONTRACTSTARTDATE").append(",'YYYY-MM-DD') <= '").append (endDate).append("') ");
					}
					if(ToolsSys.isnull(contractcyc)){//合同周期
						s.append(" AND CC.CONTRACTCYC ='").append(contractcyc).append("' ");
					}
					if(ToolsSys.isnull(prepcontractflag)){//合同状态
						s.append(" AND CC.PREPCONTRACTFLAG ='").append(prepcontractflag).append("' ");
					}
					if(ToolsSys.isnull(purchaser)){//购电方
						s.append(" AND CC.PURCHASER = '").append(purchaser).append("' ");
					}
					if(ToolsSys.isnull(seller)){//售电方
						s.append(" AND CC.SELLER ='").append(seller).append("' ");
					}
					if(ToolsSys.isnull(sequenceid)){//合同序列
						s.append(" and CC.SEQUENCEID = '").append(sequenceid).append("' ");
					}
					if(ToolsSys.isnull(flowflag)){//@判断是否为空
						if(flowflag.equals("6")){
							s.append(" and cc.flowflag in ( ").append(flowflag).append(" ) ");
						}else {
							s.append(" and cc.ISDELETE = '0' ");
							s.append(" and cc.flowflag in ( ").append(flowflag).append(" ) ");
						}
						
					}else{
						s.append(" and cc.ISDELETE = '0' ");
					}
					if(ToolsSys.isnull(contractName)){//@判断是否为空
						s.append(" and cc.contractName like '%").append(contractName).append("%' ");
					}
					
					// ================= Begin =================
					// ============ Author:WuYujian ============
					if(ToolsSys.isnull(papercontractcode)){//纸质合同编号
						s.append(" and cc.papercontractcode like '%").append(papercontractcode).append("%' ");
					}
					
					if(ToolsSys.isnull(syscontractcode)){//系统合同编号
						s.append(" and cc.expend5 like '%").append(syscontractcode).append("%' ");
					}
					
					if(ToolsSys.isnull(powertype)){//售电方发电类型
						s.append(" and CC.SELLER in ( select b.participantid from BA_MARKETPARTICIPANT b where  b.powertype =").append(powertype).append(" ) ");
					}
					
					// ================= End =================
//					
					sql.append("select ");
					sql.append(" (select sum(contractqty) from (select CC.contractqty,CC.papercontractcode from  co_contractbaseinfo CC left join  ba_gencode bg");//获取应签订的合同容量
					sql.append(" on bg.value = CC.flowflag and bg.type = '67' where CC.papercontractcode is not null and ").append(s).append(" ) t) as a,");
					sql.append(" (select sum(contractqty) from (select CC.contractqty,CC.papercontractcode from  co_contractbaseinfo CC left join  ba_gencode bg");//获取已签订的合同容量
					sql.append(" on bg.value = CC.flowflag and bg.type = '67' where CC.papercontractcode is not null and CC.flowflag in (4, 5) and ").append(s).append(") tt) as b,");
					sql.append(" max(CONTRACTPRICE),min(CONTRACTPRICE)");
					sql.append(" from  co_contractbaseinfo cc");
					sql.append(" left join  co_contractenergyinfo ccte on CC.CONTRACTID = CCTE.CONTRACTID AND CCTE.PERIOD='1'");
					sql.append(" WHERE (");
//					if(!ToolsSys.isnull(marketid)){//@判断是否为空
//						sql.append(" CC.ISDELETE = '0' ");
//						sql.append(" AND CC.MARKETID='").append(marketid1).append("' ");
//						sql.append(" or CC.contractid in (select cbs.dataid from  co_contractbaseinfo_share cbs where cbs.marketid='"+marketid1+"'))");
//					}else{
//						sql.append(" CC.ISDELETE = '0' ");
//						if(ToolsSys.isnull(marketid1)){//市场
//							if(marketid.equals(marketid1)){
//								sql.append(" AND CC.MARKETID ='").append(marketid).append("' )");
//							}else{
//								sql.append(" AND CC.contractid in (select cbs.dataid from  co_contractbaseinfo_share cbs where cbs.smarketid='"+marketid+"' AND cbs.marketid='"+marketid1+"'))");
//							}
//						}
//					}
					
					if(!ToolsSys.isnull(marketid)){
//						sql.append(" CC.ISDELETE = '0' ");
//						sql.append(" AND CC.MARKETID='").append(marketid1).append("') ");
						sql.append("  CC.MARKETID='").append(marketid1).append("') ");
					}else{
//						sql.append(" CC.ISDELETE = '0' ");
//						sql.append(" AND CC.MARKETID='").append(marketid).append("') ");
						sql.append(" CC.MARKETID='").append(marketid).append("') ");
					}
					
//					if(ToolsSys.isnull(contracttype)){//合同类型
//						sql.append(" and cc.contracttype in (select c.contracttypeid from  co_contracttypeinfo c where c.effectiveflag = '0'");
//						sql.append(" connect by prior c.contracttypeid = c.supertypeid start with c.contracttypeid = '").append(contracttype).append("')");
//					}
					
					if(ToolsSys.isnull(contracttype)){//合同类型 
						if(!ToolsSys.isnull(marketid)){
							sql.append(" and (cc.contracttype in (").append(contracttype).append(") or cc.contracttype in (select c.contracttypeid from  co_contracttypeinfo c where c.effectiveflag = '0' and  c.MARKETID='").append(marketid1);
							sql.append("' connect by prior c.contracttypeid = c.supertypeid start with c.contracttypeid in (").append(contracttype).append(")))");
						
						}else{
							sql.append(" and (cc.contracttype in (").append(contracttype).append(") or cc.contracttype in (select c.contracttypeid from  co_contracttypeinfo c where c.effectiveflag = '0' and  c.MARKETID='").append(marketid);
							sql.append("' connect by prior c.contracttypeid = c.supertypeid start with c.contracttypeid in (").append(contracttype).append(")))");
						}
					}
					//开始时间不为空，结束时间为空时，结束日期小于开始日期
					if(ToolsSys.isnull(startDate) && !ToolsSys.isnull(endDate)){//@判断是否为空
						sql.append(" AND TO_CHAR(").append ("CC.CONTRACTENDDATE").append(",'YYYY-MM-DD') >= '").append(startDate).append("' ");
					}else if(!ToolsSys.isnull(startDate) && ToolsSys.isnull (endDate)){//@判断是否为空
						sql.append(" AND TO_CHAR(").append ("CONTRACTSTARTDATE").append(",'YYYY-MM-DD') <= '").append(endDate).append("' ");
					}else if(ToolsSys.isnull(startDate) && ToolsSys.isnull (endDate)){//@判断是否为空
						sql.append(" AND (TO_CHAR(").append ("CC.CONTRACTENDDATE").append(",'YYYY-MM-DD') >= '").append(startDate).append("' AND  TO_CHAR(").append

				("CC.CONTRACTSTARTDATE").append(",'YYYY-MM-DD') <= '").append (endDate).append("') ");
					}
					if(ToolsSys.isnull(contractcyc)){//合同周期
						sql.append(" AND CC.CONTRACTCYC ='").append(contractcyc).append("' ");
					}
					if(ToolsSys.isnull(prepcontractflag)){//合同状态
						sql.append(" AND CC.PREPCONTRACTFLAG ='").append(prepcontractflag).append("' ");
					}
					if(ToolsSys.isnull(purchaser)){//购电方
						sql.append(" AND CC.PURCHASER = '").append(purchaser).append("' ");
					}
					if(ToolsSys.isnull(seller)){//售电方
						sql.append(" AND CC.SELLER ='").append(seller).append("' ");
					}
					if(ToolsSys.isnull(sequenceid)){//合同序列
						sql.append(" and CC.SEQUENCEID = '").append(sequenceid).append("' ");
					}
					if(ToolsSys.isnull(flowflag)){//@判断是否为空
						if(flowflag.equals("6")){
							sql.append(" and cc.flowflag in ( ").append(flowflag).append(" ) ");
						}else {
							sql.append(" and cc.ISDELETE = '0' ");
							sql.append(" and cc.flowflag in ( ").append(flowflag).append(" ) ");
						}
						
					}else{
						sql.append(" and cc.ISDELETE = '0' ");
					}
					if(ToolsSys.isnull(contractName)){//@判断是否为空
						sql.append(" and cc.contractName like '%").append(contractName).append("%' ");
					}
					
					// ================= Begin =================
					// ============ Author:WuYujian ============
					if(ToolsSys.isnull(papercontractcode)){//纸质合同编号
						sql.append(" and cc.papercontractcode like '%").append(papercontractcode).append("%' ");
					}
					
					if(ToolsSys.isnull(syscontractcode)){//系统合同编号
						sql.append(" and cc.expend5 like '%").append(syscontractcode).append("%' ");
					}
					
					if(ToolsSys.isnull(powertype)){//售电方发电类型
						sql.append(" and CC.SELLER in ( select b.participantid from BA_MARKETPARTICIPANT b where  b.powertype =").append(powertype).append(" ) ");
					}
					
					// ================= End =================
					
					sql.append(" and cc.papercontractcode is not null group by 1");
					List<Object[]> list = dao.executeSqlQuery(sql.toString());
					return list;
			}
			
			//各状态合同个数
			private List<Object[]> getNum(String contractName,String marketid, String date, String contracttype, String searchField,
					String startDate, String endDate, String contractcyc,
					String prepcontractflag, String purchaser, String seller,
					String sequenceid, String flowflag, String marketid1, String papercontractcode, String syscontractcode,String powertype) {// Edit by WuYujian-- add params[papercontractcode, syscontractcode]
					// TODO Auto-generated method stub
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
					StringBuffer sql = new StringBuffer();
					sql.append("select count(*),t.name from (select bg.name,cc.papercontractcode from  co_contractbaseinfo cc, ");
					sql.append("  ba_gencode bg where cc.flowflag = bg.value and bg.type = '67'  ");				
//					if(!ToolsSys.isnull(marketid)){
//						sql.append(" AND (CC.ISDELETE = '0' ");
//						sql.append(" AND CC.MARKETID='").append(marketid1).append("' ");
//						sql.append(" or CC.contractid in (select cbs.dataid from  co_contractbaseinfo_share cbs where cbs.marketid='"+marketid1+"'))");
//					}else{
//						sql.append(" AND (CC.ISDELETE = '0' ");
//						if(ToolsSys.isnull(marketid1)){//市场
//							if(!"91812".equals(marketid1)){//增加国网总部权限，能够查到各分部、省公司签订、录入情况。lvwentao确认100%修改2013-11-25
//								if(marketid.equals(marketid1)){
//									sql.append(" AND CC.MARKETID ='").append(marketid).append("' )");
//								}else{
//									sql.append(" AND CC.contractid in (select cbs.dataid from  co_contractbaseinfo_share cbs where cbs.smarketid='"+marketid+"' AND cbs.marketid='"+marketid1+"'))");
//								}
//							}
//							else{
//								sql.append(")");
//							}
//						}
//					}
					
					if(!ToolsSys.isnull(marketid)){
//						sql.append(" AND (CC.ISDELETE = '0' ");
//						sql.append(" AND CC.MARKETID='").append(marketid1).append("') ");
						sql.append(" AND (CC.MARKETID='").append(marketid1).append("') ");
					}else{
//						sql.append(" AND (CC.ISDELETE = '0' ");
//						sql.append(" CC.MARKETID='").append(marketid).append("') ");
						sql.append(" AND (CC.MARKETID='").append(marketid).append("') ");
					}
					
//					if(ToolsSys.isnull(contracttype)){//合同类型
//						sql.append(" and cc.contracttype in (select c.contracttypeid from  co_contracttypeinfo c where c.effectiveflag = '0'");
//						sql.append(" connect by prior c.contracttypeid = c.supertypeid start with c.contracttypeid = '").append(contracttype).append("')");
//					}
					
					if(ToolsSys.isnull(contracttype)){//合同类型 
						if(!ToolsSys.isnull(marketid)){
							sql.append(" and (cc.contracttype in (").append(contracttype).append(") or cc.contracttype in (select c.contracttypeid from  co_contracttypeinfo c where c.effectiveflag = '0' and  c.MARKETID='").append(marketid1);
							sql.append("' connect by prior c.contracttypeid = c.supertypeid start with c.contracttypeid in (").append(contracttype).append(")))");
						
						}else{
							sql.append(" and (cc.contracttype in (").append(contracttype).append(") or cc.contracttype in (select c.contracttypeid from  co_contracttypeinfo c where c.effectiveflag = '0' and  c.MARKETID='").append(marketid);
							sql.append("' connect by prior c.contracttypeid = c.supertypeid start with c.contracttypeid in (").append(contracttype).append(")))");
						}
					}
					//开始时间不为空，结束时间为空时，结束日期小于开始日期
					if(ToolsSys.isnull(startDate) && !ToolsSys.isnull(endDate)){//@判断是否为空
						sql.append(" AND TO_CHAR(").append ("CC.CONTRACTENDDATE").append(",'YYYY-MM-DD') >= '").append(startDate).append("' ");
					}else if(!ToolsSys.isnull(startDate) && ToolsSys.isnull (endDate)){//@判断是否为空
						sql.append(" AND TO_CHAR(").append ("CONTRACTSTARTDATE").append(",'YYYY-MM-DD') <= '").append(endDate).append("' ");
					}else if(ToolsSys.isnull(startDate) && ToolsSys.isnull (endDate)){//@判断是否为空
						sql.append(" AND (TO_CHAR(").append ("CC.CONTRACTENDDATE").append(",'YYYY-MM-DD') >= '").append(startDate).append("' AND  TO_CHAR(").append

				("CC.CONTRACTSTARTDATE").append(",'YYYY-MM-DD') <= '").append (endDate).append("') ");
					}
					if(ToolsSys.isnull(contractcyc)){//合同周期
						sql.append(" AND CC.CONTRACTCYC ='").append(contractcyc).append("' ");
					}
					if(ToolsSys.isnull(prepcontractflag)){//合同状态
						sql.append(" AND CC.PREPCONTRACTFLAG ='").append(prepcontractflag).append("' ");
					}
					if(ToolsSys.isnull(purchaser)){//购电方
						sql.append(" AND CC.PURCHASER = '").append(purchaser).append("' ");
					}
					if(ToolsSys.isnull(seller)){//售电方
						sql.append(" AND CC.SELLER ='").append(seller).append("' ");
					}
					if(ToolsSys.isnull(sequenceid)){//合同序列
						sql.append(" and CC.SEQUENCEID = '").append(sequenceid).append("' ");
					}
					if(ToolsSys.isnull(flowflag)){//@判断是否为空
						if(flowflag.equals("6")){
							sql.append(" and cc.flowflag in ( ").append(flowflag).append(" ) ");
						}else {
							sql.append(" and cc.ISDELETE = '0' ");
							sql.append(" and cc.flowflag in ( ").append(flowflag).append(" ) ");
						}
						
					}else{
						sql.append(" and cc.ISDELETE = '0' ");
					}
					if(ToolsSys.isnull(contractName)){//@判断是否为空
						sql.append(" and cc.contractName like '%").append(contractName).append("%' ");
					}
					
					// ================= Begin =================
					// ============ Author:WuYujian ============
					if(ToolsSys.isnull(papercontractcode)){//纸质合同编号
						sql.append(" and cc.papercontractcode like '%").append(papercontractcode).append("%' ");
					}
					
					if(ToolsSys.isnull(syscontractcode)){//系统合同编号
						sql.append(" and cc.expend5 like '%").append(syscontractcode).append("%' ");
					}
					
					if(ToolsSys.isnull(powertype)){//售电方发电类型
						sql.append(" and CC.SELLER in ( select b.participantid from BA_MARKETPARTICIPANT b where  b.powertype =").append(powertype).append(" ) ");
					}
					
					sql.append(" group by bg.name, cc.papercontractcode ) t ");
					sql.append(" group by t.name");
//					sql.append("  ba_gencode bg where cc.flowflag = bg.value and bg.type = '67' AND cc.papercontractcode IS NOT NULL ");
//					sql.append(" and cc.CONTRACTSTARTDATE <= TO_DATE('"+date+"','YYYY-MM-DD') and cc.CONTRACTSTARTDATE >= TO_DATE('"+date.substring(0,4)+"-01-01', 'YYYY-MM-DD') ");
//					sql.append(" and cc.isdelete<>1");
//					sql.append(" and cc.marketid in (select bm.marketid from  ba_market bm ");
//					sql.append(" connect by prior bm.marketid = bm.parentmarketid start with bm.marketid = '"+marketid+"')");
//					if(!ToolsSys.isnull(marketid)){
//						sql.append(" AND (CC.ISDELETE = '0' ");
//						sql.append(" AND CC.MARKETID='").append(marketid1).append("' ");
//						sql.append(" or CC.contractid in (select cbs.dataid from  co_contractbaseinfo_share cbs where cbs.marketid='"+marketid1+"'))");
//					}else{
//						sql.append(" AND (CC.ISDELETE = '0' ");
//						if(ToolsSys.isnull(marketid1)){//市场
//							if(marketid.equals(marketid1)){
//								sql.append(" AND CC.MARKETID ='").append(marketid).append("' )");
//							}else{
//								sql.append(" AND CC.contractid in (select cbs.dataid from  co_contractbaseinfo_share cbs where cbs.smarketid='"+marketid+"' AND cbs.marketid='"+marketid1+"'))");
//							}
//						}
//					}
//					if(ToolsSys.isnull(contracttype)){//合同类型
//						sql.append(" and cc.contracttype in (select c.contracttypeid from  co_contracttypeinfo c where c.effectiveflag = '0'");
//						sql.append(" connect by prior c.contracttypeid = c.supertypeid start with c.contracttypeid = '").append(contracttype).append("')");
//					}
//					//开始时间不为空，结束时间为空时，结束日期小于开始日期
//					if(ToolsSys.isnull(startDate) && !ToolsSys.isnull(endDate)){//@判断是否为空
//						sql.append(" AND TO_CHAR(").append(searchField).append(",'YYYY-MM-DD') >= '").append(startDate).append("' ");
//					}else if(!ToolsSys.isnull(startDate) && ToolsSys.isnull(endDate)){//@判断是否为空
//						sql.append(" AND TO_CHAR(").append(searchField).append(",'YYYY-MM-DD') <= '").append(endDate).append("' ");
//					}else if(ToolsSys.isnull(startDate) && ToolsSys.isnull(endDate)){//@判断是否为空
//						sql.append(" AND (TO_CHAR(").append(searchField).append(",'YYYY-MM-DD') >= '").append(startDate).append("' AND TO_CHAR(").append
//		
//				(searchField).append(",'YYYY-MM-DD') <= '").append(endDate).append("') ");
//					}
//					if(ToolsSys.isnull(contractcyc)){//合同周期
//						sql.append(" AND CC.CONTRACTCYC ='").append(contractcyc).append("' ");
//					}
//					if(ToolsSys.isnull(prepcontractflag)){//合同状态
//						sql.append(" AND CC.PREPCONTRACTFLAG ='").append(prepcontractflag).append("' ");
//					}
//					if(ToolsSys.isnull(purchaser)){//购电方
//						sql.append(" AND CC.PURCHASER = '").append(purchaser).append("' ");
//					}
//					if(ToolsSys.isnull(seller)){//售电方
//						sql.append(" AND CC.SELLER ='").append(seller).append("' ");
//					}
//					if(ToolsSys.isnull(sequenceid)){//合同序列
//						sql.append(" and CC.SEQUENCEID = '").append(sequenceid).append("' ");
//					}
//					
//					// ================= Begin =================
//					// ============ Author:WuYujian ============
//					if(ToolsSys.isnull(papercontractcode)){//纸质合同编号
//						sql.append(" and cc.papercontractcode like '%").append(papercontractcode).append("%' ");
//					}
//					
//					if(ToolsSys.isnull(syscontractcode)){//系统合同编号
//						sql.append(" and cc.expend5 like '%").append(syscontractcode).append("%' ");
//					}
//					// ================= End =================
//					
//		//			if(ToolsSys.isnull(flowflag)){//@判断是否为空
//		//				sql.append(" and cc.flowflag = '").append(flowflag).append("' ");
//		//			}
//					sql.append(" group by bg.name, cc.papercontractcode ) t ");
//					sql.append(" group by t.name");
					return dao.executeSqlQuery(sql.toString());
			}

			//合同总数
			private List<Object[]> getSumCount(String contractName,String marketid, String contracttype,
					String searchField, String startDate, String endDate,
					String contractcyc, String prepcontractflag, String purchaser,
					String seller, String sequenceid, String flowflag, String marketid1, String papercontractcode, String syscontractcode,String powertype) {// Edit by WuYujian-- add params[papercontractcode, syscontractcode]
				// TODO Auto-generated method stub
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
				StringBuffer sql = new StringBuffer();
				sql.append("select COUNT(papercontractcode) from (  select cc.papercontractcode from  co_contractbaseinfo cc");
				sql.append(" left join  co_contractenergyinfo ccte on CC.CONTRACTID = CCTE.CONTRACTID  AND CCTE.PERIOD = '1'");
				sql.append(" where 1=1");
//				if(!ToolsSys.isnull(marketid)){
//					sql.append(" AND (CC.ISDELETE = '0' ");
//					sql.append(" AND CC.MARKETID='").append(marketid1).append("' ");
//					sql.append(" or CC.contractid in (select cbs.dataid from  co_contractbaseinfo_share cbs where cbs.marketid='"+marketid1+"'))");
//				}else{
//					sql.append(" AND (CC.ISDELETE = '0' ");
//					if(ToolsSys.isnull(marketid1)){//市场
//						if(marketid.equals(marketid1)){
//							sql.append(" AND CC.MARKETID ='").append(marketid).append("' )");
//						}else{
//							sql.append(" AND CC.contractid in (select cbs.dataid from  co_contractbaseinfo_share cbs where cbs.smarketid='"+marketid+"' AND cbs.marketid='"+marketid1+"'))");
//						}
//					}
//				}
				
				if(!ToolsSys.isnull(marketid)){
//					sql.append(" AND (CC.ISDELETE = '0' ");
//				    sql.append(" AND CC.MARKETID='").append(marketid1).append("') ");
				    sql.append(" AND (CC.MARKETID='").append(marketid1).append("') ");
				}else{
//					sql.append(" AND (CC.ISDELETE = '0' ");
//				    sql.append(" AND CC.MARKETID='").append(marketid).append("') ");
				    sql.append(" AND (CC.MARKETID='").append(marketid).append("') ");
				}
				
//				if(ToolsSys.isnull(contracttype)){//合同类型
//					sql.append(" and cc.contracttype in (select c.contracttypeid from co_contracttypeinfo c where c.effectiveflag = '0'");
//					sql.append(" connect by prior c.contracttypeid = c.supertypeid start with c.contracttypeid = '").append(contracttype).append("')");
//				}
				
				if(ToolsSys.isnull(contracttype)){//合同类型 
					if(!ToolsSys.isnull(marketid)){
						sql.append(" and (cc.contracttype in (").append(contracttype).append(") or cc.contracttype in (select c.contracttypeid from  co_contracttypeinfo c where c.effectiveflag = '0' and  c.MARKETID='").append(marketid1);
						sql.append("' connect by prior c.contracttypeid = c.supertypeid start with c.contracttypeid in (").append(contracttype).append(")))");
					
					}else{
						sql.append(" and (cc.contracttype in (").append(contracttype).append(") or cc.contracttype in (select c.contracttypeid from  co_contracttypeinfo c where c.effectiveflag = '0' and  c.MARKETID='").append(marketid);
						sql.append("' connect by prior c.contracttypeid = c.supertypeid start with c.contracttypeid in (").append(contracttype).append(")))");
					}
				}
				//开始时间不为空，结束时间为空时，结束日期小于开始日期
				if(ToolsSys.isnull(startDate) && !ToolsSys.isnull(endDate)){//@判断是否为空
					sql.append(" AND TO_CHAR(").append ("CC.CONTRACTENDDATE").append(",'YYYY-MM-DD') >= '").append(startDate).append("' ");
				}else if(!ToolsSys.isnull(startDate) && ToolsSys.isnull (endDate)){//@判断是否为空
					sql.append(" AND TO_CHAR(").append ("CONTRACTSTARTDATE").append(",'YYYY-MM-DD') <= '").append(endDate).append("' ");
				}else if(ToolsSys.isnull(startDate) && ToolsSys.isnull (endDate)){//@判断是否为空
					sql.append(" AND (TO_CHAR(").append ("CC.CONTRACTENDDATE").append(",'YYYY-MM-DD') >= '").append(startDate).append("' AND  TO_CHAR(").append

			("CC.CONTRACTSTARTDATE").append(",'YYYY-MM-DD') <= '").append (endDate).append("') ");
				}
				if(ToolsSys.isnull(contractcyc)){//合同周期
					sql.append(" AND CC.CONTRACTCYC ='").append(contractcyc).append("' ");
				}
				if(ToolsSys.isnull(prepcontractflag)){//合同状态
					sql.append(" AND CC.PREPCONTRACTFLAG ='").append(prepcontractflag).append("' ");
				}
				if(ToolsSys.isnull(purchaser)){//购电方
					sql.append(" AND CC.PURCHASER = '").append(purchaser).append("' ");
				}
				if(ToolsSys.isnull(seller)){//售电方
					sql.append(" AND CC.SELLER ='").append(seller).append("' ");
				}
				if(ToolsSys.isnull(sequenceid)){//合同序列
					sql.append(" and CC.SEQUENCEID = '").append(sequenceid).append("' ");
				}
				if(ToolsSys.isnull(flowflag)){//@判断是否为空
					if(flowflag.equals("6")){
						sql.append(" and cc.flowflag in ( ").append(flowflag).append(" ) ");
					}else {
						sql.append(" and cc.ISDELETE = '0' ");
						sql.append(" and cc.flowflag in ( ").append(flowflag).append(" ) ");
					}
					
				}else{
					sql.append(" and cc.ISDELETE = '0' ");
				}
				if(ToolsSys.isnull(contractName)){//@判断是否为空
					sql.append(" and cc.contractName like '%").append(contractName).append("%' ");
				}
				
				// ================= Begin =================
				// ============ Author:WuYujian ============
				if(ToolsSys.isnull(papercontractcode)){//纸质合同编号
					sql.append(" and cc.papercontractcode like '%").append(papercontractcode).append("%' ");
				}
				
				if(ToolsSys.isnull(syscontractcode)){//系统合同编号
					sql.append(" and cc.expend5 like '%").append(syscontractcode).append("%' ");
				}
				
				if(ToolsSys.isnull(powertype)){//售电方发电类型
					sql.append(" and CC.SELLER in ( select b.participantid from BA_MARKETPARTICIPANT b where  b.powertype =").append(powertype).append(" ) ");
				}
				
				// ================= End =================
				
				sql.append( "and cc.papercontractcode is not null group by CC.papercontractcode)");
				return dao.executeSqlQuery(sql.toString());
			}
			//获取平均值
			private List<Object[]> getPrice(String contractName,String marketid, String contracttype,
					String searchField, String startDate, String endDate,
					String contractcyc, String prepcontractflag, String purchaser,
					String seller, String sequenceid, String flowflag,String marketid1, String papercontractcode, String syscontractcode ,String powertype) {// Edit by WuYujian-- add params[papercontractcode, syscontractcode]
					
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
					StringBuffer sql = new StringBuffer();
					sql.append("select decode(sum(contractqty),0,0,sum(contractqty*contractprice)/sum(contractqty)) from (select cc.contractqty, cc.contractprice from co_contractbaseinfo cc where cc.papercontractcode is not null");
					sql.append(" and cc.contractqty is not null and cc.contractprice is not null");
					sql.append(" and (");
//					if(!ToolsSys.isnull(marketid)){//@判断是否为空
//						sql.append(" CC.ISDELETE = '0' ");
//						sql.append(" AND CC.MARKETID='").append(marketid1).append("' ");
//						sql.append(" or CC.contractid in (select cbs.dataid from co_contractbaseinfo_share cbs where cbs.marketid='"+marketid1+"'))");
//					}else{
//						sql.append(" CC.ISDELETE = '0' ");
//						if(ToolsSys.isnull(marketid1)){//市场
//							if(marketid.equals(marketid1)){
//								sql.append(" AND CC.MARKETID ='").append(marketid).append("' )");
//							}else{
//								sql.append(" AND CC.contractid in (select cbs.dataid from co_contractbaseinfo_share cbs where cbs.smarketid='"+marketid+"' AND cbs.marketid='"+marketid1+"'))");
//							}
//						}
//					}
					
					if(!ToolsSys.isnull(marketid)){
//						sql.append(" CC.ISDELETE = '0' ");
//						sql.append(" AND CC.MARKETID='").append(marketid1).append("') ");
						sql.append(" CC.MARKETID='").append(marketid1).append("') ");
					}else{
//						sql.append(" CC.ISDELETE = '0' ");
//						sql.append(" AND CC.MARKETID='").append(marketid).append("') ");
						sql.append(" CC.MARKETID='").append(marketid).append("') ");
					}
					
//					if(ToolsSys.isnull(contracttype)){//合同类型
//						sql.append(" and cc.contracttype in (select c.contracttypeid from co_contracttypeinfo c where c.effectiveflag = '0'");
//						sql.append(" connect by prior c.contracttypeid = c.supertypeid start with c.contracttypeid = '").append(contracttype).append("')");
//					}
					
					if(ToolsSys.isnull(contracttype)){//合同类型 
						if(!ToolsSys.isnull(marketid)){
							sql.append(" and (cc.contracttype in (").append(contracttype).append(") or cc.contracttype in (select c.contracttypeid from  co_contracttypeinfo c where c.effectiveflag = '0' and  c.MARKETID='").append(marketid1);
							sql.append("' connect by prior c.contracttypeid = c.supertypeid start with c.contracttypeid in (").append(contracttype).append(")))");
						
						}else{
							sql.append(" and (cc.contracttype in (").append(contracttype).append(") or cc.contracttype in (select c.contracttypeid from  co_contracttypeinfo c where c.effectiveflag = '0' and  c.MARKETID='").append(marketid);
							sql.append("' connect by prior c.contracttypeid = c.supertypeid start with c.contracttypeid in (").append(contracttype).append(")))");
						}
					}
					//开始时间不为空，结束时间为空时，结束日期小于开始日期
					if(ToolsSys.isnull(startDate) && !ToolsSys.isnull(endDate)){//@判断是否为空
						sql.append(" AND TO_CHAR(").append ("CC.CONTRACTENDDATE").append(",'YYYY-MM-DD') >= '").append(startDate).append("' ");
					}else if(!ToolsSys.isnull(startDate) && ToolsSys.isnull (endDate)){//@判断是否为空
						sql.append(" AND TO_CHAR(").append ("CONTRACTSTARTDATE").append(",'YYYY-MM-DD') <= '").append(endDate).append("' ");
					}else if(ToolsSys.isnull(startDate) && ToolsSys.isnull (endDate)){//@判断是否为空
						sql.append(" AND (TO_CHAR(").append ("CC.CONTRACTENDDATE").append(",'YYYY-MM-DD') >= '").append(startDate).append("' AND  TO_CHAR(").append

				("CC.CONTRACTSTARTDATE").append(",'YYYY-MM-DD') <= '").append (endDate).append("') ");
					}
					if(ToolsSys.isnull(contractcyc)){//合同周期
						sql.append(" AND CC.CONTRACTCYC ='").append(contractcyc).append("' ");
					}
					if(ToolsSys.isnull(prepcontractflag)){//合同状态
						sql.append(" AND CC.PREPCONTRACTFLAG ='").append(prepcontractflag).append("' ");
					}
					if(ToolsSys.isnull(purchaser)){//购电方
						sql.append(" AND CC.PURCHASER = '").append(purchaser).append("' ");
					}
					if(ToolsSys.isnull(seller)){//售电方
						sql.append(" AND CC.SELLER ='").append(seller).append("' ");
					}
					if(ToolsSys.isnull(sequenceid)){//合同序列
						sql.append(" and CC.SEQUENCEID = '").append(sequenceid).append("' ");
					}
					if(ToolsSys.isnull(flowflag)){//@判断是否为空
						if(flowflag.equals("6")){
							sql.append(" and cc.flowflag in ( ").append(flowflag).append(" ) ");
						}else {
							sql.append(" and cc.ISDELETE = '0' ");
							sql.append(" and cc.flowflag in ( ").append(flowflag).append(" ) ");
						}
						
					}else{
						sql.append(" and cc.ISDELETE = '0' ");
					}
					if(ToolsSys.isnull(contractName)){//@判断是否为空
						sql.append(" and cc.contractName like '%").append(contractName).append("%' ");
					}
					
					// ================= Begin =================
					// ============ Author:WuYujian ============
					if(ToolsSys.isnull(papercontractcode)){//纸质合同编号
						sql.append(" and cc.papercontractcode like '%").append(papercontractcode).append("%' ");
					}
					
					if(ToolsSys.isnull(syscontractcode)){//系统合同编号
						sql.append(" and cc.expend5 like '%").append(syscontractcode).append("%' ");
					}
					
					if(ToolsSys.isnull(powertype)){//售电方发电类型
						sql.append(" and CC.SELLER in ( select b.participantid from BA_MARKETPARTICIPANT b where  b.powertype =").append(powertype).append(" ) ");
					}
					
					// ================= End =================
					
					sql.append(" group by cc.contractqty, cc.contractprice)");
					List<Object[]> list = dao.executeSqlQuery(sql.toString());
					return list; 
			}
			
			/**
			 * 
			 * @description 根据合同id查询合同序列id和合同序列名称
			 * @param contractid
			 * @return
			 * @author mengke
			 * @date 2014-5-16
			 */
			public List getHtxl(String contractid){
				StringBuffer sql = new StringBuffer();
				sql.append("SELECT T.SEQUENCEID, S.SEQUENCENAME FROM CO_CONTRACTBASEINFO T ")
				.append(" LEFT JOIN CO_CONTRACSEQUENCEINFO S ")
				.append(" ON S.SEQUENCEID = T.SEQUENCEID WHERE T.CONTRACTID = ?");
				List list = dao.executeSqlQuery(sql.toString(), new Object[]{contractid});
				return list;
			}

			/**
			 * 
			 * @description 判断是否是发电权合同
			 * @param map
			 * @return
			 * @author mengke
			 * @date 2014-6-24
			 */
			public List isfdqht(Map map){
				
				StringBuffer sql = new StringBuffer();
				sql.append("select t.contracttypeid from co_contracttypeinfo t where ")
				.append(" t.contracttypeid = '4DC24C12-413C-5A67-BEBE-AB07C0C' or t.supertypeid = '4DC24C12-413C-5A67-BEBE-AB07C0C'");
				List list = dao.executeSqlQuery(sql.toString());
				return list;
			}

			public String copyBull(List<Map> list,String marketId,String userId) {
				if(list!=null&&list.size()>0){
					for(int i=0;i<list.size();i++){
						Map map =list.get(i);
						String contractid = map.get("col0").toString();
						try{
							copyContract(contractid,marketId,userId,map);
						}catch(Exception e){
							return "error";
						}
					}
				}else{
					return "error";
				}
				return "succ";
			}
			public String copyContract(String contractId,String marketId,String userId,Map map) {
				String newContractId=Guid.create();
				ComUtils comUtils = new ComUtils();
				CoContractbaseinfo tenCo = dao.getObject(CoContractbaseinfo.class, contractId);
				CoContractbaseinfo co=new CoContractbaseinfo();
//				comUtils.obj2obj(tenCo, co);
				if(tenCo!=null){
					co.setContractname(map.get("col2")==null?tenCo.getContractname()+" - 副本":map.get("col2").toString());
					co.setIsdelete(new BigDecimal("0"));//删除标记
					co.setVersionid(Guid.create());
					co.setVersion("V1");
					co.setCoVersion("V1");
					co.setChangetimes(new BigDecimal(1));
					co.setUpdatepersonid(userId);
					co.setUpdatetime(new Date());
					co.setMarketid(marketId);
					co.setContractid(newContractId);
					co.setSupercontractid(tenCo.getSupercontractid());
					co.setContracttype(tenCo.getContracttype());
					co.setPapercontractcode(map.get("col1")==null?tenCo.getPapercontractcode():map.get("col1").toString());
					co.setPapercontractname(tenCo.getPapercontractname());
					co.setSignstate(tenCo.getSignstate());
					co.setSigneddate(tenCo.getSigneddate());
					co.setBackupstate(tenCo.getBackupstate());
					co.setContractcyc(tenCo.getContractcyc());
					co.setPurchaser(tenCo.getPurchaser());
					co.setSeller(tenCo.getSeller());
					co.setSellerunit(tenCo.getSellerunit());
					co.setPurchaseunit(tenCo.getPurchaseunit());
					co.setContractqty(tenCo.getContractqty());
					co.setQtytype(tenCo.getQtytype());
					co.setContractstartdate(map.get("col8")==null?tenCo.getContractstartdate():DateUtil.getUtilDate(map.get("col8").toString(),"yyyy-MM-dd"));
					co.setContractenddate(map.get("col9")==null?tenCo.getContractenddate():DateUtil.getUtilDate(map.get("col9").toString(),"yyyy-MM-dd"));
					co.setPurchasegate(tenCo.getPurchasegate());
					co.setSettlegate(tenCo.getSettlegate());
					co.setLossundertaker(tenCo.getLossundertaker());
					co.setIsrelation(tenCo.getIsrelation());
					co.setExectype(tenCo.getExectype());
					co.setSequenceid(tenCo.getSequenceid());
					co.setEnergy(map.get("energy")==null?tenCo.getEnergy():new BigDecimal(map.get("energy").toString()));
					co.setContractprice(map.get("contractprice")==null?tenCo.getContractprice():new BigDecimal(map.get("contractprice").toString()));
					co.setContractenergy(tenCo.getContractenergy());
					co.setSuperexecid(tenCo.getSuperexecid());
					co.setIngodown(tenCo.getIngodown());
					co.setPurchasergen(tenCo.getPurchasergen());
					co.setPurchaserenergy(tenCo.getPurchaserenergy());
					co.setSellergen(tenCo.getSellergen());
					co.setSellerenergy(tenCo.getSellerenergy());
					co.setIsTax(tenCo.getIsTax());
					co.setVendeeGenRate(tenCo.getVendeeGenRate());
					co.setSaleGenRate(tenCo.getSaleGenRate());
					co.setSaleLoss(tenCo.getSaleLoss());
					co.setVendeeLoss(tenCo.getVendeeLoss());
					co.setVendeeBreathPrice(tenCo.getVendeeBreathPrice());
					co.setSaleBreathPrice(tenCo.getSaleBreathPrice());
					co.setApprovedTariff(tenCo.getApprovedTariff());
					co.setCatalogPrice(tenCo.getCatalogPrice());
					co.setTransferAllotPrice(tenCo.getTransferAllotPrice());
					co.setNetDiscountLoss(tenCo.getNetDiscountLoss());
					co.setFundsandaddPrice(tenCo.getFundsandaddPrice());
					co.setTradePriceMargin(tenCo.getTradePriceMargin());
					
					//新的合同Id
					dao.saveObject(co);//@新的合同执行保存操作
					CoContractchangerecordinfo coContractchangerecordinfo = new CoContractchangerecordinfo();
					ComUtils.obj2obj(co, coContractchangerecordinfo);
					coContractchangerecordinfo.setCmd("新增操作");
					coContractchangerecordinfo.setOperator(userId);
					coContractchangerecordinfo.setOperatingdescription("合同复制新增");
					coContractchangerecordinfo.setOperatedate(new Date());
					coContractchangerecordinfo.setOrderno(new BigDecimal(1));
					coContractchangerecordinfo.setVersionid(co.getContractid());
					//合同历史表
					dao.saveObject(coContractchangerecordinfo);//@保存合同更改记录
					//合同峰谷电量电价维护，2013-11-25，lvwentao确认要改100%
					CoContractenergyinfo ccei = new CoContractenergyinfo();
					ccei.setContractid(contractId);
					List<CoContractenergyinfo> cceilist = dao.findAll("from "+CoContractenergyinfo.class.getName()+ " where contractId = ? ",new Object[]{contractId});//@根据ccei查询信息
					for (int i = 0; i < cceilist.size(); i++) {
						ccei = cceilist.get(i);
						ccei.setContractid(co.getContractid());
						dao.saveObject(ccei);//@保存合同峰谷电量电价
					}
					//输电方，2013-11-25，lvwentao确认要改100%
					CoContransenergyinfo cce = new CoContransenergyinfo();
					cce.setContractid(contractId);
					List<CoContransenergyinfo> ccelist = dao.findAll("from "+CoContransenergyinfo.class.getName()+ " where contractId = ? ",new Object[]{contractId});//@查询输电方
					for (int i = 0; i < ccelist.size(); i++) {
						cce = ccelist.get(i);
						cce.setContractid(co.getContractid());
						dao.saveObject(cce);//@保存输电方
					}
					//备案信息，2013-11-25，lvwentao确认要改100%
					CoContractbackupinfo ccbk = new CoContractbackupinfo();
					ccbk.setContractid(contractId);
					List<CoContractbackupinfo> ccbklist = dao.findAll("from "+CoContractbackupinfo.class.getName()+ " where contractId = ? ",new Object[]{contractId});//@查询备案信息
					for (int i = 0; i < ccbklist.size(); i++) {
						ccbk = ccbklist.get(i);
						ccbk.setContractid(co.getContractid());
						dao.saveObject(ccbk);//@保存备案信息
					}
					//线损，2013-11-25，lvwentao确认要改100%
					CoContranslossinfo ccl = new CoContranslossinfo();
					ccl.setContractid(contractId);
					List<CoContranslossinfo> ccllist = dao.findAll("from "+CoContranslossinfo.class.getName()+ " where contractId = ? ",new Object[]{contractId});//@查询线损
					for (int i = 0; i < ccllist.size(); i++) {
						ccl = ccllist.get(i);
						ccl.setContractid(co.getContractid());
						dao.saveObject(ccl);//@保存线损信息
					}
					//合同电量按月分解
					copyCoContractenergyinfo(contractId,co.getContractid());
					
					//合同输电信息
					copyTransqtyinfo(contractId,co.getContractid());
					
					//计量点信息
					copyCoContractgateinfo(contractId,co.getContractid());
					
					//合同机组信息
					copyCoContractaccessory(contractId,co.getContractid());
					
					
					//合同签订信息
					copyCoContractbackupinfo(contractId,co.getContractid());
					
					//合同文本
//					copyCoContractaffixinfo(contractId,co.getContractid());
					
				}else{
					return "0";
				}
				return "1";
			}
			/**
			 * 复制电量按月分解 复制
			 * @description 方法描述
			 * @param sourceID
			 * @param targetID
			 * @date 2014-2-26
			 */
			public void copyCoContractenergyinfo(String sourceID, String targetID) {
				ComUtils comUtils = new ComUtils();
				String sdate="";
				String edate="";
				int syear;
				int eyear;
				int smonth;
				int emonth;
				int sday;
				int eday; 
				String StrBeginLastDay="" ;//合同开始月份最后一天天数
				String beginLastDay="" ;//合同开始月份最后一天
				String endFirstDay="" ;//合同截止月份第一天
				String sql = "select co.contractid,to_char(co.contractstartdate,'yyyy-mm-dd'),to_char(co.contractenddate,'yyyy-mm-dd'),nvl(co.contractqty,''),nvl(co.sellergen,''),nvl(co.sellerenergy,''),nvl(co.purchasergen,''),nvl(co.purchaserenergy,''),nvl(co.contractenergy,'')";
				sql += "from co_contractbaseinfo co where co.contractid = '"+sourceID+"'";
				List list = dao.executeSqlQuery(sql);
				Object [] obj = null;
				if (list != null && list.size() > 0) {
					obj = (Object[]) list.get(0);
				}
				sdate = ToolsSys.ObjIsNullToStr(obj[1]);//开始时间		
				edate = ToolsSys.ObjIsNullToStr(obj[2]);//结束时间
				if(!ToolsSys.isnull(sdate)||!ToolsSys.isnull(edate)||(obj[3]==null && obj[4]==null && obj[5]==null && obj[6]==null && obj[7]==null)){
					return ;
				}else{
					syear = Integer.parseInt(sdate.substring(0, 4));//开始时间年份
					smonth = Integer.parseInt(sdate.substring(5, 7));//开始时间月份
					sday = Integer.parseInt(sdate.substring(8, 10));//开始时间日期
					eyear = Integer.parseInt(edate.substring(0, 4));//结束时间年份
					emonth = Integer.parseInt(edate.substring(5, 7));//结束时间月份
					eday = Integer.parseInt(edate.substring(8, 10));//结束时间日期	
					StrBeginLastDay = String.valueOf(yearMonthDays(syear, smonth));//合同开始月份最后一天天数
					beginLastDay = getNewDate(sdate.substring(0, 4), sdate.substring(5, 7), StrBeginLastDay);//合同开始月份最后一天
					endFirstDay = getNewDate(edate.substring(0, 4), edate.substring(5, 7), "1");//合同截止月份第一天
					
					String startdate1 = sdate;
					String enddate1 = beginLastDay;
					//@根据合同id，开始日期，结束日期查询数据
					List list1=dao.findAll("from "+CoContractenergyinfo.class.getName()+ " where contractid = ? " +
							" and startdate=to_date(?,'yyyy-mm-dd') and enddate=to_date(?,'yyyy-mm-dd') and whereinsert = '0'",
							new Object[]{sourceID,startdate1,enddate1});
					//中间月份的
					for (int i = smonth+1; i < emonth; i++) {
						String startdate2 = syear+"-"+i+"-1" ;
						String enddate2 = syear+"-"+i+"-"+yearMonthDays(syear, i);
						List list2=dao.findAll("from "+CoContractenergyinfo.class.getName()+ " where contractid = ? " +
								" and startdate=to_date(?,'yyyy-mm-dd') and enddate=to_date(?,'yyyy-mm-dd') and whereinsert = '0'",
								new Object[]{sourceID,startdate2,enddate2});
						if(list2!=null){
							list1.addAll(list2);
						}
					}
					//最后一个月的
					String startdate3 = endFirstDay;
					String enddate3 = edate;
					List list3=dao.findAll("from "+CoContractenergyinfo.class.getName()+ " where contractid = ? " +
							" and startdate=to_date(?,'yyyy-mm-dd') and enddate=to_date(?,'yyyy-mm-dd') and whereinsert = '0'",
							new Object[]{sourceID,startdate3,enddate3});
					if(list3!=null){
						list1.addAll(list3);
					}
					if(list1.size()!=0){
						for(int i=0;i<list1.size();i++){
							CoContractenergyinfo coContractenergyinfo=new CoContractenergyinfo();
							comUtils.obj2obj((CoContractenergyinfo) list1.get(i), coContractenergyinfo);
							coContractenergyinfo.setContractid(targetID);
							coContractenergyinfo.setGuid(null);
							dao.saveObject(coContractenergyinfo);
						}
					}
				}
			}
			
			/**
			 * 复制合同签订信息
			 * @description 方法描述
			 * @param sourceID
			 * @param targetID
			 * @date 2014-2-26
			 */
			public void copyCoContractbackupinfo(String sourceID, String targetID) {
				ComUtils comUtils = new ComUtils();
				List list=dao.findAll("from "+CoContractbackupinfo.class.getName()+ " where contractid = ? ",new Object[]{sourceID});
				for(int i=0;i<list.size();i++){
					CoContractbackupinfo coContractbackupinfo=new CoContractbackupinfo();
					comUtils.obj2obj((CoContractbackupinfo) list.get(i), coContractbackupinfo);
					coContractbackupinfo.setContractid(targetID);
					coContractbackupinfo.setGuid(null);
					dao.saveObject(coContractbackupinfo);
				}
			}
			/**
			 * 某年某月有多少天
			 * @description 方法描述
			 * @param year
			 * @param month
			 * @return
			 * @author zhangyan
			 * @date 2014-02-08
			 */
			public static int yearMonthDays(int year,int month){
				int yearMonthDays=0;
				switch(month){
				case 1:
				case 3:
				case 5:
				case 7:
				case 8:
				case 10:
				case 12:
					yearMonthDays=31;
					break;
				case 4:
				case 6:
				case 9:
				case 11:
					yearMonthDays=30;
					break;
				case 2:
					if(isLeapYear(year)){
						yearMonthDays=29;
					}else{
						yearMonthDays=28;
					}
					break;
				}
				return yearMonthDays;
			}
			/**
			 * 某年是否为润年
			 * @description 方法描述
			 * @param year
			 * @author zhangyan
			 * @date 2014-02-08
			 */
			static boolean isLeapYear(int year){
				return (year%4==0&&year%100!=0||year%400==0);
			}
			/**
			 * 根据给定的年月日返回DATE类型的日期
			 * @description 方法描述
			 * @param year
			 * @param month
			 * @param day
			 * @return
			 * @author zhangyan
			 * @date 2014-02-08
			 */
			public static String getNewDate(String year, String month, String day){
				String StrNewDate = year + "-" + month + "-" + day;
				return StrNewDate;
			}
			/**
			 * 从vo中获取页面展示元数据信息
			 * 
			 * sourceID  原合同id     复制新增的合同id
			 */
			public void copyTransqtyinfo(String sourceID, String targetID) {
				ComUtils comUtils = new ComUtils();
				List list =dao.findAll("from "+CoTransqtyinfo.class.getName()+ " where contractid = ? ",new Object[]{sourceID});//@查询线损
				int coSize = list.size();
				for(int i=0; i<coSize; i++){
					CoTransqtyinfo coTransqtyinfo = new CoTransqtyinfo();
					CoTransqtyinfo coTransqtyinfoOld = (CoTransqtyinfo) list.get(i);
					comUtils.obj2obj(coTransqtyinfoOld, coTransqtyinfo);
					coTransqtyinfo.setContractid(targetID);
					coTransqtyinfo.setTransinfoid(null);
					dao.saveObject(coTransqtyinfo);
					/*电力信息*/
					List listSlave = dao.findAll("from "+CoTransqtyslaveinfo.class.getName()+ " where transinfoid = ? ",new Object[]{ coTransqtyinfoOld.getTransinfoid()});//@查询线损
					int coSlaveSize = listSlave.size();
					for(int j=0; j<listSlave.size(); j++){
						com.sgcc.dlsc.entity.po.CoTransqtyslaveinfo coTransqtyslaveinfo = new CoTransqtyslaveinfo();
						comUtils.obj2obj((CoTransqtyslaveinfo) listSlave.get(j), coTransqtyslaveinfo);
						coTransqtyslaveinfo.setTransinfoid(coTransqtyinfo.getTransinfoid());
						coTransqtyslaveinfo.setGuid(null);
						dao.saveObject(coTransqtyslaveinfo);
					}
				}
			}
			/**
			 * 复制计量点信息
			 * @description 方法描述
			 * @param sourceID  原合同id
			 * @param targetID  新的合同id
			 * @author lvwentao
			 * @date 2014-2-26
			 */
			public void copyCoContractgateinfo(String sourceID, String targetID) {
				ComUtils comUtils = new ComUtils();
				List list =dao.findAll("from "+CoContractgateinfo.class.getName()+ " where contractid = ? ",new Object[]{sourceID});
				int coSize = list.size();
				for(int i=0; i<coSize; i++){
					CoContractgateinfo coContractgateinfo = new CoContractgateinfo();
					comUtils.obj2obj((CoContractgateinfo) list.get(i), coContractgateinfo);
					coContractgateinfo.setContractid(targetID);
					coContractgateinfo.setGuid(null);
					dao.saveObject(coContractgateinfo);
				}
			}
			/**
			 * 复制合同机组信息
			 * @description 方法描述
			 * @param sourceID
			 * @param targetID 
			 * @date 2014-2-26
			 */
			public void copyCoContractaccessory(String sourceID, String targetID) {
				ComUtils comUtils = new ComUtils();
				List list =dao.findAll("from "+CoContractaccessory.class.getName()+ " where contractid = ? ",new Object[]{sourceID});
				int coSize = list.size();
				for(int i=0; i<coSize; i++){
					CoContractaccessory coContractaccessory = new CoContractaccessory();
					comUtils.obj2obj((CoContractaccessory) list.get(i), coContractaccessory);
					coContractaccessory.setContractid(targetID);
					coContractaccessory.setGuid(null);
					dao.saveObject(coContractaccessory);
				}
				
			}
}
