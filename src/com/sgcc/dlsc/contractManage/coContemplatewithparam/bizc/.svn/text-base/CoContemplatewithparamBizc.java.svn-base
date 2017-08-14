package com.sgcc.dlsc.contractManage.coContemplatewithparam.bizc;

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
import com.sgcc.dlsc.entity.po.CoContemplatewithparam;
import com.sgcc.dlsc.contractManage.coContemplatewithparam.vo.CoContemplatewithparamTransfer;
import com.sgcc.dlsc.contractManage.coContemplatewithparam.vo.CoContemplatewithparamVO;
/**
 * 用户定义逻辑构件
 * 
 * @author thinpad 
 * 
 */
public class CoContemplatewithparamBizc implements ICoContemplatewithparamBizc{

	@Autowired
	private IHibernateDao hibernateDao;
	
	@Autowired
	private IDataDictionaryBizC dataDictionaryBizC;
	/**
	 * 保存更新
	 * 
	 * @param list
	 */
	 public List<CoContemplatewithparamVO> saveOrUpdate(List<Map> list) {
		 
		List<CoContemplatewithparamVO> voList = new ArrayList<CoContemplatewithparamVO>();
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
			String poName = CoContemplatewithparam.class.getName();
			if(map.containsKey("contracttemplatecode")){
				String id = (String)map.get("contracttemplatecode");
				CoContemplatewithparamVO vo = update(map,poName,id);
				voList.add(vo);
			}else{
				CoContemplatewithparamVO coContemplatewithparamVO = save(map);
				voList.add(coContemplatewithparamVO);
			}
		}
		return voList;
	 }
	 
	//保存记录
	private CoContemplatewithparamVO save(Map map) {
		
		CoContemplatewithparamVO coContemplatewithparamVo = new CoContemplatewithparamVO();
		try {
			BeanUtils.populate(coContemplatewithparamVo, map);
		} catch (Exception e) {
		}
		CoContemplatewithparam coContemplatewithparam = CoContemplatewithparamTransfer.toPO(coContemplatewithparamVo);
		hibernateDao.saveOrUpdateObject(coContemplatewithparam);
		coContemplatewithparamVo = CoContemplatewithparamTransfer.toVO(coContemplatewithparam);
		if(map.containsKey("mxVirtualId")){
			coContemplatewithparamVo.setMxVirtualId(String.valueOf(map.get("mxVirtualId")));
		}
		return coContemplatewithparamVo;
	}
	
	//更新记录
	private CoContemplatewithparamVO update(Map<String, ?> map,String poName,String id) {
		
		CoContemplatewithparamVO coContemplatewithparamVo = new CoContemplatewithparamVO();
		//更新操作
		try {
			BeanUtils.populate(coContemplatewithparamVo, map);
		} catch (Exception e) {
		}
		Object[] objArray = CrudUtils.generateHql(CoContemplatewithparam.class, map, "contracttemplatecode");
		hibernateDao.update((String) objArray[0], (Object[]) objArray[1]);
		return coContemplatewithparamVo;
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
			hibernateDao.update("delete from " + CoContemplatewithparam.class.getName() + " where contracttemplatecode = ?" ,new Object[]{id});
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
		List<CoContemplatewithparam> result = null;
		int count = 0;
		qc.addFrom(CoContemplatewithparam.class);
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
		List<QueryFilter> wheres = queryCondition.getQueryFilter(CoContemplatewithparamVO.class);
		if (wheres != null && wheres.size() > 0) {
			CrudUtils.addQCWhere(qc, wheres, CoContemplatewithparam.class.getName());

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
		CoContemplatewithparam coContemplatewithparam = (CoContemplatewithparam) hibernateDao.getObject(CoContemplatewithparam.class,id);
		CoContemplatewithparamVO vo = null;
		if (coContemplatewithparam != null) {
			vo = CoContemplatewithparamTransfer.toVO(coContemplatewithparam);
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

	public String downLoadFile(String guid, HttpServletRequest request,
			HttpServletResponse response) {
		String finStr = "false";
		try{
			String sql = " select contracttemplatename, contracttemplatefile from CO_CONTEMPLATEWITHPARAM  where contracttemplatecode = '"+guid+"' ";//and att_name like '%"+filename+"%'
			List cxlist = hibernateDao.executeSqlQuery(sql);
			for (int i = 0; i < cxlist.size(); i++) {
				Object[] ydArr = (Object[]) cxlist.get(0);
				String filename = ydArr[0].toString();
				InputStream in = null;
				long length = 0;
				if(ydArr[1]!=null){
					Blob blob = (Blob) ydArr[1];
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
				finStr = "success"; 
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return finStr;
	}
}
