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
        <jsp:include page="../../page/include/headEdit.jsp"></jsp:include>
    </head>
    <body class="input">
    <div class="main-top-hd clearfix">
        <h3 class="cur">
       <c:if test="${!edit}">FTP添加</c:if>
       <c:if test="${edit}">FTP修改</c:if>
        </h3>
    </div>
<div class=body>
    <form id="ftpCOrU" name="ftpCOrU" method="post" action="${path}/admin/ftp!save.action">
        <input type="hidden" name="ftp.id" value="${ftp.id}">
        <input type="hidden" name="ftp.version" value="${ftp.version}">
        <input type="hidden" name="edit" value="${edit}">
        <input type="hidden" name="token" value="${token}"/>
        <div class="divInputTable">
            <h3 class="main-tit-bar" id="typeBtn">基本信息</h3>
            <div id="typeDiv">
            <ul class="inputList">
                <li class="inputList-li">
                   <div class="listItem">
                     <label class="lbl-ipt-tit"><span class="star">*</span>是否默认：</label>
                     <label class="lbl-ipt-tit radioWrap">
                         <input type="radio" name="ftp.default" value="true"<c:if test="${ftp.default}"> checked</c:if> />是
                     </label>
                     <label class="lbl-ipt-tit radioWrap">
                         <input type="radio" name="ftp.default" value="false"<c:if test="${!ftp.default}">checked</c:if> />否
                     </label>
                   </div>
                   <div class="listItem">
                        <label class="lbl-ipt-tit"><span class="star">*</span>FTP类型：</label>
                        <div class="select">
                             <select name="ftp.type.id">
                                <c:forEach var="item" items="${ftpTypeList}">
                                <option value="${item.id}" <c:if test="${item.id eq ftp.type.id}">selected="selected"</c:if>>${item.typeName}</option>
                                </c:forEach>
                             </select>
                        </div>
                        <span class="errortip">${errors['ftp.type.id'][0]}</span>
                  </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem">
                     <label class="lbl-ipt-tit"><span class="star">*</span>是否启用：</label>
                     <label class="lbl-ipt-tit radioWrap">
                         <input type="radio" name="ftp.disable" value="true"<c:if test="${ftp.disable}"> checked</c:if> />是
                     </label>
                     <label class="lbl-ipt-tit radioWrap">
                         <input type="radio" name="ftp.disable" value="false"<c:if test="${!ftp.disable}">checked</c:if> />否
                     </label>
                   </div>
                   <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>排序：</label>
                    <input type="text" class="ipt" name="ftp.ftpSort" value="${ftp.ftpSort}" maxlength="5"/>
                    <span class="errortip">${errors['ftp.ftpSort'][0]}</span>
                   </div>
                </li>
                <li class="inputList-li">
                  <div class="listItem">
                    <label class="lbl-ipt-tit"><span class="star">*</span>FTP名称：</label>
                    <div class="select">
                      <input type="text" class="ipt" name="ftp.ftpName" value="${ftp.ftpName}"  maxlength="20" />
                      <span class="errortip">${errors['ftp.ftpName'][0]}</span>
                    </div>
                  </div>
                  <div class="listItem txtarea">
                        <label class="lbl-ipt-tit"><span class="star">*</span>编码：</label>
                       <input type="text" class="ipt" name="ftp.encoding" value="${ftp.encoding}"  maxlength="20" />
                         <span class="errortip">${errors['ftp.encoding'][0]}</span>
                    </div>                  
                </li>
                <li class="inputList-li">
                   <div class="listItem">
                      <label class="lbl-ipt-tit"><span class="star">*</span>服务器地址：</label>
                      <input type="text" class="ipt" name="ftp.serverIp" value="${ftp.serverIp}"  maxlength="50" />
                      <span class="errortip">${errors['ftp.serverIp'][0]}</span>
                   </div>
                   <div class="listItem">
                      <label class="lbl-ipt-tit">
                          <span class="star">*</span>FTP端口：
                      </label>
                     <input type="text" class="ipt" name="ftp.ftpPort" value="${ftp.ftpPort}"  maxlength="10" />
                      <span class="errortip">${errors['ftp.ftpPort'][0]}</span>
                    </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem txtarea">
                        <label class="lbl-ipt-tit"><span class="star">*</span>登陆账号：</label>
                        <input type="text" class="ipt" name="ftp.account" value="${ftp.account}"  maxlength="100" />
                         <span class="errortip">${errors['ftp.account'][0]}</span>
                    </div>
                    <div class="listItem txtarea">
                        <label class="lbl-ipt-tit"><span class="star">*</span>登陆密码：</label>
                        <input type="password" class="ipt" name="ftp.password" value="" maxlength="100" />
                        <span class="txt">不修改请留空</span>
                         <span class="errortip">${errors['ftp.password'][0]}</span>
                    </div>
                </li>
                <li class="inputList-li">
                   <div class="listItem txtarea">
                        <label class="lbl-ipt-tit"><span class="star">*</span>FTP路径：</label>
                        <input type="text" class="ipt" name="ftp.ftpPath" value="${ftp.ftpPath}"  maxlength="255" />
                         <span class="errortip">${errors['ftp.ftpPath'][0]}</span>
                    </div>
                    <div class="listItem txtarea">
                        <label class="lbl-ipt-tit"><span class="star">*</span>访问地址：</label>
                        <input type="text" class="ipt" name="ftp.ftpUrl" value="${ftp.ftpUrl}"  maxlength="255" />
                         <span class="errortip">${errors['ftp.ftpUrl'][0]}</span>
                    </div>
                </li>
                <li class="inputList-li">
                  <div class="listItem txtarea">
                       <label class="lbl-ipt-tit"><span class="star">*</span>超时时间：</label>
                       <input type="text" class="ipt" name="ftp.timeout" value="${ftp.timeout}"  maxlength="20" />
                        <span class="errortip">${errors['ftp.timeout'][0]}</span>
                   </div>
                </li>
            </ul>
           </div>
        </div>
         <div class="buttonArea">
            <input class="formButton" value="确  定" type="button" onclick="submits(this.form);">&nbsp;&nbsp;
            <input class="formButton" onclick="window.location='${path}/admin/ftp!list.action'" value="返  回" type="button">
        </div>
        </form>
      </div>
   </body>
<script type="text/javascript">  
        displayDiv('typeBtn','typeDiv','${errors}');
</script>
</html>