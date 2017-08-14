package com.sgcc.dlsc.contractManage.contractVersionManage;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;

import com.sgcc.dlsc.commons.util.UserInfoUtil;
import com.sgcc.dlsc.contractManage.IdeaConManage.vo.CoContractbaseinfoVO;
import com.sgcc.dlsc.contractManage.contractVersionManage.bizc.IContractVersionManageBizc;
import com.sgcc.uap.integrate.isc.service.IPermissionControl;
import com.sgcc.uap.integrate.isc.service.PermissionControl;
import com.sgcc.uap.rest.annotation.ColumnRequestParam;
import com.sgcc.uap.rest.annotation.ColumnResponseBody;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.QueryRequestParam;
import com.sgcc.uap.rest.annotation.RawResponseBody;
import com.sgcc.uap.rest.annotation.attribute.ViewAttributeData;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;
import com.sgcc.uap.rest.support.ViewMetaData;
import com.sgcc.uap.rest.support.WrappedResult;
import com.sgcc.uap.rest.utils.ViewAttributeUtils;

@Controller
@RequestMapping("/contractVerion") //根据po类名生成
public class ContractVersionManageController {
	
    @Autowired
    private IContractVersionManageBizc contractVersionManageBizc;
	
	@RequestMapping(value = "/rollBack", method = RequestMethod.GET)
	public @RawResponseBody Object rollBack(@RequestParam String params,HttpServletRequest request){
		try {
			String result = contractVersionManageBizc.rollBack(params,request);
			return WrappedResult.successWrapedResult(result);
		} catch (Exception e) {
			throw new RestClientException("程序异常");
		}
	}
    
	/**
	 * 查询
	 * 
	 * @param params
	 *            将请求参数{"filter":"","orderStr":"age","columns":"id,name"}
	 *            封装为RequestCondition对象
	 * @return queryResult
	 */
	@RequestMapping("/")
	public @ItemResponseBody
	QueryResultObject query(@QueryRequestParam("params") RequestCondition params, HttpServletRequest request) {
		String loginMarketId;
		try {
			loginMarketId = UserInfoUtil.getLoginUserMarket(request);//从数据库中获取当前登录用户
			QueryResultObject queryResult = contractVersionManageBizc.query(params, loginMarketId);
			return queryResult;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RestClientException("程序异常");
		}
	}
	
	/**
	 * 查询
	 * @param params
	 *            将请求参数{"filter":"","orderStr":"age","columns":"id,name"}
	 *            封装为RequestCondition对象
	 * @return queryResult
	 */
	@RequestMapping("/getHistoryVersion")
	public @ItemResponseBody
	QueryResultObject getHistoryVersion(@QueryRequestParam("params") RequestCondition params) {
		try {
			QueryResultObject queryResult = contractVersionManageBizc.getHistoryVersion(params);
			return queryResult;
		} catch (Exception e) {
			throw new RestClientException("程序异常");
		}
	}
	
	/**
	 * 查询
	 * 
	 * @param params
	 *            将请求参数{"filter":"","orderStr":"age","columns":"id,name"}
	 *            封装为RequestCondition对象
	 * @return queryResult
	 */
	@RequestMapping("/hisVersionCompared")
	public @ItemResponseBody
	QueryResultObject hisVersionCompared(@QueryRequestParam("params") RequestCondition params) {
		try {
			QueryResultObject queryResult = contractVersionManageBizc.hisVersionCompared(params);
			return queryResult;
		} catch (Exception e) {
			throw new RestClientException("程序异常");
		}
	}
	
	/**
	 * 查询单条记录
	 * 
	 * @param id
	 *            url中传递的值
	 */
	@RequestMapping("/{id}")
	public @ItemResponseBody
	QueryResultObject query(@PathVariable String id) {
		try {
			return contractVersionManageBizc.queryById(id);
		} catch (Exception e) {
			throw new RestClientException("程序异常");
		}
		
	}
	
	/**
	 * 从vo中获取页面展示元数据信息
	 * 
	 * @param columns
	 *            将请求参数{columns:["id","name"]}封装为字符串数组
	 * @return datas
	 */
	@RequestMapping("/meta")
	public @ColumnResponseBody
	List<ViewAttributeData> getMetaData(
			@ColumnRequestParam("params") String[] columns) {
		List<ViewAttributeData> datas = null;
		datas = ViewAttributeUtils.getViewAttributes(columns, CoContractbaseinfoVO.class);
		return datas;
	}
	
	/**
	 * 从vo中获取页面展示元数据信息
	 * 
	 * @param columns
	 *            将请求参数{columns:["id","name"]}封装为字符串数组
	 * @return datas
	 */
	@RequestMapping("/getHistoryVersion/meta")
	public @ColumnResponseBody
	List<ViewAttributeData> getMetaData1(
			@ColumnRequestParam("params") String[] columns) {
		try {
			List<ViewAttributeData> datas = null;
			datas = ViewAttributeUtils.getViewAttributes(columns, CoContractbaseinfoVO.class);
			return datas;
		} catch (Exception e) {
			throw new RestClientException("程序异常");
		}
	}

	 /**
	 * 通过统一权限从vo中获取页面展示元数据信息
	 * 
	 * @param columns
	 *            将请求参数{columns:["id","name"]}封装为字符串数组
	 * @param iscID
	 *            统一权限中数据的功能编码
	 * @return 元数据信息
	 */
	@RequestMapping("/meta/{iscID}")
	public @ResponseBody
	WrappedResult getMetaDataByIscID(
			@ColumnRequestParam("params") String[] columns, @PathVariable String iscID) {
		List<ViewAttributeData> datas = null;
		datas = ViewAttributeUtils.getViewAttributes(columns, CoContractbaseinfoVO.class);
		ViewMetaData metaData= new ViewMetaData();
		metaData.setColumns(datas);
		IPermissionControl permControl = new PermissionControl();
		metaData = permControl.addPermInfoToMetaData(iscID, metaData);
		return WrappedResult.successWrapedResult(metaData);
	}
	/**
	 * 注入逻辑构件
	 * @param contractVersionManageBizc
	 */
	public void setContractVersionManageBizc(IContractVersionManageBizc contractVersionManageBizc) {
		this.contractVersionManageBizc = contractVersionManageBizc;
	}
	
}
