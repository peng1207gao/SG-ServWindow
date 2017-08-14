package com.sgcc.dlsc.contractManage.CoContransEnergyInfoV2;
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

import com.sgcc.dlsc.commons.util.UserInfoUtil;
import com.sgcc.dlsc.contractManage.CoContransEnergyInfoV2.bizc.ICoContransEnergyInfoV2Bizc;
import com.sgcc.dlsc.entity.po.CoContransenergyinfo;
import com.sgcc.dlsc.contractManage.CoContransEnergyInfoV2.vo.CoContransenergyinfoTransfer;
import com.sgcc.dlsc.contractManage.CoContransEnergyInfoV2.vo.CoContransenergyinfoVO;

@Controller
@RequestMapping("/cocontransenergyinfov2") //根据po类名生成
public class CoContransEnergyInfoV2Controller {
	
    @Autowired
    private ICoContransEnergyInfoV2Bizc coContransEnergyInfoV2Bizc;

    private String guid;//
    private String contractId;//合同ID
    private String transmission;//合同输电方ID
    private String participantName;//合同输电方
    private String name1;//输电费用收取方式
    private String cost;//输电费
    private String price;//输电及网损价
    private String transphone;//说明
    private String name;//经济属性
    private String taxrate;//税率
    private String energypercent;//电量百分比
    private String loss;//输电损耗
    private String startdate;//开始时间
    private String enddate;//截止时间
	/**
	 * 保存
	 * 请求体格式为:{"items":[{"id":"1","name":"1","sex":"1","age":"1"},{"id":"1","name":"1","sex":"1","age":"1"}]}
	 * @return voList
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ItemResponseBody 
	List<CoContransenergyinfoVO> save(@ItemsRequestBody List<Map> list){ 
		try{
    		List<CoContransenergyinfoVO> voList = coContransEnergyInfoV2Bizc.saveOrUpdate(list);
    		System.out.println("--------voList:"+voList);
    		return voList;
		}catch(Exception e){
			throw new RestClientException("程序异常");
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
			coContransEnergyInfoV2Bizc.remove(idObject);
			return null;
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
	 * @throws Exception 
	 */
	@RequestMapping("/")
	public @ItemResponseBody
	QueryResultObject query(@QueryRequestParam("params") RequestCondition params) throws Exception {
		try {
			QueryResultObject result;
			setParamByParams(params);	//将RequestCondition中的参数拿出来
			result = coContransEnergyInfoV2Bizc
	 				.query(contractId, Integer.parseInt(params.getPageIndex()),Integer.parseInt(params.getPageSize()));
			return result;
		} catch (Exception e) {
			throw new RestClientException("程序异常");
		}
	}
	
	/**
 	 * 将RequestCondition中的参数拿出来
 	 */
 	private void setParamByParams(RequestCondition params){
 		
 		if(params.getFilter() != null && params.getFilter() != "" && params.getFilter() != "null") {
 			String[] params2 = params.getFilter().toString().split("&");//把params里边的查询条件分出来
 			int i = 0;	//获取params2的索引位置
 	 		//给各个变量赋值
 			contractId = setParam(params2, i++);
 		}
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
	 * @throws Exception 
	 */
	@RequestMapping("/{id}")
	public @ItemResponseBody
	QueryResultObject query(@PathVariable String id, HttpServletRequest request) throws Exception {
		try {
			String marketId = UserInfoUtil.getLoginUserMarket(request);
			QueryResultObject result = coContransEnergyInfoV2Bizc.queryById(id, marketId);
			return result;
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
		datas = ViewAttributeUtils.getViewAttributes(columns, CoContransenergyinfoVO.class);
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
		datas = ViewAttributeUtils.getViewAttributes(columns, CoContransenergyinfoVO.class);
		ViewMetaData metaData= new ViewMetaData();
		metaData.setColumns(datas);
		IPermissionControl permControl = new PermissionControl();
		metaData = permControl.addPermInfoToMetaData(iscID, metaData);
		return WrappedResult.successWrapedResult(metaData);
	}
	/**
	 * 注入逻辑构件
	 * @param coContransEnergyInfoV2Bizc
	 */
	public void setCoContransEnergyInfoV2Bizc(ICoContransEnergyInfoV2Bizc coContransEnergyInfoV2Bizc) {
		this.coContransEnergyInfoV2Bizc = coContransEnergyInfoV2Bizc;
	}
}
