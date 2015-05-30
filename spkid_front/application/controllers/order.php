<?php
/**
* 
*/
class Order extends CI_Controller
{
	
	function __construct()
	{
		parent::__construct();
		$this->time = date('Y-m-d H:i:s');
		$this->user_id = $this->session->userdata('user_id');
		$this->load->model('order_model');
	}

	public function index()
	{
		redirect('user/order');
	}

	public function info($order_sn)
	{
	    global $alipay_bank_list;
	    $this->load->helper('order');
		$this->load->model('user_model');
		if(!$this->user_id) goto_login('order/info/'.$order_sn);
		$user=$this->user_model->filter(array('user_id'=>$this->user_id));
		$rank=$this->user_model->filter_user_rank(array('rank_id'=>$user->rank_id));
		$order = $this->order_model->order_info('',$order_sn);
		if(!$order || $order->user_id!=$this->user_id) sys_msg('订单不存在',1);	
		$order_id = intval($order->order_id );
		$order->unpay_price=round($order->order_price+$order->shipping_fee-$order->paid_price,2);
		$order_payment = $this->order_model->order_payment($order_id);
		if(!$order->point_sent) $order->point_amount = calc_point_amount($order,$order_payment,$rank);
		$order_product = $this->order_model->order_product($order_id);
		// 如果订单中有虚库，提示用户
		$has_consign=FALSE;
		foreach( $order_product as $p )
		{
			if ( $p->consign_num )
			{
				$has_consign=TRUE;
				break;
			}
		}
		unset($p);
		// 分割礼包
		list($product_list,$package_list) = split_package_product($order_product);

		if(!empty($order->bank_code)){
		    $pay_logo = static_style_url($alipay_bank_list[$order->bank_code]['pay_logo']);
		}else{
		    $pay_logo = img_url($order->pay_logo);
		}
        
        // 平台订单未发运之前显示快递,而不是显示配送方式表中的名字
        if($order->shipping_id == SHIPPING_ID_PINGTAI){
            $order->shipping_name = '快递';
        }
		
		$this->load->vars(array(
			'title' => '订单详情',
			'order' => $order,
			'order_payment' => $order_payment,
			'product_list' => $product_list,
			'package_list' => $package_list,
			'has_consign' => $has_consign,
		    'pay_logo' => $pay_logo,
			'left_sel' =>11,
		));
		$this->load->view('order/info');
		
	}

	public function pay($order_ids)
	{
             $order_ids = trim($order_ids);
             $arr_order_id = array_filter(array_map('intval',explode('-', $order_ids)));
             $this->load->model('order_model');
             $this->load->helper('order');
             $order_list = $this->order_model->order_list_by_ids($arr_order_id);
             $arr_order = array();
             $pay_id = 0;
             $bank_code = NULL;
             $pay_price = 0;
             $arr_order_id = array();
             foreach($order_list as $order)
             {
                 if($order->user_id!=$this->user_id){
                     continue;
                 }
                 if($order->order_price+$order->shipping_fee-$order->paid_price<=0){
                     continue;
                 }
                 if($order->order_status!=0){
                     continue;
                 }
                 if($pay_id>0 && $order->pay_id!=$pay_id){
                     continue;
                 }
                 if($bank_code!==NULL && $order->bank_code!=$bank_code){
                     continue;
                 }
                 if($pay_id==0) {
                     $pay_id = $order->pay_id;
                 }
                 if($bank_code===NULL){
                     $bank_code = $order->bank_code;
                 }
                 $pay_price += round($order->order_price + $order->shipping_fee - $order->paid_price, 2);
                 $arr_order_id[] = intval($order->order_id);
             }
             if($pay_price<=0)
             {
                 sys_msg('没有可支付的订单',1);
             }
             
             // 取出或生成支付单
             $pay_track = $this->order_model->create_pay_track($arr_order_id, $pay_id, $bank_code, $pay_price, $this->user_id);
             if(empty($pay_track)){
                 sys_msg('系统繁忙，请重试',1);
             }              
             // 生成支付链接
             $payment = $this->order_model->filter_payment(array('pay_id'=>$pay_id));
             switch($payment->pay_code)
             {
                 case 'alipay':
                     $this->load->library('alipay');
                     $link=$this->alipay->get_link($pay_track);
                     redirect($link);
                     break;
                 default:
                     sys_msg('您的订单不支持在线支付',1);
             }

	}
        
