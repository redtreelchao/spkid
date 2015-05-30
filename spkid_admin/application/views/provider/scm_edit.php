<?php include(APPPATH.'views/common/header.php');?>
<script type="text/javascript" src="public/js/utils.js"></script>
<script type="text/javascript" src="public/js/validator.js"></script>

<script type="text/javascript">
	//<![CDATA[
	function check_form(){
		var validator = new Validator('mainForm');
                return validator.passed();
	}
	//]]>
</script>
<div class="main">
	<div class="main_title"><span class="l">供应商管理 >> 直发设置 </span><a href="provider/index" class="return r">返回列表</a></div>
	<div class="blank5"></div>
	<?php print form_open_multipart('provider/proc_scm_edit',array('name'=>'mainForm','onsubmit'=>'return check_form()'),array('provider_id'=>$row->provider_id));?>
		<table class="form" cellpadding=0 cellspacing=0>
			<tr>
				<td colspan=2 class="topTd"></td>
			</tr>
                        <tr>
				<td class="item_title">登陆状态:</td>
                                <td class="item_input">
                                    <label><?php print form_radio('provider_status',0,!$row->provider_status,$perm_edit?'':'disabled'); ?>正常</label>
                                    <label><?php print form_radio('provider_status',1,$row->provider_status,$perm_edit?'':'disabled'); ?>锁定</label>					
				</td>
			</tr>
                        <tr>
				<td class="item_title">登陆用户名:</td>
				<td class="item_input"><?php print form_input('user_name',$row->user_name,'class="textbox wd280" '.($perm_edit?'':'disabled'));?></td>
			</tr>	
                        <tr>
				<td class="item_title">登陆密码:</td>
				<td class="item_input"><?php print form_password('password','','class="textbox wd280" '.($perm_edit?'':'disabled'));?></td>
			</tr>	
			<tr>
				<td class="item_title">公司名称:</td>
				<td class="item_input"><?php print form_input('official_name',$row->official_name,'class="textbox wd280" '.($perm_edit?'':'disabled'));?></td>
			</tr>
                        <tr>
				<td class="item_title">公司地址:</td>
				<td class="item_input"><?php print form_input('official_address',$row->official_address,'class="textbox wd280" '.($perm_edit?'':'disabled'));?></td>
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
				<td class="item_title">税率:</td>
				<td class="item_input"><?php print form_input('provider_cess',$row->provider_cess,'class="textbox wd280" '.($perm_edit?'':'disabled'));?></td>
			</tr>
			<tr>
				<td class="item_title">负责人:</td>
				<td class="item_input"><?php print form_input('scm_responsible_user',$row->scm_responsible_user,'class="textbox wd280" '.($perm_edit?'':'disabled'));?></td>
			</tr>
                        <tr>
				<td class="item_title">负责人手机:</td>
				<td class="item_input"><?php print form_input('scm_responsible_phone',$row->scm_responsible_phone,'class="textbox wd280" '.($perm_edit?'':'disabled'));?></td>
			</tr>
                        <tr>
				<td class="item_title">负责人QQ:</td>
				<td class="item_input"><?php print form_input('scm_responsible_qq',$row->scm_responsible_qq,'class="textbox wd280" '.($perm_edit?'':'disabled'));?></td>
			</tr>
                        <tr>
				<td class="item_title">负责人EMAIL:</td>
				<td class="item_input"><?php print form_input('scm_responsible_mail',$row->scm_responsible_mail,'class="textbox wd280" '.($perm_edit?'':'disabled'));?></td>
			</tr>
                        <tr>
				<td class="item_title">订单处理联系人:</td>
				<td class="item_input"><?php print form_input('scm_order_process_user',$row->scm_order_process_user,'class="textbox wd280" '.($perm_edit?'':'disabled'));?></td>
			</tr>
                        <tr>
				<td class="item_title">订单处理人手机:</td>
				<td class="item_input"><?php print form_input('scm_order_process_phone',$row->scm_order_process_phone,'class="textbox wd280" '.($perm_edit?'':'disabled'));?></td>
			</tr>
                        <tr>
				<td class="item_title">订单处理人QQ:</td>
				<td class="item_input"><?php print form_input('scm_order_process_qq',$row->scm_order_process_qq,'class="textbox wd280" '.($perm_edit?'':'disabled'));?></td>
			</tr>
                        <tr>
				<td class="item_title">订单处理人EMAIL:</td>
				<td class="item_input"><?php print form_input('scm_order_process_mail',$row->scm_order_process_mail,'class="textbox wd280" '.($perm_edit?'':'disabled'));?></td>
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
                        <tr>
				<td class="item_title">短信价格:</td>
                                <td class="item_input"><?php print form_input('sms_price',$row->sms_price,'class="textbox wd280" '.($perm_edit?'':'disabled'));?></td>
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
