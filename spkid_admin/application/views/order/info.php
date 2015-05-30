<?php include(APPPATH.'views/common/header.php');?>
<script type="text/javascript" src="public/js/utils.js"></script>
<script type="text/javascript" src="public/js/validator.js"></script>
<script type="text/javascript" src="public/js/order.js"></script>
<script type="text/javascript" src="public/js/cluetip.js"></script>
<script type="text/javascript" src="public/js/jui/bgiframe.min.js"></script>
<script type="text/javascript" src="public/js/jui/hoverIntent.js"></script>
<script type="text/javascript" src="public/js/lhgdialog/lhgdialog.min.js"></script>
<link rel="stylesheet" href="public/style/cluetip.css" type="text/css" media="all" />
<link type="text/css" href="/public/style/jui/dialog.css" rel="stylesheet" />

<!-- <script type="text/javascript" src="public/js/jui/core.min.js"></script>
    <script type="text/javascript" src="public/js/jui/datepicker.min.js"></script>
-->    <link type="text/css" href="public/style/jui/theme.css" rel="stylesheet" />
        <link type="text/css" href="/public/style/jui/core.css" rel="stylesheet" />
        <script type="text/javascript" src="/public/js/jui/core.min.js"></script>
        <script type="text/javascript" src="/public/js/jui/widget.min.js"></script>
        <script type="text/javascript" src="/public/js/jui/position.min.js"></script>
        <script type="text/javascript" src="/public/js/jui/mouse.min.js"></script>
        <script type="text/javascript" src="/public/js/jui/draggable.min.js"></script>
        <script type="text/javascript" src="/public/js/jui/resizable.min.js"></script>
        <script type="text/javascript" src="/public/js/jui/dialog.min.js"></script>

<script type="text/javascript">
	//<![CDATA[
	$(function(){
		$('span.img_tip').cluetip({splitTitle: '|',showTitle:false});
        $('#h_shipping').dialog({autoOpen:false,width:300,modal:true,resizable:false,title:'手工发货'});

    });
	function check_form(){
		return false;
	}

	function load_user_panel()
	{
			//var parent_dom = $("div#float_panel");
			//parent_dom.show();
			//parent_dom.html(result.content);
	    	var dg = new $.dialog({ id:'thepanel',height:300,maxBtn:false, title:'',iconTitle:false,cover:true,html:$('#float_panel')[0] });
	    	dg.ShowDialog();
	    	//dg.addBtn('ok','确定',function(){post_dianping()});
	    	return false;

	}

	//]]>
