package com.sgcc.dlsc.contractManage.resolveelectricquantitytounit;
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
import com.sgcc.dlsc.contractManage.resolveelectricquantitytounit.bizc.IResolveelectricquantitytounitBizc;
import com.sgcc.dlsc.contractManage.resolveelectricquantitytounit.vo.CoContractenergyinfoVO;
import com.sgcc.uap.integrate.isc.service.IPermissionControl;
import com.sgcc.uap.integrate.isc.service.PermissionControl;
import com.sgcc.uap.rest.annotation.ColumnRequestParam;
import com.sgcc.uap.rest.annotation.ColumnResponseBody;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.ItemsRequestBody;
import com.sgcc.uap.rest.annotation.QueryRequestParam;
import com.sgcc.uap.rest.annotation.RawResponseBody;
import com.sgcc.uap.rest.annotation.attribute.ViewAttributeData;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;
import com.sgcc.uap.rest.support.ViewMetaData;
import com.sgcc.uap.rest.support.WrappedResult;
import com.sgcc.uap.rest.utils.ViewAttributeUtils;

@Controller
@RequestMapping("/resolveelectricquantitytounit") //根据po类名生成
public class ResolveelectricquantitytounitController {
	
    @Autowired
    private IResolveelectricquantitytounitBizc resolveelectricquantitytounitBizc;
	
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
		QueryResultObject queryResult;
		try {
			queryResult = resolveelectricquantitytounitBizc.query(params);
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
		return queryResult;
	}
	
	@RequestMapping("/getUnitGrid")
	public @ItemResponseBody
	QueryResultObject getUnitGrid(@QueryRequestParam("params") RequestCondition params) {
		QueryResultObject queryResult;
		try {
			queryResult = resolveelectricquantitytounitBizc.getUnitGrid(params);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RestClientException("程序异常！");
		}
		return queryResult;
	}
	
	
	/**
	 * 分解电量到经济机组，查询拼装数据
	 * 
	 * @param params
	 *            将请求参数{"filter":"","orderStr":"age","columns":"id,name"}
	 *            封装为RequestCondition对象
	 * @return queryResult
	 */
	@RequestMapping("/contractAccessory")
	public @ItemResponseBody
	QueryResultObject contractAccessory(@QueryRequestParam("params") RequestCondition params) {
		QueryResultObject queryResult;
		try {
			queryResult = resolveelectricquantitytounitBizc.contractAccessory(params);
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
		return queryResult;
	}
	
	/**
	 * 获取物理机组和经济机组下拉框内容
	 * @param params
	 * @return
	 */
	@RawResponseBody
	@RequestMapping(value = "/getUnitList", method = RequestMethod.GET)
	public Object getUnitList(@RequestParam String params, @RequestParam("isUnit") String isUnit){
		List unitList = resolveelectricquantitytounitBizc.getUnitList(params,isUnit);
		return WrappedResult.successWrapedResult(unitList);
	}
	
	/**
	 * 保存
	 * 请求体格式为:{"items":[{"id":"1","name":"1","sex":"1","age":"1"},{"id":"1","name":"1","sex":"1","age":"1"}]}
	 * @return voList
	 */
	@RequestMapping(value = "/saveBusiGeneratorQty", method = RequestMethod.POST)
	public @RawResponseBody Object saveBusiGeneratorQty(HttpServletRequest request, @ItemsRequestBody List<Map> items){
		try{
			String marketid = UserInfoUtil.getLoginUserMarket(request);//从数据库中获取当前登录用户
    		String isSuccess = resolveelectricquantitytounitBizc.saveOrUpdate(marketid, request);
    		if("success".equals(isSuccess)){
    			if(items != null && items.size() != 0){
        			String updateSuccess = resolveelectricquantitytounitBizc.update(items);
            		if("success".equals(updateSuccess)){
            			return WrappedResult.successWrapedResult("success");
            		} else {
            			return WrappedResult.successWrapedResult("fail");
            		}
    			} else {
    				return WrappedResult.successWrapedResult("success");
    			}
    		} else if("empty".equals(isSuccess)){
    			return WrappedResult.successWrapedResult("empty");
    		} else {
    			return WrappedResult.successWrapedResult("fail");
    		}
		}catch(Exception e){
			e.printStackTrace();
			throw new RestClientException("saveBusiGeneratorQty保存方法异常");
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
			return resolveelectricquantitytounitBizc.queryById(id);
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
	}
	
	@RequestMapping("/meta")
	public @ColumnResponseBody
	List<ViewAttributeData> getMetaData(
			@ColumnRequestParam("params") String[] columns) {
		List<ViewAttributeData> datas = null;
		datas = ViewAttributeUtils.getViewAttributes(columns, CoContractenergyinfoVO.class);
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
		datas = ViewAttributeUtils.getViewAttributes(columns, CoContractenergyinfoVO.class);
		ViewMetaData metaData= new ViewMetaData();
		metaData.setColumns(datas);
		IPermissionControl permControl = new PermissionControl();
		metaData = permControl.addPermInfoToMetaData(iscID, metaData);
		return WrappedResult.successWrapedResult(metaData);
	}
	
	/**
	 * 注入逻辑构件
	 * @param resolveelectricquantitytounitBizc
	 */
	public void setResolveelectricquantitytounitBizc(IResolveelectricquantitytounitBizc resolveelectricquantitytounitBizc) {
		this.resolveelectricquantitytounitBizc = resolveelectricquantitytounitBizc;
	}
}
