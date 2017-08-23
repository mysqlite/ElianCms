<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
		<frameset id=mainFrameset frameSpacing=0 border=0 cols=220,6,* frameBorder=no>
	        <frame id=menuFrame noResize src="${path}/admin/navigation.action?url=homePageLeft&param=parentId=1" frameBorder="0" name=menuFrame><!--菜单-->
	        <frame id=middleFrame noResize src="${path}/admin/navigation.action?url=middle" frameBorder="0" name=middleFrame scrolling=no><!--点击收起按钮-->
	        <frame id=mainFrame noResize src="${path}/admin/navigation.action?url=homePageRight" frameBorder="0" name=mainFrame><!--主内容区域-->
	    </frameset>
</html>
