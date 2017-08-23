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
	    <c:if test="${edit}">求购编辑</c:if>
	    <c:if test="${!edit}">求购添加</c:if>
	    </h3>
	</div>
<div class=body>
    <form id="purchaseCOrU" name="purchaseCOrU" method="post" action="${path}/admin/${action}!save.action">
      <input type="hidden" name="purchase.id" value="${purchase.id}">
      <input type="hidden" name="purchase.version" value="${purchase.version}">
      <input type="hidden" name="purchase.createTime" value="${purchase.createTime}">
      <input type="hidden" name="token" value="${token}"/>
      <input type="hidden" name="leaf" value="${leaf}"/>
      <input type="hidden" name="channelId" value="${channelId}"/>
      <input type="hidden" name="action" value="${action}"/>
      <input type="hidden" name="edit" value="${edit}"/>
       
	<div class="divInputTable">
            <h3 class="main-tit-bar" id="typeBtn">基本信息</h3>
            <div id="typeDiv">
            <ul class="inputList">
                <li class="inputList-li">
                   <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>产品名称：</label>
                    <input type="text" class="ipt" name="purchase.name" value="${purchase.name}"/>
                    <span class="errortip">${errors['purchase.name'][0]}</span>
                   </div>
                   <div class="listItem">
                     <label class="lbl-ipt-tit"><span class="star">*</span>是否启用：</label>
                     <label class="lbl-ipt-tit radioWrap">
                         <input type="radio" name="purchase.disable" value="true"<c:if test="${purchase.disable}"> checked</c:if> />是
                     </label>
                     <label class="lbl-ipt-tit radioWrap">
                         <input type="radio" name="purchase.disable" value="false"<c:if test="${!purchase.disable}">checked</c:if> />否
                     </label>
                   </div>
                </li>
                <li class="inputList-li">
                  <div class="listItem">
                    <label class="lbl-ipt-tit">关键字：</label>
                    <input type="text" class="ipt" name="purchase.keywords" value="${purchase.keywords}" />
                    <span class="errortip">${errors['purchase.keywords'][0]}</span>
                  </div>
                </li>
                <li class="inputList-li">
                  <div class="listItem">
                    <label class="lbl-ipt-tit">来源名称：</label>
                      <input type="text" class="ipt" name="purchase.sourceName" value="${purchase.sourceName}" />
                      <span class="errortip">${errors['purchase.sourceName'][0]}</span>
                  </div>
                  <div class="listItem">
                    <label class="lbl-ipt-tit">来源路径：</label>
                      <input type="text" class="ipt" name="purchase.sourceUrl" value="${purchase.sourceUrl}" />
                      <span class="errortip">${errors['purchase.sourceUrl'][0]}</span>
                  </div>
                </li>
                 <li class="inputList-li">
                  <div class="listItem">
                    <label class="lbl-ipt-tit"> 求购单价：</label>
                      <input type="text" class="ipt" name="purchase.price" value="${purchase.price}" />
                      <span class="errortip">${errors['purchase.price'][0]}</span>
                  </div>
                  <div class="listItem">
                    <label class="lbl-ipt-tit">求购数量：</label>
                      <input type="text" class="ipt" name="purchase.number" value="${purchase.number}" />
                      <span class="errortip">${errors['purchase.number'][0]}</span>
                  </div>
                  </li>
                 <li class="inputList-li">                  
                  <div class="listItem">
                    <label class="lbl-ipt-tit">规格：</label>                    
                   	 <textarea name="purchase.specification" class="formTextarea valid">${purchase.specification}</textarea>
                     <span class="errortip">${errors['purchase.specification'][0]}</span>                      
                  </div>
                  <div class="listItem">
	                    <label class="lbl-ipt-tit">摘要：</label>
	                    <textarea name="purchase.description" class="formTextarea valid">${purchase.description}</textarea>
	                         <span class="errortip">${errors['purchase.description'][0]}</span>  
	                  </div>      
                </li>
                <li class="inputList-li">
                	<div class="listItem">
                    <label class="lbl-ipt-tit">产品详情：</label>                   
                  	</div>
				</li>	
									
				<li class="inputList-li">	
					<div class="listItem txtarea">
	                     <textarea id="purchaseDesc" name="purchase.desc" class="formTextediter">${purchase.desc}</textarea>
	                     <script>
	                      var editor1;KindEditor.ready(function(K) {editor1 = K.create('textarea[id="purchaseDesc"]');});    
	                     </script>
	                     <span class="errortip">${errors['purchase.desc'][0]}</span>
	                     <div style="display:none">
				          		<textarea name="editorPrevImg">${purchase.desc}</textarea>
				         </div>
                 	</div>
                </li>                
                
                <li class="inputList-li">                 
                  <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>标题：</label>
                      <input type="text" class="ipt" name="purchase.title" value="${purchase.title}" />
                      <span class="errortip">${errors['purchase.title'][0]}</span>
                  </div>
                   <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>联系人：</label>
                    <div class="select">
                          <select name="purchase.contacter.id">
                            <c:forEach var="item" items="${contacterList}" varStatus="e">
                             <option  value="${item.id}" <c:if test="${item.id eq purchase.contacter.id}"> selected="selected" </c:if>>${item.contactName}|${item.contacter}</option>
                            </c:forEach>
                          </select>
                     </div>
                     <span class="errortip">${errors['purchase.contacter.id'][0]}</span>
                  </div>
                  </li>    
                <li class="inputList-li">
                 <div class="listItem">
                     <label class="lbl-ipt-tit"><span class="star">*</span>求购区域：</label>
                     <input id="areaId" class="ipt" name="areaName" type="text" readonly="true" value="${!empty areaName?areaName:'全国'}" 
                           onfocus="showMenuArea('treeContent','areaId','areaValueId','${path}');"
                           onkeydown="BackSpace('中国','areaId','areaValueId');" />
                    <input id="areaValueId" type="hidden" name="purchase.areaId" value="${!empty purchase.areaId?purchase.areaId:0}"/>
                    <div id="treeDiv" class="menuContent"><ul id="treeContent" class="ztree" style="width:260px;height: 300px;"></ul></div>
                    <span class="errortip">${errors['purchase.areaId'][0]}</span>
                 </div>
                 <div class="listItem">
                  <label class="lbl-ipt-tit"><span class="star">*</span>有效期至：</label>
                  <input name="purchase.expireTime" value='<fmt:formatDate value="${purchase.expireTime}" pattern="yyyy-MM-dd"/>' class="ipt" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
                  <span class="errortip">${errors['purchase.expireTime'][0]}</span>
                  </div>
                </li>
                <li class="inputList-li">
                  <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>创建人：</label>
                      <input type="text" class="ipt" name="purchase.createrUser.id" value="${!empty purchase.createrUser.id?purchase.createrUser.realName:_user.realName}" disabled="disabled"/>
                      <span class="errortip">${errors['purchase.createrUser.id'][0]}</span>
                  </div>
                  <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>创建时间：</label>
                      <input type="text" class="ipt" name="purchase.createTime" disabled="disabled" value="<fmt:formatDate value="${purchase.createTime}" pattern="yyyy-MM-dd HH:mm"/>" />
                      <span class="errortip">${errors['purchase.createTime'][0]}</span>
                  </div>
                </li>
                <li class="inputList-li">
                  <div class="listItem txtarea">
                     <label class="lbl-ipt-tit">求购须知：</label>
                   </div>
                 </li>
                <li class="inputList-li">
                  <div class="listItem txtarea">
                     <textarea id="purchaseNotice" name="purchase.notice" class="formTextediter">${purchase.notice}</textarea>
                     <script>
                      var editor2;KindEditor.ready(function(K) {editor2 = K.create('textarea[id="purchaseNotice"]');});    
                     </script>
                     <span class="errortip">${errors['purchase.notice'][0]}</span>
                     <div style="display:none">
			          		<textarea name="editorPrevImg">${purchase.notice}</textarea>
			         </div>
                   </div>
                </li>
            </ul>
           </div>
        </div>
        <jsp:include page="contentButton.jsp"></jsp:include>
        </form>
      </div>
   </body>
<script type="text/javascript">  
		displayDiv('typeBtn','typeDiv','${errors}');
</script>
</html>