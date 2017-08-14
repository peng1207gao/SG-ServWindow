package com.sgcc.dlsc.paramConfig.cocontractmembership.bizc;

import java.util.List;
import java.util.Map;

import com.sgcc.dlsc.entity.po.CoContractmembership;
import com.sgcc.uap.rest.support.QueryResultObject;


/**
 * 合同准入成员信息 {@link CoContractmembership} 逻辑执行组件
 * 
 * @author djdeng
 *
 */
public interface ICoContractmembershipBizc {

	/**
	 * 根据合同类型和合同角色查询已经关联的市场成员的Id主键集合和名称集合，主要做前端表单的展示用
	 * 
	 * @param contractroleType 合同角色
	 * 
	 * @param contractType 合同类型
	 * 
	 * @param marketId 场景Id
	 * 
	 * @return 返回市场成员的Id主键集合和名称集合封装的Map对象
	 */
	Map<String, String> getSelectedMemberships(String contractroleType,
			String contractType, String marketId);

	
	/**
	 * 根据参数查询市场成员集合
	 * 
	 * @param condition 查询过滤参数
	 * 
	 * @return 市场成员集合
	 */
	QueryResultObject queryMembership(Map<String, String>  condition);


	/**
	 * 合同准入成员信息保存
	 * 
	 * 先根据合同类型，合同角色类型，场景Id 删除旧数据
	 * 
	 * 再保存用户提交的新数据
	 * 
	 * @param contractmemberships  用户提交的合同准入成员信息 {@link CoContractmembership}
	 */
	void saveOrUpdate(List<CoContractmembership> contractmemberships);

}
