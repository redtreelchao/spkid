function dateFormat(data){
	var date = new Date(data);
	var year = date.getYear();
	var month = date.getMonth()+1;
	var day = date.getDate();
	var hour = date.getHours();
	var minute = date.getMinutes(); 
	var second = date.getSeconds();
		 
	  //different browser
	  var userAgent = navigator.userAgent.toLowerCase(); 
	  //判断是否为google的浏览器 
	  var chrome = /chrome/.test(userAgent); 
	  if($.browser.mozilla || chrome){
		  year += 1900;
	  }
	  
	return	year + "-" + month + "-" + day + " " + hour + ":" +	minute + ":" + second;
}

/**
 * 验证form表单,如果验证通过则提交此表单
 * @param fromId 表单ID
 */
function doSave(fromId) {
	var pdv = new Validate(fromId);
	if (pdv.validate()) {
		$("#"+fromId).submit();
	}
}
/**
 * 同步到,同步source的value到target的value中
 * @param source 原始ID
 * @param target 目标ID
 */
function syncTo(source,target){
	var value =$("#"+source).val();
	if(isEmpty(value)) {
		return;
	}
	$("#"+target).val(value);
}