<?php include(APPPATH.'views/common/header.php');?>
<script type="text/javascript" src="public/js/utils.js"></script>
<script type="text/javascript" src="public/js/validator.js"></script>
<script type="text/javascript" src="public/js/brand.js"></script>
<link type="text/css" href="public/style/jui/datepicker.css" rel="stylesheet" />
<link type="text/css" href="public/style/jui/theme.css" rel="stylesheet" />
<script type="text/javascript" src="public/js/jui/core.min.js"></script>
<script type="text/javascript" src="public/js/jui/datepicker.min.js"></script>
<script type="text/javascript" src="public/js/campaign.js"></script>
<script type="text/javascript">
	//<![CDATA[
	$(function(){
	$('input[type=text][name=start_time]').datepicker({dateFormat: 'yy-mm-dd',changeMonth: true,changeYear: true, nextText:'', prevText:'', yearRange:'-100:+10'});
	$('input[type=text][name=end_time]').datepicker({dateFormat: 'yy-mm-dd',changeMonth: true,changeYear: true, nextText:'', prevText:'', yearRange:'-100:+10'});

	});
	$(function(){
		show_flag();
	});
	function check_form(){
		var validator = new Validator('mainForm');
		validator.required('campaign_name', '请填写活动名称');
		validator.required('limit_price', '请填写最小金额');
		validator.selected('tag_id', '请选择赠送商品');
		validator.required('start_time', '请填写开始时间');
		validator.required('end_time', '请填写结束时间');
		return validator.passed();
	}
		
	function sel(te,c, cb){
		$('select[name='+cb+']')[0].options.length = 1;
		$.ajax({
		   type: "POST",
		   url: "campaign/sel_"+c,
		   data: "cb="+cb+"&class="+c+"&val="+te,
		   dataType: "JSON",
		   success: function(msg){
			 	if(msg.type == 1){
					if( msg.cb == 'tag_id' )
					for(i in msg.list){
						$('select[name='+msg.cb+']')[0].options.add(new Option(msg.list[i].product_name+' '+msg.list[i].product_sn+' '+msg.list[i].provider_name , msg.list[i].product_id));
					}
					if( msg.cb == 'brand_id' )
					for(i in msg.list){
						$('select[name='+msg.cb+']')[0].options.add(new Option(msg.list[i].brand_name, msg.list[i].brand_id));
					}
				}
			 	if(msg.type == 3){
					alert('没搜索到相关记录');
					return false;
				}
			}
		});
	}

	//]]>
</script>
<div class="main">
	<div class="main_title"><span class="l">活动管理 >> 编辑</span><a href="campaign/index" class="return r">返回列表</a></div>
	<div class="blank5"></div>
	<?php print form_open_multipart('campaign/proc_edit/'.$cam_arr->campaign_id,array('name'=>'mainForm','onsubmit'=>'return check_form()'));?>
		<table class="form" cellpadding=0 cellspacing=0>
			<tr>
				<td colspan=2 class="topTd"></td>
			</tr>
			<tr>
				<td class="item_title" width="100">活动名称:</td>
				<td class="item_input"><?php print form_input(array('name'=> 'campaign_name','class'=> 'textbox require' , 'value' => $cam_arr->campaign_name));?></td>
			</tr>
			<tr>
				<td class="item_title">活动类型:</td>
				<td class="item_input"><?php echo $campaign_types[$cam_arr->campaign_type];?></td>
			</tr>
			<tr>
				<td class="item_title">品牌名称:</td>
				<td class="item_input"><?php echo $brand_arr->brand_name?></td>
			</tr>
			<tr>
				<td class="item_title">最小金额:</td>
				<td class="item_input"><?php print form_input(array('name'=> 'limit_price','class'=> 'textbox require', 'value' => $cam_arr->limit_price));?></td>
			</tr>
			<?php if( isset($pro_arr) ):?>
			<tr>
				<td class="item_title">赠送商品:</td>
				<td class="item_input">                
				<input name="pro" type="text" id="pro" value="" size="20" onblur="return sel(this.value,'product','tag_id');"  />
				<select name="tag_id" id="tag_id">
				    <option selected='selected' value="<?php echo $cam_arr->promote_value?>"><?php echo $pro_arr->product_name?></option>
		        </select>可输入商品名称、编码搜索</td>
			</tr>
			<?php else:?>
			<tr>
				<td class="item_title">减金额:</td>
				<td class="item_input"><?php print form_input(array('name'=> 'tag_id','class'=> 'textbox require', 'value' => $cam_arr->promote_value));?>金额为正值</td>
			</tr>
			<?php endif;?>
			<tr>
				<td class="item_title">开始时间:</td>
				<td class="item_input">
					<input type="text" name="start_time" id="start_time" value="<?php echo $cam_arr->start_date?>" />
				</td>
			</tr>
			<tr>
			  <td class="item_title">结束时间:</td>
			  <td class="item_input"><input type="text" name="end_time" id="end_time" value="<?php echo $cam_arr->end_date?>" /></td>
		  </tr>
			<tr>
			  <td class="item_title">单品列表:<br/>《单品满减》时有用</td>
			  <td class="item_input"><?php print form_textarea(array('id'=>'product_sns','name'=>'product_sns','rows'=>5,'cols'=>80),isset($cam_arr->limits['product_sns'])?$cam_arr->limits['product_sns']:'');?>
					<?php //print form_button(array('name'=>'check_id','class'=>'button','content'=>'验证商品ID','onclick'=>"javascript:check_product_valid('product_id')"));?>
					<?php print form_button(array('name'=>'check_code','class'=>'button','content'=>'验证商品编号','onclick'=>"javascript:check_product_valid('product_sn')"));?>验证结果：<span id="check_result"></span>
					</td>
			</tr>
			<tr>
				<td class="item_title">状态:</td>
				<td class="item_input">
                    <label><input type="radio"  name="is_use" <?php echo $cam_arr->is_use == 0 ? 'checked="checked"' : '';?> value="0" >禁用</label>
                    <label><input type="radio" name="is_use" <?php echo $cam_arr->is_use == 1 ? 'checked="checked"' : '';?> value="1">启用</label>
                    <label><input type="radio" name="is_use" <?php echo $cam_arr->is_use == 2 ? 'checked="checked"' : '';?> value="2">停止</label>
			  </td>
			</tr>
			<tr>
				<td class="item_title"></td>
				<td class="item_input">
					<?php print form_submit(array('name'=>'mysubmit','class'=>'button','value'=>'提交'));?>
				</td>
			</tr>
			<tr>
				<td colspan=2 class="bottomTd"></td>
			</tr>
		</table>
	<?php print form_close();?>
</div>
<?php include(APPPATH.'views/common/footer.php');?>
