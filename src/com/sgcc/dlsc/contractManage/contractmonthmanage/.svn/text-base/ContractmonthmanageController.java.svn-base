package com.sgcc.dlsc.contractManage.contractmonthmanage;
import java.util.ArrayList;
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
import com.sgcc.uap.rest.utils.ViewAttributeUtils;

import com.sgcc.dlsc.commons.util.UserInfoUtil;
import com.sgcc.dlsc.contractManage.contractmonthmanage.bizc.IContractmonthmanageBizc;
import com.sgcc.dlsc.contractManage.contractmonthmanage.vo.CoContractmonthqtyTransfer;
import com.sgcc.dlsc.contractManage.contractmonthmanage.vo.CoContractmonthqtyVO;
import com.sgcc.dlsc.entity.po.CoContractmonthqty;

@Controller
@RequestMapping("/cocontractmonthqty") //根据po类名生成
public class ContractmonthmanageController {
	
    @Autowired
    private IContractmonthmanageBizc contractmonthmanageBizc;
    
    
    /**
	 * 判断选择合同是否已经在分月管理中分解过
	 * 
	 */
	@RequestMapping("/getMonthDataFlag")
	public @RawResponseBody Object getMonthDataFlag(@RequestParam("contractids") String contractids,HttpServletRequest request) {
		
		boolean flag = false;
		try {
			flag = contractmonthmanageBizc.getMonthDataFlag(contractids);
		} catch (Exception e) {
			throw new RestClientException("方法执行异常");
		}
		return flag;
		
	}
    
	/**
	 * 合同分月管理保存
	 * 
	 */
	@RequestMapping("/saveManage")
	public @RawResponseBody Object saveManage(@RequestParam("energys") String energys,@RequestParam("energyType") String energyType,@RequestParam("contractid") String contractid,HttpServletRequest request) {
		
		boolean flag = true;
		try {
			String marketId = UserInfoUtil.getLoginUserMarket(request);//获取登陆人场景
			String userId =  UserInfoUtil.getLoginUserId(request);
			contractmonthmanageBizc.saveManage(contractid,energyType,energys,marketId,userId);
		} catch (Exception e) {
			flag = false;
			throw new RestClientException("方法执行异常");
		}
		return flag;
		
	}

	
	
	/**
	 * 合同分月管理分解
	 * @param 
	 * @return queryResult
	 * @throws Exception 
	 */
	@RequestMapping(value = "/chooseUnit", method = RequestMethod.POST)
	public @VoidResponseBody Object chooseUnit(@ItemsRequestBody List<Map> items){
		try {
//			String marketId = UserInfoUtil.getLoginUserMarket(request);//获取登陆人场景
//			String userId =  UserInfoUtil.getLoginUserId(request);
			contractmonthmanageBizc.chooseUnitResolve(items);
		} catch (Exception e) {
			throw new RestClientException(e.getMessage());
		}
		return null;
	}
	/**
	 * 合同分月管理分解
	 * @param 
	 * @return queryResult
	 * @throws Exception 
	 */
	@RequestMapping("/energyManageFJ")
	public @RawResponseBody Object energyManageFJ(@RequestParam("contractid") String contractid,@RequestParam("month") String month,HttpServletRequest request){

		boolean flag = true;
		try {
			String marketId = UserInfoUtil.getLoginUserMarket(request);//获取登陆人场景
			String userId =  UserInfoUtil.getLoginUserId(request);
			contractmonthmanageBizc.energyManageFJ(contractid,month,marketId,userId);
		} catch (Exception e) {
			flag = false;
			throw new RestClientException(e.getMessage());
		}
		return WrappedResult.successWrapedResult(flag);
	}
    
	/**
	 * 保存
	 * 请求体格式为:{"items":[{"id":"1","name":"1","sex":"1","age":"1"},{"id":"1","name":"1","sex":"1","age":"1"}]}
	 * @return voList
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ItemResponseBody 
	List<CoContractmonthqtyVO> save(@ItemsRequestBody List<Map> list){ 
		try{
    		List<CoContractmonthqtyVO> voList = contractmonthmanageBizc.saveOrUpdate(list);
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
		contractmonthmanageBizc.remove(idObject);
		return null;
	}
	
	
	/**
	 * 查询电量分解结果
	 * 
	 * @param params
	 *            将请求参数{"filter":"","orderStr":"age","columns":"id,name"}
	 *            封装为RequestCondition对象
	 * @return queryResult
	 */
	@RequestMapping("/getResolveResult")
	public @ItemResponseBody
	QueryResultObject getResolveResult(@QueryRequestParam("params") RequestCondition params) {
		QueryResultObject queryResult = contractmonthmanageBizc.getResolveResult(params);
		return queryResult;
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
		QueryResultObject queryResult = contractmonthmanageBizc.query(params);
		List<CoContractmonthqty> result = queryResult.getItems();
		
		List<CoContractmonthqtyVO> voList = new ArrayList<CoContractmonthqtyVO>();
		for(int i = 0;i < result.size();i++){
			CoContractmonthqty po = (CoContractmonthqty)result.get(i);
			CoContractmonthqtyVO vo = CoContractmonthqtyTransfer.toVO(po);
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
		return contractmonthmanageBizc.queryById(id);
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
		datas = ViewAttributeUtils.getViewAttributes(columns, CoContractmonthqtyVO.class);
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
		datas = ViewAttributeUtils.getViewAttributes(columns, CoContractmonthqtyVO.class);
		ViewMetaData metaData= new ViewMetaData();
		metaData.setColumns(datas);
		IPermissionControl permControl = new PermissionControl();
		metaData = permControl.addPermInfoToMetaData(iscID, metaData);
		return WrappedResult.successWrapedResult(metaData);
	}
	/**
	 * 注入逻辑构件
	 * @param contractmonthmanageBizc
	 */
	public void setContractmonthmanageBizc(IContractmonthmanageBizc contractmonthmanageBizc) {
		this.contractmonthmanageBizc = contractmonthmanageBizc;
	}
}
