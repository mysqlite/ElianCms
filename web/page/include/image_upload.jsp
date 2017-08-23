<%@ page language="java" pageEncoding="UTF-8"%>
<!-- 图片上传控件开始 -->
<script type="text/javascript">
G=function(id){return document.getElementById(id);};
var originalPicture=true;
function uploadImg(n){//上传图片
	var of = $("#uploadFile"+n);
    if(of.val()=="") {alert("请选择图片");return;}//检查是否选择了图片
    $("#fileContent").empty();//将file移动到上传表单
    $("#fileContent").append(of);//复制一个file放至原处
    $("#ufc"+n).append(of.clone().attr("value",""));
    $("#uploadFileText"+n).attr("value","");//修改属性
    of.attr("id","");
    of.attr("name","imgFile");
    $("#ufFileName").val($("#fileName"+n).val());//其他表单
    $("#ufWidth").val($("#zoomWidth"+n).val());
    $("#ufHeight").val($("#zoomHeight"+n).val());
    $("#upPrevImg").val($("#prevImg"+n).val());
    if(G("img_action_name"+n).value!=""&&G("img_action_name"+n).value!="undefined"&&G("img_action_name"+n).value!=null){
        $("#img_content_action").val($("#img_action_name"+n).val());
    }
    $("#ufMark").val(G("mark"+n).checked); //先清除
    $("#uploadNum").val(n); 
    $("#uploadForm").submit();
    openDiv("alertDivImage","…",60,210);
}
//预览图片
function previewImg(n) {
 var path=G("imagePath"+n).value;//https|http|ftp|rtsp|mms
 var isQuote=false;
 if(path.indexOf("https")==0||path.indexOf("http")==0||path.indexOf("ftp")==0||path.indexOf("rtsp")==0||path.indexOf("mms")==0){
	 isQuote=true;
 }
 if($("#imagePath"+n).val()!=""){
	 if(G("originalPicture"+n).value=="defalut"){
		 if(isQuote){
		   G("showImageUpload"+n).src=G("imagePath"+n).value;
		 }else{
		   G("showImageUpload"+n).src='${_img_ftp.ftpUrl}'+G("imagePath"+n).value;
		 }
	 }
	 else{
		  if(isQuote){
             G("showImageUpload"+n).src=G("uploadImgPath"+n).value;
         }else{
		     G("showImageUpload"+n).src='${_img_ftp.ftpUrl}'+G("uploadImgPath"+n).value;
		 }
	 }
	 showIMG(n);
  }
 else{
    alert("暂无图片");	 
 }
}
function loadingImageButton(nId,defVal,btnName,mark,n,isMore,maxMore,actionNames){
	   var defValMore;
	   if(isMore){
		 defValMore=eval(defVal);
		 if(defValMore!=null&&defValMore[0]!=null)
		 defVal=defValMore[0].path;
	   }
	   if(maxMore==undefined||maxMore==null){
		   maxMore=5;
	   }
	   var nonePath=defVal.substr(defVal.lastIndexOf("/")+1,defVal.length);
	   if(defVal.indexOf("https")==0||defVal.indexOf("http")==0||defVal.indexOf("ftp")==0||defVal.indexOf("rtsp")==0||defVal.indexOf("mms")==0){
            nonePath=defVal;
        }
	   if(actionNames==undefined||actionNames==null){
		   actionNames="";
	   }
	   var actionName='<input type="hidden" id="img_action_name'+n+'" value="'+actionNames+'"/>';
	   var imgsrc='${_img_ftp.ftpUrl}'+defVal;
       var browse='<span id="ufc'+n+'" style="position:relative;float:left;">'+
                   '<input type="text" class="ipt" id="uploadFileText'+n+'" value="'+nonePath+'"/>' +
                   '<input type="file" id="uploadFile'+n+'" class="file-button"'+
                   ' onchange="$('+"'"+'#uploadFileText'+n+"'"+').val(this.value)" /><input class="browse" type="button" value="浏览" /></span>';
       var ismark='<label class="lbl-ipt-tit checkBoxWrap" style="color:brown;width:38px;padding-right:0;"><input type="checkbox" id="mark'+n+'"/><span style="font-size:10px;font-weight: 100;">水印</span></label>';
       
       if(!mark){
           ismark='<input type="checkbox" id="mark'+n+'" style="display: none"/>';
       }
       
       var upload='<input type="button" value="上传" onclick="uploadImg('+n+');" class="upload-button"/>' +
                  '<input type="hidden" value="'+defVal+'" id="uploadImgPath'+n+'" style="width:220px"/>';
                   
      var preview='<input type="button" value="预览" id="preview_button'+n+'" onclick="previewImg('+n+');" class="preview-button" href="#show_image_upload'+n+'"/>';
      
      var originalPicture='<input type="hidden" value="defalut" id="originalPicture'+n+'"/>';
      
      var defBtn='<input type="hidden" id="imagePath'+n+'" value="'+defVal+'" name="'+btnName+'"/>';
      
      var realImgValue='<input type="hidden" name="prevFile" value="'+defVal+'" id="imageRealVal'+n+'">';
      
      var prevImg='<input type="hidden" value="" id="prevImg'+n+'">';
      
      var div='<div style="display:none"><div id="show_image_upload'+n+'"><img id="showImageUpload'+n+'" src="'+imgsrc+'"/></div></div>';
      
      var quote='<input type="button" value="引用" id="quote_button'+n+'" onclick="addQuoteIMG('+n+');" class="upload-button" href="#quote_img_path"/>';
      
      var del='<input type="button" value="删除" onclick="delImg('+n+');" class="del-button"/>';
      
      var ipt='<span class="txt" style="color:green">图片限制:{小于5M，宽700px(大于此宽度会导致页面排版错误或失真)}</span>';
      
      var more='<input type="button" value="╋" onclick="addImageButton(\''+nId+'\',\''+''+'\',\''+btnName+'\','+false+',\''+((G("maxMoreImg")!=null)?parseInt(G("maxMoreImg").value)+1:(n+1))+'\','+isMore+','+true+','+maxMore+');"/>';
      var maxMoreHtml='<input type="hidden" id="maxMoreImg" value="1">';
      if(isMore){
    	  var moresHtml="";
    	  var realImgDivHtml="";
    	  for(var i=0;i<(maxMore-1);i++){
    		  moresHtml+="<div id='del_more_"+(n+i+1)+"'></div>"
    		  realImgDivHtml+="<div id='real_img_div"+(n+i+1)+"'></div>";
    	  }
    	 G(nId).innerHTML="<div id='del_more_"+n+"'>"+actionName+browse+ismark+upload+preview+originalPicture+defBtn+prevImg+div+quote+del+more+ipt+maxMoreHtml+"</div>"+moresHtml+"<div id='real_img_div"+n+"'>"+realImgValue+"</div>"+realImgDivHtml;
         for(var i=1;i<defValMore.length;i++){
        	 addImageButton(nId,defValMore[i].path,btnName,mark,parseInt(G("maxMoreImg").value)+1,isMore,true,maxMore);
         }
      }
      else{
         G(nId).innerHTML=actionName+browse+ismark+upload+preview+originalPicture+defBtn+realImgValue+prevImg+div+quote+del+ipt;
      }
}
function addImageButton(nId,defVal,btnName,mark,n,isMore,isMoreBtn,maxMore,actionNames){
	   if(G("maxMoreImg").value>=maxMore){
		   return;
	   }
	   n=parseInt(G("maxMoreImg").value)+1;
	   G("maxMoreImg").value=parseInt(G("maxMoreImg").value)+1;
	   var nonePath=defVal.substr(defVal.lastIndexOf("/")+1,defVal.length);
	   if(defVal.indexOf("https")==0||defVal.indexOf("http")==0||defVal.indexOf("ftp")==0||defVal.indexOf("rtsp")==0||defVal.indexOf("mms")==0){
            nonePath=defVal;
        }
	   
	   var actionName='<input type="hidden" id="img_action_name'+n+'" value="'+actionNames+'"/>';
	   var imgsrc='${_img_ftp.ftpUrl}'+defVal;
       var browse='<span id="ufc'+n+'" style="position:relative;float:left;">'+
                   '<input type="text" class="ipt" id="uploadFileText'+n+'" value="'+nonePath+'"/>' +
                   '<input type="file" id="uploadFile'+n+'" class="file-button"'+
                   ' onchange="$('+"'"+'#uploadFileText'+n+"'"+').val(this.value)" /><input class="browse" type="button" value="浏览" /></span>';
       var ismark='<label class="lbl-ipt-tit checkBoxWrap" style="color:brown;width:38px;padding-right:0;"><input type="checkbox" id="mark'+n+'"/><span style="font-size:10px;font-weight: 100;">水印</span></label>';
       
       if(!mark){
           ismark='<input type="checkbox" id="mark'+n+'" style="display: none"/>';
       }
       
       var upload='<input type="button" value="上传" onclick="uploadImg('+n+');" class="upload-button"/>' +
                  '<input type="hidden" value="'+defVal+'" id="uploadImgPath'+n+'" style="width:220px"/>';
                   
      var preview='<input type="button" value="预览" id="preview_button'+n+'" onclick="previewImg('+n+');" class="preview-button" href="#show_image_upload'+n+'"/>';
      
      var originalPicture='<input type="hidden" value="defalut" id="originalPicture'+n+'"/>';
      
      var defBtn='<input type="hidden" id="imagePath'+n+'" value="'+defVal+'" name="'+btnName+'"/>';
      
      var realImgValue='<input type="hidden" name="prevFile" value="'+defVal+'" id="imageRealVal'+n+'">';
      
      var prevImg='<input type="hidden" value="" id="prevImg'+n+'">';
      
      var div='<div style="display:none"><div id="show_image_upload'+n+'"><img id="showImageUpload'+n+'" src="'+imgsrc+'"/></div></div>';
      
      var quote='<input type="button" value="引用" id="quote_button'+n+'" onclick="addQuoteIMG('+n+');" class="upload-button" href="#quote_img_path"/>';
      
      var del='<input type="button" value="删除" onclick="delImg('+n+');" class="upload-button"/>';
      
      var ipt='<span class="txt" style="color:green">图片限制:{小于5M，宽700px(大于此宽度会导致页面排版错误或失真)}</span>';
      
      var delmore='<input type="button" value="▬" onclick="delMoreHtml(\''+nId+'\',\''+n+'\');">';
      
      if(isMore&&isMoreBtn){
    	 G("del_more_"+n).style.clear="both";
    	 G("del_more_"+n).style.padding="0px 0px 0px 110px";
    	 G("del_more_"+n).innerHTML=browse+ismark+upload+preview+originalPicture+defBtn+prevImg+div+quote+del+delmore+ipt+actionName;
    	 G("real_img_div"+n).innerHTML=realImgValue;
      }
      else{
         G(nId).innerHTML=browse+ismark+upload+preview+originalPicture+defBtn+realImgValue+prevImg+div+quote+del+ipt;
      }
}

