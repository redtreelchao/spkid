function doDelete(id) {
	if (isEmpty(id)) {
		return;
	}
	// var checkurl = "/workorder/list/beUsed.htm";
	// $.post(checkurl, {id:id}, function(result) {
	// if(result=="success") {
	if (confirm("确定删除？")) {
		var url = "/workorder/delete.htm";
		$.post(url, {
			id : id
		}, function(result) {
			if (result == "success") {
				submitSearch();
			} else {
				alert(result);
			}
		});
	}
	// } else {
	// alert("已被使用，无法删除");
	// }
	// });
}

isEmpty = function(str) {
	return (typeof (str) === "undefined" || str === null || (str.length === 0));
};

/**
 * 删除工单附件
 */
function doDeleteImage(woId,type) {
	var url = "/workorder/deleteImage.htm";
	if (confirm("确认删除?")) {
		$.post(url, {woId : woId, type : type}, function(result) {
			if (result == 'success') {
				$('#a').hide();
				$('#del').hide();
				$('#file').show();
			} else {
				alert("删除附件失败！！");
			}
		});
	}
}


function doCheck(id) {
	if(isEmpty(id)) {
		return;
	}
	
	if(confirm("确定审核？")) {
		var url = "/workorder/check.htm";
		$.post(url, {id:id}, function(result) {
			if(result=="success") {
				alert("审核成功！");
				submitSearch();
			} else {
				alert(result);
			}
		});
	}
}

function doCheckCancel(id) {
	if(isEmpty(id)) {
		return;
	}
	
	if(confirm("确定作废？")) {
		var url = "/workorder/checkCancel.htm";
		$.post(url, {id:id}, function(result) {
			if(result=="success") {
				alert("作废成功！");
				submitSearch();
			} else {
				alert(result);
			}
		});
	}
}

/**
 * 保存一张新的工单
 * @param type
 */
function doSave(type) {
	var pdv = new Validate("processForm");
	if (pdv.validate()) {
		$("#processForm").append('<input type="hidden" name="type" value="'+type+'"/>'); 
		$("#processForm").submit();
//		$("#type").val(type);
//		$("#processForm").ajaxSubmit(function(result) {
//			if (result === 'success') {
//				window.location.replace("/workorder/addSuccess.htm");
//			} else {
//				alert(result);
//			}
//		});
	}
}

/**
 * 检查订单号是否存在
 */
function checkUnique(key) {
	var orderSn= $("#orderSn").val();
	var url = "/workorder/list/checkOrderSnExist.htm";
	$.post(url, {orderSn:orderSn}, function(result) {
		if(result>0) {
			$("#"+key+"Tip").hide();
		} else {
			$("#"+key+"Tip").show();
		}
	});
}


var dialog;
function getOrderGoods() {
	var orderSn= $("#orderSn").val();
	if(isEmpty(orderSn)) {
		alert("请先输入订单号");
		return;
	}
	if($("#orderSnTip").is(':visible')) {
		alert("订单号不存在");
		return;
	}
	dialog = art.dialog({
		title: '选择供应商',
		width: 700,
	    // height: 500,
		resize: false,
		fixed: true,
		follow: document.getElementById("btSelectProvider"),
		lock: true,
	    background: '#EBF0F5', // 背景色
	    opacity: 0.68	// 透明度
		//okVal: '确定',
		//cancel: true
	});
	
	var url = "/workorder/list/getOrderGoods/"+orderSn+".htm";
	$.get(url, function(result) {
		dialog.content(result);
	});
}

function selectProvider(providerId, providerCode) {
	$("#providerId").val(providerId);
	$("#providerCode").val(providerCode);
	dialog.close();
}
