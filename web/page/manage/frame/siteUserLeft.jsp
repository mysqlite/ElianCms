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
		<script type="text/javascript" src="${path}/js/jquery.ztree.excheck-3.5.js"></script>
		<script type="text/javascript" src="${path}/js/jquery.ztree.exedit-3.3.js"></script>
		<script type="text/javascript">
			<!--
			
			var setting = {
				view: {
					addHoverDom: addHoverDom,
					removeHoverDom: removeHoverDom,
					selectedMulti: false
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				callback: {
					beforeDrag: beforeDrag,
					beforeRemove: beforeRemove,
					beforeRename: beforeRename,
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
			
			function beforeDrag(treeId, treeNodes) {
				return false;
			}
			
			function beforeRemove(treeId, treeNode) {
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				zTree.selectNode(treeNode);
				if(confirm("确认删除角色[" + treeNode.name + "]吗？")){
					$.post('${path}/admin/siteUser!deleteRole.action', {'id': treeNode.id}, function(data) {
						alert("角色删除成功");
					}, "json");
					return true;
				}
				return false;
			}
			
			function beforeRename(treeId, treeNode, newName) {
				if (newName.length == 0) {
					alert("角色名称不能为空");
					var zTree = $.fn.zTree.getZTreeObj("treeDemo");
					setTimeout(function(){zTree.editName(treeNode)}, 10);
					return false;
				}
				$.post('${path}/admin/siteUser!updateRole.action', {'id':treeNode.id,'roleName':newName}, function(data) {
					alert("角色修改成功");
				}, "json");
				
				return true;
			}
	
			function addHoverDom(treeId, treeNode) {
				return false;
			};
			
			function removeHoverDom(treeId, treeNode) {
				$("#addBtn_"+treeNode.id).unbind().remove();
			};
			
			$(document).ready(function(){
				$.post('${path}/admin/siteUser!roleTreeModel.action', null, function(data) {
					var zNodes = new Array();
					for ( var i = 0; i < data.length; i++) {
						zNodes[i] = {
							id : data[i].ID,
							pId : data[i].PID,
							name : data[i].NAME,
							compType : data[i].COMP_TYPE,
							target : 'siteUserMainFrame',
							url : data[i].ID!=0 ? '${path}/admin/siteUser!list.action?roleId='+data[i].ID : null
						};
					}
					$.fn.zTree.init($("#treeDemo"), setting, zNodes);
				}, "json");
			});
			
			function addRole(url){
				var roleName = document.getElementById("roleName");
				if(!roleName || roleName.value == null || roleName.value == ''){
					alert("角色名称不能为空");
					return;
				}
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				var nodes = zTree.getSelectedNodes();
				if (nodes.length == 0 || !nodes[0].isParent) {
					alert("请选择一个站点");
					return;
				}
				
				$.post(url, {'compType':nodes[0].compType,'roleName':roleName.value}, function(data) {
					var zTree = $.fn.zTree.getZTreeObj("treeDemo");
					zTree.addNodes(nodes[0], {id : data.ID,
											 pId : data.PID,
											name : data.NAME,
										compType : data.COMP_TYPE});
				}, "json");
			}
			
			//-->
		</script>
		<style type="text/css">
			.ztree li span.button.add {
				margin-left:2px; 
				margin-right: -1px; 
				background-position:-144px 0; 
				vertical-align:top; 
			    *vertical-align:middle
			}
		</style>
	</head>
	<body class="menu" style="background-color: #F2F7FB">
		<jsp:include page="/page/include/time.jsp"/>
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
		</div>
		<span>
			角色名称：<input id="roleName" type="text" style="width: 50px;"/>
			<input type="submit" value="添加" onclick="addRole('${path}/admin/siteUser!addRole.action')"/>
		</span>
		<div class="content_wrap">
			<div class="zTreeDemoBackground left">
				<ul id="treeDemo" class="ztree"></ul>
			</div>
		</div>
	</body>
</html>