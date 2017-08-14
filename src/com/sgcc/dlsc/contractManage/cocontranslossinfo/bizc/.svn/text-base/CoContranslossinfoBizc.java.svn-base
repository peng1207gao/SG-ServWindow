package com.sgcc.dlsc.contractManage.cocontranslossinfo.bizc;

import java.math.BigDecimal;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import com.sgcc.dlsc.contractManage.CoContractbaseInfo.vo.CoContractbaseinfoTransfer;
import com.sgcc.dlsc.contractManage.CoContractbaseInfo.vo.CoContractbaseinfoVO;
import com.sgcc.dlsc.contractManage.cocontranslossinfo.vo.CoContranslossinfoTransfer;
import com.sgcc.dlsc.contractManage.cocontranslossinfo.vo.CoContranslossinfoVO;
import com.sgcc.dlsc.entity.po.BaGencode;
import com.sgcc.dlsc.entity.po.BaIntertie;
import com.sgcc.dlsc.entity.po.BaMarketparticipant;
import com.sgcc.dlsc.entity.po.CoContractbaseinfo;
import com.sgcc.dlsc.entity.po.CoContranslossinfo;

/**
 * 线损信息信息 {@link CoContranslossinfo}维护逻辑构件
 * 
 * @author djdeng 
 * 
 */
public class CoContranslossinfoBizc implements ICoContranslossinfoBizc{
	
	@Autowired
	private IHibernateDao hibernateDao;
	
	@Autowired
	private IDataDictionaryBizC dataDictionaryBizC;
	
	/**
	 * 日志记录器
	 */
	private Log logger = LogFactory.getLog(getClass());
	
