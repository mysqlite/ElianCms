 var setting = {
        	//添加删编辑
            view: {
                addHoverDom: addHoverDom,
                removeHoverDom: removeHoverDom,
                selectedMulti: true
            },
            //-----------------------
            edit: {
                enable: true,
                //添加删除编辑
                editNameSelectAll: true,
                //-------
                showRemoveBtn: true,//是否出现删按钮
                showRenameBtn: true//是否出现编辑按钮
            },
            data: {
                simpleData: {
                    enable: true
                }
            },
            callback: {
                beforeDrop: beforeDrop,       //拖拽落下之前执行的方法
                onDrop:onDrop,
                //添加删除修改
               beforeEditName: beforeEditName,//编辑节点名称执行的方法
               beforeRemove: beforeRemove,    //删除节点之前执行的方法
               beforeRename: beforeRename,    //移除节点之前执行的方法
               onRemove: onRemove,            //移除节点之后执行的方法
               onRename: onRename             //删除节点之后执行的方法
               //--------------------
            }
        };
        //添加删除编辑BEGIN
        var log, className = "dark";
        //此方法返回值控制是否可拖拽
        //拖拽之前执行
        function beforeDrag(treeId, treeNodes) {
        	alert(treeNodes.id);
            for (var i=0,l=treeNodes.length; i<l; i++) {
                if (treeNodes[i].drag === false) {
                    return false;
                }
            }
            return true;
        }
        //编辑节点前执行
        function beforeEditName(treeId, treeNode) {
            className = (className === "dark" ? "":"dark");
            showLog("[ "+getTime()+" beforeEditName ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
            var zTree = $.fn.zTree.getZTreeObj("treeDemo");
            zTree.selectNode(treeNode);
            return true;//confirm("进入节点 -- " + treeNode.name + " 的编辑状态吗？");
        }
        //移除节点之前
        function beforeRemove(treeId, treeNode) {
            className = (className === "dark" ? "":"dark");
            showLog("[ "+getTime()+" beforeRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
            var zTree = $.fn.zTree.getZTreeObj("treeDemo");
            zTree.selectNode(treeNode);
            return confirm("确认删除 节点   " + treeNode.name + "  吗？");
        }
        //编辑节点前被执行
        function showLog(str) {
            if (!log) log = $("#log");
            log.append("<li class='"+className+"'>"+str+"</li>");
            if(log.children("li").length > 8) {
                log.get(0).removeChild(log.children("li")[0]);
            }
        }
         //编辑节点时执行，获取时间戳
       function getTime() {
            var now= new Date(),
            h=now.getHours(),
            m=now.getMinutes(),
            s=now.getSeconds(),
            ms=now.getMilliseconds();
            return (h+":"+m+":"+s+ " " +ms);
        }
        //移除【添加、编辑、删除】按钮，鼠标离开节点上时执行
        function removeHoverDom(treeId, treeNode) {
            $("#editBtn_"+treeNode.id).unbind().remove();
        	$("#addBtn_"+treeNode.id).unbind().remove();
        };
         function selectAll() {
            var zTree = $.fn.zTree.getZTreeObj("treeDemo");
            zTree.setting.edit.editNameSelectAll =  $("#selectAll").attr("checked");
        }
        function openAll(){
          var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
            treeObj.expandAll(true);
        } 
        function closeAll(){
          var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
            treeObj.expandAll(false);
        }
       	