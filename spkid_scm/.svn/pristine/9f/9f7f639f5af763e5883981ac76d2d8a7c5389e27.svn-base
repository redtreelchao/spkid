//~!  处理进度及状态的JS,最新的导入方法。

function roll(obj){
	var typeVal = $(obj).attr("type");
	$('body').everyTime('5s',typeVal,function(){
		progress(obj);
    }); 
}

function progress(obj){
	var typeVal = $(obj).attr("type");
	$ .post("/batchImport/getProgress.json",{type:typeVal}, function(data) {
		if(data.success){
			var res = data.result;
			$(obj).attr("title",res);
			var arr=res.split("/");
			var total=arr[0];
			var curr= arr[1];
			if(parseInt(total) == parseInt(curr)){
				progressEx(obj,100);
				state(obj);
			}else if(parseInt(total)==100 && parseInt(curr)==0){
				state(obj);
			}else{
				var numNext = (parseInt(curr)/parseInt(total))*100;
				progressEx(obj,numNext);
			}
		}else{
			$('body').stopTime(typeVal);
			alert(data.msg);
		}
	});
}
/* 控制进度 */
function progressEx(obj,numNext){
	var numThis = $(obj).width();//当前进度百分比
    var End = parseInt(numNext)+"%";//更新进度百分比字符串
    $(obj).stop(true).animate({width:End},500);
}

function state(obj){
	var typeVal = $(obj).attr("type");
	$ .post("/batchImport/getState.json",{type:typeVal}, function(data) {
		if(data.success){
			var sts = data.result;
			if(sts != "RUNING"){
				$('body').stopTime(typeVal);
				reload(typeVal);
			}
		}else{
			alert(data.msg);
			$('body').stopTime(typeVal);
		}
	});
}

function optionVerity(batchNo){
	if(confirm("审核不可回退，确定审核？")) {
		$ .post("/batchImport/importVerity.htm",{batchNo:batchNo}, function(data) {
			if(data.success){
				alert("审核发起成功");
				submitSearch_MAIN();
			}else{
				alert(data.msg);
			}
		});
	}
}

function optionGallery(){
	$ .post("/batchImport/goodsGallery.htm", function(data) {
		if(data.success){
			alert("导入请求成功");
			window.location.href=window.location.href;
		}else{
			alert(data.msg);
		}
	});
}

function optionBCS(){
	$ .post("/batchImport/goodsBcs.htm", function(data) {
		if(data.success){
			alert("导入请求成功");
			window.location.href=window.location.href;
		}else{
			alert(data.msg);
		}
	});
}

function reload(type){
	var rf = window.location.href;
	var index = rf.indexOf("?");
	if(index > 0){
		rf = rf.subString(0,index);
		rf = rf +"?type="+type;
	}else{
		rf = rf +"?type="+type;
	}
	window.location.href = rf;
}
//

function rollOf(obj){
	var iden = $(obj).attr("iden");
	$('body').everyTime('5s',iden,function(){
		progressOf(obj);
    }); 
}

function progressOf(obj){
	var typeVal = $(obj).attr("type");
	var iden = $(obj).attr("iden");
	$ .post("/batchImport/getProgressOf.json",{type:typeVal,iden:iden}, function(data) {
		if(data.success){
			var res = data.result;
			$(obj).attr("title",res);
			var arr=res.split("/");
			var total=arr[0];
			var curr= arr[1];
			if(parseInt(total) == parseInt(curr)){
				progressEx(obj,100);
				stateOf(obj);
			}else if(parseInt(total)==100 && parseInt(curr)==0){
				stateOf(obj);
			}else{
				var numNext = (parseInt(curr)/parseInt(total))*100;
				progressEx(obj,numNext);
			}
		}else{
			$('body').stopTime(iden);
			alert(data.msg);
		}
	});
}

function rollOf2(obj){
	var iden = $(obj).attr("iden");
	$('body').everyTime('3s',iden,function(){
		progressOf2(obj);
    }); 
}
function progressOf(obj){
	var typeVal = $(obj).attr("type");
	var iden = $(obj).attr("iden");
	$ .post("/batchImport/progressBar.json",{type:typeVal,iden:iden}, function(data) {
		if(data.success){
			var res = data.result;
			$(obj).attr("title",res);
			var arr=res.split("/");
			var total=arr[0];
			var curr= arr[1];
			if(parseInt(total) == parseInt(curr)){
				progressEx(obj,100);
				stateOf(obj);
			}else if(parseInt(total)==100 && parseInt(curr)==0){
				stateOf(obj);
			}else{
				var numNext = (parseInt(curr)/parseInt(total))*100;
				progressEx(obj,numNext);
			}
		}else{
			$('body').stopTime(iden);
			alert(data.msg);
		}
	});
}

function stateOf(obj){
	var typeVal = $(obj).attr("type");
	var iden = $(obj).attr("iden");
	$ .post("/batchImport/getStateOf.json",{type:typeVal,iden:iden}, function(data) {
		if(data.success){
			var sts = data.result;
			if(sts != "RUNING"){
				$('body').stopTime(iden);
				reload(typeVal);
			}
		}else{
			alert(data.msg);
			$('body').stopTime(iden);
		}
	});
}


//控制文件上传
/**
* 主要信息导入
*/
function fileUploadOfMain(){
	var pdv = new Validate("uoloadFormOfMain");
	if (pdv.validate()) {
		if ($("#isVirtual").val() == -1) {
			alert("请选择商品类型！");
			return;
		}
		if ($("#isVirtual").val() == 1 && $("#generateMethod").val() == 0) {
			alert("请选择虚拟卡生成方式！");
			return;
		}
		$("input[flg='import']").attr("disabled","true").attr("class","none_color");
		
  		$("#uoloadFormOfMain").ajaxSubmit(function(data) {
		if(typeof data == "string"){
		  data = jQuery.parseJSON(data);
		}
  		if(data.success){
  			$("#result").html(data.result.msg);
  			$("#result").show();
  		}else{
  			$("#result").html("<span style='color:red'>"+data.msg+"</span>");
  			$("#result").show();
  		}
  	});
	}
}

function forceStopGoodsGallery(listId){
	if(confirm("中止不可回退，确认手工中止？")) {
		$ .post("/batchImport/forceStopGoodsGallery.json",{listId:listId}, function(data) {
			if(data.success){
				alert("中止成功");
				window.location.href=window.location.href;
			}else{
				alert(data.msg);
			}
		});
	}
}