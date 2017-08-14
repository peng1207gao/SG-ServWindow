package com.sgcc.dlsc.statesanalysis.radarscan;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonProcessingException;
import org.hibernate.lob.SerializableBlob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;

import com.sgcc.dlsc.commons.util.DateUtil;
import com.sgcc.dlsc.commons.util.ToolsSys;
import com.sgcc.dlsc.commons.util.UserInfoUtil;
import com.sgcc.dlsc.contractManage.CoContractFjAct.bizc.ICoContractFjActBizc;
import com.sgcc.dlsc.contractManage.CoContractFjAct.vo.CoContractaffixinfoTransfer;
import com.sgcc.dlsc.contractManage.CoContractFjAct.vo.CoContractaffixinfoVO;
import com.sgcc.dlsc.contractManage.coContractbaseinfoV2Act.vo.CoContractbaseinfoVO;
import com.sgcc.dlsc.entity.po.CoContractaffixinfo;
import com.sgcc.dlsc.statesanalysis.radarscan.bizc.IRadarScanBizc;
import com.sgcc.uap.integrate.isc.service.IPermissionControl;
import com.sgcc.uap.integrate.isc.service.PermissionControl;
import com.sgcc.uap.rest.annotation.ColumnRequestParam;
import com.sgcc.uap.rest.annotation.ColumnResponseBody;
import com.sgcc.uap.rest.annotation.IdRequestBody;
import com.sgcc.uap.rest.annotation.ItemRequestParam;
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

@Controller
@RequestMapping("/radarscan") //根据po类名生成
public class RadarScanController {
	
    @Autowired
    private IRadarScanBizc radarScanBizc;
	
    /**
   	 * 
   	 * @description 获取统计图形数据
   	 * @param params
   	 * @return
   	 */
	@RequestMapping(value = "/getChartData",method = RequestMethod.GET)
	public @ItemResponseBody Object getChartData(@RequestParam String params,HttpServletRequest request) {		
		try {
			Map map = JsonUtils.getObjectMapper().readValue(params, Map.class);
			String threshold = map.get("threshold").toString();
			String period = map.get("period").toString();
			String marketId = UserInfoUtil.getLoginUserMarket(request);
			return radarScanBizc.getChartData(threshold,period,marketId);//@根据业务场景id查相应的图表的数据
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
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
 	QueryResultObject getStatics(@QueryRequestParam("params") RequestCondition params,HttpServletRequest request)  {
 		
 		try {
 			QueryResultObject result;//定义返回值
 			String marketId = UserInfoUtil.getLoginUserMarket(request);
 			String threshold = "";
 			String period = "";
 			String[] params2 = params.getFilter().toString().split("&");
 			if(params2!=null){
 				threshold = params2[0].split("=").length > 1 ? params2[0].split("=")[1] : "";
 				period = params2[1].split("=").length > 1 ? params2[1].split("=")[1] : "";
 			}
 			result = radarScanBizc.getStatics( threshold,period,marketId);
 			return result;
 			} catch (Exception e) {
 				throw new RestClientException("程序异常");
 			}
 	}
    
	
	/**
	 * 雷达扫描按阀值过滤查询
	 * 
	 * @param params
	 *            将请求参数{"filter":"","orderStr":"age","columns":"id,name"}
	 *            封装为RequestCondition对象
	 * @return queryResult
	 */
	@RequestMapping("/")
	public @ItemResponseBody
	QueryResultObject query(@QueryRequestParam("params") RequestCondition params,HttpServletRequest request) {
		
		try {
		QueryResultObject result;//定义返回值
		String marketId = UserInfoUtil.getLoginUserMarket(request);
		String threshold = "";
		String period = "";
		String[] params2 = params.getFilter().toString().split("&");
		if(params2!=null){
			threshold = params2[0].split("=").length > 1 ? params2[0].split("=")[1] : "";
			period = params2[1].split("=").length > 1 ? params2[1].split("=")[1] : "";
		}
		result = radarScanBizc.query(Integer.parseInt(params.getPageIndex()),
				Integer.parseInt(params.getPageSize()), threshold,period,marketId);
		return result;
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
	 * 注入逻辑构件
	 * @param radarScanBizc
	 */
	public void setRadarScanBizc(IRadarScanBizc radarScanBizc) {
		this.radarScanBizc = radarScanBizc;
	}
}
