package com.sgcc.dlsc.txtManage.cocontractparameter.bizc;

import java.math.BigDecimal;
import java.sql.SQLException;
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
import com.sgcc.dlsc.commons.util.ComUtils;
import com.sgcc.dlsc.commons.util.DateUtil;
import com.sgcc.dlsc.contractManage.CoContransEnergyInfoV2.vo.CoContransenergyinfoVO;
import com.sgcc.dlsc.entity.po.CoContractparameter;
import com.sgcc.dlsc.txtManage.cocontractparameter.vo.CoContractparameterTransfer;
import com.sgcc.dlsc.txtManage.cocontractparameter.vo.CoContractparameterVO;
/**
 * 用户定义逻辑构件
 * 
 * @author xiabaike 
 * 
 */
public class CocontractparameterBizc implements ICocontractparameterBizc{

	@Autowired
	private IHibernateDao hibernateDao;
	
	@Autowired
	private IDataDictionaryBizC dataDictionaryBizC;
	
	/**
	 * 复制标签
	 * @param tempparamid
	 * @return true/false
	 * */
	public boolean copyParam(String marketId, String userId, String tempparamid, String bookmark){
		StringBuffer sqlSelect = new StringBuffer();
		sqlSelect.append(" SELECT * FROM CO_CONTRACTPARAMETER CCP WHERE CCP.TEMPPARAMID = '");
		sqlSelect.append(tempparamid);
		sqlSelect.append("' ");
		List list = hibernateDao.executeSqlQuery(sqlSelect.toString());
		Object[] objs = (Object[])list.get(0);
		StringBuffer sqlInsert = new StringBuffer();
		sqlInsert.append("INSERT INTO CO_CONTRACTPARAMETER(MARKETID,TEMPPARAMID,TEMPPARAMNAME,ISSHARE,CREATEDATE,CREATOR,ISDELETE,CONTRACTTEMPLATECODE,DIC_ID,BOOKMARK) VALUES (");
		sqlInsert.append("'").append(marketId).append("','").append(ComUtils.createGUID()).append("','");
		sqlInsert.append(objs[2].toString()).append("',").append(Integer.parseInt(objs[9].toString())).append(",SYSDATE,'");
		sqlInsert.append(userId).append("',").append(Integer.parseInt(objs[12].toString())).append(",'");
		sqlInsert.append(objs[15].toString()).append("','").append(objs[16].toString()).append("','");
		sqlInsert.append(bookmark).append("' )");

		int result = hibernateDao.executeSqlUpdate(sqlInsert.toString());
		if(result == 1) {
			return true;
		}
		return false;
	}
	
	/**
	 * 执行数据维护，先删除后插入
	 * @description 方法描述
	 * @param templateCode 合同范本ID
	 * @param marketId 市场成员ID
	 * @param userId 用户ID
	 * @param dicValue 数据值
	 * @return
	 * @author xiabaike
	 * @date 2014-5-28
	 */
	public boolean updateCoParam(String templateCode, String marketId, String userId, String dicValue)throws SQLException{
		String[] data = dicValue.split("@");//字符串截取
		String[] sqllist = new String[data.length+1];
		sqllist[0] = "DELETE FROM CO_CONTRACTPARAMETER CCP WHERE CCP.CONTRACTTEMPLATECODE='"+templateCode+"'";
		for (int i = 0; i < data.length; i++) {
			String[] s = data[i].split(",");
			//@组装sql语句
			StringBuffer sql = new StringBuffer("INSERT INTO CO_CONTRACTPARAMETER(MARKETID,TEMPPARAMID,TEMPPARAMNAME,ISSHARE,CREATEDATE,CREATOR,ISDELETE,CONTRACTTEMPLATECODE,DIC_ID) VALUES (");
			sql.append("'").append(marketId).append("','").append(ComUtils.createGUID()).append("','").append(s[1]).append("',0,SYSDATE,'").append(userId).append("',0,'").append(templateCode).append("','").append(s[0]).append("')");
			sqllist[i+1] = sql.toString();
		}
		int[] num = hibernateDao.batchUpdateWithSql(sqllist);
		if(num.length == data.length+1) {
			return true;
		}
		return false;
	}
	
	/**
	 * 查询页面详细配置信息
	 * @param templateCode
	 * @return list
	 */
	public List findAllParamDic(String templateCode){
		//存放查询结果
		List list = new ArrayList();
		//存放查询参数
		List<String> paramList = new ArrayList<String>();
		paramList.add(templateCode);
		System.out.println("templateCode:"+templateCode);
		StringBuffer sql = new StringBuffer(); 
		sql.append("SELECT DIC.SHEETID,DIC.DIC_NAME,CCP.TEMPPARAMID,CCP.TEMPPARAMNAME FROM CO_CONTRACTPARAMDIC DIC ");
		sql.append("LEFT JOIN CO_CONTRACTPARAMETER CCP ON DIC.SHEETID = CCP.DIC_ID AND CCP.CONTRACTTEMPLATECODE=? ");
		sql.append("ORDER BY DIC.ORDER_NO");
		list = hibernateDao.executeSqlQuery(sql.toString(), paramList.toArray());
		return list;
	}
	
