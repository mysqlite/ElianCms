<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
	<head>
		<base href="<%=basePath%>">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=9">
		<title>医联网 资讯列表</title>
		<meta name="Keywords" content="医联网 资讯列表">
		<meta name="Description" content="医联网 资讯列表">
		<link rel="stylesheet" href="http://style.elian.cc/main/global.css" type="text/css">
		<link rel="stylesheet" href="http://style.elian.cc/main/illness/public.css" type="text/css">
		<link rel="stylesheet" href="http://style.elian.cc/main/illness/sub.css" type="text/css">
		<link rel="shortcut icon" type="image/x-icon" href="http://www.elian.cc/favicon.ico">
		<script type="text/javascript">
			function checkEnter(event, clickId) {
	　　    		if(event.keyCode == 13){
	　　    			document.getElementById(clickId).click();
	　　    		}
	　　    	}
			
			function changeType(cur, type){
				var childs = document.getElementById("typeDiv").childNodes;
				for(var i = 0; i < childs.length; i++){
					childs[i].className="";
				}
				
				document.getElementById("type").value = type;
				cur.className = "cur";
			}
			
			function changeIntDate(cur, value){
				var childs = document.getElementById("intDateDiv").childNodes;
				for(var i = 0; i < childs.length; i++){
					childs[i].className="";
				}
				
				document.getElementById("intDate").value=value;
				cur.className = "cur";
			}
			
			function submitSearch(form){
				var value = document.getElementById("keyword").value;
				if(value=='请输入关键字'){
					document.getElementById("keyword").value = "";
				}
				document.getElementById(form).submit();
			}
		</script>
	</head>
	<body>
		<jsp:include page="searchHead.jsp"></jsp:include>
		<div name="top" id="top" class="breadcrumbs">
		    <div class="wrap_bd">
		        <div class="bd">
		            你现在的位置是：<a onclick="javascript:void(0);" href="#">疾病大全</a> 
		        </div>
		    </div>
		</div>
		<div class="section">
			<div class="w750 mod_ui_box">
		        <div class="wrap_ui_bd">
		            <div class="ui_bd">
		                <div class="article clearfix">
							${illness.contentXML}
		                </div>
		                <div class="wrap_backBtn"><a href="#top" class="backBtn back2top">返回顶部</a></div>
		            </div>
		       </div>
		    </div>
		    <jsp:include page="illnessInclude.jsp"></jsp:include>
		 </div>
		<c:import url="http://www.elian.cc/include/foot.shtml" charEncoding="utf-8"/>
		<script type="text/javascript" src="script/jquery.header.js"></script>

		<div class="hidden">
			<script type="text/javascript" src="script/baidu-flow.js"></script>
			<script type="text/javascript" src="script/google-flow.js"></script>
			<a href="http://www.cnzz.com/stat/website.php?web_id=3321260"
				target="_blank" title="站长统计">站长统计</a>
			<script type="text/javascript">
				var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://"
						: " http://");
				document.write(unescape("%3Cscript src='"
								+ _bdhmProtocol
								+ "hm.baidu.com/h.js%3Ffcab8f29a9e279175c4fd1b3038becb3' type='text/javascript'%3E%3C/script%3E"));
			
				
				
			</script>			
		</div>
	</body>
</html>