	/**
	 * 保存更新
	 * 
	 * @param list
	 */
	 public List<CoContranslossinfoVO> saveOrUpdate(List<Map> list) {
		 
		List<CoContranslossinfoVO> voList = new ArrayList<CoContranslossinfoVO>();
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
			
			setTranslineno(map);	//在保存或更新之前设置线损信息的序列号
			setLoss(map);	//在保存或更新之前设置线损信息的分段线损率
			
			if(map.containsKey("guid")){
				String id = (String)map.get("guid");
				CoContranslossinfoVO vo = update(map, id);
				voList.add(vo);
			}else{
				CoContranslossinfoVO coContranslossinfoVO = save(map);
				voList.add(coContranslossinfoVO);
			}
		}
		return voList;
	 }
	 
	//保存记录
	private CoContranslossinfoVO save(Map map) {
		
		CoContranslossinfoVO coContranslossinfoVo = new CoContranslossinfoVO();
		try {
			BeanUtils.populate(coContranslossinfoVo, map);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(e);
			}
		}
		CoContranslossinfo coContranslossinfo = CoContranslossinfoTransfer.toPO(coContranslossinfoVo);
		hibernateDao.saveOrUpdateObject(coContranslossinfo);
		coContranslossinfoVo = CoContranslossinfoTransfer.toVO(coContranslossinfo);
		
		if(map.containsKey("mxVirtualId")){
			coContranslossinfoVo.setMxVirtualId(String.valueOf(map.get("mxVirtualId")));
		}
		return coContranslossinfoVo;
	}
	
	//更新记录
	private CoContranslossinfoVO update(Map<String, ?> map, String id) {
		CoContranslossinfoVO coContranslossinfoVo = new CoContranslossinfoVO();
		//更新操作
		try {
			BeanUtils.populate(coContranslossinfoVo, map);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(e);
			}
		}
		Object[] objArray = CrudUtils.generateHql(CoContranslossinfo.class, map, "guid");
		hibernateDao.update((String) objArray[0], (Object[]) objArray[1]);
		return coContranslossinfoVo;
	}
	
	/**
	 * 设置线损信息对象的输电线路序号
	 * 
	 * @param map 线损信息
	 */
	@SuppressWarnings("unchecked")
	private void setTranslineno(Map map) {
		Integer maxTransLineNo = qryMaxTransLineNo((String)map.get("contractid"));
		map.put("translineno", new BigDecimal(maxTransLineNo));
	}
	
	/**
	 * 设置线损信息对象输电网损
	 * 
	 * 输电网损率属性类型是数字类型并可以为空，所有需要特殊处理空字符串  "" 或 "  " 的情况
	 * 
	 * @param map 线损信息
	 */
	private void setLoss(Map map) {
		String lossValue = (String)map.get("loss");
		
		if (lossValue != null && lossValue.trim().equals("")) {
			map.remove("loss");
		}
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
			hibernateDao.update("delete from " + CoContranslossinfo.class.getName() +
					" where guid = ?" ,new Object[]{id});
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
		List<CoContranslossinfo> result = null;
		int count = 0;
		qc.addFrom(CoContranslossinfo.class);
		if (queryCondition != null) {
			qc = wrapQuery(queryCondition, qc);
			count = getRecordCount(qc);
			qc = wrapPage(queryCondition, qc);
			result = hibernateDao.findAllByCriteria(qc);

		} else {
			result = hibernateDao.findAllByCriteria(qc);
			count = getRecordCount(qc);
		}
		return RestUtils.wrappQueryResult(result, count).addDicItems(wrapDictList());
	}
	
	/**
	 * 封装查询条件
	 * 
	 * @param queryCondition
	 * @param qc
	 * @return
	 */
	private QueryCriteria wrapQuery(RequestCondition queryCondition,
			QueryCriteria qc) {
		List<QueryFilter> wheres = queryCondition.getQueryFilter(CoContranslossinfoVO.class);
		if (wheres != null && wheres.size() > 0) {
			CrudUtils.addQCWhere(qc, wheres, CoContranslossinfo.class.getName());
		}

		String orders = queryCondition.getSorter();
		if (orders != null) {
			qc.addOrder(orders.replaceAll("&", ","));
		}
		return qc;
	}
	
	/**
	 * 设置分页查询参数
	 * 
	 * @param queryCondition
	 * @param qc
	 * @return
	 */
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
		CoContranslossinfo coContranslossinfo = (CoContranslossinfo) hibernateDao.
				getObject(CoContranslossinfo.class,id);
		CoContranslossinfoVO vo = null;
		if (coContranslossinfo != null) {
			vo = CoContranslossinfoTransfer.toVO(coContranslossinfo);
		} 
		return RestUtils.wrappQueryResult(vo).addDicItems(wrapDictList());
	}
	
	/**
	 * 
	 * @description 根据合同ID查询最大的输电线路序号
	 * @param contractId
	 * @return
	 * @author gaoming
	 * @date 2012-9-24
	 */
	private Integer qryMaxTransLineNo(String contractId){
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT CCLI.CONTRACTID,MAX(CCLI.TRANSLINENO) FROM CO_CONTRANSLOSSINFO CCLI ");
		sql.append(" WHERE CCLI.CONTRACTID = '").append(contractId).append("' GROUP BY CCLI.CONTRACTID ");
		List list = hibernateDao.executeSqlQuery(sql.toString());//@查询信息
		int finIn = 1;
		if(list!=null &&list.size()>0){
			Object[] obj = (Object[]) list.get(0);
			finIn = (obj[1]==null) ? 1 : (Integer.valueOf(obj[1].toString())+1);//@判断是否为空并赋值
		}
		
		return finIn;
	}

	/**
	 * 初始化字典值
	 * 
	 * @return QueryResultObject
	 */
	public QueryResultObject initDict() {
		QueryResultObject query = new QueryResultObject();
		return query.addDicItems(wrapDictList());
	}

	// 将字典对象封装为list
	private List<DicItems> wrapDictList() {
		List<DicItems> dicts = new ArrayList<DicItems>();

		//查询输电方列表数据，PARTICIPANTTYPE=1
        dicts.add(translateFromDB("transmission", BaMarketparticipant.class.getName(),
        		"participantid", "participantname", "PARTICIPANTTYPE = 1"));
        
        //查询联络线列表数据
        dicts.add(translateFromDB("linkid", BaIntertie.class.getName(),
        		"linkid", "linkname", "1=1"));
        
        //查询输电方向列表数据
        dicts.add(translateFromDB("direction", BaGencode.class.getName(), 
        		"value", "name", "type = '31'"));
        
		return dicts;
	}

	// 从数据库中查询字典
	private DicItems translateFromDB(String fieldName, String poName,
			String keyField, String valueField, String filter) {
		
		List<Map<String, String>> list = dataDictionaryBizC.translateFromDB(
				poName, "value", "text", keyField, valueField, filter);
		DicItems dict = new DicItems();
		dict.setName(fieldName);
		dict.setValues(list);
		return dict;
	}
	
	/**
	 *注入数据字典构件
	 * 
	 * @param dataDictionaryBizC
	 */
	public void setDataDictionaryBizC(IDataDictionaryBizC dataDictionaryBizC) {
		this.dataDictionaryBizC = dataDictionaryBizC;
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

	/**
	 * 根据合同Id查询合同的信息
	 * 
	 */
	public QueryResultObject queryContractById(String contractid) {
		CoContractbaseinfo coContractbaseinfo = (CoContractbaseinfo) hibernateDao
				.getObject(CoContractbaseinfo.class, contractid);
		CoContractbaseinfoVO vo = null;
		if (coContractbaseinfo != null) {
			vo = CoContractbaseinfoTransfer.toVO(coContractbaseinfo);
		}
		return RestUtils.wrappQueryResult(vo);
	}
	
	/**
	 * 
	 * @description 根据linkID查询需要的信息，并返回一个字符串
	 * @param linkId
	 * @return
	 * @author gaoming
	 * @date 2013-1-18
	 */
	public String findByLinkId(String linkId){
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT BIT.LINKID, BAG.GATENAME ||'-'|| BAGG.GATENAME AS SE, BIT.LOSS");
		sql.append(" FROM BA_InterTie BIT");
		sql.append(" LEFT JOIN BA_Gate BAG ON BIT.STARTGATEID = BAG.GATEID ");
		sql.append(" LEFT JOIN BA_Gate BAGG ON BIT.ENDGATEID = BAGG.GATEID");
		sql.append("  WHERE BIT.LINKID = '").append(linkId).append("' ");
		
		List list = hibernateDao.executeSqlQuery(sql.toString());
		
		String finStr = "";
		//将从数据库中查询出的数据，拼凑成 ： “线路Id,起止信息,线路网损率”
		if(list!=null&&list.size()>0 ){
			Object[] obj = (Object[]) list.get(0);
			String linkid = (obj[0]==null?"":obj[0].toString());
			String qz = (obj[1]==null?"":obj[1].toString());
			String loss = (obj[2]==null?"":obj[2].toString());
			finStr = linkid+","+qz+","+loss;
		}
		
		return finStr;
	}
}
