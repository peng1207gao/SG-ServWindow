package com.sgcc.dlsc.contractManage.cocontractbaseInfoV2.bizc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.sgcc.uap.persistence.IHibernateDao;
import com.sgcc.uap.persistence.criterion.QueryCriteria;

import com.sgcc.uap.bizc.sysbizc.datadictionary.IDataDictionaryBizC;
import com.sgcc.uap.rest.support.DicItems;

import com.sgcc.uap.rest.support.IDRequestObject;
import com.sgcc.uap.rest.support.QueryFilter;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;
import com.sgcc.uap.rest.utils.CrudUtils;
import com.sgcc.uap.rest.utils.RestUtils;

//引入po,vo,transefer
import com.sgcc.dlsc.entity.po.CoContractbaseinfo;
import com.sgcc.dlsc.commons.bean.FCcategories;
import com.sgcc.dlsc.commons.bean.FCchart;
import com.sgcc.dlsc.commons.bean.FCdataset;
import com.sgcc.dlsc.contractManage.cocontractbaseInfoV2.vo.CoContractbaseinfoTransfer;
import com.sgcc.dlsc.contractManage.cocontractbaseInfoV2.vo.CoContractbaseinfoVO;
/**
 * 用户定义逻辑构件
 * 
 * @author xiabaike 
 * 
 */
public class CocontractbaseInfoV2Bizc implements ICocontractbaseInfoV2Bizc{

	@Autowired
	private IHibernateDao hibernateDao;
	
	@Autowired
	private IDataDictionaryBizC dataDictionaryBizC;
	
	/**
	 * 拼xml
	 * @description 方法描述
	 * @param map
	 * @return
	 * @author liling 
	 * @date 2013-6-20
	 */
	public String getChartsXml(Map map) {
		List list = getChartsList(map);
		String wd = map.get("wd")==null?"":map.get("wd").toString();//维度
		String xml = "";
		String AxisName = "发电集团";
		//if(list.size()>0){
		FCchart chart = new FCchart();
		if("2".equals(wd)){//合计
			chart.setCaption("购电均价日跟踪");
			AxisName = "时间";
		}
		if("1".equals(wd)){//直购电厂
			String name = "集团购电均价";			
			chart.setCaption("发电集团购电均价");
		}
		if("0".equals(wd)){//非直购电厂
			chart.setCaption("非直购电厂购电均价");
		}
		
		chart.setXAxisName(AxisName);
		chart.setYAxisName("电价");
		
		FCcategories categories = new FCcategories();//时间
		categories.addCategoryByDBList(list,0);
		
		FCdataset dataSet1 = new FCdataset();//日购电均价
		dataSet1.setSeriesName("日购电均价");
		dataSet1.setColor("#64288");
		dataSet1.addSetByDBList(list, 1);
					
		FCdataset dataSet2 = new FCdataset();//月累计购电均价
		dataSet2.setSeriesName("月累计购电均价");
		dataSet2.setColor("#9F2F74");
		dataSet2.addSetByDBList(list, 2);
		
		FCdataset dataSet3 = new FCdataset();
		//dataSet3.addSetByDBList(list, 3);
		xml = chart.toXml(categories.toXml() + dataSet1.toXml()+ dataSet2.toXml());
			
		//}
		
		
		return xml;
	}
	
