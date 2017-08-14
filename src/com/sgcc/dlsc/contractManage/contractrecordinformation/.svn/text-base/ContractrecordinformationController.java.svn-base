package com.sgcc.dlsc.contractManage.contractrecordinformation;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.hibernate.validator.util.privilegedactions.NewInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
import com.sgcc.uap.rest.utils.JsonUtils;
import com.sgcc.uap.rest.utils.ViewAttributeUtils;

import com.sgcc.dlsc.contractManage.coContractbackupInfo.bizc.ICoContractbackupInfoBizc;
import com.sgcc.dlsc.contractManage.coContractbackupInfo.vo.CoContractbackupinfoVO;
import com.sgcc.dlsc.contractManage.contractrecordinformation.bizc.IContractrecordinformationBizc;
import com.sgcc.dlsc.contractManage.contractrecordinformation.vo.ContractRecordInformationTransfer;
import com.sgcc.dlsc.contractManage.contractrecordinformation.vo.ContractRecordInformationVO;
import com.sgcc.dlsc.entity.po.ContractRecordInformation;

@Controller
@RequestMapping("/tjfxmaplineid")
// 根据po类名生成
public class ContractrecordinformationController {

	@Autowired
	private IContractrecordinformationBizc contractrecordinformationBizc;
	private ICoContractbackupInfoBizc coContractbackupInfoBizc;
	private String setContractId = "";

	public String getSetContractId() {
		return setContractId;
	}

	public void setSetContractId(String setContractId) {
		this.setContractId = setContractId;
	}
	@RequestMapping("/setContractId")
	public void  setContractId(HttpServletRequest request,HttpServletResponse response)
	{
		String contractId = request.getParameter("contractId");
		setContractId = contractId;
	}
	/**
	 * 保存 请求体格式为:{"items":[{"id":"1","name":"1","sex":"1","age":"1"},{"id":"1",
	 * "name":"1","sex":"1","age":"1"}]}
	 * 
	 * @return voList
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ItemResponseBody
	List<ContractRecordInformationVO> save(@ItemsRequestBody List<Map> list) {
		try {
			List<ContractRecordInformationVO> voList = contractrecordinformationBizc
					.saveOrUpdate(list);
			return voList;
		} catch (Exception e) {
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
			contractrecordinformationBizc.remove(idObject);
			return null;
		} catch (Exception e) {
			throw new RestClientException("程序异常");
		}
	}

	/**
	 * 首页信息
	 * 
	 * @param params
	 *            将请求参数{"filter":"","orderStr":"age","columns":"id,name"}
	 *            封装为RequestCondition对象
	 * @return queryResult
	 */
	@RequestMapping("/")
	public @ItemResponseBody
	QueryResultObject query(
			@QueryRequestParam("params") RequestCondition params
			) {
		try {
			QueryResultObject queryResult = contractrecordinformationBizc
					.queryContractRecordInfo(params, setContractId);
			List<ContractRecordInformation> result = queryResult.getItems();

			List<ContractRecordInformationVO> voList = new ArrayList<ContractRecordInformationVO>();
			for (int i = 0; i < result.size(); i++) {
				ContractRecordInformation po = (ContractRecordInformation) result
						.get(i);
				ContractRecordInformationVO vo = ContractRecordInformationTransfer
						.toVO(po);
				voList.add(vo);
			}
			queryResult.setItems(voList);
			return queryResult;
		} catch (Exception e) {
			throw new RestClientException("程序异常");
		}
	}
	@RequestMapping("/teStrings")
	public @ItemResponseBody List teStrings(HttpServletRequest request , HttpServletResponse response) 
	{
		try {
			String contractId = request.getParameter("contractId");
			Map<String, List> contractObj = contractrecordinformationBizc.getContractSignInfo(contractId) ;
			List list = new ArrayList();
			list.add(contractObj.get("contract").get(0));
			for(int i = 0 ; i < contractObj.get("dict").size() ; i++)
			{
				list.add(contractObj.get("dict").get(i));
			}
			list.add(contractObj.get("contract").get(1));
			return list;
		} catch (Exception e) {
			throw new RestClientException("程序异常");
		}
	}
	@RequestMapping("/saveSignContract")
	public @ItemResponseBody void saveSignContract(@RequestParam String params,HttpServletRequest request,HttpServletResponse response) throws Exception 
	{
		try {
			Map map = JsonUtils.getObjectMapper().readValue(params, Map.class);
			String contractId = map.get("contractId") == null?"":map.get("contractId").toString();
			String signPer = map.get("signPer")==null?"":map.get("signPer").toString();
			String signDate = map.get("dateTime")==null?null:map.get("dateTime").toString();
			int signStatus = Integer.parseInt(map.get("status")==null?null:map.get("status").toString());
//			signPer = new String(signPer.getBytes("gbk"),"utf-8");
			contractrecordinformationBizc.saveSignContract(contractId, signPer, signDate, signStatus);
		} catch (Exception e) {
			throw new RestClientException("程序异常");
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
			return contractrecordinformationBizc.queryById(id);
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
		datas = ViewAttributeUtils.getViewAttributes(columns,
				ContractRecordInformationVO.class);
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
			@ColumnRequestParam("params") String[] columns,
			@PathVariable String iscID) {
		List<ViewAttributeData> datas = null;
		datas = ViewAttributeUtils.getViewAttributes(columns,
				ContractRecordInformationVO.class);
		ViewMetaData metaData = new ViewMetaData();
		metaData.setColumns(datas);
		IPermissionControl permControl = new PermissionControl();
		metaData = permControl.addPermInfoToMetaData(iscID, metaData);
		return WrappedResult.successWrapedResult(metaData);
	}

	/**
	 * 注入逻辑构件
	 * 
	 * @param contractrecordinformationBizc
	 */
	public void setContractrecordinformationBizc(
			IContractrecordinformationBizc contractrecordinformationBizc) {
		this.contractrecordinformationBizc = contractrecordinformationBizc;
	}
}
