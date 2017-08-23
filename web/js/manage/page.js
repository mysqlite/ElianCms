/***
 *  列表页JS
 * 
 *	author Joe
 *  
 *  version 1.0
 **/

/**
 *	排序方法
 */
function sortRow(formId, url, disabled, currentObj) {
	if(!disabled)
		return;
	var a1 = $(currentObj);
	//取出当点td中的文本内容保存起来
	var text = a1.text();
	//清空td里面的内容
	a1.html("");
	//建立一个文本框，也就是input的元素节点
	var input = $("<input class='listbar-ipt' type='text' style='text-align: center;width:40px;'>");
	//设置文本框的值是保存起来的文本内容
	input.attr("value", text);
	//将文本匡加入到td中
	a1.append(input);
	//让文本框里面的文字被点亮选中
	//需要将jquery的对象转换成dom对象
	var inputdom = input.get(0);
	inputdom.select();
	//移除当前节点a1上的响应事件
	a1.unbind("click");

	/**
	 *	点击文本框阻止外层td的click方法运行
	 */
	input.bind("click", function(event) {event.stopPropagation();});
	
	/**
	 *	给文本框绑定keyUp事件
	 */
	input.bind("keyup", function(event) {
		//获取当前节点
		var inputnode = $(this);
		//保存当前的文本框内容
		var inputtext = inputnode.val();
		//验证数值数据
		inputtext = converterNumber(inputtext, text);
		//赋值给当前节点中的内容
		inputnode.attr("value", inputtext);

		//获取当前用户按下的键值
		//解决不同浏览器的问题，firefox获取的是envent,而ie获取的是Window.event
		var myEvent = event || window.event;
		var kcode = myEvent.keyCode;
		//判断按下的是否是回车键
		if (kcode == 13) {
			//验证空串
			inputtext = inputTextBlank(inputtext, text);
			//清空al里面的内容
			var tdNode = inputnode.parent();
			//保存的文本框的内容填充到a1中
			tdNode.html(inputtext);
			//判断输入的值和旧的数据值不同时，提交到服务器
			if (Number(inputtext) != Number(text))
				ajaxSort(formId, url, inputtext);
		}
	});
	
	/**
	 *	让文本框能够实现键盘事件
	 */
	input.bind("blur", function(event) {
		//获取当前节点
		var inputnode = $(this);
		//保存当前的文本框内容
		var inputtext = inputnode.val();
		//验证空串
		inputtext = inputTextBlank(inputtext, text);
		//清空al里面的内容
		var tdNode = inputnode.parent();
		//保存的文本框的内容填充到a1中
		tdNode.html(inputtext);
		//判断输入的值和旧的数据值不同时，提交到服务器
		if (Number(inputtext) != Number(text))
			ajaxSort(formId, url, inputtext);
	});
}

/**
 *	ajax请求的action提交排序方法
 */
function ajaxSort(formId, url, inputtext) {
	$.ajax({
    	type: 'POST',
        url: url,
        async: false,
        data: {'sort':inputtext},
		dataType: "json",
		complete:function(){document.getElementById(formId).submit();}
    });
}

/**
 *	验证空串
 */
function inputTextBlank(inputText, oldValue){
	if(inputText == '')
		inputText = oldValue;
	return inputText;
}

/**
 *	验证转换成排序的数值
 */
function converterNumber(inputText, oldValue) {
	if (!Number(inputText) || inputText == null || inputText == undefined) {
		if(inputText != '')
			inputText = oldValue;
	} else if (Number(inputText) >= 10000) {
		inputText = 9999;
	}
	return inputText;
}

/**
 *	显示状态DIV
 */
