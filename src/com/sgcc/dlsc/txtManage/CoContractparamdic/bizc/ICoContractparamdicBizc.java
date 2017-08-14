package com.sgcc.dlsc.txtManage.CoContractparamdic.bizc;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.sgcc.uap.rest.support.IDRequestObject;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;
import com.sgcc.dlsc.entity.po.CoContractparamdic;
import com.sgcc.dlsc.txtManage.CoContractparamdic.vo.CoContractparamdicVO;

/**
 * 单表场景逻辑构件
 *
 */
public interface ICoContractparamdicBizc {

	/**
	 * 查找order_no字段中最大值并加1，排序号
	 * @return string
	 */
	public String getNextOrderNo();
	
	/**
	 * 保存更新方法
	 * @param ll
	 * @return list
	 */
	public List<CoContractparamdicVO> saveOrUpdate(List<Map> ll, String loginName)  throws ParseException;
			
	/**
	 * 删除
	 * @param idObject
	 */
	public void remove(IDRequestObject idObject);
	
	/**
	 * 查询
	 * @param dicName pageIndex  pageSize
	 * @return QueryResultObject
	 */
	public QueryResultObject query(String dicName, int pageIndex, int pageSize);
	
	/**
	 * 查询单条记录
	 * @param id
	 * @return QueryResultObject
	 */
	public QueryResultObject queryById(String id);
}
