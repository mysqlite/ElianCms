<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="manage_hd">
    <h2 class="ui_tit">会员管理中心</h2>
    <div class="nav_link">
        <ul class="list" id="list2">
            <li class="cur"><a href="${path}/reg/regUserManager!myData.action">我的资料</a></li>
            <li><a href="${path}/reg/regNotes!list.action">挂号记录</a></li>
            <li><a href="${path}/reg/regConsumption!list.action">消费明细</a></li>
        </ul>
    </div>
    <b class="left"></b><b class="right"></b>
</div>
<script type="text/javascript">
$(document).ready(function() {
    var $menuItem = $("#list2 li");
    $menuItem.live("click",function(){
        var $this=$(this);
        $menuItem = $("#list2 li");
        $menuItem.removeClass("cur");
        $this.addClass("cur");
    });
});
</script>