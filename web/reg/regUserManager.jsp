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
<div class="section">
    <div class="usr_manage_bd">
         <jsp:include page="include/reg_user_manager_top.jsp"></jsp:include>
        <div class="mob_ui_box">
            <div class="tab12_hd clearfix" id="tab12_hd">
                <h4 class="cur"><a href="${path}/reg/regUserManager!myData.action">我的资料</a></h4>
                <h4><a href="${path}/reg/regUserPwd!edit.action">修改密码</a></h4>
            </div>
            <div class="ui_bd usr_message_list">
                <div class="tab12_bd" id="tab12_bd">
                    <form name="reg" id="logins" action="${path}/reg/regUserManager!save.action" method="post">
                    <input type="hidden" name="user.account" value="${user.account}"/>
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
                                <label class="tit">用户名：</label>
                                <span class="txt">
                                    <span id="lblAccount">${user.account}</span>
                                </span>
                                <c:if test="${!empty errors['user.account']}">
					              <span class="reg_tips reg_error_tips">
					                 ${errors['user.account'][0]}
					              </span>
					            </c:if>
                            </li>
                            <li>
                                <label class="tit">真实姓名：</label>
                                <span class="txt">
                                    <input name="user.realName" type="text" value="${user.realName}"/>
                                </span>
                                <c:if test="${!empty errors['user.realName']}">
                                  <span class="reg_tips reg_error_tips">
                                     ${errors['user.realName'][0]}
                                  </span>
                                </c:if>
                            </li>
                            <li>
                                <label class="tit">性　别：</label>
                                <span id="rbSex">
	                                <c:forEach var="sex" items="${sexList}" varStatus="e">
	                                    <input  type="radio" name="user.gender" value="${sex.key}" 
	                                      <c:if test="${empty user.gender or user.gender eq sex.key or user.gender eq 'secret'}">checked="checked"</c:if>/>  
	                                    <label for="rbSex_0">${sex.value}</label>
	                                </c:forEach>
                                </span>
                                 <c:if test="${!empty errors['user.gender']}">
                                  <span class="reg_tips reg_error_tips">
                                     ${errors['user.gender'][0]}
                                  </span>
                                </c:if>
                            </li>
                            <li>
                                <label class="tit">生　日：</label>
                                <input name="user.birthday" type="text" value='<fmt:formatDate value="${user.birthday}" pattern="yyyy-MM-dd"/>' onClick="WdatePicker({dateFmt:'yyyy-MM-dd',startDate:'1985-01-01'})"  class="data_ipt"/>
                                 <c:if test="${!empty errors['user.birthday']}">
                                  <span class="reg_tips reg_error_tips">
                                     ${errors['user.birthday'][0]}
                                  </span>
                                </c:if>
                            </li>
                            <li>
                                <label class="tit">邮　箱：</label>
                                <span class="txt">
                                    <input name="user.email" type="text" value="${user.email}" />
                                </span>
                                 <c:if test="${!empty errors['user.email']}">
                                  <span class="reg_tips reg_error_tips">
                                     ${errors['user.email'][0]}
                                  </span>
                                </c:if>
                            </li>
                            <li>
                                <label class="tit">手　机：</label>
                                <span class="txt">
                                    <input name="user.mobile" type="text" value="${user.mobile}"/>
                                </span> 
                                 <c:if test="${!empty errors['user.mobile']}">
                                  <span class="reg_tips reg_error_tips">
                                     ${errors['user.mobile'][0]}
                                  </span>
                                </c:if>
                            </li>
                            <li>
                                <label class="tit">身份证：</label>
                                <span class="txt">
                                    <input name="user.identityCard" type="text" value="${user.identityCard}  "/>
                                </span>
                                 <c:if test="${!empty errors['user.identityCard']}">
                                  <span class="reg_tips reg_error_tips">
                                     ${errors['user.identityCard'][0]}
                                  </span>
                                </c:if>
                            </li>
                            <li>
                                <label class="tit">健康卡：</label>
                                <span class="txt">
                                    <input name="user.medicalCard" type="text" value="${user.medicalCard}"/>
                                </span>
                                 <c:if test="${!empty errors['user.medicalCard']}">
                                  <span class="reg_tips reg_error_tips">
                                     ${errors['user.medicalCard'][0]}
                                  </span>
                                </c:if>
                            </li>
                            <li>
                                <label class="tit">地　区：</label>
                                <div class="details clearfix" id="jq_zone_select">
                                    <div class="wrap_zone">
                                        <input id="pro_key" type="hidden" name="user.comefrom" value="${user.comefrom}"/>
                                        <input id="pro_code" type="hidden" onchange="changePro();"/>
                                        <div class="zone_hd" id="province_div">
                                        <c:if test="${!empty areaName[2]}">
                                          <span class="txt">${areaName[2]}</span><i class="ico"></i>
                                        </c:if>
                                        <c:if test="${empty areaName[2]}">
                                           <span class="txt">省/直辖市</span><i class="ico"></i>
                                        </c:if>
                                        </div>
                                        <div class="zone_bd" id="province_data"></div>
                                    </div>
                                    <div class="wrap_zone">
                                        <input id="city_code" type="hidden"/>
                                        <div class="zone_hd" id="city_div">
                                         <c:if test="${!empty areaName[1]}">
                                          <span class="txt">${areaName[1]}</span>
                                        </c:if>
                                        <c:if test="${empty areaName[1]}">
                                           <span class="txt">地州/市</span>
                                        </c:if>
                                        <i class="ico"></i></div>
                                        <div class="zone_bd" id="city_data"></div>
                                    </div>
                                    <div class="wrap_zone">
                                        <input id="county_code" type="hidden"/>
                                        <div class="zone_hd" id="county_div">
                                        <c:if test="${!empty areaName[0]}">
                                          <span class="txt">${areaName[0]}</span>
                                        </c:if>
                                        <c:if test="${empty areaName[0]}">
                                           <span class="txt">区/县</span>
                                        </c:if>
                                        <i class="ico"></i></div>
                                        <div class="zone_bd" id="county_data"></div>
                                    </div>
                                </div>
                                 <c:if test="${!empty errors['user.comefrom']}">
                                  <span class="reg_tips reg_error_tips">
                                     ${errors['user.comefrom'][0]}
                                  </span>
                                </c:if>
                            </li>
                        </ul>
                        <div class="edit_btn">
                            <input type="submit" class="save_message_btn" value="修改资料"/>
                        </div>
                    </div>
                    </form>
                </div>
                <b class="left_border"></b>
                <b class="right_border"></b>
            </div>
        </div>
    </div>
     <jsp:include page="include/reg_user_manager_center.jsp"></jsp:include>