	/**
	 * 获取chartList数据
	 * @description 方法描述
	 * @param map
	 * @return
	 * @author liling 
	 * @date 2013-6-20
	 */
	public List getChartsList(Map map) {
		StringBuffer buf = new StringBuffer();
		String sj = map.get("sj")==null?"":map.get("sj").toString();//时间
		String wd = map.get("wd")==null?"":map.get("wd").toString();//维度
		String paraId = map.get("paraId")==null?"":map.get("paraId").toString();//谈出框传参
		String flg = map.get("flg")==null?"":map.get("flg").toString();//谈出框传参
		String marketId = map.get("marketId")==null?"":map.get("marketId").toString();//场景ID
		
		String[] param = null;
		String sj1 = sj.split("-")[0]+"-"+sj.split("-")[1]+"-01";
		if("".equals(flg)){
			if("2".equals(wd) || "".equals(wd)){				
				param = new String[]{sj1,sj};
				buf.append("SELECT TO_CHAR(MM.QDATE,'MM-DD'), CASE WHEN MM.DEQ = 0 THEN 0 ELSE ROUND(NVL(MM.DFREE / MM.DEQ,'0'),2) END,CASE WHEN MM.MEQ=0 THEN 0 ELSE ROUND(NVL(MM.MFREE/MEQ,'0'),2) END,'' FROM ");
				buf.append("(SELECT SUM(PD.DAYFREE) DFREE ,SUM(PD.DAYEQ) DEQ,SUM(PD.DAYFREE_T_M) MFREE,SUM(PD.DAYEQ_T_M) MEQ, PD.QUERYDATE QDATE FROM PA_SETTLE_DETAIL_D PD WHERE (PD.BUYTYPE = '0' OR PD.BUYTYPE='1') AND (PD.QUERYDATE > = TO_DATE(?,'YYYY-MM-DD') AND PD.QUERYDATE<=TO_DATE(?,'YYYY-MM-DD')) ");
				if(!"".equals(marketId)){
					param = new String[]{sj1,sj,marketId};
					buf.append(" AND PD.MARKETID=? ");
				}
				buf.append(" GROUP BY PD.QUERYDATE ) MM ORDER BY MM.QDATE ");
			}
			else if("1".equals(wd)){//直购	
				param = new String[]{wd,sj1,sj};
				buf.append("SELECT MM.GENNAME, ROUND(MM.DFREE,2), ROUND(MM.MFREE,2),MM.GENID ");
				buf.append("FROM (SELECT CASE WHEN SUM(PD.DAYEQ) = 0 THEN 0 ELSE SUM(PD.DAYFREE) / SUM(PD.DAYEQ)  END DFREE,CASE WHEN SUM(PD.DAYEQ_T_M) = 0 THEN 0 ELSE SUM(PD.DAYFREE_T_M) / SUM(PD.DAYEQ_T_M)  END MFREE,PD.GENGROUPNAME GENNAME,PD.GENGROUPID GENID ");
				buf.append("FROM PA_SETTLE_DETAIL_D PD WHERE PD.BUYTYPE = ? AND (PD.QUERYDATE > = TO_DATE(?,'YYYY-MM-DD') AND PD.QUERYDATE<=TO_DATE(?,'YYYY-MM-DD')) ");
				if(!"".equals(marketId)){
					param = new String[]{wd,sj1,sj,marketId};
					buf.append(" AND PD.MARKETID=? ");
				}
				buf.append(" GROUP BY PD.GENGROUPNAME,PD.GENGROUPID ) MM ORDER BY MM.MFREE");
			}
			else if("0".equals(wd)){//非直购
				param = new String[]{wd,sj1,sj};
				buf.append("SELECT MM.AERA, ROUND(MM.DFREE,2), ROUND(MM.MFREE,2),'' ");
				buf.append("FROM (SELECT CASE WHEN SUM(PD.DAYEQ) = 0 THEN 0 ELSE SUM(PD.DAYFREE) / SUM(PD.DAYEQ)  END DFREE,CASE WHEN SUM(PD.DAYEQ_T_M) = 0 THEN 0 ELSE SUM(PD.DAYFREE_T_M) / SUM(PD.DAYEQ_T_M)  END MFREE,PD.LINKAREA AERA ");
				buf.append("FROM PA_SETTLE_DETAIL_D PD WHERE PD.BUYTYPE = ? AND (PD.QUERYDATE > = TO_DATE(?,'YYYY-MM-DD') AND PD.QUERYDATE<=TO_DATE(?,'YYYY-MM-DD')) ");
				if(!"".equals(marketId)){
					param = new String[]{wd,sj1,sj,marketId};
					buf.append(" AND PD.MARKETID=? ");
				}
				buf.append(" GROUP BY PD.LINKAREA) MM ORDER BY MM.MFREE");
			}
		}
		
		else{
			if("wdjt".equals(flg)){
				param = new String[]{paraId,wd,sj1,sj};
				buf.append("SELECT MM.SNAME, ROUND(MM.DFREE,2), ROUND(MM.MFREE,2),'' ");
				buf.append("FROM (SELECT CASE WHEN SUM(PD.DAYEQ) = 0 THEN 0 ELSE SUM(PD.DAYFREE) / SUM(PD.DAYEQ)  END DFREE,CASE WHEN SUM(PD.DAYEQ_T_M) = 0 THEN 0 ELSE SUM(PD.DAYFREE_T_M) / SUM(PD.DAYEQ_T_M)  END MFREE,PD.STATIONNAME SNAME ");
				buf.append("FROM PA_SETTLE_DETAIL_D PD WHERE PD.GENGROUPID=? AND PD.BUYTYPE = ? AND (PD.QUERYDATE > = TO_DATE(?,'YYYY-MM-DD') AND PD.QUERYDATE<=TO_DATE(?,'YYYY-MM-DD')) ");
				if(!"".equals(marketId)){
					param = new String[]{paraId,wd,sj1,sj,marketId};
					buf.append(" AND PD.MARKETID=? ");
				}
				buf.append(" GROUP BY PD.STATIONID,PD.STATIONNAME ) MM ORDER BY MM.MFREE");
			
			}
			else if("fzg".equals(flg)){
				param = new String[]{paraId,wd,sj1,sj};
				buf.append("SELECT MM.SNAME, ROUND(MM.DFREE,2), ROUND(MM.MFREE,2),'' ");
				buf.append("FROM (SELECT CASE WHEN SUM(PD.DAYEQ) = 0 THEN 0 ELSE SUM(PD.DAYFREE) / SUM(PD.DAYEQ)  END DFREE,CASE WHEN SUM(PD.DAYEQ_T_M) = 0 THEN 0 ELSE SUM(PD.DAYFREE_T_M) / SUM(PD.DAYEQ_T_M)  END MFREE,PD.STATIONNAME SNAME ");
				buf.append("FROM PA_SETTLE_DETAIL_D PD WHERE 1=1 ");
				if("".equals(paraId)){
					buf.append(" AND (PD.LINKAREA=? OR PD.LINKAREA IS NULL) ");
				}
				else{
					buf.append(" AND PD.LINKAREA = ? ");
				}
				buf.append(" AND PD.BUYTYPE = ? AND (PD.QUERYDATE > = TO_DATE(?,'YYYY-MM-DD') AND PD.QUERYDATE<=TO_DATE(?,'YYYY-MM-DD')) ");
				if(!"".equals(marketId)){
					param = new String[]{paraId,wd,sj1,sj,marketId};
					buf.append(" AND PD.MARKETID=? ");
				}
				buf.append(" GROUP BY PD.STATIONID,PD.STATIONNAME ) MM ORDER BY MM.MFREE");
			
			}
		}
		List list = hibernateDao.executeSqlQuery(buf.toString(), param);
		return list;
	}

	
	public List findCoContract1(String marketid, String date) {
		// TODO Auto-generated method stub
		String sql = "select count(*),t.name from (select bg.name, cc.papercontractcode, cc.contracttype from co_contractbaseinfo cc, ";
		sql += " ba_gencode bg where cc.flowflag = bg.value and bg.type = '67' AND cc.papercontractcode IS NOT NULL ";
		sql += " and cc.CONTRACTSTARTDATE <= TO_DATE('"+date+"','YYYY-MM-DD')  and cc.CONTRACTSTARTDATE >= TO_DATE('"+date.substring(0,4)+"-01-01', 'YYYY-MM-DD') ";
		sql += " and cc.isdelete<>1";
		sql += " and cc.marketid in (select bm.marketid from ba_market bm ";
		sql += " connect by prior bm.marketid = bm.parentmarketid start with bm.marketid = '"+marketid+"')";
		sql += " group by bg.name, cc.papercontractcode,cc.contracttype ) t group by t.name";
		return hibernateDao.executeSqlQuery(sql);
	}

