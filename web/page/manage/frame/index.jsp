<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.elian.cms.syst.util.SysXmlUtils"%>
<!DOCTYPE HTML>
<html>
<head>
  <link rel="shortcut icon" type="image/x-icon"  href="<%=SysXmlUtils.getString("siteImgIcon")%>"/>
  <link rel="stylesheet" href='${path}/css/manage/jquery-webox.css' type="text/css">
</head>
<frameset id=indexParentFrameset frameSpacing=0 border=0 cols=* frameBorder=no>
    <frame id=indexFrame noresize src="${path}/admin/navigation.action?url=indexChild" frameBorder="0" name=indexFrame scrolling=no/><!--内容页-->
</frameset>
</html>
