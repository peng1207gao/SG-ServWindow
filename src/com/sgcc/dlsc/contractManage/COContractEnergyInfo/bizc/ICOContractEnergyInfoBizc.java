package com.sgcc.dlsc.contractManage.COContractEnergyInfo.bizc;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sgcc.uap.rest.support.IDRequestObject;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;
import com.sgcc.dlsc.entity.po.CoContractenergyinfo;
import com.sgcc.dlsc.contractManage.COContractEnergyInfo.vo.CoContractenergyinfoVO;

/**
 * 单表场景逻辑构件
 *
 */
public interface ICOContractEnergyInfoBizc {

	/**
	 * 保存更新方法
	 * @param ll
	 * @return list
	 */
	public List<CoContractenergyinfoVO> saveOrUpdate(List<Map> ll,HttpServletRequest request);
			
	/**
	 * 删除
	 * @param idObject
	 */
	public void remove(IDRequestObject idObject);
	
	/**
	 * 查询
	 * @param queryCondition
	 * @return QueryResultObject
	 */
	public QueryResultObject query(RequestCondition queryCondition);
	/**
	 * 查询单条记录
	 * @param id
	 * @return QueryResultObject
	 */
	public QueryResultObject queryById(String id,String[] params,String period);

	public List getCOContractEnergyTree(String type);

	public QueryResultObject query(int pageIndex, int pageSize, String contractid, String startdate,
			String enddate);

	public List getCOContractEnergyInit(String checkedId);
	/**
	 * 查询时间段序号最大值
	 * @description 方法描述
	 * @param contractid
	 * @return
	 * @author fujingbo
	 * @date 2014-2-22
	 */
	public String nextTimeNo(String contractid);
	/**
	 * 求年电量总和
	 * @description 方法描述
	 * @param id
	 * @param params
	 * @param period
	 * @return
	 * @author liling
	 * @date 2014-3-6
	 */
	public String querySumEnergy(String[] params);
	
	/**
	 * 
	 * @description 获取该月的尖峰平谷四种电量总和
	 * @param map
	 * @return
	 * @author mengke
	 * @date 2014-6-11
	 */
	public List getMonthEnergy(Map map);
	
	/**
	 * 
	 * @description 获取该月的总电量
	 * @param map
	 * @return
	 * @author mengke
	 * @date 2014-6-11
	 */
	public List getMonthTotalEnergy(Map map);
}
