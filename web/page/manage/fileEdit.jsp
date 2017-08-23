<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Ajax File Upload</title>
</head>
<style type="text/css">
    body {
        font-family: Verdana, Arial, sans-serif;
        font-size:90%;
    }
    h1, h2, h3, h4 {
        margin-top: 0px;
    }
    div.row {
        margin-bottom: 10px;
    }
    *.focus {
        outline: none;
    }
    .floatLeft {
        float: left;
    }
    .floatRight {
        float: right;
    }
    .clear {
        clear: both;
    }
    form {
        padding: 20px;
        border: 1px solid #cccccc;
        border-radius:10px;
        -moz-border-radius:10px;
        -webkit-box-shadow:0 0 10px #ccc;
        box-shadow: 0 0 10px #ccc;
        width:400px;
        margin:20px auto;
        background-image:-moz-linear-gradient(top,#ffffff,#f2f2f2);
        background-image:-webkit-gradient(linear,left top,bottom,from(#ffffff),to(#f2f2f2));
    }
    input {
        border:1px solid #ccc;
        font-size: 13pt;
        padding: 5px 10px 5px 10px;
        border-radius:10px;
        -moz-border-radius: 10px;
        -webkit-transition: all 0.5s ease-in-out;
        -moz-transition: all 0.5s ease-in-out;
        transition: all 0.5s ease-in-out;
    }
    input[type=button] {
        background-image: -moz-linear-gradient(top, #ffffff, #dfdfdf);
        background-image: -webkit-gradient(linear, left top, left bottom, from(#ffffff), to(#dfdfdf));
    }
    input:focus {
        -webkit-box-shadow:0 0 10px #ccc;
        -moz-box-shadow: 0 0 10px #ccc;
        box-shadow: 0 0 5px #ccc;
        -webkit-transform: scale(1.05);
        -moz-transform: scale(1.05);
        transform: scale(1.05);
    }
    #file {
        width: 378px;
    }
    #progressIndicator {
        font-size: 10pt;
    }
    #fileInfo {
        font-size: 10pt;
        font-style: italic;
        color: #aaa;
        margin-top: 10px;
    }
    #progressBar {
        height: 14px;
        border: 1px solid #cccccc;
        display: none;
        border-radius:10px;
        -moz-border-radius: 10px;
        background-image: -moz-linear-gradient(top, #66cc00, #4b9500);
        background-image: -webkit-gradient(linear, left top, left bottom, from(#66cc00), to(#4b9500));
        background-color: #4b9500;
    }
    #uploadResponse {
        margin-top: 10px;
        padding: 20px;
        overflow: hidden;
        display: none;
        border-radius:10px;
        -moz-border-radius: 10px;
        border: 1px solid #ccc;
        box-shadow: 0 0 5px #ccc;
        background-image: -moz-linear-gradient(top, #ff9900, #c77801);
        background-image: -webkit-gradient(linear, left top, left bottom, from(#ff9900), to(#c77801));
        background-color: #ff9900;
    }
</style>
<body>
<form id="form" enctype="multipart/form-data" method="post" action="${path}/upload">
  <div class="row">
    <input type="file" name="file" id="file"/>
    <input type="button" id="upload" value="上传" />
  </div>
  <div id="fileInfo">
    <div id="fileName"></div>
    <div id="fileSize"></div>
    <div id="fileType"></div>
  </div>
  <div class="row"></div>
  <div id="progressIndicator">
    <div id="progressBar" class="floatLeft"></div>
    <div id="progressNumber" class="floatRight"></div>
    <div class="clear"></div>
    <div>
      <div id="transferBytesInfo" class="floatRight" style="text-align: right;"></div>
      <div class="clear"></div>
    </div>
    <div id="uploadResponse"></div>
  </div>
</form>
<script type="text/javascript" src="${path}/js/manage/jsupload.js"></script>
<script type="text/javascript">
REQU=function(){if(window.ActiveXObject){return new ActiveObject("Microsoft.XMLHTTP"); }else if(window.XMLHTTPRequest){return new XMLHTTPRequest();}};
G=function(id){return document.getElementById(id);} ;
;(function(document){
	var file = G('file'),
	validUrl = '${path}/fileMessage',
	uploadUrl = '${path}/upload',
	progressUrl = '${path}/uploadProgress',
	upload = G('upload'),
	progressBar = G('progressBar'),
	progressNumber = G('progressNumber'),
	transferBytesInfo = G('transferBytesInfo'),
	uploadResponse = G('uploadResponse'),
	jsUpload = new JsUpload({name:'__jsupload__',file:file,validUrl:validUrl,uploadUrl:uploadUrl,progressUrl:progressUrl});
	JsUpload.bind(file,'change',function(event){
		progressBar.style.display = 'none';
		progressNumber.style.display = 'none';
		transferBytesInfo.style.display = 'none';
	    uploadResponse.style.display = 'none';
		jsUpload.change(this,function(name,size,type){
			JsUpload.get('fileName').innerHTML = '名称：' + name+";&nbsp;&nbsp;大小:"+size;
		});
	});
	JsUpload.bind(upload,'click',function(event){
		progressBar.style.display = 'none';
		progressNumber.style.display = 'none';
		transferBytesInfo.style.display = 'none';
		uploadResponse.style.display = 'none';
		jsUpload.upload(this,file,function(cur,args1,args2){
			switch(cur){
				case 'progress':
					//显示上传文件进度条
					progressBar.style.display = 'block';
					progressNumber.style.display = 'block';
					transferBytesInfo.style.display = 'block';
				    progressBar.style.width = '0px';
				    progressNumber.innerHTML = args1 + '%';
	          		progressBar.style.width = args1 * 3.55 + 'px';
	          		transferBytesInfo.innerHTML = args2;
					break;
				case 'complete':
					uploadResponse.style.display = 'block';
					break;
				case 'fail':
				case 'load':
				case 'error':
				case 'abort':
					uploadResponse.innerHTML = args1;
					uploadResponse.style.display = 'block';
					break;
				default:
					break;
			}
		});
	});
})(document);
</script>
</body>
</html>