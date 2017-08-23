<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
<style>
*{margin:0;padding:0}
</style>
	<frameset id=subFrameset frameSpacing=0 border=0 cols=220,6,* frameBorder=no>
        <frame id=submenuFrame noResize src="${path}/page/manage/doctorTree.jsp" frameBorder="0" name=submenuFrame><!--菜单-->
        <frame id=hospmiddleFrame noResize src="${path}/admin/navigation.action?url=chirdmiddle" frameBorder="0" name=hospmiddleFrame scrolling=no><!--点击收起按钮-->
        <frame id=hospFrame noResize src="${path}/admin/navigation.action?url=homePageRight" frameBorder="0" name=hospFrame><!--主内容区域-->
    </frameset>
    <noframes></noframes>
</html>
