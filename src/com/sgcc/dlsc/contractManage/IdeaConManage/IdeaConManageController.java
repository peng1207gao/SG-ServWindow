package com.sgcc.dlsc.contractManage.IdeaConManage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Hibernate;
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
import com.sgcc.isc.service.adapter.utils.JsonUtil;
import com.sgcc.uap.integrate.isc.service.IPermissionControl;
import com.sgcc.uap.integrate.isc.service.PermissionControl;
import com.sgcc.uap.rest.annotation.ColumnRequestParam;
import com.sgcc.uap.rest.annotation.ColumnResponseBody;
import com.sgcc.uap.rest.annotation.IdRequestBody;
import com.sgcc.uap.rest.annotation.ItemRequestParam;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.ItemsRequestBody;
import com.sgcc.uap.rest.annotation.QueryRequestParam;
import com.sgcc.uap.rest.annotation.RawResponseBody;
import com.sgcc.uap.rest.annotation.TreeResponseBody;
import com.sgcc.uap.rest.annotation.VoidResponseBody;
import com.sgcc.uap.rest.annotation.attribute.ViewAttributeData;
import com.sgcc.uap.rest.support.IDRequestObject;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;
import com.sgcc.uap.rest.support.TreeNode;
import com.sgcc.uap.rest.support.ViewMetaData;
import com.sgcc.uap.rest.support.WrappedResult;
import com.sgcc.uap.rest.utils.JsonUtils;
import com.sgcc.uap.rest.utils.ViewAttributeUtils;


/***
 * 此Controller中部分方法与多级合同管理模块（multiLevelContractManage）相同，公用某些方法
 * @author DELL
 *
 */
@Controller
@RequestMapping("/cocontractbaseinfo") //根据po类名生成
public class IdeaConManageController {
	
    @Autowired
    private IIdeaConManageBizc ideaConManageBizc;
	
