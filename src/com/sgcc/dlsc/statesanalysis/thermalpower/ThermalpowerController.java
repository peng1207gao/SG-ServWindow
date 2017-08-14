package com.sgcc.dlsc.statesanalysis.thermalpower;
import java.util.List;
import java.util.Map;

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
import com.sgcc.dlsc.statesanalysis.thermalpower.bizc.IThermalpowerBizc;
import com.sgcc.dlsc.statesanalysis.thermalpower.vo.SeTielineResultNVO;
import com.sgcc.uap.integrate.isc.service.IPermissionControl;
import com.sgcc.uap.integrate.isc.service.PermissionControl;
import com.sgcc.uap.rest.annotation.ColumnRequestParam;
import com.sgcc.uap.rest.annotation.ColumnResponseBody;
import com.sgcc.uap.rest.annotation.IdRequestBody;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.ItemsRequestBody;
import com.sgcc.uap.rest.annotation.QueryRequestParam;
import com.sgcc.uap.rest.annotation.VoidResponseBody;
import com.sgcc.uap.rest.annotation.attribute.ViewAttributeData;
import com.sgcc.uap.rest.support.IDRequestObject;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;
import com.sgcc.uap.rest.support.ViewMetaData;
import com.sgcc.uap.rest.support.WrappedResult;
import com.sgcc.uap.rest.utils.ViewAttributeUtils;

@Controller
@RequestMapping("/setielineresultn") //根据po类名生成
public class ThermalpowerController {
	
    @Autowired
    private IThermalpowerBizc thermalpowerBizc;
	
	/**
	 * 保存
	 * 请求体格式为:{"items":[{"id":"1","name":"1","sex":"1","age":"1"},{"id":"1","name":"1","sex":"1","age":"1"}]}
	 * @return voList
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ItemResponseBody 
	List<SeTielineResultNVO> save(@ItemsRequestBody List<Map> list){ 
		try{
    		List<SeTielineResultNVO> voList = thermalpowerBizc.saveOrUpdate(list);
    		return voList;
		}catch(Exception e){
			throw new RestClientException("保存方法异常",e);
		}
	}
	
	/**
	 * 删除操作
	 * 
	 * @param list包含map对象
	 *            ，封装ids主键值数组和idName主键名称
	 * @return null
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @VoidResponseBody
	Object delete(@IdRequestBody IDRequestObject idObject) {
		thermalpowerBizc.remove(idObject);
		return null;
	}
	
	/**
	 * 查询 火电统调电厂完成率前10名 表格数据
	 * 
	 * @param params
	 *            将请求参数{"filter":"","orderStr":"age","columns":"id,name"}
	 *            封装为RequestCondition对象
	 * @return queryResult
	 */
	@RequestMapping("/topTen")
	public @ItemResponseBody
	QueryResultObject queryTopTen(@QueryRequestParam("params") RequestCondition params, HttpServletRequest request) {
		String loginMarketId;//从数据库中获取当前登录用户
		try {
			loginMarketId = UserInfoUtil.getLoginUserMarket(request);
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
		QueryResultObject queryResult = thermalpowerBizc.queryTopTen(params, loginMarketId);
		return queryResult;
	}
	
	/**
	 * 查询 火电统调电厂完成率后10名 表格数据
	 * 
	 * @param params
	 *            将请求参数{"filter":"","orderStr":"age","columns":"id,name"}
	 *            封装为RequestCondition对象
	 * @return queryResult
	 */
	@RequestMapping("/lastTen")
	public @ItemResponseBody
	QueryResultObject queryLastTen(@QueryRequestParam("params") RequestCondition params, HttpServletRequest request) {
		String loginMarketId;//从数据库中获取当前登录用户
		try {
			loginMarketId = UserInfoUtil.getLoginUserMarket(request);
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
		QueryResultObject queryResult = thermalpowerBizc.queryLastTen(params, loginMarketId);
		return queryResult;
	}
	
	/**
	 * 查询 火电统调电厂完成率平均进度 表格数据
	 * 
	 * @param params
	 *            将请求参数{"filter":"","orderStr":"age","columns":"id,name"}
	 *            封装为RequestCondition对象
	 * @return queryResult
	 */
	@RequestMapping("/averageData")
	public @ItemResponseBody
	QueryResultObject queryAverageData(@QueryRequestParam("params") RequestCondition params, HttpServletRequest request) {
		String loginMarketId;//从数据库中获取当前登录用户
		try {
			loginMarketId = UserInfoUtil.getLoginUserMarket(request);
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
		QueryResultObject queryResult = thermalpowerBizc.queryAverageData(params, loginMarketId);
		return queryResult;
	}
	
	/**
	 * 火电统调电厂完成率前10名，获取图表数据
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getTopTenChart",method = RequestMethod.GET)
	public @ItemResponseBody Object getTopTenChart(HttpServletRequest request) throws Exception{
		String loginMarketId;//从数据库中获取当前登录用户
		try {
			loginMarketId = UserInfoUtil.getLoginUserMarket(request);
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
		String chartXml = thermalpowerBizc.getTopTenChart(loginMarketId);
		
		return chartXml;
	}
	
	/**
	 * 火电统调电厂完成率前10名，获取图表数据
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAverageDataChart",method = RequestMethod.GET)
	public @ItemResponseBody Object getAverageDataChart(@RequestParam String params, HttpServletRequest request) throws Exception{
		String loginMarketId;//从数据库中获取当前登录用户
		try {
			loginMarketId = UserInfoUtil.getLoginUserMarket(request);
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
		String chartXml = thermalpowerBizc.getAverageDataChart(params, loginMarketId);
		
		return chartXml;
	}
	
	
	
	/**
	 * 火电统调电厂完成率前10名，获取图表数据
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getLastTenChart",method = RequestMethod.GET)
	public @ItemResponseBody Object getLastTenChart(HttpServletRequest request) throws Exception{
		String loginMarketId;//从数据库中获取当前登录用户
		try {
			loginMarketId = UserInfoUtil.getLoginUserMarket(request);
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
		String chartXml = thermalpowerBizc.getLastTenChart(loginMarketId);
		
		return chartXml;
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
	QueryResultObject query(@QueryRequestParam("params") RequestCondition params) {
		return null;
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
		return thermalpowerBizc.queryById(id);
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
		datas = ViewAttributeUtils.getViewAttributes(columns, SeTielineResultNVO.class);
		return datas;
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
		datas = ViewAttributeUtils.getViewAttributes(columns, SeTielineResultNVO.class);
		ViewMetaData metaData= new ViewMetaData();
		metaData.setColumns(datas);
		IPermissionControl permControl = new PermissionControl();
		metaData = permControl.addPermInfoToMetaData(iscID, metaData);
		return WrappedResult.successWrapedResult(metaData);
	}
	/**
	 * 注入逻辑构件
	 * @param thermalpowerBizc
	 */
	public void setThermalpowerBizc(IThermalpowerBizc thermalpowerBizc) {
		this.thermalpowerBizc = thermalpowerBizc;
	}
}
