package com.sgcc.dlsc.contractManage.CoContractFjAct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.lob.SerializableBlob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;

import com.sgcc.dlsc.commons.util.DateUtil;
import com.sgcc.dlsc.commons.util.UserInfoUtil;
import com.sgcc.dlsc.contractManage.CoContractFjAct.bizc.ICoContractFjActBizc;
import com.sgcc.dlsc.contractManage.CoContractFjAct.vo.CoContractaffixinfoTransfer;
import com.sgcc.dlsc.contractManage.CoContractFjAct.vo.CoContractaffixinfoVO;
import com.sgcc.dlsc.entity.po.CoContractaffixinfo;
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
import com.sgcc.uap.rest.utils.ViewAttributeUtils;

@Controller
@RequestMapping("/cocontractaffixinfo") //根据po类名生成
public class CoContractFjActController {
	
    @Autowired
    private ICoContractFjActBizc coContractFjActBizc;
	
    
	/**
	 * 校验上传文件是否大于10M
	 * @return voList
	 */
	@RequestMapping(value = "/vaildFilesize", method = RequestMethod.GET)
	public @RawResponseBody Object vaildFilesize(@ItemRequestParam("params") String itemType,HttpServletRequest request){ 
		
		try{
			String flag = "true";
			List list  = coContractFjActBizc.getFile(itemType);
			if(list!=null){
				if(list.size()>0){
					
					//coContractaffixinfo = (CoContractaffixinfo)list.get(0);
					//byte[] byteArray = coContractaffixinfo.getPapercontractfile();
					Object size = (Object) list.get(0);
					if(size!=null){
						if(Integer.parseInt(size.toString()) > 10*1024*1024){
							
							flag = "false";
						}
					}
					
				}
			}
			return flag;
		}catch(Exception e){
			throw new RestClientException("上传失败！");
		}
		
	}
	
	/**
	 * 校验文件上传上传文件是否大于10M
	 * @return voList
	 */
	@RequestMapping(value = "/down", method = RequestMethod.GET)
	public @RawResponseBody Object down(HttpServletRequest request, HttpServletResponse response){ 
		
		try{
			String flag = "true";
			String guid = request.getParameter("guid");
			flag  = coContractFjActBizc.downAffix(guid,request,response);
			if(flag.equals("false")){
				throw new RestClientException("下载失败！");
			}
			return flag;
		}catch(Exception e){
			throw new RestClientException("下载失败！");
		}
		
	}
    

	
	/**
	 * 保存
	 * 请求体格式为:{"items":[{"id":"1","name":"1","sex":"1","age":"1"},{"id":"1","name":"1","sex":"1","age":"1"}]}
	 * @return voList
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ItemResponseBody 
	List<CoContractaffixinfoVO> save(@ItemsRequestBody List<Map> list,HttpServletRequest request){ 
		try{
			String contractid = "";
			if(list!=null&&list.size()>0){
				contractid = list.get(0).get("contractid").toString();
			}
			String marketId = UserInfoUtil.getLoginUserMarket(request);
			String username = UserInfoUtil.getLoginUser(request).getUserName();
			Date createDate = DateUtil.getUtilDate(DateUtil.getDateTime(), "yyyy-MM-dd HH:mm:ss");
			int affixno = coContractFjActBizc.getCount(contractid);
			//BigDecimal.valueOf(Double.valueOf(affixno))
			
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					list.get(i).put("marketid", marketId);
					list.get(i).put("affixno", affixno);
					list.get(i).put("uploadperson", username);
					list.get(i).put("uploadtime", createDate);
					//list.get(i).remove("mxVirtualId");
				}
			}
    		List<CoContractaffixinfoVO> voList = coContractFjActBizc.saveOrUpdate(list);
    		
    		return voList;
		}catch(Exception e){
			throw new RestClientException("保存方法异常");
		}
	}
	
//	List<CoContractaffixinfoVO> save1(@ItemsRequestBody List<Map> list,HttpServletRequest request){ 
//		try{
//			String contractid = "1";
//			CoContractaffixinfoVO coContractaffixinfo = new CoContractaffixinfoVO();
//			UserInfoUtil.getLoginUserMarket(request);
//			coContractaffixinfo.setMarketid(UserInfoUtil.getLoginUserMarket(request));//@设置场景id
//			coContractaffixinfo.setUploadperson(UserInfoUtil.getLoginUser(request).getUserName());
//			coContractaffixinfo.setUploadtime(new Date(0));//@设置上传时间
//			int affixno = coContractFjActBizc.getCount(contractid);
//			coContractaffixinfo.setAffixno(BigDecimal.valueOf(Double.valueOf(affixno)));
//			//coContractaffixinfo.setContractid(contractid);
//    		//return voList;
//		}catch(Exception e){
//			throw new RestClientException("保存方法异常",e);
//		}
//	}
//	
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
			coContractFjActBizc.remove(idObject);
			return null;
		} catch (Exception e) {
			throw new RestClientException("程序异常");
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
	@RequestMapping("/")
	public @ItemResponseBody
	QueryResultObject query(@QueryRequestParam("params") RequestCondition params) {
		
		QueryResultObject result;//定义返回值
		String contractid = "";
		String[] params2 = params.getFilter().toString().split("&");
		if(params2!=null){
			contractid = params2[0].split("=").length > 1 ? params2[0].split("=")[1] : "";
		}
		try {
			result = coContractFjActBizc.findAffList(Integer.parseInt(params.getPageIndex()), Integer.parseInt(params.getPageSize()), contractid);
			return result;
		} catch (Exception e) {
			throw new RestClientException("程序异常");
		}
		
//		QueryResultObject queryResult = coContractFjActBizc.query(params);
//		List<CoContractaffixinfo> result = queryResult.getItems();
//		
//		List<CoContractaffixinfoVO> voList = new ArrayList<CoContractaffixinfoVO>();
//		for(int i = 0;i < result.size();i++){
//			CoContractaffixinfo po = (CoContractaffixinfo)result.get(i);
//			CoContractaffixinfoVO vo = CoContractaffixinfoTransfer.toVO(po);
//			voList.add(vo);
//		}
//		
//		queryResult.setItems(voList);
//		return queryResult;
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
			return coContractFjActBizc.queryById(id);
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
		datas = ViewAttributeUtils.getViewAttributes(columns, CoContractaffixinfoVO.class);
		ViewMetaData metaData= new ViewMetaData();
		metaData.setColumns(datas);
		IPermissionControl permControl = new PermissionControl();
		metaData = permControl.addPermInfoToMetaData(iscID, metaData);
		return WrappedResult.successWrapedResult(metaData);
	}
	/**
	 * 注入逻辑构件
	 * @param coContractFjActBizc
	 */
	public void setCoContractFjActBizc(ICoContractFjActBizc coContractFjActBizc) {
		this.coContractFjActBizc = coContractFjActBizc;
	}
}
