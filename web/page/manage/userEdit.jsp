<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML>
<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<html lang="zh-CN">
    <head>
        <jsp:include page="../../page/include/headEdit.jsp"></jsp:include>
        <script type="text/javascript" src="${path}/js/jquery.ztree.excheck-3.5.js"></script>
    </head>
    <body class="input">
    <div class="main-top-hd clearfix">
        <h3 class="cur">
        <c:if test="${!myInfo}">
           <c:if test="${edit}">用户修改
               <c:set var="pwdtip" value="*不修改请留空"></c:set>
           </c:if>
           <c:if test="${!edit}">用户添加
               <c:set var="pwdtip" value=""></c:set>
           </c:if>
       </c:if>
       <c:if test="${myInfo}">
           个人信息修改
           <c:set var="pwdtip" value="*不修改请留空"></c:set>
       </c:if>
        </h3>
    </div>
<div class=body>
<SCRIPT type="text/javascript">
        var setting = {
            check: {enable: true,chkboxType: {"Y":"", "N":""}},
            view: { dblClickExpand: false},
            data: { simpleData:{enable: true}},
            callback: { beforeClick: beforeClick,onCheck: onCheck}
            };
         var roles=new Array();
         var rIds='${roleIds}';
         var rId=rIds.split(",");
         var core={};
        core.getJson=function(url,f,paramData) {$.ajax({url: url,type: "POST",dataType: 'json',data: (paramData),async:true,cache:false,timeout: 10000,success:f});}
        core.getJson('${path}/admin/user!findRoleBySiteId.action',function(data){
        	 var role=data.role;
        	 if(role!=null){
                  for(var i=0,len=role.length;i<len;i++){
                      var is=rIds.lastIndexOf(role[i].id,rIds.length);
                      if(is>=0){
                        roles[i]={id:role[i].id,pId:0,name:role[i].roleName,checked:true};
                      }else{
                        roles[i]={id:role[i].id,pId:0,name:role[i].roleName,checked:false};   
                      }
                  }
                  $.fn.zTree.init($("#roleTree"), setting, roles);
              }
         });
         
        function beforeClick(treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("roleTree");
            zTree.checkNode(treeNode, !treeNode.checked, null, true);
            return false;
        }
        
        function onCheck(e, treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("roleTree");
            var nodes = zTree.getCheckedNodes(true);
            var v = "";
            var k="";
            for (var i=0, l=nodes.length; i<l; i++) {
                v += nodes[i].name + ",";
                k += nodes[i].id+",";
            }
            if (v.length > 0 ) v = v.substring(0, v.length-1);
            if (k.length > 0 ) k = k.substring(0, k.length-1);
            var cityObj = $("#citySel");
            cityObj.attr("value", v);
            var city=$("#citysel1"); 
            city.attr("value",k);
        }
        
        function showRoles() {
            var cityObj = $("#citySel");
            var cityOffset = $("#citySel").offset();
            $("#roleContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
            $("body").bind("mousedown", onBodyDownRole);
        }
        function hideRole() {
            $("#roleContent").fadeOut("fast");
            $("body").unbind("mousedown", onBodyDownRole);
        }
        function onBodyDownRole(event) {
            if (!(event.target.id == "menuBtn" || event.target.id == "citySel" || event.target.id == "roleContent" || $(event.target).parents("#roleContent").length>0)) 
            {
                hideRole();
            }
        }
    </SCRIPT>
    <jsp:include page="../../page/include/f_upload.jsp"></jsp:include>
    <form id="userCOrU" name="userCOrU" method="post" action="${path}/admin/user!save.action">
        <input type="hidden" name="user.id" value="${user.id}"/>
        <input type="hidden" name="user.version" value="${!empty user.version?user.version:0}"/>
        <input type="hidden" name="edit" value="${edit}"/>
        <input type="hidden" name="myInfo" value="${myInfo}"/>
        <input type="hidden" name="roleId" value="${roleId}"/>
        <input type="hidden" name="siteId" value="${siteId}"/>
        <input type="hidden" name="siteUserAdd" value="${siteUserAdd}"/>
        <input type="hidden" name="token" value="${token}"/>
        <input type="hidden" name="initialPassword" value="${initialPassword}"/>
        <input type="hidden" name="user.admin" value="${user.admin}" />
        <input type="hidden" name="user.proposer" value="${user.proposer}" />
        <c:if test="${myInfo}">
        <input type="hidden" name="user.status" value="${user.status}"/>
        </c:if>
        <c:if test="${myInfo or siteUserAdd}">
         <input id="citysel1" type="hidden" name="roleIds" value="${roleIds}"/>
        </c:if>
        <input type="hidden" name="user.userType.id" value="${_user.userType.id}"/>
        
        <div class="divInputTable">
            <h3 class="main-tit-bar" id="typeBtn">基本信息</h3>
            <div id="typeDiv">
            <ul class="inputList">
             <c:if test="${edit}">   
                  <li class="inputList-li">
                     <div class="listItem txtarea">
                        <label class="lbl-ipt-tit">注册时间：</label>
                        <span class="txt">
                        <fmt:formatDate value="${user.registerTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </span>
                        <input type="hidden" name="user.registerTime" value="${user.registerTime}"/>
                     </div>
                      <div class="listItem txtarea">
                        <label class="lbl-ipt-tit">最后登录时间：</label>
                        <span class="txt">
                         <fmt:formatDate value="${user.lastLoginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </span>
                        <input type="hidden" name="user.lastLoginTime" value="${user.lastLoginTime}"/>
                        <input type="hidden" name="user.lastLoginIp" value="${user.lastLoginIp}"/>
                        <input type="hidden" name="user.loginCount" value="${user.loginCount}"/>
                     </div>
                  </li>
                   <li class="inputList-li">
                     <div class="listItem txtarea">
                        <label class="lbl-ipt-tit">注册IP：</label>
                        <span class="txt">
                        ${user.registerIp}
                        </span>
                        <input type="hidden" name="user.registerIp" value="${user.registerIp}"/>
                     </div>
                      <div class="listItem txtarea">
                        <label class="lbl-ipt-tit">最后登录IP：</label>
                        <span class="txt">
                         ${user.lastLoginIp}
                        </span>
                     </div>
                  </li>
               </c:if>
             <c:if test="${!myInfo or empty myInfo}">
                <li class="inputList-li">
                   <c:if test="${!siteUserAdd or !myInfo}">
                    <div class="listItem">
                        <label class="lbl-ipt-tit"><span class="star">*</span>所属组：</label>
                        <input id="citySel" type="text" class="ipt" readonly value="${roleName}" onclick="showRoles();" />
                        <input id="citysel1" type="hidden" name="roleIds" value="${roleIds}"/>
                        <span class="errortip">
                           <s:fielderror ><s:param>roleIds</s:param></s:fielderror>
                        </span>
                    </div>
                   </c:if>
                   <div id="roleContent" class="menuContent" style="display:none; position: absolute;">
                            <ul id="roleTree" class="ztree" style="width:250px; height: auto;"></ul>
                   </div>
                   <div class="listItem">
                     <label class="lbl-ipt-tit"><span class="star">*</span>用户状态:</label>
                        <c:forEach items="${disableStatus}" var="item" varStatus="e">
                           <c:if test="${item.key eq user.status}">
                              <input name="user.status" value="${user.status}" type="hidden"/>
                              <span class="txt">${item.value}</span>
                              <c:set var="disab" value="true"></c:set>
                          </c:if>
                       </c:forEach>
                       <c:if test="${empty disab}">
		                    <c:forEach items="${mainUserStatusList}" var="item" varStatus="e">
			                    <label class="lbl-ipt-tit radioWrap">
			                        <input type="radio" name="user.status" value="${item.key}" 
			                        <c:if test="${user.status eq item.key or empty user.status}">checked </c:if> />
			                           ${item.value}
			                    </label>
		                    </c:forEach>
	                   </c:if>
                   </div>
                </li>
                 </c:if>
                <li class="inputList-li">
                   <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>登&nbsp;&nbsp;录&nbsp;&nbsp;名：</label>
                     <c:if test="${!edit}">
                        <input type="text" class="ipt" name="user.account" value="${user.account}"/>
                        <span class="errortip">
                            <s:fielderror ><s:param>user.account</s:param></s:fielderror>
                        </span>
                     </c:if>
                      <c:if test="${edit}">
                        <input type="hidden" name="user.account" value="${user.account}"/>
                       <span class="txt"> ${user.account}</span>
                      </c:if>
                   </div>
                </li>
                 <c:if test="${!edit}">
	                <li class="inputList-li">
	                       <div class="listItem">
	                          <label class="lbl-ipt-tit"><span class="star">*</span>密&nbsp;&nbsp;码：</label>
	                           <input type="password" class="ipt" name="user.password"/>
	                           <span class="txt">${pwdtip}</span>
	                           <span class="errortip">
	                               <s:fielderror ><s:param>user.password</s:param></s:fielderror>
	                           </span>
	                       </div>
	                       <div class="listItem">
	                          <label class="lbl-ipt-tit"><span class="star">*</span>确认密码：</label>
	                          <input type="password" class="ipt" name="pwd"/><span class="txt">${pwdtip}</span>
	                          <span class="errortip">
	                                <s:fielderror ><s:param>pwd</s:param></s:fielderror>
	                          </span>
	                       </div>
	                </li>
                </c:if>
                <li class="inputList-li">
                   <div class="listItem">
                      <label class="lbl-ipt-tit"><span class="star">*</span>手机号码：</label>
                      <input name="user.mobile" value="${user.mobile}" class="ipt"/>
                      
                      <span class="errortip">
                         <s:fielderror ><s:param>user.mobile</s:param></s:fielderror>
                      </span>
                   </div>
                   <div class="listItem txtarea">
                      <label class="lbl-ipt-tit">电子邮箱：</label>
                      <input type="text" name="user.email" class="ipt" value="${user.email}"/> 
                      <span class="errortip">
                          <s:fielderror ><s:param>user.email</s:param></s:fielderror>
                       </span>
                   </div>
                </li>
                <li class="inputList-li">
                    <div class="listItem txtarea">
                      <label class="lbl-ipt-tit"><span class="star">*</span>真实名字：</label>
                       <input type="text" class="ipt" name="user.realName" value="${user.realName}" />
                       <span class="errortip">
                           <s:fielderror ><s:param>user.realName</s:param></s:fielderror>
                       </span>
                   </div>
                   <div class="listItem txtarea">
                      <label class="lbl-ipt-tit">性别：</label>
                      <c:forEach var="sex" items="${sexList}" varStatus="e">
                      <label class="lbl-ipt-tit radioWrap">
                         <input name="user.gender" type="radio" value="${sex.key}" 
                             <c:if test="${user.gender eq sex.key or empty user.gender}">checked</c:if>/>${sex.value}
                        </label>
                      </c:forEach>
                    </div>
                 </li>
                <li class="inputList-li">
	                <div class="listItem txtarea">
	                     <label class="lbl-ipt-tit">区域：</label>
	                     <input id="keyId" class="ipt" name="areaName" type="text" readonly="readonly" value="${areaName}" 
	                      onfocus="showMenuArea('treeContent','keyId','valueId','${path}');"
	                      onkeydown="BackSpace('全国','keyId','valueId');" />
	                     <input id="valueId" type="hidden" name="user.comefrom" value="${user.comefrom}"/>
	                     <div id="treeDiv" class="menuContent"><ul id="treeContent" class="ztree" style="width:300px;height: 300px;"></ul></div>
	                     <span class="errortip" >
	                         <s:fielderror ><s:param>user.comefrom</s:param></s:fielderror>
	                     </span>
                  </div>
                    <div class="listItem">
                     <label class="lbl-ipt-tit">医疗卡号：</label>
                      <input type="text" name="user.medicalCard" class="ipt" value="${user.medicalCard}"/>
                      <span class="errortip" >
                         <s:fielderror ><s:param>user.medicalCard</s:param></s:fielderror>
                      </span>
                   </div>
                </li>
               <li class="inputList-li">
                  <div class="listItem txtarea">
                     <label class="lbl-ipt-tit"> 身份证号码：</label>
                     <input type="text" name="user.identityCard" class="ipt" value="${user.identityCard}"/>
                     <span class="errortip" >
                         <s:fielderror ><s:param>user.identityCard</s:param></s:fielderror>
                      </span>
                  </div>
                  <div class="listItem txtarea">
                  <label class="lbl-ipt-tit">生日：</label>
                  <input name="user.birthday" value='<fmt:formatDate value="${user.birthday}" pattern="yyyy-MM-dd"/>' class="ipt" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
                  <span class="errortip" >
                      <s:fielderror ><s:param>user.birthday</s:param></s:fielderror>
                  </span>
                  </div>
               </li>
               <li class="inputList-li">
                  <div class="listItem txtarea">
                     <label class="lbl-ipt-tit">QQ号码：</label>
                     <input type="text" name="user.qq" class="ipt" value="${user.qq}"/>
                     <span class="errortip" >
                         <s:fielderror ><s:param>user.qq</s:param></s:fielderror>
                      </span>
                  </div>
                   <div class="listItem txtarea">
                     <label class="lbl-ipt-tit">MSN账号：</label>
                      <input type="text" name="user.msn" class="ipt" value="${user.msn}"/>
                      <span class="errortip" >
                         <s:fielderror ><s:param>user.msn</s:param></s:fielderror>
                      </span>
                  </div>
               </li>
               <li class="inputList-li">
                  <div class="listItem listItem-full-width">
                     <label class="lbl-ipt-tit">身份证照片：</label>
                     <div id="idCardTd"></div>
                  </div>
               </li>
               <li class="inputList-li">
                  <div class="listItem listItem-full-width">
                     <label class="lbl-ipt-tit">头像：</label>
                     <div id="userImgTd"></div>
                  </div>
                  <script type="text/javascript">
                   upload.btn("idCardTd",'${user.idCardImg}',"user.idCardImg",false);
                   upload.btn("userImgTd",'${user.userImg}',"user.userImg",false);
                  </script>
                </li>
                <li class="inputList-li">
                  <div class="listItem txtarea">
                     <label class="lbl-ipt-tit">自我介绍：</label>
                     <textarea id="intro" name="user.intro" class="formTextediter" style="width:750;height:200">${user.intro}</textarea>
                     <script>
                      var editor1;KindEditor.ready(function(K) {editor1 = K.create('textarea[id="intro"]');});    
                     </script>
                     <div style="display:none">
			           <textarea name="editorPrevImg">${user.intro}</textarea>
			         </div>
                     <span class="errortip" >
                        <s:fielderror ><s:param>user.intro</s:param></s:fielderror>
                     </span>
                   </div>
                 </li>
               </ul>
              </div>
           </div>
         <div class="buttonArea">
            <input class="formButton" value="确  定" type="button" onclick="submits(this.form,editor1);">&nbsp;&nbsp;
            <%--<c:if test="${siteUserAdd}">
            <input class="formButton" onclick="window.history.back(); return false;" value="返  回" type="button">
            </c:if>
            --%><c:if test="${!myInfo}">
            <input class="formButton" onclick="window.location='${path}/admin/user!list.action'" value="返  回" type="button">
            </c:if>
        </div>
        </form>
      </div>
   </body>
<script type="text/javascript">  
        displayDiv('typeBtn','typeDiv','${errors}',650);
</script>
</html>