<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
	<head>
		<base href="<%=basePath%>">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=9">
		<title>医联网 搜索列表</title>
		<meta name="Keywords" content="医联网 搜索列表">
		<meta name="Description" content="医联网 搜索列表">
		<link rel="stylesheet" href="css/front/style/public.css" type="text/css">
		<link rel="stylesheet" href="css/front/style/global.css" type="text/css">
		<link rel="stylesheet" href="css/front/style/search_result.css" type="text/css">
		<link rel="shortcut icon" type="image/x-icon" href="http://www.elian.cc/favicon.ico">
		<script type="text/javascript">
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
		<div class="section">
			<div class="section_main">
				<div class="search_tab_nav">
					<ul class="list">
						<li>
							<a href="front/searchInformation!list.action">搜资讯</a>
						</li>
						<li>
							<a href="front/searchHospital!list.action">搜医院</a>
						</li>
						<li  class="cur">
							<a href="front/searchDoctor!list.action">搜医生</a>
						</li>
						<li>
							<a href="front/searchDrugCommon!list.action">搜药品</a>
						</li>
					</ul>
				</div>
				<form id="searchDoctorForm" method="post" action="front/searchDoctor!search.action">
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
												type="text" class="ipt" value="${empty keyword ? '请输入关键字' : keyword}" />
										</div>
										<input onclick="submitSearch('searchDoctorForm')" class="ips" value="搜索" />
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
								<c:forEach var="model" items="${pagination.list}" varStatus="e">
									<li> 
				                        <a class="pic" target="_blank" href="${basePath}${model.path}"  target="blank">
				                        	<img width="100" height="90" src="${model.imgPath}" alt="图片位置">
				                        </a>
				                        <h4 class="name">
				                        	<a target="_blank" href="${basePath}${model.path}"  target="blank">${model.title}</a>
				                        </h4>
				                        <ul class="txt">
				                          <li><span class="mess_tit">医院：</span><a href="${basePath}${model.siteId}" class="tit_link"  target="blank">${model.address}</a></li>
				                          <li><span class="mess_tit">科室：</span>${model.date}
				                          <c:if test="${null != model.dutyName}">
				                          	&nbsp;<span class="mess_tit">职称：</span>${model.dutyName}</li>
				                          </c:if>
				                          <li>
				                              <span class="mess_tit">擅长疾病：</span>${model.summary}
				                          </li>
				                          <li class="more">
				                             <c:if test="${'true'== model.isReg}">
					                         	<a href="http://www.191580.com/reg/regDoctor!detial.action?docId=${model.doctId}" target="blank">我要挂号</a>|
					                         	<a href="http://www.191580.com/reg/regDoctor!detial.action?docId=${model.doctId}#duty_tbl" target="blank">医生排班表</a>|
				                          	 </c:if>
				                          <a href="${basePath}${model.path}" target="blank">查看医生详细&gt;&gt;</a></li>
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
			<div class="sidebar article_sidebar">
				<div class="aside">
					<a href="#"><img src="http://placehold.it/280x200" width="280"
							height="200" /> </a>
				</div>
				<div class="aside mod-rank">
					<div class="mod_gray_hd">
						<h3 class="ico_hand">
							点击排行
						</h3>
					</div>
					<div class="mod_gray_bd">
						<div class="aside-bd">
							<ul class="list">
								<li class="first">
									<div class="tit">
										<b class="top-rank">1</b><a href="">冬季不吃饺子会冻耳朵吗</a>
									</div>
									<div class="pt03">
										<a href="#" class="pic"> <img src="images/front/pic100x75.jpg"
												width="100" height="75" /> </a>
										<p class="txt">
											玉米饺子韭菜饺子萝卜饺子肉包叉烧包奶油蛋糕三明治菠萝包肉松面包方包餐包
										</p>
									</div>
								</li>
								<li>
									<div class="tit">
										<b class="top-rank">2</b><a href="">冬季不吃饺子会冻耳朵吗</a>
									</div>
								</li>
								<li>
									<div class="tit">
										<b class="top-rank">3</b><a href="">冬季不吃饺子会冻耳朵吗</a>
									</div>
								</li>
								<li>
									<div class="tit">
										<b>4</b><a href="">冬季不吃饺子会冻耳朵吗</a>
									</div>
								</li>
								<li>
									<div class="tit">
										<b>5</b><a href="">冬季不吃饺子会冻耳朵吗</a>
									</div>
								</li>
								<li>
									<div class="tit">
										<b>6</b><a href="">冬季不吃饺子会冻耳朵吗</a>
									</div>
								</li>
								<li>
									<div class="tit">
										<b>7</b><a href="">冬季不吃饺子会冻耳朵吗</a>
									</div>
								</li>
							</ul>
						</div>
					</div>
				</div>
				<div class="aside">
					<div class="mod_recomm_hd">
						<h3 class="ui_tit">
							推荐医生
						</h3>
						<a href="#" class="more">&gt;&gt;更多</a>
					</div>
					<div class="mod_recomm_bd">
						<ul class="pt02">
							<li>
								<a class="pic" target="_blank"
									href="http://h.elian.cc/index.jsp?h=516"><img
										src="images/front/pic70x90.jpg" width="70" height="90" alt="">
								</a>
								<div class="txt">
									<span class="tit"><a target="_blank" href="">古洁若</a> </span>
									<span>内科</span>
									<span class="ico-tel">主任医师</span>
									<a href="#" class="link_btn">点击查看</a>
								</div>
							</li>
							<li>
								<a class="pic" target="_blank"
									href="http://h.elian.cc/index.jsp?h=516"><img
										src="images/front/pic70x90.jpg" width="70" height="90" alt="">
								</a>
								<div class="txt">
									<span class="tit"><a target="_blank" href="">古洁若</a> </span>
									<span>内科</span>
									<span class="ico-tel">主任医师</span>
									<a href="#" class="link_btn">点击查看</a>
								</div>
							</li>
							<li>
								<a class="pic" target="_blank"
									href="http://h.elian.cc/index.jsp?h=516"><img
										src="images/front/pic70x90.jpg" width="70" height="90" alt="">
								</a>
								<div class="txt">
									<span class="tit"><a target="_blank" href="">古洁若</a> </span>
									<span>内科</span>
									<span class="ico-tel">主任医师</span>
									<a href="#" class="link_btn">点击查看</a>
								</div>
							</li>
							<li>
								<a class="pic" target="_blank"
									href="http://h.elian.cc/index.jsp?h=516"><img
										src="images/front/pic70x90.jpg" width="70" height="90" alt="">
								</a>
								<div class="txt">
									<span class="tit"><a target="_blank" href="">古洁若</a> </span>
									<span>内科</span>
									<span class="ico-tel">主任医师</span>
									<a href="#" class="link_btn">点击查看</a>
								</div>
							</li>
						</ul>
						<div class="line"></div>
					</div>
				</div>
				<div class="aside">
					<div class="mod_gray_hd">
						<h3>
							好评医生
						</h3>
					</div>
					<div class="mod_gray_bd pt02 high_rank_doc">
						<a class="pic" target="_blank"
							href="http://h.elian.cc/index.jsp?h=516"><img
								src="images/front/pic100x130.jpg" width="100" height="130" alt="">
						</a>
						<div class="txt">
							<div class="i">
								<span class="tit">姓名：</span><span>马晓年</span>
							</div>
							<div class="i">
								<span class="tit">科室：</span><span>男科</span>
							</div>
							<div class="i">
								<span class="tit">医院：</span><span>佛山华康专科医院</span>
							</div>
							<div class="i">
								<span class="tit">综合评分：</span><span>4.5分</span>
							</div>
							<div class="i">
								<a class="zixun_btn" href="#">咨询医生</a>
								<a class="guahao_btn" href="#">网上挂号</a>
							</div>
						</div>
					</div>
				</div>
				<div class="aside aside_recomm_hos">
					<div class="mod_recomm_hd">
						<h3 class="ui_tit">
							推荐医院
						</h3>
						<a href="#" class="more">&gt;&gt;更多</a>
					</div>
					<div class="mod_recomm_bd">
						<ul class="pt01 clearfix">
							<li>
								<a class="pic" target="_blank"
									href="http://h.elian.cc/index.jsp?h=516"><img
										src="images/front/hos120x90.jpg" width="120" height="90" alt="">
								</a>
								<div class="tit">
									<a href="#">佛山市九江医院</a>
								</div>
							</li>
							<li>
								<a class="pic" target="_blank"
									href="http://h.elian.cc/index.jsp?h=516"><img
										src="images/front/hos120x90.jpg" width="120" height="90" alt="">
								</a>
								<div class="tit">
									<a href="#">佛山市九江医院</a>
								</div>
							</li>
							<li>
								<a class="pic" target="_blank"
									href="http://h.elian.cc/index.jsp?h=516"><img
										src="images/front/hos120x90.jpg" width="120" height="90" alt="">
								</a>
								<div class="tit">
									<a href="#">佛山市九江医院</a>
								</div>
							</li>
							<li>
								<a class="pic" target="_blank"
									href="http://h.elian.cc/index.jsp?h=516"><img
										src="images/front/hos120x90.jpg" width="120" height="90" alt="">
								</a>
								<div class="tit">
									<a href="#">佛山市九江医院</a>
								</div>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div class="footer">
			<div class="tips">
				<p>
					温馨提示：如果您怀疑自己有某种健康问题，可在线咨询专家或尽快去医院就诊治疗。
				</p>
			</div>
			<div class="bottom_nav">
				<a href="http://www.elian.cc/other/about_intro.html" target="_blank">关于我们</a>
				|
				<a href="http://www.elian.cc/other/about_sitemap.html"
					target="_blank">网站地图</a> |
				<a href="http://www.elian.cc/about/index.html" target="_blank">友情链接</a>
				|
				<a href="">营销中心</a> |
				<a href="http://www.elian.cc/other/about_law.html" target="_blank">法律声明</a>
				|
				<a href="">隐私声明</a> |
				<a href="http://www.elian.cc/other/about_contact.html"
					target="_blank">联系我们</a> |
				<a href="http://www.elian.cc/other/about_vip.html" target="_blank">加入医联网推广</a>
			</div>
			<p>
				电话：0757-82137888 传真：0757-82139888 全国电话：40001-91580
			</p>
			<p>
				<a href="http://www.elian.cc/">www.elian.cc </a>
				<a href="http://www.xn--ekrw93hgwa.com/">医联网.COM</a>
				<a href="http://www.xn--ekrw93hgwa.cn/">医联网.CN</a> 版权所有未经授权请勿转载
			</p>
			<p>
				粤ICP备10054714号
				<a
					href="http://www.elian.cc/about/images/photo/DrugInformationCertificate.jpg">互联网药品信息服务资格证书</a>
				证件编号：
				<a
					href="http://www.elian.cc/about/images/photo/DrugInformationCertificate.jpg"
					target="_blank">（粤）-非经营性-2010-0176</a>
			</p>
			<p>
				本站信息仅供参考不能作为诊断及医疗的依据，如有转载或引用文章涉及版权问题请速与我们联系。
			</p>
		</div>
		<script type="text/javascript" src="script/jquery-1.4.2.min.js"></script>
		<script type="text/javascript" src="script/jquery.header.js"></script>
		<script language="javascript" type="text/javascript" src="${resScript}/base0530.js"></script>
		<div class="hidden">
			<script type="text/javascript" src="script/baidu-flow.js"></script>
			<script type="text/javascript" src="script/google-flow.js"></script>
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