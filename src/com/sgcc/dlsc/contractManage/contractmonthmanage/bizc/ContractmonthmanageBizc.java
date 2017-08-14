package com.sgcc.dlsc.contractManage.contractmonthmanage.bizc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sgcc.dlsc.commons.util.DateUtil;
import com.sgcc.dlsc.contractManage.contractmonthmanage.vo.CapacityBean;
import com.sgcc.dlsc.contractManage.contractmonthmanage.vo.CoContractmonthqtyTransfer;
import com.sgcc.dlsc.contractManage.contractmonthmanage.vo.CoContractmonthqtyVO;
import com.sgcc.dlsc.contractManage.contractmonthmanage.vo.ResolveResultVO;
import com.sgcc.dlsc.contractManage.resolveelectricquantitytounit.vo.CoBusiGeneratorQtyVO;
import com.sgcc.dlsc.contractManage.resolveelectricquantitytounit.vo.CoContractenergyinfoVO;
import com.sgcc.dlsc.contractManage.resolveelectricquantitytounit.vo.ContractUnitBean;
import com.sgcc.dlsc.entity.po.CoBusigeneratorqty;
import com.sgcc.dlsc.entity.po.CoContractmonthqty;
import com.sgcc.isc.service.adapter.utils.JsonUtil;
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
 * @author Administrator 
 * 
 */
public class ContractmonthmanageBizc implements IContractmonthmanageBizc{

	@Autowired
	private IHibernateDao hibernateDao;

	private final static Log resolveLog = LogFactory.getLog(ContractmonthmanageBizc.class);

	String mktecounitSQL = "select econ.ecounitid, econ.ecounitname, econ.ecounitcapacity " +
			"from ba_mktaadminload mkt, ba_marketparticipant market, ba_economicunit econ " +
			"where market.participantid = ? and mkt.participant_id = market.participantid and mkt.load_id = econ.generatorid ";
	
	//通过合同数据中关联市场成员，查找经济机组和物理机组
	String ecounitSQL = "select econ.ecounitid, econ.ecounitname, econ.ecounitcapacity, generator.generatorname, generator.generatorid " +
			" from (select * from ba_marketparticipant market where market.participantid = ?) market, "+
			" ba_generator         generator, " +
			" ba_economicunit      econ " +
			" where econ.participantid = market.participantid " +
			" and generator.generatorid(+) = econ.generatorid ";
	
	
	/**
	 * 分解电量到经济机组
	 */
	private int resolveEnergyToUnit(CoContractmonthqty coContractmonthqty, Date startDate){
		String contractid = coContractmonthqty.getContractid();
		String [] participantParam = participantInContract(contractid);
		String purchaserId =  participantParam[0] ;
		String purchaserType = participantParam[2];
		String sellerId = participantParam[1];
		String sellerType = participantParam[3];//售电方市场成员类型
		
		CapacityBean purchaserBean = getPurchaserUnit(contractid, purchaserId, purchaserType);
		CapacityBean sellerBean = getSellerUnit(contractid, sellerId, sellerType);
		
		return calculateCapacity(purchaserBean, sellerBean, coContractmonthqty, startDate);
	}
	
	/**
	 * 查询数据库中是否已存在数据，如果存在则保存guid。
	 * @param genQty
	 * @deprecated
	 */
	private void getQTYPO(CoBusigeneratorqty genQty){
		StringBuffer qytSQL = new StringBuffer();
		qytSQL.append("select CONTRACTID,PURCHASEUNIT, SELLERUNIT, NETQTY, GENQTY, TIMENO, STARTDATE, ENDDATE, PERIOD, MARKETID, GUID ");
		qytSQL.append("from co_busigeneratorqty ");
		qytSQL.append("where CONTRACTID = ? ");
		qytSQL.append("and PURCHASEUNIT= ? ");
		qytSQL.append("and SELLERUNIT= ? ");
		qytSQL.append("and STARTDATE= ? ");
		qytSQL.append("and PERIOD = ? ");
		qytSQL.append("and TIMENO = ? ");
		
		Object [] obj = {genQty.getContractid(),genQty.getPurchaseunit(),genQty.getSellerunit(),genQty.getStartdate(),genQty.getPeriod(),genQty.getTimeno()};
		List result = hibernateDao.executeSqlQuery(qytSQL.toString(), obj);
		if(result != null && result.size() != 0){
			Object [] obj1 = (Object [])result.get(0);
			genQty.setGuid(obj1[0].toString());
		}
	}
	
