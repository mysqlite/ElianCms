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
		<title>特殊页静态化</title>
		<jsp:include page="../../page/include/headEdit.jsp"></jsp:include>
	</head>
	<script type="text/javascript">
		function ajaxSend(type, url, async, data){
			var xmlhttp=null;
			if (window.XMLHttpRequest){// code for Firefox, Opera, IE7, etc.
				xmlhttp=new XMLHttpRequest();
			}
			else if (window.ActiveXObject){// code for IE6, IE5
				xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
			}
			if(xmlhttp!=null){
				xmlhttp.onreadystatechange=function(){
					if (xmlhttp.readyState==4 && xmlhttp.status==200){
						alert("操作成功！");
					}
				}
				var tempId = document.getElementById("tempId").value;
				url +="?tempId="+tempId;
				xmlhttp.open(type,url,async);
				xmlhttp.send(data);
			}
		}
	</script>
	<body class="input">
		<div class="main-top-hd clearfix">
        	<h3 class="cur">
	        	特殊页静态化
	        </h3>
	    </div>
		<div class="body">
			<form>
				<div id="typeDiv">
					<ul class="inputList">
						<li class="inputList-li">
		                   	<div class="listItem">
		                        <label class="lbl-ipt-tit">
	                     			<span class="star">*</span>特殊页模板：
	                     		</label>
								<div class="select">
			                        <select id="tempId" class="listbar-sel">
										<c:forEach var="f" items="${otherTempList}">
											<option value="${f.id}">${f.tempName}</option>
										</c:forEach>
									</select>
			                    </div>
		                   	</div>
		                </li>
					</ul>
				</div>
				<div class="buttonArea">
					<input class="formButton" value="生 成" type="button" onclick="ajaxSend('POST', '${path}/admin/static!staticSpecial.action', false);">&nbsp;&nbsp;
				</div>
			</form>
		</div>
		<script type="text/javascript">  
			displayDiv('typeBtn','typeDiv','${errors}');
		</script>
	</body>
</html>