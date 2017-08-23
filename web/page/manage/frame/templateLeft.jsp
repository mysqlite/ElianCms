<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML >
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html lang="zh-CN">
	<head>
		<title>模板树菜单</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="${path}/css/demo.css" type="text/css">
		<link rel="stylesheet" href="${path}/css/zTreeStyle/zTreeStyle.css" type="text/css">
		<link rel=stylesheet type=text/css href="${path}/css/manage/base.css">
        <link rel=stylesheet type=text/css href="${path}/css/manage/admin.css">
		<script type="text/javascript" src="${path}/js/jquery-1.4.4.min.js"></script>
		<script type="text/javascript" src="${path}/js/jquery.ztree.core-3.5.js"></script>
		<script type="text/javascript">
			<!--
			
			var setting = {
				data: {
					simpleData: {
						enable: true
					}
				},
				callback : {
					/**
					 *	zTree节点onclick事件
					 */
					onClick : function onClick(e, treeId, treeNode) {
						var zTree = $.fn.zTree.getZTreeObj("treeDemo");
						if(!treeNode.open){
							zTree.expandNode(treeNode);
						}
					}
				}
			};
	
			$(document).ready(function(){
				$.post('${path}/admin/template!tree.action', {}, function(data) {
					var zNodes = new Array();
					for ( var i = 0; i < data.length; i++) {
						zNodes[i] = {
							id : data[i].ID,
							pId : data[i].PID,
							name : data[i].NAME,
							target : 'mainFrame',
							url : data[i].DIR ? '${path}/admin/template!list.action?tempId='+data[i].ID :'${path}/admin/templateSet!list.action?tempId='+data[i].ID,
							open : data[i].OPEN
						};
					}
					$.fn.zTree.init($("#treeDemo"), setting, zNodes);
				}, "json");
			});
			//-->
		</script>
	</head>
	<body class="menu">
		<div class="body" id="body">
			<jsp:include page="../../../page/include/time.jsp"/>
			<div class="wrap_dpBtn" style="padding-left:3px;">
				<span id="open" class="displayBtn" onclick="openAll();">显示全部</span>
	            <span id="close" class="displayBtn" style="display: none" onclick="closeAll();">关闭全部</span>
	            <script type="text/javascript">
	            	G = function(id) {return document.getElementById(id);}
	            	function openAll(){
			            G("open").style.display="none";
			            G("close").style.display="";
			          var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
			            treeObj.expandAll(true);
			        } 
			        function closeAll(){
			            G("close").style.display="none";
			            G("open").style.display="";
			          var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
			            treeObj.expandAll(false);
			        }
	            </script>
	            <span id="shua" class="displayBtn" style="color:green;" onclick="parent.menuFrame.location.reload()">刷新</span>
			</div>
			<div class="content_wrap">
				<div class="zTreeDemoBackground left">
					<ul id="treeDemo" class="ztree"></ul>
				</div>
			</div>
		</div>
	</body>
</html>