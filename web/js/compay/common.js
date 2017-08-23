/**
 *	建立XMLHttpRequest对象池
 */
var XMLHttp = {
	xmlHttpCache : [],
	createXmlHttp : function() {
		var xmlhttp = null;
		if (window.XMLHttpRequest) {// code for Firefox, Opera, IE7, etc.
			xmlhttp = new XMLHttpRequest();
		} else if (window.ActiveXObject) {// code for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		return xmlhttp;
	},
	getXmlHttp : function() {
		//for ( var i = 0, length = this.xmlHttpCache.length; i < length; i++) {
			//if (this.xmlHttpCache[i].readyState == 0
					//|| this.xmlHttpCache[i].readyState == 4) {
				//return this.xmlHttpCache[i];
			//}
		//}
		//this.xmlHttpCache[this.xmlHttpCache.length] = this.createXmlHttp();
		//return this.xmlHttpCache[this.xmlHttpCache.length - 1];
		var xmlhttp = null;
		if (window.XMLHttpRequest) {// code for Firefox, Opera, IE7, etc.
			xmlhttp = new XMLHttpRequest();
		} else if (window.ActiveXObject) {// code for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		return xmlhttp;
	}
}
/**获取对象*/
 G=function(id){return document.getElementById(id);};
/**
 *	动态添加html页面内容
 */
function getAjaxHtml(id, url) {
	var xmlhttp = XMLHttp.getXmlHttp();
	if (xmlhttp != null) {
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				var navDiv = document.getElementById(id);
				navDiv.innerHTML = xmlhttp.responseText;
			}
		}
		//防止缓存
		if (url.lastIndexOf("?") != -1)
			url += "&rt=" + (new Date()).getTime();
		else
			url += "?rt=" + (new Date()).getTime();
		xmlhttp.open("POST", url, true);
		xmlhttp.send();
	}
}

/**
 *	通过jsonp数据类型跨域请求
 */
function getJsonpData(id, url) {
	$.ajax({
	    url: url,
	    type: "GET",
	    dataType: 'jsonp',
	    data: {},
	    timeout: 5000,
	    success: function (json) {
	        var navDiv = document.getElementById(id);
			navDiv.innerHTML = json.list;
	    }
	});
}
/**
 *	通过jsonp数据类型跨域请求
 */
function getAppendJsonpData(id, url) {
	$.ajax({
	    url: url,
	    type: "GET",
	    dataType: 'jsonp',
	    data: {},
	    timeout: 5000,
	    success: function (json) {
	        var navDiv = document.getElementById(id);
			navDiv.innerHTML = navDiv.innerHTML+json.list;
	    }
	});
}

/**
 *	通过jsonp数据类型跨域动态添加上一篇、下一篇
 */
function getFriendHtml(id, url, contentId) {
	url+="&contentId="+contentId.replace(",","");
	getJsonpData(id, url);
}
/**
 * 通过jsonp数据类型动态的读取栏目位置
 */
function getPageLevelHtml(id,url,target,getLast){
	if(null==target) target='>';
	if(null==getLast) getLast=true;	
	url+="&target="+target+"&getLast="+getLast;	
	getAppendJsonpData(id,url);
	
}
/**父栏目页显示隐藏div*/
function showDiv(divId,divClass,tabUrl,pathUrl,isList){
   var showdivs=document.getElementsByTagName("div");
   for(var i=0;i<showdivs.length;i++){
      if(showdivs[i].className==divClass){ 
         G(showdivs[i].id).style.display="none";
      }
   }
   G(divId).style.display="";
   G("pathUrl_"+divId).innerHTML="";
   getPageLevelHtml('pathUrl_'+divId,pathUrl,'>>',false);
   if(isList){
   G("tableUrl").value=tabUrl;
   getTableHtml('table',tabUrl);
   }
}

function getTableHtml(id, url) {
	if(url==null || undefined == url)
		url = document.getElementById('tableUrl').value;
	var path=url.substring(url.lastIndexOf('path=')+5,url.length);

	if(G("rowSize_list")!=null){	
	   if(G("rowSize_list").value>0){
		url+="&rowSize="+G("rowSize_list").value;
	    }	
	}
	getListJsonpData(id,url,path,listCssFunction);
} 

function getListJsonpData(id, url,path,f) {
	$.ajax({
	url: url,
	type: "GET",
	dataType: 'jsonp',
	data: {},
	timeout: 5000,
	success: function(data){
			var navDiv = document.getElementById(id);
			var html=f(path,data);
			navDiv.innerHTML = html;
		}
	});
} 

function listCssFunction(path,data){
	var html="<ul class='list01_aico'>";
	for(var i=0;i<data.data.length;i++){
		html+="<li><div class='txt'>";
		html+="		<a href='"+path+"/"+data.data[i].id+".shtml' target='_blank'>"+data.data[i].title+"</a>";
		html+="		</div>";
		html+="		<span class='date'>"+data.data[i].createTime+"</span>";
		html+="</li>";
	}
	html+="</ul>";
	return html+data.page;
}
