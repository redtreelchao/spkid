<?php include(APPPATH.'views/common/header.php'); ?>
	<script type="text/javascript" src="public/js/listtable.js"></script>
	<script type="text/javascript">
		//<![CDATA[

		//]]>
	</script>
	<div class="main">
		<div class="main_title"><span class="l">商品导入结果</span> <span class="r"><a href="import" class="return r">返回</a></span></div>
		<div class="blank5"></div>
		<div id="listDiv">
			<table id="dataTable" class="dataTable" cellpadding=0 cellspacing=0>
				<tr>
					<td colspan="7" class="topTd"> </td>
				</tr>
				<tr class="row">
					<th width="100px">商品款号</th>
					<th>供应商货号</th>
					<th>二级分类编号</th>
					<th>品牌名称</th>
					<th>市场价</th>
					<th>售价</th>
					<th>季节名称</th>
					<th>国家名称</th>
					<th>供应商编码</th>
					<th>年</th>
					<th>月</th>
					<th>最小年龄（月）</th>
					<th>最大年龄（月）</th>
					<th>状态</th>
				</tr>
				<?php foreach($error_records as $row): ?>
				<tr class="row">
					<td><?php print $row['product_sn']; ?></td>
					<td><?php print $row['provider_productcode']; ?></td>
					<td><?php print $row['category_id']; ?></td>
					<td><?php print $row['brand_name']; ?></td>
					<td><?php print $row['market_price']; ?></td>
					<td><?php print $row['shop_price']; ?></td>
					<td><?php print $row['season_name']; ?></td>
					<td><?php print $row['flag_name']; ?></td>
					<td><?php print $row['provider_code']; ?></td>
					<td><?php print $row['product_year']; ?></td>
					<td><?php print $row['product_month']; ?></td>
					<td><?php print $row['min_month']; ?></td>
					<td><?php print $row['max_month']; ?></td>
					<td><font color="red">失败</font> <?php print $row['msg']; ?></td>
				</tr>
				<?php endforeach; ?>
				<?php foreach($success_records as $row): ?>
				<tr class="row">
					<td><?php print $row['product_sn']; ?></td>
					<td><?php print $row['provider_productcode']; ?></td>
					<td><?php print $row['category_id']; ?></td>
					<td><?php print $row['brand_name']; ?></td>
					<td><?php print $row['market_price']; ?></td>
					<td><?php print $row['shop_price']; ?></td>
					<td><?php print $row['season_name']; ?></td>
					<td><?php print $row['flag_name']; ?></td>
					<td><?php print $row['provider_code']; ?></td>
					<td><?php print $row['product_year']; ?></td>
					<td><?php print $row['product_month']; ?></td>
					<td><?php print $row['min_month']; ?></td>
					<td><?php print $row['max_month']; ?></td>
					<td><font color="green">成功</font></td>
				</tr>
				<?php endforeach; ?>
				<tr>
					<td colspan="7" class="bottomTd"> </td>
				</tr>
			</table>
			<div class="blank5"></div>
		</div>
	</div>
<?php include_once(APPPATH.'views/common/footer.php'); ?>