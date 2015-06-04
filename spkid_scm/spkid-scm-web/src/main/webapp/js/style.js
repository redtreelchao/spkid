$(function() {
	//添加类页面button样式修改
	$('.title_row input[type=button]:last').css({'margin-left':'40%'});
	//筛选页面确定按钮去除样式
	$('.search_row input[type=button]:first').removeAttr('style');
});