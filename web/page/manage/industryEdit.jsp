<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html lang="zh-CN">
	<head>
		<title>编辑行业</title>
		<jsp:include page="../../page/include/headEdit.jsp"></jsp:include>
		<script language="javascript" type="text/javascript" src="${path}/js/jquery-1.4.4.min.js"></script>
		<script type="text/javascript">
			    function check(){
				  
				var str;	
				str=$("#ipt").val();
				if(str.length==0){
                //var htmlobj=$.ajax({url:'${path}/error.txt',async:false});
               // $("#error").html(htmlobj.responseText);      
                $.ajax({
		    	type: 'POST',
		        url: $base + '/admin/IndustryAction!errorMsg.action',
		        async: false,
				dataType: "json",
				success:function(data)
				{
                	$("#error").html(data);
	            }
		       });
                }
				else
				{
					document.editIndustryForm.submit();
				}
				
//				$("#error").hide();
				//alert("Value: " + str);
//				if (str.length==0){ 
//	                var xmlhttp;
//					if (window.XMLHttpRequest)
//				    {// code for IE7+, Firefox, Chrome, Opera, Safari
//				    	xmlhttp=new XMLHttpRequest();
//				    }
//				    else
//				    {// code for IE6, IE5
//				    	xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
//				    }
				
 //               	xmlhttp.onreadystatechange=function()
//			  	    {
//			  	   		if (xmlhttp.readyState==4 && xmlhttp.status==200)
//			    		{
//			    			document.getElementById("error").innerHTML=xmlhttp.responseText;
//			    			alert("Value: " + xmlhttp.responseText);
//			    		}
//			  	    }
//               	xmlhttp.open("GET","test1.txt",true);
//	             	xmlhttp.send();
					//$("#error").show();
					//document.getElementById("error").innerHTML="行业名称不能为空";
				    //return;
//				}
//				else{
//					document.editIndustryForm.submit();
//				}
			}
		</script>
	</head>
	<body class="input">
		<div class="main-top-hd clearfix">
        	<h3 class="cur">
	        	<c:if test="${!edit}">添加行业</c:if>
				<c:if test="${edit}">编辑行业</c:if>
	        </h3>
	    </div>
		<div class="body">
			<form name="editIndustryForm" action="${path}/admin/industry!save.action" method="post">
				<input type="hidden" name="token" value="${token}"/>
				<input type="hidden" name="industry.id" value="${industry.id}">
				<input type="hidden" name="industry.version" value="${industry.version}">
				<input type="hidden" name="edit" value="${edit}">
				<div class="divInputTable">
		            <h3 class="main-tit-bar" id="typeBtn">基本信息</h3>
		            <div id="typeDiv">
		            	<ul class="inputList">
		            		<li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>行业名称：
		                     		</label>
			                        <input type="text" class="ipt" id="ipt" name="industry.industryName" value="${industry.industryName}"/>
				                    <span class="errortip">
				                    <div id="error" ></div>
										<%--<s:fielderror >
										    <s:param>industry.industryName</s:param>
									    </s:fielderror>
									--%>
									</span>
			                   	</div>
			                </li>
		            		<li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>是否启用：
		                     		</label>
			                        <label class="lbl-ipt-tit radioWrap">
										<input type="radio" name="industry.disable" value="true"
											<c:if test="${industry.disable}"> checked="checked" </c:if> />
											是 
									</label>
									<label class="lbl-ipt-tit radioWrap">
										<input type="radio" name="industry.disable" value="false"<c:if test="${!industry.disable}"> checked="checked" </c:if>/>
											否
									</label>
			                   	</div>
			                </li>
			                
			                <li class="inputList-li">
			                    <div class="listItem txtarea">
			                    	<label class="lbl-ipt-tit">行业描述：</label>
			                        <textarea name="industry.industryDesc" class="formTextarea valid">${industry.industryDesc}</textarea>
			                        <span class="errortip">${errors['industry.industryName'][0]}</span>
			                    </div>
			                </li>
			            </ul>
			        </div>
			    </div>
				<div class="buttonArea">
					<%--<input class="formButton" value="确  定" type="button" onclick="document.editIndustryForm.submit();">&nbsp;&nbsp;
            		--%>
            		<input class="formButton" value="确  定" type="button" onclick="check();">&nbsp;&nbsp;
            		<input class="formButton" onclick="window.location='${path}/admin/industry!list.action'" value="返  回" type="button">
				</div>
			</form>
		</div>
		<script type="text/javascript">  
			displayDiv('typeBtn','typeDiv','${errors}');
		</script>
	</body>
</html>