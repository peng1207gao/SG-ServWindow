package com.sgcc.dlsc.contractManage.CoContransEnergyInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import com.sgcc.dlsc.commons.util.UserInfoUtil;
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

import com.sgcc.dlsc.contractManage.CoContransEnergyInfo.bizc.ICoContransEnergyInfoBizc;
import com.sgcc.dlsc.contractManage.CoContransEnergyInfo.vo.CoContransenergyinfoVO;
import com.sgcc.dlsc.entity.po.CoContransenergyinfo;

@Controller
@RequestMapping("/cocontransenergyinfo") //根据po类名生成
public class CoContransEnergyInfoController {
	
    @Autowired
    private ICoContransEnergyInfoBizc coContransEnergyInfoBizc;
	private CoContransenergyinfo coContransenergyinfo;//输电方实体类
	private CoContransenergyinfoVO ccevo;//输电方实体类


    private String contractId;//合同号
    private String contractName;//合同名
    private String participantName;//PARTICIPANTNAME 输电方交易单元
    private String licenceCode;//许可证号
    private String taxCode;//税号
    private String linkMan;//联系人
    private String telephone;//联系电话
    //String contractId,String contractName,String participantName,String licenceCode,String taxCode, String linkMan,String telephone
    
	/**
	 * 查询
	 * 
	 * @param params
	 *            将请求参数{"filter":"","orderStr":"age","columns":"id,name"}
	 *            封装为RequestCondition对象
	 * @return queryResult
	 */
	@RequestMapping("/saveSdf")
	public @ItemResponseBody List<String> saveSdf(@QueryRequestParam("params") HttpServletRequest request) {
		try {
			String finStr = "";
			String contractId = request.getParameter("contractid").toString();
			String guid = request.getParameter("guid").toString();
			String sdf = request.getParameter("sdf").toString();
			CoContransenergyinfo cce = new CoContransenergyinfo();
			cce.setContractid(contractId);
			cce.setGuid(guid);
			cce.setTransmission(sdf);
			cce.setTransperson("xbk");
			Map<String, CoContransenergyinfo> map = new HashMap<String, CoContransenergyinfo>();
			map.put(guid, cce);
			List<Map> list = new ArrayList<Map>();
			list.add(map);
			if(!coContransEnergyInfoBizc.judgeExist(contractId, guid, sdf)){
				finStr = "false";
			}else{
				List<CoContransenergyinfoVO> voList = coContransEnergyInfoBizc.saveOrUpdate(list);
				if(!voList.isEmpty()) {
					finStr = "success";
				}else{
					finStr = "fail";
				}
			}
			List<String> list1 = new ArrayList<String>();
			list1.add(finStr);
			System.out.println("-----finStr:"+finStr);
	 		return list1;
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
	 * 保存
	 * 请求体格式为:{"items":[{"id":"1","name":"1","sex":"1","age":"1"},{"id":"1","name":"1","sex":"1","age":"1"}]}
	 * @return voList
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ItemResponseBody 
	List<CoContransenergyinfoVO> save(@ItemsRequestBody List<Map> list){ 
		try{
    		List<CoContransenergyinfoVO> voList = coContransEnergyInfoBizc.saveOrUpdate(list);
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
			coContransEnergyInfoBizc.remove(idObject);
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
	 */
	@RequestMapping("/")
	public @ItemResponseBody
	QueryResultObject query(@QueryRequestParam("params") RequestCondition params) {
		try {
			QueryResultObject result;//定义返回值
	 		setParamByParams(params);	//将RequestCondition中的参数拿出来
	 		//contractId = (String) params.getFilter();
	 		result = coContransEnergyInfoBizc
	 				.query(contractId, Integer.parseInt(params.getPageIndex()),Integer.parseInt(params.getPageSize()));
	 		return result;
		} catch (Exception e) {
			throw new RestClientException("程序异常");
		}
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
			return coContransEnergyInfoBizc.queryById(id, marketId);
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
	 * @param coContransEnergyInfoBizc
	 */
	public void setCoContransEnergyInfoBizc(ICoContransEnergyInfoBizc coContransEnergyInfoBizc) {
		this.coContransEnergyInfoBizc = coContransEnergyInfoBizc;
	}
}
