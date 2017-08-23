$(function() {
	
});

//获取订单列表
var getOrder = function(){
	$.ajax({
		url:'${path}/admin/order!ajaxList.action',
		type:'get',
		success:function(data){
			if(typeof data.pagi == 'undefined' || data.pagi == null || data.pagi.length == 0){
			
			}else{
				$.each(data.pagi,function(id,item){
					
				
				});
			}
		},
		error : function(){
			
		}
	});
};