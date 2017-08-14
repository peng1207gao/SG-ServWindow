package com.sgcc.dlsc.contractManage.contractAddRegister;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.dialect.H2Dialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;

import com.sgcc.dlsc.commons.util.DateUtil;
import com.sgcc.dlsc.commons.util.UserInfoUtil;
import com.sgcc.dlsc.contractManage.coContractbaseinfoV2Act.vo.CoContractbaseinfoVO;
import com.sgcc.dlsc.contractManage.contractAddRegister.bizc.IContractAddRegisterBizc;
import com.sgcc.uap.integrate.isc.service.IPermissionControl;
import com.sgcc.uap.integrate.isc.service.PermissionControl;
import com.sgcc.uap.rest.annotation.ColumnRequestParam;
import com.sgcc.uap.rest.annotation.ColumnResponseBody;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.ItemsRequestBody;
import com.sgcc.uap.rest.annotation.RawResponseBody;
import com.sgcc.uap.rest.annotation.attribute.ViewAttributeData;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.ViewMetaData;
import com.sgcc.uap.rest.support.WrappedResult;
import com.sgcc.uap.rest.utils.JsonUtils;
import com.sgcc.uap.rest.utils.ViewAttributeUtils;
/**
 * 新增编辑明细合同界面
 * @title ContractAddRegisterController
 * @description 描述
 * @author liling
 */
@Controller
@RequestMapping("/contractAddRegister") //
public class ContractAddRegisterController {
	
    @Autowired
    private IContractAddRegisterBizc contractAddRegisterBizc;
	
	/** 
	 * 保存按钮
	 * 请求体格式为:{"items":[{"id":"1","name":"1","sex":"1","age":"1"},{"id":"1","name":"1","sex":"1","age":"1"}]}
	 * @return voList
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ItemResponseBody 
	List<CoContractbaseinfoVO> save(@ItemsRequestBody List<Map> list,HttpServletRequest request){ 
		try{
    		List<CoContractbaseinfoVO> voList = contractAddRegisterBizc.saveOrUpdate(list,request);
    		return voList;
		}catch(Exception e){
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
			QueryResultObject qo = contractAddRegisterBizc.queryById(id);
			List list = qo.getDicItems();
			return qo;
		} catch (Exception e) {
			throw new RestClientException("程序异常");
		}
	}
	
	/**
	 * 
	 * @description 获取批复电价
	 * @param params
	 * @return
	 * @author mengke
	 * @date 2014-6-5
	 */
	@RequestMapping("/getPfdj")
	public @RawResponseBody
	Object getPfdj(@RequestParam String params){
		String pfdj = null;//批复电价
		String pfdjxx = null;//批复电价选项
		List<Map<String,String>> list2 = new ArrayList<Map<String,String>>();
		List list = contractAddRegisterBizc.getPfdjList(params);
		if(list!=null && list.size()==1){
			Object[] objs = (Object[])list.get(0);
			pfdj = objs[2]==null?"":objs[2].toString();
			Map map1 = new HashMap();//存放结果
			map1.put("value", pfdj);
			map1.put("name", pfdj);
			list2.add(map1);
		}else if(list!=null && list.size()>1){
			for(int i=0;i<list.size();i++){
				Object[] objs = (Object[])list.get(i);
				pfdj = objs[2]==null?"":objs[2].toString();
				pfdjxx = objs[3]==null?"":objs[3].toString();
				Map map1 = new HashMap();//存放结果
				map1.put("value", pfdj);
				map1.put("name", pfdjxx);
				list2.add(map1);
			}
		}
		return WrappedResult.successWrapedResult(list2);
	}
	
	/**
	 * 
	 * @description 获取两个日期之间的天数
	 * @param params
	 * @return
	 * @author mengke
	 * @date 2014-7-10
	 */
	@RequestMapping("/getDays")
	public @RawResponseBody Object getDays(@RequestParam String params){
		Map map = null;//存放参数
		String startDate = null;
		String endDate = null;
		try {
			map = JsonUtils.getObjectMapper().readValue(params, Map.class);
		} catch (Exception e) {
			throw new RestClientException("程序异常");
		}
		if(map != null){
			startDate = map.get("startDate")==null?"":map.get("startDate").toString();
			endDate = map.get("endDate")==null?"":map.get("endDate").toString();
		}
		int days = DateUtil.getDays(DateUtil.getUtilDate(startDate, "yyyy-MM-dd"), DateUtil.getUtilDate(endDate, "yyyy-MM-dd"));
		return days;
	}
	