	public List findCoContract2(String marketid, String date) {
		// TODO Auto-generated method stub
		String sql = "select count(*),t.name from (select bg.name,cc.papercontractcode from co_contractbaseinfo cc left join ba_gencode bg on cc.contractcyc = bg.value and bg.type = '23' ";
		sql += " where 1=1 and cc.papercontractcode IS NOT NULL";
		sql += " and cc.contracttype in (select ct.contracttypeid  from co_contracttypeinfo ct";
		sql += " connect by prior ct.contracttypeid = ct.supertypeid start with ct.contracttypeid = '9F457805-0846-ABBC-3414-5CC4770' and ct.marketid = '91812')";
		sql += " and cc.CONTRACTSTARTDATE <= TO_DATE('"+date+"','YYYY-MM-DD')  and cc.CONTRACTSTARTDATE >= TO_DATE('"+date.substring(0, 4)+"-01-01', 'YYYY-MM-DD') ";
		sql += "   and cc.isdelete<>1";
		sql += " and cc.marketid in (select bm.marketid from ba_market bm ";
		sql += " connect by prior bm.marketid = bm.parentmarketid start with bm.marketid = '"+marketid+"')";
		sql += " group by bg.name,cc.papercontractcode) t group by t.name";
		return hibernateDao.executeSqlQuery(sql);
	}

