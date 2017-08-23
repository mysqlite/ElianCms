<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
			function toPage(page){
				if (page != null && page != ''){
					document.getElementById("currentPage").value= page;
				    document.forms[0].submit();
				    }
			}
			
			function go(pNo, currentPage) {
				var pageNo = document.getElementById(pNo);
				if (Number(pageNo.value) == Number(currentPage))
					return;
				toPage(pageNo.value);
			}
			
			function numberFormat(pageNo, lastPage) {
				var idNum = document.getElementById(pageNo);
				if (!Number(idNum.value) || idNum.value == 0 || idNum.value == null || idNum.value == undefined) {
					idNum.value = 1;
				}
				else {
					if (Number(idNum.value) > Number(lastPage)) {
						idNum.value = lastPage;
					}
				}
				idNum.value = Number(idNum.value);
			}
			
			function checkEnter(event, clickId) {
	　　    		if(event.keyCode == 13){
	　　    			document.getElementById(clickId).click();
	　　    		}
	　　    	}
	</script>
	<input id="currentPage" name="paginationComment.pageNo" type="hidden" value="${paginationComment.pageNo}"/>
	<ul id='pagination-digg' class='pagination clearfix' style='float: right'>
		<c:if test="${paginationComment.pageNo > 1}">
			<li title='首页' class='page_jump'>
				<a class="pointer" onclick="toPage(1);">首页</a>
			</li>
			<li title='上一页' class='page_jump'>
				<a class="pointer" onclick="toPage(${paginationComment.pageNo-1});">&#139;上一页</a>
			</li>
		</c:if>
		<c:forEach var="i" begin="${paginationComment.pageNo - paginationComment.offsetSize < 1 ? 1 : paginationComment.pageNo - paginationComment.offsetSize}" 
			end="${paginationComment.pageNo-1}" step="1">
		 	<c:if test="${i > 0}">
		 		<li title='第${i}页' class='page_jump'>
					<a  class="pointer" onclick="toPage(${i});">${i}</a>
				</li>
			</c:if>
		</c:forEach>
		
		<c:if test="${paginationComment.pageNo <= paginationComment.pageCount}">
			<li title='第${paginationComment.pageNo}页' class='pages cur'>
				${paginationComment.pageNo}
			</li>
		</c:if>
		
		<c:forEach var="i" begin="${paginationComment.pageNo + 1}" end="${paginationComment.pageNo + paginationComment.offsetSize}" step="1">
		 	<c:if test="${i <= paginationComment.pageCount}">
		 		<li title='第${i}页' class='page_jump'>
					<a class="pointer" onclick="toPage(${i});">${i}</a>
				</li>
			</c:if>
		</c:forEach>
		
		<c:if test="${paginationComment.pageNo < paginationComment.pageCount}">
			<li title='下一页' class='page_jump'>
				<a class="pointer" onclick="toPage(${paginationComment.pageNo+1});">&#139;下一页</a>
			</li>
			<li title='尾页' class='page_jump'>
				<a class="pointer" onclick="toPage(${paginationComment.pageCount});">尾页</a>
			</li>
		</c:if>
		<li class='page'>
			<input type='text' id='pageNo' class='ipage size2' title='请输入页码' value='${paginationComment.pageNo}' 
				onkeydown="checkEnter(event, 'igo');" onkeyup="numberFormat('pageNo',${paginationComment.pageCount});"/>
			<span class="page-count">
				&nbsp;/${paginationComment.pageCount == 0 ? 1 : paginationComment.pageCount}
			</span>
		</li>
		<li title='跳转页面' class='go'>
			<a id='igo' class='igo' onclick="go('pageNo',${paginationComment.pageNo});">GO</a>
		</li>
	</ul>