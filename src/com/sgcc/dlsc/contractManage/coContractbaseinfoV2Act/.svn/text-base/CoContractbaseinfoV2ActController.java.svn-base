package com.sgcc.dlsc.contractManage.coContractbaseinfoV2Act;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;

import com.sgcc.dlsc.commons.util.DateUtil;
import com.sgcc.dlsc.commons.util.ExcelUtilJXL;
import com.sgcc.dlsc.commons.util.UserInfoUtil;
import com.sgcc.dlsc.contractManage.coContractbaseinfoV2Act.bizc.ICoContractbaseinfoV2ActBizc;
import com.sgcc.dlsc.contractManage.coContractbaseinfoV2Act.vo.CoContractbaseinfoVO;
import com.sgcc.dlsc.entity.po.CoContractbaseinfo;
import com.sgcc.isc.core.orm.identity.User;
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
import com.sgcc.uap.utils.StringUtils;

@Controller
@RequestMapping("/coContractbaseinfoV2Act") //根据po类名生成
public class CoContractbaseinfoV2ActController {
	
    @Autowired
    private ICoContractbaseinfoV2ActBizc bizc;
    
    private String contracttype1 = "'4DC24C12-413C-5A67-BEBE-AB07C0C','38F71C1D-DAD0-23E4-A7DF-69AD9EF'";//发电权合同，跨区发电权短期交易
    private String id;//@id
    private String name;//@名称
    private String ctype;//@类型
    private String purchaserunit;//@购电方机组
    private String sellerunit;//@售电方机组
    private String sdate;//开始时间
    private String edate;//结束时间
    private String contractenergy;//合同电量
    private String price;//电价
    private String bs;//@报送
    private String ss;//@输送
    private String flowf;//@流程标志
    private String pf;//@批复
    private String updatepersonid;//@更新人id
    private String updatetime;//@更新时间
    private String updatemarket;//更新人的marketid
    private String download;//下载
    private String contracttemplateid;//合同范本
    private String startDate;//开始时间-
	 private String endDate;//结束时间-
	 private String contractcyc;//合同周期-
	 private String signstate;//签订状态
	 private String backupstate;//备案状态
	 private String prepcontractflag;//合同状态-
	 private String purchaser;//购电方-
	 private String seller;//售电方-
	 //private String transer;//输电方
	 private String isend;//是否终止，流转状态
	 private String searchDateType;//查询时间类型
	 private String contractid;
	 private String sequenceid;
	 private String appopinion;//审批意见
	 private String flowflag;
	 private String contracttype;//合同类型-
	 private String contracttypeid;//合同类型-
	 private String contractName;	//合同名称
	 private String papercontractcode;//纸质合同编号
	 private String syscontractcode;//系统合同编号
	 private String marketId;//登陆人的场景id
	 private String marketid;//页面选择的业务场景id
	 private String corporationid;//市场成员编码
	 private String powertype;//售电方发电类型
	 
	 /**
	  * 
	  * @description 初始化方法
	  * @param request
	  * @return
	  * @throws Exception
	  * @author zhangzhen
	  * @date 2014-1-22
	  */
	 @RequestMapping("/init")
	 public @ItemResponseBody List init(HttpServletRequest request) throws Exception{
		 marketId = UserInfoUtil.getLoginUserMarket(request);//获取登陆人场景
		 
		 List list = new ArrayList();//定义返回值List
		 list.add(marketId);	//添加登陆人场景
		 return list;	//返回List
	 }
	 
