<?php
/**
* Pay_Controller
*/
class Pay extends CI_Controller
{
	
	function __construct()
	{
		parent::__construct();
		$this->time = date('Y-m-d H:i:s');
		$this->user_id = $this->session->userdata('user_id');
		$this->load->model('order_model');
	}

    public function alipay($type = 'return') {
        $this->load->library('alipay');
        $post = $type == 'return' ? $this->input->get(NULL, TRUE) : $this->input->post(NULL, TRUE);

        try {
            $this->order_model->insert_onlinepay_log(array(
                'pay_code' => 'alipay',
                'data' => json_encode($post),
                'create_date' => $this->time
            ));
        } catch (Exception $e) {
            
        }

        if (!$this->alipay->verify($post)){
            $type == 'return' ? redirect('user/order') : die('fail');
        }

        $track_sn = trim($post['out_trade_no']);
        $this->db->trans_begin();
        $pay_track = $this->order_model->lock_pay_track($track_sn);
        // 检查记录状态
        if (empty($pay_track) || $pay_track->pay_status != 0) {
            $type == 'return' ? redirect('user/order') : die('fail');
        }
        // 检查支付金额
        if (round($pay_track->pay_price, 2) != round($post['total_fee'], 2)) {
            $type == 'return' ? redirect('user/order') : die('fail');
        }
        $order_list = $this->order_model->order_list_by_ids(explode('-', $pay_track->order_ids));
        $total_price = floatval($pay_track->pay_price);
        $order_price = 0;
        $order_changed = FALSE;
        // 订单状态预检测
        foreach ($order_list as $order) {
            // 订单状态不对，不添加支付记录
            if ($order->order_status != 0 || $order->pay_status != 0 || $order->pay_id != $pay_track->pay_id || $order->bank_code != $pay_track->bank_code) {
                continue;
            }
            
            $unpay_price = round($order->order_price + $order->shipping_fee - $order->paid_price, 2);
            // 已付完，不添加支付记录
            if ($unpay_price <= 0) {
                continue;
            }
            $order_price += $unpay_price;
        }
        // 如果待付金额不符，标识为问题
        if (round($order_price, 2) != round($total_price, 2)) {
            $order_changed = TRUE;
        }
        foreach ($order_list as $order) {
            // 金额有问题或已全部付完，退出循环
            if($order_changed || $total_price<=0) {
                break;
            }
            // 订单状态不对，不添加支付记录
            if ($order->order_status != 0 || $order->pay_status != 0 || $order->pay_id != $pay_track->pay_id || $order->bank_code != $pay_track->bank_code) {
                continue;
            }
            
            $unpay_price = round($order->order_price + $order->shipping_fee - $order->paid_price, 2);
            // 已付完，不添加支付记录
            if($unpay_price<=0) {
                continue;
            }
            $payment_money = min($unpay_price, $total_price);
            $total_price -= $payment_money;

            $this->order_model->insert_payment(array(
                'order_id' => $order->order_id,
                'pay_id' => PAY_ID_ALIPAY,
                'is_return' => 0,
                'payment_account' => trim($post['buyer_email']),
                'payment_money' => $payment_money,
                'trade_no' => trim($post['trade_no']),
                'payment_date' => $this->time
            ));
            $this->order_model->update(array('paid_price' => round($order->paid_price + $unpay_price, 2)), $order->order_id);
        }
        // 如果还有金额未进入订单，转入用户余额
        if (round($total_price, 2) > 0) {
            $sql_log = "INSERT INTO 
                    ty_user_account_log
                    (link_id, user_id, user_money, pay_points, change_desc, change_code, create_admin, create_date)
                    VALUES
                    (?, ?, ?, 0, '订单支付转余额', 'change_account', 0, now());";
            $this->db->query($sql_log, array($pay_track->track_id, $pay_track->user_id, $total_price));
            $sql_user = "UPDATE ty_user_info SET user_money = user_money + ? WHERE user_id = ?";
            $this->db->query($sql_user, array($total_price, $pay_track->user_id)); 
        }
        $this->order_model->update_pay_track_by_sn(array('pay_status' => 1),$track_sn);
        $this->db->trans_commit();
        $type == 'return' ? redirect('order/info/' . $order->order_id) : print 'success';
    }

    public function recharge($type='return')
	{
		$this->load->library('alipay');
		$this->load->model('user_model');
		$post=$type=='return'?$this->input->get(NULL,TRUE):$this->input->post(NULL,TRUE);

                try {
			$this->order_model->insert_onlinepay_log(array(
				'pay_code'=>'alipay',
				'data' => serialize($post),
				'create_date' => $this->time
			));			
		} catch (Exception $e) {}
		
		if(!$this->alipay->verify($post)) die('fail');
		
                $recharge_sn = trim($post['out_trade_no']);
                $recharge_id = substr($recharge_sn, 2);
                if ($this->user_model->recharge_success($recharge_id)) {
                    $type=='return'?redirect('/user/account'):print 'success';
                } else {
                    $type=='return'?redirect('/user/account'):die('fail');
                }
	}
	public function bill(){
		$this->load->library('bill');
		$get=$this->input->get(NULL,TRUE);
		try {
			$this->order_model->insert_onlinepay_log(array(
				'pay_code'=>'99bill',
				'data' => serialize($get),
				'create_date' => $this->time
			));			
		} catch (Exception $e) {}
		$order_sn=$get['orderId'];
		$order = $this->order_model->lock_order(array('order_sn'=>$order_sn));
		if(!$order){
			print $this->bill->fail();
			return;
		}
		if(!$this->bill->verify($get)){
			print $this->bill->fail($order);
			return;
		}
		/* 检查支付的金额是否相符 */
		$unpay_price = round(($order->order_price+$order->shipping_fee-$order->paid_price)*100);
		$total_fee = intval($get['orderAmount']);
		if($unpay_price!=$total_fee) 
		{
			print $this->bill->fail($order);
			return;
		}
		//插入支付记录		
		$this->order_model->insert_payment(array(
			'order_id' => $order->order_id,
			'pay_id' => PAY_ID_99BILL,
			'is_return' => 0,
			'payment_account' => '',
			'payment_money' => $total_fee/100,
			'trade_no' => trim($get['dealId']),
			'payment_date' => $this->time
		));
		$this->order_model->update(array('paid_price'=>round($order->paid_price+$total_fee/100,2)),$order->order_id);
		$this->db->trans_commit();
		print $this->bill->success($order);
	}
}