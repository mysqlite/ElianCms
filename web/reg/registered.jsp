<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="include/sub_header.jsp"></jsp:include>
<script type='text/javascript' src='${path}/js/manage/editCommon.js'></script>
 <script type='text/javascript' src='${path}/dwr/interface/user.js'></script>
<script type='text/javascript' src='${path}/dwr/interface/val.js'></script>
<script type='text/javascript' src='${path}/dwr/engine.js'></script>
<script type='text/javascript' src='${path}/dwr/util.js'></script>
<jsp:include page="include/nav.jsp"></jsp:include>
<form name="reg" id="reg" action="${path}/reg/registered!saveReg.action" method="post">
<div class="section">
    <div class="reg_page_hd">
        <h2 class="tit">注册挂号新用户</h2>
    </div>
    <div class="reg_page_bd">
        <h3 class="tit">填写用户基本信息</h3>
        <span class="login_tips">我有帐号，<a href="${path}/regindex.jsp">登录</a></span>
        <div class="i">
            <span class="details_tit">账号<b class="must">*</b></span>
            <input class="ipt" value="${user.account}" type="text" id="account" name="user.account"/>
            <c:if test="${!empty errors['user.account']}">
	           <b class="reg_tips reg_error_tips" id="account_m">
	              ${errors['user.account'][0]}
	           </b>
            </c:if>
            <span id="account_tip"></span>
        </div>
        <div class="i">
            <span class="details_tit">密码<b class="must">*</b></span>
            <input class="ipt" type="password" value="${user.password}" id="password" name="user.password"/>
            <c:if test="${!empty errors['user.password']}">
	           <b class="reg_tips reg_error_tips" id="password_m" >
	              ${errors['user.password'][0]}
	           </b>
	        </c:if>
	        <span id="password_tip"></span>
        </div>
        <div class="i">
            <span class="details_tit">确认密码<b class="must">*</b></span>
            <input class="ipt" type="password" value="${pwd}" id="pwd" name="pwd"/>
	        <c:if test="${!empty errors['pwd']}">
	           <b class="reg_tips reg_error_tips" id="pwd_m" >
	              ${errors['pwd'][0]}
	           </b>
	        </c:if>
	        <span id="pwd_tip"></span>
        </div>
        <div class="i">
            <span class="details_tit">手机号码<b class="must">*</b></span>
            <input class="ipt" value="${user.mobile}" type="text" id="mobile" name="user.mobile"/>
	        <c:if test="${!empty errors['user.mobile']}">
	           <b class="reg_tips reg_error_tips" id="mobile_m" >
	              ${errors['user.mobile'][0]}
	           </b>
	        </c:if>
	        <span id="mobile_tip"></span>
        </div>
        <div class="i">
            <span class="details_tit">真实姓名<b class="must">*</b></span>
            <input class="ipt" value="${user.realName}" type="text" id="realName" name="user.realName"/>
	        <c:if test="${!empty errors['user.realName']}">
	          <b class="reg_tips reg_error_tips" id="realName_m">
	             ${errors['user.realName'][0]}
	          </b>
	        </c:if>
	        <span id="realName_tip"></span>
        </div>
        <div class="i veti_code">
            <span class="details_tit">验证码<b class="must">*</b></span>
             <input class="ipt" name="captcha" value="" type="text" id="captcha" maxlength="5"/>
	         <img src="${path}/page/common/image.jsp" width="70"  alt="点击切换"  id="valCode" height="25"  onclick="this.src='${path}/page/common/image.jsp?random='+ Math.random()" />
	         <a href="javaScript:void(0);" onclick="G('valCode').src='${path}/page/common/image.jsp?random='+ Math.random()">点击切换验证码</a>
	          <c:if test="${!empty errors['captcha']}">
	            <b class="reg_tips reg_error_tips" id="captcha_m">
	               ${errors['captcha'][0]}
	            </b>
	          </c:if>
	          <span id="captcha_tip"></span>
        </div>
        <div class="i service_agreement">
          <label class="lbl"><input type="checkbox" id="serviceTerms" name="serviceTerms" class="ipc" <c:if test="${!empty serviceTerms and serviceTerms}">checked="checked"</c:if>  />我同意<a class="service_link" href="#">《挂号网用户注册协议》</a></label>
          <span id="serviceTerms_tip"></span>
        </div>
        <div class="i wrap_submit_btn">
            <input type="button" id="submit_btn" class="submit_btn not_ready" onclick="regSubmits(this.form);" value="提交注册"/>
        </div>
    </div>
