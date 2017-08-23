function keyLogin(){  
	if (event.keyCode==13)   //回车键的键值为13  
	    document.getElementById("submit").click(); //调用登录按钮的登录事件  
}

function checkLogin() {
	var account = document.getElementById('account');
	var password = document.getElementById('password');
	var captcha = document.getElementById('captcha');
	if (account.value == '') {
		alert('用户名不能为空！');
		account.focus();
		return false;
	}
	if (password.value == '') {
		alert('密码不能为空！');
		password.focus();
		return false;
	}
	if(captcha!=null && captcha.value==''){
		alert('请输入验证码！');
		captcha.focus();
		return false;
	}
	var remName = document.getElementById("remName");
	if (remName.checked) {
		addCookie("account", account.value + "$" + password.value, 24 * 7);
	}
	//else{
	//	deleteCookie("account");
	//}
	return true;
}

function addCookie(name, value, hours) {
	var str = name + "=" + escape(value);
	if (hours > 0) {
		var date = new Date();
		var ms = hours * 3600 * 1000;
		date.setTime(date.getTime() + ms);
		str += "; expires=" + date.toGMTString();
	}
	document.cookie = str+";path=/";
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

function getCookie(name) {//获取指定名称的cookie的值
	var arrStr = document.cookie.split("; ");
	for ( var i = 0; i < arrStr.length; i++) {
		var temp = arrStr[i].split("=");
		if (temp[0] == name)
			return unescape(temp[1]);
	}
}