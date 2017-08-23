<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML >
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html lang="zh-CN">
	<head>
		<title>栏目树菜单</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="${path}/css/demo.css" type="text/css">
		<link rel="stylesheet" href="${path}/css/zTreeStyle/zTreeStyle.css" type="text/css">
		<link rel=stylesheet type=text/css href="${path}/css/manage/base.css">
        <link rel=stylesheet type=text/css href="${path}/css/manage/admin.css">
		<script type="text/javascript" src="${path}/js/jquery.js"></script>
		<script type="text/javascript" src="${path}/js/jquery.ztree.core-3.5.js"></script>
		
		<STYLE type=text/css>HTML {
		    HEIGHT: 100%; OVERFLOW: hidden
		}
		BODY {
		    HEIGHT: 100%; OVERFLOW: hidden
		}
		.middle .leftArrow{ width: 199px;border-right: 1px solid #C8D3D9}
		.middle .leftArrow:hover{ background-color: transparent;cursor: default}
		.middle .leftArrow .bgholder,.middle .leftArrow .bgholder2{ width: 200px}
		.middle .leftArrow .bgholder2{ font-size:14px;line-height: 41px;text-indent: 10px}
		</STYLE>
		
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
			        aObj.after(editStr);
				}
			}
	
			$(document).ready(function(){
				$.post('${path}/admin/channel!tempSetTree.action', null, function(data) {
					//alert(data);
					var icons="";
					var zNodes = new Array();
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
							target : 'subFrame',
							channelType : data[i].CHANNEL_TYPE,
							contentType : data[i].CONTENT_TYPE,
							url : '${path}/admin/tempConfig!list.action?channelId='+data[i].ID+'&channelType='+data[i].CHANNEL_TYPE_EN+
							                                        '&contentType='+data[i].CONTENT_TYPE_EN+'&channelTempId='+data[i].CHANNEL_TEMP_ID+
							                                        '&contentTempId='+data[i].CONTENT_TEMP_ID,
							open : data[i].OPEN,
							icon:icons
						};
					}
					$.fn.zTree.init($("#treeDemo"), setting,zNodes);
				}, "json");
			});
			//-->
		</script>
	</head>
	<body class="menu" style="overflow: hidden">
		<div class="body" id="body">
				<div class="body" id="body">
		    <div class="main-top-hd clearfix">
	             <h3 class="cur">栏目列表</h3>
            </div>  
			<div class="wrap_dpBtn" style="padding-left:3px;">
				<span id="open" class="displayBtn" onclick="openAll();">显示全部</span>
	            <span id="close" class="displayBtn" style="display: none" onclick="closeAll();">关闭全部</span>
	            <span id="shua" class="displayBtn" style="color:green;" onclick="parent.menuFrame.location.reload()">刷新</span>	
	            <span id="closeMenus" class="displayBtn" style="color:green;" onclick="changeMenu();">关闭菜单</span>
	            <span id="openMenus" class="displayBtn" style="color:green;display: none" onclick="changeMenu();">显示菜单</span>   	
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
			         function changeMenu(){
				        var $main = $("#main");
				        var mainFrameset = window.parent.window.parent.window.document.getElementById("mainFrameset");
				            if(mainFrameset.cols == "220,6,*"||mainFrameset.cols == "220,6,*") {
				            	G("closeMenus").style.display="none";
				                G("openMenus").style.display="";
				                mainFrameset.cols = "0,6,*";
				                $main.removeClass("leftArrow");
				                $main.addClass("rightArrow");
				                var menuFrame = window.parent.window.parent.window.document.getElementById("menuFrame")//.getElementById("body");
				            } else{
				            	G("closeMenus").style.display="";
				                G("openMenus").style.display="none";
				                mainFrameset.cols = "220,6,*";
				                $main.removeClass("rightArrow");
				                $main.addClass("leftArrow");
				            }
				        }
	            </script>
			</div>
			<div class="content_wrap" style="overflow-y: auto;height:460px;">
                <div class="zTreeDemoBackground left" style="height:auto;">
				   <ul id="treeDemo" class="ztree"></ul>
			    </div>
			</div>
		</div>
	</body>
</html>