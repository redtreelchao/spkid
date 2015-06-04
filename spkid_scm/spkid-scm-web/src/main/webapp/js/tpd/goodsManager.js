/**
 * 上下架操作
 */
function toggleOnSale(glId){
	if(isEmpty(glId)) {
		return;
	}
	if($("#"+glId).attr("flag")=="true"){
		return ;
	}
	$("#"+glId).attr("flag","true");
	$.post("/goods/goodsManager/toggleOnSale.json", {glId:glId}, function(result) {
		if (result.success) {
			if(result.result){
				$("#"+glId).html("<font color=\"red\">上架</font>");
			}else{
				$("#"+glId).html("<font color=\"gray\">下架</font>");
			}
		}else{
			alert(result.msg);
		}
		$("#"+glId).attr("flag","false");
	});
}

/**
 * 更新图片状态
 */
function renewPic(target){
	var flag=target.attributes["flag"].value;
	if(flag=="true"){
		return;
	}
	var goodsId=target.attributes["goodsId"].value;
	var colorId=target.attributes["colorId"].value;
	target.attributes["flag"].value=true;
	$.post("/goods/goodsManager/renewPic.json", {goodsId:goodsId,colorId:colorId}, function(result) {
		if (result.success) {
			if(result.result){
				$("span[goodsId="+goodsId+"][colorId="+colorId+"]").html("<span class='shootOn' title='已拍摄'></span>&nbsp;[<span class='bold'>已拍摄</span>]");
			}else{
				$("span[goodsId="+goodsId+"][colorId="+colorId+"]").html("<span class='shootOff' title='未拍摄'></span>&nbsp;[<span class='red bold'>未拍摄</span>]");
			}
		}else{
			alert(result.msg);
		}
		$("span[goodsId="+goodsId+"][colorId="+colorId+"]").attr("flag",false);
	});
}
/**
 * 审核商品
 * 
 * @param goodsId
 */
function audit(goodsId){
	if(isEmpty(goodsId)) {
		return;
	}
	$.post("/goods/goodsManager/goodsAudit.json", {goodsId:goodsId}, function(result) {
		if(result.success) {
			if(result.result){
				$("div[flg=goodsAudit]").html("已审核<img src=\"/img/yes.gif\" alt=\"已审核\"/><br>");
			}else{
				$("div[flg=goodsAudit]").html("<input type=\"button\" value=\"审核\" onclick=\"javascript:audit("+goodsId+")\"/>");
			}
		}else{
			alert(result.msg);
		}
	});
}

/**
 * 删除商品
 * @param goodsId
 */
function doDelete(goodsId){
	if(isEmpty(goodsId)) {
		return;
	}
	if(confirm("确定删除？")) {
		$.post("/goods/delete.json", {goodsId:goodsId}, function(result) {
			if(result == "") {
				alert("删除成功！");
				submitSearch();
			}else{
				alert(result);
			}
		});
	}
}

/**
 * 搜索查询
 */
function doSeachForm(){
	$("#searchForm").attr("target","_self");
	$("#searchForm").attr("action","/goods/list/query.htm");
	submitSearch();
}
/**
 * 导出Excel文件
 */
function doExportXLS(){
	$("#searchForm").attr("target","_blank");
	$("#searchForm").attr("action","/goods/goodsManager/list/exportXls.htm");
	$("#searchForm").submit();
}

function exportExcel(){
	$('#searchForm').attr('action', '/goods/list/export.htm');
	$('#searchForm').submit();
	$('#searchForm').attr('action', '/goods/list/query.htm');
}

/**
 * 检查是否被使用
 */
function checkUnique(key) {
	var value = $("#"+key).val();
	if(isEmpty(value)) {
		return;
	}
	var goodsId = $("#goodsId").val();
	var url = "/goods/validation.json";
	$.post(url, {key:key,value:value,goodsId:goodsId}, function(result) {
		if(result == "") {
			$("#"+key+"Tip").hide();
		} else {
			$("#"+key+"Tip").html(result);
			$("#"+key+"Tip").show();
		}
	});
}
isEmpty = function (str) {
	return (typeof (str) === "undefined" || str === null || (str.length === 0));
};

/**
 * 全选
 */
function selectAll() {
	if($('input:checked[name=ck_check_all]').length){
		$('input[name=goodsIds][type=checkbox]').attr('checked',true);
		
	}else{
		$('input[name=goodsIds][type=checkbox]').attr('checked',false);
	}
}

/**
 * 批量提交审核
 */
function doBatchSubmit(goodsIds) {
	if(!confirm("确定批量提交审核？")) return false;
	var checkAry = document.getElementsByName(goodsIds);
	var ids = '';
	for( var i = 0; i < checkAry.length; i++ ) {
		if (checkAry[i].checked) {
			ids += checkAry[i].value + ',';
		}
	}
	
	if (ids == '') {
		alert('请选择要提交审核的商品！');
		return false;
	}
	
	var url = "/goods/batchSubmit.json";
	$.post(url, {ids:ids}, function(result) {
		if (result == "") {
			alert("批量提交审核成功！");
			submitSearch();
		} else {
			alert(result);
		} 
	});
}

