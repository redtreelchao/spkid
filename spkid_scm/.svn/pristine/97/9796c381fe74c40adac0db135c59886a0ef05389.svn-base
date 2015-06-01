isEmpty = function (str) {
	return (typeof (str) === "undefined" || str === null || (str.length === 0));
};

function doEdit() {
	//editor1.sync();
	$("#processForm").ajaxSubmit(function(result) {
		if (result == 'success') {
			alert('提交成功！');
			window.location.href = '/provider/main.htm';
		} else {
			alert(result);
		}
	});
}

function doEditBrand() {
	$("#processForm").ajaxSubmit(function(result) {
		if (result == 'success') {
			alert('提交成功！');
			window.location.href = '/provider/main.htm';
		} else {
			alert(result);
		}
	});
}
function chongzhi(){
	var url = "/provider/chongzhi/show.htm";
	var data = "height=80&width=380";
	var href=url + "?" + data; 
	
	var title = "充值";
    TB_show(title, href,submitSearch);
    this.blur();
}
function saveOk(){	
	TB_remove_Call(submitSearch);
}