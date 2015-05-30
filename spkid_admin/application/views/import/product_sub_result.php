<?php include(APPPATH.'views/common/header.php'); ?>
	<script type="text/javascript" src="public/js/listtable.js"></script>
	<script type="text/javascript">
		//<![CDATA[

		//]]>
	</script>
	<div class="main">
		<div class="main_title"><span class="l">商品次要信息导入结果</span> <span class="r"><a href="import" class="return r">返回</a></span></div>
		<div class="blank5"></div>
		<div id="listDiv">
			<table id="dataTable" class="dataTable" cellpadding=0 cellspacing=0>
				<tr>
					<td colspan="7" class="topTd"> </td>
				</tr>
				<tr class="row">
					<th width="80px">商品款号</th>
					<th width="80px">供应商货号(款号+批号)</th>
					<th width="50px">商品名称</th>
                                        <th width="30px">风格编码</th>
                                        <th width="20px">性别</th>
                                        <th width="30px">计量单位</th>
					<th width="80px">三级分类编码</th>
					<th width="80px">新商品洗标编码(|线分割)</th>
					<th width="30px">模特编码</th>
					<th width="80px">成分</th>
					<th width="30px">尺寸规格</th>
					<th width="30px">材质</th>
					<th width="40px">防水性</th>
					<th width="40px">适合人群</th>
					<th width="80px">温馨提示</th>
					<th width="30px">预计发货日期</th>
                                        <th width="80px">使用说明</th>
                                        <th width="80px">功能说明</th>
					<th width="50px">状态</th>
				</tr>
				<?php foreach($error_records as $row): ?>
				<tr class="row">
					<td><?php print $row['product_sn']; ?></td>
                                        <td><?php print $row['provider_productcode']; ?></td>
					<td><?php print $row['product_name']; ?></td>
                                        <td><?php print $row['style_id']; ?></td>
                                        <td><?php print $row['product_sex_name']; ?></td>
                                        <td><?php print $row['unit_name']; ?></td>
                                        <td><?php print $row['type_code']; ?></td>
                                        <td><?php print $row['goods_carelabel']; ?></td>
                                        <td><?php print $row['model_id']; ?></td>
                                        <td><?php print $row['desc_composition']; ?></td>
                                        <td><?php print $row['desc_dimensions']; ?></td>
                                        <td><?php print $row['desc_material']; ?></td>
                                        <td><?php print $row['desc_waterproof']; ?></td>
                                        <td><?php print $row['desc_crowd']; ?></td>
                                        <td><?php print $row['desc_notes']; ?></td>
					<td><?php print $row['desc_expected_shipping_date']; ?></td>
                                        <td><?php print $row['desc_use_explain']; ?></td>
                                        <td><?php print $row['desc_function_explain']; ?></td>
					<td><font color="red">失败</font> <?php print $row['msg']; ?></td>
				</tr>
				<?php endforeach; ?>
				<?php foreach($success_records as $row): ?>
				<tr class="row">
					<td><?php print $row['product_sn']; ?></td>
                                        <td><?php print $row['provider_productcode']; ?></td>
					<td><?php print $row['product_name']; ?></td>
                                        <td><?php print $row['style_id']; ?></td>
                                        <td><?php print $row['product_sex_name']; ?></td>
                                        <td><?php print $row['unit_name']; ?></td>
                                        <td><?php print $row['type_code']; ?></td>
                                        <td><?php print $row['goods_carelabel']; ?></td>
                                        <td><?php print $row['model_id']; ?></td>
                                        <td><?php print $row['desc_composition']; ?></td>
                                        <td><?php print $row['desc_dimensions']; ?></td>
                                        <td><?php print $row['desc_material']; ?></td>
                                        <td><?php print $row['desc_waterproof']; ?></td>
                                        <td><?php print $row['desc_crowd']; ?></td>
                                        <td><?php print $row['desc_notes']; ?></td>
					<td><?php print $row['desc_expected_shipping_date']; ?></td>
                                        <td><?php print $row['desc_use_explain']; ?></td>
                                        <td><?php print $row['desc_function_explain']; ?></td>
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