function showStatusDiv(divIdPrefix, index, crrentObj){
	//取消body绑定的事件
	$("body").unbind("click");
	
	//如果点击的是当前对象,直接隐藏DIV
	var DIV = document.getElementById(divIdPrefix+index);
	if(DIV.style.display=="block"){
		DIV.style.display = "none";
		return;
	}
	
	//隐藏所有DIV
	var objects = $(".statusDiv");
	for(var i = 0;i<objects.length; i++) {
		objects[i].style.display = "none";
	}
	//显示当前点击DIV
	DIV.style.display = "block";
	
	//防止事件冒泡
	$(crrentObj).bind("click", function(event) {event.stopPropagation();});
	
	//为显示DIV添加点击body隐藏事件
	$(crrentObj).bind("blur", function(event) {
		$("body").bind("click", function(event) {
			for(var i = 0;;i++){
				var dd = document.getElementById(divIdPrefix+i);
				if(dd == null || dd == undefined)
					break;
				dd.style.display = "none";
			}
		});
	});
}

/**
 *	在打开的状态DIV中，选择某个状态调用方法
 */
function statusRow(url, spanIdPrefix, divIdPrefix, index, statusKey, statusValue, statusColor){
	var obj = document.getElementById(spanIdPrefix+index);
	var objDiv = document.getElementById(divIdPrefix+index);
	if(obj.innerHTML.replace(/^\s+|\s+$/g,"")==statusKey){
		objDiv.style.display = "none";
		return;
	}
 	$.ajax( {
		type : 'POST',
		url : url,
		async : true,
		data : {'_status' : statusValue},
		dataType : "json",
		complete : changeStatusRow(obj, objDiv, statusKey, statusColor)
	});
}

/**
 *	状态改变回调函数
 */
function changeStatusRow(obj, objDiv, statusKey, statusColor){
	obj.innerHTML = statusKey;
	obj.style.color = statusColor;
	objDiv.style.display = "none";
}

/**
 *	启用方法
 */
function disableRow(url, disabled, currentObj, trueStr, trueColor, falseStr, falseColor){
	if(trueStr==null || trueStr==undefined)
		trueStr = "已启用";
	if(falseStr==null || falseStr==undefined)
		falseStr = "未启用";
	if(trueColor==null || trueColor==undefined)
		trueColor = "green";
	if(falseColor==null || falseColor==undefined)
		falseColor = "red";
	if(!disabled)
		return;
 	ajaxDisable(url, currentObj, trueStr, trueColor, falseStr, falseColor);
}
function disableRowSub(form,url, disabled, currentObj, trueStr, trueColor, falseStr, falseColor){
	if(trueStr==null || trueStr==undefined)
		trueStr = "已启用";
	if(falseStr==null || falseStr==undefined)
		falseStr = "未启用";
	if(trueColor==null || trueColor==undefined)
		trueColor = "green";
	if(falseColor==null || falseColor==undefined)
		falseColor = "red";
	if(!disabled)
		return;
 	ajaxDisableSub(form,url, currentObj, trueStr, trueColor, falseStr, falseColor);
}

/**
 *	ajax异步请求启用方法
 */
function ajaxDisable(url, currentObj, trueStr, trueColor, falseStr, falseColor) {
	var currentStr = currentObj.innerHTML.replace(/[\t\r\n]/g, "");
	$.ajax( {
		type : 'POST',
		url : url,
		async : true,
		data : {'disable' : currentStr == falseStr ? false : true},
		dataType : "json",
		complete : changeDisableRow(currentObj, currentStr, trueStr, trueColor, falseStr, falseColor)
	});
}

function ajaxDisableSub(form,url, currentObj, trueStr, trueColor, falseStr, falseColor) {
	var currentStr = currentObj.innerHTML.replace(/[\t\r\n]/g, "");
	$.ajax( {
		type : 'POST',
		url : url,
		async : true,
		data : {'disable' : currentStr == falseStr ? false : true},
		dataType : "json",
		complete : function(){document.getElementById(form).submit();}
	});
}

/**
 *	启用方法回调函数,改变页面显示值及颜色
 */
function changeDisableRow(currentObj, currentStr, trueStr, trueColor, falseStr, falseColor) {
	if (currentStr == falseStr){
		currentObj.style.color= trueColor;
		currentObj.innerHTML = trueStr;
	}
	else{
		currentObj.style.color= falseColor;
		currentObj.innerHTML = falseStr;
	}
}

/**
 *	启用删除和审核按钮
 */
function disabledButton(bool){
	var deleteButton = document.getElementById('deleteButton');
	if(deleteButton!=null)
		deleteButton.disabled=bool;
	var checkButton = document.getElementById('checkButton');
	if(checkButton!=null)
		checkButton.disabled=bool;
}

