<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="mod_hd03">
    <h3 class="tit">医生评论排行</h3>
</div>
<div class="mod_bd03">
    <ul class="hos-resource-list" id="hos-resource-list_doctor_comment">
    </ul>
</div>
 <script>
 	fgm.on(window, "load", function() {
		new LazyLoad("hos-resource-list_doctor_comment",regDoctorCommentTopCallBack);
	});
	 function regDoctorCommentTopCallBack($obj){
		 $.ajax({
				url: '${path}/reg/regDoctorCommentTop!listJson.action',
				type: "GET",
				dataType: 'jsonp',
				data: {},
				timeout: 5000,
				success: function(data) {
					if(data!=null && data!=undefined){
						var code="";
						for(var i=0,size=data.length;i<size;i++){
							code+="<li class='num-ico num-ico-01'>";
							code+="  <div class='txt'><a href='"+'${path}'+"/reg/regDoctor!searchByDept.action?hospId="+data[i].dept.hospital.id+"'>"+data[i].dept.hospital.hospName+"</a></div>";
							code+="  <span class='num'><a href='"+'${path}'+"/reg/regDoctor!detial.action?docId="+data[i].id+"'>"+data[i].doctName+"</a></span>";
							code+="</li>";
						}
						$obj.innerHTML=code;
					}
				}
			});
	 }
</script>