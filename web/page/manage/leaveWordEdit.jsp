<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!doctype html>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html lang="zh-CN">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta http-equiv="X-UA-Compatible" content="IE=9"/>
		<title>回复留言</title>
		<jsp:include page="../../page/include/headEdit.jsp"></jsp:include>
	</head>
<body class="input">
	<div class="main-top-hd clearfix">
       	<h3 class="cur">回复留言</h3>
    </div>
	<div class="body">
		<form id="replyForm" method="post" action="${path}/admin/leaveWord!save.action">
			<input type="hidden" name="token" value="${token}"/>
			<input type="hidden" name="reply.id" value="${reply.id}">
			<input type="hidden" name="reply.version" value="${reply.version}">
			<input type="hidden" name="reply.parentId" value="${leaveWord.id}"/>
			<input type="hidden" name="status" value="${status}"/>
			<input type="hidden" name="edit" value="${edit}"/>
			<div class="divInputTable">
				<div>
	            	<ul class="inputList">
	            		<li class="inputList-li">
	            			<div class="listItem">
	                       		<label class="lbl-ipt-tit">
	                     			用户名称：
	                     		</label>
		                        <label class="lbl-ipt-tit radioWrap">
		                        	${leaveWord.realName }
								</label>
		                   	</div>
	            		</li>
	            	</ul>
	            	<ul class="inputList">
	            		<li class="inputList-li">
	            			<div class="listItem">
	                       		<label class="lbl-ipt-tit">
	                     			用户邮箱：
	                     		</label>
		                        <label class="lbl-ipt-tit radioWrap">
		                        	${leaveWord.email }
								</label>
		                   	</div>
	            		</li>
	            	</ul>
	            	<ul class="inputList">
	            		<li class="inputList-li">
	            			<div class="listItem">
	                       		<label class="lbl-ipt-tit">
	                     			留言主题：
	                     		</label>
		                        <label class="lbl-ipt-tit radioWrap">
		                        	${leaveWord.title }
								</label>
		                   	</div>
	            		</li>
	            	</ul>
	            	<ul class="inputList">
	            		<li class="inputList-li">
	            			<div class="listItem">
	                       		<label class="lbl-ipt-tit">
	                     			留言内容：
	                     		</label>
	                     		<textarea rows="5" cols="45" disabled="disabled" >${leaveWord.content }</textarea>
		                   	</div>
	            		</li>
	            	</ul>
	            	<ul class="inputList">
	            		<li class="inputList-li">
	            			<div class="listItem">
	            			</div>
	            			<div class="listItem">
	                       		<label class="lbl-ipt-tit">
	                     			回复留言：
	                     		</label>
	                     		<textarea name="reply.content" rows="5" cols="45" >${reply.content}</textarea>
	                     		<span class="errortip">${errors['reply.content'][0]}</span>
		                   	</div>
	            		</li>
	            	</ul>
	            </div>
			</div>  
			<div class="buttonArea">
				<input class="formButton" value="<c:if test='${edit}'>修改</c:if><c:if test='${!edit}'>回复</c:if>" type="submit">
				<input class="formButton" onclick="window.location='${path}/admin/leaveWord!list.action?status=${status }'" value="返  回" type="button">
			</div>  
		</form>
	</div>
</body>
</html>