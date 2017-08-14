package com.sgcc.dlsc.txtManage.tempfieldconfig.bizc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.sgcc.dlsc.commons.util.DataTypeTrans;
import com.sgcc.dlsc.commons.util.DateUtil;
import com.sgcc.dlsc.entity.po.CoContracttypeinfo;
import com.sgcc.dlsc.entity.po.CoTempclienfieldconfig;
import com.sgcc.dlsc.txtManage.tempfieldconfig.vo.CoTempclienfieldconfigTransfer;
import com.sgcc.dlsc.txtManage.tempfieldconfig.vo.CoTempclienfieldconfigVO;
import com.sgcc.dlsc.txtManage.templatemanage.vo.CoContracttemplateVO;
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
 * @author mengke 
 * 
 */
public class TempfieldconfigBizc implements ITempfieldconfigBizc{

	@Autowired
	private IHibernateDao hibernateDao;
	
	@Autowired
	private IDataDictionaryBizC dataDictionaryBizC;
	/**
	 * 保存更新
	 * 
	 * @param list
	 */
	 public List<CoTempclienfieldconfigVO> saveOrUpdate(List<Map> list,String marketId,String userId) {
		 
		List<CoTempclienfieldconfigVO> voList = new ArrayList<CoTempclienfieldconfigVO>();
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
			map.put("marketid", marketId);
			map.put("personid", userId);
			String poName = CoTempclienfieldconfig.class.getName();
			if(map.containsKey("sheetid")){
				String id = (String)map.get("sheetid");
				map.put("updatedate", DateUtil.getNowDate("yyyy-MM-dd HH:mm:ss"));
				CoTempclienfieldconfigVO vo = update(map,poName,id);
				voList.add(vo);
			}else{
				map.put("updatedate", DateUtil.getUtilDate(DateUtil.getNowDate("yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss"));
				CoTempclienfieldconfigVO coTempclienfieldconfigVO = save(map);
				voList.add(coTempclienfieldconfigVO);
			}
		}
		return voList;
	 }
	 
	//保存记录
	private CoTempclienfieldconfigVO save(Map map) {
		
		CoTempclienfieldconfigVO coTempclienfieldconfigVo = new CoTempclienfieldconfigVO();
		try {
			BeanUtils.populate(coTempclienfieldconfigVo, map);
		} catch (Exception e) {
		}
		CoTempclienfieldconfig coTempclienfieldconfig = CoTempclienfieldconfigTransfer.toPO(coTempclienfieldconfigVo);
		hibernateDao.saveOrUpdateObject(coTempclienfieldconfig);
		coTempclienfieldconfigVo = CoTempclienfieldconfigTransfer.toVO(coTempclienfieldconfig);
		if(map.containsKey("mxVirtualId")){
			coTempclienfieldconfigVo.setMxVirtualId(String.valueOf(map.get("mxVirtualId")));
		}
		return coTempclienfieldconfigVo;
	}
	
