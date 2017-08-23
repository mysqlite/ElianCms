<%@ page language="java" pageEncoding="UTF-8"%>
<head>
    <script type="text/javascript">
    var n=parent.G("uploadNum").value;
    if('${errMsg}'!=""){
    	 alert('${errMsg}');
    	 parent.G("uploadImgPath"+n).value="";
         parent.G("uploadFileText"+n).value="";
    	 parent.closeDiv();
    }
	else{
	   parent.closeDiv();
	   var noneFileUrl='${uploadImgInfo[0]}';
	   var fileUrl='${uploadImgInfo[1]}';
	   var realname='${uploadImgInfo[2]}';
       parent.G("imagePath"+n).value=noneFileUrl;
       parent.G("uploadImgPath"+n).value=fileUrl;
       parent.G("prevImg"+n).value=fileUrl;
       parent.G("uploadFileText"+n).value=realname;
       parent.G("originalPicture"+n).value="update";
       if(parent.G("originalPicture"+n).value=="defalut"){
         parent.G("showImageUpload"+n).src='${_img_ftp.ftpUrl}'+parent.G("imagePath"+n).value;
       }
       else{
         parent.G("showImageUpload"+n).src='${_img_ftp.ftpUrl}'+parent.G("uploadImgPath"+n).value;
       }
	}   
	</script>
</head>
