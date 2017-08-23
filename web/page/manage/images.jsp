<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<!doctype html>
<html lang="zh-CN">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <meta http-equiv="X-UA-Compatible" content="IE=9"/>
        <title>FTP图片列表</title>
        <jsp:include page="../../page/include/headList.jsp"></jsp:include>
    </head>
    <body class="list">
    <div class="main-top-hd clearfix">
        <h3 class="cur"><a href="javascript:void(0);">FTP图片列表</a></h3>
    </div>
    <form id="imagesForm" name="imagesForm" method="post" action="${path}/admin/images!list.action">
    <div class="main-action-bar">
        <!-- 查询、分页 -->
        <jsp:include page="../../page/include/search.jsp"></jsp:include>
    </div>
    <div class="body">
        <table id="table" class="listtable">
                    <tr>
                        <th class="check"><input id="selectAll" type="checkbox" onclick="javascript:selectAllCheckBox(this, '${pagination.rowSize}')">
                        </th>
                        <th  class="w70">序号</th>
                        <th>图片名称</th>
                        <th>站点ID</th>
                        <th>图片URL</th>
                        <th>实体名称</th>
                        <th>实体主键</th>
                        <th>是否编辑器</th>
                        <c:if test="${update or delete or show}">
                            <th  class="w150">操作</th>
                        </c:if>
                    </tr>
                    <c:forEach var="images" items="${pagination.list}" varStatus="e">
                        <tr style="text-align: center;">
                            <td>
                                <input id="select${e.index}" type="checkbox" value="${images.id}" name="ids" onclick="javascript:selected(this, '${pagination.rowSize}');"/>
                            </td>
                            <td><span>${e.index+1+pagination.rowSize*(pagination.pageNo-1)}</span></td>
                            <td><div class="Ttitle">${images.imagesName}</div></td>
                            <td>${images.siteId}</td>
                            <td>${images.imagesPath}</td>
                            <td>${images.entityName}</td>
                            <td>${images.entityId}</td>
                            <td><c:if test="${images.editor}">是</c:if><c:if test="${!images.editor}">否</c:if></td>
                            <c:if test="${delete or show}">
	                            <td>
	                                <input href="#show_image_upload${e.index}" class="preview-button" value="预览"/>
	                                <c:if test="${delete}">
	                                <a href="#" class="delete"
	                                    onclick="checkRows('imagesForm', '${path}/admin/images!delete.action', '${pagination.rowSize}',this,'此操作将删除FTP图片，\n被引用的图片将不显示!\n确认删除吗？');">删除</a>
	                                </c:if>
	                            </td>
                            </c:if>
                        </tr>
                        <div style="display:none"><div id="show_image_upload${e.index}"><img id="showImageUpload" src="${_img_ftp.ftpUrl}${images.imagesPath}"/></div></div>
                    </c:forEach>
                </table>
        <h4 align="center" style="display:${empty pagination.list ? 'block' : 'none' }">暂无数据!</h4>
        <div class=pagerBar>
        <c:if test="${delete}">
            <div class=delete>
               <input  id="deleteButton" disabled="disabled" class="formButton" value="删 除" type="button" 
               onclick="checkRow('imagesForm','${path}/admin/images!delete.action', '${pagination.rowSize}','此操作将删除FTP图片，\n被引用的图片将不显示!\n确认删除吗？');">
            </div> 
        </c:if>
        </div>
    <jsp:include page="../../page/common/pager.jsp"></jsp:include>
    </div>
    </form>
<script type="text/javascript">
$(document).ready(function(){
          $(".preview-button").colorbox({inline:true, width:"auto",hight:"auto"});
          $("#click").click(
              function(){ 
              $('#click').css({"background-color":"#aaa", "color":"#aaa", "cursor":"inherit"}).text("Open this window again and this message will still be here.");
              return false;
          });
      });
</script>
    </body>
</html>