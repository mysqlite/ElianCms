 var $base="http://cms.elian.cc";
 G=function(id){return document.getElementById(id);};
 GV=function(id){return (G(id)!=null)?G(id).value:null};
 function getJson(url,f,paramData) {$.ajax({url: url,type: "GET",dataType: 'jsonp',data: (paramData),async:false,cache:false,timeout: 10000,success:f});}
 function go_login(noneMsg){
    var url=$base+"/admin/login!loginJson.action";
    getJson(url,function(data){
     if(data.user!=null&&data.user!=undefined){
        G("base_user_login_div").style.display="none";
		G("base_user_loginSuccess_div").style.display="";
		G("user_account_show").innerHTML=data.user.account+"["+data.user.realName+"]";
		if(data.status==0){
         G("link_url").innerHTML='<a href="'+$base+"/admin/login!main.action"+'">进入后台管理中心</a>';
		}
		if(data.status==2){
		 G("link_url").innerHTML='<a href="http://www.191580.com/reg/regUserManager!myData.action">进入挂号用户中心</a>'
		}
	 }
	 else{
		G("base_user_login_div").style.display="";
	    G("base_user_loginSuccess_div").style.display="none";
	    if(GV("base_user_account").length<1||GV("base_user_password")<1){
	    	G("base_login_msg").innerHTML="输入用户名或密码进行登录";
	    }
	    else if(data.status==1){
		 G("base_login_msg").innerHTML="当前用户被禁用";
		}
		else if(data.status==-1){
		 G("base_login_msg").innerHTML="用户名或密码错误";
		}
		if(noneMsg){
		  setTimeout(function(){noneLoginMsg();},2000);
	    }
	 }
    },{"user.account":GV("base_user_account"),"user.password":GV("base_user_password")});
 }
/**退出登录*/
function unLogin(){
   var ajaxurl= $base+"/reg/login!loginOutJson.action";
    getJson(ajaxurl,function(data){
	if(data.status==1){
	G("base_login_msg").innerHTML="退出成功";
	G("base_user_login_div").style.display="";
    G("base_user_loginSuccess_div").style.display="none";
	}
	});
}
function noneLoginMsg(){G("base_login_msg").innerHTML="";}
G("base_user_login").onclick=function(){go_login();setTimeout(function(){noneLoginMsg();},5000);}
G("base_login_out").onclick=function(){unLogin();setTimeout(function(){noneLoginMsg();},5000);}
go_login(true);