<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.elian.cms.syst.util.SysXmlUtils"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    //设置页面不缓存
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<jsp:include page="include/sub_header.jsp"></jsp:include>
<jsp:include page="include/nav.jsp"></jsp:include>
<div class="section">
    <div class="usr_manage_bd">
         <jsp:include page="include/reg_user_manager_top.jsp"></jsp:include>
        <div class="mob_ui_box">
            <div class="tab12_hd clearfix" id="tab12_hd">
                <h4 ><a href="${path}/reg/regUserManager!myData.action">我的资料</a></h4>
                <h4 class="cur"><a href="${path}/reg/regUserPwd!edit.action">修改密码</a></h4>
            </div>
            <form name="regUserPwdEdit" id="regUserPwdEdit" action="${path}/reg/regUserPwd!save.action" method="post">
            <div class="ui_bd usr_message_list">
                <div class="tab12_bd" id="tab12_bd">
                    <div class="tab_con" style="display: block;">
                        <ul class="list">
                           <li>
                                <c:if test="${!empty errors['successMsg']}">
                                    <span class="reg_tips reg_ok_tips">
                                      √${errors['successMsg'][0]}
                                    </span>
                                </c:if>
                            </li>
                            <li>
                                <label class="tit">原密码：</label>
                                <span class="txt">
                                     <input type="password" class="ipt" name="prvePwd" value="" maxlength="20"/>
                                </span>
				                <c:if test="${!empty errors['prvePwd']}">
					                <span class="reg_tips reg_error_tips" id="prvePwd">
					                   ${errors['prvePwd'][0]}
					                </span>
					            </c:if>
                            </li>
                            <li>
                                <label class="tit">新密码：</label>
                                <span class="txt">
                                    <input type="password" class="ipt" name="nowPwd" value="" maxlength="20"/>
                                </span> 
                                <c:if test="${!empty errors['nowPwd']}">
                                   <span class="reg_tips reg_error_tips" id="nowPwd">
                                      ${errors['nowPwd'][0]}
                                   </span>
                                </c:if>
                            </li>
                            <li>
                                <label class="tit">确认密码：</label>
                                <span class="txt">
                                    <input type="password" class="ipt" name="nowPwd2" value="" maxlength="20"/>
                                </span> 
                                <c:if test="${!empty errors['password']}">
                                    <span class="reg_tips reg_error_tips" id="password">
                                       ${errors['password'][0]}
                                    </span>
                                </c:if>
                            </li>
                            <li>
                                <input name="errorNum" type="hidden" value="${errorNum}"/>
                                <c:if test="${!empty errors['errorNum']}">
                                    <span class="reg_tips reg_error_tips" id="password">
                                       ${errors['errorNum'][0]}
                                    </span>
                                </c:if>
                            </li>
                        </ul>
                        <div class="edit_btn">
                            <input type="submit" class="save_pw_btn" value="修改资料"/>
                        </div>
                    </div>
                </div>
                <b class="left_border"></b>
                <b class="right_border"></b>
            </div>
            </form>
        </div>
    </div>
     <jsp:include page="include/reg_user_manager_center.jsp"></jsp:include>
</div>
<jsp:include page="include/footer.jsp"></jsp:include>
<script src="http://script.elian.cc/main/reg/script/base.js"></script>