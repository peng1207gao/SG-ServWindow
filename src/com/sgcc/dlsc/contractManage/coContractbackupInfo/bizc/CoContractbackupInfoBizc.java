package com.sgcc.dlsc.contractManage.coContractbackupInfo.bizc;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import com.sgcc.uap.persistence.IHibernateDao;
import com.sgcc.uap.persistence.criterion.QueryCriteria;

import com.sgcc.uap.bizc.sysbizc.datadictionary.IDataDictionaryBizC;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.support.DicItems;

import com.sgcc.uap.rest.support.IDRequestObject;
import com.sgcc.uap.rest.support.QueryFilter;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;
import com.sgcc.uap.rest.utils.CrudUtils;
import com.sgcc.uap.rest.utils.RestUtils;

//引入po,vo,transefer
import com.sgcc.dlsc.commons.util.ComUtils;
import com.sgcc.dlsc.commons.util.UserInfoUtil;
import com.sgcc.dlsc.contractManage.CoContransEnergyInfo.vo.CoContransenergyinfoTransfer;
import com.sgcc.dlsc.contractManage.CoContransEnergyInfo.vo.CoContransenergyinfoVO;
import com.sgcc.dlsc.contractManage.coContractbackupInfo.vo.CoContractbackupinfoTransfer;
import com.sgcc.dlsc.contractManage.coContractbackupInfo.vo.CoContractbackupinfoVO;
import com.sgcc.dlsc.contractManage.coContractbaseinfoV2Act.vo.CoContractbaseinfoTransfer;
import com.sgcc.dlsc.entity.po.CoContractbackupinfo;
import com.sgcc.dlsc.entity.po.CoContractbaseinfo;
import com.sgcc.dlsc.entity.po.CoContractchangerecordinfo;
import com.sgcc.dlsc.entity.po.CoContransenergyinfo;
/**
 * 用户定义逻辑构件
 * 
 * @author xiabaike 
 * 
 */
public class CoContractbackupInfoBizc implements ICoContractbackupInfoBizc{

	@Autowired
	private IHibernateDao hibernateDao;
	
	@Autowired
	private IDataDictionaryBizC dataDictionaryBizC;
	public SessionFactory sessionFactory;

	/**
	 * 输电方记录的删除
	 */
	public int sdfDelete(String checkedId) {
		int result = hibernateDao.update("delete from " + CoContransenergyinfo.class.getName() + " where guid = ?" ,new Object[]{checkedId});
		return result;
	}
	
	public boolean update(CoContractbaseinfo coContractbaseinfo) {
		hibernateDao.saveOrUpdateObject(coContractbaseinfo);
		return true;
	}
	
	/**
	 * 根据ID查询合同信息
	 */
	public CoContractbaseinfo findById(String checkedId){
		CoContractbaseinfo ccb = hibernateDao.getObject(CoContractbaseinfo.class, checkedId);
		System.out.println(ccb);
		return ccb;
	}
	
	public boolean save(CoContractchangerecordinfo ccc) {
		hibernateDao.saveObject(ccc);
		return true;
	}
	
