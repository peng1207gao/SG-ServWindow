package com.sgcc.dlsc.contractManage.coContractCopy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;

import com.sgcc.dlsc.commons.util.ComUtils;
import com.sgcc.dlsc.commons.util.UserInfoUtil;
import com.sgcc.dlsc.contractManage.coContractCopy.bizc.ICoContractCopyBizc;
import com.sgcc.dlsc.contractManage.coContractbaseinfoV2Act.vo.CoContractbaseinfoVO;
import com.sgcc.dlsc.entity.po.CoTransqtyinfo;
import com.sgcc.dlsc.povo.guide.vo.LoginVo;
import com.sgcc.isc.core.orm.identity.User;
import com.sgcc.uap.rest.annotation.ColumnRequestParam;
import com.sgcc.uap.rest.annotation.ColumnResponseBody;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.QueryRequestParam;
import com.sgcc.uap.rest.annotation.attribute.ViewAttributeData;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;
import com.sgcc.uap.rest.utils.ViewAttributeUtils;

@Controller
@RequestMapping("/coContractCopy")
public class CoContractCopyController {

	@Autowired
	private ICoContractCopyBizc bizc;

	private String contracttype1 = "'4DC24C12-413C-5A67-BEBE-AB07C0C','38F71C1D-DAD0-23E4-A7DF-69AD9EF'";// 发电权合同，跨区发电权短期交易
	private String id;// @id
	private String name;// @名称
	private String ctype;// @类型
	private String purchaserunit;// @购电方机组
	private String sellerunit;// @售电方机组
	private String sdate;// 开始时间
	private String edate;// 结束时间
	private String contractenergy;// 合同电量
	private String price;// 电价
	private String bs;// @报送
	private String ss;// @输送
	private String flowf;// @流程标志
	private String pf;// @批复
	private String updatepersonid;// @更新人id
	private String updatetime;// @更新时间
	private String updatemarket;// 更新人的marketid
	private String download;// 下载
	private String contracttemplateid;// 合同范本
	private String startDate;// 开始时间-
	private String endDate;// 结束时间-
	private String contractcyc;// 合同周期-
	private String signstate;// 签订状态
	private String backupstate;// 备案状态
	private String prepcontractflag;// 合同状态-
	private String purchaser;// 购电方-
	private String seller;// 售电方-
	// private String transer;//输电方
	private String isend;// 是否终止，流转状态
	private String searchDateType;// 查询时间类型
	private String contractid;
	private String sequenceid;
	private String appopinion;// 审批意见
	private String flowflag;
	private String contracttype;// 合同类型-
	private String contracttypeid;// 合同类型-
	private String contractName; // 合同名称
	private String papercontractcode;// 纸质合同编号
	private String syscontractcode;// 系统合同编号
	private String marketId;// 登陆人的场景id
	private String marketid;// 页面选择的业务场景id

	/**
	 * 给变量赋值
	 * 
	 * @param params2
	 * @param i
	 * @return
	 */
	private String setParam(String[] params2, int i) {
		return params2[i].split("=").length > 1 ? params2[i].split("=")[1] : "";
	}

	/**
	 * 将RequestCondition中的参数拿出来
	 */
	private void setParamByParams(RequestCondition params) {
		String[] params2 = params.getFilter().toString().split("&");// 把params里边的查询条件分出来
		int i = 0; // 获取params2的索引位置
		// 给各个变量赋值
		contracttype = setParam(params2, i++);		//合同类型
		contractcyc = setParam(params2, i++);		//合同周期
		searchDateType = setParam(params2, i++);	//时间类型
		startDate = setParam(params2, i++);			//开始时间
		endDate = setParam(params2, i++);			//结束时间
		prepcontractflag = setParam(params2, i++);	//合同状态
		purchaser = setParam(params2, i++);			//够电方
		seller = setParam(params2, i++);			//售电方
		marketid = setParam(params2, i++);			//业务场景
		sequenceid = setParam(params2, i++);		//合同序列
		marketId = setParam(params2, i++);			//登陆人的 业务场景
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
	public @ItemResponseBody QueryResultObject query(@QueryRequestParam("params") RequestCondition params)
			throws JsonProcessingException, IOException {
		try {
			QueryResultObject result = new QueryResultObject();// 新建返回值
			setParamByParams(params); // 将RequestCondition中的参数拿出来
			result = bizc.findCoContractList11(
					Integer.parseInt(params.getPageIndex()),
					Integer.parseInt(params.getPageSize()), marketid,
					contracttype, searchDateType, startDate, endDate,
					contractcyc, prepcontractflag, purchaser, seller,
					sequenceid, marketId); // 获取合同管理展示数据
			return result;
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
	@RequestMapping("/copyContract")
	public @ItemResponseBody QueryResultObject copyContract(@RequestParam("contractId") String contractId,@RequestParam("newContractId") String newContractId,@RequestParam("marketId") String marketId,HttpServletRequest request)
			throws Exception {
		try {
			QueryResultObject result = new QueryResultObject();// 新建返回值
			//		LoginVo vo=UserInfoUtil.getLoginInfo(request);//获取登陆人场景
			User ur = UserInfoUtil.getLoginUser(request);//获取登陆人
			String str = bizc.copyContract(contractId, marketId, ur.getName(),
					newContractId);
			List<String> list = new ArrayList<String>();
			list.add(str);
			result.setItems(list);
			return result;
		} catch (Exception e) {
			throw new RestClientException("方法执行异常");
		}
	}
	
	/**
	 * 注入逻辑构件
	 * @param bizc
	 */
	public void setCoContractCopyBizc(ICoContractCopyBizc bizc){
		this.bizc=bizc;
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
	
	
}