	public List findcoContract3(String marketid, String date) {
		// TODO Auto-generated method stub
		String sql = "select count(*) as xx, t.name from (select cc.papercontractcode,ba.name, cc.contractqty  from co_contractbaseinfo  cc left join ba_marketparticipant bmp on cc.SELLER = bmp.participantid left join ba_gencode           ba1 on bmp.participanttype = ba1.value and ba1.type = '38' and ba1.value in (2, 4, 5) left join ba_generator         bg on  bmp.participantid = bg.plantid left join ba_gencode  ba on  bg.generatortype = ba.value  and ba.type = '18'  ";
		sql += " where 1=1 and ba.name is not null ";
		sql += " AND cc.papercontractcode IS NOT NULL";
		sql += " and cc.CONTRACTSTARTDATE <= TO_DATE('"+date+"','YYYY-MM-DD') and cc.CONTRACTSTARTDATE >= TO_DATE('"+date.substring(0, 4)+"-01-01', 'YYYY-MM-DD') ";
		sql += "  and cc.isdelete<>1";
		sql += " and cc.marketid in (select bm.marketid from ba_market bm ";
		sql += " connect by prior bm.marketid = bm.parentmarketid start with bm.marketid = '"+marketid+"')";
		sql += " group by cc.papercontractcode,ba.name,cc.contractqty ) t group by t.name";
		return hibernateDao.executeSqlQuery(sql);
	}