	  /**
	 * 重置终止合同
	 * isdelete 改为 : 0  isend 改为：0
	 * @return true/false
	 * @throws Exception 
	 * @author wewu
	 */
	@RequestMapping(value = "/contractReset")
	public @ItemResponseBody List<String> contractReset(HttpServletRequest request) throws Exception{ 
		try {
			String checkedIds = request.getParameter("checkedId");
			String[] checkedId = checkedIds.split(",");
			String flag = "true";
			List<String> list = new ArrayList<String>();
			for(int i=0;i<checkedId.length;i++){
				CoContractbaseinfo coContractbaseinfo = bizc.findById(checkedId[i]);//@查询
				coContractbaseinfo.setIsdelete(new BigDecimal(0));
				coContractbaseinfo.setIsend(new BigDecimal(0));
				String  flow = bizc.getFlowState(checkedId[i]);
				coContractbaseinfo.setFlowflag(new BigDecimal(flow));
				try {
					bizc.update(coContractbaseinfo);//更新
					flag = "true";
				} catch (Exception e) {
					flag = "false";
					e.printStackTrace();
				}
			
			}
			list.add(flag);
			return list;
		} catch (Exception e) {
			throw new RestClientException("方法执行异常");
		}
	}
	 
	 /**
	  * 
	  * @description 查询合同序列
	  * @param marketId
	  * @return
	  * @author zhangzhen
	  * @date 2014-1-23
	  */
	 @RequestMapping("/getComponent13Items")
	 public @RawResponseBody Object getComponentItems(@RequestParam("marketId") String marketId,@RequestParam("contracttype") String contracttype){
			try {
				List list = bizc.querySequence(marketId,contracttype); //获取下拉框数据
				List<Map<String, String>> result = new ArrayList<Map<String, String>>(); //返回数据List
				for (int i = 0; i < list.size(); i++) { //遍历查询数据
					Object[] objs = (Object[]) list.get(i); //获取第i条数据
					String name = objs[1] == null ? "" : objs[1].toString(); //显示值
					String text = objs[0] == null ? "" : objs[0].toString(); //实际值
					Map<String, String> map = new HashMap<String, String>(); //每一对数据
					map.put("name", name); //显示值
					map.put("value", text); //实际值
					result.add(map); //添加每一对数据
				}
				return WrappedResult.successWrapedResult(result); //处理数据集，返回处理结果
			} catch (Exception e) {
				throw new RestClientException("方法执行异常");
			}
		
		}
	 
	 /**
	  * 
	  * @description 得到准入成员(类型：1,购电方，2,售点方，3,输电方)
	  * @param value
	  * @param marketId
	  * @param type
	  * @return
	  * @author zhangzhen
	  * @date 2014-1-22
	  */
	 @RequestMapping("getComponentItems")
	 public @RawResponseBody Object getComponentItems(@RequestParam("value") String value
			 			,@RequestParam("marketId") String marketId,@RequestParam("type") String type){
		 try {
			List list = bizc.getParticipantBySel(marketId,
					Integer.parseInt(value), type); //获取下拉框数据
			List<Map<String, String>> result = new ArrayList<Map<String, String>>(); //返回数据List
			for (int i = 0; i < list.size(); i++) { //遍历查询数据
				Object[] objs = (Object[]) list.get(i); //获取第i条数据
				String name = objs[1] == null ? "" : objs[1].toString(); //显示值
				String text = objs[0] == null ? "" : objs[0].toString(); //实际值
				Map<String, String> map = new HashMap<String, String>(); //每一对数据
				map.put("name", name); //显示值
				map.put("value", text); //实际值
				result.add(map); //添加每一对数据
			}
			return WrappedResult.successWrapedResult(result); //处理数据集，返回处理结果
		} catch (Exception e) {
			throw new RestClientException("方法执行异常");
		}
		 
	 }
    
