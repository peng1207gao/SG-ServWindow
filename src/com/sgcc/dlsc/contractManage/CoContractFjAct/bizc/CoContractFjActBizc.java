package com.sgcc.dlsc.contractManage.CoContractFjAct.bizc;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.sgcc.dlsc.commons.util.DateUtil;
import com.sgcc.dlsc.commons.util.ToolsSys;
import com.sgcc.dlsc.contractManage.CoContractFjAct.vo.CoContractaffixinfoTransfer;
import com.sgcc.dlsc.contractManage.CoContractFjAct.vo.CoContractaffixinfoVO;
import com.sgcc.dlsc.contractManage.coContractbaseinfoV2Act.vo.CoContractbaseinfoVO;
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
 * @author Administrator 
 * 
 */
public class CoContractFjActBizc implements ICoContractFjActBizc{

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
	 
	 //下载附件
	 public String downAffix(String guid,
				HttpServletRequest request, HttpServletResponse response) {
			String finStr = "false";
			try{
				String sql = "select attachment_id,att_size,att_name,att_file from T_MX_ATTACHMENT where pk_val = ? ";//and att_name like '%"+filename+"%'
				List cxlist = hibernateDao.executeSqlQuery(sql,new Object[]{guid});
				if(cxlist==null || cxlist.size()==0){
					sql = "SELECT T.GUID,T.AFFIXNO,T.AFFIXNAME,T.PAPERCONTRACTFILE FROM CO_CONTRACTAFFIXINFO T WHERE T.GUID = ?";
					cxlist = hibernateDao.executeSqlQuery(sql,new Object[]{guid});
				}
				for (int i = 0; i < cxlist.size(); i++) {
					Object[] ydArr = (Object[]) cxlist.get(0);
					String filename = ydArr[2].toString();
					InputStream in = null;
					long length = 0;
					if(ydArr[3]!=null){
						Blob blob = (Blob) ydArr[3];
						try {
							in = blob.getBinaryStream();
							length = blob.length();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					
					// 设置response的编码方式
					response.setContentType("application/x-msdownload");
					// 写明要下载的文件的大小
					response.setContentLength((int) length);
					// 解决中文乱码
					response.setHeader("Content-Disposition", "attachment;filename="+ new String(filename.getBytes("gbk"), "iso-8859-1"));//new String(filename.getBytes("utf-8"), "iso-8859-1"))
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
					finStr = "true";
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			return finStr;
		}

	 
	//保存记录
	private CoContractaffixinfoVO save(Map map) {
		
		CoContractaffixinfoVO coContractaffixinfoVo = new CoContractaffixinfoVO();
		try {
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
	  * 合同关联附件-查询
	 * @param pageIndex(第几页)
	 * @param pageSize(总共几页)
	 * @param contractid
	 * @return
	 */
	public QueryResultObject findAffList(int pageIndex,int pageSize,
			String contractid) {
		
		QueryResultObject result = new QueryResultObject();//新建返回值对象
		int size = 0;	//定义数据条数
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append(" select t.guid,t.contractid,t.uploadperson,to_char(t.uploadtime,'YYYY-MM-DD'),t.description,t.affixtype,TT.name,t.affixname from  CO_ContractAffixInfo t  ");
		sql.append(" LEFT JOIN ba_gencode TT ON TT.TYPE='108'  AND t.affixtype = TT.VALUE ");
		if(ToolsSys.isnull(contractid)){
			sql.append(" where t.contractid ='").append(contractid).append("' ");
		}
		String sql2 = "SELECT COUNT(*) C FROM ( "+ sql + " ) T ";		//拼接查询数据条数的sql语句
		List list = hibernateDao.executeSqlQuery(sql2.toString());
		if(list.size() > 0){	//如果有查询结果，给数据条数变量赋值
			size = Integer.parseInt(list.get(0).toString());
		}
		result.setItemCount(size);	//给返回对象赋数据的数量
		list = hibernateDao.executeSqlQuery(sql.toString(),pageIndex,pageSize);	//查询分页的数据
		
		List<CoContractaffixinfoVO> voList = new ArrayList<CoContractaffixinfoVO>();		//返回的数据结果集
		for(int i = 0;i < list.size();i++){	//遍历要展示的数据
			Object[] objs = (Object[])list.get(i);	//得到第i条数据
			CoContractaffixinfoVO vo = new CoContractaffixinfoVO();	//数据结果集中的一条数据
			//给每个变量赋值
			vo.setGuid(objs[0] == null ? "" : objs[0].toString());
			vo.setContractid(objs[1] == null ? "" : objs[1].toString());
			vo.setUploadperson(objs[2] == null ? "" : objs[2].toString());
			//vo.setUploadtime(DateUtil.getUtilDate(objs[3].toString(), "yyyy-MM-dd"));
			vo.setUploadtimestring(objs[3] == null ? "" : objs[3].toString());
			vo.setDescription(objs[4] == null ? "" : objs[4].toString());
			vo.setAffixtypename(objs[6] == null ? "" : objs[6].toString());
			vo.setAffixname(objs[7] == null ? "" : objs[7].toString());
			voList.add(vo);	//把数据添加到结果集中
		}
		result.setItems(voList);	//给返回对象赋分页查询数据
		
		return result;	//返回返回值
		
	}
	
	/**
	 * 根据输入条件查询记录
	 * 
	 * @param queryCondition
	 * @return
	 */
	public QueryResultObject query(RequestCondition queryCondition) {

		QueryCriteria qc = new QueryCriteria();
		List<CoContractaffixinfo> result = null;
		int count = 0;
		qc.addFrom(CoContractaffixinfo.class);
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
	public QueryResultObject queryById(String id) {
		CoContractaffixinfo coContractaffixinfo = (CoContractaffixinfo) hibernateDao.getObject(CoContractaffixinfo.class,id);
		CoContractaffixinfoVO vo = null;
		if (coContractaffixinfo != null) {
			vo = CoContractaffixinfoTransfer.toVO(coContractaffixinfo);
		}
		return RestUtils.wrappQueryResult(vo).addDicItems(wrapDictListNew());
	}
	
	private List<DicItems> wrapDictListNew() {// 将字典对象封装为list
		List<DicItems> dicts = new ArrayList<DicItems>();
	    dicts.add(translateFromFile1("affixtype",1));
	    return dicts;
	}
	
	// 从属性文件中查询字典
		private DicItems translateFromFile1(String fieldName,int index) {
					
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
					
			Object[] obj = {};
			StringBuffer sql = new StringBuffer();
			if(index==1){
				obj = new Object[]{"108"};
				sql.append("  SELECT BGC.VALUE, BGC.NAME FROM BA_GENCODE BGC WHERE BGC.TYPE =? ");
			}
			
			List tlist = hibernateDao.executeSqlQuery(sql.toString(),obj);
			for(int i=0;i<tlist.size();i++){
				Map<String, String> map = new HashMap();
				Object[] robj = (Object[])tlist.get(i);
				String value = robj[0]==null?"":robj[0].toString();
				String name = robj[1]==null?"":robj[1].toString();
				map.put("value", value);
				map.put("text", name);
//						map.put(value, name);
				list.add(map);
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
	
	
	/**
	 * 统计数量
	 * @description 方法描述
	 * @param contractid
	 * @return
	 * @author mengke 
	 * @date 2013-4-16
	 */
	public int getCount(String contractid) {
		// TODO Auto-generated method stub
		String sql = " select cc.contractid,count(affixno) from co_contractaffixinfo cc where 1=1 ";
		if(contractid!=null && !"".equals(contractid)){//@判断是否为空
			sql += " and cc.contractid = '"+contractid+"'";
		}
		sql += " group by cc.contractid";
		List list = hibernateDao.executeSqlQuery(sql);//@查询信息
		int affixno = 1;
		if(list !=null && list.size()>0){
			Object[] obj = (Object[]) list.get(0);
			affixno = (obj[1] == null?1:(Integer.valueOf(obj[1].toString())+1));//@判断是否为空并赋值
		}
		return affixno;
	}
	
	/**
	 * 获取文件
	 * @description 方法描述
	 * @param pkVal
	 * @return
	 * @author wenwu
	 * @date 2013-4-16
	 */
	public List getFile(String pkVal){
		
		CoContractaffixinfoVO coContractaffixinfoVO = new CoContractaffixinfoVO();
		String sql = " select t.att_size from t_mx_attachment t where t.pk_val = '" + pkVal +"'";
		List list = hibernateDao.executeSqlQuery(sql);
		return list;
		
	}

	public void setHibernateDao(IHibernateDao hibernateDao) {
		this.hibernateDao = hibernateDao;
	}
}
