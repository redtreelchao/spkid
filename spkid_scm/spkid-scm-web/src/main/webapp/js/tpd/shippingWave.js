function doSave() {
	var pdv = new Validate("processForm");
	if (pdv.validate()) {
		$("#processForm").submit();
	}
}

function doImport() {
	var pdv = new Validate("importForm");
	if (pdv.validate()) {
		TB_showMaskLayer("正在导入...");
		$("#importForm").ajaxSubmit(function(result) {
			$("#contentPanel").html(result);
			TB_removeMaskLayer();
		});
	}
}

function generateWave() {
	TB_showMaskLayer("正在生成波次...");
	$.post("/shippingwave/generateWave.htm", function(result) {
		TB_removeMaskLayer();
		if (result == "success") {
			submitSearch();
			refreshOrderNum();
		} else {
			alert(result);
		}
	});
}

function refreshOrderNum() {
	$.get("/shippingwave/list/getOrderNum.htm", function(result) {
		$("#generateBt").html("生成波次(" + result + ")")
	});
}

function printOrder() {
	var orderSn = $("#orderSn").val();
	if (isEmpty(orderSn)) {
		alert("请输入订单号");
		return;
	}
	var url = "/shippingwave/printOrder/" + orderSn + ".htm";
	window.open(url);
}

isEmpty = function(str) {
	return (typeof (str) === "undefined" || str === null || (str.length === 0));
};