	 /**
 	 * 外网用户查询
 	 * 
 	 * @param params
 	 *            将请求参数{"filter":"","orderStr":"age","columns":"id,name"}
 	 *            封装为RequestCondition对象
 	 * @return queryResult
	 * @throws Exception 
 	 */
 	@RequestMapping("/findOutWebContractList")
 	public @ItemResponseBody
 	QueryResultObject findOutWebContractList(@QueryRequestParam("params") RequestCondition params,HttpServletRequest request) throws Exception {
 		
 		try {
			corporationid = UserInfoUtil.getMarketPaintCode(request);//获取市场成员编码
			marketId = UserInfoUtil.getLoginUserMarket(request);//获取登陆人场景
			QueryResultObject result;//定义返回值
			setParamByParams(params); //将RequestCondition中的参数拿出来
			result = bizc
					.findOutWebContractList(
							Integer.parseInt(params.getPageIndex()),
							Integer.parseInt(params.getPageSize()),
							contracttype, searchDateType, startDate, endDate,
							contractcyc, sequenceid, signstate, backupstate,
							corporationid, marketId); //获取合同管理展示数据
			return result;
		} catch (Exception e) {
			throw new RestClientException("方法执行异常");
		}
 	}
 	
 	 /**
 	 * 合同附件维护-查询
 	 * 
 	 * @param params
 	 *            将请求参数{"filter":"","orderStr":"age","columns":"id,name"}
 	 *            封装为RequestCondition对象
 	 * @return queryResult
    * @throws IOException 
    * @throws JsonProcessingException 
 	 */
 	@RequestMapping("/findFjContractList")
 	public @ItemResponseBody
 	QueryResultObject findFjContractList(@QueryRequestParam("params") RequestCondition params) throws JsonProcessingException, IOException {
 		
 		try {
			QueryResultObject result;//定义返回值
			setParamByParams(params); //将RequestCondition中的参数拿出来
			result = bizc.findFjContractList(
					Integer.parseInt(params.getPageIndex()),
					Integer.parseInt(params.getPageSize()), marketid,
					contracttype, searchDateType, flowflag, startDate, endDate,
					contractcyc, prepcontractflag, purchaser, seller,
					sequenceid, marketId); //获取合同管理展示数据
			return result;
		} catch (Exception e) {
			throw new RestClientException("方法执行异常");
		}
 	}
	 
    /**
 	 * 查询功能
 	 * 
 	 * @param params
 	 *            将请求参数{"filter":"","orderStr":"age","columns":"id,name"}
 	 *            封装为RequestCondition对象
 	 * @return queryResult
    * @throws IOException 
    * @throws JsonProcessingException 
 	 */
 	@RequestMapping("/")
 	public @ItemResponseBody
 	QueryResultObject query(@QueryRequestParam("params") RequestCondition params) throws JsonProcessingException, IOException {
 		
 		try {
			QueryResultObject result;//定义返回值
			setParamByParams(params); //将RequestCondition中的参数拿出来
			result = bizc.findCoContractList11(
					Integer.parseInt(params.getPageIndex()),
					Integer.parseInt(params.getPageSize()), marketid,
					contracttype, searchDateType, startDate, endDate,
					contractcyc, prepcontractflag, purchaser, seller,
					sequenceid, flowflag, marketId, contractName,
					papercontractcode, syscontractcode,powertype); //获取合同管理展示数据
			return result;
		} catch (Exception e) {
			throw new RestClientException("方法执行异常");
		}
 	}
 	
 	
 	 /**
 	 * 合同分页管理查询功能
 	 * 
 	 * @param params
 	 *            将请求参数{"filter":"","orderStr":"age","columns":"id,name"}
 	 *            封装为RequestCondition对象
 	 * @return queryResult
    * @throws IOException 
    * @throws JsonProcessingException 
 	 */
 	@RequestMapping("/queryMonthManage")
 	public @ItemResponseBody
 	QueryResultObject queryMonthManage(@QueryRequestParam("params") RequestCondition params) throws JsonProcessingException, IOException {
 		
 		try {
			QueryResultObject result;//定义返回值
			setParamByParams(params); //将RequestCondition中的参数拿出来
			result = bizc.queryMonthManage(
					Integer.parseInt(params.getPageIndex()),
					Integer.parseInt(params.getPageSize()), marketid,
					contracttype, searchDateType, startDate, endDate,
					contractcyc, prepcontractflag, purchaser, seller,
					sequenceid, flowflag, marketId, contractName,
					papercontractcode, syscontractcode,powertype); //获取合同管理展示数据
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RestClientException("方法执行异常");
		}
 	}
 	
