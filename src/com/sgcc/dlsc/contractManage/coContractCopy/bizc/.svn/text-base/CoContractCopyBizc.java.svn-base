package com.sgcc.dlsc.contractManage.coContractCopy.bizc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.sgcc.dlsc.commons.util.ComUtils;
import com.sgcc.dlsc.commons.util.Guid;
import com.sgcc.dlsc.commons.util.ToolsSys;
import com.sgcc.dlsc.commons.util.UserInfoUtil;
import com.sgcc.dlsc.contractManage.coContractbaseinfoV2Act.vo.CoContractbaseinfoVO;
import com.sgcc.dlsc.entity.po.CoContractaccessory;
import com.sgcc.dlsc.entity.po.CoContractaffixinfo;
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
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.WrappedResult;

public class CoContractCopyBizc implements ICoContractCopyBizc{

	
	@Autowired
	private IHibernateDao dao;
	
	@Autowired
	private IDataDictionaryBizC dataDictionaryBizC;
	
	
	/**
	 * @author 张岩
	 * 合同复制的查询功能
	 * marketid 查询条件 的业务场景
	 * contracttype 合同类型
	 * searchDateType 时间类型
	 * startDate 开始时间
	 * endDate  结束时间
	 * contractcyc 合同周期
	 * prepcontractflag 合同状态
	 * purchaser  够电方
	 * seller 售电方
	 * sequenceid 合同序列
	 * marketid1 当前用户的 场景
	 * */
	public QueryResultObject findCoContractList11(int pageIndex,int pageSize,String marketid, String contracttype,
			String searchDateType, String startDate, String endDate,
			String contractcyc, String prepcontractflag, String purchaser,
			String seller, String sequenceid,
			String marketid1) {
		
			int size=0;
			QueryResultObject result = new QueryResultObject();//新建返回值对象
			//时间开始和截至日期查询的字段
			String searchField = "CC.CONTRACTSTARTDATE";//合同开始日期
			if ("1".equals(searchDateType)) {//合同信息录入日期
				searchField = "CC.UPDATETIME";
			}else if ("2".equals(searchDateType)) {//合同签订日期
				searchField = "CC.SIGNEDDATE";
			}
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT CC.CONTRACTID,CC.PAPERCONTRACTCODE,CC.CONTRACTNAME,CCT.TYPENAME,BM.PARTICIPANTNAME AS PP, ");
			sql.append(" BMM.PARTICIPANTNAME AS QQ ,CC.CONTRACTSTARTDATE,CC.CONTRACTENDDATE,CC.CONTRACTQTY,TT.NAME AS AA ,TTT.NAME AS BB, ");
//			sql.append(" T.NAME AS CC ,TTTT.NAME AS DD,PO.ORGAN_NAME,CC.UPDATETIME,TTTTT.MARKETNAME AS EE,ROUND(DECODE(SUM(CCTE.SELLERENERGY),0,0,SUM(CCTE.PRICE * CCTE.SELLERENERGY) / SUM(CCTE.SELLERENERGY)), 1) AS AVG_PRICE,count(ccai.affixname),CC.MARKETID,CC.CONTRACTPRICE");
			sql.append(" T.NAME AS CC ,TTTT.NAME AS DD,CC.UPDATETIME,TTTTT.MARKETNAME AS EE,ROUND(DECODE(SUM(CCTE.SELLERENERGY),0,0,SUM(CCTE.PRICE * CCTE.SELLERENERGY) / SUM(CCTE.SELLERENERGY)), 1) AS AVG_PRICE,count(ccai.affixname),CC.MARKETID,CC.CONTRACTPRICE");
			sql.append(" FROM CO_CONTRACTBASEINFO CC LEFT JOIN CO_CONTRACTBUSIFLOWINFO CCB ");
			sql.append(" ON CC.CONTRACTID = CCB.CONTRACTID LEFT JOIN  ba_gencode T ON T.type='67' AND CC.flowflag = T.VALUE ");
			sql.append(" LEFT JOIN CO_CONTRACTTYPEINFO CCT ON CC.CONTRACTTYPE = CCT.CONTRACTTYPEID ");
			sql.append(" LEFT JOIN BA_MARKETPARTICIPANT BM ON CC.PURCHASER = BM.PARTICIPANTID  ");
			sql.append(" LEFT JOIN BA_MARKETPARTICIPANT BMM ON CC.SELLER = BMM.PARTICIPANTID ");
			sql.append(" LEFT JOIN ba_gencode TT ON TT.TYPE='26'  AND CC.BACKUPSTATE = TT.VALUE ");
			sql.append(" LEFT JOIN ba_gencode TTT ON TTT.TYPE='24' AND CC.SIGNSTATE = TTT.VALUE ");
			sql.append(" LEFT JOIN ba_gencode TTTT ON TTTT.TYPE='27' AND CC.PREPCONTRACTFLAG = TTTT.VALUE ");
//			sql.append(" LEFT JOIN SGSOTOWER.PUB_ORGAN PO ON CC.UPDATEPERSONID = PO.ORGAN_CODE ");
			sql.append(" LEFT JOIN BA_MARKET TTTTT ON CC.MARKETID = TTTTT.MARKETID ");
			sql.append(" LEFT JOIN CO_CONTRACTENERGYINFO CCTE ON CC.CONTRACTID = CCTE.CONTRACTID AND CCTE.PERIOD='1'");
			sql.append(" left join co_contractaffixinfo ccai on cc.contractid = ccai.contractid");
			sql.append(" WHERE 1=1 ");
			if(!ToolsSys.isnull(marketid)){
				sql.append(" AND (CC.ISDELETE = '0' ");
				sql.append(" AND CC.MARKETID='").append(marketid1).append("') ");
//				sql.append(" or CC.contractid in (select cbs.dataid from co_contractbaseinfo_share cbs where cbs.marketid='"+marketid1+"')");
				
				//sql.append(" and cc.contractid not in (select ccc.contractid from co_contractchangerecordinfo ccc where ccc.marketid='"+marketid1+"'))");
			}else{
				sql.append(" AND (CC.ISDELETE = '0' ");
				if(ToolsSys.isnull(marketid1)){//市场
					if(marketid.equals(marketid1)){
						sql.append(" AND CC.MARKETID ='").append(marketid).append("' )");
					}else{
						sql.append(")");
//						sql.append(" AND CC.contractid in (select cbs.dataid from co_contractbaseinfo_share cbs where cbs.smarketid='"+marketid+"' AND cbs.marketid='"+marketid1+"')");
					}
				}
			}
			if(ToolsSys.isnull(contracttype)){//合同类型
				sql.append(" and cc.contracttype in (select c.contracttypeid from co_contracttypeinfo c where c.effectiveflag = '0'");
				sql.append(" connect by prior c.contracttypeid = c.supertypeid start with c.contracttypeid = '").append(contracttype).append("')");
			}
			//开始时间不为空，结束时间为空时，结束日期小于开始日期
			if(ToolsSys.isnull(startDate) && !ToolsSys.isnull(endDate)){//@判断是否为空
				sql.append(" AND TO_CHAR(").append(searchField).append(",'YYYY-MM-DD') >= '").append(startDate).append("' ");
			}else if(!ToolsSys.isnull(startDate) && ToolsSys.isnull(endDate)){//@判断是否为空
				sql.append(" AND TO_CHAR(").append(searchField).append(",'YYYY-MM-DD') <= '").append(endDate).append("' ");
			}else if(ToolsSys.isnull(startDate) && ToolsSys.isnull(endDate)){//@判断是否为空
				sql.append(" AND (TO_CHAR(").append(searchField).append(",'YYYY-MM-DD') >= '").append(startDate).append("' AND TO_CHAR(").append
				(searchField).append(",'YYYY-MM-DD') <= '").append(endDate).append("') ");
			}
			if(ToolsSys.isnull(contractcyc)){//合同周期
				sql.append(" AND CC.CONTRACTCYC ='").append(contractcyc).append("' ");
			}
			if(ToolsSys.isnull(prepcontractflag)){//合同状态
				sql.append(" AND CC.flowflag ='").append(prepcontractflag).append("' ");
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
//			sql.append(" CCT.TYPENAME, BM.PARTICIPANTNAME,BMM.PARTICIPANTNAME ,CC.CONTRACTSTARTDATE,CC.CONTRACTENDDATE, CC.CONTRACTQTY, TT.NAME,TTT.NAME,T.NAME,TTTT.NAME,PO.ORGAN_NAME,CC.UPDATETIME,TTTTT.MARKETNAME,CC.MARKETID,CC.CONTRACTPRICE ");
			sql.append(" CCT.TYPENAME, BM.PARTICIPANTNAME,BMM.PARTICIPANTNAME ,CC.CONTRACTSTARTDATE,CC.CONTRACTENDDATE, CC.CONTRACTQTY, TT.NAME,TTT.NAME,T.NAME,TTTT.NAME,CC.UPDATETIME,TTTTT.MARKETNAME,CC.MARKETID,CC.CONTRACTPRICE ");
			String sql2 = "SELECT COUNT(*) C FROM ( "+ sql.toString() + " ) T ";	
			List list1 = new ArrayList();
			list1 = dao.executeSqlQuery(sql2.toString());
			if(list1.size() > 0){	//如果有查询结果，给数据条数变量赋值
				size = Integer.parseInt(list1.get(0).toString());
			}
			sql.append(" ORDER BY CC.UPDATETIME DESC ");
			result.setItemCount(size);	//给返回对象赋数据的数量
			List list = new ArrayList();
			list = dao.executeSqlQuery(sql.toString(),pageIndex,pageSize);	//查询分页的数据
			List<CoContractbaseinfoVO> voList = new ArrayList<CoContractbaseinfoVO>();		//返回的数据结果集
			for (int i = 0; i < list.size(); i++) { // 遍历要展示的数据
				Object[] objs = (Object[]) list.get(i); // 得到第i条数据
				CoContractbaseinfoVO vo = new CoContractbaseinfoVO(); // 数据结果集中的一条数据
				// 给每个变量赋值
				vo.setCol0(objs[0] == null ? "" : objs[0].toString());	//合同id
				vo.setCol1(objs[1] == null ? "" : objs[1].toString());	//纸质合同编号  主合同编号
				vo.setCol2(objs[2] == null ? "" : objs[2].toString());	//合同名称
				vo.setCol3(objs[3] == null ? "" : objs[3].toString());	//合同类型
				vo.setCol4(objs[4] == null ? "" : objs[4].toString());  //够电方
				vo.setCol5(objs[5] == null ? "" : objs[5].toString());	//售电方
				vo.setCol6(objs[6] == null ? "" : objs[6].toString());	//开始时间
				vo.setCol7(objs[7] == null ? "" : objs[7].toString());  //结束时间
				vo.setCol8(objs[8] == null ? "" : objs[8].toString());	//总电量
				vo.setCol9(objs[18] == null ? "" : objs[18].toString()); //电价
				vo.setCol10(objs[9] == null ? "" : objs[9].toString());//备案状态
				vo.setCol11(objs[10] == null ? "" : objs[10].toString());//签订状态
				vo.setCol12(objs[11] == null ? "" : objs[11].toString());//合同流转状态
				vo.setCol13(objs[11] == null ? "" : objs[11].toString());//合同状态
//				vo.setCol14(objs[13] == null ? "" : objs[13].toString()); //维护人
//				vo.setCol15(objs[14] == null ? "" : objs[14].toString());//维护时间
//				vo.setCol16(objs[15] == null ? "" : objs[15].toString());//维护单位
//				vo.setCol17(objs[17] == null ? "" : objs[17].toString());//附件下载
				voList.add(vo); // 把数据添加到结果集中
			}
			result.setItems(voList);	//给返回对象赋分页查询数据
			return result;	//返回返回值
	}


	public String copyContract1(String contractId,String marketId,String userId,String newContractId) {
		ComUtils comUtils = new ComUtils();
		CoContractbaseinfo tenCo = dao.getObject(CoContractbaseinfo.class, contractId);
		CoContractbaseinfo co=new CoContractbaseinfo();
		comUtils.obj2obj(tenCo, co);
		if (co!=null) {
			co.setContractname(co.getContractname()+" - 副本");
			co.setVersion("V1");
			co.setCoVersion("V1");
			co.setChangetimes(new BigDecimal(1));
			co.setUpdatepersonid(userId);
			co.setUpdatetime(new Date());
			//co.setContractid(Guid.create());
			co.setMarketid(marketId);
			co.setContractid(newContractId);
			
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
//			copyCoContractaffixinfo(contractId,co.getContractid());
			
		}else{
			return "0";
		}
		return "1";
	}
	/**
	 * 指定字段合同复制
	 */
	public String copyContract(String contractId,String marketId,String userId,String newContractId) {
		ComUtils comUtils = new ComUtils();
		CoContractbaseinfo tenCo = dao.getObject(CoContractbaseinfo.class, contractId);
		CoContractbaseinfo co=new CoContractbaseinfo();
//		comUtils.obj2obj(tenCo, co);
		if(tenCo!=null){
			co.setContractname(tenCo.getContractname()+" - 副本");
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
			co.setPapercontractcode(tenCo.getPapercontractcode());
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
			co.setContractstartdate(tenCo.getContractstartdate());
			co.setContractenddate(tenCo.getContractenddate());
			co.setPurchasegate(tenCo.getPurchasegate());
			co.setSettlegate(tenCo.getSettlegate());
			co.setLossundertaker(tenCo.getLossundertaker());
			co.setIsrelation(tenCo.getIsrelation());
			co.setExectype(tenCo.getExectype());
			co.setSequenceid(tenCo.getSequenceid());
			co.setEnergy(tenCo.getEnergy());
			co.setContractprice(tenCo.getContractprice());
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
//			copyCoContractaffixinfo(contractId,co.getContractid());
			
		}else{
			return "0";
		}
		return "1";
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
	
	/**
	 * 复制合同文本信息
	 * @description 方法描述
	 * @param sourceID
	 * @param targetID
	 * @date 2014-2-26
	 */
	public void copyCoContractaffixinfo(String sourceID, String targetID) {
		ComUtils comUtils = new ComUtils();
		List list =dao.findAll("from "+CoContractaffixinfo.class.getName()+ " where contractid = ? ",new Object[]{sourceID});
		for(int i=0; i<list.size(); i++){
			CoContractaffixinfo coContractaffixinfo = new CoContractaffixinfo();
			comUtils.obj2obj((CoContractaffixinfo) list.get(i), coContractaffixinfo);
			coContractaffixinfo.setContractid(targetID);
			coContractaffixinfo.setGuid(null);
			coContractaffixinfo.setPapercontractfile(null);
			dao.saveObject(coContractaffixinfo);
		}
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
	
}
