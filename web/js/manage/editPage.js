/***
 *  编辑页JS
 * 
 *	author Joe
 *  
 *  version 1.0
 **/

/**
 *	移除option中的值 
 */
function removeOption(selectId) {
	var op = $("#" + selectId).children();
	if (op.length > 1) {
		for ( var i = 0; i < op.length; i++) {
			if (i >= 1) {
				document.getElementById(selectId).removeChild(op[i]);
			}
		}
	}
}

/**
 *	改变下拉列表中的值 
 */
function changeSelect(url, id) {
	var params = new Array(arguments.length-2);
	for(var i = 2;i<arguments.length; i++) {
		params[i-2] = arguments[i];
		removeOption(arguments[i]);
	}
	
	var selectedValue = $('#' + id).val();
	if (selectedValue == null || selectedValue == ""){
		for(var i = 0;i<params.length; i++) {
			document.getElementById(params[i]).disabled=true;
		}
		return;
	}
	
	$.post(url, {
		"selectedValue" : selectedValue
	}, function(datas) {
		if (datas != null && datas != "") {
			for(var i=0;;i++){
				if(datas[i]==null || datas[i]==undefined)
					break;
				var str = "";
				for ( var j = 0; j < datas[i].length; j++) {
					str += "<option value='" + datas[i][j].value + "'>" + datas[i][j].key
							+ "</option>"
				}
				$(str).appendTo($("#" + params[i]));
				document.getElementById(params[i]).disabled=false;
			}
		}
	}, "json");
}

/**
 *	格式化Int值
 */
function intFormat(currentObj, minValue, maxValue) {
	if (currentObj.value == null || currentObj.value == undefined
			|| !Number(currentObj.value)) {
		currentObj.value = Number(minValue);
	} 
	else if (maxValue != null && maxValue != undefined
			&& Number(currentObj.value) > Number(maxValue)) {
		currentObj.value = Number(maxValue);
	}
}