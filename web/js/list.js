/**
 *	通过jsonp数据类型跨域动态添加table内容
 */
function getTableHtml(id, url) {
	if(url==null || undefined == url)
		url = document.getElementById('tableUrl').value;
	getJsonpData(id, url);
}

function toPage(pNo) {
	var tableUrl = document.getElementById('tableUrl').value;
	var url = tableUrl + "&pageNo=" + pNo;
	getTableHtml("table", url);
}

function toPageAjax(pNo) {
    var tableUrl = document.getElementById('tableUrl').value;
    var url = tableUrl + "&pageNo=" + pNo;
    getListAjax('',url);
}
function goAjax(pNo, currentPage) {
    var pageNo = document.getElementById(pNo);
    var cPage = document.getElementById(currentPage);
    if (Number(pageNo.value) == Number(cPage.value))
        return;
    toPageAjax(Number(pageNo.value, 1));
}

function go(pNo, currentPage) {
	var pageNo = document.getElementById(pNo);
	var cPage = document.getElementById(currentPage);
	if (Number(pageNo.value) == Number(cPage.value))
		return;
	toPage(Number(pageNo.value, 1));
}

function numberFormat(pageNo, lastPage) {
	var idNum = document.getElementById(pageNo);
	if (!Number(idNum.value) || idNum.value == null || idNum.value == undefined) {
		idNum.value = 1;
	} else {
		var last = document.getElementById(lastPage);
		if (Number(idNum.value) > Number(last.value)) {
			idNum.value = last.value;
		}
	}
	idNum.value = Number(idNum.value);
}

function checkEnter(event, clickId) {
　　 if(event.keyCode == 13){
　　      	document.getElementById(clickId).click();
　　  }
}