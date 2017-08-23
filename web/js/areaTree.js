/***
 *	author GeChuanYi
 *  version 1.0
 *  此JS主要针对区域树形下拉菜单
 **/
/**
 *treeUlId="树形中ui的id"
 *keyId="当前输入框的id"
 *valueId="当前输入框应该绑定的值的控件ID"
 *path="请求根目录"
 * 主调方法
 */
function showMenuArea(treeUlId,keyId,valueId,path){
  path=path.replace("/","");
  if($("#treeDiv").is(":hidden")){
    loadArea(treeUlId,keyId,valueId,path);
    var cityObj = $("#"+keyId);
    var cityOffset = $("#"+keyId).offset();
    $("#treeDiv").css( {left : cityOffset.left + "px",top : cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
    $("body").bind("mousedown", onBodyDownArea);
  }
}
function loadArea(treeUi,keyId,valueId,path){
                var paramPath=path+'/front/frontArea!areapro.action?id=0&depth=0';
                $.post(paramPath, null, function(data) {
                    var icons='../css/zTreeStyle/img/diy/1_close.png';
                    var zNodes = new Array();
                    for ( var i = 0; i < data.length; i++) {
                        zNodes[i] = {id:data[i].id,name:data[i].name,open : true,isParent:true,depth:1,icon:icons};
                    }
                    initTree(treeUi,zNodes,keyId,valueId,path);
                },"json");
            }
 function initTree(treeUi,zNodes,keyId,valueId,path){
    var setting = {
                check: {enable: true,chkStyle: "radio",radioType:"all"},
                data: {simpleData: { enable: true}},
                async: {
                    enable: true,
                    url:path+'/front/frontArea!areapro.action',
                    dataType: "text",
                    type: "post",
                    dataFilter: ajaxDataFilter,
                    autoParam:["id","depth"]
                },
                callback:{
                    onClick : function onClick(e, treeId, treeNode) {
                        $("#"+keyId).attr("value",treeNode.name);
                        $("#"+valueId).attr("value",treeNode.id);
                        if(treeNode.id!=null)
                            hideMenuArea();
                    },
                    onCheck:function onCheckArea(e, treeId, treeNode){
                                 $("#"+keyId).attr("value", treeNode.name);
                                 $("#"+valueId).attr("value", treeNode.id);
                                    if(treeNode.id!=null)
                                       hideMenuArea();
                            }
                }
            };  
    $.fn.zTree.init($("#"+treeUi), setting, zNodes);
 }
 function ajaxDataFilter(treeId, parentNode, data) {
    var array = [];
    var icons='../css/zTreeStyle/img/diy/1_close.png';
    for(var i=0; i<data.length; i++){    
        if(data[i].depth>=3){icons='../css/zTreeStyle/img/diy/1_open.png';}
        array[i] = {id:data[i].id,name:data[i].name,isParent:data[i].isParent,open:data[i].open,depth:data[i].depth,icon:icons};        
    }
    return array;
 }
 
/**
 *  点击页面的其他区域，隐藏树面板
 */
function hideMenuArea() {
    $("#treeDiv").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDownArea);
}

/**
 *  点击页面的其他区域，隐藏树面板方法
 */
function onBodyDownArea(event) {
    if (!(event.target.id == "treeDiv" || $(event.target).parents("#treeDiv").length > 0)) {
        hideMenuArea();
    }
}
