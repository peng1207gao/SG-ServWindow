<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/mx-framework" prefix="mx" %>    

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<mx:Framework debugMode="true"/>
<title>经法合同文本查询</title>
<style>
html, body
{
    width:100%;
    height:100%;
    margin:0;
    overflow:hidden;
}
</style>
</head>
<body>

<mx:WebletContainer id="jfcontractaffixContainer" webletID="jfcontractaffix" bundleName="contract"></mx:WebletContainer>
</body>
</html>