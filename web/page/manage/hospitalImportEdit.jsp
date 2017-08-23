<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html lang="zh-CN">
	<head>
		<title>医院引入</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="${path}/css/demo.css" type="text/css">
		<link rel="stylesheet" href="${path}/css/zTreeStyle/zTreeStyle.css" type="text/css">
		<link rel=stylesheet type=text/css href="${path}/css/manage/base.css">
        <link rel=stylesheet type=text/css href="${path}/css/manage/admin.css">
		<script type="text/javascript" src="${path}/js/jquery-1.4.4.min.js"></script>
		<script type="text/javascript" src="${path}/js/jquery.ztree.core-3.5.js"></script>
		<script type="text/javascript" src="${path}/js/jquery.ztree.excheck-3.5.js"></script>
		<script type="text/javascript">
			<!--
			var setting = {
				check: {
					enable: true
				},
				async: {
                 enable: true,
                 url:"${path}/admin/hospitalImport!tree.action",
                 autoParam:["id", "type"],
                 dataFilter: filter
             	}
			};
			
			function filter(treeId, parentNode, data) {
				var array = [];
				var icons="";
			    for(var i=0; i<data.length; i++){ 
			    	icons='${path}/css/zTreeStyle/img/diy/';
                    if(data[i].TYPE=="area"){
                        icons=icons+"1_close.png";
                    }
                    else if(data[i].TYPE=="hospital"){
                        icons=icons+"yiyuan.png";
                    }
			        array[i] = {id:data[i].ID, name:data[i].NAME, isParent:data[i].IS_PARENT, type:data[i].TYPE,icon:icons };        
			    }
			    return array;
	         }
			
			$(document).ready(function(){
				$.fn.zTree.init($("#treeDemo"), setting);
			});
			
			function selectCheckAll(id) {
                var select = document.getElementById(id);
                if(select){
                    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
                    zTree.checkAllNodes(select.checked);
                }
            }
			
			function save(formId) {
				var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
				nodes = zTree.getCheckedNodes(true);
				var ids = "";
				for(var i =0;i<nodes.length;i++){
					if(nodes[i].type =='hospital'){
						if(i>0){
							ids+=",";
						}
						ids+=nodes[i].id;
					}
				}
				var form = document.getElementById(formId);
				form.action = '${path}/admin/hospital_cImport!save.action' +"?ids=" + ids;
				form.submit();
			}
			//-->
		</script>
	</head>
	<body class="input">
		<jsp:include page="../../page/include/image_upload.jsp"></jsp:include>
		<div class="main-top-hd clearfix">
        	<h3 class="cur">
	        	医院引入
	        </h3>
	    </div>
		<div class="body">
			<form id="editHospitalImportForm" method="post">
				<input type="hidden" name="token" value="${token}"/>
				<input type="hidden" name="leaf" value="${leaf}"/>
				<input type="hidden" name="channelId" value="${channelId}"/>
				<input type="hidden" name="action" value="${action}"/>
				<input type="hidden" name="public" value="${public}"/>
				
				<div class="divInputTable">
		            <h3 class="main-tit-bar" id="typeBtn">基本信息</h3>
		            <div id="typeDiv">
		            	<table>
			            	<tr>
								<th>
									<input id="checkAll" type="checkbox" onclick="selectCheckAll('checkAll')"/>&nbsp;<span>*</span>医院
								</th>
								<td>
									<div class="content_wrap">
										<div class="zTreeDemoBackground left">
											<ul id="treeDemo" class="ztree"></ul>
										</div>
									</div>
								</td>
							</tr>
						</table>
			        </div>
			    </div>
				
				<div class="buttonArea">
					<input type="hidden" name="status" value="1"/>
					<input class="formButton" value="保 存" type="button" onclick="save('editHospitalImportForm');"/>&nbsp;&nbsp;
	           		<input class="formButton" onclick="window.location='${path}/admin/content!list.action?leaf=${leaf}&channelId=${channelId}&action=${action}&public=${public}&status=${status}'" value="返  回" type="button">
				</div>
			</form>
		</div>
	</body>
</html>