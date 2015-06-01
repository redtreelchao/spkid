function doDelete(id) {
	if(isEmpty(id)) {
		return;
	}
//	var checkurl = "/notice/list/beUsed.htm";
//	$.post(checkurl, {id:id}, function(result) {
//		if(result=="success") {
			if(confirm("确定删除?")) {
				var url = "/notice/delete.htm";
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

/**
 * 查询列表
 */
function submitSearch() {
	$("#pageNo").val("1");	
	if ($("#selePageSize").val()) {
		$("#pageSize").val($("#selePageSize").val());
	}
	var pdv = new Validate("searchForm");
	if (pdv.validate()) {
		TB_showMaskLayer("正在查询...");
		$("#searchForm").ajaxSubmit(function(result) {
			$("#contentPanel").html(result);
			TB_removeMaskLayer();
		});
	}
}

function doAudit(noticeId) {
	var url = "/notice/audit.htm";
	if(confirm("确认审核?")){
		$.post(url, {noticeId:noticeId}, function(result) {
			if (result == 'success') {
				submitSearch();
			} else {
				alert("审核失败！！");
			}
		});
	}
}

function doSave() {
	var pdv = new Validate("processForm");
	if (pdv.validate()) {
		$("#processForm").submit();
	}
}

isEmpty = function (str) {
	return (typeof (str) === "undefined" || str === null || (str.length === 0));
};