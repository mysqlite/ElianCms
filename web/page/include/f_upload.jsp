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
htm.toStr=function(str){if(upload.blank(str))return "'"+''+"'"; else return "'"+str+"'";}
upload.G=function(id){return document.getElementById(id);};
upload.blank=function(str){if(str==null||str==undefined||str==""){return true;}else{return false;}};
upload.quote=function (url){if(url.indexOf("https")==0||url.indexOf("http")==0||url.indexOf("ftp")==0||url.indexOf("rtsp")==0||url.indexOf("mms")==0)return true;else return false;}
upload.addDomain=function(url){if(upload.blank(url))return '';else if(url=='${_img_ftp.ftpUrl}')return '';if(upload.quote(url))return url;else return '${_img_ftp.ftpUrl}'+url;}
upload.uploadFile=function(id,n,file){
	$('#'+id+'_show_text_'+id).val($('#'+id+'_file_'+n).val());
	var of=$('#'+id+'_file_'+n);
    if(of.val()==""){alert("请选择文件");return;}
    $('#fileContent').empty();//将file移动到上传表单
    $('#fileContent').append(of);//复制一个file放至原处
    $('#'+id+'_ufc_'+n).append(of.clone().attr("value",""));
    $('#'+id+'_show_text_'+n).attr("value","");//修改属性
    of.attr("id","site_file_upload");
    of.attr("name","site_file");
    if(upload.G(id+"_original_"+n).value!='defalut')
        $("#upPrevFile").val(upload.G(id+"_temp_src_"+n).value); //将临时路径传回供删除
    if(!upload.blank(upload.G(id+"_mark_"+n)))
        $("#ufMark").val(upload.G(id+"_mark_"+n).checked); //先清除
    else 
    	$("#ufMark").val(false);
    $("#uploadNum").val(n); 
    $("#divId").val(id);
    $("#is_file").val(file?true:false);
    $("#upload_file_Form").submit();
    openDiv("alertDivImage","…",60,210);
}
/*浏览、选择文件按钮*/
upload.browseHtml=function(id,n,nonePath,file){
    return htm.span(0,id+"_ufc_"+n,'','','','position:relative;float:left;')+htm.input(id+"_show_text_"+n,"","ipt",nonePath)+
    htm.input(id+'_file_'+n,'file','file-button','','onchange="upload.uploadFile('+htm.toStr(id)+','+n+','+file+');"')+htm.input(id+"_browse_"+n,"button","browse","浏览")+htm.span(true);
}
//是否添加水印
upload.markHtml=function(id,n,mark){
	if(!mark)return '';
	return htm.lable(0,id+"_mark_lable"+n,'lbl-ipt-tit checkBoxWrap','','','color:brown;width:38px;padding-right:0;')+
             htm.input(id+"_mark_"+n,"checkbox","","","")+htm.span(0,"",'','水印','style="font-size:10px;font-weight: 100;"')+htm.span(1)+htm.lable(1);
}
//图片预览按钮
upload.showHtml=function(id,n){
    return htm.input(id+'_show_'+n,'button','preview-button','预览','onclick="upload.preview('+htm.toStr(id)+','+n+');" href="#'+id+'_show_img_div_'+n+'"');
}
//记录交替上传过程中的值，避免恶意占用服务器空间
upload.originalHtml=function(id,n){
	return htm.input(id+'_original_'+n,'hidden','','defalut')+htm.input(id+'_temp_src_'+n,'hidden');
}
//表单提交信息
upload.submitBtn=function(id,n,value,btnName){return	htm.input(id+'_path_'+n,'hidden','',value,'','',btnName);}
//原文件路径,用于记录编辑时的文件路径/[在交替上传过程中产生]
upload.realFile=function(value){return htm.input('','hidden','',value,'','','prevFile');}
//预览文件DIV
upload.divHtml=function(id,n,img_src){
	return htm.div(0,'','','display:none;')+htm.div(0,id+'_show_img_div_'+n)+'<img id="'+id+'_show_img_'+n+'" src="'+img_src+'"/></div></div>';
}
//引用_删除按钮
upload.quoteHtml=function(id,n){
	return htm.input(id+'_quote_'+n,'button','upload-button','引用','onclick="upload.quoteFile('+htm.toStr(id)+","+n+');" href="#quote_img_path"');
}
//删除按钮
upload.deleteBtn=function(id,n){
	return htm.input('','button','del-button','删除','onclick="upload.delFile('+htm.toStr(id)+','+n+');"');
}
//提示
upload.iptHtml=function(txt){
	return htm.span(0,'','txt',upload.blank(txt)?'温馨提示:{小于5M，宽700px(大于此宽度会导致页面排版错误或失真)}':txt,'','color: green;')+'</span>';
}
//更多
upload.more=function(id,btnName,mark,maxMore){
	return htm.input('','button','','╋','onclick="upload.addBtn('+htm.toStr(id)+','+htm.toStr(btnName)+','+mark+','+maxMore+');"');
}
//删除更多
upload.delMore=function(id,n){
	return '<input type="button" value="▬" onclick="upload.delBtn('+htm.toStr(id)+','+htm.toStr(n)+');">';
}

