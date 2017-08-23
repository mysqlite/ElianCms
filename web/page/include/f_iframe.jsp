<%@ page language="java" pageEncoding="UTF-8"%>
<head>
    <script type="text/javascript">
    var id='${divId}';
    var n='${n}';
    if('${errMsg}'!=""){
    	 alert('${errMsg}');
         parent.upload.G(id+"_show_text_"+n).value="";
    	 parent.closeDiv();
    }
	else{
	   parent.closeDiv();
       parent.upload.G(id+"_path_"+n).value='${uploadImgInfo[0]}';//预存储路径
       parent.upload.G(id+"_show_text_"+n).value='${uploadImgInfo[2]}';//显示值
       parent.upload.G(id+"_original_"+n).value="update";
       parent.upload.G(id+"_show_img_"+n).src=parent.upload.addDomain('${uploadImgInfo[1]}');//临时显示，做预览用
       parent.upload.G(id+"_temp_src_"+n).value='${uploadImgInfo[1]}';
	}   
	</script>
</head>
