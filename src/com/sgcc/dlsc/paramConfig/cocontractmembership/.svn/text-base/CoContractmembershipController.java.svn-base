package com.sgcc.dlsc.paramConfig.cocontractmembership;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestClientException;

import com.sgcc.dlsc.commons.util.UserInfoUtil;
import com.sgcc.dlsc.entity.po.CoContractmembership;
import com.sgcc.dlsc.paramConfig.cocontractmembership.bizc.ICoContractmembershipBizc;
import com.sgcc.dlsc.paramConfig.cocontractmembership.vo.CoContractmembershipTransfer;
import com.sgcc.isc.core.orm.identity.User;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.support.QueryResultObject;

/**
 * 执行对合同准入成员信息 {@link CoContractmembership} 的操作请求，例如查询，保存等功能
 * 
 * @author djdeng
 *
 */
@Controller
@RequestMapping("/cocontractmembership")
public class CoContractmembershipController {
	
	/**
	 * 合同准入成员信息维护业务组件，通过IOC容器注入
	 */
	@Autowired
	private ICoContractmembershipBizc cocontractmembershipBizc;

	
	/**
	 * 解析前台传递的数据，保存合同准入成员信息 {@link CoContractmembership}
	 * 
	 * @param params 前台传递的参数，参数解析请参考
	 *  {@linkplain CoContractmembershipTransfer
	 *  #buildContractmemberships(Map, String, String)}
	 * 
	 * @param request Servlet请求对象
	 * 
	 * @throws RuntimeException IO异常，数据库异常，业务判断抛出的异常
	 * 
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ItemResponseBody 
	Object save(@RequestBody Map<String, String> params, 
			HttpServletRequest request){
		
		//
		String marketId = getMarketId(request);
		String userName = getUserName(request);
		
		//将前台提交的数据转换成合同准入成员信息 
		List<CoContractmembership> contractmemberships = CoContractmembershipTransfer
				.buildContractmemberships(params, marketId, userName);
		
		try {
			cocontractmembershipBizc.saveOrUpdate(contractmemberships);
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
		return null;
	}
	
	/**
	 * 根据合同类型和合同角色查询已经关联的市场成员的Id主键集合和名称集合，主要做前端表单的展示用
	 * 
	 * 返回的数据举例： {"ids":"1233,1233,122233", "names":"ssss,eeee,dddd"}
	 * 
	 * @param params 前段提交的参数，包括合同类型 contractType 和合同角色 contractroleType
	 * 
	 * @return 已经与合同类型关联的市场成员，返回的数据封装成一个Map对象，其中包括市场成员的Id集合和名称集合
	 * 
	 */
	@RequestMapping(value = "/selectedMemberships", method = RequestMethod.POST)
	public @ItemResponseBody
	Object getSelectedMemberships(@RequestBody Map<String, String> params, 
			HttpServletRequest request) {
		//
		String contractroleType = params.get("contractroleType");
		String contractType = params.get("contractType");
		String marketId = getMarketId(request);
		
		//
		Map<String, String> memberships;
		try {
			memberships = cocontractmembershipBizc.getSelectedMemberships(
					contractroleType, contractType, marketId);
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
		
		return memberships;
	}
	
	/**
	 * 根据前端提交的参数查询市场成员集合
	 * 
	 * @param condition 查询条件，逻辑组件根据该参数，执行不同的查询
	 * 
	 * @param request Servlet请求对象
	 * 
	 * @return queryResult 市场成员集合封装
	 */
	@RequestMapping("/membership/")
	public @ItemResponseBody
	QueryResultObject queryMembership(@RequestBody Map<String, String>  condition ,
			HttpServletRequest request) {
		
		//
		condition.put("marketId", getMarketId(request));
		
		QueryResultObject queryResult;
		try {
			queryResult = cocontractmembershipBizc.queryMembership(condition);
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
		return queryResult;
	}
	
	/**
	 * 从请求 {@link HttpServletRequest}对象中获得场景Id
	 * 
	 * @param request Servlet请求对象
	 * 
	 * @return 场景Id
	 * 
	 * @throws RestClientException 返回为空时抛出异常
	 */
	private String getMarketId(HttpServletRequest request) {
		try {
			String marketId = UserInfoUtil.getLoginUserMarket(request);
			if (StringUtils.isBlank(marketId)) {
				throw new RestClientException("获得当前登录用户的marketId参数为空");
			}
			return marketId;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RestClientException("获得当前登录用户的marketId参数失败", e);
		}
	}
	
	/**
	 * 从请求 {@link HttpServletRequest}对象中获得用户门户账号
	 * 
	 * @param request Servlet请求对象
	 * 
	 * @return 用户门户账号，例如 "zhangsan"
	 * 
	 * @throws RestClientException 返回为空时抛出异常
	 */
	private String getUserName(HttpServletRequest request) {
		try {
			User user = UserInfoUtil.getLoginUser(request);
			if (user == null || user.getUserName() == null) {
				throw new RestClientException("获得当前登录用户的门户账号为空");
			}
			return user.getUserName();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RestClientException("获得当前登录用户的门户账号失败", e);
		}
	}
}
