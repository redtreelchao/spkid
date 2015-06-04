/**
 * 绑定表格编辑事件
 */
function bindTblExpandEv(tblId){
	$('#'+tblId+' tbody tr td[click2expand=true]').click(function(){
		var $parent = $(this).parent();
		var $read = $($parent).find('.div-read');
		var $edit = $($parent).find('.div-edit');
		if($edit.css('display') == 'none'){
			$parent.addClass('click');
			$read.hide();
			$edit.show();
			//将其他显示的detail tr隐藏
			$parent.siblings('.click').find('[click2expand=true]:eq(1)').trigger('click');
		}else{
			$parent.removeClass('click');
			$edit.each(function(i, dom) {
				var newVal = $(dom).children().val();
				$(dom).siblings().filter(".pd-chill-tip").attr("title", newVal);
				$(dom).siblings().text(newVal);
			});
			$read.show();
			$edit.hide();
		}
	});
	$('#'+tblId+' tbody tr td div.div-edit :input').click(function(e){
		//阻止事件向上冒泡
		e.stopPropagation(); 
	});
}

/**
 * 可编辑事件
 */
function change2Edit(dom){
	
	if(!isEmpty($(dom).find("input"))) {
		return;
	}
	var width = $(dom).parent().width()-8;
	var oldVal = $.trim($(dom).html());
	var inputHtml = "<input type='text' class='inputBorder autoChangeValue' style='width:"+width+"px;' value='"+oldVal+"' />";
	$(dom).html(inputHtml);
	$(dom).find("input").focus();
	$(".autoChangeValue").blur(function(){
		var newVal = this.value;
		if(oldVal == newVal) {
			$(dom).html(oldVal);
			return;
		}
		var url = $(dom).attr("upUrl");
		$.post(url, {upKey:$(dom).attr("upKey"), upVal:newVal}, function(result) {
			if(result=="success") {
				//alert("修改成功！");
				$(dom).html(newVal);
			} else {
				//alert(result);
				$(dom).html(oldVal);
			}
		});
	});
}

isEmpty = function (str) {
	return (typeof (str) === "undefined" || str === null || (str.length === 0));
};