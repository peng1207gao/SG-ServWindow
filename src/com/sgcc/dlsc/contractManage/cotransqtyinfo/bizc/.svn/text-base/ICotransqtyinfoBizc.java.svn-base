package com.sgcc.dlsc.contractManage.cotransqtyinfo.bizc;

import java.util.List;
import java.util.Map;

import com.sgcc.dlsc.contractManage.cotransqtyinfo.vo.CoTransqtyinfoVO;
import com.sgcc.dlsc.contractManage.cotransqtyinfo.vo.CoTransqtyslaveinfoVO;
import com.sgcc.isc.core.orm.identity.User;
import com.sgcc.uap.rest.support.IDRequestObject;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

/**
 * 单表场景逻辑构件
 *
 */
public interface ICotransqtyinfoBizc {

	/**
	 * 保存更新方法
	 * @param ll
	 * @return list
	 */
	public List<CoTransqtyinfoVO> saveOrUpdate(List<Map> ll,String marketId,User user);
			
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
	public QueryResultObject queryById(String id,String marketId);
	
	/**
	 * 根据联络线id查询起始关口
	 * @description 方法描述
	 * @param linkid
	 * @return
	 * @author mengke
	 * @date 2014-2-10
	 */
	public List listStartGate(String linkid);
	
	/**
	 * 根据联络线id查询终点关口信息
	 * @description 方法描述
	 * @param linkid
	 * @return
	 * @author mengke
	 * @date 2014-2-10
	 */
	public List listEndGate(String linkid);
	
	/**
	 * 根据输电信息id查询电力信息
	 * @description 方法描述
	 * @param queryCondition
	 * @return
	 * @author mengke
	 * @date 2014-2-11
	 */
	public QueryResultObject queryElecInfo(RequestCondition queryCondition);
	
	/**
	 * 查询单条记录
	 * @description 方法描述
	 * @param id
	 * @return
	 * @author mengke
	 * @date 2014-2-11
	 */
	public QueryResultObject queryElecById(String id,String marketId);
	
	/**
	 * 保存或更新电力信息
	 * @description 方法描述
	 * @param ll
	 * @return
	 * @author mengke
	 * @date 2014-2-11
	 */
	public List<CoTransqtyslaveinfoVO> saveOrUpdateElec(List<Map> ll,String marketId,User user);
	
	/**
	 * 删除电力信息
	 * @description 方法描述
	 * @param idObject
	 * @author mengke
	 * @date 2014-2-11
	 */
	public void removeElec(IDRequestObject idObject);
	
	public void removeDlqx(String transInfoId,List list1);
	public List getDlqxName(String transInfoId);
	
	/**
	 * 
	 * @description 查询合同开始时间和结束时间
	 * @param contractid
	 * @return
	 * @author mengke
	 * @date 2014-3-17
	 */
	public List getContractDate(String contractid);
	
	/**
	 * 
	 * @description 查询已经选中的输电方
	 * @param transid
	 * @return
	 * @author mengke
	 * @date 2014-3-20
	 */
	public List getSeleSdf(String transid);
	
	/**
	 * 
	 * @description 查询电力曲线
	 * @param queryCondition
	 * @return
	 * @author mengke
	 * @date 2014-6-16
	 */
	public QueryResultObject queryDlqx(RequestCondition queryCondition);
	public String getPie3DXml(Map map);
}
