<?php
#doc
#	classname:	Brand
#	scope:		PUBLIC
		#1=>Array('全场满赠','全赠','send'),
		#2=>Array('全场满减','全减','minus'),
		#3=>Array('品牌满赠','品赠','send'),
		#4=>Array('品牌满减','品减','minus'),
		#5=>Array('单品满赠','单赠','send'),
		#6=>Array('单品满减','单减','minus')
##
#/doc

class Campaign extends CI_Controller
{
	var $campaign_types = Array();
	var $campaign_types_send = Array(
		1=>'全场满赠',
		3=>'品牌满赠',
		5=>'单品满赠',
	);
	var $campaign_types_minus= Array(
		2=>'全场满减',
		4=>'品牌满减',
		6=>'单品满减'
		);

	function __construct ()
	{
		parent::__construct();
		$this->admin_id = $this->session->userdata('admin_id');
		if(!$this->admin_id) redirect('index/login');
		$this->load->model('campaign_model');
		$this->campaign_types = $this->campaign_types_send + $this->campaign_types_minus;
		$this->campaign_types['minus'] = $this->campaign_types_minus;
		$this->campaign_types['send'] = $this->campaign_types_send;
	}
	
	public function index ()
	{
                auth(array('campaign_view','campaign_edit'));
		$filter = $this->uri->uri_to_assoc(3);
		$campaign_name = trim($this->input->post('campaign_name'));
		if (!empty($campaign_name)) $filter['campaign_name'] = $campaign_name;
                $start_time = trim($this->input->post('start_time'));
		if (!empty($start_time)) $filter['start_time'] = $start_time;
                $end_time = trim($this->input->post('end_time'));
		if (!empty($end_time)) $filter['end_time'] = $end_time;

		$filter = get_pager_param($filter);
		$data = $this->campaign_model->campaign_list($filter);
		if ($this->input->is_ajax_request())
		{
			$data['full_page'] = FALSE;
			$data['content'] = $this->load->view('campaign/index', $data, TRUE);
			$data['error'] = 0;
			unset($data['list']);
			echo json_encode($data);
			return;
		}
		$data['full_page'] = TRUE;
		$data['campaign_types'] = $this->campaign_types;
		$this->load->view('campaign/index', $data);
	}
         function sel_brand(){
            $bn = $this->input->post('val');
            if($bn == ''){
                echo json_encode(array('type'=>4));
                exit;
            }
            $this->load->model('brand_model');
            //$list = $this->brand_model->all_brand(Array('brand_name'=>$bn));
            $list = $this->brand_model->brand_list(Array('brand_name'=>$bn));
            echo json_encode(array('type'=>1, 'cb'=> $this->input->post('cb'), 'list' => $list['list']));
        }       
        function sel_product(){
            $pro = $this->input->post('val');
            if($pro == ''){
                echo json_encode(array('type'=>4));
                exit;
            }
            $list = $this->campaign_model->product($pro);
            if(empty($list)){
                $list = $this->campaign_model->productsn($pro);
            }
            echo json_encode(array('type'=>1, 'cb'=> $this->input->post('cb'), 'list' => $list));
        }
	public function add_minus()
	{
            auth(array('campaign_edit'));
	$this->load->vars('campaign_types', $this->campaign_types);
            $this->load->view('campaign/add_minus');
	}
	public function add()
	{
            auth(array('campaign_edit'));
		$data['campaign_types'] = $this->campaign_types;
            $this->load->view('campaign/add',$data);
	}

	public function proc_add()
	{
            auth(array('campaign_edit'));
		$this->load->library('form_validation');
		$this->form_validation->set_rules('campaign_name', '活动名称', 'trim|required');
		if (!$this->form_validation->run()) {
			sys_msg(validation_errors(), 1);
		}
		$update = array();
		$update['campaign_type'] = $this->input->post('campaign_type');
                $update['campaign_name'] = $this->input->post('campaign_name');
		$update['limit_price'] = $this->input->post('limit_price');
		$update['brand_id'] = $this->input->post('brand_id');
		$update['promote_value'] = $this->input->post('tag_id');
		$update['start_date'] = $this->input->post('start_time');
                $update['end_date'] = $this->input->post('end_time');
		$update['is_use'] = intval($this->input->post('is_use'));
		$update['create_admin'] = $this->admin_id;
		$update['create_date'] = date('Y-m-d H:i:s');

		$campaign_id = $this->campaign_model->insert($update);
		sys_msg('操作成功', 0, array(array('text'=>'继续编辑','href'=>'campaign/edit/'.$campaign_id), array('text'=>'返回列表','href'=>'campaign/index')));
	}