	private CoBusigeneratorqty getNewPo(String pruchaserUnitId,String sellerUnitId,String marketid,String contractid,
			double purCapacity, double purchaseCapacityTotal, double sellCapacity, double sellCapacityTotal, double qty, 
			int year, BigDecimal timeno, BigDecimal monthqtytype){
		CoBusigeneratorqty entity = new CoBusigeneratorqty();
		setCommonProperties(entity, pruchaserUnitId, sellerUnitId, marketid, contractid);
		Double qty1Capatity = formula(purCapacity, purchaseCapacityTotal, sellCapacity, sellCapacityTotal, qty);
		entity.setGenqty(new BigDecimal(qty1Capatity));
		entity.setNetqty(new BigDecimal(qty1Capatity));
		entity.setTimeno(timeno);
		Date date = new Date(year,timeno.intValue(),01);
		entity.setStartdate(getMonthFirstDay(date));
		entity.setEnddate(getMonthLastDay(date));
		entity.setMonthqtytype(monthqtytype);
		
//		getQTYPO(entity);//查询数据是否已存在
		return entity;
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
	 * @param purchaserBean
	 * @param sellerBean
	 * @param coContractmonthqty
	 * 
	 */
	private int calculateCapacity(CapacityBean purchaserBean, CapacityBean sellerBean, CoContractmonthqty coContractmonthqty, Date startDate){
		ArrayList<CoBusiGeneratorQtyVO> purchaserList = (ArrayList<CoBusiGeneratorQtyVO>) purchaserBean.getUnitList();//购电方经济机组
		double purchaseCapacityTotal = purchaserBean.getCapacity();//购电方经济机组权益容量总和
		
		ArrayList<CoBusiGeneratorQtyVO> sellerList = (ArrayList<CoBusiGeneratorQtyVO>) sellerBean.getUnitList();//售点方经济机组
		double sellCapacityTotal = sellerBean.getCapacity();//售点方经济机组权益容量总和
		
		String marketid = coContractmonthqty.getMarketid();//场景id
		String contractid = coContractmonthqty.getContractid();
		
		ArrayList<CoBusigeneratorqty> saveEntity = new ArrayList<CoBusigeneratorqty>();
		
		Iterator<CoBusiGeneratorQtyVO> purIterator = purchaserList.iterator();
		
		//每个月电量值
		BigDecimal qty1 = coContractmonthqty.getJanqty();
		BigDecimal qty2 = coContractmonthqty.getFebqty();
		BigDecimal qty3 = coContractmonthqty.getMarqty();
		BigDecimal qty4 = coContractmonthqty.getAprilqty();
		BigDecimal qty5 = coContractmonthqty.getMayqty();
		BigDecimal qty6 = coContractmonthqty.getJunqty();
		BigDecimal qty7 = coContractmonthqty.getJulyqty();
		BigDecimal qty8 = coContractmonthqty.getAugqty();
		BigDecimal qty9 = coContractmonthqty.getSepqty();
		BigDecimal qty10 = coContractmonthqty.getOctqty();
		BigDecimal qty11 = coContractmonthqty.getNovqty();
		BigDecimal qty12 = coContractmonthqty.getDecqty();
		
		BigDecimal monthType = coContractmonthqty.getMonthqtytype();
		
		while(purIterator.hasNext()){
			CoBusiGeneratorQtyVO purqtyvo = purIterator.next();
			double purCapacity = string2Double(purqtyvo.getEcounitcapacity());
			
			Iterator<CoBusiGeneratorQtyVO> sellIterator = sellerList.iterator();
			while(sellIterator.hasNext()){
				CoBusiGeneratorQtyVO sellqtyvo = sellIterator.next();
				double sellCapacity = string2Double(sellqtyvo.getEcounitcapacity());
				
				if(qty1 != null){//一月份电量
					CoBusigeneratorqty entity1 = getNewPo(purqtyvo.getEcounitid(), sellqtyvo.getEcounitid(), marketid, contractid,
							purCapacity, purchaseCapacityTotal, sellCapacity, sellCapacityTotal, qty1.doubleValue(),
							startDate.getYear(),new BigDecimal("1"), monthType);
					
					saveEntity.add(entity1);
				}
				
				if(qty2 != null){//二月份电量
					CoBusigeneratorqty entity2 = getNewPo(purqtyvo.getEcounitid(), sellqtyvo.getEcounitid(), marketid, contractid,
							purCapacity, purchaseCapacityTotal, sellCapacity, sellCapacityTotal, qty2.doubleValue(),
							startDate.getYear(),new BigDecimal("2"), monthType);
					
					saveEntity.add(entity2);
				}
				
				if(qty3 != null){//三月份电量
					CoBusigeneratorqty entity3 = getNewPo(purqtyvo.getEcounitid(), sellqtyvo.getEcounitid(), marketid, contractid,
							purCapacity, purchaseCapacityTotal, sellCapacity, sellCapacityTotal, qty3.doubleValue(),
							startDate.getYear(),new BigDecimal("3"), monthType);
					saveEntity.add(entity3);
				}
				
				if(qty4 != null){//四月份电量
					CoBusigeneratorqty entity4 = getNewPo(purqtyvo.getEcounitid(), sellqtyvo.getEcounitid(), marketid, contractid,
							purCapacity, purchaseCapacityTotal, sellCapacity, sellCapacityTotal, qty4.doubleValue(),
							startDate.getYear(),new BigDecimal("4"), monthType);
					saveEntity.add(entity4);
				}
				
				if(qty5 != null){//五月份电量
					CoBusigeneratorqty entity5 = getNewPo(purqtyvo.getEcounitid(), sellqtyvo.getEcounitid(), marketid, contractid,
							purCapacity, purchaseCapacityTotal, sellCapacity, sellCapacityTotal, qty5.doubleValue(),
							startDate.getYear(),new BigDecimal("5"), monthType);
					saveEntity.add(entity5);
				}
				
				if(qty6 != null){//六月份电量
					CoBusigeneratorqty entity6 = getNewPo(purqtyvo.getEcounitid(), sellqtyvo.getEcounitid(), marketid, contractid,
							purCapacity, purchaseCapacityTotal, sellCapacity, sellCapacityTotal, qty6.doubleValue(),
							startDate.getYear(),new BigDecimal("6"), monthType);
					saveEntity.add(entity6);
				}
				
				if(qty7 != null){//七月份电量
					CoBusigeneratorqty entity7 = getNewPo(purqtyvo.getEcounitid(), sellqtyvo.getEcounitid(), marketid, contractid,
							purCapacity, purchaseCapacityTotal, sellCapacity, sellCapacityTotal, qty7.doubleValue(),
							startDate.getYear(),new BigDecimal("7"), monthType);
					saveEntity.add(entity7);
				}
				
				if(qty8 != null){//八月份电量
					CoBusigeneratorqty entity8 = getNewPo(purqtyvo.getEcounitid(), sellqtyvo.getEcounitid(), marketid, contractid,
							purCapacity, purchaseCapacityTotal, sellCapacity, sellCapacityTotal, qty8.doubleValue(),
							startDate.getYear(),new BigDecimal("8"), monthType);
					saveEntity.add(entity8);
				}
				
				if(qty9 != null){//九月份电量
					CoBusigeneratorqty entity9 = getNewPo(purqtyvo.getEcounitid(), sellqtyvo.getEcounitid(), marketid, contractid,
							purCapacity, purchaseCapacityTotal, sellCapacity, sellCapacityTotal, qty9.doubleValue(),
							startDate.getYear(),new BigDecimal("9"), monthType);
					saveEntity.add(entity9);
				}
				
				if(qty10 != null){//十月份电量
					CoBusigeneratorqty entity10 = getNewPo(purqtyvo.getEcounitid(), sellqtyvo.getEcounitid(), marketid, contractid,
							purCapacity, purchaseCapacityTotal, sellCapacity, sellCapacityTotal, qty10.doubleValue(),
							startDate.getYear(),new BigDecimal("10"), monthType);
					saveEntity.add(entity10);
				}
				
				if(qty11 != null){//十一月份电量
					CoBusigeneratorqty entity11 = getNewPo(purqtyvo.getEcounitid(), sellqtyvo.getEcounitid(), marketid, contractid,
							purCapacity, purchaseCapacityTotal, sellCapacity, sellCapacityTotal, qty11.doubleValue(),
							startDate.getYear(),new BigDecimal("11"), monthType);
					saveEntity.add(entity11);
				}
				
				if(qty12 != null){//十二月份电量
					CoBusigeneratorqty entity12 = getNewPo(purqtyvo.getEcounitid(), sellqtyvo.getEcounitid(), marketid, contractid,
							purCapacity, purchaseCapacityTotal, sellCapacity, sellCapacityTotal, qty12.doubleValue(),
							startDate.getYear(),new BigDecimal("12"), monthType);
					saveEntity.add(entity12);
				}
			}
		}
		String deleteSQL = "delete from co_busigeneratorqty where contractid = ? and monthqtytype = ?";
		hibernateDao.executeSqlUpdate(deleteSQL, new Object[]{contractid, monthType});
		hibernateDao.saveOrUpdateAllObject(saveEntity);
		return saveEntity.size();
	}
	
	private static void setCommonProperties(CoBusigeneratorqty entity, String purchaserid, 
			String sellerid, String marketid, String contractid){
		entity.setPurchaseunit(purchaserid);
		entity.setSellerunit(sellerid);
		entity.setMarketid(marketid);
		entity.setPeriod(new BigDecimal("1"));
		entity.setContractid(contractid);
	}
	
	/**
	 * 计算公式方法
	 * A1/A  * B1/B * 总电量
	 * @param 购方一个经济机组权益容量，没有购方经济机组参数为1d
	 * @param 购方经济机组权益容量之和，没有购方经济机组参数为1d
	 * @param 售方一个经济机组权益容量
	 * @param 售方经济机组权益容量之和
	 * @param 总电量
	 * @return
	 */
	private Double formula(Double pNumerator, Double pDenominator,Double sNumerator, Double sDenominator, Double total){
		if(total == null){
			throw new RuntimeException("电量值为空");
		}
		if(pDenominator != 0 && sDenominator!=0){//权益总量之和作为分母，不能为0
			Double power = (pNumerator/pDenominator) * (sNumerator/sDenominator) * total;//计算后电量值
			return getRound(power);
		} else {
			throw new RuntimeException("权益容量总和为空");
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
	
	/**
	 * 查询售方经济机组
	 * @param coContractmonthqty
	 */
	private CapacityBean getSellerUnit(String contractid, String sellerId, String sellerType){
		if(contractid == null){
			return null;
		}
		StringBuffer sql = new StringBuffer();
		sql.append("select acc.contractid,acc.contractrole,acc.participantid,econ.ecounitid,econ.ecounitname,econ.ecounitcapacity");
		sql.append(" from co_contractaccessory acc,ba_economicunit econ ");
		sql.append(" where contractid = ? and acc.participantid = econ.generatorid and contractrole = '2'");//经济机组与物理机组一对一，所以用acc.participantid = econ.generatorid
		Object [] param = {contractid};
		List result = hibernateDao.executeSqlQuery(sql.toString(), param);
		if(result == null || result.size() == 0){
			resolveLog.info("合同分月管理：co_contractaccessory表中没有售电方机组");
			CapacityBean sellerBean = getSellerUnitWithParticipantid(contractid, sellerId, sellerType);
			return sellerBean;
		} else {
			ArrayList<CoBusiGeneratorQtyVO> sellerList = new ArrayList<CoBusiGeneratorQtyVO>();
			double sellerCapacityTotal = 0;//售方权益容量总和
			
			for(int i = 0; i < result.size(); i++){
				Object [] obj = (Object [])result.get(i);
//				String contractRole = validateString(obj[1]);//经济机组角色（购方经济机组，售房经济机组）
				
				CoBusiGeneratorQtyVO qtyVO = new CoBusiGeneratorQtyVO();
				qtyVO.setContractid(validateString(obj[0]));
				qtyVO.setContractrole(validateBigDecimal(obj[1]));
				qtyVO.setParticipantid(validateString(obj[2]));
				qtyVO.setEcounitid(validateString(obj[3]));
				qtyVO.setSellerunitid(validateString(obj[3]));
				if(participantType(sellerType)){//判断用户类型是否是大用户或电网企业
					qtyVO.setEcounitcapacity("1");
					sellerCapacityTotal = 1;
				} else {
					qtyVO.setEcounitcapacity(validateString(obj[5]));
					sellerCapacityTotal += string2Double(validateString(obj[5]));
				}
				sellerList.add(qtyVO);
			}
			CapacityBean sellerBean = new CapacityBean();
			sellerBean.setUnitList(sellerList);
			sellerBean.setCapacity(sellerCapacityTotal);
			return sellerBean;
		}
	}
	
	/**
	 * 查询购方经济机组
	 * @param coContractmonthqty
	 * @return 经济机组列表，经济机组权益容量总和
	 */
	private CapacityBean getPurchaserUnit(String contractid, String purchaserId, String purchaserType){
		if(contractid == null){
			return null;
		}
		StringBuffer sql = new StringBuffer();
		sql.append("select acc.contractid,acc.contractrole,acc.participantid,econ.ecounitid,econ.ecounitname,econ.ecounitcapacity");
		sql.append(" from co_contractaccessory acc,ba_economicunit econ ");
		sql.append(" where contractid = ? and acc.participantid = econ.generatorid and contractrole = '1'");//经济机组与物理机组一对一，所以用acc.participantid = econ.generatorid
		Object [] param = {contractid};
		List result = hibernateDao.executeSqlQuery(sql.toString(), param);
		if(result == null || result.size() == 0){
			resolveLog.info("合同分月管理：co_contractaccessory表中没有购电方机组");
			CapacityBean purchaserBean =  getPurchaserUnitWithParticipantid(contractid, isBigUserType(contractid), purchaserId, purchaserType);
			return purchaserBean;
		} else {
			ArrayList<CoBusiGeneratorQtyVO> purchaserList = new ArrayList<CoBusiGeneratorQtyVO>();
			double purchaserCapacityTotal = 0;//购方权益容量总和
			
			for(int i = 0; i < result.size(); i++){
				Object [] obj = (Object [])result.get(i);
				String contractRole = validateString(obj[1]);//经济机组角色（购方经济机组，售房经济机组）
				
				CoBusiGeneratorQtyVO qtyVO = new CoBusiGeneratorQtyVO();
				qtyVO.setContractid(validateString(obj[0]));
				qtyVO.setContractrole(validateBigDecimal(obj[1]));
				qtyVO.setParticipantid(validateString(obj[2]));
				qtyVO.setEcounitid(validateString(obj[3]));
				qtyVO.setPurchaseunitid(validateString(obj[3]));
				
				if("1".equals(contractRole)){//购方经济机组
					if(participantType(purchaserType)){//判断用户类型是否是大用户或电网企业
						qtyVO.setEcounitcapacity("1");
						purchaserCapacityTotal = 1;
					} else {
						qtyVO.setEcounitcapacity(validateString(obj[5]));
						purchaserCapacityTotal += string2Double(validateString(obj[5]));
					}
					purchaserList.add(qtyVO);
				} 
			}
			CapacityBean purchaserBean = new CapacityBean();
			purchaserBean.setUnitList(purchaserList);
			purchaserBean.setCapacity(purchaserCapacityTotal);
			return purchaserBean;
		}
	}
	
	private CapacityBean getSellerUnitWithParticipantid(String contractid, String sellerId, String sellerType) {
		
		List sellerUnitList = hibernateDao.executeSqlQuery(ecounitSQL, new Object[]{sellerId});//售方经济机组
		ArrayList<CoBusiGeneratorQtyVO> sellerList = new ArrayList<CoBusiGeneratorQtyVO>();
		
		double sellerCapacityTotal = 0;//售方权益容量总和
		
		if(sellerUnitList != null && sellerUnitList.size() != 0){
			for(int i = 0; i <sellerUnitList.size(); i++){
				Object [] obj = (Object [])sellerUnitList.get(i);
				
				CoBusiGeneratorQtyVO qtyVO = new CoBusiGeneratorQtyVO();
				qtyVO.setContractid(contractid);
				qtyVO.setContractrole(new BigDecimal("2"));
				qtyVO.setParticipantid(sellerId);
				qtyVO.setEcounitid(validateString(obj[0]));
				qtyVO.setSellerunit(validateString(obj[1]));
				
				if(participantType(sellerType)){//判断用户类型是否是大用户或电网企业
					qtyVO.setEcounitcapacity("1");
					sellerCapacityTotal = 1;
				} else {
					qtyVO.setEcounitcapacity(validateString(obj[2]));
					sellerCapacityTotal += string2Double(validateString(obj[2]));
				}
				sellerList.add(qtyVO);
			}
			CapacityBean bean = new CapacityBean();
			bean.setUnitList(sellerList);
			bean.setCapacity(sellerCapacityTotal);
			return bean;
		} else {
			resolveLog.info("没有查到售方经济机组");
			throw new RuntimeException("没有查到售方经济机组");
		}
	}


	private CapacityBean getPurchaserUnitWithParticipantid(String contractid, boolean isBigUserType, String purchaserId, String purchaserType) {
		
		List purchaserUnitList = hibernateDao.executeSqlQuery(ecounitSQL, new Object[]{purchaserId});//购方经济机组
		ArrayList<CoBusiGeneratorQtyVO> purchaserList = new ArrayList<CoBusiGeneratorQtyVO>();
		
		double purchaserCapacityTotal = 0;//购方权益容量总和
		
		if(purchaserUnitList != null && purchaserUnitList.size() != 0){
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
					purchaserCapacityTotal = 1;
				} else {
					qtyVO.setEcounitcapacity(validateString(obj[2]));
					purchaserCapacityTotal += string2Double(validateString(obj[2]));
				}
				purchaserList.add(qtyVO);
			}
		} else {
			if(isBigUserType){
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
							purchaserCapacityTotal = 1;
						} else {
							qtyVO.setEcounitcapacity(validateString(obj[2]));
							purchaserCapacityTotal += string2Double(validateString(obj[2]));
						}
						purchaserList.add(qtyVO);
					}
				}
			} else {
				resolveLog.info("没有查到购方经济机组");
				throw new RuntimeException("没有查到购方经济机组");
			}
		}
		CapacityBean bean = new CapacityBean();
		bean.setUnitList(purchaserList);
		bean.setCapacity(purchaserCapacityTotal);
		return bean;
	}
	
	/**
	 * 判断合同类型是否是大用户合同或者其子类型合同
	 * @param contractid
	 * @return
	 */
	private boolean isBigUserType(String contractid){
		
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
	
	/**
	 * 判断用户类型是否是大用户或电网企业
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
	 * 查询合同购电方id、售电方id、购电方市场成员类型、售电方市场成员类型
	 * @param contractid
	 * @return
	 */
	private String [] participantInContract(String contractid){
		String [] param = new String[4];
		
		//通过合同找到购电方和售电方的市场成员id
		StringBuffer purchaserAndSellerSQl = new StringBuffer();
		purchaserAndSellerSQl.append("select base.purchaser, base.seller, participant1.participanttype purchasertype, participant2.participanttype sellertype ");
		purchaserAndSellerSQl.append(" from co_contractbaseinfo base, ba_marketparticipant participant1, ba_marketparticipant participant2 ");
		purchaserAndSellerSQl.append(" where participant1.participantid = base.purchaser ");
		purchaserAndSellerSQl.append(" and participant2.participantid = base.seller" );
		purchaserAndSellerSQl.append(" and contractid = ? ");
		List purchaserAndSeller = hibernateDao.executeSqlQuery(purchaserAndSellerSQl.toString(), new Object[]{contractid});
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

	private String validateString(Object obj){
		return obj==null?"":obj.toString();
	}
	
	private BigDecimal validateBigDecimal(Object obj){
		return obj==null?null:new BigDecimal(obj.toString());
	}
	
	/**
	 * 
	 * @description  判断选择合同是否已经在分月管理中分解过
	 * @param 
	 * @author 
	 * @date 
	 */
	public boolean getMonthDataFlag(String contractids){
		
		boolean flag =  false;
		StringBuffer sql = new StringBuffer();
		List listparm = new ArrayList();
		sql.append("select t.guid from co_contractmonthqty t where t.contractid in ( ");
		String[] contractidss = contractids.split(",");
		if(contractidss.length ==0){
			sql.append("?");
			listparm.add(contractids);
		}
		for(int i=0;i<contractidss.length;i++){
			sql.append("?,");
			listparm.add(contractidss[i]);
		}
		List list = hibernateDao.executeSqlQuery(sql.substring(0, sql.length()-1)+")" ,listparm.toArray());
		if (list != null && list.size() > 0) {
			flag = true;
		}
		return flag;
		
	}
	
	/**
	 * 
	 * @description 合同分月管理保存
	 * @param 
	 * @author 
	 * @date 
	 */
	public void saveManage(String contractid,String energyType,String energys,String marketId,String userId){
		
		if(!energys.toString().replace(",", "").equals("")){
			energys += ", zb";
			String[] energy = energys.split(",");
			CoContractmonthqty coContractmonthqty = new CoContractmonthqty();
			coContractmonthqty.setContractid(contractid);
			String guid = getGuid(contractid,energyType);
			coContractmonthqty.setGuid(guid.equals("")?null:guid);
			coContractmonthqty.setJanqty(energy[0].toString().equals("")?null:new BigDecimal(energy[0].toString()));
			coContractmonthqty.setFebqty(energy[1].toString().equals("")?null:new BigDecimal(energy[1].toString()));
			coContractmonthqty.setMarqty(energy[2].toString().equals("")?null:new BigDecimal(energy[2].toString()));
			coContractmonthqty.setAprilqty(energy[3].toString().equals("")?null:new BigDecimal(energy[3].toString()));
			coContractmonthqty.setMayqty(energy[4].toString().equals("")?null:new BigDecimal(energy[4].toString()));
			coContractmonthqty.setJunqty(energy[5].toString().equals("")?null:new BigDecimal(energy[5].toString()));
			coContractmonthqty.setJulyqty(energy[6].toString().equals("")?null:new BigDecimal(energy[6].toString()));
			coContractmonthqty.setAugqty(energy[7].toString().equals("")?null:new BigDecimal(energy[7].toString()));
			coContractmonthqty.setSepqty(energy[8].toString().equals("")?null:new BigDecimal(energy[8].toString()));
			coContractmonthqty.setOctqty(energy[9].toString().equals("")?null:new BigDecimal(energy[9].toString()));
			coContractmonthqty.setNovqty(energy[10].toString().equals("")?null:new BigDecimal(energy[10].toString()));
			coContractmonthqty.setDecqty(energy[11].toString().equals("")?null:new BigDecimal(energy[11].toString()));
			coContractmonthqty.setMarketid(marketId);
			if("".equals(guid)){
				coContractmonthqty.setAddpersonid(userId);
				coContractmonthqty.setAdddate(new java.sql.Date(new Date().getTime()));
			}else{
				coContractmonthqty.setUpdatepersonid(userId);
				coContractmonthqty.setUpdatedate(new java.sql.Date(new Date().getTime()));
			}
			coContractmonthqty.setMonthqtytype(new BigDecimal(energyType));
			hibernateDao.saveOrUpdateObject(coContractmonthqty);
		}
	}
	
	
	/**
	 * 
	 * @description 合同分月管理分解
	 * @param 
	 * @author 
	 * @date 
	 */
	public void energyManageFJ(String contractids,String month,String marketId,String userId){
		
		String[] contractidss = contractids.split(",");
		String contractid = "";
		String contractType = "";
		List<CoContractenergyinfoVO> listVO = new ArrayList<CoContractenergyinfoVO>();
		Object [] obj = null;
		String sql = "select to_char(b.contractstartdate,'mm'),to_char(b.contractenddate,'mm'),to_char(t.startdate,'yyyy-mm-dd'),to_char(t.enddate,'yyyy-mm-dd'),nvl(t.energy,''),nvl(t.sellergen,''),nvl(t.sellerenergy,''),nvl(t.purchasergen,''),nvl(t.purchaserenergy,''),nvl(b.contractqty,''),nvl(b.sellergen,''),nvl(b.sellerenergy,''),nvl(b.purchasergen,''),nvl(b.purchaserenergy,'') from co_contractenergyinfo t,co_contractbaseinfo b where t.contractid = b.contractid and  t.contractid = ? and t.whereinsert='1' and t.period='1' and t.qtytype = '1' ";
		for(int i=0;i<contractidss.length;i++){
			contractid = contractidss[i];
			contractType = checkCoTypeTwo(contractid);
			List list = hibernateDao.executeSqlQuery(sql ,new Object[]{contractid});
			int smoth = 0;
			int emoth = 0;
			BigDecimal energynum = new BigDecimal(0);
			BigDecimal sellergennum = new BigDecimal(0);
			BigDecimal sellerenergynum = new BigDecimal(0);
			BigDecimal purchasergennum = new BigDecimal(0);
			BigDecimal purchaserenergynum = new BigDecimal(0);
			if (list != null && list.size() > 0) {
				for(int j=0;j<list.size();j++){
					obj = (Object[]) list.get(j);
					CoContractenergyinfoVO coContractenergyinfoVO =  new CoContractenergyinfoVO();
					smoth =  obj[0] == null ? 0 :Integer.parseInt(obj[0].toString());
					emoth =  obj[1] == null ? 0 :Integer.parseInt(obj[1].toString());
					coContractenergyinfoVO.setStartdate(DateUtil.getUtilDate(obj[2] == null ? "" : obj[2].toString(), "yyyy-MM-dd"));
					coContractenergyinfoVO.setEnddate(DateUtil.getUtilDate(obj[3] == null ? "" : obj[3].toString(), "yyyy-MM-dd"));
					coContractenergyinfoVO.setEnergy(obj[4] == null ? null :new BigDecimal(obj[4].toString()));
					coContractenergyinfoVO.setSellergen(obj[5] == null ? null :new BigDecimal(obj[5].toString()));
					coContractenergyinfoVO.setSellerenergy(obj[6] == null ? null :new BigDecimal(obj[6].toString()));
					coContractenergyinfoVO.setPurchasergen(obj[7] == null ? null :new BigDecimal(obj[7].toString()));
					coContractenergyinfoVO.setPurchaserenergy(obj[8] == null ? null :new BigDecimal(obj[8].toString()));
					energynum = obj[9] == null ? new BigDecimal(0) :new BigDecimal(obj[9].toString());
					sellergennum = obj[10] == null ? new BigDecimal(0) :new BigDecimal(obj[10].toString());
					sellerenergynum = obj[11] == null ? new BigDecimal(0) :new BigDecimal(obj[11].toString());
					purchasergennum = obj[12] == null ? new BigDecimal(0) :new BigDecimal(obj[12].toString());
					purchaserenergynum = obj[13] == null ? new BigDecimal(0) :new BigDecimal(obj[13].toString());
					listVO.add(coContractenergyinfoVO);
				}
			}
			
			if (listVO != null && listVO.size() > 0) {
				CoContractmonthqty coContractmonthqty = new CoContractmonthqty();
				coContractmonthqty.setContractid(contractid);
				if(contractType.equals("0")){
					String guid = getGuid(contractid,"1");
					coContractmonthqty.setGuid(guid.equals("")?null:guid);
					if(energynum != null && !"".equals(energynum)){
						List listMonth = getMonthEng(emoth,smoth,listVO,"energy",energynum,month,contractid,"1");
						coContractmonthqty.setJanqty(listMonth.get(0).toString().equals("")?null:new BigDecimal(listMonth.get(0).toString()));
						coContractmonthqty.setFebqty(listMonth.get(1).toString().equals("")?null:new BigDecimal(listMonth.get(1).toString()));
						coContractmonthqty.setMarqty(listMonth.get(2).toString().equals("")?null:new BigDecimal(listMonth.get(2).toString()));
						coContractmonthqty.setAprilqty(listMonth.get(3).toString().equals("")?null:new BigDecimal(listMonth.get(3).toString()));
						coContractmonthqty.setMayqty(listMonth.get(4).toString().equals("")?null:new BigDecimal(listMonth.get(4).toString()));
						coContractmonthqty.setJunqty(listMonth.get(5).toString().equals("")?null:new BigDecimal(listMonth.get(5).toString()));
						coContractmonthqty.setJulyqty(listMonth.get(6).toString().equals("")?null:new BigDecimal(listMonth.get(6).toString()));
						coContractmonthqty.setAugqty(listMonth.get(7).toString().equals("")?null:new BigDecimal(listMonth.get(7).toString()));
						coContractmonthqty.setSepqty(listMonth.get(8).toString().equals("")?null:new BigDecimal(listMonth.get(8).toString()));
						coContractmonthqty.setOctqty(listMonth.get(9).toString().equals("")?null:new BigDecimal(listMonth.get(9).toString()));
						coContractmonthqty.setNovqty(listMonth.get(10).toString().equals("")?null:new BigDecimal(listMonth.get(10).toString()));
						coContractmonthqty.setDecqty(listMonth.get(11).toString().equals("")?null:new BigDecimal(listMonth.get(11).toString()));
						coContractmonthqty.setMarketid(marketId);
						if("".equals(guid)){
							coContractmonthqty.setAddpersonid(userId);
							coContractmonthqty.setAdddate(new java.sql.Date(new Date().getTime()));
						}else{
							coContractmonthqty.setUpdatepersonid(userId);
							coContractmonthqty.setUpdatedate(new java.sql.Date(new Date().getTime()));
						}
						coContractmonthqty.setMonthqtytype(new BigDecimal("1"));
						hibernateDao.saveOrUpdateObject(coContractmonthqty);
						
//						try {
							CoContractenergyinfoVO energyInfoVo = listVO.get(0);
							resolveEnergyToUnit(coContractmonthqty, energyInfoVo.getStartdate());//分解电量到经济机组
//						} catch (Exception e) {
//							e.printStackTrace();
//							throw new RuntimeException("分解电量到经济机组失败");
//						}
					}
				}else if(contractType.equals("1")){
					
					String[] engTpyes =  {"energy","sellergen","sellerenergy","purchasergen","purchaserenergy"};
					BigDecimal[] engs =  {energynum,sellergennum,sellerenergynum,purchasergennum,purchaserenergynum};
					for(int j=1;j<=engTpyes.length;j++){
						String guid = getGuid(contractid,""+j);
						coContractmonthqty.setGuid(guid.equals("")?null:guid);
						if(engs[j-1] != null && !"".equals(engs[j-1])){
							List listMonth = getMonthEng(emoth,smoth,listVO,engTpyes[j-1],engs[j-1],month,contractid,j+"");
							coContractmonthqty.setJanqty(listMonth.get(0).toString().equals("")?null:new BigDecimal(listMonth.get(0).toString()));
							coContractmonthqty.setFebqty(listMonth.get(1).toString().equals("")?null:new BigDecimal(listMonth.get(1).toString()));
							coContractmonthqty.setMarqty(listMonth.get(2).toString().equals("")?null:new BigDecimal(listMonth.get(2).toString()));
							coContractmonthqty.setAprilqty(listMonth.get(3).toString().equals("")?null:new BigDecimal(listMonth.get(3).toString()));
							coContractmonthqty.setMayqty(listMonth.get(4).toString().equals("")?null:new BigDecimal(listMonth.get(4).toString()));
							coContractmonthqty.setJunqty(listMonth.get(5).toString().equals("")?null:new BigDecimal(listMonth.get(5).toString()));
							coContractmonthqty.setJulyqty(listMonth.get(6).toString().equals("")?null:new BigDecimal(listMonth.get(6).toString()));
							coContractmonthqty.setAugqty(listMonth.get(7).toString().equals("")?null:new BigDecimal(listMonth.get(7).toString()));
							coContractmonthqty.setSepqty(listMonth.get(8).toString().equals("")?null:new BigDecimal(listMonth.get(8).toString()));
							coContractmonthqty.setOctqty(listMonth.get(9).toString().equals("")?null:new BigDecimal(listMonth.get(9).toString()));
							coContractmonthqty.setNovqty(listMonth.get(10).toString().equals("")?null:new BigDecimal(listMonth.get(10).toString()));
							coContractmonthqty.setDecqty(listMonth.get(11).toString().equals("")?null:new BigDecimal(listMonth.get(11).toString()));
							coContractmonthqty.setMarketid(marketId);
							if("".equals(guid)){
								coContractmonthqty.setAddpersonid(userId);
								coContractmonthqty.setAdddate(new java.sql.Date(new Date().getTime()));
							}else{
								coContractmonthqty.setUpdatepersonid(userId);
								coContractmonthqty.setUpdatedate(new java.sql.Date(new Date().getTime()));
							}
							coContractmonthqty.setMonthqtytype(new BigDecimal(j));
							hibernateDao.saveOrUpdateObject(coContractmonthqty);
//							try {
								CoContractenergyinfoVO energyInfoVo = listVO.get(0);
								resolveEnergyToUnit(coContractmonthqty, energyInfoVo.getStartdate());//分解电量到经济机组
//							} catch (Exception e) {
//								e.printStackTrace();
//								throw new RuntimeException("分解电量到经济机组失败");
//							}
						}
					}
					
				}
				
				
			}
			
		}
	}
	
	/**
	 * 获取分月管理表主键 （判断是否已经分解，若分解返回主键）
	 */
	public String getGuid(String contractid,String monthqtytype){
		String guid ="";
		String sql = "select t.guid from co_contractmonthqty t where t.contractid = ? and t.monthqtytype = ? ";
		List list = hibernateDao.executeSqlQuery(sql ,new Object[]{contractid,monthqtytype});
		if (list != null && list.size() > 0) {
			guid = list.get(0).toString();
		}
		return guid;
	}
	
	/**
	 * 判断此合同是否在合同分月管理中分解过
	 * @param contractid
	 * @return
	 */
	public List getContractmonthqty(String contractid,String monthqtytype) {
		List list = new ArrayList();
		String sql="select t.guid,t.janqty,t.febqty,t.marqty,t.aprilqty,t.mayqty,t.junqty,t.julyqty,t.augqty,t.sepqty,t.octqty,t.novqty,t.decqty from co_contractmonthqty t where t.contractid = ? and t.monthqtytype = ?";
		List tlist= hibernateDao.executeSqlQuery(sql.toString(), new Object[]{contractid,monthqtytype});
		for(int i=0;i<tlist.size();i++){
			Object[] obj  = (Object[])tlist.get(i);
			list.add(obj[0] == null ? "" : obj[0].toString());
			list.add(obj[1] == null ? "" : obj[1].toString());
			list.add(obj[2] == null ? "" : obj[2].toString());
			list.add(obj[3] == null ? "" : obj[3].toString());
			list.add(obj[4] == null ? "" : obj[4].toString());
			list.add(obj[5] == null ? "" : obj[5].toString());
			list.add(obj[6] == null ? "" : obj[6].toString());
			list.add(obj[7] == null ? "" : obj[7].toString());
			list.add(obj[8] == null ? "" : obj[8].toString());
			list.add(obj[9] == null ? "" : obj[9].toString());
			list.add(obj[10] == null ? "" : obj[10].toString());
			list.add(obj[11] == null ? "" : obj[11].toString());
			list.add(obj[12] == null ? "" : obj[12].toString());
		}
		return list;
	}
	
//	/**
//	 * 返回特定电量类型在每个月分得的电量集合
//	 * @param emoth
//	 * @param smoth
//	 * @param listVO
//	 * @param engType
//	 * @return
//	 */
//	
//	public List getMonthEng(int emoth,int smoth,List<CoContractenergyinfoVO> listVO,String engType){
//		
//		List listMonth = new ArrayList();
//		for(int i=1;i<=12;i++){
//			if(i<=emoth && i>=smoth){
//				listMonth.add(getEng(i,listVO,engType));
//			}else{
//				listMonth.add("");
//			}
//			
//		}
//		return listMonth;
//	}
	
	/**
	 * 返回特定电量类型在每个月分得的电量集合(最后一个月等于总合同电量-前面所以月份的电量总和)
	 * @param emoth
	 * @param smoth
	 * @param listVO
	 * @param engType
	 * @return
	 */
	
	public List getMonthEng(int emoth,int smoth,List<CoContractenergyinfoVO> listVO,String engType,BigDecimal eng,String month,String contractid,String monthqtytype){
		
		List listMonth = new ArrayList();
//		float num = 0;
		BigDecimal num = new BigDecimal(0);
		List listMonthManage = getContractmonthqty(contractid,monthqtytype);
		if(month.equals("")||month==null||emoth<=Integer.parseInt(month)){
			for(int i=1;i<=12;i++){
				if(i<emoth && i>=smoth){
					listMonth.add(getEng(i,listVO,engType));
					num = num.add(getEng(i,listVO,engType));
//					num = num+getEng(i,listVO,engType).floatValue();
				}else if(i==emoth){
					listMonth.add(eng.subtract(num));
				}else{
					listMonth.add("");
				}
				
			}
		}else if(listMonthManage!=null&&listMonthManage.size()>0){
			
			if(Integer.parseInt(month)<smoth){
				BigDecimal energMonth = eng.divide(new BigDecimal(emoth-smoth+1),4, BigDecimal.ROUND_HALF_EVEN);
				for(int i=1;i<=12;i++){
					if(i<emoth && i>=smoth){
						listMonth.add(energMonth);
						num = num.add(energMonth);
					}else if(i==emoth){
						listMonth.add(eng.subtract(num));
					}else{
						listMonth.add("");
					}
					
				}
			}else if(Integer.parseInt(month)<emoth){
				
				for(int i=1;i<Integer.parseInt(month);i++){
					if(i<emoth && i>=smoth){
						listMonth.add(listMonthManage.get(i));
						num = num.add(new BigDecimal(listMonthManage.get(i).toString()));
					}else if(i==emoth){
						listMonth.add(eng.subtract(num));
					}else{
						listMonth.add("");
					}
					
				}
				
				BigDecimal energMonth = eng.subtract(num).divide(new BigDecimal(emoth-Integer.parseInt(month)+1),4, BigDecimal.ROUND_HALF_EVEN);
				for(int i=Integer.parseInt(month);i<=12;i++){
					if(i<emoth && i>=smoth){
						listMonth.add(energMonth);
						num = num.add(energMonth);
					}else if(i==emoth){
						listMonth.add(eng.subtract(num));
					}else{
						listMonth.add("");
					}
					
				}
			}else {
				for(int i=1;i<=12;i++){
					if(i<emoth && i>=smoth){
						listMonth.add(getEng(i,listVO,engType));
						num = num.add(getEng(i,listVO,engType));
					}else if(i==emoth){
						listMonth.add(eng.subtract(num));
					}else{
						listMonth.add("");
					}
					
				}
			}
			
		}else {
			for(int i=1;i<=12;i++){
				if(i<emoth && i>=smoth){
					listMonth.add(getEng(i,listVO,engType));
					num = num.add(getEng(i,listVO,engType));
				}else if(i==emoth){
					listMonth.add(eng.subtract(num));
				}else{
					listMonth.add("");
				}
				
			}
		}
		
		return listMonth;
	}
	
	/**
	 * 算出指定月份的电量
	 * @param month
	 * @param listVO
	 * @return
	 */
	public BigDecimal getEng(int month,List<CoContractenergyinfoVO> listVO,String engType){
		
		
		float energy = 0;
		Integer accuracy = -4; //小数点后精确到4位
		for(int i=0;i<listVO.size();i++){
			float energyTotle = 0;
			if(engType.equals("energy")){
				if(listVO.get(i).getEnergy()==null){
					continue;
				}else{
					energyTotle = energyTotle + listVO.get(i).getEnergy().floatValue();
				}
			}else if(engType.equals("sellerenergy")){
				if(listVO.get(i).getSellerenergy()==null){
					continue;
				}else{
					energyTotle = energyTotle + listVO.get(i).getSellerenergy().floatValue();
				}
			}else if(engType.equals("sellergen")){
				if(listVO.get(i).getSellergen()==null){
					continue;
				}else{
					energyTotle = energyTotle + listVO.get(i).getSellergen().floatValue();
				}
			}else if(engType.equals("purchaserenergy")){
				if(listVO.get(i).getPurchaserenergy()==null){
					continue;
				}else{
					energyTotle = energyTotle + listVO.get(i).getPurchaserenergy().floatValue();
				}
			}else if(engType.equals("purchasergen")){
				if(listVO.get(i).getPurchasergen()==null){
					continue;
				}else{
					energyTotle = energyTotle + listVO.get(i).getPurchasergen().floatValue();
				}
			}
			Date sdate  = listVO.get(i).getStartdate();//合同分段电量开始时间
			Date edate  = listVO.get(i).getEnddate();//合同分段电量结束时间
			int daytotle  = daysOfTwo(sdate,edate)+1;
			int sdatemonth  = Integer.parseInt(DateUtil.getUtilDateString(sdate, "MM")); 
			int edatemonth  = Integer.parseInt(DateUtil.getUtilDateString(edate, "MM")); 
			if(month==sdatemonth && month==edatemonth){
				energy = energyTotle;
			}else if(month==sdatemonth && month<edatemonth){
				Date lastDay = getMonthLastDay(sdate);//获取第i月的最后天
				int day  = daysOfTwo(sdate,lastDay);//获取合同分段开始时间到月底最后一天的天数
				energy = energy+(energyTotle/daytotle)*day;
			}else if(month>sdatemonth && month==edatemonth){
				Date firstDay = getMonthFirstDay(edate);//获取第i月的第一天
				int day  = daysOfTwo(firstDay,edate)+1;//获取到合同分段结束时间的天数
				energy = energy+(energyTotle/daytotle)*day;
			}else if(month>sdatemonth && month<edatemonth){
				Date monthDay = getMonth(sdate,month);//获取第i月的日期
				int day  = getMonthDays(monthDay);//获取当月天数
				energy = energy+(energyTotle/daytotle)*day;
			}
		}
		return new BigDecimal(energy).setScale(-1 * accuracy, 5);
		
	}
	
	
	/**
	 * 传入时间特定月份的日期
	 */
	public Date getMonth(Date date,int month) {
		
		Calendar cal = Calendar.getInstance(); 
		cal.set(date.getYear(),month,date.getDay()); 
		return cal.getTime();
		
	}
	
	/**
	 * 返回传入时间的月的第一天
	 */
	public Date getMonthFirstDay(Date date) {
		
		Calendar cal = Calendar.getInstance(); 
		cal.setTime(date); 
		cal.set(Calendar.DAY_OF_MONTH, 1); 
		return cal.getTime();
		
	}
	
	/**
	 * 返回传入时间的月的最后一天
	 */
	public Date getMonthLastDay(Date date) {
		
		Calendar cal = Calendar.getInstance(); 
		cal.setTime(date); 
		cal.roll(Calendar.DAY_OF_MONTH, -1);  
		return cal.getTime();
		
	}
	
	/**
	 * 获得传入时间月的天数
	 */
	public int getMonthDays(Date date) {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		
	}
	
	
	/**
	 * 两时间相差天数
	 */
	public int daysOfTwo(Date fDate, Date oDate) {

	       Calendar aCalendar = Calendar.getInstance();

	       aCalendar.setTime(fDate);

	       int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);

	       aCalendar.setTime(oDate);

	       int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);

	       return day2-day1;

	  }
	
	
	/**
	 * 判断是否为发电权类型合同
	 * @param contractid
	 * @return
	 */
	public String checkCoTypeTwo(String contractid) {
		String sql="select t.contracttypeid,t.supertypeid,t.typename from co_contracttypeinfo t start with t.contracttypeid =(select contracttype from co_contractbaseinfo   where contractid = ?) connect by t.contracttypeid = prior t.supertypeid and t.supertypeid is null";
		List tlist= hibernateDao.executeSqlQuery(sql.toString(), new Object[]{contractid});
		for(int i=0;i<tlist.size();i++){
			Object [] tenObj=(Object[]) tlist.get(i);
			if("4DC24C12-413C-5A67-BEBE-AB07C0C".equals(tenObj[0])){
				return "1";
			}
		}
		return "0";
	}
	
	
	/**
	 * 保存更新
	 * 
	 * @param list
	 */
	 public List<CoContractmonthqtyVO> saveOrUpdate(List<Map> list) {
		 
		List<CoContractmonthqtyVO> voList = new ArrayList<CoContractmonthqtyVO>();
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
			String poName = CoContractmonthqty.class.getName();
			if(map.containsKey("contractid")){
				String id = (String)map.get("contractid");
				CoContractmonthqtyVO vo = update(map,poName,id);
				voList.add(vo);
			}else{
				CoContractmonthqtyVO coContractmonthqtyVO = save(map);
				voList.add(coContractmonthqtyVO);
			}
		}
		return voList;
	 }
	 
	//保存记录
	private CoContractmonthqtyVO save(Map map) {
		
		CoContractmonthqtyVO coContractmonthqtyVo = new CoContractmonthqtyVO();
		try {
			BeanUtils.populate(coContractmonthqtyVo, map);
		} catch (Exception e) {
		}
		CoContractmonthqty coContractmonthqty = CoContractmonthqtyTransfer.toPO(coContractmonthqtyVo);
		hibernateDao.saveOrUpdateObject(coContractmonthqty);
		coContractmonthqtyVo = CoContractmonthqtyTransfer.toVO(coContractmonthqty);
		if(map.containsKey("mxVirtualId")){
			coContractmonthqtyVo.setMxVirtualId(String.valueOf(map.get("mxVirtualId")));
		}
		return coContractmonthqtyVo;
	}
	
	//更新记录
	private CoContractmonthqtyVO update(Map<String, ?> map,String poName,String id) {
		
		CoContractmonthqtyVO coContractmonthqtyVo = new CoContractmonthqtyVO();
		//更新操作
		try {
			BeanUtils.populate(coContractmonthqtyVo, map);
		} catch (Exception e) {
		}
		Object[] objArray = CrudUtils.generateHql(CoContractmonthqty.class, map, "contractid");
		hibernateDao.update((String) objArray[0], (Object[]) objArray[1]);
		return coContractmonthqtyVo;
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
			hibernateDao.update("delete from " + CoContractmonthqty.class.getName() + " where contractid = ?" ,new Object[]{id});
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
		List<CoContractmonthqty> result = null;
		int count = 0;
		qc.addFrom(CoContractmonthqty.class);
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
		List<QueryFilter> wheres = queryCondition.getQueryFilter(CoContractmonthqtyVO.class);
		if (wheres != null && wheres.size() > 0) {
			CrudUtils.addQCWhere(qc, wheres, CoContractmonthqty.class.getName());

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
		CoContractmonthqty coContractmonthqty = (CoContractmonthqty) hibernateDao.getObject(CoContractmonthqty.class,id);
		CoContractmonthqtyVO vo = null;
		if (coContractmonthqty != null) {
			vo = CoContractmonthqtyTransfer.toVO(coContractmonthqty);
		}
		return RestUtils.wrappQueryResult(vo);
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
	
	public void chooseUnitResolve(List items){
		Map<String, ContractUnitBean> contractUnit = new HashMap<String, ContractUnitBean>();//存储选中的机组信息
		
		Set<String> contractSet = new TreeSet<String>();//合同id集合。多个相同合同id，只保留一个
		
		for(int i = 0; i < items.size(); i++){
			String param = items.get(i).toString();
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
		
		Iterator<String> idIterator = contractSet.iterator(); 
		while(idIterator.hasNext()){
			String contractid = idIterator.next();
			
			ArrayList<String> purchaserList = contractUnit.get(contractid).getPurchaserList();//选中的购方经济机组id信息
			ArrayList<String> sellerList = contractUnit.get(contractid).getSellerList();//选中的售方经济机组id信息
			
			String [] participantParam = participantInContract(contractid);
			String purchaserId =  participantParam[0];
			String purchaserType = participantParam[2];
			String sellerId = participantParam[1];
			String sellerType = participantParam[3];//售电方市场成员类型
			
			
			CapacityBean purchaserBean = null;
			CapacityBean sellerBean = null;
			if((purchaserList == null || purchaserList.size() == 0) && ( sellerList != null && sellerList.size() != 0)){
				purchaserBean = getPurchaserUnit(contractid, purchaserId, purchaserType);
				sellerBean = filterParticipant(contractid, sellerId, sellerType, sellerList);
			} else if((purchaserList != null && purchaserList.size() != 0) && ( sellerList == null || sellerList.size() == 0)){
				purchaserBean = filterParticipant(contractid, purchaserId, purchaserType, purchaserList);
				sellerBean = getSellerUnit(contractid, sellerId, sellerType);
			} else if((purchaserList != null && purchaserList.size() != 0) && ( sellerList != null && sellerList.size() != 0)){
				purchaserBean = filterParticipant(contractid, purchaserId, purchaserType, purchaserList);
				sellerBean = filterParticipant(contractid, sellerId, sellerType, sellerList);
			}
			
			List<CoContractmonthqty> list = getCoContractmonthqty(contractid);// 发电权合同查询出多条数据
			if(list != null && list.size() != 0){
				for(int i = 0; i < list.size(); i++){//发电权合同查询出多条数据
					CoContractmonthqty coContractmonthqty = list.get(i);
					calculateCapacity(purchaserBean, sellerBean, coContractmonthqty, getStartDate(contractid));
				}
			}
		}
	}
	
	private Date getStartDate(String contractid){
		String sql = "select t.startdate from co_contractenergyinfo t where t.contractid = ?";
		List list = hibernateDao.executeSqlQuery(sql, new Object[]{contractid});
		if(list != null && list.size() != 0){
			return getDate(list.get(0));
		} 
		return null;
	}
	
	private Date getDate(Object obj){
		return obj==null?null:DateUtil.getUtilDate(obj.toString(), "yyyy-MM-dd");
	}
	
	private CapacityBean filterParticipant(String contractid,String purchaserId, String purchaserType, List purchaserList){
		CapacityBean pBean = new CapacityBean();
		double totalCapacity = 0d;
		
		CapacityBean purchaserBean = getPurchaserUnit(contractid, purchaserId, purchaserType);//查询合同所有的购方经济机组信息
		List<CoBusiGeneratorQtyVO> allPurchaserList = purchaserBean.getUnitList();
		
		Iterator<String> purIt = purchaserList.iterator();//选中的购方经济机组信息
		
		while(purIt.hasNext()){
			String purchaser = purIt.next();
			
			Iterator<CoBusiGeneratorQtyVO> allPurIt = allPurchaserList.iterator();
			while(allPurIt.hasNext()){
				CoBusiGeneratorQtyVO vo = allPurIt.next();
				if(purchaser.equals(vo.getEcounitid())){//筛选出选中的购方经济机组信息，保存到新的对象中。
					if(participantType(vo.getParticipantid())){//判断用户类型是否是大用户或电网企业
						totalCapacity = 1;
					} else {
						totalCapacity += string2Double(vo.getEcounitcapacity());
					}
					pBean.getUnitList().add(vo);
				}
			}
		}
		pBean.setCapacity(totalCapacity);
		return pBean;
	}
	
	private List<CoContractmonthqty> getCoContractmonthqty(String contractid){
		List<CoContractmonthqty> result = null;
		QueryFilter qf = new QueryFilter();
		qf.setDataType("string");
		qf.setFieldName("contractid");
		qf.setOperator("=");
		qf.setValue(contractid);
		
		List<QueryFilter> wheres = new ArrayList<QueryFilter>();//queryCondition.getQueryFilter(CoContractmonthqtyVO.class);
		wheres.add(qf);
		QueryCriteria qc = new QueryCriteria();
		qc.addFrom(CoContractmonthqty.class);
		CrudUtils.addQCWhere(qc, wheres, CoContractmonthqty.class.getName()); 
		result = hibernateDao.findAllByCriteria(qc);
		return result;
	}

	public QueryResultObject getResolveResult(RequestCondition params) {
		ArrayList<ResolveResultVO> resolveList = new ArrayList<ResolveResultVO>();
		
		Map<String, String> map = null;
		try {
			map = JsonUtil.jsonToObject(params.getFilter().toString(), Map.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//获取前台filter中的contractid
		if(map == null){
			throw new RuntimeException("前台传入contractid为空");
		}
		String contractid = map.get("contractid");
		String monthqtytype = map.get("monthqtytype");//TODO 
		String sql = getResolveResult();
		List result = hibernateDao.executeSqlQuery(sql, new Object[]{contractid, monthqtytype}, 
				Integer.parseInt(params.getPageIndex()), Integer.parseInt(params.getPageSize()));
		if(result != null && result.size() != 0){
			Iterator it = result.iterator();
			while(it.hasNext()){
				ResolveResultVO vo = new ResolveResultVO();
				Object [] obj = (Object [])it.next();
				
				vo.setContractid(validateString(obj[0]));
				vo.setContractname(validateString(obj[1]));
				vo.setPurchaserunitname(validateString(obj[2]));
				vo.setSellerunitname(validateString(obj[3]));
				vo.setJanqty(validateString(obj[4]));
				vo.setFebqty(validateString(obj[5]));
				vo.setMarqty(validateString(obj[6]));
				vo.setAprilqty(validateString(obj[7]));
				vo.setMayqty(validateString(obj[8]));
				vo.setJunqty(validateString(obj[9]));
				vo.setJulyqty(validateString(obj[10]));
				vo.setAugqty(validateString(obj[11]));
				vo.setSepqty(validateString(obj[12]));
				vo.setOctqty(validateString(obj[13]));
				vo.setNovqty(validateString(obj[14]));
				vo.setDecqty(validateString(obj[15]));
				resolveList.add(vo);
			}
		}
		
		String countsql = "select count(1) from (" + sql + ")";
		List list2=hibernateDao.executeSqlQuery(countsql, new Object[]{contractid, monthqtytype});
		int count = 0;
		if(list2!=null ){
			count = Integer.parseInt(list2.get(0).toString());
		}
		return RestUtils.wrappQueryResult(resolveList, count);
	}
	
	public static String getResolveResult(){
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append("members.contractid, ");
		sql.append("(select baseinfo.contractname from co_contractbaseinfo baseinfo where baseinfo.contractid =  members.contractid ) contractname, ");
		sql.append("(select econ.ecounitname from ba_economicunit econ  where econ.ecounitid = members.purchaseunit) purchaserunit, ");
		sql.append("(select econ.ecounitname from ba_economicunit econ where econ.ecounitid = members.sellerunit) sellerunit, ");
		sql.append("(select qty.netqty from co_busigeneratorqty qty where qty.contractid= members.contractid and qty.purchaseunit=members.purchaseunit and qty.sellerunit = members.sellerunit and qty.monthqtytype = members.monthqtytype and timeno = '1') jan, ");
		sql.append("(select qty.netqty from co_busigeneratorqty qty where qty.contractid= members.contractid and qty.purchaseunit=members.purchaseunit and qty.sellerunit = members.sellerunit and qty.monthqtytype = members.monthqtytype and timeno = '2') feb, ");
		sql.append("(select qty.netqty from co_busigeneratorqty qty where qty.contractid= members.contractid and qty.purchaseunit=members.purchaseunit and qty.sellerunit = members.sellerunit and qty.monthqtytype = members.monthqtytype and timeno = '3') mar, ");
		sql.append("(select qty.netqty from co_busigeneratorqty qty where qty.contractid= members.contractid and qty.purchaseunit=members.purchaseunit and qty.sellerunit = members.sellerunit and qty.monthqtytype = members.monthqtytype and timeno = '4') april, ");
		sql.append("(select qty.netqty from co_busigeneratorqty qty where qty.contractid= members.contractid and qty.purchaseunit=members.purchaseunit and qty.sellerunit = members.sellerunit and qty.monthqtytype = members.monthqtytype and timeno = '5') mah, ");
		sql.append("(select qty.netqty from co_busigeneratorqty qty where qty.contractid= members.contractid and qty.purchaseunit=members.purchaseunit and qty.sellerunit = members.sellerunit and qty.monthqtytype = members.monthqtytype and timeno = '6') jun, ");
		sql.append("(select qty.netqty from co_busigeneratorqty qty where qty.contractid= members.contractid and qty.purchaseunit=members.purchaseunit and qty.sellerunit = members.sellerunit and qty.monthqtytype = members.monthqtytype and timeno = '7') july, ");
		sql.append("(select qty.netqty from co_busigeneratorqty qty where qty.contractid= members.contractid and qty.purchaseunit=members.purchaseunit and qty.sellerunit = members.sellerunit and qty.monthqtytype = members.monthqtytype and timeno = '8') aug, ");
		sql.append("(select qty.netqty from co_busigeneratorqty qty where qty.contractid= members.contractid and qty.purchaseunit=members.purchaseunit and qty.sellerunit = members.sellerunit and qty.monthqtytype = members.monthqtytype and timeno = '9') sep, ");
		sql.append("(select qty.netqty from co_busigeneratorqty qty where qty.contractid= members.contractid and qty.purchaseunit=members.purchaseunit and qty.sellerunit = members.sellerunit and qty.monthqtytype = members.monthqtytype and timeno = '10') oct, ");
		sql.append("(select qty.netqty from co_busigeneratorqty qty where qty.contractid= members.contractid and qty.purchaseunit=members.purchaseunit and qty.sellerunit = members.sellerunit and qty.monthqtytype = members.monthqtytype and timeno = '11') nov, ");
		sql.append("(select qty.netqty from co_busigeneratorqty qty where qty.contractid= members.contractid and qty.purchaseunit=members.purchaseunit and qty.sellerunit = members.sellerunit and qty.monthqtytype = members.monthqtytype and timeno = '12') December ");
		sql.append("from ");
		sql.append("(select contractid, qty.purchaseunit,qty.sellerunit,monthqtytype from co_busigeneratorqty qty where contractid = ? and monthqtytype = ? ");
		sql.append("group by contractid, qty.purchaseunit,qty.sellerunit,monthqtytype) members ");
		sql.append("order by purchaserunit,sellerunit");
		return sql.toString();
	}
}
