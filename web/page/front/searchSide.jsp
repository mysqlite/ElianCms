<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="sidebar article_sidebar">
	<div class="aside">
		<a href="#"><img src="http://placehold.it/280x200" width="280"
				height="200" /> </a>
	</div>
	<div class="aside mod-rank">
		<div class="mod_gray_hd">
			<h3 class="ico_hand">
				热点新闻
			</h3>
		</div>
		<div class="mod_gray_bd">
			<div class="aside-bd">
				<ul id="hos-resource-list_hot_news" class="list">
					<li class="first">
						<div class="tit">
							<b class="top-rank">1</b><a href="">冬季不吃饺子会冻耳朵吗</a>
						</div>
					</li>
					<li>
						<div class="tit">
							<b class="top-rank">2</b><a href="">冬季不吃饺子会冻耳朵吗</a>
						</div>
					</li>
					<li>
						<div class="tit">
							<b class="top-rank">3</b><a href="">冬季不吃饺子会冻耳朵吗</a>
						</div>
					</li>
					<li>
						<div class="tit">
							<b>4</b><a href="">冬季不吃饺子会冻耳朵吗</a>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</div>	
</div>
<script type="text/javascript">
	$.ajax({
		url: '${path}/reg/regHotNews!listJson.action',
		type: "GET",
		dataType: 'jsonp',
		data: {},
		timeout: 5000,
		success: function(data) {
			if(data!=null && data!=undefined){
				var code="";
				for(var i=0,size=data.length;i<size;i++){
					code+="<li>";
					code+="  <div class='tit'><b class='"+(i<3?'top-rank':'')+"'>"+(i+1)+"</b><a href='"+data[i].path+"'>"+data[i].title+"</a></div>";
					code+="</li>";
				}
				document.getElementById("hos-resource-list_hot_news").innerHTML=code;
			}
		}
	});
</script>
