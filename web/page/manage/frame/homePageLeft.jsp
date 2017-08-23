<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html>
    <head>
        <link rel=stylesheet type=text/css href="${path}/css/manage/base.css"/>
        <link rel=stylesheet type=text/css href="${path}/css/manage/admin.css"/>
        <script src="${path}/js/jquery.js"></script>
        <script src="${path}/js/manage/treeview.js"></script>
    </head>
    <body class="menu">
    <div class="body" id="body">
     <jsp:include page="../../../page/include/time.jsp"></jsp:include>
        <div class="wrap_dpBtn">
         <span class="displayBtn" id="displayBtn">显示全部</span>
        </div>
        <ul class="treeview clearfix" id="treeview"></ul>
    </div>
    <script type="text/javascript" language="javascript">
     G = function(id){return document.getElementById(id);};
     var left={};
     var openFrame=false;
     var menu1=new Array();
     var menu2=new Array();
     var menu3=new Array();
     left.getJson=function(url,f,paramData) {$.ajax({url: url,type: "POST",dataType: 'json',data: (paramData),async:false,cache:false,timeout: 10000,success:f});}
     left.getJson('${path}'+"/admin/menu!findMenuByParentId.action",function(data){
    	 var menu=data.menu;
    	 var a=0,j=0,k=0;
         var len=menu.length;
         for(var i=0;i<len;i++){
             if(menu[i].depth==2){
                menu1[a]=menu[i];
                a++;
               }
             if(menu[i].depth==3){
                menu2[j]=menu[i];
                j++;
               }
             if(menu[i].depth==4){
                menu3[k]=menu[i];
                k++; 
               }
         }
         showMenu(menu1,menu2,menu3);
     },{"parentId":<%=request.getParameter("parentId")%>});
     
     function showMenu(m1,m2,m3){
         var opli='<li class="lv0">';
         var cpli='</li>';
         var menuStr="";
         var urls="";
         for(i=0,m1len=m1.length;i<m1len;i++){
             if(!openFrame){
            	 if(action(m1[i].menuUrl)!=""){
                     parent.mainFrame.location=action(m1[i].menuUrl);openFrame=true;
            	 }
             }
             menuStr+=opli+parentHtml(m1[i].menuName,action(m1[i].menuUrl));
             menuStr+='<ul class="list" style="display:none;">';
             for(j=0,m2len=m2.length;j<m2len;j++){
                 if(m1[i].id==m2[j].parentId){
                  if(!openFrame){if(action(m2[j].menuUrl)!=""){
                       parent.mainFrame.location=action(m2[j].menuUrl);openFrame=true;//}
                	  }
                  }
                    menuStr+=childHtml(m2[j].menuName,action(m2[j].menuUrl));     
                 }
             }
             if(m2.length==0){
                 menuStr=menuStr.replace('<b class="hitarea"></b>',"");
             }
              menuStr+="</ul></div>"+cpli;
         }
         G("treeview").innerHTML=menuStr;
     }
     
     function action(urlAction){
        var urls="";
        var l=urlAction.lastIndexOf(".action",urlAction.length);
        if(l!=-1){urls='${path}'+urlAction}else{urls="";}
       return urls;
     }
     
     function parentHtml(title,menuurl){
       var parentStr="";
       if(menuurl==""){
           parentStr='<div class="hd"><b class="hitarea"></b><span class="tit">'+title+'</span></div>';
        }else{
           parentStr='<div class="hd"><b class="hitarea"></b><span class="tit"><a '+
       ' onBlur="this.href=\'javascrpit:void(0);\'"'+
       ' onMouseOut="this.href=\'javascrpit:void(0);\'" target="mainFrame"'+
       ' onclick="this.href=\''+menuurl+'\'">'+ title+'</a></span>';
        }
       return parentStr;
     }
     function childHtml(title,menuurl){
       var childStr='<li><a ' +
       ' onBlur="this.href=\'javascrpit:void(0);\'"'+
       ' onMouseOut="this.href=\'javascrpit:void(0);\'" target="mainFrame"'+
       'onclick="this.href=\''+menuurl+'\'">'+title+'</a></li>';
       return childStr;
     }
    </script>
    
    <script>
    $(document).ready(function(){
        $('#treeview').easyTreeView({//可以传入多个id实例
            branchBtn:'collapsable-hitarea',//按钮的类 加减号;
            aniSpeed:200,//动画展开速度;
            last:'last',//最后一个菜单类,折线;
            single:false
        });
    }(jQuery))
    </script>
    </body>
</html>