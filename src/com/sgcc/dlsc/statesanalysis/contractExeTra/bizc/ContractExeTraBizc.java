package com.sgcc.dlsc.statesanalysis.contractExeTra.bizc;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.sgcc.uap.persistence.IHibernateDao;

import com.sgcc.uap.bizc.sysbizc.datadictionary.IDataDictionaryBizC;

import com.sgcc.uap.rest.support.RequestCondition;

//引入po,vo,transefer
import com.sgcc.dlsc.commons.bean.FCcategories;
import com.sgcc.dlsc.commons.bean.FCcategory;
import com.sgcc.dlsc.commons.bean.FCchart;
import com.sgcc.dlsc.commons.bean.FCdataset;
import com.sgcc.dlsc.commons.util.DateUtil;
import com.sgcc.dlsc.commons.util.ToolsSys;
import com.sgcc.dlsc.entity.po.CoContractbaseinfo;
import com.sgcc.dlsc.paramConfig.cocontracsequence.RequestConditionFilterUtil;
import com.sgcc.dlsc.statesanalysis.contractExeTra.vo.CoContractbaseinfoTransfer;
import com.sgcc.dlsc.statesanalysis.contractExeTra.vo.CoContractbaseinfoVO;

/**
 * 用户定义逻辑构件
 * 
 * @author Administrator
 * 
 */
public class ContractExeTraBizc implements IContractExeTraBizc {

	@Autowired
	private IHibernateDao hibernateDao;

	@Autowired
	private IDataDictionaryBizC dataDictionaryBizC;

	// 保存记录
	private CoContractbaseinfoVO save(Map map) {

		CoContractbaseinfoVO coContractbaseinfoVo = new CoContractbaseinfoVO();
		try {
			BeanUtils.populate(coContractbaseinfoVo, map);
		} catch (Exception e) {
		}
		CoContractbaseinfo coContractbaseinfo = CoContractbaseinfoTransfer
				.toPO(coContractbaseinfoVo);
		hibernateDao.saveOrUpdateObject(coContractbaseinfo);
		coContractbaseinfoVo = CoContractbaseinfoTransfer
				.toVO(coContractbaseinfo);
		if (map.containsKey("mxVirtualId")) {
			coContractbaseinfoVo.setMxVirtualId(String.valueOf(map
					.get("mxVirtualId")));
		}
		return coContractbaseinfoVo;
	}

