<?php include(APPPATH.'views/common/header.php');?>
<script type="text/javascript" src="public/js/utils.js"></script>
<script type="text/javascript" src="public/js/validator.js"></script>
<script type="text/javascript" src="public/js/cluetip.js"></script>
<link rel="stylesheet" href="public/style/cluetip.css" type="text/css" media="all" />
<script type="text/javascript">
	//<![CDATA[
	$(function(){
		$('span.img_tip').cluetip({splitTitle: '|',showTitle:false});
	});
	function check_form(){
		var validator = new Validator('mainForm');
			
			return validator.passed();
	}
	//]]>
</script>
<div class="main">
	<div class="main_title"><span class="l">规格详情图管理 >> 编辑 </span><a href="size/image_index" class="return r">返回列表</a></div>
	<div class="blank5"></div>
	<?php print form_open_multipart('size/proc_image_edit',array('name'=>'mainForm','onsubmit'=>'return check_form()'), array('size_image_id'=>$row->size_image_id));?>
		<table class="form" cellpadding=0 cellspacing=0>
			<tr>
				<td colspan=2 class="topTd"></td>
			</tr>
			<tr>
				<td class="item_title">品牌:</td>
				<td class="item_input">
					<?php print form_dropdown('brand_id', $all_brand, $row->brand_id,$perm_edit?'':'disabled')?>
				</td>
			</tr>
			<tr>
				<td class="item_title">分类:</td>
				<td class="item_input">
					<select name="category_id" <?php print $perm_edit?'':'disabled';?>>
						<?php 
						foreach ($all_category as $key => $value) {
							echo "<option value='{$value->category_id}' ".($value->category_id == $row->category_id?'selected':'').">{$value->level_space} {$value->category_name}</option>";
						}
						;?>
					</select>
				</td>
			</tr>
			<tr>
				<td class="item_title">性别:</td>
				<td class="item_input">
					<label><?php print form_radio('sex',1,$row->sex==1,$perm_edit?'':'disabled'); ?>男</label>
					<label><?php print form_radio('sex',2,$row->sex==2,$perm_edit?'':'disabled'); ?>女</label>
					
				</td>
			</tr>
			<tr>
				<td class="item_title">详情图:</td>
				<td class="item_input">
					<?php print form_upload('image_url','',$perm_edit?'':'disabled');?>
					<?php print img_tip(PUBLIC_DATA_IMAGES, $row->image_url);?>
				</td>
			</tr>
			<?php if ($perm_edit): ?>
				<tr>
					<td class="item_title"></td>
					<td class="item_input">
						<?php print form_submit(array('name'=>'mysubmit','class'=>'am-btn am-btn-primary','value'=>'提交'));?>
					</td>
				</tr>
			<?php endif ?>
			
			<tr>
				<td colspan=2 class="bottomTd"></td>
			</tr>
		</table>
	<?php print form_close();?>
</div>
<?php include(APPPATH.'views/common/footer.php');?>