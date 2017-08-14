<%@page import="com.sgcc.dlsc.commons.util.GetConfigValue"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="com.sgcc.dlsc.contractManage.contracttemplate.GetWordData"%>
<%@ page language="java"
	import="java.util.*,com.zhuozhengsoft.pageoffice.FileMakerCtrl, com.zhuozhengsoft.pageoffice.*, com.zhuozhengsoft.pageoffice.wordwriter.*"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/pageoffice" prefix="po"%>
<%
	GetConfigValue configValue = new GetConfigValue();
	GetWordData getdata = new GetWordData();
	String idNum = request.getParameter("id");
	String pdf = request.getParameter("pdf");
	String tempId = request.getParameter("tempid");
	String contractId = request.getParameter("contractid");
	List<String[]> coparamList_temp = getdata.queryContractInfo(idNum,tempId,contractId);
	FileMakerCtrl fmCtrl = new FileMakerCtrl(request);
	fmCtrl.setServerPage(request.getContextPath()+"/../ETradePublicUtils/poserver.zz");
	String[] comTextKey = new String[coparamList_temp.size()];
	String[] comTextValue = new String[coparamList_temp.size()];
	WordDocument doc = new WordDocument();
	String path1 = getdata.getSaveModelPath(tempId);
	//禁用右击事件
	doc.setDisableWindowRightClick(true);
	//给数据区域赋值，即把数据填充到模板中相应的位置
	for (int j = 0; j < coparamList_temp.size(); j++) {
		String[] key_value =coparamList_temp.get(j);
		comTextKey[j] = key_value[0];
		comTextValue[j] = key_value[1];
	}
		//Object[] object = (Object[])(((list.get(0))==null)?"":(list.get(0)));
		
		for(int i=0;i<coparamList_temp.size();i++){
			doc.openDataRegion(comTextKey[i]).setValue(comTextValue[i]);
		}
		//doc.openDataRegion("PO_contractname").setValue(object[1]==null?"":object[1].toString());
		String contractname=getdata.getContractName(contractId);
		contractname = URLEncoder.encode(contractname,"UTF-8");
		fmCtrl.setSaveFilePage("SaveMaker.jsp?contractname="+ contractname +"&idNum="+idNum);
		fmCtrl.setWriter(doc);
		//fmCtrl.fillDocumentAsPDF(arg0, arg1, arg2);
		//fmCtrl.setJsFunction_OnProgressComplete("SaveAsPDF()");
		fmCtrl.setJsFunction_OnProgressComplete("OnProgressComplete()");
		fmCtrl.setFileTitle("newfilename.doc");
		if(pdf.equals("y"))
		{
			fmCtrl.fillDocumentAsPDF("file://"+path1, DocumentOpenType.Word, "template.pdf");//"file://"+
		}
		else
		{
			fmCtrl.fillDocument("file://"+path1, DocumentOpenType.Word);//saveModelPath+"合同参数测试用模版.doc""file://"+
		}
		fmCtrl.setTagId("FileMakerCtrl1"); //此行必须
	
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<title>My JSP 'FileMaker.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	</head>

	<body>
		<div>
			<!--**************   卓正 PageOffice 客户端代码开始    ************************-->
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
			<script language="javascript" type="text/javascript">
				function OnProgressComplete() 
				{
					window.parent.myFunc(); //调用父页面的js函数
				}
				 function SaveAsPDF()
				 {
			        document.getElementById("PageOfficeCtrl1").WebSaveAsPDF();
			     }
			</script>
			<!--**************   卓正 PageOffice 客户端代码结束    ************************-->
			<po:FileMakerCtrl id="FileMakerCtrl1"/>
			
		</div>

	</body>
</html>
