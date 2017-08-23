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
		<link rel="stylesheet" href="${path}/css/demo.css" type="text/css">
		<link rel="stylesheet" href="${path}/css/zTreeStyle/zTreeStyle.css" type="text/css">
		<link rel=stylesheet type=text/css href="${path}/css/manage/base.css">
        <link rel=stylesheet type=text/css href="${path}/css/manage/admin.css">
		<script type="text/javascript" src="${path}/js/jquery.js"></script>
		<script type="text/javascript" src="${path}/js/jquery.ztree.core-3.5.js"></script>
	    <script type='text/javascript' src='${path}/dwr/util.js'></script>
	</head>
	<body class="menu" style="overflow: hidden">
		<div class="body" id="body">
		     <div class="main-top-hd clearfix"><br/><span id="msg"></span></div>
			<div class="wrap_dpBtn" style="padding-left:3px;">
				<span id="open" class="displayBtn" onclick="openAll();">显示全部</span>
	            <span id="close" class="displayBtn" style="display: none" onclick="closeAll();">关闭全部</span>
	            <span id="shua" class="displayBtn" style="color:green;" onclick="parent.submenuFrame.location.reload()">刷新</span>
	            <span id="closeMenus" class="displayBtn" style="color:green;" onclick="changeMenu();">关闭菜单</span>
	            <span id="openMenus" class="displayBtn" style="color:green;display: none" onclick="changeMenu();">显示菜单</span>   	
			</div>
			<span class="txt" style="color:green">医院：</span>
              <input type="text" id="hospName" style="width:130px;"/>
              <input type="button" id="searchHosp" value="查找" onclick="searchHosp();">
              <div></div>
			<span class="txt" style="color:red">省份：</span>
              <select class="listbar-sel" id="proId" onchange="initSearchHosp();" style="width:130px;">
                <option value="0" style="color:green">→请选择省份←</option>
              </select>
              <div></div>
			<div class="content_wrap" style="overflow-y: auto;height: 460px;">
				<div class="zTreeDemoBackground left">
					<ul id="treeDemo" class="ztree"></ul>
				</div>
			</div>
		</div>
	</body>
	<script type="text/javascript">
	<!--
	var core={};
        core.getJson=function(url,f,paramData) {$.ajax({url: url,type: "POST",dataType: 'json',data: (paramData),async:false,cache:false,timeout: 10000,success:f});}
	var isLoad=false;
	G = function(id) {return document.getElementById(id);}
    initSetting=function(){
         var setting = {
                check: { enable: true},
                data: {simpleData: { enable: true}},
                view: {dblClickExpand: false,fontCss: getFontCss},
                async: {
                    enable: true,
                    url:'${path}/admin/hospital!areaHospitalTree.action',
                    dataType: "text",
                    type: "post",
                    dataFilter: ajaxDataFilter,
                    autoParam:["paramId"],
                    otherParam:{"requType":"depart","hospName":G("hospName").value}
                },
                callback: {
                    onAsyncSuccess: onAsyncSuccess,
                    onAsyncError:zTreeOnAsyncError
                   }
            };
         return setting;
     };
	function initTree(){
    	var setting=initSetting();
		var zNodes =[];
		var code=G("proId").value;
		if(code==0)
			code='${_substation.areaId}';
		core.getJson('${path}/admin/area!areaList.action',function(data){
			var area=data.area;
			var icons='${path}/css/zTreeStyle/img/diy/1_close.png';
            for(var i=0,len=area.length;i<len;i++){
                   zNodes[i]={
                    id:area[i].areaCode,
                    pId:area[i].parentCode,
                    name:area[i].areaName,
                    isParent:true,
                    paramId:'area:'+area[i].areaCode,
                    icon:icons
                };
            }
         $.fn.zTree.init($("#treeDemo"), setting, zNodes);
		},{"areaCode":code});
        }
	function ajaxDataFilter(treeId, parentNode, data) {
	    var array = [];
	    var urls="";
	    var links="javaScript:void(0);";
	    var AREA="area",HOSP="hosp",DEPART="depart",DOCTOR="doctor";
	    var icons='';
	    for(var i=0; i<data.length; i++){    
	    	icons='${path}/css/zTreeStyle/img/diy/';
	    	if(data[i].type==AREA){
	    		urls='${path}/admin/hospital!list.action?ztree=true&areaCode='+data[i].id;
	    		icons=icons+"chengshi.png";
	    		}
	    	else if(data[i].type==HOSP){
	    	    urls='${path}/admin/department!list.action?ztree=true&hospId='+data[i].id;
	    	    icons=icons+"yiyuan.png";
	    	    }
	    	else if(data[i].type==DEPART){
	    		urls='${path}/admin/doctor!list.action?ztree=true&departId='+data[i].id;
	    		icons=icons+"keshi.png";
	    		}
	    	else if(data[i].type==DOCTOR){
	    		urls='${path}/admin/doctor!edit.action?ztree=true&id='+data[i].id;
	    		icons=icons+"yisheng.png";
	    		}
	        array[i] = {
	        	id:data[i].id,name:data[i].name,pId:parentNode.id,isParent:data[i].isParent,open:data[i].open,
	        	paramId:data[i].type+":"+data[i].id,target : 'hospFrame',url:urls,icon:icons,requType:"depart"
	        };        
	    }
	    return array;
    }
	
	
    function initSearchHosp(){
        initTree();
        if(G("hospName").value!=null&&G("hospName").value!=""){
            setTimeout(asyncAll,100);
        }
    }
    var searchHospName="",savesearchname;
    function searchHosp(){
        searchHospName=G("hospName").value;
        if(searchHospName!=savesearchname){
            savesearchname=searchHospName;
            initTree();
            setTimeout(asyncAll,100);
        }else{
        	 var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        	  nodeList = zTree.getNodesByParamFuzzy("name", $.trim(G("hospName").value));
              updateNodes(true);
        }
    }
	
    function onAsyncSuccess(event, treeId, treeNode, msg) {
    	 G("msg").innerHTML="数据加载完毕";
       setTimeout(function(){G("msg").innerHTML="";},500);
    }
    function zTreeOnAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
          G("msg").innerHTML="数据加载失败";
      };
    function asyncAll() {
            var zTree = $.fn.zTree.getZTreeObj("treeDemo");
            var nodes=zTree.getNodes();
            G("msg").innerHTML="数据加载中";
            for (var i=0, l=nodes.length; i<l; i++) {
              zTree.reAsyncChildNodes(nodes[i],null, false);
            }
     }
    
    function getFontCss(treeId, treeNode) {
            return (!!treeNode.highlight) ? {color:"#A60000", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};
        }
    function focusKey(e) {
        if (key.hasClass("empty")) {
            key.removeClass("empty");
        }
    }
    function blurKey(e) {
        if (key.get(0).value === "") {
            key.addClass("empty");
        }
    }
    var lastValue = "", nodeList = [], fontCss = {};
    function clickRadio(e) {
        lastValue = "";
        searchNode(e);
    }
    function searchNode(e) {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
            var value = $.trim(G("hospName").value);
            var keyType = "name";
            if (key.hasClass("empty"))value = "";
            if (lastValue === value) return;
            lastValue = value;
            if (value === "") return;
            updateNodes(false);
            nodeList = zTree.getNodesByParamFuzzy(keyType, value);
            updateNodes(true);
    }
    function updateNodes(highlight) {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        for( var i=0, l=nodeList.length; i<l; i++) {
            nodeList[i].highlight = highlight;
            zTree.updateNode(nodeList[i]);
        }
    }
    key = $("#hospName");
    key.bind("focus", focusKey).bind("blur", blurKey).bind("propertychange", searchNode).bind("input", searchNode);
	G("hospName").onkeypress=function(e){
        var ie=navigator.appName=='Microsoft Internet Explorer'?true:false;
        if(!ie) var key = e.which;
        else var key = event.keyCode;
        if(key==13){
            if(G("proId").value>0)
                 searchHosp();
            else{
                G("msg").innerHTML="请选择省份";
                G("msg").style.color="red";
                }
        }
    }
    //-->
	</script>
	<script type="text/javascript">
	    function onloadArea(){
	    	      if(!isLoad){
	    	        var thisAreaCode='${_substation.areaId}';
	    	        core.getJson('${path}/admin/area!areaList.action',function(data){
	    	          var area=data.area;
    	        	dwr.util.removeAllOptions('proId');
                       for(var i=0,len=area.length;i<len;i++){
                           dwr.util.addOptions('proId',[{name:area[i].areaName,id:area[i].areaCode}],"id","name");
                           dwr.util.setValue('proId',thisAreaCode);
                       }
	    	        });
                  isLoad=true; 
                  initTree();
	    	      }
         }
	    onloadArea();
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