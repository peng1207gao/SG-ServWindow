package com.sgcc.dlsc.contractManage.multiLevelContractManage;
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
import com.sgcc.dlsc.contractManage.IdeaConManage.bizc.IIdeaConManageBizc;
import com.sgcc.dlsc.contractManage.IdeaConManage.vo.CoContractbaseinfoVO;
import com.sgcc.dlsc.contractManage.multiLevelContractManage.bizc.IMultiLevelContractManageBizc;
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

@Controller
@RequestMapping("/multiLevelContractManage") //根据po类名生成
public class MultiLevelContractManageController {
	
    @Autowired
    private IMultiLevelContractManageBizc multiLevelContractManageBizc;
    
    @Autowired
    private IIdeaConManageBizc ideaConManageBizc;
    
    @RequestMapping(value = "/setUp", method = RequestMethod.GET)
	public @RawResponseBody Object setUp(@RequestParam String params, HttpServletRequest request){
		String result;
		try {
			result = multiLevelContractManageBizc.setUp(params, request);
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
		return WrappedResult.successWrapedResult(result);
	}
    
    /**
	 * 修改合同关联权限
	 * 
	 * @param list
	 */
	@RequestMapping(value = "/authorityRelation", method = RequestMethod.GET)
	public @ItemResponseBody QueryResultObject authorityRelation(@RequestParam String params, HttpServletRequest request){
		Map map = null;
		try {
			map = JsonUtils.getObjectMapper().readValue(params, Map.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(map != null){
			String contractid = map.get("contractid")==null?"":map.get("contractid").toString();
			String isrelation = map.get("isrelation")==null?"":map.get("isrelation").toString();
			try{
				QueryResultObject vo = multiLevelContractManageBizc.updateContractInfo(contractid, isrelation, request);
	    		return vo;
			}catch(Exception e){
				throw new RestClientException("保存方法异常");
			}
		}
		return null;
	}
    
	/**
	 * 保存
	 * 请求体格式为:{"items":[{"id":"1","name":"1","sex":"1","age":"1"},{"id":"1","name":"1","sex":"1","age":"1"}]}
	 * @return voList
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ItemResponseBody 
	List<CoContractbaseinfoVO> save(@ItemsRequestBody List<Map> list){ 
		try{
    		List<CoContractbaseinfoVO> voList = multiLevelContractManageBizc.saveOrUpdate(list);
    		return voList;
		}catch(Exception e){
			throw new RestClientException("保存方法异常");
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
		try {
			multiLevelContractManageBizc.remove(idObject);
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
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
		String loginMarketId;
		try {
			loginMarketId = UserInfoUtil.getLoginUserMarket(request);//从数据库中获取当前登录用户
			QueryResultObject queryResult = ideaConManageBizc.query(params, loginMarketId);//  采用  ideaConManageBizc 中的query方法
			return queryResult;
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
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
			return multiLevelContractManageBizc.queryById(id);
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
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
	 * @param multiLevelContractManageBizc
	 */
	public void setMultiLevelContractManageBizc(IMultiLevelContractManageBizc multiLevelContractManageBizc) {
		this.multiLevelContractManageBizc = multiLevelContractManageBizc;
	}
}
