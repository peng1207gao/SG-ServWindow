package com.sgcc.dlsc.statesanalysis.jfcontractaffix;
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

import com.sgcc.dlsc.commons.util.UserInfoUtil;
import com.sgcc.dlsc.entity.po.SjjcJfDljyhtwb;
import com.sgcc.dlsc.statesanalysis.jfcontractaffix.bizc.IJfcontractaffixBizc;
import com.sgcc.dlsc.statesanalysis.jfcontractaffix.vo.SjjcJfDljyhtwbTransfer;
import com.sgcc.dlsc.statesanalysis.jfcontractaffix.vo.SjjcJfDljyhtwbVO;
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
@RequestMapping("/sjjcjfdljyhtwb") //根据po类名生成
public class JfcontractaffixController {
	
    @Autowired
    private IJfcontractaffixBizc jfcontractaffixBizc;
	
	/**
	 * 保存
	 * 请求体格式为:{"items":[{"id":"1","name":"1","sex":"1","age":"1"},{"id":"1","name":"1","sex":"1","age":"1"}]}
	 * @return voList
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ItemResponseBody 
	List<SjjcJfDljyhtwbVO> save(@ItemsRequestBody List<Map> list){ 
		try{
    		List<SjjcJfDljyhtwbVO> voList = jfcontractaffixBizc.saveOrUpdate(list);
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
		jfcontractaffixBizc.remove(idObject);
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
		QueryResultObject queryResult = jfcontractaffixBizc.query(params);
		List<SjjcJfDljyhtwbVO> voList = new ArrayList<SjjcJfDljyhtwbVO>();
		voList = queryResult.getItems();
		queryResult.setItems(voList);
		return queryResult;
	}
	
	/**
	 * 获取运行单位下拉框数据
	 * @description 方法描述
	 * @return
	 * @author mengke
	 * @date 2014-3-14
	 */
	@RequestMapping("/getWorkingUnit")
	public @ItemResponseBody List<Map<String,String>> getWorkingUnit(){
		List<Map<String,String>> mapList = new ArrayList<Map<String,String>>();
		List list;
		try {
			list = jfcontractaffixBizc.getWorkingUnit();
		} catch (Exception e) {
			throw new RestClientException("程序异常");
		}
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				Map<String,String> map = new HashMap<String,String>();
				Object[] obj = (Object[])list.get(i);
				map.put("value", obj[0]==null?"":obj[0].toString());
				map.put("name", obj[1]==null?"":obj[1].toString());
				mapList.add(map);
			}
		}
		return mapList;
	}
	
	/**
	 * 
	 * @description 业务场景下拉框默认为当前登录场景
	 * @param request
	 * @return
	 * @throws Exception
	 * @author mengke
	 * @date 2014-4-23
	 */
	@RequestMapping("/initMarket")
	public @RawResponseBody Object initMarket(HttpServletRequest request) throws Exception{
		Map<String,String> map;
		try {
			String marketId = UserInfoUtil.getLoginUserMarket(request);
			map = jfcontractaffixBizc.initMarket(marketId);
		} catch (Exception e) {
			throw new RestClientException("程序异常");
		}
		return WrappedResult.successWrapedResult(map);
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
		return jfcontractaffixBizc.queryById(id);
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
		datas = ViewAttributeUtils.getViewAttributes(columns, SjjcJfDljyhtwbVO.class);
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
		datas = ViewAttributeUtils.getViewAttributes(columns, SjjcJfDljyhtwbVO.class);
		ViewMetaData metaData= new ViewMetaData();
		metaData.setColumns(datas);
		IPermissionControl permControl = new PermissionControl();
		metaData = permControl.addPermInfoToMetaData(iscID, metaData);
		return WrappedResult.successWrapedResult(metaData);
	}
	/**
	 * 注入逻辑构件
	 * @param jfcontractaffixBizc
	 */
	public void setJfcontractaffixBizc(IJfcontractaffixBizc jfcontractaffixBizc) {
		this.jfcontractaffixBizc = jfcontractaffixBizc;
	}
}
