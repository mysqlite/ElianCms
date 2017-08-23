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
		<title>编辑资讯</title>
		<jsp:include page="../../page/include/headEdit.jsp"></jsp:include>
	</head>
	<body class="input">
		<jsp:include page="../../page/include/f_upload.jsp"></jsp:include>
		<div class="main-top-hd clearfix">
        	<h3 class="cur">
	        	<c:if test="${!edit}">资讯添加</c:if>
				<c:if test="${edit}">资讯修改</c:if>
	        </h3>
	    </div>
		<div class="body">
			<form name="editInformationForm" action="${path}/admin/${action}!save.action" method="post">
				<input type="hidden" name="token" value="${token}"/>
				<input type="hidden" name="edit" value="${edit}"/>
				<input type="hidden" name="leaf" value="${leaf}"/>
				<input type="hidden" name="channelId" value="${channelId}"/>
				<input type="hidden" name="action" value="${action}"/>
				<input type="hidden" name="information.id" value="${information.id}">
				<input type="hidden" name="information.version" value="${information.version}">
				<input type="hidden" name="information.createTime" value="${information.createTime}"/>
				<input type="hidden" name="information.publishTime" value="${information.publishTime}"/>
				<input type="hidden" name="information.creater.id" value="${information.creater.id}"/>
				
				<div class="divInputTable">
		            <h3 class="main-tit-bar" id="typeBtn">基本信息</h3>
		            <div id="typeDiv">
		            	<ul class="inputList">
		            		<li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>是否启用：
		                     		</label>
			                        <label class="lbl-ipt-tit radioWrap">
										<input type="radio" name="information.disable" value="true"
											<c:if test="${information.disable}"> checked="checked" </c:if> />
											是 
									</label>
									<label class="lbl-ipt-tit radioWrap">
										<input type="radio" name="information.disable" value="false"<c:if test="${!information.disable}"> checked="checked" </c:if>/>
											否
									</label>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>标题：
		                     		</label>
			                        <input type="text" class="ipt" name="information.title" value="${information.title}" />
				                    <span class="errortip">${errors['information.title'][0]}</span>
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			标题颜色：
		                     		</label>
				                    <span class="errortip">${errors['information.titleColor'][0]}</span>
									<input id="color_core" type="text" readonly="readonly" name="information.titleColor" value="${information.titleColor}"/>
									<div id="color_show" style="height:20px ;width:25px;background-color: #000000;"></div>
									<script type="text/javascript">
										$("#color_show").bigColorpicker(function(el,color){ $("#color_core").val(color); $(el).css("backgroundColor",color);}) 
									</script>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>文章类型：
		                     		</label>
			                        <label class="lbl-ipt-tit radioWrap">
										<input type="radio" name="information.hasImg" value="0"
											<c:if test="${information.hasImg eq 0 or empty information.hasImg}"> checked="checked" </c:if> />
											普通 
									</label>
									
									<label class="lbl-ipt-tit radioWrap">
										<input type="radio" name="information.hasImg" value="1"
										     <c:if test="${information.hasImg eq 1}"> checked="checked" </c:if>/>
											图文
									</label>
									
									<label class="lbl-ipt-tit radioWrap">
                                        <input type="radio" name="information.hasImg" value="2"
                                             <c:if test="${information.hasImg eq 2}"> checked="checked" </c:if>/>
                                            幻灯片
                                    </label>
                                    
									<label class="lbl-ipt-tit radioWrap">
                                        <input type="radio" name="information.hasImg" value="3"
                                             <c:if test="${information.hasImg eq 3}"> checked="checked" </c:if>/>
                                            视频
                                    </label>
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>是否标题加粗：
		                     		</label>
			                        <label class="lbl-ipt-tit radioWrap">
										<input type="radio" name="information.titleBold" value="true"
											<c:if test="${information.titleBold}"> checked="checked" </c:if> />
											是 
									</label>
									<label class="lbl-ipt-tit radioWrap">
										<input type="radio" name="information.titleBold" value="false"<c:if test="${!information.titleBold}"> checked="checked" </c:if>/>
											否
									</label>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem listItem-full-width">
		                       		<label class="lbl-ipt-tit">
		                     			标题图片：
		                     		</label>
		                     		<div id="idCardTd" >
		                     		</div>
			                        <script type="text/javascript">
				                    	upload.btn("idCardTd",'${information.infoImg}',"information.infoImg",false,0)
				                    </script>
				                    <span class="errortip">${errors['information.infoImg'][0]}</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			副标题：
		                     		</label>
			                        <input type="text" class="ipt" name="information.subTitle" value="${information.subTitle}" />
				                    <span class="errortip">${errors['information.subTitle'][0]}</span>
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			关键字：
		                     		</label>
			                        <input type="text" class="ipt" name="information.keywords" value="${information.keywords}" />
				                    <span class="errortip">${errors['information.keywords'][0]}</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			摘要：
		                     		</label>
		                     		<textarea name="information.description" class="formTextarea valid">${information.description}</textarea>
				                    <span class="errortip">${errors['information.description'][0]}</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			来源名称：
		                     		</label>
			                        <input type="text" class="ipt" name="information.sourceName" value="${information.sourceName}" />
				                    <span class="errortip">${errors['information.sourceName'][0]}</span>
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			来源URL：
		                     		</label>
			                        <input type="text" class="ipt" name="information.sourceUrl" value="${information.sourceUrl}" />
				                    <span class="errortip">${errors['information.sourceUrl'][0]}</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			作者：
		                     		</label>
			                        <input type="text" class="ipt" name="information.author" value="${information.author}" />
				                    <span class="errortip">${errors['information.author'][0]}</span>
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>发布时间：
		                     		</label>
			                        <input type="text" class="ipt" disabled="true" value='<fmt:formatDate value="${information.publishTime}" pattern="yyyy-MM-dd HH:mm:ss"/>' />
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>创建人：
		                     		</label>
			                        <input type="text" class="ipt" disabled="true" value="${information.creater.realName}" />
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>创建时间：
		                     		</label>
			                        <input type="text" class="ipt" disabled="true" value='<fmt:formatDate value="${information.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>' />
			                   	</div>
			                </li>
			                <li class="inputList-li">
                                <div class="listItem listItem-full-width">
                                    <label class="lbl-ipt-tit">
                                        <span class="star"></span>附件：
                                    </label>
                                    <div id="file_path" >
                                    </div>
                                    <script type="text/javascript">
                                        upload.btn("file_path",'${information.filePath}',"information.filePath",false,0,0,1);
                                    </script>
                                </div>
                            </li>
                            <li class="inputList-li">
                                <div class="listItem">
                                    <label class="lbl-ipt-tit">
                                        <span class="star">*</span>预览模式：
                                    </label>
                                    <label class="lbl-ipt-tit radioWrap">
                                        <input type="radio" name="information.previewMode" value="true"
                                            <c:if test="${information.previewMode}"> checked="checked" </c:if> />
                                            播放器
                                    </label>
                                    <label class="lbl-ipt-tit radioWrap">
                                        <input type="radio" name="information.previewMode" value="false"
                                            <c:if test="${!information.previewMode}"> checked="checked" </c:if>/>
                                            PDF阅读器
                                    </label>
                                </div>
                            </li>
			                <li class="inputList-li">
			                    <div class="listItem txtarea">
			                    	<label class="lbl-ipt-tit">内容：</label>
			                    	<span class="errortip">${errors['information.content'][0]}</span>
			                    </div>
			                </li>
			                <li class="inputList-li">
			                    <div class="listItem txtarea">
			                        <textarea id="intro" name="information.content" class="formTextediter">${information.content}</textarea>
				                    <script type="text/javascript">
				                    	var editor1;KindEditor.ready(function(K) {editor1 = K.create('textarea[id="intro"]');});
				                    </script>
				                    <div style="display:none">
			          					 <textarea name="editorPrevImg">${information.content}</textarea>
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
</html>