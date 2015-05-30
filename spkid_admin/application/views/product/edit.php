<?php include(APPPATH.'views/common/header.php');?>
<script type="text/javascript" src="public/js/utils.js"></script>
<script type="text/javascript" src="public/js/listtable.js"></script>
<script type="text/javascript" src="public/js/validator.js"></script>
<script type="text/javascript" src="public/js/product.js"></script>
<script type="text/javascript" src="public/js/jui/core.min.js"></script>
<script type="text/javascript" src="public/js/jui/datepicker.min.js"></script>
<link rel="stylesheet" href="public/style/jui/theme.css" type="text/css" media="all" />
<link rel="stylesheet" href="public/style/jui/datepicker.css" type="text/css" media="all" />
<script type="text/javascript" src="public/js/cluetip.js"></script>
<script type="text/javascript" src="public/js/jui/bgiframe.min.js"></script>
<script type="text/javascript" src="public/js/jui/hoverIntent.js"></script>
<script type="text/javascript" src="public/js/jquery.form.js"></script>
<link rel="stylesheet" href="public/style/cluetip.css" type="text/css" media="all" />
<script type="text/javascript">
	//<![CDATA[
	$(function(){
		$(':input[name=desc_expected_shipping_date]').datepicker({dateFormat: 'yy-mm-dd',changeMonth: true,changeYear: true, nextText:'', prevText:''});
		var btns = $(".conf_btn");
		var tabs = $(".conf_tab");
		$.each(btns, function(i, btn){
			$(btn).bind("click",function(){
				$(tabs).hide();
				if(i == 2){
				    show_product_type(<?=$row->product_id?>);
				}
				$(tabs[i]).show();
				$(btns).removeClass("currentbtn");
				$(this).addClass("currentbtn");
			});
			$(btn).bind("focus",function(){$(this).blur();});
		});
		i=0;
        var i = Utils.request(location.href,'tab');
        if(!i) i = 0;
		$(btns[i]).click();
		$('span.img_tip').cluetip({splitTitle: '|',showTitle:false});
		<?php foreach($all_gallery_sub as $color_id=>$gallery_sub) print "ajax_form({$color_id});";?>

	});
	function check_form(){
		var validator = new Validator('mainForm');
			validator.required('product_name', '请填写商品名称');
			validator.reg('product_sn',/^[0-9A-Za-z]{1,10}$/,'请填写由字母和数字组成最大10位的商品款号');
			validator.required('provider_productcode', '请填写供应商货号');
			validator.required('unit_name', '请填写计量单位');
			validator.isNonNegative('shop_price', '请填写本店售价', true);
			validator.isPrice('market_price', '请填写市场价', true);
			return validator.passed();
	}
	listTable.filter.product_id = <?php print $row->product_id; ?>;
	listTable.url = 'product_api/link_search';
	function search(){
		var container = $('form[name=search]');
		listTable.filter['product_sn'] = $.trim($('input[type=text][name=product_sn]', container).val());
		listTable.filter['product_name'] = $.trim($('input[type=text][name=product_name]', container).val());
		listTable.filter['provider_productcode'] = $.trim($('input[type=text][name=provider_productcode]', container).val());
		listTable.filter['style_id'] = $('select[name=style_id]', container).val();
		listTable.filter['season_id'] = $('select[name=season_id]', container).val();
		listTable.filter['product_sex'] = $('select[name=product_sex]', container).val();
		listTable.loadList();
	}
	function show_product_type(product_id){
		$.ajax({
		    url: 'product_type_link/pre_set_type',
		    data: {product_id:product_id, rnd : new Date().getTime()},
		    dataType: 'json',
		    type: 'POST',
		    success: function(result){
			if(result.error == 0)
			{
			    var content = result.content;
			    var div = $("div[class=conf_tab][rel=3]");
			    div.html(content);
			    div.append("<input type='button' style='text-align: center' class='button' value='确认' onclick='set_type()'/>");
			    div.find("#type_link_title").hide();
			}
		    }
		 });
	}
	
	function set_type(){
	    var product_id = $("#product_id").val();
	    var chk_value =[];    
	    $('input[name="type_ids"]:checked').each(function(){
	     chk_value.push($(this).val());    
	    });
	   $.ajax({
		    url: 'product_type_link/set_type',
		    data: {product_id : product_id ,type_ids : chk_value, rnd : new Date().getTime()},
		    dataType: 'json',
		    type: 'POST',
		    success: function(){
			window.location.reload();
		    }
	   });
	}
	//]]>
