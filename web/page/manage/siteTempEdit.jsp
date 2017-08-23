<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
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
        <c:if test="${edit}">站点模板编辑</c:if>
        <c:if test="${!edit}">站点模板添加</c:if>
        </h3>
    </div>
<div class=body>
<jsp:include page="../../page/include/image_upload.jsp"></jsp:include>
    <form id="siteCOrU" name="siteCOrU" method="post" action="${path}/admin/site!saveTempurl.action">
        <c:if test="${!empty site}">
        <input type="hidden" name="site.id" value="${site.id}">
        <input type="hidden" name="siteInfo" value="${siteInfo}">
        <input type="hidden" name="edit" value="${edit}">
        </c:if>
        <input type="hidden" name="token" value="${token}"/>
        <div class="divInputTable">
            <h3 class="main-tit-bar" id="typeBtn">基本信息</h3>
            <div id="typeDiv">
            <ul class="inputList">
                <li class="inputList-li">
                   <div class="listItem" style="display:none;">
	                  <label class="lbl-ipt-tit"><span class="star">*</span>状&nbsp;&nbsp;&nbsp;&nbsp;态：</label>
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
	                  <span class="errortip">
	                      <s:fielderror ><s:param>site.status</s:param></s:fielderror>
	                  </span>
                   </div>
                   <div class="listItem">
                     <label class="lbl-ipt-tit"><span class="star">*</span>会员等级：</label>
                     <div class="select">
	                       <select id="gradeId" name="site.grade.id">
	                       <c:forEach var="g" items="${gradeList}">
	                           <option value="${g.id}" <c:if test="${g.id == site.grade.id}">selected="selected"</c:if>>${g.gradeName}</option>
	                       </c:forEach>
	                     </select>
                     </div>                   
                     <span class="errortip">
                         <s:fielderror><s:param>site.grade.id</s:param></s:fielderror>
                     </span>
                  </div>
                </li>                
                <li class="inputList-li">
                  <div class="listItem">
                     <label class="lbl-ipt-tit"><span class="star">*</span>模板名称：</label>
                     <div class="select">
	                     <select id="site_template_id" name="site.template.id" <c:if test="${!edit || site.template.id!=null}">disabled="disabled"</c:if>>
	                         <option value="0">--请选择--</option>
	                         <c:forEach var="t" items="${templateList}">
	                             <option value="${t.id}" <c:if test="${t.id == site.template.id}">selected="selected"</c:if>>${t.tempName}</option>
	                         </c:forEach>
                        </select>
                     </div>
                     <span class="errortip">
                         <s:fielderror><s:param>site.template.id</s:param></s:fielderror>
                     </span>
                  </div>
               </li>   
            </ul>
           </div>
        </div>
         <div class="buttonArea">
	         <c:if test="${empty site.template.id}">
	            <input class="formButton" value="确  定" type="button" onclick="submits(this.form);">&nbsp;&nbsp;
	         </c:if>
            <c:if test="${!siteInfo}">
            	<input class="formButton" onclick="window.location='${path}/admin/site!list.action'" value="返  回" type="button">
        	</c:if>
        </div>
        </form>
      </div>
   </body>
<script type="text/javascript">  
        $(document).ready(function(data){
        	var isInit='${isInit}';
        	if(isInit=='true'){
        		var r=confirm("您属于初次建站用户！是否立即生成默认栏目，以及配置信息？");
        		if(r){
        			var tempId=$("#site_template_id").val();
		 			if(tempId==0) return;
		 			getParentFrame().alertMaskDiv("数据初始化中……",'${path}');
		 			$.ajax({
					  type: "POST",
					  url: "${path}/admin/initChannel!init.action",
					  dataType: "json",
					  success: function(data){
					     var msg=data.msg;
		 				 if(data.exception!=null && data.exception!=undefined)
		 					msg+=data.exception;
		 				getParentFrame().changeMaskDiv("loadingWordText",msg,'${path}');
		 			 },
	 				 timeout:300000
					}); 
        		}
        	}
        });
</script>
</script>
</html>