
/***
 *	author Joe
 *  
 *  version 1.0
 * 
 *  look zTree
 *  update_GCY_1.1
 *
 **/

/**
 *	点击通过post请求获取数据，并显示数结构面板
 */
function showMenu(url, type, keyId, valueId, depthId) {
	if ($("#treeDiv").is(":hidden")) {
		$.post(url, {
			'type' : type
		}, function(data) {
			var zNodes = new Array();
			for ( var i = 0; i < data.length; i++) {
				zNodes[i] = {
					id : data[i].id,
					pId : data[i].pId,
					name : data[i].name
				};
			}
			
			if(keyId==null || keyId==undefined){
				keyId="keyId";
			}
			if(valueId==null || valueId==undefined){
				valueId="valueId";
			}
			if(depthId==null || depthId==undefined){
				depthId="depthId";
			}
			
			/**
			 *	zTree初始换参数设置
			 */
			var setting = {
				view : {
					dblClickExpand : true
				},
				data : {
					simpleData : {
						enable : true
					}
				},
				callback : {
					/**
					 *	zTree节点onclick事件
					 */
					onClick : function onClick(e, treeId, treeNode) {
						$("#"+keyId).attr("value", treeNode.name);
						$("#"+valueId).attr("value", treeNode.id);
						var depth = $("#"+depthId);
						if(depth!=null)
							depth.attr("value", treeNode.level + 2);
						if(treeNode.id!=null)
							hideMenu();
					}
				}
			};
			
			$.fn.zTree.init($("#treeContent"), setting, zNodes);

			var cityObj = $("#"+keyId);
			var cityOffset = $("#"+keyId).offset();
			$("#treeDiv").css( {
				left : cityOffset.left + "px",
				top : cityOffset.top + cityObj.outerHeight() + "px"
			}).slideDown("fast");
			$("body").bind("mousedown", onBodyDown);
		}, "json");
	}
}

/**
 *	点击页面的其他区域，隐藏树面板
 */
function hideMenu() {
	$("#treeDiv").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown);
}

/**
 *	点击页面的其他区域，隐藏树面板方法
 */
function onBodyDown(event) {
	if (!(event.target.id == "treeDiv" || $(event.target).parents("#treeDiv").length > 0)) {
		hideMenu();
	}
}

/**
 *	判断是否是backSpace键
 */
function checkBackSpace(keyId, valueId) {
	var c = event.keyCode;
	if (c == 8) {
		event.returnValue = false;
		if (keyId == null || keyId == undefined) {
			keyId = "keyId";
		}
		if (valueId == null || valueId == undefined) {
			valueId = "valueId";
		}
		$("#" + keyId).attr("value", "");
		$("#" + valueId).attr("value", 0);
	}
}