<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=9">
		<title>医联网 资讯列表</title> 
		<meta name="Keywords" content="医联网 资讯列表">
		<meta name="Description" content="医联网 资讯列表">
		<link rel="stylesheet" href="${path}/css/front/style/public.css" type="text/css">
		<link rel="stylesheet" href="http://style.elian.cc/main/global.css" type="text/css">
		<link rel="stylesheet" href="${path}/css/front/style/job_search.css" type="text/css" media="screen">
		<link rel="shortcut icon" type="image/x-icon" href="http://www.elian.cc/favicon.ico">
		<script language="javascript" type="text/javascript" src="${path}/js/manage/editPage.js"></script>
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
			
			function changeIntDate(cur, value){
				var childs = document.getElementById("intDateDiv").childNodes;
				for(var i = 0; i < childs.length; i++){
					childs[i].className="";
				}
				
				document.getElementById("intDate").value=value;
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
			<div class="section_hd">
				<h3 class="tit">
					搜索职位信息
				</h3>
			</div>
			<form id="searchJobForm" method="post" action="${path}/front/searchJob!list.action">
				<div class="w970">
					<div class="ui_search_hd"></div>
					<div class="search_bar_bd">
						<div class="item">
							<label class="lbl">
								工作地点：
							</label>
							<select
								style="width: 155px; background-color: rgb(255, 243, 222);"
								id="provinceId" name="provinceId"
								onchange="changeSelect('${path}/front/searchJob!changeSelect.action','provinceId', 'cityId')">
								<option value="">
									-请选择-
								</option>
								<c:forEach var="f" items="${provinceList}">
									<option value="${f.value}" <c:if test="${f.value == provinceId}">selected="selected"</c:if>>
										${f.key}
									</option>
								</c:forEach>
							</select>
							<label class="lbl">
								&nbsp;&nbsp;&nbsp;&nbsp;城市：
							</label>
							<select
								style="width: 155px; background-color: rgb(255, 243, 222);"
								id="cityId" name="cityId" <c:if test="${empty provinceId}"> disabled="disabled" </c:if>>
								<option value="">
									-请选择-
								</option>
								<c:forEach var="f" items="${cityList}">
									<option value="${f.value}" <c:if test="${f.value == cityId}">selected="selected"</c:if>>
										${f.key}
									</option>
								</c:forEach>
							</select>
							<label class="lbl">
								&nbsp;&nbsp;月薪要求：
							</label>
							<select
								style="width: 155px; background-color: rgb(255, 243, 222);"
								id="job_salary">
								<option value="-1">
									-请选择-
								</option>
								<option value="1">
									1000-2000元
								</option>
								<option value="2">
									2000-3000元
								</option>
								<option value="3">
									3000-4000元
								</option>
								<option value="4">
									4000-5000元
								</option>
								<option value="5">
									5000-6000元
								</option>
								<option value="6">
									6000-7000元
								</option>
								<option value="7">
									7000-8000元
								</option>
								<option value="8">
									8000-10000元
								</option>
								<option value="9">
									10000以上
								</option>
								<option selected="selected" value="0">
									不限
								</option>
							</select>

						</div>
						<div class="item">
							<label class="lbl">
								所属行业：
							</label>

							<select
								style="width: 155px; background-color: rgb(255, 243, 222);"
								id="city">
								<option selected="selected" value="">
									-请选择-
								</option>
							</select>
							<label class="lbl">
								发布时间：
							</label>
							<select
								style="width: 155px; background-color: rgb(255, 243, 222);"
								id="job_period">
								<option selected="selected" value="-1">
									-请选择-
								</option>
								<option value="1">
									一天前
								</option>
								<option value="3">
									三天前
								</option>
								<option value="7">
									一周前
								</option>
								<option value="15">
									半个月前
								</option>
								<option value="30">
									一个月前
								</option>
							</select>

							<label class="lbl">

								职位关键字：
							</label>
							&nbsp;
							<input
								style="width: 150px; background-color: rgb(255, 243, 222);"
								name="jobName" type="text" value="${jobName}">
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input value=" 搜  索  " type="submit">
						</div>

					</div>
					<div class="ui_search_ft"></div>
				</div>
				<div id="search_result" class="w970 search_result">
					<div class="hd">
						<div class="item2">
							<span class="txt">满足搜索条件的前${pagination.rowCount}个记录 |
								显示风格选择：</span>
							<span id="style_btn" class="style_btn"><b
								id="style_btn_list" class="cur">列表</b><b id="style_btn_all">详细</b>
							</span>
						</div>
					</div>
					<input id="tableUrl"
						value="http://cms.elian.cc/front/mainList!job.action?siteId=1&amp;channelId=243&amp;path=/job/yyzp"
						type="hidden">
					<div id="table" class="bd">
						<table class="search_result_tbl" cellSpacing="0" width="100%">
							<thead>
								<tr class="th">
									<th class="th01">
										操作
									</th>
									<th class="th02">
										职位名称
									</th>
									<th class="th03">
										公司名称
									</th>
									<th class="th04">
										性别
									</th>
									<th class="th05">
										工作地点
									</th>
									<th class="th06">
										工作经验
									</th>
									<th class="th07">
										发布时间
									</th>
									<th class="th08">
										有效日期
									</th>
									<th class="th09"></th>
								</tr>
							</thead>
							<tbody id="zebra_lists">
								<c:forEach var="job" items="${virtualJobList}" varStatus="e">
									<tr class="tr0">
										<td>
											<input class="ipc" type="checkbox">
										</td>
										<td class="job_tit">
											<a href="${job.path}" target="_blank">${job.jobName}</a>
											<img alt="急聘"
												src="http://images.elian.cc/design/main/jobs/ico_jipin.png">
										</td>
										<td>
											<a>${job.companyName}</a>
										</td>
										<td>
											<c:forEach var="item" items="${sexList}">
												<c:if test="${job.sex == item.key}">${item.value}</c:if>
											</c:forEach>
										</td>
										<td>
											${job.jobArea}
										</td>
										<td>
											${job.workExpe}
										</td>
										<td>
											<fmt:formatDate value="${job.publishTime}"
												pattern="yyyy-MM-dd" />
										</td>
										<td>
											<fmt:formatDate value="${job.expireTime}"
												pattern="yyyy-MM-dd" />
										</td>
										<td class="detail_btn" height="40">
											<div>
												职位描述
												<b></b>
											</div>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<h4 align="center"
							style="display:${empty pagination.list ? 'block' : 'none' }">
							很遗憾，没有找到您想要的，换个关键词再试试？
						</h4>
						<jsp:include page="../../page/common/pager.jsp"></jsp:include>
					</div>
				</div>
			</form>
		</div>



		<c:import url="http://www.elian.cc/include/foot.shtml" charEncoding="utf-8"/>
		<script type="text/javascript" src="script/jquery.header.js"></script>
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
			</script>
			<script type="text/javascript">
				function submitForm(){
					document.getElementById("searchJobForm").submit();
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