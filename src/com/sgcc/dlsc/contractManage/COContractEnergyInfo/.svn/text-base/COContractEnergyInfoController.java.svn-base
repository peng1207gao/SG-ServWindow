package com.sgcc.dlsc.contractManage.COContractEnergyInfo;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.rmi.CORBA.Tie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;

import com.sap.conn.jco.support.SimpleDataProviderImpl;
import com.sgcc.dlsc.commons.util.DateUtil;
import com.sgcc.dlsc.commons.util.ToolsSys;
import com.sgcc.dlsc.commons.util.UserInfoUtil;
import com.sgcc.dlsc.contractManage.COContractEnergyInfo.bizc.ICOContractEnergyInfoBizc;
import com.sgcc.dlsc.contractManage.COContractEnergyInfo.vo.CoContractenergyinfoTransfer;
import com.sgcc.dlsc.contractManage.COContractEnergyInfo.vo.CoContractenergyinfoVO;
import com.sgcc.dlsc.entity.po.CoContractenergyinfo;
import com.sgcc.uap.integrate.isc.service.IPermissionControl;
import com.sgcc.uap.integrate.isc.service.PermissionControl;
import com.sgcc.uap.rest.annotation.ColumnRequestParam;
import com.sgcc.uap.rest.annotation.ColumnResponseBody;
import com.sgcc.uap.rest.annotation.IdRequestBody;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.ItemsRequestBody;
import com.sgcc.uap.rest.annotation.QueryRequestParam;
import com.sgcc.uap.rest.annotation.RawResponseBody;
import com.sgcc.uap.rest.annotation.TreeResponseBody;
import com.sgcc.uap.rest.annotation.VoidResponseBody;
import com.sgcc.uap.rest.annotation.attribute.ViewAttributeData;
import com.sgcc.uap.rest.support.IDRequestObject;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;
import com.sgcc.uap.rest.support.TreeNode;
import com.sgcc.uap.rest.support.ViewMetaData;
import com.sgcc.uap.rest.support.WrappedResult;
import com.sgcc.uap.rest.utils.JsonUtils;
import com.sgcc.uap.rest.utils.ViewAttributeUtils;

@Controller
@RequestMapping("/cocontractenergyinfo") //根据po类名生成
public class COContractEnergyInfoController {
	
    @Autowired
    private ICOContractEnergyInfoBizc cOContractEnergyInfoBizc;
	
