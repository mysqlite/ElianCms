<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<jsp:include page="reg/include/index_header.jsp"></jsp:include>
<jsp:include page="reg/include/nav.jsp"></jsp:include>
<script type='text/javascript' src='${path}/dwr/util.js'></script>  
<div class="section">
    <%--登录模块--%>
    <jsp:include page="reg/include/login.jsp"></jsp:include>
    <jsp:include page="reg/indexInclude/rapidGuahao.jsp"></jsp:include>
    <jsp:include page="reg/include/side/tingZhen.jsp"></jsp:include>
</div>
<div class="section">
    <img class="banner722" src="http://images.elian.cc/design/main/reg/img/banner722x90.jpg" width="722" height="90"/>
    <a href="#" class="banner-250">
        <img class="banner250" src="http://images.elian.cc/design/main/reg/img/banner250x90.jpg" width="250" height="90"/>
    </a>
</div>
<div class="section">
    <!--查找疾病 -->
    <link rel="stylesheet" href="http://style.elian.cc/main/reg/style/tuibing.css" type="text/css" media="screen" />
    <jsp:include page="reg/indexInclude/illnessSearch.jsp"></jsp:include>
    <!-- 床位查询 -->
    <%--
    <div class="sidebar250">
        <jsp:include page="reg/indexInclude/bedTopSearch.jsp"></jsp:include>
    </div>
    --%>
    <!-- 医院放号排行 -->
    <div class="sidebar250">
    	<jsp:include page="reg/indexInclude/hospitalTopRegNo.jsp"></jsp:include>
    </div>
</div>

<div class="section clearfix">
    <!-- 当地医院 -->
    <jsp:include page="reg/indexInclude/localHospital.jsp"></jsp:include>
     <!-- 医生评论排行 -->
    <div class="sidebar250">
    	<jsp:include page="reg/indexInclude/DoctorCommentTopList.jsp"></jsp:include>
    </div>
</div>
<div class="section">
    <!-- 热门医生 本周排行 -->
    <jsp:include page="reg/indexInclude/workTopDoctor.jsp"></jsp:include>
     <!--热点新闻 -->
    <div class="sidebar250">
    	<jsp:include page="reg/indexInclude/HotNewsList.jsp"></jsp:include>
    </div>
    <!-- 商城设备 -->
     <%--<jsp:include page="reg/indexInclude/medicalDevices.jsp"></jsp:include> --%>
</div>
<%--三好医院、科室、医生 --%>
<jsp:include page="reg/indexInclude/miyoshiHospital.jsp"></jsp:include>
<script src="http://script.elian.cc/main/reg/script/base.js"></script>
<script src="http://script.elian.cc/main/reg/script/gd-top.js"></script>
<script src="http://script.elian.cc/main/reg/script/jquery.slideBtn.js"></script>
<script src="http://script.elian.cc/main/reg/script/ui.core.js"></script> 
<script src="http://script.elian.cc/main/reg/script/ui.dialog.js"></script>
<!--<script>
var url="http://www.915800.com/diagnose/diagnose.js"
var js_obj = document.createElement( "script" ); 

js_obj.type = "text/javascript" ; 

