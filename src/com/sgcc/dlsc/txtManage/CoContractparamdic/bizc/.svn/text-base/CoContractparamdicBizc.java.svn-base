package com.sgcc.dlsc.txtManage.CoContractparamdic.bizc;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.sgcc.dlsc.commons.util.DataTypeTrans;
import com.sgcc.dlsc.commons.util.DateUtil;
import com.sgcc.dlsc.entity.po.CoContractparamdic;
import com.sgcc.dlsc.entity.po.CoContractparameter;
import com.sgcc.dlsc.txtManage.CoContractparamdic.vo.CoContractparamdicTransfer;
import com.sgcc.dlsc.txtManage.CoContractparamdic.vo.CoContractparamdicVO;
import com.sgcc.dlsc.txtManage.cocontractparameter.vo.CoContractparameterVO;
/**
 * 用户定义逻辑构件
 * 
 * @author xiabaike 
 * 
 */
public class CoContractparamdicBizc implements ICoContractparamdicBizc{

	@Autowired
	private IHibernateDao hibernateDao;
	
	@Autowired
	private IDataDictionaryBizC dataDictionaryBizC;
	/**
	 * 保存更新
	 * 
	 * @param list
	 * @throws ParseException 
	 */
	 public List<CoContractparamdicVO> saveOrUpdate(List<Map> list, String loginName) throws ParseException {
		 
		String nowDate = DateUtil.getNowDate("yyyy/MM/dd");
		//Date date = DateUtil.getUtilDate(nowDate, "yyyy/MM/dd");
		List<CoContractparamdicVO> voList = new ArrayList<CoContractparamdicVO>();
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
			map.put("operateUser", loginName);
			//map.put("operateDate", nowDate);
			String poName = CoContractparamdic.class.getName();
			if(map.containsKey("sheetid")){
				String id = (String)map.get("sheetid");
				map.put("operateDate", DateUtil.getNowDate("yyyy-MM-dd HH:mm:ss"));
				CoContractparamdicVO vo = update(map,poName,id);
				voList.add(vo);
			}else{
				map.put("operateDate", DateUtil.getUtilDate(DateUtil.getNowDate("yyyy/MM/dd"), "yyyy/MM/dd"));
				CoContractparamdicVO coContractparamdicVO = save(map);
				voList.add(coContractparamdicVO);
			}
		}
		return voList;
	 }
	 
	 public String getNextOrderNo(){
			String sql = "SELECT MAX(CCPD.ORDER_NO) FROM SGBIZ.CO_CONTRACTPARAMDIC CCPD";
			List list = hibernateDao.executeSqlQuery(sql);
			if (list != null && list.size() > 0) {
				int NextOrderNo = Integer.parseInt(list.get(0).toString()) + 1;
				return NextOrderNo+"";
			}
			return "";
	}
	 
	//保存记录
	private CoContractparamdicVO save(Map map) {
		
		CoContractparamdicVO coContractparamdicVo = new CoContractparamdicVO();
		try {
			BeanUtils.populate(coContractparamdicVo, map);
		} catch (Exception e) {
		}
		CoContractparamdic coContractparamdic = CoContractparamdicTransfer.toPO(coContractparamdicVo);
		hibernateDao.saveOrUpdateObject(coContractparamdic);
		coContractparamdicVo = CoContractparamdicTransfer.toVO(coContractparamdic);
		if(map.containsKey("mxVirtualId")){
			coContractparamdicVo.setMxVirtualId(String.valueOf(map.get("mxVirtualId")));
		}
		return coContractparamdicVo;
	}
	
	//更新记录
	private CoContractparamdicVO update(Map<String, ?> map,String poName,String id) {
		
		CoContractparamdicVO coContractparamdicVo = new CoContractparamdicVO();
		//更新操作
		CoContractparamdic po = hibernateDao.getObject(CoContractparamdic.class, id);
		po = (CoContractparamdic) DataTypeTrans.getObjectForMapSet2Po(map, po, CoContractparamdic.class);
		hibernateDao.updateObject(po);

		return coContractparamdicVo;
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
			hibernateDao.update("delete from " + CoContractparamdic.class.getName() + " where sheetid = ?" ,new Object[]{id});
		}
	}
	
	/**
	 * 根据输入条件查询记录
	 * 
	 * @param dicName
	 * @return
	 */
	public QueryResultObject query(String dicName, int pageIndex, int pageSize) {		
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * from co_contractparamdic ccpd where 1=1 ");
		if (!"".equals(dicName) && dicName != null && !"null".equals(dicName)) {
			sql.append(" and ccpd.dic_name like '%"+dicName+"%'");
		}
		sql.append(" order by ccpd.order_no");
		QueryResultObject result = new QueryResultObject();
		
		String sql2 = "SELECT COUNT(*) C FROM ( "+ sql.toString() + " ) T ";		//拼接查询数据条数的sql语句
		List list = hibernateDao.executeSqlQuery(sql2.toString());
		int size = 0;	//定义数据条数
		if(list.size() > 0){	//如果有查询结果，给数据条数变量赋值
			size = Integer.parseInt(list.get(0).toString());
		}
		result.setItemCount(size);	//给返回对象赋数据的数量
		list = hibernateDao.executeSqlQuery(sql.toString(), pageIndex, pageSize);	//查询分页的数据 , pageIndex, pageSize
		List<CoContractparamdicVO> voList = new ArrayList<CoContractparamdicVO>();		//返回的数据结果集
		for(int i = 0;i < list.size();i++){	//遍历要展示的数据
 			Object[] objs = (Object[])list.get(i);	//得到第i条数据
 			CoContractparamdicVO vo = new CoContractparamdicVO();	//数据结果集中的一条数据
 			//给每个变量赋值
 			vo.setSheetid(objs[0] == null ? "" : objs[0].toString());
 			vo.setDicName(objs[1] == null ? "" : objs[1].toString());
 			vo.setDicValue(objs[2] == null ? "" : objs[2].toString());
 			vo.setDicContent(objs[3] == null ? "" : objs[3].toString());
 			vo.setOperateUser(objs[4] == null ? "" : objs[4].toString());
 			vo.setOperateDate(objs[5] == null ? null : DateUtil.getUtilDate(objs[5].toString(), "yyyy-MM-dd"));
 			voList.add(vo);	//把数据添加到结果集中
 		}
		result.setItems(voList);	//给返回对象赋分页查询数据
		return result;	
	}
	private QueryCriteria wrapQuery(RequestCondition queryCondition,
			QueryCriteria qc) {
		List<QueryFilter> wheres = queryCondition.getQueryFilter(CoContractparamdicVO.class);
		if (wheres != null && wheres.size() > 0) {
			CrudUtils.addQCWhere(qc, wheres, CoContractparamdic.class.getName());

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
		CoContractparamdic coContractparamdic = (CoContractparamdic) hibernateDao.getObject(CoContractparamdic.class,id);
		CoContractparamdicVO vo = null;
		if (coContractparamdic != null) {
			vo = CoContractparamdicTransfer.toVO(coContractparamdic);
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
