<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML >
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html lang="zh-CN">
	<head>
		<title>内容树菜单</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="${path}/css/demo.css" type="text/css">
		<link rel="stylesheet" href="${path}/css/zTreeStyle/zTreeStyle.css" type="text/css">
		<link rel=stylesheet type=text/css href="${path}/css/manage/base.css">
        <link rel=stylesheet type=text/css href="${path}/css/manage/admin.css">
		<script type="text/javascript" src="${path}/js/jquery-1.4.4.min.js"></script>
		<script type="text/javascript" src="${path}/js/jquery.ztree.core-3.5.js"></script>
		<script type="text/javascript">
			<!--
			var IDMark_A = "_a";
			
			var setting = {
				data: {
					simpleData: {
						enable: true
					}
				},
				view: {
					addDiyDom: addDiyDom
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
			
			function addDiyDom(treeId, treeNode) {
				if(Number(treeNode.id)>0){
					var aObj = $("#" + treeNode.tId + IDMark_A);
			        var editStr = "&nbsp;[<span style='color:red;'>"+treeNode.channelType+"</span>]";
			      //editStr= "<span style='color:green;'>"+treeNode.contentType+"</span>";
			        aObj.after(editStr);
				}
			}
	
			$(document).ready(function(){
				$.post('${path}/admin/content!tree.action', null, function(data) {
					var zNodes = new Array();
					var icons="";
					for ( var i = 0; i < data.length; i++) {
						if(data[i].CONTENT_TYPE=='列表页'){
							icons='${path}/css/zTreeStyle/img/diy/liebiao.png';
						}
						else if(data[i].CONTENT_TYPE=='单页'){
							icons='${path}/css/zTreeStyle/img/diy/danye.png';
						}
						else if(data[i].CHANNEL_TYPE=="父栏目"){
							icons='${path}/css/zTreeStyle/img/diy/1_close.png';
						}
						else if(data[i].CHANNEL_TYPE=="首页"){
							icons='${path}/css/zTreeStyle/img/diy/3.png';
						}
						else if(data[i].CHANNEL_TYPE=="外部链接"){
                            icons='${path}/css/zTreeStyle/img/diy/4.png';
                        }
						else if(data[i].CHANNEL_TYPE=="友情链接"){
                            icons='${path}/css/zTreeStyle/img/diy/4.png';
                        }
						zNodes[i] = {
							id : data[i].ID,
							pId : data[i].PID,
							name : data[i].NAME,
							target : 'mainFrame',
							channelType : data[i].CHANNEL_TYPE,
							contentType : data[i].CONTENT_TYPE,
							url : '${path}/admin/content!list.action?leaf='+data[i].IS_LEAF+'&channelId='+data[i].ID+'&action='+data[i].ACTION_NAME+'&public='+data[i].IS_PUBLIC,
							open : data[i].OPEN,
							icon:icons
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