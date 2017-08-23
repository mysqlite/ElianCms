<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="buttonArea">
	<input type="hidden" id="contentStatus" name="status" value="${status}"/>
	<input type="hidden" id="controlId" name="controlId" value="${controlId}"/>
	<input type="hidden" id="publish" name="publish" value="${publish}"/>
	<input type="hidden" id="contentCheck" name="contentCheck" value="${contentCheck}"/>
	<input type="hidden" id="contentGenerate" name="contentGenerate" value="${contentGenerate}"/>
	<script type="text/javascript">
		function submitDraft(form, editor1, status,publish){
			document.getElementById("contentStatus").value = status;
			G("publish").value=publish;
			submits(form,editor1);
		}
	</script>
	<c:if test="${!edit}">
		<input class="formButton" value="存草稿" type="button" onclick="submitDraft(this.form,editor1, 0);">&nbsp;&nbsp;
	</c:if>
	<c:if test="${contentCheck}">
	   <input class="formButton" value="保存且通过" type="button" onclick="submitDraft(this.form,editor1, 3);">&nbsp;&nbsp;
     </c:if>
     <c:if test="${contentGenerate}">
       <input class="formButton" value="保存且发布" type="button" onclick="submitDraft(this.form,editor1, 3,true);">&nbsp;&nbsp;
     </c:if>
	<input class="formButton" value="保   存" type="button" <c:if test="${!edit}"> onclick="submitDraft(this.form,editor1, 1);"  </c:if>
		<c:if test="${edit}"> onclick="submits(this.form,editor1);" </c:if>>&nbsp;&nbsp;
    <input class="formButton" onclick="window.location='${path}/admin/content!list.action?leaf=${leaf}&channelId=${channelId}&action=${action}&status=${status}'" value="返  回" type="button">
</div>
