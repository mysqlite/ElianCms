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
        <jsp:include page="../../page/include/headEdit.jsp"></jsp:include>
    </head>
	<body class="input">
	<div class="main-top-hd clearfix">
	    <h3 class="cur">
		    <c:if test="${edit}">医院编辑</c:if>
		    <c:if test="${!edit}">医院添加</c:if>
	    </h3>
	</div>
<div class=body>
    <jsp:include page="../../page/include/f_upload.jsp"></jsp:include>
    <c:if test="${!empty channelId}">
		<form name="editDepartmentForm" action="${path}/admin/hospital_c!save.action" method="post">
	</c:if>
	<c:if test="${empty channelId}">
		<c:if test="${!ztree}">
	      <form id="hospitalCOrU" name="hospitalCOrU" method="post" action="${path}/admin/hospital!save.action">
	    </c:if>
	    <c:if test="${ztree}">
	       <form id="hospitalCOrU" name="hospitalCOrU" method="post" action="${path}/admin/hospitalTree!save.action">
           <input type="hidden" name="ztree" value="${ztree}"/>
           <input type="hidden" name="areaCode" value="${areaCode}"/>
	    </c:if>
	</c:if>
        <input type="hidden" name="token" value="${token}"/>
        <input type="hidden" name="hospital.version" value="${hospital.version}"/>
        <input type="hidden" name="hospital.id" value="${hospital.id}"/>
        <input type="hidden" name="hospital.createTime" value="${hospital.createTime}"/>
        <input type="hidden" name="hospital.hits" value="${!empty hospital.hits?hospital.hits:0}"/>
        <input type="hidden" name="hospital.auditTime" value="${hospital.auditTime}"/>
        <input type="hidden" name="hospital.auditor" value="${hospital.auditor}"/>
        <input type="hidden" name="hospital.followupTime" value="${hospital.followupTime}"/>
        <input type="hidden" name="hospital.regTime" value="${hospital.regTime}"/>
        <input type="hidden" name="edit" value="${edit}"/>
        <input type="hidden" name="leaf" value="${leaf}"/>
        <input type="hidden" name="channelId" value="${channelId}"/>
        <input type="hidden" name="action" value="${action}"/>
        <input type="hidden" name="myHosp" value="${myHosp}"/>
        <div class="divInputTable">
            <h3 class="main-tit-bar" id="mainBtn">基本信息 </h3>
            <div id="mainDiv">
            <ul class="inputList">
                <li class="inputList-li">
                   <div class="listItem">
                     <label class="lbl-ipt-tit"><span class="star">*</span>医院状态：</label>
                     <c:if test="${empty sessionScope._hospital}">
                        <c:forEach items="${disableStatus}" var="item" varStatus="e">
                           <c:if test="${item.key eq user.status}">
                              <input name="hospital.status" value="${hospital.status}" type="hidden"/>
                              <span class="txt">${item.value}</span>
                              <c:set var="disab" value="true"></c:set>
                          </c:if>
                       </c:forEach>
                       <c:if test="${empty disab}">
                            <c:forEach items="${mainHospitalStatusList}" var="item" varStatus="e">
                                <label class="lbl-ipt-tit radioWrap">
                                    <input type="radio" name="hospital.status" value="${item.key}" 
                                    <c:if test="${hospital.status eq item.key or empty hospital.status}">checked </c:if> />
                                       ${item.value}
                                </label>
                            </c:forEach>
                       </c:if>
                     </c:if>
                     <c:if test="${!empty sessionScope._hospital}">
                       <input type="hidden" name="hospital.status" value="${hospital.status}"/>
                        <span class="txt">
                            <c:forEach items="${mainHospitalStatusList}" var="item" varStatus="e">
                                <c:if test="${hospital.status eq item.key or empty hospital.status}">${item.value}</c:if>
                            </c:forEach>
                        </span>
                     </c:if>
                   </div>
                   <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>是否医保医院：</label>
                     <label class="lbl-ipt-tit radioWrap">
                         <input type="radio" name="hospital.health" value="true"<c:if test="${hospital.health}"> checked</c:if> />是
                     </label>
                     <label class="lbl-ipt-tit radioWrap">
                         <input type="radio" name="hospital.health" value="false"<c:if test="${!hospital.health}">checked</c:if> />否
                     </label>
                   </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>是否挂号：</label>
                     <label class="lbl-ipt-tit radioWrap">
                         <input type="radio" name="hospital.reg" value="true"<c:if test="${hospital.reg}"> checked</c:if> />是
                     </label>
                     <label class="lbl-ipt-tit radioWrap">
                         <input type="radio" name="hospital.reg" value="false"<c:if test="${!hospital.reg}">checked</c:if> />否
                     </label>
                   </div>
                   <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>是否随访：</label>
                     <label class="lbl-ipt-tit radioWrap">
                         <input type="radio" name="hospital.followup" value="true"<c:if test="${hospital.followup}"> checked</c:if> />是
                     </label>
                     <label class="lbl-ipt-tit radioWrap">
                         <input type="radio" name="hospital.followup" value="false"<c:if test="${!hospital.followup}">checked</c:if> />否
                     </label>
                   </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>医院名称：</label>
                    <input type="text" class="ipt" name="hospital.hospName" value="${hospital.hospName}"/>
                    <span class="errortip">${errors['hospital.hospName'][0]}</span>
                   </div>
                   <div class="listItem">
                    <label class="lbl-ipt-tit">医院简称：</label>
                    <input type="text" class="ipt" name="hospital.shortName" value="${hospital.shortName}"/>
                    <span class="errortip">${errors['hospital.shortName'][0]}</span>
                   </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>医院性质：</label>
                    <div class="select">
	                     <select name="hospital.nature">
	                        <c:forEach var="nature" items="${natureList}">
	                        <option value="${nature.id}" <c:if test="${nature.id eq hospital.nature}">selected="selected"</c:if>>${nature.typeName}</option>
	                        </c:forEach>
	                     </select>
                    </div>
                    <span class="errortip">${errors['hospital.nature'][0]}</span>
                   </div>
                   <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>医院类型：</label>
                   <div class="select">
                         <select name="hospital.hospType">
                            <c:forEach var="hospType" items="${hospTypeList}">
                            <option value="${hospType.id}" <c:if test="${hospType.id eq hospital.hospType}">selected="selected"</c:if>>${hospType.typeName}</option>
                            </c:forEach>
                         </select>
                    </div>
                    <span class="errortip">${errors['hospital.hospType'][0]}</span>
                   </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>医院等级：</label>
                    <div class="select">
                         <select name="hospital.rank">
                          <c:forEach var="rank" items="${rankList}">
                            <option value="${rank.id}" <c:if test="${rank.id eq hospital.rank}">selected="selected"</c:if>>${rank.typeName}</option>
                            </c:forEach>
                         </select>
                    </div>
                    <span class="errortip">${errors['hospital.rank'][0]}</span>
                   </div>
                </li>
                <li class="inputList-li">
                    <div class="listItem">
                        <label class="lbl-ipt-tit"><span class="star">*</span>医院地址：</label>
                        <input type="text" class="ipt" name="hospital.address" value="${hospital.address}"/>
                        <span class="errortip">${errors['hospital.address'][0]}</span>
                   </div>
                   <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>医院所在区域：</label>
                    <input id="areaId" class="ipt" name="areaName" type="text" readonly="true" value="${!empty areaName?areaName:'中国'}" 
                                    onfocus="showMenuArea('treeContent','areaId','areaValueId','${path}');"
                                    onkeydown="BackSpace('中国','areaId','areaValueId');" />
                     <%--
                     <c:if test="${edit}">
                     <input id="areaId" class="ipt" name="areaName" type="text" readonly="true" value="${!empty areaName?areaName:'中国'}" />
                     </c:if>
                     --%>
                       <input id="areaValueId" type="hidden" name="hospital.areaId" value="${!empty hospital.areaId?hospital.areaId:0}"/>
                                <div id="treeDiv" class="menuContent"><ul id="treeContent" class="ztree" style="width:260px;height: 300px;"></ul></div>
                    <span class="errortip">${errors['hospital.areaId'][0]}</span>
                   </div>
                </li>
                </ul>
                </div>
                <h3 class="main-tit-bar" id="extBtn">扩展信息</h3>
                <div id="extDiv">
                <ul class="inputList">
                <li class="inputList-li">
                   <div class="listItem">
	                    <label class="lbl-ipt-tit">医院电话：</label>
	                    <input type="text" class="ipt" name="hospital.phone" value="${hospital.phone}"/>
	                    <span class="errortip">${errors['hospital.phone'][0]}</span>
                   </div>
                   <div class="listItem">
	                    <label class="lbl-ipt-tit">备用电话：</label>
	                    <input type="text" class="ipt" name="hospital.emergencyPhone" value="${hospital.emergencyPhone}"/>
	                    <span class="errortip">${errors['hospital.emergencyPhone'][0]}</span>
                   </div>
                </li>
                <li class="inputList-li">
                  <div class="listItem">
	                    <label class="lbl-ipt-tit">医院传真：</label>
	                    <input type="text" class="ipt" name="hospital.fax" value="${hospital.fax}"/>
	                    <span class="errortip">${errors['hospital.fax'][0]}</span>
                   </div>
                   <div class="listItem">
	                    <label class="lbl-ipt-tit">医院电子邮件：</label>
	                    <input type="text" class="ipt" name="hospital.email" value="${hospital.email}"/>
	                    <span class="errortip">${errors['hospital.email'][0]}</span>
                   </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem">
                    <label class="lbl-ipt-tit">医院邮政编码：</label>
                    <input type="text" class="ipt" name="hospital.postcode" value="${hospital.postcode}"/>
                    <span class="errortip">${errors['hospital.postcode'][0]}</span>
                   </div>
                   <div class="listItem">
	                    <label class="lbl-ipt-tit">医院网站：</label>
	                    <input type="text" class="ipt" name="hospital.siteUrl" value="${hospital.siteUrl}"/>
	                    <span class="errortip">${errors['hospital.siteUrl'][0]}</span>
                   </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem listItem-full-width">
                    <label class="lbl-ipt-tit">职业许可证：</label>
                    <div id="permitImg"></div>
                    <span class="errortip">${errors['hospital.permitImg'][0]}</span>
                   </div>
                </li>
                 <li class="inputList-li">
                   <div class="listItem listItem-full-width">
                    <label class="lbl-ipt-tit">医院LOGO：</label>
                    <div id="logoImg"></div>
                    <span class="errortip">${errors['hospital.logoImg'][0]}</span>
                   </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem listItem-full-width">
                    <label class="lbl-ipt-tit">医院形象图片：</label>
                    <div id="hospImg"></div>
                    <span class="errortip">${errors['hospital.hospImg'][0]}</span>
                   </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem listItem-full-width">
                    <label class="lbl-ipt-tit">医院地图：</label>
                    <div id="mapImg"></div>
                    <span class="errortip">${errors['hospital.mapImg'][0]}</span>
                   </div>
                 <SCRIPT type="text/javascript">
                     upload.btn("permitImg",'${hospital.permitImg}',"hospital.permitImg",true);//职业许可
                     upload.btn("logoImg",'${hospital.logoImg}',"hospital.logoImg",true);//LOGO
                     upload.btn("hospImg",'${hospital.hospImg}',"hospital.hospImg",true);//形象
                     upload.btn("mapImg",'${hospital.mapImg}',"hospital.mapImg",true);//地图
                </SCRIPT>
                </li>
                <li class="inputList-li">
                   <div class="listItem">
                    <label class="lbl-ipt-tit">医院简介：</label>
                    <textarea name="hospital.shortDesc" class="formTextarea valid">${hospital.shortDesc}</textarea>
                    <span class="errortip">${errors['hospital.shortDesc'][0]}</span>
                   </div>
                </li>
                 <li class="inputList-li">
                    <div class="listItem">
                    <label class="lbl-ipt-tit">医院BUS线路：</label>
                   </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem">
                     <textarea id="busLine" name="hospital.busLine" class="formTextediter">${hospital.busLine}</textarea>
                     <div style="display:none">
         				<textarea name="editorPrevImg">${hospital.busLine}</textarea>
       				 </div>
                    <span class="errortip">${errors['hospital.busLine'][0]}</span>
                   </div>
                </li>
                 <li class="inputList-li">
                   <div class="listItem">
                    <label class="lbl-ipt-tit">医院详细介绍：</label>
                    </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem">
                     <textarea id="hospDesc" name="hospital.hospDesc" class="formTextediter">${hospital.hospDesc}</textarea>
                     <script type="text/javascript">
                        var editor1;KindEditor.ready(function(K){
                        	editor1 = K.create('textarea[id="busLine"]');
                        	}); 
                     </script>
                     
                     <script type="text/javascript">
                        var editor2;
                        KindEditor.ready(function(K){
                            editor2 = K.create('textarea[id="hospDesc"]');
                        }); 
                     </script>
                     
                     <div style="display:none">
         				<textarea name="editorPrevImg">${hospital.hospDesc}</textarea>
       				 </div>
                    <span class="errortip">${errors['hospital.hospDesc'][0]}</span>
                   </div>
                </li>
            </ul>
           </div>
        </div>
         <div class="buttonArea">
            <c:if test="${!empty channelId}">
	            <jsp:include page="contentButton.jsp"></jsp:include>
            </c:if>
            <c:if test="${empty channelId}">
            	<input class="formButton" value="确  定" type="button" onclick="submits(this.form,editor1,editor2);">&nbsp;&nbsp;
                <c:if test="${empty sessionScope._hospital}">
	                <c:if test="${!myHosp}">
	                    <c:if test="${ztree}">
	                        <input class="formButton" onclick="window.location='${path}/admin/hospitalTree!list.action?areaCode=${areaCode}'" value="返  回" type="button">
	                    </c:if>
	                    <c:if test="${!ztree}">
	                        <input class="formButton" onclick="window.location='${path}/admin/hospital!list.action'" value="返  回" type="button">
	                    </c:if>
	                </c:if>
	            </c:if>
	            <c:if test="${!empty sessionScope._hospital}">
	               <input class="formButton" onclick="window.location='${path}/admin/hospitalTree!edit.action?edit=true&id=${id}'" value="返  回" type="button">
	            </c:if>
            </c:if>
        </div>
        </form>
      </div>
   </body>
<script type="text/javascript">  
		displayDiv('mainBtn','mainDiv','${errors}');
		displayDiv('extBtn','extDiv','${errors}');
</script>
</html>