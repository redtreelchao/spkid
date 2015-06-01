isEmpty = function (str) {
	return (typeof (str) === "undefined" || str === null || (str.length === 0));
};

function doSearch(formId) {
	if (isEmpty($("input[name=goodsSn]").val())) return;
	TB_showMaskLayer("正在载入...");
	$("#" + formId).ajaxSubmit(function(result) {
		if (result.success) {
			if (result.msg)
				editor1.html(result.msg);
			$("#productId").val(result.result);
			$("#detailDiv").attr("style", "");
		} else {
			alert(result.msg);
			$("#detailDiv").attr("style", "display:none;");
		}
		TB_removeMaskLayer();
	});
}
$(function() {
    $(".enterQuery").bind('keydown', function(event){
    	if(event.keyCode==13){
    		doSearch();
    	}
    });
    doSearch();
});

function doValidate(formId) {
	var pdv = new Validate(formId);
	return pdv.validate();
}

function doSave(formId) {
	editor1.sync();
	if (doValidate(formId)) {
		var action = $("#"+formId).attr("action");
		$("#"+formId).attr("action",action+"?isSubmit=0");
		$("#"+formId).submit();
	}
}

function doSubmit(formId) {
	editor1.sync();
	if (confirm("确定提交审核吗？") && doValidate(formId)) {
		var action = $("#"+formId).attr("action");
		$("#"+formId).attr("action",action+"?isSubmit=1");
		$("#"+formId).submit();
	}
}

function doCheck(tuanId) {
	if (confirm("确定审核吗？")) {
		var url = "/mamituan/check.htm";
		$.post(url, {tuanId : tuanId}, function(result) {
			if (result == "success") {
				alert("审核成功！");
				submitSearch();
			} else {
				alert(result);
			}
		});
	}
}

function doReject(tuanId) {
	if (confirm("确定驳回吗？")) {
		var url = "/mamituan/reject.htm";
		$.post(url, {tuanId : tuanId}, function(result) {
			if (result == "success") {
				alert("驳回成功！");
				submitSearch();
			} else {
				alert(result);
			}
		});
	}
}

function doStop(tuanId) {
	if (confirm("确定停止吗？")) {
		var url = "/mamituan/stop.htm";
		$.post(url, {tuanId : tuanId}, function(result) {
			if (result == "success") {
				alert("停止成功！");
				submitSearch();
			} else {
				alert(result);
			}
		});
	}
}

function doDelete(tuanId) {
	if (confirm("确定删除吗？")) {
		var url = "/mamituan/delete.htm";
		$.post(url, {tuanId : tuanId}, function(result) {
			if (result == "success") {
				alert("删除成功！");
				submitSearch();
			} else {
				alert(result);
			}
		});
	}
}