upload.delBtn=function(id,n){
	upload.G(id+'_more_div_'+n).innerHTML='';
	upload.G(id+"_more_n").value=parseInt(upload.G(id+"_more_n").value-1);
}

upload.addBtn=function(id,btnName,mark,maxMore){
    if(upload.G(id+"_more_n").value>=maxMore)
    	return;
    var n=parseInt(upload.G(id+"_more_n").value)+1;
    upload.G(id+"_more_n").value=n;
    
    upload.G(id+"_more_div_"+n).innerHTML=upload.browseHtml(id,n,'',false)+upload.markHtml(id,n,mark)+
                upload.showHtml(id,n)+upload.originalHtml(id,n)+upload.realFile('')+
                upload.submitBtn(id,n,'',btnName)+upload.divHtml(id,n,'__###__')+
                upload.quoteHtml(id,n)+upload.deleteBtn(id,n)+upload.delMore(id,n);
}
upload.allHtml=function(id,n,defVal,mark,btnName,file){
	var nonePath=defVal.substr(defVal.lastIndexOf("/")+1,defVal.length);//显示文本框地址
	if(upload.quote(defVal)){nonePath=defVal;}
	return upload.browseHtml(id,n,nonePath,file)+upload.markHtml(id,n,mark)+upload.showHtml(id,n)+upload.originalHtml(id,n)+upload.submitBtn(id,n,defVal,btnName)+
	        upload.divHtml(id,n,upload.blank(upload.addDomain(defVal))?'__###__':upload.addDomain(defVal))+upload.quoteHtml(id,n)+upload.deleteBtn(id,n);
	 
}

upload.btn=function(id,defVal,btnName,mark,more,maxMore,file){
	file=upload.blank(file)?false:file;
	var n=1,more_n=1;
	var moreValue;
	var htmls='',fileDivHtmls='';
    $("#"+id).attr("style",'float:left;')
    if(more){
    	if(upload.blank(maxMore))maxMore=5;
    	moreValue=eval(defVal);
        if(!upload.blank(moreValue)&&moreValue[0]!=null){
             defVal=moreValue[0].path;more_n=moreValue.length;
        }
    	for(var i=0;i<maxMore;i++){
    		fileDivHtmls+=htm.div(false,id+'_file_div_'+(n+i),'','clear: both;')+'</div>';
    	}
    	upload.G(id).innerHTML=fileDivHtmls+htm.input(id+'_more_n','hidden','',more_n);
    	for(var i=0;i<maxMore;i++){
    		htmls='';
    		htmls+=htm.div(0,id+'_more_div_'+(n+i));
    		if(!upload.blank(moreValue)&&!upload.blank(moreValue[i])){
    			htmls+=upload.allHtml(id,(n+i),moreValue[i].path,mark,btnName,file);
    			if(i==0) htmls+=upload.more(id,btnName,mark,maxMore)+upload.iptHtml();
    			else htmls+=upload.delMore(id,(n+i));
    		}
    		else if(upload.blank(moreValue)&&i==0)
    			htmls+=upload.allHtml(id,(n+i),'',mark,btnName,file)+upload.more(id,btnName,mark,maxMore)+upload.iptHtml();
    		htmls+='</div>';
    		if(!upload.blank(moreValue)&&!upload.blank(moreValue[i]))
    			htmls+=htm.div(0,id+'_real_file_'+(n+i))+(file?upload.realFile(moreValue[i].path):upload.realFile(moreValue[i].path))+'</div>';
            else if(upload.blank(moreValue)&&i==0)
                htmls+=htm.div(0,id+'_real_file_'+(n+i))+upload.realFile('')+'</div>';
           upload.G(id+"_file_div_"+(n+i)).innerHTML=htmls;
        }
    	console.log(fileDivHtmls);
    }
    else if(!file)
    	upload.G(id).innerHTML=htm.div(false,id+'_file_div_'+n)+upload.allHtml(id,n,defVal,mark,btnName,file)+upload.realFile(defVal)+upload.iptHtml()+'</div>';
    else if(file){
    	upload.G(id).innerHTML=htm.div(false,id+'_file_div_'+n)+upload.allHtml(id,n,defVal,mark,btnName,file)+upload.realFile(defVal)+upload.iptHtml("支持office、PDF、swf文件上传")+'</div>';
    }
}
/**图片预览*/
upload.preview=function(id,n){
	if(!upload.blank(upload.G(id+'_show_img_'+n).src)&&upload.G(id+'_show_img_'+n).src.indexOf('###')==-1)
		$('#'+id+'_show_'+n).colorbox({slideshow:false,inline:true, width:"auto",hight:"auto",close:"关闭",overlayClose:true});
	else
		alert("暂无图片,上传方可预览");jQuery().colorbox.close();
}
/**引用文件Div*/
upload.quoteFile=function(id,n){
	upload.G('quote_n').value=n;upload.G('quote_id').value=id;
	$("#"+id+'_quote_'+n).colorbox({slideshow:false,inline:true, width:"320px",hight:"70px",close:"关闭",overlayClose:false});
}

