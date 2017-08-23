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
		<title>医联网 医院列表</title>
		<meta name="Keywords" content="医联网 医院列表">
		<meta name="Description" content="医联网 医院列表">
		<link rel="stylesheet" href="${path}/css/front/style/public.css" type="text/css">
		<link rel="stylesheet" href="http://style.elian.cc/main/global.css" type="text/css">
		<link rel="stylesheet" href="${path}/css/front/style/search_result.css" type="text/css">
		<script src="http://script.elian.cc/main/base0530.js"></script>
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
						<li class="cur">
							<a href="${path}/front/searchHospital!list.action">搜医院</a>
						</li>
						<li>
							<a href="${path}/front/searchCompany!list.action">搜企业</a>
						</li>
						<li>
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
				<form id="searchHospitalForm" method="post" action="${path}/front/searchHospital!search.action">
					<input type="hidden" name="siteId" id="siteId" value="${siteId}"/>	
					<input type="hidden" name="parentAreaCode" id="parentAreaCode" value="${parentAreaCode}"/>	
					<input type="hidden" name="subAreaCode" id="subAreaCode" value="${subAreaCode}"/>	
					<input type="hidden" name="hospRank" id="hospRank" value="${hospRank}"/>	
					<input type="hidden" name="hospType" id="hospType" value="${hospType}"/>
					<div class="wrap_key_select">
						<div class="search_result_tips">
							共搜到
							<em class="em">${pagination.rowCount}</em>条符合搜索条件的结果
						</div>
						<div class="key_select">
							<div class="i"> <span class="tit">所在地区：</span>
			                    <div class="details" id="jq_zone_select">     
			                    	<div class="wrap_zone">
				                        <div class="zone_hd">
				                        	<span class="txt" id="parentAreaSpan">
				                        	<c:if test="${fn:length(parentAreaList)==1}">
				                        		${parentAreaList[0].areaName}
				                        	</c:if>
				                        	<c:if test="${fn:length(parentAreaList)>1}">
					                        	<c:if test="${empty parentAreaCode || parentAreaCode==0}">省市</c:if>
					                        	<c:if test="${not empty parentAreaCode && parentAreaCode!=0}">
						                        	<c:forEach var="area" items="${parentAreaList}">
						                        		<c:if test="${parentAreaCode==area.areaCode}">
						                        			${area.areaName}
						                        		</c:if>
						                        	</c:forEach>
						                        </c:if>
				                        	</c:if>
				                        	</span>
				                        	<b class="ico"></b>
				                        </div>
				                        <c:if test="${fn:length(parentAreaList)>1}">
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
					                    </c:if>
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
				                	<a href="javascript:void(0)" <c:if test="${empty hospRank || hospRank==0}"> class="cur" </c:if>  onclick="selectHospRank(0)">不限</a>
				                	<c:forEach var="rank" items="${hospRankList}">
				                		<a href="javascript:void(0)" <c:if test="${rank.id==hospRank}"> class="cur" </c:if> onclick="selectHospRank(${rank.id})">${rank.typeName}</a>
				                	</c:forEach>
				                </div>
				            </div>
				            <div class="i"> <span class="tit">医院类型：</span>
				                <div class="details"> 
				                	<a href="javascript:void(0)" <c:if test="${empty hospType || hospType==0}"> class="cur" </c:if> onclick="selectHospType(0)">不限</a>
				                	<c:forEach var="type" items="${hospTypeList}">
				                		<a href="javascript:void(0)" <c:if test="${type.id==hospType}"> class="cur" </c:if> onclick="selectHospType(${type.id})">${type.typeName}</a>
				                	</c:forEach>
				                </div>
				            </div>
							<div class="last">
								<div class="wrap_search">
									<div class="search">
										<div class="wrap_ipt">
											<input id="keyword" name="keyword" onfocus="if (this.value=='请输入关键字'){this.value='';this.style.color='javascript:void(0)333'}"
												type="text" class="ipt" value="${empty keyword ? '请输入关键字' : keyword}" 
												onkeydown="checkEnter(event, 'searchButton');"/>
										</div>
										<input id="searchButton" onclick="submitSearch('searchHospitalForm')" class="ips" value="搜索" />
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="wrap_bd clearfix">
						<div class="wrap_ui_tit">
							<h3 class="ui_tit">
								搜索医院
							</h3>
						</div>
						<div class="mod_list">
							<ul class="pt03">
								<c:forEach var="item" items="${pagination.list}" varStatus="e">
									<li>
										<a class="pic" target="_blank" href="<c:if test='${empty siteList[e.index].alias}'> ${basePath}${siteList[e.index].id}</c:if><c:if test='${!empty siteList[e.index].alias}'> ${siteList[e.index].alias}</c:if>">
											<img width="100" height="90"
											 src="
											 	<c:if test='${empty item.hospImg}'>http://images.elian.cc/design/main/reg/img/hos_default.jpg</c:if>
											 	<c:if test='${!empty item.hospImg}'>
											 		<c:if test="${fn:startsWith(item.hospImg,'http://')}">
											 			${item.hospImg}
											 		</c:if>
											 		<c:if test="${!fn:startsWith(item.hospImg,'http://')}">
											 			${_img_ftp.ftpUrl}${item.hospImg}
											 		</c:if>
											 	</c:if>
											 " alt="图片位置">
										</a>
										<h4 class="name">
											<a target="_blank" href="<c:if test='${empty siteList[e.index].alias}'> ${basePath}${siteList[e.index].id}</c:if><c:if test='${!empty siteList[e.index].alias}'> ${siteList[e.index].alias}</c:if>">${item.hospName}</a>
										</h4>
										<ul class="txt">
											<li>
												<span class="mess_tit">地址：</span>${item.address}
											</li>
											<li>
												<span class="mess_tit">简介：</span>${item.shortDesc}
											</li>
											<li class="more">
												<a href="<c:if test='${empty siteList[e.index].alias}'> ${basePath}${siteList[e.index].id}</c:if><c:if test='${!empty siteList[e.index].alias}'> ${siteList[e.index].alias}</c:if>">查看医院详细&gt;&gt;</a>
											</li>
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
		<<script type="text/javascript" src="http://script.elian.cc/main/reg/script/area-select.js"></script>
		<script type="text/javascript" src="http://script.elian.cc/main/jquery.header.js"></script>
		<div class="hidden">
			<script type="text/javascript" src="http://script.elian.cc/main/baidu-flow.js"></script>
			<script type="text/javascript" src="http://script.elian.cc/main/google-flow.js"></script>
			<a href="http://www.cnzz.com/stat/website.php?web_id=3321260"
				target="_blank" title="站长统计">站长统计</a>
			<script type="text/javascript">
				var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://"
						: " http://");
				document.write(unescape("%3Cscript src='"
								+ _bdhmProtocol
								+ "hm.baidu.com/h.js%3Ffcab8f29a9e279175c4fd1b3038becb3' type='text/javascript'%3E%3C/script%3E"));
			</script>
			<script type="text/javascript">
				function selectParentArea(areaId){
					document.getElementById("parentAreaCode").value=areaId;
					document.getElementById("subAreaCode").value="";
					document.getElementById("subAreaSpan").innerHTML="地区";
					submitForm();
				}
				
				function selectSubArea(areaId){
					document.getElementById("subAreaCode").value=areaId;
					submitForm();
				}
				
				function selectHospRank(rank){
					document.getElementById("hospRank").value=rank;
					submitForm();
				}
				
				function selectHospType(type){
					document.getElementById("hospType").value=type;
					submitForm();
				}
				
				function submitForm(){
					var element=document.getElementById("keyword");
					if(element.value=="请输入关键字"){
						element.value="";
					}
					document.getElementById("searchHospitalForm").submit();
				}
				function toPage(page){
					if (page != null && page != ''){
						document.getElementById("currentPage").value= page;
					    submitForm();
				    }
				}
			</script>
		</div>
	</body>
</html>