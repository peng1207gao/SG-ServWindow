package com.sgcc.dlsc.contractManage.contResoByMonth.bizc;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sgcc.dlsc.entity.po.CoContractenergyinfo;

public interface IContResoByMonthBizc {
	/**
	 * 
	 * @description 查询分解方式下拉菜单
	 * @param type
	 * @author zhangyan
	 * @date 2014-2-07
	 */
	public List queryFjfsSelect(String type);
	
	/**
	 * 
	 * @description 初始化合同电量分解表格
	 * @param type
	 * @author zhangyan
	 * @date 2014-2-08
	 */
	public List initHtdl(String contractid);
	
	/**
	 * 
	 * @description 根据合同id 查询合同
	 * @param type
	 * @author zhangyan
	 * @date 2014-2-08
	 */
	public Object[] findContractById(String contractid);
	
	/**
	 * 进入该功能界面前判断
	 * 		是否在电量电力中维护过
	 * @description 方法描述
	 * @param contractid
	 * @return
	 * @author zhangyan
	 * @date 2014-2-08
	 */
	public boolean isOnlyOper(String contractid);
	
	/**
	 * 
	 * @description 方法描述
	 * @param contractid
	 * @param startdate
	 * @param enddate
	 * @return
	 * @author zhangbo
	 * @date 2013-3-25
	 */
	public List getDataList(String contractid,String startdate,String enddate);
	/**
	 * 得到检修时间段
	 * @description 方法描述
	 * @param contractid
	 * @param contractrole
	 * @return
	 * @author zhangyan
	 * @date 2014-2-10
	 */
	public List getSellergenCalendarList(String contractid, int contractrole);
	
	/**
	 * 获取机组个数
	 * @description 方法描述
	 * @param contractid
	 * @param contractrole
	 * @return
	  * @author zhangyan
	 * @date 2014-2-10
	 */
	public int findUnitNum(String contractid, int contractrole);
	/**
	 *  获得合同电量拆分类型
	 * @description 方法描述
	 * @param 
	 * @return
	 * @author zhangyan
	 * @date 2014-2-10
	 */
	public List getEnergyFSList();
	
	/**
	 * 获得历史信息
	 * @description 方法描述
	 * @param contractid
	 * @param startdate
	 * @param enddate
	 * @return
	 * @author zhangyan
	 * @date 2014-2-10
	 */
	public List findOldInfo(String contractid,String startdate,String enddate);
	/**
	 * 保存方法
	 * @description 方法描述
	 * @param updateList
	 * @param insertList
	 * @return
	 * @throws SQLException
	 * @author zhangbo
	 * @date 2013-3-25
	 */
	public int updateBySqlList(List<String> updateList, List<CoContractenergyinfo> insertList, List<CoContractenergyinfo> insertList2) ;
	
	/**
	 * 删除当前合同的电量内容
	 * @description 方法描述
	 * @param contractid
	 * @return
	 * @author zhangyan
	 * @date 2013-02-21
	 */
	public boolean delHtdl(String contractid);
	
	/**
	 * @desc  检验合同类型
	 * @author zhangyan
	 * 
	 * */
	public String checkCoType(String coType);
	
	/**
	 * @desc  根据合同id检验合同类型
	 * @author wenwu
	 * 
	 * */
	public String checkCoTypeTwo(String coType);
	
	/**
	 * @desc 纵向传输
	 * */
	public void zxcs(HttpServletRequest request,String contractid);
}
