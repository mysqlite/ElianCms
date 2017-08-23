/**
 *  建立XMLHttpRequest对象池
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
/**
 *  动态添加html页面内容
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
 *  通过jsonp数据类型跨域请求
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
 *  通过jsonp数据类型跨域请求
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
 *  通过jsonp数据类型跨域动态添加上一篇、下一篇
 */
function getFriendHtml(id, url, contentId) {
    url+="&contentId="+contentId.replace(",","");
    getJsonpData(id, url);
}
/**
 *通过jsonp数据类型跨域动态添加上一篇、下一篇
 */
function getFrendAjax(id,url,contentId){
    url="http://192.168.0.7:8081/ElianCms/front/friend!list.action"+url.substr(url.indexOf("?"),url.length);
    url+="&contentId="+contentId;
    getJson(url,function(data){
      frendHtml(id,data);    
    });

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
function getJson(url,f,paramData) {$.ajax({url: url,type: "GET",dataType: 'jsonp',data: (paramData),async:false,cache:false,timeout: 10000,success:f});}
function blank(str){if(str==null||str==undefined||str==""){return true;}else{return false;}};
G=function(id){return document.getElementById(id);};
 
function showDivAjax(divId,divClass,tabUrl,pathUrl,isList){
   var showdivs=document.getElementsByTagName("div");
   for(var i=0;i<showdivs.length;i++){
      if(showdivs[i].className==divClass){ 
         G(showdivs[i].id).style.display="none";
      }
   }
   G(divId).style.display="";
   G("pathUrl_"+divId).innerHTML="";
   G("tableUrl").value=tabUrl;
   getPageLeveAjax('pathUrl_'+divId,pathUrl,false);
   if(isList)
     getListAjax(divId,tabUrl);
   else
    getFrendAjax('friendDiv_'+divId,pathUrl,divId);
}
function getListAjax(){}
function getPageLeveAjax(id,url){}
