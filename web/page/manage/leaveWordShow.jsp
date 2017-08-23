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
		<title>查看留言</title>
		<jsp:include page="../../page/include/headEdit.jsp"></jsp:include>
	</head>
<body class="input">
	<div class="main-top-hd clearfix">
       	<h3 class="cur">查看会话信息</h3>
    </div>
	<div class="body">
		<div class="divInputTable">
			<div>
				<ul class="inputList">
           			<li class="inputList-li">
            			<div class="listItem">
                       		<label class="lbl-ipt-tit">
                     			用户信息：
                     		</label>
                   			<span class="txt">	
                   				<c:if test="${!empty leaveWordList[0]}">
                    				${leaveWordList[0].realName},${leaveWordList[0].phoneNumber},${leaveWordList[0].email}
                   				</c:if>
                   			</span>
	                   	</div>
	                </li>
           			<li class="inputList-li">
            			<div class="listItem">
                       		<label class="lbl-ipt-tit">
                     			留言标题：
                     		</label>
                   			<span class="txt">	
                   				<c:if test="${!empty leaveWordList[0]}">
                    				${leaveWordList[0].title}
                   				</c:if>
                   			</span>
	                   	</div>
	                </li>
	            </ul>
            	<c:forEach var="item" items="${leaveWordList}">
            		<c:if test="${!item.reply}">
            			<ul class="inputList">
	            			<li class="inputList-li">
		            			<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			留言内容：
		                     		</label>
		                     		<textarea rows="5" cols="45" disabled="disabled">${item.content}</textarea>
			                   	</div>
			                </li>
			            </ul>
            		</c:if>
            		<c:if test="${item.reply}">
            			<ul class="inputList">
	            			<li class="inputList-li">
		            			<div class="listItem">
		            			</div>
		            			<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			回复留言：
		                     		</label>
		                     		<textarea rows="5" cols="45"  disabled="disabled">${item.content}</textarea>
			                   	</div>
	            			</li>
	            		</ul>
            		</c:if>
            	</c:forEach>
            </div>
		</div>  
		<div class="buttonArea">
			<input class="formButton" onclick="window.location='${path}/admin/leaveWord!list.action?status=${status }'" value="返  回" type="button">
		</div>  	
	</div>
	<SCRIPT type="text/javascript">
		function edit(obj){
			alert("");
		}
	</SCRIPT>
</body>
</html>