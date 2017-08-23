$(document).ready(function(){
    $.fn.easyTreeView=function(opt){
        $.fn.easyTreeView.def={//默认参数
            branchBtn:'collapsable-hitarea',//按钮的类 加减号;
            aniSpeed:500,//动画速度;
            last:'last',//最后一个菜单类,折线;
            single:true,//单例模式
            displayBtn:true//是否显示显示关闭按钮
        };
        var setting=$.extend($.fn.easyTreeView.def,opt);//扩展默认参数
        var _$this=$(this);//传递的当前容器的id
        var branch=_$this.find('li')//分支
        var branchUl=_$this.find('ul');//分支详细内容；
        var branchTit=branch.find('div');//分支标题
        var b3=_$this.find('b'),$disBtn=$('#displayBtn');
        branchTit.live("click",function(){
        	 branchUl=_$this.find('ul');
            var $this=$(this);
            var curBranchCon=$this.next();
            var curBranchBtn=$this.find('b');
            if(setting.single)
            {branchUl.slideUp();}
            if(curBranchCon.is(':hidden')){
                curBranchBtn.addClass(setting.branchBtn);
                curBranchCon.slideDown(setting.aniSpeed);
            }else{
                curBranchBtn.removeClass(setting.branchBtn);
                curBranchCon.slideUp(setting.aniSpeed);
            }
        })
        branch.filter(':last-child').addClass(setting.last);
        branchUl.parent().find('b').show()//如果有二级菜单，就显示按钮
        if(setting.displayBtn){
               $("#displayBtn").live("click",function(){
            	    var curBranchCon=$("#treeview").find('div').next();
            		var curBranchBtn=$("#treeview").find('b');
            	   if(curBranchCon.is(':hidden')){
		                curBranchBtn.addClass(setting.branchBtn);
		            }else{
		                curBranchBtn.removeClass(setting.branchBtn);
		            }
            	    branchUl=$("#treeview").find('ul');//分支详细内容；
                    if(branchUl.is(':hidden'))
                    {
                       branchUl.slideDown();$(this).text('关闭全部')
                       {
                        var mainFrameset = window.parent.window.document.getElementById("mainFrameset");
			mainFrameset.cols = "220,6,*";
		} 
                        } 
                    else{branchUl.slideUp();$(this).text('显示全部')
                    var mainFrameset = window.parent.window.document.getElementById("mainFrameset");
			mainFrameset.cols = "220,6,*";

                    }
                    
                })
                
            }
    }
}(jQuery)
)