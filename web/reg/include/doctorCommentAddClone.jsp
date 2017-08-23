<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%--医生评论添加--%>
<div class="reply_area" id="reply_area">
	<div class="hd">
		<h3 class="ui_tit">
			医生评价
		</h3>
	</div>
	<div class="bd">
		<form action="${path}/reg/regDoctorClone!save.action#reply_area"
			name="doctor_comment" id="doctor_comment" target="" method="post">
			<input type="hidden" name="cssStr" value="${cssStr}">
			<input type="hidden" name="docId" value="${docId}" />
			<input type="hidden" name="forwardPage" id="forwardPage"
				value="${forwardPage}" />
			<input type="hidden" name="token" value="${token}" />
			<div class="i">
				<c:if test="${!empty errors['userMsg']}">
					<div class="reg_tips reg_error_tips" style="color:red">
						${errors['userMsg'][0]}
					</div>
				</c:if>
				<span class="tit">评分：</span>
				<ul class="wrap_rate_area">
					<li>
						<span class="tit">诊疗效果</span>
						<div class="star" id="star"
							style="cursor: pointer; width: 100px;">
							<img
								src="http://style.elian.cc/main/reg/style/rate/star-off.png"
								alt="1" title="很差(2分)" />
							&nbsp;
							<img
								src="http://style.elian.cc/main/reg/style/rate/star-off.png"
								alt="2" title="差(4分)" />
							&nbsp;
							<img
								src="http://style.elian.cc/main/reg/style/rate/star-off.png"
								alt="3" title="一般(6分)" />
							&nbsp;
							<img
								src="http://style.elian.cc/main/reg/style/rate/star-off.png"
								alt="4" title="好(8分)" />
							&nbsp;
							<img
								src="http://style.elian.cc/main/reg/style/rate/star-off.png"
								alt="5" title="很好(10分)" />
							<input type="hidden" name="score" value="${score}" />
						</div>
						<div class="hint" id="hint">
							待评分
						</div>
					</li>
					<li>
						<span class="tit">服务态度</span>
						<div class="star" id="star2"
							style="cursor: pointer; width: 100px;">
							<img
								src="http://style.elian.cc/main/reg/style/rate/star-off.png"
								alt="1" title="很差(2分)">
							&nbsp;
							<img
								src="http://style.elian.cc/main/reg/style/rate/star-off.png"
								alt="2" title="差(4分)" />
							&nbsp;
							<img
								src="http://style.elian.cc/main/reg/style/rate/star-off.png"
								alt="3" title="一般(6分)" />
							&nbsp;
							<img
								src="http://style.elian.cc/main/reg/style/rate/star-off.png"
								alt="4" title="好(8分)">
							&nbsp;
							<img
								src="http://style.elian.cc/main/reg/style/rate/star-off.png"
								alt="5" title="很好(10分)" />
							<input type="hidden" name="score" value="${score}" />
						</div>
						<div class="hint" id="hint2">
							待评分
						</div>
					</li>
					<li>
						<span class="tit">医德</span>
						<div class="star" id="star3"
							style="cursor: pointer; width: 100px;">
							<img
								src="http://style.elian.cc/main/reg/style/rate/star-off.png"
								alt="1" title="很差(2分)" />
							&nbsp;
							<img
								src="http://style.elian.cc/main/reg/style/rate/star-off.png"
								alt="2" title="差(4分)" />
							&nbsp;
							<img
								src="http://style.elian.cc/main/reg/style/rate/star-off.png"
								alt="3" title="一般(6分)" />
							&nbsp;
							<img
								src="http://style.elian.cc/main/reg/style/rate/star-off.png"
								alt="4" title="好(8分)" />
							&nbsp;
							<img
								src="http://style.elian.cc/main/reg/style/rate/star-off.png"
								alt="5" title="很好(10分)" />
							<input type="hidden" name="score" value="${score}" />
						</div>
						<div class="hint" id="hint3">
							待评分
						</div>
					</li>
				</ul>
			</div>
			<c:if test="${!empty errors['docComment.score']}">
				<span class="reg_tips reg_error_tips"  style="color:red">
					${errors['docComment.score'][0]} </span>
			</c:if>
			<div class="i">
				<span class="tit">就诊疾病：</span>
				<input name="docComment.illness" id="illness" type="text"
					class="ipt"
					value="${!empty docComment.illness?docComment.illness:'输入本次就医的疾病名称'}"
					onfocus="if (this.value=='输入本次就医的疾病名称'){this.value='';this.style.color='#333'}" />
				<span class="must">*</span>
				<c:if test="${!empty errors['docComment.illness']}">
					<span class="reg_tips reg_error_tips"  style="color:red">
						${errors['docComment.illness'][0]} </span>
				</c:if>
			</div>
			<div class="i clearfix">
				<span class="tit">我的评价：</span>
				<textarea class="txtarea" name="docComment.leaveWords">${docComment.leaveWords}</textarea>
				<c:if test="${!empty errors['docComment.leaveWords']}">
					<span class="reg_tips reg_error_tips">
						${errors['docComment.leaveWords'][0]} </span>
				</c:if>
			</div>
			<div class="i wrap_submit_btn">
				<span class="tips">请文明发言，勿进行人身攻击</span>
				<input type="button" class="ipt" value="提交"
					onclick="forwardPages(this.form);" />
			</div>
		</form>
	</div>
	<script type="text/javascript">
function submitGologin() {
	G("forwardPage").value = document.location.href;
	return true;
}

function checkLogin(){
	var bool = false;
	$.ajax({
    	type: 'POST',
        url: '${path}/reg/regDoctorWorkClone!checkLogin.action',
        async: false,
		dataType: "json",
		success:function(data){
			bool = data.MSG;
		}
    });
	return bool;
}

function forwardPages(form) {
	if(checkLogin()=='false'){ 
		alert('请先登录');
		return;
	}
	G("forwardPage").value = document.location.href;
	if (GV("illness") == "输入本次就医的疾病名称") {
		G("illness").value = "";
		G("illness").focus();
	} else {
		submits(form);
	}
}
</script>
</div>