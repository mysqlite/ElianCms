<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.elian.cms.syst.util.SysXmlUtils"%>
<!DOCTYPE HTML>
<html>
<head>
  <link rel="shortcut icon" type="image/x-icon"  href="<%=SysXmlUtils.getString("siteImgIcon")%>"/>
  <link rel="stylesheet" href='${path}/css/manage/jquery-webox.css' type="text/css">
  <script type="text/javascript" src="${path}/js/jquery.js"></script>
  <script type='text/javascript' src='${path}/js/manage/editCommon.js'></script>
</head>
<frameset id=parentFrameset frameSpacing=0 border=0 cols=* frameBorder=no rows=95,*>
    <frame id=headerFrame noresize src="${path}/admin/navigation.action?url=header" frameBorder="0" name=headerFrame scrolling=no/><!--头部导航页-->
    <frame id=contentFrame noresize src="${path}/admin/navigation.action?url=homePageMain" frameBorder="0" name=contentFrame scrolling=no/><!--内容页-->
</frameset>
<body>
<!-- 此处为弹出层必要表单元素-->
      <div id="AjaxLoading" class="showbox">
          <div class="loadingWord" ><img id="loadingImg" src="${path}/images/manage/waiting.gif" width="32px;" height="32px;"/><span id="loadingWordText">初始化进行中</span></div>
      </div>
      <div class="overlay"></div>
<!--此处为弹出层必要表单元素-->
<script type="text/javascript">
function divtest2(){
    alertMaskDiv();//弹出遮罩层
   setTimeout(function(){
     closeMaskDiv();//关闭遮罩层
  },3000);
}
</script>
</body>
</html>
