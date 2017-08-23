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
    </head>
    <body class="input">
    <jsp:include page="../../page/include/image_upload.jsp"></jsp:include>
    <div class="main-top-hd clearfix">
        <h3 class="cur">
           <c:if test="${edit}">站点用户修改
               <c:set var="pwdtip" value="*不修改请留空"></c:set>
           </c:if>
           <c:if test="${!edit}">站点用户添加
               <c:set var="pwdtip" value=""></c:set>
           </c:if>
        </h3>
    </div>
    <div class=body>
    <form id="siteUserCOrU" name="siteUserCOrU" method="post" action="${path}/admin/siteUser!save.action">
        <input type="hidden" name="user.id" value="${user.id}"/>
        <input type="hidden" name="user.version" value="${user.version}"/>
        <input type="hidden" name="edit" value="${edit}"/>
        <input type="hidden" name="user.userExt.version" value="${user.userExt.version}"/>
        <div class="divInputTable">
            <h3 class="main-tit-bar" id="typeBtn">基本信息</h3>
            <div id="typeDiv">
            <ul class="inputList">
                <li class="inputList-li">
                  <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>站点选择：</label>
                    <c:if test="${edit}"><input name="siteUser.site.id" type="hidden" value="${siteUser.site.id}"/></c:if>
                     <div class="select">
	                   <select name="siteUser.site.id" <c:if test="${edit}">disabled="true"</c:if>>
	                       <c:forEach var="r" items="${siteList}">
	                           <option value="${r.id}" <c:if test="${r.id == siteRole.site.id}">selected="selected"</c:if>>${r.siteName}</option>
	                       </c:forEach>
	                   </select>
                    </div>
                    <span class="errortip">
                        <s:fielderror>
                            <s:param>siteUser.site.id</s:param>
                        </s:fielderror>
                    </span>
                  </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem">
                     <label class="lbl-ipt-tit"><span class="star">*</span>用户状态：</label>
                     <label class="lbl-ipt-tit radioWrap">
                         <input type="radio" name="user.status" value="0" <c:if test="${user.status==3}">disabled</c:if><c:if test="${user.status==0 or empty user.status}">checked </c:if> />
                            未启用 &nbsp;&nbsp;&nbsp;
                     </label>
                     <label class="lbl-ipt-tit radioWrap">
                         <input type="radio" name="user.status" value="1" <c:if test="${user.status==3}">disabled</c:if><c:if test="${user.status==1}">checked</c:if> />
                            启用&nbsp;&nbsp;&nbsp;
                     </label>
                     <label class="lbl-ipt-tit radioWrap">
                        <input type="radio" name="user.status" value="2" <c:if test="${user.status==3}">disabled</c:if><c:if test="${user.status==2}">checked</c:if> />
                            注销   &nbsp;&nbsp;&nbsp;
                     </label>
                     <label class="lbl-ipt-tit radioWrap">
                         <input type="radio" name="user.status"  value="3" <c:if test="${user.status==3}">  checked="checked" </c:if> />
                            <span style="color:#202B31;">删除</span>
                     </label>
                   </div>
                </li>
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
                   <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>用户类型：</label>
                    <div class="select">
                      <input id="treevalue" class="ipt" name="user.userType.typeName" type="text" readonly="true" value="${user.userType.typeName}" 
                          onfocus="showMenu('${path}/page/common/tree.jsp','userTypeTree','treevalue','treeid'); return false;"/>
                      <input id="treeid" type="hidden" name="user.userType.id" value="${user.userType.id}"/>
                      <div id="treeDiv" class="menuContent"><ul id="treeContent" class="ztree" style="width:250px;"/></div>
                      <span class="errortip">
                       <s:fielderror ><s:param>user.userType.id</s:param></s:fielderror>
                      </span>
                    </div>
                  </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem">
                      <label class="lbl-ipt-tit"><span class="star">*</span>密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码：</label>
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
                <li class="inputList-li">
                   <div class="listItem">
                      <label class="lbl-ipt-tit">手机号码：</label>
                      <input name="user.mobile" value="${user.mobile}" class="ipt"/>
                      <span class="errortip" name="errortip">
                         <s:fielderror ><s:param>user.mobile</s:param></s:fielderror>
                      </span>
                   </div>
                   <div class="listItem txtarea">
                      <label class="lbl-ipt-tit">电子邮箱：</label>
                      <input type="text" name="user.email" class="ipt" value="${user.email}"/> 
                      <span class="errortip" name="errortip">
                          <s:fielderror ><s:param>user.email</s:param></s:fielderror>
                       </span>
                   </div>
                </li>
                <li class="inputList-li">
                <div class="listItem">
                     <label class="lbl-ipt-tit">医疗卡号：</label>
                      <input type="text" name="user.medicalCard" class="ipt" value="${user.medicalCard}"/>
                      <span class="errortip" name="errortip">
                         <s:fielderror ><s:param>user.medicalCard</s:param></s:fielderror>
                      </span>
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
                        <input type="text" class="ipt" name="user.userExt.realName" value="${user.userExt.realName}" />
                        <span class="errortip" name="errortip">
                            <s:fielderror ><s:param>user.userExt.realName</s:param></s:fielderror>
                        </span>
                    </div>
                    <div class="listItem txtarea">
                      <label class="lbl-ipt-tit">性别：</label>
                      <label class="lbl-ipt-tit radioWrap">
                         <input name="user.userExt.gender" type="radio" value="man" <c:if test="${user.userExt.gender == 'man'}">checked</c:if>/>男士&nbsp;&nbsp;
                      </label><label class="lbl-ipt-tit radioWrap">
                         <input name="user.userExt.gender" type="radio" value="madam" <c:if test="${user.userExt.gender == 'madam'}">checked</c:if>/>女士&nbsp;&nbsp;
                      </label><label class="lbl-ipt-tit radioWrap">
                         <input name="user.userExt.gender" type="radio" value="secret" <c:if test="${user.userExt.gender == 'secret'}">checked</c:if>/>保密
                      </label>
                    </div>
                </li>
               <li class="inputList-li">
                  <div class="listItem txtarea">
                     <label class="lbl-ipt-tit"> 身份证号码：</label>
                     <input type="text" name="user.userExt.identityCard" class="ipt" value="${user.userExt.identityCard}"/>
                     <span class="errortip" name="errortip">
                         <s:fielderror ><s:param>user.userExt.identityCard</s:param></s:fielderror>
                      </span>
                  </div>
                  <div class="listItem txtarea">
                  <label class="lbl-ipt-tit">生日：</label>
                  <input name="user.userExt.birthday" value='<fmt:formatDate value="${user.userExt.birthday}" pattern="yyyy-MM-dd"/>' class="ipt" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
                  <span class="errortip" name="errortip">
                      <s:fielderror ><s:param>user.userExt.birthday</s:param></s:fielderror>
                  </span>
                  </div>
               </li>
               <li class="inputList-li">
                  <div class="listItem txtarea">
                     <label class="lbl-ipt-tit">身份证照片：</label>
                     <div id="idCardTd"></div>
                  </div>
                  <div class="listItem txtarea">
                     <label class="lbl-ipt-tit">头像：</label>
                     <div id="userImgTd"></div>
                  </div>
                  <script type="text/javascript">
                         loadingImageButton("idCardTd",'${user.userExt.idCardImg}',"user.userExt.idCardImg",false,1)
                         loadingImageButton("userImgTd",'${user.userExt.userImg}',"user.userExt.userImg",false,2)
                  </script>
               </li>
               <li class="inputList-li">
                  <div class="listItem txtarea">
                     <label class="lbl-ipt-tit">QQ号码：</label>
                     <input type="text" name="user.userExt.qq" class="ipt" value="${user.userExt.qq}"/>
                     <span class="errortip" name="errortip">
                         <s:fielderror ><s:param>user.userExt.qq</s:param></s:fielderror>
                      </span>
                  </div>
                   <div class="listItem txtarea">
                     <label class="lbl-ipt-tit">MSN账号：</label>
                      <input type="text" name="user.userExt.msn" class="ipt" value="${user.userExt.msn}"/>
                      <span class="errortip" name="errortip">
                         <s:fielderror ><s:param>user.userExt.msn</s:param></s:fielderror>
                      </span>
                  </div>
               </li>
               <li class="inputList-li">
                  <div class="listItem txtarea">
                     <label class="lbl-ipt-tit">区域：</label>
                     <input id="keyId" class="ipt" name="areaName" type="text" readonly="true" value="${areaName}" 
                      onfocus="showMenu('${path}/page/common/tree.jsp','areaTree','keyId','valueId'); return false;"/>
                     <input id="valueId" type="hidden" name="user.userExt.comefrom" value="${user.userExt.comefrom}"/>
                     <div id="treeDiv" class="menuContent"><ul id="treeContent" class="ztree" style="width:300px;height: 300px;"></ul></div>
                     <span class="errortip" name="errortip">
                         <s:fielderror ><s:param>user.userExt.comefrom</s:param></s:fielderror>
                     </span>
                  </div>
               </li>
               <c:if test="${edit}">   
                  <li class="inputList-li">
                     <div class="listItem txtarea">
                        <label class="lbl-ipt-tit">注册信息：</label>
                        <span class="txt">
                        <fmt:formatDate value="${user.registerTime}" pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;&nbsp;${user.registerIp}</td>
                        </span>
                        <input type="hidden" name="user.registerTime" value="${user.registerTime}"/>
                        <input type="hidden" name="user.registerIp" value="${user.registerIp}"/>
                     </div>
                      <div class="listItem txtarea">
                        <label class="lbl-ipt-tit">登录信息：</label>
                        <span class="txt">
                         <fmt:formatDate value="${user.lastLoginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;&nbsp;${user.lastLoginIp}
                        </span>
                        <input type="hidden" name="user.lastLoginTime" value="${user.lastLoginTime}"/>
                        <input type="hidden" name="user.lastLoginIp" value="${user.lastLoginIp}"/>
                        <input type="hidden" name="user.loginCount" value="${user.loginCount}"/>
                     </div>
                  </li>
               </c:if>
                <li class="inputList-li">
                  <div class="listItem txtarea">
                     <label class="lbl-ipt-tit">自我介绍：</label>
                   </div>
                 </li>
               <li class="inputList-li">
                  <div class="listItem txtarea">
                     <textarea id="intro" name="user.userExt.intro" class="formTextediter">${user.userExt.intro}</textarea>
                     <script type="text/javascript">
                  		    var editor1;KindEditor.ready(function(K) {editor1 = K.create('textarea[id="intro"]');});    
                     </script>
                     <span class="errortip" name="errortip">
                        <s:fielderror ><s:param>user.userExt.intro</s:param></s:fielderror>
                     </span>
                      <div style="display:none">
			          		<textarea name="editorPrevImg">${user.userExt.intro}</textarea>
			        	</div>
                  </div>
               </li>
               </ul>
              </div>
           </div>
         <div class="buttonArea">
            <input class="formButton" value="确  定" type="button" onclick="submits(this.from,editor1);">&nbsp;&nbsp;
            <input class="formButton" onclick="window.location='${path}/admin/user!list.action'" value="返  回" type="button">
        </div>
        </form>
      </div>
   </body>
<script type="text/javascript">  
        displayDiv('typeBtn','typeDiv','${errors}');
        displayDiv('userExtBtn','userExtDiv','${errors}');
</script>
</html>