<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.elian.cms.syst.util.SysXmlUtils"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>医联网[分站]注册</title>
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
<script type="text/javascript" src="../js/ajaxTree.js" ></script>
<script type="text/javascript" src="../js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="../js/jquery.ztree.excheck-3.5.js"></script>
</head>
<body>
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
                <h3 ><a  href="javascript:void(0);" onclick="this.href='<%=SysXmlUtils.getString("cmsSitedomain")+SysXmlUtils.getString("mainDomain")%>/front/regHosp!regHospital.action'">医院注册</a></h3>
                <h3><a  href="javascript:void(0);" onclick="this.href='<%=SysXmlUtils.getString("cmsSitedomain")+SysXmlUtils.getString("mainDomain")%>/front/regCompany!reg.action'">分站注册</a></h3>
                <h3 class="cur"><a href="javascript:void(0);">分站注册</a></h3>
            </div>
        </div>
    </div>
</div>
<div class="section">
    <div class="reg_page_hd">
        <h2 class="tit">注册页面</h2>
        <span class="login_tips">我有帐号，<a href="">登录</a></span>
    </div>
    <form name="subsReg" id="subsReg" action="../front/regSubstation!saveSubstation.action" method="post">
            <input name="site.comType" value="substation" type="hidden"/>
            <input type="hidden" name="token" value="${token}"/>
            <input type="hidden" name="user.userType.id" value="3" /> 
	    <div class="reg_page_bd">
	        <h3 class="tit">填写分站基本信息</h3>
	        <div class="i">
	            <span class="details_tit">分站名称<b class="must">*</b></span>
	            <input class="ipt" type="text" name="substation.subName" value="${substation.subName}"/>
	             <c:if test="${!empty errors['substation.subName']}">
                   <b class="reg_tips reg_error_tips" >
                     ${errors['substation.subName'][0]}
                   </b>
                </c:if>
	        </div>
	        <%--
	        <div class="i">
	            <span class="details_tit">分站简称<b class="must">*</b></span>
	            <input class="ipt" type="text" />
	            <b class="reg_tips reg_error_tips">填写错误</b>
	        </div>
	        --%>
	        <div class="i">
	            <span class="details_tit">电子邮箱</span>
	            <input class="ipt" value="${substation.email}" type="text" name="substation.email"/>
	             <c:if test="${!empty errors['substation.email']}">
                   <b class="reg_tips reg_error_tips" >
                     ${errors['substation.email'][0]}
                   </b>
                </c:if>
	        </div>
	        <div class="i">
	            <span class="details_tit">联系电话<b class="must">*</b></span>
	            <input class="ipt" value="${substation.phone}" type="text" name="substation.phone"/>
	             <c:if test="${!empty errors['substation.phone']}">
                   <b class="reg_tips reg_error_tips" >
                     ${errors['substation.phone'][0]}
                   </b>
                </c:if>
	        </div>
	        <div class="i">
	            <span class="details_tit">地址<b class="must">*</b></span>
	            <input class="ipt" value="${substation.address}" type="text" name="substation.address"/>
	             <c:if test="${!empty errors['substation.address']}">
                   <b class="reg_tips reg_error_tips" >
                     ${errors['substation.address'][0]}
                   </b>
                </c:if>
	        </div>
	        <div class="i">
	            <span class="details_tit">邮政编码<b class="must">*</b></span>
	            <input class="ipt" value="${substation.postcode}" type="text" name="substation.postcode"/>
	             <c:if test="${!empty errors['substation.postcode']}">
                   <b class="reg_tips reg_error_tips" >
                     ${errors['substation.postcode'][0]}
                   </b>
                </c:if>
	        </div>
	        <div class="i">
	            <span class="details_tit">传真号码</span>
	            <input class="ipt" value="${substation.fax}" type="text" name="substation.fax"/>
	             <c:if test="${!empty errors['substation.fax']}">
                   <b class="reg_tips reg_error_tips" >
                     ${errors['substation.fax'][0]}
                   </b>
                </c:if>
	        </div>
	        <div class="i">
	            <span class="details_tit">区域<b class="must">*</b></span>
	             <div>
                     <input id="keyId" class="ipt" type="text" readonly="readonly" name="areaName" value="${!empty areaName?areaName:'中国'}"  
                         onfocus="showMenu('../page/common/tree.jsp','areaParent'); return false;" 
                         onkeydown="BackSpace('中国','areaId','areaValueId');"  />
                     <input id="valueId" type="hidden" name="substation.areaId" value="${substation.areaId}"/>
                 </div>
                  <c:if test="${!empty errors['substation.areaId']}">
                   <b class="reg_tips reg_error_tips" >
                     ${errors['substation.areaId'][0]}
                   </b>
                </c:if>
	        </div>
	        <div class="i wrap_txtarea">
	            <span class="details_tit">简介<b class="must">*</b></span>
	            <textarea class="txtarea" name="substation.shortDesc">${substation.shortDesc}</textarea>
	            <c:if test="${!empty errors['substation.shortDesc']}">
                   <b class="reg_tips reg_error_tips" >
                     ${errors['substation.shortDesc'][0]}
                   </b>
                </c:if>
	        </div>
	        <h3 class="tit">填写用户基本信息(分站管理员)</h3>
	         <jsp:include page="regInclude.jsp"></jsp:include>
	          <div class="i wrap_submit_btn">
        <input type="button" id="submit_reg" class="submit_btn" onclick="regSubmits(this.form);"  value="提交注册"/>
     </div>
     </script>
	    </div>
    </form>
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
 <div id="treeDiv"><ul id="treeContent" class="ztree" style="width:200px;"/></ul></div>
<div class="footer">  
    <p class="copyright">CopyRight© 2000-2012 医联网 版权所有未经允许请勿转载 All Rights Reserved.</p>
    <p><a href="http://www.elian.cc">医联网</a>　粤ICP备05141209号-1  版权所有未经授权请勿转载</p>
    <p>本站信息仅供参考不能作为诊断及医疗的依据，如有转载或引用文章涉及版权问题请速与我们联系。</p>
    <p>电话：0757-82137888 传真：0757-82139888 全国电话：40001-91580</p>
</div>
</body>
</html>