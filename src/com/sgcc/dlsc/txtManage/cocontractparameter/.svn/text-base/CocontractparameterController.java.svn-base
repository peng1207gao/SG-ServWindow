package com.sgcc.dlsc.txtManage.cocontractparameter;
import java.util.ArrayList;
import java.util.HashMap;
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

import com.sgcc.uap.integrate.isc.service.IPermissionControl;
import com.sgcc.uap.integrate.isc.service.PermissionControl;
import com.sgcc.uap.rest.annotation.ColumnRequestParam;
import com.sgcc.uap.rest.annotation.ColumnResponseBody;
import com.sgcc.uap.rest.annotation.IdRequestBody;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.ItemsRequestBody;
import com.sgcc.uap.rest.annotation.QueryRequestParam;
import com.sgcc.uap.rest.annotation.RawResponseBody;
import com.sgcc.uap.rest.annotation.VoidResponseBody;
import com.sgcc.uap.rest.annotation.attribute.ViewAttributeData;
import com.sgcc.uap.rest.support.IDRequestObject;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;
import com.sgcc.uap.rest.support.ViewMetaData;
import com.sgcc.uap.rest.support.WrappedResult;
import com.sgcc.uap.rest.utils.JsonUtils;
import com.sgcc.uap.rest.utils.ViewAttributeUtils;

import com.sgcc.dlsc.txtManage.cocontractparameter.bizc.ICocontractparameterBizc;
import com.sgcc.dlsc.commons.util.UserInfoUtil;
import com.sgcc.dlsc.entity.po.CoContractparameter;
import com.sgcc.dlsc.txtManage.cocontractparameter.vo.CoContractparameterTransfer;
import com.sgcc.dlsc.txtManage.cocontractparameter.vo.CoContractparameterVO;

@Controller
@RequestMapping("/cocontractparameter") //根据po类名生成
public class CocontractparameterController {
	
    @Autowired
    private ICocontractparameterBizc cocontractparameterBizc;

    /**
     * 复制标签
     * @param 
     * @author xiabaike
     * @return true/false
     * @throws Exception
     */ 
	@RequestMapping(value = "/copyParam")
    public @RawResponseBody Object copyParam(@RequestParam("params")String params, HttpServletRequest request) throws Exception{
    	String marketId = UserInfoUtil.getLoginUserMarket(request);//业务场景ID
		String userId = UserInfoUtil.getLoginUserId(request);//当前登陆人ID
		Map map = JsonUtils.getObjectMapper().readValue(params, Map.class);
    	boolean result = cocontractparameterBizc.copyParam(marketId, userId, map.get("tempparamid").toString(), map.get("bookmark").toString());
    	return result;
    }
    
    /**
	 * 查询页面详细配置信息
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({"rawtypes"})
	@RequestMapping(value = "/findAllParamDic")
	public @RawResponseBody Object findAllParamDic(@QueryRequestParam("params") RequestCondition params, @RequestParam("modulekey") String moduleKey) throws Exception{
		try {
			//String marketId = UserInfoUtil.getLoginUserMarket(request);//业务场景ID
			
			//String templateCode = request.getParameterValues("modulekey");// setParamByParams(params);
			//得到预览数据
			List modelDetailList = cocontractparameterBizc.findAllParamDic(moduleKey);
			return WrappedResult.successWrapedResult(modelDetailList);
		} catch (Exception e) {
			throw new RestClientException("程序异常");
		}
	}
    
    /**
	 * 批量维护合同参数保存
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateCoParam")
	public @RawResponseBody Object updateCoParam(@RequestParam("params") String params, HttpServletRequest request) throws Exception{
		Map map = JsonUtils.getObjectMapper().readValue(params, Map.class);
		
			String marketId = UserInfoUtil.getLoginUserMarket(request);//业务场景ID
			String userId = UserInfoUtil.getLoginUserId(request);//当前登陆人ID
			//String templateCode = request.getParameterValues("modulekey");// setParamByParams(params);
//			List<String> list = setParamByParams1(params);
			boolean result = cocontractparameterBizc.updateCoParam(map.get("moduleId").toString(), marketId, userId, map.get("paramsList").toString());
			return result;
	}
	
	/**
	 * 保存
	 * 请求体格式为:{"items":[{"id":"1","name":"1","sex":"1","age":"1"},{"id":"1","name":"1","sex":"1","age":"1"}]}
	 * @return voList
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ItemResponseBody 
	List<CoContractparameterVO> save(@ItemsRequestBody List<Map> list){ 
		try{
    		List<CoContractparameterVO> voList = cocontractparameterBizc.saveOrUpdate(list);
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
		cocontractparameterBizc.remove(idObject);
		return null;
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
		String loginMarketId;//从数据库中获取当前登录用户
		try {
			loginMarketId = UserInfoUtil.getLoginUserMarket(request);
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
		String contracttemplateId = setParamByParams(params);
		QueryResultObject queryResult = cocontractparameterBizc.query(contracttemplateId, loginMarketId, 
				Integer.parseInt(params.getPageIndex()),Integer.parseInt(params.getPageSize()));
		return queryResult;
	}
	
	/**
 	 * 将RequestCondition中的参数拿出来
 	 */
 	private String setParamByParams(RequestCondition params){
 		
 		String contracttemplateId = "";
 		if(params.getFilter() != null && params.getFilter() != "" && params.getFilter() != "null") {
 			String[] params2 = params.getFilter().toString().split("&");//把params里边的查询条件分出来
 			int i = 0;	//获取params2的索引位置
 	 		//给各个变量赋值
 			contracttemplateId = setParam(params2, i++);
 			//showId = setParam(params2, i++);
 		}
 		return contracttemplateId;
 	}
 	
	/**
 	 * 将RequestCondition中的参数拿出来
 	 */
 	private List<String> setParamByParams1(RequestCondition params){
 		List<String> list = new ArrayList<String>();
 		String contracttemplateId = "";
 		String dicValue = "";
 		if(params.getFilter() != null && params.getFilter() != "" && params.getFilter() != "null") {
 			String[] params2 = params.getFilter().toString().split("&");//把params里边的查询条件分出来
 			int i = 0;	//获取params2的索引位置
 	 		//给各个变量赋值
 			contracttemplateId = setParam(params2, i++);
 			dicValue = setParam(params2, i++);
 		}
 		list.add(contracttemplateId);
 		list.add(dicValue);
 		return list;
 	}
	
 	/**
	 * 给变量赋值
	 * @param params2
	 * @param i
	 * @return
	 */
	 private String setParam(String[] params2,int i){
	 	return params2[i].split("=").length > 1 ? params2[i].split("=")[1] : "";
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
		return cocontractparameterBizc.queryById(id);
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
		datas = ViewAttributeUtils.getViewAttributes(columns, CoContractparameterVO.class);
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
		datas = ViewAttributeUtils.getViewAttributes(columns, CoContractparameterVO.class);
		ViewMetaData metaData= new ViewMetaData();
		metaData.setColumns(datas);
		IPermissionControl permControl = new PermissionControl();
		metaData = permControl.addPermInfoToMetaData(iscID, metaData);
		return WrappedResult.successWrapedResult(metaData);
	}
	/**
	 * 注入逻辑构件
	 * @param cocontractparameterBizc
	 */
	public void setCocontractparameterBizc(ICocontractparameterBizc cocontractparameterBizc) {
		this.cocontractparameterBizc = cocontractparameterBizc;
	}
}
