package com.sgcc.dlsc.contractManage.contResoByMonth.bizc;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.sgcc.dlsc.commons.bean.VIParamBean;
import com.sgcc.dlsc.commons.bean.VIReturnBean;
import com.sgcc.dlsc.commons.bizc.IComm_VerticalIntegration_Bizc;
import com.sgcc.dlsc.commons.util.DateUtil;
import com.sgcc.dlsc.commons.util.UserInfoUtil;
import com.sgcc.dlsc.constants.DataConstants;
import com.sgcc.dlsc.contractManage.COContractEnergyInfo.vo.CoContractenergyinfoTransfer;
import com.sgcc.dlsc.entity.po.BaGencode;
import com.sgcc.dlsc.entity.po.CoContractbackupinfo;
import com.sgcc.dlsc.entity.po.CoContractenergyinfo;
import com.sgcc.dlsc.entity.po.CoContracttypeinfo;
import com.sgcc.dlsc.entity.po.CoContransenergyinfo;
import com.sgcc.isc.core.orm.identity.User;
import com.sgcc.uap.bizc.sysbizc.datadictionary.IDataDictionaryBizC;
import com.sgcc.uap.persistence.IHibernateDao;

public class ContResoByMonthBizc implements IContResoByMonthBizc{
	
	
	
	@Autowired
	private IHibernateDao dao;
	
	@Autowired
	private IDataDictionaryBizC dataDictionaryBizC;
	
	@Autowired
	private IComm_VerticalIntegration_Bizc commVerticalIntegrationBizc;

	public List queryFjfsSelect(String type) {
		Object[] obj = new Object[]{type};
		StringBuffer sql = new StringBuffer();
		sql.append(" select bg.value,bg.name from ba_gencode bg where bg.type=? order by bg.value asc");
		List list = dao.executeSqlQuery(sql.toString(),obj);
		return list;
	}

	public List initHtdl(String contractid) {
		
		return null;
	}

	public Object[] findContractById(String contractid) {
		String sql = "select co.contractid,to_char(co.contractstartdate,'yyyy-mm-dd'),to_char(co.contractenddate,'yyyy-mm-dd'),nvl(co.contractqty,''),nvl(co.sellergen,''),nvl(co.sellerenergy,''),nvl(co.purchasergen,''),nvl(co.purchaserenergy,''),nvl(co.contractenergy,''),co.PAPERCONTRACTCODE,co.CONTRACTNAME ";
		sql += "from co_contractbaseinfo co where co.contractid = '"+contractid+"'";
		List list = dao.executeSqlQuery(sql);
		Object [] obj = null;
		if (list != null && list.size() > 0) {
			obj = (Object[]) list.get(0);
		}
		return obj;
	}

	public boolean isOnlyOper(String contractid) {
		boolean isok = false;
		String sql = "select* from co_contractenergyinfo co where ";
		sql += "co.contractid = '"+contractid+"' and co.whereinsert = '0'";
		
		String sql2 = "select* from co_contractenergyinfo co where ";
		sql2 += "co.contractid = '"+contractid+"' and co.whereinsert = '0'";
		
		List list = dao.executeSqlQuery(sql);
		List list2 =dao.executeSqlQuery(sql2);
		
		if((list != null && list.size() > 0) || (list2.size() == 0)){
			isok = true;//标识在当前界面中维护过，可以编辑
		}
		return isok;
	}
	
	public List getDataList(String contractid,String startdate,String enddate){
		String sql = "select cc.contractid,cc.startdate,cc.enddate,cc.sellergen,cc.sellerenergy,cc.purchasergen,cc.purchaserenergy,cc.energy from co_contractenergyinfo cc where cc.contractid='"+contractid+"' and cc.startdate=to_date('"+startdate+"','yyyy-mm-dd') and cc.enddate=to_date('"+enddate+"','yyyy-mm-dd') and cc.whereinsert = '0'";
		return dao.executeSqlQuery(sql);
	}

	public List getSellergenCalendarList(String contractid, int contractrole) {
		String sql = "select uc.guid,uc.unitid,to_char(uc.stratdate,'yyyy-mm-dd'),to_char(uc.enddate,'yyyy-mm-dd') from unitcheck uc left join co_contractaccessory cca on uc.unitid=cca.unitid  where cca.contractid='"+contractid+"' and cca.contractrole="+contractrole+"  ";
		return dao.executeSqlQuery(sql);
	}

	public int findUnitNum(String contractid, int contractrole) {
		int num=0;
//		String sql = "select nvl((select count(unitid) from co_contractaccessory ccc  where  ccc.contractid='"+contractid+"' and ccc.contractrole="+contractrole+" group by unitid),0) from dual";
		String sql = "select nvl((select count(unitid) from co_contractaccessory ccc  where  ccc.contractid='"+contractid+"' and ccc.contractrole="+contractrole+"),0) from dual";
		List list = dao.executeSqlQuery(sql);
		if(list != null && list.size() > 0){
			Object obj = (Object) list.get(0);
			if(obj!=null){
				num =  Integer.parseInt(obj.toString());
			} 
		}
		return num;
	}

	public List getEnergyFSList() {
		List<BaGencode> ccelist = dao.findAll("from "+BaGencode.class.getName()+ " where type = ? ",new Object[]{"95"});//@查询输电方
		return ccelist;
	}

	public List findOldInfo(String contractid, String startdate, String enddate) {
		String hql = ("from "+CoContractenergyinfo.class.getName()+"  WHERE contractid=? AND startdate=TO_DATE(?,'YYYY-MM-DD') AND enddate=TO_DATE(?,'YYYY-MM-DD') and whereinsert ='0'");
		return dao.findAll(hql, new Object[]{contractid,startdate,enddate});
	
	}

