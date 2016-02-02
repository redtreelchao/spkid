<?php include(APPPATH.'views/common/header.php'); ?>
	<script type="text/javascript" src="public/js/listtable.js"></script>
	<script type="text/javascript" src="public/js/utils.js"></script>
	<script type="text/javascript" src="public/js/validator.js"></script>
    <script type="text/javascript">
		$(function(){
			$('input[type=text][name=start_date]').datepicker({dateFormat: 'yy-mm-dd',changeMonth: true,changeYear: true, nextText:'', prevText:''});
			$('input[type=text][name=end_date]').datepicker({dateFormat: 'yy-mm-dd',changeMonth: true,changeYear: true, nextText:'', prevText:''});
		});

		function check_add(){
			var validator = new Validator('mainForm');
			validator.required('tuan_name', '请填写团购名称');
			validator.selected('tuan_price', '请填写团购价格');
			validator.required('start_date', '请填写开始时间');
			validator.required('start_time', '请填写开始时间');
			validator.required('end_date', '请填写结束时间');
			validator.required('end_time', '请填写结束时间');
			return validator.passed();
		}
	</script>
	<div class="main">
		<div class="main_title"><span class="l">团购管理 &gt;&gt; 编辑团购</span><span class="r">[ <a href="/mami_tuan">返回列表 </a>]</span></div>
		<div class="produce">
		<div class="pc base">
		<div class="blank5"></div>
		<!-- 团购信息填写开始 -->
		<div id="listDiv">
			<?php print form_open_multipart('mami_tuan/post_edit/'.$tuan->tuan_id,array('name'=>'mainForm','onsubmit'=>'return check_add()'));?>
				<table class="form" cellpadding=0 cellspacing=0>
					<tr>
						<td colspan=4 class="topTd"></td>
					</tr>
					<tr>
						<td class="item_title">商品名称：</td>
						<td class="item_input">
							<input name="product_name" type="text" class="textbox" disabled="disabled" value="<?php print $product->product_name; ?>"/>
						</td>
					</tr>
					<tr>
						<td class="item_title">团购名称：</td>
						<td class="item_input">
							<textarea rows="3" cols="50" name="tuan_name" <?php echo $is_edit ? '' : 'disabled="disabled"';?>><?php print $tuan->tuan_name; ?></textarea>
							最多只能输入48个汉字。<span style="color:red">（温馨提示：1个汉字=2个字符，1个字母=1个字符）</span>
						</td>
					</tr>
					<tr>
					  <td class="item_title">团购价格：</td>
					  <td class="item_input">
						<input name="tuan_price" type="text" <?php echo $is_edit ? '' : 'disabled="disabled"';?> class="textbox require" value="<?php print $tuan->tuan_price; ?>"/>
						输入价格。
					  </td>
					</tr>
					<tr>
						<td class="item_title">开始时间:</td>
						<td class="item_input">
							<input name="start_date" type="text" <?php echo $is_edit ? '' : 'disabled="disabled"';?> class="textbox require" value="<?php echo $start_arr[0]; ?>"/>
							<input name="start_time" type="text" <?php echo $is_edit ? '' : 'disabled="disabled"';?> value="<?php echo $start_arr[1]; ?>" class="textbox require" />
						</td>
					</tr>
					<tr>
						<td class="item_title">结束时间:</td>
						<td class="item_input">
							<input name="end_date" type="text" <?php echo $is_edit ? '' : 'disabled="disabled"';?> class="textbox require" value="<?php echo $end_arr[0]; ?>"/>
							<input name="end_time" type="text" <?php echo $is_edit ? '' : 'disabled="disabled"';?> value="<?php echo $end_arr[1]; ?>" class="textbox require" />
						</td>
					</tr>
					<tr>
						<td class="item_title">团购折扣：</td>
						<td class="item_input">
							<?php print $tuan->product_discount; ?> 折
						</td>
					</tr>
					<tr>
						<td class="item_title">购买人数：</td>
						<td class="item_input">
							<input name="buy_num" type="text" <?php echo $is_edit ? '' : 'disabled="disabled"';?> class="textbox" value="<?php print $tuan->buy_num; ?>"/>
							输入数字。
						</td>
					</tr>
					<tr>
						<td class="item_title">排序:</td>
						<td class="item_input">
							<input name="tuan_sort" type="text" <?php echo $is_edit ? '' : 'disabled="disabled"';?> class="textbox" size="3" value="<?php print $tuan->tuan_sort; ?>" />
							同一天开始的排序值高的在前
						</td>
					</tr>
					<tr>
						<td class="item_title">商品图：</td>
						<td class="item_input">
							<input name="img_315_207" type="file" <?php echo $is_edit ? '' : 'disabled="disabled"';?> class="textbox require" />请上传指定规格（315*207）图片
							<?php if(!empty($tuan->img_315_207)):?>
							<br><img src="<?php echo  base_url()?>public/data/images/<?php echo $tuan->img_315_207;?>" width="80px"/>
							<?php endif;?>
						</td>
					</tr>
					<tr>
						<td class="item_title">商品详情图:</td>
						<td class="item_input">
							<input name="img_500_450" type="file" <?php echo $is_edit ? '' : 'disabled="disabled"';?> class="textbox require" />请上传指定规格（500*450）图片
							<?php if(!empty($tuan->img_500_450)):?>
							<br><img src="<?php echo  base_url()?>public/data/images/<?php echo $tuan->img_500_450;?>" width="80px"/>
							<?php endif;?>
						</td>
					</tr>
					<tr>
						<td class="item_title">最近浏览图:</td>
						<td class="item_input">
							<input name="img_168_110" type="file" <?php echo $is_edit ? '' : 'disabled="disabled"';?> class="textbox require" />请上传指定规格（168*110）图片
							<?php if(!empty($tuan->img_168_110)):?>
							<br><img src="<?php echo  base_url()?>public/data/images/<?php echo $tuan->img_168_110;?>" width="80px"/>
							<?php endif;?>
						</td>
					</tr>
					<tr>
						<td class="item_title">购买需知：</td>
						<?php if($is_edit){?>
						<td class="item_input"><?php print $this->ckeditor->editor('tuan_desc',$tuan->tuan_desc,array('width'=>750,'height'=>180));?></td>
						<?php }else{?>
						<td class="item_input"><?php print stripslashes($tuan->tuan_desc);?></td>
						<?php }?>
					</tr>
					<tr>
						<td class="item_title">头部描述：</td>
						<?php if($is_edit){?>
						<td class="item_input"><?php print $this->ckeditor->editor('userdefine1',$tuan->userdefine1,array('width'=>750,'height'=>180));?></td>
						<?php }else{?>
						<td class="item_input"><?php print stripslashes($tuan->userdefine1);?></td>
						<?php }?>
					</tr>
					<tr>
						<td class="item_title">中部描述:</td>
						<?php if($is_edit){?>
						<td class="item_input"><?php print $this->ckeditor->editor('userdefine2',$tuan->userdefine2,array('width'=>750,'height'=>180));?></td>
						<?php }else{?>
						<td class="item_input"><?php print stripslashes($tuan->userdefine2);?></td>
						<?php }?>
					</tr>
					<!--
					<tr>
						<td class="item_title">底部描述：</td>
						<?php //if($is_edit){?>
						<td class="item_input"><?php //print $this->ckeditor->editor('userdefine3',$tuan->userdefine3,array('width'=>750,'height'=>180));?></td>
						<?php //}else{?>
						<td class="item_input"><?php //print stripslashes($tuan->userdefine3);?></td>
						<?php //}?>
					</tr>
					-->
					<tr>
						<td class="item_title">商品详情右上角描述:</td>
						<?php if($is_edit){?>
						<td class="item_input"><?php print $this->ckeditor->editor('userdefine4',$tuan->userdefine4,array('width'=>750,'height'=>180));?></td>
						<?php }else{?>
						<td class="item_input"><?php print stripslashes($tuan->userdefine4);?></td>
						<?php }?>
					</tr>
					
					<tr>
						<?php if($is_edit){?>
						<td class="item_title"></td>
						<td class="item_input" colspan=3>
						<?php print form_submit(array('name'=>'mysubmit','class'=>'am-btn am-btn-primary','value'=>'提交'));?>
						</td>
						<?php }else{?>
						<td>&nbsp;&nbsp;</td>
						<?php }?>
					</tr>
					<tr>
						<td colspan=4 class="bottomTd"></td>
					</tr>
				</table>
			<?php print form_close();?>
		</div>
		</div></div>
	</div>
<?php include_once(APPPATH.'views/common/footer.php'); ?>
