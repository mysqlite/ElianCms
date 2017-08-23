<%@ page language="java" pageEncoding="UTF-8"%>
<div class="sidebar">
   <div class="aside body_check">
       <div class="mod_hd01">
           <span class="tit">
               疾病查询
           </span>
           <div class="tab1_hd" id="tab10_hd">
               <h3 class="cur" onClick="showTab2(10,1,'h3','li')">
                   男
               </h3>
               <h3 onClick="showTab2(10,2,'h3','li')">
                   女
               </h3>
           </div>
       </div>
       <div class="mod_bd pub_bdr">
           <ul class="wrap_tab1_con" id="tab10_bd">
               <li class="tab1_con" style="display: block;">
                   <div class="sub_tab_hd" id="tab2_hd">
                       <h4 class="cur" onClick="showTab2(2,1,'h4','img')">
                           正面
                       </h4>
                       <b>
                           /
                       </b>
                       <h4 onClick="showTab2(2,2,'h4','img')">
                           背面
                       </h4>
                   </div>
                   <div class="body_tab_con" id="tab2_bd">
                       <img src="http://images.elian.cc/design/main/reg/img/man_z.jpg" width="120" height="330" border="0" usemap="#Map"
                       class="body_map" style="display: block;" />
                       <map name="Map" id="Map">
                           <area shape="rect" coords="54,1,81,11" title="头部" href="#" onClick="javascript:return modelClick(1,'1','头部');"
                           />
                           <area shape="rect" coords="62,24,84,35" title="口腔" href="#5" onClick="javascript:return modelClick(1,'5','口腔');"
                           />
                           <area shape="rect" coords="60,13,84,22" title="眼部" href="#" onClick="javascript:return modelClick(1,'2','眼部');"
                           />
                           <area shape="rect" coords="49,12,59,34" title="耳部" href="#6" onClick="javascript:return modelClick(1,'6','耳部');"
                           />
                           <area shape="rect" coords="50,40,78,52" title="颈部" href="#7" onClick="javascript:return modelClick(1,'7','颈部');"
                           />
                           <area shape="rect" coords="23,47,44,70" title="肩部" href="#8" onClick="javascript:return modelClick(1,'8','肩部');"
                           />
                           <area shape="rect" coords="74,54,100,67" title="肩部" href="#8" onClick="javascript:return modelClick(1,'8','肩部');"
                           />
                           <area shape="rect" coords="43,70,91,89" title="胸部" href="#10" onClick="javascript:return modelClick(1,'10','胸部');"
                           />
                           <area shape="rect" coords="48,55,71,68" title="胸部" href="#10" onClick="javascript:return modelClick(1,'10','胸部');"
                           />
                           <area shape="rect" coords="49,97,91,117" title="右上腹部" href="#26" onClick="javascript:return modelClick(1,'26','右上腹部');"
                           />
                           <area shape="rect" coords="14,80,33,116" title="上臂" href="#35" onClick="javascript:return modelClick(1,'35','上臂');"
                           />
                           <area shape="rect" coords="6,121,21,146" title="前臂" href="#37" onClick="javascript:return modelClick(1,'37','前臂');"
                           />
                           <area shape="rect" coords="2,151,22,180" title="手部" href="#9" onClick="javascript:return modelClick(1,'9','手部');"
                           />
                           <area shape="rect" coords="49,120,89,138" title="腹部" href="#26" onClick="javascript:return modelClick(1,'26','右上腹部');"
                           />
                           <area shape="rect" coords="92,90,109,120" title="上臂" href="#35" onClick="javascript:return modelClick(1,'35','上臂');"
                           />
                           <area shape="rect" coords="94,124,111,151" title="前臂" href="#37" onClick="javascript:return modelClick(1,'37','前臂');"
                           />
                           <area shape="rect" coords="98,156,119,185" title="手部" href="#9" onClick="javascript:return modelClick(1,'9','手部');"
                           />
                           <area shape="rect" coords="54,145,78,176" title="阴部" href="#14" onclick="javascript:return modelClick(1,'14','阴部');"
                           />
                           <area shape="rect" coords="31,149,53,180" title="髋部" href="#30" onClick="javascript:return modelClick(1,'30','髋部');"
                           />
                           <area shape="rect" coords="80,147,93,180" title="髋部" href="#30" onClick="javascript:return modelClick(1,'30','髋部');"
                           />
                           <area shape="rect" coords="37,183,59,219" title="大腿" href="#31" onClick="javascript:return modelClick(1,'31','大腿');"
                           />
                           <area shape="rect" coords="68,184,92,208" title="大腿" href="#31" onClick="javascript:return modelClick(1,'31','大腿');"
                           />
                           <area shape="rect" coords="66,208,93,239" title="膝部" href="#32" onClick="javascript:return modelClick(1,'32','膝部');"
                           />
                           <area shape="rect" coords="39,239,60,285" title="小腿" href="#33" onClick="javascript:return modelClick(1,'33','小腿');"
                           />
                           <area shape="rect" coords="67,252,85,289" title="小腿" href="#33" onClick="javascript:return modelClick(1,'33','小腿');"
                           />
                           <area shape="rect" coords="43,307,95,327" title="足部" href="#20" onclick="javascript:return modelClick(1,'20','足部');"
                           />
                           <area shape="rect" coords="43,289,61,304" title="踝部" href="#34" onClick="javascript:return modelClick(1,'34','踝部');"
                           />
                           <area shape="rect" coords="62,291,81,305" title="踝部" href="#34" onClick="javascript:return modelClick(1,'34','踝部');"
                           />
                           <area shape="rect" coords="43,221,63,237" title="膝部" href="#32" onClick="javascript:return modelClick(1,'32','膝部');"
                           />
                       </map>
                       <img src="http://images.elian.cc/design/main/reg/img/man_b.jpg" width="120" height="330" border="0" usemap="#Map2"
                       class="body_map" />
                       <map name="Map2" id="Map2">
                           <area shape="rect" coords="49,4,71,24" title="头部" href="#1" onClick="javascript:return modelClick(2,'1','头部');"
                           />
                           <area shape="rect" coords="47,26,69,41" title="项部" href="#24" onClick="javascript:return modelClick(2,'24','项部');"
                           />
                           <area shape="rect" coords="17,46,48,58" title="肩部" href="#8" onClick="javascript:return modelClick(2,'8','肩部');"
                           />
                           <area shape="rect" coords="65,45,99,63" title="肩部" href="#8" onClick="javascript:return modelClick(2,'8','肩部');"
                           />
                           <area shape="rect" coords="51,43,62,131" title="脊柱" href="#29" onClick="javascript:return modelClick(2,'29','脊柱');"
                           />
                           <area shape="rect" coords="28,61,49,94" title="背部" href="#16" onClick="javascript:return modelClick(2,'16','背部');"
                           />
                           <area shape="rect" coords="62,67,82,100" title="背部" href="#16" onClick="javascript:return modelClick(2,'16','背部');"
                           />
                           <area shape="rect" coords="33,101,48,130" title="腰部" href="#17" onClick="javascript:return modelClick(2,'17','腰部');"
                           />
                           <area shape="rect" coords="63,104,82,131" title="腰部" href="#17" onClick="javascript:return modelClick(2,'17','腰部');"
                           />
                           <area shape="rect" coords="32,136,86,173" title="臀部(肛门)" href="#18" onClick="javascript:return modelClick(2,'18','臀部(肛门)');"
                           />
                           <area shape="rect" coords="16,62,27,102" title="上臂" href="#35" onclick="javascript:return modelClick(2,'35','上臂');"
                           />
                           <area shape="rect" coords="85,67,101,98" title="前臂" href="#37" onClick="javascript:return modelClick(2,'37','前臂');"
                           />
                           <area shape="rect" coords="8,104,25,117" title="肘部" href="#36" onClick="javascript:return modelClick(2,'36','肘部');"
                           />
                           <area shape="rect" coords="89,102,109,113" title="肘部" href="#36" onClick="javascript:return modelClick(2,'36','肘部');"
                           />
                           <area shape="rect" coords="4,120,18,147" title="前臂" href="#37" onClick="javascript:return modelClick(2,'37','前臂');"
                           />
                           <area shape="rect" coords="92,115,114,137" title="前臂" href="#37" onClick="javascript:return modelClick(2,'37','前臂');"
                           />
                           <area shape="rect" coords="1,157,18,186" title="手部" href="#9" onClick="javascript:return modelClick(2,'9','手部');"
                           />
                           <area shape="rect" coords="104,147,118,178" title="手部" href="#9" onClick="javascript:return modelClick(2,'9','手部');"
                           />
                           <area shape="rect" coords="31,177,59,212" title="大腿" href="#31" onclick="javascript:return modelClick(2,'31','大腿');"
                           />
                           <area shape="rect" coords="60,176,90,212" title="大腿" href="#31" onclick="javascript:return modelClick(2,'31','大腿');"
                           />
                           <area shape="rect" coords="33,215,60,244" title="膝部" href="#32" onClick="javascript:return modelClick(2,'32','膝部');"
                           />
                           <area shape="rect" coords="65,216,88,245" title="膝部" href="#32" onClick="javascript:return modelClick(2,'32','膝部');"
                           />
                           <area shape="rect" coords="35,248,55,278" title="小腿" href="#33" onClick="javascript:return modelClick(2,'33','小腿');"
                           />
                           <area shape="rect" coords="60,248,87,279" title="小腿" href="#33" onClick="javascript:return modelClick(2,'33','小腿');"
                           />
                           <area shape="rect" coords="35,282,54,302" title="踝部" href="#34" onClick="javascript:return modelClick(2,'34','踝部');"
                           />
                           <area shape="rect" coords="60,285,83,304" title="踝部" href="#34" onClick="javascript:return modelClick(2,'34','踝部');"
                           />
                           <area shape="rect" coords="26,309,90,327" title="足部" href="#20" onClick="javascript:return modelClick(2,'20','足部');"
                           />
                       </map>
                   </div>
               </li>
               <li class="tab1_con">
                   <div class="sub_tab_hd" id="tab3_hd">
                       <h4 class="cur" onClick="showTab2(3,1,'h4','img')">
                           正面
                       </h4>
                       <b>
                           /
                       </b>
                       <h4 onClick="showTab2(3,2,'h4','img')">
                           背面
                       </h4>
                   </div>
                   <div class="body_tab_con" id="tab3_bd">
                       <img src="http://images.elian.cc/design/main/reg/img/woman_z.jpg" width="120" height="330" border="0" usemap="#Map3"
                       class="body_map" style="display: block;" />
                       <map name="Map3" id="Map3">
                           <area shape="rect" coords="46,0,73,11" title="头部" href="#" onClick="javascript:return modelClick(3,'1','头部');"
                           />
                           <area shape="rect" coords="47,12,77,24" onClick="javascript:return modelClick(3,'2','眼部');"
                           />
                           <area shape="rect" coords="53,27,75,38" title="口腔" href="#5" onClick="javascript:return modelClick(3,'5','口腔');"
                           />
                           <area shape="rect" coords="34,14,46,37" title="耳部" href="#6" onClick="javascript:return modelClick(3,'6','耳部');"
                           />
                           <area shape="rect" coords="39,42,76,53" title="颈部" href="#7" onClick="javascript:return modelClick(3,'7','颈部');"
                           />
                           <area shape="rect" coords="18,54,43,68" title="肩部" href="#8" onClick="javascript:return modelClick(3,'8','肩部');"
                           />
                           <area shape="rect" coords="43,63,73,76" title="胸部" href="#10" onClick="javascript:return modelClick(3,'10','胸部');"
                           />
                           <area shape="rect" coords="75,54,94,75" title="肩部" href="#8" onClick="javascript:return modelClick(3,'8','肩部');"
                           />
                           <area shape="rect" coords="37,77,92,99" title="乳房" href="#23" onClick="javascript:return modelClick(3,'23','乳房');"
                           />
                           <area shape="rect" coords="15,69,32,97" title="上臂" href="#35" onClick="javascript:return modelClick(3,'35','上臂');"
                           />
                           <area shape="rect" coords="8,100,29,118" title="肘部" href="#36" onClick="javascript:return modelClick(3,'36','肘部');"
                           />
                           <area shape="rect" coords="5,121,26,144" title="前臂" href="#37" onClick="javascript:return modelClick(3,'37','前臂');"
                           />
                           <area shape="rect" coords="42,102,78,118" title="右上腹部" href="#26" onClick="javascript:return modelClick(3,'26','右上腹部');"
                           />
                           <area shape="rect" coords="83,102,100,122" title="肘部" href="#36" onClick="javascript:return modelClick(3,'36','肘部');"
                           />
                           <area shape="rect" coords="83,126,111,147" title="前臂" href="#37" onClick="javascript:return modelClick(3,'37','前臂');"
                           />
                           <area shape="rect" coords="91,155,114,187" title="手部" href="#9" onClick="javascript:return modelClick(3,'9','手部');"
                           />
                           <area shape="rect" coords="35,122,83,137" title="右下腹部" href="#28" onClick="javascript:return modelClick(3,'28','右下腹部');"
                           />
                           <area shape="rect" coords="51,145,71,188" title="阴部" href="#15" onclick="javascript:return modelClick(3,'15','阴部');"
                           />
                           <area shape="rect" coords="24,146,51,188" title="大腿" href="#31" onclick="javascript:return modelClick(3,'31','大腿');"
                           />
                           <area shape="rect" coords="74,143,87,189" title="大腿" href="#31" onclick="javascript:return modelClick(3,'31','大腿');"
                           />
                           <area shape="rect" coords="28,191,86,215" title="大腿" href="#31" onClick="javascript:return modelClick(3,'31','大腿');"
                           />
                           <area shape="rect" coords="31,226,83,252" title="膝部" href="#32" onClick="javascript:return modelClick(3,'32','膝部');"
                           />
                           <area shape="rect" coords="32,252,80,272" title="小腿" href="#33" onClick="javascript:return modelClick(3,'33','小腿');"
                           />
                           <area shape="rect" coords="25,300,85,329" title="足部" href="#20" onclick="javascript:return modelClick(3,'20','足部');"
                           />
                           <area shape="rect" coords="-2,149,22,188" title="手部" href="#9" onClick="javascript:return modelClick(3,'9','手部');"
                           />
                           <area shape="rect" coords="29,276,84,296" title="踝部" href="#34" onClick="javascript:return modelClick(3,'34','踝部');"
                           />
                       </map>
                       <img src="http://images.elian.cc/design/main/reg/img/woman_b.jpg" width="120" height="330" border="0" usemap="#Map4"
                       class="body_map" />
                       <map name="Map4" id="Map4">
                           <area shape="rect" coords="44,5,67,22" title="头部" href="#1" onClick="javascript:return modelClick(4,'1','头部');"
                           />
                           <area shape="rect" coords="42,31,67,47" title="项部" href="#24" onClick="javascript:return modelClick(4,'24','项部');"
                           />
                           <area shape="rect" coords="69,16,81,38" title="耳部" href="#6" onClick="javascript:return modelClick(4,'6','耳部');"
                           />
                           <area shape="rect" coords="20,51,45,67" title="肩部" href="#8" onClick="javascript:return modelClick(4,'8','肩部');"
                           />
                           <area shape="rect" coords="65,50,95,71" title="肩部" href="#8" onClick="javascript:return modelClick(4,'8','肩部');"
                           />
                           <area shape="rect" coords="50,53,62,132" title="脊柱" href="#29" onClick="javascript:return modelClick(4,'29','脊柱');"
                           />
                           <area shape="rect" coords="31,74,48,107" title="背部" href="#16" onClick="javascript:return modelClick(4,'16','背部');"
                           />
                           <area shape="rect" coords="61,75,78,107" title="背部" href="#16" onClick="javascript:return modelClick(4,'16','背部');"
                           />
                           <area shape="rect" coords="33,108,49,131" title="腰部" href="#17" onClick="javascript:return modelClick(4,'17','腰部');"
                           />
                           <area shape="rect" coords="61,108,82,133" title="腰部" href="#17" onClick="javascript:return modelClick(4,'17','腰部');"
                           />
                           <area shape="rect" coords="17,69,31,107" title="上臂" href="#35" onclick="javascript:return modelClick(4,'35','上臂');"
                           />
                           <area shape="rect" coords="11,109,29,122" title="肘部" href="#36" onClick="javascript:return modelClick(4,'36','肘部');"
                           />
                           <area shape="rect" coords="7,126,26,141" title="腕部" href="#38" onClick="javascript:return modelClick(4,'38','腕部');"
                           />
                           <area shape="rect" coords="7,149,24,183" title="手部" href="#9" onClick="javascript:return modelClick(4,'9','手部');"
                           />
                           <area shape="rect" coords="80,77,97,99" title="上臂" href="#35" onclick="javascript:return modelClick(4,'35','上臂');"
                           />
                           <area shape="rect" coords="82,106,111,130" title="肘部" href="#36" onClick="javascript:return modelClick(4,'36','肘部');"
                           />
                           <area shape="rect" coords="85,138,118,149" title="腕部" href="#38" onClick="javascript:return modelClick(4,'38','腕部');"
                           />
                           <area shape="rect" coords="89,155,117,193" title="手部" href="#9" onClick="javascript:return modelClick(4,'9','手部');"
                           />
                           <area shape="rect" coords="30,132,86,175" title="臀部(肛门)" href="#18" onclick="javascript:return modelClick(4,'18','臀部(肛门)');"
                           />
                           <area shape="rect" coords="27,175,55,209" title="大腿" href="#31" onclick="javascript:return modelClick(4,'31','大腿');"
                           />
                           <area shape="rect" coords="58,179,87,209" title="大腿" href="#31" onclick="javascript:return modelClick(4,'31','大腿');"
                           />
                           <area shape="rect" coords="28,211,57,250" title="膝部" href="#32" onClick="javascript:return modelClick(4,'32','膝部');"
                           />
                           <area shape="rect" coords="58,212,82,255" title="膝部" href="#32" onClick="javascript:return modelClick(4,'32','膝部');"
                           />
                           <area shape="rect" coords="29,255,50,279" title="小腿" href="#33" onClick="javascript:return modelClick(4,'33','小腿');"
                           />
                           <area shape="rect" coords="54,258,77,280" title="小腿" href="#33" onClick="javascript:return modelClick(4,'33','小腿');"
                           />
                           <area shape="rect" coords="33,285,76,300" title="踝部" href="#34" onClick="javascript:return modelClick(4,'34','踝部');"
                           />
                           <area shape="rect" coords="27,304,84,324" title="足部" href="#20" onClick="javascript:return modelClick(4,'20','足部');"
                           />
                       </map>
                   </div>
               </li>
           </ul>
           <span class="body_nav" style="display: none;">
               <a href="#" class="linkBtn">
                   头部
               </a>
               <a href="#" class="linkBtn">
                   颈部
               </a>
               <a href="#" class="linkBtn">
                   胸部
               </a>
               <a href="#" class="linkBtn">
                   腹部
               </a>
               <a href="#" class="linkBtn">
                   生殖部
               </a>
               <a href="#" class="linkBtn">
                   排泄部
               </a>
               <a href="#" class="linkBtn">
                   上肢
               </a>
               <a href="#" class="linkBtn">
                   下肢
               </a>
               <a href="#" class="linkBtn">
                   皮肤
               </a>
           </span>
           <div class="body_tab_tips" id="body_tab_tips">
               <div class="i tips_btn" id="tips_btn">
                   查看帮助
               </div>
               <div class="i tips_con" id="tips_con">
                   点击人体部位可显示 相对部位的疾病
               </div>
           </div>
       </div>
   </div>
   <div class="aside">
       <div class="mod_hd02">
           <a href="#">
               科室分类
           </a>
       </div>
       <div class="mod_bd pub_bdr">
           <div class="gutter">
           </div>
           <ul class="treeview clearfix" id="treeview">
               <li class="lv0">
                   <div class="hd">
                       <b class="hitarea">
                       </b>
                       <span class="tit">
                           内科
                       </span>
                   </div>
                   <ul class="list">
                       <li>
                           <a onclick="depClick('中毒')" href="javascript:void(0)">
                               中毒
                           </a>
                       </li>
                       <li>
                           <a onclick="depClick('消化内科')" href="javascript:void(0)">
                               消化内科
                           </a>
                       </li>
                       <li>
                           <a onclick="depClick('呼吸内科')" href="javascript:void(0)">
                               呼吸内科
                           </a>
                       </li>
                       <li>
                           <a onclick="depClick('肾内科')" href="javascript:void(0)">
                               肾内科
                           </a>
                       </li>
                       <li>
                           <a onclick="depClick('血液内科')" href="javascript:void(0)">
                               血液内科
                           </a>
                       </li>
                       <li>
                           <a onclick="depClick('营养代谢科')" href="javascript:void(0)">
                               营养代谢科
                           </a>
                       </li>
                       <li>
                           <a onclick="depClick('肛肠科')" href="javascript:void(0)">
                               肛肠科
                           </a>
                       </li>
                       <li>
                           <a onclick="depClick('肛肠科')" href="javascript:void(0)">
                               眼科
                           </a>
                       </li>
                       <li>
                           <a onclick="depClick('口腔科')" href="javascript:void(0)">
                               口腔科
                           </a>
                       </li>
                       <li>
                           <a onclick="depClick('骨科')" href="javascript:void(0)">
                               骨科
                           </a>
                       </li>
                       <li>
                           <a onclick="depClick('急诊科')" href="javascript:void(0)">
                               急诊科
                           </a>
                       </li>
                       <li>
                           <a onclick="depClick('内分泌科')" href="javascript:void(0)">
                               内分泌科
                           </a>
                       </li>
                       <li>
                           <a onclick="depClick('血管外科')" href="javascript:void(0)">
                               血管外科
                           </a>
                       </li>
                       <li>
                           <a onclick="depClick('心血管内科')" href="javascript:void(0)">
                               心血管内科
                           </a>
                       </li>
                       <li>
                           <a onclick="depClick('职业病科')" href="javascript:void(0)">
                               职业病科
                           </a>
                       </li>
                       <li>
                           <a onclick="depClick('肠道传染病科')" href="javascript:void(0)">
                               肠道传染病科
                           </a>
                       </li>
                       <li>
                           <a onclick="depClick('呼吸道传染病科')" href="javascript:void(0)">
                               呼吸道传染病科
                           </a>
                       </li>
                   </ul>
               </li>
               <li class="lv0">
                   <div class="hd">
                       <b class="hitarea">
                       </b>
                       <span class="tit">
                           外科
                       </span>
                   </div>
                   <ul class="list">
                       <li>
                           <a onclick="depClick('普通外科')" href="javascript:void(0)">
                               普通外科
                           </a>
                       </li>
                       <li>
                           <a onclick="depClick('神经内科')" href="javascript:void(0)">
                               神经内科
                           </a>
                       </li>
                       <li>
                           <a onclick="depClick('泌尿外科')" href="javascript:void(0)">
                               泌尿外科
                           </a>
                       </li>
                       <li>
                           <a onclick="depClick('皮肤科')" href="javascript:void(0)">
                               皮肤科
                           </a>
                       </li>
                       <li>
                           <a onclick="depClick('疼痛专科')" href="javascript:void(0)">
                               疼痛专科
                           </a>
                       </li>
                       <li>
                           <a onclick="depClick('心胸外科')" href="javascript:void(0)">
                               心胸外科
                           </a>
                       </li>
                       <li>
                           <a onclick="depClick('耳鼻喉科')" href="javascript:void(0)">
                               耳鼻喉科
                           </a>
                       </li>
                   </ul>
               </li>
               <li class="lv0">
                   <div class="hd">
                       <b class="hitarea">
                       </b>
                       <span class="tit">
                           肿瘤科
                       </span>
                   </div>
                   <ul class="list">
                       <li>
                           <a onclick="depClick('肿瘤科')" href="javascript:void(0)">
                               肿瘤科
                           </a>
                       </li>
                       <li>
                           <a onclick="depClick('结核专科 ')" href="javascript:void(0)">
                               结核专科 
                           </a>
                       </li>
                   </ul>
               </li>
               <li class="lv0">
                   <div class="hd">
                       <b class="hitarea">
                       </b>
                       <span class="tit">
                           妇产科
                       </span>
                   </div>
                   <ul class="list">
                       <li>
                           <a onclick="depClick('妇科')" href="javascript:void(0)">
                               妇科
                           </a>
                       </li>
                       <li>
                           <a onclick="depClick('产科')" href="javascript:void(0)">
                               产科
                           </a>
                       </li>
                   </ul>
               </li>
               <li class="lv0">
                   <div class="hd">
                       <b class="hitarea">
                       </b>
                       <span class="tit">
                           儿科
                       </span>
                   </div>
                   <ul class="list">
                       <li>
                           <a onclick="depClick('新生儿科')" href="javascript:void(0)">
                               新生儿科
                           </a>
                       </li>
                       <li>
                           <a onclick="depClick('小儿呼吸内科')" href="javascript:void(0)">
                               小儿呼吸内科
                           </a>
                       </li>
                       <li>
                           <a onclick="depClick('小儿消化内科')" href="javascript:void(0)">
                               小儿消化内科
                           </a>
                       </li>
                       <li>
                           <a onclick="depClick('小儿心血管内科')" href="javascript:void(0)">
                               小儿心血管内科
                           </a>
                       </li>
                       <li>
                           <a onclick="depClick('小儿肾内科')" href="javascript:void(0)">
                               小儿肾内科
                           </a>
                       </li>
                       <li>
                           <a onclick="depClick('小儿血液病科')" href="javascript:void(0)">
                               小儿血液病科
                           </a>
                       </li>
                       <li>
                           <a onclick="depClick('小儿神经内科')" href="javascript:void(0)">
                               小儿神经内科
                           </a>
                       </li>
                       <li>
                           <a onclick="depClick('小儿风湿病科')" href="javascript:void(0)">
                               小儿风湿病科
                           </a>
                       </li>
                       <li>
                           <a onclick="depClick('小儿风湿病科')" href="javascript:void(0)">
                               小儿内分泌科
                           </a>
                       </li>
                       <li>
                           <a onclick="depClick('小儿感染内科')" href="javascript:void(0)">
                               小儿感染内科
                           </a>
                       </li>
                       <li>
                           <a onclick="depClick('小儿急诊科')" href="javascript:void(0)">
                               小儿急诊科
                           </a>
                       </li>
                       <li>
                           <a onclick="depClick('小儿皮肤病科')" href="javascript:void(0)">
                               小儿皮肤病科
                           </a>
                       </li>
                       <li>
                           <a onclick="depClick('小儿精神科')" href="javascript:void(0)">
                               小儿精神科
                           </a>
                       </li>
                   </ul>
               </li>
               <li class="lv0">
                   <div class="hd">
                       <b class="hitarea">
                       </b>
                       <span class="tit">
                           男科
                       </span>
                   </div>
                   <ul class="list">
                       <li>
                           <a onclick="depClick('男性专科, 泌尿外科')" href="javascript:void(0)">
                               男性专科, 泌尿外科
                           </a>
                       </li>
                   </ul>
               </li>
               <li class="lv0">
                   <div class="hd">
                       <b class="hitarea">
                       </b>
                       <span class="tit">
                           传染科
                       </span>
                   </div>
                   <ul class="list">
                       <li>
                           <a onclick="depClick('男性专科, 泌尿外科')" href="javascript:void(0)">
                               动物源性传染病科
                           </a>
                       </li>
                       <li>
                           <a onclick="depClick('虫媒传染病科')" href="javascript:void(0)">
                               虫媒传染病科
                           </a>
                       </li>
                       <li>
                           <a onclick="depClick('性病科')" href="javascript:void(0)">
                               性病科
                           </a>
                       </li>
                       <li>
                           <a onclick="depClick('医源性疾病, 皮肤科')" href="javascript:void(0)">
                               医源性疾病, 皮肤科
                           </a>
                       </li>
                       <li>
                           <a onclick="depClick('遗传病科')" href="javascript:void(0)">
                               遗传病科
                           </a>
                       </li>
                       <li>
                           <a onclick="depClick('非典型类疾病')" href="javascript:void(0)">
                               非典型类疾病
                           </a>
                       </li>
                   </ul>
               </li>
               <li class="lv0">
                   <div class="hd">
                       <b class="hitarea">
                       </b>
                       <span class="tit">
                           肝病肝炎
                       </span>
                   </div>
                   <ul class="list">
                       <li>
                           <a onclick="depClick('肝炎专科')" href="javascript:void(0)">
                               肝炎专科
                           </a>
                       </li>
                   </ul>
               </li>
               <li class="lv0">
                   <div class="hd">
                       <b class="hitarea">
                       </b>
                       <span class="tit">
                           精神心理科
                       </span>
                   </div>
                   <ul class="list">
                       <li>
                           <a onclick="depClick('精神科')" href="javascript:void(0)">
                               精神科
                           </a>
                       </li>
                       <li>
                           <a onclick="depClick('神经外科')" href="javascript:void(0)">
                               神经外科
                           </a>
                       </li>
                   </ul>
               </li>
               <li class="lv0">
                   <div class="hd">
                       <b class="hitarea">
                       </b>
                       <span class="tit">
                           中医科
                       </span>
                   </div>
                   <ul class="list">
                       <li>
                           <a onclick="depClick('中医体质')" href="javascript:void(0)">
                               中医体质
                           </a>
                       </li>
                       <li>
                           <a onclick="depClick('中医妇产科')" href="javascript:void(0)">
                               中医妇产科
                           </a>
                       </li>
                       <li>
                           <a onclick="depClick('中医男科')" href="javascript:void(0)">
                               中医男科
                           </a>
                       </li>
                       <li>
                           <a onclick="depClick('中医内科')" href="javascript:void(0)">
                               中医内科
                           </a>
                       </li>
                       <li>
                           <a onclick="depClick('')" href="javascript:void(0)">
                               中医外科
                           </a>
                       </li>
                       <li>
                           <a onclick="depClick('中医儿科')" href="javascript:void(0)">
                               中医儿科
                           </a>
                       </li>
                       <li>
                           <a onclick="depClick('中医五官科')" href="javascript:void(0)">
                               中医五官科
                           </a>
                       </li>
                   </ul>
               </li>
               <li class="lv0">
                   <div class="hd">
                       <b class="hitarea">
                       </b>
                       <span class="tit">
                           老年科
                       </span>
                   </div>
                   <ul class="list">
                       <li>
                           <a onclick="depClick('风湿病科')" href="javascript:void(0)">
                               风湿病科
                           </a>
                       </li>
                       <li>
                           <a onclick="depClick('糖尿病专科')" href="javascript:void(0)">
                               糖尿病专科
                           </a>
                       </li>
                   </ul>
               </li>
           </ul>
       </div>
    </div>
  </div>
  		<script type="text/javascript" src="http://script.elian.cc/main/jquery-1.4.2.min.js"></script>
		<SCRIPT src="http://script.elian.cc/public/jquery.easyTreeView.js"></SCRIPT>
  
  <script type="text/javascript">
				
				function showTab2(n1,n2,hd,bd){
				     var h=document.getElementById("tab"+n1+"_hd").getElementsByTagName(hd);
				     var b=document.getElementById("tab"+n1+"_bd").getElementsByTagName(bd);
				     for(var i=0;i<h.length;i++){
				   if(n2-1==i){
				       h[i].className="cur";
				       b[i].style.display = "block";
				
				       var objname = "tab" + n1 + "_bd";
				
				   }
				    else {h[i].className="";
				         b[i].style.display="none";
				     }
				
				     
				}};
				
				
				
				
				//滑动展开疾病
				+function($){
				    $('#treeview').easyTreeView({//可以传入多个id实例
				        branchBtn:'collapsable-hitarea',//按钮的类 加减号;
				        aniSpeed:200,//动画展开速度;
				        last:'last'//最后一个菜单类,折线;
				    })
				}(jQuery)
				//帮助提示
				+function($){
				    $("#body_tab_tips").hover(function(){
				        $("#tips_con").stop(true,false).animate({opacity: 1},500).show()
				    },function(){
				        $("#tips_con").stop(true,false).animate({opacity: 0},1000,function(){ $(this).hide()})
				    })
				}(jQuery)
				//跳进来后初始化人体图
				$(document).ready(function(){
				 initDiv(); 
				})
				
				 function initDiv()
				 {
				     var a=request.QueryString("a");
				     if(a==null)
				       return;
				       
				     switch(a.toString())
				     {
				        case "1":
				           //男正面
				          showTab2(10,1,'h3','li');
				          showTab2(2,1,'h4','img');
				          break;
				        case "2":
				            //男背面
				          showTab2(10,1,'h3','li');
				          showTab2(2,2,'h4','img');
				          break;
				        case "3":  //女正面
				
				          showTab2(10,2,'h3','li');
				          showTab2(3,1,'h4','img');
				          break;
				         case "4":  //女反面
				          showTab2(1,2,'h3','li');
				          showTab2(3,2,'h4','img');
				          break;
				     }
				     
				     
				 } 
				   //获取url参 数值
				var request = {
					QueryString : function(val) {
						var uri = window.location.search;
						var re = new RegExp("" +val+ "=([^&?]*)", "ig");
						return ((uri.match(re))?(uri.match(re)[0].substr(val.length+1)):"");
					}
					
					
				};
				
				function sethash() {
				    var urlPre = "http://www.elian.cc";
				        hashH = document.documentElement.scrollHeight; //获取自身高度
				        urlC = urlPre+"/illness/a.htm"; //设置iframeA的src
				        document.getElementById("iframeA").src = urlC + "#" + hashH; //将高度作为参数传递
				    } 
				  
				function depClick(key){
					var url="${path}/page/front/illnessList.jsp?departmentName="+encodeURI(key);
					url=encodeURI(url);
					window.location.href=url;
				}			
				
				function modelClick(){
					return false;
				}
			</script>