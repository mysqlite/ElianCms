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
	    <c:if test="${edit}">人才招聘编辑</c:if>
	    <c:if test="${!edit}">人才招聘添加</c:if>
	    </h3>
	</div>
<div class=body>
    <form id="jobCOrU" name="jobCOrU" method="post" action="${path}/admin/${action}!save.action">
        <input type="hidden" name="job.id" value="${job.id}">
        <input type="hidden" name="job.createTime" value="${job.createTime}">
        <input type="hidden" name="job.version" value="${job.version}">
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
                    <label class="lbl-ipt-tit"><span class="star">*</span>招聘标题：</label>
                    <input type="text" class="ipt" name="job.title" value="${job.title}"/>
                    <span class="errortip">${errors['job.title'][0]}</span>
                   </div>
                   <div class="listItem">
                     <label class="lbl-ipt-tit"><span class="star">*</span>是否启用：</label>
                     <label class="lbl-ipt-tit radioWrap">
                         <input type="radio" name="job.disable" value="true"<c:if test="${job.disable}"> checked</c:if> />是
                     </label>
                     <label class="lbl-ipt-tit radioWrap">
                         <input type="radio" name="job.disable" value="false"<c:if test="${!job.disable}">checked</c:if> />否
                     </label>
                   </div>
                </li>
                <li class="inputList-li">
                  <div class="listItem">
                    <label class="lbl-ipt-tit">关键字：</label>
                    <input type="text" class="ipt" name="job.keywords" value="${job.keywords}" />
                    <span class="errortip">${errors['job.keywords'][0]}</span>
                  </div>
                  <div class="listItem txtarea">
                      <label class="lbl-ipt-tit">性别要求：</label>
                      <c:forEach var="sex" items="${sexList}" varStatus="e">
                      <label class="lbl-ipt-tit radioWrap">
                         <input name="job.gender" type="radio" value="${sex.key}" 
                             <c:if test="${job.gender eq sex.key or empty job.gender}">checked</c:if>/>${sex.value}
                        </label>
                      </c:forEach>
                    </div>
                </li>
                <li class="inputList-li">
                  <div class="listItem">
                    <label class="lbl-ipt-tit">来源名称：</label>
                      <input type="text" class="ipt" name="job.sourceName" value="${job.sourceName}" />
                      <span class="errortip">${errors['job.sourceName'][0]}</span>
                  </div>
                  <div class="listItem">
                    <label class="lbl-ipt-tit">来源路径：</label>
                      <input type="text" class="ipt" name="job.sourceUrl" value="${job.sourceUrl}" />
                      <span class="errortip">${errors['job.sourceUrl'][0]}</span>
                  </div>
                </li>
                 <li class="inputList-li">
                  <div class="listItem">
                    <label class="lbl-ipt-tit"> 用人部门：</label>
                      <input type="text" class="ipt" name="job.servantDepa" value="${job.servantDepa}" />
                      <span class="errortip">${errors['job.servantDepa'][0]}</span>
                  </div>
                  <div class="listItem">
                    <label class="lbl-ipt-tit">职位名称：</label>
                      <input type="text" class="ipt" name="job.jobName" value="${job.jobName}" />
                      <span class="errortip">${errors['job.jobName'][0]}</span>
                  </div>
                </li>
                 <li class="inputList-li">
                  <div class="listItem">
                    <label class="lbl-ipt-tit"> 联系人：</label>
                      <input type="text" class="ipt" name="job.contacter" value="${job.contacter}" />
                      <span class="errortip">${errors['job.contacter'][0]}</span>
                  </div>
                  <div class="listItem">
                    <label class="lbl-ipt-tit">联系电话：</label>
                      <input type="text" class="ipt" name="job.contactPhone" value="${job.contactPhone}" />
                      <span class="errortip">${errors['job.contactPhone'][0]}</span>
                  </div>
                </li>
                <li class="inputList-li">
                  <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>工作性质：</label>
                      <c:forEach var="item" items="${jobNatureList}" varStatus="e">
	                     <label class="lbl-ipt-tit radioWrap">
	                         <input type="radio" name="job.jobNature" value="${item.key}" <c:if test="${job.jobNature eq item.key}"> checked</c:if> />${item.value}
	                     </label>
                      </c:forEach>
                      <span class="errortip">${errors['job.jobNature'][0]}</span>
                  </div>
                  <div class="listItem">
                    <label class="lbl-ipt-tit">招聘人数：</label>
                      <input type="text" class="ipt" name="job.hireNum" value="${job.hireNum}" />
                      <span class="errortip">${errors['job.hireNum'][0]}</span>
                  </div>
                </li>
                <li class="inputList-li">
                  <div class="listItem">
                    <label class="lbl-ipt-tit">提供月薪：</label>
                      <input type="text" class="ipt" name="job.salary" value="${job.salary}" />
                      <span class="errortip">${errors['job.salary'][0]}</span>
                  </div>
                  <div class="listItem txtarea">
                     <label class="lbl-ipt-tit"><span class="star">*</span>工作地点：</label>
                     <input id="keyId" class="ipt" name="areaName" type="text" readonly="true" value="${!empty areaName?areaName:'全国'}" 
                      onfocus="showMenuArea('treeContent','keyId','valueId','${path}'); return false;"
                      onkeydown="BackSpace('全国','keyId','valueId');"/>
                     <input id="valueId" type="hidden" name="job.areaId" value="${!empty job.areaId?job.areaId:0}"/>
                     <div id="treeDiv" class="menuContent"><ul id="treeContent" class="ztree" style="width:300px;height: 300px;"></ul></div>
                     <span class="errortip">${errors['job.areaId'][0]}</span>
                  </div>
                </li>
                 <li class="inputList-li">
                    <div class="listItem txtarea">
                        <label class="lbl-ipt-tit">摘要：</label>
                       <textarea name="job.description"  class="formTextarea valid">${job.description}</textarea>
                         <span class="errortip">${errors['job.jobDesc'][0]}</span>
                    </div>
                    <div class="listItem txtarea">
                        <label class="lbl-ipt-tit">专业要求：</label>
                       <textarea name="job.majorRequ" class="formTextarea valid">${job.majorRequ}</textarea>
                         <span class="errortip">${errors['job.majorRequ'][0]}</span>
                    </div>
                </li>
                 <li class="inputList-li">
                  <div class="listItem">
                    <label class="lbl-ipt-tit">住宿情况：</label>
                     <textarea name="job.housing" class="formTextarea valid">${job.housing}</textarea>
                      <span class="errortip">${errors['job.housing'][0]}</span>
                  </div>
                  <div class="listItem">
                     <label class="lbl-ipt-tit">语言要求：</label>
                     <textarea id="language" name="job.language"  class="formTextarea valid">${job.language}</textarea>
                     <span class="errortip">${errors['job.language'][0]}</span>
                  </div>
                </li>
                 <li class="inputList-li">
                  <div class="listItem">
                    <label class="lbl-ipt-tit">工作经验要求：</label>
                     <textarea name="job.workExpe" class="formTextarea valid">${job.workExpe}</textarea>
                      <span class="errortip">${errors['job.workExpe'][0]}</span>
                  </div>
                </li>
                <li class="inputList-li">
                  <div class="listItem">
                     <label class="lbl-ipt-tit"><span class="star">*</span>学历要求：</label>
                     <div class="select">
                          <select name="job.education">${job.education}
                          <c:forEach var="item" items="${educationList}" varStatus="e">
                             <option value="${item.key}" <c:if test="${item.key eq job.education}"> selected="selected" </c:if> >${item.value}</option>
                          </c:forEach>
                          </select>
                     </div>
                     <span class="errortip">${errors['job.education'][0]}</span>
                  </div>
                  <div class="listItem">
                     <label class="lbl-ipt-tit">年龄要求：</label>
                     <input type="text" class="ipt" name="job.ageRange" value="${!empty job.ageRange?job.ageRange:'不限'}" />
                     <span class="errortip">${errors['job.ageRange'][0]}</span>
                  </div>
                </li>
                <li class="inputList-li">
                 <div class="listItem">
                  <label class="lbl-ipt-tit"><span class="star">*</span>有效期至：</label>
                  <input name="job.expireTime" value='<fmt:formatDate value="${job.expireTime}" pattern="yyyy-MM-dd"/>' class="ipt" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
                  <span class="errortip">${errors['job.expireTime'][0]}</span>
                  </div>
                  <div class="listItem">
                     <label class="lbl-ipt-tit"><span class="star">*</span>行业类型：</label>
                      <div class="select">
                         <select class="listbar-sel" name="job.industry.id">
                             <option value="">--请选择--</option>
                             <c:forEach var="item" items="${jobIndustryList}">
                                 <option value="${item.id}" <c:if test="${job.industry.id == item.id}">selected="selected"</c:if>>${item.industryName}</option>
                             </c:forEach>
                         </select>
                     </div>
                      <span class="errortip">${errors['job.industry'][0]}</span>
                  </div>
                </li>
                <li class="inputList-li">
                  <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>创建人：</label>
                      <input type="text" class="ipt" name="job.createrId.id" value="${!empty job.createrId.id?job.createrId.realName:_user.realName}" disabled="disabled"/>
                      <span class="errortip">${errors['job.createrId.id'][0]}</span>
                  </div>
                  <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>创建时间：</label>
                      <input type="text" class="ipt" name="job.createTime" disabled="disabled" value="<fmt:formatDate value="${job.createTime}" pattern="yyyy-MM-dd HH:mm"/>" />
                      <span class="errortip">${errors['job.createTime'][0]}</span>
                  </div>
                </li>
                <li class="inputList-li">
                  <div class="listItem txtarea">
                     <label class="lbl-ipt-tit">岗位要求：</label>
                     <textarea id="jobRequ" name="job.jobRequ" class="formTextediter">${job.jobRequ}</textarea>
                     <script>
                      var editor1;KindEditor.ready(function(K) {editor1 = K.create('textarea[id="jobRequ"]');});    
                     </script>
                     <span class="errortip">${errors['job.jobRequ'][0]}</span>
                     <div style="display:none">
			          		<textarea name="editorPrevImg">${job.jobRequ}</textarea>
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