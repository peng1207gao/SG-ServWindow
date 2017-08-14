package com.sgcc.dlsc.paramConfig.cocontracsequence;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;

import com.sgcc.dlsc.commons.util.UserInfoUtil;
import com.sgcc.dlsc.contractManage.CoContransEnergyInfoV2.vo.CoContransenergyinfoVO;
import com.sgcc.dlsc.contractManage.cocontractaccessory.vo.CoContractaccessoryTransfer;
import com.sgcc.dlsc.contractManage.cocontractaccessory.vo.CoContractaccessoryVO;
import com.sgcc.dlsc.entity.po.CoContractaccessory;
import com.sgcc.dlsc.entity.po.CoContracttypeinfo;
import com.sgcc.dlsc.entity.po.CoContransenergyinfo;
import com.sgcc.dlsc.paramConfig.cocontracsequence.bizc.ICoContractsequenceBizc;
import com.sgcc.dlsc.paramConfig.cocontracsequence.vo.CoContractSequenceinfoVO;
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
import com.sgcc.uap.rest.support.DicItems;
import com.sgcc.uap.rest.support.IDRequestObject;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;
import com.sgcc.uap.rest.support.TreeNode;
import com.sgcc.uap.rest.support.WrappedResult;
import com.sgcc.uap.rest.utils.RestUtils;
import com.sgcc.uap.rest.utils.ViewAttributeUtils;

/**
 * 
 * @author djdeng
 *
 */
@Controller
@RequestMapping("/cocontractsequence") 
public class CoContractsequenceController {