function delMoreHtml(nId,n){
	G("del_more_"+n).innerHTML="";
	G("del_more_"+n).style.clear="";
    G("del_more_"+n).style.padding="";
	G("maxMoreImg").value=parseInt(G("maxMoreImg").value)-1;
}

function closeQuoteDiv(){
    var n=G("quote_img_n").value;
    var path=G("quote_img_path_input").value;
    var img=document.createElement('img');//创建img元素
    img.src=path;
    img.style.position="absolute";//防止正常内容变型
    img.style.visibility='hidden';//设置隐藏
    var inj=document.body.appendChild(img);
    var width=inj.offsetWidth;
    var height=inj.offsetHeight;
    //if(width<700&&height<1500){
	    if(IsURL(path)&&path.length<=255){
	        jQuery().colorbox.close();
	        G("uploadFileText"+n).value=path;
	        G("originalPicture"+n).value="false";
	        G("uploadImgPath"+n).value=path;
	        G("imagePath"+n).value=path;
	        G("quote_img_path_input").value="";
	        G("Img_tip_Msg").innerHTML="*请输入引用图片地址";
	    }else{
	        G("Img_tip_Msg").innerHTML="*引用地址非法,255字以内";
	    }
	//}
   // else{
	//	G("Img_tip_Msg").innerHTML="引用图片宽小于700,高小于10000";
	//}
}
function delImg(n){
   	G("uploadFileText"+n).value="";
    G("imagePath"+n).value="";
}


