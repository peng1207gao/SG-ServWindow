package com.sgcc.dlsc.contractManage.contracttemplate;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.sgcc.dlsc.commons.util.GetConfigValue;
import com.sgcc.dlsc.commons.util.Guid;
import com.sgcc.dlsc.commons.util.UserInfoUtil;
import com.sgcc.dlsc.contractManage.contracttemplate.bizc.IContractTemplateBizc;
import com.sgcc.dlsc.entity.po.CoContemplatewithparam;
import com.sgcc.dlsc.entity.po.CoContractbaseinfo;
import com.sgcc.uap.utils.ComponentFactory;
public class GetWordData {
	
	static GetConfigValue configValue = new GetConfigValue();
	private static String tempFile = configValue.getGloabalValue("EXPORT_PATH"); 
	IContractTemplateBizc bizc = (IContractTemplateBizc)ComponentFactory.getBean("ContractTemplateBizc");
	private String marketid=ContractTemplateController.marketid;
	private String userId=ContractTemplateController.userid;
	private CoContractbaseinfo coContractbaseinfo = new CoContractbaseinfo();	
	
	@SuppressWarnings("rawtypes")
	public List<String[]> queryContractInfo1(String idNum,String tempId,String objId)
	{
//		String objId = "";//合同编号
//		String[] contractids =null;// (String[]) ids.toArray();
		//查找模板对象
		CoContemplatewithparam po = bizc.findById(tempId);
//		CoContemplatewithparam coContemplatewithparam = contractTemplateBizc.findById(objId);
		if (po == null || po.getContracttemplatefile() == null) {//@判断模板对象是否存在
			String errMsg = "找不到模板对象或模板对象中没有文件";
			System.out.println(errMsg);
			return null;
		}
//		String[] wordTemps = new String[contractids.length];//word临时文件
//		String[] pdfTemps = new String[contractids.length];//pdf临时文件
//		//查询对应合同模版的合同参数
		List<String[]> coparamList = bizc.findCoParamByCoModel(po.getContracttemplatecode());
//		String marketid = UserInfoUtil.getLoginUserMarket(request);
//		String userId = UserInfoUtil.getLoginUserId(request);
//		//查询客户配置范本中名称与字段对应
		List<String[]> findConfig = bizc.findConfig("SGBIZ.CO_CONTRACTBASEINFO","CO_CONTRACTBASEINFO", marketid, po.getContracttemplatecode());
		String msg="";
//		String temp = "";
//		//是否到处pdf格式文档
//		if(idPdf.equals("doc")){
//			temp=tempFile + Guid.create() + ".doc";
//		}else{
//			//临时文件名称及路径，及模版文件
//			temp=tempFile + Guid.create() + ".pdf";
//		}
//		//遍历合同编号
//			//查询合同信息
			coContractbaseinfo = bizc.findByInfoId(objId);
			if (coContractbaseinfo == null) {
				String errMsg = "找不到合同信息";
				System.out.println(errMsg);
			}
			//关键值
			String[][] params = new String[][]{{"\\$contractid\\$",objId},{"\\$userid\\$",userId}};
			List<String[]> coparamList_temp = new ArrayList<String[]>();
			if (coparamList != null && coparamList.size() > 0) {
				//此时coparamList为Key，Value类型的数据集合
				coparamList_temp = bizc.findValueByParamsql(coparamList, params);
			}
			
			//查询客户配置及对应值，与下面位置不能换
			coparamList_temp = bizc.findValueByClientConf(findConfig, "SGBIZ.CO_CONTRACTBASEINFO", "CONTRACTID='"+objId+"'", coparamList_temp);
			
			String[] comTextKey = new String[coparamList_temp.size()];
			String[] comTextValue = new String[coparamList_temp.size()];
			for (int j = 0; j < coparamList_temp.size(); j++) {
				String[] key_value =coparamList_temp.get(j);
				comTextKey[j] = key_value[0];
				comTextValue[j] = key_value[1];
			}
		return coparamList_temp;
	}
	public String getContractName(String contractId){
		String contractName = bizc.getcontractName(contractId);
		return contractName;
	}
	
	public String isokdown(String tempId){
		return bizc.isokdown(tempId);
	}
	
	public String getSaveModelPath(String tempId){
		String path = bizc.getSsaveModelPath(tempId);
		return path;
	}
	/**
	 * 
	 * @description 方法描述
	 * @param idNum
	 * @param tempId
	 * @param objId
	 * @return
	 * @author xuzihu
	 * @date 2014-7-8
	 */
	@SuppressWarnings("rawtypes")
	public List<String[]> queryContractInfo(String idNum,String tempId,String objId)
	{
		CoContemplatewithparam po = bizc.findById(tempId);
		if (po == null || po.getContracttemplatefile() == null) {//@判断模板对象是否存在
			String errMsg = "找不到模板对象或模板对象中没有文件";
			System.out.println(errMsg);
			return null;
		}
		List<String[]> coparamList = bizc.findCoParamByCoModelall();
//		List<String[]> findConfig = bizc.findConfig("SGBIZ.CO_CONTRACTBASEINFO","CO_CONTRACTBASEINFO", marketid, po.getContracttemplatecode());
//		String msg="";
//			//查询合同信息
			coContractbaseinfo = bizc.findByInfoId(objId);
			if (coContractbaseinfo == null) {
				String errMsg = "找不到合同信息";
				System.out.println(errMsg);
			}
			//关键值
			String[][] params = new String[][]{{"\\$contractid\\$",objId},{"\\$userid\\$",userId}};
			List<String[]> coparamList_temp = new ArrayList<String[]>();
			if (coparamList != null && coparamList.size() > 0) {
				//此时coparamList为Key，Value类型的数据集合
				coparamList_temp = bizc.findValueByParamsql(coparamList, params);
			}
			
			//查询客户配置及对应值，与下面位置不能换
//			coparamList_temp = bizc.findValueByClientConf(findConfig, "SGBIZ.CO_CONTRACTBASEINFO", "CONTRACTID='"+objId+"'", coparamList_temp);
			
			String[] comTextKey = new String[coparamList_temp.size()];
			String[] comTextValue = new String[coparamList_temp.size()];
			for (int j = 0; j < coparamList_temp.size(); j++) {
				String[] key_value =coparamList_temp.get(j);
				comTextKey[j] = key_value[0];
				comTextValue[j] = key_value[1];
			}
		return coparamList_temp;
	}
}

