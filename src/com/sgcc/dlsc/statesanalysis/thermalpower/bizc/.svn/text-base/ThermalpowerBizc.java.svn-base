package com.sgcc.dlsc.statesanalysis.thermalpower.bizc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.sgcc.dlsc.commons.bean.FCcategories;
import com.sgcc.dlsc.commons.bean.FCchart;
import com.sgcc.dlsc.commons.bean.FCdataset;
import com.sgcc.dlsc.commons.bean.FCline;
import com.sgcc.dlsc.commons.bean.FCtrendLines;
import com.sgcc.dlsc.entity.po.SeTielineResultN;
import com.sgcc.dlsc.statesanalysis.thermalpower.vo.PowerFinishPercentageVO;
import com.sgcc.dlsc.statesanalysis.thermalpower.vo.SeTielineResultNTransfer;
import com.sgcc.dlsc.statesanalysis.thermalpower.vo.SeTielineResultNVO;
import com.sgcc.uap.persistence.IHibernateDao;
import com.sgcc.uap.rest.support.IDRequestObject;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;
import com.sgcc.uap.rest.utils.CrudUtils;
import com.sgcc.uap.rest.utils.JsonUtils;
import com.sgcc.uap.rest.utils.RestUtils;
/**
 * 用户定义逻辑构件
 * 
 * @author DELL 
 * 
 */
public class ThermalpowerBizc implements IThermalpowerBizc{

	@Autowired
	private IHibernateDao hibernateDao;
	