        public function invalid($order_sn) {
            $this->load->model('invalid_model');
            $this->load->model('user_model');
            $this->load->helper('order_helper');
            $this->db->trans_begin();
            $order = $this->invalid_model->lock_order($order_sn);
            if(!$order) {
                echo "<html><head><meta content=\"text/html; charset=utf-8\" http-equiv=\"Content-Type\"><script type='text/javascript'>alert('抱歉，此订单不存在，请联系客服!'); window.location.href = '/user/order';</script></head><body></body></html>";
		exit();
            }
	    $order_id = intval($order->order_id);
            $perms = get_order_perm($order);
            if(!$perms['invalid']) {
		echo "<html><head><meta content=\"text/html; charset=utf-8\" http-equiv=\"Content-Type\"><script type='text/javascript'>alert('抱歉，此订单不能作废，请联系客服!'); window.location.href = '/user/order';</script></head><body></body></html>";
		exit();
	    }
            $user = $this->user_model->lock_user($order->user_id);
            // 返还余额支付
            $order_payment = $this->invalid_model->order_payment($order_id);
            $balance_amount = 0;
            foreach ($order_payment as $payment) {
            if ($payment->is_discount)  {
                $voucher = $this->invalid_model->lock_voucher($payment->payment_account);
                // 删除支付记录
		$this->invalid_model->delete_payment($payment->payment_id);
                // 恢复现金券的可用数量
		$this->invalid_model->update_voucher(array('used_number'=>$voucher->used_number-1),$voucher->voucher_id);
                // 更新订单
		$order->paid_price = fix_price($order->paid_price - $payment->payment_money);
                $this->invalid_model->update_order(array('paid_price'=>$order->paid_price),$order_id);
                update_shipping_fee($order);
		continue;
            }
            $balance_amount += $payment->payment_money;
        }
        if ($balance_amount) {
            $this->invalid_model->insert_payment(array(
                'order_id' => $order->order_id,
                'is_return' => 0,
                'pay_id' => PAY_ID_PAYBACK,
                'bank_code' => '',
                'payment_account' => '',
                'payment_money' => fix_price($balance_amount * -1),
                'trade_no' => '',
                'payment_remark' => '订单作废，已付金额返还帐户。',
                'payment_admin' => -1,
                'payment_date' => date('Y-m-d H:i:s')
            ));

            $this->invalid_model->insert_user_account_log(array(
                'link_id' => $order->order_id,
                'user_id' => $user->user_id,
                'user_money' => fix_price($balance_amount),
                'change_desc' => sprintf("订单 %s 作废，已付金额返还帐户。", $order->order_sn),
                'change_code' => 'order_payback',
                'create_admin' => -1,
                'create_date' => date('Y-m-d H:i:s')
            ));
            // 更新用户表
            $this->user_model->update(array('user_money' => fix_price($user->user_money + $balance_amount)), $user->user_id);
        }
        // 返回库存
        $this->invalid_model->update_trans(
                array('trans_status' => TRANS_STAT_CANCELED, 'cancel_admin' => -1, 'cancel_date' => date('Y-m-d H:i:s')), array('trans_type' => TRANS_TYPE_SALE_ORDER, 'trans_sn' => $order->order_sn, 'trans_status' => TRANS_STAT_AWAIT_OUT)
        );
        $order_product = $this->invalid_model->order_product($order_id);
        $subs = array();
        foreach ($order_product as $product) {
            if (!isset($subs[$product->sub_id])) {
                $subs[$product->sub_id] = array('gl_num' => 0, 'consign_num' => 0);
            }
            $subs[$product->sub_id]['gl_num'] += $product->product_num - $product->consign_num;
            $subs[$product->sub_id]['consign_num'] += $product->consign_num;
        }

        if ($subs) {
            $sub_list = $this->invalid_model->lock_sub(array('sub_id' => array_keys($subs)));
            foreach ($sub_list as $sub) {
                $update = array('gl_num' => $sub->gl_num + $subs[$sub->sub_id]['gl_num'], 'wait_num' => $sub->wait_num - $subs[$sub->sub_id]['consign_num']);
                if ($sub->consign_num >= 0)
                    $update['consign_num'] = $sub->consign_num + $subs[$sub->sub_id]['consign_num'];
                $this->invalid_model->update_sub($update, $sub->sub_id);
            }
        }

        // 置订单为作废
        if (!$order_payment && !$order_product) {
            $delete = TRUE;
            $this->invalid_model->delete_order($order_id);
            $this->invalid_model->delete_advice_where(array('order_id' => $order_id, 'is_return' => 0));
            $this->invalid_model->delete_action_where(array('order_id' => $order_id, 'is_return' => 0));
        } else {
            $delete = FALSE;
            $order->order_status = 4;
            $this->invalid_model->update_order(
                    array(
                'order_status' => $order->order_status,
                'is_ok' => 1,
                'is_ok_admin' => -1,
                'is_ok_date' => date('Y-m-d H:i:s'),
                'lock_admin' => 0
                    ), $order_id);
            $this->invalid_model->insert_action($order, '订单作废');
        }
        $this->db->trans_commit();

        echo "<html><head><meta content=\"text/html; charset=utf-8\" http-equiv=\"Content-Type\"><script type='text/javascript'>alert('订单已作废!'); window.location.href = '/order/info/".$order->order_sn."';</script></head><body></body></html>";
        die;
        }

	public function order_print($order_id)
	{
		
	}
}