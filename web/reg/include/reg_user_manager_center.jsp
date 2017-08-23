<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="sidebar200">
    <div class="aside">
        <div class="mod_hd03">
            <h3 class="tit"><span>用户信息</span></h3>
        </div>
        <div class="mod_bd03">
            <div class="i usr_message_area">
                <span class="usr_ico">用户帐号：${_user.account}</span>
                <div class="log_out"><a href="${path}/reg/login!loginOut.action">[退出登录]</a></div>
                <div class="ui_btn_nav">
                    <a href="${path}/reg/regNotes!list.action" class="guahao_log_btn">挂号记录</a>
                    <a href="${path}/reg/regUserManager!myData.action" class="usr_message_btn">我的资料</a>
                </div>
            </div>
        </div>
    </div>
    <div class="gutter"></div>
    <div class="aside ui_usr_guahao_nav">
        <ul class="list01_aico">
            <li><div class="txt"><a href="#">专家预约</a></div></li>
            <li><div class="txt"><a href="#">专科预约</a></div></li>
            <li><div class="txt"><a href="#">挂号查询</a></div></li>
            <li><div class="txt"><a href="#">出诊时间一览表</a></div></li>
            <li><div class="txt"><a href="#">专家介绍</a></div></li>
        </ul>
    </div>
    <div class="gutter"></div>
    <div class="aside">
        <div class="mod_hd03">
            <h3 class="tit"><span>最新公告</span></h3>
        </div>
        <div class="mod_bd03">
            <ul class="list01_lico">
                <li><a href="#">佛山市第一人民医院</a></li>
                <li><a href="#">佛山市第一人民医院</a></li>
                <li><a href="#">佛山市第一人民医院</a></li>
                <li><a href="#">佛山市第一人民医院</a></li>
                <li><a href="#">佛山市第一人民医院</a></li>
                <li><a href="#">佛山市第一人民医院</a></li>
                <li><a href="#">佛山市第一人民医院</a></li>
            </ul>
        </div>
    </div>
</div>