</div>
 </form>
 <script type="text/javascript">
    function regSubmits(formsa){
         var checkService=G("serviceTerms").checked;
         if(!checkService){
             alert("请勾选我同意《挂号网用户注册协议》");
             return false;
         }else{
        	 if(disables()){submits(formsa);}
         }
     }
    disables=function(){
    	if(G("serviceTerms").checked){
    		serviceTerms=true;
    	}
       if(account&&pwd&&password&&realName&&mobile&&captcha&&serviceTerms){
    	   $('#submit_btn').removeClass('not_ready');
          return true;       
        }else{
          $('#submit_btn').addClass('not_ready');
          return false;
        }
     };
   var storAccount;
   var account=false,pwd=false,password=false,realName=false,mobile=false,captcha=false,serviceTerms=false;
   var accountMsg="账号为4-16位的小写字母及数字组合,不能包含空格";
   var passwordMsg="密码为6-20字以内字母、数字、符号组合";
   var pwdMsg="再次输入密码";
   var realNameMsg="真实姓名在16个汉字以内";
   var mobileMsg="请填写阿拉伯数字的手机号码";
   var captchaMsg="请输入验证码";
   
   G("serviceTerms").onclick=function(){if(G("serviceTerms").checked){serviceTerms=true;disables();}else{serviceTerms=false;disables();}}
   G("account").onfocus=function(){if(!account){MO("account",accountMsg);N("account");}};
   G("password").onfocus=function(){if(!password){MO("password",passwordMsg);N("password");}};
   G("pwd").onfocus=function(){if(!pwd){MO("pwd",pwdMsg);N("pwd");}};
   G("realName").onfocus=function(){if(!realName){MO("realName",realNameMsg);N("realName");}};
   G("mobile").onfocus=function(){if(!mobile){MO("mobile",mobileMsg);N("mobile");}};
   G("captcha").onfocus=function(){if(!captcha){MO("captcha",captchaMsg);N("captcha");}};
   
   
   G("account").oninput=function(){ MO("account",accountMsg+ML("account"));};
   G("password").oninput=function(){ MO("password",passwordMsg+ML("password"));};
   G("pwd").oninput=function(){ MO("pwd",pwdMsg+ML("pwd"));};
   G("realName").oninput=function(){ MO("realName",realNameMsg+ML("realName"));};
   G("mobile").oninput=function(){ MO("mobile",mobileMsg+ML("mobile"));};
   G("captcha").oninput=function(){ MO("captcha",captchaMsg+ML("captcha"));};
   
   G("captcha").onkeyup=function(){
	    val.checkCaptcha(GV("captcha"),REQU,function(result){
           if(result){MO("captcha","正确",true);captcha=true;disables()}
       });
   };
   G("captcha").onblur=function(){
	   val.checkCaptcha(GV("captcha"),REQU,function(result){
		   if(result){MO("captcha","正确",true);captcha=true;disables();}else{ME("captcha","验证码错误");}
	   });
   };
   
   G("mobile").onblur=function(){
	   val.isMobile(GV("mobile"),function(result){if(result){mobile=true;MO("mobile","正确",true);disables();}else{ME("mobile",mobileMsg);}});
   };
   G("realName").onblur=function(){
	   val.isChina(GV("realName"),function(result){if(result){
		   val.isLengOut(GV("realName"),16,function(resuName){if(!resuName){MO("realName","正确",true);realName=true;disables();} else{ME("realName",realNameMsg);}});
	   }else{ME("realName","请输入全中文真实姓名");}});
   };
   G("pwd").onblur=function(){
	   val.pwdFilter(GV("pwd"),6,20,function(result){if(GV("pwd")!=GV("password")){ME("pwd","两次密码不相等");}else if(result){MO("pwd","正确",true);pwd=true;disables();}else{ME("pwd",passwordMsg);}})
   };
   G("password").onblur=function(){
	   val.pwdFilter(GV("password"),6,20,function(result){if(result){MO("password","正确",true);password=true;disables();}else{ME("password",passwordMsg);}})
   };
   
   G("account").onblur=function(){
	   if(GV("account")==""){
		   ME("account","账号长度不在4-16位字母及数字组合范围内");
		   return;
	   }
	   if(storAccount==GV("account")){
		   return;
	   }
	   storAccount=GV("account");
	   val.accountFilter(GV("account"),4,20,function(result){if(!result){ME("account","账号长度不在4-16位字母及数字组合范围内");}else{
	   user.checkAccount(GV("account"),REQU,function(resuCheck){if(!resuCheck){ME("account","该账号已被注册,请重新输入");}else{
	   MO("account","账号可用",true);account=true;disables();}});
	   }});
   };
     </script>
     <script src="http://script.elian.cc/main/reg/script/base.js"></script>
<jsp:include page="include/footer.jsp"></jsp:include>