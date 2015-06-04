function submitSearch() {
	
	var param = "", info = "", queryType, queryValue;
	var orderSnOrg = $("input[name=orderSn]").val(),
		returnSnOrg = $("input[name=returnSn]").val();
	var orderSn = orderSnOrg.trim(), returnSn = returnSnOrg.trim();
	if (orderSn != "" && returnSn != "") {
    	alert("订单号与退货单号不能同时搜索！");
		return false;
	}
	if (orderSn == "" && returnSn == "") {
    	alert("请输入搜索条件！");
		return false;
	}
	
	if (orderSn != "") {
		info = "相关退货单号：<br/>";
		queryType = 1;
		queryValue = orderSn;
	} else if (returnSn != "") {
		info = "相关订单号：<br/>";
		queryType = 2;
		queryValue = returnSn;
	}
	
	TB_showMaskLayer("正在查询...");
	var url = "/orderreturnquery/list/query.htm";
	$.post(url, {queryType:queryType,queryValue:queryValue}, function(result) {
		$("#contentPanel").html(info + (result.result==null?"无":result.result));
		TB_removeMaskLayer();
	});
}