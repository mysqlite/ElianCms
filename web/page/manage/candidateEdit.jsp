<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<html lang="zh-CN">
	<head>
		<title>申请该职位</title>
        <jsp:include page="../../page/include/headEdit.jsp"></jsp:include>
	</head>
	<body class="">
    <div class="main-top-hd clearfix">
        <h3 class="cur">
        <c:if test="${edit}">编辑</c:if>
        <c:if test="${!edit}">申请该职位</c:if>
        </h3>
    </div>
<div class=body>
    <form id="contacterCOrU" name="contacterCOrU" method="post" action="${path}/admin/candidate!save.action">
        <input type="hidden" name="contacter.version" value="${contacter.version}">
        <input type="hidden" name="contacter.site.id" value="27">
        <input type="hidden" name="contacter.id" value="${contacter.id}">
        <input type="hidden" name="edit" value="${edit}">
        <input type="hidden" name="token" value="${token}"/>
        <div class="divInputTable">
            <!-- h3 class="main-tit-bar" id="typeBtn">基本信息</h3 -->
            <div id="typeDiv">
            <ul class="inputList">
                <li class="inputList-li" style="display:none;">
                   
                   <div class="listItem">
                     <label class="lbl-ipt-tit"><span class="star">*</span>是否启用：</label>
                     <label class="lbl-ipt-tit radioWrap">
                         <input type="radio" name="contacter.disable" value="true" checked />是
                     </label>
                     <label class="lbl-ipt-tit radioWrap">
                         <input type="radio" name="contacter.disable" value="false" />否
                     </label>
                   </div>
                </li>
                <li class="inputList-li">
	                <div class="listItem">
	                    <label class="lbl-ipt-tit"><span class="star">*</span>应聘职位：</label>
	                     <input type="text" class="ipt" name="contacter.applyPost" value="${contacter.applyPost}" maxlength="9"/>
	                     <span class="errortip">${errors['contacter.applyPost'][0]}</span>
                   </div>
                   
                </li>
                
                <li class="inputList-li">
	                <div class="listItem">
	                   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                   基本资料
                   </div>
                   
                </li>
                
                <li class="inputList-li">
	                <div class="listItem">
	                    <label class="lbl-ipt-tit"><span class="star">*</span>姓名：</label>
	                     <div class="select">
	                      <input type="text" class="ipt" name="contacter.contacter" value="${contacter.contacter}" />
	                      <span class="errortip">${errors['contacter.contacter'][0]}</span>
	                    </div>
                   </div>
                   
                </li>
                
                
                <li class="inputList-li">
                   
                   <div class="listItem">
                     <label class="lbl-ipt-tit"><span class="star">*</span>性别：</label>
                     <label class="lbl-ipt-tit radioWrap">
                         <input type="radio" name="contacter.gender" value="1" checked />男
                     </label>
                     <label class="lbl-ipt-tit radioWrap">
                         <input type="radio" name="contacter.gender" value="0" />女
                     </label>
                   </div>
                </li>
                <li class="inputList-li">
                   
                 <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>出生日期：</label>
                    <div class="select">
                      <input type="text" class="ipt" name="contacter.birthday" value="${contacter.birthday}" />
                    </div>
                 </div>
                </li>
                
                <li class="inputList-li">
                   
                 <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>身高：</label>
                    <div class="select">
                      <input type="text" class="ipt" name="contacter.height" value="${contacter.height}" />CM
                    </div>
                 </div>
                </li>
                
                <li class="inputList-li">
                   
                 <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>体重：</label>
                    <div class="select">
                      <input type="text" class="ipt" name="contacter.weight" value="${contacter.weight}" />KG
                    </div>
                 </div>
                </li>
                
                <li class="inputList-li">
                   
                 <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>籍贯：</label>
                    <div class="select">
                      <input type="text" class="ipt" name="contacter.hometown" value="${contacter.hometown}" />
                      <span class="errortip">${errors['contacter.hometown'][0]}</span>
                    </div>
                 </div>
                </li>
                
                 <li class="inputList-li">
                  <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>毕业学校：</label>
                    <div class="select">
                      <input type="text" class="ipt" name="contacter.schools" value="${contacter.schools}" />
                      <span class="errortip">${errors['contacter.schools'][0]}</span>
                    </div>
                 </div>
                   
                </li>
                
                <li class="inputList-li">
                   
                 <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>专业：</label>
                    <div class="select">
                      <input type="text" class="ipt" name="contacter.specialty" value="${contacter.specialty}" />
                    </div>
                 </div>
                </li>
                
                <li class="inputList-li">
                   
                 <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>最高学历：</label>
                    <div class="select">
                      <input type="text" class="ipt" name="contacter.educationalBackground" value="${contacter.educationalBackground}" />
                    </div>
                 </div>
                </li>
                
                <li class="inputList-li">
                   
                 <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>学位：</label>
                    <div class="select">
                      <input type="text" class="ipt" name="contacter.degree" value="${contacter.degree}" />
                      <span class="errortip">${errors['contacter.degree'][0]}</span>
                    </div>
                 </div>
                </li>
                
                <li class="inputList-li">
                   
                 <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>政治面貌：</label>
                    <div class="select">
                      <input type="text" class="ipt" name="contacter.politicalLandscape" value="${contacter.politicalLandscape}" />
                    </div>
                 </div>
                </li>
                
                <li class="inputList-li">
                   
                 <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>婚姻情况：</label>
                     <label class="lbl-ipt-tit radioWrap">
                         <input type="radio" name="contacter.maritalStatus" value="1" checked />已婚
                     </label>
                     <label class="lbl-ipt-tit radioWrap">
                         <input type="radio" name="contacter.maritalStatus" value="0" />未婚
                     </label>
                 </div>
                </li>
                
                <li class="inputList-li">
                   
                 <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>生育情况：</label>

                     <label class="lbl-ipt-tit radioWrap">
                         <input type="radio" name="contacter.fertility" value="1" checked />已育
                     </label>
                     <label class="lbl-ipt-tit radioWrap">
                         <input type="radio" name="contacter.fertility" value="0" />未育
                     </label>
                 </div>
                </li>
                
                <li class="inputList-li">
                   
                 <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>户口所在地：</label>
                    <div class="select">
                      <input type="text" class="ipt" name="contacter.hukou" value="${contacter.hukou}" />
                    </div>
                 </div>
                </li>
                
                <li class="inputList-li">
                   
                 <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>相关工作经验：</label>
                    <div class="select">
                      <input type="text" class="ipt" name="contacter.xWorkExperience" value="${contacter.xWorkExperience}" />
                    </div>
                 </div>
                </li>
                
                
                <li class="inputList-li">
	                <div class="listItem">
	                   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                   详细资料
                   </div>
                   
                </li>
                
                <li class="inputList-li">
                   
                 <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>教育经历：</label>
                        <textarea name="contacter.education" class="formTextarea valid">${contacter.education}</textarea>
                             <span class="errortip">${errors['contacter.education'][0]}</span>
                 </div>
                </li>
                
                <li class="inputList-li">
                   <div class="listItem txtarea">
                        <label class="lbl-ipt-tit"><span class="star">*</span>工作经历：</label>
                        <textarea name="contacter.workExperience" class="formTextarea valid">${contacter.workExperience}</textarea>
                             <span class="errortip">${errors['contacter.workExperience'][0]}</span>
                    </div>
                </li>
                
                <li class="inputList-li">
                   
                 <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>移动电话：</label>
                    <div class="select">
                      <input type="text" class="ipt" name="contacter.mobile" value="${contacter.mobile}" />
                    </div>
                 </div>
                </li>
                
                <li class="inputList-li">
                   
                 <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>联系电话：</label>
                    <div class="select">
                      <input type="text" class="ipt" name="contacter.phone" value="${contacter.phone}" />
                      <span class="errortip">${errors['contacter.phone'][0]}</span>
                    </div>
                 </div>
                </li>
                
                <li class="inputList-li">
                   
                 <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>E-mail：</label>
                    <div class="select">
                      <input type="text" class="ipt" name="contacter.email" value="${contacter.email}" />
                    </div>
                 </div>
                </li>
                
                 <li class="inputList-li">
                  <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>QQ号码：</label>
                    <div class="select">
                      <input type="text" class="ipt" name="contacter.qq" value="${contacter.qq}" />
                      <span class="errortip">${errors['contacter.qq'][0]}</span>
                    </div>
                 </div>
                   
                </li>
                
                 <li class="inputList-li">
                  <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>联系地址：</label>
                    <div class="select">
                      <input type="text" class="ipt" name="contacter.address" value="${contacter.address}" />
                      <span class="errortip">${errors['contacter.address'][0]}</span>
                    </div>
                 </div>
                </li>
                
                <li class="inputList-li">
                   
                 <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>有无计算机等级证书：</label>
                    <div class="select">
                      <input type="text" class="ipt" name="contacter.computerCertificates" value="${contacter.computerCertificates}" />
                    </div>
                 </div>
                </li>
                
                <li class="inputList-li">
                   
                 <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>有无外语等级证书：</label>
                    <div class="select">
                      <input type="text" class="ipt" name="contacter.foreignLanguageCertificates" value="${contacter.foreignLanguageCertificates}" />
                    </div>
                 </div>
                </li>
                
                <li class="inputList-li">
                   
                 <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>有无相关资格证书：</label>
                    <div class="select">
                      <input type="text" class="ipt" name="contacter.certificates" value="${contacter.certificates}" />
                    </div>
                 </div>
                </li>
                
                <li class="inputList-li">
                   
                 <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>有无相关执业证书：</label>
                    <div class="select">
                      <input type="text" class="ipt" name="contacter.practicingCertificate" value="${contacter.practicingCertificate}" />
                    </div>
                 </div>
                </li>
                
                <li class="inputList-li">
                   
                 <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>其它证书：</label>
                        <textarea name="contacter.otherCrtificates" class="formTextarea valid">${contacter.otherCrtificates}</textarea>
                             <span class="errortip">${errors['contacter.otherCrtificates'][0]}</span>
                 </div>
                </li>
                
            </ul>
           </div>
        </div>
         <div class="buttonArea">
            <input class="formButton" value="确  定" type="button" onclick="submits(this.form);">&nbsp;&nbsp;
        </div>
        </form>
      </div>
   </body>
<script type="text/javascript">  
        displayDiv('typeBtn','typeDiv','${errors}');
</script>
</html>