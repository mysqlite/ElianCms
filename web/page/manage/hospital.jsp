<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
		<title>医院列表</title>
		<jsp:include page="../../page/include/headList.jsp"></jsp:include>
	</head>
<body class="list">
<div class="main-top-hd clearfix">
	<h3 class="cur"><a href="javascript:void(0);">医院列表</a></h3>
</div>
 <form id="hospitalForm" name="hospitalForm" method="post" action="${path}/admin/hospital!list.action">
    <input type="hidden" name="areaCode" value="${areaCode}"/>
    <input type="hidden" name="ztree" value="${ztree}"/>
<div class="main-action-bar">
    <a class="ui-btn-wrap" href="${path}/admin/hospital!edit.action?ztree=${ztree}&areaCode=${areaCode}">
    	<span class="ui-action-btn ui-add-btn-ico" style="font-size:14px">添加</span>
    </a>
    <!-- 查询、分页 -->
	<jsp:include page="../../page/include/search.jsp"></jsp:include>
</div>
<div class="body">
	<table id="listtable" class="listtable">
		<tbody>
			<tr>
			    <th class="check"><input class="allCheck" type="checkbox"  id="selectAll" onclick="javascript:selectAllCheckBox(this, '${pagination.rowSize}')"> </th>
			    <th class="w70">序号</th>
			    <th class="w90">医院名称</th>
			    <th>医院性质</th>
			    <th>医院类型</th>
			    <th>医院等级</th>
			    <th>医保</th>
			    <th>挂号</th>
			    <th>创建时间</th>
                <th>审核时间</th>
			    <th class="w90">状态</th>
			     <c:if test="${delete or update}">
			    <th class="w150"><span>操作</span></th>
			    </c:if>
			</tr>
		    <c:forEach var="hospital" items="${pagination.list}" varStatus="e">
			    <tr>
				    <td>
				    	<input id="select${e.index}" type="checkbox" value="${hospital.id}" name=ids onclick="javascript:selected(this, '${pagination.rowSize}');">
				    </td>
				    <td><span>${e.index+1+pagination.rowSize*(pagination.pageNo-1)}</span></td>
				    <td><div class="Ttitle">${hospital.hospName}</div></td>
				    <td>
					    <c:forEach var="natureList" items="${natureList}" varStatus="t">
                          <c:if test="${natureList.id eq hospital.nature}">${natureList.typeName}</c:if>                        
                        </c:forEach>
				    </td>
				    <td>
                        <c:forEach var="hospTypeList" items="${hospTypeList}" varStatus="g">
                          <c:if test="${hospTypeList.id eq hospital.hospType}">${hospTypeList.typeName}</c:if>                        
                        </c:forEach>
                    </td>
				    <td>
                        <c:forEach var="rankList" items="${rankList}" varStatus="k">
                          <c:if test="${rankList.id eq hospital.rank}">${rankList.typeName}</c:if>                        
                        </c:forEach>
                    </td>
                    <td><c:if test="${hospital.health}">是</c:if><c:if test="${!hospital.health}">否</c:if></td>
                    <td><c:if test="${hospital.reg}">是</c:if><c:if test="${!hospital.reg}">否</c:if></td>
                    <td><fmt:formatDate value="${hospital.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <td>
                        <fmt:formatDate value="${hospital.auditTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </td>
				    <td>
					    <div class="statusDiv-wrap">
                                <a href="#" onclick="showStatusDiv('statusDiv', ${e.index}, this);">
                                   <c:forEach var="item" items="${hospitalStatusList}">
                                       <c:if test="${hospital.status == item.key}"><span id="status${e.index}" style="color: ${item.description};">${item.value}</span></c:if>
                                   </c:forEach>
                                </a>
                                 <c:if test="${update}">
                                    <c:if test="${hospital.status < 4}">
                                       <div id="statusDiv${e.index}" class="statusDiv">
                                           <c:forEach var="item" items="${mainHospitalStatusList}">
                                               <a onclick="statusRow('${path}/admin/hospital!status.action?id=${hospital.id}', 'status', 'statusDiv', ${e.index}, '${item.value}', '${item.key}', '${item.description}');">${item.value}</a><br/>
                                           </c:forEach>
                                        </div>
                                    </c:if>
                                </c:if>
                         </div>
				    </td>
				    <c:if test="${delete or update}">
					    <td>
					        <c:if test="${update}">
					            <a href="${path}/admin/hospital!edit.action?id=${hospital.id}&edit=true&areaCode=${areaCode}&ztree=${ztree}" class="edit">修改</a>&nbsp;|&nbsp;
							</c:if>
							<c:if test="${delete}">
							    <a href="#" class="delete" onclick="deleteRow('hospitalForm', '${path}/admin/hospital!deleteHospital.action', '${pagination.rowSize}', this);">删除</a>
						    </c:if>
						</td>
					</c:if>
			    </tr>
		    </c:forEach>
		</tbody>
	</table>
	<h4 align="center" style="display:${empty pagination.list ? 'block' : 'none' }">暂无数据!</h4>
	<div class=pagerBar>
	   <c:if test="${delete}">
		<div class=delete>
		    <input hideFocus id="deleteButton" disabled="disabled" class=formButton value="删 除" type=button onclick="deleteRow('hospitalForm','${path}/admin/hospital!deleteHospital.action', '${pagination.rowSize}');">
		</div> 
		</c:if>
		<c:if test="${update}">
	    <input hideFocus id="checkButton" disabled="disabled" class=formButton value="审核" type=button onclick="checkRow('hospitalForm','${path}/admin/hospital!check.action', '${pagination.rowSize}');">
	    </c:if>
	</div>
    <jsp:include page="../../page/common/pager.jsp"></jsp:include>
</div>
</form>
</body>
</html>