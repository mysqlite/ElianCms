<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<html lang="zh-CN">
	<head>
		<title>用户密码修改</title>
        <jsp:include page="../../page/include/headEdit.jsp"></jsp:include>
	</head>
	<body class="input">
    <div class="main-top-hd clearfix">
        <h3 class="cur">密码修改</h3>
    </div>
<div class=body>
    <form id="areaCOrU" name="areaCOrU" method="post" action="${path}/admin/userPwd!save.action">
        <input type="hidden" name="token" value="${token}"/>
        <div class="divInputTable">
        <h3 class="main-tit-bar" id="typeBtn">基本信息</h3>
            <div id="typeDiv">
            <ul class="inputList">
                <li class="inputList-li">
                   <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>原密码：</label>
                     <input type="password" class="ipt" name="prvePwd" value="" maxlength="20"/>
                     <span class="errortip">
                         <s:fielderror ><s:param>prvePwd</s:param></s:fielderror>
                     </span>
                   </div>
                </li>
                 <li class="inputList-li">
                   <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>新密码：</label>
                     <input type="password" class="ipt" name="nowPwd" value="" maxlength="20"/>
                     <span class="errortip">
                         <s:fielderror ><s:param>nowPwd</s:param></s:fielderror>
                     </span>
                   </div>
                   <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>重复新密码：</label>
                     <input type="password" class="ipt" name="nowPwd2" value="" maxlength="20"/>
                     <span class="errortip">
                         <s:fielderror ><s:param>nowPwd2</s:param></s:fielderror>
                     </span>
                   </div>
                </li>
                 <li class="inputList-li">
                   <div class="listItem">
                   <input name="errorNum" type="hidden" value="${errorNum}"/>
                     <span class="errortip">
                         <s:fielderror ><s:param>errorNum</s:param></s:fielderror>
                     </span>
                     </div>
                </li>
            </ul>
        </div>
         <div class="buttonArea">
            <input class="formButton" value="确  定" type="button" onclick="submits(this.form);">&nbsp;&nbsp;
        </div>
        </div>
        </form>
        <script type="text/javascript">
            var errorNum='${errorNum}';
            if(errorNum>=3){
            	alert("第"+errorNum+"原密码不匹配,自动转到首页!")
            	setTimeout(function(){
            	   parent.menuFrame.location='${path}'+"/admin/navigation.action?url=homePageLeft&param=parentId=1&myinfo=true";
            	});
            }
        </script>
      </div>
   </body>
</html>