</script>
<div class="main" id="order">
	<div class="main_title"><span class="l">订单管理 >> 查看 </span><a href="order/index" class="return r">返回列表</a></div>
	<div class="blank5"></div>
	<?php print form_open_multipart('model/proc_edit',array('name'=>'mainForm','onsubmit'=>'return check_form()'), array('order_id'=>$order->order_id));?>
		<table style="border:1px solid #7F9DB9; width:100%;" cellpadding=0 cellspacing=0 bgcolor="#FFFFFF">
			<tr>
				<td height="40" colspan="4" align="center">

					<?php
						if ($order->lock_admin)
							print form_button('op_unlock','解锁',($perms['unlock']?'':'disabled').' onclick="switch_lock(\'unlock\')"');
						else
							print form_button('op_lock','锁定',($perms['lock']?'':'disabled').' onclick="switch_lock(\'lock\')"')
					?>
					<?php print form_button('op_confirm','客审',($perms['confirm']?'':'disabled').' onclick="order_confirm()"'); ?>
					<?php print form_button('op_unconfirm','反客审',($perms['unconfirm']?'':'disabled').' onclick="order_unconfirm()"'); ?>
					<?php print form_button('op_shipping','发货',($perms['shipping']?'':'disabled').' onclick="order_shipping()"'); ?>
					<?php print form_button('op_deny','拒收',($perms['deny']?'':'disabled').' onclick="order_deny()"'); ?>
					<?php print form_button('op_pay','财审',($perms['pay']?'':'disabled').' onclick="order_pay()"'); ?>
					<?php print form_button('op_invalid','作废',($perms['invalid']?'':'disabled').' onclick="invalid()"'); ?>
					<?php if($order->odd):?>
                                        <?php print form_button('op_odd_cancel','取消问题单标记',($perms['odd_cancel']?'':'disabled').' onclick="odd_cancel()"'); ?>
                                        <?php endif;?>
					&nbsp;
					&nbsp;
					&nbsp;
					&nbsp;
					<?php if ($perms['edit_order'] || $perms['shipping'] || $perms['change_shipping']): ?>
						<?php print form_dropdown('source_id',array(''=>'订单来源')+get_pair($source_list,'source_id','source_name'),'','onchange="change_source()" '.($perms['edit_order']?'':'disabled')) ?>
						<?php print form_dropdown('pay_id',array(''=>'支付方式'),'','onchange="change_pay()" '.($perms['edit_order']?'':'disabled')) ?>
						<?php print form_dropdown('shipping_id',array(''=>'配送方式')+get_pair($shipping_list,'shipping_id','shipping_name'),'',$perms['edit_order']||$perms['shipping']||$perms['change_shipping']?'':'disabled') ?>
						<?php print form_button('op_routing','更改流程','onclick="change_routing();"') ?>
					<?php endif ?>
				</td>
			</tr>
			<tr>
				<td colspan="4" class="item_title_1">
					订单号：<?php print $order->order_sn; ?>
					【查看用户：<a href="#" onclick="load_user_panel();return false;"><?php print $user->user_name; ?></a>】
                     <?php if (!$order->shipping_true) : ?> <font color="red" style="font-weight:bold;">【虚发】</font> <?php endif; ?>
				</td>
			</tr>
			<tr>
				<td class="item_title" width="100"><strong>订单流程</strong></td>
				<td class="item_input">
				【<?php print $order->source_name ?>】
				【<font color=red><?php print $order->pay_name ?></font>】
				【<?php print $order->shipping_name ?>】
				</td>
				<td class="item_title" width="100"><strong>订单状态</strong></td>
				<td class="item_input">
				<?php print implode('&nbsp;',format_order_status($order,TRUE)); ?>
				<?php if(isset($lock_admin)) print "<font color=red>当前被 {$lock_admin} 锁定</font>"; ?>
				<?php if ($order->shipping_status) print "【运单号:{$order->invoice_no} <a href='javascript:void(0);' onclick='edit_invoice_no()' class='edit'></a>】" ?>
				</td>
			</tr>

			<tr>
				<td class="item_title"><strong>配送地址</strong></td>
				<td class="item_input" colspan="3">
				<?php print '<strong>'.$order->consignee.'</strong>'; ?>
				<?php print $order->province_name ?>
				<?php print $order->city_name ?>
				<?php print $order->district_name ?>
				<?php print $order->address ?>
				<?php if($order->zipcode) print '&nbsp;&nbsp;邮编： '.$order->zipcode ?>
				&nbsp;&nbsp;电话：
				<?php if ($order->mobile) print '<i>'.$order->mobile.'</i>&nbsp;&nbsp' ?>
				<?php if ($order->tel) print '<i>'.$order->tel.'</i>' ?>
				<?php if($order->best_time) print "<font color=red>&nbsp;&nbsp;最佳送货时间：{$order->best_time}</font>" ?>
				<?php if ($perms['edit_order']): ?>
				<a class="edit" href="order/consignee/<?php print $order->order_id; ?>" title="编辑"></a>
				<?php endif ?>
				</td>
			</tr>
			<tr>
				<td class="item_title"><strong>运费</strong></td>
				<td class="item_input">
				<?php print $order->shipping_fee ?>
					<?php if ($order->shipping_fee>0): ?>
						<?php print form_button('btn_free_shipping_fee','免运费','onclick="free_shipping_fee();" '.($perms['edit_order']?'':'disabled')) ?>
					<?php else: ?>
						<?php print form_button('btn_reset_shipping_fee','重新计算运费','onclick="reset_shipping_fee();" '.($perms['edit_order']?'':'disabled')) ?>
					<?php endif ?>
					<?php print form_input('new_shipping_fee',$order->shipping_fee, 'size="3" '.($perms['edit_order']?'':'disabled')) ?>
					<?php print form_button('btn_update_shipping_fee','更新运费','onclick="update_shipping_fee();" '.($perms['edit_order']?'':'disabled')) ?>
				</td>
				<td class="item_title"><strong>实际运费</strong></td>
				<td class="item_input">
				<?php print $order->real_shipping_fee ?>
				<?php if ($order->shipping_status) print "<a href='javascript:void(0);' onclick='edit_real_shipping_fee()' class='edit'></a>" ?>
				</td>
				</td>
			</tr>

			<tr>
				<td class="item_title"><strong>使用余额</strong></td>
				<td class="item_input">
					<?php if ($order->order_amount>0): ?>
					<?php print form_input('balance_amount','','class="textbox" size="5" '.($perms['edit_order']?'':'disabled')); ?>
					<?php print form_button('balance_submit','支付','onclick="pay_balance();" '.($perms['edit_order']?'':'disabled')); ?>
					<?php else: ?>
					已全额支付。
					<?php endif ?>
					可用余额： <?php print fix_price($user->user_money); ?>
				</td>
				<td class="item_title">使用现金券</td>
				<td class="item_input">
					<?php if ($voucher_payment): ?>
						<?php printf('已使用现金券 %s , 抵用 %.2f 元', $voucher_payment->payment_account, $voucher_payment->payment_money) ?>
						<?php if ($perms['edit_order']): ?>
							<a href="javascript:remove_voucher()">[ 移除 ]</a>
						<?php endif ?>
					<?php else: ?>
						<select name="available_voucher" onchange="choice_voucher();" <?php if(!$perms['edit_order']) print 'disabled'; ?>>
							<option value="">可用现金券</option>
							<?php foreach ($voucher_list as $voucher): ?>
								<option value="<?php print $voucher->voucher_sn; ?>"><?php print $voucher->voucher_sn.' '.$voucher->voucher_name ?></option>
							<?php endforeach ?>
						</select>
						<?php print form_input('voucher_sn','','class="textbox" '.($perms['edit_order']?'':'disabled')); ?>
						<?php print form_button('voucher_submit','支付','onclick="pay_voucher();" '.($perms['edit_order']?'':'disabled')); ?>
					<?php endif ?>
				</td>
			</tr>
			<tr>
				<td colspan="4" class="item_title_1">
					其它信息
					<?php if ($perms['edit_order']): ?>
					<a class="edit" href="order/other/<?php print $order->order_id; ?>" title="编辑"></a>
					<?php endif ?>
				</td>
			</tr>
			<tr>
				<td class="item_title"><strong>发票抬头</strong></td>
				<td class="item_input"><?php print $order->invoice_title?$order->invoice_title:'[ 不要发票 ]' ?></td>
				<td class="item_title"><strong>发票内容</strong></td>
				<td class="item_input"><?php print $order->invoice_content ?></td>
			</tr>
			<tr>
				<td class="item_title"><strong>客户留言</strong></td>
				<td class="item_input"><?php print $order->user_notice ?></td>
				<td class="item_title"><strong>客服回复</strong></td>
				<td class="item_input"><?php print $order->to_buyer ?></td>
			</tr>

			<tr>
				<td colspan="4" class="item_title_1">
					订单商品
					<?php if ($perms['edit_order']): ?>
					<a class="edit" href="order/product/<?php print $order->order_id; ?>" title="编辑"></a>
					<?php endif ?>
				</td>
			</tr>
			<tr>
				<td colspan="4" class="item_input" style="padding:0;">
					<?php include 'order_product_info.php' ?>
				</td>
			</tr>
			<tr>
				<td colspan="4" class="item_title_1" >
					支付记录
				</td>
			</tr>
			<tr>
				<td colspan="4" class="item_input" style="padding:0;">
					<?php include 'order_payment.php' ?>
				</td>
			</tr>
			<tr>
				<td colspan="4" class="item_title_1">
					意见
				</td>
			</tr>
			<tr>
				<td colspan="4" class="item_input" style=" padding:0 0 5px 0;">
					<?php include 'order_advice.php' ?>
					<?php print form_dropdown('advice_type_id',array('意见类型')+get_pair($all_advice_type,'type_id','type_name'),'',$perms['advice']?'':'disabled') ?>
					<?php print form_input('advice_content','','class="textbox" size="60" '.($perms['advice']?'':'disabled')) ?>
					<?php print form_button('advicesubmit','提交意见','onclick="post_advice()" '.($perms['advice']?'':'disabled')) ?>
				</td>
			</tr>
			<tr>
				<td colspan="4" class="item_title_1" style="padding:0;">
					操作日志
				</td>
			</tr>
			<tr>
				<td colspan="4" class="item_input" style="padding:0;">
					<div style="height:250px; overflow:scroll;">
					<?php include 'order_action.php' ?>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan=2 class="bottomTd"></td>
			</tr>
		</table>
	<?php print form_close();?>
