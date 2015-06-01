
/**
 * 验证form表单,如果验证通过则提交此表单
 * 
 * @param fromId
 *            表单ID
 */
function doSave(fromId) {
	editor1.sync();
	if (validateGoods(fromId)) {
		var action = $("#"+fromId).attr("action")+"?isSubmit=0";
		$("#"+fromId).attr("action",action);
		$("#"+fromId).submit();
	}
}

/**
 * 验证form表单,如果验证通过则提交此表单
 * 
 * @param fromId
 *            表单ID
 */
function doSubmit(fromId) {
	editor1.sync();
	if (confirm("确定提交审核吗？") && validateGoods(fromId)) {
		var action = $("#"+fromId).attr("action")+"?isSubmit=1";
		$("#"+fromId).attr("action",action);
		$("#"+fromId).submit();
	}
}

/**
 * 验证商品信息
 */
function validateGoods(fromId) {
	var pdv = new Validate(fromId);
	var flg = pdv.validate();
	if (flg) {
		var msg = "验证有误:\n";
		var flag=true;
		if(!checkVal("catId", "0")){
			msg += "► 请输入[商品分类]\n";
			flag=false;
		}
		if(!checkVal("brandId", "0")){
			msg += "► 请输入[商品品牌]\n";
			flag=false;
		}
		if(!checkVal("goodsSex", "0")){
			msg += "► 请选择[性别]\n";
			flag=false;
		}
			// 验证通过
		if(flag){
			return true;
		}
		alert(msg);
		return false;
	} else {
		alert("请输入必输项!");
		return false;
	}
}

/**
 * 验证值
 * 
 * @param id
 * @param defVal
 */
function checkVal(id, defVal) {
	var val = $("#" + id).val();
	if (val != null && val != "") {
		if (val == defVal) {
			return false;
		} else {
			return true;
		}
	} else {
		return false;
	}
}
/**
 * 验证颜色
 */
function checkColor(msg){
	var color=$("tr[id^='tm_']");
	if(color!=null && color.length>0){
		return true;
	}else{
		return false;
	}
}