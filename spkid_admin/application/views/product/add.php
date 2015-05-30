<?php include(APPPATH.'views/common/header.php');?>
<script type="text/javascript" src="public/js/utils.js"></script>
<script type="text/javascript" src="public/js/validator.js"></script>
<script type="text/javascript" src="public/js/product.js"></script>
<script type="text/javascript" src="public/js/jui/core.min.js"></script>
<script type="text/javascript" src="public/js/jui/datepicker.min.js"></script>
<link rel="stylesheet" href="public/style/jui/theme.css" type="text/css" media="all" />
<link rel="stylesheet" href="public/style/jui/datepicker.css" type="text/css" media="all" />
<script type="text/javascript">
	//<![CDATA[
	$(function(){
		$(':input[name=desc_expected_shipping_date]').datepicker({dateFormat: 'yy-mm-dd',changeMonth: true,changeYear: true, nextText:'', prevText:''});
	});
	function check_form(){
	
		var validator = new Validator('mainForm');
			validator.required('product_name', '请填写商品名称');
			validator.reg('product_sn',/^[0-9A-Za-z]{1,10}$/,'请填写由字母和数字组成最大10位的商品款号');
			validator.required('product_sn', '请填写商品款号');
			validator.required('provider_productcode', '请填写供应商货号');
			validator.required('unit_name', '请填写计量单位');
			validator.isNonNegative('shop_price', '请填写本店售价', true);
			validator.isPrice('market_price', '请填写市场价', true);
			return validator.passed();
	}
	//]]>
</script>
<div class="main">
	<div class="main_title"><span class="l">商品管理 >> 新增</span> <a href="product/index" class="return r">返回列表</a></div>
	<div class="blank5"></div>
	<?php print form_open_multipart('product/proc_add',array('name'=>'mainForm','onsubmit'=>'return check_form()'));?>
		<table class="form" cellpadding=0 cellspacing=0>
			<tr>
				<td colspan=4 class="topTd"></td>
			</tr>
			<tr>
				<td class="item_title" width="100px;">商品名称</td>
				<td class="item_input"><?php print form_input(array('name'=> 'product_name','class'=> 'textbox require'));?></td>
				<td class="item_title">商品角标:</td>
				<td class="item_input">
					<label><?php print form_checkbox('is_best', 1, FALSE)?>清仓</label>
					<label><?php print form_checkbox('is_new', 1, FALSE)?>新品</label>
					<label><?php print form_checkbox('is_hot', 1, FALSE)?>热销</label>
					<label><?php print form_checkbox('is_offcode', 1, FALSE)?>促销</label>
					<label><?php print form_checkbox('is_gifts', 1, FALSE)?>赠品</label>
				</td>
			</tr>
			<tr>
				<td class="item_title">商品款号:</td>
				<td class="item_input"><?php print form_input(array('name'=> 'product_sn','class'=> 'textbox require'));?></td>
				<td class="item_title">商品状态:</td>
				<td class="item_input">
					<label><?php print form_checkbox('is_stop', 1, FALSE)?>停止订货</label>
				</td>
			</tr>
			<tr>
				<td class="item_title">供应商:</td>
				<td class="item_input"><?php print form_dropdown('provider_id', get_pair($all_provider,'provider_id','provider_code'));?></td>
				<td class="item_title">供应商货号:</td>
				<td class="item_input"><?php print form_input(array('name'=> 'provider_productcode','class'=> 'textbox require'));?></td>
			</tr>
			<tr>
				<td class="item_title">品牌:</td>
				<td class="item_input">
					<?php print form_dropdown('brand_id', get_pair($all_brand,'brand_id','brand_name'));?>
				</td>
				<td class="item_title">分类:</td>
				<td class="item_input">
					<?php print form_product_category('category_id', $all_category);?>
				</td>
			</tr>
			<tr>
				<td class="item_title">风格:</td>
				<td class="item_input">
					<?php print form_dropdown('style_id', get_pair($all_style,'style_id','style_name'));?>
				</td>
				<td class="item_title">季节:</td>
				<td class="item_input">
					<?php print form_dropdown('season_id', get_pair($all_season,'season_id','season_name'));?>
				</td>
			</tr>
			<tr>
				<td class="item_title">上市时间:</td>
				<td class="item_input">
					<?php print form_dropdown('product_year',array_combine(range(2000, date('Y')), range(2000, date('Y')))); ?>年
					<?php print form_dropdown('product_month',array_combine(range(1,12), range(1,12))); ?>月
				</td>
				<td class="item_title">计量单位:</td>
				<td class="item_input">
					<?php print form_input(array('name'=>'unit_name','class'=>'textbox require','size'=>3));?>
				</td>
			</tr>
			<tr>
				<td class="item_title">模特:</td>
				<td class="item_input">
					<?php print form_dropdown('model_id', array(''=>'无')+get_pair($all_model,'model_id','model_name'));?>
				</td>
				<td class="item_title">性别:</td>
				<td class="item_input">
					<label><?php print form_radio('product_sex', 1, true)?>男</label>
					<label><?php print form_radio('product_sex', 2)?>女</label>
					<label><?php print form_radio('product_sex', 3)?>男女</label>
				</td>
			</tr>
			<tr>
				<td class="item_title">国旗:</td>
				<td class="item_input">
					<?php print form_dropdown('flag_id', get_pair($all_flag,'flag_id','flag_name'));?>
				</td>
				<td class="item_title">商品重量:</td>
				<td class="item_input">
					<?php print form_input(array('name'=>'product_weight','class'=>'textbox','size'=>3));?> 千克
				</td>
			</tr>
			<tr>
				<td class="item_title">尺寸详情图</td>
				<td class="item_input">
					<?php print form_upload('size_image');?>
				</td>
				<td class="item_title">岁段:</td>
				<td class="item_input">
					<?php print form_dropdown('min_month',$all_age); ?>
					至
					<?php print form_dropdown('max_month',$all_age); ?>
				</td>
			</tr>
			<tr>
				<td class="item_title">本站价:</td>
				<td class="item_input">
					<?php print form_input(array('name'=>'shop_price','class'=>'textbox require'));?>
				</td>
				<td class="item_title">市场价:</td>
				<td class="item_input">
					<?php print form_input(array('name'=>'market_price','class'=>'textbox require'));?>
				</td>
			</tr>
			<tr>
				<td class="item_title">关键字:</td>
				<td class="item_input">
					<?php print form_input(array('name'=>'keywords','class'=>'textbox'))?>
				</td>
				<td class="item_title">排序号:</td>
				<td class="item_input"><?php print form_input(array('name'=> 'sort_order','class'=> 'textbox', 'size'=>3));?></td>
			</tr>
                        <tr>
				<td class="item_title">限购数量:</td>
                                <td class="item_input" colspan="3">
					<?php print form_input(array('name'=>'limit_num','class'=>'textbox'))?>
				</td>