</div>
<div id="float_panel" style="display:none;">
<table border="0" width="100%">
<caption>
<strong> 购货人信息 </strong>
</caption>
<tr>
<td> 电子邮件 </td>
<td>
<a href="mailto:<?php print $user->email ?>"><?php print $user->email ?></a>
</td>
</tr>
<tr>
<td> 手机 </td>
<td> <?php print $user->mobile ?> </td>
</tr>
<tr>
<td> 账户余额 </td>
<td> ￥<?php print $user->user_money ?>元 </td>
</tr>
<tr>
<td> 消费积分 </td>
<td> <?php print $user->pay_points ?> </td>
</tr>
</table>
<table border="0" width="100%">
<caption>
<strong> 收货人：<?php print $order->consignee ?> </strong>
</caption>
<tr>
<td> 地址 </td>
<td> <?php print $order->province_name ?> <?php print $order->city_name ?> <?php print $order->district_name ?> <?php print $order->address ?> </td>
</tr>
<tr>
<td> 邮编 </td>
<td> <?php print $order->zipcode ?> </td>
</tr>
<tr>
<td> 电话 </td>
<td> <?php print $order->tel ?> </td>
</tr>
<tr>
<td> 手机 </td>
<td> <?php print $order->mobile ?> </td>
</tr>
</table>
</div>
<div id="h_shipping" style="display:none;">
<table style="width:100%;">
<tr>
<td height="30" align="right">运单号：</td><td><input type="text" name="invoice_no"></td>
</tr>
<tr>
<td height="30" align="right">发货类型：</td>
<td><!--<input type="radio" value="1" name="shipping_true" checked/>实发-->
<input type="radio" value="0" name="shipping_true" style="margin-left:10px;" checked/>虚发
</td>
</tr>
<tr>
<td height="30">&nbsp;</td>
<td><input type="button" value="确定" name="sb_shipping" class="button" onclick="order_shipping();"></td>
</tr>
</table>
</div>
<?php include(APPPATH.'views/common/footer.php');?>