	public List findcoContract4(String marketid, String date) {
		// TODO Auto-generated method stub
		String sql = "select sum(contractqty), t.name from (select cc.papercontractcode,ba.name,sum(cc.contractqty) as contractqty  from co_contractbaseinfo  cc left join ba_marketparticipant bmp on cc.SELLER = bmp.participantid left join ba_gencode           ba1 on bmp.participanttype = ba1.value and ba1.type = '38' and ba1.value in (2, 4, 5) left join ba_generator         bg on  bmp.participantid = bg.plantid left join ba_gencode  ba on  bg.generatortype = ba.value  and ba.type = '18'  ";
		sql += " where 1=1 and ba.name is not null ";
		sql += " AND cc.papercontractcode IS NOT NULL";
		sql += " and cc.CONTRACTSTARTDATE <= TO_DATE('"+date+"','YYYY-MM-DD') and cc.CONTRACTSTARTDATE >= TO_DATE('"+date.substring(0, 4)+"-01-01', 'YYYY-MM-DD') ";
		sql += "  and cc.isdelete<>1";
		sql += " and cc.marketid in (select bm.marketid from ba_market bm ";
		sql += " connect by prior bm.marketid = bm.parentmarketid start with bm.marketid = '"+marketid+"')";
		sql += " group by cc.papercontractcode,ba.name ) t group by t.name";
		return hibernateDao.executeSqlQuery(sql);
	}

	public List findGen(String gen) {
		// TODO Auto-generated method stub
		String sql = "select bg.aliasname from ba_gengroup bg where 1=1";
		if(gen!=null&&!"".equals(gen)){
			sql += " and bg.gengroupid = '"+gen+"'";
		}
		return hibernateDao.executeSqlQuery(sql);
	}

	public List findCoContract5(String marketid, String date) {
		// TODO Auto-generated method stub
		String sql = "select count(*),t.gengroupname from ( ";
		sql += " select bg.gengroupname, cc.papercontractcode from co_contractbaseinfo   cc left join ba_marketparticipant  bmp on  cc.SELLER = bmp.participantid left join BA_ParticipantGroupRelation bp on bmp.participantid = bp.participantid left join ba_gengroup  bg on bp.gengroupid = bg.gengroupid ";
		sql += " where 1=1 ";
		sql += " and cc.papercontractcode is not null ";
		sql += " and bg.gengroupid in ('4028814a3dad7dba013db006f069047a' ,'4028814a3d67b884013d693d32d2003b','4028814a3d67b884013d693c92e90039','4028814a3d67b884013d693d9fc5003c','4028814a3d67b884013d693df528003d')";
		sql += " and cc.CONTRACTSTARTDATE <= TO_DATE('"+date+"','YYYY-MM-DD')  and cc.CONTRACTSTARTDATE >= TO_DATE('"+date.substring(0, 4)+"-01-01', 'YYYY-MM-DD') ";
		sql += "  and cc.isdelete<>1";
		sql += " and cc.marketid in (select bm.marketid from ba_market bm connect by prior bm.marketid = bm.parentmarketid start with bm.marketid = '"+marketid+"') ";
		sql += " group by bg.gengroupname, cc.papercontractcode ) t group by t.gengroupname";
		return hibernateDao.executeSqlQuery(sql);
	}

	public List findCoContract6(String marketid, String date) {
		// TODO Auto-generated method stub
		String sql = "select sum(contractqty), t.gengroupname   from ( ";
		sql += " select bg.gengroupname, cc.papercontractcode , sum(cc.contractqty) as contractqty from co_contractbaseinfo   cc left join ba_marketparticipant  bmp on  cc.SELLER = bmp.participantid left join BA_ParticipantGroupRelation bp on bmp.participantid = bp.participantid left join ba_gengroup  bg on bp.gengroupid = bg.gengroupid ";
		sql += " where 1=1 ";
		sql += " and cc.papercontractcode is not null ";
		sql += " and bg.gengroupid in ('4028814a3dad7dba013db006f069047a' ,'4028814a3d67b884013d693d32d2003b','4028814a3d67b884013d693c92e90039','4028814a3d67b884013d693d9fc5003c','4028814a3d67b884013d693df528003d')";
		sql += " and cc.CONTRACTSTARTDATE <= TO_DATE('"+date+"','YYYY-MM-DD') and cc.CONTRACTSTARTDATE >= TO_DATE('"+date.substring(0, 4)+"-01-01', 'YYYY-MM-DD') ";
		sql += "  and cc.isdelete<>1";
		sql += " and cc.marketid in (select bm.marketid from ba_market bm connect by prior bm.marketid = bm.parentmarketid start with bm.marketid = '"+marketid+"') ";
		sql += " group by bg.gengroupname, cc.papercontractcode ) t group by t.gengroupname";
		return hibernateDao.executeSqlQuery(sql);
	}

