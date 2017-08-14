package com.sgcc.dlsc.contractManage.coContractEnergyInfoChart;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;

import com.sgcc.dlsc.commons.util.DateUtil;
import com.sgcc.dlsc.commons.util.UserInfoUtil;
import com.sgcc.dlsc.contractManage.COContractEnergyInfo.bizc.ICOContractEnergyInfoBizc;
import com.sgcc.dlsc.contractManage.coContractEnergyInfoChart.bizc.ICoContractEnergyInfoChartBizc;
import com.sgcc.isc.core.orm.identity.User;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.TreeResponseBody;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.TreeNode;
import com.sgcc.uap.rest.utils.JsonUtils;

@Controller
@RequestMapping("/coCoEnInChart")
public class CoContractEnergyInfoChartController {
	
	  @Autowired
	    private ICoContractEnergyInfoChartBizc bizc;
	/**
	 * 获取合同购售电量电价信息
	 * @description 方法描述
	 * @param request
	 * @return
	 * @author zhangyan
	 * @date 2014-2-12
	 */
	@RequestMapping(value = "/getCOContractEnergyTree", method = RequestMethod.GET)
	public @TreeResponseBody
	List<TreeNode> getRoot(@RequestParam("contractid") String contractid) {
		try {
			List list = bizc.getCOContractEnergyTree(contractid);
			TreeNode pnode = new TreeNode();
			pnode.setHasChildren(true);
			pnode.setItemType("0");
			List<TreeNode> pnodelist = new ArrayList<TreeNode>();
			List<TreeNode> nodelist = new ArrayList<TreeNode>();
			Object[] o = (Object[]) list.get(0);
			Date ssdate = DateUtil.getUtilDate(o[1].toString(), "yyyy-MM-dd");
			Date sedate = DateUtil.getUtilDate(o[2].toString(), "yyyy-MM-dd");
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					TreeNode node = new TreeNode();
					Object[] obj = (Object[]) list.get(i);
					if (obj[3] != null) {
						BigDecimal bd = new BigDecimal(obj[3].toString())
								.setScale(4);
						node.setId(obj[1].toString() + "&&" + obj[2].toString()
								+ "&&" + bd.toString());
					} else {
						node.setId(obj[1].toString() + "&&" + obj[2].toString()
								+ "&&");
					}
					Date sdate = DateUtil.getUtilDate(obj[1].toString(),
							"yyyy-MM-dd");
					Date edate = DateUtil.getUtilDate(obj[2].toString(),
							"yyyy-MM-dd");
					if (ssdate.after(sdate)) {
						ssdate = sdate;
					}
					if (sedate.before(edate)) {
						sedate = edate;
					}
					node.setText("第" + (i + 1) + "段" + obj[1].toString() + "至"
							+ obj[2].toString());//显示字段的get方法
					node.setHasChildren(false);
					node.setItemType(""); //根节点的itemType
					nodelist.add(node);
				}
			}
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
			String ssdatestr = sd.format(ssdate);
			String sedatestr = sd.format(sedate);
			pnode.setChildNodes(nodelist);
			pnode.setText(ssdatestr + "至" + sedatestr);
			pnode.setId(ssdatestr + "&&" + sedatestr);
			pnodelist.add(pnode);
			return pnodelist;
		} catch (Exception e) {
			throw new RestClientException("方法执行异常");
		}
	}
	
	/**
	 * 复制合同
	 * 
	 * @param params
	 *          
	 *           
	 * @return queryResult
	 * @throws Exception 
	 */
	@RequestMapping("/queryRjAndZdl")
	public @ItemResponseBody QueryResultObject queryRjAndZdl(@RequestParam("contractId") String contractId,@RequestParam("startdate") String startdate,@RequestParam("enddate") String enddate)
			{
		try {
			QueryResultObject result = new QueryResultObject();// 新建返回值
			String val = "";
			List<String> list = new ArrayList<String>();
			if (contractId != null && !"".equals(contractId)
					&& startdate != null && !"".equals(startdate)
					&& enddate != null && !"".equals(enddate)) {
				int[] averger = bizc.getAvg(startdate, startdate, enddate);
				int avg = averger[0];
				list.add(avg + "");
				val = val + avg;
				int days = Integer.valueOf(getworktime(startdate, enddate) + 1);//@计算天数
				int summary = 0;
				summary = averger[0] * days;//@总量
				list.add(summary + "");
			} else {
				list.add("error");
			}
			result.setItems(list);
			return result;
		} catch (Exception e) {
			throw new RestClientException("方法执行异常");
		}
	}
	/**
	 * 获取合同电量信息
	 * @description 方法描述
	 * @return
	 * @author 张岩
	 * @date 2013-02-18
	 */
	@RequestMapping(value = "/getChartData",method = RequestMethod.GET)
	public @ItemResponseBody Object getChartData(@RequestParam String params) throws Exception{		
		
		try {
			Map map = JsonUtils.getObjectMapper().readValue(params, Map.class);
			//map = userInfoUtil.getLoginUserMarket(request,map);
			String contractId = map.get("contractId") == null ? "" : map.get(
					"contractId").toString(); //获取场景id
			String startDate = map.get("startDate") == null ? "" : map.get(
					"startDate").toString(); //获取开始日期
			String endDate = map.get("endDate") == null ? "" : map.get(
					"endDate").toString(); //获取结束日期
			return bizc.getChartXml(contractId, startDate, endDate);
		} catch (Exception e) {
			throw new RestClientException("方法执行异常");
		}
	}
	/**
	 * 计算天数差的函数，通用  
	 * @description 方法描述
	 * @return
	 * @author 张岩
	 * @date 2013-02-16
	 */
	public static int getworktime(String startdate, String enddate){ 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date startTime = null;
		Date endTime = null;
		try {
			endTime = dateFormat.parse(enddate);//将结束时间转化成date类型
			startTime = dateFormat.parse(startdate);//将开始时间转化成date类型
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int days = (int)((endTime.getTime() - startTime.getTime()) / (1000*60*60*24));//计算天数
		return days;//返回天数
	}
	
}
