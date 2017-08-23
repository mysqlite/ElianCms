<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="w722" id="workTopDoctorDiv">
    <div class="mod_hd02">
            <div class="line_bg"></div>
            <div class="wrap_bg doc-online-hd">
                <h3 class="tit">热门医生[本周排行]</h3>
                <div class="tab3_hd" id="tab3_hd"></div>
           </div>
    </div>
    <div class="mod_bd doc-online-bd clearfix" id="doc-online-list">
        <div class="tab3_bd" id="tab3_bd"></div>
    </div>
</div>
<script type="text/javascript">
G=function(id){return document.getElementById(id);};
function getJson(url,f,paramData) {$.ajax({url: url,type: "GET",dataType: 'jsonp',data: (paramData),async:true,cache:true,timeout: 10000,success:f});}
function loadWorkTop(){
    getJson('${path}/reg/regIndex!getWorkTopDeparmentsJson.action',function(data){
      var workTopList=data.top;
      var doctors=null;
      var tab3hdHtml="";
      var tab3bdHtml="";
      if(workTopList!=undefined&&workTopList!=null){
        for(var i=0,len=workTopList.length;i<len;i++){
            if(i==0){
                tab3hdHtml+='<h4 class="cur" onmouseover="overTab(3,'+(i+1)+',\'h4\',\'div\')" onmouseout="outTab()"><a href="'+'${path}'
                        +'/reg/regDoctor!searchByDept.action?hospId='+workTopList[i].hospId+'&deptId='+workTopList[i].depaId
                        +'" title="'+workTopList[i].hospName+'['+workTopList[i].depaTypeName+']">'+workTopList[i].depaName+'</a></h4>';
                tab3bdHtml+='<div class="tab_con" style="display: block;">';
                
            }else{
                tab3hdHtml+='<h4 onmouseover="overTab(3,'+(i+1)+',\'h4\',\'div\')" onmouseout="outTab()"><a href="'+'${path}'
                        +'/reg/regDoctor!searchByDept.action?hospId='+workTopList[i].hospId+'&deptId='+workTopList[i].depaId
                        +'" title="'+workTopList[i].hospName+'['+workTopList[i].depaTypeName+']">'+workTopList[i].depaName+'</a></h4>';
                tab3bdHtml+='<div class="tab_con">';
            }
            doctors=workTopList[i].doctors;
                tab3bdHtml+='<ul class="pt01 clearfix" id="doc-online-list">';
            for(var j=0,jlen=doctors.length;j<jlen;j++){
                tab3bdHtml+='<li><a class="pic" href="'+'${path}'+'/reg/regDoctor!detial.action?docId='+doctors[j].doctorId+'">';
                tab3bdHtml+='<img src="'+doctors[j].doctImg+'" alt="'+doctors[j].doctorName+'" width="100px" height="120px"/>';
                tab3bdHtml+='<span class="in-context"><b>';
                tab3bdHtml+=doctors[j].jobTitle;
                tab3bdHtml+='</b><b class="detail">主治：';
                tab3bdHtml+=doctors[j].speciality;
                tab3bdHtml+='</b><b class="guahao-total-num">剩余挂号数：'+doctors[j].overNo+'</b></span></a>';
                tab3bdHtml+='<span class="wrap_star"><span class="star star-'+doctors[j].avgScore+'"></span></span>';
                tab3bdHtml+='<span class="tit"><a href="'+'${path}'+'/reg/regDoctor!detial.action?docId='+doctors[j].doctorId+'">'+doctors[j].doctorName+'</a>';
                tab3bdHtml+='<a href="'+'${path}'+'/reg/regDoctor!detial.action?docId='+doctors[j].doctorId+'#duty_tbl" class="ui_btn btn-online">立刻挂号</a></span>';
                tab3bdHtml+='<span class="tit"><a href="'+'${path}'+'/reg/regDoctor!searchByDept.action?hospId='+
                            doctors[j].hospId+'">'+doctors[j].hospName+'</a></span></li>';
            }
            tab3bdHtml+='</ul></div>';
        }    
      }else{
       tab3bdHtml+="<h4>暂无</h4>";
      }
      //addWorkTopMastHtml("workTopDoctorDiv");  
      G("tab3_hd").innerHTML=tab3hdHtml;
      G("tab3_bd").innerHTML=tab3bdHtml;
    });
}
function addWorkTopMastHtml(id){
    G(id).innerHTML='<div class="mod_hd02"><div class="line_bg"></div><div class="wrap_bg doc-online-hd"><h3 class="tit">热门医生[本周排行]</h3>'+
                    '<div class="tab3_hd" id="tab3_hd"></div> </div></div><div class="mod_bd doc-online-bd clearfix" id="doc-online-list"><div class="tab3_bd" id="tab3_bd"></div></div>';
}
fgm.on(window, "load", function() {
        new LazyLoad("tab3_bd",loadWorkTop);
    });
</script>