/**关闭引用文件DIV*/
upload.closeQuoteDiv=function(){
	var id=upload.G('quote_id').value;
	var n=upload.G('quote_n').value;
	var path=G("quote_path_input").value;
	if(upload.url(path)&&path.length<=255){
		upload.G(id+"_path_"+n).value=path;
		upload.G(id+"_show_text_"+n).value=path;
		upload.G(id+"_show_img_"+n).src=path;
		upload.G("quote_path_input").value='';
		jQuery().colorbox.close();
		upload.G("Img_tip_Msg").innerHTML="*请输入引用图片地址";
	}
	else{
		upload.G("Img_tip_Msg").innerHTML="*引用地址非法,255字以内";
	}
}

upload.delFile=function(id,n){
	upload.G(id+"_show_text_"+n).value="";
	upload.G(id+"_path_"+n).value="";
	upload.G(id+"_show_img_"+n).src="__###__";
}

upload.url=function(url){
	var strRegex = "^((https|http|ftp|rtsp|mms)?://){1,1}"  
        + "?(([0-9a-zA-Z_!~*'().&=+$%-]+: )?[0-9a-zA-Z_!~*'().&=+$%-]+@)?" //ftp的user@  
        + "(([0-9]{1,3}\.){3}[0-9]{1,3}|" // IP形式的URL- 199.194.52.184 允许IP和DOMAIN（域名）  
        + "([0-9a-zA-Z_!~*'()-]+\.)*"+ "([0-9a-zA-Z][0-9a-zA-Z-]{0,61})?[0-9a-zA-Z]\." //域名- www. 二级域名  
        + "[a-zA-Z]{2,6})" + "(:[0-9]{1,4})?" // 端口- :80  
        + "((/?)|" //如果没有文件名，不需要一个斜线  
        + "(/[0-9a-zA-Z_!~*'().;?:@&=+$,%#-]+)+/?)$";  
     var re=new RegExp(strRegex);    //re.test() 
     if (re.test(url)) return true;  else return false;  
}

upload.show=function(id,defVal,more){
	var n=1;var htmls='';var moreValue;
	if(more){
		moreValue=eval(defVal);
		if(!upload.blank(moreValue)){
            for(var i=0;i<moreValue.length;i++){
                htmls+=upload.showHtml(id,n+i)+upload.divHtml(id,n+i,upload.addDomain(moreValue[i].path));
            }
        }
	}
	else{
		 htmls+=upload.showHtml(id,n)+upload.divHtml(id,n,upload.addDomain(defVal));
	}
	upload.G(id).innerHTML=htmls;
}
//-->
</script>

<form id="upload_file_Form" action="${path}/front/fileUpload!uploadFileToTemp.action" method="post" enctype="multipart/form-data" target="hiddenIframe" style="display:none;width:0px;height:0px;">   
    <span id="fileContent"></span>
    <input id="ufFileName" type="hidden" name="fileName"/>
    <input id="ufMark" type="hidden" name="mark"/>
    <input id="uploadNum" type="hidden" name="n"/>
    <input id="upPrevFile" type="hidden" name="upPrevFile"/>
    <input id="upControls" type="hidden" name="controls" value="true"/>
    <input id="divId" type="hidden" name="divId"/>
    <input id="is_file" type="hidden" name="files"/>
    <input id="quote_n" type="hidden"/>
    <input id="quote_id" type="hidden"/>
    <input name="contentAction" id="img_content_action" value="${action}">
    <div id="alertDivImage" style="display:none;">
        <img alt="图片上传中" src="${path}/images/manage/base_loading_bar.gif" style="padding-left:0px;padding-bottom:0px;padding-top:0px;padding-right:0px;">
    </div>
    <div id="quote_img_path" class="listItem"> 
       <input id="quote_path_input"  class="ipt"/><span class="star" id="Img_tip_Msg" style="width:240px;">*请输入引用图片地址</span>
       <br/>
       <div class="buttonArea" style=" width:240px;padding-right:1px;">
          <input class="formButton" value="确  定" onclick="upload.closeQuoteDiv()" type="button" style="float:right;">
       </div>
    </div>
    <div id="val_quote_img_"></div>
    <iframe name="hiddenIframe" frameborder="0" style="display:none;width:0px;height:0px;"></iframe>
</form>
<!--上传控件结束 -->