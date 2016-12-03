function validateLogin(form) {
	var userName = $("#userName").val();
	var password = $("#password").val();
	if(null == userName || "" == userName.trim()) {
		alert(alertMessage.provideUserName);
		$("#userName").focus();
		return false;
	} else if(null == password || "" == password.trim()) {
		alert(alertMessage.providePassword);
		$("#password").focus();
		return false;
	}
	return true;
}