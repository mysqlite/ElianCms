<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="rapidGuahao">
    <div class="hd">
        <h3 class="tit">快速挂号</h3>
        <a href="${path}/reg/regDoctor!regSearch.action" class="more">&gt;&gt;更多</a>
    </div>
    <div class="bd">
    <form action="${path }/reg/regDoctor!regSearch.action" method="post">
        <div class="white-line"></div>
        <div class="wrap-date-select clearfix">
            <span class="tit">挂号日期：</span>
            <div class="date-select">
                <label for="date-week" class="lbl">
                	<input name="day" class="ipr" id="date-week" type="radio" name="date-select" checked="check" value="0"/>&nbsp;未来7天
                </label>
                <c:forEach var="day" items="${dayList}" varStatus="e">
                	 <label for="date-${e.index+1}" class="lbl">
                	 	<input name="day" class="ipr" id="date-${e.index+1}" type="radio" name="date-select" value="${day.key}"/>${day.value}
                	 </label>
                </c:forEach>
            </div>
        </div>
        <div class="details-select">
            <div class="i">
                <span class="tit">挂号医院：</span>
                <select name="searchHospId" id="regHospital_select" class="sel"
                 onchange="changeRegHosp(this)">
                 <option value="">--选择挂号医院--</option>
                 </select>
            </div>
            <div class="i">
                <span class="tit">挂号号源：</span>
                <select class="sel" name="regType">
                	<option value="">--不限--</option>
                	<c:forEach var="item" items="${regTypeList}">
	                    <option value="${item.key}">--${item.value}--</option>
                	</c:forEach>
                </select>
            </div>
            <div class="i">
                <span class="tit">挂号科室：</span>
                 <select id="regDeptType_select" class="sel" name="searchDeptId">
                 	 <option value="">--选择挂号科室--</option>
                 </select>
            </div>
            <div class="i search_area">
                <span class="tit">挂号医生：</span>
                <input class="ipt" type="text" value="" name="doctorName"/>
                <input class="ips search_btn" type="submit" value="搜索"/>
            </div>
        </div>
    </form>
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
	function loadRegHospitalData(){
		var element= document.getElementById("regHospital_select");
		if(element.options.length>1) return;
		getJsonpData("${path}/reg/regIndex!regHospital.action",{},function (data){
			data=data.hosp;
			dwr.util.removeAllOptions('regHospital_select');
			dwr.util.addOptions('regHospital_select',[{name:'--选择挂号医院--',id:''}],"id","name");
			for(var i=0;i<data.length;i++){
				dwr.util.addOptions('regHospital_select',[{name:data[i].hospName,id:data[i].id}],"id","name");
			}
		});		
	}
	
	function changeRegHosp(obj){
		if(obj.value!=null && obj.value!=undefined)
			laodRegDeptDate(obj.value);
	}
	
	loadRegHospitalData();
	
	function laodRegDeptDate(hospId){
		var element= document.getElementById("regDeptType_select");
		if(hospId==null || hospId==undefined) return;
		getJsonpData("${path}/reg/regIndex!regDept.action",{regHospId:hospId},function (data){
			data=data.dept;
			dwr.util.removeAllOptions('regDeptType_select');
			dwr.util.addOptions('regDeptType_select',[{name:'--选择挂号科室--',id:''}],"id","name");
			for(var i=0;i<data.length;i++){
				dwr.util.addOptions('regDeptType_select',[{name:data[i].value,id:data[i].key}],"id","name");
			}
		});		
	}
</script>