package com.sgcc.dlsc.contractManage.coContractbaseinfoV2Act.bizc;

import java.util.List;
import java.util.Map;

import com.sgcc.dlsc.contractManage.coContractbaseinfoV2Act.vo.CoContractbaseinfoVO;
import com.sgcc.dlsc.entity.po.CoContractbaseinfo;
import com.sgcc.uap.rest.support.IDRequestObject;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

/**
 * 单表场景逻辑构件
 *
 */
public interface ICoContractbaseinfoV2ActBizc {
	
	
	
	 /**
	 * 获取合同终止前的合同流转状态
	 * @throws  
	 * @author wewu
	 */
	public String getFlowState(String  coContractId);
	
	/**
	 * 重置终止合同
	 * isdelete 改为 : 0  isend 改为：0
	 * @return true/false
	 * @throws  
	 * @author wewu
	 */
	public boolean update(CoContractbaseinfo coContractbaseinfo);
	
	/**
	 * 根据主键查询合同
	 * checkedId 主键编号
	 * @return CoContractbaseinfo
	 * @throws  
	 * @author wewu
	 */
	public CoContractbaseinfo findById(String checkedId);
	
	/**
	 * 
	 * @description 统计信息
	 * @param marketid
	 * @param contracttype
	 * @param searchDateType
	 * @param startDate
	 * @param endDate
	 * @param contractcyc
	 * @param prepcontractflag
	 * @param purchaser
	 * @param seller
	 * @param sequenceid
	 * @param flowflag
	 * @param marketId
	 * @param date
	 * @param papercontractcode
	 * @param syscontractcode
	 * @return
	 * @author zhangzhen
	 * @date 2014-1-26
	 */
	public String[] getCoStatistics12(String contractName,
				String marketid,String contracttype,String searchDateType,String startDate, String
				endDate,String contractcyc,String prepcontractflag, String
				purchaser,String seller,String sequenceid,String flowflag,String marketId, String
				date,String papercontractcode,String syscontractcode,String powertype);	
	
	/**
	 * 
	 * @description 查询合同序列
	 * @param marketid
	 * @return
	 * @author zhangzhen
	 * @date 2014-1-23
	 */
	public List querySequence(String marketid,String contracttype) ;
	
	/**
	 * 
	 * @description 得到准入成员(类型：1,购电方，2,售点方，3,输电方)
	 * @param marketId
	 * @param displayType
	 * @param coType
	 * @return
	 * @author zhangzhen
	 * @date 2014-1-22
	 */
	public List getParticipantBySel(String marketId, int displayType, String coType);

	/**
	 * 查询外网合同(原来的方法去掉了二个参数orderField、orderType)
	 * @description 方法描述
	 * @param pageNo
	 * @param pageSize
	 * @param contracttype
	 * @param searchField
	 * @param startDate
	 * @param endDate
	 * @param contractcyc
	 * @param sequenceid
	 * @param signstate
	 * @param backupstate
	 * @param corporationid
	 * @return
	 * @author mengke 
	 * @date 2013-4-16
	 */
	public QueryResultObject findOutWebContractList(int pageNo, int pageSize,
			String contracttype, String searchField,
			String startDate, String endDate,String contractcyc, String sequenceid,
			String signstate, String backupstate,String corporationid,String marketId);
	
	/**
	 * 合同附件维护-查询
	 * @param pageIndex(第几页)
	 * @param pageSize(总共几页)
	 * @param marketid
	 * @param contracttype
	 * @param searchDateType
	 * @param startDate
	 * @param endDate
	 * @param contractcyc
	 * @param prepcontractflag
	 * @param purchaser
	 * @param seller
	 * @param sequenceid
	 * @param marketid1
	 * @return
	 */
	public QueryResultObject findFjContractList(int pageIndex,int pageSize,
			String marketid, String contracttype, String searchDateType,String flowflag,
			String startDate, String endDate, String contractcyc,
			String prepcontractflag, String purchaser, String seller,
			String sequenceid,String marketid1);
	
