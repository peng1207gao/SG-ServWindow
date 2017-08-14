package com.sgcc.dlsc.contractManage.cocontractbaseInfoV2;
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

import com.sgcc.dlsc.commons.util.DateUtil;
import com.sgcc.dlsc.commons.util.UserInfoUtil;
import com.sgcc.dlsc.contractManage.cocontractbaseInfoV2.bizc.ICocontractbaseInfoV2Bizc;
import com.sgcc.dlsc.contractManage.cocontractbaseInfoV2.vo.CoContractbaseinfoVO;
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
import com.sgcc.uap.rest.support.WrappedResult;
import com.sgcc.uap.rest.utils.JsonUtils;
import com.sgcc.uap.rest.utils.ViewAttributeUtils;

@Controller
@RequestMapping("/cocontractbaseinfoV2") //根据po类名生成
public class CocontractbaseInfoV2Controller {
	
    @Autowired
    private ICocontractbaseInfoV2Bizc cocontractbaseInfoV2Bizc;

    /**
	 * 保存
	 * 请求体格式为:{"items":[{"id":"1","name":"1","sex":"1","age":"1"},{"id":"1","name":"1","sex":"1","age":"1"}]}
	 * @return voList
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ItemResponseBody 
	List<CoContractbaseinfoVO> save(@ItemsRequestBody List<Map> list){ 
		return null;
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
		return null;
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
		return null;
	}
	/**
	 * 注入逻辑构件
	 * @param coContractbaseInfoBizc
	 */
	public void setCoContractbaseInfoBizc(ICocontractbaseInfoV2Bizc cocontractbaseInfoV2Bizc) {
		this.cocontractbaseInfoV2Bizc = cocontractbaseInfoV2Bizc;
	}
    
	/**
	 * 获得fusioncharts
	 * @description 方法描述
	 * @param params
	 * @return
	 * @author liling
	 * @throws Exception 
	 * @date 2013-6-20
	 */
	@RequestMapping(value = "/getChartData",method = RequestMethod.GET)
	public @ItemResponseBody Object getChartData(@RequestParam String params,HttpServletRequest request) throws Exception{		
		
		try {
			Map map = JsonUtils.getObjectMapper().readValue(params, Map.class);
			UserInfoUtil.getLoginUserMarket(request, map);
			String chartString = cocontractbaseInfoV2Bizc.getChartsXml(map);
			return chartString;
		} catch (Exception e) {
			throw new RestClientException("方法执行异常");
		}
	}
	
