<%@ page language="java" pageEncoding="UTF-8"%>
<!--上传控件开始 -->
<script type="text/javascript">
upload={};
htm={};
htm.core=function(types,id,value,clazz,style,e,name,inputType){
	var html="<";
	if(!upload.blank(types)){
		html+=types;
		if(!upload.blank(id))html+=' id="'+id+'"';
		if(!upload.blank(name))html+=' name="'+name+'"';
		if(types=='input'&&!upload.blank(inputType))html+=' type="'+inputType+'"';
		if(!upload.blank(clazz))html+=' class="'+clazz+'"';
		if(!upload.blank(style))html+=' style="'+style+'"';
		if(!upload.blank(e))html+=' '+e;
		if(!upload.blank(value)){
			if(types=='span'||types=='lable'||types=='div')html+='>'+value;
			else html+=' value="'+value+'"/>'
		}
		else if(types=='span'||types=='lable'||types=='div')html+='>';
		else html+='/>';
		return html;
	}
}
htm.div=function(end,id,clazz,style,e){if(end)return '</div>';else return htm.core('div',id,'',clazz,style,e);}
htm.input=function(id,type,clazz,value,e,s,name){if(upload.blank(type))type="text";return htm.core('input',id,value,clazz,s,e,name,type);}
htm.span=function(end,id,clazz,value,e,s){if(end)return '</span>';else return  htm.core('span',id,value,clazz,s,e);}
htm.lable=function(end,id,clazz,value,e,s){if(end)return '</lable>';else return htm.core('lable',id,value,clazz,s,e);}
upload.G=function(id){return document.getElementById(id);};
upload.blank=function(str){if(str==null||str==undefined||str==""){return true;}else{return false;}};
upload.quote=function (url){if(url.indexOf("https")==0||url.indexOf("http")==0||url.indexOf("ftp")==0||url.indexOf("rtsp")==0||url.indexOf("mms")==0)return true;else return false;}
upload.addDomain=function(url){if(upload.blank(url))return '';if(upload.quote(url))return url;else return '${_img_ftp.ftpUrl}'+url;}
upload.uploadFile=function(n){
	var of=$("#uploadFile"+n);
    if(of.val()==""){alert("请选择文件");return;}
    $("#fileContent").empty();//将file移动到上传表单
    $("#fileContent").append(of);//复制一个file放至原处
    $("#ufc"+n).append(of.clone().attr("value",""));
    $("#uploadFileText"+n).attr("value","");//修改属性
    of.attr("id","site_file_upload");
    of.attr("name","site_file");
    $("#ufFileName").val($("#fileName"+n).val());//其他表单
    $("#upPrevFile").val($("#prevFile"+n).val());
    $("#ufMark").val(upload.G("mark"+n).checked); //先清除
    $("#uploadNum").val(n); 
    $("#upload_file_Form").submit();
    openDiv("alertDivImage","…",60,210);
}
//浏览、选择文件按钮
upload.browseHtml=function(n,nonePath){
    return htm.span(false,"ufc"+n,'','','','position:relative;float:left;')+htm.input("uploadFileText"+n,"","ipt",nonePath)+
    htm.input('uploadFile'+n,'file','file-button','','onchange="$('+'\''+"#uploadFileText"+n+'\''+').val(this.value);"')+htm.input("browse_"+n,"button","browse","浏览")+htm.span(true);
}
//是否添加水印
upload.markHtml=function(n,mark){
	var _mark=htm.lable(false,"mark_lable"+n,'lbl-ipt-tit checkBoxWrap','','','color:brown;width:38px;padding-right:0;')+
             htm.input("mark"+n,"checkbox","","","")+htm.span(false,"",'','水印','style="font-size:10px;font-weight: 100;"')+htm.span(true)+htm.lable(true);
    if(!mark)_mark=htm.input("mark"+n,"checkbox",'','','','display: none');
    return _mark;
}
//上传按钮
upload.uploadHtml=function(n,defVal){
	return htm.input("","button","upload-button","上传",'onclick="upload.uploadFile('+n+');"');
}
//图片预览按钮
upload.previewHtml=function(n){
    return htm.input('preview_button'+n,'button','preview-button','预览','onclick="upload.previewImg('+n+');" href="#show_image_upload'+n+'"');
}
//记录交替上传过程中的值，避免恶意占用服务器空间
upload.originalHtml=function(n){
	return htm.input('original'+n,'hidden','','defalut');
}
//表单提交信息
upload.submitBtn=function(n,value,btnName){
    return	htm.input('file_path'+n,'hidden','',value,'','',btnName);
}
//原文件路径,用于记录编辑时的文件路径/[在交替上传过程中产生]
upload.realFile=function(n,value){
	return htm.input('file_real_val'+n,'hidden','',value,'','','prevFile')+htm.input('prev_file'+n,'hidden');
}
//预览文件DIV
upload.divHtml=function(n,img_src){
	return htm.div(false,'','','display:none;')+htm.div(false,'show_image_upload'+n)+'<img id="show_img'+n+'" src="'+img_src+'"/></div></div>';
}
//引用按钮
upload.quoteHtml=function(n){
	return htm.input('quote_btn'+n,'button','upload-button','引用','onclick="upload.addQuoteIMG('+n+');" href="#quote_img_path"');
}
//删除按钮
upload.deleteBtn=function(n){
	return htm.input('','button','upload-button','删除','onclick="upload.delFile('+n+');"');
}
//提示
upload.iptHtml=function(){
	return htm.span(false,'','txt','图片限制:{小于5M，宽700px(大于此宽度会导致页面排版错误或失真)}','','color: green;');
}
//更多
upload.more=function(id,btnName,more,maxMore,n){
	return htm.input('','button','','╋','onclick="upload.addBtn(\''+id+'\',\''+''+'\',\''+btnName+'\','+false+',\''+
	 ((upload.G("maxMore")!=null)?parseInt(upload.G("maxMore").value)+1:(n+1))+'\','+more+','+true+','+maxMore+');"')+
	 htm.input('maxMore','hidden','','1');
}
//删除更多
upload.delMore=function(id,n){
	return '<input type="button" value="▬" onclick="upload.delMoreHtml(\''+id+'\',\''+n+'\');">';
}

