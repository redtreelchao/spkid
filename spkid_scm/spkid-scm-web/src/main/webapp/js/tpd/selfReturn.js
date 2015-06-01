function doDelete(id) {
	if(isEmpty(id)) {
		return;
	}
//	var checkurl = "/selfreturn/list/beUsed.htm";
//	$.post(checkurl, {id:id}, function(result) {
//		if(result=="success") {
			if(confirm("确定删除？")) {
				var url = "/selfreturn/delete.htm";
				$.post(url, {id:id}, function(result) {
					if(result=="success") {
						submitSearch();
					} else {
						alert(result);
					}
				});
			}
//		} else {
//			alert("已被使用，无法删除");
//		}
//	});
}

function doSave(applyId) {
	
	if($("#suggestType").val() == 2) {
		if(isEmpty($("#suggestContent").val())) {
			alert("异常时请填写内容");
			return;
		}
	}
	
	var pdv = new Validate("processForm");
	if (pdv.validate()) {
		$("#processForm").submit();
	}
//	var suggestContent = $("#suggestContent").val();
//	var suggestType = $("#suggestType").val();
//	var url = "/selfreturn/edit.htm";
//	$.post(url,{applyId:applyId,suggestType:suggestType,suggestContent:suggestContent}, function(result){
//		if(suggestContent != null && suggestContent != ''){
//			alert("审核成功！");
//			location.href= "/selfreturn/list/main.htm";
//		} else {
//			alert("请填写意见！");
//		}
//	});
}

isEmpty = function (str) {
	return (typeof (str) === "undefined" || str === null || (str.length === 0));
};