    /**
  	 * 已终止合同查询功能
  	 * 
  	 * @param params
  	 *            将请求参数{"filter":"","orderStr":"age","columns":"id,name"}
  	 *            封装为RequestCondition对象
  	 * @return queryResult
     * @throws IOException 
     * @throws JsonProcessingException 
  	 */
  	@RequestMapping("/queryTermination")
  	public @ItemResponseBody
  	QueryResultObject queryTermination(@QueryRequestParam("params") RequestCondition params) throws JsonProcessingException, IOException {
  		
  		try {
			QueryResultObject result;//定义返回值
			setParamByParams(params); //将RequestCondition中的参数拿出来
			result = bizc.findCoContractList12(
					Integer.parseInt(params.getPageIndex()),
					Integer.parseInt(params.getPageSize()), marketid,
					contracttype, searchDateType, startDate, endDate,
					contractcyc, prepcontractflag, purchaser, seller,
					sequenceid, flowflag, marketId, contractName,
					papercontractcode, syscontractcode); //获取合同管理展示数据
			return result;
		} catch (Exception e) {
			throw new RestClientException("方法执行异常");
		}
  	}
 	
 	/**
 	 * 
 	 * @description 获取统计信息
 	 * @param params
 	 * @return
 	 * @throws JsonProcessingException
 	 * @throws IOException
 	 * @author zhangzhen
 	 * @date 2014-1-25
 	 */
 	@RequestMapping("/getStatics")
 	public @ItemResponseBody
 	QueryResultObject getStatics(@QueryRequestParam("params") RequestCondition params) throws JsonProcessingException, IOException {
 		
 		try {
			QueryResultObject result = new QueryResultObject();//定义返回值
			setParamByParams(params); //将RequestCondition中的参数拿出来
			String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			String[] list = bizc.getCoStatistics12(contractName, marketid,
					contracttype, searchDateType, startDate, endDate,
					contractcyc, prepcontractflag, purchaser, seller,
					sequenceid, flowflag, marketId, date, papercontractcode,
					syscontractcode,powertype); //获取合同管理展示数据
			List<CoContractbaseinfoVO> voList = new ArrayList<CoContractbaseinfoVO>(); //返回的数据结果集
			CoContractbaseinfoVO vo = new CoContractbaseinfoVO(); //数据结果集的一个实体类
			vo.setCol0(list[0]);
			vo.setCol1(list[1]);
			vo.setCol2(list[2]);
			vo.setCol3(list[3]);
			vo.setCol4(list[4]);
			vo.setCol5(list[5]);
			vo.setCol6(list[6]);
			vo.setCol7(list[9]);
			vo.setCol8(list[7]);
			vo.setCol9(list[8]);
			voList.add(vo);
			result.setItems(voList); //添加数据结果集 
			return result;
		} catch (Exception e) {
			throw new RestClientException("方法执行异常");
		}
 	}
 	
