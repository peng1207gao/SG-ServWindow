package com.sgcc.dlsc.statesanalysis.kqksexetrace;
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
import com.sgcc.dlsc.statesanalysis.kqksexetrace.bizc.IKqksexetraceBizc;
import com.sgcc.dlsc.statesanalysis.kqksexetrace.vo.CoContractbaseinfoVO;
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

@Controller
@RequestMapping("/cocontractbaseinfo_mk") //根据po类名生成
public class KqksexetraceController {
	
    @Autowired
    private IKqksexetraceBizc kqksexetraceBizc;
	
	/**
	 * 保存
	 * 请求体格式为:{"items":[{"id":"1","name":"1","sex":"1","age":"1"},{"id":"1","name":"1","sex":"1","age":"1"}]}
	 * @return voList
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ItemResponseBody 
	List<CoContractbaseinfoVO> save(@ItemsRequestBody List<Map> list){ 
		try{
    		List<CoContractbaseinfoVO> voList = kqksexetraceBizc.saveOrUpdate(list);
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
		kqksexetraceBizc.remove(idObject);
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
	QueryResultObject query(@QueryRequestParam("params") RequestCondition params,HttpServletRequest request) {
		String marketId = "";
		try {
			marketId = UserInfoUtil.getLoginUserMarket(request);
		} catch (Exception e) {
			// TODO: handle exception
			throw new RestClientException("程序异常！");
		}
		QueryResultObject queryResult = kqksexetraceBizc.query(params,marketId);
		List<CoContractbaseinfoVO> voList = new ArrayList<CoContractbaseinfoVO>();
		voList = queryResult.getItems();
		queryResult.setItems(voList);
		return queryResult;
	}
	
	/**
	 * 
	 * @description 智能查询联络线名称
	 * @param term
	 * @param request
	 * @return
	 * @throws Exception
	 * @author mengke
	 * @date 2014-3-20
	 */
	@RequestMapping("/getLine")
	public @ResponseBody Object getLineName(@RequestParam("term") String term,
			HttpServletRequest request) throws Exception{
		try {
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			List<String> list1 = new ArrayList<String>();
			String marketId = UserInfoUtil.getLoginUserMarket(request);
			List result = kqksexetraceBizc
					.getLineName(term,marketId);
			if (result != null && result.size() > 0) {
				for (int i = 0; i < result.size(); i++) {
					Object obj = (Object) result.get(i);
					Map<String, String> map1 = new HashMap<String, String>();
					map1.put("text", obj == null ? "" : obj.toString());
					map1.put("value", obj == null ? "" : obj.toString());
					list.add(map1);
				}
			}
			return WrappedResult.successWrapedResult(list);
		} catch (Exception e) {
			throw new RestClientException("程序异常");
		}
	}
	
	/**
	 * 
	 * @description 获取图表数据
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception
	 * @author mengke
	 * @date 2014-4-29
	 */
	@RequestMapping(value = "/getDataChart",method = RequestMethod.GET)
	public @ItemResponseBody Object getDataChart(@RequestParam String params, HttpServletRequest request) throws Exception{
		String marketId;//从数据库中获取当前登录用户
		try {
			marketId = UserInfoUtil.getLoginUserMarket(request);
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
		String chartXml = kqksexetraceBizc.getDataChart(params, marketId);
		
		return chartXml;
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
		return kqksexetraceBizc.queryById(id);
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
	
	@RequestMapping("/seller/meta")
	public @ColumnResponseBody
	List<ViewAttributeData> getSellerMetaData(
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
	 * @param kqksexetraceBizc
	 */
	public void setKqksexetraceBizc(IKqksexetraceBizc kqksexetraceBizc) {
		this.kqksexetraceBizc = kqksexetraceBizc;
	}
}
