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
		<title>药品信息查看</title>
		<jsp:include page="../../page/include/headEdit.jsp"></jsp:include>
	</head>
	<body class="input">
		<jsp:include page="../../page/include/image_upload.jsp"></jsp:include>
		<div class="main-top-hd clearfix">
        	<h3 class="cur">
				药品信息查看
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
				<div class="divInputTable">
		            <h3 class="main-tit-bar" id="typeBtn">基本信息</h3>
		            <div id="typeDiv">
		            	<ul class="inputList">
		            		<li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">是否启用：</label>
									<span class="txt">${medicine.disable?'是':'否'}</span>
			                   	</div>
			                   	<div class="listItem">
                                    <label class="lbl-ipt-tit">开启折扣价：</label>
                                    <span class="txt">${medicine.discountedPrice?'是':'否'}</span>
                                </div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">中文名：</label>
			                         <span class="txt">${medicine.cnName}</span>
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">英文名：</label>
		                     		 <span class="txt">${medicine.enName}</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                    <div class="listItem">
                                    <label class="lbl-ipt-tit">别名：</label>
                                    <span class="txt">${medicine.alias}</span>
                                </div>
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">批准文号：</label>
		                       		<span class="txt">${medicine.approvalNumber}</span>
			                   	</div>
			                </li>
			                
			                <li class="inputList-li">
                                <div class="listItem">
                                    <label class="lbl-ipt-tit">单价：</label>
                                    <span class="txt"><fmt:formatNumber value='${medicine.pricePany}' pattern='########.##' type='number'/>元</span>
                                </div>
                                <div class="listItem">
                                    <label class="lbl-ipt-tit">折扣价：</label>
                                    <span class="txt"><fmt:formatNumber value='${medicine.discountedPricePany}' pattern='########.##' type='number'/>元</span>
                                </div>
                            </li>
                            <li class="inputList-li">
                                <div class="listItem">
                                    <label class="lbl-ipt-tit">单位：</label>
                                    <span class="txt">${medicine.medicineUnit}</span>
                                </div>
                                <div class="listItem">
                                    <label class="lbl-ipt-tit">剂型：</label>
                                    <span class="txt">${medicine.dosage}</span>
                                </div>
                            </li>
                            
                            <li class="inputList-li">
                                <div class="listItem">
                                    <label class="lbl-ipt-tit">排序：</label>
                                    <span class="txt">${medicine.sort}</span>
                                </div>
                                <div class="listItem">
                                    <label class="lbl-ipt-tit">药品类型：</label>
                                     <span class="txt">
	                                     <c:forEach var="item" items="${typeList}">
	                                        <c:if test="${medicine.type.id == item.id}">${item.typeName}</c:if>
	                                     </c:forEach>
                                     </span>
                                </div>
                            </li>
			                <li class="inputList-li">
                                <div class="listItem listItem-full-width">
                                    <label class="lbl-ipt-tit">药品图片：</label>
                                    <div id="medicineImg" ></div>
                                    <script type="text/javascript">
                                        showImageButton("medicineImg",'${medicine.medicineImg}',"medicine.medicineImg",1,true);
                                    </script>
                                </div>
                            </li>
                            <li class="inputList-li">
				                <div class="listItem">
	                                <label class="lbl-ipt-tit">摘要：</label>
	                                <p class="lbl-p">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${medicine.summary}</p> 
                                </div>
                                <div class="listItem">
                                    <label class="lbl-ipt-tit">作用类别：</label>
                                    <p class="lbl-p">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${medicine.summary}</p> 
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
		                    	<div class="listItem">
                                    <label class="lbl-ipt-tit">规格：</label>
                                    <p class="lbl-p">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${medicine.specification}</p> 
                                </div>
		                    	<div class="listItem">
                                    <label class="lbl-ipt-tit">主要成分：</label>
                                    <p class="lbl-p">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${medicine.composition}</p> 
                                </div>
			                </li>
			                <li class="inputList-li">
                                <div class="listItem">
                                    <label class="lbl-ipt-tit">功能主治：</label>
                                    <p class="lbl-p">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${medicine.attendingFunctions}</p> 
                                </div>
                                <div class="listItem">
                                    <label class="lbl-ipt-tit">用法用量：</label>
                                    <p class="lbl-p">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${medicine.usageDosage}</p> 
                                </div>
                            </li>
			                <li class="inputList-li">
                                <div class="listItem">
                                    <label class="lbl-ipt-tit">不良反应：</label>
                                    <p class="lbl-p">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${medicine.adverseReaction}</p> 
                                </div>
                                <div class="listItem">
                                    <label class="lbl-ipt-tit">禁忌：</label>
                                    <p class="lbl-p">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${medicine.contraindication}</p> 
                                </div>
                            </li>
			                <li class="inputList-li">
                                <div class="listItem">
                                    <label class="lbl-ipt-tit">药物相互作用：</label>
                                    <p class="lbl-p">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${medicine.drugInteractions}</p> 
                                </div>
                                <div class="listItem">
                                    <label class="lbl-ipt-tit">药理作用：</label>
                                    <p class="lbl-p">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${medicine.pharmacologicalEffects}</p> 
                                </div>
                            </li>
			                <li class="inputList-li">
                                <div class="listItem">
                                    <label class="lbl-ipt-tit">注意事项：</label>
                                    <p class="lbl-p">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${medicine.attentions}</p> 
                                </div>
                                <div class="listItem">
                                    <label class="lbl-ipt-tit">贮藏方法：</label>
                                    <p class="lbl-p">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${medicine.storageMethod}</p> 
                                </div>
                            </li>
			                <li class="inputList-li">
                                <div class="listItem">
                                    <label class="lbl-ipt-tit">描述：</label>
                                    <p class="lbl-p">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${medicine.description}</p> 
                                </div>
                            </li>
			            </ul>
			        </div>
			    </div>
				<div class="buttonArea">
					<input class="formButton" value="修 改" type="button" onclick="window.location='${path}/admin/medicine!edit.action?id=${medicine.id}'">&nbsp;&nbsp;
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