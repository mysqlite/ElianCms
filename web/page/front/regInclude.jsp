<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
            <input type="hidden" name="user.status" value="5" />
            <input type="hidden" name="user.admin" value="true" />
            <input type="hidden" name="user.proposer" value="true" />   
    <div class="i">
        <span class="details_tit">登录名<b class="must">*</b></span>
        <input class="ipt" value="${user.account}" type="text" name="user.account" />
        <c:if test="${!empty errors['user.account']}">
           <b class="reg_tips reg_error_tips" >
              ${errors['user.account'][0]}
           </b>
        </c:if>
    </div>
    <div class="i">
        <span class="details_tit">密码<b class="must">*</b></span>
        <input class="ipt" type="password" value="${user.password}" name="user.password"/>
        <c:if test="${!empty errors['user.password']}">
           <b class="reg_tips reg_error_tips" >
              ${errors['user.password'][0]}
           </b>
        </c:if>
    </div>
    <div class="i">
        <span class="details_tit">确认密码<b class="must">*</b></span>
        <input class="ipt" type="password" value="${pwd}" name="pwd"/>
        <c:if test="${!empty errors['pwd']}">
           <b class="reg_tips reg_error_tips" >
              ${errors['pwd'][0]}
           </b>
        </c:if>
    </div>
    <div class="i">
        <span class="details_tit">手机号码<b class="must">*</b></span>
        <input class="ipt" value="${user.mobile}" type="text" name="user.mobile"/>
        <c:if test="${!empty errors['user.mobile']}">
           <b class="reg_tips reg_error_tips" >
              ${errors['user.mobile'][0]}
           </b>
        </c:if>
    </div>
    <div class="i">
        <span class="details_tit">电子邮箱<b class="must">*</b></span>
        <input class="ipt" value="${user.email}" type="text" name="user.email"/>
        <c:if test="${!empty errors['user.email']}">
           <b class="reg_tips reg_error_tips" >
              ${errors['user.email'][0]}
           </b>
        </c:if>
    </div>
    <div class="i">
        <span class="details_tit">真实名字</span>
        <input class="ipt" value="${user.realName}" type="text" name="user.realName"/>
        <c:if test="${!empty errors['user.realName']}">
           <b class="reg_tips reg_error_tips" >
              ${errors['user.realName'][0]}
           </b>
        </c:if>
    </div>
    <div class="i gender">
        <span class="details_tit">性别<b class="must">*</b></span>
        <c:forEach var="sex" items="${sexList}" varStatus="e">
           <label class="lbl" for="${sex.key}"><input <c:if test="${sex.key eq 'male'}"> checked="checked" </c:if> name="user.gender" id="${sex.key}" type="radio" class="ipr" value="${sex.key}"/>${sex.value}</label>
        </c:forEach>
        <c:if test="${!empty errors['user.gender']}">
           <b class="reg_tips reg_error_tips" >
              ${errors['user.gender'][0]}
           </b>
        </c:if>
    </div>
    <div class="i veti_code">
        <span class="details_tit">验证码<b class="must">*</b></span>
        <input class="ipt" name="captcha" value="" type="text" maxlength="5"/>
        <img src="../page/common/image.jsp" width="70"  alt="点击切换"  id="valCode" height="25" onclick="this.src='../page/common/image.jsp?random='+ Math.random()"  />
        <a href="javaScript:void(0);" onclick="G('valCode').src='../page/common/image.jsp?random='+ Math.random()">点击切换验证码</a>
         <c:if test="${!empty errors['captcha']}">
           <b class="reg_tips reg_error_tips" >
              ${errors['captcha'][0]}
           </b>
        </c:if>
    </div>
    <div class="i service_agreement">
       <label class="lbl"><input type="checkbox" id="serviceTerms" class="ipc" id="check"/>我已看过并同意<a class="service_link" href="#">《医联网平台服务条款》</a></label>
    </div>
