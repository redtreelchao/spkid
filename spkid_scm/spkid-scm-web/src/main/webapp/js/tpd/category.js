/**
 * 查询商品分类列表
 */
function submitSearch() {
	TB_showMaskLayer("正在查询...");
	$("#searchForm").ajaxSubmit(function(result) {
		$("#contentPanel").html(result);
		TB_removeMaskLayer();
	});
}

/**
 * 删除商品分类
 */
function doDelete(id) {
	if (isEmpty(id)) {
		return;
	}
	var checkurl = "/goods/category/checkBeenUsed.htm";
	$.post(checkurl, {
		catId : id,
		option : 'delete'
	}, function(result) {
		if (result == "success") {
			if (confirm("确定删除？")) {
				var url = "/goods/category/delete.htm";
				$.post(url, {
					catId : id
				}, function(result) {
					if (result == "success") {
						submitSearch();
					} else {
						alert(result);
					}
				});
			}
		} else {
			alert(result);
		}
	});
}

/**
 * 检查'商品分类代码','商品分类名称'是否被使用
 */
function checkUnique(key) {
	var value = $("#" + key).val();
	if (isEmpty(value)) {
		return;
	}
	var catId = $("#catId").val();
	var url = "/goods/category/checkUnique.htm";
	$.post(url, {
		key : key,
		value : value,
		catId : catId
	}, function(result) {
		if (result == "success") {
			$("#" + key + "Tip").hide();
		} else {
			$("#" + key + "Tip").show();
		}
	});
}

/**
 * 表单提交
 */
function doSave() {

	var pdv = new Validate("categoryForm");
	if (pdv.validate()) {
		$("#categoryForm").submit();
	}
}

/**
 * 判断字符串是否为空
 */
isEmpty = function(str) {
	return (typeof (str) === "undefined" || str === null || (str.length === 0));
};