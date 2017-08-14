package com.sgcc.dlsc.statesanalysis.radarscan.bizc;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.sgcc.dlsc.commons.bean.FCcategories;
import com.sgcc.dlsc.commons.bean.FCcategory;
import com.sgcc.dlsc.commons.bean.FCchart;
import com.sgcc.dlsc.commons.bean.FCdataset;
import com.sgcc.dlsc.commons.bean.FCset;
import com.sgcc.dlsc.commons.util.DateUtil;
import com.sgcc.dlsc.commons.util.ToolsSys;
import com.sgcc.dlsc.contractManage.CoContractFjAct.vo.CoContractaffixinfoTransfer;
import com.sgcc.dlsc.contractManage.CoContractFjAct.vo.CoContractaffixinfoVO;
import com.sgcc.dlsc.contractManage.coContractbaseinfoV2Act.vo.CoContractbaseinfoVO;
import com.sgcc.dlsc.entity.po.CoContractaffixinfo;
import com.sgcc.uap.bizc.sysbizc.datadictionary.IDataDictionaryBizC;
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
 * @author Administrator 
 * 
 */
public class RadarScanBizc implements IRadarScanBizc{

	@Autowired
	private IHibernateDao hibernateDao;
	
	public String getChartData(String threshold,String period,String marketId){
		FCchart fcchart=new FCchart();
//		fcchart.setNumberSuffix("个");
		fcchart.setUseRoundEdges("1");
		fcchart.setShowBorder("0");
		fcchart.setBgColor("FFFFFF,FFFFFF");
		List list = getStatics(threshold,period,marketId).getItems();
		if(list!=null){
			CoContractbaseinfoVO vo = (CoContractbaseinfoVO)list.get(0);
			FCset set1=new FCset();
			set1.setValue(vo.getCol0());
			set1.setLabel("即将执行的合同");
			FCset set2=new FCset();
			set2.setValue(vo.getCol1());
			set2.setLabel("即将到期的合同");
			FCset set3=new FCset();
			set3.setValue(vo.getCol2());
			set3.setLabel("新增的合同");
			FCset set4=new FCset();
			set4.setValue(vo.getCol3());
			set4.setLabel("变更的合同");
			FCset set5=new FCset();
			set5.setValue(vo.getCol4());
			set5.setLabel("终止的合同");
			FCset set6=new FCset();
			set6.setValue(vo.getCol5());
			set6.setLabel("统调火电完成率偏高的合同");
			FCset set7=new FCset();
			set7.setValue(vo.getCol6());
			set7.setLabel("统调火电完成率偏低的合同");
			return fcchart.toXml(set1.toXml()+set2.toXml()+set3.toXml()+set4.toXml()+set5.toXml()+set6.toXml()+set7.toXml());
//			return "<chart yAxisName='Sales Figure' caption='Top 5 Sales Person' numberPrefix='$' useRoundEdges='1' bgColor='FFFFFF,FFFFFF' showBorder='0'>  <set label='Alex' value='25000'  /> <set label='Mark' value='35000' /> <set label='David' value='42300' />  <set label='Graham' value='35300' /> <set label='John' value='31300' /> </chart>";
		}else{
			return "";
		}
		
		
	}

	
 	/**
	 *  雷达扫描统计数量
	 * 
	 * @param queryCondition
	 * @return
	 */
	public QueryResultObject getStatics(String threshold,String period,String marketId) {

		QueryResultObject result = new QueryResultObject();//新建返回值对象
		Calendar c=Calendar.getInstance();
		Calendar c2=Calendar.getInstance();
		Date date=new Date();
		c.setTime(date);
		c2.setTime(date);
		if(period.equals("1")){
			c.add(Calendar.DATE,3); 
			c2.add(Calendar.DATE,-3); 
		}else if(period.equals("2")){
			c.add(Calendar.DATE,5); 
			c2.add(Calendar.DATE,-5); 
		}else if(period.equals("3")){
			c.add(Calendar.DATE,7);
			c2.add(Calendar.DATE,-7); 
		}else if(period.equals("4")){
			c.add(Calendar.DATE,14);
			c2.add(Calendar.DATE,-14); 
		}else if(period.equals("5")){
			c.add(Calendar.MONTH,1);
			c2.add(Calendar.MONTH,-1);
		}
//		Date startdate=new Date();
		String startdate = DateUtil.getUtilDateString(new Date(), "yyyy-MM-dd");
//		Date enddate=c.getTime();
		String enddate = DateUtil.getUtilDateString(c.getTime(), "yyyy-MM-dd");
		String end2date = DateUtil.getUtilDateString(c2.getTime(), "yyyy-MM-dd");
		StringBuffer sql = new StringBuffer();
//		sql.append(" select  ");
//		sql.append(" (select count(*) from co_contractbaseinfo t where t.marketid=? and t.contractstartdate >= to_date(?,'yyyy-mm-dd') and t.contractstartdate <= to_date(?,'yyyy-mm-dd')) col1, ");
//		sql.append(" (select count(*) from co_contractbaseinfo t where t.marketid=? and t.contractenddate > = to_date(?,'yyyy-mm-dd') and t.contractenddate <= to_date(?,'yyyy-mm-dd')) col2, ");
//		sql.append(" (select count(*) from co_contractbaseinfo t where t.marketid=? and t.version='V1' and t.updatetime <=to_date(?,'yyyy-mm-dd') and t.updatetime >=to_date(?,'yyyy-mm-dd')) col3, ");
//		sql.append(" (select count(*) from co_contractbaseinfo t where t.marketid=? and t.version!='V1' and t.updatetime <=to_date(?,'yyyy-mm-dd') and t.updatetime >=to_date(?,'yyyy-mm-dd')) col4, ");
//		sql.append(" (select count(*) from co_contractbaseinfo t where t.marketid=? and t.isdelete = '1' and t.updatetime <=to_date(?,'yyyy-mm-dd') and t.updatetime >=to_date(?,'yyyy-mm-dd')) col5, ");
//		sql.append(" (select count(distinct t.contractid) from co_contractbaseinfo t, ba_marketparticipant m, ba_generator g where t.seller = m.participantid and m.participantid = g.plantid and m.participanttype = '2' and g.generatortype = '0' and t.marketid = ? and (decode((select sum(contractqty) from co_contractbaseinfo where contractid = t.contractid),0,0,(select sum(energy_t) from se_result_n_m where contractid = t.contractid) / (select sum(contractqty) from co_contractbaseinfo where contractid = t.contractid)) - decode((select sum(contractqty) from co_contractbaseinfo),0,0,(select sum(energy_t) from se_result_n_m) / (select sum(contractqty) from co_contractbaseinfo))) > ?) col6, ");
//		sql.append(" (select count(distinct t.contractid) from co_contractbaseinfo t, ba_marketparticipant m, ba_generator g where t.seller = m.participantid and m.participantid = g.plantid and m.participanttype = '2' and g.generatortype = '0' and t.marketid = ? and (decode((select sum(contractqty) from co_contractbaseinfo where contractid = t.contractid),0,0,(select sum(energy_t) from se_result_n_m where contractid = t.contractid) / (select sum(contractqty) from co_contractbaseinfo where contractid = t.contractid)) - decode((select sum(contractqty) from co_contractbaseinfo),0,0,(select sum(energy_t) from se_result_n_m) / (select sum(contractqty) from co_contractbaseinfo))) < ?) col7 ");
//		sql.append(" from dual ");
		sql.append("	select                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       ")
		.append("	 (select count(*) from co_contractbaseinfo t left join co_contracttypeinfo s on s.contracttypeid = t.contracttype where t.contractcyc=3 and (s.contracttypeid='C4BB2F9D-1C3F-D011-7460-6F5390E' or s.supertypeid='C4BB2F9D-1C3F-D011-7460-6F5390E') and t.marketid=? and t.contractstartdate >= to_date(?,'yyyy-mm-dd') and t.contractstartdate <= to_date(?,'yyyy-mm-dd')) col1,                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                ")
		.append("	 (select count(*) from co_contractbaseinfo t left join co_contracttypeinfo s on s.contracttypeid = t.contracttype where t.contractcyc=3 and (s.contracttypeid='C4BB2F9D-1C3F-D011-7460-6F5390E' or s.supertypeid='C4BB2F9D-1C3F-D011-7460-6F5390E') and t.marketid=? and t.contractenddate > = to_date(?,'yyyy-mm-dd') and t.contractenddate <= to_date(?,'yyyy-mm-dd')) col2,                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   ")
		.append("	 (select count(*) from co_contractbaseinfo t left join co_contracttypeinfo s on s.contracttypeid = t.contracttype where t.contractcyc=3 and (s.contracttypeid='C4BB2F9D-1C3F-D011-7460-6F5390E' or s.supertypeid='C4BB2F9D-1C3F-D011-7460-6F5390E') and t.marketid=? and t.version='V1' and t.updatetime <=to_date(?,'yyyy-mm-dd') and t.updatetime >=to_date(?,'yyyy-mm-dd')) col3,                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             ")
		.append("	 (select count(*) from co_contractbaseinfo t left join co_contracttypeinfo s on s.contracttypeid = t.contracttype where t.contractcyc=3 and (s.contracttypeid='C4BB2F9D-1C3F-D011-7460-6F5390E' or s.supertypeid='C4BB2F9D-1C3F-D011-7460-6F5390E') and t.marketid=? and t.version!='V1' and t.updatetime <=to_date(?,'yyyy-mm-dd') and t.updatetime >=to_date(?,'yyyy-mm-dd')) col4,                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ")
		.append("	 (select count(*) from co_contractbaseinfo t left join co_contracttypeinfo s on s.contracttypeid = t.contracttype where t.contractcyc=3 and (s.contracttypeid='C4BB2F9D-1C3F-D011-7460-6F5390E' or s.supertypeid='C4BB2F9D-1C3F-D011-7460-6F5390E') and t.marketid=? and t.isdelete = '1' and t.updatetime <=to_date(?,'yyyy-mm-dd') and t.updatetime >=to_date(?,'yyyy-mm-dd')) col5,                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           ")
		.append("	 (select count(distinct t.contractid) from co_contractbaseinfo t, ba_marketparticipant m, ba_generator g,co_contracttypeinfo s where t.seller = m.participantid and m.participantid = g.plantid and s.contracttypeid = t.contracttype and t.contractcyc = 3 and (s.contracttypeid='C4BB2F9D-1C3F-D011-7460-6F5390E' or s.supertypeid='C4BB2F9D-1C3F-D011-7460-6F5390E') and m.participanttype = '2' and g.generatortype = '0' and t.marketid = ? and (decode((select sum(contractqty) from co_contractbaseinfo where contractid = t.contractid),0,0,(select sum(energy_t) from se_result_n_m where contractid = t.contractid) / (select sum(contractqty) from co_contractbaseinfo where contractid = t.contractid)) - decode((select sum(contractqty) from co_contractbaseinfo),0,0,(select sum(energy_t) from se_result_n_m) / (select sum(contractqty) from co_contractbaseinfo))) > ?) col6,  ")
		.append("	 (select count(distinct t.contractid) from co_contractbaseinfo t, ba_marketparticipant m, ba_generator g,co_contracttypeinfo s where t.seller = m.participantid and m.participantid = g.plantid and s.contracttypeid = t.contracttype and t.contractcyc = 3 and (s.contracttypeid='C4BB2F9D-1C3F-D011-7460-6F5390E' or s.supertypeid='C4BB2F9D-1C3F-D011-7460-6F5390E') and m.participanttype = '2' and g.generatortype = '0' and t.marketid = ? and (decode((select sum(contractqty) from co_contractbaseinfo where contractid = t.contractid),0,0,(select sum(energy_t) from se_result_n_m where contractid = t.contractid) / (select sum(contractqty) from co_contractbaseinfo where contractid = t.contractid)) - decode((select sum(contractqty) from co_contractbaseinfo),0,0,(select sum(energy_t) from se_result_n_m) / (select sum(contractqty) from co_contractbaseinfo))) < ?) col7   ")
		.append("	 from dual                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       ");

		List list = hibernateDao.executeSqlQuery(sql.toString(),new Object[]{marketId,startdate,enddate,marketId,startdate,enddate,marketId,startdate,end2date,marketId,startdate,end2date,marketId,startdate,end2date,marketId,threshold,marketId,"-"+threshold});	//查询数据
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
 			vo.setCol6(objs[6] == null ? "" : objs[6].toString());
 			voList.add(vo);	//把数据添加到结果集中
 		}
		result.setItems(voList);	//给返回对象赋分页查询数据
		
