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
		<title>编辑等级模板</title>
		<jsp:include page="../../page/include/headEdit.jsp"></jsp:include>
		<script type="text/javascript">
			function save(formId) {
				var ids = "";
				var checkboxs = document.getElementsByTagName("input");
				for ( var i = 0,j=0; i < checkboxs.length; i++) {
					var inputName = checkboxs[i].getAttribute("name");
					if (inputName!=null && inputName.indexOf("temp")>-1 && checkboxs[i].checked) {
						if(j>0){
							ids+=",";
						}
						ids+=checkboxs[i].value;
						j++;
					}
				}
				var form = document.getElementById(formId);
				form.action += "?ids=" + ids;
				form.submit();
			}
			
			/**
			 *	选中或者取消所有节点
			 */
			function selectCheckAll(formId, id) {
				var all = document.getElementById(id);
				var form = document.getElementById(formId);
				
				var checkboxs = form.getElementsByTagName("input");
				for ( var i = 0; i < checkboxs.length; i++) {
					var inputName = checkboxs[i].getAttribute("name");
					if (inputName!=null && inputName.indexOf("temp")>-1) {
						checkboxs[i].checked=all.checked;
					}
				}
			}
		</script>
	</head>
<body class="input">
    <div class="main-top-hd clearfix">
        <h3 class="cur">
        <c:if test="${!edit}">等级模板添加</c:if>
        <c:if test="${edit}">等级模板修改</c:if>
        </h3>
    </div>
		<div class="body">
			<form id="editGradeTemplateForm" action="${path}/admin/gradeTemplate!save.action" method="post">
				<input type="hidden" name="edit" value="${edit}">
				<input type="hidden" name="token" value="${token}"/>
				 <div class="divInputTable">
                 <h3 class="main-tit-bar" id="typeBtn">基本信息</h3>
				  <div id="typeDiv">
				  <ul class="inputList">
					<li class="inputList-li">
                        <div class="listItem">
							<label class="lbl-ipt-tit"><span class="star">*</span>等级名称：</label>
							<div class="select">
							<c:if test="${edit}"><input name="grade.id" type="hidden" value="${grade.id}"/></c:if>
							<select name="grade.id" class="listbar-sel" <c:if test="${edit}">disabled="true"</c:if>>
								<option value="">--请选择--</option>
								<c:forEach var="g" items="${gradeList}">
									<option value="${g.id}" <c:if test="${g.id == grade.id}">selected="selected"</c:if>>${g.gradeName}</option>
								</c:forEach>
							</select>
							</div>
							<span class="errortip">${errors['grade.id'][0]}</span>
					   </div>
                    </li>
					<li class="inputList-li">
                        <div class="listItem">
                                <label class="lbl-ipt-tit checkBoxWrap">
                                <span class="star">*</span>模&nbsp;&nbsp;&nbsp;板：
                                <input id="checkAll" type="checkbox" onclick="selectCheckAll('editGradeTemplateForm','checkAll')"/>
                                </label>
								<c:forEach var="temp" items="${templateList}" varStatus="e">
									<label class="lbl-ipt-tit checkBoxWrap">
									   <input name="temp${e.index}" class="ipc" type="checkbox" value="${temp.id}" <c:if test="${temp.selected}">checked="checked"</c:if>/>${temp.tempName}
									</label>
								</c:forEach>
								<span class="errortip">${errors['temp.id'][0]}</span>
					    </div>
					 </li>
					</ul>
					</div>
				</div>
				 <div class="buttonArea">
					<input class="formButton" value="确  定" type="button" onclick="save('editGradeTemplateForm');">&nbsp;&nbsp;
                    <input class="formButton" onclick="window.location='${path}/admin/gradeTemplate!list.action'" value="返  回" type="button">
				</div>
			</form>
		</div>
	</body>
</html>