	//更新记录
	private CoTempclienfieldconfigVO update(Map<String, ?> map,String poName,String id) {
		
		CoTempclienfieldconfigVO coTempclienfieldconfigVo = new CoTempclienfieldconfigVO();
		//更新操作
//		try {
//			BeanUtils.populate(coTempclienfieldconfigVo, map);
//		} catch (Exception e) {
//		}
//		Object[] objArray = CrudUtils.generateHql(CoTempclienfieldconfig.class, map, "sheetid");
//		hibernateDao.update((String) objArray[0], (Object[]) objArray[1]);
		CoTempclienfieldconfig po = hibernateDao.getObject(CoTempclienfieldconfig.class, id);
		po = (CoTempclienfieldconfig) DataTypeTrans.getObjectForMapSet2Po(map, po, CoTempclienfieldconfig.class);
		hibernateDao.updateObject(po);
		return coTempclienfieldconfigVo;
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
			hibernateDao.update("delete from " + CoTempclienfieldconfig.class.getName() + " where sheetid = ?" ,new Object[]{id});
		}
	}
	
	/**
	 * 根据输入条件查询记录
	 * 
	 * @param queryCondition
	 * @return
	 */
	public QueryResultObject query(RequestCondition queryCondition,String marketId) {
		List<QueryFilter> filterList = queryCondition.getQueryFilter(CoTempclienfieldconfigVO.class);
		String contractTemp = "";//合同范本
		String field = "";//对应字段
		if(filterList!=null&&filterList.size()>0){
			for(int i=0;i<filterList.size();i++){
				QueryFilter filter = filterList.get(i);
				String colName = filter.getFieldName();
				if(colName.equals("contracttemplatecode")){
					contractTemp = ((filter.getValue()==null||filter.getValue().equals("null"))?"":filter.getValue().toString());
				}else if(colName.equals("configFieldName")){
					field = ((filter.getValue()==null||filter.getValue().equals("null"))?"":filter.getValue().toString());
				}
			}
		}
		List<CoTempclienfieldconfigVO> result = new ArrayList<CoTempclienfieldconfigVO>();
		List list = new ArrayList();//存放查询结果
		List list1 = new ArrayList();//存放查询条数
		List paramsList = new ArrayList();//存放查询参数
		int count = 0;//查询结果数
		StringBuffer sql = new StringBuffer();
		sql.append("		select ctcf.sheetid,                                      ")
		.append("		       ctcc.field_Name,                                       ")
		.append("		       ctcf.config_Field_Name,                                ")
		.append("		       cct.contracttemplatename                               ")
		.append("		  from Co_Tempcliencanfieldconfig ctcc,                       ")
		.append("		       Co_Tempclienfieldconfig    ctcf,                       ")
		.append("		       Co_Contemplatewithparam    cct                         ")
		.append("		 where ctcc.sheetid = ctcf.can_Sheetid                        ")
		.append("		   and ctcf.contracttemplatecode = cct.contracttemplatecode   ")
		.append("		   and ctcf.marketid = ?                                      ");
		paramsList.add(marketId);
		if(!"".equals(contractTemp)&&contractTemp!=null){
			sql.append(" and ctcf.contracttemplatecode = ? ");
			paramsList.add(contractTemp);
		}else if(!"".equals(field)&&field!=null){
			sql.append(" and ctcf.config_Field_Name like ? ");
			field = "%"+field+"%";
			paramsList.add(field);
		}
		sql.append(" order by ctcf.contracttemplatecode ");
		String sql2 = "SELECT COUNT(*) FROM ("+sql.toString()+") TS ";
		
		list1 = hibernateDao.executeSqlQuery(sql2, paramsList.toArray());
		list = hibernateDao.executeSqlQuery(sql.toString(),paramsList.toArray(),
			Integer.parseInt(queryCondition.getPageIndex()),Integer.parseInt(queryCondition.getPageSize()));
		count = list1.size()>0 ? Integer.parseInt(list1.get(0).toString()) : 0;//查询结果总数
		if(list!=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				CoTempclienfieldconfigVO vo = new CoTempclienfieldconfigVO();
				Object[] obj = (Object[])list.get(i);
				vo.setSheetid(obj[0]==null?"":obj[0].toString());
				vo.setCanSheetid(obj[1]==null?"":obj[1].toString());
				vo.setConfigFieldName(obj[2]==null?"":obj[2].toString());
				vo.setContracttemplatecode(obj[3]==null?"":obj[3].toString());
				result.add(vo);
			}
		}
		return RestUtils.wrappQueryResult(result, count);
	}
	
	private QueryCriteria wrapQuery(RequestCondition queryCondition,
			QueryCriteria qc) {
		List<QueryFilter> wheres = queryCondition.getQueryFilter(CoTempclienfieldconfigVO.class);
		if (wheres != null && wheres.size() > 0) {
			CrudUtils.addQCWhere(qc, wheres, CoTempclienfieldconfig.class.getName());

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
		CoTempclienfieldconfig coTempclienfieldconfig = (CoTempclienfieldconfig) hibernateDao.getObject(CoTempclienfieldconfig.class,id);
		CoTempclienfieldconfigVO vo = null;
		if (coTempclienfieldconfig != null) {
			vo = CoTempclienfieldconfigTransfer.toVO(coTempclienfieldconfig);
		}
		return RestUtils.wrappQueryResult(vo).addDicItems(wrapDictList());
	}
	
	/**
	 * 
	 * @description 可配置字段数据字典
	 * @return
	 * @author mengke
	 * @date 2014-5-27
	 */
	private List<DicItems> wrapDictList() {
		List<DicItems> dicts = new ArrayList<DicItems>();
        dicts.add(getZd("canSheetid","SGBIZ.CO_CONTRACTBASEINFO","CO_CONTRACTBASEINFO"));
		return dicts;
	}
		
	/**
	 * 
	 * @description 获取可配置字段
	 * @param fieldName
	 * @param tableId1
	 * @param tableId2
	 * @return
	 * @author mengke
	 * @date 2014-5-27
	 */
	private DicItems getZd(String fieldName, String tableId1,String tableId2) {
		List<Map<String, String>> list = new ArrayList();
		Object[] obj = new Object[]{tableId1,tableId2};
		StringBuffer sql = new StringBuffer();
		sql.append("select c.sheetid,c.field_name from Co_Tempcliencanfieldconfig c where c.table_Id = ? or c.table_id = ? ");
		List list1 = hibernateDao.executeSqlQuery(sql.toString(), obj);
		if(list1!=null && list1.size()>0){
			for(int i=0;i<list1.size();i++){
				Map<String,String> map = new HashMap<String,String>();
				Object[] objs = (Object[])list1.get(i);
				map.put("value", objs[0]==null?"":objs[0].toString());
				map.put("text", objs[1]==null?"":objs[1].toString());
				list.add(map);
			}
		}
		DicItems dict = new DicItems();
		dict.setName(fieldName);
		dict.setValues(list);
		return dict;
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