/**
 *	审核方法
 */
function checkRow(formId, url, rowSize,msg){
	var ids=getTableIds(rowSize);
	if(""==ids){
		alert("请勾选审核行！")
		return;
	}
	disabledButton(false);
	var msgs="确认审核吗？";
	if(msg!=null){
		msgs=msg;
	}
	if (confirm(msgs))
 		ajaxSubmit(formId, ids, url);
}

/**
 *	删除方法
 */
function deleteRow(formId, url, rowSize, currentObj,msg){
	if(currentObj!=null && currentObj != undefined){
		select(currentObj, rowSize);
	}
	var ids=getTableIds(rowSize);
	if(""==ids){
		alert("请勾选行！")
		return;
	}
	disabledButton(false);
	if (confirm(msg!=null?msg:'确认删除吗？'))
 		ajaxSubmit(formId, ids, url);
}

function checkRows(formId, url, rowSize, currentObj,msg){
	if(currentObj!=null && currentObj != undefined){
		select(currentObj, rowSize);
	}
	var ids=getTableIds(rowSize);
	if(""==ids){
		alert("请勾选行！")
		return;
	}
	disabledButton(true);
	var msgs="确认审核吗？";
	if(msg!=null && msg != undefined){
		msgs=msg;
	}
	if (confirm(msgs))
 		ajaxSubmit(formId, ids, url);
}
/**
 *	去掉列表中已勾选上的checkbox,并勾选当前对象checkbox
 */
function select(currentObj, rowSize){
	for (var i = 0; i < rowSize; i++) {
		var current = eval(i);
        var select = document.getElementById('select'+current);
        if(select){
        	select.checked = false;
        }		
	}
	var index = currentObj.parentNode.parentNode.rowIndex;
	var checkBox = document.getElementById('select'+(index-1));
	if (!checkBox.checked){
		checkBox.checked=true;
	}
}

/**
 *	获取表格中的Id
 */
function getTableIds(rowSize){
	var ids="";
	for (var i = 0; i < rowSize; i++) {
        var select = document.getElementById('select'+i);
        if(select!=null && select != undefined && select.checked){
        	if(ids!="")
        		ids+=",";
        	ids+=select.value;
        }		
	}
	return ids;
}

/**
 *	ajax异步提交数据,回调并提交Form
 */
function ajaxSubmit(formId, ids, url){
	$.ajax({
    	type: 'POST',
        url: url,
        async: false,
        data: {'ids':ids},
		dataType: "json",
		complete:function(){document.getElementById(formId).submit();}
    });
}

/**
 *	checkbox全选
 */
function selectAllCheckBox(selectAll, rowSize) {
	disabledButton(!selectAll.checked);
	for (var i = 0; i < rowSize; i++) {
		var current = eval(i);
        var select = document.getElementById('select'+current);
		if(select){
			select.checked = selectAll.checked;
		}
	}
}

/**
 *	点击checkbox,根据checkbox的值显示和隐藏按钮
 */
function selected(selected, rowSize){
	var checked = true;
	for (var i = 0; i < rowSize; i++) {
		var current = eval(i);
        var select = document.getElementById('select'+current);
        if(select && select.checked){
        	checked = false;
        	break;
        }		
	}
	disabledButton(checked);
}

/**
 *	改变并提交form删除数据
 */
//function changeActionDelete(formId, ids, url){
//	var form = document.getElementById(formId);
//	form.action=url+"?ids="+ids;
//	form.submit();
//}

/**
 *	删除表格中选中的行
 */
//function removeDeleteRow(tableId){
//	var tab=document.getElementById(tableId);
//	var rows=tab.rows;
//	for(var i=0;i<rows.length-1;i++){
		//var cheid=document.getElementById("select"+i);
		//if(cheid!=null && cheid.checked){
////			tab.deleteRow(i+1);
			//for(var j=i;j<rows.length-1;j++){
//				var cheid1=document.getElementById("select"+(j+1));
				//cheid1.id="select"+j;
			//}
			//i--;
		//}
	//}
//}