<!--				<td class="item_title">限购天数:</td>
                                <td class="item_input">
                                    <select name="limit_day">
                                        <option value="0">请选择</option>
                                        <option value="1">1天</option>
                                    </select>
                                </td>-->
			</tr>
			<tr>
				<td class="item_title" colspan="4" style="text-align: center">商品附加详细信息</td>
			</tr>
			<tr>
				<td class="item_title">成分:</td>
				<td class="item_input">
					<?php print form_input(array('name'=>'desc_composition','class'=>'textbox'))?>
				</td>
				<td class="item_title">尺寸规格:</td>
				<td class="item_input"><?php print form_input(array('name'=> 'desc_dimensions','class'=> 'textbox'));?></td>
			</tr>
			<tr>
				<td class="item_title">材质:</td>
				<td class="item_input">
					<?php print form_input(array('name'=>'desc_material','class'=>'textbox'))?>
				</td>
				<td class="item_title">防水性:</td>
				<td class="item_input"><?php print form_input(array('name'=> 'desc_waterproof','class'=> 'textbox'));?></td>
			</tr>
			<tr>
				<td class="item_title">适合人群:</td>
				<td class="item_input">
					<?php print form_input(array('name'=>'desc_crowd','class'=>'textbox'))?>
				</td>
				<td class="item_title">预计发货日期:</td>
				<td class="item_input"><?php print form_input(array('name'=> 'desc_expected_shipping_date','class'=> 'textbox'));?></td>
			</tr>
                        <tr>
				<td class="item_title">使用说明:</td>
				<td class="item_input">
					<?php print form_input(array('name'=>'desc_use_explain','class'=>'textbox'))?>
				</td>
				<td class="item_title">功能说明:</td>
				<td class="item_input"><?php print form_input(array('name'=> 'desc_function_explain','class'=> 'textbox'));?></td>
			</tr>
			<tr>
				<td class="item_title">温馨提示:</td>
				<td class="item_input" colspan=3>
					<?php print form_input(array('name'=>'desc_notes','class'=>'textbox'))?>
				</td>
			</tr>
			<tr>
				<td class="item_title">洗标:</td>
				<td class="item_input" colspan=3>
					<?php
						foreach($all_carelabel as $carelabel){
							print "<label>";
							print form_checkbox('goods_carelabel[]', $carelabel->carelabel_id);
							print $carelabel->carelabel_name;
							print "</label> ";
						}
					?>
				</td>
				
			</tr>
			<tr>
				<td class="item_title">商品描述:</td>
				<td class="item_input" colspan=3>
					<?php print $this->ckeditor->editor('product_desc') ?>
				</td>
				
			</tr>
			<tr>
				<td class="item_title">商品细节展示:</td>
				<td class="item_input" colspan=3>
					<?php print $this->ckeditor->editor('product_desc_detail') ?>
				</td>
			</tr>
			<tr>
				<td class="item_title"></td>
				<td class="item_input" colspan=3>
					<?php print form_submit(array('name'=>'mysubmit','class'=>'button','value'=>'提交'));?>
				</td>
			</tr>
			<tr>
				<td colspan=4 class="bottomTd"></td>
			</tr>
		</table>
	<?php print form_close();?>
</div>
<?php include(APPPATH.'views/common/footer.php');?>