package com.sgcc.dlsc.paramConfig.contractTypeInfo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;

import com.sgcc.dlsc.commons.util.DateUtil;
import com.sgcc.dlsc.commons.util.UserInfoUtil;
import com.sgcc.dlsc.entity.po.CoContracttypeinfo;
import com.sgcc.dlsc.paramConfig.contractTypeInfo.bizc.IContractTypeInfoBizc;
import com.sgcc.dlsc.paramConfig.contractTypeInfo.vo.CoContracttypeinfoTransfer;
import com.sgcc.dlsc.paramConfig.contractTypeInfo.vo.CoContracttypeinfoVO;
import com.sgcc.isc.core.orm.identity.User;
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
@RequestMapping("/cocontracttypeinfo") //根据po类名生成
public class ContractTypeInfoController {
	
    @Autowired
    private IContractTypeInfoBizc contractTypeInfoBizc;
	
	/**
	 * 保存
	 * 请求体格式为:{"items":[{"id":"1","name":"1","sex":"1","age":"1"},{"id":"1","name":"1","sex":"1","age":"1"}]}
	 * @return voList
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ItemResponseBody 
	List<CoContracttypeinfoVO> save(@ItemsRequestBody List<Map> list,HttpServletRequest request){ 
		try{
			String marketId = UserInfoUtil.getLoginUserMarket(request);
			User user = UserInfoUtil.getLoginUser(request);
			String updatepersonId = user.getUserName();
    		List<CoContracttypeinfoVO> voList = contractTypeInfoBizc.saveOrUpdate(list,marketId,updatepersonId,user);
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
			contractTypeInfoBizc.remove(idObject);
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
	QueryResultObject query(@QueryRequestParam("params") RequestCondition params) {
		QueryResultObject queryResult;
		try {
			queryResult = contractTypeInfoBizc.query(params);//进入bizc层查询结果
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
		List<CoContracttypeinfo> result = queryResult.getItems();
		
		List<CoContracttypeinfoVO> voList = new ArrayList<CoContracttypeinfoVO>();
		for(int i = 0;i < result.size();i++){
			CoContracttypeinfo po = (CoContracttypeinfo)result.get(i);
			CoContracttypeinfoVO vo = CoContracttypeinfoTransfer.toVO(po);
			voList.add(vo);
		}
		
		queryResult.setItems(voList);
		return queryResult;
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
			return contractTypeInfoBizc.queryById(id);
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
		datas = ViewAttributeUtils.getViewAttributes(columns, CoContracttypeinfoVO.class);
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
		datas = ViewAttributeUtils.getViewAttributes(columns, CoContracttypeinfoVO.class);
		ViewMetaData metaData= new ViewMetaData();
		metaData.setColumns(datas);
		IPermissionControl permControl = new PermissionControl();
		metaData = permControl.addPermInfoToMetaData(iscID, metaData);
		return WrappedResult.successWrapedResult(metaData);
	}
	
	/**
	 * 查询下拉树列表的根节点
	 * @description 方法描述
	 * @param params
	 * @return
	 * @author mengke
	 * @date 2014-2-18
	 */
	@RequestMapping(value="/queryTree",method=RequestMethod.GET)
	public @ItemResponseBody
	QueryResultObject queryTree(@QueryRequestParam("params") RequestCondition params){
		QueryResultObject queryResult;
		try {
			queryResult = contractTypeInfoBizc.queryTree(params);//进入bizc层查询下拉树
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
		return queryResult;
	}
	
	/**
	 * 查询下拉树列表的子节点
	 * @description 方法描述
	 * @param id
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception
	 * @author mengke
	 * @date 2014-2-18
	 */
	@RequestMapping("/queryTree/{id}/children")
	public @ItemResponseBody
	QueryResultObject queryDepTreeChild(@PathVariable String id,@QueryRequestParam("params") RequestCondition params) {
		QueryResultObject queryResult;
		try {
			queryResult = contractTypeInfoBizc.queryDepTreeChild(id, params);//进入bizc层查询下拉树的子节点
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
		return queryResult;
	}
	
	/**
	 * 生效合同类型
	 * @description 方法描述
	 * @param params
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @author mengke
	 * @date 2014-2-19
	 */
	@RequestMapping("/effect")
	public @ItemResponseBody List effectContract(@RequestParam String params) throws JsonParseException, JsonMappingException, IOException{
		Map map = JsonUtils.getObjectMapper().readValue(params, Map.class);
		String contractTypeId = map.get("contractTypeId").toString();//获取合同类型id
		List result;
		try {
			result = contractTypeInfoBizc.effectContract(contractTypeId);//进入bizc层执行数据库生效合同类型
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
		return result;
	}
	
	/**
	 * 失效合同类型
	 * @description 方法描述
	 * @param params
	 * @return
	 * @author mengke
	 * @throws Exception 
	 * @date 2014-2-19
	 */
	@RequestMapping("/invalid")
	public @ItemResponseBody List invalidContract(@RequestParam String params,HttpServletRequest request) throws Exception{
		Map map = JsonUtils.getObjectMapper().readValue(params, Map.class);
		String contractTypeId = map.get("contractTypeId").toString();//获取合同类型id
		String marketId = UserInfoUtil.getLoginUserMarket(request);
		List result;
		try {
			result = contractTypeInfoBizc.invalidContract(contractTypeId,
					marketId);//进入bizc层执行数据库失效合同类型
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
		return result;
	}
	
	/**
	 * 获取系统当前时间
	 * @description 方法描述
	 * @return
	 * @author mengke
	 * @date 2014-2-25
	 */
	@RequestMapping(value="/getTime",method=RequestMethod.GET)
	public @RawResponseBody Object getNowTime(){
		String time = DateUtil.getNowDate("yyyy-MM-dd");
//		Date nowTime = DateUtil.getUtilDate(time, "yyyy-MM-dd");
		return time;
	}
	
	/**
	 * 判断登录场景是否是国网总部，国网总部的可以创建父合同类型，其余场景只能创建子合同类型
	 * @description 方法描述
	 * @param request
	 * @return
	 * @throws Exception
	 * @author mengke
	 * @date 2014-3-4
	 */
	@RequestMapping("/validValue")
	public @RawResponseBody Object validValue(HttpServletRequest request) throws Exception{
		String marketId;
		try {
			marketId = UserInfoUtil.getLoginUserMarket(request);//获取当前登录场景id
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
		String result = "";
		if(!marketId.equals("91812")){
			result = "no";
		}else{
			result = "yes";
		}
		return result;
	}
	
	/**
	 * 控制编辑、生效、失效的权限
	 * @description 方法描述
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception
	 * @author mengke
	 * @date 2014-3-7
	 */
	@RequestMapping("/limits")
	public @RawResponseBody Object limitEditor(@RequestParam String params,HttpServletRequest request) throws Exception{
		String marketId;
		try {
			marketId = UserInfoUtil.getLoginUserMarket(request);//获取当前登录场景id
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
		Map map = JsonUtils.getObjectMapper().readValue(params, Map.class);//解析参数
		String seleMarketId = map.get("selectMarketId").toString();//获取选择的合同类型的维护单位场景id
		String result = "";
		if(!marketId.equals(seleMarketId)){
			result = "limit"; //选择的合同类型的维护单位场景id与当前登录场景id不同，说明是非国网场景选择第一级合同类型，要加以限制
		}else{
			result = "nolimit";//选择的合同类型的维护单位场景id与当前登录场景id相同，说明是选择的本场景下的合同类型，可以维护
		}
		return result;
	}
	
	/**
	 * 
	 * @description 获取已经存在的合同类型名称
	 * @param request
	 * @param typename
	 * @param supertypeid
	 * @param objID
	 * @return
	 * @throws Exception
	 * @author mengke
	 * @date 2014-3-17
	 */
	@RequestMapping("/getTypeName")
	public @ItemResponseBody List getTypeName(HttpServletRequest request,@RequestParam String typename,@RequestParam String supertypeid,@RequestParam String objID) throws Exception{
		String marketId;
		try {
			marketId = UserInfoUtil.getLoginUserMarket(request);//获取当前登录场景id
		} catch (Exception e) {
			throw new RestClientException("获取当前登录场景id失败！");
		}
		List list;
		try {
			list = contractTypeInfoBizc.getTypeName(marketId, typename,
					supertypeid, objID);//获取已经存在的合同类型名称
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
		return list;
	}
	
	/**
	 * 注入逻辑构件
	 * @param contractTypeInfoBizc
	 */
	public void setContractTypeInfoBizc(IContractTypeInfoBizc contractTypeInfoBizc) {
		this.contractTypeInfoBizc = contractTypeInfoBizc;
	}
}