	/**
	 * 保存
	 * 请求体格式为:{"items":[{"id":"1","name":"1","sex":"1","age":"1"},{"id":"1","name":"1","sex":"1","age":"1"}]}
	 * @return voList
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ItemResponseBody 
	List<CoContractenergyinfoVO> save(@ItemsRequestBody List<Map> list,HttpServletRequest request){ 
		try{
//			String timeno = list.get(0).get("timeno")==null?"":list.get(0).get("timeno").toString();
//			String startdate = list.get(0).get("startdate")==null?null:list.get(0).get("startdate").toString();
//			String clickstime = list.get(0).get("clickstime")==null?"":list.get(0).get("clickstime").toString();
//			String contractid = list.get(0).get("contractid")==null?null:list.get(0).get("contractid").toString();
//			if(timeno.equals("1") ||(!timeno.equals("1") && (!startdate.equals(clickstime))))
//			{
//				timeno = cOContractEnergyInfoBizc.nextTimeNo(contractid);
//			}
//			CoContractenergyinfoVO co = new CoContractenergyinfoVO();
			//co.setTimeno(new BigDecimal(timeno));
//			list.get(0).remove("timeno");
//			list.get(0).put("timeno", timeno);
			//String timeNo = cOContractEnergyInfoBizc.nextTimeNo("");
//			list.get(0).remove("clickstime");
    		List<CoContractenergyinfoVO> voList = cOContractEnergyInfoBizc.saveOrUpdate(list,request);
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
			cOContractEnergyInfoBizc.remove(idObject);
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
	QueryResultObject query(@QueryRequestParam("params") RequestCondition params, HttpServletRequest request) {
		try {
			String strs = (String) params.getFilter();
			String[] sl = strs.split("&");
			String contractid;
			String startdate = "";
			String enddate = "";
			if (sl.length > 1) {
				String sdate = sl[1].split("=")[1];
				String[] sd = sdate.split("-");
				startdate = sd[0] + "/" + sd[1] + "/" + sd[2] + " 00:00:00";
				String edate = sl[2].split("=")[1];
				String[] ed = edate.split("-");
				enddate = ed[0] + "/" + ed[1] + "/" + ed[2] + " 23:59:59";
				//			params.setFilter(sl[0]+"&"+startdate+"&"+enddate);
				contractid = sl[0].split("=")[1];
			} else {

				contractid = strs.split("=")[1];
			}
			QueryResultObject queryResult = cOContractEnergyInfoBizc.query(
					Integer.parseInt(params.getPageIndex()),
					Integer.parseInt(params.getPageSize()), contractid,
					startdate, enddate);
			return queryResult;
		} catch (Exception e) {
			throw new RestClientException("方法执行异常");
		}
	}
	String[] params = null;
	String period = null;
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
			return cOContractEnergyInfoBizc.queryById(id, params, period);
		} catch (Exception e) {
			throw new RestClientException("方法执行异常");
		}
	}
	
	/**
	 * 参数传递
	 * @description 方法描述
	 * @param startdate
	 * @param enddate
	 * @param contractid
	 * @param perd
	 * @return
	 * @author liling
	 * @date 2014-3-6
	 */
	@RequestMapping("/getParam")
	public @RawResponseBody
	Object getParam(@RequestParam String startdate,@RequestParam String enddate,String contractid,String perd) {
		try {
			String sum = "0";
			if (ToolsSys.isnull(startdate) && !"null".equals(startdate)
					&& ToolsSys.isnull(enddate) && !"null".equals(enddate)
					&& ToolsSys.isnull(contractid)
					&& !"null".equals(contractid)) {
				params = new String[] { contractid, startdate, enddate };
			} else {
				params = null;
				period = null;
			}
			if (ToolsSys.isnull(perd) && !"null".equals(perd)) {
				period = perd;
				if ("1".equals(period)) {//为总时
					sum = cOContractEnergyInfoBizc.querySumEnergy(params);
				}
			}
			return sum;
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
		datas = ViewAttributeUtils.getViewAttributes(columns, CoContractenergyinfoVO.class);
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
		datas = ViewAttributeUtils.getViewAttributes(columns, CoContractenergyinfoVO.class);
		ViewMetaData metaData= new ViewMetaData();
		metaData.setColumns(datas);
		IPermissionControl permControl = new PermissionControl();
		metaData = permControl.addPermInfoToMetaData(iscID, metaData);
		return WrappedResult.successWrapedResult(metaData);
	}
	/**
	 * 注入逻辑构件
	 * @param cOContractEnergyInfoBizc
	 */
	public void setCOContractEnergyInfoBizc(ICOContractEnergyInfoBizc cOContractEnergyInfoBizc) {
		this.cOContractEnergyInfoBizc = cOContractEnergyInfoBizc;
	}
	
	/**
	 * 获取合同购售电量电价信息
	 * @description 方法描述
	 * @param request
	 * @return
	 * @author yanjiangfeng
	 * @date 2014-2-12
	 */
	@RequestMapping(value = "/getCOContractEnergyTree", method = RequestMethod.GET)
	public @TreeResponseBody
	List<TreeNode> getRoot(@RequestParam("type") String type, HttpServletRequest request) {
		try {
			List list = cOContractEnergyInfoBizc.getCOContractEnergyTree(type);
			TreeNode pnode = new TreeNode();
			pnode.setHasChildren(false);
			pnode.setItemType("0");
			List<TreeNode> pnodelist = new ArrayList<TreeNode>();
			List<TreeNode> nodelist = new ArrayList<TreeNode>();
			if (list != null && list.size() > 0) {
				pnode.setHasChildren(true);
				Object[] o = (Object[]) list.get(0);
				Date ssdate = DateUtil.getUtilDate(o[1].toString(),
						"yyyy-MM-dd");
				Date sedate = DateUtil.getUtilDate(o[2].toString(),
						"yyyy-MM-dd");
				for (int i = 0; i < list.size(); i++) {
					TreeNode node = new TreeNode();
					Object[] obj = (Object[]) list.get(i);
					if (obj[3] != null) {
						BigDecimal bd = new BigDecimal(obj[3].toString())
								.setScale(4);
						node.setId(obj[0].toString() + "&" + bd.toString()); //主键的get方法
					} else {
						node.setId(obj[0].toString()); //主键的get方法
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
				SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
				String ssdatestr = sd.format(ssdate);
				String sedatestr = sd.format(sedate);
				pnode.setText(ssdatestr + "至" + sedatestr);
				pnode.setChildNodes(nodelist);
			} else {
				pnode.setText("无时间段信息");
			}
			pnodelist.add(pnode);
			return pnodelist;
		} catch (Exception e) {
			throw new RestClientException("方法执行异常");
		}
	}
	@RequestMapping("/init")
	public @ItemResponseBody List<String> contractEnd(HttpServletRequest request) throws Exception{ 
		String checkedId = request.getParameter("contractid");
		List  list= cOContractEnergyInfoBizc.getCOContractEnergyInit(checkedId);
		return list;
	}
	
	/**
	 * 
	 * @description 获取该月的尖峰平谷四种电量的总和
	 * @param params
	 * @return
	 * @author mengke
	 * @date 2014-6-11
	 */
	@RequestMapping("/getMonthEnergy")
	public @ItemResponseBody Object getMonthEnergy(@RequestParam String params){
		Map map = new HashMap();
		String monthEnergy = "0";
		try {
			map = JsonUtils.getObjectMapper().readValue(params, Map.class);
		} catch (Exception e) {
			// TODO: handle exception
		}
		List list = cOContractEnergyInfoBizc.getMonthEnergy(map);
		if(list!=null&&list.size()>0){
			monthEnergy = (list.get(0)==null||list.get(0).equals("null"))?"0":list.get(0).toString();
		}
		return monthEnergy;
	}
	
	@RequestMapping("/getMonthTotalEnergy")
	public @ItemResponseBody Object getMonthTotalEnergy(@RequestParam String params){
		String totalEnergy = "0";
		Map map = new HashMap();
		try {
			map = JsonUtils.getObjectMapper().readValue(params, Map.class);
		} catch (Exception e) {
			// TODO: handle exception
		}
		List list = cOContractEnergyInfoBizc.getMonthTotalEnergy(map);
		if(list!=null&&list.size()>0){
			totalEnergy = (list.get(0)==null||list.get(0).equals("null"))?"0":list.get(0).toString();
		}
		return totalEnergy;
	}
}
