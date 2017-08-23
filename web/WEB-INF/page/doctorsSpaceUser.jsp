<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%  
	response.setHeader("Cache-Control","no-cache");  
	response.setHeader("Pragma","no-cache");  
	response.setDateHeader ("Expires", 0);  
%> 
<!doctype html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=10"/>
<title>医家空间-用户</title>
<meta name="Keywords" content="医联网用户个人主页" /> 
<meta name="Description" content="医联网用户个人主页，医联网用户个人主页，医联网用户个人主页" />
<link rel="stylesheet" href="http://style.elian.cc/doctorsSpace/style/base.css" type="text/css" media="screen" />
<link rel="stylesheet" href="http://style.elian.cc/doctorsSpace/style/header.css" type="text/css" media="screen" />
<link rel="stylesheet" href="http://style.elian.cc/doctorsSpace/style/usr_manage.css" type="text/css" media="screen" />
<link rel="shortcut icon" type="image/x-icon"  href="http://www.xxx.com/favicon.ico"/>
</head>
<body>
<div class="wrap_topbar">
    <div class="topbar">
	    <div class="login_link">
		    <a href="#" class="home_ico">医联网</a>
		    <a href="#" class="link">登录</a> | <a href="#" class="link">注册</a>
	    </div>
        <div class="top_link">
	        <a href="#">我要买</a> | <a href="#">我的医家</a> | <a href="#">我的购物车(<span class="num">0</span>)</a> | <a href="#">联系客服</a> | <a href="#">帮助</a> | <a onclick="AddFavorite(window.location,document.title)" href="javascript:void(0);">加入收藏</a>
        </div>
    </div>
</div>
<div class="header">
    <h1><a href="#"><img src="http://style.elian.cc/doctorsSpace/img/logo.jpg" width="111" height="50" alt="医联网用户个人主页"/>医联网用户个人主页</a></h1>
	<h2 class="sub_tit">我的医家</h2>
    <div class="search_bar">
        <div class="bd">
                <span class="ui_search_bar">
                    <input class="ipt" type="text" value="请输入产品名称"/>
                    <input class="ips" type="submit" value="搜索"/>
                </span>
        </div>
    </div>
</div>
<div class="wrap_usr_header">
	<div class="usr_header clearfix">
		<div class="usr_msg">
			<div class="pt03">
				<a href="#" class="pic"><img src="http://style.elian.cc/doctorsSpace/img/usr120x120.jpg"/></a>
				<h3 class="tit">医联网韦海</h3>
				<div class="txt">实名认证  手机验证</div>
				<div class="txt">男  广东 佛山</div>
			</div>
			<div class="sns_log clearfix">
		        <a href="#"><span class="num">13</span><span class="txt">订单</span></a>
				<a href="#"><span class="num">13</span><span class="txt">挂号</span></a>
				<a href="#"><span class="num">13</span><span class="txt">关注</span></a>
			</div>
		</div>


	</div>

