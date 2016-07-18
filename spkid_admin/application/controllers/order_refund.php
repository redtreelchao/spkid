<?php

class Order_refund extends CI_Controller {

    public function __construct() {
        parent::__construct();
        $this->admin_id = $this->session->userdata('admin_id');
        $this->admin_name = $this->session->userdata('admin_name');
        if (!$this->admin_id) {
            redirect('index/login');
        }
        $this->load->model('order_refund_model');
    }
    public function index() {
        auth(array('order_refund_index'));
        $this->load->helper('perms_helper');
        $this->load->vars('perms', get_friend_perm());
	global $refund;
        $filter = $this->uri->uri_to_assoc(3);

        // 关键字search_keys是搜索用的字段
        $keys  = array("order_sn","r_type","create_admin","create_date","finance_date","finance_admin");
        $filter = fill_filter( $filter, $keys, true );
        if( !empty($filter['search_keys']) ) $filter['sort_order'] = 'ASC';

        $filter = get_pager_param($filter);
        $data = $this->order_refund_model->list_f($filter);
        if ($this->input->post('is_ajax')) {
            $data['full_page'] = FALSE;
            $data['content'] = $this->load->view('order_refund/index', $data, TRUE);
            $data['error'] = 0;
            unset($data['index']);
            echo json_encode($data);
            return;
        }

        $data['fields_source']['r_type'] = $refund;
        $data['fields_source_data']['r_type'] = $this->_to_js_json($data['fields_source']['r_type']);
        $data['full_page'] = TRUE;
        $this->load->view('order_refund/index', $data);
    }

    public function add() {
        auth('order_refund_add');
	global $refund;
        $data = array();

        $data['fields_source']['r_type'] = $refund;
        $data['fields_source_data']['r_type'] = $this->_to_js_json($data['fields_source']['r_type']);
        $this->load->view('order_refund/add',$data);
    }

    public function proc_add() {
        auth('order_refund_edit');
        $this->load->library('form_validation');
        $this->load->model('order_model');

        $data['order_sn'] = $this->input->post('order_sn');
        $this->form_validation->set_rules('order_sn', 'order_sn', 'trim|required');
        $data['r_type'] = $this->input->post('r_type');
        # $this->form_validation->set_rules('r_type', 'r_type', 'trim|required');
        $data['amount'] = $this->input->post('amount');
        $this->form_validation->set_rules('amount', 'amount', 'trim|required');
        $data['remark'] = $this->input->post('remark');
        # $this->form_validation->set_rules('remark', 'remark', 'trim|required');

        if (!$this->form_validation->run()) {
            sys_msg(validation_errors(), 1);
        }
        $order_info = $this->order_model->filter(array('order_sn' => $data['order_sn']));
        if (empty($order_info)) sys_msg('订单不存在！');
        $data['order_id'] = $order_info->order_id;
        $data['create_name'] = $this->admin_name;
        $data['create_admin'] = $this->admin_id;
        $data['create_date'] = date('Y-m-d H:i:s');
        $pk_id = $this->order_refund_model->insert($data);
        sys_msg('操作成功', 2, array(array('href' => 'order_refund/index', 'text' => '返回列表页')));
    }

    public function edit($pk_id) {
        auth('order_refund_edit');
	global $refund;
        $data = array();
        $pk_id = intval($pk_id);
        $check = $this->order_refund_model->filter(array('id' => $pk_id));
        if (empty($check)) {
            sys_msg('记录不存在', 1);
            return;
        }

        $data['fields_source']['r_type'] = $refund;
        $data['fields_source_data']['r_type'] = $this->_to_js_json($data['fields_source']['r_type']);
        $this->load->vars('row', $check);
        $this->load->view('order_refund/edit',$data);
    }

    public function proc_edit($pk_id) {
        auth('order_refund_edit');
        $this->load->library('form_validation');

        //$data['order_sn'] = $this->input->post('order_sn');
        #$this->form_validation->set_rules('order_sn', 'order_sn', 'trim|required');
        $data['r_type'] = $this->input->post('r_type');
        $this->form_validation->set_rules('r_type', 'r_type', 'trim|required');
        $data['amount'] = $this->input->post('amount');
        $this->form_validation->set_rules('amount', 'amount', 'trim|required');
        $data['remark'] = $this->input->post('remark');
        #$this->form_validation->set_rules('remark', 'remark', 'trim|required');
        if (!$this->form_validation->run()) {
            sys_msg(validation_errors(), 1);
        }
        $this->order_refund_model->update($data, $pk_id);
        sys_msg('操作成功', 2, array(array('href' => 'order_refund/index', 'text' => '返回列表页')));
    }

    public function delete($pk_id) {
        auth('order_refund_delete');
        $pk_id = intval($pk_id);
        $check = $this->order_refund_model->filter(array('id' => $pk_id));

        if (empty($check)) {
            sys_msg('记录不存在', 1);
            return;
        }
        if (!empty($check->finance_admin)){
            sys_msg('订单已财审，不能删除', 1);
        }
        $this->order_refund_model->del(array('id' => $pk_id));
        sys_msg('操作成功', 2, array(array('href' => 'order_refund/index', 'text' => '返回列表页')));
    }

    public function editable() {
        if( ! auth('order_refund_editable'))  die(json_encode(Array('success'=>false,'msg'=>'操作失败，无操作权限！')));
        $pk = $this->input->post( 'pk' );
        $name = $this->input->post( 'name' );
        $value = $this->input->post( 'value' );
        $data[$name] = $value;
        $result = $this->order_refund_model->update( $data, $pk );
        die(json_encode(Array('success'=>true,'msg'=>'操作成功！', 'value'=>443)));
       
    }        

         /**
          * 将一维数组(key=>value)对应样子的，生成可以editable的select 数据源
          */
        function _to_js_json( $ary ){
            $tmp = array();
            foreach( $ary AS $key => $value )
                $tmp[] = '{value:"'.$key.'",text:"'.$value.'"}';
            $tmp = implode(',',$tmp);
            return '['.$tmp.'];';
        }

}

?>