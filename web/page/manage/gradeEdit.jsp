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
        <jsp:include page="../../page/include/headEdit.jsp"></jsp:include>
    </head>
    <body class="input">
    <div class="main-top-hd clearfix">
        <h3 class="cur">
        <c:if test="${!edit}">等级添加</c:if>
        <c:if test="${edit}">等级修改</c:if>
        </h3>
    </div>
<div class=body>
    <form id="gradeCOrU" name="gradeCOrU" method="post" action="${path}/admin/grade!save.action">
        <input type="hidden" name="grade.id" value="${grade.id}">
        <input type="hidden" name="grade.version" value="${grade.version}">
        <input type="hidden" name="grade.createTime" value="${grade.createTime}"/>
        <input type="hidden" name="edit" value="${edit}">
        <input type="hidden" name="token" value="${token}"/>
        <div class="divInputTable">
            <h3 class="main-tit-bar" id="typeBtn">基本信息</h3>
            <div id="typeDiv">
            <ul class="inputList">
                <li class="inputList-li">
                   <div class="listItem">
                     <label class="lbl-ipt-tit"><span class="star">*</span>是否启用：</label>
                     <label class="lbl-ipt-tit radioWrap">
                         <input id="radioTrue" type="radio" name="grade.disable" value="true" <c:if test="${grade.disable}"> checked="checked" </c:if> />是
                     </label>
                     <label class="lbl-ipt-tit radioWrap">
                         <input type="radio" name="grade.disable" value="false"<c:if test="${!grade.disable}">checked</c:if> />否
                     </label>
                   </div>
                </li>
                 <li class="inputList-li">
                   <div class="listItem">
                     <label class="lbl-ipt-tit"><span class="star">*</span>是否默认：</label>
                     <label class="lbl-ipt-tit radioWrap">
                         <input id="radioTrue" type="radio" name="grade.default" value="true" <c:if test="${grade.default}"> checked="checked" </c:if> />是
                     </label>
                     <label class="lbl-ipt-tit radioWrap">
                         <input type="radio" name="grade.default" value="false"<c:if test="${!grade.default}">checked</c:if> />否
                     </label>
                   </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>等级名称：</label>
                    <input type="text" class="ipt" name="grade.gradeName" value="${grade.gradeName}"  maxlength="20" />
                    <span class="errortip">${errors['grade.gradeName'][0]}</span>
                   </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>等级类型：</label>
                     <div class="select">
                             <select name="grade.comType">
                                <c:forEach var="comp" items="${comTypeList}">
                               <option value="${comp.key}" <c:if test="${comp.key eq grade.comType}">selected="selected"</c:if>>${comp.value}</option>
                                </c:forEach>
                             </select>
                        </div>
                        <span class="errortip">${errors['grade.comType'][0]}</span>
                   </div>
                </li>
                <li class="inputList-li">
                  <div class="listItem">
                    <label class="lbl-ipt-tit">排序：</label>
                    <div class="select">
                      <input type="text" class="ipt" name="grade.gradeSort" value="${grade.gradeSort}" maxlength="5" onkeyup="intFormat(this,1,99);"/>
                      <span class="errortip">${errors['grade.gradeSort'][0]}</span>
                    </div>
                  </div>
                </li>
                
                <li class="inputList-li">
                   <div class="listItem txtarea">
                        <label class="lbl-ipt-tit">等级描述：</label>
                       <textarea name="grade.gradeDesc" class="formTextarea valid">${grade.gradeDesc}</textarea>
                         <span class="errortip">${errors['grade.gradeDesc'][0]}</span>
                    </div>
                </li>
            </ul>
           </div>
        </div>
        <div class="buttonArea">
			<input class="formButton" value="确  定" type="button" onclick="document.gradeCOrU.submit();">&nbsp;&nbsp;
          	<input class="formButton" onclick="window.location='${path}/admin/grade!list.action'" value="返  回" type="button">
		</div>
        </form>
      </div>
   </body>
<script type="text/javascript">  
        displayDiv('typeBtn','typeDiv','${errors}');
</script>
</html>