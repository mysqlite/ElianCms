<!DOCTYPE HTML>
<HTML lang="zh-CN">
	<HEAD>
		<TITLE>显示/隐藏菜单 - Powered By SHOP++</TITLE>
		<META content="text/html; charset=utf-8" http-equiv=content-type>
		<META name=Author content="SHOP++ Team">
		<META name=Copyright content=SHOP++>
		<LINK rel=icon type=image/x-icon href="favicon.ico">
		<link rel=stylesheet type=text/css href="${path}/css/manage/base.css">
		<link rel=stylesheet type=text/css href="${path}/css/manage/admin.css">
		<SCRIPT type=text/javascript src="${path}/js/jquery.js"></SCRIPT>
		<SCRIPT type=text/javascript src="${path}/js/manage/base.js"></SCRIPT>
		<SCRIPT type=text/javascript src="${path}/js/manage/admin.js"></SCRIPT>
		<SCRIPT type=text/javascript>
$().ready(function() {
	var $main = $("#main");
	$main.click( function() {
		var mainFrameset = window.parent.window.document.getElementById("mainFrameset");
		if(mainFrameset.cols == "220,6,*"||mainFrameset.cols == "220,6,*") {
			mainFrameset.cols = "0,6,*";
			$main.removeClass("leftArrow");
			$main.addClass("rightArrow");
            var menuFrame = window.parent.window.document.getElementById("menuFrame")//.getElementById("body");
		} else{
            mainFrameset.cols = "220,6,*";
			$main.removeClass("rightArrow");
			$main.addClass("leftArrow");
		}
	})

})
</SCRIPT>

		<STYLE type=text/css>
HTML {
	HEIGHT: 100%;
	OVERFLOW: hidden
}

BODY {
	HEIGHT: 100%;
	OVERFLOW: hidden
}
</STYLE>

<style>
	.top{ background: url("${path}/images/manage/asset/main-hd-repeat.png") 0 0 repeat-x;
	     height: 40px;
	 border-left: 1px solid #A9B2B8;}
</style>
		<META name=GENERATOR content="MSHTML 8.00.6001.19154">
	</HEAD>
	<BODY class=middle>
		   <DIV id=main class="main leftArrow"><div class="top"></div></DIV>
	</BODY>
</HTML>
