/*
inputType:标签类型（radio及checkbox）
inputId：显示内容的标签id
noneInputId：隐藏标签的Id
divId：
*-------------
<div id="menuContent" class="menuContent" style="display:none; position: absolute;">
                    <ul id="treeDemo" class="ztree" style="margin-top:0; width:180px; height: 300px;"></ul>
</div>
*--------------以上标签加入需要应用的页面
*/
	  if("undefined"==menuContents){
   this.menuContent="menuContent";
	   }else{
   var menuContent=menuContents;
		}
   var inId =inputId;
   var dId=divId;
   var noneId=noneInputId;
   var setting={
		check : {
			enable : true,
			chkStyle :inputType,
			radioType : "all"
		},
		view : {dblClickExpand : false},
		data : {simpleData : {enable : true}},
		callback : {
			onClick :onClick,
			onCheck :onCheck
		}
	};
G=function(id){return document.getElementById(id);};
G(inId).onclick=function(){
	showMenu();
	return false;
};
function onClick(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj(dId);
	zTree.checkNode(treeNode, !treeNode.checked, null, true);
	return false;
};
function onCheck(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj(dId), nodes = zTree.getCheckedNodes(true), v = "",ids = "";
	for ( var i = 0, l = nodes.length; i < l; i++) {
		v += nodes[i].name + ",";
		ids += nodes[i].id + ",";
	}
	if (v.length > 0) {
		v = v.substring(0, v.length - 1);
		ids = ids.substring(0, ids.length - 1);
	}
	var cityObj = $("#"+inId);
		cityObj.attr("value", v);
    var pid = $("#"+noneId);
		pid.attr("value", ids);
	if(treeNode.checked){
		hideMenu();
		}
		
};
function showMenu() {
	var cityObj = $("#"+inId);
	var cityOffset = $("#"+inId).offset();
	$("#"+menuContent).css( {
		left : cityOffset.left + "px",
		top : cityOffset.top + cityObj.outerHeight() + "px"
	}).slideDown("fast");
	$("body").bind("mousedown", onBodyDown);
};
function hideMenu(){
	$("#"+menuContent).fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown);
};
function onBodyDown(event){
	if (!(event.target.id =="menuBtn"||event.target.id == inId||event.target.id ==menuContent||$(event.target).parents("#"+menuContent).length > 0)) 
	{
		hideMenu();
	}
};
function loading(obj) {
	$(document).ready(function(){
		$.fn.zTree.init($("#"+dId),setting,obj);
	});
};
