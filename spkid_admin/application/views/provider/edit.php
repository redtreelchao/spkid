<?php include(APPPATH.'views/common/header.php');?>
<script type="text/javascript" src="public/js/utils.js"></script>
<script type="text/javascript" src="public/js/validator.js"></script>

<script type="text/javascript">
	//<![CDATA[
	function check_form(){
		var validator = new Validator('mainForm');
			validator.required('provider_name', '请填写供应商名称');
                        validator.required('provider_name', '请填写供应商名称');
                        validator.required('provider_cooperation', '请选择供应商合作方式');
			return validator.passed();
	}
	//]]>
</script>
<div class="main">
	<div class="main_title"><span class="l">供应商管理 >> 编辑 </span><a href="provider/index" class="return r">返回列表</a></div>
	<div class="blank5"></div>
	<?php print form_open_multipart('provider/proc_edit',array('name'=>'mainForm','onsubmit'=>'return check_form()'),array('provider_id'=>$row->provider_id));?>
		<table class="form" cellpadding=0 cellspacing=0>
			<tr>
				<td colspan=2 class="topTd"></td>
			</tr>
                        <tr>
				<td class="item_title">供应商代码:</td>
				<td class="item_input"><?php print form_input('provider_code',$row->provider_code,'class="textbox require wd280" disabled');?></td>
			</tr>	
                        <tr>
				<td class="item_title">供应商名称:</td>
				<td class="item_input"><?php print form_input('provider_name',$row->provider_name,'class="textbox require wd280" '.($perm_edit?'':'disabled'));?></td>
			</tr>	
			<tr>
				<td class="item_title">供应商合作方式:</td>
				<td class="item_input"><?php print form_dropdown('provider_cooperation', get_pair($all_cooperation,'cooperation_id','cooperation_name'), $row->provider_cooperation, 'disabled');?></td>
			</tr>	
			<tr>
				<td class="item_title">公司名称:</td>
				<td class="item_input"><?php print form_input('official_name',$row->official_name,'class="textbox wd280" '.($perm_edit?'':'disabled'));?></td>
			</tr>
			<tr>
				<td class="item_title">开户银行:</td>
				<td class="item_input"><?php print form_input('provider_bank',$row->provider_bank,'class="textbox wd280" '.($perm_edit?'':'disabled'));?></td>
			</tr>
			<tr>
				<td class="item_title">银行帐号:</td>
				<td class="item_input"><?php print form_input('provider_account',$row->provider_account,'class="textbox wd280" '.($perm_edit?'':'disabled'));?></td>
			</tr>
			<tr>
				<td class="item_title">纳税号:</td>
				<td class="item_input"><?php print form_input('tax_no',$row->tax_no,'class="textbox wd280" '.($perm_edit?'':'disabled'));?></td>
			</tr>
			<tr>
				<td class="item_title">状态:</td>
				<td class="item_input">
					<label><?php print form_radio('is_use',0,!$row->is_use,$perm_edit?'':'disabled'); ?>禁用</label>
					<label><?php print form_radio('is_use',1,$row->is_use,$perm_edit?'':'disabled'); ?>启用</label>					
				</td>
			</tr>
                        <tr>
				<td class="item_title">LOGO图:</td>
				<td class="item_input">
                                    <?php print form_upload('logo',$row->logo,'class="textbox wd280" '.($perm_edit?'':'disabled'));?>
                                    
                                </td>
			</tr>
                        <tr>
				<td class="item_title">前台显示名称:</td>
				<td class="item_input"><?php print form_input('display_name',$row->display_name,'class="textbox wd280" '.($perm_edit?'':'disabled'));?></td>
			</tr>
			<tr>
				<td class="item_title">退货地址:</td>
                                <td class="item_input"><?php print form_input('return_address',$row->return_address,'class="textbox wd280" '.($perm_edit?'':'disabled'));?></td>
			</tr>
			<tr>
				<td class="item_title">退货邮编:</td>
                                <td class="item_input"><?php print form_input('return_postcode',$row->return_postcode,'class="textbox wd280" '.($perm_edit?'':'disabled'));?></td>
			</tr>
			<tr>
				<td class="item_title">退货收货人:</td>
                                <td class="item_input"><?php print form_input('return_consignee',$row->return_consignee,'class="textbox wd280" '.($perm_edit?'':'disabled'));?></td>
			</tr>
                        <tr>
				<td class="item_title">退货收货人手机:</td>
                                <td class="item_input"><?php print form_input('return_mobile',$row->return_mobile,'class="textbox wd280" '.($perm_edit?'':'disabled'));?></td>
			</tr>
			<?php if ($perm_edit): ?>
				<tr>
					<td class="item_title"></td>
					<td class="item_input">
						<?php print form_submit(array('name'=>'mysubmit','class'=>'button','value'=>'提交'));?>
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
