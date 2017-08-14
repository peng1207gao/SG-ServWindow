package com.sgcc.dlsc.contractManage.contractaffixinfo;
import java.util.ArrayList;
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

import com.sgcc.dlsc.commons.util.DateUtil;
import com.sgcc.dlsc.commons.util.UserInfoUtil;
import com.sgcc.dlsc.contractManage.contractaffixinfo.bizc.IContractaffixinfoBizc;
import com.sgcc.dlsc.contractManage.contractaffixinfo.vo.CoContractaffixinfoVO;
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
@RequestMapping("/cocontractaffixinfo_mk") //根据po类名生成
public class ContractaffixinfoController {
	
    @Autowired
    private IContractaffixinfoBizc contractaffixinfoBizc;
	
	/**
	 * 保存
	 * 请求体格式为:{"items":[{"id":"1","name":"1","sex":"1","age":"1"},{"id":"1","name":"1","sex":"1","age":"1"}]}
	 * @return voList
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ItemResponseBody 
	List<CoContractaffixinfoVO> save(@ItemsRequestBody List<Map> list){ 
		try{
    		List<CoContractaffixinfoVO> voList = contractaffixinfoBizc.saveOrUpdate(list);
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
			contractaffixinfoBizc.remove(idObject);
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
			QueryResultObject queryResult = contractaffixinfoBizc.query(params);//进入bizc层查询结果
			List<CoContractaffixinfoVO> voList = new ArrayList<CoContractaffixinfoVO>();
			voList = queryResult.getItems();
			queryResult.setItems(voList);
			return queryResult;
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
	QueryResultObject query(@PathVariable String id,HttpServletRequest request) throws Exception {
		try {
			String uploadPerson = UserInfoUtil.getLoginUser(request).getName();//获取当前登录人名称
			String uploadTime = DateUtil.getNowDate("yyyy-MM-dd");//获取当前系统时间
			String marketId = UserInfoUtil.getLoginUserMarket(request);//获取当前登录场景id
			return contractaffixinfoBizc.queryById(id,uploadPerson,uploadTime,marketId);
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
		datas = ViewAttributeUtils.getViewAttributes(columns, CoContractaffixinfoVO.class);
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
		datas = ViewAttributeUtils.getViewAttributes(columns, CoContractaffixinfoVO.class);
		ViewMetaData metaData= new ViewMetaData();
		metaData.setColumns(datas);
		IPermissionControl permControl = new PermissionControl();
		metaData = permControl.addPermInfoToMetaData(iscID, metaData);
		return WrappedResult.successWrapedResult(metaData);
	}
	
	/**
	 * 
	 * @description 下载附件
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author mengke
	 * @date 2014-3-24
	 */
	@RequestMapping(value = "/down", method = RequestMethod.GET)
   	public @ItemResponseBody List<String>  downLoadUpFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String guid = request.getParameter("guid")==null?"":request.getParameter("guid").toString();
			String finStr = contractaffixinfoBizc.downAffix(guid,request,response);
	       	List<String> list = new ArrayList<String>();
	       	list.add(finStr);
	       	return list;
		} catch (Exception e) {
			throw new RestClientException("程序异常");
		}
	}
	
	/**
	 * 注入逻辑构件
	 * @param contractaffixinfoBizc
	 */
	public void setContractaffixinfoBizc(IContractaffixinfoBizc contractaffixinfoBizc) {
		this.contractaffixinfoBizc = contractaffixinfoBizc;
	}
}