	public List findPur(String marketid, String date) {
		// TODO Auto-generated method stub
		String sql = "select count(*),ROUND(sum(contractqty)/100000,4) from (select cc.papercontractcode, sum(cc.contractqty) as contractqty from co_contractbaseinfo cc ";
		sql += " where 1=1 ";
		sql += " and cc.papercontractcode is not null and cc.CONTRACTSTARTDATE <= TO_DATE('"+date+"', 'YYYY-MM-DD') and cc.CONTRACTSTARTDATE >= TO_DATE('"+date.substring(0,4)+"-01-01', 'YYYY-MM-DD') ";
		sql += "  and cc.isdelete<>1 and cc.marketid in (select bm.marketid from ba_market bm connect by prior bm.marketid = bm.parentmarketid";
		sql += " start with bm.marketid = '"+marketid+"') and cc.contracttype in (select ct2.contracttypeid from co_contracttypeinfo ct2 connect by prior ct2.contracttypeid = ct2.supertypeid start with ct2.contracttypeid IN ('C4BB2F9D-1C3F-D011-7460-6F5390E','C1E45104-857A-B0EA-3EF8-2468860','402881da3c3847ba013c3854017a0001','4DC24C12-413C-5A67-BEBE-AB07C0C') and ct2.marketid = '91812'  )   group by cc.papercontractcode)";
		return hibernateDao.executeSqlQuery(sql);
	}

	public List findarea(String marketid, String date) {
		// TODO Auto-generated method stub
		String sql = "select count(*) from (select cc.papercontractcode from co_contractbaseinfo cc ,co_contracttypeinfo cct";
		sql += " where cc.contracttype = cct.contracttypeid and cct.contracttypeid in (select ct.contracttypeid  from co_contracttypeinfo ct";
		sql += " connect by prior ct.contracttypeid = ct.supertypeid start with ct.contracttypeid = 'D4E5C0A9-E1B2-71A7-87CC-D7C6A65' and ct.marketid = '91812')";
		sql += " and cc.papercontractname is not null and cc.CONTRACTSTARTDATE <= TO_DATE('"+date+"', 'YYYY-MM-DD')  and cc.CONTRACTSTARTDATE >= TO_DATE('"+date.substring(0, 4)+"-01-01', 'YYYY-MM-DD') ";
		sql += " and cc.isdelete<>1 and cc.marketid in (select bm.marketid from ba_market bm connect by prior bm.marketid = bm.parentmarketid";
		sql += " start with bm.marketid = '"+marketid+"') group by cc.papercontractcode)";
		return hibernateDao.executeSqlQuery(sql);
	}

	public List finddyh(String marketid, String date) {
		// TODO Auto-generated method stub
		String sql = "select count(*),ROUND(sum(contractqty) / 100000, 4) from (select cc.papercontractcode,sum(cc.contractqty) as contractqty from co_contractbaseinfo cc, ";
		sql += " co_contracttypeinfo cct";
		sql += " where cc.contracttype = cct.contracttypeid and cct.contracttypeid in (select ct.contracttypeid  from co_contracttypeinfo ct  ";
		sql += " connect by prior ct.contracttypeid = ct.supertypeid start with ct.contracttypeid = '402881da3c3847ba013c3854017a0001' and ct.marketid = '91812')";
		sql += " and cc.papercontractcode is not null and cc.CONTRACTSTARTDATE <= TO_DATE('"+date+"', 'YYYY-MM-DD') and cc.CONTRACTSTARTDATE >= TO_DATE('"+date.substring(0, 4)+"-01-01', 'YYYY-MM-DD') ";
		sql += " and cc.isdelete<>1 and cc.marketid in (select bm.marketid from ba_market bm connect by prior bm.marketid = bm.parentmarketid";
		sql += " start with bm.marketid = '"+marketid+"') group by cc.papercontractcode)";
		return hibernateDao.executeSqlQuery(sql);
	}

