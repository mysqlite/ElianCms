<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
		<title>测试</title>
		<!-- 颜色选择器CSS -->
		<link rel="stylesheet" type="text/css" href="plugin/bigcolorpicker/css/jquery.bigcolorpicker.css">
		<!-- 编辑器JS -->
		<script language="javascript" type="text/javascript" src="plugin/ckeditor/config.js"></script>
		<script language="javascript" type="text/javascript" src="plugin/ckeditor/ckeditor.js"></script>
		<!-- 日期控件JS -->
		<script language="javascript" type="text/javascript" src="plugin/My97DatePicker/WdatePicker.js"></script>
		<!-- 颜色选择器JS -->
		<script language="javascript" type="text/javascript" src="plugin/bigcolorpicker/js/jquery-1.6.1.js"></script>
		<script language="javascript" type="text/javascript" src="plugin/bigcolorpicker/js/jquery.bigcolorpicker.js"></script>
	</head>
	<body>
		<div style="width: 880px; height: 600px;">
			<h1>
				1、编辑器
			</h1>
			<!-- 编辑器控件 -->
			<input id="newsContent" name="news.newsContent" />
			<script type="text/javascript">
CKEDITOR.replace('newsContent');
</script>
			<!-- 日期控件 -->
			<h2>
				2、日期控件
			</h2>
			<input name="news.getDate" class="Wdate" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
			<!-- 下拉列表 -->
			<h2>
				3、下拉列表
			</h2>
			<s:select tooltip="Choose user_type" label="收费类型"
				list="#{'free':'免费','vip':'收费'}" value="#{'free':'免费'}"
				name="bean.user_type" emptyOption="none" headerKey="None"
				headerValue="None" />
			<!-- 复选框 -->
			<h2>
				4、复选框
			</h2>
			<s:checkboxlist tooltip="Choose your Friends" label="朋友"
				list="{'Patrick', 'Jason', 'Jay', 'Toby', 'Rene'}" name="friends" />
			<!-- 单选框 -->
			<h2>
				5、单选框
			</h2>
			<s:radio name="user.grade" value="'0'"
				list="#{'0':'保密','1':'男人','2':'女士'}"></s:radio>
			<!-- 单行文本域 -->
			<h2>
				6、单行文本域
			</h2>
			<s:textfield name="user.name" value=""></s:textfield>
			<!-- 颜色选择器 -->
			<h2>
				7、颜色选择器
			</h2>
			<input id="color_core" type="text" readonly="readonly" name="Title.color" />
			<div id="color_show" style="height:20px ;width:25px;background-color: #000000;"></div>
			<script type="text/javascript">
			$("#color_show").bigColorpicker(function(el,color){
				$("#color_core").val(color);
				$(el).css("backgroundColor",color);})
			</script>
			<s:checkbox list="{'加粗'}" name="is_bold" label="加粗"></s:checkbox>
		</div>
	</body>
</html>