upload.addBtn=function(id,defVal,btnName,mark,n,more,moreBtn,maxMore){
    if(upload.G("maxMore").value>=maxMore)
    	return;
    n=parseInt(upload.G("maxMore").value)+1;
    upload.G("maxMore").value=n;
    var nonePath=defVal.substr(defVal.lastIndexOf("/")+1,defVal.length);
    if(upload.quote(defVal)){nonePath=defVal;}
    
    if(more&&moreBtn){
    	upload.G("del_more_"+n).style.clear="both";
        upload.G("del_more_"+n).style.padding="0px 0px 0px 110px";
        upload.G("del_more_"+n).innerHTML=upload.browseHtml(n,nonePath)+upload.markHtml(n,mark)+
                    upload.uploadHtml(n,defVal)+upload.previewHtml(n)+upload.originalHtml(n)+
                    upload.submitBtn(n,defVal,btnName)+upload.realFile(n,defVal)+upload.divHtml(n,upload.addDomain(defVal))+
                    upload.quoteHtml(n)+upload.deleteBtn(n)+upload.delMore(id,n)+upload.iptHtml();
        upload.G("real_file_div"+n).innerHTML=upload.realFile(n,defVal);
    }
    else{
    	upload.G(id).innerHTML=upload.browseHtml(n,nonePath)+upload.markHtml(n,mark)+
    	            upload.uploadHtml(n,defVal)+upload.previewHtml(n)+upload.originalHtml(n)+
    	            upload.submitBtn(n,defVal,btnName)+upload.realFile(n,defVal)+upload.divHtml(n,upload.addDomain(defVal))+
    	            upload.quoteHtml(n)+upload.deleteBtn(n)+upload.iptHtml();
    }
}