	/**
	 * 保存
	 * 请求体格式为:{"items":[{"id":"1","name":"1","sex":"1","age":"1"},{"id":"1","name":"1","sex":"1","age":"1"}]}
	 * @return voList
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ItemResponseBody 
	List<CoContractbaseinfoVO> save(@ItemsRequestBody List<Map> list){ 
		try{
    		List<CoContractbaseinfoVO> voList = ideaConManageBizc.saveOrUpdate(list);
    		return voList;
		}catch(Exception e){
			throw new RestClientException("保存方法异常");
		}
	}
	

	
	
	
	/**
	 * 数据库取下拉列表，合同周期下拉菜单
	 * @description 方法描述
	 * @return 
	 * @author juguanghui
	 * @date 2014-07-15 
	 */
	@RequestMapping(value="/getContractTree2", method = RequestMethod.GET) 
	public @TreeResponseBody List<TreeNode> getContractTree2(@RequestParam String params) {
		Map map = null;
		try {
			map = JsonUtils.getObjectMapper().readValue(params, Map.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(map != null){
			String treeid = map.get("contractid")==null?"":map.get("contractid").toString();
			String sceneType = map.get("sceneType")==null?"":map.get("sceneType").toString();//标识功能模块类型，意向性协议（ideaContract）或者 多级合同管理（multiLevel）
			
			return ideaConManageBizc.getContractTreeRoot(treeid,sceneType);
		}
		return null;
	}
	
	/**
	 * 获取指定id的下级节点
	 * 
	 * @param id
	 *          
	 * @return nodelist
	 */
	@RequestMapping(value = "/getContractTree2/{contractid}", method = RequestMethod.GET)
	public @TreeResponseBody
	List<TreeNode> getNodes(@PathVariable String contractid,
			@ItemRequestParam("params") String itemType) {
		List<TreeNode> nodelist = new ArrayList<TreeNode>();
	    if ("ideaContract".equals(itemType)) {
			nodelist.addAll(ideaConManageBizc.getNodesList(contractid, itemType));
		} else if("multiLevel".equals(itemType)) {
			nodelist.addAll(ideaConManageBizc.getNodesList(contractid, itemType));
		}
		return nodelist;
	}
	
	
	/**
	 * 数据库取下拉列表，合同周期下拉菜单
	 * @description 方法描述
	 * @return 
	 * @author juguanghui
	 * @date 2014-1-22 
	 */
	@RequestMapping(value="/getContractTree", method = RequestMethod.GET) 
	public @TreeResponseBody List<TreeNode> getContractTreeData(@RequestParam String params) {
		Map map = null;
		try {
			map = JsonUtils.getObjectMapper().readValue(params, Map.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(map != null){
			String treeid = map.get("contractid")==null?"":map.get("contractid").toString();
			String sceneType = map.get("sceneType")==null?"":map.get("sceneType").toString();//标识功能模块类型，意向性协议（ideaContract）或者 多级合同管理（multiLevel）
			
			List dataList;
			try {
				dataList = ideaConManageBizc.getContractTree("", treeid,
						sceneType);
			} catch (Exception e) {
				throw new RestClientException("程序异常！");
			}
			int listSize = dataList.size();
			List<TreeNode> childnodeList = new ArrayList();
			TreeNode rootNode = new TreeNode();
			for(int i=0; i<listSize; i++){
				Object[] dataArr = (Object[]) dataList.get(i);
				String contractid = dataArr[0].toString();	//组织机构CODE
				String contractname = dataArr[1].toString();	//组织机构名称
				String childNum = dataArr[2].toString();	//下级组织机构个数
				
				if("0".equals(childNum)){
					rootNode.setId(contractid);
					rootNode.setHasChildren(false);
					rootNode.setText(contractname);
				} else {
					rootNode.setId(contractid);
					rootNode.setHasChildren(true);
					rootNode.setText(contractname);
					
					//加载子节点
					List childList;
					try {
						childList = ideaConManageBizc.getContractTree(
								contractid, treeid, sceneType);
					} catch (Exception e) {
						throw new RestClientException("程序异常！");
					}
					for(int j = 0; j < childList.size(); j++){
						Object[] childDataArr = (Object[]) childList.get(j);
						String childContractid = childDataArr[0].toString();	//组织机构CODE
						String childContractname = childDataArr[1].toString();	//组织机构名称
						TreeNode childNode = new TreeNode();
						childNode.setId(childContractid);
						childNode.setText(childContractname);
						childNode.setHasChildren(false);
						childnodeList.add(childNode);
					}
					rootNode.setChildNodes(childnodeList);
				}
			}
			
			ArrayList<TreeNode> nodeList = new ArrayList<TreeNode>();
			nodeList.add(rootNode);
			return nodeList;
		}
		return null;
	}
	
	/**
	 * 数据库取下拉列表，合同周期下拉菜单
	 * @description 方法描述
	 * @return
	 * @author juguanghui
	 * @date 2014-1-22 
	 */
	@RawResponseBody
	@RequestMapping(value = "/getContractCycDropEditor", method = RequestMethod.GET)
	public Object getContractCycDropEditor() {
		List<Map<String, String>> namelist = new ArrayList<Map<String, String>>();
		List list;//取数据库数据
		try {
			list = ideaConManageBizc.getDictionaryByType("23");
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
		for(int i = 0;i<list.size();i++){
			Map<String, String> map = new HashMap<String, String>();
			Object[] obj =(Object[])list.get(i);
			map.put("name", obj[1]==null?"":obj[1].toString());//封装下拉列表数据
			map.put("value", obj[0]==null?"":obj[0].toString());
			namelist.add(map);
		}
		return WrappedResult.successWrapedResult(namelist);
	}
	
	/**
	 * 数据库取下拉列表，合同状态下拉菜单
	 * @description 方法描述
	 * @return
	 * @author juguanghui
	 * @date 2014-1-22 
	 */
	@RawResponseBody
	@RequestMapping(value = "/getContractStateDropEditor", method = RequestMethod.GET)
	public Object getContractStateDropEditor() {
		List<Map<String, String>> namelist = new ArrayList<Map<String, String>>();
		List list;//取数据库数据
		try {
			list = ideaConManageBizc.getDictionaryByType("64");
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
		for(int i = 0;i<list.size();i++){
			Map<String, String> map = new HashMap<String, String>();
			Object[] obj =(Object[])list.get(i);
			map.put("name", obj[1]==null?"":obj[1].toString());//封装下拉列表数据
			map.put("value", obj[0]==null?"":obj[0].toString());
			namelist.add(map);
		}
		return WrappedResult.successWrapedResult(namelist);
	}
	
	/**
	 * 数据库取下拉列表，购电方下拉菜单
	 * @description 方法描述
	 * @return
	 * @author juguanghui
	 * @date 2014-1-22 
	 */
//	@RawResponseBody
//	@RequestMapping(value = "/getPurchaserDropEditor", method = RequestMethod.GET)
//	public Object getPurchaserDropEditor() {
//		List<Map<String, String>> namelist = new ArrayList<Map<String, String>>();
//		List list = ideaConManageBizc.qryBuyerList();//取数据库数据
//		for(int i = 0;i<list.size();i++){
//			Map<String, String> map = new HashMap<String, String>();
//			Object[] obj =(Object[])list.get(i);
//			map.put("name", obj[1]==null?"":obj[1].toString());//封装下拉列表数据
//			map.put("value", obj[0]==null?"":obj[0].toString());
//			namelist.add(map);
//		}
//		return WrappedResult.successWrapedResult(namelist);
//	}
	
	@RequestMapping("/getPurchaserDropEditor")
	public@ResponseBody Object getPurchaserDropEditor(@RequestParam("term") String term, @RequestParam("limit") int limit) {
		List<Map<String, String>> namelist = new ArrayList<Map<String, String>>();
		List list;//取数据库数据
		try {
			list = ideaConManageBizc.qryBuyerList(term);
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
		for(int i = 0;i<list.size();i++){
			Map<String, String> map = new HashMap<String, String>();
			Object[] obj =(Object[])list.get(i);
			map.put("name", obj[1]==null?"":obj[1].toString());//封装下拉列表数据
			map.put("value", obj[0]==null?"":obj[0].toString());
			namelist.add(map);
		}
		return WrappedResult.successWrapedResult(namelist);
	}
	
	/**
	 * 修改意向性合同关联权限
	 * 
	 * @param list
	 */
	@RequestMapping(value = "/cancleMultiLevelRelation", method = RequestMethod.GET)
	public @ItemResponseBody QueryResultObject cancleMultiLevelRelation(@RequestParam String params){
		Map map = null;
		try {
			map = JsonUtils.getObjectMapper().readValue(params, Map.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(map != null){
			String contractid = map.get("contractid")==null?"":map.get("contractid").toString();
			String isRootNode = map.get("isRootNode")==null?"":map.get("isRootNode").toString();
			
			try{
				QueryResultObject vo = ideaConManageBizc.cancleMultiLevelRelation(contractid, isRootNode);
	    		return vo;
			}catch(Exception e){
				throw new RestClientException("保存方法异常");
			}
		}
		return null;
	}
	
	
	/**
	 * 修改多级合同关联权限
	 * 
	 * @param list
	 */
	@RequestMapping(value = "/cancleIdeaRelation", method = RequestMethod.GET)
	public @ItemResponseBody QueryResultObject cancleIdeaRelation(@RequestParam String params, HttpServletRequest request){
		Map map = null;
		try {
			map = JsonUtils.getObjectMapper().readValue(params, Map.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(map != null){
			String contractid = map.get("contractid")==null?"":map.get("contractid").toString();
			String isRootNode = map.get("isRootNode")==null?"":map.get("isRootNode").toString();
			
			try{
				QueryResultObject vo = ideaConManageBizc.cancleIdeaRelation(contractid, isRootNode, request);
	    		return vo;
			}catch(Exception e){
				throw new RestClientException("保存方法异常");
			}
		}
		return null;
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
			String exetype = map.get("exetype")==null?"":map.get("exetype").toString();
			
			try{
				QueryResultObject vo = ideaConManageBizc.updateContractInfo(contractid, exetype, request);
	    		return vo;
			}catch(Exception e){
				throw new RestClientException("保存方法异常");
			}
		}
		return null;
	}
	/**
	 * 数据库取下拉列表，购电方下拉菜单
	 * @description 方法描述
	 * @return
	 * @author juguanghui
	 * @date 2014-1-23 
	 */
	@RawResponseBody
	@RequestMapping(value = "/getSellerDropEditor", method = RequestMethod.GET)
	public Object getSellerDropEditor() {
		List<Map<String, String>> namelist = new ArrayList<Map<String, String>>();
		List list;//取数据库数据
		try {
			list = ideaConManageBizc.qrySellerList();
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
		for(int i = 0;i<list.size();i++){
			Map<String, String> map = new HashMap<String, String>();
			Object[] obj =(Object[])list.get(i);
			map.put("name", obj[1]==null?"":obj[1].toString());//封装下拉列表数据
			map.put("value", obj[0]==null?"":obj[0].toString());
			namelist.add(map);
		}
		return WrappedResult.successWrapedResult(namelist);
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
			ideaConManageBizc.remove(idObject);
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
		return null;
	}
	
	/**
	 * 查询
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
		try {
			QueryResultObject queryResult = ideaConManageBizc.query(params, loginMarketId);
			return queryResult;
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
	}
	
	
	/**
	 * 查询单条合同信息
	 * @param id
	 *            url中传递的值
	 */
	@RequestMapping("/detailContractInfo")
	public @ItemResponseBody
	QueryResultObject getDetailContractInfo(@PathVariable String id) {
		try {
			return ideaConManageBizc.getDetailContractInfo(id);
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
			return ideaConManageBizc.queryById(id);
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
	 * @param ideaConManageBizc
	 */
	public void setIdeaConManageBizc(IIdeaConManageBizc ideaConManageBizc) {
		this.ideaConManageBizc = ideaConManageBizc;
	}
	
	/**
	 * 获取当前登录用户的marketid
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getUserInfo")
   	public @ItemResponseBody List<Map<String,String>> queryInit(HttpServletRequest request) throws Exception {
       	String marketid;//从数据库中获取当前登录用户
		try {
			marketid = UserInfoUtil.getLoginUserMarket(request);
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
       	
       	List<Map<String,String>> list = new ArrayList<Map<String,String>>();
       	
       	Map<String,String> map = new HashMap<String,String>();
       	map.put("marketid", marketid);
       	list.add(map);
      	return list;
    }
	
	
	/**
	 * 查询
	 * @param params
	 *            将请求参数{"filter":"","orderStr":"age","columns":"id,name"}
	 *            封装为RequestCondition对象
	 * @return queryResult
	 * @throws Exception 
	 */
	@RequestMapping("/relationContract")
	public @ItemResponseBody
	QueryResultObject relationContract(@QueryRequestParam("params") RequestCondition params) throws Exception {
		try {
			Map<String, String> map = JsonUtil.jsonToObject(params.getFilter()
					.toString(), Map.class);//获取前台filter中的contractid
			String contractid = map.get("contractid");
			if (contractid != null && contractid.length() != 0) {
				QueryResultObject queryResult = ideaConManageBizc
						.queryById(contractid);
				return queryResult;
			} else {
				return null;
			}
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
	}
	
	@RequestMapping(value = "/setUp", method = RequestMethod.GET)
	public @RawResponseBody Object setUp(@RequestParam String params, HttpServletRequest request){
		String result;
		try {
			result = ideaConManageBizc.setUp(params, request);
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
		return WrappedResult.successWrapedResult(result);
	}
	
	@RequestMapping(value = "/setDown", method = RequestMethod.GET)
	public @RawResponseBody Object setDown(@RequestParam String params, HttpServletRequest request){
		String result;
		try {
			result = ideaConManageBizc.setDown(params, request);
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
		return WrappedResult.successWrapedResult(result);
	}
	
}
