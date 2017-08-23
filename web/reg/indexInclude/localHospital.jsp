<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="w722">
      <div class="mod_hd02">
          <div class="line_bg"></div>
          <div class="wrap_bg">
              <h3 class="tit">选择当地医院</h3>
              <a href="${path}/reg/regHospitalSearch!search.action?parentAreaCode=440000&subAreaCode=440600" class="more">&gt;&gt;更多</a>
          </div>
      </div>
      <div class="mod_bd hos_list_bd">
          <div class="tab2_hd clearfix" id="tab2_hd">
              <h3 class="cur" onmouseover="overTab(2,1,'h3','div')" onmouseout="outTab()">全部</h3>
              <c:forEach var="area" items="${subAreaList}" varStatus="e">
               <h3 onmouseover="overTab(2,${e.index+2},'h3','div');loadHospitalByAreaId(${area.areaCode})" onmouseout="outTab()">${area.areaName}</h3>
              </c:forEach>
          </div>        
          <div class="tab2_bd clearfix" id="tab2_bd">
              <div class="tab_con" style="display: block;">
              	<c:forEach var="hosp" items="${hospitalList}">
              	     <c:if test="${hosp.reg}">
              		     <span><a href="${path}/reg/regDoctor!searchByDept.action?hospId=${hosp.id}">${hosp.hospName}</a></span>
              	     </c:if>
              	</c:forEach>
              </div>
              <c:forEach var="area" items="${subAreaList}" >
               <div id="${area.areaCode}" class="tab_con"></div>
              </c:forEach>
          </div>
  	</div>
  </div>
  <script type="text/javascript">
  	function getJsonpData(url,paramData,f) {
	$.ajax({
	    url: url,
	    type: "GET",
	    dataType: 'jsonp',
	    data: (paramData),
	    timeout: 5000,
	    success: function (data) {
	       f(data);
	    }
		});
	}
  	
  	function loadHospitalByAreaId(areaId){
  		var element=document.getElementById(areaId);
  		if(element.innerHTML.length>0) return;
  		getJsonpData('${path}/reg/regIndex!hospital.action',{subAreaId:areaId},function(data){
  			var hospitalList=data.hosp;
  			if(hospitalList!=null && hospitalList!=undefined){
	   			var html="";
	   			if(hospitalList.length==0){
	   				element.innerHTML="<input type='hidden'/>";
					return;	   				
	   			}
				for(var i=0;i<hospitalList.length;i++){
					if(hospitalList[i].reg)
	 				html+= "<span><a href='${path}/reg/regDoctor!searchByDept.action?hospId="+hospitalList[i].id+"'>"+hospitalList[i].hospName+"</a></span>";
				}
				element.innerHTML=html;
  			}else{
  				element.innerHTML="<input type='hidden'/>";
  			}
  		});
  	}
  </script>