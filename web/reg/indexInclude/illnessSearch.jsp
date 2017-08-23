<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="http://style.elian.cc/main/reg/style/tuibing.css" type="text/css" media="screen" />
<div class="w722 find-disease">
        <div class="mod_hd01">
            <h3 class="ui_hd"><b class="ui_bg"></b>查找疾病</h3>
        </div>
        <div class="mod_bd01">
            <div class="mod_in_box step_1">
                <div class="i wrap_range">
                    <h3 class="ui-bg ui-medal">选择范围</h3>
                    <div class="ui_select" id="medicine_select">
                        <span class="ui-bg cur-option" id="cur-option">西医</span>
		        <div class="wrap">
                            <ul class="list-option">
			        <li class="option" id="western_medicine">西医</li>
                                <li class="option" id="chinese_medicine">中医</li>
                            </ul>
			</div>
                    </div>
                    <div class="disease_category" id="n01">
                        <span class="ui-bg man_btn" id="imgC">男</span>
                        <span class="ui-bg woman_btn" id="imgF">女</span>
                        <span class="ui-bg erke_btn" id="imgE">儿</span>
                    </div>
                </div>
                <div class="i">
                    <div title="点击人体选择疾病部位" class="ui-bg humen_body_popup" id="picBody"></div>
                    <div class="ui_select" id="symptoms_select">
                        <span class="ui-bg cur-option" id="cur-option">症状</span>
			<div class="wrap">
			    <ul class="list-option">
                                <li class="option" id="chinese_medicine">症状</li>
                                <li class="option" id="western_medicine">病名</li>
                            </ul>
			</div>
                        
                    </div>
                    <div class="search_bar">
                        <input id="txtCdt" type="text" class="search_ipt" value="输入症状或病名"/><input id="btnOK" type="submit" class="ui-bg imgBtn" value="相关"/>
                        <div id="slide_down" class="slide_down" style="display:none"><ul><li onMouseOver="this.className='cur'" onMouseOut="this.className=''" onClick="" >头痛病</li><li>头痛病</li><li>头痛病</li><li>头痛病</li><li>头痛病</li></ul></div>
                    </div>
                    
                </div>
                <div class="i result_box">
                    <div class="ui_hd">相关症状</div>
                    <div class="bd">
                        <div class="wrap_bd" id="divSym">
                            <ul class="result_list"><li><input class="ipc" type="checkbox" onclick="clkDivSym(this);" name="ckSym" value="29" checked><label class="lbl">头晕 </label></li><li><input class="ipc" type="checkbox" onclick="clkDivSym(this);" name="ckSym" value="42"><label class="lbl">疲乏倦怠 </label></li><li><input class="ipc" type="checkbox" onclick="clkDivSym(this);" name="ckSym" value="1287"><label class="lbl">全身不适</label></li><li><input class="ipc" type="checkbox" onclick="clkDivSym(this);" name="ckSym" value="2296"><label class="lbl">咽干 </label></li><li><input class="ipc" type="checkbox" onclick="clkDivSym(this);" name="ckSym" value="759"><label class="lbl">咽痒</label></li><li><input class="ipc" type="checkbox" onclick="clkDivSym(this);" name="ckSym" value="457"><label class="lbl">喷嚏 </label></li><li><input class="ipc" type="checkbox" onclick="clkDivSym(this);" name="ckSym" value="458"><label class="lbl">涕清稀</label></li><li><input class="ipc" type="checkbox" onclick="clkDivSym(this);" name="ckSym" value="251"><label class="lbl">鼻塞 </label></li></ul>
                        </div>
                    </div>
                </div>
            </div>
            <div class="mod_in_box step_2">
                <div class="i help_tips" id="help_tips">
                    <h3 class="ui-bg ui_btn hd"><a class="ui-bg tips_ico" href="#">操作说明</a></h3>
                    <div class="bd">操作说明操作说明操作说明操作说明<br />1<br />1<br />1<br />1<br />1<br />1<br /></div>
                </div>       
                <div class="i wrap_forward_disease">
                    <span id="btnS2I" class="ui-bg forward_disease">正推病名</span>
                    <span class="tips">说明：勾选方可推导</span>
                </div>
                <div class="i wrap_reverse_symptom">
                    <span id="btnI2S" class="ui-bg reverse_symptom">反推病症</span>
                    <span class="tips tips2">说明：勾选方可反推</span>
                </div>
                <div class="ui-bg clean_all" id="btnClear"><span class="ui-bg clean_all_ico">清空重来</span></div>
            </div>
            <div class="mod_in_box step_3">
                <div class="i result_box">
                    <div class="ui_hd">选择病名</div>
                    <div class="bd">
                        <div class="wrap_bd" id="divIll">
                            <ul class="result_list"><li><input class="ipc" onclick="illClick(this)" type="radio" name="rdIll" title="感冒初期" value="6205" checked><label class="lbl">感冒初期</label></li><li><input class="ipc" onclick="illClick(this)" type="radio" name="rdIll" title="感冒 " value="6300"><label class="lbl">感冒 </label></li><li><input class="ipc" onclick="illClick(this)" type="radio" name="rdIll" title="神经衰弱" value="482"><label class="lbl">神经衰弱</label></li><li><input class="ipc" onclick="illClick(this)" type="radio" name="rdIll" title="急性上呼吸道感染 " value="1773"><label class="lbl">急性上呼吸道感染 </label></li><li><input class="ipc" onclick="illClick(this)" type="radio" name="rdIll" title="低血糖症" value="2328"><label class="lbl">低血糖症</label></li><li><input class="ipc" onclick="illClick(this)" type="radio" name="rdIll" title="慢性鼻炎" value="1333"><label class="lbl">慢性鼻炎</label></li><li><input class="ipc" onclick="illClick(this)" type="radio" name="rdIll" title="慢性单纯性鼻炎" value="1334"><label class="lbl">慢性单纯性鼻炎</label></li><li><input class="ipc" onclick="illClick(this)" type="radio" name="rdIll" title="慢性疲劳综合征" value="8694"><label class="lbl">慢性疲劳综合征</label></li></ul>
                            <a href="#" id="btnXml" class="ui-bg disease_details_btn">病名解释</a>
                        </div>
                    </div>
                </div>
                <div class="i result_area">
                    <div class="wrap">
                        <span class="tit">疾病名称：</span><span id="txtIllName" type="ipt" class="result_ipt">感冒初期</span></div>
                    <div class="wrap">
                        <span class="tit">推荐科室：</span><span  id="txtDepart" type="ipt" class="result_ipt">呼吸内科</span></div>
                    <a href="#" id="nextpage" onClick="javascript:return next(this);" class="ui-bg link_btn">挂号</a>
                </div>
            </div>
        </div>
    </div>