 	/**
 	 * 
 	 * @description 获取统计信息
 	 * @param params
 	 * @return
 	 * @throws JsonProcessingException
 	 * @throws IOException
 	 * @author zhangzhen
 	 * @date 2014-1-25
 	 */
 	@RequestMapping("/getStatics2")
 	public @RawResponseBody
 	Object getStatics2(@RequestParam("contractName") String contractName
 			,@RequestParam("papercontractcode") String papercontractcode,@RequestParam("syscontractcode") String syscontractcode,@RequestParam("contractcyc") String contractcyc
 			,@RequestParam("prepcontractflag") String prepcontractflag,@RequestParam("purchaser") String purchaser,@RequestParam("seller") String seller,@RequestParam("marketid") String marketid
 			,@RequestParam("searchDateType") String searchDateType,@RequestParam("startDate") String startDate,@RequestParam("endDate") String endDate,@RequestParam("sequenceid") String sequenceid
 			,@RequestParam("marketId") String marketId,@RequestParam("flowflag") String flowflag) throws JsonProcessingException, IOException {
 		
 		try {
			String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			String[] list = bizc.getCoStatistics12(contractName, marketid,
					contracttype, searchDateType, startDate, endDate,
					contractcyc, prepcontractflag, purchaser, seller,
					sequenceid, flowflag, marketId, date, papercontractcode,
					syscontractcode,powertype); //获取合同管理展示数据
			List<CoContractbaseinfoVO> voList = new ArrayList<CoContractbaseinfoVO>(); //返回的数据结果集
			CoContractbaseinfoVO vo = new CoContractbaseinfoVO(); //数据结果集的一个实体类
			int num = Integer.parseInt(list[1]) + Integer.parseInt(list[2]);
			vo.setCol0(list[0]);
			vo.setCol1(num + "");
			vo.setCol2(list[2]);
			vo.setCol3(list[3]);
			vo.setCol4(list[4]);
			vo.setCol5(list[5]);
			vo.setCol6(list[6]);
			vo.setCol7(list[9]);
			vo.setCol8(list[7]);
			vo.setCol9(list[8]);
			voList.add(vo);
			return vo;
		} catch (Exception e) {
			throw new RestClientException("方法执行异常");
		}
 	}
 	/**
 	 * 
 	 * @description 合同导出功能
 	 * @param request
 	 * @param response
 	 * @return
 	 * @throws Exception
 	 * @author zhangzhen
 	 * @date 2014-1-24
 	 */
 	@RequestMapping(value = "/excel",method = RequestMethod.GET)
	public @VoidResponseBody Object exportExcel(HttpServletRequest request,HttpServletResponse response) throws Exception{		
		try {
			// 获取页面参数
			// 导出的excel的文件名
			String fileName = request.getParameter("fileName");
			// 要显示的excel的标题
			String columnCaptions = request.getParameter("columnCaptions");
			// 拦截条件
			String filter = request.getParameter("filter");
			// 对所有的参数进行格式编码转换
			if (StringUtils.isNullOrEmpty(columnCaptions))
				columnCaptions = "";
			else {
				columnCaptions = URLDecoder.decode(columnCaptions, "UTF-8");
			}
			//编码转换参数
			if (filter != null) {
				filter = URLDecoder.decode(filter, "UTF-8");
			}
			// 格式化查询条件，将字符串格式的条件转换为Map类型的，用于查询数据
			Map map = com.sgcc.uap.utils.JsonUtils.getMap(filter); //把查询条件转换成map格式
			setParamByParams2(map); //将String中的参数拿出来
			List list = new ArrayList();
			list = bizc.findCContractList(marketid, contracttype,
					searchDateType, startDate, endDate, contractcyc,
					prepcontractflag, purchaser, seller, sequenceid, flowflag,
					marketId, contractName, papercontractcode, syscontractcode,powertype); //查询数据
			if (fileName == null || fileName.trim().equals("")) {
				fileName = DateUtil.getUtilDateString(new Date(),
						"yyyyMMddHHmmssSSS");// 当文件名为空,取文件名为系统当前时间
			} else {
				fileName = URLDecoder.decode(fileName, "UTF-8");
			}
			// 调用工具类将excel导出
			ExcelUtilJXL.exportExcel(request, response, fileName,
					columnCaptions, list);
			return null;
		} catch (Exception e) {
			throw new RestClientException("方法执行异常");
		}
	}
 	/**
 	 * 将RequestCondition中的参数拿出来
 	 */
 	private void setParamByParams(RequestCondition params){
 		String[] params2 = params.getFilter().toString().split("&");//把params里边的查询条件分出来
 		int i = 0;	//获取params2的索引位置
 		
 		//给各个变量赋值
 		contractName = setParam(params2, i++);
 		papercontractcode = setParam(params2, i++);
 		syscontractcode = setParam(params2, i++);
 		contractcyc = setParam(params2, i++);
 		prepcontractflag = setParam(params2, i++);
 		contracttype = setParam(params2, i++);
 		purchaser = setParam(params2, i++);
 		seller = setParam(params2, i++);
 		marketid = setParam(params2, i++);
 		searchDateType = setParam(params2, i++);
 		startDate = setParam(params2, i++);
 		endDate = setParam(params2, i++);
 		sequenceid = setParam(params2, i++);
 		marketId = setParam(params2, i++);
 		flowflag = setParam(params2, i++);
 		powertype = setParam(params2, i++);
 		
 	}
 
