package com.sgcc.dlsc.contractManage.contractaffixinfo.bizc;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.sgcc.dlsc.commons.util.ComUtils;
import com.sgcc.dlsc.commons.util.DateUtil;
import com.sgcc.dlsc.contractManage.contractaffixinfo.vo.CoContractaffixinfoTransfer;
import com.sgcc.dlsc.contractManage.contractaffixinfo.vo.CoContractaffixinfoVO;
import com.sgcc.dlsc.entity.po.CoContractaffixinfo;
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
public class ContractaffixinfoBizc implements IContractaffixinfoBizc{

	@Autowired
	private IHibernateDao hibernateDao;
	
	@Autowired
	private IDataDictionaryBizC dataDictionaryBizC;
	/**
	 * 保存更新
	 * 
	 * @param list
	 */
	 public List<CoContractaffixinfoVO> saveOrUpdate(List<Map> list) {
		 
		List<CoContractaffixinfoVO> voList = new ArrayList<CoContractaffixinfoVO>();
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
			String poName = CoContractaffixinfo.class.getName();
			if(map.containsKey("guid")){
				String id = (String)map.get("guid");
				CoContractaffixinfoVO vo = update(map,poName,id);
				voList.add(vo);
			}else{
				CoContractaffixinfoVO coContractaffixinfoVO = save(map);
				voList.add(coContractaffixinfoVO);
			}
		}
		return voList;
	 }
	 
	//保存记录
	private CoContractaffixinfoVO save(Map map) {
		
		CoContractaffixinfoVO coContractaffixinfoVo = new CoContractaffixinfoVO();
		try {
			//从页面传过来的时间是String类型的，需要转换成Date类型，与VO中的类型对应
			String uploadtime = map.get("uploadtime")==null?DateUtil.getNowDate("yyyy-MM-dd"):map.get("uploadtime").toString();
			Date date = DateUtil.getUtilDate(uploadtime, "yyyy-mm-dd");
			map.remove("uploadtime");
			map.put("uploadtime", date);
			BeanUtils.populate(coContractaffixinfoVo, map);
		} catch (Exception e) {
		}
		CoContractaffixinfo coContractaffixinfo = CoContractaffixinfoTransfer.toPO(coContractaffixinfoVo);
		hibernateDao.saveOrUpdateObject(coContractaffixinfo);
		coContractaffixinfoVo = CoContractaffixinfoTransfer.toVO(coContractaffixinfo);
		if(map.containsKey("mxVirtualId")){
			coContractaffixinfoVo.setMxVirtualId(String.valueOf(map.get("mxVirtualId")));
		}
		return coContractaffixinfoVo;
	}
	
	//更新记录
	private CoContractaffixinfoVO update(Map<String, ?> map,String poName,String id) {
		
		CoContractaffixinfoVO coContractaffixinfoVo = new CoContractaffixinfoVO();
		//更新操作
		try {
			BeanUtils.populate(coContractaffixinfoVo, map);
		} catch (Exception e) {
		}
		Object[] objArray = CrudUtils.generateHql(CoContractaffixinfo.class, map, "guid");
		hibernateDao.update((String) objArray[0], (Object[]) objArray[1]);
		return coContractaffixinfoVo;
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
			hibernateDao.update("delete from " + CoContractaffixinfo.class.getName() + " where guid = ?" ,new Object[]{id});
		}
	}
	
	/**
	 * 根据输入条件查询记录
	 * 
	 * @param queryCondition
	 * @return
	 */
	public QueryResultObject query(RequestCondition queryCondition) {

		List<QueryFilter> filterList = queryCondition.getQueryFilter(CoContractaffixinfoVO.class);
		String contractid = "";//合同id
		if(filterList != null && filterList.size() > 0){
			for(int i=0;i<filterList.size();i++){
				QueryFilter filter = filterList.get(i);
				String colName = filter.getFieldName();
				if( colName.equals("contractid")){//获取合同id
					contractid = (filter.getValue()==null?"":filter.getValue().toString());
				}
			}
		}
		List<CoContractaffixinfoVO> result = new ArrayList<CoContractaffixinfoVO>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT T.AFFIXNAME,T.UPLOADPERSON,T.UPLOADTIME,T.DESCRIPTION,")
		.append("S.NAME,T.GUID,T.MARKETID,T.CONTRACTID ")
		.append("FROM CO_CONTRACTAFFIXINFO T LEFT JOIN BA_GENCODE S ")
		.append("ON T.AFFIXTYPE = S.VALUE WHERE S.TYPE = '108' AND T.CONTRACTID = ?");
		
		int count = 0;//查询结果条数
		Object[] objs = new Object[]{contractid};
		
		String sql2 = "SELECT COUNT(*) FROM ("+sql.toString()+") TT ";
		List list = hibernateDao.executeSqlQuery(sql2, objs);
		count = list.size() > 0 ? Integer.parseInt(list.get(0).toString()) : 0;
		list = hibernateDao.executeSqlQuery(sql.toString(), objs,Integer.parseInt(queryCondition.getPageIndex()),Integer.parseInt(queryCondition.getPageSize()));
		if(list!=null && list.size()>0){//封装VO
			for(int i=0;i<list.size();i++){
				Object[] obj = (Object[])list.get(i);
				CoContractaffixinfoVO ct = new CoContractaffixinfoVO();
				ct.setAffixname(obj[0]==null?"":obj[0].toString());
				ct.setUploadperson(obj[1]==null?"":obj[1].toString());
				ct.setUploadtime(obj[2]==null?null:DateUtil.getUtilDate(obj[2].toString(),"yyyy-MM-dd"));
				ct.setDescription(obj[3]==null?"":obj[3].toString());
				ct.setAffixtype(obj[4]==null?"":obj[4].toString());
				ct.setGuid(obj[5]==null?"":obj[5].toString());
				ct.setMarketid(obj[6]==null?"":obj[6].toString());
				ct.setContractid(obj[7]==null?"":obj[7].toString());
				result.add(ct);
			}
		}
		return RestUtils.wrappQueryResult(result, count);
	}
	
	private QueryCriteria wrapQuery(RequestCondition queryCondition,
			QueryCriteria qc) {
		List<QueryFilter> wheres = queryCondition.getQueryFilter(CoContractaffixinfoVO.class);
		if (wheres != null && wheres.size() > 0) {
			CrudUtils.addQCWhere(qc, wheres, CoContractaffixinfo.class.getName());

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
	public QueryResultObject queryById(String id,String uploadPerson,String uploadTime,String marketId) {
//		CoContractaffixinfo coContractaffixinfo = (CoContractaffixinfo) hibernateDao.getObject(CoContractaffixinfo.class,id);
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT T.MARKETID,T.CONTRACTID,T.AFFIXNO,T.GUID,T.AFFIXNAME,")
		.append("T.PAPERCONTRACTFILE,T.UPLOADPERSON,T.UPLOADTIME,T.DESCRIPTION,T.AFFIXTYPE ")
		.append("FROM CO_CONTRACTAFFIXINFO T ")
		.append("WHERE T.CONTRACTID = ?");
		Object[] obj = new Object[]{id};
		List list = hibernateDao.executeSqlQuery(sql.toString(), obj);
		List<CoContractaffixinfoVO> result = new ArrayList<CoContractaffixinfoVO>();
		if (list != null && list.size()>0) {
			for(int i=0;i<list.size();i++){
				Object[] objs = (Object[])list.get(i);
				CoContractaffixinfoVO coContractaffixinfo = new CoContractaffixinfoVO();
				coContractaffixinfo.setMarketid(objs[0]==null?"":objs[0].toString());
				coContractaffixinfo.setContractid(objs[1]==null?"":objs[1].toString());
				coContractaffixinfo.setAffixno(objs[2]==null?null:new BigDecimal(objs[2].toString()));
//				coContractaffixinfo.setGuid(ComUtils.createGUID());
//				coContractaffixinfo.setAffixname(objs[4]==null?"":objs[4].toString());
//				coContractaffixinfo.setPapercontractfile(null);
				coContractaffixinfo.setUploadperson(uploadPerson);
				coContractaffixinfo.setUploadtime(DateUtil.getUtilDate(uploadTime, "yyyy-MM-dd"));
//				coContractaffixinfo.setDescription(objs[8]==null?"":objs[8].toString());
//				coContractaffixinfo.setAffixtype(objs[9]==null?null:new BigDecimal(objs[9].toString()));
//				vo = CoContractaffixinfoTransfer.toVO(coContractaffixinfo);
				result.add(coContractaffixinfo);
			}
		}else{
			CoContractaffixinfoVO coContractaffixinfo = new CoContractaffixinfoVO();
			coContractaffixinfo.setMarketid(marketId);
			coContractaffixinfo.setContractid(id);
			coContractaffixinfo.setAffixno(new BigDecimal("1"));
			coContractaffixinfo.setUploadperson(uploadPerson);
			coContractaffixinfo.setUploadtime(DateUtil.getUtilDate(uploadTime, "yyyy-MM-dd"));
			result.add(coContractaffixinfo);
		}
		return RestUtils.wrappQueryResult(result).addDicItems(wrapDictList());
	}

	/**
	 * 创建附件类型数据字典
	 * @description 方法描述
	 * @return
	 * @author mengke
	 * @date 2014-2-15
	 */
	private List<DicItems> wrapDictList() {
		List<DicItems> dicts = new ArrayList<DicItems>();

        dicts.add(queryAffixType("affixtype","108"));
		return dicts;
	}
	
	/**
	 * 查询附件类型数据字典
	 * @description 方法描述
	 * @param fieldName
	 * @param type
	 * @return
	 * @author mengke
	 * @date 2014-2-15
	 */
	private DicItems queryAffixType(String fieldName,String type){
		List<Map<String, String>> list = new ArrayList();
		Object[] obj = new Object[]{type};
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT T.VALUE,T.NAME FROM BA_GENCODE T WHERE T.TYPE = ?");
		List list1=  hibernateDao.executeSqlQuery(sql.toString(),obj);
		for(int i=0;i<list1.size();i++){
			Map<String, String> map = new HashMap();
			Object[] robj = (Object[])list1.get(i);
			String value = robj[0]==null?"":robj[0].toString();//实际值
			String name = robj[1]==null?"":robj[1].toString();//显示值
			map.put("value", value);
			map.put("text", name);
			list.add(map);
		}
		DicItems dict = new DicItems();//封装数据字典
		dict.setName(fieldName);
		dict.setValues(list);
		return dict;
	}
	
	/**
	 * 附件下载
	 * @description 方法描述
	 * @param guid
	 * @param request
	 * @param response
	 * @return
	 * @author mengke 
	 * @date 2014-2-16
	 */
	public String downAffix(String guid,HttpServletRequest request,
			HttpServletResponse response){
		String finStr = "false";
		System.out.println(guid);
		try{  
			StringBuffer sql = new StringBuffer();
			sql.setLength(0);
			sql.append("SELECT S.ATTACHMENT_ID,S.ATT_NAME,S.ATT_SIZE,S.ATT_FILE ")
			.append("FROM T_MX_ATTACHMENT S WHERE S.PK_VAL = ?");//uap中会将上传的附件插入到表T_MX_ATTACHMENT中，此表的PK_VAL字段与合同附件表的guid对应
			Object[] obj = new Object[]{guid};
			List cxlist = hibernateDao.executeSqlQuery(sql.toString(),obj);//查询附件
			if(cxlist==null || cxlist.size()==0){
				sql.setLength(0);
				sql.append("SELECT T.GUID,T.AFFIXNAME,T.AFFIXNO,T.PAPERCONTRACTFILE FROM CO_CONTRACTAFFIXINFO T WHERE T.GUID = ?");
				cxlist = hibernateDao.executeSqlQuery(sql.toString(), obj);
			}
			for (int i = 0; i < cxlist.size(); i++) {
				Object[] ydArr = (Object[]) cxlist.get(0);
				String filename = ydArr[1].toString();//附件名称
				InputStream in = null;
				long length = 0;
				if(ydArr[3]!=null){
					Blob blob = (Blob) ydArr[3];//获取附件
					try {
						in = blob.getBinaryStream();
						length = blob.length();//附件长度
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				
				// 设置response的编码方式
				response.setContentType("application/x-msdownload");
				// 写明要下载的文件的大小
				response.setContentLength((int) length);
				// 解决中文乱码
				response.setHeader("Content-Disposition", "attachment;filename="+ new String(filename.getBytes("gbk"), "iso-8859-1"));
				// 读出文件到i/o流
				BufferedInputStream buff = new BufferedInputStream(in);
				byte[] b = new byte[1024];// 相当于我们的缓存
				long k = 0;// 该值用于计算当前实际下载了多少字节
				// 从response对象中得到输出流,准备下载
				OutputStream myout = response.getOutputStream();
				
				// 开始循环下载
				while (k < length) {
					int j = buff.read(b, 0, 1024);
					k += j;
					// 将b中的数据写到客户端的内存
					myout.write(b, 0, j);
				}
				// 将写入到客户端的内存的数据,刷新到磁盘
				myout.flush();
				myout.close();
				finStr = "success";
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return finStr;
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