	/**
	 * 
	 * @description 获取厂用电率
	 * @param params
	 * @param request
	 * @return
	 * @author mengke
	 * @date 2014-7-16
	 */
	@RequestMapping("/getCydl")
	public @RawResponseBody Object getCydl(@RequestParam String params,HttpServletRequest request){
		Map map = null;//存放参数
		String participantid = "";//市场成员id
		String marketId = "";
		String cydl = "0";//厂用电率
		try {
			map = JsonUtils.getObjectMapper().readValue(params, Map.class);
		} catch (Exception e) {
			throw new RestClientException("程序异常");
		}
		if(map != null){
			participantid = map.get("participantid")==null?"":map.get("participantid").toString();
		}
		try {
			marketId = UserInfoUtil.getLoginUserMarket(request);
		} catch (Exception e) {
			throw new RestClientException("程序异常");
		}
		List list = contractAddRegisterBizc.getCydl(participantid,marketId);
		if(list!=null && list.size()>0){
			cydl = list.get(0)==null?"0":list.get(0).toString();//厂用电率
		}
		return cydl;
	}
	
	/**
	 * 
	 * @description 计算电量(购方发电量、购方上网电量、售方发电量、售方上网电量)
	 * @param params
	 * @return
	 * @author mengke
	 * @date 2014-7-16
	 */
	@RequestMapping("/getDl")
	public @RawResponseBody Object getGfswdl(@RequestParam String params){
		Map map = null;//存放参数
		String gffdl = "";//购方发电量
		String gfswdl = "";//购方上网电量
		String sffdl = "";//售方发电量
		String sfswdl = "";//售方上网电量
		String cydl = "";
		BigDecimal gfdl = null;//购方发电量或者购方上网电量
		try {
			map = JsonUtils.getObjectMapper().readValue(params, Map.class);
		} catch (Exception e) {
			throw new RestClientException("程序异常");
		}
		if(map != null && map.containsKey("gffdl")){//根据购方发电量计算购方上网电量
			gffdl = map.get("gffdl")==null?"":map.get("gffdl").toString();
			cydl = map.get("cydl")==null?"":map.get("cydl").toString();
			BigDecimal gffdl1 = new BigDecimal(gffdl);
			BigDecimal cydl1 = new BigDecimal(cydl);
			BigDecimal m = new BigDecimal(1);
			gfdl = gffdl1.multiply(m.subtract(cydl1));//根据购方发电量计算购方上网电量
		}
		if(map != null && map.containsKey("gfswdl")){//根据购方上网电量计算购方发电量
			gfswdl = map.get("gfswdl")==null?"":map.get("gfswdl").toString();
			cydl = map.get("cydl")==null?"":map.get("cydl").toString();
			BigDecimal gfswdl1 = new BigDecimal(gfswdl);
			BigDecimal cydl1 = new BigDecimal(cydl);
			BigDecimal m = new BigDecimal(1);
			gfdl = gfswdl1.divide(m.subtract(cydl1));//根据购方上网电量计算购方发电量
		}
		if(map != null && map.containsKey("sffdl")){//根据售方发电量计算售方上网电量
			sffdl = map.get("sffdl")==null?"":map.get("sffdl").toString();
			cydl = map.get("cydl")==null?"":map.get("cydl").toString();
			BigDecimal sffdl1 = new BigDecimal(sffdl);
			BigDecimal cydl1 = new BigDecimal(cydl);
			BigDecimal m = new BigDecimal(1);
			gfdl = sffdl1.multiply(m.subtract(cydl1));//根据售方发电量计算售方上网电量
		}
		if(map != null && map.containsKey("sfswdl")){//根据售方上网电量计算售方发电量
			sfswdl = map.get("sfswdl")==null?"":map.get("sfswdl").toString();
			cydl = map.get("cydl")==null?"":map.get("cydl").toString();
			BigDecimal sfswdl1 = new BigDecimal(sfswdl);
			BigDecimal cydl1 = new BigDecimal(cydl);
			BigDecimal m = new BigDecimal(1);
			gfdl = sfswdl1.divide(m.subtract(cydl1));//根据售方上网电量计算售方发电量
		}
		return gfdl;
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
	 * @param economicUnitRegisterBizc
	 */
	public void setContractAddRegisterBizc(IContractAddRegisterBizc contractAddRegisterBizc) {
		this.contractAddRegisterBizc = contractAddRegisterBizc;
	}
}
