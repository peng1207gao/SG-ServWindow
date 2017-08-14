package com.sgcc.dlsc.contractManage.coContractgateinfo;
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

import com.sgcc.dlsc.commons.util.UserInfoUtil;
import com.sgcc.dlsc.contractManage.coContractgateinfo.bizc.ICoContractgateinfoBizc;
import com.sgcc.dlsc.contractManage.coContractgateinfo.vo.CoContractgateinfoVO;
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

@Controller
@RequestMapping("/cocontractgateinfo") //根据po类名生成
public class CoContractgateinfoController {
	
    @Autowired
    private ICoContractgateinfoBizc coContractgateinfoBizc;
    private String contractID;	//合同名称
	/**
	 * 保存
	 * 请求体格式为:{"items":[{"id":"1","name":"1","sex":"1","age":"1"},{"id":"1","name":"1","sex":"1","age":"1"}]}
	 * @return voList
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ItemResponseBody 
	List<CoContractgateinfoVO> save(@ItemsRequestBody List<Map> list,HttpServletRequest request){ 
		try{
    		List<CoContractgateinfoVO> voList = coContractgateinfoBizc.saveOrUpdate(list,request);
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
			coContractgateinfoBizc.remove(idObject);
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
	QueryResultObject query(@QueryRequestParam("params") RequestCondition params, HttpServletRequest request) {
		try {
			String marketId = "";
			try {
				marketId = com.sgcc.dlsc.commons.util.UserInfoUtil.getLoginUserMarket(request);
			} catch (Exception e) {
				e.printStackTrace();
			}
			setParamByParams(params);	//将RequestCondition中的参数拿出来
			QueryResultObject queryResult = coContractgateinfoBizc.findCoContractList(Integer.parseInt(params.getPageIndex()),Integer.parseInt(params.getPageSize()), contractID, marketId);
//			List<CoContractgateinfo> result = queryResult.getItems();
//			
//			List<CoContractgateinfoVO> voList = new ArrayList<CoContractgateinfoVO>();
//			for(int i = 0;i < result.size();i++){
//				CoContractgateinfo po = (CoContractgateinfo)result.get(i);
//				CoContractgateinfoVO vo = CoContractgateinfoTransfer.toVO(po);
//				voList.add(vo);
//			}
//			
//			queryResult.setItems(voList);
			return queryResult;
		} catch (Exception e) {
			throw new RestClientException("程序异常");
		}
	}
	/**
 	 * 将RequestCondition中的参数拿出来
 	 */
 	private void setParamByParams(RequestCondition params){
 		String[] params2 = params.getFilter().toString().split("&");//把params里边的查询条件分出来
 		int i = 0;	//获取params2的索引位置
 		
 		//给各个变量赋值
 		contractID = setParam(params2, i++);
 		
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
	QueryResultObject query(@PathVariable String id, HttpServletRequest request) {
		String marketId = "";
		try {
			marketId = com.sgcc.dlsc.commons.util.UserInfoUtil.getLoginUserMarket(request);
			return coContractgateinfoBizc.queryById(id, marketId);
		} catch (Exception e) {
			throw new RestClientException("程序 异常");
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
		datas = ViewAttributeUtils.getViewAttributes(columns, CoContractgateinfoVO.class);
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
		datas = ViewAttributeUtils.getViewAttributes(columns, CoContractgateinfoVO.class);
		ViewMetaData metaData= new ViewMetaData();
		metaData.setColumns(datas);
		IPermissionControl permControl = new PermissionControl();
		metaData = permControl.addPermInfoToMetaData(iscID, metaData);
		return WrappedResult.successWrapedResult(metaData);
	}
	/**
	 * 注入逻辑构件
	 * @param coContractgateinfoBizc
	 */
	public void setCoContractgateinfoBizc(ICoContractgateinfoBizc coContractgateinfoBizc) {
		this.coContractgateinfoBizc = coContractgateinfoBizc;
	}
	
	@RequestMapping("getBusiunit")
	 public @RawResponseBody Object getComponentItems(@RequestParam("participantid") String participantid){
		 try {
			 List list = coContractgateinfoBizc.getBusiunitSel(participantid);		//获取下拉框数据
			 List<Map<String,String>> result = new ArrayList<Map<String,String>>();	//返回数据List
			 for(int i = 0; i < list.size(); i++){	//遍历查询数据
				 Object[] objs = (Object[])list.get(i);	//获取第i条数据
				 String name = objs[1] == null ? "" : objs[1].toString();	//显示值
				 String text = objs[0] == null ? "" : objs[0].toString();	//实际值
				 Map<String,String> map = new HashMap<String,String>();	//每一对数据
				 map.put("text", name);		//显示值
				 map.put("value", text);	//实际值
				 result.add(map);		//添加每一对数据
			 }
			 return WrappedResult.successWrapedResult(result);		//处理数据集，返回处理结果
		} catch (Exception e) {
			throw new RestClientException("程序异常");
		}
	 }
	
	
	/**
	 * 
	 * @description 方法描述
	 * @param participantid
	 * @return
	 * @author xuzihu
	 * @date 2014-7-16
	 */
	@RequestMapping("getGateid")
	 public @RawResponseBody Object getGateItems(@RequestParam("participantid") String participantid){
		 try {
			 List list = coContractgateinfoBizc.getGateItems(participantid);		//获取下拉框数据
			 List<Map<String,String>> result = new ArrayList<Map<String,String>>();	//返回数据List
			 for(int i = 0; i < list.size(); i++){	//遍历查询数据
				 Object[] objs = (Object[])list.get(i);	//获取第i条数据
				 String name = objs[1] == null ? "" : objs[1].toString();	//显示值
				 String text = objs[0] == null ? "" : objs[0].toString();	//实际值
				 Map<String,String> map = new HashMap<String,String>();	//每一对数据
				 map.put("text", name);		//显示值
				 map.put("value", text);	//实际值
				 result.add(map);		//添加每一对数据
			 }
			 return WrappedResult.successWrapedResult(result);		//处理数据集，返回处理结果
		} catch (Exception e) {
			throw new RestClientException("程序异常");
		}
	 }
	/**
	 * 
	 * @description 方法描述
	 * @param contractid
	 * @return
	 * @author xuzihu
	 * @date 2014-7-15
	 */
	@RequestMapping("getMarketparent")
	public @RawResponseBody
	Object getMarketparent(@RequestParam("contractid") String contractid){
		List list = coContractgateinfoBizc.getMarketparent(contractid);
		return WrappedResult.successWrapedResult(list);
	}
	/**
	 * 
	 * @description 方法描述
	 * @param participantid
	 * @return
	 * @author xuzihu
	 * @date 2014-7-16
	 */
	@RequestMapping("getTieline")
	 public @RawResponseBody Object getTieline(HttpServletRequest request){
		 try {
			 String marketid =  UserInfoUtil.getLoginUserMarket(request);
			 List list = coContractgateinfoBizc.getTieline(marketid);		//获取下拉框数据
			 List<Map<String,String>> result = new ArrayList<Map<String,String>>();	//返回数据List
			 for(int i = 0; i < list.size(); i++){	//遍历查询数据
				 Object[] objs = (Object[])list.get(i);	//获取第i条数据
				 String name = objs[1] == null ? "" : objs[1].toString();	//显示值
				 String text = objs[0] == null ? "" : objs[0].toString();	//实际值
				 Map<String,String> map = new HashMap<String,String>();	//每一对数据
				 map.put("text", name);		//显示值
				 map.put("value", text);	//实际值
				 result.add(map);		//添加每一对数据
			 }
			 return WrappedResult.successWrapedResult(result);		//处理数据集，返回处理结果
		} catch (Exception e) {
			throw new RestClientException("程序异常");
		}
	 }
	/**
	 * 
	 * @description 方法描述
	 * @param request
	 * @return
	 * @author xuzihu
	 * @date 2014-7-16
	 */
	@RequestMapping("getTielinebyparticipantid")
	 public @RawResponseBody Object getTielinebyparticipantid(@RequestParam("participantid") String participantid){
		 try {
			 List list = coContractgateinfoBizc.getTielinebyparticipantid(participantid);		//获取下拉框数据
			 List<Map<String,String>> result = new ArrayList<Map<String,String>>();	//返回数据List
			 for(int i = 0; i < list.size(); i++){	//遍历查询数据
				 Object[] objs = (Object[])list.get(i);	//获取第i条数据
				 String name = objs[1] == null ? "" : objs[1].toString();	//显示值
				 String text = objs[0] == null ? "" : objs[0].toString();	//实际值
				 Map<String,String> map = new HashMap<String,String>();	//每一对数据
				 map.put("text", name);		//显示值
				 map.put("value", text);	//实际值
				 result.add(map);		//添加每一对数据
			 }
			 return WrappedResult.successWrapedResult(result);		//处理数据集，返回处理结果
		} catch (Exception e) {
			throw new RestClientException("程序异常");
		}
	 }
	/**
	 * 
	 * @description 方法描述
	 * @param request
	 * @return
	 * @author xuzihu
	 * @date 2014-7-16
	 */
	@RequestMapping("getGateidbytieline")
	 public @RawResponseBody Object getGateidbytieline(@RequestParam("tielineid") String tielineid){
		 try {
			 List list = coContractgateinfoBizc.getGateidbytieline(tielineid);		//获取下拉框数据
			 List<Map<String,String>> result = new ArrayList<Map<String,String>>();	//返回数据List
			 for(int i = 0; i < list.size(); i++){	//遍历查询数据
				 Object[] objs = (Object[])list.get(i);	//获取第i条数据
				 String name = objs[1] == null ? "" : objs[1].toString();	//显示值
				 String text = objs[0] == null ? "" : objs[0].toString();	//实际值
				 Map<String,String> map = new HashMap<String,String>();	//每一对数据
				 map.put("text", name);		//显示值
				 map.put("value", text);	//实际值
				 result.add(map);		//添加每一对数据
			 }
			 return WrappedResult.successWrapedResult(result);		//处理数据集，返回处理结果
		} catch (Exception e) {
			throw new RestClientException("程序异常");
		}
	 }
}
