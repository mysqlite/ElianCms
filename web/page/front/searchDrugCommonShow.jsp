<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-CN">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>西药</title>
		<meta name="Keywords" content="西药 药品查询">
		<meta name="Description" content="西药 药品查询">
		<link rel="stylesheet" href="http://style.elian.cc/main/global.css"
			type="text/css">
		<link rel="stylesheet" href="http://style.elian.cc/main/yaopin/public.css" type="text/css" media="screen">
		<link rel="stylesheet" href="http://style.elian.cc/main/yaopin/index0822.css" type="text/css" media="screen">
		<link rel="shortcut icon" type="image/x-icon" href="/favicon.ico">
		<!--<script src="script/base0530.js"></script>-->
	</head>
	<body>
		<jsp:include page="searchHead.jsp"></jsp:include>
		<div class="section">
			<div class="section_750">
				<div class="article">
					<div class="breadcrumbs">
						<a class="link" href="front/searchDrugCommon!list.action">药品查询首页
							&gt; </a>
					</div>
					<div class="article_hd">
						<h1>
							${drugCommon.pdtName}
						</h1>
						<div class="line"></div>
					</div>
					<div class="article_bd" id="zebra_lists">
						<dl class="list">
							<dt>
								【批准文号】
							</dt>
							<dd>${drugCommon.approvalNumber}</dd>
						</dl>
						<dl class="list even">
							<dt>
								【产品通用名称】
							</dt>
							<dd>
								${drugCommon.alias}
							</dd>
						</dl>
						<dl class="list">
							<dt>
								【商品英文名称】
							</dt>
							<dd>
								${drugCommon.merchandiseEN}
							</dd>
						</dl>
						<dl class="list even">
							<dt>
								【备注】
							</dt>
							<dd>
								${drugCommon.remark}
							</dd>
						</dl>
						<dl class="list">
							<dt>
								【功效主治】
							</dt>
							<dd>
								${drugCommon.marjorFunc}
							</dd>
						</dl>
						<dl class="list even">
							<dt>
								【主要成分】
							</dt>
							<dd>${drugCommon.packing}</dd>
						</dl>
						<dl class="list">
							<dt>
								【药理作用】
							</dt>
							<dd>
							</dd>
						</dl>
						<dl class="list even">
							<dt>
								【药物相互作用】
							</dt>
							<dd></dd>
						</dl>
						<dl class="list">
							<dt>
								【不良反应】
							</dt>
							<dd></dd>
						</dl>
						<dl class="list even">
							<dt>
								【禁忌】
							</dt>
							<dd></dd>
						</dl>
						<dl class="list">
							<dt>
								【产品规格】
							</dt>
							<dd>${drugCommon.specification}</dd>
						</dl>
						<dl class="list even">
							<dt>
								【用法用量】
							</dt>
							<dd>
							</dd>
						</dl>
						<dl class="list">
							<dt>
								【贮藏方法】
							</dt>
							<dd></dd>
						</dl>
						<dl class="list even">
							<dt>
								【注意事项】
							</dt>
							<dd></dd>
						</dl>

					</div>
				</div>
			</div>
			<div class="sidebar">
				<div class="aside article_aside">
					<div class="mod_side_hd">
						<h3 class="tit">
							药品数据库
						</h3>
					</div>
					<div class="mod_side_bd">
						<ul class="list01_aico">
							<li>
								<a
									href="javascript:void(0);">中成药</a>
							</li>
							<li>
								<a
									href="javascript:void(0);">西药</a>
							</li>
							<li>
								<a
									href="javascript:void(0);">中草药</a>
							</li>
							<li>
								<a
									href="javascript:void(0);">保健品</a>
							</li>
						</ul>
					</div>
					<div class="mod_side_ft"></div>
				</div>
				<div class="aside gg_banner">
					<img
						src="http://images.elian.cc/design/main/yaopin/gg/banner200x125a.jpg"
						width="200" height="125">
				</div>
				<div class="aside gg_banner">
					<img
						src="http://images.elian.cc/design/main/yaopin/gg/banner200x125b.jpg"
						width="200" height="125">

				</div>
			</div>
		</div>
		<c:import url="http://www.elian.cc/include/foot.shtml" charEncoding="utf-8"/>
		<script src="http://script.elian.cc/main/base.js"></script>
		<script src="http://script.elian.cc/main/jquery-1.4.2.min.js"></script>
		<script src="http://script.elian.cc/main/yaopin/jq.sub.js"></script>
	</body>
</html>