	/**
	 * 合同管理查询功能(原来的方法去掉了四个参数pageNo、
	 * 						pageSize、orderField、orderType)
	 * @param pageIndex(第几页)
	 * @param pageSize(总共几页)
	 * @param marketid
	 * @param contracttype
	 * @param searchDateType
	 * @param startDate
	 * @param endDate
	 * @param contractcyc
	 * @param prepcontractflag
	 * @param purchaser
	 * @param seller
	 * @param sequenceid
	 * @param flowflag
	 * @param marketid1
	 * @param contractName
	 * @param papercontractcode
	 * @param syscontractcode
	 * @return
	 */
	public QueryResultObject findCoContractList11(int pageIndex,int pageSize,
			String marketid, String contracttype, String searchDateType,
			String startDate, String endDate, String contractcyc,
			String prepcontractflag, String purchaser, String seller,
			String sequenceid, String flowflag, String marketid1, 
			String contractName, String papercontractcode, String syscontractcode,String powertype);
	
	
	/**
	 * 合同分月管理查询功能(原来的方法去掉了四个参数pageNo、
	 * 						pageSize、orderField、orderType)
	 * @param pageIndex(第几页)
	 * @param pageSize(总共几页)
	 * @param marketid
	 * @param contracttype
	 * @param searchDateType
	 * @param startDate
	 * @param endDate
	 * @param contractcyc
	 * @param prepcontractflag
	 * @param purchaser
	 * @param seller
	 * @param sequenceid
	 * @param flowflag
	 * @param marketid1
	 * @param contractName
	 * @param papercontractcode
	 * @param syscontractcode
	 * @return
	 */
	public QueryResultObject queryMonthManage(int pageIndex,int pageSize,
			String marketid, String contracttype, String searchDateType,
			String startDate, String endDate, String contractcyc,
			String prepcontractflag, String purchaser, String seller,
			String sequenceid, String flowflag, String marketid1, 
			String contractName, String papercontractcode, String syscontractcode,String powertype);
	
	/**
	 * 已终止查询功能(原来的方法去掉了四个参数pageNo、
	 * 						pageSize、orderField、orderType)
	 * @param pageIndex(第几页)
	 * @param pageSize(总共几页)
	 * @param marketid
	 * @param contracttype
	 * @param searchDateType
	 * @param startDate
	 * @param endDate
	 * @param contractcyc
	 * @param prepcontractflag
	 * @param purchaser
	 * @param seller
	 * @param sequenceid
	 * @param flowflag
	 * @param marketid1
	 * @param contractName
	 * @param papercontractcode
	 * @param syscontractcode
	 * @return
	 */
	public QueryResultObject findCoContractList12(int pageIndex,int pageSize,
			String marketid, String contracttype, String searchDateType,
			String startDate, String endDate, String contractcyc,
			String prepcontractflag, String purchaser, String seller,
			String sequenceid, String flowflag, String marketid1, 
			String contractName, String papercontractcode, String syscontractcode);
	
	/**
	 * 
	 * @description 合同导出功能
	 * @param marketid
	 * @param contracttype
	 * @param searchDateType
	 * @param startDate
	 * @param endDate
	 * @param contractcyc
	 * @param prepcontractflag
	 * @param purchaser
	 * @param seller
	 * @param sequenceid
	 * @param flowflag
	 * @param marketid1
	 * @param contractName
	 * @param papercontractcode
	 * @param syscontractcode
	 * @return
	 * @author zhangzhen
	 * @date 2014-1-24
	 */
	public List findCContractList(
			String marketid, String contracttype, String searchDateType,
			String startDate, String endDate, String contractcyc,
			String prepcontractflag, String purchaser, String seller,
			String sequenceid, String flowflag, String marketid1, 
			String contractName, String papercontractcode, String syscontractcode,String powertype);
	
	/**
	 * 保存更新方法
	 * @param ll
	 * @return list
	 */
	public List<CoContractbaseinfoVO> saveOrUpdate(List<Map> ll);
			
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
	public QueryResultObject queryById(String id);
	
	/**
	 * 
	 * @description 根据合同id查询合同序列id和合同序列名称
	 * @param contractid
	 * @return
	 * @author mengke
	 * @date 2014-5-16
	 */
	public List getHtxl(String contractid);
	
	/**
	 * 
	 * @description 判断是否是发电权合同
	 * @param map
	 * @return
	 * @author mengke
	 * @date 2014-6-24
	 */
	public List isfdqht(Map map);
	
	public String copyBull(List<Map> list,String marketId,String userId);
}
