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
        <jsp:include page="../../page/include/headEdit.jsp"></jsp:include>
    </head>
    <body class="input">
    <div class="main-top-hd clearfix">
        <h3 class="cur">
        <c:if test="${edit}">站点编辑</c:if>
        <c:if test="${!edit}">站点添加</c:if>
        </h3>
    </div>
<div class=body>
<jsp:include page="../../page/include/f_upload.jsp"></jsp:include>
    <form id="siteCOrU" name="siteCOrU" method="post" action="${path}/admin/site!save.action">
        <c:if test="${!empty site}">
        <input type="hidden" name="site.id" value="${site.id}">
        <input type="hidden" name="siteInfo" value="${siteInfo}">
        <input type="hidden" name="site.version" value="${site.version}">
        <input type="hidden" name="edit" value="${edit}">
        <input type="hidden" name="site.createTime" value="${site.createTime}"/>
        <input type="hidden" name="site.domain" value="${site.domain}"/>
        <input type="hidden" name="site.domainPass" value="${site.domainPass}"/>
        <input type="hidden" name="site.creater" value="${site.creater}"/>
        <input type="hidden" name="site.comId" value="${site.comId}"/>
        <input type="hidden" name="site.template.id" value="${site.template.id}"/>
        </c:if>
        <input type="hidden" name="token" value="${token}"/>
        <div class="divInputTable">
            <h3 class="main-tit-bar" id="typeBtn">基本信息</h3>
            <div id="typeDiv">
            <ul class="inputList">
                <li class="inputList-li">
                   <div class="listItem">
	                  <label class="lbl-ipt-tit"><span class="star">*</span>状&nbsp;&nbsp;&nbsp;&nbsp;态：</label>
                       
                       <c:if test="${empty sessionScope._hospital and sessionScope._site.comType eq 'mainstation'}">
                        <c:forEach items="${disableStatus}" var="item" varStatus="e">
                           <c:if test="${item.key eq site.status}">
                              <input name="site.status" value="${site.status}" type="hidden"/>
                              <span class="txt">${item.value}</span>
                              <c:set var="disab" value="true"></c:set>
                           </c:if>
                       </c:forEach>
                       <c:if test="${empty disab}">
                            <c:forEach items="${availableList}" var="item" varStatus="e">
                                <label class="lbl-ipt-tit radioWrap">
                                    <input type="radio" name="site.status" value="${item.key}" 
                                    <c:if test="${site.status eq item.key}">checked </c:if> />
                                       ${item.value}
                                </label>
                            </c:forEach>
                       </c:if>
                       </c:if>
                       <c:if test="${!empty sessionScope._hospital or sessionScope._site.comType eq 'substation'}">
                            <input type="hidden" name="site.status" value="${site.status}"/>
                            <span class="txt">
                                <c:forEach items="${availableList}" var="item" varStatus="e">
                                    <c:if test="${site.status eq item.key}">${item.value}</c:if>
                                </c:forEach>
                            </span>
                       </c:if>
	                  <span class="errortip">${errors['site.status'][0]}</span>
                   </div>
                   <div class="listItem">
                     <label class="lbl-ipt-tit"><span class="star">*</span>会员等级：</label>
                     <div class="select">
	                     <select id="gradeId" name="site.grade.id" onchange="changeSelect('${path}/admin/site!changeSelect.action','gradeId','tempUrl')">
	                       <option value="0">--请选择--</option>
	                       <c:forEach var="g" items="${gradeList}">
	                           <option value="${g.id}" <c:if test="${g.id == site.grade.id}">selected="selected"</c:if>>${g.gradeName}</option>
	                       </c:forEach>
	                     </select>
                     </div>
                     <span class="errortip">${errors['site.grade.id'][0]}</span>
                  </div>
                </li>
                <li class="inputList-li">
                <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>组织类型：</label>
                    <div class="select">
                    <span class="txt">
                      <c:forEach items="${compTypeList}" var="comp" varStatus="e">
                       ${comp.value}
                      </c:forEach>
                      <input type="hidden" name="site.comType" value="${site.comType}"/>
                      </span>
                      <span class="errortip">${errors['site.comType'][0]}</span>
                    </div>
                  </div>
                   <%--<div class="listItem">
                      <label class="lbl-ipt-tit"><span class="star">*</span> 组织ID：</label>
                      <span class="txt">${site.comId}</span>
                   </div>
                --%>
                <div class="listItem">
                     <label class="lbl-ipt-tit"><span class="star">*</span>模板名称：</label>
                     <span class="txt">
                    	<c:forEach var="t" items="${templateList}">
                             <c:if test="${t.id == site.template.id}">${t.tempName}</c:if>
                         </c:forEach>
                     </span>
                     <span class="errortip">${errors['site.tempUrl'][0]}</span>
                  </div>
                </li>               
                <li class="inputList-li">
                <div class="listItem">
	                  <label class="lbl-ipt-tit"><span class="star">*</span>站点名称：</label>
	                  <input type="text" class="ipt" name="site.siteName" value="${site.siteName}"  maxlength="100" />
	                  <span class="errortip">${errors['site.siteName'][0]}</span>
                   </div>
                   <div class="listItem">
                     <label class="lbl-ipt-tit">站点域名：</label>
                     <span class="txt">${site.domain}${subDomain}</span>
                  <%--<input type="text" class="ipt" name="site.domain" value="${site.domain}"  maxlength="50" />
                  --%></div>
                </li>
                <li class="inputList-li">
                <div class="listItem">
                      <label class="lbl-ipt-tit">站点简称：</label>
                      <input type="text" class="ipt" name="site.shortName" value="${site.shortName}"  maxlength="50" />
                      <span class="errortip">${errors['site.shortName'][0]}</span>
                   </div>
                <div class="listItem">
                      <label class="lbl-ipt-tit">其他域名：</label>
                      <input type="text" class="ipt" name="site.alias" value="${site.alias}"/>
                      <span class="errortip">${errors['site.alias'][0]}</span>
                   </div>
                </li>   
               <li class="inputList-li">
                   <div class="listItem listItem-full-width">
                      <label class="lbl-ipt-tit">LOGO：</label>
                  <div id = "logoImg" ></div>
                  <script type="text/javascript">
                         upload.btn("logoImg",'${site.logoImg}',"site.logoImg",false);
                  </script>
                      <span class="errortip">${errors['site.logoImg'][0]}</span>
                   </div>
                </li>
                <li  class="inputList-li">
                  <div class="listItem">
                       <label class="lbl-ipt-tit">站点总空间：</label>
                       <c:if test="${mainStation}">
                       <input type="text" class="search-input-bar" name="site.siteSize" value="${site.siteSize}"/><span class="txt">Byte</span>
                       </c:if>
                       <c:if test="${!mainStation}">
                       <input type="hidden" name="site.siteSize" value="${site.siteSize}"/>
                       </c:if>
                       <span class="txt"><c:if test="${mainStation}">&nbsp;&nbsp;&nbsp;&nbsp;</c:if>${site.siteSize/(1024*1024)}MB</span>
                       <span class="errortip">${errors['site.siteSize'][0]}</span>
                   </div>
                   <div class="listItem">
                       <label class="lbl-ipt-tit">站点已用空间：</label>
                       <span class="txt"><fmt:formatNumber value="${site.siteUsedSize/(1024*1024)}" pattern="##############.###"></fmt:formatNumber>MB</span>
                   </div>
                </li>  
                <li  class="inputList-li">
                   <div class="listItem">
                       <label class="lbl-ipt-tit">站点剩余空间：</label>
                       <span class="txt"><fmt:formatNumber value="${(site.siteSize-site.siteUsedSize)/(1024*1024)}" pattern="##############.###"></fmt:formatNumber>MB</span>
                       <input type="hidden" name="site.siteUsedSize" value="${site.siteUsedSize}">
                   </div>
                </li>  
                <c:if test="${subStation || mainStation}">
	                <li  class="inputList-li">
	              	  <div class="listItem">
		                   <label class="lbl-ipt-tit">排序：</label>
		                   <input type="text" class="ipt" name="site.siteSort" value="${site.siteSort}" maxlength="5" onkeyup="intFormat(this,1,99);"/>
		                   <span class="errortip">${errors['site.siteSort'][0]}</span>
	                   </div>
	                </li>  
	            </c:if>
	            <c:if test="${!subStation && !mainStation}">
	            	<input type="hidden" name="site.siteSort" value="${site.siteSort}"/>
	            </c:if>
            </ul>
           </div>
        </div>
         <div class="buttonArea">
            <input class="formButton" value="确  定" type="button" onclick="submits(this.form);">&nbsp;&nbsp;
            <c:if test="${!siteInfo}">
            <input class="formButton" onclick="window.location='${path}/admin/site!list.action'" value="返  回" type="button">
        	</c:if>
        </div>
        </form>
        <jsp:include page="../../page/include/headEdit.jsp"></jsp:include>
      </div>
   </body>
<script type="text/javascript">  
        displayDiv('typeBtn','typeDiv','${errors}');
</script>
</html>