isEmpty = function (str) {
	return (typeof (str) === "undefined" || str === null || (str.length === 0));
};

function selectAll() {
	if($('input:checked[name=ck_check_all]').length){
		$('input[name=cardIds][type=checkbox]').attr('checked',true);
		
	}else{
		$('input[name=cardIds][type=checkbox]').attr('checked',false);
	}
}

function doEdit(id) {
	if(isEmpty(id)) {
		return;
	}
	if(confirm("确定使用吗？")) {
		var url = "/productcard/edit.json";
		$.post(url, {id:id}, function(result) {
			if(result=="success") {
				alert("编辑成功！");
				submitSearch();
			} else {
				alert(result);
			}
		});
	}
}

function doBatchEdit(cardIds) {
	if(!confirm("确定批量使用？")) return false;
	var checkAry = document.getElementsByName(cardIds);
	var ids = '';
	for( var i = 0; i < checkAry.length; i++ ) {
		if (checkAry[i].checked) {
			ids += checkAry[i].value + ',';
		}
	}
	
	if (ids == '') {
		alert('请选择使用的卡密！');
		return false;
	}
	
	var url = "/productcard/batchEdit.json";
	$.post(url, {ids:ids}, function(result) {
		if (result == "success") {
			alert("批量使用成功！");
			submitSearch();
		} else {
			alert(result);
		} 
	});
}

function doDelete(id) {
	if(isEmpty(id)) {
		return;
	}
	if(confirm("确定删除？")) {
		var url = "/productcard/delete.json";
		$.post(url, {id:id}, function(result) {
			if(result=="success") {
				alert("删除成功！");
				submitSearch();
			} else {
				alert(result);
			}
		});
	}
}

function doBatchDelete(cardIds) {
	if(!confirm("确定批量删除？")) return false;
	var checkAry = document.getElementsByName(cardIds);
	var ids = '';
	for( var i = 0; i < checkAry.length; i++ ) {
		if (checkAry[i].checked) {
			ids += checkAry[i].value + ',';
		}
	}
	
	if (ids == '') {
		alert('请选择删除的卡密！');
		return false;
	}
	
	var url = "/productcard/batchDelete.json";
	$.post(url, {ids:ids}, function(result) {
		if (result == "success") {
			alert("批量删除成功！");
			submitSearch();
		} else {
			alert(result);
		} 
	});
}

function exportExcel(){
	$('#searchForm').attr('action', '/productcard/list/export.htm');
	$('#searchForm').submit();
	$('#searchForm').attr('action', '/productcard/list/query.htm');
}