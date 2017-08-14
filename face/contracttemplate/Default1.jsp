<%@ page language="java" 
import="java.util.*,com.zhuozhengsoft.pageoffice.*"
 pageEncoding="UTF-8"%>
 <%@page import="com.sgcc.dlsc.contractManage.contracttemplate.GetWordData"%>

<%
//String url=request.getSession().getServletContext().getRealPath("resources/contracts/"+"/");
String url = "D:\\contracts";
String idLength = request.getParameter("contractIds");

String path = request.getContextPath();
String basePath = request.getScheme() + "://"
		+ request.getServerName() + ":" + request.getServerPort()
		+ path + "/";

String tempId = request.getParameter("tempId");
GetWordData getdata = new GetWordData();
String foo = getdata.isokdown(tempId);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title></title>
	<style>
	.button {
    background: url("resources/images/button_bg.png") repeat-x scroll center center rgba(0, 0, 0, 0);
    border: 1px solid #707070;
    border-radius: 3px;
    padding-left: 15px;
    padding-right: 15px;
}
	</style>
	<script language="javascript" src="<%=basePath.replaceAll("/contract", "")%>mx/scripts/lib/jquery.js"></script>
    <script type="text/javascript">
    	var contractids = window.parent.document.getElementById("contractIds").value;
    	var tempId = window.parent.document.getElementById("tempId").value;
    	var contractidObj = contractids.split("@_@");
    	//alert(contractids);
        count = 0;
        var PdfType;
        var contractId;
        var local;
        var idLength = parseInt(<%= idLength%>);
        window.myFunc = function() {
        	count++;
        	contractId = contractidObj[count];
            if (count < idLength) {
                if(PdfType==true)
        		{
                	document.getElementById("ifram").src="FileMaker.jsp?id=" + count+"&pdf=y"+"&contractid="+contractId+"&tempid="+tempId;
        			//document.frames["ifram"].window.location.href = "FileMaker.jsp?id=" + count+"&pdf=y"+"&contractid="+contractId+"&tempid="+tempId;
        		}
        		else
        		{
        			document.getElementById("ifram").src="FileMaker.jsp?id=" + count+"&pdf=n"+"&contractid="+contractId+"&tempid="+tempId;
        			//document.frames["ifram"].window.location.href = "FileMaker.jsp?id=" + count+"&pdf=n"+"&contractid="+contractId+"&tempid="+tempId;
        		}

                //设置进度条
                document.getElementById("ProgressBarSide").style.visibility = "visible";
                document.getElementById("ProgressBar").style.width = count + "0%";
            } else {
                //隐藏进度条div
                document.getElementById("ProgressBarSide").style.visibility = "hidden";
                //重置进度条
                document.getElementById("ProgressBar").style.width = "0%";
                alert('批量生成完毕！');
                if(local==false){
            		//转存为合同附件
            			$.ajax({
			       		   type: "POST",
			       		   url: "<%=basePath%>rest/contracrtTemplate/upLoadFile",
			       		   //data: "name=John&location=Boston",
			       		   async: false,
			       		   success: function(msg){
            					alert('附件转存成功！');
			       		   }});
            	}else{
            		if(count<=1){
            			document.getElementById("ifram").src = "<%=basePath%>rest/contracrtTemplate/saveAsDocOrPdf";
            		}else{
            			document.getElementById("ifram").src = "<%=basePath%>rest/contracrtTemplate/saveAsZip";
            		}
            	}
                count = 0;
            }
        };
        //批量转换完毕
        function ConvertFiles() {
        	var foo= '<%=foo%>';
        	if(foo=='fail'){
        		alert("范本文件不存在，请先上传范本文件！");
        		return;
        	}
        	local = document.getElementById("t1").checked;
        	PdfType = document.getElementById("g1").checked;
        	contractId = contractidObj[count];
        	$.ajax({
       		   type: "POST",
       		   url: "<%=basePath%>rest/contracrtTemplate/clearList",
       		   //data: "name=John&location=Boston",
       		   async: false,
       		   success: function(msg){
       		  	if(PdfType==true)
	          	{
	          		//document.frames["ifram"].window.location.href = "FileMaker.jsp?id=" + count+"&pdf=y"+"&contractid="+contractId+"&tempid="+tempId;
	          		document.getElementById("ifram").src="FileMaker.jsp?id=" + count+"&pdf=y"+"&contractid="+contractId+"&tempid="+tempId;
	          	}
	          	else
	          	{
	          		//document.frames["ifram"].window.location.href = "FileMaker.jsp?id=" + count+"&pdf=n"+"&contractid="+contractId+"&tempid="+tempId;
	          		document.getElementById("ifram").src="FileMaker.jsp?id=" + count+"&pdf=n"+"&contractid="+contractId+"&tempid="+tempId;
	          	}
       		   }
       		});
            //第一次让子页面自刷新
        	
        }
        function closeWin()
        {
        	window.parent.document.getElementById("closeButton").click();
        }
    </script>

</head>
<body>
    <form id="form1">
    <div id="win" align="center" iconCls="icon-save" title="选择窗口" resizable="false" collapsible="false" minimizable="false" maximizable="false" >
	<div><span style="font-size: 12px;color:red;">请先检查是否安装pageoffice插件，如果未安装请到系统管理模块下载并安装pageoffice插件！</span></div>
	<div align="left" style="font-size: 12px;">
		<br/>
		&nbsp;&nbsp;
		格式：
		<input type="radio" id="g2" name="gs" value="doc" checked="checked">Word
		&nbsp;&nbsp;&nbsp;
		<input type="radio" id="g1" name="gs" value="pdf">Pdf
		<br/>
		&nbsp;&nbsp;
		操作：
		<input type="radio" id="t1" name="rt" value="1" checked="checked">保存到本地
		&nbsp;&nbsp;&nbsp;
		<input type="radio" id="t2" name="rt" value="2">存为合同附件
	</div>
</div>
    <div id="ProgressBarSide" style="color: Silver; width: 200px; visibility: hidden;
        position: absolute;  left: 40%; top: 50%; margin-top: -32px">
        <span style="color: gray; font-size: 12px; text-align: center;">正在导出请稍后...</span><br />
        <div id="ProgressBar" style="background-color: Green; height: 16px; width: 0%; border-width: 1px;
            border-style: Solid;">
        </div>
    </div>
    <div style="text-align: center;margin-top:20px">
        <br />
        <input id="Button1" type="button" value="确定" onclick="ConvertFiles()" />&nbsp;&nbsp;
        <input id="Button2" type="button" value="关闭" onclick="closeWin()" /> 
    </div>
    <div style="width: 0px; height: 0px; overflow: hidden;">
        <iframe id="ifram" name="ifram" src="" ></iframe>
    </div>
    </form>
</body>
</html>
