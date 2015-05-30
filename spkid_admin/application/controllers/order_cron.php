<?php  if ( ! defined('BASEPATH')) exit('No direct script access allowed');
class Order_cron extends CI_Controller
{
    public function __construct() {
        parent::__construct();
        ini_set('max_execution_time', '0');
        $this->time=date('Y-m-d H:i:s');
    }
    
    /**
     * 设置定单的快递方式
     */
    public function express($lifetime=600)
    {
        $this->load->model('order_model');
        $start_time = time();
        $batch_num = 100;
        $sql = "select order_id, province, city, district, order_status, shipping_status, pay_status
                from ty_order_info 
                where shipping_id = 0 and order_status = 0 and lock_admin = 0 and create_date >= date_sub(now(), INTERVAL 30 MINUTE)
                limit ?";
        $shipping_sql = "select si.shipping_id, si.shipping_name
                        from ty_shipping_area_region as sar
                        left join ty_shipping_area as sa on sar.shipping_area_id = sa.shipping_area_id
                        left join ty_shipping_info as si on sa.shipping_id = si.shipping_id
                        where sar .region_id in (?,?,?) and si.is_use=1
                        limit 1";
        $order_sql = "update ty_order_info set shipping_id = ? where order_id = ? and shipping_id = 0 and lock_admin=0";
        //while(true)
        //{
            if(time()-$start_time>$lifetime) return;
            $query = $this->db->query($sql, array($batch_num));
            $result = $query->result();
            if(empty($result))
            {
                return;
            }
            foreach($result as $order)
            {

                $coop_id = $this->order_model->get_order_cooperation_id($order->order_id);
                if($coop_id == COOPERATION_TYPE_TMALL){
                    $shipping_id = SHIPPING_ID_PINGTAI;
                    $shipping_name = '平台快递';
                }else{
                    $row = $this->db->query($shipping_sql, array($order->province, $order->city, $order->district))->row();

                    if(empty($row))
                    {
                        continue;
                    }
                    $shipping_id = $row->shipping_id;
                    $shipping_name = $row->shipping_name;
                }

                $this->db->query($order_sql, array($shipping_id, $order->order_id));
                $this->order_model->insert_action($order, '自动分配配送方式'.$shipping_name);
                usleep(1000);
            }
        //}
    }
}