package com.sgcc.dlsc.contractManage.cocontranslossinfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestClientException;

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
import com.sgcc.uap.rest.utils.RestUtils;
import com.sgcc.uap.rest.utils.ViewAttributeUtils;

import com.sgcc.dlsc.contractManage.CoContractbaseInfo.vo.CoContractbaseinfoVO;
import com.sgcc.dlsc.contractManage.cocontranslossinfo.bizc.ICoContranslossinfoBizc;
import com.sgcc.dlsc.contractManage.cocontranslossinfo.vo.CoContranslossinfoTransfer;
import com.sgcc.dlsc.contractManage.cocontranslossinfo.vo.CoContranslossinfoVO;
import com.sgcc.dlsc.entity.po.CoContranslossinfo;

/**
 * 处理对线损信息  {@link CoContranslossinfo} 的操作请求，包括分页查询、新增、删除、更新等功能
 * 
 * @author djdeng
 *
 */
@Controller
@RequestMapping("/cocontranslossinfo") //根据po类名生成
public class CoContranslossinfoController {
	
	/**
	 * 现存线损信息维护服务组件，通过IOC上下文注入
	 */
    @Autowired
    private ICoContranslossinfoBizc coContranslossinfoBizc;
	
	/**
	 * 保存线损信息
	 * 
	 * 请求体格式为:{"items":[{"id":"1","name":"1","sex":"1","age":"1"},{"id":"1","name":"1","sex":"1","age":"1"}]}
	 * 
	 * @return voList 保存后的线损信息
	 * 
	 * @exception RestClientException 在保存的过程中可能出现IO异常，网络异常，或者业务逻辑判断抛出的异常
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ItemResponseBody 
	List<CoContranslossinfoVO> save(@ItemsRequestBody List<Map> list){ 
		try{
    		List<CoContranslossinfoVO> voList = coContranslossinfoBizc.saveOrUpdate(list);
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
	 * 
	 * @exception RuntimeException 在删除过程中可能出现IO异常，网络异常，或者业务逻辑判断抛出的异常
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @VoidResponseBody
	Object delete(@IdRequestBody IDRequestObject idObject) {
		try {
			coContranslossinfoBizc.remove(idObject);
			return null;
		} catch (Exception e) {
			throw new RestClientException("程序异常");
		}
	}
	
	/**
	 * 根据合同Id分页查询关联的线损信息
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
			QueryResultObject queryResult = coContranslossinfoBizc.query(params);
			List<CoContranslossinfo> result = queryResult.getItems();
			List<CoContranslossinfoVO> voList = new ArrayList<CoContranslossinfoVO>();
			for(int i = 0;i < result.size();i++){
				CoContranslossinfo po = (CoContranslossinfo)result.get(i);
				CoContranslossinfoVO vo = CoContranslossinfoTransfer.toVO(po);
				voList.add(vo);
			}
			queryResult.setItems(voList);
			return queryResult;
		} catch (Exception e) {
			throw new RestClientException("程序异常");
		}
	}
	
	/**
	 * 查询单条记录
	 * 
	 * @param id 线损信息Id
	 *            url中传递的值
	 */
	@RequestMapping("/{id}")
	public @ItemResponseBody
	QueryResultObject query(@PathVariable String id, HttpServletRequest request) {
		try {
			return coContranslossinfoBizc.queryById(id);
		} catch (Exception e) {
			throw new RestClientException("程序异常");
		}
		
	}
	
	/**
	 * 根据合同Id查询合同的信息 {@link CoContractbaseinfoVO}，并返回给前台展示
	 * 
	 * @param contractid 合同Id
	 * @return 合同信息
	 */
	@RequestMapping("/contract/{contractid}")
	public @ItemResponseBody
	QueryResultObject queryContractById(@PathVariable String contractid) {
		try {
			return coContranslossinfoBizc.queryContractById(contractid);
		} catch (Exception e) {
			throw new RestClientException("程序异常");
		}
	}
	
	/**
	 * 根据连接线Id查询
	 * 
	 * @param linkid 连接线Id
	 * @return 返回一个字符串，格式是：“”
	 */
	@RequestMapping("/link/{linkid}")
	public @ItemResponseBody
	QueryResultObject qtyLink(@PathVariable String linkid) {
		try {
			String str = coContranslossinfoBizc.findByLinkId(linkid);
			return RestUtils.wrappQueryResult(str);
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
		datas = ViewAttributeUtils.getViewAttributes(columns, CoContranslossinfoVO.class);
		return datas;
	}

	/**
	 * 初始化字典
	 * 
	 * @return QueryResultObject
	 */
	@RequestMapping("/new")
	public @ItemResponseBody
	QueryResultObject initDictValues() {
		try {
			return coContranslossinfoBizc.initDict();
		} catch (Exception e) {
			throw new RestClientException("程序异常");
		}
	}
	
	/**
	 * 注入逻辑构件
	 * @param coContranslossinfoBizc
	 */
	public void setCoContranslossinfoBizc(ICoContranslossinfoBizc coContranslossinfoBizc) {
		this.coContranslossinfoBizc = coContranslossinfoBizc;
	}
}
