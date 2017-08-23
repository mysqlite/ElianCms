<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML >
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html lang="zh-CN">
	<head>
		<title>医院树菜单</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<link rel=stylesheet type=text/css href="${path}/css/manage/base.css">
        <link rel=stylesheet type=text/css href="${path}/css/manage/admin.css">
        <link rel="stylesheet" href="${path}/css/zTreeStyle/zTreeStyle.css" type="text/css">
		<script type="text/javascript" src="${path}/js/jquery.js"></script>
		<script type="text/javascript" src="${path}/js/jquery.ztree.core-3.5.js"></script>
	</head>
	<body class="menu" style="overflow: hidden">
		<div class="body" id="body">
		    <div class="main-top-hd clearfix"></div>
			<div class="wrap_dpBtn" style="padding-left:3px;">
				<span id="open" class="displayBtn" onclick="openAll();">显示全部</span>
	            <span id="close" class="displayBtn" style="display: none" onclick="closeAll();">关闭全部</span>
	            <span id="shua" class="displayBtn" style="color:green;" onclick="parent.submenuFrame.location.reload()">刷新</span>
	            <span id="closeMenus" class="displayBtn" style="color:green;" onclick="changeMenu();">关闭菜单</span>
	            <span id="openMenus" class="displayBtn" style="color:green;display: none" onclick="changeMenu();">显示菜单</span>   	
			</div>
			<div class="content_wrap" style="overflow-y: auto;height:460px;">
                <div class="zTreeDemoBackground left" style="height:auto;">
				   <ul id="treeDemo" class="ztree"></ul>
			    </div>
			</div>
		</div>
	</body>
	<script type="text/javascript">
	<!--
	var core={};
        core.getJson=function(url,f,paramData) {$.ajax({url: url,type: "POST",dataType: 'json',data: (paramData),async:false,cache:false,timeout: 10000,success:f});}
        function initTree(){
			var setting = {
		        check: { enable: true},
		        data: {simpleData: { enable: true}},
		        view: {dblClickExpand: false},
		        async: {
		            enable: false,
		            url:'${path}/admin/hospital!areaHospital.action',
		            dataType: "text",
		            type: "get",
		            //contentType:"application/json",
		            dataFilter: ajaxDataFilter,
		            autoParam:["areaCode"],
		            otherParam:{"requType":"hosp"}
		        }
		    };
		var zNodes =[{id:"0", name:"全国", open:true,target : 'hospFrame',url:'${path}/admin/hospital!list.action?areaCode='+0,icon:'${path}/css/zTreeStyle/img/diy/guojia.png'}];
		 core.getJson('${path}/admin/area!areaList.action', function(data) {
			  var area=data.area;
        	  var links="javaScript:void(0);";
        	  var urls='${path}/admin/hospital!list.action?ztree=true&areaCode=';
              for(var i=0,len=area.length;i<len;i++){
                 if(len==1){
                	 zNodes[i]={
                      id:area[i].areaCode,
                      pId:area[i].parentCode,
                      name:area[i].areaName,
                      isParent:false,
                      open:false,
                      areaCode:area[i].areaCode,
                      target : 'hospFrame',
                      url:urls+area[i].areaCode,
                      icon:'${path}/css/zTreeStyle/img/diy/1_close.png'
                  };
                 }else{
            	  zNodes[i+1]={
                	  id:area[i].areaCode,
                	  pId:area[i].parentCode,
                	  name:area[i].areaName,
                	  isParent:false,
                	  areaCode:area[i].areaCode,
                	  target : 'hospFrame',
                      url:urls+area[i].areaCode ,
                      icon:'${path}/css/zTreeStyle/img/diy/1_close.png'
                  };
                }
              }
          $.fn.zTree.init($("#treeDemo"), setting, zNodes);
          });
        }
	function ajaxDataFilter(treeId, parentNode, data) {
	    var array = [];
	    for(var i=0; i<data.length; i++){    
	        array[i] = {pId:parentNode.id, id:data[i].id, name:data[i].name, isParent:false, open:true};        
	    }
	    return array;
    }
	initTree();
	        //-->
	</script>
	<script type="text/javascript">
	    G = function(id) {return document.getElementById(id);}
	   function onloadMenus(){
		    var $main = $("#main");
	        var mainFrameset = window.parent.window.parent.window.document.getElementById("mainFrameset");
	            if(mainFrameset.cols == "220,6,*"||mainFrameset.cols == "220,6,*") {
	            	G("closeMenus").style.display="";
                    G("openMenus").style.display="none";
	            }else{
	            	G("closeMenus").style.display="none";
                    G("openMenus").style.display="";
	            }
	   }
	   onloadMenus();
       function changeMenu(){
        var $main = $("#main");
        var mainFrameset = window.parent.window.parent.window.document.getElementById("mainFrameset");
            if(mainFrameset.cols == "220,6,*"||mainFrameset.cols == "220,6,*") {
            	G("closeMenus").style.display="none";
                G("openMenus").style.display="";
                mainFrameset.cols = "0,6,*";
                $main.removeClass("leftArrow");
                $main.addClass("rightArrow");
                var menuFrame = window.parent.window.parent.window.document.getElementById("menuFrame")//.getElementById("body");
            } else{
            	G("closeMenus").style.display="";
                G("openMenus").style.display="none";
                mainFrameset.cols = "220,6,*";
                $main.removeClass("rightArrow");
                $main.addClass("leftArrow");
            }
        }
       
       function closeLeftMenu(){
        var $main = $("#main");
        var mainFrameset = window.parent.window.parent.window.document.getElementById("mainFrameset");
            G("closeMenus").style.display="";
            G("openMenus").style.display="none";
            mainFrameset.cols = "220,6,*";
            $main.removeClass("rightArrow");
            $main.addClass("leftArrow");
        }
        function openAll(){
            G("open").style.display="none";
            G("close").style.display="";
          var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
            treeObj.expandAll(true);
        } 
        function closeAll(){
            G("close").style.display="none";
            G("open").style.display="";
          var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
            treeObj.expandAll(false);
        }
    </script>
</html>