	/**
	 * 合同饼图展示
	 * @description 方法描述
	 * @return
	 * @author xiabaie
	 * @date 2014-1-15
	 */
	@RequestMapping(value = "/charts")
	public @ItemResponseBody List charts(HttpServletRequest request){
		try {
			StringBuffer str1 = new StringBuffer();
			StringBuffer str2 = new StringBuffer();
			StringBuffer str3 = new StringBuffer();
			StringBuffer str4 = new StringBuffer();
			StringBuffer str5 = new StringBuffer();
			StringBuffer str6 = new StringBuffer();
			System.out.println("---------------------------------");
			String marketid = "";
			Map map = new HashMap();
			try {
				marketid = UserInfoUtil.getLoginUserMarket(request);
			} catch (Exception e) {
				e.printStackTrace();
			}
			int count = 0;//合同总数
			String marketName = "";
			//如果是国网总部，则显示国家电网公司系统sgbiz
			if (marketid.equals("91812")) {
				marketName = "国家电网公司系统";
			} else {
				try {
					marketName = UserInfoUtil.getLoginUserMarketName(request);//市场名
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			map.put("marketName", marketName);
			//marketName = getLoginUser().getMarketName();//市场名
			//String date = DateConvertor.getYesterdayTime("yyyy-MM-dd");//昨天
			//gendate = DateConvertor.getYesterdayTime("yyyy年MM月dd日");//昨天
			String date = DateUtil.getNowDate("yyyy-MM-dd");//.getUtilDateString(DateUtil.getDateTime(), "yyyy-MM-dd");//今天
			String gendate = DateUtil.getNowDate("yyyy年MM月dd日");//getUtilDate(DateUtil.getDateTime(), "yyyy年MM月dd日").toString();//今天
			map.put("gendate", gendate);
			String codate = date.substring(0, 4);//年份
			map.put("codate", codate);
			//合同状态
			String name = "";
			List statelist = cocontractbaseInfoV2Bizc.findCoContract1(marketid,
					date);
			map.put("statelist", statelist);
			double percent = 0.00;
			int c = 0;//已签订合同
			//contextPvd.setRequestAttr("statelist", statelist);
			str1.append("<chart caption='不同合同状态下的合同个数（单位 ：个）'  exportEnabled='1' exportAtClient='1' exportDialogMessage='正在导出' exportHandler='fcExporter1' formatNumberScale='0' exportFormats='PDF=导出为PDF|PNG=导出为PNG|JPG=导出为JPG' showLegend='1' showLabels='0' bgColor ='e1f5ff' showBorder='1' borderColor='gray'>");
			if (statelist.size() > 0) {
				for (int i = 0; i < statelist.size(); i++) {
					//合同份数
					Object[] obj = (Object[]) statelist.get(i);
					name = obj[1] == null ? "" : obj[1].toString();
					int num = obj[0] == null ? 0 : Integer.valueOf(obj[0]
							.toString());
					//已签订=已签订状态+已备案状态
					if (name.equals("已签订") || name.equals("已备案")) {
						c = c + num;
					}
					str1.append("<set label='");
					str1.append(name);
					str1.append("' value='");
					str1.append(num);
					str1.append("' isSliced='1'/>");
					//count = count + num;
				}
				List countlist = cocontractbaseInfoV2Bizc.findCount(marketid,
						date);
				if (countlist != null && countlist.size() > 0) {
					Object obj = (Object) countlist.get(0);
					count = obj == null ? 0 : Integer.parseInt(obj.toString());
				}
				//contextPvd.setRequestAttr("c", c);
				if (count != 0) {
					percent = Math.round((c * 10000) / count) / 100.0;
				} else {
					percent = 0.00;
				}
			} else {
				str1.append("<set label='' value='' isSliced='0'/>");
			}
			str1.append("</chart>");
			map.put("count", count);
			map.put("percent", percent);
			map.put("c", c);
			//合同期别
			List cyclist = cocontractbaseInfoV2Bizc.findCoContract2(marketid,
					date);
			str2.append("<chart caption='不同合同期别下的跨省区合同个数（单位 ：个）'  exportEnabled='1' exportAtClient='1' exportDialogMessage='正在导出' exportHandler='fcExporter2' formatNumberScale='0' exportFormats='PDF=导出为PDF|PNG=导出为PNG|JPG=导出为JPG' showLegend='1' showLabels='0' bgColor ='e1f5ff' showBorder='1' borderColor='gray'>");
			//跨省区合同
			if (cyclist.size() > 0) {
				for (int i = 0; i < cyclist.size(); i++) {
					Object[] obj = (Object[]) cyclist.get(i);
					name = obj[1] == null ? "其它" : obj[1].toString();
					int num = obj[0] == null ? 0 : Integer.valueOf(obj[0]
							.toString());
					str2.append("<set label='");
					str2.append(name);
					str2.append("' value='");
					str2.append(num);
					str2.append("' isSliced='1'/>");

				}
			} else {
				str2.append("<set label='' value='' isSliced='0'/>");
			}
			str2.append("</chart>");
			//发电类型
			str3.append("<chart caption='各发电类型下的合同个数（单位 ：个）'  exportEnabled='1' exportAtClient='1' exportDialogMessage='正在导出' exportHandler='fcExporter3' formatNumberScale='0' exportFormats='PDF=导出为PDF|PNG=导出为PNG|JPG=导出为JPG' showLegend='1' showLabels='0' bgColor ='e1f5ff' showBorder='1' borderColor='gray'>");
			List typelist = cocontractbaseInfoV2Bizc.findcoContract3(marketid,
					date);
			if (typelist.size() > 0) {
				for (int i = 0; i < typelist.size(); i++) {
					Object[] obj = (Object[]) typelist.get(i);
					name = obj[1] == null ? "" : obj[1].toString();
					//合同个数
					int num = obj[0] == null ? 0 : Integer.valueOf(obj[0]
							.toString());
					str3.append("<set label='");
					str3.append(name);
					str3.append("' value='");
					str3.append(num);
					str3.append("' isSliced='1'/>");

				}
			} else {
				str3.append("<set label='' value='' isSliced='0'/>");
			}
			str3.append("</chart>");
			//合同类型
			str4.append("<chart caption='各发电类型下的合同电量（单位 ：兆瓦时）'  exportEnabled='1' exportAtClient='1' exportDialogMessage='正在导出' exportHandler='fcExporter4' formatNumberScale='0' exportFormats='PDF=导出为PDF|PNG=导出为PNG|JPG=导出为JPG' showLegend='1' showLabels='0' bgColor ='e1f5ff' showBorder='1' borderColor='gray'>");
			List typelist2 = cocontractbaseInfoV2Bizc.findcoContract4(marketid,
					date);
			if (typelist2.size() > 0) {
				for (int i = 0; i < typelist2.size(); i++) {
					Object[] obj = (Object[]) typelist2.get(i);
					name = obj[1] == null ? "" : obj[1].toString();
					//合同电量
					double num = obj[0] == null ? 0 : Double.valueOf(obj[0]
							.toString());
					str4.append("<set label='");
					str4.append(name);
					str4.append("' value='");
					str4.append(num);
					str4.append("' isSliced='1'/>");

				}
			} else {
				str4.append("<set label='' value='' isSliced='0'/>");
			}
			str4.append("</chart>");
			//发电集团
			str5.append("<chart caption='5大发电集团下的合同个数（单位 ：个）'  exportEnabled='1' exportAtClient='1' exportDialogMessage='正在导出' exportHandler='fcExporter5' formatNumberScale='0' exportFormats='PDF=导出为PDF|PNG=导出为PNG|JPG=导出为JPG' showLegend='1' showLabels='0' bgColor ='e1f5ff' showBorder='1' borderColor='gray'>");
			List genlist = cocontractbaseInfoV2Bizc.findCoContract5(marketid,
					date);
			if (genlist.size() > 0) {
				for (int i = 0; i < genlist.size(); i++) {
					Object[] obj = (Object[]) genlist.get(i);
					name = obj[1] == null ? "" : obj[1].toString();
					//合同个数
					int num = obj[0] == null ? 0 : Integer.valueOf(obj[0]
							.toString());
					str5.append("<set label='");
					str5.append(name);
					str5.append("' value='");
					str5.append(num);
					str5.append("' isSliced='1'/>");

				}
			} else {
				str5.append("<set label='' value='' isSliced='0'/>");
			}
			str5.append("</chart>");
			//发电集团
			//String[] gengroup = {"大唐","华电","华能","国电","中电投"};
			str6.append("<chart caption='5大发电集团下的合同电量（单位 ：兆瓦时）'  exportEnabled='1' exportAtClient='1' exportDialogMessage='正在导出' exportHandler='fcExporter6' formatNumberScale='0' exportFormats='PDF=导出为PDF|PNG=导出为PNG|JPG=导出为JPG' showLegend='1' showLabels='0' bgColor ='e1f5ff' showBorder='1' borderColor='gray'>");
			List genlist2 = cocontractbaseInfoV2Bizc.findCoContract6(marketid,
					date);
			if (genlist2.size() > 0) {
				for (int i = 0; i < genlist2.size(); i++) {
					Object[] obj = (Object[]) genlist2.get(i);
					name = obj[1] == null ? "" : obj[1].toString();
					//合同个数
					double num = obj[0] == null ? 0 : Double.valueOf(obj[0]
							.toString());
					str6.append("<set label='");
					str6.append(name);
					str6.append("' value='");
					str6.append(num);
					str6.append("' isSliced='1'/>");

				}
			} else {
				str6.append("<set label='' value='' isSliced='0'/>");
			}
			str6.append("</chart>");
			request.setAttribute("strCapXml1", str1.toString());
			map.put("strCapXml1", str1.toString());
			//contextPvd.setRequestAttr("strCapXml2",str2.toString());
			map.put("strCapXml2", str2.toString());
			//contextPvd.setRequestAttr("strCapXml3",str3.toString());
			map.put("strCapXml3", str3.toString());
			//contextPvd.setRequestAttr("strCapXml4",str4.toString());
			map.put("strCapXml4", str4.toString());
			//contextPvd.setRequestAttr("strCapXml5",str5.toString());
			map.put("strCapXml5", str5.toString());
			//contextPvd.setRequestAttr("strCapXml6",str6.toString());
			map.put("strCapXml6", str6.toString());
			List purlist = cocontractbaseInfoV2Bizc.findPur(marketid, date);//向电厂购电
			if (purlist.size() > 0) {
				Object[] obj = (Object[]) purlist.get(0);
				int purcount = obj[0] == null ? 0 : Integer.valueOf(obj[0]
						.toString());//向电厂购电个数
				String pursum = obj[1] == null ? "0" : obj[1].toString();//向电厂购电电量
				map.put("purcount", purcount);
				map.put("pursum", pursum);
			}
			List fdqlist = cocontractbaseInfoV2Bizc.findfdq(marketid, date);//发电权
			if (fdqlist.size() > 0) {
				Object[] obj = (Object[]) fdqlist.get(0);
				int fdqcount = obj[0] == null ? 0 : Integer.valueOf(obj[0]
						.toString());
				String fdqsum = obj[1] == null ? "0" : obj[1].toString();
				map.put("fdqcount", fdqcount);//发电权个数
				map.put("fdqsum", fdqsum);//发电权电量
			}
			List dyhlist = cocontractbaseInfoV2Bizc.finddyh(marketid, date);//大用户
			if (dyhlist.size() > 0) {
				Object[] obj = (Object[]) dyhlist.get(0);
				int dyhcount = obj[0] == null ? 0 : Integer.valueOf(obj[0]
						.toString());
				String dyhsum = obj[1] == null ? "0" : obj[1].toString();
				map.put("dyhcount", dyhcount);//发电权个数
				map.put("dyhsum", dyhsum);//发电权电量
			}
			List arealist = cocontractbaseInfoV2Bizc.findarea(marketid, date);//跨区
			if (arealist.size() > 0) {
				Object obj = (Object) arealist.get(0);
				int areacount = obj == null ? 0 : Integer.valueOf(obj
						.toString());
				map.put("areacount", areacount);//跨区
			}
			List prolist = cocontractbaseInfoV2Bizc.findpro(marketid, date);//跨省
			if (prolist.size() > 0) {
				Object obj = (Object) prolist.get(0);
				int procount = obj == null ? 0 : Integer
						.valueOf(obj.toString());
				map.put("procount", procount);//跨省
			}
			List jslist = cocontractbaseInfoV2Bizc.findjs(marketid, date);//大用户
			if (jslist.size() > 0) {
				Object[] obj = (Object[]) jslist.get(0);
				int jscount = obj[0] == null ? 0 : Integer.valueOf(obj[0]
						.toString());
				String jssum = obj[1] == null ? "0" : obj[1].toString();
				map.put("jscount", jscount);//基数
				map.put("jssum", jssum);
			}
			List wsdlist = cocontractbaseInfoV2Bizc.findwsd(marketid, date);//外送电
			if (wsdlist.size() > 0) {
				Object[] obj = (Object[]) wsdlist.get(0);
				int wsdcount = obj[0] == null ? 0 : Integer.valueOf(obj[0]
						.toString());
				String wsdsum = obj[1] == null ? "0" : obj[1].toString();
				map.put("wsdcount", wsdcount);//基数
				map.put("wsdsum", wsdsum);
			}
			List kqksList = cocontractbaseInfoV2Bizc.getTractqtyAll(marketid,
					date, "9F457805-0846-ABBC-3414-5CC4770"); //跨区跨省
			if (kqksList.size() > 0) {
				Object obj = (Object) kqksList.get(0);
				int kqkscount = obj == null ? 0 : Integer.valueOf(obj
						.toString());
				map.put("kqkscount", kqkscount);//跨区跨省个数
			}
			List<Map> list = new ArrayList<Map>();
			list.add(map);
			return list;
		} catch (Exception e) {
			throw new RestClientException("方法执行异常");
		}
	}
}