function showImageButton(nId,defVal,btnName,n,isMore){
	if(isMore){
		defValMore=eval(defVal);
         if(defValMore!=null&&defValMore[0]!=null)
         defVal=defValMore[0].path;
	}
	var imgsrc='${_img_ftp.ftpUrl}'+defVal;
	var context= '<input type="button" value="预览" onclick="previewImg('+n+');" id="preview_button'+n+'" class="preview-button" href="#show_image_upload'+n+'"/><input type="hidden" id="imagePath'+n+'" value="'+defVal+'" name="'+btnName+'"/>';
    var originalPicture='<input type="hidden" value="defalut" id="originalPicture'+n+'"/>';
    var div='<div style="display:none"><div id="show_image_upload'+n+'"><img id="showImageUpload'+n+'"  src="'+imgsrc+'"/></div></div>';
   if(defVal!=""){
	   G(nId).innerHTML=context+originalPicture+div;
   }
   else{
	   G(nId).style.color="red";
	   G(nId).innerHTML="暂无图片";
   }
}
function addQuoteIMG(n){
	G("quote_img_n").value=n;
     $("#quote_button"+n).colorbox({slideshow:false,inline:true, width:"320px",hight:"70px",close:"关闭",overlayClose:false});
    }
function showIMG(n){
	 $("#preview_button"+n).colorbox({slideshow:false,inline:true, width:"auto",hight:"auto",close:"关闭",overlayClose:true});
	}