</div>
<div class="gutter"></div>
<div class="section">
	<div class="usr_nav">
		<h3 class="cur"><span class="txt">首页</span></h3><h3><span class="txt">提问</span></h3>
		<h3><span class="txt">收藏</span></h3><h3><span class="txt">评论</span></h3>
		<h3><span class="txt">设置</span></h3><h3><span class="txt">医生动态</span></h3>
		<h3 class="last"><span class="txt">更多</span></h3>
	</div>
    <div class="main_con clearfix">
        <div class="mod_box reply_box">
	        <div class="hd"><h3>最新回复</h3><a class="more" href="#">查看全部<em>xx</em>个回复</a></div>
	        <div class="bd">
		        <div class="i">
			        <h4>
				        <span class="msg_tit">我是标题我是标题我是标题我是标题</span>
				        <span class="date">提问日期: 2013-04-24</span>
			        </h4>
			        <div class="reply_box_bd">
				        <span class="msg_reply">收到回复：<a href="#" class="txt">"请速到我院请速到我院请速到我院请速到我院"</a></span>
				        <span class="date">回复日期: 2013-04-24</span>
			        </div>
		        </div>
		        <div class="i">
			        <h4>
				        <span class="msg_tit">我是标题我是标题我是标题我是标题</span>
				        <span class="date">提问日期: 2013-04-24</span>
			        </h4>
			        <div class="reply_box_bd">
				        <span class="msg_reply">收到回复：<a href="#" class="txt">"请速到我院请速到我院请速到我院请速到我院"</a></span>
				        <span class="date">回复日期: 2013-04-24</span>
			        </div>
		        </div>
		        <div class="i">
			        <h4>
				        <span class="msg_tit">我是标题我是标题我是标题我是标题</span>
				        <span class="date">提问日期: 2013-04-24</span>
			        </h4>
			        <div class="reply_box_bd">
				        <span class="msg_reply">收到回复：<a href="#" class="txt">"请速到我院请速到我院请速到我院请速到我院"</a></span>
				        <span class="date">回复日期: 2013-04-24</span>
			        </div>
		        </div>
	        </div>
        </div>
	    <div class="mod_box reply_box">
		    <div class="hd"><h3>最新回复</h3><a class="more" href="#">查看全部<em>xx</em>个回复</a></div>
		    <div class="bd">
			    <div class="i">
				    <h4>
					    <span class="msg_tit">我是标题我是标题我是标题我是标题</span>
					    <span class="date">提问日期: 2013-04-24</span>
				    </h4>
				    <div class="reply_box_bd">
					    <span class="msg_reply">收到回复：<a href="#" class="txt">"请速到我院请速到我院请速到我院请速到我院"</a></span>
					    <span class="date">回复日期: 2013-04-24</span>
				    </div>
			    </div>
			    <div class="i">
				    <h4>
					    <span class="msg_tit">我是标题我是标题我是标题我是标题</span>
					    <span class="date">提问日期: 2013-04-24</span>
				    </h4>
				    <div class="reply_box_bd">
					    <span class="msg_reply">收到回复：<a href="#" class="txt">"请速到我院请速到我院请速到我院请速到我院"</a></span>
					    <span class="date">回复日期: 2013-04-24</span>
				    </div>
			    </div>
			    <div class="i">
				    <h4>
					    <span class="msg_tit">我是标题我是标题我是标题我是标题</span>
					    <span class="date">提问日期: 2013-04-24</span>
				    </h4>
				    <div class="reply_box_bd">
					    <span class="msg_reply">收到回复：<a href="#" class="txt">"请速到我院请速到我院请速到我院请速到我院"</a></span>
					    <span class="date">回复日期: 2013-04-24</span>
				    </div>
			    </div>
		    </div>
	    </div>
    </div>
    <div class="sidebar">
        <div class="aside">
	        <div class="hd">
		        <h3 class="tit">关注医生</h3><a class="more" href="3">更多&gt;&gt;</a>
	        </div>
	        <div class="bd">
		        <ul class="pt03">
			        <li>
				        <a href="#" class="pic"><img src="http://style.elian.cc/doctorsSpace/img/3.jpg" width="80" height="64"/></a>
				        <h3 class="tit">医联网韦海</h3>
				        <div class="txt">妇科</div>
				        <div class="txt">佛山第一中医医院</div>
			        </li>
			        <li>
				        <a href="#" class="pic"><img src="http://style.elian.cc/doctorsSpace/img/3.jpg" width="80" height="64"/></a>
				        <h3 class="tit">医联网韦海</h3>
				        <div class="txt">妇科</div>
				        <div class="txt">佛山第一中医医院</div>
			        </li>
			        <li>
				        <a href="#" class="pic"><img src="http://style.elian.cc/doctorsSpace/img/3.jpg" width="80" height="64"/></a>
				        <h3 class="tit">医联网韦海</h3>
				        <div class="txt">妇科</div>
				        <div class="txt">佛山第一中医医院</div>
			        </li>

		        </ul>
	        </div>
        </div>
	    <div class="gutter"></div>
	    <div class="aside">
		    <div class="hd">
			    <h3 class="tit">健康保健</h3><a class="more" href="3">更多&gt;&gt;</a>
		    </div>
		    <div class="bd">
			    <ul class="list01">
					<li><a href="#">·我是健康资讯我是健康资讯我是健康资讯我是健康资讯</a></li>
				    <li><a href="#">·我是健康资讯我是健康资讯我是健康资讯我是健康资讯</a></li>
				    <li><a href="#">·我是健康资讯我是健康资讯我是健康资讯我是健康资讯</a></li>
				    <li><a href="#">·我是健康资讯我是健康资讯我是健康资讯我是健康资讯</a></li>
				    <li><a href="#">·我是健康资讯我是健康资讯我是健康资讯我是健康资讯</a></li>
				    <li><a href="#">·我是健康资讯我是健康资讯我是健康资讯我是健康资讯</a></li>


			    </ul>
		    </div>
	    </div>
	    <div class="gutter"></div>
	    <div class="aside hot_read">
		    <div class="hd">
			    <h3 class="tit">大家都在看</h3><a class="more" href="3">更多&gt;&gt;</a>
		    </div>
		    <div class="bd">
			    <ul class="list01">
				    <li><a href="#">·我是健康资讯我是健康资讯我是健康资讯我是健康资讯</a>
				        <p class="txt">摘要摘要摘要摘要摘要摘要摘要摘要摘要摘要摘要摘要摘要摘要摘要</p>
				    </li>
				    <li><a href="#">·我是健康资讯我是健康资讯我是健康资讯我是健康资讯</a>
					    <p class="txt">摘要摘要摘要摘要摘要摘要摘要摘要摘要摘要摘要摘要摘要摘要摘要</p>
				    </li>
				    <li><a href="#">·我是健康资讯我是健康资讯我是健康资讯我是健康资讯</a>
					    <p class="txt">摘要摘要摘要摘要摘要摘要摘要摘要摘要摘要摘要摘要摘要摘要摘要</p>
				    </li>


			    </ul>
		    </div>
	    </div>
    </div>
