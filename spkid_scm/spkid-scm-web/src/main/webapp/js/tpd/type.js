function changeParentType(id,val) {
	var data, subSele;
	if(id == "goods_type_1") {
		subSele = $("#goods_type_2");
		subSele.empty();
		subSele.append('<option value="0">二级分类</option>');
		data = {
			key: "levelOne", 
			value: $("#"+id).val()
		};
	}
	var url = "/goods/type/list/getChildTypes.htm";
	$.get(url, data, function(result) {
		$.each(result, function(i,n){
			var option;
			if(val!=null&& val==i){
				option='<option value="'+i+'" SELECTED="SELECTED">'+n+'</option>';
			}else{
				option= '<option value="'+i+'">'+n+'</option>';
			}
			subSele.append(option);
		});
	});
}

function getList(url){
	var val=$("#searchForm").attr("action");
	var flg=$("#searchForm").attr("flg");
	if(flg!=null && flg=="true"){
		$("#searchForm").attr("action",url);
	}else{
		$("#searchForm").attr("oldAct",val).attr("action",url);
		$("#searchForm").attr("flg",true);
	}
	clean();
	submitSearch();
}

function searchFormAction(){
	var val=$("#searchForm").attr("oldAct");
	if(val!=null){
		$("#searchForm").attr("action",val);
	}
	submitSearch();
}

function clean(){
	$("#searchForm").find("select").val(0);
	$("#searchForm").find("#keyWord").val("");
}

isEmpty = function (str) {
	return (typeof (str) === "undefined" || str === null || (str.length === 0));
};