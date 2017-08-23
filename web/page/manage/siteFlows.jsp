<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
        <title>站点统计</title>
        <jsp:include page="../../page/include/headList.jsp"></jsp:include>
    </head>
<body class="list">
<div class="main-top-hd clearfix">
    <h3 class="cur"><a href="javascript:void(0);">站点统计</a></h3>
</div>
<form id="siteFlowForm" name="siteFlowForm" method="post" action="${path}/admin/siteFlows!list.action">
<div class="main-action-bar">
    <!-- 查询、分页 -->
    <jsp:include page="../../page/include/search.jsp"></jsp:include>
</div>
<div class="body">
   <table class="listtable">
      <th class="w70" ><span style="font-weight:bold;">今日</span></th>
      <th>浏览量(PV):<span id="pv"></span></th>
      <th>访客数(UV):<span id="uv"></span></th>
      <th>IP数:<span id="ip"></span></th>
      <th>百度搜索:<span id="baidu"></span></th>
      <th>google搜索:<span id="google"></span></th>
      <th>360搜索:<span id="so360"></span></th>
      <th>腾讯soso:<span id="soso"></span></th>
    </table>
    <table class="listtable">
      <th class="w70"><span style="font-weight:bold;">昨日</span></th>
      <th>浏览量(PV):<span id="pv1"></span></th>
      <th>访客数(UV):<span id="uv1"></span></th>
      <th>IP数:<span id="ip1"></span></th>
      <th>百度搜索:<span id="baidu1"></span></th>
      <th>google搜索:<span id="google1"></span></th>
      <th>360搜索:<span id="so3601"></span></th>
      <th>腾讯soso:<span id="soso1"></span></th>
    </table>
    <table class="listtable">
      <th class="w70"><span style="font-weight:bold;">共计</span></th>
      <th>浏览量(PV):<span id="pv2"></span></th>
      <th>访客数(UV):<span id="uv2"></span></th>
      <th>IP数:<span id="ip2"></span></th>
      <th>百度搜索:<span id="baidu2"></span></th>
      <th>google搜索:<span id="google2"></span></th>
      <th>360搜索:<span id="so3602"></span></th>
      <th>腾讯soso:<span id="soso2"></span></th>
    </table>
</div>
<script type="text/javascript">
function todays(){
    var urls='{path}'+"/admin/siteFlows!today.action";
	getJson(urls,function(data){
		commHtmls("",data);
	})
}

function yesterdays(){
    var urls='{path}'+"/admin/siteFlows!yesterday.action";
    getJson(urls,function(data){
        commHtmls("1",data);
    })
}

function statisticals(){
    var urls='{path}'+"/admin/siteFlows!statistical.action";
    getJson(urls,function(data){
        commHtmls("2",data);
    })
}

function commHtmls(id,data){
	G("pv"+id).innerHTML=data.pv;
	G("uv"+id).innerHTML=data.uv;
	G("ip"+id).innerHTML=data.ip;
	G("baidu"+id).innerHTML=data.baidu;
	G("google"+id).innerHTML=data.google;
	G("soso"+id).innerHTML=data.soso;
	G("so360"+id).innerHTML=data.so360;
}
todays();
yesterdays();
statisticals();
</script>
<div class="body">
    <table id="listtable" class="listtable">
        <tbody>
            <tr>
                <th class="check"><input class="allCheck" type="checkbox"  id="selectAll" onclick="javascript:selectAllCheckBox(this, '${pagination.rowSize}')"> </th>
                <th class="w70">序号</th>
                <th class="w70">访问者ip</th>
                <th class="w150">访问时间</th>
                <th class="w70">访问页面</th>
                <th class="w70">入口域名</th>
                <th class="w70">入口页面</th>
                <th>关键字</th>
                <c:if test="${update or delete}">
                <th class="w150"><span>操作</span></th>
                </c:if>
            </tr>
            <c:forEach var="siteFlow" items="${pagination.list}" varStatus="e">
                <tr>
                    <td>
                        <input id="select${e.index}" type="checkbox" value="${siteFlow.id}" name="ids" onclick="javascript:selected(this, '${pagination.rowSize}');">
                    </td>
                    <td>
                        <span>${e.index+1+pagination.rowSize*(pagination.pageNo-1)}</span>
                    </td>
                    <td><a target="_bank" href="http://www.baidu.com/s?wd=${siteFlow.accessIp}" title="点击查看IP来着哪里">${siteFlow.accessIp}</a></td>
                    <td><fmt:formatDate value="${siteFlow.accessTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <td><a target="_bank" href="${siteFlow.accessPage}" title="${siteFlow.accessPage}">访问</a></td>
                    <td>
                    <c:if test="${!empty siteFlow.refererSite}">
                    <a target="_bank" href="${siteFlow.refererSite}" title="${siteFlow.refererSite}">${siteFlow.refererSite}</a>
                    </c:if>
                    <c:if test="${empty siteFlow.refererSite}">直接访问</c:if>
                    </td>
                    <td>
                    <c:if test="${!empty siteFlow.refererPage}">
                    <a target="_bank" href="${siteFlow.refererPage}" title="${siteFlow.refererPage}">访问</a>
                    </c:if>
                    <c:if test="${empty siteFlow.refererPage}">直接访问</c:if>
                    </td>
                    <td><div class="Tkeyword">${siteFlow.refererKeyword}</div></td>
                    <c:if test="${delete}">
                        <td>
                           <c:if test="${delete}">   
                            <a href="#" class="delete" onclick="deleteRow('siteFlowForm', '${path}/admin/siteFlows!delete.action', '${pagination.rowSize}', this);">删除</a>
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
    <div class="delete">
       <input  id="deleteButton" disabled="disabled" class=formButton value="删 除" type=button onclick="deleteRow('siteFlowForm','${path}/admin/siteFlow!delete.action', '${pagination.rowSize}');">
    </div> 
	</c:if>
	<jsp:include page="../../page/common/pager.jsp" flush="false"></jsp:include>
	</div>
</form>
</body>
</html>