</div>
<div class="footer">
	<div class="tips">
		<p>温馨提示：如果您怀疑自己有某种健康问题，可在线咨询专家或尽快去医院就诊治疗。</p>
	</div>
	<div class="bottom_nav">
		<a href="" target="_blank">关于我们</a>
		|
		<a href="#" target="_blank">网站地图</a>
		|
		<a href="" target="_blank">友情链接</a>
		|
		<a href="">营销中心</a> |
		<a href="" target="_blank">法律声明</a>
		|
		<a href="">隐私声明</a> |
		<a href="http://software.elian.cc/contactus.asp?mid=8" target="_blank">联系我们</a>
		|
		<a href="http://software.elian.cc/agent-join.asp?mid=6" target="_blank">加入医联网推广</a>
	</div>
	<p style="text-align:center;">
		电话：0757-82137888 传真：0757-82139888 全国电话：40001-91580
	</p>
	<p style="text-align:center;">
		<a href="http://www.elian.cc">www.elian.cc </a>
		<a href="http://www.xn--ekrw93hgwa.com">医联网.COM</a>
		<a href="http://www.xn--ekrw93hgwa.cn">医联网.CN</a> 版权所有未经授权请勿转载
	</p>
	<p style="text-align:center;">
		粤ICP备10054714号-2 <a href="#">互联网药品信息服务资格证书</a> 证件编号：<a href="#" target="_blank">（粤）-非经营性-2010-0176</a>
	</p>
	<p style="text-align:center;">
		本站信息仅供参考不能作为诊断及医疗的依据，如有转载或引用文章涉及版权问题请速与我们联系。
	</p>
</div>
<script src="${path}/js/jquery-1.4.4.min.js"></script>
<script src="${path}/js/doctorsSpace/doctorsSpaceUser.js"></script>
</body>
</html>