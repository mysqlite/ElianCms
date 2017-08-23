<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${jsonresult.error == 0}">
  <%=request.getAttribute("jsonresult")%>
  </c:if>
  <c:if test="${!empty errMsg}">
  <%=request.getAttribute("errMsg")%>
  </c:if>