function IsURL(str_url){ 
        var strRegex = "^((https|http|ftp|rtsp|mms)?://){1,1}"  
        + "?(([0-9a-zA-Z_!~*'().&=+$%-]+: )?[0-9a-zA-Z_!~*'().&=+$%-]+@)?" //ftp的user@  
        + "(([0-9]{1,3}\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184  
        + "|" // 允许IP和DOMAIN（域名） 
        + "([0-9a-zA-Z_!~*'()-]+\.)*" // 域名- www.  
        + "([0-9a-zA-Z][0-9a-zA-Z-]{0,61})?[0-9a-zA-Z]\." // 二级域名  
        + "[a-zA-Z]{2,6})" // first level domain- .com or .museum  
        + "(:[0-9]{1,4})?" // 端口- :80  
        + "((/?)|" // a slash isn't required if there is no file name  
        + "(/[0-9a-zA-Z_!~*'().;?:@&=+$,%#-]+)+/?)$";  
        var re=new RegExp(strRegex);    //re.test() 
       if (re.test(str_url)){ 
            return (true);  
         }else{  
            return (false);  
        } 
     } 
</script>    
<form id="uploadForm" action="${path}/front/imageUpload!uploadImageToTemp.action" method="post" enctype="multipart/form-data" target="hiddenIframe" style="display:none;width:0px;height:0px;">   
	<span id="fileContent"></span>
	<input id="ufWidth" type="hidden" name="zoomWidth"/>
	<input id="ufHeight" type="hidden" name="zoomHeight"/>
	<input id="ufFileName" type="hidden" name="fileName"/>
	<input id="ufMark" type="hidden" name="mark"/>
	<input id="uploadNum" type="hidden" name="uploadNum"/>
	<input id="upPrevImg" type="hidden" name="upPrevImg"/>
	<input id="upControls" type="hidden" name="controls" value="true"/>
	<input id="quote_img_n" type="hidden"/>
	<input name="contentAction" id="img_content_action" value="${action}">
	<input name="id" id="id" value="${id}">
	<div id="alertDivImage" style="display:none;">
	    <img alt="图片上传中" src="${path}/images/manage/base_loading_bar.gif" style="padding-left:0px;padding-bottom:0px;padding-top:0px;padding-right:0px;">
	</div>
	<div id="quote_img_path" class="listItem"> 
	   <input id="quote_img_path_input"  class="ipt"/><span class="star" id="Img_tip_Msg" style="width:240px;">*请输入引用图片地址</span>
	   <br/>
	   <div class="buttonArea" style=" width:240px;padding-right:1px;">
	      <input class="formButton" value="确  定" onclick="closeQuoteDiv()" type="button" style="float:right;">
	   </div>
	</div>
	<div id="val_quote_img_"></div>
	<iframe name="hiddenIframe" frameborder="0" style="display:none;width:0px;height:0px;"></iframe>
</form>  
<!-- 图片上传控件结束 -->