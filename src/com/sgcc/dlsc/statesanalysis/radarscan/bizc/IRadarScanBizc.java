package com.sgcc.dlsc.statesanalysis.radarscan.bizc;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sgcc.dlsc.contractManage.CoContractFjAct.vo.CoContractaffixinfoVO;
import com.sgcc.uap.rest.support.IDRequestObject;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

/**
 * 单表场景逻辑构件
 *
 */
public interface IRadarScanBizc {

	
	/**
   	 * 
   	 * @description 获取统计图形数据
   	 * @param params
   	 * @return
   	 */
	public String getChartData(String threshold,String period,String marketId);
	
	/**
	 * 雷达扫描按阀值过滤查询
	 * @param queryCondition
	 * @return QueryResultObject
	 */
	public QueryResultObject query(int pageIndex,int pageSize,String threshold,String period,String marketId);
	
	/**
	 * 雷达扫描统计数量
	 * @description 方法描述
	 * @param contractid
	 * @return
	 * @author mengke
	 * @date 2013-4-16
	 */
	public QueryResultObject getStatics(String threshold,String period,String marketId);
	
}