		return result;	//返回返回值
		
	}
	
	/**
	 * 雷达扫描按阀值过滤查询
	 * 
	 * @param queryCondition
	 * @return
	 */
	public QueryResultObject query(int pageIndex,int pageSize,String threshold,String period,String marketId) {

		QueryResultObject result = new QueryResultObject();//新建返回值对象
		int size = 0;	//定义数据条数
		
		StringBuffer sql = new StringBuffer();
		sql.append(" select t.contractname, CCT.Typename,BM.PARTICIPANTNAME purname,BMM.PARTICIPANTNAME selname,t.contractqty from co_contractbaseinfo t ");
		sql.append(" LEFT JOIN  CO_CONTRACTTYPEINFO CCT ON t.CONTRACTTYPE = CCT.CONTRACTTYPEID ");
		sql.append(" LEFT JOIN  BA_MARKETPARTICIPANT BM ON t.PURCHASER = BM.PARTICIPANTID ");
		sql.append(" LEFT JOIN  BA_MARKETPARTICIPANT BMM ON t.SELLER = BMM.PARTICIPANTID ");
		sql.append(" and t.contractcyc = 3 and (cct.contracttypeid = 'C4BB2F9D-1C3F-D011-7460-6F5390E' or cct.supertypeid = 'C4BB2F9D-1C3F-D011-7460-6F5390E') ");
		sql.append(" where t.marketid = ? order by t.contractname ");
		String sql2 = "SELECT COUNT(*) C FROM ( "+ sql.toString() + " ) A ";		//拼接查询数据条数的sql语句
		List list = hibernateDao.executeSqlQuery(sql2,new Object[]{marketId});
		if(list.size() > 0){	//如果有查询结果，给数据条数变量赋值
			size = Integer.parseInt(list.get(0).toString());
		}
		result.setItemCount(size);	//给返回对象赋数据的数量
		list = hibernateDao.executeSqlQuery(sql.toString(),new Object[]{marketId},pageIndex,pageSize);	//查询分页的数据
		List<CoContractbaseinfoVO> voList = new ArrayList<CoContractbaseinfoVO>();		//返回的数据结果集
		for(int i = 0;i < list.size();i++){	//遍历要展示的数据
 			Object[] objs = (Object[])list.get(i);	//得到第i条数据
 			CoContractbaseinfoVO vo = new CoContractbaseinfoVO();	//数据结果集中的一条数据
 			//给每个变量赋值
 			vo.setContractname(objs[0] == null ? "" : objs[0].toString());
 			vo.setContracttype(objs[1] == null ? "" : objs[1].toString());
 			vo.setPurchaser(objs[2] == null ? "" : objs[2].toString());
 			vo.setSeller(objs[3] == null ? "" : objs[3].toString());
 			vo.setContractqty(objs[4] == null ? null : new BigDecimal(objs[4].toString()));
 			voList.add(vo);	//把数据添加到结果集中
 		}
		result.setItems(voList);	//给返回对象赋分页查询数据
		
		return result;	//返回返回值
		
		
	}
	
	public void setHibernateDao(IHibernateDao hibernateDao) {
		this.hibernateDao = hibernateDao;
	}
	
	
	
}
