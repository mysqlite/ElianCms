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
		<title>查看医生</title>
		<jsp:include page="../../page/include/headEdit.jsp"></jsp:include>
	</head>
	<body class="input">
		<jsp:include page="../../page/include/image_upload.jsp"></jsp:include>
		<div class="main-top-hd clearfix">
        	<h3 class="cur">
	        	查看医生
	        </h3>
	    </div>
		<div class="body">
			<form name="editDoctorForm" method="post">
			<input type="hidden" name="ztree" value="${ztree}"/>
		    <input type="hidden" name="hospId" value="${hospId}"/>
		    <input type="hidden" name="departId" value="${departId}"/>
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
			                     		<c:if test="${doctor.disable}">是</c:if>
										<c:if test="${!doctor.disable}">否</c:if>
									</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>姓名：
		                     		</label>
		                     		<span class="txt">${doctor.doctName}</span>
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>科室：
		                     		</label>
									<c:forEach var="item" items="${deptList}">
										<c:if test="${doctor.dept.id == item.id}">
											<span class="txt">${item.deptName}</span>
										</c:if>
									</c:forEach>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>性别：
		                     		</label>
				                    <c:forEach var="item" items="${sexList}">
										<c:if test="${item.key == doctor.gender}">
											<span class="txt">${item.value}</span>
										</c:if>
									</c:forEach>
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			生日：
		                     		</label>
		                     		<span class="txt"><fmt:formatDate value="${doctor.birthday}" pattern="yyyy-MM-dd"/></span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			职称：
		                     		</label>
		                     		<span class="txt">${doctor.jobTitle}</span>
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			职务：
		                     		</label>
		                     		<span class="txt">${doctor.dutyName}</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			毕业院校：
		                     		</label>
		                     		<span class="txt">${doctor.graduateSchool}</span>
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			专长：
		                     		</label>
		                     		<span class="txt">${doctor.speciality}</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			从业时间：
		                     		</label>
		                     		<span class="txt"><fmt:formatDate value="${doctor.jobBeginTime}" pattern="yyyy-MM-dd"/></span>
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			电子邮箱：
		                     		</label>
		                     		<span class="txt">${doctor.email}</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			手机：
		                     		</label>
		                     		<span class="txt">${doctor.moblie}</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>创建时间：
		                     		</label>
		                     		<span class="txt"><fmt:formatDate value="${doctor.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>排序：
		                     		</label>
		                     		<span class="txt">${doctor.doctSort}</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>是否开通挂号：
		                     		</label>
		                     		<span class="txt">	
			                     		<c:if test="${doctor.reg}">是</c:if>
										<c:if test="${!doctor.reg}">否</c:if>
									</span>
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			开通挂号时间：
		                     		</label>
		                     		<span class="txt"><fmt:formatDate value="${doctor.regTime}" pattern="yyyy-MM-dd"/></span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>是否开通随访：
		                     		</label>
									<span class="txt">	
			                     		<c:if test="${doctor.followUp}">是</c:if>
										<c:if test="${!doctor.followUp}">否</c:if>
									</span>
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			随访开通时间：
		                     		</label>
		                     		<span class="txt"><fmt:formatDate value="${doctor.followUpTime}" pattern="yyyy-MM-dd"/></span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem listItem-full-width">
		                       		<label class="lbl-ipt-tit">
		                     			医生图片：
		                     		</label>
									<span class="txt" id="doctImg"></span>
			                        <script type="text/javascript">
				                    	showImageButton("doctImg",'${doctor.doctImg}',"doctor.doctImg",1);
				                    </script>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			详细介绍：
		                     		</label>
		                     		<p class="lbl-p">${doctor.introduction}</p>
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
                     <input class="formButton" onclick="window.location='${path}/admin/doctor!list.action?ztree=${ztree}&departId=${departId}&hospId=${hospId}'" value="返  回" type="button">
                    </c:if>
				</div>
			</form>
		</div>
	</body>
</html>