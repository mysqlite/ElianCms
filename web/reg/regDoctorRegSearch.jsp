<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="include/sub_header.jsp"></jsp:include>
<jsp:include page="include/nav.jsp"></jsp:include> 
<link rel="stylesheet" type="text/css" href="http://style.elian.cc/main/pager.css"/>
<script src="http://script.elian.cc/main/base0530.js"></script>
<script src="${path}/plugin/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="${path}/js/proscenium/duty_tbl.js"></script>
<script>fgm.on(window, "load", function() {new LazyLoad("lazyLoad1");});</script>
<div class="breadcrumbs">
    <div class="bd">
        <a href="#">首页</a> &gt; <a href="#">广东</a> &gt; 
        <a href="#">佛山</a> &gt; <span class="cur">广东省人民医院</span>
    </div>
</div>
<div class="gutter"></div>
<form id="searchForm" action="${path}/reg/regDoctor!regSearch.action" method="post" onsubmit="">
<input type="hidden" name="searchHospId" id="searchHospId_hidden" value="${searchHospId}"/>
<input type="hidden" name="searchDeptId" id="searchDeptId_hidden" value="${searchDeptId}"/>
<input type="hidden" name="doctorName" id="doctorName_hidden" value="${doctorName}"/>
<input type="hidden" name="day" id="day_hidden" value="${day}"/>
<input type="hidden" name="regType" id="regType_hidden" value="${regType}"/>
<input type="hidden" name="doctorSpeciality" id="doctorSpeciality_hidden" value="${doctorSpeciality}"/>
<div class="section">
    <div class="wrap_key_select">
        <div class="search_result_tips"> 共搜到<em class="em">${pagination.rowCount}</em>条符合搜索条件的结果 </div>
        <div class="key_select">
        	 <div class="i"><span class="tit">挂号日期：</span>
	            <div class="details">
	            	<a href="#" <c:if test="${day==0}"> class="cur"</c:if> onclick="selectRegDay(0)">未来7天</a>
              		<c:forEach var="day1" items="${dayList}" varStatus="e">
              	 		<a href="#" <c:if test="${day==day1.key}"> class="cur"</c:if> onclick="selectRegDay(${day1.key})">${day1.value}</a>
               		</c:forEach>
           	    </div>
	        </div>
	        <div class="i"><span class="tit">挂号号源：</span>
	        	<div class="details">
	        		<a href="#" <c:if test="${empty regType}"> class="cur" </c:if> onclick="selectRegType()">不限</a>
		        	<c:forEach var="t" items="${regTypeList}" varStatus="e">
		        		<a href="#" <c:if test="${regType==t.key}"> class="cur" </c:if> onclick="selectRegType(${t.key})">${t.value}</a>
	                </c:forEach>
	            </div>
	        </div>
	        <div class="i"><span class="tit">挂号医院：</span>
                <div class="details" id="jq_zone_select">
                    <div class="wrap_zone">
                        <div class="zone_hd"> 
                        	<span class="txt">
                        		<c:if test="${empty searchHospId || searchHospId==0}">挂号医院</c:if>
                        		<c:if test="${!empty searchHospId && searchHospId!=0}">
                        			<c:forEach var="h" items="${hospList}">
		                				<c:if test="${h.id==searchHospId}">${h.hospName}</c:if>
		               				</c:forEach>
                        		</c:if>
                        	</span> 
                        	<b class="ico"></b> 
                        </div>
                        <div class="zone_bd"> 
                        	<a href="#" onclick="selectRegHosp()">不限</a>
                        	<c:forEach var="h" items="${hospList}" varStatus="e">
                				<a href="#" onclick="selectRegHosp(${h.id})">${h.hospName}</a>
               				</c:forEach>
                       	</div>
                    </div>
                    <div class="wrap_zone">
                        <div class="zone_hd"> 
                        	<span class="txt">
                        		<c:if test="${empty searchDeptId || searchDeptId==0}">科室</c:if>
                        		<c:if test="${!empty searchDeptId && searchDeptId!=0}">
                        			<c:forEach var="d" items="${regDeptList}">
	                        			<c:if test="${d.id==searchDeptId}">
		                        			${d.deptName}
	                        			</c:if>
	                        		</c:forEach>
                        		</c:if>
                        	</span>
                        	<b class="ico"></b> 
                        </div>
                        <div class="zone_bd">
                        	<a href="#" onclick="selectDeptHosp()">不限</a>
                        	<c:if test="${!empty searchHospId && searchHospId!=0}">
                        		<c:forEach var="d" items="${regDeptList}">
                        			<a href="#" onclick="selectDeptHosp(${d.id})">${d.deptName}</a>
                        		</c:forEach>
                        	</c:if>
                       	</div>
                    </div>
                </div>
	        </div>
            <div class="i"> <span class="tit">搜索选项：</span>
                <div class="details"> 
                	<a href="#" id="doctorName_a" onclick="selectItem(this,'doctorName','doctorSpeciality')" <c:if test="${empty doctorSpeciality || doctorSpeciality==''}"> class="cur"</c:if>>医生名称</a>
                	<a href="#" id="doctorSpeciality_a" onclick="selectItem(this,'doctorSpeciality','doctorName')" <c:if test="${!empty doctorSpeciality && doctorSpeciality!=''}"> class="cur"</c:if>>个人专长</a>
               	</div>
            </div>
            <div class="last">
                <div class="wrap_search">
                    <div class="search">
                        <div class="wrap_ipt">
                            <input id="doctorName_input" 
                            	onblur="if (this.value!='请输入关键字'){document.getElementById('doctorName_hidden').value=this.value}" 
                            	onfocus="if (this.value=='请输入关键字'){this.value='';this.style.color='#333'}" 
                            	<c:if test="${empty doctorSpeciality || doctorSpeciality==''}"> type="text" </c:if> 
                            	<c:if test="${!empty doctorSpeciality && doctorSpeciality!=''}"> type="hidden" </c:if> 
                            	class="ipt" value="<c:if test="${empty doctorName || doctorName==''}">请输入关键字</c:if>${doctorName}"/>
                            <input id="doctorSpeciality_input"
                            	onblur="if (this.value!='请输入关键字'){document.getElementById('doctorSpeciality_hidden').value=this.value}" 
                            	onfocus="if (this.value=='请输入关键字'){this.value='';this.style.color='#333'}"
                            	<c:if test="${!empty doctorSpeciality && doctorSpeciality!=''}"> type="text" </c:if> 
                            	<c:if test="${empty doctorSpeciality || doctorSpeciality==''}"> type="hidden" </c:if>  
                            	class="ipt" value="<c:if test="${empty doctorSpeciality || doctorSpeciality==''}">请输入关键字</c:if>${doctorSpeciality}"/>
                        </div>
                        <input onclick="submitSearchForm()" type="button" class="ips" value="搜索"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="section" id="lazyLoad1">
	<div class="mod_bd04">
         <ul class="pt03 doc-list" id="doc-list">
         	<c:forEach var="doc" items="${pagination.list}">
              <li><a class="pic" href="${path }/reg/regDoctor!detial.action?docId=${doc.id}">
              	<img src="http://style.elian.cc/main/bg/none.gif" data-img=
	                <c:if test="${!fn:startsWith(doc.doctImg,'http://')}">"${_img_ftp.ftpUrl}${doc.doctImg}"</c:if> 
	            	<c:if test="${fn:startsWith(doc.doctImg,'http://')}">"${doc.doctImg}"</c:if>
				 alt="${doc.doctName}"/></a>
                  <div class="tit">
                      <span class="name">${doc.doctName}</span>
                      <span class="txt">${doc.jobTitle}</span>
                      <span class="name">${doc.dept.hospital.hospName}</span>
                      <span class="point">综合评分：${doc.avgScore==0?0:doc.avgScore}分</span>
                  </div>
                  <p class="txt">
                      <c:if test="${fn:length(doc.introductionNoHTML)>150}">
	                  	${fn:substring(doc.introductionNoHTML,0,150)};
	                  </c:if>
	                  <c:if test="${fn:length(doc.introductionNoHTML)<=150}">
	                  	${doc.introductionNoHTML};
	                  </c:if>
                      <a href="${path}/reg/regDoctor!detial.action?docId=${doc.id}" class="more">更多&gt;&gt;</a>
                  </p>
                  <span class="show-duty-btn"><em>查看排班表</em><b class="ico"></b></span>
                  <iframe data-id="${doc.id}" src="" frameborder="0" scrolling="no" width="840" height="245" class="iframe_duty_tbl"></iframe>
              </li>
         	</c:forEach>
         </ul>
         <jsp:include page="../page/common/pager.jsp"></jsp:include>
     </div>