 	/**
 	 * 给变量赋值
 	 * @param params2
 	 * @param i
 	 * @return
 	 */
 	private String setParam(String[] params2,int i){
 		return params2[i].split("=").length > 1 ? params2[i].split("=")[1] : "";
 	}
	/**
 	 * 
 	 * @description 将String中的参数拿出来
 	 * @author zhangzhen
 	 * @date 2014-1-24
 	 */
 	private void setParamByParams2(Map map){
 		//给各个变量赋值
 		contractName = setParam2(map,"contractName");
 		papercontractcode = setParam2(map,"papercontractcode");
 		syscontractcode = setParam2(map,"syscontractcode");
 		contractcyc = setParam2(map,"contractcyc");
 		prepcontractflag = setParam2(map,"prepcontractflag");
 		contracttype =setParam2(map,"contracttype");
 		purchaser = setParam2(map,"purchaser");
 		seller = setParam2(map,"seller");
 		marketid = setParam2(map,"marketid");
 		searchDateType = setParam2(map,"searchDateType");
 		startDate = setParam2(map,"startDate");
 		endDate = setParam2(map,"endDate");
 		sequenceid = setParam2(map,"sequenceid");
 		marketId = setParam2(map,"marketId");
 		flowflag = setParam2(map,"flowflag");
 	}
 	/**
 	 * 给变量赋值
 	 * @param params2
 	 * @param i
 	 * @return
 	 */
 	private String setParam2(Map map,String param){
 		return map.get(param) == null ? "" : map.get(param).toString();
 	}
	
	/**
	 * 保存
	 * 请求体格式为:{"items":[{"id":"1","name":"1","sex":"1","age":"1"},{"id":"1","name":"1","sex":"1","age":"1"}]}
	 * @return voList
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ItemResponseBody 
	List<CoContractbaseinfoVO> save(@ItemsRequestBody List<Map> list){ 
		try{
    		List<CoContractbaseinfoVO> voList = bizc.saveOrUpdate(list);
    		return voList;
		}catch(Exception e){
			throw new RestClientException("方法执行异常");
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
			bizc.remove(idObject);
		} catch (Exception e) {
			throw new RestClientException("方法执行异常");
		}
		return null;
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
			return bizc.queryById(id);
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
		datas = ViewAttributeUtils.getViewAttributes(columns, CoContractbaseinfoVO.class);
		return datas;
	}
	
	/**
	 * 从vo中获取页面展示元数据信息
	 * 
	 * @param columns
	 *            将请求参数{columns:["id","name"]}封装为字符串数组
	 * @return datas
	 */
	@RequestMapping("/queryTermination/meta")
	public @ColumnResponseBody
	List<ViewAttributeData> queryTermination(
			@ColumnRequestParam("params") String[] columns) {
		List<ViewAttributeData> datas = null;
		datas = ViewAttributeUtils.getViewAttributes(columns, CoContractbaseinfoVO.class);
		return datas;
	}
	
