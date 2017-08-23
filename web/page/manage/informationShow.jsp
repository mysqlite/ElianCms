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
		<title>查看资讯</title>
		<jsp:include page="../../page/include/headEdit.jsp"></jsp:include>
	</head>
	<body class="input">
		<jsp:include page="../../page/include/f_upload.jsp"></jsp:include>
		<div class="main-top-hd clearfix">
        	<h3 class="cur">
	        	查看资讯
	        </h3>
	    </div>
		<div class="body">
			<form name="editInformationForm" method="post">
				<div class="divInputTable">
		            <h3 class="main-tit-bar" id="typeBtn">基本信息</h3>
		            <div id="typeDiv">
		            	<ul class="inputList">
		            		<li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>是否启用：
		                     		</label>
		                     		<span class="txt">	
			                     		<c:if test="${information.disable}">是</c:if>
										<c:if test="${!information.disable}">否</c:if>
									</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>标题：
		                     		</label>
		                     		<span class="txt">${information.title}</span>
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>标题颜色：
		                     		</label>
				                    <span class="txt">${information.titleColor}</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
                                        <span class="star">*</span>文章类型：
                                    </label>
                                    <label class="lbl-ipt-tit radioWrap">
                                            <span class="txt">
                                             <c:if test="${information.hasImg eq 0}">普通</c:if>
                                             <c:if test="${information.hasImg eq 1}">图文</c:if>
                                             <c:if test="${information.hasImg eq 2}">切换</c:if>
                                             </span>
                                    </label>
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>是否标题加粗：
		                     		</label>
		                     		<span class="txt">
			                     		<c:if test="${information.titleBold}">是</c:if>
										<c:if test="${!information.titleBold}">否</c:if>
									</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem listItem-full-width">
		                       		<label class="lbl-ipt-tit">
		                     			标题图片：
		                     		</label>
		                     		<span class="txt" id="infoImg"></span>
			                        <script type="text/javascript">
				                    	upload.show("infoImg",'${information.infoImg}',0);
				                    </script>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			副标题：
		                     		</label>
		                     		<span class="txt">${information.subTitle}</span>
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			关键字：
		                     		</label>
		                     		<span class="txt">${information.keywords}</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			摘要：
		                     		</label>
		                     		<p class="lbl-p">${information.description}</p>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			来源名称：
		                     		</label>
		                     		<span class="txt">${information.sourceName}</span>
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			来源URL：
		                     		</label>
		                     		<span class="txt">${information.sourceUrl}</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			作者：
		                     		</label>
		                     		<span class="txt">${information.author}</span>
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>发布时间：
		                     		</label>
			                        <span class="txt"><fmt:formatDate value="${information.publishTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>创建人：
		                     		</label>
		                     		<span class="txt">${information.creater.realName}</span>
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>创建时间：
		                     		</label>
		                     		<span class="txt"><fmt:formatDate value="${information.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                    <div class="listItem txtarea">
			                    	<label class="lbl-ipt-tit">内容：</label>
			                    	<p class="lbl-p">${information.content}</p>
			                    </div>
			                </li>
			            </ul>
			        </div>
			    </div>
				<div class="buttonArea">
	           		<input class="formButton" onclick="window.location='${path}/admin/content!list.action?leaf=${leaf}&channelId=${channelId}&action=${action}&status=${status}'" value="返  回" type="button">
				</div>
			</form>
		</div>
	</body>
</html>