</script>
<div class="main">
	<div class="main_title">商品管理 >> 编辑 <a href="product/index" class="return r">返回列表</a></div>
	<div class="blank5"></div>
	<div class="button_row">
        <ul>
         <li class="conf_btn" rel="1"><span>基础信息</span></li>
         <li class="conf_btn" rel="2"><span>颜色尺码</span></li>
	 <li class="conf_btn" rel="3"><span>前台分类</span></li>
         <li class="conf_btn" rel="4"><span>关联商品</span></li>
        </ul>
        <div class="clear"></div>
	</div>

	<div class="blank5"></div>

	<?php print form_open_multipart('product/proc_edit',array('name'=>'mainForm','onsubmit'=>'return check_form()'), array('product_id'=>$row->product_id));?>
		<table class="form conf_tab" cellpadding=0 cellspacing=0 rel="1">
			<tr>
				<td colspan=4 class="topTd"></td>
			</tr>
			<tr>
				<td class="item_title" width="100px">商品名称</td>
				<td class="item_input"><?php print form_input(array('name'=> 'product_name','class'=> 'textbox require', 'value'=>$row->product_name));?></td>
				<td class="item_title">商品角标:</td>
				<td class="item_input">
					<label><?php print form_checkbox('is_best', 1, (bool)$row->is_best)?>清仓</label>
					<label><?php print form_checkbox('is_new', 1, (bool)$row->is_new)?>新品</label>
					<label><?php print form_checkbox('is_hot', 1, (bool)$row->is_hot)?>热销</label>
					<label><?php print form_checkbox('is_offcode', 1, (bool)$row->is_offcode)?>促销</label>
					<label><?php print form_checkbox('is_gifts', 1, (bool)$row->is_gifts)?>赠品</label>
				</td>
			</tr>
			<tr>
				<td class="item_title">商品款号:</td>
				<td class="item_input"><?php print form_input('product_sn',$row->product_sn,"class='textbox require' ".($row->is_audit?'disabled':''));?></td>
				<td class="item_title">商品状态:</td>
				<td class="item_input">
					<label><?php print $row->is_audit?'已审核':'未审核 <a href="product_api/audit/'.$row->product_id.'" onclick="return confirm(\'确定执行该操作?\');" class="button" />点击审核</a> '?></label>
					<label><?php print form_checkbox('is_stop', 1, (bool)$row->is_stop)?>停止订货</label>
				</td>
			</tr>
			<tr>
				<td class="item_title">供应商:</td>
				<td class="item_input"><?php print form_dropdown('provider_id', get_pair($all_provider,'provider_id','provider_code'),$row->provider_id,$row->is_audit?'disabled':'');?></td>
				<td class="item_title">供应商货号:</td>
				<td class="item_input"><?php print form_input('provider_productcode',$row->provider_productcode,"class='textbox require' ".($row->is_audit?'disabled':''));?></td>
			</tr>
			<tr>
				<td class="item_title">品牌:</td>
				<td class="item_input">
					<?php print form_dropdown('brand_id', get_pair($all_brand,'brand_id','brand_name'),$row->brand_id,$row->is_audit?'disabled':'');?>
				</td>
				<td class="item_title">分类:</td>
				<td class="item_input">
					<?php print form_product_category('category_id', $all_category, $row->category_id, $row->is_audit?'disabled':'');?>
				</td>
			</tr>
			<tr>
				<td class="item_title">风格:</td>
				<td class="item_input">
					<?php print form_dropdown('style_id', get_pair($all_style,'style_id','style_name'),$row->style_id);?>
				</td>
				<td class="item_title">季节:</td>
				<td class="item_input">
					<?php print form_dropdown('season_id', get_pair($all_season,'season_id','season_name'),$row->season_id);?>
				</td>
			</tr>
			<tr>
				<td class="item_title">上市时间:</td>
				<td class="item_input">
					<?php print form_dropdown('product_year',array_combine(range(2000, date('Y')), range(2000, date('Y'))), $row->product_year); ?>年
					<?php print form_dropdown('product_month',array_combine(range(1,12), range(1,12)), $row->product_month); ?>月
				</td>
				<td class="item_title">计量单位:</td>
				<td class="item_input">
                                        <?php print form_input('unit_name',$row->unit_name,"class='textbox require' size='3' ");?>
				</td>
			</tr>
			<tr>
				<td class="item_title">模特:</td>
				<td class="item_input">
					<?php print form_dropdown('model_id', array(''=>'无')+get_pair($all_model,'model_id','model_name'),$row->model_id);?>
				</td>
				<td class="item_title">性别:</td>
				<td class="item_input">
					<label><?php print form_radio('product_sex', 1, $row->product_sex==1)?>男</label>
					<label><?php print form_radio('product_sex', 2, $row->product_sex==2)?>女</label>
					<label><?php print form_radio('product_sex', 3, $row->product_sex==3)?>男女</label>
				</td>
			</tr>
			<tr>
				<td class="item_title">国旗:</td>
				<td class="item_input">
					<?php print form_dropdown('flag_id', get_pair($all_flag,'flag_id','flag_name'), $row->flag_id);?>
				</td>
				<td class="item_title">商品重量:</td>
				<td class="item_input">
					<?php print form_input(array('name'=>'product_weight','class'=>'textbox','size'=>3, 'value'=>$row->product_weight));?> 千克
				</td>
			</tr>
			<tr>
				<td class="item_title">尺寸详情图</td>
				<td class="item_input">
					<?php print form_upload('size_image');?>
					<?php print img_tip(PUBLIC_DATA_IMAGES,$row->size_image);?>
					<label><?php if ($row->size_image) print form_checkbox('delete_size_image',1) . '删除原图'?></label>
				</td>
				<td class="item_title">岁段:</td>
				<td class="item_input">
					<?php print form_dropdown('min_month', $all_age, $row->min_month); ?>
					至
					<?php print form_dropdown('max_month', $all_age, $row->max_month); ?>
				</td>
			</tr>
			<tr>
				<td class="item_title">本站价:</td>
				<td class="item_input">
					<?php print form_input(array('name'=>'shop_price','class'=>'textbox require','value'=>$row->shop_price));?>
				</td>
				<td class="item_title">市场价:</td>
				<td class="item_input">
					<?php print form_input(array('name'=>'market_price','class'=>'textbox require','value'=>$row->market_price));?>
				</td>
			</tr>
			<?php if($row->is_promote): ?>
			<tr>
				<td class="item_title">促销价:</td>
				<td class="item_input" colspan=3>
					<?php print form_checkbox('XX', 1, (bool)$row->is_promote, 'disabled')?>
					<?php print $row->promote_price;?>
					促销时间：
					<?php print $row->promote_start_date;?>
					-
					<?php print $row->promote_end_date;?>
				</td>
			</tr>
			<?php endif;?>
			<tr>
				<td class="item_title">关键字:</td>
				<td class="item_input">
					<?php print form_input(array('name'=>'keywords','class'=>'textbox','size'=>80,'value'=>$row->keywords))?>
				</td>
				<td class="item_title">排序号:</td>
				<td class="item_input"><?php print form_input(array('name'=> 'sort_order','class'=> 'textbox', 'size'=>3,'value'=>$row->sort_order));?></td>
			</tr>
                        <tr>
				<td class="item_title">限购数量:</td>
                                <td class="item_input" colspan="3">
					<?php print form_input(array('name'=>'limit_num','class'=>'textbox', 'value'=>$row->limit_num))?>
				</td>