	public int fjByList(List list) {
		System.out.println("*******************************************"+list.size()+"******************************");
		int result = 0;
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).toString());
			result += hibernateDao.executeSqlUpdate(list.get(i).toString());
		}
		System.out.println("--------------------fjByList result:--"+ result);
		return result;
	}
	
	public List getCotractList(String marketid){
		//String sql = "select contractid from  CO_ContractbaseInfo cc where marketid='"+marketid+"' and sysdate between cc.contractstartdate and cc.contractenddate";
		String sql = "select contractid from  CO_ContractbaseInfo cc where marketid='"+marketid+"' ";
		return hibernateDao.executeSqlQuery(sql);
	}
	
	public List fjList(String marketid){
		List list = new ArrayList();
		List list1 = new ArrayList();
		List list2 = new ArrayList();
		List list3 = new ArrayList();
		String deleteSql ="delete from  co_contractbaseinfo_unit where marketid='"+marketid+"'";
		String deleteSql1 ="delete from  co_contractbaseinfo_unit1 where marketid='"+marketid+"'";
		list.add(deleteSql);
		list.add(deleteSql1);
		List contractList = getCotractList(marketid);
		for (int i = 0; i < contractList.size(); i++) {
			
			Object obj = (Object)contractList.get(i);
			String contractid = obj.toString();
			list1 = getlist1(contractid);
			if (list1!=null && list1.size()>0) {//从表有数据
				list2 = getlist2(contractid);
				if (list2!=null && list2.size()>0) {//维护了机组
					list3 = getlist3(contractid,1,marketid);//维护了机组
				}else{
					list3 = getlist3(contractid,2,marketid);//没有维护机组
				}
			}else{//从表没有数据，直接分解主表
				list2 = getlist2(contractid);
				if (list2!=null && list2.size()>0) {//维护了机组
					list3 = getlist3(contractid,3,marketid);//维护了机组
				}else{
					list3 = getlist3(contractid,4,marketid);//没有维护机组
				}
			}
			list.addAll(list3);
		}
//		}
		return list;
	}
	
	public List getlist1(String contractid) {
		String sql = "select * from  CO_ContractEnergyInfo where contractid='"+contractid+"'";
		return hibernateDao.executeSqlQuery(sql);
	}
	
	public List getlist2(String contractid) {
		String sql = "select participantid from  Co_Contractaccessory where contractid='"+contractid+"' and participantid is not null group by participantid";
		return hibernateDao.executeSqlQuery(sql);
	}
	/**
	 * 合同分解到机组
	 * @description 方法描述:对于一条合同，若合同从表co_contractenergyinfo有数据，则根据该表的数据进行分解到分解表co_contractbaseinfo_unit；若从表数据中的上网电量为空，就把合同电量分解到购方上网电量和售方上网电量，发电量只根据发电量分；若从表无数据，则分解主表的数据。根据合同机组表co_contractaccessory查询与合同有关的机组，如果该表没有数据，则根据购方和售方的市场成员在经济机组表找到机组。
	 * @param contractid
	 * @param n
	 * @param marketid
	 * @return
	 * @author zhangbo 
	 * @throws SQLException 
	 * @date 2013-8-21
	 */
	public List getlist3(String contractid,int n,String marketid){
		List sqllist = new ArrayList();
		if (n==1) {//从表有数据且维护了机组，按机组的权容分
			/*
			 * Co_Contractaccessory中的交易角色
			 */
			String sql = "select  contractrole from  Co_Contractaccessory where contractid='"+contractid+"' group by contractrole";
			List list = hibernateDao.executeSqlQuery(sql);
			
			//////////////2013-9-26
			if (list!=null && list.size()>0 && list.size()==1) {//2013-9-26追加的判断，若只有一方维护了机组，另一方按全部经济机组分解；引用n=4的插入语句
				Object obj = (Object) list.get(0);
				String contractrole = obj.toString();//交易角色
				if (contractrole.equals("1")) {//维护了购方，此处分解售方的
					/*
					 * 售方
					 */
					//循环经济机组
					String sql5 = "select be.ecounitid,1/(select count(be.ecounitid) from  BA_EconomicUnit be where be.participantid=(select seller from  co_contractbaseinfo cc where cc.marketid='"+marketid+"' and cc.contractid='"+contractid+"')) from  BA_EconomicUnit be where be.participantid=(select seller from  co_contractbaseinfo cc where cc.marketid='"+marketid+"' and cc.contractid='"+contractid+"')";
					List list5= hibernateDao.executeSqlQuery(sql5);
					for (int j = 0; j < list5.size(); j++) {
						Object[] obj1 = (Object[])list5.get(j);
						String ecounitid = obj1[0].toString();
						float fene = Float.parseFloat(obj1[1].toString());
						//循环时段
						String sql3 = "select nvl(ce.period,1),case when(select min(nvl(timeno,0)) from  co_contractenergyinfo where contractid='"+contractid+"')=0 then rownum else ce.timeno end timeno,to_char(ce.startdate,'yyyy-mm-dd'),to_char(ce.enddate,'yyyy-mm-dd'),ce.energy,ce.purchaserenergy,ce.purchasergen,ce.sellerenergy,ce.sellergen from  co_contractenergyinfo ce where contractid='"+contractid+"'order by ce.startdate";
						List list3 = hibernateDao.executeSqlQuery(sql3);//timeno是否可为空，现在timeno为空，导致12条已经按月分解的数据没办法区分,暂时按时间排序。
						for (int k = 0; k < list3.size(); k++) {//循环时段
							Object[] obj2 = (Object[]) list3.get(k);
							String energy = obj2[4]==null?"0":obj2[4].toString();//合同电量
							String period = obj2[0]==null?"1":obj2[0].toString();
							String timeno = obj2[1]==null?"":obj2[1].toString();
							String startdate = obj2[2]==null?"":obj2[2].toString();
							String enddate = obj2[3]==null?"":obj2[3].toString();
							String purchaserenergy = obj2[5]==null?energy:obj2[5].toString();
							String purchasergen = obj2[6]==null?"0":obj2[6].toString();
							String sellerenergy = obj2[7]==null?energy:obj2[7].toString();
							String sellergen = obj2[8]==null?"0":obj2[8].toString();
							
							String sql4 = "insert into  CO_ContractBaseInfo_Unit(guid, contractid, purchasertype, unitid, netqty, genqty, timeno, startdate, enddate, period, marketid) values((select sys_guid() from dual),'"+contractid+"','2','"+ecounitid+"',"+fene+"*"+sellerenergy+","+fene+"*"+sellergen+","+timeno+",to_date('"+startdate+"','yyyy-mm-dd'),to_date('"+enddate+"','yyyy-mm-dd'),'"+period+"','"+marketid+"')";//插入
							sqllist.add(sql4);
						}
					}
				}else if (contractrole.equals("2")) {//维护了售方，此处分解购方的
					/*
					 * 购方
					 */
					//循环经济机组
					String sql2 = "select be.ecounitid,1/(select count(be.ecounitid) from  BA_EconomicUnit be where be.participantid=(select purchaser from  co_contractbaseinfo cc where cc.marketid='"+marketid+"' and cc.contractid='"+contractid+"')) from  BA_EconomicUnit be where be.participantid=(select purchaser from  co_contractbaseinfo cc where cc.marketid='"+marketid+"' and cc.contractid='"+contractid+"')";
					List list2= hibernateDao.executeSqlQuery(sql2);
					for (int j = 0; j < list2.size(); j++) {
						Object[] obj1 = (Object[])list2.get(j);
						String ecounitid = obj1[0].toString();
						float fene = Float.parseFloat(obj1[1].toString());
						//循环时间段
						String sql3 = "select nvl(ce.period,1),case when(select min(nvl(timeno,0)) from  co_contractenergyinfo where contractid='"+contractid+"')=0 then rownum else ce.timeno end timeno,to_char(ce.startdate,'yyyy-mm-dd'),to_char(ce.enddate,'yyyy-mm-dd'),ce.energy,ce.purchaserenergy,ce.purchasergen,ce.sellerenergy,ce.sellergen from  co_contractenergyinfo ce where contractid='"+contractid+"'order by ce.startdate";
						List list3 = hibernateDao.executeSqlQuery(sql3);//timeno是否可为空，现在timeno为空，导致12条已经按月分解的数据没办法区分,暂时按时间排序。
						for (int k = 0; k < list3.size(); k++) {//循环时间段
							Object[] obj2 = (Object[]) list3.get(k);
							String energy = obj2[4]==null?"0":obj2[4].toString();//合同电量
							String period = obj2[0]==null?"1":obj2[0].toString();
							String timeno = obj2[1]==null?"":obj2[1].toString();
							String startdate = obj2[2]==null?"":obj2[2].toString();
							String enddate = obj2[3]==null?"":obj2[3].toString();
							String purchaserenergy = obj2[5]==null?energy:obj2[5].toString();
							String purchasergen = obj2[6]==null?"0":obj2[6].toString();
							String sellerenergy = obj2[7]==null?energy:obj2[7].toString();
							String sellergen = obj2[8]==null?"0":obj2[8].toString();
							
							String sql4 = "insert into  CO_ContractBaseInfo_Unit(guid, contractid, purchasertype, unitid, netqty, genqty, timeno, startdate, enddate, period, marketid) values((select sys_guid() from dual),'"+contractid+"','1','"+ecounitid+"',"+fene+"*"+purchaserenergy+","+fene+"*"+purchasergen+","+timeno+",to_date('"+startdate+"','yyyy-mm-dd'),to_date('"+enddate+"','yyyy-mm-dd'),'"+period+"','"+marketid+"')";//插入
							sqllist.add(sql4);
						}
					}
				}
			}
			/////////////
			for (int i = 0; i < list.size(); i++) {//循环角色
				Object obj = (Object) list.get(i);
				String contractrole = obj.toString();//交易角色
				//经济机组的权容,维护了机组的权容是否可能为空？为空不能继续操作，暂时处理为按机组平均分
				String sql1 = "select be.ecounitid,nvl(ecoUnitCapacity,0),case when (select sum(nvl(ecoUnitCapacity,0))  from  BA_EconomicUnit be where be.generatorid in (select ccc.participantid from  co_contractaccessory ccc where ccc.contractid='"+contractid+"' and ccc.contractrole='"+contractrole+"') )<>0 then nvl(ecoUnitCapacity,0)/(select sum(nvl(ecoUnitCapacity,0))  from  BA_EconomicUnit be where be.generatorid in (select ccc.participantid from  co_contractaccessory ccc where ccc.contractid='"+contractid+"' and ccc.contractrole='"+contractrole+"') ) ELSE 1/(SELECT COUNT(DISTINCT BE.ECOUNITID) FROM BA_ECONOMICUNIT BE WHERE BE.generatorid IN (select PARTICIPANTID from  Co_Contractaccessory cy where cy.contractid='"+contractid+"') ) END c3 from  BA_EconomicUnit be where be.generatorid in (select ccc.participantid from  co_contractaccessory ccc where ccc.contractid='"+contractid+"' and ccc.contractrole='"+contractrole+"')";
				List list1 = hibernateDao.executeSqlQuery(sql1);
				for (int j = 0; j < list1.size(); j++) {//循环经济机组
					Object[] obj1 = (Object[]) list1.get(j);
					String ecounitid = obj1[0].toString();//经济机组id
					float fene = Float.parseFloat(obj1[2].toString());//份额
					String sql2 = "select nvl(ce.period,1),case when(select min(nvl(timeno,0)) from  co_contractenergyinfo where contractid='"+contractid+"')=0 then rownum else ce.timeno end timeno,to_char(ce.startdate,'yyyy-mm-dd'),to_char(ce.enddate,'yyyy-mm-dd'),ce.energy,ce.purchaserenergy,ce.purchasergen,ce.sellerenergy,ce.sellergen from  co_contractenergyinfo ce where contractid='"+contractid+"'order by ce.startdate";
					List list2 = hibernateDao.executeSqlQuery(sql2);//period是否可为空，现在period为空，导致12条已经按月分解的数据没办法区分,暂时按时间排序。
					for (int k = 0; k < list2.size(); k++) {//循环时段
						Object[] obj2 = (Object[]) list2.get(k);
						String energy = obj2[4]==null?"":obj2[4].toString();//合同电量
						String period = obj2[0]==null?"":obj2[0].toString();
						String timeno = obj2[1]==null?"1":obj2[1].toString();
						String startdate = obj2[2]==null?"":obj2[2].toString();
						String enddate = obj2[3]==null?"":obj2[3].toString();
						String purchaserenergy = obj2[5]==null?energy:obj2[5].toString();
						String purchasergen = obj2[6]==null?"0":obj2[6].toString();
						String sellerenergy = obj2[7]==null?energy:obj2[7].toString();
						String sellergen = obj2[8]==null?"0":obj2[8].toString();
						if (contractrole.equals("1")) {
							String sql4 = "insert into  CO_ContractBaseInfo_Unit(guid, contractid, purchasertype, unitid, netqty, genqty, timeno, startdate, enddate, period, marketid) values((select sys_guid() from dual),'"+contractid+"','"+contractrole+"','"+ecounitid+"',"+fene+"*"+purchaserenergy+","+fene+"*"+purchasergen+","+timeno+",to_date('"+startdate+"','yyyy-mm-dd'),to_date('"+enddate+"','yyyy-mm-dd'),'"+period+"','"+marketid+"')";//插入
							sqllist.add(sql4);
						}else if (contractrole.equals("2")) {
							String sql4 = "insert into  CO_ContractBaseInfo_Unit(guid, contractid, purchasertype, unitid, netqty, genqty, timeno, startdate, enddate, period, marketid) values((select sys_guid() from dual),'"+contractid+"','"+contractrole+"','"+ecounitid+"',"+fene+"*"+sellerenergy+","+fene+"*"+sellergen+","+timeno+",to_date('"+startdate+"','yyyy-mm-dd'),to_date('"+enddate+"','yyyy-mm-dd'),'"+period+"','"+marketid+"')";//插入
							sqllist.add(sql4);
						}
					}
				}
				
			}
			
		}else if(n==2){//从表有数据但没有维护机组,按市场成员下的机组平均分
			/*
			 * 购方
			 */
			//循环经济机组
			String sql2 = "select be.ecounitid,1/(select count(be.ecounitid) from  BA_EconomicUnit be where be.participantid=(select purchaser from  co_contractbaseinfo cc where cc.marketid='"+marketid+"' and cc.contractid='"+contractid+"')) from  BA_EconomicUnit be where be.participantid=(select purchaser from  co_contractbaseinfo cc where cc.marketid='"+marketid+"' and cc.contractid='"+contractid+"')";
			List list2= hibernateDao.executeSqlQuery(sql2);
			for (int j = 0; j < list2.size(); j++) {
				Object[] obj = (Object[])list2.get(j);
				String ecounitid = obj[0].toString();
				float fene = Float.parseFloat(obj[1].toString());
				//循环时间段
				String sql3 = "select nvl(ce.period,1),case when(select min(nvl(timeno,0)) from  co_contractenergyinfo where contractid='"+contractid+"')=0 then rownum else ce.timeno end timeno,to_char(ce.startdate,'yyyy-mm-dd'),to_char(ce.enddate,'yyyy-mm-dd'),ce.energy,ce.purchaserenergy,ce.purchasergen,ce.sellerenergy,ce.sellergen from  co_contractenergyinfo ce where contractid='"+contractid+"'order by ce.startdate";
				List list3 = hibernateDao.executeSqlQuery(sql3);//timeno是否可为空，现在timeno为空，导致12条已经按月分解的数据没办法区分,暂时按时间排序。
				for (int k = 0; k < list3.size(); k++) {//循环时间段
					Object[] obj2 = (Object[]) list3.get(k);
					String energy = obj2[4]==null?"0":obj2[4].toString();//合同电量
					String period = obj2[0]==null?"1":obj2[0].toString();
					String timeno = obj2[1]==null?"":obj2[1].toString();
					String startdate = obj2[2]==null?"":obj2[2].toString();
					String enddate = obj2[3]==null?"":obj2[3].toString();
					String purchaserenergy = obj2[5]==null?energy:obj2[5].toString();
					String purchasergen = obj2[6]==null?"0":obj2[6].toString();
					String sellerenergy = obj2[7]==null?energy:obj2[7].toString();
					String sellergen = obj2[8]==null?"0":obj2[8].toString();
					
					String sql4 = "insert into  CO_ContractBaseInfo_Unit(guid, contractid, purchasertype, unitid, netqty, genqty, timeno, startdate, enddate, period, marketid) values((select sys_guid() from dual),'"+contractid+"','1','"+ecounitid+"',"+fene+"*"+purchaserenergy+","+fene+"*"+purchasergen+","+timeno+",to_date('"+startdate+"','yyyy-mm-dd'),to_date('"+enddate+"','yyyy-mm-dd'),'"+period+"','"+marketid+"')";//插入
					sqllist.add(sql4);
				}
			}
			/*
			 * 售方
			 */
			//循环经济机组
			String sql5 = "select be.ecounitid,1/(select count(be.ecounitid) from  BA_EconomicUnit be where be.participantid=(select seller from  co_contractbaseinfo cc where cc.marketid='"+marketid+"' and cc.contractid='"+contractid+"')) from  BA_EconomicUnit be where be.participantid=(select seller from  co_contractbaseinfo cc where cc.marketid='"+marketid+"' and cc.contractid='"+contractid+"')";
			List list5= hibernateDao.executeSqlQuery(sql5);
			for (int j = 0; j < list5.size(); j++) {
				Object[] obj = (Object[])list5.get(j);
				String ecounitid = obj[0].toString();
				float fene = Float.parseFloat(obj[1].toString());
				//循环时段
				String sql3 = "select nvl(ce.period,1),case when(select min(nvl(timeno,0)) from  co_contractenergyinfo where contractid='"+contractid+"')=0 then rownum else ce.timeno end timeno,to_char(ce.startdate,'yyyy-mm-dd'),to_char(ce.enddate,'yyyy-mm-dd'),ce.energy,ce.purchaserenergy,ce.purchasergen,ce.sellerenergy,ce.sellergen from  co_contractenergyinfo ce where contractid='"+contractid+"'order by ce.startdate";
				List list3 = hibernateDao.executeSqlQuery(sql3);//timeno是否可为空，现在timeno为空，导致12条已经按月分解的数据没办法区分,暂时按时间排序。
				for (int k = 0; k < list3.size(); k++) {//循环时段
					Object[] obj2 = (Object[]) list3.get(k);
					String energy = obj2[4]==null?"0":obj2[4].toString();//合同电量
					String period = obj2[0]==null?"1":obj2[0].toString();
					String timeno = obj2[1]==null?"":obj2[1].toString();
					String startdate = obj2[2]==null?"":obj2[2].toString();
					String enddate = obj2[3]==null?"":obj2[3].toString();
					String purchaserenergy = obj2[5]==null?energy:obj2[5].toString();
					String purchasergen = obj2[6]==null?"0":obj2[6].toString();
					String sellerenergy = obj2[7]==null?energy:obj2[7].toString();
					String sellergen = obj2[8]==null?"0":obj2[8].toString();
					
					String sql4 = "insert into  CO_ContractBaseInfo_Unit(guid, contractid, purchasertype, unitid, netqty, genqty, timeno, startdate, enddate, period, marketid) values((select sys_guid() from dual),'"+contractid+"','2','"+ecounitid+"',"+fene+"*"+sellerenergy+","+fene+"*"+sellergen+","+timeno+",to_date('"+startdate+"','yyyy-mm-dd'),to_date('"+enddate+"','yyyy-mm-dd'),'"+period+"','"+marketid+"')";//插入
					sqllist.add(sql4);
				}
			}

		}else if(n==3){//从表没有数据,分主表数据，时段为1;维护了机组
			/*
			 * 交易角色
			 */
			String sql = "select  contractrole from  Co_Contractaccessory where contractid='"+contractid+"'";
			List list = hibernateDao.executeSqlQuery(sql);
			///////////////////2013-9-26
			if (list!=null && list.size()>0 && list.size()==1) {//2013-9-26追加的判断，若只有一方维护了机组，另一方按全部经济机组分解；引用n=4的插入语句
				Object obj = (Object) list.get(0);
				String contractrole = obj.toString();//交易角色
				if (contractrole.equals("1")) {//维护了购方，此处分解售方的
					/*
					 * 售方
					 */
					String sql3 = "select be.ecounitid,1/(select count(be.ecounitid) from  BA_EconomicUnit be where be.participantid=(select seller from  co_contractbaseinfo cc where cc.marketid='"+marketid+"' and cc.contractid='"+contractid+"')) from  BA_EconomicUnit be where be.participantid=(select seller from  co_contractbaseinfo cc where cc.marketid='"+marketid+"' and cc.contractid='"+contractid+"')";
					List list3= hibernateDao.executeSqlQuery(sql3);
					for (int j = 0; j < list3.size(); j++) {
						Object[] obj1 = (Object[])list3.get(j);
						String ecounitid = obj1[0].toString();
						float fene = Float.parseFloat(obj1[1].toString());
						String sql4 = "insert into  CO_ContractBaseInfo_Unit1(guid, contractid, purchasertype, unitid, netqty, genqty, timeno, startdate, enddate, period, marketid) values((select sys_guid() from dual),'"+contractid+"',2,'"+ecounitid+"',"+fene+"*nvl((select SELLERENERGY from  co_contractbaseinfo where marketID='"+marketid+"'and contractID='"+contractid+"'),(select cc.contractqty from  co_contractbaseinfo cc where cc.marketid='"+marketid+"' and cc.contractid='"+contractid+"')),"+fene+"*(select SELLERGEN from  co_contractbaseinfo where marketID='"+marketid+"'and contractID='"+contractid+"'),1,(select distinct ce.contractstartdate from  co_contractbaseinfo ce where contractid='"+contractid+"' and marketID='"+marketid+"'),(select distinct ce.contractenddate from  co_contractbaseinfo ce where contractid='"+contractid+"' and marketID='"+marketid+"' ),1,'"+marketid+"')";//插入
						sqllist.add(sql4);
					}
				}else if (contractrole.equals("2")) {//维护了售方，此处分解购方的
					/*
					 * 购方
					 */
					String sql1 = "select be.ecounitid,1/(select count(be.ecounitid) from  BA_EconomicUnit be where be.participantid=(select purchaser from  co_contractbaseinfo cc where cc.marketid='"+marketid+"' and cc.contractid='"+contractid+"')) from  BA_EconomicUnit be where be.participantid=(select purchaser from  co_contractbaseinfo cc where cc.marketid='"+marketid+"' and cc.contractid='"+contractid+"')";
					List list1= hibernateDao.executeSqlQuery(sql1);
					for (int i = 0; i < list1.size(); i++) {
						Object[] obj1 = (Object[])list1.get(i);
						String ecounitid = obj1[0].toString();
						float fene = Float.parseFloat(obj1[1].toString());
						String sql2 = "insert into  CO_ContractBaseInfo_Unit1(guid, contractid, purchasertype, unitid, netqty, genqty, timeno, startdate, enddate, period, marketid) values((select sys_guid() from dual),'"+contractid+"',1,'"+ecounitid+"',"+fene+"*nvl((select PURCHASERENERGY from  co_contractbaseinfo where marketID='"+marketid+"'and contractID='"+contractid+"'),(select cc.contractqty from  co_contractbaseinfo cc where cc.marketid='"+marketid+"' and cc.contractid='"+contractid+"')),"+fene+"*(select PURCHASERGEN from  co_contractbaseinfo where marketID='"+marketid+"'and contractID='"+contractid+"'),1,(select distinct ce.contractstartdate from  co_contractbaseinfo ce where contractid='"+contractid+"' and marketID='"+marketid+"'),(select distinct ce.contractenddate from  co_contractbaseinfo ce where contractid='"+contractid+"' and marketID='"+marketid+"' ),1,'"+marketid+"')";//插入
						sqllist.add(sql2);
					}
				}
			}
			/////////////////////
			for (int i = 0; i < list.size(); i++) {//循环角色
				Object obj = (Object) list.get(i);
				String contractrole = obj.toString();//交易角色
				//查询经济机组，经济机组的权容；没有的经济机组维护了机组的权容是否可能为空？
				String sql1 = "select be.ecounitid,nvl(ecoUnitCapacity,0),case when (select sum(nvl(ecoUnitCapacity,0))  from  BA_EconomicUnit be where be.generatorid in (select ccc.participantid from  co_contractaccessory ccc where ccc.contractid='"+contractid+"' and ccc.contractrole='"+contractrole+"') )<>0 then nvl(ecoUnitCapacity,0)/(select sum(nvl(ecoUnitCapacity,0))  from  BA_EconomicUnit be where be.generatorid in (select ccc.participantid from  co_contractaccessory ccc where ccc.contractid='"+contractid+"' and ccc.contractrole='"+contractrole+"') ) ELSE 1/(SELECT COUNT(DISTINCT BE.ECOUNITID) FROM BA_ECONOMICUNIT BE WHERE BE.generatorid IN (select PARTICIPANTID from  Co_Contractaccessory cy where cy.contractid='"+contractid+"') ) END c3 from  BA_EconomicUnit be where be.generatorid in (select ccc.participantid from  co_contractaccessory ccc where ccc.contractid='"+contractid+"' and ccc.contractrole='"+contractrole+"')";
				List list1 = hibernateDao.executeSqlQuery(sql1);
				for (int j = 0; j < list1.size(); j++) {//循环经济机组
					Object[] obj1 = (Object[]) list1.get(i);
					String ecounitid = obj1[0].toString();//经济机组id
					float fene = Float.parseFloat(obj1[2].toString());//份额
					if (contractrole.equals("1")) {
						String sql4 = "insert into  CO_ContractBaseInfo_Unit1(guid, contractid, purchasertype, unitid, netqty, genqty, timeno, startdate, enddate, period, marketid) values((select sys_guid() from dual),'"+contractid+"',1,'"+ecounitid+"',"+fene+"*nvl((select PURCHASERENERGY from  co_contractbaseinfo where marketID='"+marketid+"'and contractID='"+contractid+"'),(select cc.contractqty from  co_contractbaseinfo cc where cc.marketid='"+marketid+"' and cc.contractid='"+contractid+"')),"+fene+"*(select PURCHASERGEN from  co_contractbaseinfo where marketID='"+marketid+"'and contractID='"+contractid+"'),1,(select distinct ce.contractstartdate from  co_contractbaseinfo ce where contractid='"+contractid+"' and marketID='"+marketid+"'),(select distinct ce.contractenddate from  co_contractbaseinfo ce where contractid='"+contractid+"' and marketID='"+marketid+"' ),1,'"+marketid+"')";//插入
						sqllist.add(sql4);
					}else if (contractrole.equals("2")) {
						String sql4 = "insert into  CO_ContractBaseInfo_Unit1(guid, contractid, purchasertype, unitid, netqty, genqty, timeno, startdate, enddate, period, marketid) values((select sys_guid() from dual),'"+contractid+"',1,'"+ecounitid+"',"+fene+"*nvl((select PURCHASERENERGY from  co_contractbaseinfo where marketID='"+marketid+"'and contractID='"+contractid+"'),(select cc.contractqty from  co_contractbaseinfo cc where cc.marketid='"+marketid+"' and cc.contractid='"+contractid+"')),"+fene+"*(select PURCHASERGEN from  co_contractbaseinfo where marketID='"+marketid+"'and contractID='"+contractid+"'),1,(select distinct ce.contractstartdate from  co_contractbaseinfo ce where contractid='"+contractid+"' and marketID='"+marketid+"'),(select distinct ce.contractenddate from  co_contractbaseinfo ce where contractid='"+contractid+"' and marketID='"+marketid+"' ),1,'"+marketid+"')";//插入
						sqllist.add(sql4);
					}
				}
				
			}
		}else if(n==4){//从表没有数据,分主表数据，时段为1；没有维护机组
				/*
				 * 购方
				 */
				String sql1 = "select be.ecounitid,1/(select count(be.ecounitid) from  BA_EconomicUnit be where be.participantid=(select purchaser from  co_contractbaseinfo cc where cc.marketid='"+marketid+"' and cc.contractid='"+contractid+"')) from  BA_EconomicUnit be where be.participantid=(select purchaser from  co_contractbaseinfo cc where cc.marketid='"+marketid+"' and cc.contractid='"+contractid+"')";
				List list1= hibernateDao.executeSqlQuery(sql1);
				for (int i = 0; i < list1.size(); i++) {
					Object[] obj1 = (Object[])list1.get(i);
					String ecounitid = obj1[0].toString();
					float fene = Float.parseFloat(obj1[1].toString());
					String sql2 = "insert into  CO_ContractBaseInfo_Unit1(guid, contractid, purchasertype, unitid, netqty, genqty, timeno, startdate, enddate, period, marketid) values((select sys_guid() from dual),'"+contractid+"',1,'"+ecounitid+"',"+fene+"*nvl((select PURCHASERENERGY from  co_contractbaseinfo where marketID='"+marketid+"'and contractID='"+contractid+"'),(select cc.contractqty from  co_contractbaseinfo cc where cc.marketid='"+marketid+"' and cc.contractid='"+contractid+"')),"+fene+"*(select PURCHASERGEN from  co_contractbaseinfo where marketID='"+marketid+"'and contractID='"+contractid+"'),1,(select distinct ce.contractstartdate from  co_contractbaseinfo ce where contractid='"+contractid+"' and marketID='"+marketid+"'),(select distinct ce.contractenddate from  co_contractbaseinfo ce where contractid='"+contractid+"' and marketID='"+marketid+"' ),1,'"+marketid+"')";//插入
					sqllist.add(sql2);
				}
				/*
				 * 售方
				 */
				String sql3 = "select be.ecounitid,1/(select count(be.ecounitid) from  BA_EconomicUnit be where be.participantid=(select seller from  co_contractbaseinfo cc where cc.marketid='"+marketid+"' and cc.contractid='"+contractid+"')) from  BA_EconomicUnit be where be.participantid=(select seller from  co_contractbaseinfo cc where cc.marketid='"+marketid+"' and cc.contractid='"+contractid+"')";
				List list3= hibernateDao.executeSqlQuery(sql3);
				for (int j = 0; j < list3.size(); j++) {
					Object[] obj1 = (Object[])list3.get(j);
					String ecounitid = obj1[0].toString();
					float fene = Float.parseFloat(obj1[1].toString());
					String sql4 = "insert into  CO_ContractBaseInfo_Unit1(guid, contractid, purchasertype, unitid, netqty, genqty, timeno, startdate, enddate, period, marketid) values((select sys_guid() from dual),'"+contractid+"',2,'"+ecounitid+"',"+fene+"*nvl((select SELLERENERGY from  co_contractbaseinfo where marketID='"+marketid+"'and contractID='"+contractid+"'),(select cc.contractqty from  co_contractbaseinfo cc where cc.marketid='"+marketid+"' and cc.contractid='"+contractid+"')),"+fene+"*(select SELLERGEN from  co_contractbaseinfo where marketID='"+marketid+"'and contractID='"+contractid+"'),1,(select distinct ce.contractstartdate from  co_contractbaseinfo ce where contractid='"+contractid+"' and marketID='"+marketid+"'),(select distinct ce.contractenddate from  co_contractbaseinfo ce where contractid='"+contractid+"' and marketID='"+marketid+"' ),1,'"+marketid+"')";//插入
					sqllist.add(sql4);
				}
			}
			
		return sqllist;
	}

	
	/**
	 * 保存更新
	 * 
	 * @param list
	 */
	 public List<CoContractbackupinfoVO> saveOrUpdate(List<Map> list) {
		 
		List<CoContractbackupinfoVO> voList = new ArrayList<CoContractbackupinfoVO>();
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
			String poName = CoContractbackupinfo.class.getName();
			if(map.containsKey("guid")){
				String id = (String)map.get("guid");
				CoContractbackupinfoVO vo = update(map,poName,id);
				voList.add(vo);
			}else{
				CoContractbackupinfoVO coContractbackupinfoVO = save(map);
				voList.add(coContractbackupinfoVO);
			}
		}
		return voList;
	 }
	 
	//保存记录
	private CoContractbackupinfoVO save(Map map) {
		
		CoContractbackupinfoVO coContractbackupinfoVo = new CoContractbackupinfoVO();
		try {
			BeanUtils.populate(coContractbackupinfoVo, map);
		} catch (Exception e) {
		}
		CoContractbackupinfo coContractbackupinfo = CoContractbackupinfoTransfer.toPO(coContractbackupinfoVo);
		hibernateDao.saveOrUpdateObject(coContractbackupinfo);
		coContractbackupinfoVo = CoContractbackupinfoTransfer.toVO(coContractbackupinfo);
		if(map.containsKey("mxVirtualId")){
			coContractbackupinfoVo.setMxVirtualId(String.valueOf(map.get("mxVirtualId")));
		}
		return coContractbackupinfoVo;
	}
	
	//更新记录
	private CoContractbackupinfoVO update(Map<String, ?> map,String poName,String id) {
		
		CoContractbackupinfoVO coContractbackupinfoVo = new CoContractbackupinfoVO();
		//更新操作
		try {
			BeanUtils.populate(coContractbackupinfoVo, map);
		} catch (Exception e) {
		}
		Object[] objArray = CrudUtils.generateHql(CoContractbackupinfo.class, map, "guid");
		hibernateDao.update((String) objArray[0], (Object[]) objArray[1]);
		return coContractbackupinfoVo;
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
			hibernateDao.update("delete from " + CoContractbackupinfo.class.getName() + " where guid = ?" ,new Object[]{id});
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
		List<CoContractbackupinfo> result = null;
		int count = 0;
		qc.addFrom(CoContractbackupinfo.class);
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
		List<QueryFilter> wheres = queryCondition.getQueryFilter(CoContractbackupinfoVO.class);
		if (wheres != null && wheres.size() > 0) {
			CrudUtils.addQCWhere(qc, wheres, CoContractbackupinfo.class.getName());

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
	    return dicts;
	}
	// 从属性文件中查询字典
	private DicItems translateFromFile(String fieldName,String marketId) {
				List<Map<String, String>> list = new ArrayList();
				Object[] obj = {};
				StringBuffer sql = new StringBuffer();
				obj = new Object[]{"1",marketId};
				sql.append("SELECT T.PARTICIPANTID,T.PARTICIPANTNAME FROM BA_MARKETPARTICIPANT T WHERE T.PARTICIPANTTYPE=? AND T.MARKETID=?");
				
				List tlist = hibernateDao.executeSqlQuery(sql.toString(),obj);
				for(int i=0;i<tlist.size();i++){
					Map<String, String> map = new HashMap();
					Object[] robj = (Object[])tlist.get(i);
					String value = robj[0]==null?"":robj[0].toString();
					String name = robj[1]==null?"":robj[1].toString();
					map.put("value", value);
					map.put("text", name);
//					map.put(value, name);
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
