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
         <jsp:include page="include/reg_consumption_top.jsp"></jsp:include>
         <div class="usr_account_txt">
            <table cellspacing="0" cellpadding="0" class="tbl">
                <tbody>
                    <tr>
                        <td class="td_tit">用户余额</td><td class="td_txt"><em class="red">0</em>元</td>
                        <td class="td_tit">成功挂号</td><td class="td_txt"><em class="red">${pagination.rowCount}</em>次</td>
                    </tr>
                    <tr>
                        <td class="td_tit">消费</td><td class="td_txt"><em class="red">${consumption}</em>元</td>
                        <td class="td_tit">爽约</td><td class="td_txt"><em class="red"></em>暂未爽约</td>
                    </tr>
                </tbody>
            </table>
            <div class="i">您现在的级别是 <em class="red">普通会员</em></div>  
        </div> 
       <form action="${path}/reg/regConsumption!list.action" method="post" name="regNotes" id="regNotes">
         <div class="usr_wrap_tbl">
            <div class="ui_tbl_hd"></div>
            <table cellspacing="0" cellpadding="0" class="tbl">
                <thead>
                    <tr>
                       <th class="col_p5">序号</th>
                       <th class="col_p15">单号</th>
                       <th class="col_p10">挂号日期</th>
                       <th class="col_p25">医院</th>
                       <th class="col_p15">科室</th>
                       <th class="col_p10">医生</th>
                       <th class="col_p10">消费方式</th>
                       <th class="col_p10">操作资金</th>
                   </tr>
                </thead>
                <tbody>
                 <c:forEach var="ur" items="${pagination.list}" varStatus="e">                
                    <tr>
                        <td class="col_p5">${e.index+1}</td>
                        <td class="col_p15">${ur.registerCode}</td>
                        <td class="col_p10">
                           <fmt:formatDate value="${ur.createTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
                        </td>
                        <td class="col_p25">
                        <a href="${path}/reg/regDoctor!searchByDept.action?hospId=${ur.schedulingId.doctor.dept.hospital.id}">
                           ${ur.schedulingId.doctor.dept.hospital.hospName}
                        </a>
                        </td>
                        <td class="col_p15">${ur.schedulingId.doctor.dept.deptName}</td>
                        <td class="col_p10"><a href="${path}/reg/regDoctor!detial.action?docId=${ur.schedulingId.doctor.id}">${ur.schedulingId.doctor.doctName}</a></td>
                        <td class="col_p10">
                        <c:forEach var="payType" items="${payTypeList}">
                          <c:if test="${payType.key eq ur.payType}">${payType.value}</c:if>
                        </c:forEach>
                       </td>
                        <td class="col_p10">￥${ur.amount}</td>
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