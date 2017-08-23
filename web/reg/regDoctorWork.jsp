<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML >
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html lang="zh-CN">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>91580 就医我帮您智能挂号平台</title>
		<meta name="Keywords" content="91580就医我帮您智能挂号平台" />
		<meta name="Description" content="91580就医我帮您，去就医，去挂号我帮您" />
		<link rel="stylesheet" href="http://style.elian.cc/main/reg/style/sub.css" type="text/css" media="screen" />
		<link rel="shortcut icon" type="image/x-icon" href="http://www.915800.com/favicon.ico" />
		<script src="http://script.elian.cc/public/jquery-1.4.2.min.js"></script>
		<script src="${path}/js/proscenium/duty_tbl.js"></script>
		<script type="text/javascript">
			function checkLogin(){
				var bool = false;
				$.ajax({
			    	type: 'POST',
			        url: '${path}/reg/regDoctorWork!checkLogin.action',
			        async: false,
					dataType: "json",
					success:function(data){
						bool = data.MSG;
					}
			    });
				return bool;
			}
			
			function submitRegister(doctorId, rank, dateYhd, dateHm){
				var mainWindow = top?top:window;
				mainWindow.location = '${path}/reg/regRegisterConfirm!confirm.action?i='+doctorId+'&r='+rank+'&dy='+dateYhd+'&dh='+dateHm;
			}
		</script>
	</head>
	<body>
		<div class="duty_tbl doc_details_duty_tbl" id="doc-list">
			<div class="hd">
				<span class="duty-time">出诊时间：${dateMark}</span>
				<span class="intro-menzhen">
                    <c:forEach var="item" items="${regTypeList}"  varStatus="e">
						<b class="ico ico${e.index+1}">${item.value}</b>
					</c:forEach>
                </span>
			</div>
			<table cellspacing="0" cellpadding="0" class="tbl">
                 <thead>
                     <tr>
                         <th class="tit"></th>
                         <c:forEach var="item" items="${dateList}">
	                         <th>星期${item.value}<br/>${item.key}</th>
                         </c:forEach>
                     </tr>
                 </thead>
             	 <tbody>
             	   <c:forEach var="r" items="${rankList}">
	                   <tr>
	             	   	   <td>${r.value}</td>
	             	   	   <c:forEach var="d" items="${dateList}">
		                   	   <td data-date="${d.key}|星期${d.value}">
		                   	   	   <c:set var="key" value="${r.key}-${d.key}" />
		                   	   	   <c:if test="${workMap[key]!=null}">
		                   	   	   	   <c:forEach var="data" items="${workMap[key]}" varStatus="e">
		                   	   	   	   	   <c:if test="${e.index==0}">
		                   	   	   	   	   	   <b class="ico ico${data[0]}"></b>
		                   	   	   	   	   </c:if>
		                   	   	   	   </c:forEach>
		                               <div class="time_list">
		                               	<b class="show_btn">马上预约</b>
		                                   <div class="time_list_bd">
		                                    <div class="ui_hd">
		                                        <h3></b>选择就诊时间段</h3>
		                                    </div>
		                                       <table cellspacing="0" cellpadding="0" class="list">    
		                                       	   <tbody>
		                                         		<tr>
		                                         			<c:forEach var="data" items="${workMap[key]}" varStatus="e">
		                                         				<td>
		                                         					<b class="ico"></b>
		                                         					<a onclick="submitRegister('${doctorId}','${r.key}','${d.key}', '${data[1]}')">${data[1]}</a>
		                                         				</td>
		                                         				<c:if test="${(e.index+1)%5==0}">
		                                         					</tr>
		                                         					<tr>
		                                         				</c:if>
		                                         			</c:forEach>
		                                         		</tr>
		                                         	</tbody>
		                                       </table>
		                                       <span class="duty_tbl_close">关闭</span>
		                                   </div>
		                               </div>
		                   	   	   </c:if>
		                   	   </td>
	                       </c:forEach>
	             	   </tr>
             	   </c:forEach>
             	</tbody>
            </table>
            <div id="jq_time_list_bd" class="time_list_bd">
		    	<div class="ui_hd">
		        	<h3>选择就诊时间段</h3>
		        </div>
		    	<table cellspacing="0" cellpadding="0" class="list">
		        </table>
		        <span class="duty_tbl_close">关闭</span>
		    </div>
		</div>
	</body>
</html>