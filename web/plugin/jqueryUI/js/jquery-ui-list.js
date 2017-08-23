// 弹出div窗口id
var DIALOG = "dialog";
// 弹出div窗口中iframe的id
var DIALOG_IFRAME = "dialogIframe";
// 操作div窗口打开和关闭的隐藏按钮
var DIALOG_BUTTON = "dialogButton";

/**
 *	调用显示主窗口中Dialog方法，并传递属性参数
 */
function showDialog(title, url, width, height) {
	var mainWindow = top ? top : window;
	var dialogDiv = $("#" + DIALOG, mainWindow.document);
	dialogDiv.attr("dataWidth", width);
	dialogDiv.attr("dataHeight", height);
	dialogDiv.attr("dataButtons", getButtonParams(arguments));
	dialogDiv.attr("title", title);
	$("#" + DIALOG_BUTTON, mainWindow.document).trigger("click");
	$("#" + DIALOG_IFRAME, mainWindow.document).attr("src", url);
}

/**
 *	获取显示窗口方法传递过来的按钮字符串参数
 */
function getButtonParams(args) {
	var buttons = "";
	for ( var i = 4; i < args.length; i++) {
		if (i > 4) {
			buttons += ";";
		}
		buttons += args[i];
	}
	return buttons;
}

/**
 *	通过触发主窗口中mousedown方法,关闭主窗口中的dialog
 */
function closeDialog() {
	var mainWindow = top ? top : window;
	$("#" + DIALOG_BUTTON, mainWindow.document).trigger("mousedown");
}