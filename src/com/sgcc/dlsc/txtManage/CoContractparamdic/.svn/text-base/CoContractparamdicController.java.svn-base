package com.sgcc.dlsc.txtManage.CoContractparamdic;
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
import com.sgcc.uap.rest.annotation.VoidResponseBody;
import com.sgcc.uap.rest.annotation.attribute.ViewAttributeData;
import com.sgcc.uap.rest.support.IDRequestObject;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;
import com.sgcc.uap.rest.support.ViewMetaData;
import com.sgcc.uap.rest.support.WrappedResult;
import com.sgcc.uap.rest.utils.ViewAttributeUtils;

import com.sgcc.dlsc.txtManage.CoContractparamdic.bizc.ICoContractparamdicBizc;
import com.sgcc.dlsc.commons.util.UserInfoUtil;
import com.sgcc.dlsc.entity.po.CoContractparamdic;
import com.sgcc.dlsc.txtManage.CoContractparamdic.vo.CoContractparamdicTransfer;
import com.sgcc.dlsc.txtManage.CoContractparamdic.vo.CoContractparamdicVO;

@Controller
@RequestMapping("/cocontractparamdic") //根据po类名生成
public class CoContractparamdicController {
	
    @Autowired
    private ICoContractparamdicBizc coContractparamdicBizc;
	//private String dicName;//字典标签名称
	/**
	 * 保存
	 * 请求体格式为:{"items":[{"id":"1","name":"1","sex":"1","age":"1"},{"id":"1","name":"1","sex":"1","age":"1"}]}
	 * @return voList
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ItemResponseBody 
	List<CoContractparamdicVO> save(@ItemsRequestBody List<Map> list, HttpServletRequest request){ 
		try{
			String loginName = UserInfoUtil.getLoginUser(request).getName();
    		List<CoContractparamdicVO> voList = coContractparamdicBizc.saveOrUpdate(list, loginName);
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
		coContractparamdicBizc.remove(idObject);
		return null;
	}
	
	/**
	 * 最大排序号+1
	 * @return queryResult
	 */
	@RequestMapping("/getNextOrderNo")
	public @ColumnResponseBody List<String> getNextOrderNo() {
		String orderNo = coContractparamdicBizc.getNextOrderNo();
		List<String> list = new ArrayList<String>();
		list.add(orderNo);
		return list;
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
		String dicName = setParamByParams(params);
		QueryResultObject queryResult = coContractparamdicBizc.query(dicName, Integer.parseInt(params.getPageIndex()),Integer.parseInt(params.getPageSize()));
		return queryResult;
	}
	
	/**
 	 * 将RequestCondition中的参数拿出来
 	 */
 	private String setParamByParams(RequestCondition params){
 		String dicName = "";
 		if(params.getFilter() != null && params.getFilter() != "" && params.getFilter() != "null") {
 			String[] params2 = params.getFilter().toString().split("&");//把params里边的查询条件分出来
 			int i = 0;	//获取params2的索引位置
 	 		//给各个变量赋值
 			dicName = setParam(params2, i++);
 		}
 		return dicName;
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
		return coContractparamdicBizc.queryById(id);
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
		datas = ViewAttributeUtils.getViewAttributes(columns, CoContractparamdicVO.class);
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
		datas = ViewAttributeUtils.getViewAttributes(columns, CoContractparamdicVO.class);
		ViewMetaData metaData= new ViewMetaData();
		metaData.setColumns(datas);
		IPermissionControl permControl = new PermissionControl();
		metaData = permControl.addPermInfoToMetaData(iscID, metaData);
		return WrappedResult.successWrapedResult(metaData);
	}
	/**
	 * 注入逻辑构件
	 * @param coContractparamdicBizc
	 */
	public void setCoContractparamdicBizc(ICoContractparamdicBizc coContractparamdicBizc) {
		this.coContractparamdicBizc = coContractparamdicBizc;
	}
}
