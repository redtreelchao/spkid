//！== 此文件仅在最初版本的批量导入使用，最新的批量导入不能引入此文件。
function optionVerity(batchNo){
	if(confirm("审核不可回退，确定审核？")) {
		$ .post("/goods/import/importVerity.htm", {batchNo:batchNo}, function(data) {
			if(data.success){
				alert("审核发起成功");
				window.location.href=window.location.href;
			}else{
				alert(data.msg);
			}
		});
	}
}

function optionGallery(batchNo){
	$ .post("/goods/import/goodsGallery.htm", {batchNo:batchNo}, function(data) {
		if(data.success){
			alert("导入请求成功");
			window.location.href=window.location.href;
		}else{
			alert(data.msg);
		}
	});
}

function optionBCS(batchNo){
	$ .post("/goods/import/goodsBcs.htm", {batchNo:batchNo}, function(data) {
		if(data.success){
			alert("导入请求成功");
			window.location.href=window.location.href;
		}else{
			alert(data.msg);
		}
	});
}
