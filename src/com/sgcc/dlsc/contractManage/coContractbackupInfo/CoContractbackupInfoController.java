package com.sgcc.dlsc.contractManage.coContractbackupInfo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;

import com.sgcc.dlsc.commons.util.ComUtils;
import com.sgcc.dlsc.commons.util.ToolsSys;
import com.sgcc.dlsc.commons.util.UserInfoUtil;
import com.sgcc.dlsc.contractManage.CoContransEnergyInfo.bizc.ICoContransEnergyInfoBizc;
import com.sgcc.dlsc.contractManage.CoContransEnergyInfo.vo.CoContransenergyinfoVO;
import com.sgcc.dlsc.contractManage.IdeaConManage.vo.CoContractbaseinfoTransfer;
import com.sgcc.dlsc.contractManage.coContractbackupInfo.bizc.ICoContractbackupInfoBizc;
import com.sgcc.dlsc.contractManage.coContractbackupInfo.vo.CoContractbackupinfoTransfer;
import com.sgcc.dlsc.contractManage.coContractbackupInfo.vo.CoContractbackupinfoVO;
import com.sgcc.dlsc.entity.po.CoContractbackupinfo;
import com.sgcc.dlsc.entity.po.CoContractbaseinfo;
import com.sgcc.dlsc.entity.po.CoContractchangerecordinfo;
import com.sgcc.dlsc.entity.po.CoContransenergyinfo;
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
import com.sgcc.uap.rest.utils.ViewAttributeUtils;

@Controller
@RequestMapping("/cocontractbackupinfo") //根据po类名生成
public class CoContractbackupInfoController {
	
    @Autowired
    private ICoContractbackupInfoBizc coContractbackupInfoBizc;
	private ICoContransEnergyInfoBizc coContransEnergyInfoBizc;

    /**
	 * 输电方记录的删除
	 * @return true/false
	 * @throws Exception 
	 * @author xiabaike
	 */
	@RequestMapping(value = "/sdfDelete")
	public @ItemResponseBody List<String> sdfDelete(HttpServletRequest request) throws Exception{ 
		String checkedId = request.getParameter("checkedId");
		System.out.println("------checkedId:"+checkedId);
		int result = coContractbackupInfoBizc.sdfDelete(checkedId);
		System.out.println("----result:"+result);
		List<String> list = new ArrayList<String>();
		if(result == 1) {
			list.add("true");
		}else{
			list.add("false");
		}
		return list;
	}
    
    /**
	 * 合同终止
	 * @return true/false
	 * @throws Exception 
	 * @author xiabaike
	 */
	@RequestMapping(value = "/contractEnd")
	public @ItemResponseBody List<String> contractEnd(HttpServletRequest request) throws Exception{ 
		String checkedIds = request.getParameter("checkedId");
		String[] checkedId = checkedIds.split(",");
		try {
			for(int i=0;i<checkedId.length;i++){
				CoContractbaseinfo coContractbaseinfo = coContractbackupInfoBizc
						.findById(checkedId[i]);//@查询
				coContractbaseinfo.setIsend(new BigDecimal(1));
				String contractid = coContractbaseinfo.getContractid();//获得合同id
				CoContractchangerecordinfo ccc = new CoContractchangerecordinfo();//
				boolean a = ComUtils.obj2obj(coContractbaseinfo, ccc);
				ccc.setContractid(contractid);
				ccc.setMarketid(coContractbaseinfo.getMarketid());
				ccc.setCmd("3");
				ccc.setOperator(UserInfoUtil.getLoginUserMarket(request));
				ccc.setFlowflag(coContractbaseinfo.getFlowflag());
				ccc.setUpdatetime(new Date());
				ccc.setOperatedate(new Date(0));
				ccc.setOperatingdescription("合同终止");
				ccc.setOrderno(new BigDecimal("1"));
				coContractbaseinfo.setIsdelete(new BigDecimal(1));
				coContractbaseinfo.setFlowflag(new BigDecimal(6));
				coContractbackupInfoBizc.update(coContractbaseinfo);//更新
				System.out.println("-------------coContractbaseinfo:"
						+ coContractbaseinfo);
				if (!ToolsSys.isnull(ccc.getIsdelete() == null ? null : ccc
						.getIsdelete().toString())) {
					ccc.setIsdelete(new BigDecimal(0));
				}
				coContractbackupInfoBizc.save(ccc);
				System.out.println("---------ccc:" + ccc);
			}
			List<String> list = new ArrayList<String>();
			list.add("true");
			return list;
		} catch (Exception e) {
			throw new RestClientException("方法执行异常");
		}
//		return null;
	}
    
	/**
	 * 分解电量到机组
	 * @return true/false
	 * @throws Exception 
	 * @author xiabaike
	 */
	@RequestMapping(value = "/fj")
	public Object fj(HttpServletRequest request) throws Exception{ 
		System.out.println("-----fj-----");
		try {
			int num = 0;
			String selectId = request.getParameter("selectId");
			String marketid = UserInfoUtil.getLoginUserMarket(request);
			List list = coContractbackupInfoBizc.fjList(marketid);
			num = coContractbackupInfoBizc.fjByList(list);
			if (num > 0) {
				//setMsg("分解成功！");
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			throw new RestClientException("方法执行异常");
		}
	}
    
	/**
	 * 保存
	 * 请求体格式为:{"items":[{"id":"1","name":"1","sex":"1","age":"1"},{"id":"1","name":"1","sex":"1","age":"1"}]}
	 * @return voList
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ItemResponseBody 
	List<CoContransenergyinfoVO> save(@ItemsRequestBody List<Map> list){ 
		try{
			System.out.println("--------------"+list);
    		List<CoContransenergyinfoVO> voList = coContransEnergyInfoBizc.saveOrUpdate(list);
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
			coContractbackupInfoBizc.remove(idObject);
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
			QueryResultObject queryResult = coContractbackupInfoBizc
					.query(params);
			List<CoContractbackupinfo> result = queryResult.getItems();
			List<CoContractbackupinfoVO> voList = new ArrayList<CoContractbackupinfoVO>();
			for (int i = 0; i < result.size(); i++) {
				CoContractbackupinfo po = (CoContractbackupinfo) result.get(i);
				CoContractbackupinfoVO vo = CoContractbackupinfoTransfer
						.toVO(po);
				voList.add(vo);
			}
			queryResult.setItems(voList);
			return queryResult;
		} catch (Exception e) {
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
	QueryResultObject query(@PathVariable String id,HttpServletRequest request) throws Exception {
		String marketId = UserInfoUtil.getLoginUserMarket(request);
		try {
			return coContractbackupInfoBizc.queryById(id, marketId);
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
		datas = ViewAttributeUtils.getViewAttributes(columns, CoContractbackupinfoVO.class);
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
					CoContractbackupinfoVO.class);
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
	 * 注入逻辑构件
	 * @param coContractbackupInfoBizc
	 */
	public void setCoContractbackupInfoBizc(ICoContractbackupInfoBizc coContractbackupInfoBizc) {
		this.coContractbackupInfoBizc = coContractbackupInfoBizc;
	}
}
