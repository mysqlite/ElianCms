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
		<title>内容发布</title>
		<jsp:include page="../../page/include/headEdit.jsp"></jsp:include>
		<script language="javascript" type="text/javascript" src="${path}/js/manage/ajaxMessage.js"></script>
	</head>
	<script type="text/javascript">
		function ajaxSend(type, url, async, cache, data){
			var channelId = document.getElementById("valueId").value;
			url +="?channelId="+channelId;
			url +="&cache="+cache;
			ajax(type, url, async, 'message');
		}
	</script>
	<body class="input">
		<div class="main-top-hd clearfix">
        	<h3 class="cur">
	        	内容发布
	        </h3>
	    </div>
		<div class="body">
			<form>
				<div id="typeDiv">
					<ul class="inputList">
						<li class="inputList-li">
							<div class="listItem">
								<label class="lbl-ipt-tit">
	                     			<span class="star">*</span>栏目：
	                     		</label>
	                     		<div>
								    <input id="keyId" class="formText" type="text" readonly="true" value="" 
								    	onfocus="showMenu('${path}/admin/static!tree.action'); return false;" onkeydown="checkBackSpace()"/>
								    <input id="valueId" type="hidden" value=""/>
								    <div id="treeDiv"><ul id="treeContent" class="ztree" style="width:200px;"/></div>
								</div>
							</div>
						</li>
					</ul>
				</div>
				<div class="buttonArea">
					<input class="formButton" value="发布" type="button" onclick="ajaxSend('POST', '${path}/admin/static!staticContent.action', false);">&nbsp;&nbsp;
					<input class="formButton" value="重新发布" type="button" onclick="ajaxSend('POST', '${path}/admin/static!staticContent.action', false);">&nbsp;&nbsp;
				</div>
			</form>
		</div>
		<script type="text/javascript">  
			displayDiv('typeBtn','typeDiv','${errors}');
		</script>
	</body>
</html>