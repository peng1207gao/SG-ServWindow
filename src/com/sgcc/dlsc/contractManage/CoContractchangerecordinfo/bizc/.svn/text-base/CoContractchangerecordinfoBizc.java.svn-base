package com.sgcc.dlsc.contractManage.CoContractchangerecordinfo.bizc;

import com.sgcc.uap.rest.utils.JsonUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
import com.sgcc.dlsc.commons.util.GetConfigValue;
import com.sgcc.dlsc.contractManage.CoContractchangerecordinfo.vo.CoContractchangerecordinfoTransfer;
import com.sgcc.dlsc.contractManage.CoContractchangerecordinfo.vo.CoContractchangerecordinfoVO;
import com.sgcc.dlsc.entity.po.CoContractchangerecordinfo;
/**
 * 用户定义逻辑构件
 * 
 * @author xiabaike 
 * 
 */
public class CoContractchangerecordinfoBizc implements ICoContractchangerecordinfoBizc{

	@Autowired
	private IHibernateDao hibernateDao;
	
	@Autowired
	private IDataDictionaryBizC dataDictionaryBizC;
	/**
	 * 保存更新
	 * 
	 * @param list
	 */
	 public List<CoContractchangerecordinfoVO> saveOrUpdate(List<Map> list) {
		 
		List<CoContractchangerecordinfoVO> voList = new ArrayList<CoContractchangerecordinfoVO>();
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
			String poName = CoContractchangerecordinfo.class.getName();
			if(map.containsKey("versionid")){
				String id = (String)map.get("versionid");
				CoContractchangerecordinfoVO vo = update(map,poName,id);
				voList.add(vo);
			}else{
				CoContractchangerecordinfoVO coContractchangerecordinfoVO = save(map);
				voList.add(coContractchangerecordinfoVO);
			}
		}
		return voList;
	 }
	 
	//保存记录
	private CoContractchangerecordinfoVO save(Map map) {
		
		CoContractchangerecordinfoVO coContractchangerecordinfoVo = new CoContractchangerecordinfoVO();
		try {
			BeanUtils.populate(coContractchangerecordinfoVo, map);
		} catch (Exception e) {
		}
		CoContractchangerecordinfo coContractchangerecordinfo = CoContractchangerecordinfoTransfer.toPO(coContractchangerecordinfoVo);
		hibernateDao.saveOrUpdateObject(coContractchangerecordinfo);
		coContractchangerecordinfoVo = CoContractchangerecordinfoTransfer.toVO(coContractchangerecordinfo);
		if(map.containsKey("mxVirtualId")){
			coContractchangerecordinfoVo.setMxVirtualId(String.valueOf(map.get("mxVirtualId")));
		}
		return coContractchangerecordinfoVo;
	}
	
	//更新记录
	private CoContractchangerecordinfoVO update(Map<String, ?> map,String poName,String id) {
		
		CoContractchangerecordinfoVO coContractchangerecordinfoVo = new CoContractchangerecordinfoVO();
		//更新操作
		try {
			BeanUtils.populate(coContractchangerecordinfoVo, map);
		} catch (Exception e) {
		}
		Object[] objArray = CrudUtils.generateHql(CoContractchangerecordinfo.class, map, "versionid");
		hibernateDao.update((String) objArray[0], (Object[]) objArray[1]);
		return coContractchangerecordinfoVo;
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
			hibernateDao.update("delete from " + CoContractchangerecordinfo.class.getName() + " where versionid = ?" ,new Object[]{id});
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
		List<CoContractchangerecordinfo> result = null;
//		int count = 0;
//		qc.addFrom(CoContractchangerecordinfo.class);
//		if (queryCondition != null) {
//			qc = wrapQuery(queryCondition, qc);
//			count = getRecordCount(qc);
//			qc = wrapPage(queryCondition, qc);
//			result = hibernateDao.findAllByCriteria(qc);
//
//		} else {
//			result = hibernateDao.findAllByCriteria(qc);
//			count = getRecordCount(qc);
//		}
		return RestUtils.wrappQueryResult(result, 0).addDicItems(wrapDictList());
		
		
	}
	private QueryCriteria wrapQuery(RequestCondition queryCondition,
			QueryCriteria qc) {
		List<QueryFilter> wheres = queryCondition.getQueryFilter(CoContractchangerecordinfoVO.class);
		if (wheres != null && wheres.size() > 0) {
			CrudUtils.addQCWhere(qc, wheres, CoContractchangerecordinfo.class.getName());

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
		CoContractchangerecordinfo coContractchangerecordinfo = (CoContractchangerecordinfo) hibernateDao.getObject(CoContractchangerecordinfo.class,id);
		CoContractchangerecordinfoVO vo = null;
		if (coContractchangerecordinfo != null) {
			vo = CoContractchangerecordinfoTransfer.toVO(coContractchangerecordinfo);
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
	// 将字典对象封装为list
			private List<DicItems> wrapDictList() {
				List<DicItems> dicts = new ArrayList<DicItems>();

				dicts.add(translateFromFile("signstate"));
				dicts.add(translateFromFile1("backupstate"));
				return dicts;
			}
			// 从属性文件中查询字典
			private DicItems translateFromFile(String fieldName) {
				List<Map<String, String>> list = new ArrayList();
				Object[] obj = new Object[1];
				StringBuffer sql = new StringBuffer();
				sql.append(" select value, name from ba_gencode where type ='24'  ");
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
			// 从属性文件中查询字典
			private DicItems translateFromFile1(String fieldName) {
				List<Map<String, String>> list = new ArrayList();
				Object[] obj = new Object[1];
				StringBuffer sql = new StringBuffer();
				sql.append(" select value, name from ba_gencode where type ='26'   ");
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

			public QueryResultObject queryFor(String keyField, String marketId,
					String pageId, String orderField, String type,
					String objectPO, String keyValue) {
				StringBuffer sql = new StringBuffer("SELECT BDC.SHEETID,BDC.ITEM_FIELD_ID,BIC.DISPLAYITEMNAME,BDC.ITEM_FIELD_NAME,BDC.DATA_URL,BDC.DATA_SELECT FROM BA_DISPLAYITEMCONFIG BDC")
				.append(" LEFT JOIN BA_INTERFACECONFIG BIC ON BDC.SHEETID = BIC.DISPLAYITEMID AND BIC.MARKETID = ? AND BIC.COTYPE = ? ")
				.append(" WHERE (BIC.ISDISPLAY = 1 OR BDC.IS_REQUIRED_DISPLAY = 1) AND BDC.ITEM_FIELD_ID IS NOT NULL AND BDC.PAGE_ID = ? ORDER BY BDC.ORDERBY ASC");
				
				List list = hibernateDao.executeSqlQuery(sql.toString(), new Object[]{marketId, type, pageId});
				if("69486820-5BAB-D177-FH".equals(pageId)){
					list.add(new Object[]{"guid_1", "updatePersonId", "信息更新人", "信息更新人", null, null});
					list.add(new Object[]{"guid_2", "updateTime", "信息更新时间", "信息更新时间", null, null});
				}else if ("69486820-5BAB-D177-WLJZ".equals(pageId)) {
					list.add(new Object[]{"guid_2", "updatetime", "信息更新时间", "信息更新时间", null, null});
				}else{
					list.add(new Object[]{"guid_1", "updatepersonid", "信息更新人", "信息更新人", null, null});
					list.add(new Object[]{"guid_2", "updatetime", "信息更新时间", "信息更新时间", null, null});
				}
				
				StringBuffer objectField = new StringBuffer();
				for (int i = 0; i < list.size(); i++) {
					Object[] objs = (Object[]) list.get(i);
					//判断字段信息为汉字时，加引号，liling
					Pattern p_str = Pattern.compile("[\\u4e00-\\u9fa5]+");
					String str = objs[1]==null?"":objs[1].toString();
					Matcher m = p_str.matcher(str);
					if(m.find()&&m.group(0).equals(str)){
						objectField.append("'"+objs[1]+"'").append(",");//汉字的加单引号
					}
					else{
						objectField.append(objs[1]).append(",");
					}
				}
				String hql = "select "+objectField.substring(0,objectField.length()-1)+" from "+objectPO+" t where t."+keyField+"=? order by t."+orderField+" asc";
				
				List list1 = hibernateDao.executeSqlQuery(hql, new Object[]{keyValue});
				
				String json = "{\"rows\":";
				//数据集合
				List<Map<String, String>> dataList = new ArrayList<Map<String,String>>();
				for (int i = 0; i < list1.size(); i++) {
					//查询数据
					Object[] objs = (Object[]) list1.get(i);
					Map<String, String> fildMap = new HashMap<String, String>();
					//将值与字段对应存入Map
					for (int j = 0; j < objs.length; j++) {
						//对象属性
						Object[] fds = (Object[]) list.get(j);
						if (i==0) {
							//第一条数据不做比较
							//fildMap.put(fds[1].toString(), ToolsSys.ObjIsNullToStr(objs[j]));
							fildMap.put(fds[1].toString(), getDisplayText(objs[j], fds[4], fds[5]));
						}else{
							//上一条数据
							Object[] objs_1 = (Object[]) list1.get(i-1);
							String new_val = ObjIsNullToStr(objs[j]);//此条数据
							String old_val = ObjIsNullToStr(objs_1[j]);//上一条数据
							//对比历史数据，不同则标红，相同则去上一条中的显示值，减少数据库读取次数
							String val = new_val.equals(old_val) ? dataList.get(i-1).get(fds[1].toString()).replaceAll("style=\"color:red;\"", "") : "<span style=\"color:red;\">"+getDisplayText(new_val, fds[4], fds[5])+"</span>";
							fildMap.put(fds[1].toString(), val);
						}
						
					}
					dataList.add(fildMap);
				}
//				json = json + JsonUtils.toJsonArrayString(list);
				return null;
			}
//			public static String toJsonArrayString(List list, JsonConfig config) {
//				JSONArray jsonArray = JSONArray.fromObject(list, config);
//				return jsonArray.toString();
//			}
			/**
			 * 根据Id获取显示名字的主方法
			 * @description 方法描述
			 * @param value
			 * @param dataUrl
			 * @param dataSelect
			 * @return
			 * @author wanshulu
			 * @date 2013-8-5
			 */
			private String getDisplayText(Object value, Object dataUrl, Object dataSelect){
				//data_url是否为空
				if (dataUrl != null) {
					//执行ID转换显示值
					return getValueNameByUrl(ObjIsNullToStr(dataUrl), ObjIsNullToStr(value));
				}
				//data_select是否为空
				if (dataSelect != null){
					//执行ID转换显示值
					return getValueNameBySel(ObjIsNullToStr(dataSelect), ObjIsNullToStr(value));
				}
				return ObjIsNullToStr(value);
			}
			private String getValueNameBySel(String dataSelect, String value){
				if (!isnull(dataSelect) || !isnull(value)) {
					return "";
				}
				//根据逗号分割出sql中需要的表名、字段名等
				String[] sel = dataSelect.split(",");//分割下拉框数据字段
				StringBuffer sql = new StringBuffer("SELECT T.").append(sel[1]).append(" FROM ").append(sel[0])
						.append(" T WHERE T.").append(sel[2]).append("=?");
				//如果长度为4说明有其他过滤条件续加
				if (sel.length>=4) {
					sql.append(" AND ").append(sel[3]);
				}
				List<Object> list = hibernateDao.executeSqlQuery(sql.toString(), new Object[]{value});
				if (list == null || list.size() <= 0) {
					return "";
				}
				return ObjIsNullToStr(list.get(0));
			}
			public static String ObjIsNullToStr(Object obj) {
				return (obj == null || "".equals(obj)) ? "" : obj.toString();
			}
			/**
			 * 
			 * @description 方法描述
			 * @param dataUrl
			 * @param value
			 * @return
			 * @author wanshulu
			 * @date 2013-8-5
			 */
			@SuppressWarnings("unchecked")
			private String getValueNameByUrl(String dataUrl, String value){
				if (!isnull(dataUrl) || !isnull(value)) {
					return "";
				}
				//截取Url作为Key查询对应Sql
				String key = dataUrl.substring(dataUrl.lastIndexOf("/")+1, dataUrl.indexOf("."));
				//根据Key读取sql
				String sql = getParamsValue(key, "urlToSql.properties");
				if (sql == null || "".equals(sql)) {
					return "";
				}
				//执行sql得到对应显示值
				List<Object> list = hibernateDao.executeSqlQuery(sql, new Object[]{value});
				if (list == null || list.size() <= 0) {
					return "";
				}
				return ObjIsNullToStr(list.get(0));
			}
			public static Boolean isnull(String str) {
				if (str == null || "".equals(str)) {
					return false;
				}
				return true;
			}
			
			/**
			 * 自定义配置文件读取
			 * @description 方法描述
			 * @param key key值
			 * @param propertiesFileName 配置文件名称，带扩展名
			 * @return
			 * @author wanshulu
			 * @date 2013-8-5
			 */
			public static String getParamsValue(String key, String propertiesFileName){
				Properties config = null;
				InputStream in = GetConfigValue.class.getClassLoader().getResourceAsStream(propertiesFileName);
				config = new Properties();
				try {
					config.load(in);
					in.close();
				} catch (IOException e) {
						System.out.println("No global.properties defined error");
				}
				//得到key对应的value值
				String value = config.getProperty(key);
				return value;
			}
}