	/**
	 * 保存更新
	 * 
	 * @param list
	 */
	 public List<CoContractparameterVO> saveOrUpdate(List<Map> list) {
		
		List<CoContractparameterVO> voList = new ArrayList<CoContractparameterVO>();
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
			String poName = CoContractparameter.class.getName();
			if(map.containsKey("tempparamid")){
				String id = (String)map.get("tempparamid");
				CoContractparameterVO vo = update(map,poName,id);
				voList.add(vo);
			}else{
				CoContractparameterVO coContractparameterVO = save(map);
				voList.add(coContractparameterVO);
			}
		}
		return voList;
	 }
	 
	//保存记录
	private CoContractparameterVO save(Map map) {
		
		CoContractparameterVO coContractparameterVo = new CoContractparameterVO();
		try {
			BeanUtils.populate(coContractparameterVo, map);
		} catch (Exception e) {
		}
		CoContractparameter coContractparameter = CoContractparameterTransfer.toPO(coContractparameterVo);
		hibernateDao.saveOrUpdateObject(coContractparameter);
		coContractparameterVo = CoContractparameterTransfer.toVO(coContractparameter);
		if(map.containsKey("mxVirtualId")){
			coContractparameterVo.setMxVirtualId(String.valueOf(map.get("mxVirtualId")));
		}
		return coContractparameterVo;
	}
	
	//更新记录
	private CoContractparameterVO update(Map<String, ?> map,String poName,String id) {
		
		CoContractparameterVO coContractparameterVo = new CoContractparameterVO();
		//更新操作
		try {
			BeanUtils.populate(coContractparameterVo, map);
		} catch (Exception e) {
		}
		Object[] objArray = CrudUtils.generateHql(CoContractparameter.class, map, "tempparamid");
		hibernateDao.update((String) objArray[0], (Object[]) objArray[1]);
		return coContractparameterVo;
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
			hibernateDao.update("delete from " + CoContractparameter.class.getName() + " where tempparamid = ?" ,new Object[]{id});
		}
	}
	
	/**
	 * 根据输入条件查询记录
	 * 
	 * @param queryCondition
	 * @param marketId
	 * @return
	 */
	public QueryResultObject query(String contracttemplateId, String marketId, int pageIndex, int pageSize) {

		CoContractparameter coparam = new CoContractparameter();
		coparam.setMarketid(marketId);//@获取市场id
		coparam.setContracttemplatecode(contracttemplateId);//@获取合同模板代码
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT CCP.TEMPPARAMID,CCTL.CONTRACTTEMPLATENAME,CCP.TEMPPARAMNAME,DIC.DIC_NAME,CCP.BOOKMARK,CCP.TEMPPARAMDESC ");
		sql.append(" FROM CO_CONTRACTPARAMETER CCP, CO_CONTEMPLATEWITHPARAM CCTL,CO_CONTRACTPARAMDIC DIC WHERE CCP.CONTRACTTEMPLATECODE=CCTL.CONTRACTTEMPLATECODE ");
		sql.append(" AND CCP.DIC_ID = DIC.SHEETID AND CCP.MARKETID= ");
		sql.append(marketId);
		sql.append(" ");
		if ( !"null".equals(coparam.getContracttemplatecode()) 
				&& !"".equals(coparam.getContracttemplatecode()) 
				&& coparam.getContracttemplatecode() != null ) {//@判断是否为空
			sql.append(" AND CCP.CONTRACTTEMPLATECODE = '").append(coparam.getContracttemplatecode()).append("' ");
		}
		QueryResultObject result = new QueryResultObject();
		
		String sql2 = "SELECT COUNT(*) C FROM ( "+ sql.toString() + " ) T ";		//拼接查询数据条数的sql语句
		List list = hibernateDao.executeSqlQuery(sql2.toString());
		int size = 0;	//定义数据条数
		if(list.size() > 0){	//如果有查询结果，给数据条数变量赋值
			size = Integer.parseInt(list.get(0).toString());
		}
		result.setItemCount(size);	//给返回对象赋数据的数量
		list = hibernateDao.executeSqlQuery(sql.toString(), pageIndex, pageSize);	//查询分页的数据 , pageIndex, pageSize
		List<CoContractparameterVO> voList = new ArrayList<CoContractparameterVO>();		//返回的数据结果集
		for(int i = 0;i < list.size();i++){	//遍历要展示的数据
 			Object[] objs = (Object[])list.get(i);	//得到第i条数据
 			CoContractparameterVO vo = new CoContractparameterVO();	//数据结果集中的一条数据
 			//给每个变量赋值
 			vo.setTempparamid(objs[0] == null ? "" : objs[0].toString());
 			vo.setContracttemplatename(objs[1] == null ? "" : objs[1].toString());
 			vo.setTempparamname(objs[2] == null ? "" : objs[2].toString());
 			vo.setDicname(objs[3] == null ? "" : objs[3].toString());
 			vo.setBookmark(objs[4] == null ? "" : objs[4].toString());
 			vo.setTempparamdesc(objs[5] == null ? "" : objs[5].toString());
 			voList.add(vo);	//把数据添加到结果集中
 		}
		result.setItems(voList);	//给返回对象赋分页查询数据
		return result;
	}
	private QueryCriteria wrapQuery(RequestCondition queryCondition,
			QueryCriteria qc) {
		List<QueryFilter> wheres = queryCondition.getQueryFilter(CoContractparameterVO.class);
		if (wheres != null && wheres.size() > 0) {
			CrudUtils.addQCWhere(qc, wheres, CoContractparameter.class.getName());

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
		CoContractparameter coContractparameter = (CoContractparameter) hibernateDao.getObject(CoContractparameter.class,id);
		CoContractparameterVO vo = null;
		if (coContractparameter != null) {
			vo = CoContractparameterTransfer.toVO(coContractparameter);
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
}
