isEmpty = function(str) {
	return (typeof (str) === "undefined" || str === null || (str.length === 0));
};

/**
 * 查询上传短信列表
 */
function submitSearch() {
	TB_showMaskLayer("正在查询...");
	$("#searchForm").ajaxSubmit(function(result) {
		$("#contentPanel").html(result);
		TB_removeMaskLayer();
	});
}

function doSubmit(submitType) {
	var pdv = new Validate("importForm");
	var sendType=$("#sendType").val();
	if (pdv.validate()) {
		var select_val = $("#sendType").children('option:selected').val(); 
		var flag=true;	
		if(select_val=='2'){
			flag=false;
			var num=$("#smsNum").val();			
			var patrn = /^\d+$/;			
			if(patrn.test(num)){
				var n=parseInt(num);
				if(n>0 && n<=5000){
					flag=true;					
				}
			}
		}
		if(flag){
			var msg = "";
			if (submitType == 0) {
				msg = "保存";
			} else if(submitType == 1) {
				msg = "提交";
			}
			if (confirm("确定" + msg + "吗？")) {
				$("#importForm").attr("action","/sms/upload.htm?submitType="+submitType+"&sendType="+sendType);
				$("#importForm").ajaxSubmit(function(result) {
					if (result == 'success') {
						alert(msg + "成功！");
						window.location.href = '/sms/list/main.htm';
					} else {
						alert(result);
					}
				});
			}
		}else{
			alert("随机条数不合法，应为1到5000之间！");
		}
	}
}

function doDelete(smsId) {
	if (confirm("确定删除吗？")) {
		var url = "/sms/delete.htm";
		$.post(url, {smsId : smsId}, function(result) {
			if (result == "success") {
				alert("删除成功！");
				submitSearch();
			} else {
				alert(result);
			}
		});
	}
}

function doCheck(smsId) {
	if (confirm("确定审核吗？")) {
		var url = "/sms/check.htm";
		$.post(url, {smsId : smsId}, function(result) {
			if (result == "success") {
				alert("审核成功！");
				submitSearch();
			}else if (result == "fail") {
				//alert("审核未通过，驳回请求！");
				alert("余额不足，系统将自动驳回申请！");
				submitSearch();
			} else {
				alert(result);
			}
		});
	}
}

function showReject(smsId) {
	var url = "/sms/reject/show.htm";
	var data = "height=100&width=380";
	data=data+"&smsId="+smsId;
	var href=url + "?" + data; 
	
	var title = "驳回";
    TB_show(title, href,submitSearch);
    this.blur();
}
function doReject(smsId) {
	if (confirm("确定驳回吗？")) {
		var url = "/sms/reject.htm";
		var memo=$("#memo").val();		
		if(memo==""){
			alert("驳回原因不能为空！");
		}else{
			$.post(url, {smsId : smsId , memo : memo}, function(result) {
				if (result == "success") {
					alert('驳回成功！');	
					TB_remove_Call(submitSearch);
				} else {
					alert(result);
				}
			});
		}
	}
}
function doCancel(smsId) {
	if (confirm("确定作废吗？")) {
		var url = "/sms/cancel.htm";
		$.post(url, {smsId : smsId}, function(result) {
			if (result == "success") {
				alert("作废成功！");
				submitSearch();
			} else {
				alert(result);
			}
		});
	}
}