	public function edit($campaign_id)
	{
                auth(array('campaign_edit'));
                $campaign_id = intval($campaign_id);
		$campaign = $this->campaign_model->filter(array('campaign_id'=>$campaign_id));
		if (!$campaign) {
			sys_msg('记录不存在', 1);
		}
		// 满赠要 加载赠品
		if( in_array( $campaign->campaign_type, array_keys( $this->campaign_types_send ) )){
			$this->load->model('product_model');
			$pro_arr = $this->product_model->filter(array('product_id' => $campaign->promote_value));
			$this->load->vars('pro_arr', $pro_arr);
		}
		// 品牌相关
		if ( $campaign->brand_id > 0 ) {
			$this->load->model('brand_model');
			$brand_arr = $this->brand_model->filter(array('brand_id' => $campaign->brand_id));
			$this->load->vars('brand_arr', $brand_arr);
		}
		$this->load->vars('cam_arr', $campaign);
		$this->load->vars('campaign_types', $this->campaign_types);
		$this->load->view('campaign/edit');
	}
	public function check_product(){
                auth(array('campaign_edit'));
                $product_ids_name = $this->input->post('name');
                $product_ids_content = $this->input->post('content');
		$product_ids_content = preg_split("/[,，\n]+/", $product_ids_content);
		if( is_array($product_ids_content) ) $product_ids_content = array_map( 'trim', $product_ids_content );
		$result = $this->campaign_model->check_product_valid($product_ids_name, $product_ids_content);
		$result = $result[0];
		$result['total'] = sizeof($product_ids_content);
		$str = "识别出%s个，共%s个。";
		if( $result['total'] != $result['recog_num'] ) $str .= "未识别出：%s";
		
		$str = sprintf( $str, $result['recog_num'], $result['total'], implode(',',array_diff($product_ids_content, explode(',',$result[$product_ids_name]))) );
		echo json_encode(array('type'=>1,'content'=>$str,'array'=> $result ));
}

	public function proc_edit($campaign_id)
	{
                auth(array('campaign_edit'));
                $campaign_id = intval($campaign_id);
		$this->load->library('form_validation');
		$this->form_validation->set_rules('campaign_name', '活动名称', 'trim|required');
		if (!$this->form_validation->run()) {
			sys_msg(validation_errors(), 1);
		}
		$update = array();
                $update['campaign_name'] = $this->input->post('campaign_name');
		$update['limit_price'] = $this->input->post('limit_price');
		$update['promote_value'] = $this->input->post('tag_id');
		//$update['brand_id'] = $this->input->post('brand_id');
		$update['start_date'] = $this->input->post('start_time');
                $update['end_date'] = $this->input->post('end_time');
		$update['is_use'] = intval($this->input->post('is_use'));

		$this->campaign_model->update($update,$campaign_id);
		sys_msg('操作成功', 0, array(array('text'=>'继续编辑','href'=>'campaign/edit/'.$campaign_id), array('text'=>'返回列表','href'=>'campaign/index')));
	}

	public function delete($campaign_id)
	{
                auth(array('campaign_edit'));
		$campaign_id = intval($campaign_id);
		$test = $this->input->post('test');
		$campaign = $this->campaign_model->filter(array('campaign_id'=>$campaign_id));
		if (empty ($campaign)) {
			sys_msg('记录不存在', 1);
		}
		if($test) sys_msg('');
		$this->campaign_model->delete($campaign_id);
		sys_msg('操作成功', 0, array('text'=>'返回列表', 'href'=>'campaign/index'));		
	}


}
###
