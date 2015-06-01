function changeCaptcha(img) {
    var src = img.src;
    if(src.indexOf('?') > -1) {
        src = src.substring(0, src.indexOf('?'));
    }
    img.src = src + '?' + new Date().getTime();
}

function doLogin() {
	var formId = "loginForm";
	var validate = new Validate(formId);
	if (!validate.validate()) {
		return;
	}
	
	var userName = $("#userName").val();
	var password = $("#password").val();
	var captcha = $("#captcha").val();
	var adminLogin = "0";
	if ($("#adminLogin").attr("checked") == "checked") {
		adminLogin = "1";
	}
	var url = "/login/check.htm";
	
	$.post(url, {userName:userName, password:password, captcha:captcha, adminLogin:adminLogin}, function(result) {
		if(result=="success") {
			$("#" + formId).submit();
		} else {
			alert(result);
		}
	});
}