	public List findfdq(String marketid, String date) {
		// TODO Auto-generated method stub
		String sql = "select count(*), ROUND(sum(contractqty) / 100000, 4) from (select cc.papercontractcode,sum(cc.contractqty) as contractqty from co_contractbaseinfo cc, ";
		sql += " co_contracttypeinfo cct";
		sql += " where cc.contracttype = cct.contracttypeid and cct.contracttypeid in (select ct.contracttypeid  from co_contracttypeinfo ct  ";
		sql += " connect by prior ct.contracttypeid = ct.supertypeid start with ct.contracttypeid = '4DC24C12-413C-5A67-BEBE-AB07C0C' and ct.marketid = '91812')";
		sql += " and cc.papercontractcode is not null and cc.CONTRACTSTARTDATE <= TO_DATE('"+date+"', 'YYYY-MM-DD')  and cc.CONTRACTSTARTDATE >= TO_DATE('"+date.substring(0, 4)+"-01-01', 'YYYY-MM-DD') ";
		sql += " and cc.isdelete<>1 and cc.marketid in (select bm.marketid from ba_market bm connect by prior bm.marketid = bm.parentmarketid";
		sql += " start with bm.marketid = '"+marketid+"') group by cc.papercontractcode)";
		return hibernateDao.executeSqlQuery(sql);
	}
	
	public List findjs(String marketid, String date) {
		// TODO Auto-generated method stub
		String sql = "select count(*), ROUND(sum(contractqty) / 100000, 4) from (select cc.papercontractcode,sum(cc.contractqty) as contractqty from co_contractbaseinfo cc, ";
		sql += " co_contracttypeinfo cct";
		sql += " where cc.contracttype = cct.contracttypeid and cct.contracttypeid in (select ct.contracttypeid  from co_contracttypeinfo ct  ";
		sql += " connect by prior ct.contracttypeid = ct.supertypeid start with ct.contracttypeid = 'C4BB2F9D-1C3F-D011-7460-6F5390E' and ct.marketid = '91812')";
		sql += " and cc.papercontractcode is not null and cc.CONTRACTSTARTDATE <= TO_DATE('"+date+"', 'YYYY-MM-DD')  and cc.CONTRACTSTARTDATE >= TO_DATE('"+date.substring(0, 4)+"-01-01', 'YYYY-MM-DD') ";
		sql += " and cc.isdelete<>1 and cc.marketid in (select bm.marketid from ba_market bm connect by prior bm.marketid = bm.parentmarketid";
		sql += " start with bm.marketid = '"+marketid+"') group by cc.papercontractcode)";
		return hibernateDao.executeSqlQuery(sql);
	}
	
	public List findwsd(String marketid, String date) {
		// TODO Auto-generated method stub
		String sql = "select count(*),ROUND(sum(contractqty) / 100000, 4) from (select cc.papercontractcode,sum(cc.contractqty) as contractqty from co_contractbaseinfo cc, ";
		sql += " co_contracttypeinfo cct";
		sql += " where cc.contracttype = cct.contracttypeid and cct.contracttypeid in (select ct.contracttypeid  from co_contracttypeinfo ct ";
		sql += " connect by prior ct.contracttypeid = ct.supertypeid start with ct.contracttypeid = 'C1E45104-857A-B0EA-3EF8-2468860' and ct.marketid = '91812')";
		sql += " and cc.papercontractcode is not null and cc.CONTRACTSTARTDATE <= TO_DATE('"+date+"', 'YYYY-MM-DD')  and cc.CONTRACTSTARTDATE >= TO_DATE('"+date.substring(0, 4)+"-01-01', 'YYYY-MM-DD') ";
		sql += " and cc.isdelete<>1 and cc.marketid in (select bm.marketid from ba_market bm connect by prior bm.marketid = bm.parentmarketid";
		sql += " start with bm.marketid = '"+marketid+"') group by cc.papercontractcode)";
		return hibernateDao.executeSqlQuery(sql);
	}

