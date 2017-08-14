package com.sgcc.dlsc.statesanalysis.contractExeTra;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.QueryRequestParam;
import com.sgcc.uap.rest.annotation.attribute.ViewAttributeData;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;
import com.sgcc.uap.rest.support.ViewMetaData;
import com.sgcc.uap.rest.support.WrappedResult;
import com.sgcc.uap.rest.utils.ViewAttributeUtils;

import com.sgcc.dlsc.commons.util.UserInfoUtil;
import com.sgcc.dlsc.statesanalysis.contractExeTra.bizc.IContractExeTraBizc;
import com.sgcc.dlsc.statesanalysis.contractExeTra.vo.CoContractbaseinfoVO;

@Controller
@RequestMapping("/contractexetra") //根据po类名生成
public class ContractExeTraController {
	
    @Autowired
    private IContractExeTraBizc contractExeTraBizc;
	
    
    int tenPage=0;
    int tenPageSize=0;
    List<Object> tableList;
	
	
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
		QueryResultObject queryResult=new QueryResultObject();
		try {
			StringBuilder sql=new StringBuilder("");
			String	marketId = UserInfoUtil.getLoginUserMarket(request);//获取登陆人场景
			tableList=contractExeTraBizc.query(params,marketId,sql);
			tenPage = Integer.parseInt(params.getPageIndex());
			tenPageSize = Integer.parseInt(params.getPageSize());
			//数据映射
			List<CoContractbaseinfoVO> vos =contractExeTraBizc.toVoList(tableList);
			queryResult.setItems(vos);
			queryResult.setItemCount(contractExeTraBizc.count(sql));
			
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
		return queryResult;
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
	 * @param contractExeTraBizc
	 */
	public void setContractExeTraBizc(IContractExeTraBizc contractExeTraBizc) {
		this.contractExeTraBizc = contractExeTraBizc;
	}

	/**
	 * 
	 * 获取图表
	 * 
	 * */
	@RequestMapping(value = "/getStatckChart",method = RequestMethod.GET)
	public @ItemResponseBody Object getChartPie(@RequestParam String params,HttpServletRequest request) throws Exception{	
		try {
			String chartsXML = contractExeTraBizc.getStackChart(tableList);//@根据业务场景id查相应的图表的数据
			return chartsXML;
		} catch (Exception e) {
			throw new RestClientException("程序异常");
		}
	}
}
