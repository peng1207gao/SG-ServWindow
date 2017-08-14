package com.sgcc.dlsc.contractManage.coContemplatewithparam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.sgcc.dlsc.contractManage.coContemplatewithparam.bizc.ICoContemplatewithparamBizc;
import com.sgcc.dlsc.entity.po.CoContemplatewithparam;
import com.sgcc.dlsc.contractManage.coContemplatewithparam.vo.CoContemplatewithparamTransfer;
import com.sgcc.dlsc.contractManage.coContemplatewithparam.vo.CoContemplatewithparamVO;

@Controller
@RequestMapping("/cocontemplatewithparam") //根据po类名生成
public class CoContemplatewithparamController {
	
    @Autowired
    private ICoContemplatewithparamBizc coContemplatewithparamBizc;
	
	/**
	 * 保存
	 * 请求体格式为:{"items":[{"id":"1","name":"1","sex":"1","age":"1"},{"id":"1","name":"1","sex":"1","age":"1"}]}
	 * @return voList
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ItemResponseBody 
	List<CoContemplatewithparamVO> save(@ItemsRequestBody List<Map> list){ 
		try{
    		List<CoContemplatewithparamVO> voList = coContemplatewithparamBizc.saveOrUpdate(list);
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
			coContemplatewithparamBizc.remove(idObject);
		} catch (Exception e) {
			throw new RestClientException("方法执行异常");
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
			queryResult = coContemplatewithparamBizc.query(params);
		} catch (Exception e) {
			throw new RestClientException("方法执行异常");
		}
		List<CoContemplatewithparam> result = queryResult.getItems();
		
		List<CoContemplatewithparamVO> voList = new ArrayList<CoContemplatewithparamVO>();
		for(int i = 0;i < result.size();i++){
			CoContemplatewithparam po = (CoContemplatewithparam)result.get(i);
			CoContemplatewithparamVO vo = CoContemplatewithparamTransfer.toVO(po);
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
			return coContemplatewithparamBizc.queryById(id);
		} catch (Exception e) {
			throw new RestClientException("方法执行异常");
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
		datas = ViewAttributeUtils.getViewAttributes(columns, CoContemplatewithparamVO.class);
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
		try {
			List<ViewAttributeData> datas = null;
			datas = ViewAttributeUtils.getViewAttributes(columns,
					CoContemplatewithparamVO.class);
			ViewMetaData metaData = new ViewMetaData();
			metaData.setColumns(datas);
			IPermissionControl permControl = new PermissionControl();
			metaData = permControl.addPermInfoToMetaData(iscID, metaData);
			return WrappedResult.successWrapedResult(metaData);
		} catch (Exception e) {
			throw new RestClientException("方法执行异常");
		}
	}
	/**
	 * 注入逻辑构件
	 * @param coContemplatewithparamBizc
	 */
	public void setCoContemplatewithparamBizc(ICoContemplatewithparamBizc coContemplatewithparamBizc) {
		this.coContemplatewithparamBizc = coContemplatewithparamBizc;
	}
	@RequestMapping(value = "/down")
   	public @ItemResponseBody List<String>  downLoadUpFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			List<String> list = new ArrayList<String>();
			String guid = request.getParameter("guid") == null ? "" : request
					.getParameter("guid").toString();
			String finStr = coContemplatewithparamBizc.downLoadFile(guid,
					request, response);
			list.add(finStr);
			return list;
		} catch (Exception e) {
			throw new RestClientException("方法执行异常");
		}
	}
}