</div>
<script src="http://style.elian.cc/main/reg/script/area-select.js"></script>
<script type="text/javascript">
var pro_html="";//省
var pro_code="";
var city_html="";//市
var city_code="";
var county_html="";//县
var county_code="";
function setPro(code){G("pro_key").value=code;G("pro_code").value=code;if(pro_code!=GV("pro_code")){changePro();}}
function setCity(code){G("pro_key").value=code;G("city_code").value=code;if(city_code!=GV("city_code")){changeCity();}}
function setCounty(code){G("pro_key").value=code;G("county_code").value=code;}
function changePro(){
	G("city_data").innerHTML="";
    G("city_div").innerHTML="<span class='txt'>地州/市</span><i class='ico'></i>";
    G("county_data").innerHTML="";
    G("county_div").innerHTML="<span class='txt'>县/市</span><i class='ico'></i>";
}
function changeCity(){
     G("county_data").innerHTML="";
     G("county_div").innerHTML="<span class='txt'>县/市</span><i class='ico'></i>";
}
G("province_div").onclick=function(){
    if(pro_html==""){
        $.post('${path}/admin/area!areapro.action',{"id":0},function(pro_node){
            for(var i=0,len=pro_node.length;i<len;i++){
                pro_html+='<a href="javascript:void(0);" onClick="setPro('+pro_node[i].id+');">'+pro_node[i].name+'</a>';
            }
            G("province_data").innerHTML=pro_html;
        },"json");
    }
};  
G("city_div").onclick=function(){
    if(pro_code!=GV("pro_code")&&GV("pro_code")!=""){city_html="";
        $.post('${path}/admin/area!areapro.action',{"id":GV("pro_code")},function(pro_node){
            for(var i=0,len=pro_node.length;i<len;i++){
                city_html+='<a href="javascript:void(0);" onClick="setCity('+pro_node[i].id+');">'+pro_node[i].name+'</a>';
            }
            G("city_data").innerHTML=city_html;pro_code=GV("pro_code");
        },"json");
    }
    
};
G("county_div").onclick=function(){
    if(city_code!=GV("city_code")&&GV("city_code")!=""){county_html="";
        $.post('${path}/admin/area!areapro.action',{"id":GV("city_code")},function(pro_node){
            for(var i=0,len=pro_node.length;i<len;i++){
                county_html+='<a href="javascript:void(0);" onClick="setCounty('+pro_node[i].id+');">'+pro_node[i].name+'</a>';
            }
            G("county_data").innerHTML=county_html;city_code=GV("city_code");
        },"json");
    }
};
 </script>
<jsp:include page="include/footer.jsp"></jsp:include>
<script src="http://script.elian.cc/main/reg/script/base.js"></script>