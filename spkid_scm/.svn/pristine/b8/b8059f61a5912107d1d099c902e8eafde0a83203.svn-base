function doDelete(id) {
	if(isEmpty(id)) {
		return;
	}
//	var checkurl = "/log/list/beUsed.htm";
//	$.post(checkurl, {id:id}, function(result) {
//		if(result=="success") {
			if(confirm("确定删除？")) {
				var url = "/log/delete.htm";
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

function doSave() {
	var pdv = new Validate("processForm");
	if (pdv.validate()) {
		$("#processForm").submit();
	}
}

isEmpty = function (str) {
	return (typeof (str) === "undefined" || str === null || (str.length === 0));
};