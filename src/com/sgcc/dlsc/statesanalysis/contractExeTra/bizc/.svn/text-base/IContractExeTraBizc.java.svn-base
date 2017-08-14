package com.sgcc.dlsc.statesanalysis.contractExeTra.bizc;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.List;
import com.sgcc.uap.rest.support.RequestCondition;
import com.sgcc.dlsc.statesanalysis.contractExeTra.vo.CoContractbaseinfoVO;

/**
 * 单表场景逻辑构件
 *
 */
public interface IContractExeTraBizc {

	
			
	
	/**
	 * 查询
	 * @param queryCondition
	 * @return QueryResultObject
	 * @throws ParseException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public List<Object> query(RequestCondition queryCondition,String marketId,StringBuilder sql) throws IllegalAccessException, InvocationTargetException, ParseException;
	
	/**
	 * 拼chart图
	 * 
	 * */
	public String getStackChart(List<Object> list);
	
	public List<CoContractbaseinfoVO>  toVoList(List<Object> list);
	
	public int count(StringBuilder sql);
}