/**
 * 批量删除
 */
function doBatchDelete(goodsIds) {
	if(!confirm("确定批量删除？")) return false;
	var checkAry = document.getElementsByName(goodsIds);
	var ids = '';
	for( var i = 0; i < checkAry.length; i++ ) {
		if (checkAry[i].checked) {
			ids += checkAry[i].value + ',';
		}
	}
	
	if (ids == '') {
		alert('请选择要删除的商品！');
		return false;
	}
	
	var url = "/goods/batchDelete.json";
	$.post(url, {ids:ids}, function(result) {
		if (result == "") {
			alert("批量删除成功！");
			submitSearch();
		} else {
			alert(result);
		} 
	});
}



// ---- admin --------------------------------------------
/**
 * 审核
 */
function doCheck(goodsId){
	if(isEmpty(goodsId)) {
		return;
	}
	if(confirm("确定审核？")) {
		check([goodsId]);
	}
}

/**
 * 驳回
 */
function doReject(goodsId){
	if(isEmpty(goodsId)) {
		return;
	}
	if(confirm("确定驳回？")) {
		reject([goodsId]);
	}
}


/**
 * 批量审核
 */
function doBatchCheck(goodsIds) {
	if(!confirm("确定批量审核？")) {
		return false;
	}
	
	var ids = [];
	var checkAry = document.getElementsByName(goodsIds);
	for( var i = 0; i < checkAry.length; i++ ) {
		if (checkAry[i].checked) {
			ids.push(checkAry[i].value);
		}
	}
	
	if (ids.length == 0) {
		alert('请选择要审核的商品！');
		return false;
	}
	
	check(ids);
}

/**
 * 批量驳回
 */
function doBatchReject(goodsIds) {
	if(!confirm("确定批量驳回？")) {
		return false;
	}
	
	var ids = [];
	var checkAry = document.getElementsByName(goodsIds);
	for( var i = 0; i < checkAry.length; i++ ) {
		if (checkAry[i].checked) {
			ids.push(checkAry[i].value);
		}
	}
	
	if (ids.length == 0) {
		alert('请选择要驳回的商品！');
		return false;
	}
	
	reject(ids);
}


function check(ids) {
	var url = "/goods/check.json";
	$.post(url, {ids:ids}, function(result) {
		if (result == "") {
			alert("审核成功！");
			submitSearch();
		} else {
			alert(result);
		} 
	});
}

function reject(ids) {
	var url = "/goods/reject.json";
	$.post(url, {ids:ids}, function(result) {
		if (result == "") {
			alert("驳回成功！");
			submitSearch();
		} else {
			alert(result);
		} 
	});
}


/**
 * 上架
 */
function doOnline(goodsId){
	if(isEmpty(goodsId)) {
		return;
	}
	
	if(confirm("确定上架？")) {
		online([goodsId]);
	}
}

/**
 * 下架
 */
function doOffline(goodsId){
	if(isEmpty(goodsId)) {
		return;
	}
	if(confirm("确定下架？")) {
		offline([goodsId]);
	}
}


/**
 * 批量上架
 */
function doBatchOnline(goodsIds) {
	if(!confirm("确定批量上架？")) {
		return false;
	}
	
	var ids = [];
	var checkAry = document.getElementsByName(goodsIds);
	for( var i = 0; i < checkAry.length; i++ ) {
		if (checkAry[i].checked) {
			ids.push(checkAry[i].value);
		}
	}
	
	if (ids.length == 0) {
		alert('请选择要上架的商品！');
		return false;
	}
	
	online(ids);
}

/**
 * 批量下架
 */
function doBatchOffline(goodsIds) {
	if(!confirm("确定批量下架？")) {
		return false;
	}
	
	var ids = [];
	var checkAry = document.getElementsByName(goodsIds);
	for( var i = 0; i < checkAry.length; i++ ) {
		if (checkAry[i].checked) {
			ids.push(checkAry[i].value);
		}
	}
	
	if (ids.length == 0) {
		alert('请选择要下架的商品！');
		return false;
	}
	
	offline(ids);
}



function online(ids) {
	var url = "/goods/online.json";
	$.post(url, {ids:ids}, function(result) {
		if (result == "") {
			alert("上架成功！");
			submitSearch();
		} else {
			alert(result);
		} 
	});
}

function offline(ids) {
	var url = "/goods/offline.json";
	$.post(url, {ids:ids}, function(result) {
		if (result == "") {
			alert("下架成功！");
			submitSearch();
		} else {
			alert(result);
		} 
	});
}