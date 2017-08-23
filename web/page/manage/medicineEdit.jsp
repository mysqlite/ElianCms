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
		<title>编辑药品</title>
		<jsp:include page="../../page/include/headEdit.jsp"></jsp:include>
	</head>
	<body class="input">
		<jsp:include page="../../page/include/f_upload.jsp"></jsp:include>
		<div class="main-top-hd clearfix">
        	<h3 class="cur">
	        	<c:if test="${!edit}">药品添加</c:if>
				<c:if test="${edit}">药品修改</c:if>
	        </h3>
	    </div>
		<div class="body">
			<c:if test="${!empty channelId}">
				<form name="editMedicineForm" action="${path}/admin/medicine_c!save.action" method="post">
			</c:if>
			<c:if test="${empty channelId}">
				<c:if test="${ztree}">
				    <form name="editMedicineForm" action="${path}/admin/medicineTree!save.action" method="post">
				    <input type="hidden" name="ztree" value="${ztree}"/>
                    <input type="hidden" name="compId" value="${compId}"/>
				</c:if>
				<c:if test="${!ztree}">
	               <form name="editMedicineForm" action="${path}/admin/medicine!save.action" method="post">
	            </c:if>
			   </c:if>
				<input type="hidden" name="token" value="${token}"/>
				<input type="hidden" name="edit" value="${edit}"/>
				<input type="hidden" name="leaf" value="${leaf}"/>
				<input type="hidden" name="channelId" value="${channelId}"/>
				<input type="hidden" name="action" value="${action}"/>
				<input type="hidden" name="medicine.id" value="${medicine.id}">
				<input type="hidden" name="medicine.version" value="${medicine.version}">
				<input type="hidden" name="medicine.createTime" value="${medicine.createTime}"/>
				<input type="hidden" name="medicine.user.id" value="${medicine.user.id}"/>
				<input type="hidden" name="medicine.company.id" value="${medicine.company.id}"/>
				<div class="divInputTable">
		            <h3 class="main-tit-bar" id="typeBtn">基本信息</h3>
		            <div id="typeDiv">
		            	<ul class="inputList">
		            		<li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit"><span class="star">*</span>是否启用：</label>
			                        <label class="lbl-ipt-tit radioWrap">
										<input type="radio" name="medicine.disable" value="true"
											<c:if test="${medicine.disable}"> checked="checked" </c:if> />
											是 
									</label>
									<label class="lbl-ipt-tit radioWrap">
										<input type="radio" name="medicine.disable" value="false"<c:if test="${!medicine.disable}"> checked="checked" </c:if>/>
											否
									</label>
			                   	</div>
			                   	<div class="listItem">
                                    <label class="lbl-ipt-tit"><span class="star">*</span>开启折扣价：</label>
                                    <label class="lbl-ipt-tit radioWrap">
                                        <input type="radio" name="medicine.discountedPrice" value="true"
                                            <c:if test="${medicine.discountedPrice}"> checked="checked" </c:if> />
                                            是 
                                    </label>
                                    <label class="lbl-ipt-tit radioWrap">
                                        <input type="radio" name="medicine.discountedPrice" value="false"<c:if test="${!medicine.discountedPrice}"> checked="checked" </c:if>/>
                                            否
                                    </label>
                                </div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>中文名：
		                     		</label>
			                        <input type="text" class="ipt" name="medicine.cnName" value="${medicine.cnName}" />
				                    <span class="errortip">${errors['cnName'][0]}</span>
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star"></span>英文名：
		                     		</label>
		                     		<input type="text" class="ipt" name="medicine.enName" value="${medicine.enName}" />
				                    <span class="errortip">${errors['enName'][0]}</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                    <div class="listItem">
                                    <label class="lbl-ipt-tit">别名：</label>
                                    <input type="text" class="ipt" name="medicine.alias" value="${medicine.alias}"/>
                                    <span class="errortip">${errors['alias'][0]}</span>
                                </div>
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit"><span class="star">*</span>批准文号：</label>
		                       		<input type="text" class="ipt" name="medicine.approvalNumber" value="${medicine.approvalNumber}"/>
		                     		<span class="errortip">${errors['approvalNumber'][0]}</span>
			                   	</div>
			                </li>
			                
			                <li class="inputList-li">
                                <div class="listItem">
                                    <label class="lbl-ipt-tit"><span class="star">*</span>单价：</label>
                                    <input type="text" class="search-input-bar" name="medicine.pricePany" 
                                    value="<fmt:formatNumber value='${medicine.pricePany}' pattern='########.##' type='number'/>" style="text-align: right;"  onblur="intFormat(this,0,99999999);"/>
                                    <span class="txt">元</span><span class="txt" style="color:green">&nbsp;&nbsp;精确两位小数</span>
                                    <span class="errortip">${errors['pricePany'][0]}</span>
                                </div>
                                <div class="listItem">
                                    <label class="lbl-ipt-tit">折扣价：</label>
                                    <input type="text" class="search-input-bar" name="medicine.discountedPricePany"
                                     value="<fmt:formatNumber value='${medicine.discountedPricePany}' pattern='########.##' type='number'/> " style="text-align: right;" onblur="intFormat(this,0,99999999);"/>
                                    <span class="txt">元</span><span class="txt" style="color:green">&nbsp;&nbsp;精确两位小数</span>
                                    <span class="errortip">${errors['discountedPricePany'][0]}</span>
                                </div>
                            </li>
                            <li class="inputList-li">
                                <div class="listItem">
                                    <label class="lbl-ipt-tit"><span class="star">*</span>单位：</label>
                                    <input type="text" class="ipt" name="medicine.medicineUnit" value="${medicine.medicineUnit}"/>
                                    <span class="errortip">${errors['medicineUnit'][0]}</span>
                                </div>
                                <div class="listItem">
                                    <label class="lbl-ipt-tit"><span class="star">*</span>剂型：</label>
                                    <input type="text" class="ipt" name="medicine.dosage" value="${medicine.dosage}"/>
                                    <span class="errortip">${errors['dosage'][0]}</span>
                                </div>
                            </li>
                            
                            <li class="inputList-li">
                                <div class="listItem">
                                    <label class="lbl-ipt-tit">排序：</label>
                                    <input type="text" class="ipt" name="medicine.sort" value="${medicine.sort}" onkeyup="intFormat(this,1);"/>
                                    <span class="errortip">${errors['sort'][0]}</span>
                                </div>
                                <div class="listItem">
                                    <label class="lbl-ipt-tit"><span class="star">*</span>药品类型：</label>
                                     <div class="select">
                                        <select class="listbar-sel" name="medicine.type.id">
                                            <option value="">--请选择--</option>
                                            <c:forEach var="item" items="${typeList}">
                                                <option value="${item.id}" <c:if test="${medicine.type.id == item.id}">selected="selected"</c:if>>${item.typeName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <span class="errortip">${errors['type'][0]}</span>
                                </div>
                            </li>
			                <li class="inputList-li">
                                <div class="listItem listItem-full-width">
                                    <label class="lbl-ipt-tit">药品图片：</label>
                                    <div id="medicineImg" ></div>
                                    <script type="text/javascript">
                                        upload.btn("medicineImg",'${medicine.medicineImg}',"medImgs",false,true);
                                    </script>
                                    <span class="errortip">${errors['medicineImg'][0]}</span>
                                </div>
                            </li>
                            <li class="inputList-li">
                                <div class="listItem txtarea">
                                    <label class="lbl-ipt-tit">摘要：</label>
                                    <textarea name="medicine.summary" class="formTextarea valid">${medicine.summary}</textarea>
                                    <span class="errortip">${errors['summary'][0]}</span>
                                </div>
                                <div class="listItem txtarea">
                                    <label class="lbl-ipt-tit"><span class="star">*</span>作用类别：</label>
                                    <textarea name="medicine.effectCategory" class="formTextarea valid">${medicine.effectCategory}</textarea>
                                    <span class="errortip">${errors['effectCategory'][0]}</span>
                                </div>
                            </li>
                            <li class="inputList-li">
                                <div class="listItem">
                                    <label class="lbl-ipt-tit">创建时间：</label>
                                    <span class="txt"><fmt:formatDate value="${medicine.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                                </div>
                                <div class="listItem">
                                    <label class="lbl-ipt-tit">创建人：</label>
                                    <span class="txt">${medicine.user.realName}</span>
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
			                        <textarea name="medicine.specification" class="formTextarea valid">${medicine.specification}</textarea>
			                        	<span class="errortip">${errors['specification'][0]}</span>
		                    	</div>
		                    	<div class="listItem txtarea">
			                        <label class="lbl-ipt-tit">主要成分：</label>
			                        <textarea name="medicine.composition" class="formTextarea valid">${medicine.composition}</textarea>
			                        <span class="errortip">${errors['composition'][0]}</span>
		                    	</div>
			                </li>
			                <li class="inputList-li">
                                <div class="listItem txtarea">
                                    <label class="lbl-ipt-tit">功能主治：</label>
                                    <textarea name="medicine.attendingFunctions" class="formTextarea valid">${medicine.attendingFunctions}</textarea>
                                        <span class="errortip">${errors['attendingFunctions'][0]}</span>
                                </div>
                                <div class="listItem txtarea">
                                    <label class="lbl-ipt-tit">用法用量：</label>
                                    <textarea name="medicine.usageDosage" class="formTextarea valid">${medicine.usageDosage}</textarea>
                                    <span class="errortip">${errors['usageDosage'][0]}</span>
                                </div>
                            </li>
                            <li class="inputList-li">
                                <div class="listItem txtarea">
                                    <label class="lbl-ipt-tit">不良反应：</label>
                                    <textarea name="medicine.adverseReaction" class="formTextarea valid">${medicine.adverseReaction}</textarea>
                                        <span class="errortip">${errors['adverseReaction'][0]}</span>
                                </div>
                                <div class="listItem txtarea">
                                    <label class="lbl-ipt-tit">禁忌：</label>
                                    <textarea name="medicine.contraindication" class="formTextarea valid">${medicine.contraindication}</textarea>
                                    <span class="errortip">${errors['contraindication'][0]}</span>
                                </div>
                            </li>
                            
                            <li class="inputList-li">
                                <div class="listItem txtarea">
                                    <label class="lbl-ipt-tit">药物相互作用：</label>
                                    <textarea name="medicine.drugInteractions" class="formTextarea valid">${medicine.drugInteractions}</textarea>
                                        <span class="errortip">${errors['drugInteractions'][0]}</span>
                                </div>
                                <div class="listItem txtarea">
                                    <label class="lbl-ipt-tit">药理作用：</label>
                                    <textarea name="medicine.pharmacologicalEffects" class="formTextarea valid">${medicine.pharmacologicalEffects}</textarea>
                                    <span class="errortip">${errors['pharmacologicalEffects'][0]}</span>
                                </div>
                            </li>
                            
                            <li class="inputList-li">
                                <div class="listItem txtarea">
                                    <label class="lbl-ipt-tit"><span class="star">*</span>注意事项：</label>
                                    <textarea name="medicine.attentions" class="formTextarea valid">${medicine.attentions}</textarea>
                                        <span class="errortip">${errors['attentions'][0]}</span>
                                </div>
                                <div class="listItem txtarea">
                                    <label class="lbl-ipt-tit">贮藏方法：</label>
                                    <textarea name="medicine.storageMethod" class="formTextarea valid">${medicine.storageMethod}</textarea>
                                    <span class="errortip">${errors['storageMethod'][0]}</span>
                                </div>
                            </li>
			                <li class="inputList-li">
                                <div class="listItem">
                                    <label class="lbl-ipt-tit">描述：</label>
                                    <textarea id="description" name="medicine.description" class="formTextediter">${medicine.description}</textarea>
                                    <script type="text/javascript">
                                        var editor1;KindEditor.ready(function(K) {editor1 = K.create('textarea[id="description"]');});    
                                    </script>
                                    <div style="display:none"><textarea name="editorPrevImg">${medicine.description}</textarea></div>
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
		           		         <input class="formButton" onclick="window.location='${path}/admin/medicine!list.action'" value="返  回" type="button">
		           		     </c:if>
		           		     <c:if test="${ztree}">
                                 <input class="formButton" onclick="window.location='${path}/admin/medicineTree!list.action?ztree=${ztree}&compId=${compId}'" value="返  回" type="button">
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