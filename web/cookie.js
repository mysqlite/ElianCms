function addCookie(name, value, hours) {
	var str = name + "=" + escape(value);
	if (hours > 0) {
		var date = new Date();
		var ms = hours * 3600 * 1000;
		date.setTime(date.getTime() + ms);
		str += "; expires=" + date.toGMTString();
	}
	document.cookie = str;
}

function deleteCookie(name) {
	var exp = new Date();
	exp.setTime(exp.getTime() - 1);
	var cval = getCookie(name);
	if (cval != null)
		document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
}

function checkCookie() {
	var account = getCookie("account");
	if (account != null && account != undefined) {
		var temp = account.split("$");
		if (temp.length == 2) {
			document.getElementById('account').value = temp[0];
			document.getElementById('password').value = temp[1];
		}
	}
}

function getCookie(name) {//
	var arrStr = document.cookie.split("; ");
	for ( var i = 0; i < arrStr.length; i++) {
		var temp = arrStr[i].split("=");
		if (temp[0] == name)
			return unescape(temp[1]);
	}
}