<!--				<td class="item_title">限购天数:</td>
                                <td class="item_input">
                                    <select name="limit_day">
                                        <option value="0">请选择</option>
                                        <option value="1" <?php if($row->limit_day==1):?> selected="selected"<?php endif;?>>1天</option>
                                    </select>
                                </td>-->
			</tr>
			<tr>
				<td class="item_title" colspan="4" style="text-align: center">商品附加详细信息</td>
			</tr>
			<tr>
				<td class="item_title">成分:</td>
				<td class="item_input">
					<?php print form_input(array('name'=>'desc_composition','class'=>'textbox','value'=>$row->desc_composition))?>
				</td>
				<td class="item_title">尺寸规格:</td>
				<td class="item_input"><?php print form_input(array('name'=> 'desc_dimensions','class'=> 'textbox','value'=>$row->desc_dimensions));?></td>
			</tr>
			<tr>
				<td class="item_title">材质:</td>
				<td class="item_input">
					<?php print form_input(array('name'=>'desc_material','class'=>'textbox','value'=>$row->desc_material))?>
				</td>
				<td class="item_title">防水性:</td>
				<td class="item_input"><?php print form_input(array('name'=> 'desc_waterproof','class'=> 'textbox','value'=>$row->desc_waterproof));?></td>
			</tr>
			<tr>
				<td class="item_title">适合人群:</td>
				<td class="item_input">
					<?php print form_input(array('name'=>'desc_crowd','class'=>'textbox','value'=>$row->desc_crowd))?>
				</td>
				<td class="item_title">预计发货日期:</td>
				<td class="item_input"><?php print form_input(array('name'=> 'desc_expected_shipping_date','class'=> 'textbox','value'=>$row->desc_expected_shipping_date));?></td>
			</tr>
                        <tr>
				<td class="item_title">使用说明:</td>
				<td class="item_input">
					<?php print form_input(array('name'=>'desc_use_explain','class'=>'textbox','value'=>$row->desc_use_explain))?>
				</td>
				<td class="item_title">功能说明:</td>
				<td class="item_input"><?php print form_input(array('name'=> 'desc_function_explain','class'=> 'textbox','value'=>$row->desc_function_explain));?></td>
			</tr>
			<tr>
				<td class="item_title">温馨提示:</td>
				<td class="item_input" colspan=3>
					<?php print form_input(array('name'=>'desc_notes','class'=>'textbox','value'=>$row->desc_notes))?>
				</td>
			</tr>
			<tr>
				<td class="item_title">洗标:</td>
				<td class="item_input" colspan=3>
					<?php
						foreach($all_carelabel as $carelabel){
							print "<label>";
							print form_checkbox('goods_carelabel[]', $carelabel->carelabel_id, in_array($carelabel->carelabel_id,$row->goods_carelabel));
							print $carelabel->carelabel_name;
							print "</label>";
						}
					?>
				</td>
				
			</tr>
			<tr>
				<td class="item_title">商品描述:</td>
				<td class="item_input" colspan=3>
					<?php print $this->ckeditor->editor('product_desc',$row->product_desc) ?>
				</td>
				
			</tr>
			<tr>
				<td class="item_title">商品细节展示:</td>
				<td class="item_input" colspan=3>
					<?php print $this->ckeditor->editor('product_desc_detail',$row->product_desc_detail) ?>
				</td>
			</tr>
			<tr>
				<td class="item_title"></td>
				<td class="item_input" colspan=3>
					<?php print form_submit(array('name'=>'mysubmit','class'=>'button','value'=>'提交'));?>
				</td>
			</tr>
		</table>
		<?php print form_close();?>
		<table class="form conf_tab" cellpadding=0 cellspacing=0 rel="2">
			<tr>
				<td class="topTd"></td>
			</tr>
			<tr>
				<td class="search_row" style="border-width:0 0 1px 0;">
					颜色组：<?php print form_dropdown('cs_color_group_id',get_pair($all_color_group,'group_id','group_name',array('0'=>'所有颜色组')),'','onchange="cs_group_id_change();"')?>
					颜色：<?php print form_dropdown('cs_color_id',get_pair($all_color,'color_id','color_name'))?>
					<input type="button" value="添加" class="button" onclick="add_color();">
				</td>
			</tr>
			<tr>
				<td class='item_input' id="cs_list">
					<?php foreach($all_gallery_sub as $color_id => $gallery_sub):?>						
						<table class="access_list" id="cs_color_<?php print $color_id?>" cellspacing="0" cellpadding="0">
							<tbody>
								<tr>
									<td colspan="2" class="access_left" style="text-align:center">
										<strong><?php print $gallery_sub['color_info']['color_name']?></strong><a href="javascript:remove_color(<?php print $color_id?>);">[删]</a>
										&nbsp;排序：<?php print edit_link('product_api/sort_sub', $row->product_id, $color_id, $gallery_sub['color_info']['sort_order']);?>
									</td>
								</tr>
								<tr>
									<td class="access_left" style="text-align:left">
										尺码：
										<?php print form_dropdown('cs_size_src', get_pair($all_size,'size_id','size_name'));?>
										<input type="button" value="添加"  onclick="add_size(<?php print $color_id?>);">
										<br>供应商条码：<input type="text" id="provider_barcode" />
									</td>
									<td class="cs_table_2">
										<?php foreach($gallery_sub['size_list'] as $sub):?>
										<div id="cs_size_<?php print $sub->size_id;?>" style="border: 1px solid #C8DFA7;display: inline-block;margin: 3px;padding: 2px;text-align: center;width: 220px;">
											尺码：<?php print $sub->size_name;?> &nbsp;&nbsp; [<a href="javascript:remove_size(<?php print $color_id?>,<?php print $sub->size_id?>);">删</a>]<br>
											供应商条码：<?php print $sub->provider_barcode;?>
										</div>
										<?php endforeach;?>
									</td>
								</tr>
								<tr>
									<td class="access_left" style="text-align:left">
										<form id="cs_upload_<?php print $color_id?>" action="product_api/add_gallery" method="POST" enctype="multipart/form-data" encoding="multipart/form-data">
										<label><input type="radio" name="cs_upload_image_type" value="default">默认</label><br/>
										<label><input type="radio" name="cs_upload_image_type" value="part">局部</label><br/>
										<label><input type="radio" name="cs_upload_image_type" value="tonal">色片</label><br>
										<input type="file" name="cs_upload_image">
										<input type="hidden" name="cs_upload_color_id" value="<?php print $color_id?>"><br>
										<input type="hidden" name="cs_upload_product_id" value="<?php print $row->product_id?>"><br>
										<input type="submit" name="mysubmit" value="上传"></form>
									</td>
									<td class="cs_table_4">
										<?php foreach($gallery_sub['gallery_list'] as $gallery):?>
										<div class="cs_image_<?php print $gallery->image_type?> cs_image_<?php print $gallery->image_id?>" style="display:inline-block;float:left;margin:5px;width:100px;">
											<span><?php if($gallery->image_type=='default') print '默认图'; elseif($gallery->image_type=='tonal') print '色片图'; else print '局部图';?></span>&nbsp;
											<a href="javascript:remove_gallery(<?php print $gallery->image_id?>)" style="clear:both;">[删]</a><br>
											<img src="public/data/images/<?php print $gallery->img_url?>.85x85.jpg" ><br>
											<?php print edit_link('product_api/edit_gallery', 'img_desc', $gallery->image_id, $gallery->img_desc?$gallery->img_desc:'无描述');?>
											<br>
											排序：<?php print edit_link('product_api/edit_gallery', 'sort_order', $gallery->image_id, $gallery->sort_order);?><br>
										</div>
										<?php endforeach;?>
									</td>
								</tr>
							</tbody>
						</table>
						<div class="span5"></div>
					<?php endforeach;?>
				</td>
			</tr>
			<tr>
				<td colspan=4 class="bottomTd"></td>
			</tr>
		</table> 
		<div class="conf_tab" rel="3">
		</div>
		<div class="conf_tab" rel="4">
			<div id="link_list">
			<?php include('link_list.php');?>
			</div>
			<div class="blank5"></div>
			<div class="search_row">
				<form name="search" action="javascript:search(); ">
				商品款号：<input type="text" class="ts" name="product_sn" value="" style="width:100px;" />
				商品名称：<input type="text" class="ts" name="product_name" value="" style="width:100px;" />
				供应商货号：<input type="text" class="ts" name="provider_productcode" value="" style="width:100px;" />
				<?php print form_dropdown('style_id', get_pair($all_style,'style_id','style_name',array(''=>'风格')));?>
				<?php print form_dropdown('season_id', get_pair($all_season,'season_id','season_name',array(''=>'季节')));?>
				<select name="product_sex"><option value="">性别</option><option value="1">男款</option><option value="2">女款</option><option value="3">男女款</option></select>
				<input type="submit" value="搜索" />
				</form>
			</div>
			<div class="blank5"></div>
			<div id="listDiv">
			</div>
			<div class="blank5"></div>
		</div>
		
</div>
<?php include(APPPATH.'views/common/footer.php');?>