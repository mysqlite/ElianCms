<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html lang="zh-CN">
    <head>
       <jsp:include page="../../page/include/headShow.jsp"></jsp:include>
    </head>
    <body class="input">
    <div class="main-top-hd clearfix">
        <h3 class="cur">
	        <c:if test="${!myInfo}">用户信息查看</c:if>
		    <c:if test="${myInfo}">个人信息查看</c:if>
       </h3>
    </div>
<div class=body>
    <jsp:include page="../../page/include/image_upload.jsp"></jsp:include>
    <form id="userCOrU" name="userCOrU" method="post" action="${path}/admin/user!edit.action">
        <div class="divInputTable">
            <h3 class="main-tit-bar" id="typeBtn">基本信息</h3>
            <div id="typeDiv">
            <ul class="inputList">
                <li class="inputList-li">
                   <div class="listItem">
                     <label class="lbl-ipt-tit">用户状态：</label>
                         <c:if test="${user.status==0}"><span class="txt">未启用</span></c:if>
                         <c:if test="${user.status==1}"><span class="txt">启用</span></c:if>
                         <c:if test="${user.status==2}"><span class="txt">注销</span></c:if>
                         <c:if test="${user.status==3}"><span class="txt">删除</span></c:if>
                   </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem">
                    <label class="lbl-ipt-tit">登&nbsp;&nbsp;录&nbsp;&nbsp;名：</label>
                       <span class="txt"> ${user.account}</span>
                   </div>
                   <div class="listItem">
                    <label class="lbl-ipt-tit">用户类型：</label>
                    <div class="select">
                     <span class="txt">${user.userType.typeName}</span>
                    </div>
                  </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem">
                      <label class="lbl-ipt-tit">手机号码：</label>
                      <span class="txt">${user.mobile}</span>
                   </div>
                   <div class="listItem txtarea">
                      <label class="lbl-ipt-tit">电子邮箱：</label>
                      <span class="txt">${user.email}</span>
                   </div>
                </li>
                <li class="inputList-li">
                <div class="listItem">
                     <label class="lbl-ipt-tit">医疗卡号：</label>
                      <span class="txt">${user.medicalCard}</span>
                   </div>
                 </li>
               </ul>
              </div>
              <h3 class="main-tit-bar" id="userExtBtn">扩展信息</h3> 
              <div id="userExtDiv">
               <ul class="inputList">
                <li class="inputList-li">
                   <div class="listItem txtarea">
                       <label class="lbl-ipt-tit">真实名字：</label>
                        <span class="txt">${user.realName}</span>
                    </div>
                    <div class="listItem txtarea">
                      <label class="lbl-ipt-tit">性别：</label>
                         <c:if test="${user.gender == 'man'}"><span class="txt">男士</span></c:if>
                         <c:if test="${user.gender == 'madam'}"><span class="txt">女士</span></c:if>
                         <c:if test="${user.gender == 'secret'}"><span class="txt">保密</span></c:if>
                    </div>
                </li>
               <li class="inputList-li">
                  <div class="listItem txtarea">
                     <label class="lbl-ipt-tit"> 身份证号码：</label>
                     <span class="txt">${user.identityCard}</span>
                  </div>
                  <div class="listItem txtarea">
                  <label class="lbl-ipt-tit">生日：</label>
                  <span class="txt"><fmt:formatDate value="${user.birthday}" pattern="yyyy-MM-dd"/></span>
                  </div>
               </li>
               <li class="inputList-li">
                  <div class="listItem listItem-full-width">
                     <label class="lbl-ipt-tit">身份证照片：</label>
                     <span class="txt" id="idCardImg"></span>
                  </div>
               </li>
               <li class="inputList-li">
                  <div class="listItem listItem-full-width">
                     <label class="lbl-ipt-tit">头像：</label>
                     <span class="txt" id="userImg"></span>
                  </div>
               </li>
               <script type="text/javascript">
                     showImageButton("idCardImg",'${user.idCardImg}',"user.idCardImg",1);//职业许可
                     showImageButton("userImg",'${user.userImg}',"user.userImg",2);//LOGO
               </script>
               <li class="inputList-li">
                  <div class="listItem txtarea">
                     <label class="lbl-ipt-tit">区域：</label>
                     <span class="txt">${areaName}</span>
                  </div>
               </li>
               <li class="inputList-li">
                  <div class="listItem txtarea">
                     <label class="lbl-ipt-tit">QQ号码：</label>
                     <span class="txt">${user.qq}</span>
                  </div>
                   <div class="listItem txtarea">
                     <label class="lbl-ipt-tit">MSN账号：</label>
                     <span class="txt">${user.msn}</span>
                  </div>
               </li>
               <li class="inputList-li">
                  <div class="listItem txtarea">
                     <label class="lbl-ipt-tit">注册信息：</label>
                     <span class="txt">
                     <fmt:formatDate value="${user.registerTime}" pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;&nbsp;${user.registerIp}
                     </span>
                  </div>
                   <div class="listItem txtarea">
                     <label class="lbl-ipt-tit">登录信息：</label>
                     <span class="txt">
                      <fmt:formatDate value="${user.lastLoginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;&nbsp;${user.lastLoginIp}
                     </span>
                  </div>
               </li>
                <li class="inputList-li">
                   <div class="listItem listItem-full-width">
                     <label class="lbl-ipt-tit">自我介绍：</label>
                     ${user.intro} 
                 </div>
                 </li>
               </ul>
              </div>
           </div>
         <div class="buttonArea">
           <input class="formButton" onclick="window.location='${path}/admin/user!edit.action?id=${user.id}&edit=true&myInfo=${myInfo}'" value="修改"/>
           <c:if test="${!myInfo}">
           <input class="formButton" onclick="window.location='${path}/admin/user!list.action'" value="返回"/>
           </c:if>
           <c:if test="${myInfo}">
           <input class="formButton" onclick="window.history.back();return false;" value="返回"/>
           </c:if>
        </div>
        </form>
      </div>
   </body>
<script type="text/javascript">  
        displayDiv('typeBtn','typeDiv','${errors}',650);
        displayDiv('userExtBtn','userExtDiv','${errors}',650);
</script>
</html>