	/**
	 * 根据输入条件查询记录
	 * 
	 * @param queryCondition
	 * @return
	 * @throws ParseException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public List<Object> query(RequestCondition queryCondition, String marketId,
			StringBuilder sql) throws IllegalAccessException,
			InvocationTargetException, ParseException {
		// 解析前端请求的查询过滤参数
		Map<String, String> filters = new HashMap<String, String>();
		if (queryCondition.getFilter() != null) {
			filters = RequestConditionFilterUtil
					.resolveFilterParams(queryCondition);
		}
		// 根据前端设置的搜索条件构建sql语句和参数
		List<Object> sqlParams = new ArrayList<Object>();
		searchSqlAndParams(filters, sql, marketId);
		return queryTable(queryCondition, sql, sqlParams);
	}

	private List<Object> queryTable(RequestCondition params, StringBuilder sql,
			List<Object> sqlParams) throws IllegalAccessException,
			InvocationTargetException, ParseException {
		int pageIndex = Integer.parseInt(params.getPageIndex());
		int pageSize = Integer.parseInt(params.getPageSize());
		List<Object> list = hibernateDao.executeSqlQuery(sql.toString(),
				sqlParams.toArray(), pageIndex, pageSize);
		return list;
	}

	public List<CoContractbaseinfoVO> toVoList(List<Object> list) {
		List<CoContractbaseinfoVO> tenlist = new ArrayList<CoContractbaseinfoVO>();
		for (int i = 0; i < list.size(); i++) {
			CoContractbaseinfoVO coContractbaseinfoVO = new CoContractbaseinfoVO();
			Object obj[] = (Object[]) list.get(i);
			coContractbaseinfoVO.setContractid(obj[0] == null ? "" : obj[0]
					.toString()); // 合同id
			coContractbaseinfoVO.setContractname(obj[1] == null ? "" : obj[1]
					.toString()); // 合同名称
			coContractbaseinfoVO.setContracttype(obj[2] == null ? "" : obj[2]
					.toString()); // 合同类型
			coContractbaseinfoVO.setPurchaser(obj[3] == null ? "" : obj[3]
					.toString()); // 够电方
			coContractbaseinfoVO.setSeller(obj[4] == null ? "" : obj[4]
					.toString()); // 售电方
			coContractbaseinfoVO
					.setContractqty(obj[5] != null ? new BigDecimal(obj[5]
							.toString()) : new BigDecimal(0)); // 合同电量
			coContractbaseinfoVO
					.setContractprice(obj[6] != null ? new BigDecimal(obj[6]
							.toString()) : null); // 合同价格
			coContractbaseinfoVO.setWcdl(obj[7] == null ? "0" : obj[7]
					.toString()); // 完成电量
			coContractbaseinfoVO.setWcl(obj[8] == null ? "0" : obj[8]
					.toString()); // 完成电量
			tenlist.add(coContractbaseinfoVO);
		}
		return tenlist;
	}

	public int count(StringBuilder sql) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT COUNT(1) FROM (").append(sql.toString())
				.append(")");
		// Object[] sqlParamsArray = ObjectUtils.toObjectArray(sqlParams);
		List list = hibernateDao.executeSqlQuery(buffer.toString());
		if (list == null || list.isEmpty()) {
			return 0;
		}
		Object obj = list.get(0);
		int totalCount = Integer.parseInt(obj.toString());
		return totalCount;
	}

	private void searchSqlAndParams(Map<String, String> filters,
			StringBuilder sql, String marketid) {
		String htTypeSearch = filters.get("htTypeSearch");
		String htzqSearch = filters.get("htzqSearch");
		String kssjSearch = filters.get("kssjSearch");
		String jssjSearch = filters.get("jssjSearch");
		String htztSearch = filters.get("htztSearch");
		String gdfSearch = filters.get("gdfSearch");
		String sdfSearch = filters.get("sdfSearch");
		String htSquSearch = filters.get("htSquSearch");
		String powertype = filters.get("component14");
		String contractName = filters.get("component1");
		String date = DateUtil.getNowDate("yyyy-MM");
		String searchField = "aa.contractstartdate";// 合同开始日期
		
		//合同类型改成多选，故先将合同类型拼成sql可以使用的字符串
		if(ToolsSys.isnull(htTypeSearch)){
			String[] contracttypes = htTypeSearch.split(",");
			for(int i=0;i<contracttypes.length;i++){
				if(i==0){
					htTypeSearch = "'"+contracttypes[i]+"'";
				}else {
					htTypeSearch = htTypeSearch + ",'"+contracttypes[i]+"'";
				}
			}
		}
		
		sql.append(" select aa.contractid,aa.contractname,ct.typename,bmp.participantname tt,bmc.participantname tc,aa.contractqty,aa.contractprice, ");
		sql.append(" decode((select sum(bb.energy_t) wcdl from se_result_n_m bb where bb.contract_id = aa.contractid),null,0,(select sum(bb.energy_t) wcdl from se_result_n_m bb where bb.contract_id = aa.contractid)) zdl, ");
		sql.append(" decode((select t.contractqty from co_contractbaseinfo t where t.contractid = aa.contractid),0,'0',decode((select sum(bb.energy_t) wcdl from se_result_n_m bb where bb.contract_id = aa.contractid),null,0,round((select sum(se.energy_t) from SE_RESULT_N_M se, ba_busiunit bb, co_contractbaseinfo cc,se_sbstype_def ss where  se.sbs_unit_id = bb.busiunitid and cc.seller = bb.participantid and cc.contractid = se.contract_id and se.band_index = '0' and ss.sbs_type_id = cc.contracttype and se.contract_id = aa.contractid)*100/(select t.contractqty from co_contractbaseinfo t where t.contractid = aa.contractid),2)))||'%'  wcl, ");
		sql.append(" aa.contractqty-decode((select sum(bb.energy_t) wcdl from se_result_n_m bb where bb.contract_id = aa.contractid),null,0,(select sum(bb.energy_t) wcdl from se_result_n_m bb where bb.contract_id = aa.contractid)) ");
		sql.append(" from co_contractbaseinfo aa ");
		sql.append(" left join  co_contracttypeinfo ct on aa.contracttype=ct.contracttypeid ");
		sql.append(" left join ba_marketparticipant bmp on aa.purchaser = bmp.participantid ");
		sql.append(" left join ba_marketparticipant bmc on aa.seller = bmc.participantid ");
		sql.append("where aa.marketid ='" + marketid + "'");
		// 合同类型
//		if (StringUtils.isNotBlank(htTypeSearch)) {
//			sql.append(" and contracttype='" + htTypeSearch + "'");
//		}
		if(ToolsSys.isnull(contractName)){//合同类型 
			sql.append(" and aa.contractName like '%").append(contractName).append("%' ");
		}
		
		if(ToolsSys.isnull(htTypeSearch)){//合同类型 
			sql.append(" and (aa.contracttype in (").append(htTypeSearch).append(") or aa.contracttype in (select c.contracttypeid from  co_contracttypeinfo c where c.effectiveflag = '0' and  c.MARKETID='").append(marketid);
			sql.append("' connect by prior c.contracttypeid = c.supertypeid start with c.contracttypeid in (").append(htTypeSearch).append(")))");
		}
		
		// 合同周期
		if (ToolsSys.isnull(htzqSearch)) {
			sql.append(" and aa.contractcyc ='").append(htzqSearch)
					.append("' ");
		}
		// 开始时间不为空，结束时间为空时，结束日期小于开始日期
		if (ToolsSys.isnull(kssjSearch) && !ToolsSys.isnull(jssjSearch)) {// @判断是否为空
			sql.append(" AND TO_CHAR(").append(searchField)
					.append(",'YYYY-MM-DD') >= '").append(kssjSearch)
					.append("' ");
		} else if (!ToolsSys.isnull(kssjSearch) && ToolsSys.isnull(jssjSearch)) {// @判断是否为空
			sql.append(" AND TO_CHAR(").append(searchField)
					.append(",'YYYY-MM-DD') <= '").append(jssjSearch)
					.append("' ");
		} else if (ToolsSys.isnull(kssjSearch) && ToolsSys.isnull(jssjSearch)) {// @判断是否为空
			sql.append(" AND (TO_CHAR(").append(searchField)
					.append(",'YYYY-MM-DD') >= '").append(kssjSearch)
					.append("' AND TO_CHAR(").append(searchField)
					.append(",'YYYY-MM-DD') <= '").append(jssjSearch)
					.append("') ");
		}else{
			sql.append(" AND TO_CHAR(").append ("aa.CONTRACTSTARTDATE").append(",'YYYY') = TO_CHAR(sysdate,'YYYY') ").append(" AND TO_CHAR(").append ("aa.CONTRACTENDDATE").append(",'YYYY') = TO_CHAR(sysdate,'YYYY') ");
		}
		// 合同状态
		if (ToolsSys.isnull(htztSearch)) {
			sql.append(" and aa.prepcontractflag ='").append(htztSearch)
					.append("' ");
		}
		if (ToolsSys.isnull(gdfSearch)) {// 购电方
			sql.append(" AND aa.PURCHASER = '").append(gdfSearch).append("' ");
		}
		if (ToolsSys.isnull(sdfSearch)) {// 售电方
			sql.append(" AND aa.SELLER ='").append(sdfSearch).append("' ");
		}
		if (ToolsSys.isnull(htSquSearch)) {// 合同序列
			sql.append(" and aa.SEQUENCEID = '").append(htSquSearch)
					.append("' ");
		}
		
		if(ToolsSys.isnull(powertype)){//售电方发电类型
			sql.append(" and aa.SELLER in ( select b.participantid from BA_MARKETPARTICIPANT b where  b.powertype =").append(powertype).append(" ) ");
		}
		
		sql.append("group by aa.contractid,　aa.contractname,ct.typename,bmp.participantname,bmc.participantname,aa.contractqty, aa.contractprice");
	}

	public void setHibernateDao(IHibernateDao hibernateDao) {
		this.hibernateDao = hibernateDao;
	}

	public String getStackChart(List<Object> list) {
		if (list == null || list.size() == 0) {
			return "";
		}
		FCchart chart = new FCchart();
		chart.setCaption("年度合同电量追踪"); // 设置图表的标题
		// chart.setSubCaption("单位：MW");
		chart.setFormatNumberScale("0");
		chart.setShowValues("0"); // 设置数据值与数据图形不一同显示。
		chart.setYAxisName("");
		chart.setSetAdaptiveYMin("0"); // 设置Y轴的起始值为最小值
//		chart.setPlotGradientColor("435336");
//		chart.setPlotFillAngle("0");
//		chart.setPlotFillAlpha("90");
//		chart.setPlotFillRatio("50,50");
		chart.setShowLegend("1");
		FCcategories categories = new FCcategories(); // 定义横坐标
		categories.addCategoryByDBList(list, 1); // x轴取SQL语句的第0列

		FCdataset dataset1 = new FCdataset();
		dataset1.setColor("5ba6ea");
		dataset1.setSeriesName("完成电量"); // 设置集合名称
		dataset1.addSetByDBList(list, 7, "0"); // 当期取SQL语句的第1列
		dataset1.setShowValues("1");
		
		FCdataset dataset2 = new FCdataset();
		dataset2.setColor("fff000");
		dataset2.setSeriesName("未完成电量"); // 设置集合名称
		dataset2.addSetByDBList(list, 9); // 当期取SQL语句的第2列
		dataset2.setShowValues("1");
		
		return  chart.toXml(categories.toXml()+dataset1.toXml()+dataset2.toXml());
	}
}
