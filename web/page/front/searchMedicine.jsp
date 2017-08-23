<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<c:import url="searchMedicineHeader.jsp" charEncoding="utf-8"/>
		<script type="text/javascript">
			function checkEnter(event, clickId) {
	　　    		if(event.keyCode == 13){
	　　    			document.getElementById(clickId).click();
	　　    		}
	　　    	}
			
			function submitSearch(form){
				var value = document.getElementById("keyword").value;
				if(value=='请输入关键字'){
					document.getElementById("keyword").value = "";
				}
				document.getElementById(form).submit();
			}
			
			function submitForm(){
				var element=document.getElementById("keyword");
				if(element.value=="请输入关键字"){
					element.value="";
				}
				document.getElementById("searchInstrumentForm").submit();
			}
			
			function toPage(page){
				if (page != null && page != ''){
					document.getElementById("currentPage").value= page;
				    submitForm();
			    }
			}
		</script>
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
						<li>
							<a href="front/searchCompany!list.action">搜企业</a>
						</li>
						<li>
							<a href="front/searchDoctor!list.action">搜医生</a>
						</li>
						<li>
							<a href="front/searchDrugCommon!list.action">搜药品</a>
						</li>
						<li class="cur">
							<a href="${path}/front/searchMedicine!search.action">搜药品</a>
						</li>
						<li>
							<a href="${path}/front/searchInstrument!search.action">搜器械</a>
						</li>
					</ul>
				</div>
				<form id="searchMedicineForm" method="post" action="${path}/front/searchMedicine!search.action">
					<div class="wrap_key_select">
						<div class="search_result_tips">
							共搜到
							<em class="em">${pagination.rowCount}</em>条符合搜索条件的结果
						</div>
						<div class="key_select">
							<div class="last">
								<div class="wrap_search">
									<div class="search">
										<div class="wrap_ipt">
											<input id="keyword" name="keyword" onfocus="if (this.value=='请输入关键字'){this.value='';this.style.color='#333'}"
												type="text" class="ipt" value="${empty keyword ? '请输入关键字' : keyword}" 
												onkeydown="checkEnter(event, 'searchButton');"/>
										</div>
										<input id="searchButton" onclick="submitSearch('searchMedicineForm')" class="ips" value="搜索" />
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="wrap_bd clearfix">
						<div class="wrap_ui_tit">
							<h3 class="ui_tit">
								搜索器械
							</h3>
						</div>
						<div class="mod_list">
							<ul class="pt03">
								<c:forEach var="model" items="${pagination.list}" varStatus="e">
									<li>
										<a class="pic" target="_blank" href="${model.path}">
											<img width="100" height="90"
											 src="${model.imgPath}" alt="图片位置">
										</a>
										<h4 class="name">
											<a target="_blank" href="${model.path}">${model.title}</a>
										</h4>
										<ul class="txt">
											<li>
												<span class="mess_tit">主治功能：</span>${model.summary}
											</li>
											<li>
												<span class="mess_tit">￥</span>${model.price}
											</li>
											<li class="more">
												<a href="${model.path}">查看详细信息&gt;&gt;</a>
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
		<c:import url="searchFooter.jsp" charEncoding="utf-8"/>