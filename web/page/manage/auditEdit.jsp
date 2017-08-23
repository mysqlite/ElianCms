<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html lang="zh-CN">
    <head>
       <jsp:include page="../../page/include/headShow.jsp"></jsp:include>
    </head>
    <body class="input">
<div class=body>
     <jsp:include page="../../page/include/image_upload.jsp"></jsp:include>
    <form id="menuCOrU" name="menuCOrU" method="post" action="${path}/admin/audit!save.action">
    <input type="hidden" name="token" value="${token}"/>
    <input type="hidden" name="siteUser.id" value="${siteUser.id}"/>
    <input type="hidden" name="status" value="${status}"/>
    <input type="hidden" name="siteUser.site.comId" value="${siteUser.site.comId}"/>
     <div class="divInputTable">
       <%-- <c:if test="${site.comType eq 'company'}"></c:if>企业--%>
         <c:if test="${site.comType eq 'substation' or site.comType eq 'mainstation'}">
                    <h3 class="main-tit-bar" id="substationBtn">分站基本信息</h3>
                    <div id="substationDiv">
                        <ul class="inputList">
                            <li class="inputList-li">
                                <div class="listItem">
                                    <label class="lbl-ipt-tit">是否启用：</label>
	                                    <span class="txt">
	                                         <c:forEach var="status"  items="${statusList}" varStatus="e">
	                                           <c:if test="${substation.status eq status.key}">${status.value}</c:if>
	                                         </c:forEach>
	                                     </span>
                                </div>
                                <div class="listItem">
                                    <label class="lbl-ipt-tit">区域：</label><span class="txt">${areaName}</span>
                                </div>
                            </li>
                            <li class="inputList-li">
                                <div class="listItem">
                                    <label class="lbl-ipt-tit">分站名称：</label><span class="txt">${substation.subName}</span>
                                </div>
                                <div class="listItem">
                                    <label class="lbl-ipt-tit">分站简称：</label><span class="txt">${substation.shortName}</span>
                                </div>
                            </li>
                            <li class="inputList-li">
                                <div class="listItem">
                                    <label class="lbl-ipt-tit">联系电话：</label><span class="txt">${substation.phone}</span>
                                </div>
                                <div class="listItem">
                                    <label class="lbl-ipt-tit">地址：</label><span class="txt">${substation.address}</span>
                                </div>
                            </li>
                            <li class="inputList-li">
                                <div class="listItem">
                                    <label class="lbl-ipt-tit">邮政编码：</label><span class="txt">${substation.postcode}</span>
                                </div>
                                <div class="listItem">
                                    <label class="lbl-ipt-tit">传真号码：</label><span class="txt">${substation.fax}</span>
                                </div>
                            </li>
                            <li class="inputList-li">
                                <div class="listItem">
                                    <label class="lbl-ipt-tit">电子邮箱：</label><span class="txt">${substation.email}</span>
                                </div>
                            </li>
                            <li class="inputList-li">
                                 <div class="listItem">
                                    <label class="lbl-ipt-tit">简介：</label>
                                     <p class="lbl-p">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${substation.shortDesc}</p> 
                                </div>
                                <div class="listItem txtarea">
                                    <label class="lbl-ipt-tit">分站描述：</label>
                                    <p class="lbl-p">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${substation.subDesc}</p> 
                                </div>
                            </li>
                        </ul>
                    </div>
              <h3 class="main-tit-bar" id="remarksBtn">退回理由</h3>
              <div id="remarksDiv">
                 <ul class="inputList">
                     <li class="inputList-li">
                         <div class="listItem">
                                 <label class="lbl-ipt-tit">退回说明：</label>
                                  <textarea name="substation.remarks" class="formTextarea valid">${substation.remarks}</textarea>
                                  <span class="errortip">
				                       ${errors['substation.remarks'][0]}
				                  </span>
                          </div>
                     </li>
                 </ul>
             </div>   
             </c:if> 
       <%--分站--%>
       <c:if test="${siteUser.site.comType eq 'hospital'}">
            <h3 class="main-tit-bar" id="hospMainBtn">医院基本信息</h3>
            <div id="hospMainDiv">
            <ul class="inputList">
                <li class="inputList-li">
                   <div class="listItem">
                     <label class="lbl-ipt-tit">医院状态：</label>
                    <label class="lbl-ipt-tit radioWrap">
                        <span class="txt">
                         <c:forEach var="status"  items="${statusList}" varStatus="e">
                           <c:if test="${hospital.status eq status.key}">${status.value}</c:if>
                         </c:forEach>
                         </span>
                     </label>
                   </div>
                   <div class="listItem">
                    <label class="lbl-ipt-tit">是否医保医院：</label>
                    <span class="txt">
                    <c:if test="${hospital.health}"> 是</c:if>
                    <c:if test="${!hospital.health}">否</c:if>
                    </span>
                   </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem">
                    <label class="lbl-ipt-tit">是否挂号：</label>
                    <span class="txt">
                     <c:if test="${hospital.reg}">是</c:if>
                     <c:if test="${!hospital.reg}">否</c:if>
                     </span>
                   </div>
                   <div class="listItem">
                    <label class="lbl-ipt-tit">是否随访：</label>
                    <span class="txt">
                     <c:if test="${hospital.followup}">是</c:if>
                     <c:if test="${!hospital.followup}">否</c:if>
                     </span>
                   </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem">
                    <label class="lbl-ipt-tit">医院名称：</label><span class="txt">${hospital.hospName}</span>
                   </div>
                   <div class="listItem">
                    <label class="lbl-ipt-tit">医院简称：</label><span class="txt">${hospital.shortName}</span>
                   </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem">
                    <label class="lbl-ipt-tit">医院性质：</label>
                        <c:forEach var="nature" items="${natureList}">
                             <c:if test="${nature.id eq hospital.nature}"><span class="txt">${nature.typeName}</span></c:if>
                        </c:forEach>
                   </div>
                   <div class="listItem">
                    <label class="lbl-ipt-tit">医院类型：</label>
                        <c:forEach var="hospType" items="${hospTypeList}">
                            <c:if test="${hospType.id eq hospital.hospType}"><span class="txt">${hospType.typeName}</span></c:if>
                        </c:forEach>
                   </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem">
                    <label class="lbl-ipt-tit">医院等级：</label>
                          <c:forEach var="rank" items="${rankList}">
                            <c:if test="${rank.id eq hospital.rank}"><span class="txt">${rank.typeName}</span></c:if>
                          </c:forEach>
                   </div>
                </li>
                <li class="inputList-li">
                    <div class="listItem">
                        <label class="lbl-ipt-tit">医院地址：</label><span class="txt">${hospital.address}</span>
                   </div>
                   <div class="listItem">
                    <label class="lbl-ipt-tit">医院所在区域：</label><span class="txt">${!empty areaName?areaName:'中国'}</span>
                   </div>
                </li>
                </ul>
                </div>
                <h3 class="main-tit-bar" id="hospExtBtn">医院扩展信息</h3>
                <div id="hospExtDiv">
                <ul class="inputList">
                <li class="inputList-li">
                   <div class="listItem">
                        <label class="lbl-ipt-tit">医院电话：</label><span class="txt">${hospital.phone}</span>
                   </div>
                    <div class="listItem">
                        <label class="lbl-ipt-tit">医院传真：</label><span class="txt">${hospital.fax}</span>
                   </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem">
                        <label class="lbl-ipt-tit">医院电子邮件：</label><span class="txt">${hospital.email}</span>
                   </div>
                   <div class="listItem">
                        <label class="lbl-ipt-tit">医院网站：</label><span class="txt">${hospital.siteUrl}</span>
                   </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem">
                    <label class="lbl-ipt-tit">医院邮政编码：</label><span class="txt">${hospital.postcode}</span>
                   </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem">
                    <label class="lbl-ipt-tit">职业许可证：</label>
                    <span class="txt" id="permitImg"></span>
                   </div>
                   <div class="listItem">
                    <label class="lbl-ipt-tit">医院LOGO：</label>
                     <span class="txt" id="logoImg"></span>
                   </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem">
                    <label class="lbl-ipt-tit">医院形象图片：</label>
                     <span class="txt" id="hospImg"></span>
                   </div>
                   <div class="listItem">
                    <label class="lbl-ipt-tit">医院地图：</label>
                     <span class="txt" id="mapImg"></span>
                   </div>
                    <script type="text/javascript">
                     showImageButton("permitImg",'${hospital.permitImg}',"hospital.permitImg",1);//职业许可
                     showImageButton("logoImg",'${hospital.logoImg}',"hospital.logoImg",2);//LOGO
                     showImageButton("hospImg",'${hospital.hospImg}',"hospital.hospImg",3);//形象
                     showImageButton("mapImg",'${hospital.mapImg}',"hospital.mapImg",4);//地图
                    </script>
                </li>
                <li class="inputList-li">
                   <div class="listItem">
                    <label class="lbl-ipt-tit">医院简介：</label>
                    <p class="lbl-p">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${hospital.shortDesc}</p> 
                   </div>
                </li>
                 <li class="inputList-li">
                  <div class="listItem">
                    <label class="lbl-ipt-tit">医院BUS线路：</label>
                    <p class="lbl-p">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${hospital.busLine}</p> 
                  </div>
                  <div class="listItem">
                   <label class="lbl-ipt-tit">医院详细介绍：</label>
                       <p class="lbl-p">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${hospital.hospDesc}</p> 
                  </div>
                </li>
            </ul>
           </div>
           <h3 class="main-tit-bar" id="remarksBtn">退回理由</h3>
           <div id="remarksDiv">
              <ul class="inputList">
                  <li class="inputList-li">
                      <div class="listItem">
                              <label class="lbl-ipt-tit">退回说明：</label>
                               <textarea name="hospital.remarks" class="formTextarea valid">${hospital.remarks}</textarea>
                               <span class="errortip">
                                    <s:fielderror><s:param>hospital.remarks</s:param></s:fielderror>
                               </span>
                       </div>
                  </li>
              </ul>
          </div>
       </c:if><%--医院--%>
       <c:if test="${siteUser.site.comType eq 'company'}">
       		<div class="divInputTable">
	            <h3 class="main-tit-bar">企业基本信息</h3>
	            <div id="hospMainDiv">
	            <ul class="inputList">
	                <li class="inputList-li">
	                   <div class="listItem">
	                     <label class="lbl-ipt-tit">企业状态：</label>
	                    <label class="lbl-ipt-tit radioWrap">
	                        <span class="txt">
	                         <c:if test="${company.status==0}">未启用</c:if>
	                         <c:if test="${company.status==1}">启用</c:if>
	                         <c:if test="${company.status==2}">注销 </c:if>
	                         <c:if test="${company.status==3}"><span style="color:#202B31;">删除</span></c:if>
	                         <c:if test="${company.status==4}"><span style="color:gray;">退回</span></c:if>
	                         </span>
	                     </label>
	                   </div>
	                   <div class="listItem">
	                    <label class="lbl-ipt-tit">是否开通商城：</label>
	                    <span class="txt">
	                    <c:if test="${company.mall}"> 是</c:if>
	                    <c:if test="${!company.mall}">否</c:if>
	                    </span>
	                   </div>
	                </li>
	                <li class="inputList-li">
	                   <div class="listItem">
	                    <label class="lbl-ipt-tit">企业名称：</label><span class="txt">${company.name}</span>
	                   </div>
	                   <div class="listItem">
	                    <label class="lbl-ipt-tit">企业简称：</label><span class="txt">${company.shortName}</span>
	                   </div>
	                </li><%--
	                <li class="inputList-li">
	                   <div class="listItem">
	                    <label class="lbl-ipt-tit">医院性质：</label>
	                        <c:forEach var="nature" items="${natureList}">
	                             <c:if test="${nature.id eq company.nature}"><span class="txt">${nature.typeName}</span></c:if>
	                        </c:forEach>
	                   </div>
	                   <div class="listItem">
	                    <label class="lbl-ipt-tit">医院类型：</label>
	                        <c:forEach var="hospType" items="${hospTypeList}">
	                            <c:if test="${hospType.id eq company.hospType}"><span class="txt">${hospType.typeName}</span></c:if>
	                        </c:forEach>
	                   </div>
	                </li>
	                --%>
	                <li class="inputList-li">
	                    <div class="listItem">
	                        <label class="lbl-ipt-tit">企业地址：</label><span class="txt">${company.address}</span>
	                   </div>
	                   <div class="listItem">
	                    <label class="lbl-ipt-tit">企业所在区域：</label><span class="txt">${!empty company.area.areaName?company.area.areaName:'中国'}</span>
	                   </div>
	                </li>
	                </ul>
	                </div>
	                <h3 class="main-tit-bar" id="companyExtBtn">企业扩展信息</h3>
	                <div id="hospExtDiv">
	                <ul class="inputList">
	                <li class="inputList-li">
	                   <div class="listItem">
	                        <label class="lbl-ipt-tit">企业电话：</label><span class="txt">${company.phone}</span>
	                   </div>
	                    <div class="listItem">
	                        <label class="lbl-ipt-tit">企业传真：</label><span class="txt">${company.fax}</span>
	                   </div>
	                </li>
	                <li class="inputList-li">
	                   <div class="listItem">
	                        <label class="lbl-ipt-tit">企业电子邮件：</label><span class="txt">${company.email}</span>
	                   </div>
	                   <div class="listItem">
	                        <label class="lbl-ipt-tit">企业网站：</label><span class="txt">${company.siteUrl}</span>
	                   </div>
	                </li>
	                <li class="inputList-li">
	                   <div class="listItem">
	                    <label class="lbl-ipt-tit">企业邮政编码：</label><span class="txt">${company.postcode}</span>
	                   </div>
	                </li>
	                <li class="inputList-li">
	                   <div class="listItem">
	                    <label class="lbl-ipt-tit">职业许可证：</label>
	                    <span class="txt" id="CompanyPermitImg"></span>
	                   </div>
	                   <div class="listItem">
	                    <label class="lbl-ipt-tit">企业LOGO：</label>
	                     <span class="txt" id="CompanyLogoImg"></span>
	                   </div>
	                </li>
	                <li class="inputList-li">
	                   <div class="listItem">
	                    <label class="lbl-ipt-tit">企业形象图片：</label>
	                     <span class="txt" id="companyImg"></span>
	                   </div>
	                   <div class="listItem">
	                    <label class="lbl-ipt-tit">企业地图：</label>
	                     <span class="txt" id="companyMapImg"></span>
	                   </div>
	                    <script type="text/javascript">
	                     showImageButton("CompanyPermitImg",'${company.permitImg}',"company.permitImg",1);//职业许可
	                     showImageButton("CompanyLogoImg",'${company.logoImg}',"company.logoImg",2);//LOGO
	                     showImageButton("companyImg",'${company.companyImg}',"company.companyImg",3);//形象
	                     showImageButton("companyMapImg",'${company.mapImg}',"company.mapImg",4);//地图
	                    </script>
	                </li>
	                <li class="inputList-li">
	                   <div class="listItem">
	                    <label class="lbl-ipt-tit">企业简介：</label>
	                    <p class="lbl-p">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${company.shortIntroduce}</p> 
	                   </div>
	                </li>
	                 <li class="inputList-li">
	                  <div class="listItem">
	                    <label class="lbl-ipt-tit">企业BUS线路：</label>
	                    <p class="lbl-p">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${company.busLine}</p> 
	                  </div>
	                  <div class="listItem">
	                   <label class="lbl-ipt-tit">企业详细介绍：</label>
	                       <p class="lbl-p">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${company.introduce}</p> 
	                  </div>
	                </li>
	            </ul>
	           </div>
                <h3 class="main-tit-bar" id="remarksBtn">退回理由</h3>
	           <div id="remarksDiv">
	              <ul class="inputList">
	                  <li class="inputList-li">
	                      <div class="listItem">
	                              <label class="lbl-ipt-tit">退回说明：</label>
	                               <textarea name="company.remarks" class="formTextarea valid">${company.remarks}</textarea>
	                               <span class="errortip">${errors['company.remarks'][0]}</span>
	                       </div>
	                  </li>
	              </ul>
		        </div>
		    </div>
       </c:if><%--企业--%>
       <div class="buttonArea">
         <input class="formButton" value="确  定" type="button" onclick="submits(this.form);">&nbsp;&nbsp;
             <c:if test="${status eq 'notAudit'}">
            <input class="formButton" onclick="window.location='${path}/admin/audit!list.action?status=notAudit'" value="返  回" type="button">
            </c:if>
            <c:if test="${status eq 'audit'}">
            <input class="formButton" onclick="window.location='${path}/admin/audit!list.action?status=audit'" value="返  回" type="button">
            </c:if>
            <c:if test="${status eq 'editAudit'}">
            <input class="formButton" onclick="window.location='${path}/admin/audit!list.action?status=exitAudit'" value="返  回" type="button">
            </c:if>
        </div>
        </div>
         </form>
         </div>
   </body>
<script type="text/javascript">  
        if('${site.comType}'=="substation"|'${site.comType}'=="mainstation"){
        displayDiv('substationBtn','substationDiv','${errors}');
        }
        if('${site.comType}'=="hospital"){
        displayDiv('hospExtBtn','hospExtDiv','${errors}');
        displayDiv('hospMainBtn','hospMainDiv','${errors}');
        }
        displayDiv('remarksBtn','remarksDiv','${errors}');
</script>
</html>