	List topTenList = null;
	List lastTenList = null;
	List averageList = null;
	/**
	 * 保存更新
	 * 
	 * @param list
	 */
	 public List<SeTielineResultNVO> saveOrUpdate(List<Map> list) {
		 
		List<SeTielineResultNVO> voList = new ArrayList<SeTielineResultNVO>();
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
			String poName = SeTielineResultN.class.getName();
			if(map.containsKey("guid")){
				String id = (String)map.get("guid");
				SeTielineResultNVO vo = update(map,poName,id);
				voList.add(vo);
			}else{
				SeTielineResultNVO seTielineResultNVO = save(map);
				voList.add(seTielineResultNVO);
			}
		}
		return voList;
	 }
	 
	//保存记录
	private SeTielineResultNVO save(Map map) {
		
		SeTielineResultNVO seTielineResultNVo = new SeTielineResultNVO();
		try {
			BeanUtils.populate(seTielineResultNVo, map);
		} catch (Exception e) {
		}
		SeTielineResultN seTielineResultN = SeTielineResultNTransfer.toPO(seTielineResultNVo);
		hibernateDao.saveOrUpdateObject(seTielineResultN);
		seTielineResultNVo = SeTielineResultNTransfer.toVO(seTielineResultN);
		if(map.containsKey("mxVirtualId")){
			seTielineResultNVo.setMxVirtualId(String.valueOf(map.get("mxVirtualId")));
		}
		return seTielineResultNVo;
	}
	
	//更新记录
	private SeTielineResultNVO update(Map<String, ?> map,String poName,String id) {
		
		SeTielineResultNVO seTielineResultNVo = new SeTielineResultNVO();
		//更新操作
		try {
			BeanUtils.populate(seTielineResultNVo, map);
		} catch (Exception e) {
		}
		Object[] objArray = CrudUtils.generateHql(SeTielineResultN.class, map, "guid");
		hibernateDao.update((String) objArray[0], (Object[]) objArray[1]);
		return seTielineResultNVo;
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
			hibernateDao.update("delete from " + SeTielineResultN.class.getName() + " where guid = ?" ,new Object[]{id});
		}
	}

	/**
	 * 火电统调电厂完成率前10名SQL
	 * @return
	 */
	private String topTenSql(){
		StringBuffer sql = new StringBuffer();
		sql.append("select  seller, participantname, contractqty, energy_t, percentage from (");
		sql.append("select seller, market.participantname participantname, contractqty, energy_t, percentage ");
		sql.append("from (select htdl.seller, ");
		sql.append("sum(htdl.contractqty) contractqty, ");
		sql.append("sum(wcdl.energy_t) energy_t, ");
		sql.append("round(100 * sum(wcdl.energy_t) / sum(htdl.contractqty), 2) percentage, ");
		sql.append("htdl.marketid ");
		sql.append("from (select info.contractid, info.seller, info.marketid, sum(info.contractqty) contractqty ");
		sql.append(" 	from co_contractbaseinfo info");
		sql.append("	where (info.contractenddate > trunc(sysdate, 'year') and info.contractstartdate < add_months(trunc(sysdate, 'yyyy'), 12) - 1 ) and info.contractqty <> 0 ");
		sql.append("	group by info.contractid, info.seller, info.marketid) htdl, ");
		sql.append("(select cc.contractid, cc.seller, cc.marketid, sum(se.energy_t) energy_t ");
		sql.append("from SE_RESULT_N_M se, ba_busiunit bb, co_contractbaseinfo cc, se_sbstype_def ss ");
		sql.append("where se.sbs_unit_id = bb.busiunitid and cc.seller = bb.participantid and cc.contractid = se.contract_id ");
		sql.append("and se.band_index = '0' and ss.sbs_type_id = cc.contracttype and ");
		sql.append("((cc.contractstartdate >= trunc(sysdate, 'year') and add_months(trunc(sysdate, 'yyyy'), 12) - 1 >= cc.contractstartdate) or ");
		sql.append("(cc.contractenddate >= trunc(sysdate, 'year') and add_months(trunc(sysdate, 'yyyy'), 12) - 1 >= cc.contractenddate) or ");
		sql.append("(cc.contractstartdate <= trunc(sysdate, 'year') and add_months(trunc(sysdate, 'yyyy'), 12) - 1 <= cc.contractenddate)) ");
		sql.append("group by cc.contractid, cc.seller, cc.marketid) wcdl ");
		sql.append("where htdl.contractid = wcdl.contractid ");
		sql.append("group by htdl.seller, htdl.marketid) info, ");
		sql.append("ba_marketparticipant market, ");
		sql.append("ba_generator generator ");
		sql.append("where info.seller = market.participantid ");
		sql.append("and generator.plantid = market.participantid ");
		sql.append("and generator.generatortype = '0' ");//火电
		sql.append("and info.marketid = ? ");
		sql.append("group by seller, market.participantname, contractqty, energy_t, percentage ");
		sql.append("order by percentage desc ");
		sql.append(" ) WHERE ROWNUM < 11 ");
		 
		return sql.toString();
	} 
	
	/**
	 * 火电统调电厂完成率后10名SQL
	 * @return
	 */
	private String lastTenSql(){
		StringBuffer sql = new StringBuffer();
		sql.append("select  seller, participantname, contractqty, energy_t, percentage from (");
		sql.append("select seller, market.participantname participantname, contractqty, energy_t, percentage ");
		sql.append("from (select htdl.seller, ");
		sql.append("sum(htdl.contractqty) contractqty, ");
		sql.append("sum(wcdl.energy_t) energy_t, ");
		sql.append("round(100 * sum(wcdl.energy_t) / sum(htdl.contractqty), 2) percentage, ");
		sql.append("htdl.marketid ");
		sql.append("from (select info.contractid, info.seller, info.marketid, sum(info.contractqty) contractqty ");
		sql.append(" 	from co_contractbaseinfo info");
		sql.append("	where (info.contractenddate > trunc(sysdate, 'year') and info.contractstartdate < add_months(trunc(sysdate, 'yyyy'), 12) - 1 ) and info.contractqty <> 0 ");
		sql.append("	group by info.contractid, info.seller, info.marketid) htdl, ");
		sql.append("(select cc.contractid, cc.seller, cc.marketid, sum(se.energy_t) energy_t ");
		sql.append("from SE_RESULT_N_M se, ba_busiunit bb, co_contractbaseinfo cc, se_sbstype_def ss ");
		sql.append("where se.sbs_unit_id = bb.busiunitid and cc.seller = bb.participantid and cc.contractid = se.contract_id ");
		sql.append("and se.band_index = '0' and ss.sbs_type_id = cc.contracttype and ");
		sql.append("((cc.contractstartdate >= trunc(sysdate, 'year') and add_months(trunc(sysdate, 'yyyy'), 12) - 1 >= cc.contractstartdate) or ");
		sql.append("(cc.contractenddate >= trunc(sysdate, 'year') and add_months(trunc(sysdate, 'yyyy'), 12) - 1 >= cc.contractenddate) or ");
		sql.append("(cc.contractstartdate <= trunc(sysdate, 'year') and add_months(trunc(sysdate, 'yyyy'), 12) - 1 <= cc.contractenddate)) ");
		sql.append("group by cc.contractid, cc.seller, cc.marketid) wcdl ");
		sql.append("where htdl.contractid = wcdl.contractid ");
		sql.append("group by htdl.seller, htdl.marketid) info, ");
		sql.append("ba_marketparticipant market, ");
		sql.append("ba_generator generator ");
		sql.append("where info.seller = market.participantid ");
		sql.append("and generator.plantid = market.participantid ");
		sql.append("and generator.generatortype = '0' ");//火电
		sql.append("and info.marketid = ? ");
		sql.append("group by seller, market.participantname, contractqty, energy_t, percentage ");
		sql.append("order by percentage asc ");
		sql.append(" ) WHERE ROWNUM < 11 ");
		 
		return sql.toString();
	} 
	
	private String averageDataSql() {
		StringBuffer sql = new StringBuffer();
		sql.append("select seller, market.participantname, contractqty, energy_t, percentage ");
		sql.append("from (select htdl.seller, ");
		sql.append("sum(htdl.contractqty) contractqty, ");
		sql.append("sum(wcdl.energy_t) energy_t, ");
		sql.append("round(100 * sum(wcdl.energy_t) / sum(htdl.contractqty), 2) percentage, ");
		sql.append("htdl.marketid ");
		sql.append("from (select info.contractid, info.seller, info.marketid, sum(info.contractqty) contractqty ");
		sql.append(" 	from co_contractbaseinfo info");
		sql.append("	where (info.contractenddate > trunc(sysdate, 'year') and info.contractstartdate < add_months(trunc(sysdate, 'yyyy'), 12) - 1 ) and info.contractqty <> 0 ");
		sql.append("	group by info.contractid, info.seller, info.marketid) htdl, ");
		sql.append("(select cc.contractid, cc.seller, cc.marketid, sum(se.energy_t) energy_t ");
		sql.append("from SE_RESULT_N_M se, ba_busiunit bb, co_contractbaseinfo cc, se_sbstype_def ss ");
		sql.append("where se.sbs_unit_id = bb.busiunitid and cc.seller = bb.participantid and cc.contractid = se.contract_id ");
		sql.append("and se.band_index = '0' and ss.sbs_type_id = cc.contracttype and ");
		sql.append("((cc.contractstartdate >= trunc(sysdate, 'year') and add_months(trunc(sysdate, 'yyyy'), 12) - 1 >= cc.contractstartdate) or ");
		sql.append("(cc.contractenddate >= trunc(sysdate, 'year') and add_months(trunc(sysdate, 'yyyy'), 12) - 1 >= cc.contractenddate) or ");
		sql.append("(cc.contractstartdate <= trunc(sysdate, 'year') and add_months(trunc(sysdate, 'yyyy'), 12) - 1 <= cc.contractenddate)) ");
		sql.append("group by cc.contractid, cc.seller, cc.marketid) wcdl ");
		sql.append("where htdl.contractid = wcdl.contractid ");
		sql.append("group by htdl.seller, htdl.marketid) info, ");
		sql.append("ba_marketparticipant market, ");
		sql.append("ba_generator generator ");
		sql.append("where info.seller = market.participantid ");
		sql.append("and generator.plantid = market.participantid ");
		sql.append("and generator.generatortype = '0' ");//火电
		sql.append("and info.marketid = ? ");
		sql.append("group by seller, market.participantname, contractqty, energy_t, percentage ");
		sql.append("order by percentage desc ");
		 
		return sql.toString();
	}
	
	/**
	 * 平均进度
	 * @return
	 */
	private String avgPercentageSql() {
		StringBuffer sql = new StringBuffer();
		sql.append("select round(avg(percentage), 2 ) from (");
		sql.append("select seller, market.participantname, contractqty, energy_t, percentage ");
		sql.append("from (select htdl.seller, ");
		sql.append("sum(htdl.contractqty) contractqty, ");
		sql.append("sum(wcdl.energy_t) energy_t, ");
		sql.append("round(100 * sum(wcdl.energy_t) / sum(htdl.contractqty), 2) percentage, ");
		sql.append("htdl.marketid ");
		sql.append("from (select info.contractid, info.seller, info.marketid, sum(info.contractqty) contractqty ");
		sql.append(" 	from co_contractbaseinfo info");
		sql.append("	where (info.contractenddate > trunc(sysdate, 'year') and info.contractstartdate < add_months(trunc(sysdate, 'yyyy'), 12) - 1 ) and info.contractqty <> 0 ");
		sql.append("	group by info.contractid, info.seller, info.marketid) htdl, ");
		sql.append("(select cc.contractid, cc.seller, cc.marketid, sum(se.energy_t) energy_t ");
		sql.append("from SE_RESULT_N_M se, ba_busiunit bb, co_contractbaseinfo cc, se_sbstype_def ss ");
		sql.append("where se.sbs_unit_id = bb.busiunitid and cc.seller = bb.participantid and cc.contractid = se.contract_id ");
		sql.append("and se.band_index = '0' and ss.sbs_type_id = cc.contracttype and ");
		sql.append("((cc.contractstartdate >= trunc(sysdate, 'year') and add_months(trunc(sysdate, 'yyyy'), 12) - 1 >= cc.contractstartdate) or ");
		sql.append("(cc.contractenddate >= trunc(sysdate, 'year') and add_months(trunc(sysdate, 'yyyy'), 12) - 1 >= cc.contractenddate) or ");
		sql.append("(cc.contractstartdate <= trunc(sysdate, 'year') and add_months(trunc(sysdate, 'yyyy'), 12) - 1 <= cc.contractenddate)) ");
		sql.append("group by cc.contractid, cc.seller, cc.marketid) wcdl ");
		sql.append("where htdl.contractid = wcdl.contractid ");
		sql.append("group by htdl.seller, htdl.marketid) info, ");
		sql.append("ba_marketparticipant market, ");
		sql.append("ba_generator generator ");
		sql.append("where info.seller = market.participantid ");
		sql.append("and generator.plantid = market.participantid ");
		sql.append("and generator.generatortype = '0' ");//火电
		sql.append("and info.marketid = ? ");
		sql.append("group by seller, market.participantname, contractqty, energy_t, percentage ");
		sql.append("order by percentage asc ");
		sql.append(")");
		return sql.toString();
	}
	/**
	 * 获取火电统调电厂完成率前10名 List
	 * @return
	 */
	private List topTenList(String loginMarketId){
		String sql = topTenSql();
		return hibernateDao.executeSqlQuery(sql.toString(), new Object[]{loginMarketId});
	}
	
	/**
	 * 获取火电统调电厂完成率后10名 List
	 * @return
	 */
	private List lastTenList(String loginMarketId){
		String sql = lastTenSql();
		return hibernateDao.executeSqlQuery(sql.toString(), new Object[]{loginMarketId});
	}
	
	/**
	 * 获取火电统调电厂完成率平均进度 List
	 * @param j 
	 * @param i 
	 * @return
	 */
	private List averageList(String loginMarketId, int pageIndex, int pageSize){
		String sql = averageDataSql();
		return hibernateDao.executeSqlQuery(sql.toString(), new Object[]{loginMarketId}, pageIndex, pageSize);
	}
	
	private List averageList(String loginMarketId){
		String sql = averageDataSql();
		return hibernateDao.executeSqlQuery(sql.toString(), new Object[]{loginMarketId});
	}

	/**
	 * 查询 火电统调电厂完成率前10名 表格数据
	 * 
	 * @param queryCondition
	 * @return
	 */
	public QueryResultObject queryTopTen(RequestCondition queryCondition, String loginMarketId) {
		ArrayList<PowerFinishPercentageVO> result = new ArrayList<PowerFinishPercentageVO>();
		
		if(topTenList == null || topTenList.size() == 0){
			topTenList = topTenList(loginMarketId);
		}
		
		if(average == 0){
			List list = hibernateDao.executeSqlQuery(avgPercentageSql(), new Object[]{loginMarketId});
			
			if(list != null && list.size() != 0){
				BigDecimal obj = (BigDecimal)list.get(0);
				if(obj != null){
					average = obj.doubleValue();
				}
			}
		}
		
		if(topTenList != null && topTenList.size() !=0){
			for (int i = 0; i < topTenList.size(); i++) {
				Object [] obj = (Object [])topTenList.get(i);
				
				PowerFinishPercentageVO vo = new PowerFinishPercentageVO();
				vo.setSellerid(validateString(obj[0]));
				vo.setSellername(validateString(obj[1]));
				vo.setContractqty(validateString(obj[2]));
				vo.setEnergy_t(validateString(obj[3]));
				vo.setPercentage(validateString(obj[4])+"%");
				
				double percentage = (obj[4]==null? 0d : ((BigDecimal)obj[4]).doubleValue());
				double deviation = getRound(percentage - average);
				vo.setDeviation(deviation + "%");
				vo.setDeviationvalue(getRound(Double.parseDouble(validateString(obj[2])) * deviation / 100).toString());
				result.add(vo);
			}
		}
		return RestUtils.wrappQueryResult(result, result.size());
	}
	
	/**
	 * 查询 火电统调电厂完成率后10名 表格数据
	 * 
	 * @param queryCondition
	 * @return
	 */
	public QueryResultObject queryLastTen(RequestCondition queryCondition, String loginMarketId) {
		ArrayList<PowerFinishPercentageVO> result = new ArrayList<PowerFinishPercentageVO>();
		
		if(lastTenList == null || lastTenList.size() == 0){
			lastTenList = lastTenList(loginMarketId);
		}
		
		if(average == 0){
			List list = hibernateDao.executeSqlQuery(avgPercentageSql(), new Object[]{loginMarketId});
			if(list != null && list.size() != 0){
				BigDecimal obj = (BigDecimal)list.get(0);
				if(obj != null){
					average = obj.doubleValue();
				}
			}
		}
		
		if(lastTenList != null && lastTenList.size() !=0){
			for (int i = 0; i < lastTenList.size(); i++) {
				Object [] obj = (Object [])lastTenList.get(i);
				
				PowerFinishPercentageVO vo = new PowerFinishPercentageVO();
				vo.setSellerid(validateString(obj[0]));
				vo.setSellername(validateString(obj[1]));
				vo.setContractqty(validateString(obj[2]));
				vo.setEnergy_t(validateString(obj[3]));
				vo.setPercentage(validateString(obj[4])+"%");
				
				double percentage = (obj[4]==null? 0d : ((BigDecimal)obj[4]).doubleValue());
				double deviation = getRound(percentage - average);
				vo.setDeviation(deviation + "%");
				vo.setDeviationvalue(getRound(Double.parseDouble(validateString(obj[2])) * deviation / 100).toString());
				result.add(vo);
			}
		}
		return RestUtils.wrappQueryResult(result, result.size());
	}
	
	double average = 0;
	public QueryResultObject queryAverageData(RequestCondition params,
			String loginMarketId) {
		ArrayList<PowerFinishPercentageVO> result = new ArrayList<PowerFinishPercentageVO>();
		
//		if(averageList == null || averageList.size() == 0){
			averageList = averageList(loginMarketId, Integer.parseInt(params.getPageIndex()), Integer.parseInt(params.getPageSize()));
//		}
		
		List list = hibernateDao.executeSqlQuery(avgPercentageSql(), new Object[]{loginMarketId});
		
		if(list != null && list.size() != 0){
			BigDecimal obj = (BigDecimal)list.get(0);
			if(obj != null){
				average = obj.doubleValue();
			}
		}
		
		if(averageList != null && averageList.size() !=0){
			for (int i = 0; i < averageList.size(); i++) {
				Object [] obj = (Object [])averageList.get(i);
				
				String contractqty = validateString(obj[2]);
				
				PowerFinishPercentageVO vo = new PowerFinishPercentageVO();
				vo.setSellerid(validateString(obj[0]));
				vo.setSellername(validateString(obj[1]));
				vo.setContractqty(contractqty);
				vo.setEnergy_t(validateString(obj[3]));
				vo.setPercentage(validateString(obj[4]) + "%");
				
				double percentage = (obj[4]==null? 0d : ((BigDecimal)obj[4]).doubleValue());
				double deviation = getRound(percentage - average);
				vo.setDeviation(deviation + "%");
				vo.setDeviationvalue(getRound(Double.parseDouble(contractqty) * deviation / 100).toString());
				result.add(vo);
			}
		}
		String countSQL = "SELECT count(1) FROM (" + averageDataSql() + ")";
		List countList = hibernateDao.executeSqlQuery(countSQL, new Object[]{loginMarketId});
		int count = 0;
		if(countList!=null ){
			count = Integer.parseInt(countList.get(0).toString());
		}
		return RestUtils.wrappQueryResult(result, count);
	}
	
	private String validateString(Object obj){
		return obj==null?"":obj.toString();
	}
	
	private Double getRound(Double value){
		java.text.DecimalFormat df =new java.text.DecimalFormat("#.00");  
		return Double.valueOf(df.format(value));
	}
	
	/**
	 * 查询单条记录
	 * @param id
	 * @return QueryResultObject
	 */
	public QueryResultObject queryById(String id) {
		SeTielineResultN seTielineResultN = (SeTielineResultN) hibernateDao.getObject(SeTielineResultN.class,id);
		SeTielineResultNVO vo = null;
		if (seTielineResultN != null) {
			vo = SeTielineResultNTransfer.toVO(seTielineResultN);
		}
		return RestUtils.wrappQueryResult(vo);
	}

	public void setHibernateDao(IHibernateDao hibernateDao) {
		this.hibernateDao = hibernateDao;
	}

	public String getTopTenChart(String loginMarketId) {
		FCchart chart = new FCchart();
		chart.setDecimals("2");							//功能属性类-------------设置四舍五入的小数点精度。范围0-10
		chart.setNumberSuffix("%");
		if(topTenList == null || topTenList.size() == 0){
			topTenList = topTenList(loginMarketId);
		}
		
		FCcategories fccatagories = new FCcategories();
		fccatagories.addCategoryByDBList(topTenList, 1);
		
		FCdataset fcdataset = new FCdataset();
		fcdataset.addSetByDBList(topTenList, 4);//完成率
		 
		return chart.toXml(fccatagories.toXml() + fcdataset.toXml());//@返回
	}

	public String getLastTenChart(String loginMarketId) {
		FCchart chart = new FCchart();
		chart.setDecimals("2");							//功能属性类-------------设置四舍五入的小数点精度。范围0-10
		chart.setNumberSuffix("%");
		if(lastTenList == null){
			lastTenList = lastTenList(loginMarketId);
		}
		
		FCcategories fccatagories = new FCcategories();
		fccatagories.addCategoryByDBList(lastTenList, 1);
		
		FCdataset fcdataset = new FCdataset();
		fcdataset.addSetByDBList(lastTenList, 4);//完成率
		
		return chart.toXml(fccatagories.toXml() + fcdataset.toXml());//@返回
	}

	public String getAverageDataChart(String params, String loginMarketId) {
		Map map = null;
		String topOrLast = null;
		try {
			map = JsonUtils.getObjectMapper().readValue(params, Map.class);
			topOrLast = map.get("toporlast")==null?null:map.get("toporlast").toString();//读取前十天还是后十条数据的标识
		}catch (Exception e) {
			e.printStackTrace();
		}	
		
		FCchart chart = new FCchart();
		chart.setDecimals("2");							//功能属性类-------------设置四舍五入的小数点精度。范围0-10
		chart.setNumberSuffix("%");
		
		if(averageList == null){
			averageList = averageList(loginMarketId);
		}
		FCcategories fccatagories = new FCcategories();
		FCdataset fcdataset = new FCdataset();
		if(topOrLast != null){
			List dataList = null;
			if("topTen".equals(topOrLast)){
				dataList = topTenList;
			} else if("lastTen".equals(topOrLast)){
				dataList = lastTenList;
			}
			
			fccatagories.addCategoryByDBList(dataList, 1);//纵轴
			fcdataset.addSetByDBList(dataList, 4);//完成率  横轴
		}

		if(average == 0){
			List list = hibernateDao.executeSqlQuery(avgPercentageSql(), new Object[]{loginMarketId});
			
			if(list != null && list.size() != 0){
				BigDecimal obj = (BigDecimal)list.get(0);
				if(obj != null){
					average = obj.doubleValue();
				}
			}
		}
		FCline line = new FCline();
		line.setDisplayValue("平均进度" + average + "%");
		line.setStartValue(average+"");
		line.setShowOnTop("1");
		line.setColor("FF0000");
		line.setValueOnRight("1");
		
		FCtrendLines fcTrendlines = new FCtrendLines();
		fcTrendlines.addLine(line);
		
		return chart.toXml(fccatagories.toXml() + fcdataset.toXml() + fcTrendlines.toXml());//@返回
	}

}
