<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<html>
	<head>
		<title>上传单个文件</title>
	</head>
	<body>
		<table border="1">
			<tr bgcolor="yellow">
				<td>
					文件名
				</td>
				<td>
					文件大小(byte)
				</td>
				<td>
					操作
				</td>
			</tr>
			<s:iterator value="fileFileName" status="fn">
				<tr>
					<td>
						<!-- 上传成功文件名 -->
						<s:property />
					</td>
					<td>
						<s:property value="fileSize[#fn.getIndex()]" />
					</td>
					<td>
						<a href="<s:url value='d:\\upload\\'> <s:param name='fileName' value='fileFileName[#fn.getIndex()]' /> </s:url>">
							下载
						</a>
					</td>
				</tr>
			</s:iterator>
		</table>
	</body>
</html>
