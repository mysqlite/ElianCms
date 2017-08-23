$(document).ready(function(e) {
	var $objListShowBtn=$("#doc-list").find(".show-duty-btn");
	var $objTimeListBd=$("#doc-list table td div.time_list_bd");//排班表具体时间容器
	$objListShowBtn.live('click',function(){
		var $this=$(this);
		var nextEle=$this.next();
		var ifrSrc=!!(nextEle.attr("src"));//排班表地址
		var ifrDataId=nextEle.attr("data-id");//自定义属性
		var $tipsTxt=$this.find("em");//按钮文字
		var $btnIcon=$this.find("b");//按钮图标容器
		if(!ifrSrc)
		{
			$tipsTxt.text("加载数据..")//增加读取数据提示，改变按钮的文字
            $(this).next().attr("src","${path}/reg/regDoctorWork!list.action?doctorId="+ifrDataId);
			//nextEle.attr("src","duty_tbl.htm");
			//$(this).next().attr("src","${path}/reg/regDoctorWork!list.action?doctorId="+ifrDataId);
			nextEle.load(function(){
			//console.log("框架加载完成");
			$this.toggle(
				function(){
					$tipsTxt.text("查看排班表")
					nextEle.slideDown(96);
					$btnIcon.addClass("open");
					//console.log("open");
				},function(){
					nextEle.slideUp(96);
					$objTimeListBd.hide()
					$btnIcon.removeClass("open");
				}
				).trigger('click')//页面刷新时强制这个事件执行
			});//框架加载完后再展

		};//框架加载成功后运行

	});//点击显示表格

    //医生具体时间

	var $objShowBtn=$("#doc-list table td div").find(".show_btn");//马上预约按钮
	var $allTd=$objShowBtn.parents("table").find('td');//排班表所有td
	var $JqTimeListBd=$("#jq_time_list_bd");//复制排班表的新容器
	$objShowBtn.live('click',function(){
			//var $list_bd=$objTimeListBd;
            var $this=$(this);
			var $firstTd=$this.parents("tr").find('td:first');//表行第一个td
			var $curTd=$this.parents("td");//当前选择td
			$allTd.css({'background-color':'','color':'#333333'});//选中排班表时去背景
            //if($list_bd.is(':hidden')){
                //$(".time_list").css("z-index","1");
           // $objTimeListBd.hide()//.css("z-index","1");
			if(!($JqTimeListBd.attr('style')=="undefine"))
			{ $JqTimeListBd.fadeIn(96);
			$firstTd.css({'background-color':'#F57C1C','color':'#ffffff'});//选中排班表时高亮
			$curTd.css({'background-color':'#FFFCF3'});//选中排班表时高亮
			}
			var $arrData=$curTd.attr("data-date")
			var $arrDataStrings=$arrData.split('|');
			$('#jq_time_list_bd h3 span').html($arrDataStrings[0]+"　"+$arrDataStrings[1])
            return false
	})

	$objTimeListBd.bind('click',function(){
		return false

	});
	//关闭排班表按钮
	var $tblCloseBtn=$("#doc-list #jq_time_list_bd .duty_tbl_close");
	$tblCloseBtn.click(function(){
			$objTimeListBd.hide();
	});
    var $allIframe=$("#doc-list .iframe_duty_tbl");
    $('body').bind('click',function(){
		var $obj=$("#doc-list .time_list_bd");
        var $iframeObj=$allIframe.contents().find("#doc-list .time_list_bd");//父窗口执行对象
        if((!!($obj.is(':hidden'))||(!!($iframeObj.is(':hidden')))))
        { 	var $objTbl=$("#doc-list table.tbl");
		    var $allTd=$objTbl.find('td');
			var $allFirstTd=$objTbl.find('tbody tr').find('td:first');

            //父窗口智行对象2
            var $iframeObjTbl=$allIframe.contents().find("#doc-list table.tbl");
            var $iframeObjallTd=$iframeObjTbl.find('td');
            var $iframeObjallFirstTd=$iframeObjTbl.find('tbody tr').find('td:first');
			$obj.hide();
		    $allTd.css({'background-color':''});
		    $allFirstTd.css({'background-color':'','color':'#333333'});//选中排班表时去背景

            //父窗口智行对象3
            $iframeObj.hide();
            $iframeObjallTd.css({'background-color':''});
            $iframeObjallFirstTd.css({'background-color':'','color':'#333333'});//选中排班表时去背景
        }//菜单隐藏时还原样式
    })

	//复制后的排班表dom处理+登录提示框
    $allIframe.load(function(){
        var $parent=$(this).contents();//框架dom
        var $iframeBtn=$parent.find("#doc-list .show_btn");
        $iframeBtn.click(function(){//框架页原来表格的点击显示按钮
            var $dutyTimeSourceObj=$(this).next();//排班表时间数据源
            var $dutyTimeCloneObj=$parent.find("#jq_time_list_bd table");//复制数据容器
            var $dutyTimeSourceObj_html=$dutyTimeSourceObj.find("table").html();//排班表时间数据源
            var $dutyTimeCloneObj_html=$dutyTimeCloneObj.html();//提取"最终数据容器"
            if(!($dutyTimeCloneObj_html===$dutyTimeSourceObj_html))//2个容器数据相同时，不再复制数据
            { $dutyTimeCloneObj.html($dutyTimeSourceObj_html)}//复制数据后的排班表时间数据源

            
            if(1){ // 判断登录状态，然后弹出登录框
                $dutyTimeCloneObj.find("a").bind('click',function() {
                	
                    //$.fancybox.open({
                        //href : '挂号登录页-iframe.htm',
                        //width: '380',
                        //type : 'iframe',
                        //padding : 0,
                        //openEffect : 'none',
                        //closeEffect : 'none',
                        //openSpeed  : 0,
                        //closeSpeed  : 0,
                        //closeClick : true,
//                        helpers : {//关闭背景层
                            //overlay : null
                        //}
                    //});
                    return false
                    //没登陆时，不能跳转页面，禁止原来的a标签页面跳转

                })

            }
        });
    })

});