<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.elian.cms.syst.util.SysXmlUtils"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>医联网[医院]注册</title>
<meta name="Keywords" content="医联网注册页" /> 
<meta name="Description" content="医联网注册页" />

<link rel="shortcut icon" type="image/x-icon"  href="<%=SysXmlUtils.getString("siteImgIcon")%>"/>
<liNK rel="stylesheet" href="../css/manage/colorbox.css" type=text/css>
<link rel="stylesheet" href='../css/manage/jquery-webox.css' type="text/css">
<link rel="stylesheet" href="../css/zTreeStyle/zTreeStyle.css" type="text/css">
<link rel="stylesheet" href="../css/manage/selectMenu.css" type="text/css">
<link rel="stylesheet" href="../css/front/style/reg/sub.css" type="text/css" media="screen" />
<script type='text/javascript' src='../js/manage/editCommon.js'></script>
<script type="text/javascript" src="../js/jquery.js"></script>
<script src='../js/manage/jquery-webox.js'></script>
<script src="../js/manage/jquery.colorbox.js"></script>
<script type="text/javascript" src="../js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="../js/areaTree.js" ></script>

</head>
<body>
    <jsp:include page="../../page/include/image_upload.jsp"></jsp:include>
	<form name="hospReg" id="hospReg" action="../front/regHosp!saveHospatil.action" method="post">
		<input type="hidden" name="user.userType.id" value="2" />   
		<div class="header">
		    <h1><a href="<%=SysXmlUtils.getString("mainSitedomain")+SysXmlUtils.getString("mainDomain")%>">
		        <img src="<%=SysXmlUtils.getString("adminLoginlogoImg")%>" width="182" height="100" alt="医联网注册用户"/>
		        医联网注册用户</a>
		    </h1>
		</div>
		<div class="section">
		    <div class="reg_nav">
		        <div class="reg_nav_bar">
		            <div class="wrap_tit">
		                <h3 class="cur"><a href="#">医院注册</a></h3>
		                <h3><a  href="javascript:void(0);" onclick="this.href='<%=SysXmlUtils.getString("cmsSitedomain")+SysXmlUtils.getString("mainDomain")%>/front/regCompany!reg.action'">企业注册</a></h3>
		                <h3><a  href="javascript:void(0);" onclick="this.href='<%=SysXmlUtils.getString("cmsSitedomain")+SysXmlUtils.getString("mainDomain")%>/front/regSubstation!regSubstation.action'">分站注册</a></h3>
		            </div>
		        </div>
		    </div>
		</div>
		<div class="section">
		    <div class="reg_page_hd">
		        <h2 class="tit">注册页面</h2>
		        <span class="login_tips">我有帐号，<a href="">登录</a></span>
		    </div>
		    <div class="reg_page_bd">
		        <h3 class="tit">填写医院基本信息</h3>
		        <div class="i">
		            <span class="details_tit">医院名称<b class="must">*</b></span>
		            <input class="ipt" type="text" name="hospital.hospName" value="${hospital.hospName}"/>
		            <c:if test="${!empty errors['hospital.hospName']}">
			            <b class="reg_tips reg_error_tips" >
			              ${errors['hospital.hospName'][0]}
			            </b>
		            </c:if>
		        </div>
		        <%--
		        <div class="i">
		            <span class="details_tit">医院简称<b class="must">*</b></span>
		            <input class="ipt" type="text"/>
		            <b class="reg_tips reg_ok_tips">填写正确</b>
		            <b class="reg_tips reg_error_tips">填写错误</b>
		        </div>
		        --%>
		        <div class="i">
		            <span class="details_tit">医院性质<b class="must">*</b></span>
		            <select class="sel" name="hospital.nature">
		                <c:forEach var="nature" items="${natureList}">
		                   <option value="${nature.id}" <c:if test="${nature.id eq hospital.nature}">selected="selected"</c:if>>${nature.typeName}</option>
		                 </c:forEach>
		            </select>
		            <c:if test="${!empty errors['hospital.nature']}">
                        <b class="reg_tips reg_error_tips" >
                          ${errors['hospital.nature'][0]}
                        </b>
                    </c:if>
		        </div>
		        <div class="i">
		            <span class="details_tit">医院类型<b class="must">*</b></span>
		            <select class="sel" name="hospital.hospType">
		                <c:forEach var="hospType" items="${hospTypeList}">
                            <option value="${hospType.id}" <c:if test="${hospType.id eq hospital.hospType}">selected="selected"</c:if>>${hospType.typeName}</option>
                         </c:forEach>
		            </select>
		            <c:if test="${!empty errors['hospital.hospType']}">
                        <b class="reg_tips reg_error_tips" >
                          ${errors['hospital.hospType'][0]}
                        </b>
                    </c:if>
		        </div>
		        <div class="i">
		            <span class="details_tit">医院等级<b class="must">*</b></span>
		            <select class="sel" name="hospital.rank">
		                <c:forEach var="rank" items="${rankList}">
                           <option value="${rank.id}" <c:if test="${rank.id eq hospital.rank}">selected="selected"</c:if>>${rank.typeName}</option>
                        </c:forEach>
		            </select>
		            <c:if test="${!empty errors['hospital.rank']}">
                        <b class="reg_tips reg_error_tips">
                          ${errors['hospital.rank'][0]}
                        </b>
                    </c:if>
		        </div>
		        <div class="i">
		            <span class="details_tit">医院电话<b class="must">*</b></span>
		            <input class="ipt" type="text" name="hospital.phone" value="${hospital.phone}"/>
		            <c:if test="${!empty errors['hospital.phone']}">
                        <b class="reg_tips reg_error_tips" >
                          ${errors['hospital.phone'][0]}
                        </b>
                    </c:if>
		        </div>
		        <div class="i">
		            <span class="details_tit">医院地址<b class="must">*</b></span>
		            <input class="ipt" type="text" name="hospital.address" value="${hospital.address}">
		            <c:if test="${!empty errors['hospital.address']}">
                        <b class="reg_tips reg_error_tips" >
                          ${errors['hospital.address'][0]}
                        </b>
                    </c:if>
		        </div>
		        <div class="i area_select clearfix">
		            <span class="details_tit">医院所在区域<b class="must">*</b></span>
		           <div>
                          <input id="areaId" class="ipt" name="areaName" type="text" readonly="true" value="${!empty areaName?areaName:'中国'}" 
                           onfocus="showMenuArea('treeContent','areaId','areaValueId','${path}');"
                           onkeydown="BackSpace('中国','areaId','areaValueId');" />
                          <input id="areaValueId" type="hidden" name="hospital.areaId" value="${!empty hospital.areaId?hospital.areaId:0}"/>
                     </div> 
                     <c:if test="${!empty errors['hospital.areaId']}">
                        <b class="reg_tips reg_error_tips">
                          ${errors['hospital.areaId'][0]}
                        </b>
                    </c:if>
		        </div>
		        <div class="i">
		            <span class="details_tit">职业许可证</span>
		            <span id="permitImg"></span>
		            <c:if test="${!empty errors['hospital.permitImg']}">
                        <b class="reg_tips reg_error_tips" >
                          ${errors['hospital.permitImg'][0]}
                        </b>
                    </c:if>
		        </div>
		        <script type="text/javascript">
		              loadingImageButton("permitImg",'${hospital.permitImg}',"hospital.permitImg",false,1,"hospital");//职业许可
		        </script>
		        <h3 class="tit">填写用户基本信息(医院管理员)</h3>
		          <jsp:include page="regInclude.jsp"></jsp:include>
		           <div class="i wrap_submit_btn">
        <input type="button" id="submit_reg" class="submit_btn" onclick="regSubmits(this.form);"  value="提交注册"/>
     </div>
     <script type="text/javascript">
     function regSubmits(formsa){
         var checkService=G("serviceTerms").checked;
         if(!checkService){
             alert("请选择同意《医联网平台服务条款》");
             return false;
         }else{
           submits(formsa);
         }
     }
     </script>
		    </div>
		</div>
		<div id="treeDiv" class="menuContent"><ul id="treeContent" class="ztree" style="width:220px;height:300px;"></ul></div>
		<div class="footer">  
		    <p class="copyright">CopyRight© 2000-2012 医联网 版权所有未经允许请勿转载 All Rights Reserved.</p>
		    <p><a href="http://www.elian.cc">医联网</a>　粤ICP备05141209号-1  版权所有未经授权请勿转载</p>
		    <p>本站信息仅供参考不能作为诊断及医疗的依据，如有转载或引用文章涉及版权问题请速与我们联系。</p>
		    <p>电话：0757-82137888 传真：0757-82139888 全国电话：40001-91580</p>
		</div>
	</form>
</body>
</html>