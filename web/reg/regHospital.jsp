<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="include/sub_header.jsp"%> 
<%@ include file="include/nav.jsp"%> 
<div class="breadcrumbs">
    <div class="bd">
        <a href="#">首页</a> &gt; <a href="#">广东</a> &gt; 
        <a href="#">佛山</a> &gt; <span class="cur">广东省人民医院</span>
    </div>
</div>
<div class="gutter"></div>
<div class="hos-banner">
    <div class="i">
        <h1>${hospital.hospName}</h1><span class="sub_tit">${hospRank.typeName}</span>
        <c:if test="${hospital.reg}">
            <span class="ico on">已开通挂号</span>
        </c:if>
        <c:if test="${!hospital.reg}">
            <span class="ico off">未开通挂号</span>
        </c:if>
    </div>
</div>
<div class="section">
    <div class="hos-list-more">
        <div class="mod_hd01">
            <h3 class="ui_hd"><b class="ui_bg"></b>网上预约挂号</h3>
        </div>
        <div class="mod_bd01">
            <div class="hos-details">
                <a class="pic" href="#"><img src=
                	<c:if test="${!fn:startsWith(hospital.hospImg,'http://')}">"${_img_ftp.ftpUrl}${hospital.hospImg}"</c:if> 
	            	<c:if test="${fn:startsWith(hospital.hospImg,'http://')}">"${hospital.hospImg}"</c:if> 
                width="132" height="96"/></a>
                <ul class="details">
                    <li>名称：${hospital.hospName}</li><li>地址：${hospital.address }</li>
                    <li>性质：${hospType.typeName}</li><!-- <li>特色专科：妇科医院</li> -->
                    <li>等级：${hospRank.typeName}</li><li>联系电话：${hospital.phone }</li>
                </ul>
            </div>
        </div>
    </div>
    <div class="hos-details-keshi clearfix">
        <div class="w730">
            <table cellspacing="0" cellpadding="0" class="tbl">
            	<tbody>
            		<c:forEach var="item" items="${depaMap}">
	            		<tr>
	            			<td class="tit">${item.key.typeName }</td>
	                        <td>
	                            <div class="details">
	                            	<c:forEach var="subItem" items="${item.value}">
		                                <a href="regDoctor!list.action?hospId=${hospital.id}&deptId=${subItem.key.id}">${subItem.key.deptName}(${subItem.value}人)</a>
	                            	</c:forEach>
	                            </div>
	                        </td>
	            		</tr>
            		</c:forEach>
            	</tbody>
            </table>
        </div>
        <div class="w240">
            <div class="aside">
                <div class="mod_hd03">
                    <h3 class="tit"><span class="search-ico">操作流程</span></h3>
                    <a href="#" class="more">&gt;&gt;更多</a>
                </div>
                <div class="mod_bd03">
                    <ul class="list-with-num">
                        <li><div class="txt">·<a href="#">智能挂号操作说明</a></div></li>
                        <li><div class="txt">·<a href="#">常规挂号</a></div></li>
                    </ul>
                </div>
            </div>
            <div class="gutter"></div>
            <div class="aside">
                <div class="mod_hd03">
                    <h3 class="tit"><span class="search-ico">常见问题</span></h3>
                    <a href="#" class="more">&gt;&gt;更多</a>
                </div>
                <div class="mod_bd03">
                    <ul class="list-with-num">
                        <li><div class="txt">·<a href="#">怎样注册成为191580(就医我帮您)的用户</a></div></li>
                        <li><div class="txt">·<a href="#">注册为什么要留下身份证号码等个人信息</a></div></li>
                        <li><div class="txt">·<a href="#">如何查询挂号情况</a></div></li>
                        <li><div class="txt">·<a href="#">挂号费用如何收取</a></div></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="include/footer.jsp"%>
<script src="http://style.elian.cc/main/reg/script/base.js"></script>