js_obj.setAttribute( "src" , url); 
document.body.appendChild(js_obj);
</script>-->
<!--<script src="http://www.915800.com/diagnose/915800-agent.js"></script>-->
<script src="http://script.elian.cc/main/reg/script/diagnose.js"></script>
<div id="divModel"  class="outerBox" style="dis1play: none"> 
        <div class="innnerBox"> 
            <div class="top"> 
                <h3> 
                    人体模型
                </h3> 
                <input type="image" src="images/close.gif" id="btnClose" /> 
            </div> 
            <div class="content"> 
                <div class="sider_l"> 
                    <h3> 
                        <span>区域症状 - </span><span id="sltName"></span> 
                    </h3> 
                    <div id="divSymByBody"> 
                    </div> 
                    <span> 
                        <input name="rdType" type="radio" value="0" checked="checked" 
                        onclick="switchQuery();" id="rdSym" /> 
                        症状
                        <input name="rdType" type="radio" value="1" onClick="switchQuery();" 
                        id="rdIll" /> 
                        病名
                        <input type="submit" value="确定" id="btnCnf" class="submit btn"  /> 
                    </span> 
                </div> 
                <div class="sider_r"> 
                    <input type="image" id="imgSex" src="images/sex.gif" /> 
                    <img src="images/woman.jpg" id="picMap" border="0" usemap="#MapWoman" /> 
                    <map name="MapWoman" id="MapWoman"> 
                        <area shape="rect" coords="76,14,120,30" title="头部" href="#1" onClick="javascript:return modelClick('1','头部');" /> 
                        <area shape="rect" coords="237,10,275,32" title="头部" href="#1" onClick="javascript:return modelClick('1','头部');" /> 
                        <area shape="rect" coords="241,31,268,53" title="头部" href="#1" onClick="javascript:return modelClick('1','头部');" /> 
                        <area shape="rect" coords="73,37,82,52" title="耳部" href="#6" onClick="javascript:return modelClick('6','耳部');" /> 
                        <area shape="rect" coords="92,30,106,40" title="眼部" href="#2" onClick="javascript:return modelClick('2','眼部');" /> 
                        <area shape="rect" coords="83,32,93,57" title="颜面" href="#3"  onclick="javascript:return modelClick('3','颜面');" /> 
                        <area shape="rect" coords="88,41,101,57" title="颜面" href="#3" onClick="javascript:return modelClick('3','颜面');" /> 
                        <area shape="rect" coords="100,52,114,57" title="口腔" href="#5" onClick="javascript:return modelClick('5','口腔');" /> 
                        <area shape="rect" coords="107,32,114,49" title="鼻部" href="#4" onClick="javascript:return modelClick('4','鼻部');" /> 
                        <area shape="rect" coords="114,30,123,39" title="眼部" href="#2" onClick="javascript:return modelClick('2','眼部');" /> 
                        <area shape="rect" coords="116,42,125,55" title="颜面" href="#3" onClick="javascript:return modelClick('3','颜面');" /> 
                        <area shape="rect" coords="80,61,108,81" title="颈部" href="#7" onClick="javascript:return modelClick('7','颈部');" /> 
                        <area shape="rect" coords="52,82,88,95" title="肩部" href="#8" onClick="javascript:return modelClick('8','肩部');" /> 
                        <area shape="rect" coords="110,79,138,94" title="肩部" href="#8" onClick="javascript:return modelClick('8','肩部');" /> 
                        <area shape="rect" coords="40,95,66,150" title="上臂" href="#35" onClick="javascript:return modelClick('35','上臂');" /> 
                        <area shape="rect" coords="34,152,57,169" title="肘部" href="#36" onClick="javascript:return modelClick('36','肘部');" /> 
                        <area shape="rect" coords="30,170,52,203" title="前臂" href="#37" onClick="javascript:return modelClick('37','前臂');" /> 
                        <area shape="rect" coords="25,202,43,219" title="前臂" href="#37" onClick="javascript:return modelClick('37','前臂');" /> 
                        <area shape="rect" coords="21,220,43,231" title="腕部" href="#38" onClick="javascript:return modelClick('38','腕部');"/> 
                        <area shape="rect" coords="22,232,44,263" title="手部" href="#9" onClick="javascript:return modelClick('9','手部');" /> 
                        <area shape="rect" coords="66,96,78,155" title="胸部" href="#10" onClick="javascript:return modelClick('10','胸部');" /> 
                        <area shape="rect" coords="77,95,136,111" title="胸部" href="#10" onClick="javascript:return modelClick('10','胸部');" /> 
                        <area shape="rect" coords="105,111,114,138" title="胸部" href="#10" onClick="javascript:return modelClick('10','胸部');" /> 
                        <area shape="rect" coords="77,111,105,137" title="乳房" href="#23" onClick="javascript:return modelClick('23','乳房');" /> 
                        <area shape="rect" coords="114,112,136,135" title="乳房" href="#23" onClick="javascript:return modelClick('23','乳房');" /> 
                        <area shape="rect" coords="134,93,143,137" title="上臂" href="#35" onClick="javascript:return modelClick('35','上臂');" /> 
                        <area shape="rect" coords="75,139,107,175" title="右上腹部" href="#26" onClick="javascript:return modelClick('26','右上腹部');"/> 
                        <area shape="rect" coords="108,137,133,175" title="左上腹部" href="#25" onClick="javascript:return modelClick('25','左上腹部');"/> 
                        <area shape="rect" coords="135,137,146,163" title="前臂" href="#37" onClick="javascript:return modelClick('37','前臂');" /> 
                        <area shape="rect" coords="133,165,154,179" title="肘部" href="#36" onClick="javascript:return modelClick('36','肘部');" /> 
                        <area shape="rect" coords="61,175,107,202" title="右下腹部" href="#28" onClick="javascript:return modelClick('28','右下腹部');" /> 
                        <area shape="rect" coords="58,204,92,239" title="髋部" href="#30" onClick="javascript:return modelClick('30','髋部');" /> 
                        <area shape="rect" coords="92,203,127,219" title="盆腔" href="#13" onClick="javascript:return modelClick('13','盆腔');"/> 
                        <area shape="rect" coords="107,177,135,203" title="左下腹部" href="#27" onClick="javascript:return modelClick('27','左下腹部');"/> 
                        <area shape="rect" coords="140,179,163,215" title="前臂" href="#37" onClick="javascript:return modelClick('37','前臂');"/> 
                        <area shape="rect" coords="157,372,211,420" title="非可选区域" href="#22" onClick="javascript:return modelClick('22','非可选区域');" /> 
                        <area shape="rect" coords="143,215,165,228" title="腕部" href="#38" onClick="javascript:return modelClick('38','腕部');"/> 
                        <area shape="rect" coords="151,230,172,259" title="手部" href="#9" onClick="javascript:return modelClick('9','手部');" /> 
                        <area shape="rect" coords="97,220,114,237" title="阴部" href="#15"  onclick="javascript:return modelClick('15','阴部');"/> 
                        <area shape="rect" coords="63,239,98,318" title="大腿" href="#31" onClick="javascript:return modelClick('31','大腿');"/> 
                        <area shape="rect" coords="105,221,137,315" title="大腿" href="#31" onClick="javascript:return modelClick('31','大腿');"/> 
                        <area shape="rect" coords="71,318,94,341" title="膝部" href="#32" onClick="javascript:return modelClick('32','膝部');"/> 
                        <area shape="rect" coords="101,317,123,339" title="膝部" href="#32" onClick="javascript:return modelClick('32','膝部');"/> 
                        <area shape="rect" coords="65,341,91,412" title="小腿" href="#33" onClick="javascript:return modelClick('33','小腿');"/> 
                        <area shape="rect" coords="95,339,123,412" title="小腿" href="#33" onClick="javascript:return modelClick('33','小腿');"/> 
                        <area shape="rect" coords="69,414,92,428" title="踝部" href="#34" onClick="javascript:return modelClick('34','踝部');" /> 
                        <area shape="rect" coords="95,415,117,427" title="踝部" href="#34" onClick="javascript:return modelClick('34','踝部');" /> 
                        <area shape="rect" coords="73,429,99,455" title="足部" href="#20"  onclick="javascript:return modelClick('20','足部');"/> 
                        <area shape="rect" coords="99,429,126,449" title="足部" href="#20" onClick="javascript:return modelClick('20','足部');" /> 
                        <area shape="rect" coords="242,55,269,77" title="项部" href="#24" onClick="javascript:return modelClick('24','项部');" /> 
                        <area shape="rect" coords="268,32,277,53" title="耳部" href="#6" onClick="javascript:return modelClick('6','耳部');" /> 
                        <area shape="rect" coords="232,35,238,52" title="耳部" href="#6" onClick="javascript:return modelClick('6','耳部');" /> 
                        <area shape="rect" coords="244,77,257,192" title="脊柱" href="#29" onClick="javascript:return modelClick('29','脊柱');"/> 
                        <area shape="rect" coords="212,77,244,90" title="肩部" href="#8" onClick="javascript:return modelClick('8','肩部');" /> 
                        <area shape="rect" coords="222,90,244,128" title="背部" href="#16" onClick="javascript:return modelClick('16','背部');" /> 
                        <area shape="rect" coords="202,89,221,156" title="上臂" href="#35"  onclick="javascript:return modelClick('35','上臂');"/> 
                        <area shape="rect" coords="256,78,297,90" title="肩部" href="#8" onClick="javascript:return modelClick('8','肩部');" /> 
                        <area shape="rect" coords="258,92,279,128" title="背部" href="#16" onClick="javascript:return modelClick('16','背部');"/> 
                        <area shape="rect" coords="277,91,303,129" title="前臂" href="#37" onClick="javascript:return modelClick('37','前臂');" /> 
                        <area shape="rect" coords="259,130,286,191" title="腰部" href="#17" onClick="javascript:return modelClick('17','腰部');" /> 
                        <area shape="rect" coords="286,130,310,164" title="前臂" href="#37" onClick="javascript:return modelClick('37','前臂');" /> 
                        <area shape="rect" coords="223,132,243,190" title="腰部" href="#17" onClick="javascript:return modelClick('17','腰部');" /> 
                        <area shape="rect" coords="197,159,218,171" title="肘部" href="#36" onClick="javascript:return modelClick('36','肘部');" /> 
                        <area shape="rect" coords="192,170,214,209" title="前臂" href="#37" onClick="javascript:return modelClick('37','前臂');" /> 
                        <area shape="rect" coords="187,210,203,222" title="腕部" href="#38" onClick="javascript:return modelClick('38','腕部');" /> 
                        <area shape="rect" coords="189,221,210,255" title="手部" href="#9" onClick="javascript:return modelClick('9','手部');" /> 
                        <area shape="rect" coords="292,166,314,178" title="肘部" href="#36" onClick="javascript:return modelClick('36','肘部');" /> 
                        <area shape="rect" coords="297,180,321,214" title="前臂" href="#37" onClick="javascript:return modelClick('37','前臂');" /> 
                        <area shape="rect" coords="308,214,328,228" title="腕部" href="#38"  onclick="javascript:return modelClick('38','腕部');"/> 
                        <area shape="rect" coords="312,228,332,263" title="手部" href="#9" onClick="javascript:return modelClick('9','手部');" /> 
                        <area shape="rect" coords="217,192,246,239" title="臀部(肛门)" href="#18"  onclick="javascript:return modelClick('18','臀部(肛门)');"/> 
                        <area shape="rect" coords="246,192,283,237" title="臀部(肛门)" href="#18" onClick="javascript:return modelClick('18','臀部(肛门)');" /> 
                        <area shape="rect" coords="286,181,297,236" title="髋部" href="#30" onClick="javascript:return modelClick('30','髋部');" /> 
                        <area shape="rect" coords="220,239,254,317" title="大腿" href="#31"  onclick="javascript:return modelClick('31','大腿');"/> 
                        <area shape="rect" coords="254,235,295,316" title="大腿" href="#31" onClick="javascript:return modelClick('31','大腿');" /> 
                        <area shape="rect" coords="228,317,252,341" title="膝部" href="#32" onClick="javascript:return modelClick('32','膝部');" /> 
                        <area shape="rect" coords="257,316,284,340" title="膝部" href="#32" onClick="javascript:return modelClick('32','膝部');" /> 
                        <area shape="rect" coords="223,341,250,413" title="小腿" href="#33" onClick="javascript:return modelClick('33','小腿');" /> 
                        <area shape="rect" coords="251,341,279,414" title="小腿" href="#33" onClick="javascript:return modelClick('33','小腿');" /> 
                        <area shape="rect" coords="223,415,252,428" title="踝部" href="#34" onClick="javascript:return modelClick('34','踝部');" /> 
                        <area shape="rect" coords="252,415,277,430" title="踝部" href="#34" onClick="javascript:return modelClick('34','踝部');" /> 
                        <area shape="rect" coords="226,429,252,447" title="足部" href="#20" onClick="javascript:return modelClick('20','足部');" /> 
                        <area shape="rect" coords="251,430,289,448" title="足部" href="#20" onClick="javascript:return modelClick('20','足部');" /> 
                    </map> 
                    <map name="MapMan" id="MapMan"> 
                        <area shape="rect" coords="78,16,117,31" title="头部" href="#1" onClick="javascript:return modelClick('1','头部');" /> 
                        <area shape="rect" coords="74,21,80,35" title="头部" href="#1" onClick="javascript:return modelClick('1','头部');" /> 
                        <area shape="rect" coords="72,37,81,54" title="耳部" href="#6" onClick="javascript:return modelClick('6','耳部');" /> 
                        <area shape="rect" coords="81,33,89,60" title="颜面" href="#3" onClick="javascript:return modelClick('3','颜面');" /> 
                        <area shape="rect" coords="88,40,96,60" title="颜面" href="#3" onClick="javascript:return modelClick('3','颜面');" /> 
                        <area shape="rect" coords="38,78,75,93" title="肩部" href="#8" onClick="javascript:return modelClick('8','肩部');" /> 
                        <area shape="rect" coords="27,94,56,154" title="上臂" href="#35" onClick="javascript:return modelClick('35','上臂');" /> 
                        <area shape="rect" coords="68,131,105,178" title="右上腹部" href="#26" onClick="javascript:return modelClick('26','右上腹部');" /> 
                        <area shape="rect" coords="58,155,68,178" title="右上腹部" href="#26" onClick="javascript:return modelClick('26','右上腹部');" /> 
                        <area shape="rect" coords="103,131,131,179" title="左上腹部" href="#25" onClick="javascript:return modelClick('25','左上腹部');" /> 
                        <area shape="rect" coords="56,93,131,132" title="胸部" href="#10" onClick="javascript:return modelClick('10','胸部');" /> 
                        <area shape="rect" coords="59,128,68,153" title="胸部" href="#10" onClick="javascript:return modelClick('10','胸部');" /> 
                        <area shape="rect" coords="76,65,107,86" title="颈部" href="#7" onClick="javascript:return modelClick('7','颈部');" /> 
                        <area shape="rect" coords="19,156,48,168" title="肘部" href="#36" onClick="javascript:return modelClick('36','肘部');" /> 
                        <area shape="rect" coords="131,98,144,164" title="上臂" href="#35" onClick="javascript:return modelClick('35','上臂');" /> 
                        <area shape="rect" coords="105,82,131,93" title="肩部" href="#8" onClick="javascript:return modelClick('8','肩部');" /> 
                        <area shape="rect" coords="15,169,40,208" title="前臂" href="#37" onClick="javascript:return modelClick('37','前臂');" /> 
                        <area shape="rect" coords="13,211,31,221" title="腕部" href="#38"  onclick="javascript:return modelClick('38','腕部');"/> 
                        <area shape="rect" coords="4,223,35,262" title="手部" href="#9" onClick="javascript:return modelClick('9','手部');" /> 
                        <area shape="rect" coords="62,179,107,192" title="右下腹部" href="#40" onClick="javascript:return modelClick('40','右下腹部');" /> 
                        <area shape="rect" coords="69,192,106,208" title="右下腹部" href="#40" onClick="javascript:return modelClick('40','右下腹部');" /> 
                        <area shape="rect" coords="53,192,69,242" title="髋部" href="#30" onClick="javascript:return modelClick('30','髋部');" /> 
                        <area shape="rect" coords="63,207,82,241" title="髋部" href="#30"  onclick="javascript:return modelClick('30','髋部');"/> 
                        <area shape="rect" coords="107,179,129,209" title="左下腹部" href="#39" onClick="javascript:return modelClick('39','左下腹部');" /> 
                        <area shape="rect" coords="128,166,146,176" title="肘部" href="#36" onClick="javascript:return modelClick('36','肘部');" /> 
                        <area shape="rect" coords="131,179,154,219" title="前臂" href="#37" onClick="javascript:return modelClick('37','前臂');" /> 
                        <area shape="rect" coords="142,221,161,232" title="腕部" href="#38" onClick="javascript:return modelClick('38','腕部');" /> 
                        <area shape="rect" coords="143,234,168,263" title="手部" href="#9" onClick="javascript:return modelClick('9','手部');" /> 
                        <area shape="rect" coords="54,243,95,311" title="大腿" href="#31" onClick="javascript:return modelClick('31','大腿');" /> 
                        <area shape="rect" coords="81,211,129,224" title="盆腔" href="#12" onClick="javascript:return modelClick('12','盆腔');" /> 
                        <area shape="rect" coords="96,227,113,249" title="阴部" href="#14"  onclick="javascript:return modelClick('14','阴部');"/> 
                        <area shape="rect" coords="95,249,133,313" title="大腿" href="#31" onClick="javascript:return modelClick('31','大腿');" /> 
                        <area shape="rect" coords="113,227,130,249" title="大腿" href="#31" onClick="javascript:return modelClick('31','大腿');" /> 
                        <area shape="rect" coords="58,312,91,336" title="膝部" href="#32" onClick="javascript:return modelClick('32','膝部');" /> 
                        <area shape="rect" coords="99,314,127,337" title="膝部" href="#32" onClick="javascript:return modelClick('32','膝部');" /> 
                        <area shape="rect" coords="92,339,123,417" title="小腿" href="#33" onClick="javascript:return modelClick('33','小腿');" /> 
                        <area shape="rect" coords="57,338,83,419" title="小腿" href="#33" onClick="javascript:return modelClick('33','小腿');" /> 
                        <area shape="rect" coords="57,423,86,435" title="踝部" href="#34" onClick="javascript:return modelClick('34','踝部');" /> 
                        <area shape="rect" coords="89,420,114,435" title="踝部" href="#34" onClick="javascript:return modelClick('34','踝部');" /> 
                        <area shape="rect" coords="64,438,92,456" title="足部" href="#20" onClick="javascript:return modelClick('20','足部');" /> 
                        <area shape="rect" coords="96,439,131,454" title="足部" href="#20" onClick="javascript:return modelClick('20','足部');" /> 
                        <area shape="rect" coords="239,18,273,53" title="头部" href="#1" onClick="javascript:return modelClick('1','头部');" /> 
                        <area shape="rect" coords="276,33,282,54" title="耳部" href="#6" onClick="javascript:return modelClick('6','耳部');" /> 
                        <area shape="rect" coords="240,56,272,74" title="项部" href="#24" onClick="javascript:return modelClick('24','项部');" /> 
                        <area shape="rect" coords="201,78,242,89" title="肩部" href="#8" onClick="javascript:return modelClick('8','肩部');" /> 
                        <area shape="rect" coords="262,76,309,86" title="肩部" href="#8" onClick="javascript:return modelClick('8','肩部');" /> 
                        <area shape="rect" coords="245,74,261,193" title="脊柱" href="#29" onClick="javascript:return modelClick('29','脊柱');" /> 
                        <area shape="rect" coords="220,90,242,129" title="背部" href="#16" onClick="javascript:return modelClick('16','背部');" /> 
                        <area shape="rect" coords="193,92,220,147" title="上臂" href="#35" onClick="javascript:return modelClick('35','上臂');" /> 
                        <area shape="rect" coords="287,88,313,142" title="上臂" href="#35" onClick="javascript:return modelClick('35','上臂');" /> 
                        <area shape="rect" coords="262,87,286,131" title="背部" href="#16" onClick="javascript:return modelClick('16','背部');" /> 
                        <area shape="rect" coords="262,133,296,194" title="腰部" href="#17"  onclick="javascript:return modelClick('17','腰部');"/> 
                        <area shape="rect" coords="298,142,323,156" title="肘部" href="#36" onClick="javascript:return modelClick('36','肘部');" /> 
                        <area shape="rect" coords="299,158,331,214" title="前臂" href="#37" onClick="javascript:return modelClick('37','前臂');" /> 
                        <area shape="rect" coords="319,214,342,228" title="腕部" href="#38" onClick="javascript:return modelClick('38','腕部');" /> 
                        <area shape="rect" coords="321,230,344,262" title="手部" href="#9" onClick="javascript:return modelClick('9','手部');" /> 
                        <area shape="rect" coords="221,133,242,193" title="腰部" href="#17" onClick="javascript:return modelClick('17','腰部');" /> 
                        <area shape="rect" coords="218,194,248,240" title="臀部(肛门)" href="#18" onClick="javascript:return modelClick('18','臀部(肛门)');" /> 
                        <area shape="rect" coords="250,194,286,239" title="臀部(肛门)" href="#18" onClick="javascript:return modelClick('18','臀部(肛门)');" /> 
                        <area shape="rect" coords="189,149,215,164" title="肘部" href="#36" onClick="javascript:return modelClick('36','肘部');" /> 
                        <area shape="rect" coords="178,168,210,216" title="前臂" href="#37" onClick="javascript:return modelClick('37','前臂');" /> 
                        <area shape="rect" coords="176,217,197,230" title="腕部" href="#38" onClick="javascript:return modelClick('38','腕部');" /> 
                        <area shape="rect" coords="176,233,199,261" title="手部" href="#9" onClick="javascript:return modelClick('9','手部');" /> 
                        <area shape="rect" coords="287,196,299,316" title="大腿" href="#31" onClick="javascript:return modelClick('31','大腿');" /> 
                        <area shape="rect" coords="260,239,289,315" title="大腿" href="#31" onClick="javascript:return modelClick('31','大腿');" /> 
                        <area shape="rect" coords="221,241,258,314" title="大腿" href="#31" onClick="javascript:return modelClick('31','大腿');" /> 
                        <area shape="rect" coords="261,315,293,335" title="膝部" href="#32" onClick="javascript:return modelClick('32','膝部');" /> 
                        <area shape="rect" coords="226,317,255,335" title="膝部" href="#32" onClick="javascript:return modelClick('32','膝部');" /> 
                        <area shape="rect" coords="224,339,257,421" title="小腿" href="#33" onClick="javascript:return modelClick('33','小腿');" /> 
                        <area shape="rect" coords="258,335,288,423" title="小腿" href="#33" onClick="javascript:return modelClick('33','小腿');" /> 
                        <area shape="rect" coords="228,424,256,437" title="踝部" href="#34" onClick="javascript:return modelClick('34','踝部');" /> 
                        <area shape="rect" coords="257,425,280,437" title="踝部" href="#34" onClick="javascript:return modelClick('34','踝部');" /> 
                        <area shape="rect" coords="230,438,257,454" title="足部" href="#20" onClick="javascript:return modelClick('20','足部');" /> 
                        <area shape="rect" coords="260,437,295,456" title="足部" href="#20" onClick="javascript:return modelClick('20','足部');" /> 
                        <area shape="rect" coords="82,227,93,244" title="大腿" href="#31" onClick="javascript:return modelClick('31','大腿');" /> 
                        <area shape="rect" coords="89,32,102,40" title="眼部" href="#2" onClick="javascript:return modelClick('2','眼部');" /> 
                        <area shape="rect" coords="104,34,109,48" title="鼻部" href="#4" onClick="javascript:return modelClick('4','鼻部');" /> 
                        <area shape="rect" coords="110,34,120,41" title="眼部" href="#2" onClick="javascript:return modelClick('2','眼部');" /> 
                        <area shape="rect" coords="111,41,118,51" title="颜面" href="#3" onClick="javascript:return modelClick('3','颜面');" /> 
                        <area shape="rect" coords="96,51,112,60" title="口腔" href="#5" onClick="javascript:return modelClick('5','口腔');" /> 
                        <area shape="rect" coords="96,41,101,49" title="口腔1" href="#5" onClick="javascript:return modelClick('5','口腔');" /> 
                        <area shape="rect" coords="143,371,183,423" title="非可选区域" href="#22" onClick="javascript:return modelClick('22','非可选区域');" /> 
                    </map> 
                </div> 
            </div> 
            <div class="bottom"> 
            </div> 
        </div> 
    </div>
