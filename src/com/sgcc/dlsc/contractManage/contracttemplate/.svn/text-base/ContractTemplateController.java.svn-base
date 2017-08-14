package com.sgcc.dlsc.contractManage.contracttemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;

import com.sgcc.dlsc.commons.util.DateUtil;
import com.sgcc.dlsc.commons.util.GetConfigValue;
import com.sgcc.dlsc.commons.util.Guid;
import com.sgcc.dlsc.commons.util.UserInfoUtil;
import com.sgcc.dlsc.contractManage.contractrecordinformation.bizc.IContractrecordinformationBizc;
import com.sgcc.dlsc.contractManage.contracttemplate.bizc.IContractTemplateBizc;
import com.sgcc.dlsc.entity.po.CoContemplatewithparam;
import com.sgcc.dlsc.entity.po.CoContractbaseinfo;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.utils.JsonUtils;
import com.sgcc.uap.utils.ComponentFactory;

@Controller
@RequestMapping("/contracrtTemplate")
public class ContractTemplateController 
{	
	static GetConfigValue configValue = new GetConfigValue();
	@Autowired
	private IContractTemplateBizc contractTemplateBizc;
//	public static  String tempId =new String();//合同模版id
//	public static  List ids =new ArrayList();//合同id数组
	public static String marketid;
	public static String userid;
	public static List wordNames =new ArrayList();//存放生成的文件名称
	public String zipFilePath ;
	private String tempId1;
	private List ids1;
	// 临时文件路径
	private static String tempFile = configValue.getGloabalValue("EXPORT_PATH"); 
	private CoContractbaseinfo coContractbaseinfo = new CoContractbaseinfo();
	
	
	public String getTempId1() {
		return tempId1;
	}

	public List getIds1() {
		return ids1;
	}

	public void setContractTemplateBizc(IContractTemplateBizc contractTemplateBizc) {
		this.contractTemplateBizc = contractTemplateBizc;
	}

	public static List getWordNames() {
		return wordNames;
	}

	public static void setWordNames(List wordNames) {
		ContractTemplateController.wordNames = wordNames;
	}

