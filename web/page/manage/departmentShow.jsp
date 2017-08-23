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
		<title>查看科室</title>
		<jsp:include page="../../page/include/headEdit.jsp"></jsp:include>
	</head>
	<body class="input">
		<jsp:include page="../../page/include/image_upload.jsp"></jsp:include>
		<div class="main-top-hd clearfix">
        	<h3 class="cur">
	        	查看科室
	        </h3>
	    </div>
		<div class="body">
			<form name="editDepartmentForm" method="post">
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
			                     		<c:if test="${department.disable}">是</c:if>
										<c:if test="${!department.disable}">否</c:if>
									</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>科室性质：
		                     		</label>
									<c:forEach var="item" items="${natureList}">
										<c:if test="${department.natureId == item.id}">
											<span class="txt">${item.typeName}</span>
										</c:if>
									</c:forEach>
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>科室类型：
		                     		</label>
									<c:forEach var="item" items="${typeList}">
										<c:if test="${department.typeId == item.id}">
											<span class="txt">${item.typeName}</span>
										</c:if>
									</c:forEach>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>科室全称：
		                     		</label>
		                     		<span class="txt">${department.deptName}</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			传真：
		                     		</label>
		                     		<span class="txt">${department.fax}</span>
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			电子邮箱：
		                     		</label>
		                     		<span class="txt">${department.email}</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			科室地址：
		                     		</label>
		                     		<span class="txt">${department.address}</span>
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			电话：
		                     		</label>
		                     		<span class="txt">${department.phone}</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>创建时间：
		                     		</label>
		                     		<span class="txt"><fmt:formatDate value="${department.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>排序：
		                     		</label>
		                     		<span class="txt">${department.deptSort}</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>是否开通挂号：
		                     		</label>
									<span class="txt">	
			                     		<c:if test="${department.reg}">是</c:if>
										<c:if test="${!department.reg}">否</c:if>
									</span>
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			开通挂号时间：
		                     		</label>
		                     		<span class="txt">
			                        	<s:date name="department.regTime" format="yyyy-MM-dd HH:mm:ss" />
			                        </span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>是否开通随访：
		                     		</label>
									<span class="txt">	
			                     		<c:if test="${department.followUp}">是</c:if>
										<c:if test="${!department.followUp}">否</c:if>
									</span>
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			随访开通时间：
		                     		</label>
		                     		<span class="txt">
			                        	<s:date name="department.followUpTime" format="yyyy-MM-dd HH:mm:ss" />
			                        </span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem listItem-full-width">
		                       		<label class="lbl-ipt-tit">
		                     			科室图片：
		                     		</label>
		                     		<span class="txt" id="deptImg"></span>
			                        <script type="text/javascript">
				                    	showImageButton("deptImg",'${department.deptImg}',"department.deptImg",1);
				                    </script>
				                    <span class="errortip">
										<s:fielderror >
										    <s:param>department.deptImg</s:param>
									    </s:fielderror>
									</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			科室简要介绍：
		                     		</label>
		                     		<p class="lbl-p">${department.shortDesc}</p>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			科室详细介绍：
		                     		</label>
		                     		<p class="lbl-p">${department.description}</p>
			                   	</div>
			                </li>
			            </ul>
			        </div>
			    </div>
				
				<div class="buttonArea">
				    <c:if test="${!empty channelId}">
	           		  <input class="formButton" onclick="window.location='${path}/admin/content!list.action?leaf=${leaf}&channelId=${channelId}&action=${action}&status=${status}'" value="返  回" type="button">
				    </c:if>
				    <c:if test="${empty channelId}">
				      <input class="formButton" onclick="window.location='${path}/admin/departmentTree!trees.action'" value="返  回" type="button">
				    </c:if>
				</div>
			</form>
		</div>
	</body>
</html>