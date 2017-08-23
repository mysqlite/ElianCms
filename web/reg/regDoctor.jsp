<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="include/sub_header.jsp"></jsp:include>
<jsp:include page="include/nav.jsp"></jsp:include>
<script src="http://script.elian.cc/main/base0530.js"></script>
<script>fgm.on(window, "load", function() {new LazyLoad("lazyLoad1");});</script>
<script src="${path}/js/proscenium/duty_tbl.js"></script>
<div class="breadcrumbs">
    <div class="bd">
        <a href="#">首页</a> &gt; <a href="#">广东</a> &gt; 
        <a href="#">佛山</a> &gt; <span class="cur">广东省人民医院</span>
    </div>
</div>
<div class="gutter"></div>
 <c:if test="${hospital.reg}">
<div class="section">
    <div class="hos-list-more">
        <div class="mod_hd01">
            <h3 class="ui_hd"><b class="ui_bg"></b>网上预约挂号</h3>
        </div>
        <div class="mod_bd01">
            <div class="hos-details">
                <a class="pic" href="#"><img src=
                	<c:if test="${hospital.hospImg==null || hospital.hospImg==''}">"http://images.elian.cc/design/main/reg/img/hos_default.jpg"</c:if> 
                	<c:if test="${hospital.hospImg!=null && hospital.hospImg!=''}">
	                	<c:if test="${!fn:startsWith(hospital.hospImg,'http://')}">"${_img_ftp.ftpUrl}${hospital.hospImg}"</c:if> 
		            	<c:if test="${fn:startsWith(hospital.hospImg,'http://')}">"${hospital.hospImg}"</c:if> 
                	</c:if> 
                width="132" height="96"/>
                </a>
                <ul class="details">
                    <li>名称：${hospital.hospName}</li><li>地址：${hospital.address }</li>
                    <li>性质：${hospType.typeName}</li><!-- <li>特色专科：妇科医院</li> -->
                    <li>等级：${hospRank.typeName}</li><li>联系电话：${hospital.phone }</li>
                </ul>
            </div>
        </div>
    </div>
    <form id="searchForm" action="regDoctor!searchByDept.action" method="post">
    	<input id="hospId" type="hidden" name="hospId" value="${hospId}"/>
    	<input id="deptId" type="hidden" name="deptId" value="${deptId}"/>
    </form>
	<div class="wrap_key_select">
        <div class="key_select">
            <div class="i"> <span class="tit">选择就诊科室：</span>
                <div class="details"><a href="#" <c:if test="${empty deptId || deptId==0}">class="cur"</c:if> onclick="selectDept(0)">不限</a>
                	<c:forEach var="item" items="${deptList}">
                		<a href="#" onclick="selectDept(${item.id})"
                			<c:if test="${item.id==deptId}">class="cur"</c:if>>
                			${item.deptName }</a>
                	</c:forEach>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="section" id="lazyLoad1">
    <div class="">
        <div class="mod_hd04">
            <h3 class="tit search_result_tips">该科室共有<em class="em">${fn:length(docList)}</em>名医生 </h3>
        </div>
        <div class="mod_bd04">
            <ul class="pt03 doc-list" id="doc-list">
            	<c:forEach var="doc" items="${docList}">
	                <li><a class="pic" href="${path }/reg/regDoctor!detial.action?docId=${doc.id}">
	                	<img src="http://style.elian.cc/main/bg/none.gif" data-img=
			                <c:if test="${!fn:startsWith(doc.doctImg,'http://')}">"
			                     <c:if test="${empty doc.doctImg or doc.doctImg==''}">http://style.elian.cc/main/bg/none.gif</c:if>
			                     <c:if test="${!empty doc.doctImg and doc.doctImg!=''}">${_img_ftp.ftpUrl}${doc.doctImg}</c:if>"
			                </c:if> 
			            	<c:if test="${fn:startsWith(doc.doctImg,'http://')}">"${doc.doctImg}"</c:if> alt="${doc.doctName}"/></a>
	                    <div class="tit">
	                        <span class="name">${doc.doctName}</span>
	                        <span class="txt">${doc.jobTitle}</span>
	                        <span class="name">${hospital.hospName}</span>
	                        <span>[${hospRank.typeName}]</span>
	                        <span class="point">${doc.avgScore==0?0:doc.avgScore}分</span>
	                    </div>
	                    <p class="txt">	 
		                    <c:if test="${fn:length(doc.introductionNoHTML)>150}">
			                  ${fn:substring(doc.introductionNoHTML,0,150)};
			                </c:if>
			                <c:if test="${fn:length(doc.introductionNoHTML)<=150}">
			                  ${doc.introductionNoHTML};
			                </c:if>
	                        <a href="${path }/reg/regDoctor!detial.action?docId=${doc.id}" class="more">更多&gt;&gt;</a>
	                    </p>
	                    <span class="show-duty-btn">查看排班表<b class="ico"></b></span>
	                    <iframe data-id="${doc.id}" src="" frameborder="0" scrolling="no" width="840" height="245" class="iframe_duty_tbl"></iframe>
	                </li>
            	</c:forEach>
            </ul>
        </div>
    </div>
</div>
 </c:if>
  <c:if test="${!hospital.reg}">
    <div align="center"><h1>该医院尚未在本站开通挂号,如需开通，请联系QQ:876292931</h1></div>
  </c:if>
<%@ include file="include/footer.jsp"%> 
<script src="http://style.elian.cc/main/reg/script/base.js"></script>
<script type="text/javascript">
	function selectDept(deptId){
		if(deptId==0)deptId="";
		document.getElementById("deptId").value=deptId;
		document.getElementById("searchForm").submit();
	}
</script>