	@RequestMapping(value = "/clearList")
	public @ItemResponseBody Object clearList(HttpServletRequest request,HttpServletResponse response) throws FileNotFoundException {
		ContractTemplateController.wordNames.clear();
		return null;

    }
	//saveAsDocOrPdf
	@RequestMapping(value = "/saveAsDocOrPdf")
	public @ItemResponseBody Object saveAsDocOrPdf(HttpServletRequest request,HttpServletResponse response) throws Exception {
		System.out.println(wordNames.size()+"********************************");
		String zipFileName = wordNames.get(0).toString();
		String docOrPdfFileName=tempFile+wordNames.get(0).toString();
		try {
			zipFileName = new String(zipFileName.getBytes("GBK"),"ISO8859_1");//@设置压缩文件名
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		  InputStream inStream = new FileInputStream(docOrPdfFileName);// 文件的存放路径
	        // 设置输出的格式
	        response.reset();
	        response.setContentType("bin");
	        response.addHeader("Content-Disposition", "attachment; filename=\"" + zipFileName + "\"");
	        // 循环取出流中的数据
	        byte[] b = new byte[100];
	        int len;
	        try {
	        	OutputStream os= response.getOutputStream();
	            while ((len = inStream.read(b)) > 0){
	                os.write(b, 0, len);
	            }
	            os.flush();
	            os.close();
	            inStream.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        for(int i=0;i<wordNames.size();i++){
				delTempFile(tempFile+wordNames.get(i).toString());
			}
		return null;

    }
	@RequestMapping(value = "/saveAsZip")
	public @ItemResponseBody Object saveAsZip(HttpServletRequest request,HttpServletResponse response) throws Exception {
		System.out.println(wordNames.size()+"#####################################");
		String zipFileName = "合同文本_" + Guid.create()+".zip";//压缩文件名
		zipFilePath = tempFile + zipFileName;//压缩路径
		String[] strs = new String[wordNames.size()];
		for(int i=0;i<wordNames.size();i++){
			strs[i]=tempFile+wordNames.get(i).toString();
		}
		try {
			contractTemplateBizc.excZip(zipFilePath, strs);//执行压缩
		} catch (Exception e3) {
			String errMsg = "压缩文件出现异常，error info：\n\t";
			e3.printStackTrace();
		}
		
		try {
			zipFileName = new String(zipFileName.getBytes("GBK"),"ISO8859_1");//@设置压缩文件名
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		response.setContentType("application/x-msdownload");//@设置内容格式
		response.addHeader("Content-Disposition", "attachment; filename=\""+ zipFileName + "\"");
		//文件输入流
		FileInputStream istream = null;
		//文件输出流
		ServletOutputStream servletOS = null;
		
		//得到输出流
		try {
			istream = new FileInputStream(new File(zipFilePath));
			/* 创建输出流 */
			byte[] buf = new byte[4096];
			servletOS = response.getOutputStream();
			int readLength;
			while (((readLength = istream.read(buf)) != -1)) {
				servletOS.write(buf, 0, readLength);
			}
			servletOS.close();
			istream.close();
		} catch (SocketException e2) {   
			servletOS.close();
			istream.close();
		} catch (Exception e) {
			servletOS.close();
			istream.close();
			e.printStackTrace();
		}
		for(int i=0;i<wordNames.size();i++){
			delTempFile(tempFile+wordNames.get(i).toString());
		}
		delTempFile(zipFilePath);
		return null;

    }
	
//	@RequestMapping(value = "/deleteFile")
//	public @ItemResponseBody Object deleteFile(HttpServletRequest request,HttpServletResponse response) throws FileNotFoundException {
//		for(int i=0;i<wordNames.size();i++){
//			delTempFile(tempFile+wordNames.get(i).toString());
//		}
//		return null;
//    }
	public boolean delTempFile(String tempFile) {
		File filetemp = new File(tempFile);
		if (filetemp.exists() && filetemp.isFile()) {//@判断文件是否存在
			filetemp.delete();
			System.out.println("删除文件"+tempFile+"成功~");
			return true;
		} else {
			System.out.println("文件不存在~");
			return false;
		}
	}
	@RequestMapping(value = "/upLoadFile")
	public @ItemResponseBody Object upLoadFile(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String marketid = UserInfoUtil.getLoginUserMarket(request);
		for(int i=0;i<ids1.size();i++){
			String id = ids1.get(i).toString();
			File filetemp = new File(tempFile+wordNames.get(i).toString());
			String fileName = wordNames.get(i).toString();
			contractTemplateBizc.saveupLoadFile(filetemp,id,fileName,marketid);
		}
//		
		for(int i=0;i<wordNames.size();i++){
			delTempFile(tempFile+wordNames.get(i).toString());
		}
		return null;
    }
	
	@RequestMapping(value = "/changeContractFile")
	public @ItemResponseBody Object downloadLocal(String idPdf,HttpServletResponse response,HttpServletRequest request) throws Exception {
		String objId = "";//合同编号
		String[] contractids =null;// (String[]) ids.toArray();
		//查找模板对象
		CoContemplatewithparam po = contractTemplateBizc.findById(objId);
//		CoContemplatewithparam coContemplatewithparam = contractTemplateBizc.findById(objId);
		if (po == null || po.getContracttemplatefile() == null) {//@判断模板对象是否存在
			String errMsg = "找不到模板对象或模板对象中没有文件";
			System.out.println(errMsg);
			return null;
		}
		String[] wordTemps = new String[contractids.length];//word临时文件
		String[] pdfTemps = new String[contractids.length];//pdf临时文件
//		//查询对应合同模版的合同参数
		List<String[]> coparamList = contractTemplateBizc.findCoParamByCoModel(po.getContracttemplatecode());
		String marketid = UserInfoUtil.getLoginUserMarket(request);
		String userId = UserInfoUtil.getLoginUserId(request);
//		//查询客户配置范本中名称与字段对应
		List<String[]> findConfig = contractTemplateBizc.findConfig("SGBIZ.CO_CONTRACTBASEINFO","CO_CONTRACTBASEINFO", marketid, po.getContracttemplatecode());
		String msg="";
		String temp = "";
		//是否到处pdf格式文档
		if(idPdf.equals("doc")){
			temp=tempFile + Guid.create() + ".doc";
		}else{
			//临时文件名称及路径，及模版文件
			temp=tempFile + Guid.create() + ".pdf";
		}
//		//遍历合同编号
		for (int i = 0; i < contractids.length; i++) {
			objId = contractids[i];//遍历合同ID
//			//查询合同信息
			coContractbaseinfo = contractTemplateBizc.findByInfoId(objId);
			if (coContractbaseinfo == null) {
				String errMsg = "找不到合同信息";
				System.out.println(errMsg);
				continue;
			}
			//关键值
			String[][] params = new String[][]{{"\\$contractid\\$",objId},{"\\$userid\\$",userId}};
			List<String[]> coparamList_temp = new ArrayList<String[]>();
			if (coparamList != null && coparamList.size() > 0) {
				//此时coparamList为Key，Value类型的数据集合
				coparamList_temp = contractTemplateBizc.findValueByParamsql(coparamList, params);
			}
			
			//查询客户配置及对应值，与下面位置不能换
			coparamList_temp = contractTemplateBizc.findValueByClientConf(findConfig, "SGBIZ.CO_CONTRACTBASEINFO", "CONTRACTID='"+objId+"'", coparamList_temp);
			
			String[] comTextKey = new String[coparamList_temp.size()];
			String[] comTextValue = new String[coparamList_temp.size()];
			for (int j = 0; j < coparamList_temp.size(); j++) {
				String[] key_value =coparamList_temp.get(j);
				comTextKey[j] = key_value[0];
				comTextValue[j] = key_value[1];
			}
//			//临时文件名称及路径
////			String temp = tempFile + (ToolsSys.isnull(coContractbaseinfo.getContractname())?coContractbaseinfo.getContractname():"未命名合同") + "_" + Guid.create().substring(10) + ".doc";
////			log.info("临时文件：{}", temp);
////			ExpWordMsg expWordMsg = null;
////			String pdfPath = temp.substring(0, temp.lastIndexOf("."))+".pdf";
////			log.info("合同pdf格式文档路径：{}", pdfPath);
////			//将模板读到本地并执行文件替换
////			expWordMsg = wordServiceMng.getContractDoc2Pdf(coContemplatewithparam, temp, pdfPath, comTextKey, comTextValue);
////			log.info(expWordMsg.toString());
//			String pdfPath = tempFile + coContractbaseinfo.getContractname() + "_" + Guid.create(8) + ".pdf";
//			String coFileName = (ToolsSys.isnull(coContractbaseinfo.getContractname())?coContractbaseinfo.getContractname():"未命名合同");
//			//文档名称
//			ExpWordMsg expWordMsg = null;
//			if("pdf".equals(isPdf)){
//				coFileName = coFileName + ".pdf";
//				expWordMsg = wordServiceMng.getContractDoc2Pdf(coContemplatewithparam, temp, pdfPath, comTextKey, comTextValue);
//			}else{
//				coFileName = coFileName + ".doc";
//				pdfPath = tempFile + coContractbaseinfo.getContractname() + "_" + Guid.create(8) + ".doc";
//				expWordMsg = wordServiceMng.getContractDoc2Doc97(coContemplatewithparam, temp, pdfPath, comTextKey, comTextValue);
//			}
//			log.info("合同文本文档路径：{}", pdfPath);
//			if (!expWordMsg.getSuccess()) {
//				String errMsg = "数据库模板读取本地和文本替换异常！\n\t";
//				log.warn(errMsg);
//				continue ;
//			}
//			wordTemps[i] = expWordMsg.getPath();//记录替换成功的word
//			pdfTemps[i] = pdfPath;//记录转换成功的pdf
//			
//			CoContractaffixinfo coContractaffixinfo = new CoContractaffixinfo();
//			coContractaffixinfo.setContractid(contractid);//@设置合同id
//			coContractaffixinfo.setMarketid(getLoginUser().getMarketId());//@设置登录场景id
//			coContractaffixinfo.setAffixno(2);
//			coContractaffixinfo.setAffixname(coFileName);
//			//pdf附件文件
//			coContractaffixinfo.setPapercontractfile(FileUtils.readFileToByteArray(new File(pdfPath)));
//			coContractaffixinfo.setUploadperson(getLoginUser().getUserId());
//			coContractaffixinfo.setUploadtime(new Date());
//			coContractaffixinfo.setAffixtype(7);//附件类型
//			coContractaffixinfo.setDescription("合同附件转存，时间："+ToolsSys.toDateStryMdHms(new Date()));
//			coContractaffixinfo = coContractaffixinfoMng.save(coContractaffixinfo);//@保存合同附件信息
//			if (coContractaffixinfo != null) {
//				msg = "转存附件成功！";
//				log.info("转存附件成功，合同ID：{}", coContractaffixinfo.getContractid());
//			}else{
//				msg = "转存附件失败！";
//				log.error("转存附件失败，合同ID：{}", coContractaffixinfo.getContractid());
//			}
		}
//		clearTemp(wordTemps, pdfTemps);
//		addActionMessage(msg);
//		return inputForm();
		
		return null;

    }

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/setFields",method = RequestMethod.GET)
	public @ItemResponseBody
	List getFlowState(@RequestParam String params,HttpServletRequest request) throws Exception{
		Map<String, Object> map = getMapForParams(params);
		tempId1 = map.get("tempIds")==null?"":map.get("tempIds").toString();
		ids1 =(map.get("contractids")==null?null:(List) map.get("contractids"));
		marketid=UserInfoUtil.getLoginUserMarket(request);
		userid =UserInfoUtil.getLoginUserId(request);
		return null;
	}

//	@SuppressWarnings("rawtypes")
//	public List queryContractInfo()
//	{
//		List list = contractTemplateBizc.getContractInfoByCode(ids.get(0).toString());
//		return list;
//	}
	@SuppressWarnings("unchecked")
	private Map<String, Object> getMapForParams(String params) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = JsonUtils.getObjectMapper().readValue(params, Map.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}

}
