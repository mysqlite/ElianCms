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
		<title>器械信息查看</title>
		<jsp:include page="../../page/include/headEdit.jsp"></jsp:include>
	</head>
	<body class="input">
		<jsp:include page="../../page/include/image_upload.jsp"></jsp:include>
		<div class="main-top-hd clearfix">
        	<h3 class="cur">
				器械信息查看
	        </h3>
	    </div>
		<div class="body">
			<c:if test="${!empty channelId}">
				<form name="editInstrumentForm" action="${path}/admin/instrument_c!save.action" method="post">
			</c:if>
			<c:if test="${empty channelId}">
				<c:if test="${ztree}">
				    <form name="editInstrumentForm" action="${path}/admin/instrumentTree!save.action" method="post">
				    <input type="hidden" name="ztree" value="${ztree}"/>
                    <input type="hidden" name="compId" value="${compId}"/>
				</c:if>
				<c:if test="${!ztree}">
	               <form name="editMedicineForm" action="${path}/admin/instrument!save.action" method="post">
	            </c:if>
			   </c:if>
				<input type="hidden" name="token" value="${token}"/>
				<input type="hidden" name="edit" value="${edit}"/>
				<input type="hidden" name="leaf" value="${leaf}"/>
				<input type="hidden" name="channelId" value="${channelId}"/>
				<input type="hidden" name="action" value="${action}"/>
				<div class="divInputTable">
		            <h3 class="main-tit-bar" id="typeBtn">基本信息</h3>
		            <div id="typeDiv">
		            	<ul class="inputList">
		            		<li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">是否启用：</label>
									<span class="txt">${instrument.disable?'是':'否'}</span>
			                   	</div>
			                   	<div class="listItem">
                                    <label class="lbl-ipt-tit">开启折扣价：</label>
                                    <span class="txt">${instrument.discountedPrice?'是':'否'}</span>
                                </div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">中文名：</label>
			                         <span class="txt">${instrument.cnName}</span>
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">英文名：</label>
		                     		 <span class="txt">${instrument.enName}</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                    <div class="listItem">
                                    <label class="lbl-ipt-tit">别名：</label>
                                    <span class="txt">${instrument.alias}</span>
                                </div>
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">批准文号：</label>
		                       		<span class="txt">${instrument.approvalNumber}</span>
			                   	</div>
			                </li>
			                
			                <li class="inputList-li">
                                <div class="listItem">
                                    <label class="lbl-ipt-tit">单价：</label>
                                    <span class="txt"><fmt:formatNumber value='${instrument.pricePany}' pattern='########.##' type='number'/>元</span>
                                </div>
                                <div class="listItem">
                                    <label class="lbl-ipt-tit">折扣价：</label>
                                    <span class="txt"><fmt:formatNumber value='${instrument.discountedPricePany}' pattern='########.##' type='number'/>元</span>
                                </div>
                            </li>
                            <li class="inputList-li">
                                <div class="listItem">
                                    <label class="lbl-ipt-tit">排序：</label>
                                    <span class="txt">${instrument.sort}</span>
                                </div>
                                <div class="listItem">
                                    <label class="lbl-ipt-tit">器械类型：</label>
                                     <span class="txt">${instrument.type.typeName}</span>
                                </div>
                            </li>
			                <li class="inputList-li">
			                    <div class="listItem">
                                    <label class="lbl-ipt-tit">科室类型：</label>
                                     <span class="txt">${instrument.departmentType.typeName}</span>
                                </div>
                                <div class="listItem listItem-full-width">
                                    <label class="lbl-ipt-tit">器械图片：</label>
                                    <div id="instrumentImg" ></div>
                                    <script type="text/javascript">
                                        showImageButton("instrumentImg",'${instrument.instrImg}',"instrument.instrImg",1,true);
                                    </script>
                                </div>
                            </li>
                             <li class="inputList-li">
                                <div class="listItem">
                                   <label class="lbl-ipt-tit">批准时间：</label>
                                   <span class="txt"><fmt:formatDate value="${instrument.approvalTime}" pattern="yyyy-MM-dd"/></span>
                                 </div>
                                 <div class="listItem">
                                   <label class="lbl-ipt-tit">到期时间：</label>
                                   <span class="txt">${instrument.effectiveTime}</span>
                                 </div>
                            </li>
                            <li class="inputList-li">
				                <div class="listItem">
	                                <label class="lbl-ipt-tit">摘要：</label>
	                                <p class="lbl-p">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${instrument.summary}</p> 
                                </div>
                            </li>
                            <li class="inputList-li">
                                <div class="listItem">
                                    <label class="lbl-ipt-tit">创建时间：</label>
                                    <span class="txt"><fmt:formatDate value="${instrument.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                                </div>
                                <div class="listItem">
                                    <label class="lbl-ipt-tit">创建人：</label>
                                    <span class="txt">${instrument.user.realName}</span>
                                </div>
                            </li>
			              </ul>
                    </div>
			        <h3 class="main-tit-bar" id="itemBtn">详细信息</h3>
                    <div id="itemDiv">
                        <ul class="inputList">        
			                <li class="inputList-li">
		                    	<div class="listItem">
                                    <label class="lbl-ipt-tit">规格：</label>
                                    <p class="lbl-p">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    ${instrument.specifications}</p> 
                                </div>
		                    	<div class="listItem">
                                    <label class="lbl-ipt-tit">包装：</label>
                                    <p class="lbl-p">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    ${instrument.packaging}</p> 
                                </div>
			                </li>
			                <li class="inputList-li">
                                <div class="listItem">
                                    <label class="lbl-ipt-tit">用法：</label>
                                    <p class="lbl-p">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    ${instrument.usage}</p> 
                                </div>
                                <div class="listItem">
                                    <label class="lbl-ipt-tit">用途：</label>
                                    <p class="lbl-p">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    ${instrument.instrUse}</p> 
                                </div>
                            </li>
			                <li class="inputList-li">
                                <div class="listItem">
                                    <label class="lbl-ipt-tit">描述：</label>
                                    <p class="lbl-p">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${instrument.description}</p> 
                                </div>
                            </li>
			            </ul>
			        </div>
			    </div>
				<div class="buttonArea">
					<input class="formButton" value="修 改" type="button" onclick="window.location='${path}/admin/instrument!edit.action?id=${instrument.id}&edit=true'">&nbsp;&nbsp;
		           		<c:if test="${empty channelId}">
		           		     <c:if test="${!ztree}">
		           		         <input class="formButton" onclick="window.location='${path}/admin/instrument!list.action'" value="返  回" type="button">
		           		     </c:if>
		           		     <c:if test="${ztree}">
                                 <input class="formButton" onclick="window.location='${path}/admin/instrumentTree!list.action?ztree=${ztree}&compId=${compId}'" value="返  回" type="button">
                             </c:if>
		           		</c:if>
		           		<c:if test="${!empty channelId}">
			               <input class="formButton" onclick="window.location='${path}/admin/content!list.action?leaf=${leaf}&channelId=${channelId}&action=${action}&status=${status}'" value="返  回" type="button">
			            </c:if>
				</div>
			</form>
		</div>
	</body>
	<script type="text/javascript">  
        displayDiv('typeBtn','typeDiv','${errors}',650);
        displayDiv('itemBtn','itemDiv','${errors}',650);
    </script>
</html>