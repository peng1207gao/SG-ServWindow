package com.sgcc.dlsc.contractManage.coContractCopy.bizc;

import java.util.List;

import com.sgcc.uap.rest.support.QueryResultObject;

public interface ICoContractCopyBizc {
	
	
	/**
	 * @author 张岩
	 * 合同复制的查询功能
	 * marketid 查询条件 的业务场景
	 * contracttype 合同类型
	 * searchDateType 时间类型
	 * startDate 开始时间
	 * endDate  结束时间
	 * contractcyc 合同周期
	 * prepcontractflag 合同状态
	 * purchaser  够电方
	 * seller 售电方
	 * sequenceid 合同序列
	 * marketid1 当前用户的 场景
	 * */
	public QueryResultObject findCoContractList11(int pageIndex,int pageSize,
			String marketid, String contracttype, String searchDateType,
			String startDate, String endDate, String contractcyc,
			String prepcontractflag, String purchaser, String seller,
			String sequenceid,String marketid1);
	
	/**
	 * @author 张岩
	 * contractId 合同id
	 * */
	public String copyContract(String contractId,String marketId,String userId,String newContractId);
}
