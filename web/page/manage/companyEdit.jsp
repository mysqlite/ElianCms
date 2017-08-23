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
	    <script language="javascript" type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
    </head>
	<body class="input">
	<div class="main-top-hd clearfix">
	    <h3 class="cur">
		    <c:if test="${edit}">企业编辑</c:if>
		    <c:if test="${!edit}">企业添加</c:if>
	    </h3>
	</div>
<div class=body>
    <jsp:include page="../../page/include/f_upload.jsp"></jsp:include>
	<form action="${path}/admin/company!saveCompany.action" method="post">
        <input type="hidden" name="token" value="${token}"/>
        <input type="hidden" name="id" value="${id}"/>
        <input type="hidden" name="company.version" value="${company.version}"/>
        <input type="hidden" name="company.id" value="${company.id}"/>
        <input type="hidden" name="company.createTime" value="${company.createTime}"/>
        <input type="hidden" name="company.mallTime" value="${company.mallTime}"/>
        <input type="hidden" name="company.type" value="${company.type}"/>
        <input type="hidden" name="company.remarks" value="${company.remarks}"/>
        <input type="hidden" name="company.hits" value="${!empty company.hits?company.hits:0}"/>
        <input type="hidden" name="company.auditTime" value="${company.auditTime}"/>
        <input type="hidden" name="company.auditor" value="${company.auditor}"/>
        <input type="hidden" name="company.status" value="${company.status}"/>
        <input type="hidden" name="edit" value="${edit}"/>
        <input type="hidden" name="areaId" value="${areaId}"/>
        <div class="divInputTable">
            <h3 class="main-tit-bar" id="mainBtn">基本信息 </h3>
            <div id="mainDiv">
            <ul class="inputList">
                <li class="inputList-li">
                   <div class="listItem">
                     <label class="lbl-ipt-tit"><span class="star">*</span>企业状态：</label>
                        <c:forEach items="${statusList}" var="item" varStatus="e">
                           <c:if test="${item.key eq company.status}">
                              <span class="txt">${item.value}</span>
                          </c:if>
                       </c:forEach>
                   </div>
                   <div class="listItem">
                     <label class="lbl-ipt-tit"><span class="star">*</span>开通商城：</label>
                     <label class="lbl-ipt-tit radioWrap">
                         <input type="radio" name="company.mall" value="true"<c:if test="${company.mall}"> checked</c:if> />是
                     </label>
                     <label class="lbl-ipt-tit radioWrap">
                         <input type="radio" name="company.mall" value="false"<c:if test="${!company.mall}">checked</c:if> />否
                     </label>
                   </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>企业名称：</label>
                    <input type="text" class="ipt" name="company.name" value="${company.name}"/>
                    <span class="errortip">${errors['company.name'][0]}</span>
                   </div>
                   <div class="listItem">
                    <label class="lbl-ipt-tit">企业简称：</label>
                    <input type="text" class="ipt" name="company.shortName" value="${company.shortName}"/>
                    <span class="errortip">${errors['company.shortName'][0]}</span>
                   </div>
                </li>
                <li class="inputList-li">
                    <div class="listItem">
                        <label class="lbl-ipt-tit"><span class="star">*</span>企业地址：</label>
                        <input type="text" class="ipt" name="company.address" value="${company.address}"/>
                        <span class="errortip">${errors['company.address'][0]}</span>
                   </div>
                   <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>企业所在区域：</label>
                    <input id="areaId" class="ipt" name="areaName" type="text" readonly="true" value="${!empty company.area.areaName?company.area.areaName:'中国'}" 
                                    onfocus="showMenuArea('treeContent','areaId','areaValueId','${path}');"
                                    onkeydown="BackSpace('中国','areaId','areaValueId');" />
                    <input id="areaValueId" type="hidden" name="company.area.areaCode" value="${!empty company.area.areaCode?company.area.areaCode:0}"/>
                    <div id="treeDiv" class="menuContent"><ul id="treeContent" class="ztree" style="width:260px;height: 300px;"></ul></div>
                    <span class="errortip">${errors['company.area.areaCode'][0]}</span>
                   </div>
                </li>
                </ul>
                </div>
                <h3 class="main-tit-bar" id="extBtn">扩展信息</h3>
                <div id="extDiv">
                <ul class="inputList">
                <li class="inputList-li">
                   <div class="listItem">
	                    <label class="lbl-ipt-tit">法人代表：</label>
	                    <input type="text" class="ipt" name="company.legalRepresentative" value="${company.legalRepresentative}"/>
	                    <span class="errortip">${errors['company.legalRepresentative'][0]}</span>
                   </div>
                   <div class="listItem">
	                    <label class="lbl-ipt-tit">注册资金：</label>
	                    <input type="text" class="ipt" name="company.registeredCapital" value="${company.registeredCapital}"/>
	                    <span class="errortip">${errors['company.registeredCapital'][0]}</span>
                   </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem">
	                    <label class="lbl-ipt-tit">企业QQ：</label>
	                    <input type="text" class="ipt" name="company.qq" value="${company.qq}"/>
	                    <span class="errortip">${errors['company.qq'][0]}</span>
                   </div>
                   <div class="listItem">
	                    <label class="lbl-ipt-tit">QQ联系人：</label>
	                    <input type="text" class="ipt" name="company.contactName" value="${company.contactName}"/>
	                    <span class="errortip">${errors['company.contactName'][0]}</span>
                   </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem">
	                    <label class="lbl-ipt-tit">经营模式：</label>
	                    <input type="text" class="ipt" name="company.empiricaMode" value="${company.empiricaMode}"/>
	                    <span class="errortip">${errors['company.empiricaMode'][0]}</span>
                   </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem">
	                    <label class="lbl-ipt-tit">企业电话：</label>
	                    <input type="text" class="ipt" name="company.phone" value="${company.phone}"/>
	                    <span class="errortip">${errors['company.phone'][0]}</span>
                   </div>
                   <div class="listItem">
	                    <label class="lbl-ipt-tit">成立时间：</label>
	                    <input type="text" class="ipt Wdate" name="company.foundedTime" value="${company.foundedTime}" onClick="WdatePicker()"/>
	                    <span class="errortip">${errors['company.foundedTime'][0]}</span>
                   </div>
                </li>
                <li class="inputList-li">
                  <div class="listItem">
	                    <label class="lbl-ipt-tit">企业传真：</label>
	                    <input type="text" class="ipt" name="company.fax" value="${company.fax}"/>
	                    <span class="errortip">${errors['company.fax'][0]}</span>
                   </div>
                   <div class="listItem">
	                    <label class="lbl-ipt-tit">企业电子邮件：</label>
	                    <input type="text" class="ipt" name="company.email" value="${company.email}"/>
	                    <span class="errortip">${errors['company.email'][0]}</span>
                   </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem">
                    <label class="lbl-ipt-tit">企业邮政编码：</label>
                    <input type="text" class="ipt" name="company.postcode" value="${company.postcode}"/>
                    <span class="errortip">${errors['company.postcode'][0]}</span>
                   </div>
                   <div class="listItem">
	                    <label class="lbl-ipt-tit">企业网站：</label>
	                    <input type="text" class="ipt" name="company.siteUrl" value="${company.siteUrl}"/>
	                    <span class="errortip">${errors['company.siteUrl'][0]}</span>
                   </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem listItem-full-width">
                    <label class="lbl-ipt-tit">职业许可证：</label>
                    <div id="permitImg"></div>
                    <span class="errortip">${errors['company.permitImg'][0]}</span>
                   </div>
                </li>
                 <li class="inputList-li">
                   <div class="listItem listItem-full-width">
                    <label class="lbl-ipt-tit">企业LOGO：</label>
                    <div id="logoImg"></div>
                    <span class="errortip">${errors['company.logoImg'][0]}</span>
                   </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem listItem-full-width">
                    <label class="lbl-ipt-tit">企业形象图片：</label>
                    <div id="companyImg"></div>
                    <span class="errortip">${errors['company.companyImg'][0]}</span>
                   </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem listItem-full-width">
                    <label class="lbl-ipt-tit">企业地图：</label>
                    <div id="mapImg"></div>
                    <span class="errortip">${errors['menu.mapImg'][0]}</span>
                   </div>
                 <SCRIPT type="text/javascript">
                     upload.btn("permitImg",'${company.permitImg}',"company.permitImg",true);//职业许可
                     upload.btn("logoImg",'${company.logoImg}',"company.logoImg",true);//LOGO
                     upload.btn("companyImg",'${company.companyImg}',"company.companyImg",true);//形象
                     upload.btn("mapImg",'${company.mapImg}',"company.mapImg",true);//地图
                </SCRIPT>
                </li>
                <li class="inputList-li">
                   <div class="listItem">
                    <label class="lbl-ipt-tit">企业简介：</label>
                    <textarea name="company.shortIntroduce" class="formTextarea valid">${company.shortIntroduce}</textarea>
                    <span class="errortip">${errors['company.shortIntroduce'][0]}</span>
                   </div>
                </li>
                 <li class="inputList-li">
                    <div class="listItem">
                    <label class="lbl-ipt-tit">企业BUS线路：</label>
                   </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem">
                     <textarea id="busLine" name="company.busLine" class="formTextediter">${company.busLine}</textarea>
                     <div style="display:none">
         				<textarea name="editorPrevImg">${company.busLine}</textarea>
       				 </div>
                    <span class="errortip">${errors['company.busLine'][0]}</span>
                   </div>
                </li>
                 <li class="inputList-li">
                   <div class="listItem">
                    <label class="lbl-ipt-tit">企业详细介绍：</label>
                    </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem">
                     <textarea id="hospDesc" name="company.introduce" class="formTextediter">${company.introduce}</textarea>
                     <script type="text/javascript">
                        var editor1,editor2;KindEditor.ready(function(K) {
                        	editor1 = K.create('textarea[id="busLine"]');
                        	editor2 = K.create('textarea[id="hospDesc"]');
                        	}); 
                     </script>
                     <div style="display:none">
         				<textarea name="editorPrevImg">${company.introduce}</textarea>
       				 </div>
                    <span class="errortip">${errors['company.introduce'][0]}</span>
                   </div>
                </li>
            </ul>
           </div>
        </div>
         <div class="buttonArea">
           	<input class="formButton" value="确  定" type="button" onclick="submits(this.form,editor1,editor2);">&nbsp;&nbsp;
            <input class="formButton" onclick="window.location='${path}/admin/company!edit.action?edit=true&id=${id}'" value="返  回" type="button">
        </div>
        </form>
      </div>
   </body>
<script type="text/javascript">  
		displayDiv('mainBtn','mainDiv','${errors}');
		displayDiv('extBtn','extDiv','${errors}');
</script>
</html>