	/**
	 * 从vo中获取页面展示元数据信息
	 * 
	 * @param columns
	 *            将请求参数{columns:["id","name"]}封装为字符串数组
	 * @return datas
	 */
	@RequestMapping("/getStatics/meta")
	public @ColumnResponseBody
	List<ViewAttributeData> getStatics(
			@ColumnRequestParam("params") String[] columns) {
		List<ViewAttributeData> datas = null;
		datas = ViewAttributeUtils.getViewAttributes(columns, CoContractbaseinfoVO.class);
		return datas;
	}
	
	
	/**
	 * 从vo中获取页面展示元数据信息
	 * 
	 * @param columns
	 *            将请求参数{columns:["id","name"]}封装为字符串数组
	 * @return datas
	 */
	@RequestMapping("/queryMonthManage/meta")
	public @ColumnResponseBody
	List<ViewAttributeData> queryMonthManage(
			@ColumnRequestParam("params") String[] columns) {
		List<ViewAttributeData> datas = null;
		datas = ViewAttributeUtils.getViewAttributes(columns, CoContractbaseinfoVO.class);
		return datas;
	}
	
	
	
	
	/**
	 * 从vo中获取页面展示元数据信息(外网用户查询)
	 * 
	 * @param columns
	 *            将请求参数{columns:["id","name"]}封装为字符串数组
	 * @return datas
	 */
	@RequestMapping("/findOutWebContractList/meta")
	public @ColumnResponseBody
	List<ViewAttributeData> findOutWebContractList(
			@ColumnRequestParam("params") String[] columns) {
		List<ViewAttributeData> datas = null;
		datas = ViewAttributeUtils.getViewAttributes(columns, CoContractbaseinfoVO.class);
		return datas;
	}
	
	/**
	 * 从vo中获取页面展示元数据信息(合同附件维护)
	 * 
	 * @param columns
	 *            将请求参数{columns:["id","name"]}封装为字符串数组
	 * @return datas
	 */
	@RequestMapping("/findFjContractList/meta")
	public @ColumnResponseBody
	List<ViewAttributeData> findFjContractList(
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
	 * 
	 * @description 根据合同id查询合同序列id和合同序列名称
	 * @param contractid
	 * @return
	 * @author mengke
	 * @date 2014-5-16
	 */
	@RequestMapping("/getHtxl")
	public @RawResponseBody
	Object getHtxl(@RequestParam("contractid") String contractid){
		List list = bizc.getHtxl(contractid);
		return list;
	}
	@RequestMapping(value="/copybull",method = RequestMethod.POST)
	public @ItemResponseBody
	Object copybull(@ItemsRequestBody List<Map> items,HttpServletRequest request) throws Exception{
//		Map map = (Map)items;//JsonUtils.getObjectMapper().readValue(items, Map.class);
//		List<Map> list1 = (List) map.get("item");
		List list = new ArrayList();
		User ur = UserInfoUtil.getLoginUser(request);
		String str =bizc.copyBull(items,marketId,ur.getName()); 
		list.add(str);
//		List list = bizc.getHtxl(items);
		return list;
	}
	
	/**
	 * 
	 * @description 判断是否是发电权合同
	 * @param params
	 * @return
	 * @author mengke
	 * @date 2014-6-24
	 */
	@RequestMapping("/isfdqht")
	public @RawResponseBody
	Object isfdqht(@RequestParam("params") String params){
		boolean flag = false;
		Map map;
		try {
			map = JsonUtils.getObjectMapper().readValue(params, Map.class);
		} catch (Exception e) {
			// TODO: handle exception
			throw new RestClientException("方法执行异常!");
		}
		String contractTypeId = map.get("contracttype")==null?"":map.get("contracttype").toString();
		List list = bizc.isfdqht(map);
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				if(contractTypeId.equals(list.get(i))){
					flag = true;
					break;
				}
			}
		}
		return flag;
	}
	
	/**
	 * 注入逻辑构件
	 * @param bizc
	 */
	public void setCoContractbaseinfoV2ActBizc(ICoContractbaseinfoV2ActBizc bizc) {
		this.bizc = bizc;
	}
}
