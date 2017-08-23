<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	function searchSubmit(){
		var currentPage = document.getElementById('currentPage');
		if(currentPage!=null)
			currentPage.value='1';
		document.forms[0].submit();
	}
	
	function search(event) {
　　    	if(event.keyCode==13 ){
　　    		searchSubmit();
　　    	}
　　 }
</script>
<label class="lbl-ipt-tit">查找: </label>
<select class="select"  name="pagination.conditionName">
	<c:forEach var="map" items="${pagination.conditionMap}">
		<option value="${map.value}" <c:if test="${pagination.conditionName == map.value}">selected="selected"</c:if>>
			${map.key}
		</option>
	</c:forEach>
</select> 
<label class="lbl-ipt-tit" for="search-input-bar">关键字：</label>
<input type="text" class="search-input-bar" id="search-input-bar" name="pagination.conditionContent" value="${pagination.conditionContent}"
	onkeypress="search(event)"/>
<input type="submit" class="search-btn" value="搜索"  onclick="searchSubmit()" />
<label class="lbl-ipt-tit">每页显示: </label>
<select class="select" name="pagination.rowSize" onchange="searchSubmit()"> 
	<option value="10" <c:if test="${pagination.rowSize == 10}">selected="selected"</c:if>>
		10
	</option>
	<option value="20" <c:if test="${pagination.rowSize == 20}">selected="selected"</c:if>>
		20
	</option>
	<option value="50" <c:if test="${pagination.rowSize == 50}">selected="selected"</c:if>>
		50
	</option>
		<option value="100" <c:if test="${pagination.rowSize == 100}">selected="selected"</c:if>>
		100
	</option>
</select>
<div style="padding-left:10px;padding-top:7px;">
    共&nbsp;&nbsp;${pagination.rowCount}&nbsp;&nbsp;条记录
</div>
