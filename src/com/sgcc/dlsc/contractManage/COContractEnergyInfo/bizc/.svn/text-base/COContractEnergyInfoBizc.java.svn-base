package com.sgcc.dlsc.contractManage.COContractEnergyInfo.bizc;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

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
import com.sgcc.dlsc.entity.po.CoContractenergyinfo;
import com.sgcc.dlsc.commons.bean.VIParamBean;
import com.sgcc.dlsc.commons.bean.VIReturnBean;
import com.sgcc.dlsc.commons.bizc.IComm_VerticalIntegration_Bizc;
import com.sgcc.dlsc.commons.util.DateUtil;
import com.sgcc.dlsc.commons.util.ToolsSys;
import com.sgcc.dlsc.commons.util.UserInfoUtil;
import com.sgcc.dlsc.constants.DataConstants;
import com.sgcc.dlsc.contractManage.COContractEnergyInfo.vo.CoContractenergyinfoTransfer;
import com.sgcc.dlsc.contractManage.COContractEnergyInfo.vo.CoContractenergyinfoVO;
import com.sgcc.isc.core.orm.identity.User;
/**
 * 用户定义逻辑构件
 * 
 * @author thinpad 
 * 
 */
public class COContractEnergyInfoBizc implements ICOContractEnergyInfoBizc{

	@Autowired
	private IHibernateDao hibernateDao;
	
	@Autowired
	private IDataDictionaryBizC dataDictionaryBizC;
	
	@Autowired
	private IComm_VerticalIntegration_Bizc commVerticalIntegrationBizc;
	public IComm_VerticalIntegration_Bizc getCommVerticalIntegrationBizc() {
		return commVerticalIntegrationBizc;
	}

	public void setCommVerticalIntegrationBizc(
			IComm_VerticalIntegration_Bizc commVerticalIntegrationBizc) {
		this.commVerticalIntegrationBizc = commVerticalIntegrationBizc;
	}

