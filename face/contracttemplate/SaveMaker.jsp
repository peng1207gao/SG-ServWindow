<%@page import="com.sgcc.dlsc.commons.util.Guid"%>
<%@page import="com.sgcc.dlsc.commons.util.GetConfigValue"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="com.sgcc.dlsc.contractManage.contracttemplate.ContractTemplateController"%>
<%@page import="com.sgcc.dlsc.contractManage.contracttemplate.DownContractFiles"%>
<%@ page language="java"
	import="java.io.*,java.util.*,com.zhuozhengsoft.pageoffice.*"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/pageoffice" prefix="po"%>
<%
	GetConfigValue configValue = new GetConfigValue();
	String savePath = configValue.getGloabalValue("EXPORT_PATH");
	try{
		File file = new File(savePath);
		if(!file.exists()){
			file.mkdir(); 
		}
		}catch(Exception e){
			
		}
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	FileSaver fs = new FileSaver(request, response);
	DownContractFiles dc = new DownContractFiles();
	String contractname = request.getParameter("contractname");
	int idNum = Integer.parseInt(request.getParameter("idNum"))+1;
	String err = "";
	String fileName="";
	if (contractname != null && contractname.length() > 0)
	{
		fileName =  contractname +"_"+Guid.create()+ fs.getFileExtName();
		fs.saveToFile(savePath+
				 fileName);
		ContractTemplateController.wordNames.add(fileName);
	} 
	else 
	{
		err = "<script>alert('未获得合同名称');</script>";
	}
	fs.close();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>


		<title>My JSP 'SaveMaker.jsp' starting page</title>

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
		<%=err%>
		
	</body>
</html>