upload.btn=function(id,defVal,btnName,mark,more,maxMore){
	var n=id;
	var moreValue;
    if(more){
        moreValue=eval(defVal);
        if(moreValue!=null&&moreValue[0]!=null)
        defVal=moreValue[0].path;
    }
    if(upload.blank(maxMore))maxMore=5;
    
    if(upload.blank(maxMore)){maxMore=1;}
    var nonePath=defVal.substr(defVal.lastIndexOf("/")+1,defVal.length);//显示文本框地址
    if(upload.quote(defVal)){nonePath=defVal;}
    var allHtml=upload.browseHtml(n,nonePath)+upload.markHtml(n,mark)+upload.uploadHtml(n,defVal)+
                upload.previewHtml(n)+upload.originalHtml(n)+upload.submitBtn(n,defVal,btnName)+
                upload.realFile(n,defVal)+upload.divHtml(n,upload.addDomain(defVal))+
                upload.quoteHtml(n)+upload.deleteBtn(n)+upload.more(id,btnName,more,maxMore,n)+upload.iptHtml();
    if(more){
    	var moresHtml="";
    	var realImgDivHtml="";
    	for(var i=0;i<(maxMore-1);i++){
    		moresHtml+=htm.div(false,'del_more_'+(n+i+1),'','','')+htm.div(true);
    		realImgDivHtml+="<div id='real_file_div"+(n+i+1)+"'></div>";
    	}
    	upload.G(id).innerHTML='<div id="del_more_'+n+'">'+allHtml+htm.div(true)+moresHtml+htm.div(false,'real_file_div'+n,'','','')+upload.realFile(n,defVal)+htm.div(true)+realImgDivHtml;
        for(var i=1;i<moreValue.length;i++){
        	upload.addBtn(id,defVal,btnName,mark,n,more,true,maxMore);
        }
    }
    else{
    	console.log("upload.browseHtml:"+upload.browseHtml(n,nonePath));
    	console.log("upload.markHtml:"+upload.markHtml(n,mark));
    	console.log("upload.uploadHtml:"+upload.uploadHtml(n,defVal));
    	console.log("upload.previewHtml:"+upload.previewHtml(n));
    	console.log("upload.originalHtml:"+upload.originalHtml(n));
    	console.log("upload.submitBtn:"+upload.submitBtn(n,defVal,btnName));
    	console.log("upload.previewHtml:"+upload.previewHtml(n));
    	console.log("upload.divHtml:"+upload.divHtml(n,upload.addDomain(defVal)));
    	console.log("upload.quoteHtml:"+upload.quoteHtml(n));
    	console.log("upload.deleteBtn:"+upload.deleteBtn(n));
    	upload.G(id).innerHTML=upload.browseHtml(n,nonePath)+upload.markHtml(n,mark)+
    	                       upload.uploadHtml(n,defVal)+upload.previewHtml(n)+
    	                       upload.originalHtml(n)+upload.submitBtn(n,defVal,btnName)+
    	                       upload.realFile(n,defVal)+upload.divHtml(n,upload.addDomain(defVal))+
    	                       upload.quoteHtml(n)+upload.deleteBtn(n)+upload.iptHtml();
    	                       
    }
}
//-->
</script>

<form id="upload_file_Form" action="${path}/front/fileUpload!uploadFileToTemp.action" method="post" enctype="multipart/form-data" target="hiddenIframe" style="display:none;width:0px;height:0px;">   
    <span id="fileContent"></span>
    <input id="ufFileName" type="hidden" name="fileName"/>
    <input id="ufMark" type="hidden" name="mark"/>
    <input id="uploadNum" type="hidden" name="uploadNum"/>
    <input id="upPrevFile" type="hidden" name="upPrevFile"/>
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
<!--上传控件结束 -->