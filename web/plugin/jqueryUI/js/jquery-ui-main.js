// 弹出div窗口id
var DIALOG = "dialog";
// 弹出div窗口中iframe的id
var DIALOG_IFRAME = "dialogIframe";
// 操作div窗口打开和关闭的隐藏按钮
var DIALOG_BUTTON = "dialogButton";

/**
 *	打开弹出层
 */
function showDialogDiv() {
	var dialogDiv = $("#" + DIALOG);
	var dialogIframe = $("#" + DIALOG_IFRAME);
	dialogIframe.attr("width", dialogDiv.attr('dataWidth') );
	dialogIframe.attr("height", dialogDiv.attr('dataHeight') );
	dialogDiv.dialog( {
		autoOpen : false,
		title : dialogDiv.attr('title'),
		width : dialogDiv.attr('dataWidth'),
		height : dialogDiv.attr('dataHeight'),
		modal : true,
		buttons : getButtons(dialogDiv),
		resizable: false,
		beforeClose : function(event, ui) {
		}// 窗体关闭后事件
	});
	dialogDiv.dialog("open");
}

/**
 *	根据传递过来的按钮字符串参数，组织按钮及其回调方法
 */
function getButtons(dialogDiv) {
	var dataButtons = dialogDiv.attr('dataButtons');
	if (dataButtons == null || dataButtons == "")
		return null;
	var buttons = dataButtons.split(";");
	var temp = new Array();
	for ( var i = 0, j = 0, length = buttons.length; i < length; i = i + 2, j++) {
		temp[j] = eval('(' + "{" + "text:'" + buttons[i]
				+ "',click: function() { backMethod('" + buttons[i + 1]
				+ "');}}" + ')');
	}
	return temp;
}

/**
 *	按钮回调方法
 */
function backMethod(id) {
	var ids = id.split(":");
	if (ids.length == 2) {
		var $iframe = $("#" + ids[0]);
		var $parent = $iframe.contents();//框架dom
		var $btn = $parent.find("#" + ids[1]);
		if ($btn != null && $btn != undefined) {
			$btn.trigger("click");
		}
	}
}

/**
 *	关闭弹出层
 */
function closeDialogDiv() {
	//清空IFRAME上面的链接，防止没有必要的请求
	$("#" + DIALOG_IFRAME).attr("src", "");
	//关闭弹出层
	$("#" + DIALOG).dialog("close");
}

/**
 *	在页面加载完成后，为页面body追加弹出层标签
 */
$(document).ready(function() {
	 $("body").append(" <div id='" + DIALOG + "' dataWidth='380' dataHeight='250' style='display:none'><iframe id='" + DIALOG_IFRAME + "' frameborder='0' scrolling='no'></iframe></div>")
			.append(" <input type='button' id='" + DIALOG_BUTTON + "' onmousedown='closeDialogDiv()' style='display: none;'> ");
	 
	 $("#" + DIALOG_BUTTON).bind("click", showDialogDiv); 
});