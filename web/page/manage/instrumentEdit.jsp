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
		<title>编辑器械</title>
		<jsp:include page="../../page/include/headEdit.jsp"></jsp:include>
	</head>
	<body class="input">
		<jsp:include page="../../page/include/image_upload.jsp"></jsp:include>
		<div class="main-top-hd clearfix">
        	<h3 class="cur">
	        	<c:if test="${!edit}">器械添加</c:if>
				<c:if test="${edit}">器械修改</c:if>
	        </h3>
	    </div>
		<div class="body">
			<c:if test="${!empty channelId}">
				<form name="editMedicineForm" action="${path}/admin/instrument_c!save.action" method="post">
			</c:if>
			<c:if test="${empty channelId}">
				<c:if test="${ztree}">
				    <form name="editMedicineForm" action="${path}/admin/instrumentTree!save.action" method="post">
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
				<input type="hidden" name="instrument.id" value="${instrument.id}">
				<input type="hidden" name="instrument.version" value="${instrument.version}">
				<input type="hidden" name="instrument.createTime" value="${instrument.createTime}"/>
				<input type="hidden" name="instrument.user.id" value="${instrument.user.id}"/>
				<input type="hidden" name="instrument.company.id" value="${instrument.company.id}"/>
				<div class="divInputTable">
		            <h3 class="main-tit-bar" id="typeBtn">基本信息</h3>
		            <div id="typeDiv">
		            	<ul class="inputList">
		            		<li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit"><span class="star">*</span>是否启用：</label>
			                        <label class="lbl-ipt-tit radioWrap">
										<input type="radio" name="instrument.disable" value="true"
											<c:if test="${instrument.disable}"> checked="checked" </c:if> />
											是 
									</label>
									<label class="lbl-ipt-tit radioWrap">
										<input type="radio" name="instrument.disable" value="false"<c:if test="${!instrument.disable}"> checked="checked" </c:if>/>
											否
									</label>
			                   	</div>
			                   	<div class="listItem">
                                    <label class="lbl-ipt-tit"><span class="star">*</span>开启折扣价：</label>
                                    <label class="lbl-ipt-tit radioWrap">
                                        <input type="radio" name="instrument.discountedPrice" value="true"
                                            <c:if test="${instrument.discountedPrice}"> checked="checked" </c:if> />
                                            是 
                                    </label>
                                    <label class="lbl-ipt-tit radioWrap">
                                        <input type="radio" name="instrument.discountedPrice" value="false"<c:if test="${!instrument.discountedPrice}"> checked="checked" </c:if>/>
                                            否
                                    </label>
                                </div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>中文名：
		                     		</label>
			                        <input type="text" class="ipt" name="instrument.cnName" value="${instrument.cnName}" />
				                    <span class="errortip">${errors['cnName'][0]}</span>
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star"></span>英文名：
		                     		</label>
		                     		<input type="text" class="ipt" name="instrument.enName" value="${instrument.enName}" />
				                    <span class="errortip">${errors['enName'][0]}</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                    <div class="listItem">
                                    <label class="lbl-ipt-tit">别名：</label>
                                    <input type="text" class="ipt" name="instrument.alias" value="${instrument.alias}"/>
                                    <span class="errortip">${errors['alias'][0]}</span>
                                </div>
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit"><span class="star">*</span>批准文号：</label>
		                       		<input type="text" class="ipt" name="instrument.approvalNumber" value="${instrument.approvalNumber}"/>
		                     		<span class="errortip">${errors['approvalNumber'][0]}</span>
			                   	</div>
			                </li>
			                
			                <li class="inputList-li">
                                <div class="listItem">
                                    <label class="lbl-ipt-tit"><span class="star">*</span>单价：</label>
                                    <input type="text" class="search-input-bar" name="instrument.pricePany" 
                                    value="<fmt:formatNumber value='${instrument.pricePany}' pattern='########.##' type='number'/>" style="text-align: right;"  onblur="intFormat(this,0,99999999);"/>
                                    <span class="txt">元</span><span class="txt" style="color:green">&nbsp;&nbsp;精确两位小数</span>
                                    <span class="errortip">${errors['pricePany'][0]}</span>
                                </div>
                                <div class="listItem">
                                    <label class="lbl-ipt-tit">折扣价：</label>
                                    <input type="text" class="search-input-bar" name="instrument.discountedPricePany"
                                     value="<fmt:formatNumber value='${instrument.discountedPricePany}' pattern='########.##' type='number'/> " style="text-align: right;" onblur="intFormat(this,0,99999999);"/>
                                    <span class="txt">元</span><span class="txt" style="color:green">&nbsp;&nbsp;精确两位小数</span>
                                    <span class="errortip">${errors['discountedPricePany'][0]}</span>
                                </div>
                            </li>
                            <li class="inputList-li">
                                <div class="listItem">
                                    <label class="lbl-ipt-tit">排序：</label>
                                    <input type="text" class="ipt" name="instrument.sort" value="${instrument.sort}" onkeyup="intFormat(this,1);"/>
                                    <span class="errortip">${errors['sort'][0]}</span>
                                </div>
                                <div class="listItem">
                                    <label class="lbl-ipt-tit"><span class="star">*</span>器械类型：</label>
                                     <div>
                                        <input id="keyId" class="formText" type="text" readonly="true" value="${instrument.type.typeName}" 
                                            onfocus="showMenu('${path}/admin/instrument!tree.action'); return false;" onkeydown="checkBackSpace()"/>
                                        <input id="valueId" type="hidden" name="instrument.type.id" value="${instrument.type.id}"/>
                                        <div id="treeDiv"><ul id="treeContent" class="ztree" style="width:200px;"/></div>
                                    </div>
                                    <span class="errortip">${errors['type'][0]}</span>
                                </div>
                            </li>
			                <li class="inputList-li">
                                <div class="listItem listItem-full-width">
                                    <label class="lbl-ipt-tit">器械图片：</label>
                                    <div id="instrumentImg"></div>
                                    <div id="instrumentImg1"></div>
                                    <div id="instrumentImg2"></div>
                                    <div id="instrumentImg3"></div>
                                    <script type="text/javascript">
                                        loadingImageButton("instrumentImg",'${instrument.instrImg}',"instrImg",false,1,true);
                                    </script>
                                    <span class="errortip">${errors['instrImg'][0]}</span>
                                </div>
                            </li>
                             <li class="inputList-li">
                                <div class="listItem">
                                    <label class="lbl-ipt-tit"><span class="star">*</span>科室类型：</label>
                                     <div>
                                        <input id="departmentTypeId" class="formText" type="text" readonly="true" value="${instrument.departmentType.typeName}" 
                                            onfocus="showMenu('${path}/admin/instrument!department.action','post','departmentTypeId','departmentTypeValue'); return false;" onkeydown="checkBackSpace('departmentTypeId','departmentTypeValue')"/>
                                        <input id="departmentTypeValue" type="hidden" name="instrument.departmentType.id" value="${instrument.departmentType.id}"/>
                                    </div>
                                    <span class="errortip">${errors['type'][0]}</span>
                                </div>
                            </li>
                            <li class="inputList-li">
                                <div class="listItem">
				                   <label class="lbl-ipt-tit"><span class="star">*</span>批准时间：</label>
				                   <input name="instrument.approvalTime" value='<fmt:formatDate value="${instrument.approvalTime}" pattern="yyyy-MM-dd"/>' class="ipt" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
				                   <span class="errortip">${errors['approvalTime'][0]}</span>
				                 </div>
				                 <div class="listItem">
                                   <label class="lbl-ipt-tit"><span class="star">*</span>到期时间：</label>
                                   <input name="instrument.effectiveTime" value='<fmt:formatDate value="${instrument.effectiveTime}" pattern="yyyy-MM-dd"/>' class="ipt" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
                                   <span class="errortip">${errors['effectiveTime'][0]}</span>
                                 </div>
                            </li>
                            <li class="inputList-li">
                                <div class="listItem txtarea">
                                    <label class="lbl-ipt-tit">摘要：</label>
                                    <textarea name="instrument.summary" class="formTextarea valid">${instrument.summary}</textarea>
                                    <span class="errortip">${errors['summary'][0]}</span>
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
                                <div class="listItem txtarea">
                                    <label class="lbl-ipt-tit">规格：</label>
                                    <textarea name="instrument.specifications" class="formTextarea valid">${instrument.specifications}</textarea>
                                    <span class="errortip">${errors['specifications'][0]}</span>
                                </div>
                                <div class="listItem txtarea">
                                    <label class="lbl-ipt-tit">包装：</label>
                                    <textarea name="instrument.packaging" class="formTextarea valid">${instrument.packaging}</textarea>
                                    <span class="errortip">${errors['packaging'][0]}</span>
                                </div>
                            </li>
                           <li class="inputList-li">
                                <div class="listItem txtarea">
                                    <label class="lbl-ipt-tit">用法：</label>
                                    <textarea name="instrument.usage" class="formTextarea valid">${instrument.usage}</textarea>
                                    <span class="errortip">${errors['usage'][0]}</span>
                                </div>
                                <div class="listItem txtarea">
                                    <label class="lbl-ipt-tit">用途：</label>
                                    <textarea name="instrument.instrUse" class="formTextarea valid">${instrument.instrUse}</textarea>
                                    <span class="errortip">${errors['instrUse'][0]}</span>
                                </div>
                            </li>
                        
                                
			                <li class="inputList-li">
                                <div class="listItem">
                                    <label class="lbl-ipt-tit">描述：</label>
                                    <textarea id="description" name="instrument.description" class="formTextediter">${instrument.description}</textarea>
                                    <script type="text/javascript">
                                        var editor1;KindEditor.ready(function(K) {editor1 = K.create('textarea[id="description"]');});    
                                    </script>
                                    <div style="display:none"><textarea name="editorPrevImg">${instrument.description}</textarea></div>
                                    <span class="errortip"></span>
                                </div>
                            </li>
			            </ul>
			        </div>
			    </div>
				<div class="buttonArea">
					<input class="formButton" value="确  定" type="button" onclick="submits(this.form,editor1);">&nbsp;&nbsp;
		           		<c:if test="${empty channelId}">
		           		     <c:if test="${!ztree}">
		           		         <input class="formButton" onclick="window.location='${path}/admin/instrument!list.action'" value="返  回" type="button">
		           		     </c:if>
		           		     <c:if test="${ztree}">
                                 <input class="formButton" onclick="window.location='${path}/admin/instrumentTree!list.action?ztree=${ztree}&compId=${compId}'" value="返  回" type="button">
                             </c:if>
		           		</c:if>
		           		<c:if test="${!empty channelId}">
			               <input class="formButton" onclick="window.location=window.location='${path}/admin/content!list.action?leaf=${leaf}&channelId=${channelId}&action=${action}&status=${status}'" value="返  回" type="button">
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