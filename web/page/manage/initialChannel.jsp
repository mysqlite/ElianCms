<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
        <title>默认栏目列表</title>
        <jsp:include page="../../page/include/headList.jsp"></jsp:include>
        <script type="text/javascript">
	function getAllCheckBox(selectAll){	        	
		disabledButton(!selectAll.checked);
		var rowSize=$("tr").length-1;
		for (var i = 0; i < rowSize; i++) {
			var current = eval(i);
	        var select = document.getElementById('select'+current);
			if(select){
				select.checked = selectAll.checked;
			}
		}
	}
	function deleteAllCheckedBox(){	        	
		var rowSize=$("tr").length-1;
		for (var i = 0; i < rowSize; i++) {
			var current = eval(i);
	        var select = document.getElementById('select'+current);
			if(select){
				select.checked = '';
			}
		}
		document.getElementById('selectAll').checked = '';
	}

	function getSelected(selected){
		var checked = true;
		var rowSize=$("tr").length-1;		
		for (var i = 0; i < rowSize; i++) {
			var current = eval(i);
	        var select = document.getElementById('select'+current);
	        if(select && select.checked){
	        	checked = false;
	        	break;
	        }		
		}
		disabledButton(checked);
	}
	
	function del(formId, url, currentObj){
		var rowSize=$("tr").length-1;	
		if(currentObj!=null && currentObj != undefined){
			select(currentObj, rowSize);
		}
		var ids=getTableIds(rowSize);
		if(""==ids){
			alert("请勾选删除行！")
			return;
		}
		disabledButton(false);
		if (confirm('确认删除吗？'))
	 		ajaxSubmit(formId, ids, url);
		}
	function addToChannel(url, currentObj){
		var rowSize=$("tr").length-1;	
		if(currentObj!=null && currentObj != undefined){
			select(currentObj, rowSize);
		}
		var ids=getTableIds(rowSize);
		if(""==ids){
			alert("请勾选删除行！")
			return;
		}
		disabledButton(false);
		if (confirm('确认要初始化选中的栏目吗？'))
	 		//ajaxSubmit(formId, ids, url);
		$.post(url,{'ids':ids},function(datas){
			alert(datas['msg']);
		}, "json");
		deleteAllCheckedBox();
		}
        </script>
    </head>
    <body class="list">  
    <div class="main-top-hd clearfix">
        <h3 class="cur"><a href="javascript:void(0);">默认栏目列表</a></h3>
    </div>
    <form id="initChannelForm" method="post" action="${path}/admin/initialChannel!list.action">
    <div class="main-action-bar">
        <c:if test="${add}">
        <a class="ui-btn-wrap" href="${path}/admin/initialChannel!edit.action">
            <span class="ui-action-btn ui-add-btn-ico" style="font-size:14px">添加</span>
        </a>
        </c:if>
    </div>
   <c:set var="shunxu" value="-1"/>
   <div class="body">
        <table id="listtable" class="listtable">
            <tbody>
                <tr>
                    <th class="check">
                    	<input class="allCheck" type="checkbox" id="selectAll" onclick="javascript:getAllCheckBox(this)">
                    </th>
                    <th class="w70">序号</th>
                    <th>栏目名称</th>
                    <th class="Ttitle">栏目所属机构</th>
                    <th>栏目类型</th>
                    <th class="th6">
							<a class="sort">导航</a>
					</th>
                    <th class="th6">
							<a class="sort">状态</a>
					</th>
                    <th class="w70">排序</th>
                    <c:if test="${add or update or delete}">
                        <th class="w150"><span>操作</span></th>
                    </c:if>
                </tr>   
                <c:set var="order" value="0"/>
                <c:set var="index" value="0"/>
                <c:forEach var="item" items="${permissionTypeList}">
                	<c:forEach var="channel" items="${channelList[index]}">                	
                		<c:set var="shunxu" value="${shunxu+1}"/>                		
                		<c:set var="order" value="${order+1}"/>                		
                		<tr>
                			<td><input id="select${shunxu}" type="checkbox" onclick="getSelected(this)" value="${channel.channelId}"/></td>
                			<td>${order}</td>
                			<td>${channel.channelName}</td>
                			<td>${item.typeName}</td>
                			<td>
                			<c:set var="isParent" value="${false}" />
                			<c:forEach var="cType" items="${channelTypeList}">                			                			
	                			<c:if test="${cType.key==channel.channelType}">	                				
	                				${cType.value}	                				
	                			</c:if>
	                			<c:if test="${channelTypeParentName==channel.channelType}">	                				
                					<c:set var="isParent" value="${true}" />
	                			</c:if>
                			</c:forEach>
                			${channel.model.modelName}</td>
                			<td>${channel.navigetor ? '是' : '否'}
	                			<!--  
	                				<a href="#" style="color:${channel.navigetor ? naviItem.trueDescription : naviItem.falseDescription}"
	                					onclick="disableRow('${path}/admin/initialChannel!navigetor.action?id=${channel.channelId}', ${update}, this, '${naviItem.trueStr}', 
											'${naviItem.trueDescription}','${naviItem.falseStr}', '${naviItem.falseDescription}');">
										${channel.navigetor ? naviItem.trueStr : naviItem.falseStr}
									</a>
								-->
							</td>
							<td>
								<a href="#" style="color:${channel.disable ? disableItem.trueDescription : disableItem.falseDescription}"
								onclick="disableRow('${path}/admin/initialChannel!disable.action?id=${channel.channelId}', ${update}, this, '${disableItem.trueStr}', 
										'${disableItem.trueDescription}','${disableItem.falseStr}', '${disableItem.falseDescription}');">
									${channel.disable ? disableItem.trueStr : disableItem.falseStr}
								</a>
							</td>
							
							<td onclick="sortRow('initChannelForm', '${path}/admin/initialChannel!sort.action?id=${channel.channelId}', ${update}, this);">${channel.channelSort}</td>
						<!-- 	<td>${channel.channelSort}</td>		 -->					
							<c:if test="${add || update || delete}">
								<td>										
									<c:if test="${update}">
										<a href="${path}/admin/initialChannel!edit.action?addSub=true&edit=true&initChannel.channelId=${channel.channelId}" class="edit">修改</a>
									</c:if>
									<c:if test="${delete}">										
										&nbsp;|&nbsp;<a href="#" class="delete" onclick="del('initChannelForm', '${path}/admin/initialChannel!delete.action', this);">删除</a>
									</c:if>
									<c:if test="${add && isParent}">
										&nbsp;|&nbsp;<a href="${path}/admin/initialChannel!edit.action?addSub=true&initChannel.channelId=${channel.channelId}" class="edit">添加栏目</a>
									</c:if>
								</td>
							</c:if>								
                		</tr>
                		<c:if test="${isParent}">
                			<c:forEach var="subChannel" items="${channel.subChannelList}">
	                			<c:set var="shunxu" value="${shunxu+1}"/>  
	                			<tr>
		                			<td><input id="select${shunxu}"  type="checkbox" onclick="getSelected(this)" value="${subChannel.channelId}"/></td>
		                			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;----|</td>
		                			<td>${subChannel.channelName}</td>
		                			<td>${item.typeName}</td>
		                			<td>
			                			<c:forEach var="cType" items="${channelTypeList}">                			                			
				                			<c:if test="${cType.key==subChannel.channelType}">	                				
				                				${cType.value}	                				
				                			</c:if>
			                			</c:forEach>   ${subChannel.model.modelName}
			                		</td>
		                			<td>
		                				${subChannel.navigetor ? '是' : '否'}
									</td>
									<td>
										<a href="#" style="color:${subChannel.disable ? disableItem.trueDescription : disableItem.falseDescription}"
										onclick="disableRow('${path}/admin/initialChannel!disable.action?id=${subChannel.channelId}', ${update}, this, '${disableItem.trueStr}', 
												'${disableItem.trueDescription}','${disableItem.falseStr}', '${disableItem.falseDescription}');">
											${subChannel.disable ? disableItem.trueStr : disableItem.falseStr}
										</a>
									</td>
									<td onclick="sortRow('initChannelForm', '${path}/admin/initialChannel!sort.action?id=${subChannel.channelId}', ${update}, this);">${subChannel.channelSort}</td>	
									<!-- <td>${subChannel.channelSort}</td>		 -->							
									<c:if test="${update || delete}">
									<td>										
										<c:if test="${update}">
											<a href="${path}/admin/initialChannel!edit.action?addSub=true&edit=true&initChannel.channelId=${subChannel.channelId}" class="edit">修改</a>
										</c:if>
										<c:if test="${delete}">										
											&nbsp;|&nbsp;<a href="#" class="delete" onclick="del('initChannelForm','${path}/admin/initialChannel!delete.action', this);">删除</a>
										</c:if>										
									</td>
								</c:if>								
	                			</tr>
                			</c:forEach>                			
                		</c:if>
                	</c:forEach> 
                     <c:set var="index" value="${index+1}"/>                	
                </c:forEach>                           
            </tbody>
        </table> 
        <h4 align="center" style="display:${empty permissionTypeList ? 'block' : 'none' }">暂无数据!</h4>
        <div class=pagerBar>
	        <c:if test="${delete}">
	            <div class=delete>
	               <input  id="deleteButton" disabled="disabled" class=formButton value="删 除" type=button onclick="del('initChannelForm','${path}/admin/initialChannel!delete.action');">
	               <input  id="addChannelButton" class=formButton value="添加到栏目" type=button onclick="addToChannel('${path}/admin/initialChannel!addToChannel.action');">
	            </div> 
	        </c:if>
        </div>
    </div>
  </form>    
    <script type="text/javascript">
    function getInitialChannelList(typeId,parentId){
    	$.post('${path}'+"/admin/initialChannel!getInitialChannelList.action",
		{"deptTypeId" : typeId,"parentId":parentId},function(datas){			
				if (datas != null && datas != "") {
				var str = "<table class='listtable'>" +
								"<tbody>";
				for(var i=0;;i++){
					if(datas[i]==null || datas[i]==undefined)
						break;	
					str +="<tr>";
					for ( var j = 0; j < datas[i].length; j++) {
						str += "<th class='check'></th>"+
	               				"<th class='w70'>"+j+"</th>"+
	               				"<th width='30%'>"+datas[i][j].channelName+"</th>"
								
						$(str).appendTo($("#initialChannelList" + datas[i][j].id));
					}
					str +="</tr>";
					}	
					str+="</tbody></table>"			
	    		}
				
			}, "json");
    }
    </script>
    </body>
</html>
