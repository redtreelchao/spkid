


/**
 * 导出Excel文件
 */

function exportExcel(){
	$('#searchForm').attr('action', '/reportform/list/export.htm');
	$('#searchForm').submit();
	$('#searchForm').attr('action', '/reportform/list/query.htm');
}
