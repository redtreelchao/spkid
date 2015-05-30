<?php if($full_page): ?>
<?php include(APPPATH.'views/common/header.php'); ?>
	<script type="text/javascript" src="public/js/utils.js"></script>
	<script type="text/javascript" src="public/js/listtable.js"></script>
	<style type="text/css">
	a.provider_brand{border: 1px solid #A7B7DF;
	    display: block;
	    margin: 3px;
	    padding: 0 5px;
	    text-align: center;
	    float: left;}
	a.provider_brand:hover{border-color:red;text-decoration:none;cursor:default}
	</style>
	<script type="text/javascript">
		//<![CDATA[
		listTable.filter.page_count = '<?php echo $filter['page_count']; ?>';
		listTable.filter.page = '<?php echo $filter['page']; ?>';
		listTable.url = 'provider/index';
		function search(){
			listTable.filter['provider_code'] = $.trim($('input[type=text][name=provider_code]').val());
			listTable.filter['provider_name'] = $.trim($('input[type=text][name=provider_name]').val());
			listTable.loadList();
		}
		//]]>
	</script>
	<div class="main">
		<div class="main_title"><span class="l">供应商列表</span><span class="r"><a href="provider/add" class="add">新增</a></span></div>
		
		<div class="blank5"></div>
		<div class="search_row">
			<form name="search" action="javascript:search(); ">
			供应商编码：<input type="text" class="ts" name="provider_code" value="" style="width:100px;" />
			供应商名称：<input type="text" class="ts" name="provider_name" value="" style="width:100px;" />
			<input type="submit" class="button" value="搜索" />
			</form>
		</div>
		<div class="blank5"></div>
		<div id="listDiv">
<?php endif; ?>
			<table id="dataTable" class="dataTable" cellpadding=0 cellspacing=0>
				<tr>
					<td colspan="9" class="topTd"> </td>
				</tr>
				<tr class="row">
					<th width="50px">
						<a href="javascript:listTable.sort('p.provider_id', 'ASC'); ">编号<?php echo ($filter['sort_by'] == 'p.provider_id') ? $filter['sort_flag'] : '' ?></a>
					</th>
                                        <th>供应商代码</th>
                                        <th>供应商名称</th>
					<th>合作方式</th>
					<th>公司名称</th>
					<th width="300px;">品牌</th>
					<th>启用</th>
					<th width="160px;">操作</th>
				</tr>
				<?php foreach($list as $row): ?>
				<tr class="row">
					<td><?php print $row->provider_id; ?></td>
					<td><?php print $row->provider_code?></td>
                                        <td><?php print $row->provider_name?></td>
                                        <td><?php print $row->cooperation_name?></td>
					<td><?php print $row->official_name?></td>
					<td>
					    <?php if(!empty($row->provider_brand_list))
						    foreach ($row->provider_brand_list as $brand){
						    $brand_initial ="";
						    if(!empty($brand->brand_initial))
							$brand_initial ='['.$brand->brand_initial.']';
						    echo '<a href="javascript:void(0);" id="pb_'.$brand->brand_id.'" class="provider_brand">'.$brand_initial.$brand->brand_name.'</a>';
						    }?>
					</td>
					<td>
						<?php print toggle_link('provider/toggle','is_use',$row->provider_id, $row->is_use);?>
					</td>
					<td>
						<a class="edit" href="provider/edit/<?php print $row->provider_id; ?>" title="编辑"></a>
						<?php if ($perm_delete): ?>
							<a class="del" href="javascript:void(0)" rel="provider/delete/<?php print $row->provider_id; ?>" title="删除" onclick="do_delete(this)"></a>
						<?php endif ?>
						<?php if ($perm_provider_brand_setup): ?><a class="priv" href="provider_brand/index/<?php print $row->provider_id; ?>" title="分配品牌"></a><?php endif ?>
						<a href="provider/shipping/<?php print $row->provider_id; ?>" title="运费">运费</a>
                                                <a href="provider/scm_edit/<?php print $row->provider_id; ?>" title="直发设置">直发设置</a>
					</td>
				</tr>
				<?php endforeach; ?>
				<tr>
					<td colspan="9" class="bottomTd"> </td>
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