<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML>
<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<html lang="zh-CN">
	<head>
		<title>站点头尾部编辑</title>
        <jsp:include page="../../page/include/headEdit.jsp"></jsp:include>       
	</head>
	<body class="input">
    <div class="main-top-hd clearfix">
        <h3 class="cur">站点头尾部编辑</h3>
    </div>
<div class=body>
    <form id="areaCOrU" name="areaCOrU" method="post" action="${path}/admin/siteHeadAndFoot!save.action">        
        <input type="hidden" name="siteInclude.id" value="${siteInclude.id}">        
        <input type="hidden" name="siteInclude.fileName" value="${siteInclude.fileName}">
        <input type="hidden" name="siteInclude.version" value="${siteInclude.version}">
        <input type="hidden" name="token" value="${token}"/>
        <div class="divInputTable">
            <h3 class="main-tit-bar" id="typeBtn">信息编辑</h3>
            <div id="typeDiv">
            <ul class="inputList">                            
                <li class="inputList-li">
                  <div class="listItem txtarea">
                     <label class="lbl-ipt-tit"><span class="star">*</span>文件内容：</label>
                     <textarea name="siteInclude.content" class="formTextediter">${siteInclude.content}</textarea>
                     <span class="errortip" name="errortip">
                        <s:fielderror ><s:param>siteInclude.content</s:param></s:fielderror>
                     </span>
                   </div>                   
                </li>   
                <li class="inputList-li">
	               <input class="formButton" value="保存" type="button" onclick="this.form.submit()">&nbsp;&nbsp;
	               <!-- <input class="formButton" value="发布" type="button" onclick="staticIncludeFile(${siteInclude.id})">&nbsp;&nbsp; -->
	               <input class="formButton" value="返回" type="button" onclick="window.location='${path}/admin/siteHeadAndFoot!list.action'">&nbsp;&nbsp;
                </li>
            </ul>
           </div>
        </div>
       </form>
      </div>
       <script type="text/javascript">
        	function staticIncludeFile(includeFileId){
        		$.post('${path}/admin/static!staticIncludeFile.action',{siteIncludeId:includeFileId},function(data){
        			alert(data.MSG);
        		},"json")
        	}
        </script>
   </body> 
</html>