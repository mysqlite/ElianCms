REQU=function(){if(window.ActiveXObject){return new ActiveObject("Microsoft.XMLHTTP"); }else if(window.XMLHTTPRequest){return new XMLHTTPRequest();}};
G=function(id){return document.getElementById(id);};
GV=function(id){return (G(id)!=null)?G(id).value:null};
ME=function(id,msg){id=id+"_tip";if(msg!=""){G(id).innerHTML="<b class='reg_tips reg_error_tips'>×"+msg+"</b>";}else{G(id).innerHTML="";}};
MO=function(id,msg,isOK){id=id+"_tip";if(isOK==null||isOK==undefined){isOK=false;} if(msg!=""){G(id).innerHTML="<b class='reg_tips reg_ok_tips'>"+(isOK?"<span style='color:green'>√<span>":"")+msg+"</b>";}else{G(id).innerHTML="";}};
ML=function(id){return ",当前已输入"+GV(id).length+"位。"};
N=function(id){if(G(id+"_m")!=null)G(id+"_m").style.display="none";};
function getJson(url,f,paramData) {$.ajax({url: url,type: "GET",dataType: 'jsonp',data: (paramData),async:true,cache:true,timeout: 10000,success:f});}
/**表单分类*/
function displayDiv(btnId,divId,errors,times){
	var time=650;
    $("#"+btnId).click(function(a){
    	if(!$(btnId).is(":animated")){ 
	    	if(times!=0){time=times;}
	    	var display=G(divId).style.display;
	    	if(display=="none"){$("#"+divId).slideDown(time);}
	    	else{$("#"+divId).slideUp(time);}
	    	a.stopPropagation();
	    	$(this).blur()
    	}
    	});
    if(errors!="{}"){$("#"+divId).slideDown(time);}
}
/**
 *	判断是否是backSpace键
 */
function BackSpace(val,keyId, valueId) {
	var c = event.keyCode;
	if (c == 8){
		event.returnValue = false;
		if (keyId == null || keyId == undefined) {
			keyId = "keyId";
		}
		if (valueId == null || valueId == undefined) {
			valueId = "valueId";
		}
		$("#"+keyId).attr("value", val);
		G("#"+valueId).attr("value", 0);
	}
}
/**
*表单提交
**/
var submitNum = 0;
function submits(form,editor1,editor2,editor3,editor4){
	if(editor1!=null){editor1.sync();}
	if(editor2!=null){editor2.sync();}
	if(editor3!=null){editor3.sync();}
	if(editor4!=null){editor4.sync();}
    submitNum = submitNum + 1;
    if (submitNum == 1) {
        form.submit();
    }else{
        alert("表单提交中……请稍后");
    }
};
/**打开，关闭弹出层*/
function closeDiv(){
	$('.webox').css({display:'none'});
    $('.background').css({display:'none'});
}
function openDiv(divId,tit,hgt,wth,img){
	if(!divId==""){
		$.webox({
	            height : hgt,
	            width : wth,
	            bgvisibel : true,
	            title : tit,
	            html : $("#"+divId).html()
	            
	          });
	}
}

function alertMaskDiv(msg,imgPath,maskXY,times){
if(maskXY==null){maskXY='200';}
if(times==null){ times=1000;}
if(msg==null){ msg="加载中，请稍后!";}
getParentFrame().$("#loadingImg").attr("src",imgPath+"/images/manage/waiting.gif");
$(".overlay").css({'display':'block','opacity':'0.8'});
$(".showbox").stop(true).animate({'margin-top':maskXY+'px','opacity':'1'},times);
getParentFrame().$("#loadingWordText").html(msg);
}

function closeMaskDiv(id,maskXY,times){
if(maskXY==null){
    maskXY='250';
}
if(times==null){
   times=1000;
}
$(".showbox").stop(true).animate({'margin-top':'250px','opacity':'0'},times);
$(".overlay").css({'display':'none','opacity':'0'});
setTimeout(function(){getParentFrame().$("#loadingWordText").html("加载中，请稍后");},times);
}

function changeMaskDiv(id,msg,imgPath,times){
	if(times==undefined || times==null)times=1500;
	if(msg==undefined || msg==null)msg="执行完毕！";
	getParentFrame().$("#loadingWordText").html(msg);
	getParentFrame().$("#loadingImg").attr("src",imgPath+"/images/manage/over.jpg");
 	setTimeout(function(){
	getParentFrame().closeMaskDiv();//关闭遮罩层
	},times);
}

function getParentFrame(){

return parent.parent.parent.indexFrame;
}
/**
 *	格式化Int值
 */
function intFormat(currentObj, minValue, maxValue) {
	if (currentObj.value == null || currentObj.value == undefined|| !Number(currentObj.value)) {
		currentObj.value = Number(minValue);
	} 
	else if (maxValue != null && maxValue != undefined&& Number(currentObj.value) > Number(maxValue)) {
		currentObj.value = Number(maxValue);
	}
}