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
	    <h3 class="cur">查看医院信息</h3>
	</div>
<div class=body>  
  	  <jsp:include page="../../page/include/image_upload.jsp"></jsp:include>
        <div class="divInputTable">
            <h3 class="main-tit-bar" id="mainBtn">基本信息</h3>
            <div id="mainDiv">
            <ul class="inputList">
                <li class="inputList-li">
                   <div class="listItem">
                     <label class="lbl-ipt-tit">医院状态：</label>   
                      <c:forEach var="item" items="${hospitalStatusList}">
                   		 <c:if test="${item.key eq hospital.status}">
                   		 	<span class="txt">${item.value}</span>
                   		 </c:if>
	                </c:forEach>                        
                   </div>
                   <div class="listItem">
                 	  <label class="lbl-ipt-tit">是否医保医院：</label>
                 	  <c:if test="${hospital.health}">
                      	<span class="txt">是 </span>                     
                   	  </c:if>
                 	  <c:if test="${!hospital.health}">
                    	 <span class="txt">否 </span>                     
                   	  </c:if>   
                   </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem">
	                    <label class="lbl-ipt-tit">是否挂号：</label>
	                    <c:if test="${hospital.reg}">
	                      <span class="txt">是 </span>                     
	                   	</c:if>
	                 	<c:if test="${!hospital.reg}">
	                    	<span class="txt">否 </span>                     
	                   	</c:if>  
                   </div>
                   <div class="listItem">
                    <label class="lbl-ipt-tit">是否随访：</label>
                     <c:if test="${hospital.followup}">
	                      <span class="txt">是 </span>                     
	                 </c:if>
	                 <c:if test="${!hospital.followup}">
	                    	<span class="txt">否 </span>                     
	                 </c:if>  
                   </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem">
                    <label class="lbl-ipt-tit">医院名称：</label><span class="txt">${hospital.hospName}</span>                    
                   </div>
                   <div class="listItem">
                    <label class="lbl-ipt-tit">医院简称：</label><span class="txt">${hospital.shortName}</span>                    
                   </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem">
                    <label class="lbl-ipt-tit">医院性质：</label>
                    <c:forEach var="nature" items="${natureList}">
                   		 <c:if test="${nature.id eq hospital.nature}">
                   		 	<span class="txt">${nature.typeName}</span>
                   		 </c:if>
	                </c:forEach>                    
                   </div>
                   <div class="listItem">
                    <label class="lbl-ipt-tit">医院类型：</label>
                    <c:forEach var="hospType" items="${hospTypeList}">
                   		 <c:if test="${hospType.id eq hospital.hospType}">
                   		 	<span class="txt">${hospType.typeName}</span>
                   		 </c:if>
	                </c:forEach>   
                   </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem">
                    <label class="lbl-ipt-tit">医院等级：</label>  
                    <c:forEach var="rank" items="${rankList}">
                   		 <c:if test="${rank.id eq hospital.rank}">
                   		 	<span class="txt">${rank.typeName}</span>
                   		 </c:if>
	                </c:forEach>                    
                   </div>
                </li>
                <li class="inputList-li">
                    <div class="listItem">
                        <label class="lbl-ipt-tit">医院地址：</label><span class="txt">${hospital.address}</span>                       
                   </div>
                   <div class="listItem">
                    <label class="lbl-ipt-tit">医院所在区域：</label><span class="txt">${!empty areaName?areaName:'全国'}</span>                    
                   </div>
                </li>
                </ul>
                </div>
                <h3 class="main-tit-bar" id="extBtn">扩展信息</h3>
                <div id="extDiv">
                <ul class="inputList">
                <li class="inputList-li">
                   <div class="listItem">
	                    <label class="lbl-ipt-tit">医院电话：</label><span class="txt">${hospital.phone}</span>  	                    
                   </div>
                    <div class="listItem">
	                    <label class="lbl-ipt-tit">备用电话：</label><span class="txt">${hospital.emergencyPhone}</span> 	                   
                   </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem">
	                    <label class="lbl-ipt-tit">医院传真：</label><span class="txt">${hospital.fax}</span> 	                   
                   </div>
                   <div class="listItem">
	                    <label class="lbl-ipt-tit">医院电子邮件：</label><span class="txt">${hospital.email}</span> 	                    
                   </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem">
                    <label class="lbl-ipt-tit">医院邮政编码：</label><span class="txt">${hospital.postcode}</span>                   
                   </div>
                   <div class="listItem">
	                    <label class="lbl-ipt-tit">医院网站：</label><span class="txt">${hospital.siteUrl}</span>	                    
                   </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem listItem-full-width">
                    <label class="lbl-ipt-tit">职业许可证：</label> 
                    <span class="txt" id="permitImg"></span>                     
                   </div>
                </li>
                 <li class="inputList-li">
                   <div class="listItem listItem-full-width">
                    <label class="lbl-ipt-tit">医院LOGO：</label>  
                    <span class="txt" id="logoImg"></span>                   
                   </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem listItem-full-width">
                    <label class="lbl-ipt-tit">医院形象图片：</label> 
                    <span class="txt" id="hospImg"></span>                    
                   </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem listItem-full-width">
                    <label class="lbl-ipt-tit">医院地图：</label>  
                    <span class="txt" id="mapImg"></span>                                  
                   </div>                
                <SCRIPT type="text/javascript">
                     showImageButton("permitImg",'${hospital.permitImg}','hospital.permitImg',1);//职业许可
                     showImageButton("logoImg",'${hospital.logoImg}','hospital.logoImg',2);//LOGO
                     showImageButton("hospImg",'${hospital.hospImg}','hospital.hospImg',3);//形象
                     showImageButton("mapImg",'${hospital.mapImg}','hospital.mapImg',4);//地图
                </SCRIPT>
                </li>
                <li class="inputList-li">
                   <div class="listItem">
                    <label class="lbl-ipt-tit">医院简介：</label>
                    <p class="lbl-p">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${hospital.shortDesc}</p>                    
                   </div>
                </li>
                 <li class="inputList-li">
                    <div class="listItem">
                    <label class="lbl-ipt-tit">医院BUS线路：</label>
                   </div>
                </li>
                <li class="inputList-li">    
                	<p class="lbl-p">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${hospital.busLine}</p>
                </li>                    
                <li class="inputList-li">
                   <div class="listItem">
                    <label class="lbl-ipt-tit">医院详细介绍：</label>
                    </div>
                </li>
               <li class="inputList-li">               
                <p class="lbl-p">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${hospital.hospDesc}</p> 
               </li>
            </ul>
           </div>
        </div>
        <div class="buttonArea">
	        <input class="formButton" onclick="window.location='${path}/admin/content!list.action?leaf=${leaf}&channelId=${channelId}&action=${action}&status=${status}'" value="返  回" type="button">
		</div>         
      </div>
   </body>
<script type="text/javascript">  
		displayDiv('mainBtn','mainDiv','${errors}');
		displayDiv('extBtn','extDiv','${errors}');
</script>
</html>