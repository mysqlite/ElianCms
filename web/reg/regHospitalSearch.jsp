<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="include/sub_header.jsp"></jsp:include>
<jsp:include page="include/nav.jsp"></jsp:include> 
<link rel="stylesheet" type="text/css" href="http://cms.elian.cc/css/manage/page.css"/>
<script src="http://script.elian.cc/main/base0530.js"></script>
<script>fgm.on(window, "load", function() {new LazyLoad("lazyLoad1");});</script>
<div class="breadcrumbs">
    <div class="bd">
        <a href="#">首页</a> &gt; <a href="#">广东</a> &gt; 
        <a href="#">佛山</a> &gt; <span class="cur">广东省人民医院</span>
    </div>
</div>
<div class="gutter"></div>
<form id="searchForm" action="${path}/reg/regHospitalSearch!search.action" method="post" >
	<input type="hidden" name="pagination.conditionContent" value="${pagination.conditionContent}"/>
	<input type="hidden" name="parentAreaCode" id="parentAreaCode" value="${parentAreaCode}"/>	
	<input type="hidden" name="subAreaCode" id="subAreaCode" value="${subAreaCode}"/>	
	<input type="hidden" name="hosptialRank" id="hosptialRank" value="${hosptialRank}"/>	
	<input type="hidden" name="hosptialType" id="hosptialType" value="${hosptialType}"/>	
	<input type="hidden" name="hospitalName" id="hosptialName" value="${hospitalName}"/>	
	<div class="section">
	    <div class="wrap_key_select">
	        <div class="search_result_tips"> 共搜到<em class="em">${pagination.rowCount}</em>条符合搜索条件的结果 </div>
	        <div class="key_select">
	            <div class="i"> <span class="tit">所在地区：</span>
	                <div class="details" id="jq_zone_select">
	                    <div class="wrap_zone">
	                        <div class="zone_hd">
	                        	<span class="txt" id="parentAreaSpan">
	                        	<c:if test="${empty parentAreaCode || parentAreaCode==0}">省市</c:if>
	                        	<c:if test="${not empty parentAreaCode && parentAreaCode!=0}">
		                        	<c:forEach var="area" items="${parentAreaList}">
		                        		<c:if test="${parentAreaCode==area.areaCode}">
		                        			${area.areaName}
		                        		</c:if>
		                        	</c:forEach>
		                        </c:if>
	                        	</span>
	                        	<b class="ico"></b>
	                        </div>
	                        <div class="zone_bd"> <a href="#" onclick="selectParentArea(0)">不限</a>
	                        	<c:forEach var="area" items="${parentAreaList}">
	                        		<a href="#" onclick="selectParentArea(${area.areaCode})">${area.areaName}</a>
	                        		<c:if test="{parentAreaCode==area.areaCode}">
	                        			<script type="text/javascript">
											document.getElementById("parentAreaSpan").innerHTML="${area.areaName}";
										</script>
	                        		</c:if>
	                        	</c:forEach>
	                        </div>
	                    </div>
	                    <div class="wrap_zone">
	                        <div class="zone_hd">
	                        	<span id="subAreaSpan" class="txt">
	                        	<c:if test="${empty subAreaCode || subAreaCode==0}">地区</c:if>
	                        	<c:if test="${not empty subAreaCode && subAreaCode!=0}">
		                        	<c:forEach var="area" items="${subAreaList}">
		                        		<c:if test="${subAreaCode==area.areaCode}">
		                        			${area.areaName}
		                        		</c:if>
		                        	</c:forEach>
		                        </c:if>
	                        	</span> <b class="ico"></b> 
	                        </div>
	                        <div id="subArea" class="zone_bd"> <a href="#" onclick="selectSubArea(0)">不限</a>
	                        	<c:forEach var="area" items="${subAreaList}">
	                        		<a href="#" onclick="selectSubArea(${area.areaCode})">${area.areaName}</a>
	                        	</c:forEach>
	                        </div>
	                    </div>
	                </div>
	            </div>
	            <div class="i"> <span class="tit">医院等级：</span>
	                <div class="details"> 
	                	<a href="#" <c:if test="${empty hosptialRank || hosptialRank==0}"> class="cur" </c:if>  onclick="selectHospRank(0)">不限</a>
	                	<c:forEach var="rank" items="${hospRankList}">
	                		<a href="#" <c:if test="${rank.id==hosptialRank}"> class="cur" </c:if>onclick="selectHospRank(${rank.id})">${rank.typeName}</a>
	                	</c:forEach>
	                </div>
	            </div>
	            <div class="i"> <span class="tit">医院类型：</span>
	                <div class="details"> 
	                	<a href="#" <c:if test="${empty hosptialType || hosptialType==0}"> class="cur" </c:if> onclick="selectHospType(0)">不限</a>
	                	<c:forEach var="type" items="${hospTypeList}">
	                		<a href="#" <c:if test="${type.id==hosptialType}"> class="cur" </c:if> te onclick="selectHospType(${type.id})">${type.typeName}</a>
	                	</c:forEach>
	                </div>
	            </div>
	            <div class="last">
	                <div class="wrap_search">
	                    <div class="search">
	                        <div class="wrap_ipt">
	                            <input onblur="if (this.value!='请输入关键字'){document.getElementById('hosptialName').value=this.value}" onfocus="if (this.value=='请输入关键字'){this.value='';this.style.color='#333'}" type="text" class="ipt"
	                            	value="${hospitalName}<c:if test='${empty hospitalName}'>请输入关键字</c:if>"/>
	                        </div>
	                        <input type="submit" class="ips" value="搜索"/>
	                    </div>
	                </div>
	            </div>
	        </div>
	    </div>
	</div>
	<div class="section" id="lazyLoad1">
	    <ul class="hos-list">
	    	<c:forEach var="hosp" items="${pagination.list}">
	    		<li>
	            <a class="pic" href="${path}/reg/regDoctor!searchByDept.action?hospId=${hosp.id}">
	            	<img src="http://style.elian.cc/main/bg/none.gif" data-img=
	            		<c:if test="${hosp.hospImg==null || hosp.hospImg==''}">"http://images.elian.cc/design/main/reg/img/hos_default.jpg"</c:if> 
	            		<c:if test="${hosp.hospImg!=null && hosp.hospImg!=''}">
		            		<c:if test="${!fn:startsWith(hosp.hospImg,'http://')}">"${_img_ftp.ftpUrl}${hosp.hospImg}"</c:if> 
		            		<c:if test="${fn:startsWith(hosp.hospImg,'http://')}">"${hosp.hospImg}"</c:if> 
		            	</c:if> 
	            		width="132" height="96"/></a>
	            <div class="tit">
	                <h3>
	                    <a href="${path}/reg/regDoctor!searchByDept.action?hospId=${hosp.id}">${hosp.hospName}[
	                    <c:forEach var="rank" items="${hospRankList}">
	                    	<c:if test="${hosp.rank==rank.id}">${rank.typeName}</c:if>
	                    </c:forEach>
	                    ]</a>
	                </h3>
	                
	                <c:if test="${hosp.reg}">
		                <b class="ico on">已开通挂号&nbsp;&nbsp;未提供空床位</b>                
	                </c:if>
	                <c:if test="${!hosp.reg}">
	                	<b class="ico off">未开通挂号&nbsp;&nbsp;未提供空床位</b>
	                </c:if>
	            </div>            
	            <p class="txt">
	            	<c:if test="${!empty hosp.shortDesc}">
	            		${hosp.shortDesc}
	            	</c:if>
	            	<a href="${path}/reg/regDoctor!searchByDept.action?hospId=${hosp.id}" class="more">更多&gt;&gt;</a>
	            </p>
	        </li>
	    	</c:forEach>
	    </ul>
	    <jsp:include page="../page/common/pager.jsp"></jsp:include>
	</div>
</form>    
<%@ include file="include/footer.jsp"%> 
<script src="http://style.elian.cc/main/reg/script/base.js"></script>
<script type="text/javascript" src="http://style.elian.cc/main/reg/script/area-select.js"></script><!--省市下拉菜单效果-2013-02-03--> 
<script type="text/javascript">
	function selectParentArea(areaId){
		document.getElementById("parentAreaCode").value=areaId;
		document.getElementById("subAreaCode").value="";
		document.getElementById("subAreaSpan").innerHTML="地区";
		document.getElementById("searchForm").submit();
	}
	
	function selectSubArea(areaId){
		document.getElementById("subAreaCode").value=areaId;
		document.getElementById("searchForm").submit();
	}
	
	function selectHospRank(rank){
		document.getElementById("hosptialRank").value=rank;
		document.getElementById("searchForm").submit();
	}
	
	function selectHospType(type){
		document.getElementById("hosptialType").value=type;
		document.getElementById("searchForm").submit();
	}
	
	function toPage(page){
		if (page != null && page != ''){
			document.getElementById("currentPage").value= page;
		    document.getElementById("searchForm").submit();
	    }
	}
</script>