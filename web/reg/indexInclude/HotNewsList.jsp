<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	fgm.on(window, "load", function() {
		new LazyLoad("hos-resource-list_hot_news",regHotNewsCallback);
	});
	
	function regHotNewsCallback($obj){
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
						code+="<li class='num-ico num-ico-0"+(i+1)+"'>";
						code+="  <div class='txt'><a href='"+data[i].path+"' target='_blank'>"+data[i].title+"</a></div>";
						code+="  <span class='num'>"+data[i].time.substring(1,data[i].time.length-1)+"</span>";
						code+="</li>";
					}
					$obj.innerHTML=code;
				}
			}
		});
	}
</script>
<div class="mod_hd03">
    <h3 class="tit">热点新闻</h3>
</div>
<div class="mod_bd03">
    <ul class="hos-resource-list" id="hos-resource-list_hot_news">
    </ul>
</div>
