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
		<title>编辑角色</title>
		<link rel="stylesheet" href="${path}/css/manage/base.css" type="text/css"/>
        <link rel="stylesheet" href="${path}/css/manage/admin.css" type="text/css"/>
        <link rel="stylesheet" href="${path}/css/zTreeStyle/zTreeStyle.css" type="text/css">
		<script type="text/javascript" src="${path}/js/jquery-1.4.4.min.js"></script>
		<script type="text/javascript" src="${path}/js/jquery.ztree.core-3.5.js"></script>
        <script type="text/javascript" src="${path}/js/jquery.ztree.excheck-3.5.js"></script>
		<script type="text/javascript">
			var IDMark_A = "_a";
			
			var setting = {
				check: {
					enable: true
				},
				data: {
					key: {
						title: "title"
					},
					simpleData: {
						enable: true
					}
				},
				view: {
					addDiyDom: addDiyDom
				},
				callback: {
					onCheck: onCheck
				}
			};
			
			function addDiyDom(treeId, treeNode) {
				var aObj = $("#" + treeNode.tId + IDMark_A);
				var checkedStr = treeNode.authorize == 'true' ? " checked='true'" : "";
		        var editStr = "&nbsp;&nbsp;<input id='select_" +treeNode.id+ "' type='checkbox'"+checkedStr+"/>可授权";
		        var autBtn = $("#select_"+treeNode.id);
		        aObj.after(editStr);
		        if (autBtn)
		        	autBtn.bind("click", function(){
		          alert("aa");
		        });
			}
	
            function setCheck() {
            var zTree = $.fn.zTree.getZTreeObj("treeDemo"),py = true,sy = true,pn = true,sn = true,type = { "Y":py + sy, "N":pn + sn};
            zTree.setting.check.chkboxType = type;
             }
			function onCheck(e, treeId, treeNode) {
				count();
			}
	
			function setTitle(node) {
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				var nodes = node ? [node]:zTree.transformToArray(zTree.getNodes());
				for (var i=0, l=nodes.length; i<l; i++) {
					var n = nodes[i];
					n.title = "[" + n.id + "] isFirstNode = " + n.isFirstNode + ", isLastNode = " + n.isLastNode;
					zTree.updateNode(n);
				}
			}
			
			function count() {
				function isForceHidden(node) {
					if (!node.parentTId) return false;
					var p = node.getParentNode();
					return !!p.isHidden ? true : isForceHidden(p);
				}
				var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
				checkCount = zTree.getCheckedNodes(true).length,
				nocheckCount = zTree.getCheckedNodes(false).length,
				hiddenNodes = zTree.getNodesByParam("isHidden", true),
				hiddenCount = hiddenNodes.length;
	
				for (var i=0, j=hiddenNodes.length; i<j; i++) {
					var n = hiddenNodes[i];
					if (isForceHidden(n)) {
						hiddenCount -= 1;
					} else if (n.isParent) {
						hiddenCount += zTree.transformToArray(n.children).length;
					}
				}
	
				$("#isHiddenCount").text(hiddenNodes.length);
				$("#hiddenCount").text(hiddenCount);
				$("#checkCount").text(checkCount);
				$("#nocheckCount").text(nocheckCount);
			}
			
			function showNodes() {
				var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
				nodes = zTree.getNodesByParam("isHidden", true);
				zTree.showNodes(nodes);
				setTitle();
				count();
			}
			
			function hideNodes() {
				var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
				nodes = zTree.getSelectedNodes();
				if (nodes.length == 0) {
					alert("请至少选择一个节点");
					return;
				}
				zTree.hideNodes(nodes);
				setTitle();
				count();
			}
			
			function loadTree(actionId){
				$.post('${path}/admin/roleAction!treeModel.action', {id : actionId}, function(data) {
					var zNodes = new Array();
					for ( var i = 0; i < data.length; i++) {
						zNodes[i] = {
							id : data[i].id,
							pId : data[i].parentId,
							name : data[i].actionName,
							checked : data[i].selected,
							authorize : data[i].actionDesc,
							open : true
						};
					}
					$.fn.zTree.init($("#treeDemo"), setting, zNodes);
					$("#hideNodesBtn").bind("click", {type:"rename"}, hideNodes);
					$("#showNodesBtn").bind("click", {type:"icon"}, showNodes);
					
					setTitle();
					count();
				}, "json");
			}
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
				var authorizeIds = "";
				for(var i =0,j=0;i<nodes.length;i++){
					if(j>0){
						ids+=",";
					}
					ids+=nodes[i].id;
					j++;
					
					
					var authorizeObj = document.getElementById("select_"+nodes[i].id);
					if(authorizeObj.checked){
						authorizeIds += nodes[i].id + ",";
					}
				}
				var form = document.getElementById(formId);
				form.action += "?ids=" + ids+"&authorizeIds=" + authorizeIds;
				form.submit();
			}
		</script>
	</head>
	<body class="input" onload="loadTree(${role.id});">
		<div class="main-top-hd clearfix">
        	<h3 class="cur">
	        	<c:if test="${!edit}">角色权限添加</c:if>
				<c:if test="${edit}">角色权限修改</c:if>
	        </h3>
	    </div>
		<div class="body">
			<form id="editRoleActionForm" action="${path}/admin/roleAction!save.action" method="post">
				<input type="hidden" name="edit" value="${edit}">
				<input type="hidden" name="token" value="${token}"/>
				
				<div class="divInputTable">
		            <h3 class="main-tit-bar" id="typeBtn">基本信息</h3>
		            <div id="typeDiv">
		            	<ul class="inputList">
		            		<li class="inputList-li">
			                	<div class="listItem">
									<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>角色名称：
		                     		</label>
			                        <c:if test="${edit}"><input name="role.id" type="hidden" value="${role.id}"/></c:if>
									<select name="role.id" class="listbar-sel" <c:if test="${edit}">disabled="true"</c:if>>
										<option value="">--请选择--</option>
										<c:forEach var="r" items="${roleList}">
											<option value="${r.id}" <c:if test="${r.id == role.id}">selected="selected"</c:if>>${r.roleName}</option>
										</c:forEach>
									</select>
									<span class="errortip">${errors['role.id'][0]}</span>
			                   	</div>
			                </li>
			            </ul>
			            <table>
			            	<tr>
								<th>
									<input id="checkAll" type="checkbox" onclick="selectCheckAll('checkAll')"/>&nbsp;<span>*</span>权限
								</th>
								<span id="open" class="displayBtn" style="display: none" onclick="openAll();">显示全部</span>
                                <span id="close" class="displayBtn"  onclick="closeAll();">关闭全部</span>
								<td>
									<div class="content_wrap">
										<div class="zTreeDemoBackground left">
											<ul id="treeDemo" class="ztree"></ul>
										</div>
									</div>
									<span class="errortip">${errors['actionIds'][0]}</span>   
								</td>
							</tr>
						</table>
			        </div>
			    </div>
				<div class="buttonArea">
					<input class="formButton" value="保 存" type="button" onclick="save('editRoleActionForm');"/>&nbsp;&nbsp;
            		<input class="formButton" onclick="window.location='${path}/admin/role!list.action'" value="返  回" type="button"/>
				</div>
			</form>
		</div>
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
	</body>
</html>