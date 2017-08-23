<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML >
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html lang="zh-CN">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=9" />
		<title>内容列表</title>
		<link rel="icon" type="image/x-icon" href="favicon.ico"/>
        <link rel="stylesheet" type="text/css" href="${path}/css/manage/base.css" />
        <link rel="stylesheet" type="text/css" href="${path}/css/manage/admin.css" />
        <link rel="stylesheet" type="text/css" href="${path}/css/manage/page.css"/>
        <script language="javascript" type="text/javascript" src="${path}/js/jquery.js"></script>
        <script language="javascript" type="text/javascript" src="${path}/js/manage/page.js"></script>
        
        
        <!--树形下拉列表-->
        <script type="text/javascript" src="${path}/js/jquery.ztree.core-3.5.js"></script>
		<script type="text/javascript" src="${path}/js/ajaxTree.js" ></script>
		<link rel="stylesheet" href="${path}/css/zTreeStyle/zTreeStyle.css" type="text/css">
        <link href="${path}/plugin/jqueryUI/css/ui-lightness/jquery-ui-1.10.0.custom.css" rel="stylesheet">
		<script src="${path}/plugin/jqueryUI/js/jquery-ui-1.10.0.custom.js"></script>
		<script>
			function showMenu1(url, type, keyId, valueId, depthId) {
				if ($("#treeDiv").is(":hidden")) {
					$.post(url, {
						'type' : type
					}, function(data) {
						var zNodes = new Array();
						for ( var i = 0; i < data.length; i++) {
							zNodes[i] = {
								id : data[i].id,
								pId : data[i].pId,
								name : data[i].name
							};
						}
						
						if(keyId==null || keyId==undefined){
							keyId="keyId";
						}
						if(valueId==null || valueId==undefined){
							valueId="valueId";
						}
						if(depthId==null || depthId==undefined){
							depthId="depthId";
						}
						
						/**
						 *	zTree初始换参数设置
						 */
						var setting = {
							view : {
								dblClickExpand : true
							},
							data : {
								simpleData : {
									enable : true
								}
							},
							callback : {
								/**
								 *	zTree节点onclick事件
								 */
								onClick : function onClick(e, treeId, treeNode) {
									$("#"+keyId).attr("value", treeNode.name);
									$("#"+valueId).attr("value", treeNode.id);
									var depth = $("#"+depthId);
									if(depth!=null)
										depth.attr("value", treeNode.level + 2);
									if(treeNode.id!=null)
										hideMenu();
								}
							}
						};
						
						$.fn.zTree.init($("#treeContent"), setting, zNodes);
			
						var cityObj = $("#"+keyId);
						$("#treeDiv").slideDown("fast");
						$("body").bind("mousedown", onBodyDown);
					}, "json");
				}
			}
			
			function changeSubSite(value){
				var parentSite = document.getElementById ("parentSiteId");
				parentSite.value = value;
			}
			
			function ajaxSend(type, url, async, data){
				var xmlhttp=null;
				if (window.XMLHttpRequest){// code for Firefox, Opera, IE7, etc.
					xmlhttp=new XMLHttpRequest();
				}
				else if (window.ActiveXObject){// code for IE6, IE5
					xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
				}
				if(xmlhttp!=null){
					xmlhttp.onreadystatechange=function(){
						if (xmlhttp.readyState==4 && xmlhttp.status==200){
							var myObject = eval("(" + xmlhttp.responseText + ")");
							alert(myObject.MSG);
						}
					}
					var channelId = document.getElementById("valueId").value;
					url +="&channelId="+channelId;
					xmlhttp.open(type,url,async);
					xmlhttp.send(data);
				}
			}
			
			function showDialog(url){
				$("#dialog").dialog( {
					autoOpen : false,
					width : 600,
					height: 400,
					modal: true,
					buttons : [ {
						text : "确定",
						click : function() {
							$(this).dialog("close");
							ajaxSend('POST', url, false);
						}
					}, {
						text : "取消",
						click : function() {
							$(this).dialog("close");
						}
					} ]
				});
				
				$("#dialog").dialog("open");
			}
			
			function changeParentSiteId(url){
				var xmlhttp=null;
				if (window.XMLHttpRequest){// code for Firefox, Opera, IE7, etc.
					xmlhttp=new XMLHttpRequest();
				}
				else if (window.ActiveXObject){// code for IE6, IE5
					xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
				}
				var temp = false;
				if(xmlhttp!=null){
					xmlhttp.onreadystatechange=function(){
						if (xmlhttp.readyState==4 && xmlhttp.status==200){
							var data = null;
							if(xmlhttp.responseText!="" && (data= eval("(" + xmlhttp.responseText + ")"))!=null){
								var div = document.getElementById ("dialog");
								div.title="推荐->"+data.SITE_NAME;
								document.getElementById("showText").innerHTML="推荐->"+data.SITE_NAME;
								var parentSite = document.getElementById ("parentSiteId");
								parentSite.value = data.SITE_ID;
								temp =  true;
							}
						}
					}
					xmlhttp.open("POST",url,false);
					xmlhttp.send(null);
				}
				return temp;
			}
			
			function clearTreeChannel(){
				$("#keyId").attr("value", "");
				$("#valueId").attr("value", "");
				$("#subSiteId").attr("value", "");
			}
			
			function recommend(url, contentId){
				if(changeParentSiteId('${path}/admin/recommend!recommendParentSite.action?controlId='+contentId)){
					clearTreeChannel();
					document.getElementById("contentId").value = contentId;
					showDialog(url+"?contentId="+contentId);
				}
				else{
					alert("不存在推荐的站点!");
				}
			}
			
			function showChannelTree(){
				var parentSiteId=document.getElementById("parentSiteId").value;
				if(parentSiteId==null || parentSiteId==""){
					alert("请选择分站!");
					return;
				}
				var contentId=document.getElementById("contentId").value;
				if(contentId==null || contentId==""){
					alert("请选择推荐内容!");
					return;
				}
				showMenu1('${path}/admin/recommend!channelTree.action?siteId='+parentSiteId+"&contentId="+contentId);
			}
			
		</script>
		<style type="text/css">
			div.content_wrap {width: 200px;height:380px;}/**/
			div.content_wrap div.left{float: left;width: 250px;}
			div.content_wrap div.right{float: right;width: 340px;}
			div.zTreeDemoBackground {width:250px;height:362px;text-align:left;}
			
			ul.ztree {border: 1px solid #617775;width:78%;height:80%; background: #F2F7FB;overflow-y:scroll;overflow-x:auto;margin-top:0; width:auto; height: 92%;}/**/
			ul.log {border: 1px solid #617775;background: #f0f6e4;width:300px;height:170px;overflow: hidden;}
			ul.log.small {height:45px;}
			ul.log li {color: #666666;list-style: none;padding-left: 10px;}
			ul.log li.dark {background-color: #E3E3E3;}
			.menuContent{display:none; position: absolute;width:auto;position:relative;z-index:999;}
			
			/* ruler */
			div.ruler {height:20px; width:220px; background-color:#f0f6e4;border: 1px solid #333; margin-bottom: 5px; cursor: pointer}
			div.ruler div.cursor {height:20px; width:30px; background-color:#3C6E31; color:white; text-align: right; padding-right: 5px; cursor: pointer}
			
			.wrap_treeDiv{ position:relative}
			.wrap_treeDiv .star{ color:red}
			.wrap_treeDiv .tit{ font-size:14px;font-weight: bold}
			.wrap_treeDiv #treeDiv{ top: 30px;left: 48px}
		</style>
		
		
    </head>
	<body class="list">
	
	
		<!-- ui-dialog -->
		<div id="dialog" title="推荐" style="display:none">
        	<input id="parentSiteId" type="hidden" value=""/>
        	<input id="contentId" type="hidden" value=""/>
        	
        	<div class="i" style="display: <c:if test="${!mainStation}"> none </c:if>">
	        	<span class="star">*</span>分站：
				<select class="listbar-sel" onchange="changeSubSite(this.value);" id="subSiteId">
					<option value="">--请选择--</option>
					<c:forEach var="f" items="${siteList}">
						<option value="${f.id}">${f.siteName}</option>
					</c:forEach>
				</select>
			</div>
			
        	<div class="i wrap_treeDiv">
	        	<span class="star">*</span>栏目：
			    <input id="keyId" class="formText" type="text" readonly="true" value="" onclick="showChannelTree();"/>
			    <span id="showText" class="txt"></span>
			    
			    <input id="valueId" type="hidden" value=""/>
			    <div id="treeDiv"><ul id="treeContent" class="ztree" style="width:200px;"/></div>
			</div>
		</div>
		
		
		<div class="main-top-hd clearfix">
	        <h3 class="cur"><a href="javascript:void(0);">内容列表</a></h3>
	    </div>
		<form id="contentForm" method="post" action="${path}/admin/content!list.action">
			<input type="hidden" name="leaf" value="${leaf}"/>
			<input type="hidden" name="channelId" value="${channelId}"/>
			<input type="hidden" name="action" value="${action}"/>
			<input type="hidden"  name="public" value="${public}"/>
			
			<div class="main-action-bar">
				<c:if test="${add && leaf && !single}">
				    <c:choose>
						<c:when test="${public}">
							<a class="ui-btn-wrap" href="${path}/admin/${action}!edit.action?leaf=${leaf}&channelId=${channelId}&action=${action}&status=${status}&contentCheck=${check}&contentGenerate=${generate}">
					            <span class="ui-action-btn ui-add-btn-ico" style="font-size:14px">添加</span>
					        </a>
					    </c:when>
					    <c:otherwise>
					    	<a class="ui-btn-wrap" href="${path}/admin/${action}Import!edit.action?leaf=${leaf}&channelId=${channelId}&action=${action}&status=${status}&public=${public}">
					            <span class="ui-action-btn ui-add-btn-ico" style="font-size:14px">引入</span>
					        </a>
					    </c:otherwise>
					</c:choose>
			    </c:if>
		        <!-- 查询、分页 -->
		        <jsp:include page="../../../page/include/search.jsp"></jsp:include>
		    </div>
		    <div class="main-action-bar" style="font-size:14px;height:30px;">
		    	<input id="status_-1" type="radio" name="status" value="-1" onchange="searchSubmit()" <c:if test="${status == -1}"> checked="checked" </c:if>/><label for="status_-1">所有状态</label>
		    	<c:forEach var="item" items="${contentStatusList}">
					<input id="status_${item.key}" type="radio" name="status" value="${item.key}" onclick="searchSubmit()" <c:if test="${status == item.key}"> checked="checked" </c:if>/><label for="status_${item.key}">${item.value}</label>
				</c:forEach>
		    </div>
		    <div class="body">
		    	<table id="table" class="listtable">
					<tr>
						<th class="check" width="10px">
							<input id="selectAll" type="checkbox" onclick="javascript:selectAllCheckBox(this, '${pagination.rowSize}')"/>
						</th>
						<th width="30px">
							<a class="sort">序号</a>
						</th>
						<th width="100px">
							<a class="sort">标题</a>
						</th>
						<th width="65px">
							<a class="sort">创建时间</a>
						</th>
						<th width="30px">
							<a class="sort">创建人</a>
						</th>
						<th width="30px">
							<a class="sort">推荐</a>
						</th>
						<th width="35px">
							<a class="sort">静态化</a>
						</th>
						<th width="30px">
							<a class="sort">状态</a>
						</th>
						<th width="30px">
							<a class="sort">置顶</a>
						</th>
						<th width="30px">
							<a class="sort">排序</a>
						</th>
						<c:if test="${show || update || delete || generate || check}">
							<th width="130px">
								<span>操作</span>
							</th>
						</c:if>
					</tr>
					<c:forEach var="content" items="${pagination.list}" varStatus="e">
						<tr>
							<td>
								<input id="select${e.index}" type="checkbox" value="${content.id}" onclick="selected(this, '${pagination.rowSize}');">
							</td>
							<td>${e.index+1+pagination.rowSize*(pagination.pageNo-1)}</td>
							<td style="text-align: left;">
								<a href="${path}/admin/${content.actionName}!edit.action?edit=true&leaf=${leaf}&channelId=${content.channel.id}&action=${content.actionName}&id=${content.entityId}&status=${status}&contentCheck=${check}&contentGenerate=${generate}&controlId=${content.id}">
									<div class="wrap_txt">
										<span style='color: red;'>[${content.channel.channelName}]</span>&nbsp;${content.title}
									</div>
								</a>
							</td>
							<td><f:formatDate value="${content.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td>${content.creater}</td>
							<td>${content.recommend ? disableItem.trueStr : disableItem.falseStr}</td>
							<td>
								<c:forEach var="item" items="${staticStatusList}">
									<c:if test="${content.staticStatus == item.key}">${item.value}</c:if>
								</c:forEach>
							</td>
							<td>
								<c:forEach var="item" items="${contentStatusList}">
									<c:if test="${content.status == item.key}">${item.value}</c:if>
								</c:forEach>
							</td>
							<td>
								<div class="statusDiv-wrap">
                                	 <a href="#" onclick="showStatusDiv('topDiv', ${e.index}, this);">
										<c:forEach var="item" items="${topmostList}">
											<c:if test="${content.topmost == item.key}"><span id="top${e.index}" style="color: ${item.description};">${item.value}</span></c:if>
										</c:forEach>
									 </a>
									 <c:if test="${update}">
										 <div id="topDiv${e.index}" class="statusDiv">
											<c:forEach var="item" items="${topmostList}">
												<a onclick="statusRow('${path}/admin/content!topmost.action?id=${content.id}', 'top', 'topDiv', ${e.index}, '${item.value}', '${item.key}', '${item.description}');">${item.value}</a><br/>
											</c:forEach>
										 </div>
								     </c:if>
								</div>
							</td>
							<td onclick="sortRow('contentForm', '${path}/admin/content!sort.action?id=${content.id}', ${update}, this);">${content.sort}</td>
							<c:if test="${show || update || delete || generate || check}">
								<td>
									<c:if test="${show}">
										<a href="${path}/admin/${content.actionName}!show.action?edit=true&leaf=${leaf}&channelId=${content.channel.id}&action=${content.actionName}&id=${content.entityId}&status=${status}" class="show">查看</a>
									</c:if>
									<c:if test="${content.status == 3}">
										&nbsp;|&nbsp;<a href="#" id="dialog-link" onclick="recommend('${path}/admin/recommend!recommend.action','${content.id}');" class="show">推荐</a>
									</c:if>
									<c:if test="${generate && content.status == 3}">
										&nbsp;|&nbsp;<a href="#" onclick="ajaxSubmit('contentForm', '', '${path}/admin/content!generate.action?id=${content.id}');" class="show">发布</a>
									</c:if>
									<c:if test="${check && content.status == 2}">
										&nbsp;|&nbsp;<a href="#" onclick="checkRows('contentForm','${path}/admin/content!check.action?status=3', '${pagination.rowSize}', this);" class="show">通过</a>
									</c:if>
									<c:if test="${check && (content.status == 2 || content.status == 3)}">
										&nbsp;|&nbsp;<a href="#" onclick="checkRows('contentForm','${path}/admin/content!check.action?status=4', '${pagination.rowSize}', this, '确认退回吗？');" class="show">退回</a>
									</c:if>
									<c:if test="${update}">
										<c:if test="${content.status == 0}">
											&nbsp;|&nbsp;<a href="#" onclick="checkRows('contentForm','${path}/admin/content!check.action?status=1', '${pagination.rowSize}', this);" class="show">送未审</a>
										</c:if>
										<c:if test="${content.status == 1 || content.status == 4}">
											&nbsp;|&nbsp;<a href="#" onclick="checkRows('contentForm','${path}/admin/content!check.action?status=2', '${pagination.rowSize}', this);" class="show">送待审</a>
										</c:if>
										&nbsp;|&nbsp;<a href="${path}/admin/${content.actionName}!edit.action?edit=true&leaf=${leaf}&channelId=${content.channel.id}&action=${content.actionName}&id=${content.entityId}&status=${status}&contentCheck=${check}&contentGenerate=${generate}&controlId=${content.id}" class="edit">修改</a>
									</c:if>
									<c:if test="${delete}">
										&nbsp;|&nbsp;
											 <c:if test="${(leaf&&!public)}">
											     <a href="#" class="delete" onclick="deleteRow('contentForm', '${path}/admin/content!delete.action', '${pagination.rowSize}', this);">删除引用</a>
											 </c:if>
											 <c:if test="${public or !leaf}">
											     <a href="#" class="delete" onclick="deleteRow('contentForm', '${path}/admin/${content.actionName}!delete.action', '${pagination.rowSize}', this);">删除</a>
											 </c:if>
									</c:if>
								</td>
							</c:if>
						</tr>
					</c:forEach>
				</table>
				<h4 align="center" style="display:${empty pagination.list ? 'block' : 'none' }">暂无数据!</h4>
				<div class="pagerBar">
					<c:if test="${delete && leaf}">
						<c:choose>
	                        <c:when test="${public}">
	                            <a id="deleteButton" class="formButton" disabled="disabled" onclick="deleteRow('contentForm','${path}/admin/${action}!delete.action', '${pagination.rowSize}');">删&nbsp;&nbsp;除</a>
	                        </c:when>
	                        <c:otherwise>
	                            <a id="deleteButton" class="formButton" disabled="disabled" onclick="deleteRow('contentForm','${path}/admin/content!delete.action', '${pagination.rowSize}');">删除引用</a>
	                        </c:otherwise>
	                    </c:choose>
					</c:if>
					<c:if test="${check && status == 2}">
						<a id="checkButton" class="formButton" disabled="disabled" onclick="checkRow('contentForm','${path}/admin/content!check.action?status=3', '${pagination.rowSize}');">审&nbsp;&nbsp;核</a>
			        </c:if>
				</div>
				<jsp:include page="../../../page/common/pager.jsp"></jsp:include>
		    </div>
		</form>
	</body>
</html>