<script>
    $(function(){ 
    /*滑动按钮*/
    $("#n01").slideBtn({
        childBox:'span',//图片容器
        aniRange:'-22px',//移动距离
        closeSpeed:100,//关闭速度
        openSpeed:150,//按钮打开速度
        callBack:function(){}
    })
    $(".ui_select").toggle(
                    function(){
                       $(this).find("ul").show() 
                    },
                    
                    function(){
                         $(this).find("ul").hide() 
    })
                    
           
    /*下拉列表*/             
    var _list=$(".ui_select").find("ul"),
    //_curOption=$(".ui_select").find("span")
    _option=_list.find("li");
    
    _option.click(function(){
        
      var _selectTxt=$(this).text(),
          _curOption=$(this).parent().prev();
          _curOption.text(_selectTxt);
          $(this).parent().hide()  
    });
    
    /*帮助菜单*/
    var $help_tips=$('#help_tips').find('div');
    $('#help_tips').hover(
        function(){
           helpEnter=setTimeout(function(){$($help_tips).animate({opacity:'show'},100)},300);
           clearTimeout(helpOut);
    },
    function(){
       clearTimeout(helpEnter);
       helpOut=setTimeout(function(){$($help_tips).animate({opacity:'hide'},100);},500);
    }
        
    );
    /*服务器ajax同源问题-上线前再处理人体图*/
    /*$("#picBody").click(function() {
        $('<div id="divModel"></div>').appendTo("body");
        $("#divModel").load('1.htm').dialog('open')
        //$('#divModel'); 
        //alert("ok")
        });
    */
    })
</script>
<jsp:include page="reg/include/footer.jsp"></jsp:include>
