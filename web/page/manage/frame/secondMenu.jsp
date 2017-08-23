<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<style>
*{margin:0;padding:0}
</style>
	<frameset id=subFrameset frameSpacing=0 border=0 cols=220,6,* frameBorder=no>
        <frame id=menuFrame noResize src="${path}/page/manage/<%=request.getParameter("page") %>.jsp" frameBorder="0" name=menuFrame><!--菜单-->
        <frame id=middleFrame noResize src="${path}/admin/navigation.action?url=chirdmiddle" frameBorder="0" name=middleFrame scrolling=no><!--点击收起按钮-->
        <frame id=subFrame noResize src="${path}/admin/navigation.action?url=homePageRight" frameBorder="0" name=subFrame><!--主内容区域-->
    </frameset>
    <noframes></noframes>
</html>
