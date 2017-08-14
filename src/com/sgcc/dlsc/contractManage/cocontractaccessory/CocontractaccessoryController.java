package com.sgcc.dlsc.contractManage.cocontractaccessory;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
import com.sgcc.uap.rest.annotation.QueryRequestParam;
import com.sgcc.uap.rest.annotation.RawResponseBody;
import com.sgcc.uap.rest.annotation.VoidResponseBody;
import com.sgcc.uap.rest.annotation.attribute.ViewAttributeData;
import com.sgcc.uap.rest.support.IDRequestObject;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;
import com.sgcc.uap.rest.support.ViewMetaData;
import com.sgcc.uap.rest.support.WrappedResult;
import com.sgcc.uap.rest.utils.RestUtils;
import com.sgcc.uap.rest.utils.ViewAttributeUtils;

import com.sgcc.dlsc.contractManage.cocontractaccessory.bizc.ICocontractaccessoryBizc;
import com.sgcc.dlsc.contractManage.cocontractaccessory.vo.CoBaGeneratorVO;
import com.sgcc.dlsc.contractManage.cocontractaccessory.vo.CoContractaccessoryTransfer;
import com.sgcc.dlsc.contractManage.cocontractaccessory.vo.CoContractaccessoryVO;
import com.sgcc.dlsc.entity.po.CoContractaccessory;

/**
 * 处理对合同机组信息 {@link CoContractaccessory}操作请求
 * 
 * @author djdeng
 *
 */
@Controller
@RequestMapping("/cocontractaccessory")
public class CocontractaccessoryController {
	
    @Autowired
    private ICocontractaccessoryBizc cocontractaccessoryBizc;
    
	@RawResponseBody
    @RequestMapping(value = "/getDropDownList", method = RequestMethod.GET)
	public Object getDropDownList(@RequestParam String params){
    	return WrappedResult.successWrapedResult(cocontractaccessoryBizc.getDropDownList(params));
    }
    
	/**
	 * 保存
	 * @return voList
	 * @throws RuntimeException 
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ItemResponseBody 
	Object save(@RequestBody Map<String, String> params,HttpServletRequest request){ 
		List<CoContractaccessoryVO> vos = buildCoContractaccessoryVO(params);
		try {
			cocontractaccessoryBizc.saveOrUpdate(vos,request);
		} catch (Exception e) {
			throw new RestClientException("方法执行异常");
		}
		return null;
	}
	
	/**
	 * 解析前台传递的参数，转换成 {@link CoContractaccessoryVO} VO对象集合
	 * 
	 * 前台传递参数例子：
	 * 
	 * Map ["contractrole" : "1", contractid:"345670865FGHJK", "ids": "456789hghui,434589hghui"]
	 * 
	 * contractrole：合同角色（购电方 1，售电方 2）
	 * contractid：合同Id
	 * ids：机组主键ID集合，ID之间使用逗号隔开
	 * 
	 * @param params 前台传递参数
	 * @return VO对象集合
	 */
	private List<CoContractaccessoryVO> buildCoContractaccessoryVO(
			Map<String, String> params) {
		List<CoContractaccessoryVO> vos = new ArrayList<CoContractaccessoryVO>();
		String contractrole = params.get("contractrole");
		String contractid = params.get("contractid");
		String ids = params.get("ids");
		if (StringUtils.isNotBlank(ids)) {
			String[] arrs = ids.split(",");
			for (int i = 0 ; i < arrs.length ; i++) {
				String id = arrs[i];
				CoContractaccessoryVO vo = new CoContractaccessoryVO();
				vo.setContractid(contractid);
				vo.setContractrole(new BigDecimal(contractrole));
				vo.setParticipantid(id);
				vos.add(vo);
			}
		}
		return vos;
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
			cocontractaccessoryBizc.remove(idObject);
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
		try {
			QueryResultObject queryResult = cocontractaccessoryBizc
					.query(params);
			List<CoContractaccessory> result = queryResult.getItems();
			List<CoContractaccessoryVO> voList = new ArrayList<CoContractaccessoryVO>();
			for (int i = 0; i < result.size(); i++) {
				CoContractaccessory po = (CoContractaccessory) result.get(i);
				CoContractaccessoryVO vo = CoContractaccessoryTransfer.toVO(po);
				voList.add(vo);
			}
			queryResult.setItems(voList);
			return queryResult;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RestClientException("方法执行异常");
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
			return cocontractaccessoryBizc.queryById(id);
		} catch (Exception e) {
			throw new RestClientException("方法执行异常");
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
	@RequestMapping("/generator/")
	public @ItemResponseBody
	QueryResultObject queryQenerator(@QueryRequestParam("params") RequestCondition params) {
		List<CoBaGeneratorVO> queryResult = cocontractaccessoryBizc.getGenerator(params);
		try {
			return RestUtils.wrappQueryResult(queryResult, queryResult.size());
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
	@RequestMapping("/generator/meta")
	public @ColumnResponseBody
	List<ViewAttributeData> getMetaDataGenerator(
			@ColumnRequestParam("params") String[] columns) {
		List<ViewAttributeData> datas = null;
		datas = ViewAttributeUtils.getViewAttributes(columns, CoBaGeneratorVO.class);
		return datas;
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
		datas = ViewAttributeUtils.getViewAttributes(columns, CoContractaccessoryVO.class);
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
					CoContractaccessoryVO.class);
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
	 * 初始化字典
	 * 
	 * @return QueryResultObject
	 */
	@RequestMapping("/new")
	public @ItemResponseBody
	QueryResultObject initDictValues() {
		return cocontractaccessoryBizc.initDict();
	}
	/**
	 * 注入逻辑构件
	 * @param cocontractaccessoryBizc
	 */
	public void setCocontractaccessoryBizc(ICocontractaccessoryBizc cocontractaccessoryBizc) {
		this.cocontractaccessoryBizc = cocontractaccessoryBizc;
	}
}
