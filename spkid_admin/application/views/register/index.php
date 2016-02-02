<?php if($full_page): ?>
<?php include(APPPATH.'views/common/header.php'); ?>
	<script type="text/javascript" src="public/js/utils.js"></script>
	<script type="text/javascript" src="public/js/listtable.js"></script>
	<script type="text/javascript">
		//<![CDATA[
		listTable.filter.page_count = '<?php echo $filter['page_count']; ?>';
		listTable.filter.page = '<?php echo $filter['page']; ?>';
		listTable.url = 'register_code/index';
		function search(){
			listTable.filter['register_no'] = $.trim($('input[type=text][name=register_no]').val());
			listTable.loadList();
		}
		//]]>
	</script>
	<style type="text/css">

	</style>
	<div class="main">
		<div class="main_title"><span class="l">注册号</span><span class="r"><a href="register_code/add" class="add">新增</a></span></div>		
		<div class="blank5"></div>
		<div class="search_row">
			<form name="search" action="javascript:search(); ">
			注册号：<input type="text" class="ts" name="register_no" id="register_no" value="" style="width:230px;" />
			<input type="submit" class="am-btn am-btn-primary" value="搜索" />
			</form>
		</div>
		<div class="blank5"></div>
		<div id="listDiv">
<?php endif; ?>
			<table id="dataTable" class="dataTable" cellpadding=0 cellspacing=0>
				<tr>
					<td colspan="7" class="topTd"> </td>
				</tr>
				<tr class="row">
					<th width="40px"><a href="javascript:listTable.sort('r.id', 'ASC'); ">编号<?php echo ($filter['register_id'] == 'r.id') ? $filter['sort_flag'] : '' ?></th>
					<th width="250px">注册号</th>
					<th>产品名称</th>
					<th>生产单位</th>
					<th>产品标准</th>
					<th width="300px;">产品性能结构及组成</th>
					<th>产品适用范围</th>
					<th>有效期</th>
					<th width="40px;">添加人</th>
					<th>添加时间</th>
					<th width="120px;">操作</th>
				</tr>
				<?php foreach($list as $row): ?>
				<tr class="row">
					<td><?php print $row->id; ?></td>
					<td><?php print $row->register_no; ?><br/><?php print $row->field_value1; ?> 、<?php print $row->field_value2; ?></td>
					<td><?php print $row->product_name; ?></td>
					<td><?php print $row->unit; ?></td>
					<td><?php print $row->standard; ?></td>
					<td><?php print $row->property; ?></td>
					<td><?php print $row->scope; ?></td>
					<td><?php print $row->valid_time; ?></td>
					<td><?php print $row->admin_name; ?></td>
					<td><?php print date('Y-m-d',$row->add_admin_time); ?></td>
					<td>
						<?php if ($perm_fetch): ?>
							<a class="priv" href="register_code/grab/<?php print $row->id; ?>" title="抓取"></a>
						<?php endif ?>
						<a class="edit" href="register_code/edit/<?php print $row->id; ?>" title="编辑"></a>
						<?php if ($perm_delete): ?>
							<a class="del" href="javascript:void(0)" rel="register_code/delete/<?php print $row->id; ?>" title="删除" onclick="do_delete(this)"></a>
						<?php endif ?>
					</td>
				</tr>
				<?php endforeach; ?>
				<tr>
					<td colspan="7" class="bottomTd"> </td>
				</tr>
			</table>
			<div class="blank5"></div>
			<div class="page">
				<?php include(APPPATH.'views/common/page.php') ?>
			</div>
<?php if($full_page): ?>
		</div>
	</div>
<?php include_once(APPPATH.'views/common/footer.php'); ?>
<?php endif; ?>