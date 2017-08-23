<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<!doctype html>
<html lang="zh-CN">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <meta http-equiv="X-UA-Compatible" content="IE=9"/>
        <title>未审核站点列表</title>
        <jsp:include page="../../page/include/headList.jsp"></jsp:include>
    </head>
    <body class="list"><%--
    <div class="main-top-hd clearfix">
        <h3 class="cur"><a href="javascript:void(0);">用户列表</a></h3>
    </div>
    --%><form id="siteUserForm" method="post" action="${path}/admin/audit!list.action">
    <div class="main-action-bar">
        <!-- 查询、分页 -->
        <jsp:include page="../../page/include/search.jsp"></jsp:include>
    </div>
    <div class="main-action-bar" style="font-size:14px;height:30px;">
          <input type="radio" name="compType" value="" onclick="searchSubmit()" <c:if test="${compType == item.key}"> checked="checked" </c:if>/>所有
	   <c:forEach var="item" items="${comList}">
	       <input type="radio" name="compType" value="${item.key}"  onclick="searchSubmit()" <c:if test="${compType == item.key}"> checked="checked" </c:if>/>${item.value}
	   </c:forEach>
	</div>
    <div class="body">
        <input type="hidden" name="status" value="${status}">
        <table id="listtable" class="listtable">
            <tbody>
                <tr>
                    <th class="check"><input class="allCheck" type="checkbox"  id="selectAll" onclick="javascript:selectAllCheckBox(this, '${pagination.rowSize}')"> </th>
                    <th class="w70">序号</th>
                    <th class="w70">组织类型</th>
                    <th>组织名称</th>
                    <c:if test="${status eq 'notAudit'}">
                    <th>申请人</th>
                    <th style="width:135px;">申请时间</th>
                    </c:if>
                     <c:if test="${status eq 'audit' or status eq 'disable' or status eq 'delete'}">
                     <th>申请人</th>
                    <th style="width:135px;">申请时间</th>
                    <th>审核人</th>
                    <th style="width:135px;">审核时间</th>
                    </c:if>                  
                    <c:if test="${status eq 'exitAudit'}">
                    <th>退回人</th>
                    <th style="width:135px;">退回时间</th>
                    </c:if>
                    <th style="width:70px;">站点状态</th>
                    <th style="width:170px;">操作</th>
                </tr>
                <c:forEach var="site" items="${siteAudit}" varStatus="e">
                    <tr>
                        <td>
                            <input id="select${e.index}" type="checkbox" value="${site.siteUser.id}" name=ids onclick="javascript:selected(this, '${pagination.rowSize}');">
                        </td>
                        <td><span>${e.index+1+pagination.rowSize*(pagination.pageNo-1)}</span></td>
                        <td>
                            <c:forEach var="item" items="${compTypeList}">
                               <c:if test="${site.siteUser.site.comType eq item.key}">${item.value}</c:if>
                            </c:forEach>                        
                        </td>
                        <td>${site.compName}</td>
                        
                     <c:if test="${status eq 'notAudit'}">
                        <td>
                            <span style="color:green;">${site.siteUser.user.realName}[${site.siteUser.user.account}]</span>
                        </td>
                        <td>
                            <span style="color: green;">
                            <fmt:formatDate value="${site.siteUser.site.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                        </td>
                     </c:if>
                        
                     <c:if test="${status eq 'audit' or status eq 'disable' or status eq 'delete' }">
                        <td>
                            <span style="color:green;">${site.siteUser.user.realName}[${site.siteUser.user.account}]</span>
                        </td>
                        <td>
                            <span style="color: green;">
                            <fmt:formatDate value="${site.siteUser.site.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                        </td>
                        <td>${site.auditor}</td>
                        <td>
                        <span style="color: green;">
                            <fmt:formatDate value="${site.auditTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                        </td>
                     </c:if>
                        
                     <c:if test="${status eq 'exitAudit'}">
                        <td>${site.auditor}</td>
                        <td>
                            <span style="color: green;"><fmt:formatDate value="${site.auditTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                        </td>
                     </c:if>
                     
                        <td>
                             <div class="statusDiv-wrap">
                                  <c:forEach var="item" items="${statusList}">
                                      <c:if test="${site.siteUser.site.status == item.key}"><span id="status${e.index}" style="color: ${item.description};">${item.value}</span></c:if>
                                  </c:forEach>
                             </div>
                        </td>
                        
                        <td>
	                       <a href="javascript:void(0);" class="show" id="show" onclick="this.href='${path}/admin/audit!show.action?id=${site.siteUser.id}&status=${status}'">查看</a>
	                       <c:if test="${check}">
	                           <c:if test="${status eq 'audit' or status eq 'exitAudit'}">
                                &nbsp;|&nbsp;
                                <a href="javascript:void(0);" class="delete" onclick="checkRows('siteUserForm', '${path}/admin/audit!exitCheck.action?id=${site.siteUser.id}&status=${status}', '${pagination.rowSize}', this,'确认退回未审核？');">退回未审核</a>
                                </c:if>
	                           <c:if test="${status eq 'notAudit'}">
	                           &nbsp;|&nbsp;
	                             <a href="javascript:void(0);" class="delete" onclick="checkRows('siteUserForm', '${path}/admin/audit!check.action?id=${site.siteUser.id}&status=${status}', '${pagination.rowSize}', this,'确认启用？');">审核</a>
	                           </c:if>
	                           <c:if test="${status eq 'exitAudit'}">
	                            &nbsp;|&nbsp;
                                 <a href="javascript:void(0);" class="delete" onclick="checkRows('siteUserForm', '${path}/admin/audit!delete.action?id=${site.siteUser.id}&status=${status}', '${pagination.rowSize}', this,'此操作将删除注册信息的全部内容，\n删除数据无法恢复，\n确认删除？');">删除</a>
	                           </c:if>
	                            <c:if test="${status eq 'notAudit' or status eq 'audit'}">
	                            &nbsp;|&nbsp;
	                            <a href="javascript:void(0);" onclick="this.href='${path}/admin/audit!edit.action?id=${site.siteUser.id}&status=${status}'" class="delete">退回</a>
	                            </c:if>
	                            <%--
	                            <c:if test="${status eq 'audit'}">
	                            &nbsp;|&nbsp;
	                            <a href="javascript:void(0);" class="delete" onclick="checkRows('siteUserForm', '${path}/audit!disable.action?id=${site.siteUser.id}&status=${status}', '${pagination.rowSize}', this,'确认禁用？');">注销</a>
	                            </c:if>
	                           --%>
	                           </c:if>
                       </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <h4 align="center" style="display:${empty pagination.list ? 'block' : 'none' }">暂无数据!</h4>
        <div class=pagerBar>
        <%--
        <c:if test="${delete}">
            <div class=delete>
               <input  id="deleteButton" disabled="disabled" class=formButton value="删 除" type="button" onclick="deleteRow('siteUserForm','${path}/audit!delete.action', '${pagination.rowSize}');">
            </div> 
        </c:if>
        --%>
        <c:if test="${check}">
             <c:if test="${status eq 'notAudit'}">
                <input  id="checkButton" disabled="disabled" class=formButton value="审核" type="button" onclick="checkRow('siteUserForm','${path}/admin/audit!check.action', '${pagination.rowSize}','确认审核？');">
             </c:if>
             <%--<c:if test="${status eq 'audit'}">
                <input  id="checkButton" disabled="disabled" class=formButton value="禁用" type="button" onclick="checkRow('siteUserForm','${path}/audit!disable.action', '${pagination.rowSize}','确认禁用？');">
             </c:if>--%>
             <c:if test="${status eq 'audit' or status eq 'exitAudit'}">
                <input  id="checkButton" disabled="disabled" class=formButton value="退回未审核" type="button" onclick="checkRow('siteUserForm','${path}/admin/audit!exitCheck.action', '${pagination.rowSize}','确认退回未审核？');">
             </c:if>
        </c:if>
         <c:if test="${delete}">
	        <c:if test="${status eq 'exitAudit'}">
	                <input  id="deleteButton" disabled="disabled" class=formButton value="删除" type="button" onclick="checkRow('siteUserForm','${path}/admin/audit!delete.action', '${pagination.rowSize}','此操作将删除注册信息的全部内容，\n删除数据无法恢复，\n确认删除？');">
	        </c:if>
        </c:if>
        </div>
    <jsp:include page="../../page/common/pager.jsp"></jsp:include>
    </div>
    </form>
    </body>
</html>