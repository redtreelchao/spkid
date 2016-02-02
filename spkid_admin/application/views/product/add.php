<?php include(APPPATH.'views/common/header.php');?>
<script type="text/javascript" src="public/js/utils.js"></script>
<script type="text/javascript" src="public/js/validator.js"></script>
<script type="text/javascript" src="public/js/product.js"></script>
<script type="text/javascript">
	//<![CDATA[
	$(function(){

<?php echo $genre_field_map_js;?>
	});
	function check_form(){
	
		var validator = new Validator('mainForm');
			validator.required('product_name', '请填写商品名称');
			validator.selected('provider_id', '请选择供应商名称');
			validator.selected('brand_id', '请选择品牌');
			// validator.reg('product_sn',/^[0-9A-Za-z]{1,10}$/,'请填写由字母和数字组成最大10位的商品款号');
			//validator.required('product_sn', '请填写商品款号');
			// validator.required('provider_productcode', '请填写供应商货号');
			validator.required('unit_name', '请填写计量单位');
			validator.isNonNegative('shop_price', '请填写本店售价', true);
			validator.isPrice('market_price', '请填写市场价', true);
			return validator.passed();
	}

	$(function(){
		var genre_id = '<?php echo $genre_id;?>';		
		if (genre_id == 1) {
			$('.product_detail_edit').show();
			$('.course_detail_edit').hide();				
		};
			
		if (genre_id == 2) {
			$('.product_detail_edit').hide();
			$('.course_detail_edit').show();			
		};
		$('select[name="genre_id"]').change(function(){
			var val = $('select[name="genre_id"]').val();
			//产品详情
			if (val == 1) {
					
				if ($('.product_detail_edit [name="detail11"]').length) {
					$('.product_detail_edit [name="detail11"]').attr('name', 'detail1');
					$('.product_detail_edit [name="detail22"]').attr('name', 'detail2');
					$('.course_detail_edit [name="detail1"]').attr('name', 'detail11');
					$('.course_detail_edit [name="detail2"]').attr('name', 'detail22');
				};
				$('.product_detail_edit').show();
				$('.course_detail_edit').hide();
			};
			//课程详情
			if (val == 2) {
					
				if ($('.course_detail_edit [name="detail11"]').length) {
					$('.product_detail_edit [name="detail1"]').attr('name', 'detail11');
					$('.product_detail_edit [name="detail2"]').attr('name', 'detail22');
					$('.course_detail_edit [name="detail11"]').attr('name', 'detail1');
					$('.course_detail_edit [name="detail22"]').attr('name', 'detail2');
				};

				$('.product_detail_edit').hide();
				$('.course_detail_edit').show();
			};
		});
	});
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
				<td class="item_title" id="product_sn_label">商品款号:</td>
				<td class="item_input"><?php print form_input(array('name'=> 'product_sn','class'=> 'textbox','placeholder'=>'留空可自动生成'));?></td>
				<td class="item_title" id="is_stop_label">商品状态:</td>
				<td class="item_input">
					<label><?php print form_checkbox('is_stop', 1, FALSE)?>停止订货</label>
				</td>
			</tr>
			<tr>
				<td class="item_title" id="provider_id_label">供应商:</td>
				<td class="item_input">
					<?php print form_dropdown('provider_id', array('0'=>'请选择')+get_pair($all_provider,'provider_id','provider_name'),array($default_provider),'data-am-selected="{searchBox: 1,maxHeight: 300}"');?>
				</td>
				<td class="item_title" id="provider_productcode_label">供应商货号:</td>
				<td class="item_input"><?php print form_input(array('name'=> 'provider_productcode','class'=> 'textbox'));?></td>
			</tr>
			<tr>
				<td class="item_title" id="brand_id_label">品牌:</td>
				<td class="item_input">
					<?php print form_dropdown('brand_id', array('0'=>'请选择')+get_pair($all_brand,'brand_id','brand_name'),array($default_brand_id),'data-am-selected="{searchBox: 1,maxHeight: 300}"');?>
				</td>
				<td class="item_title" id="category_id_label">分类:</td>
				<td class="item_input">
					<?php print form_product_category('category_id', $all_category,'','data-am-selected');?>
				</td>
			</tr>
			<tr>
				<td class="item_title" id="register_id_label">注册证号:</td>
				<td class="item_input">
					<?php print form_dropdown('register_id', array('0'=>'请选择')+get_pair($all_register,'id','register_no'),array($default_register_no),'data-am-selected="{searchBox: 1,maxHeight: 300}"');?>
				</td>
				<td class="item_title" id="unit_name_label">计量单位:</td>
				<td class="item_input">
					<?php print form_input(array('name'=>'unit_name','class'=>'textbox require','size'=>3, 'value'=>'g'));?>
				</td>
			</tr>
			<tr>
				<td class="item_title" id="shop_id_label">店铺:</td>
				<td class="item_input">
					<?php print form_dropdown('shop_id', array('0'=>'请选择')+get_pair($all_provider_coop,'provider_id','provider_name'),array($self_shop),'data-am-selected="{searchBox: 1,maxHeight: 300}"');?>
				</td>
				<td class="item_title" id="genre_id_label">商品大类:</td>
				<td class="item_input">
					<?php echo $all_genre[$genre_id];?> <input type="hidden" name='genre_id' value="<?=$genre_id;?>" />
				</td>
			</tr>
			<tr>
				<td class="item_title" id="flag_id_label">产地:</td>
				<td class="item_input">
					<?php print form_dropdown('flag_id', get_pair($all_flag,'flag_id','flag_name'));?>
				</td>
				<td class="item_title" id="product_weight_label">商品重量:</td>
				<td class="item_input">
					<?php print form_input(array('name'=>'product_weight','class'=>'textbox','size'=>3));?> 克
				</td>
			</tr>
			<tr>
				<td class="item_title" id="size_image_label">规格详情图</td>
				<td class="item_input">
					<?php print form_upload('size_image');?>
				</td>
				<td class="item_title" id="price_show_label">是否询价:</td>
				<td class="item_input">
					<select name="price_show">
                        <option value="0">请选择</option>
                        <option value="1">询价/仅展示</option>
                    </select>
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
				<td class="item_title" id="limit_num_label">限购数量:</td>
                <td class="item_input">
					<?php print form_input(array('name'=>'limit_num','class'=>'textbox'))?>
				</td>
				<td class="item_title" id="content_source_label">内容来源:</td>
                <td class="item_input">
                   <?php print form_input(array('name'=>'content_source','class'=>'textbox'))?>
                </td>
		</tr>
		<tr>
				<td class="item_title">销售数量:</td>
                                <td class="item_input">
					<?php print form_input(array('name'=>'ps_num','class'=>'textbox'))?>
				</td>
				<td class="item_title">访问数量:</td>
                <td class="item_input">
                   <?php print form_input(array('name'=>'pv_num','class'=>'textbox'))?>
                </td>
		</tr>

			<tr>
				<td class="item_title" colspan="4" style="text-align: center">附加详细信息</td>
			</tr>
			<tr>
				<td class="item_title" id="subhead_label">副标题:</td>
				<td class="item_input">
					<?php print form_input(array('name'=>'subhead','class'=>'textbox'))?>
				</td>

				<td class="item_title" id="package_name_label">包装名称:</td>
				<td class="item_input"><?php print form_input(array('name'=> 'package_name','class'=> 'textbox'));?></td>
			</tr>
                        <tr>
				<td class="item_title" id="pack_method_label">包装方式:</td>
                                <td class="item_input" colspan="3">
					<?php print form_input(array('name'=>'pack_method','class'=>'textbox'))?>
				</td>
			</tr>
			 <tr>
				<td class="item_title" id="desc_material_label">可自定义:</td>
				<td class="item_input">
					<?php print form_input(array('name'=>'desc_material','class'=>'textbox'))?>desc_material
				</td>
				<td class="item_title" id="desc_waterproof_label">可自定义</td>
				<td class="item_input"><?php print form_input(array('name'=> 'desc_waterproof','class'=> 'textbox'));?>desc_waterproof</td>
			</tr>
			
			 <tr>
				<td class="item_title" id="desc_crowd_label">可自定义:</td>
				<td class="item_input">
					<?php print form_input(array('name'=>'desc_crowd','class'=>'textbox'))?>desc_crowd
				</td>
				<td class="item_title" id="desc_expected_shipping_date_label">可自定义:</td>
				<td class="item_input"><?php print form_input(array('name'=> 'desc_expected_shipping_date','class'=> 'textbox'));?>desc_expected_shipping_date</td>
			</tr>
			<tr>
				<td class="item_title" id="desc_composition_label">可自定义:</td>
				<td class="item_input">
					<?php print form_input(array('name'=>'desc_composition','class'=>'textbox'))?>desc_composition
				</td>
				<td class="item_title" id="desc_dimensions_label">可自定义:</td>
				<td class="item_input"><?php print form_input(array('name'=> 'desc_dimensions','class'=> 'textbox'));?>desc_dimensions</td>
			</tr>			
                        <tr>
				<td class="item_title" id="desc_use_explain_label">可自定义:</td>
				<td class="item_input">
					<?php print form_input(array('name'=>'desc_use_explain','class'=>'textbox'))?>desc_use_explain
				</td>
				<td class="item_title" id="desc_function_exlain_label">可自定义:</td>
				<td class="item_input"><?php print form_input(array('name'=> 'desc_function_explain','class'=> 'textbox'));?>desc_function_exlain</td>
			</tr>
			<tr>
				<td class="item_title" id="desc_notes_label">可自定义:</td>
				<td class="item_input">
					<?php print form_input(array('name'=>'desc_notes','class'=>'textbox'))?>desc_notes
				</td>
			</tr>
			<!-- <tr>
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
			</tr> -->
			<tr>
				<td class="item_title" id="product_desc_label">商品描述:</td>
				<td class="item_input" colspan=3>
					<?php print $this->ckeditor->editor('product_desc') ?>
				</td>
				
			</tr>
			<!-- 产品详情 -->	
			
			<tr class="product_detail_edit">
				<td class="item_title">产品图文详情:</td>
				<td class="item_input" colspan=3>
					<?php print $this->ckeditor->editor("detail1" . ($genre_id == 1 ? '' : '1')) ?>
				</td>
			</tr>

			<tr class="product_detail_edit">
				<td class="item_title">产品测试视频:</td>
				<td class="item_input" colspan=3>
					<?php print $this->ckeditor->editor("detail2" . ($genre_id == 1 ? '' : '2')) ?>
				</td>
			</tr>
			
			<!-- ends 产品详情 -->	
			<!-- 课程详情 -->
			
			<tr class="course_detail_edit">
				<td class="item_title">培训详情:</td>
				<td class="item_input" colspan=3>
					<?php print $this->ckeditor->editor("detail1" . ($genre_id == 2 ? '' : '1')) ?>
				</td>
			</tr>

			<tr class="course_detail_edit">
				<td class="item_title">老师介绍:</td>
				<td class="item_input" colspan=3>
					<?php print $this->ckeditor->editor("detail2" . ($genre_id == 2 ? '' : '2')) ?>
				</td>
			</tr>

			<tr class="course_detail_edit">
				<td class="item_title">交通路线:</td>
				<td class="item_input" colspan=3>
					<?php print $this->ckeditor->editor("detail3") ?>
				</td>
			</tr>

			<tr class="course_detail_edit">
				<td class="item_title">学员评价:</td>
				<td class="item_input" colspan=3>
					<?php print $this->ckeditor->editor("detail4") ?>
				</td>
			</tr>
			
			
			<!-- ends 课程详情 -->	
			<tr>
				<td class="item_title" id="product_desc_detail_label">商品细节展示:</td>
				<td class="item_input" colspan=3>
					<?php print $this->ckeditor->editor('product_desc_detail') ?>
				</td>
			</tr>
			<tr>
				<td class="item_title"></td>
				<td class="item_input" colspan=3>
					<?php print form_submit(array('name'=>'mysubmit','class'=>'am-btn am-btn-primary','value'=>'提交'));?>
				</td>
			</tr>
			<tr>
				<td colspan=4 class="bottomTd"></td>
			</tr>
		</table>
	<?php print form_close();?>
</div>
<?php include(APPPATH.'views/common/footer.php');?>
