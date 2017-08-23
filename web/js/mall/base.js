function getid(id){
    return document.getElementById(id);
}

//选项卡
function showTab(n1,n2,hd,bd){
     var h=getid("tab"+n1+"_hd").getElementsByTagName(hd);
     var b=getid("tab"+n1+"_bd").getElementsByTagName(bd);
     for(var i=0;i<h.length;i++){
   if(n2-1==i){
       h[i].className="cur";
       b[i].style.display="block";
   }
    else {h[i].className=" ";
         b[i].style.display="none";
    }
}};

function overTab(n1,n2,hd,bd){
tabTimeout=setTimeout(function(){showTab(n1,n2,hd,bd)},100);
}
function outTab(){ clearTimeout(tabTimeout)}
//TODO 选项卡延时切换


function AddFavorite(sURL, sTitle)
{
    try
    {
        window.external.addFavorite(sURL, sTitle);
    }
    catch (e)
    {
        try
        {
            window.sidebar.addPanel(sTitle, sURL, "");
        }
        catch (e)
        {
            alert("加入收藏失败，请使用Ctrl+D进行添加");
        }
    }
}

function SetHome(obj,vrl){
        try{
                obj.style.behavior='url(#default#homepage)';
                obj.setHomePage(vrl);
        }
        catch(e){
                if(window.netscape) {
                        try {
                                netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
                        }
                        catch (e) {
                                alert("此操作被浏览器拒绝！\n请在浏览器地址栏输入“about:config”并回车\n然后将 [signed.applets.codebase_principal_support]的值设置为'true',双击即可。");
                        }
                        var prefs = Components.classes['@mozilla.org/preferences-service;1'].getService(Components.interfaces.nsIPrefBranch);
                        prefs.setCharPref('browser.startup.homepage',vrl);
                 }
        }
}