</div>
</form>
<%@ include file="include/footer.jsp"%> 
<script src="http://style.elian.cc/main/reg/script/base.js"></script>
<script type="text/javascript" src="http://style.elian.cc/main/reg/script/area-select.js"></script><!--省市下拉菜单效果-2013-02-03--> 
<script type="text/javascript">
	function selectItem(obj,selfFliedName,otherFiledName){
		obj.setAttribute('class','cur');
		document.getElementById(otherFiledName+'_a').setAttribute('class','');
		
		document.getElementById(selfFliedName+'_input').setAttribute('type','text');
		document.getElementById(otherFiledName+'_input').setAttribute('type','hidden');
		
		document.getElementById(selfFliedName+'_input').value='';
		document.getElementById(otherFiledName+'_input').value='';
		
		document.getElementById(selfFliedName+'_hidden').value='';
		document.getElementById(otherFiledName+'_hidden').value='';
	}	
	
	function selectRegDay(day){
		document.getElementById('day_hidden').value=day;
		submitSearchForm();
	}
	
	function selectRegType(regType){
		if(regType==undefined) regType='';
		document.getElementById("regType_hidden").value=regType;
		submitSearchForm();
	}

	function selectRegHosp(hospId){
		if(hospId==undefined) hospId='';
		document.getElementById("searchHospId_hidden").value=hospId;
		document.getElementById("searchDeptId_hidden").value='';
		submitSearchForm();
	}
	
	function selectDeptHosp(deptId){
		if(deptId==undefined) deptId='';
		document.getElementById("searchDeptId_hidden").value=deptId;
		submitSearchForm();
	}
	
	function submitSearchForm(){
		document.getElementById("currentPage").value= 1;
		document.getElementById("searchForm").submit();
	}
	
	function toPage(page){
		if (page != null && page != ''){
			document.getElementById("currentPage").value= page;
		    document.getElementById("searchForm").submit();
	    }
	}
</script>