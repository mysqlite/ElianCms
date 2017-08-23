// 操作div窗口打开和关闭的隐藏按钮
var DIALOG_BUTTON = "dialogButton";

/**
 *	将form转为AJAX方式提交
 */
function ajaxSubmit(form, successMethod) {
	var dataPara = getFormJson(form);
	$.ajax( {
		url : form.action,
		type : form.method,
		async : false,
		data : dataPara,
		dataType : "json",
		success : successMethod
	});
}

/**
 *	将form中的值转换为键值对
 */
function getFormJson(frm) {
	var o = {};
	var a = $(frm).serializeArray();
	$.each(a, function() {
		if (o[this.name] !== undefined) {
			if (!o[this.name].push) {
				o[this.name] = [ o[this.name] ];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;
}

/**
 *	关闭弹出窗口
 *  @param buttonId : 关闭窗口后执行的click按钮id,参数为null或者不传表示没有回调事件
 */
function closeDialog(buttonId) {
	var mainWindow = top ? top : window;
	$("#"+DIALOG_BUTTON, mainWindow.document).trigger("mousedown");
	if (buttonId != null && buttonId != "")
		backMethod(buttonId, mainWindow.document);
}

/**
 *	关闭弹出窗口回调方法
 *  @param buttonId : 关闭窗口后执行的click按钮id
 *  @param topDocument: top中的document
 */
function backMethod(buttonId, topDocument) {
	var ids = buttonId.split(":");
	if(ids.length == 1){
		var $btn = $("#" + ids[0], topDocument);
		if ($btn != null && $btn != undefined) {
			$btn.trigger("click");
		}
	}
	else if (ids.length == 2) {
		var $iframe = $("#" + ids[0], topDocument);
		var $parent = $iframe.contents();//框架dom
		var $btn = $parent.find("#" + ids[1]);
		if ($btn != null && $btn != undefined) {
			$btn.trigger("click");
		}
	}
}