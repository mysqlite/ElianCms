<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<html>
	<head>
		<title>上传单个文件</title>
	</head>
	<body>
		<form action="${path}/admin/upload.action" method="post" enctype="multipart/form-data"> 
            <input type='file' name='upload' /> 
            <input type="submit" value="上传" /> 
        </form> 
	</body>
</html>
