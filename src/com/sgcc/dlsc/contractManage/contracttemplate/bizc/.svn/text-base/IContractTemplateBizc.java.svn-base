package com.sgcc.dlsc.contractManage.contracttemplate.bizc;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.sgcc.dlsc.entity.po.CoContemplatewithparam;
import com.sgcc.dlsc.entity.po.CoContractbaseinfo;

public interface IContractTemplateBizc
{
	public List getContractInfoByCode(String contractid);
	
	public  void excZip(String zipFileName, String[] fileNames) throws IOException;
	
	public CoContemplatewithparam findById(String objId);
	
	public List<String[]> findCoParamByCoModel(String objId);
	
	public List<String[]> findConfig(String tbn,String tb, String marketid, String objId);
	
	public CoContractbaseinfo findByInfoId(String objId);
	
	public List<String[]> findValueByParamsql(List<String[]> coparamList,String[][] str);
	
	public void saveupLoadFile(File filetemp,String id,String fileName,String marketid);
	
	public String getcontractName(String contractId);
	
	public String getSsaveModelPath(String tempId);
	
	public String isokdown(String tempId);
	
	public List<String[]> findValueByClientConf(List<String[]> findConfig, String tbs,String contractid,List<String[]> coparamList_temp);
	
	public List<String[]> findCoParamByCoModelall();
}
