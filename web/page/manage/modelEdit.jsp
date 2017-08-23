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
	        <c:if test="${!edit}">模型添加</c:if>
	        <c:if test="${edit}">模型修改</c:if>
        </h3>
    </div>
<div class=body>
    <form id="editModelForm" name="editModelForm" method="post" action="${path}/admin/model!save.action">
        <input type="hidden" name="model.id" value="${model.id}">
        <input type="hidden" name="model.version" value="${model.version}">
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
                        <input type="radio" name="model.disable" value="true"<c:if test="${model.disable}"> checked="checked" </c:if> />是
                     </label>
                     <label class="lbl-ipt-tit radioWrap">
                        <input type="radio" name="model.disable" value="false"<c:if test="${!model.disable}">checked</c:if> />否
                     </label>
                   </div>
                   <div class="listItem">
                     	<label class="lbl-ipt-tit">
                   			<span class="star">*</span>组织类型：
                   		</label>
                   		<div class="select">
                        <select class="listbar-sel" name="model.compType">
							<c:forEach var="item" items="${compTypeList}">
								<option value="${item.key}" <c:if test="${model.compType == item.key}">selected="selected"</c:if>>${item.value}</option>
							</c:forEach>
						</select>
					</div>
                    <span class="errortip">${errors['model.compType'][0]}</span>
                  </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>模型名称：</label>
                     <input type="text" class="ipt" name="model.modelName" value="${model.modelName}" />
                     <span class="errortip">${errors['model.modelName'][0]}</span>
                   </div>
                   <div class="listItem">
                      <label class="lbl-ipt-tit"><span class="star">*</span>排序：</label>
                      <input type="text" class="ipt" name="model.modelSort" value="${model.modelSort}" onkeyup="intFormat(this,1,99);"/>
                      <span class="errortip">${errors['model.modelSort'][0]}</span>
                   </div>
                </li>
                <li class="inputList-li">
                  <div class="listItem">
                    <label class="lbl-ipt-tit">栏目模板前缀：</label>
                    <input type="text" class="ipt" name="model.channelTempPrefix" value="${model.channelTempPrefix}" />
                    <span class="errortip">${errors['model.channelTempPrefix'][0]}</span>
                  </div>
                   <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>栏目模板路径：</label>
                    <input type="text" class="ipt" name="model.channelTempUrl" value="${model.channelTempUrl}" />
                    <span class="errortip">${errors['model.channelTempUrl'][0]}</span>
                  </div>
                </li>
                <li class="inputList-li">
                  <div class="listItem">
                      <label class="lbl-ipt-tit">列表模板前缀：</label>
                      <input type="text" class="ipt" name="model.listTempPrefix" value="${model.listTempPrefix}" />
                      <span class="errortip">${errors['model.listTempPrefix'][0]}</span>
                   </div>
                  <div class="listItem">
                      <label class="lbl-ipt-tit"><span class="star">*</span>列表模板路径：</label>
                      <input type="text" class="ipt" name="model.listTempUrl" value="${model.listTempUrl}" />
                      <span class="errortip">${errors['model.listTempUrl'][0]}</span>
                   </div>
                </li>
                <li class="inputList-li">
                  <div class="listItem">
                      <label class="lbl-ipt-tit">内容模板前缀：</label>
                      <input type="text" class="ipt" name="model.contentTempPrefix" value="${model.contentTempPrefix}" />
                      <span class="errortip">${errors['model.contentTempPrefix'][0]}</span>
                   </div>
                  <div class="listItem">
                      <label class="lbl-ipt-tit"><span class="star">*</span>内容模板路径：</label>
                      <input type="text" class="ipt" name="model.contentTempUrl" value="${model.contentTempUrl}" />
                      <span class="errortip">${errors['model.contentTempUrl'][0]}</span>
                   </div>
                </li>
                <li class="inputList-li">
                  <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>内容模型：</label>
                    <label class="lbl-ipt-tit radioWrap">
                    	<c:forEach var="c" items="${contentModelList}">
                    		<label class="lbl-ipt-tit radioWrap">
                        		<input type="radio" name="model.contentModel.id" value="${c.key}" <c:if test="${c.key== model.contentModel.id}"> checked="checked" </c:if>/>${c.value}
                        	</label>
                        </c:forEach>
                    </label>
                    <span class="errortip">${errors['model.contentModel.id'][0]}</span>
                  </div>
                </li>
            </ul>
           </div>
        </div>
        <div class="buttonArea">
			<input class="formButton" value="确  定" type="button" onclick="document.editModelForm.submit();">&nbsp;&nbsp;
          	<input class="formButton" onclick="window.location='${path}/admin/model!list.action'" value="返  回" type="button">
		</div>
        </form>
      </div>
   </body>
<script type="text/javascript">  
        displayDiv('typeBtn','typeDiv','${errors}');
</script>
</html>