	public int updateBySqlList(List<String>updateList, List<CoContractenergyinfo>insertList , List<CoContractenergyinfo>insertList2){
		int updateNum = 0;
		int insertNum = 0;
		if(updateList.size()!=0){
			updateNum = updateBySqllist(updateList);
		}
		if(insertList.size()!=0){
			insertNum = saveBySqllist(insertList);
		}
		
		saveBySqllist2(insertList2);
		return updateNum+insertNum;
	}
	
	public String  getFlag(String contractid,Date startdate,Date enddate){
		
		String guid = "";
		StringBuffer sql = new StringBuffer();
		String sdate = DateUtil.getUtilDateString(startdate,"yyyy-MM-dd");
		String edate = DateUtil.getUtilDateString(enddate,"yyyy-MM-dd");
		sql.append("select t.guid from CO_ContractEnergyInfo t where t.qtytype = '1' and WHEREINSERT='1' and t.contractid = ? and t.startdate=to_date(?,'YYYY-MM-DD') and t.enddate = to_date(?,'YYYY-MM-DD') ");
		List list = dao.executeSqlQuery(sql.toString(),new Object[]{contractid,sdate,edate});
		if(list!=null&&list.size()>0) {
			guid = list.get(0).toString();
		}
		return guid;
	}
	
	//同步保存分段电量
	public void saveBySqllist2(List<CoContractenergyinfo> insertList2){
		if(insertList2!=null&&insertList2.size()>0){
			Integer timeno = 1;
			for(CoContractenergyinfo co:insertList2){
				CoContractenergyinfo coContractenergyinfo = new CoContractenergyinfo();
				String guid = getFlag(co.getContractid(),co.getStartdate(),co.getEnddate());
				if(!guid.equals("")){
					coContractenergyinfo.setGuid(guid);
				}
				coContractenergyinfo.setContractid(co.getContractid());
				coContractenergyinfo.setEnergy(co.getEnergy());
				coContractenergyinfo.setSellerenergy(co.getSellerenergy());
				coContractenergyinfo.setSellergen(co.getSellergen());
				coContractenergyinfo.setPurchaserenergy(co.getPurchaserenergy());
				coContractenergyinfo.setPurchasergen(co.getPurchasergen());
				coContractenergyinfo.setStartdate(co.getStartdate());
				coContractenergyinfo.setEnddate(co.getEnddate());
				coContractenergyinfo.setWhereinsert("1");
				coContractenergyinfo.setTimeno(new BigDecimal(timeno));
				coContractenergyinfo.setQtytype(new BigDecimal(1));
				coContractenergyinfo.setPeriod(new BigDecimal(1));
				dao.saveOrUpdateObject(coContractenergyinfo);
				timeno = timeno+1;
			}
		}
	}
	
	public void  zxcs(HttpServletRequest request,String contractid){
		//纵向传输
		String marketId;
		VIParamBean vip = new VIParamBean();
		try {
			marketId = UserInfoUtil.getLoginUserMarket(request);
			vip.setMarketid_source(marketId);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		vip.setMarketid_target(DataConstants.marketid_target);//目的场景
		vip.setTableName("co_contractenergyinfo");//表名
		vip.setWhereCondtion("contractid='"+contractid+"' and whereinsert='0' ");
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
	
	public int updateBySqllist(List<String> sqllist){
		int tenNum=0;
		if(sqllist==null){
			return tenNum;
		}else{
			for(int i=0;i<sqllist.size();i++){
				tenNum+=dao.executeSqlUpdate(sqllist.get(i));
			}
			return tenNum;
		}
	}
	public int saveBySqllist(List<CoContractenergyinfo> sqllist){
		int tenNum=0;
		if(sqllist==null){
			return tenNum;
		}else{
			dao.saveAllObject(sqllist);
			return sqllist.size();
		}
	}

	public boolean delHtdl(String contractid) {
		boolean isok = false;
		String sql = "delete from co_contractenergyinfo co where co.contractid='"+contractid+"'"+" and co.whereinsert = '0'";
		int size =dao.executeSqlUpdate(sql);
		if(size!=0){
			isok = true;
		}
		return isok;
	}

	public String checkCoType(String coType) {
		String sql="select t.contracttypeid,t.supertypeid,t.typename from co_contracttypeinfo t start with t.contracttypeid =? connect by t.contracttypeid = prior t.supertypeid and t.supertypeid is null";
		List tlist= dao.executeSqlQuery(sql.toString(), new Object[]{coType});
		for(int i=0;i<tlist.size();i++){
			Object [] tenObj=(Object[]) tlist.get(i);
			if("4DC24C12-413C-5A67-BEBE-AB07C0C".equals(tenObj[0])){
				return "1";
			}
		}
		return "0";
	}
	
	public String checkCoTypeTwo(String contractid) {
		String sql="select t.contracttypeid,t.supertypeid,t.typename from co_contracttypeinfo t start with t.contracttypeid =(select contracttype from co_contractbaseinfo   where contractid = ?) connect by t.contracttypeid = prior t.supertypeid and t.supertypeid is null";
		List tlist= dao.executeSqlQuery(sql.toString(), new Object[]{contractid});
		for(int i=0;i<tlist.size();i++){
			Object [] tenObj=(Object[]) tlist.get(i);
			if("4DC24C12-413C-5A67-BEBE-AB07C0C".equals(tenObj[0])){
				return "1";
			}
		}
		return "0";
	}

	public void setCommVerticalIntegrationBizc(
			IComm_VerticalIntegration_Bizc commVerticalIntegrationBizc) {
		this.commVerticalIntegrationBizc = commVerticalIntegrationBizc;
	}
	
}
