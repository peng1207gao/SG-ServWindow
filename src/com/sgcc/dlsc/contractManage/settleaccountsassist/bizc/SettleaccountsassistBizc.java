package com.sgcc.dlsc.contractManage.settleaccountsassist.bizc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.sgcc.dlsc.commons.util.DateUtil;
import com.sgcc.dlsc.commons.util.ToolsSys;
import com.sgcc.dlsc.contractManage.IdeaConManage.vo.CoContractbaseinfoVO;
import com.sgcc.dlsc.entity.po.CoContractbaseinfo;
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
 * @author juguanghui
 * 
 */
public class SettleaccountsassistBizc implements ISettleaccountsassistBizc{

	@Autowired
	private IHibernateDao hibernateDao;
	
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
//		CoContractbaseinfo coContractbaseinfo = CoContractbaseinfoTransfer.toPO(coContractbaseinfoVo);
//		hibernateDao.saveOrUpdateObject(coContractbaseinfo);
//		coContractbaseinfoVo = CoContractbaseinfoTransfer.toVO(coContractbaseinfo);
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
		hibernateDao.update((String) objArray[0], (Object[]) objArray[1]);
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
			hibernateDao.update("delete from " + CoContractbaseinfo.class.getName() + " where contractid = ?" ,new Object[]{id});
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
			result = hibernateDao.findAllByCriteria(qc);

		} else {
			result = hibernateDao.findAllByCriteria(qc);
			count = getRecordCount(qc);
		}
		return RestUtils.wrappQueryResult(result, count);
		
		
	}
	
	/**
	 * 根据条件查询合同信息
	 * @description 方法描述
	 * @param pageNo
	 * @param pageSize
	 * @param map
	 * @return
	 * @author juguanghui
	 * @date 2013-4-16
	 */
	public String getContractListByMCondition(Map<String, String> map) {
		String marketid = map.get("marketid");//marketid
		String conType =  map.get("contracttype");//合同类型
//		String timetype = map.get("timetype");//时间类型
		String startTime = map.get("contractstartdate");//开始时间
		String endTime = map.get("contractenddate");//截止时间
		String conCircle = map.get("contractcyc");//合同周期
		String signState = map.get("signstate");//合同状态
		String sequence = map.get("sequenceid");//合同序列
		String purchaser = map.get("purchaser");//购电方
		String seller = map.get("seller");//售点方
	    String contractid = map.get("contractid");
	    String powertype = map.get("powertype");
	    
	  //合同类型改成多选，故先将合同类型拼成sql可以使用的字符串
  		if(ToolsSys.isnull(conType)){
  			String[] contracttypes = conType.split(",");
  			for(int i=0;i<contracttypes.length;i++){
  				if(i==0){
  					conType = "'"+contracttypes[i]+"'";
  				}else {
  					conType = conType + ",'"+contracttypes[i]+"'";
  				}
  			}
  		}

		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT DISTINCT CC.CONTRACTID,CC.PAPERCONTRACTCODE,CC.CONTRACTNAME,CC.isRelation,CCT.TYPENAME,BM.PARTICIPANTNAME AS PP, ");
		sql.append(" BMM.PARTICIPANTNAME AS QQ ,CC.CONTRACTSTARTDATE,CC.CONTRACTENDDATE,CC.CONTRACTQTY,CC.ContractPrice,ttt.name as qd,tt.name as bea,bgyx.name as yx,mm.contractname as mmn,cg.name as flow,bg.marketname,cc.marketid ");
		sql.append(" FROM CO_CONTRACTBASEINFO CC LEFT JOIN CO_CONTRACTBUSIFLOWINFO CCB ");
		sql.append(" ON CC.CONTRACTID = CCB.CONTRACTID LEFT JOIN  ba_gencode T ON T.type='28' AND CCB.BUSIFLOWSTATE = T.VALUE ");
		//sql.append(" LEFT JOIN CO_ContractEnergyInfo CE ON CE.CONTRACTID = CC.CONTRACTID ");
		sql.append(" LEFT JOIN CO_CONTRACTTYPEINFO CCT ON CC.CONTRACTTYPE = CCT.CONTRACTTYPEID ");
/*		sql.append(" LEFT JOIN BA_MARKETPARTICIPANT BM ON  BM.PARTICIPANTTYPE IN ('0','2') AND CC.PURCHASER = BM.PARTICIPANTNAME  ");
		sql.append(" LEFT JOIN BA_MARKETPARTICIPANT BMM ON  BMM.PARTICIPANTTYPE IN ('0','1')  AND CC.SELLER = BMM.PARTICIPANTNAME ");*/
		sql.append(" LEFT JOIN BA_MARKETPARTICIPANT BM ON CC.PURCHASER = BM.PARTICIPANTID  ");
		sql.append(" LEFT JOIN BA_MARKETPARTICIPANT BMM ON CC.SELLER = BMM.PARTICIPANTID ");
		sql.append(" LEFT JOIN ba_gencode TT ON  TT.TYPE='26'  AND CC.BACKUPSTATE = TT.VALUE ");
		sql.append(" LEFT JOIN ba_gencode TTT ON TTT.TYPE='24' AND CC.SIGNSTATE = TTT.VALUE ");
		sql.append(" LEFT JOIN ba_gencode TTTT ON  TTTT.TYPE='27' AND CC.PREPCONTRACTFLAG = TTTT.VALUE ");
		sql.append(" LEFT JOIN ba_gencode TTTTT ON  TTTTT.type='1'  AND  CC.MARKETID = TTTTT.VALUE ");
		sql.append(" LEFT JOIN ba_gencode bgyx ON bgyx.TYPE='68' AND CC.execType = bgyx.VALUE ");
		sql.append(" LEFT JOIN ba_gencode cg  ON  cg.type='67' and cg.VALUE=CC.flowflag  ");
		sql.append(" LEFT JOIN CO_ContracSequenceInfo CCE ON  CCE.sequenceID=CC.sequenceID  ");
		//合同序列待定，做成模糊查询还是什么的？
		sql.append(" left join co_contractbaseinfo mm on  mm.contractid  = cc.supercontractid ");
//		sql.append(" left join CO_CONTRACTBASEINFO_share cs on cs.dataid=cc.CONTRACTID");
		sql.append(" left join Ba_market bg on cc.marketid = bg.marketcode");
		sql.append(" WHERE");
//		sql.append(" CC.ISDELETE = '0'  ");
		sql.append(" (CC.ISDELETE = '0' and cc.flowflag <> '6') and  ");
		sql.append(" 1 = 1");
		
		if(marketid != null && !"".equals(marketid) && !"null".equals(marketid)){
    		   sql.append(" and cc.marketid='"+marketid+"'"); //本业务场景
		    }else {//全部
//		    	sql.append(" and (cs.marketid='"+isDefine+"' or cc.marketid='"+isDefine+"')");   
		    }
		
		if(conType!=null && !"".equals(conType) && !"null".equals(conType)){
			sql.append(" AND CC.CONTRACTTYPE in (").append(conType).append(") ");
		}
		
		//合同时间段，用合同开始时间字段
		if((startTime!=null && !startTime.equals(""))&& (endTime==null || endTime.equals(""))){
        	sql.append(" AND TO_CHAR(CC.contractStartDate,'YYYY-MM-DD')>= '").append(startTime).append("' ");
        }else if((endTime!=null && !endTime.equals(""))&& (startTime==null || startTime.equals("")) ){
        	sql.append(" AND TO_CHAR(CC.contractStartDate,'YYYY-MM-DD')<= '").append(endTime).append("' ");
        }else if((startTime!=null && !startTime.equals(""))&& (endTime!=null &&! endTime.equals(""))){
        	sql.append(" AND TO_CHAR(CC.contractStartDate,'YYYY-MM-DD')>= '").append(startTime).append("' and TO_CHAR(CC.contractStartDate,'YYYY-MM-DD')<= '").append(endTime).append("' ");
        }
		
		if(conCircle!=null && !"".equals(conCircle) && !"null".equals(conCircle)){//@判断合同周期是否为空
			sql.append(" AND CC.CONTRACTCYC ='").append(conCircle).append("' ");
		}
		
		if(powertype!=null && !"".equals(powertype) && !"null".equals(powertype)){
			sql.append(" AND BMM.powertype = '" + powertype + "' ");
		} 
		//modified by juguanghui 2012-02-25 （合同状态字段 统一取 flowflag） start
//		if(signState!=null && !"".equals(signState) && !"null".equals(signState)){//@判断签订状态是否为空
//			if("0".equals(signState)){//预合同
//				sql.append(" AND CC.prepContractFlag = '1' ");
//			}else if("1".equals(signState)){//流转中	backupState = 3
//				sql.append(" AND CC.prepContractFlag = '2'");
//				sql.append(" AND CC.backupState = '3' ");
//			}else if("2".equals(signState)){//未签订	signState = 2
//				sql.append(" AND CC.prepContractFlag = '2'");
//				sql.append(" AND CC.signState = '2' ");
//			}else if("3".equals(signState)){//已签订	signState = 1
//				sql.append(" AND CC.prepContractFlag = '2'");
//				sql.append(" AND CC.signState = '1' ");
//			}else if("4".equals(signState)){//未备案	backupState = 2
//				sql.append(" AND CC.prepContractFlag = '2'");
//				sql.append(" AND CC.backupState = '2' ");
//			}else if("5".equals(signState)){//已备案	backupState = 1
//				sql.append(" AND CC.prepContractFlag = '2'");
//				sql.append(" AND CC.backupState = '1' ");
//			}
//		}
		
		if(signState!=null && !"".equals(signState) && !"null".equals(signState)){//@判断签订状态是否为空
			if("1".equals(signState)){//起草中
				sql.append(" AND CC.flowflag = '1' ");
			}else if("2".equals(signState)){//流转审核
				sql.append(" AND CC.flowflag = '2' ");
			}else if("3".equals(signState)){//审批中	
				sql.append(" AND CC.flowflag = '3' ");
			}else if("4".equals(signState)){//已签订	
				sql.append(" AND CC.flowflag = '4' ");
			}else if("5".equals(signState)){//已备案	
				sql.append(" AND CC.flowflag = '5' ");
			}
		}
		//modified by juguanghui 2012-02-25 （合同状态字段 统一取 flowflag） end
		
		if(purchaser!=null && !"".equals(purchaser) && !"null".equals(purchaser)){
			sql.append(" AND CC.PURCHASER = '").append(purchaser).append("' ");
		}
		if(seller!=null && !"".equals(seller) && !"null".equals(seller)){
			sql.append(" AND CC.SELLER ='").append(seller).append("' ");
		}
		if(sequence!=null && !"".equals(sequence) && !"null".equals(sequence)){
			sql.append(" AND CCE.sequenceid like '%").append(sequence).append("%' ");
		}
		//sql.append("  group by CC.CONTRACTID,  CC.PAPERCONTRACTCODE, CC.CONTRACTNAME,");
		//sql.append("  CCT.TYPENAME, BM.PARTICIPANTNAME   ,BMM.PARTICIPANTNAME ,CC.CONTRACTSTARTDATE,CC.CONTRACTENDDATE, CC.CONTRACTQTY, TT.NAME ,TTT.NAME,T.NAME,TTTT.NAME,CC.UPDATETIME,TTTTT.NAME ");
		sql.append(" ORDER BY  CC.CONTRACTNAME,CC.CONTRACTQTY ");
		return sql.toString();
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
		CoContractbaseinfo coContractbaseinfo = (CoContractbaseinfo) hibernateDao.getObject(CoContractbaseinfo.class,id);
		CoContractbaseinfoVO vo = null;
		if (coContractbaseinfo != null) {
//			vo = CoContractbaseinfoTransfer.toVO(coContractbaseinfo);
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

	public QueryResultObject findCoContractList(RequestCondition queryCondition) {
		List<CoContractbaseinfoVO> result = new ArrayList<CoContractbaseinfoVO>();
		
		String marketid = "";
		String contracttype = "";
		String powertype = "";
		String contractstartdate = "";
		String contractenddate = "";
		String contractcyc = "";
		String signstate = "";
		String purchaser = "";
		String seller =  "";
		String sequenceid = "";
		
		List<QueryFilter> list1 = queryCondition.getQueryFilter(CoContractbaseinfoVO.class);
		int PageIndex  = Integer.valueOf(queryCondition.getPageIndex());
		int PageSize  = Integer.valueOf(queryCondition.getPageSize());
		Map<String,String> valueMap = new HashMap<String,String>();
		if(list1 != null){
			for (int i = 0; i < list1.size(); i++) {
				QueryFilter filter = list1.get(i);
				String colName = filter.getFieldName();
				if(colName.equals("marketid")){
					marketid = (filter.getValue()==null?"":filter.getValue().toString());
					valueMap.put("marketid", marketid);
				}else if(colName.equals("contracttype")){
					contracttype = (filter.getValue()==null?"":filter.getValue().toString());
					valueMap.put("contracttype", contracttype);
//				}else if(colName.equals("timetype")){
//					timetype = (filter.getValue()==null?"":filter.getValue().toString());
//					valueMap.put("timetype", timetype);
				}else if(colName.equals("contractstartdate")){
					contractstartdate = (filter.getValue()==null?"":filter.getValue().toString());
					valueMap.put("contractstartdate", contractstartdate);
				}else if(colName.equals("contractenddate")){
					contractenddate = (filter.getValue()==null?"":filter.getValue().toString());
					valueMap.put("contractenddate", contractenddate);
				}else if(colName.equals("contractcyc")){
					contractcyc = (filter.getValue()==null?"":filter.getValue().toString());
					valueMap.put("contractcyc", contractcyc);
				}else if(colName.equals("signstate")){
					signstate = (filter.getValue()==null?"":filter.getValue().toString());
					valueMap.put("signstate", signstate);
				}else if(colName.equals("purchaser")){
					purchaser = (filter.getValue()==null?"":filter.getValue().toString());
					valueMap.put("purchaser", purchaser);
				}else if(colName.equals("seller")){
					seller = (filter.getValue()==null?"":filter.getValue().toString());
					valueMap.put("seller", seller);
				}else if(colName.equals("sequenceid")){
					sequenceid = (filter.getValue()==null?"":filter.getValue().toString());
					valueMap.put("sequenceid", sequenceid);
				} else if(colName.equals("powertype")){
					powertype = (filter.getValue()==null?"":filter.getValue().toString());
					valueMap.put("powertype", powertype); 
				}
			}
		}
		String sql = getContractListByMCondition(valueMap);
		
		List list = hibernateDao.executeSqlQuery(sql, PageIndex, PageSize);
		
		int count = 0;
		if(list!=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				Object[] obj = (Object[]) list.get(i);
				CoContractbaseinfoVO contractVO = new CoContractbaseinfoVO();
				
				contractVO.setContractid(validateString(obj[0]));
				contractVO.setPapercontractcode(validateString(obj[1]));
				contractVO.setContractname(validateString(obj[2]));
				contractVO.setIsrelation(validateBigDecimal(obj[3]));
				contractVO.setContracttype(validateString(obj[4]));
//				contractVO.setMarketname(validateString(obj[16]));
//				contractVO.setExectype(validateString(obj[13]));
				contractVO.setPurchaser(validateString(obj[5]));
				contractVO.setSeller(validateString(obj[6]));
				contractVO.setContractstartdate(validateDate(obj[7]));
				contractVO.setContractenddate(validateDate(obj[8]));
				contractVO.setEnergy(validateBigDecimal(obj[9]));
				contractVO.setBackupstate(validateString(obj[12]));
				contractVO.setSignstate(validateString(obj[11]));
				contractVO.setFlowstate(validateString(obj[15]));
				contractVO.setMarketid(validateString(obj[17]));
				contractVO.setSupercontractid(validateString(obj[14]));
				
				result.add(contractVO);
			}
			String tt = "SELECT COUNT(*) FROM ("+sql.toString()+") OOX ";
			List list2=hibernateDao.executeSqlQuery(tt);
			if(list2!=null ){
				count = Integer.parseInt(list2.get(0).toString());
			}
		}
		return RestUtils.wrappQueryResult(result, count)
//				.addDicItems(wrapDictList())
				;
	}
	
	private Date validateDate(Object obj){
		return obj==null?null:DateUtil.getUtilDate(obj.toString(), "yyyy-MM-dd");
	}
	
	private BigDecimal validateBigDecimal(Object obj){
		return obj==null?null:new BigDecimal(obj.toString());
	}
	
	private String validateString(Object obj){
		return obj==null?"":obj.toString();
	}
}
