<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    //设置页面不缓存
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<jsp:include page="include/manager_header.jsp"></jsp:include>
<jsp:include page="include/nav.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="http://style.elian.cc/main/pager.css"/>
<div class="section">
    <div class="usr_manage_bd">
         <jsp:include page="include/reg_notes_top.jsp"></jsp:include>
       <form action="${path}/reg/regNotes!list.action" method="post" name="regNotes" id="regNotes">
        <div class="mob_ui_box">
            <h4>挂号记录</h4>
            <div class="ui_bd">
                <div class="usr_wrap_tbl">
                    <div class="ui_tbl_hd"></div>
                    <table cellspacing="0" cellpadding="0" class="tbl">
                        <thead>
                            <tr>
                               <th class="col_p5">序号</th>
                               <th class="col_p15">单号</th>
                               <th class="col_p15">挂号日期</th>
                               <th class="col_p25">医院</th>
                               <th class="col_p15">科室</th>
                               <th class="col_p10">医生</th>
                               <th class="col_p15">预约时间</th>
                           </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="ur" items="${pagination.list}" varStatus="e">
                            <tr>
                                <td class="col_p5">${e.index+1}</td>
                                <td class="col_p15">${ur.registerCode}</td>
                                <td class="col_p15">
                                  <fmt:formatDate value="${ur.createTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
                                </td>
                                <td class="col_p25">
                                <a href="${path}/reg/regDoctor!searchByDept.action?hospId=${ur.schedulingId.doctor.dept.hospital.id}">
                                   ${ur.schedulingId.doctor.dept.hospital.hospName}
                                </a>
                                </td>
                                <td class="col_p15">${ur.schedulingId.doctor.dept.deptName}</td>
                                <td class="col_p10">
                                <a href="${path}/reg/regDoctor!detial.action?docId=${ur.schedulingId.doctor.id}&regId=${ur.id}">${ur.schedulingId.doctor.doctName}</a>
                                </td>
                                <td class="col_p15"><fmt:formatDate value="${ur.schedulingId.startTime}" pattern="HH:mm"/>
                                -<fmt:formatDate value="${ur.schedulingId.endTime}" pattern="HH:mm"/>
                                </td>
                             </tr>
                        </c:forEach>
                            <tr>
                               <td class="last" colspan="8">
                               <c:if test="${pagination.rowCount>10}">
			                     <jsp:include page="/page/common/pager.jsp"></jsp:include>
			                   </c:if>
                               </td>
                            </tr>
                         </tbody>
                    </table>
                </div>
                <b class="left_border"></b>
                <b class="right_border"></b>
            </div>
        </div>
        </form>
        <script type="text/javascript">
          function toPage(page){
              if (page != null && page != ''){
                  document.getElementById("currentPage").value= page;
                  document.getElementById("regNotes").submit();
              }
          }
         </script>
    </div>
     <jsp:include page="include/reg_user_manager_center.jsp"></jsp:include>
</div>
<jsp:include page="include/footer.jsp"></jsp:include>
<script src="http://script.elian.cc/main/reg/script/base.js"></script>