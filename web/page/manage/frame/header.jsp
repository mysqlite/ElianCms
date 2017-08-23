<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@page import="com.elian.cms.syst.util.SysXmlUtils"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 
<!DOCTYPE html>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html>
	<head>
		<title>医联网CMS</title>
		<meta content="text/; charset=utf-8" http-equiv=content-type>
		<link rel=icon type=image/x-icon href="favicon.ico">
		<link rel=stylesheet type=text/css href="${path}/css/manage/base.css">
		<link rel=stylesheet type=text/css href="${path}/css/manage/admin.css">
		<script type=text/javascript src="${path}/js/jquery.js"></script>
        <style type="text/css">
        html{ overflow:hidden}
        </style>
	</head>
	<body class="header-body">
	    <div class="header clearfix">
		    <div class="bodyLeft">
		        <div class="logo">
		            <a href="<%=SysXmlUtils.getString("mainSitedomain")+SysXmlUtils.getString("mainDomain")%>" target="_blank"><img src="<%=SysXmlUtils.getString("adminHeaderlogoImg") %>" width="190" height="90"/></a>
		        </div>
		    </div>
		    <div></div>
		    <div class="bodyRight">
		        <div class="link">
		        <a href="javascript:void(0);" target=mainFrame>技术支持</a>|
                <a href="javascript:void(0);" target=mainFrame>购买咨询</a>|
                <a href="javascript:void(0);" target=mainFrame>关于我们</a>|
                <a href="javascript:void(0);" target=mainFrame>联系我们</a>|
                <a href="javascript:void(0);" target=mainFrame>提建议</a>
                <div></div>
                <span class="welcome">
                ${_site.siteName}&nbsp;&nbsp;
                <c:forEach var="role" items="${_roleList}" varStatus="e">${role.roleName}</c:forEach>&nbsp;&nbsp;
                ${_user.realName}&nbsp;您好!
                </span>
		        </div>
		        <div id="menu" class="menu" style="float: left;">
		            <ul id="menuList">
		            </ul>
		            <div class=info>
				        <a id="myinfo" href="javascript:void(0);" class="profile" target="mainFrame">个人资料</a>
	                    <a class="logout" target=_top href="${path}/admin/login!loginOut.action" id="exit">退出</a>
	                    <c:if test="${!empty _site.alias}">
	                    	<a href="${_site.alias}" target="_blank">前台首页</a>
	                    </c:if>
	                    <c:if test="${empty _site.alias}">
		                    <c:if test="${_site.comType eq 'mainstation'}">
		                       <a href="http://${_site.domain}<%=SysXmlUtils.getString("mainDomain")%>" target="_blank">前台首页</a>
		                    </c:if>
		                     <c:if test="${_site.comType eq 'substation'}">
	                           <a href="http://${_site.domain}<%=SysXmlUtils.getString("mainDomain")%>" target="_blank">前台首页</a>
	                        </c:if>
		                    <c:if test="${_site.comType eq 'hospital'}">
		                       <a href="http://hosp<%=SysXmlUtils.getString("mainDomain")%>/${_site.id}/index.shtml" target="_blank">前台首页</a>
		                    </c:if>
		                    <c:if test="${_site.comType eq 'company'}">
		                       <a href="http://comp<%=SysXmlUtils.getString("mainDomain")%>/${_site.id}/index.shtml" target="_blank">前台首页</a>
		                    </c:if>
		                    <c:if test="${_site.comType eq 'company_medicine'}">
		                       <a href="http://comp<%=SysXmlUtils.getString("mainDomain")%>/${_site.id}/index.shtml" target="_blank">前台首页</a>
		                    </c:if>
		                    <c:if test="${_site.comType eq 'company_instrument'}">
		                       <a href="http://comp<%=SysXmlUtils.getString("mainDomain")%>/${_site.id}/index.shtml" target="_blank">前台首页</a>
		                    </c:if>
	                    </c:if>
		            </div>
		        </div>
		    </div>
	    </div>
        <script type="text/javascript">
             var header={};
             G=function(id){return document.getElementById(id);};  
             header.getJson=function(url,f,paramData) {$.ajax({url: url,type: "POST",dataType: 'json',data: (paramData),async:false,cache:false,timeout: 10000,success:f});}
             G("myinfo").onclick=function(){
            	 parent.contentFrame.menuFrame.location='${path}'+"/admin/navigation.action?url=homePageLeft&param=parentId=1&myinfo=true";
            	 var $menuItem = $("#menu li");
            	 $menuItem.removeClass("current");
            	 var $this=$("#0");
            	 $this.addClass("current");
            	 setTimeout(function(){
            		 parent.contentFrame.mainFrame.location='${path}'+"/admin/user!edit.action?edit=true&myInfo=true";
            	 },400);
             };
            var context="",classes="",url="",taraget="";
           	header.getJson('${path}'+'/admin/menu!findMenuByRoot.action',function(data){
           		var menus=data.menus;
               for (var i=0,len = menus.length; i< len; i++) {
                           if(menus[i].menuSort==1)classes="menuItem current";
                           else classes="menuItem";
                           if(menus[i].menuUrl!="")url='${path}'+menus[i].menuUrl;
                           else url="javascript:void(0);"
                           if(menus[i].menuUrl=="/admin/navigation.action?url=templateMain&template"|
                              menus[i].menuUrl=="/admin/navigation.action?url=channelMain&channel"|
                              menus[i].menuUrl=="/admin/navigation.action?url=contentMain&content")
                           {taraget="contentFrame";}
                           else {taraget="menuFrame";}
                           context+='<li class="'+classes+'" id="'+i+'"><a target="'+taraget+'" href="javascrpit:void(0);" ' +
                           ' onBlur="this.href=\'javascrpit:void(0);\'"'+
                           ' onMouseOut="this.href=\'javascrpit:void(0);\'"'+
                           'onclick="this.href=\''+url+"&param=parentId="+menus[i].id+'\'">'+menus[i].menuName+'</a></li>';
                         }
                       G("menuList").innerHTML=context;
            });
            G("exit").onclick=function(){
            if (confirm('确定要退出吗？')){
                return true;
            }
            else{ 
                return false;
                }
            };
            $(document).ready(function() {
                var $menuItem = $("#menu li");
                $menuItem.live("click",function(){
                    var $this=$(this);
                    $menuItem = $("#menu li");
                    $menuItem.removeClass("current");
                    $this.addClass("current");
                });
            });
        </script>
         </body>
</html>