	/**
	 * 保存更新
	 * 
	 * @param list
	 */
	 public List<CoContractenergyinfoVO> saveOrUpdate(List<Map> list,HttpServletRequest request) {
		 
		List<CoContractenergyinfoVO> voList = new ArrayList<CoContractenergyinfoVO>();
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
			String poName = CoContractenergyinfo.class.getName();
			if(map.containsKey("guid")){
				String id = (String)map.get("guid");
				CoContractenergyinfoVO vo = update(map,poName,id,request);
				voList.add(vo);
			}else{
				CoContractenergyinfoVO coContractenergyinfoVO = save(map,request);
				voList.add(coContractenergyinfoVO);
			}
		}
		return voList;
	 }
	 
	//保存记录
	private CoContractenergyinfoVO save(Map map,HttpServletRequest request) {
		String month="";
		CoContractenergyinfoVO coContractenergyinfoVo = new CoContractenergyinfoVO();
		String sd="";
		String ed="";
		try {
			if(map.containsKey("startdate")) {
				String startdate=(String)map.get("startdate");
				String[] sdates = startdate.split("-");
				sd = sdates[0]+"/"+sdates[1]+"/"+sdates[2];
				map.put("startdate", DateUtil.getUtilDate(sd, "yyyy/MM/dd"));
				coContractenergyinfoVo.setStartdate(DateUtil.getUtilDate(sd, "yyyy/MM/dd"));
			}
			if(map.containsKey("enddate")) { 
				String enddate=(String)map.get("enddate");
				String[] edates = enddate.split("-");
				ed = edates[0]+"/"+edates[1]+"/"+edates[2];
				map.put("enddate", DateUtil.getUtilDate(ed, "yyyy/MM/dd"));
				coContractenergyinfoVo.setEnddate(DateUtil.getUtilDate(ed, "yyyy/MM/dd"));
			}
			BeanUtils.populate(coContractenergyinfoVo, map);
//			coContractenergyinfoVo.setContractid(map.get("contractid")==null?null:map.get("contractid").toString());
//			coContractenergyinfoVo.setPurchaserprice(new BigDecimal(map.get("purchaserprice")==null?null:map.get("purchaserprice").toString()));
//			coContractenergyinfoVo.setSellerprice(new BigDecimal(map.get("sellerprice")==null?null:map.get("sellerprice").toString()));
//			coContractenergyinfoVo.setPrice(new BigDecimal(map.get("price")==null?null:map.get("price").toString()));
//			coContractenergyinfoVo.setTradePriceMargin(new BigDecimal(map.get("tradePriceMargin")==null?null:map.get("tradePriceMargin").toString()));
//			coContractenergyinfoVo.setVendeeBreathPrice(new BigDecimal(map.get("vendeeBreathPrice")==null?null:map.get("vendeeBreathPrice").toString()));
//			coContractenergyinfoVo.setSaleBreathPrice(new BigDecimal(map.get("saleBreathPrice")==null?null:map.get("saleBreathPrice").toString()));
//			coContractenergyinfoVo.setPeriod(new BigDecimal(map.get("period")==null?null:map.get("period").toString()));
//			coContractenergyinfoVo.setEnergy(new BigDecimal(map.get("energy")==null?null:map.get("energy").toString()));
		} catch (Exception e) {
		}
		CoContractenergyinfo coContractenergyinfo = CoContractenergyinfoTransfer.toPO(coContractenergyinfoVo);
		coContractenergyinfo.setWhereinsert("1");
		
		StringBuffer sql = new StringBuffer();
		sql.append("select timeno from CO_ContractEnergyInfo where TO_CHAR(startdate, 'YYYY/MM/DD') =? and TO_CHAR(enddate, 'YYYY/MM/DD') =? and contractid=? and WHEREINSERT='1' group by timeno");
		
		List list = hibernateDao.executeSqlQuery(sql.toString(),new Object[]{sd, ed, (String)map.get("contractid")});
		if(list.size()>0) {
			BigDecimal bd = new BigDecimal("0");
			bd = (BigDecimal)list.get(0);
			month = bd.toString();
		}else{
			
			StringBuffer sql1 = new StringBuffer();
			sql1.append("select max(timeno) from (select timeno from CO_ContractEnergyInfo where contractid = ? and WHEREINSERT='1' group by timeno)");
			
			List list1 = hibernateDao.executeSqlQuery(sql1.toString(),new Object[]{(String)map.get("contractid")});
			if(list1.size()>0) {
				if(list1.get(0)!=null) {
					BigDecimal bd = new BigDecimal("0");
					bd = (BigDecimal)list1.get(0);
					month =(Integer.parseInt(bd.toString())+1)+"";
				}else {
					month="1";
				}
			}else{
				month="1";
			}
		}
		coContractenergyinfo.setTimeno(new BigDecimal(month));
		coContractenergyinfo.setQtytype(new BigDecimal(1));
		hibernateDao.saveOrUpdateObject(coContractenergyinfo);
		coContractenergyinfoVo = CoContractenergyinfoTransfer.toVO(coContractenergyinfo);
		if(map.containsKey("mxVirtualId")){
			coContractenergyinfoVo.setMxVirtualId(String.valueOf(map.get("mxVirtualId")));
		}
		//纵向传输
		String marketId = "";
		try {
			marketId = UserInfoUtil.getLoginUserMarket(request);//当前登录场景
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		VIParamBean vip = new VIParamBean();
		vip.setMarketid_source(marketId);
		vip.setMarketid_target(DataConstants.marketid_target);//目的场景
		vip.setTableName("CO_ContractEnergyInfo");//表名
		vip.setWhereCondtion("contractid='"+coContractenergyinfo.getContractid()+"'");
		User user;
		try {
			user = UserInfoUtil.getLoginUser(request);
			vip.setUserId(user.getId());
			vip.setUserName(user.getUserName());//拼音
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		vip.setDate(DateUtil.getNowDate("yyyy-MM-dd"));
		VIReturnBean vir = commVerticalIntegrationBizc.executeVerticalIntegrationHSXByLogObjId(vip);
				
		return coContractenergyinfoVo;
	}
	
	//更新记录
	private CoContractenergyinfoVO update(Map<String, ?> map,String poName,String id,HttpServletRequest request) {
		
		CoContractenergyinfoVO coContractenergyinfoVo = new CoContractenergyinfoVO();
		Set set = map.keySet();
		ArrayList<String> keyList = new ArrayList<String>();
		//更新操作
		try {
			for (Object object : set) {
				String key = (String) object;
				if(map.get(key)==null){
					keyList.add(key);
				}
			}
			for (String string : keyList) {
				map.remove(string);
			}
			BeanUtils.populate(coContractenergyinfoVo, map);
		} catch (Exception e) {
		}
		
		CoContractenergyinfo coContractenergyinfo = CoContractenergyinfoTransfer.toPO(coContractenergyinfoVo);
		coContractenergyinfo.setWhereinsert("1");
		Set set1 = map.keySet();
		if(set1 != null && set1.size() > 1){
			Object[] objArray = CrudUtils.generateHql(CoContractenergyinfo.class, map, "guid");
			hibernateDao.update((String) objArray[0], (Object[]) objArray[1]);
		}
		CoContractenergyinfo po = hibernateDao.getObject(CoContractenergyinfo.class, id);
		for (String string : keyList) {
			try{
				Field field = CoContractenergyinfo.class.getDeclaredField(string);
				field.setAccessible(true);
				field.set(po, null);
				hibernateDao.updateObject(po);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		//纵向传输
				String marketId = "";
				try {
					marketId = UserInfoUtil.getLoginUserMarket(request);//当前登录场景
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				VIParamBean vip = new VIParamBean();
				vip.setMarketid_source(marketId);
				vip.setMarketid_target(DataConstants.marketid_target);//目的场景
				vip.setTableName("CO_ContractEnergyInfo");//表名
				vip.setWhereCondtion("guid='"+coContractenergyinfo.getContractid()+"'");
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
		
		return coContractenergyinfoVo;
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
			hibernateDao.update("delete from " + CoContractenergyinfo.class.getName() + " where guid = ?" ,new Object[]{id});
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
		List<CoContractenergyinfo> result = null;
		int count = 0;
		qc.addFrom(CoContractenergyinfo.class);
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
		List<QueryFilter> wheres = queryCondition.getQueryFilter(CoContractenergyinfoVO.class);
		if (wheres != null && wheres.size() > 0) {
			CrudUtils.addQCWhere(qc, wheres, CoContractenergyinfo.class.getName());

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
	 * 
	 * 查询单条记录
	 * @description 方法描述
	 * @param id主键
	 * @param params参数
	 * @param period尖峰平谷总
	 * @return
	 * @author liling 
	 * @date 2014-3-6
	 */
	public QueryResultObject queryById(String id,String[] params,String period) {
		CoContractenergyinfo coContractenergyinfo = new CoContractenergyinfo();
		CoContractenergyinfoVO vo = null;
		//如果id非空时，即为修改
		if(!"".equals(id) && id!=null && !"null".equals(id)){
			coContractenergyinfo = (CoContractenergyinfo) hibernateDao.getObject(CoContractenergyinfo.class,id);

			if (coContractenergyinfo != null) {
				vo = CoContractenergyinfoTransfer.toVO(coContractenergyinfo);
			}
		}
		//否则新增，新增时对选择的类型进行判断
		else
			if(!"".equals(period) && period != null){
					//而且选择总时，若已经存储了信息，则不读取按月分解信息
					if(params != null){//参数不为空时，先读取该合同该时间段该段信息是否已存在
						StringBuffer hql = new StringBuffer();
						hql.append(" from ");
						hql.append(CoContractenergyinfo.class.getName());
						hql.append("  where  contractid=? and startdate=to_date(?,'yyyy-MM-dd') and enddate=to_date(?,'yyyy-MM-dd') and whereinsert='1' and period='"+period+"'");
						List<CoContractenergyinfo> list = hibernateDao.findAll(hql.toString(), params);
						if(list != null && list.size() >0){//若存在，读取显示
							coContractenergyinfo = list.get(0);
						}
						else{//不存在，查询按月分解该时间段是否存在
							if("1".equals(period)){//类型为总时。需要读取按月分解的该月energy，whereinsert=‘0’，
								hql = new StringBuffer();
								hql.append(" from ");
								hql.append(CoContractenergyinfo.class.getName());
								hql.append("  where  contractid=? and startdate=to_date(?,'yyyy-MM-dd') and enddate=to_date(?,'yyyy-MM-dd') and whereinsert='0'");
								list = new ArrayList<CoContractenergyinfo>();
								list = hibernateDao.findAll(hql.toString(), params);
								if(list != null && list.size() >0){//若存在，读取显示
									coContractenergyinfo = list.get(0);
									coContractenergyinfo.setPeriod(new BigDecimal(period));
									coContractenergyinfo.setGuid(null);
									coContractenergyinfo.setStartdate(DateUtil.getUtilDate(params[1], "yyyy-MM-dd"));
									coContractenergyinfo.setEnddate(DateUtil.getUtilDate(params[2], "yyyy-MM-dd"));
								}
							}
						}
						if (coContractenergyinfo != null) {
							vo = CoContractenergyinfoTransfer.toVO(coContractenergyinfo);
						}
					}
				}
			//否则，按步骤进行新增
			else{
				coContractenergyinfo = (CoContractenergyinfo) hibernateDao.getObject(CoContractenergyinfo.class,id);

				if (coContractenergyinfo != null) {
					vo = CoContractenergyinfoTransfer.toVO(coContractenergyinfo);
				}
		}
		return RestUtils.wrappQueryResult(vo).addDicItems(wrapDictList());	//返回返回值
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

	public List getCOContractEnergyTree(String type) {
		StringBuffer sql = new StringBuffer();
		sql.append("select cc.contractid, TO_CHAR(cc.startdate, 'YYYY-MM-DD'),  TO_CHAR(cc.enddate, 'YYYY-MM-DD'),  cc.timeno from CO_ContractEnergyInfo cc where WHEREINSERT='1' and cc.contractid = ? group by cc.contractid, cc.startdate, cc.enddate, cc.timeno order by cc.startdate");
		
		List list = hibernateDao.executeSqlQuery(sql.toString(),new Object[]{type});
		return list;
	}

	public QueryResultObject query(int pageIndex, int pageSize, String contractid, String startdate,
			String enddate) {
		QueryResultObject result = new QueryResultObject();//新建返回值对象
		StringBuffer sb = new  StringBuffer();
		int size = 0;	//定义数据条数
		sb.append("select co.guid,co.startdate, co.enddate, bg.name, co.sellerenergy, co.sellerprice, co.price, co.period, co.energy, co.purchaserprice from co_contractenergyinfo co, ba_gencode bg where co.whereinsert='1' and  co.period = bg.value(+) and bg.type = '33' and co.contractid='").append(contractid).append("' "); 
		if(!startdate.equals("")) {
			sb.append("and co.STARTDATE >= to_date('").append(startdate).append("', 'yyyy/mm/dd  HH24:mi:ss')")
			  .append("and co.enddate <= to_date('").append(enddate).append("', 'yyyy/mm/dd  HH24:mi:ss')");
		}
		String sql2 = "SELECT COUNT(*) G FROM ( "+ sb.toString() + " ) T ";		//拼接查询数据条数的sql语句
		List list = hibernateDao.executeSqlQuery(sql2.toString());
		if(list.size() > 0){	//如果有查询结果，给数据条数变量赋值
			size = Integer.parseInt(list.get(0).toString());
		}
		result.setItemCount(size);	//给返回对象赋数据的数量
		list = hibernateDao.executeSqlQuery(sb.toString(),pageIndex,pageSize);	//查询分页的数据
		List<CoContractenergyinfoVO> voList = new ArrayList<CoContractenergyinfoVO>();
		for(int i = 0;i < list.size();i++){	//遍历要展示的数据
 			Object[] objs = (Object[])list.get(i);	//得到第i条数据
 			CoContractenergyinfoVO vo = new CoContractenergyinfoVO();
 			vo.setGuid(objs[0] == null ? "" : objs[0].toString());
 			vo.setStartdate(objs[1] == null ? null : DateUtil.getUtilDate(objs[1].toString(), "yyyy-MM-dd"));
 			vo.setEnddate(objs[2] == null ? null : DateUtil.getUtilDate(objs[2].toString(), "yyyy-MM-dd"));
 			vo.setWhereinsert(objs[3] == null ? "" : objs[3].toString());
 			vo.setSellerenergy(objs[4] == null ? null : new BigDecimal(objs[4].toString()));
 			vo.setSellerprice(objs[5] == null ? null : new BigDecimal(objs[5].toString()));
 			vo.setPrice(objs[6] == null ? null : new BigDecimal(objs[6].toString()));
 			vo.setPeriod(objs[7] == null ? null : new BigDecimal(objs[7].toString()));
 			vo.setEnergy(objs[8] == null ? null : new BigDecimal(objs[8].toString()));
 			vo.setPurchaserenergy(objs[9] == null ? null : new BigDecimal(objs[9].toString()));
 			voList.add(vo);	//把数据添加到结果集中
		}
		return RestUtils.wrappQueryResult(voList).addDicItems(wrapDictList());	//返回返回值
	}
	// 将字典对象封装为list
		private List<DicItems> wrapDictList() {
			List<DicItems> dicts = new ArrayList<DicItems>();

			dicts.add(translateFromFile("period"));
			return dicts;
		}
		// 从属性文件中查询字典
		private DicItems translateFromFile(String fieldName) {
			List<Map<String, String>> list = new ArrayList();
			Object[] obj = new Object[1];
			StringBuffer sql = new StringBuffer();
			sql.append(" select value, name from ba_gencode where type = '33' ");
			List tlist  = hibernateDao.executeSqlQuery(sql.toString());
			for(int i=0;i<tlist.size();i++){
				Map<String, String> map = new HashMap();
				Object[] robj = (Object[])tlist.get(i);
				String value = robj[0]==null?"":robj[0].toString();
				String name = robj[1]==null?"":robj[1].toString();
				map.put("value", value);
				map.put("text", name);
				list.add(map);
			}
			DicItems dict = new DicItems();
			dict.setName(fieldName);
			dict.setValues(list);
			return dict;
		}
		
		public List getCOContractEnergyInit(String checkedId) {
			StringBuffer sql = new StringBuffer();
			sql.append("select TO_CHAR(contractstartdate, 'YYYY-MM-DD'), TO_CHAR(contractenddate, 'YYYY-MM-DD') from co_contractbaseinfo where  contractid=?");
			
			List list = hibernateDao.executeSqlQuery(sql.toString(),new Object[]{checkedId});
			return list;
		}
		
		/**
		 * 查询时间段序号最大值
		 * @description 方法描述
		 * @param contractid
		 * @return
		 * @author fujingbo
		 * @date 2014-2-22
		 */
		public String nextTimeNo(String contractid){
			Integer nextNo = 1;
			String sql = " SELECT '1', MAX(CO.TIMENO) FROM  CO_ContractEnergyInfo CO WHERE CO.CONTRACTID = '"+contractid+"' GROUP BY '1'";
			List list = hibernateDao.executeSqlQuery(sql);//@查询信息
			if (list != null && list.size()>0) {
				Object []obj = (Object[]) list.get(0);
				String StrNo = ToolsSys.ObjIsNullToStr(obj[1]);//@判断是否为空
				if(!StrNo.equals("")){
					nextNo = Integer.parseInt(StrNo) + 1;
				}
			}		
			return nextNo.toString();
		}
		/**
		 * 查询合同电量-年度分段电量之和=差
		 * @description 方法描述
		 * @param params
		 * @return
		 * @author liling 
		 * @date 2014-3-6
		 */
		public String querySumEnergy(String[] params){
			String result = "0";
			if(params !=null){
				//查询合同电量
				String sqlco = "SELECT T.CONTRACTQTY FROM CO_CONTRACTBASEINFO T WHERE T.CONTRACTID=?";
				//年度分段电量总和
				String sql = "SELECT SUM(T.ENERGY) FROM CO_CONTRACTENERGYINFO T WHERE  CONTRACTID=? AND WHEREINSERT='1' AND TO_CHAR(T.STARTDATE,'YYYY-MM')>=? AND TO_CHAR(T.STARTDATE,'YYYY-MM')<=? AND PERIOD ='1'";
//				String sql = "SELECT SUM(T.ENERGY) FROM CO_CONTRACTENERGYINFO T WHERE  CONTRACTID=? AND WHEREINSERT='1' AND TO_CHAR(T.STARTDATE,'YYYY-MM')>=? AND TO_CHAR(T.STARTDATE,'YYYY-MM')<=? ";
				//取年度
				Date sdate = DateUtil.getUtilDate(params[1], "yyyy");
				Date edate = DateUtil.getUtilDate(params[2], "yyyy");
				//转为该年度1-12月
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
				String startdate = dateFormat.format(sdate)+"-01";
				String enddate = dateFormat.format(edate)+"-12";
				Object[] obj  = {params[0],startdate,enddate};
				//合同电量
				List listco = hibernateDao.executeSqlQuery(sqlco,new Object[]{params[0]});
				//存在合同电量时比较
				if(listco !=null && listco.size() >0){
					if(listco.get(0) != null && listco.get(0).toString() != "null"){
//						Float qty = Float.valueOf(listco.get(0).toString());
						BigDecimal qty = new BigDecimal(listco.get(0).toString());
						
						//年度分段之和
						List list = hibernateDao.executeSqlQuery(sql, obj);
						if(list !=null && list.size() >0){
//							Float sum = Float.valueOf(list.get(0)==null?"0":list.get(0).toString());
							BigDecimal sum = new BigDecimal(list.get(0)==null?"0":list.get(0).toString());
//							Float p = qty-sum;
							BigDecimal p = qty.subtract(sum);
							result = p.toString();
						}
					}
					
				}
			}
			return result;
			
		}
		
		/**
		 * 
		 * @description 获取该月的尖峰平谷四种电量总和
		 * @param map
		 * @return
		 * @author mengke 
		 * @date 2014-6-11
		 */
		public List getMonthEnergy(Map map){
			String contractId = map.get("contractId")==null?"":map.get("contractId").toString();
			String stDate = map.get("stDate")==null?"":map.get("stDate").toString();
			String[] stDates = stDate.split("-");
			if(stDates!=null&&stDates.length>1){
				stDate = stDates[1];
			}
			StringBuffer sql = new StringBuffer();
			sql.append("select sum(t.energy) from co_contractenergyinfo t where t.contractid = ? and substr(to_char(t.startdate,'yyyy-mm-dd'),6,2) = ?  and t.whereinsert = '1' and t.period <> 1");
			List list = hibernateDao.executeSqlQuery(sql.toString(), new Object[]{contractId,stDate});
			return list;
		}
		
		/**
		 * 
		 * @description 获取该月的总电量
		 * @param map
		 * @return
		 * @author mengke
		 * @date 2014-6-11
		 */
		public List getMonthTotalEnergy(Map map){
			String contractId = map.get("contractId")==null?"":map.get("contractId").toString();
			String startDate = map.get("startDate")==null?"":map.get("startDate").toString();
			String[] stDates = startDate.split("-");
			if(stDates!=null&&stDates.length>1){
				startDate = stDates[1];
			}
			StringBuffer sql = new StringBuffer();
			sql.append("select t.energy from co_contractenergyinfo t where t.contractid = ? and substr(to_char(t.startdate,'yyyy-mm-dd'),6,2) = ? and t.period = 1 and t.whereinsert = ?");
			List list = hibernateDao.executeSqlQuery(sql.toString(), new Object[]{contractId,startDate,"1"});
			if(list==null || list.size()==0){
				list = hibernateDao.executeSqlQuery(sql.toString(), new Object[]{contractId,startDate,"0"});
			}
			return list;
		}
}
