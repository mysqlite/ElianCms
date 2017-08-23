<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
	<frameset id=auditFrameset frameSpacing=0  border=0 cols=* frameBorder=no rows=45,*>
        <frame id=auditHeardFrame noresize src="${path}/admin/navigation.action?url=auditHead" frameBorder="0" name=auditHeardFrame scrolling="no"/><!--头部导航页-->
        <frame id="auditMainFrame" noResize src="${path}/admin/audit!list.action?status=notAudit" frameBorder="0" name=auditMainFrame><!--主内容区域-->
    </frameset>
</html>
