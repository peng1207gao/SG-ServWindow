package com.sgcc.dlsc.contractManage.cotransqtyinfo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Id;
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

import com.sgcc.dlsc.commons.util.UserInfoUtil;
import com.sgcc.dlsc.contractManage.cotransqtyinfo.bizc.ICotransqtyinfoBizc;
import com.sgcc.dlsc.contractManage.cotransqtyinfo.vo.CoTransqtyinfoVO;
import com.sgcc.dlsc.contractManage.cotransqtyinfo.vo.CoTransqtyslaveinfoVO;
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
@RequestMapping("/cotransqtyinfo") //根据po类名生成
public class CotransqtyinfoController {
	
    @Autowired
    private ICotransqtyinfoBizc cotransqtyinfoBizc;
	
	/**
	 * 保存
	 * 请求体格式为:{"items":[{"id":"1","name":"1","sex":"1","age":"1"},{"id":"1","name":"1","sex":"1","age":"1"}]}
	 * @return voList
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ItemResponseBody 
	List<CoTransqtyinfoVO> save(@ItemsRequestBody List<Map> list,HttpServletRequest request){ 
		try{
			String marketId = UserInfoUtil.getLoginUserMarket(request);
			User user = UserInfoUtil.getLoginUser(request);
    		List<CoTransqtyinfoVO> voList = cotransqtyinfoBizc.saveOrUpdate(list,marketId,user);
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
			cotransqtyinfoBizc.remove(idObject);
			return null;
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
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
			QueryResultObject queryResult = cotransqtyinfoBizc.query(params);//进入bizc层查询结果
			List<CoTransqtyinfoVO> voList = new ArrayList<CoTransqtyinfoVO>();
			voList = queryResult.getItems();
			queryResult.setItems(voList);
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
	 * @throws Exception 
	 */
	@RequestMapping("/{id}")
	public @ItemResponseBody
	QueryResultObject query(@PathVariable String id,HttpServletRequest request) throws Exception {
		try {
			String marketId = UserInfoUtil.getLoginUserMarket(request);//获取当前登录场景id
			return cotransqtyinfoBizc.queryById(id, marketId);//进入bizc层查询结果
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
		datas = ViewAttributeUtils.getViewAttributes(columns, CoTransqtyinfoVO.class);
		return datas;
	}
	
	@RequestMapping("/queryElec/meta")
	public @ColumnResponseBody
	List<ViewAttributeData> getMetaData1(
			@ColumnRequestParam("params") String[] columns) {
		List<ViewAttributeData> datas = null;
		datas = ViewAttributeUtils.getViewAttributes(columns, CoTransqtyslaveinfoVO.class);
		return datas;
	}
	
	@RequestMapping("/queryDlqx/meta")
	public @ColumnResponseBody
	List<ViewAttributeData> getMetaData2(
			@ColumnRequestParam("params") String[] columns) {
		List<ViewAttributeData> datas = null;
		datas = ViewAttributeUtils.getViewAttributes(columns, CoTransqtyslaveinfoVO.class);
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
		datas = ViewAttributeUtils.getViewAttributes(columns, CoTransqtyinfoVO.class);
		ViewMetaData metaData= new ViewMetaData();
		metaData.setColumns(datas);
		IPermissionControl permControl = new PermissionControl();
		metaData = permControl.addPermInfoToMetaData(iscID, metaData);
		return WrappedResult.successWrapedResult(metaData);
	}
	
	/**
	 * 根据联络线id查询起点关口信息
	 * @description 方法描述
	 * @param params
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @author mengke
	 * @date 2014-2-10
	 */
	@RequestMapping("getStartGate")
	public @ItemResponseBody List getStartGate(@RequestParam String params) throws 
		JsonParseException, JsonMappingException, IOException{
		try {
			Map map = JsonUtils.getObjectMapper().readValue(params, Map.class);//解析参数
			String linkid = map.get("linkid").toString();//获取市场成员id
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();//存放下拉框的name和value值
			List queryResult = cotransqtyinfoBizc.listStartGate(linkid);
			if (queryResult != null && queryResult.size() > 0) {
				for (int i = 0; i < queryResult.size(); i++) {//遍历查询结果
					Map<String, String> map1 = new HashMap<String, String>();
					Object[] objs = (Object[]) queryResult.get(i);
					map1.put("name", objs[0] == null ? "" : objs[0].toString());//显示值
					map1.put("value", objs[1] == null ? "" : objs[1].toString());//实际值
					list.add(map1);
				}
			}
			return list;
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
	}
	
	/**
	 * 根据联络线id查询终点关口信息
	 * @description 方法描述
	 * @param params
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @author mengke
	 * @date 2014-2-10
	 */
	@RequestMapping("getEndGate")
	public @ItemResponseBody List getEndGate(@RequestParam String params) throws 
	JsonParseException, JsonMappingException, IOException{
		try {
			Map map = JsonUtils.getObjectMapper().readValue(params, Map.class);//解析参数
			String linkid = map.get("linkid").toString();//获取市场成员id
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();//存放下拉框的name和value值
			List queryResult = cotransqtyinfoBizc.listEndGate(linkid);
			if (queryResult != null && queryResult.size() > 0) {
				for (int i = 0; i < queryResult.size(); i++) {//遍历查询结果
					Map<String, String> map1 = new HashMap<String, String>();
					Object[] objs = (Object[]) queryResult.get(i);
					map1.put("name", objs[0] == null ? "" : objs[0].toString());//显示值
					map1.put("value", objs[1] == null ? "" : objs[1].toString());//实际值
					list.add(map1);
				}
			}
			return list;
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
	}
	
	/**
	 * 
	 * @description 查询电力信息
	 * @param params
	 * @return
	 * @author mengke
	 * @date 2014-3-24
	 */
	@RequestMapping("/queryElec")
	public @ItemResponseBody
	QueryResultObject queryElecInfo(@QueryRequestParam("params") RequestCondition params) {
		try {
			QueryResultObject queryResult = cotransqtyinfoBizc
					.queryElecInfo(params);//进入bizc层查询结果
			List<CoTransqtyslaveinfoVO> voList = new ArrayList<CoTransqtyslaveinfoVO>();
			voList = queryResult.getItems();
			queryResult.setItems(voList);
			return queryResult;
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
	}
	
	/**
	 * 
	 * @description 查询单条电力信息
	 * @param id
	 * @param request
	 * @return
	 * @throws Exception
	 * @author mengke
	 * @date 2014-3-24
	 */
	@RequestMapping("/queryElec/{id}")
	public @ItemResponseBody
	QueryResultObject queryElecPower(@PathVariable String id,HttpServletRequest request) throws Exception {
		String marketId = UserInfoUtil.getLoginUserMarket(request);//获取当前登录场景id
		try {
			return cotransqtyinfoBizc.queryElecById(id, marketId);//进入bizc层查询结果
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
	}
	
	/**
	 * 保存
	 * 请求体格式为:{"items":[{"id":"1","name":"1","sex":"1","age":"1"},{"id":"1","name":"1","sex":"1","age":"1"}]}
	 * @return voList
	 */
	@RequestMapping(value = "/queryElec/save", method = RequestMethod.POST)
	public @ItemResponseBody 
	List<CoTransqtyslaveinfoVO> saveElec(@ItemsRequestBody List<Map> list,HttpServletRequest request){ 
		try{
			String marketId = UserInfoUtil.getLoginUserMarket(request);
			User user = UserInfoUtil.getLoginUser(request);
    		List<CoTransqtyslaveinfoVO> voList = cotransqtyinfoBizc.saveOrUpdateElec(list,marketId,user);
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
	@RequestMapping(value = "/queryElec/delete", method = RequestMethod.POST)
	public @VoidResponseBody
	Object deleteElec(@IdRequestBody IDRequestObject idObject) {
		try {
			cotransqtyinfoBizc.removeElec(idObject);
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
		return null;
	}
	
	/**
	 * 
	 * @description 查询合同开始时间和结束时间
	 * @param contractid
	 * @return
	 * @author mengke
	 * @date 2014-3-17
	 */
	@RequestMapping("/getContractDate")
	public @ItemResponseBody List getContractDate(@RequestParam String params) throws Exception{
		Map map = JsonUtils.getObjectMapper().readValue(params, Map.class);//解析参数
		String contractid = map.get("contractid").toString();//获取市场成员id
		List list;
		try {
			list = cotransqtyinfoBizc.getContractDate(contractid);//进入bizc层查询结果
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
		return list;
	}
	
	/**
	 * 
	 * @description 查询已经选中的输电方
	 * @param transid
	 * @return
	 * @author mengke
	 * @date 2014-3-20
	 */
	@RequestMapping("/getSeleSdf")
	public @RawResponseBody Object getSeleSdf(@RequestParam("transid") String transid){
		List list;
		try {
			list = cotransqtyinfoBizc.getSeleSdf(transid);//根据选中的输电id，进入bizc层查询结果
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
		List<Map<String,String>> mapList = new ArrayList<Map<String,String>>();
		Map<String,String> map = new HashMap<String,String>();
		if(list!=null && list.size()>0){
			Object[] objs = (Object[])list.get(0);
			map.put("value", objs[0]==null?"":objs[0].toString());//实际值
			map.put("name", objs[1]==null?"":objs[1].toString());//显示值
			mapList.add(map);
		}
		return WrappedResult.successWrapedResult(mapList);
	}
	
	/**
	 * 
	 * @description 查询电力曲线
	 * @param params
	 * @return
	 * @author mengke
	 * @date 2014-6-16
	 */
	@RequestMapping("/queryDlqx")
	public @ItemResponseBody
	QueryResultObject queryDlqx(@QueryRequestParam("params") RequestCondition params){
		try {
			QueryResultObject queryResult = cotransqtyinfoBizc
					.queryDlqx(params);//进入bizc层查询结果
			List<CoTransqtyslaveinfoVO> voList = new ArrayList<CoTransqtyslaveinfoVO>();
			voList = queryResult.getItems();
			queryResult.setItems(voList);
			return queryResult;
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
	}
	
	
	@RequestMapping("/removeDlqx")
	public @ItemResponseBody List getStartGate1(@RequestParam String params) throws 
		JsonParseException, JsonMappingException, IOException{
		List list = new ArrayList();
		try {
			Map map = JsonUtils.getObjectMapper().readValue(params, Map.class);//解析参数
			String transInfoId = map.get("transInfoId")==null?"":map.get("transInfoId").toString();//获取市场成员id
			List list1 = map.get("bights")==null?null:(List)map.get("bights");//存放下拉框的name和value值
			cotransqtyinfoBizc.removeDlqx(transInfoId,list1);
			list.add("succ");
			return list;
		} catch (Exception e) {
			list.clear();
			list.add("erro");
			return list;
		}
	}
	@RequestMapping("/getQuName")
	public @ItemResponseBody List getQuXianName(@RequestParam String params) throws Exception {
		Map map = JsonUtils.getObjectMapper().readValue(params, Map.class);//解析参数
		String transInfoId = map.get("transInfoId")==null?"":map.get("transInfoId").toString();//获取市场成员id
		List list =cotransqtyinfoBizc.getDlqxName(transInfoId);
		return list;
	}
	
	@RequestMapping(value = "/queryLeftChartData",method = RequestMethod.GET)
	public @ItemResponseBody Object getLeftChartData(@RequestParam String params,HttpServletRequest request) throws Exception{		
		Map map = JsonUtils.getObjectMapper().readValue(params, Map.class);
		return cotransqtyinfoBizc.getPie3DXml(map);
	}
	/**
	 * 注入逻辑构件
	 * @param cotransqtyinfoBizc
	 */
	public void setCotransqtyinfoBizc(ICotransqtyinfoBizc cotransqtyinfoBizc) {
		this.cotransqtyinfoBizc = cotransqtyinfoBizc;
	}
}