	@Autowired
	private ICoContractsequenceBizc coContractsequenceBizc;
	
	
	/**
	 * 查询
	 * 
	 * @param params
	 *            将请求参数{"filter":"","orderStr":"age","columns":"id,name"}
	 *            封装为RequestCondition对象
	 * @return queryResult
	 * @throws ParseException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping("/")
	public @ItemResponseBody
	QueryResultObject query(@QueryRequestParam("params") RequestCondition params) throws IllegalAccessException, InvocationTargetException, ParseException {
		QueryResultObject queryResult;
		try {
			queryResult = coContractsequenceBizc.query(params);
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
		List<CoContractaccessory> result = queryResult.getItems();
		
//		List<CoContractaccessoryVO> voList = new ArrayList<CoContractaccessoryVO>();
//		for(int i = 0;i < result.size();i++){
//			if(result.get(i)!=null){
//				CoContractaccessory po = (CoContractaccessory)result.get(i);
//				CoContractaccessoryVO vo = CoContractaccessoryTransfer.toVO(po);
//				voList.add(vo);
//			}
//		}
//		queryResult.setItems(voList);
		return queryResult;
	}
	
	@RequestMapping("/meta")
	public @ColumnResponseBody
	List<ViewAttributeData> getMetaData(
			@ColumnRequestParam("params") String[] columns) {
		List<ViewAttributeData> datas = null;
		datas = ViewAttributeUtils.getViewAttributes(columns, CoContractSequenceinfoVO.class);
		return datas;
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
		String name=UserInfoUtil.getLoginUser(request).getName();
		QueryResultObject result;
		try {
			result = coContractsequenceBizc.queryById(id, name);
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
		return result;
	}
	
	/**
	 * 删除操作
	 * 
	 * @param list包含map对象
	 *            ，封装ids主键值数组和idName主键名称
	 * @return null
	 */
	@RequestMapping("/delete")
	public @ItemResponseBody QueryResultObject delete(@RequestParam("ids") String ids) {//@VoidResponseBody Object
		try {
			String[] strs = ids.split(",");
			QueryResultObject result = new QueryResultObject();// 新建返回值
			if (strs != null && strs.length != 0) {
				String str = coContractsequenceBizc.remove(strs);
				List<String> list = new ArrayList<String>();
				list.add(str);
				result.setItems(list);
			}
			return result;
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
	}
	
	/**
	 * 保存
	 * @return voList
	 * @throws Exception 
	 * @throws RuntimeException 
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ItemResponseBody 
	Object save(@ItemsRequestBody List<Map> list,HttpServletRequest request) throws Exception{ 
		CoContractSequenceinfoVO vo=new CoContractSequenceinfoVO();
		for(int i=0;i<list.size();i++){
			Map params=list.get(i);
			vo.setSequenceid(params.get("mxVirtualId")==null?params.get("sequenceid").toString():params.get("mxVirtualId").toString());
			vo.setTenId(params.get("mxVirtualId")!=null?params.get("mxVirtualId").toString():null);//tenId有值说明是 新增 没值说明之 更新
			vo.setSequencecircleStr(params.get("sequencecircleStr")!=null?params.get("sequencecircleStr").toString():null);
			vo.setSequencename(params.get("sequencename")!=null?params.get("sequencename").toString():null);
			vo.setSequencetypeStr(params.get("sequencetypeStr").toString());
			vo.setContractType(params.get("contractType")!=null?params.get("contractType").toString():null);
		}
		String marketId = UserInfoUtil.getLoginUserMarket(request);
		vo.setUpdatepersonid(UserInfoUtil.getLoginUser(request).getUserName());
		vo.setMarketid(marketId);
		try {
			coContractsequenceBizc.saveOrUpdate(vo);
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
		return null;
	}
	
	/**
	 * 获取根节点
	 * 
	 * @return nodelist
	 */
	@RequestMapping(value = "/getTree", method = RequestMethod.GET)
	public @TreeResponseBody
	List<TreeNode> getRoot(HttpServletRequest request) {
		String marketId = request.getParameter("marketId");
		if(!"91812".equals(marketId)){
			try {
				marketId = UserInfoUtil.getLoginUserMarket(request);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String marid = marketId;
		List<CoContracttypeinfo> list;
		try {
			list = coContractsequenceBizc.listRoot(CoContracttypeinfo.class,
					"supertypeid", marketId);
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
		List<TreeNode> nodelist = new ArrayList<TreeNode>();
		for (int i = 0; i < list.size(); i++) {
			TreeNode node = new TreeNode();
			CoContracttypeinfo cocontracttypeinfo = (CoContracttypeinfo) list.get(i);
			node.setId(cocontracttypeinfo.getContracttypeid()); //主键的get方法
//			boolean hasChild = coContractsequenceBizc.hasChild( cocontracttypeinfo.getContracttypeid(), CoContracttypeinfo.class,"supertypeid",marketId);
			node.setText(cocontracttypeinfo.getTypename()==null?"":cocontracttypeinfo.getTypename());//显示字段的get方法
			node.setItemType("cocontracttypeinfo"); //根节点的itemType
			List<TreeNode> getNodes=getNodes(node.getId(),node.getItemType(),marketId);
			if(getNodes.size()==0){
				node.setHasChildren(false);
			}else{
				node.setHasChildren(true);
				node.setChildNodes(getNodes);
			}
			nodelist.add(node);
			getNodes(node.getId(),node.getItemType(),marketId);
		}
		return nodelist;
	}
	
	List<TreeNode> getNodes( String id, String itemType,String marketId) {
		List<TreeNode> nodelist = new ArrayList<TreeNode>();
		List<String> itemTypeList = this.getItemTypes(itemType); 
		for (int i = 0; i < itemTypeList.size(); i++) {
			String itemtype = (String) itemTypeList.get(i);
		    if ("cocontracttypeinfo".equals(itemtype)) {
				nodelist.addAll(this.getCoContracttypeinfoList(id, itemtype,marketId));
				for(int num=0;num<nodelist.size();num++){
					List<TreeNode> tenNodelist=getNodes(nodelist.get(num).getId(),nodelist.get(num).getItemType(),marketId);
					if(tenNodelist.size()==0){
						nodelist.get(num).setHasChildren(false);
					}else{
						nodelist.get(num).setHasChildren(true);
						nodelist.get(num).setChildNodes(tenNodelist);
					}
				}
			}	
		}
		return nodelist;
	}
	
	private List<TreeNode> getCoContracttypeinfoList(String id, String itemType,String marketId) {
		try {
			List<TreeNode> nodelist = new ArrayList<TreeNode>();
			// CoContracttypeinfo中取出下级节点
			List<CoContracttypeinfo> cocontracttypeinfoList = coContractsequenceBizc
					.listNode(CoContracttypeinfo.class, id, "supertypeid",
							marketId);
			for (int i = 0; i < cocontracttypeinfoList.size(); i++) {
				TreeNode treeNode = new TreeNode();
				CoContracttypeinfo cocontracttypeinfo = (CoContracttypeinfo) cocontracttypeinfoList
						.get(i);
				treeNode.setId(cocontracttypeinfo.getContracttypeid());
				boolean hasChild = coContractsequenceBizc.hasChild(
						cocontracttypeinfo.getContracttypeid(),
						CoContracttypeinfo.class, "supertypeid", marketId);
				treeNode.setHasChildren(hasChild);
				treeNode.setText(cocontracttypeinfo.getTypename()); //显示字段
				treeNode.setItemType(itemType);
				nodelist.add(treeNode);
			}
			return nodelist;
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
	}
	public void setCoContractsequenceBizc(
			ICoContractsequenceBizc coContractsequenceBizc) {
		this.coContractsequenceBizc = coContractsequenceBizc;
	}
	/*
	 * 获取itemType集合
	 */
	private List<String> getItemTypes(String itemtype) {
		List<String> list = new ArrayList<String>();
		if ("cocontracttypeinfo".equals(itemtype)) {
			list.add("cocontracttypeinfo");
		}
	    return list;
	}
	
	/**
	 * 合同序列 查询内的  合同序列类型  合同序列日期 初始化
	 * @param type
	 * @return
	 * @author 
	 * 
	 */
	@RequestMapping(value = "/gencode/{type}", method = RequestMethod.GET)
	public @ItemResponseBody 
	Object getGenType(@PathVariable(value="type") String type){ 
		DicItems dicItems;
		try {
			dicItems = coContractsequenceBizc.getGencodeByType(type);
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
		return dicItems.getValues();
	}
}
