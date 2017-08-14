package com.sgcc.dlsc.txtManage.templatemanage.bizc;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.sgcc.dlsc.commons.util.DateUtil;
import com.sgcc.dlsc.entity.po.CoContemplatewithparam;
import com.sgcc.dlsc.entity.po.CoContracttemplate;
import com.sgcc.dlsc.entity.po.CoContracttypeinfo;
import com.sgcc.dlsc.txtManage.templatemanage.vo.CoContemplatewithparamTransfer;
import com.sgcc.dlsc.txtManage.templatemanage.vo.CoContemplatewithparamVO;
import com.sgcc.dlsc.txtManage.templatemanage.vo.CoContracttemplateTransfer;
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
public class TemplatemanageBizc implements ITemplatemanageBizc{

	@Autowired
	private IHibernateDao hibernateDao;
	
	@Autowired
	private IDataDictionaryBizC dataDictionaryBizC;
	
	/**
	 * 保存更新
	 * 
	 * @param list
	 */
	 public List<CoContemplatewithparamVO> saveOrUpdate(List<Map> list,String marketId) {
		 
		List<CoContemplatewithparamVO> voList = new ArrayList<CoContemplatewithparamVO>();
		CoContemplatewithparam coContemplatewithparam = new CoContemplatewithparam();
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
			String poName = CoContemplatewithparam.class.getName();
			if(map.containsKey("contracttemplatecode")){
				String id = (String)map.get("contracttemplatecode");
				coContemplatewithparam = hibernateDao.getObject(CoContemplatewithparam.class, id);
				map.put("version", Integer.valueOf(coContemplatewithparam.getVersion())+1+"");//更新版本号
				if(map.containsKey("contracttemplatefile")){
					String fileName = (String)map.get("contracttemplatefile");
					map.remove("contracttemplatefile");
					map.put("contracttemplatefile", fileName.getBytes());
				}
				CoContemplatewithparamVO vo = update(map,poName,id);
				voList.add(vo);
			}else{
				map.put("marketid", marketId);
				map.put("starteffectivedate", DateUtil.getUtilDate(DateUtil.getNowDate("yyyy-MM-dd"),"yyyy-MM-dd"));
				map.put("version", "1");
				map.put("effectiveflag", 0);
				CoContemplatewithparamVO coContemplatewithparamVO = save(map);
				voList.add(coContemplatewithparamVO);
			}
		}
		return voList;
	 }
	 
	//保存记录
	private CoContemplatewithparamVO save(Map map) {
		
		CoContemplatewithparamVO coContemplatewithparamVO = new CoContemplatewithparamVO();
		try {
			BeanUtils.populate(coContemplatewithparamVO, map);
		} catch (Exception e) {
		}
		CoContemplatewithparam coContemplatewithparam = CoContemplatewithparamTransfer.toPO(coContemplatewithparamVO);
		hibernateDao.saveOrUpdateObject(coContemplatewithparam);
		coContemplatewithparamVO = CoContemplatewithparamTransfer.toVO(coContemplatewithparam);
		if(map.containsKey("mxVirtualId")){
			coContemplatewithparamVO.setMxVirtualId(String.valueOf(map.get("mxVirtualId")));
		}
		return coContemplatewithparamVO;
	}
	
	//更新记录
	private CoContemplatewithparamVO update(Map<String, ?> map,String poName,String id) {
		
		CoContemplatewithparamVO coContemplatewithparamVO = new CoContemplatewithparamVO();
		//更新操作
		try {
			BeanUtils.populate(coContemplatewithparamVO, map);
		} catch (Exception e) {
		}
		Object[] objArray = CrudUtils.generateHql(CoContemplatewithparam.class, map, "contracttemplatecode");
		hibernateDao.update((String) objArray[0], (Object[]) objArray[1]);
		
		return coContemplatewithparamVO;
	}
	
	/**
	 * 删除（假删除）
	 * 
	 * @param idObject
	 */
	public void remove(IDRequestObject idObject) {
		String[] ids = idObject.getIds();
		String nowDate = DateUtil.getNowDate("yyyy-MM-dd");
		StringBuffer sql = new StringBuffer();
		for (int i = 0; i < ids.length; i++) {
			String id = ids[i];
			sql.setLength(0);
			sql.append(" UPDATE CO_CONTEMPLATEWITHPARAM CTWP SET CTWP.ENDEFFECTIVEDATE=TO_DATE(?,'YYYY-MM-DD') , CTWP.EFFECTIVEFLAG='1' ");
			sql.append(" WHERE CTWP.CONTRACTTEMPLATECODE = ? ");
			Object[] obj = new Object[]{nowDate,id};
			hibernateDao.executeSqlUpdate(sql.toString(), obj);
		}
	}
	
	/**
	 * 根据输入条件查询记录
	 * 
	 * @param queryCondition
	 * @return
	 */
	public QueryResultObject query(RequestCondition queryCondition,String marketId) {
		List<QueryFilter> filterList = queryCondition.getQueryFilter(CoContemplatewithparamVO.class);
		String contractType = "";//合同类型
		String flag = "0";//是否生效标识，0：生效，1：失效
		if(filterList!=null&&filterList.size()>0){
			for(int i=0;i<filterList.size();i++){
				QueryFilter filter = filterList.get(i);
				String colName = filter.getFieldName();
				if(colName.equals("contracttype")){
					contractType = ((filter.getValue()==null||filter.getValue().equals("null"))?"":filter.getValue().toString());
				}else if(colName.equals("effectiveflag")){
					flag = ((filter.getValue()==null||filter.getValue().equals("null"))?"0":filter.getValue().toString());
				}
			}
		}
		List<CoContracttemplateVO> result = new ArrayList<CoContracttemplateVO>();
		List list = new ArrayList();//存放查询结果
		List paramsList = new ArrayList();//存放查询参数
		int count = 0;//查询结果数
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT CTWP.CONTRACTTEMPLATECODE,CTWP.CONTRACTTEMPLATENAME,CCT.TYPENAME,CTWP.CONTRACTTEMPLATECODE as tt ,CTWP.STARTEFFECTIVEDATE,CTWP.ENDEFFECTIVEDATE ,CTWP.ISSHARE ");
		sql.append(" FROM CO_CONTEMPLATEWITHPARAM CTWP  LEFT JOIN CO_CONTRACTTYPEINFO CCT ON CTWP.CONTRACTTYPE = CCT.CONTRACTTYPEID AND CCT.EFFECTIVEFLAG = '0' ");
		sql.append(" WHERE CTWP.MARKETID = ?    ");
		paramsList.add(marketId);
		if(contractType!=null && !contractType.equals("")){
			sql.append(" AND CTWP.CONTRACTTYPE = ? ");
			paramsList.add(contractType);
		}
		if(flag.equals("0")){
			sql.append(" AND CTWP.EFFECTIVEFLAG = '0' ");
		}
		sql.append(" ORDER BY CTWP.STARTEFFECTIVEDATE DESC ");
		String sql2 = "SELECT COUNT(*) FROM ("+sql.toString()+") TS ";
		List list1 = hibernateDao.executeSqlQuery(sql2, paramsList.toArray());
		list = hibernateDao.executeSqlQuery(sql.toString(),paramsList.toArray(),
			Integer.parseInt(queryCondition.getPageIndex()),Integer.parseInt(queryCondition.getPageSize()));
		count = list1.size()>0 ? Integer.parseInt(list1.get(0).toString()) : 0;//查询结果总数
		if(list!=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				CoContracttemplateVO vo = new CoContracttemplateVO();
				Object[] obj = (Object[])list.get(i);
				vo.setContracttemplateid(obj[0]==null?"":obj[0].toString());//合同范本id
				vo.setContracttemplatename(obj[1]==null?"":obj[1].toString());//合同范本名称
				vo.setContracttype(obj[2]==null?"":obj[2].toString());//合同类型
				vo.setContracttemplatecode(obj[3]==null?"":obj[3].toString());//范本文件号
				vo.setStarteffectivedate(obj[4]==null?null:DateUtil.getUtilDate(obj[4].toString(),"yyyy-MM-dd"));
				vo.setEndeffectivedate(obj[5]==null?null:DateUtil.getUtilDate(obj[5].toString(),"yyyy-MM-dd"));
				result.add(vo);
			}
		}
		return RestUtils.wrappQueryResult(result, count);
	}
	
	/**
	 * 
	 * @description 生效范本（此方法中设计处理blob类型的字段，需要通过事务处理，故在方法名开始加save）
	 * @param id 合同范本id
	 * @return
	 * @author mengke
	 * @date 2014-5-22
	 */
	public String saveEffectContractTemplate(String id){
		String nowDate = DateUtil.getNowDate("yyyy-MM-dd");//当前时间
		CoContemplatewithparam coContemplatewithparam = new CoContemplatewithparam();
		coContemplatewithparam = hibernateDao.getObject(CoContemplatewithparam.class, id);
		String flag = coContemplatewithparam.getEffectiveflag().toString();
		if("0".equals(flag)){//范本已经生效
			return "exist";
		}
		coContemplatewithparam.setEffectiveflag(new BigDecimal(0));
		coContemplatewithparam.setStarteffectivedate(DateUtil.getUtilDate(nowDate,"yyyy-MM-dd"));
		coContemplatewithparam.setEndeffectivedate(null);
		coContemplatewithparam.setVersion(Integer.valueOf(coContemplatewithparam.getVersion())+1+"");
		hibernateDao.saveOrUpdateObject(coContemplatewithparam);
		
		return "success";
	}
	
	/**
	 * 
	 * @description 获取合同范本附件名称
	 * @param id 合同范本id
	 * @return
	 * @author mengke
	 * @date 2014-5-26
	 */
	public String getFjmc(String id){
		String fjmc = "";//附件名称
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT S.ATT_NAME FROM CO_CONTEMPLATEWITHPARAM T ")
		.append(" LEFT JOIN T_MX_ATTACHMENT S ON S.PK_VAL = T.CONTRACTTEMPLATECODE ")
		.append(" WHERE T.CONTRACTTEMPLATECODE = ? ");
		List list = hibernateDao.executeSqlQuery(sql.toString(), new Object[]{id});
		if(list!=null && list.size()>0){
			fjmc = list.get(0).toString();
		}
		return fjmc;
	}
	
	private QueryCriteria wrapQuery(RequestCondition queryCondition,
			QueryCriteria qc) {
		List<QueryFilter> wheres = queryCondition.getQueryFilter(CoContracttemplateVO.class);
		if (wheres != null && wheres.size() > 0) {
			CrudUtils.addQCWhere(qc, wheres, CoContracttemplate.class.getName());

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
	 * @param id 主键 合同范本id
	 * @return QueryResultObject
	 */
	public QueryResultObject queryById(String id) {
//		CoContemplatewithparam coContemplatewithparam = (CoContemplatewithparam) hibernateDao.getObject(CoContemplatewithparam.class,id);
		CoContemplatewithparamVO vo = new CoContemplatewithparamVO();
//		if (coContemplatewithparam != null) {
//			vo = CoContemplatewithparamTransfer.toVO(coContemplatewithparam);
//		}
		StringBuffer sql = new StringBuffer();
		sql.append("		SELECT T.MARKETID,                       ")
		.append("		       T.CONTRACTTYPE,                       ")
		.append("		       T.CONTRACTTEMPLATECODE,               ")
		.append("		       T.CONTRACTTEMPLATENAME,               ")
		.append("		       S.ATT_NAME,                           ")
//		.append("		       t.contracttemplatename as name,       ")
		.append("		       T.VERSION,                            ")
		.append("		       T.ISSUEDDATE,                         ")
		.append("		       T.STARTEFFECTIVEDATE,                 ")
		.append("		       T.ENDEFFECTIVEDATE,                   ")
		.append("		       T.EFFECTIVEFLAG,                      ")
		.append("		       T.ISSHARE,                            ")
		.append("		       T.DESCREPTION                         ")
		.append("		  FROM CO_CONTEMPLATEWITHPARAM T             ")
		.append("		  LEFT JOIN T_MX_ATTACHMENT S                ")
		.append("		    ON S.PK_VAL = T.CONTRACTTEMPLATECODE     ")
		.append("          WHERE T.CONTRACTTEMPLATECODE = ?          ");
		List list = hibernateDao.executeSqlQuery(sql.toString(),new Object[]{id});
		if(list!=null && list.size()>0){
			Object[] obj = (Object[])list.get(0);
			vo.setMarketid(obj[0]==null?"":obj[0].toString());//场景id
			vo.setContracttype(obj[1]==null?"":obj[1].toString());//合同类型
			vo.setContracttemplatecode(obj[2]==null?"":obj[2].toString());//合同范本id（主键）
			vo.setContracttemplatename(obj[3]==null?"":obj[3].toString());//合同范本名称
			vo.setContracttemplatefile(obj[4]==null?"":obj[4].toString());//合同范本文件，该字段为blob，一期是用来存放附件，现在附件存放在uap自带的附件表t_mx_attachment中，故该字段只存放附件名称
			vo.setVersion(obj[5]==null?"":obj[5].toString());//范本版本
			vo.setIssueddate(obj[6]==null?null:DateUtil.getUtilDate(obj[6].toString(),"yyyy-MM-dd"));
			vo.setStarteffectivedate(obj[7]==null?null:DateUtil.getUtilDate(obj[7].toString(),"yyyy-MM-dd"));//生效时间
			vo.setEndeffectivedate(obj[8]==null?null:DateUtil.getUtilDate(obj[8].toString(),"yyyy-MM-dd"));//失效时间
			vo.setEffectiveflag(obj[9]==null?null:new BigDecimal(obj[9].toString()));//是否生效标识
			vo.setIsshare(obj[10]==null?null:new BigDecimal(obj[10].toString()));
			vo.setDescreption(obj[11]==null?"":obj[11].toString());//备注
		}
		return RestUtils.wrappQueryResult(vo).addDicItems(wrapDictList());
	}
	
	// 将字典对象封装为list，此数据字典用于在form表单中合同类型下拉树的自动填值
	private List<DicItems> wrapDictList() {
		List<DicItems> dicts = new ArrayList<DicItems>();
        dicts.add(this.translateFromDB("contracttype",CoContracttypeinfo.class.getName(),"contracttypeid","typename"));
		return dicts;
	}
		
	//从数据库中查找数据字典 
	private DicItems translateFromDB(String fieldName, String poName,String keyField, String valueField) {
		List<Map<String, String>> list = dataDictionaryBizC.translateFromDB
			(poName, "value", "text", keyField, valueField,"1=1");
		DicItems dict = new DicItems();
		dict.setName(fieldName);
		dict.setValues(list);
		return dict;
	}

	/**
	 * 附件下载(下载上传到数据库中的文件)
	 * @description 方法描述
	 * @param guid 合同范本表主键contracttemplatecode
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
			sql.append("SELECT S.ATTACHMENT_ID,S.ATT_NAME,S.ATT_SIZE,S.ATT_FILE ")
			.append("FROM T_MX_ATTACHMENT S WHERE S.PK_VAL = ?");//uap中会将上传的附件插入到表T_MX_ATTACHMENT中，此表的PK_VAL字段与合同范本表的contracttemplatecode对应
			Object[] obj = new Object[]{guid};
			List cxlist = hibernateDao.executeSqlQuery(sql.toString(),obj);//查询附件
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
	
	/**
	 * 
	 * @description 附件下载(下载上传到服务器路径中的文件)
	 * @param guid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author mengke 
	 * @date 2014-5-28
	 */
	public String downAffix1(String guid,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String finStr = "false";
		System.out.println(guid);
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT S.ATTACHMENT_ID,S.ATT_NAME,S.ATT_SIZE,S.att_path ")
		.append("FROM T_MX_ATTACHMENT S WHERE S.PK_VAL = ?");//uap中会将上传的附件插入到表T_MX_ATTACHMENT中，此表的PK_VAL字段与合同范本表的contracttemplatecode对应
		Object[] obj = new Object[]{guid};
		List cxlist = hibernateDao.executeSqlQuery(sql.toString(),obj);//查询附件
		if(cxlist!=null&&cxlist.size()>0){
			for (int i = 0; i < cxlist.size(); i++) {
				Object[] ydArr = (Object[]) cxlist.get(0);
				String filename = ydArr[1].toString();//附件名称
				String url = ydArr[3].toString()+ydArr[1].toString();
				InputStream in = new FileInputStream(url);
				long length = ydArr[2]==null?Long.parseLong("0"):Long.parseLong(ydArr[2].toString());
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
		}else{
			finStr = "fail";
		}
		return finStr;
	}
	
	/**
	 * 
	 * @description 判断是否有可下载的范本
	 * @param guid
	 * @return
	 * @author mengke
	 * @date 2014-6-19
	 */
	public String isokdown(String guid){
		String finStr = "fail";
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT S.ATTACHMENT_ID,S.ATT_NAME,S.ATT_SIZE,S.att_path ")
		.append("FROM T_MX_ATTACHMENT S WHERE S.PK_VAL = ?");//uap中会将上传的附件插入到表T_MX_ATTACHMENT中，此表的PK_VAL字段与合同范本表的contracttemplatecode对应
		Object[] obj = new Object[]{guid};
		List cxlist = hibernateDao.executeSqlQuery(sql.toString(),obj);//查询附件
		if(cxlist!=null&&cxlist.size()>0){
			if(cxlist!=null&&cxlist.size()>0){
				for (int i = 0; i < cxlist.size(); i++) {
					Object[] ydArr = (Object[]) cxlist.get(0);
					String filename = ydArr[1].toString();//附件名称
					String url = ydArr[3].toString()+ydArr[1].toString();
					File file = new File(url);
					if(file.exists()){
						finStr = "success";
					}
				}
			}
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
