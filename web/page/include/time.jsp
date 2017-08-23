<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<style>
*+html{overflow-x:hidden;}
.time{ font-size: 14px;color: #4F8FB2;height: 40px;line-height: 40px;text-align: center}
</style>
       <div class="ui-menu-hd">
           <span id="dates" class="time"></span>
       </div>
       <script type="text/javascript">
            function datese(){
                var dateTime =new Date();
                var yy =dateTime.getFullYear();
                var MM =dateTime.getMonth() + 1; 
                var dd =dateTime.getDate();
                var week=dateTime.getDay();
                var days=[ "日 ","一 ","二 ","三 ","四 ","五 ","六 "];
                document.getElementById("dates").innerHTML =yy+"年"+MM+"月"+dd+"日 "+"星期"+ days[week];
                //setTimeout("datese();", 60000);
            }
            datese();
            </script>
