/***
 *  输出消息JS
 * 
 *	author Joe
 *  
 *  version 1.0
 **/

/**
 *	获取XmlHttp对象
 */
function getXmlHttp() {
	var xmlhttp = null;
	if (window.XMLHttpRequest) {// code for Firefox, Opera, IE7, etc.
		xmlhttp = new XMLHttpRequest();
	} else if (window.ActiveXObject) {// code for IE6, IE5
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	return xmlhttp;
}

/**
 *	发送Ajax请求
 *  type: 请求方式，GET、POST等
 *  url: 请求路径
 * 	async: 是否异步请求，true是异步，false同步
 *  method: 回调方法
 */
function ajax(type, url, async, method) {
	var xmlhttp = getXmlHttp();
	if (xmlhttp != null) {
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				if (method != null && method != undefined && "" != method
						&& "" != xmlhttp.responseText) {
					eval(method + "('" + xmlhttp.responseText + "');");//回调方法
				}
			}
		}
		xmlhttp.open(type, url, async);
		xmlhttp.send(null);
	}
}

/**
 *	打印消息方法
 */
function message(responseText){
	var tempText = splitText(responseText);
	var messages = eval("([" + tempText + "])");
	var printMsg = "";
	for(var i=0;i<messages.length;i++){
		printMsg += messages[i].MSG + "\n";
	}
	alert(printMsg);
}

/**
 *	对ajax请求反馈回来的responseText,组装成message数组
 */
function splitText(responseText) {
	var arrTmp = responseText.split("}{");
	if (arrTmp.length == 1)
		return responseText;
	var temp = "";
	for(var i=0;i<arrTmp.length;i++){　
		if(i==0)
			temp += arrTmp[i]+"},";
		else if(i==arrTmp.length-1)
			temp += "{"+arrTmp[i];
		else
			temp += "{"+arrTmp[i]+"},";
	}
	return temp;
}