	public List findpro(String marketid, String date) {
		// TODO Auto-generated method stub
		String sql = "select count(*) from (select cc.papercontractcode from co_contractbaseinfo cc , co_contracttypeinfo cct";
		sql += " where cc.contracttype = cct.contracttypeid and cct.contracttypeid in (select ct.contracttypeid  from co_contracttypeinfo ct";
		sql += " connect by prior ct.contracttypeid = ct.supertypeid start with ct.contracttypeid = 'CC3303A9-86E3-1037-83F5-E7B923A' and ct.marketid = '91812')";
		sql += " and cc.papercontractname is not null and cc.CONTRACTSTARTDATE <= TO_DATE('"+date+"', 'YYYY-MM-DD') and cc.CONTRACTSTARTDATE >= TO_DATE('"+date.substring(0, 4)+"-01-01', 'YYYY-MM-DD') ";
		sql += "  and cc.isdelete<>1 and cc.marketid in (select bm.marketid from ba_market bm connect by prior bm.marketid = bm.parentmarketid";
		sql += " start with bm.marketid = '"+marketid+"') group by cc.papercontractcode)";
		return hibernateDao.executeSqlQuery(sql);
	}
	
	/**
	 * 查询某一合同类型以及以下类型的某一市场以及下属市场的所有合同的电量之和
	 * @description 方法描述
	 * @param marketid
	 * @param date
	 * @param contype
	 * @return
	 * @author lvwentao
	 * @date 2013-5-23
	 */
	public List getTractqtyAll(String marketid, String date, String contype) {
		// TODO Auto-generated method stub
		String sql = "select count(*) from (select cc.papercontractcode from co_contractbaseinfo cc ,co_contracttypeinfo cct";
		sql += " where cc.contracttype = cct.contracttypeid and cct.contracttypeid in (select ct.contracttypeid  from co_contracttypeinfo ct";
		sql += " connect by prior ct.contracttypeid = ct.supertypeid start with ct.contracttypeid = '"+ contype +"' and ct.marketid = '91812')";
		sql += " and cc.papercontractname is not null and cc.CONTRACTSTARTDATE <= TO_DATE('"+date+"', 'YYYY-MM-DD')  and cc.CONTRACTSTARTDATE >= TO_DATE('"+date.substring(0, 4)+"-01-01', 'YYYY-MM-DD') ";
		sql += " and cc.isdelete<>1 and cc.marketid in (select bm.marketid from ba_market bm connect by prior bm.marketid = bm.parentmarketid";
		sql += " start with bm.marketid = '"+marketid+"') group by cc.papercontractcode)";
		return hibernateDao.executeSqlQuery(sql);
	}

	public List findCount(String marketid, String date) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("select COUNT(papercontractcode) from (select cc.papercontractcode, cc.contracttype from co_contractbaseinfo cc ");
		sql.append(" where 1=1");
		sql.append(" and cc.CONTRACTSTARTDATE <= TO_DATE('"+date+"','YYYY-MM-DD')  and cc.CONTRACTSTARTDATE >= TO_DATE('"+date.substring(0, 4)+"-01-01', 'YYYY-MM-DD')");
		sql.append(" and cc.marketid in (select bm.marketid from ba_market bm connect by prior bm.marketid = bm.parentmarketid start with bm.marketid = '"+marketid+"') ");
		sql.append( "and cc.papercontractcode is not null AND CC.ISDELETE <> 1 AND CC.FLOWFLAG IS NOT NULL group by CC.papercontractcode, cc.contracttype,cc.flowflag) ");
		return hibernateDao.executeSqlQuery(sql.toString());
	}

}
