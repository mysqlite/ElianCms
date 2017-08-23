<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=9">
		<title>医联网 医生列表</title>
		<meta name="Keywords" content="医联网 医生列表">
		<meta name="Description" content="医联网 医生列表">
		<link rel="stylesheet" href="${path}/css/front/style/public.css" type="text/css">
		<link rel="stylesheet" href="http://style.elian.cc/main/global.css" type="text/css">
		<link rel="stylesheet" href="${path}/css/front/style/search_result.css" type="text/css">
		<link rel="shortcut icon" type="image/x-icon" href="http://www.elian.cc/favicon.ico">
		<script type="text/javascript">
			function checkEnter(event, clickId) {
	　　    		if(event.keyCode == 13){
	　　    			document.getElementById(clickId).click();
	　　    		}
	　　    	}
			
			function changeType(cur, type){
				var childs = document.getElementById("typeDiv").childNodes;
				for(var i = 0; i < childs.length; i++){
					childs[i].className="";
				}
				
				document.getElementById("type").value = type;
				cur.className = "cur";
			}
			
			function submitSearchForm(form){
				document.getElementById("currentPage").value=1;
				submitSearch(form);
			}
			
			function submitSearch(form){
				var value = document.getElementById("keyword").value;
				if(value=='请输入关键字'){
					document.getElementById("keyword").value = "";
				}
				document.getElementById(form).submit();
			}
		</script>
	</head>
	<body>
		<jsp:include page="searchHead.jsp"></jsp:include>
		
		<div class="section">
			<div class="section_main">
				<div class="search_tab_nav">
					<ul class="list">
						<li>
							<a href="${path}/front/searchInformation!list.action">搜资讯</a>
						</li>
						<li>
							<a href="${path}/front/searchHospital!list.action">搜医院</a>
						</li>
						<li>
							<a href="${path}/front/searchCompany!list.action">搜企业</a>
						</li>
						<li  class="cur">
							<a href="${path}/front/searchDoctor!list.action">搜医生</a>
						</li>
						<li>
							<a href="${path}/front/searchDrugCommon!list.action">搜药品</a>
						</li>
						<li>
							<a href="${path}/front/searchMedicine!search.action">搜药品</a>
						</li>
						<li>
							<a href="${path}/front/searchInstrument!search.action">搜器械</a>
						</li>
					</ul>
				</div>
				<form id="searchDoctorForm" method="post" action="${path}/front/searchDoctor!search.action">
					<div class="wrap_key_select">
						<div class="search_result_tips">
							共搜到
							<em class="em">${pagination.rowCount}</em>条符合搜索条件的结果
						</div>
						<div class="key_select">
							<div class="i"> <span class="tit">搜索选项：</span>
		                        <div id="typeDiv" class="details">
		                        	<input id="type" type="hidden" name="type" value="${type}">
		                        	<input type="hidden" name="siteId" value="${siteId}">
		                        	<a href="javascript:void(0);" class="${empty type ? 'cur' : ''}" onclick="changeType(this, '')">不限</a>
		                        	<a href="javascript:void(0);" class="${type == 'doctName' ? 'cur' : ''}" onclick="changeType(this, 'doctName')">名称</a>
		                        	<a href="javascript:void(0);" class="${type == 'speciality' ? 'cur' : ''}" onclick="changeType(this, 'speciality')">专长</a>
		                        </div>
		                    </div>
							<div class="last">
								<div class="">
									<div class="search">
										<div class="wrap_ipt">
											<input id="keyword" name="keyword" onfocus="if (this.value=='请输入关键字'){this.value='';this.style.color='#333'}"
												type="text" class="ipt" value="${empty keyword ? '请输入关键字' : keyword}" 
												onkeydown="checkEnter(event, 'searchButton');"/>
										</div>
										<input id="searchButton" onclick="submitSearchForm('searchDoctorForm')" class="ips" value="搜索" />
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="wrap_bd clearfix">
						<div class="wrap_ui_tit">
							<h3 class="ui_tit">
								搜索医生
							</h3>
						</div>
						<div class="mod_list">
							<ul class="pt03">
								<c:forEach var="item" items="${pagination.list}" varStatus="e">
									<li> 
				                        <a class="pic" target="_blank" href="${pathList[e.index]}"  target="blank">
				                        	<img width="100" height="90" src="
				                        	<c:if test="${empty item.doctImg}">
				                        		http://style.elian.cc/main/bg/none.gif
				                        	</c:if>
				                        	<c:if test="${!empty item.doctImg}">
					                        	<c:if test="${!fn:startsWith(item.doctImg,'http://')}">${_img_ftp.ftpUrl}${item.doctImg}</c:if> 
		            							<c:if test="${fn:startsWith(item.doctImg,'http://')}">${item.doctImg}</c:if>
				                        	</c:if>
				                        	" alt="图片位置">
				                        </a>
				                        <h4 class="name">
				                        	<a target="_blank" href="${pathList[e.index]}"  target="blank">${item.doctName}</a>
				                        </h4>
				                        <ul class="txt">
				                          <li><span class="mess_tit">医院：</span><a href="${basePath}${siteIdList[e.index]}" class="tit_link"  target="blank">${item.dept.hospital.hospName}</a></li>
				                          <li><span class="mess_tit">科室：</span>${item.dept.deptName}
				                          <c:if test="${null != item.dutyName}">
				                          	&nbsp;<span class="mess_tit">职称：</span>${item.dutyName}</li>
				                          </c:if>
				                          <li>
				                              <span class="mess_tit">擅长疾病：</span>${item.speciality}
				                          </li>
				                          <li class="more">
				                             <c:if test="${true== item.reg}">
					                         	<a href="http://www.191580.com/reg/regDoctor!detial.action?docId=${item.id}" target="blank">我要挂号</a>|
					                         	<a href="http://www.191580.com/reg/regDoctor!detial.action?docId=${item.id}#duty_tbl" target="blank">医生排班表</a>|
				                          	 </c:if>
				                          <a href="${pathList[e.index]}" target="blank">查看医生详细&gt;&gt;</a></li>
				                        </ul>
				                	</li>
								</c:forEach>
							</ul>
						</div>
						<h4 align="center" style="display:${empty pagination.list ? 'block' : 'none' }">很遗憾，没有找到您想要的，换个关键词再试试？</h4>
						<jsp:include page="../../page/common/pager.jsp"></jsp:include>
					</div>
				</form>
			</div>
			<jsp:include page="searchSide.jsp"></jsp:include>
		</div>
		<c:import url="http://www.elian.cc/include/foot.shtml" charEncoding="utf-8"/>
		<script type="text/javascript" src="${path}/script/jquery.header.js"></script>
		<script language="javascript" type="text/javascript" src="${resScript}/base0530.js"></script>
		<div class="hidden">
			<script type="text/javascript" src="${path}/script/baidu-flow.js"></script>
			<script type="text/javascript" src="${path}/script/google-flow.js"></script>
			<a href="http://www.cnzz.com/stat/website.php?web_id=3321260"
				target="_blank" title="站长统计">站长统计</a>
			<script type="text/javascript">
				var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://"
						: " http://");
				document.write(unescape("%3Cscript src='"
								+ _bdhmProtocol
								+ "hm.baidu.com/h.js%3Ffcab8f29a9e279175c4fd1b3038becb3' type='text/javascript'%3E%3C/script%3E"));
			
				function toPage(page){
					if (page != null && page != ''){
						document.getElementById("currentPage").value= page;
					    submitSearch('searchDoctorForm');
				    }
				}
			</script>
		</div>
	</body>
</html>