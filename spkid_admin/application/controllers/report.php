<?php
#doc
#	classname:	Index
#	scope:		PUBLIC
#
#/doc
class Report extends CI_Controller
{
    public function __construct ()
    {
            parent::__construct();
            $this->admin_id = $this->session->userdata('admin_id');
            if ( ! $this->admin_id )
            {
                    redirect('index/login');
            }
            $this->load->model('report_model');
    }

    public function finance_report()
    {
    	auth(array('finance_invoicing_su_report','finance_invoicing_de_report'));
    	$this->load->helper('perms_helper');
        $this->load->vars('perms' , get_finance_perm());
		$all_list = $this->report_model->all_finance();
        $this->load->vars('list', $all_list);
        $this->load->vars('report_type', '财务报表');
        $this->load->view('report/report_list');
    }

    public function depot_report()
    {
    	auth(array('invoicing_summary_report','invoicing_details_report'));
    	$this->load->helper('perms_helper');
        $this->load->vars('perms' , get_depot_perm());
		$all_list = $this->report_model->all_depot();
        $this->load->vars('list', $all_list);
        $this->load->vars('report_type', '经销存报表');
        $this->load->view('report/report_list');
    }

    public function finance_invoicing_de_report()
    {
		auth('finance_invoicing_de_report');
        $this->load->helper('perms_helper');
        $this->load->vars('perms' , get_finance_perm());
        $filter = $this->uri->uri_to_assoc(3);
        $category_id = $this->input->post("category_id");
        if(!empty($category_id)) $filter['category_id'] = $category_id;
        $brand_id = $this->input->post("brand_id");
        if(!empty($brand_id)) $filter['brand_id'] = $brand_id;
        $product_sn = $this->input->post("product_sn");
        if(!empty($product_sn)) $filter['product_sn'] = $product_sn;
        $keyword = $this->input->post("keyword");
        if(!empty($keyword)) $filter['keyword'] = $keyword;
        $color_id = $this->input->post("color_id");
        if(!empty($color_id)) $filter['color_id'] = $color_id;
        $size_id = $this->input->post("size_id");
        if(!empty($size_id)) $filter['size_id'] = $size_id;
        $cooperation_id = $this->input->post("cooperation_id");
        if(!empty($cooperation_id)) $filter['cooperation_id'] = $cooperation_id;
        $depot_id = $this->input->post("depot_id");
        if(!empty($depot_id)) $filter['depot_id'] = $depot_id;
        //$provider_id = $this->input->post("provider_id");
        //if(!empty($provider_id)) $filter['provider_id'] = $provider_id;

        $start_time = $this->input->post("start_time");
        if(!empty($start_time)) $filter['start_time'] = $start_time;
        $end_time = $this->input->post("end_time");
        if(!empty($end_time)) $filter['end_time'] = $end_time;

        //$filter = get_pager_param($filter);
        $data = $this->report_model->finance_invoicing_de_report($filter);
		$data['product_sn']=$product_sn;
		$data['keyword']=$keyword;
		$data['brand_id']=$brand_id;
		$data['category_id']=$category_id;
		$data['color_id']=$color_id;
		$data['size_id']=$size_id;
		$data['start_time']=$start_time;
		$data['end_time']=$end_time;
		$data['cooperation_id']=$cooperation_id;
		$data['depot_id']=$depot_id;
		//$data['provider_id']=$provider_id;

        $this->load->vars('cat_list',$this->report_model->cat_list(0,$category_id));
        $this->load->vars('brand_list',$this->report_model->brand_list());
        $this->load->vars('color_list',$this->report_model->color_list());
        $this->load->vars('size_list',$this->report_model->size_list());
        $this->load->vars('coop_list',$this->report_model->coop_list());
        $this->load->vars('depot_list',$this->report_model->depot_list());
        $this->load->view('report/finance_invoicing_de_report', $data);
    }

    public function finance_invoicing_su_report()
    {
		auth('finance_invoicing_su_report');
        $this->load->helper('perms_helper');
        $this->load->vars('perms' , get_finance_perm());
        $filter = $this->uri->uri_to_assoc(3);
        $category_id = $this->input->post("category_id");
        if(!empty($category_id)) $filter['category_id'] = $category_id;
        $brand_id = $this->input->post("brand_id");
        if(!empty($brand_id)) $filter['brand_id'] = $brand_id;
        $cooperation_id = $this->input->post("cooperation_id");
        if(!empty($cooperation_id)) $filter['cooperation_id'] = $cooperation_id;
        $depot_id = $this->input->post("depot_id");
        if(!empty($depot_id)) $filter['depot_id'] = $depot_id;

        $start_time = $this->input->post("start_time");
        if(!empty($start_time)) $filter['start_time'] = $start_time;
        $end_time = $this->input->post("end_time");
        if(!empty($end_time)) $filter['end_time'] = $end_time;

        //$filter = get_pager_param($filter);
        $rclist = $this->report_model->finance_invoicing_su_report($filter);
        $count_arr = $this->report_model->finance_invoicing_su_report_count($rclist);

		$data['brand_id']=$brand_id;
		$data['category_id']=$category_id;
		$data['start_time']=$start_time;
		$data['end_time']=$end_time;
		$data['cooperation_id']=$cooperation_id;
		$data['depot_id']=$depot_id;
		$data['list']=$rclist;
		$data['count']=$count_arr;


        $this->load->vars('cat_list',$this->report_model->cat_list(0,$category_id));
        $this->load->vars('brand_list',$this->report_model->brand_list());
        $this->load->vars('coop_list',$this->report_model->coop_list());
        $this->load->vars('depot_list',$this->report_model->depot_list());
        $this->load->view('report/finance_invoicing_su_report', $data);
    }

    public function invoicing_details_report()
    {
		auth('invoicing_details_report');
        $this->load->helper('perms_helper');
        $this->load->vars('perms' , get_depot_perm());
        $filter = $this->uri->uri_to_assoc(3);
        $category_id = $this->input->post("category_id");
        if(!empty($category_id)) $filter['category_id'] = $category_id;
        $brand_id = $this->input->post("brand_id");
        if(!empty($brand_id)) $filter['brand_id'] = $brand_id;
        $product_sn = $this->input->post("product_sn");
        if(!empty($product_sn)) $filter['product_sn'] = $product_sn;
        $keyword = $this->input->post("keyword");
        if(!empty($keyword)) $filter['keyword'] = $keyword;
        $color_id = $this->input->post("color_id");
        if(!empty($color_id)) $filter['color_id'] = $color_id;
        $size_id = $this->input->post("size_id");
        if(!empty($size_id)) $filter['size_id'] = $size_id;
        $cooperation_id = $this->input->post("cooperation_id");
        if(!empty($cooperation_id)) $filter['cooperation_id'] = $cooperation_id;
        $depot_id = $this->input->post("depot_id");
        if(!empty($depot_id)) $filter['depot_id'] = $depot_id;
        //$provider_id = $this->input->post("provider_id");
        //if(!empty($provider_id)) $filter['provider_id'] = $provider_id;

        $start_time = $this->input->post("start_time");
        if(!empty($start_time)) $filter['start_time'] = $start_time;
        $end_time = $this->input->post("end_time");
        if(!empty($end_time)) $filter['end_time'] = $end_time;

        //$filter = get_pager_param($filter);
        $data['list'] = $this->report_model->invoicing_details_report($filter);
        $data['count'] = $this->report_model->invoicing_details_count($data['list']);
		$data['product_sn']=$product_sn;
		$data['keyword']=$keyword;
		$data['brand_id']=$brand_id;
		$data['category_id']=$category_id;
		$data['color_id']=$color_id;
		$data['size_id']=$size_id;
		$data['start_time']=$start_time;
		$data['end_time']=$end_time;
		$data['cooperation_id']=$cooperation_id;
		$data['depot_id']=$depot_id;
		//$data['provider_id']=$provider_id;

        $this->load->vars('cat_list',$this->report_model->cat_list(0,$category_id));
        $this->load->vars('brand_list',$this->report_model->brand_list());
        $this->load->vars('color_list',$this->report_model->color_list());
        $this->load->vars('size_list',$this->report_model->size_list());
        $this->load->vars('coop_list',$this->report_model->coop_list());
        $this->load->vars('depot_list',$this->report_model->depot_list());
        $this->load->view('report/invoicing_details_report', $data);
    }

	public function invoicing_summary_report()
    {
		auth('invoicing_summary_report');
        $this->load->helper('perms_helper');
        $this->load->vars('perms' , get_depot_perm());
        $filter = $this->uri->uri_to_assoc(3);
        $category_id = $this->input->post("category_id");
        if(!empty($category_id)) $filter['category_id'] = $category_id;
        $brand_id = $this->input->post("brand_id");
        if(!empty($brand_id)) $filter['brand_id'] = $brand_id;
        $cooperation_id = $this->input->post("cooperation_id");
        if(!empty($cooperation_id)) $filter['cooperation_id'] = $cooperation_id;
        $depot_id = $this->input->post("depot_id");
        if(!empty($depot_id)) $filter['depot_id'] = $depot_id;

        $start_time = $this->input->post("start_time");
        if(!empty($start_time)) $filter['start_time'] = $start_time;
        $end_time = $this->input->post("end_time");
        if(!empty($end_time)) $filter['end_time'] = $end_time;

        //$filter = get_pager_param($filter);
        $rclist = $this->report_model->invoicing_summary_report($filter);
        $count_arr = $this->report_model->invoicing_summary_report_count($rclist);

		$data['brand_id']=$brand_id;
		$data['category_id']=$category_id;
		$data['start_time']=$start_time;
		$data['end_time']=$end_time;
		$data['cooperation_id']=$cooperation_id;
		$data['depot_id']=$depot_id;
		$data['list']=$rclist;
		$data['count']=$count_arr;

        $this->load->vars('cat_list',$this->report_model->cat_list(0,$category_id));
        $this->load->vars('brand_list',$this->report_model->brand_list());
        $this->load->vars('coop_list',$this->report_model->coop_list());
        $this->load->vars('depot_list',$this->report_model->depot_list());
        $this->load->view('report/invoicing_summary_report', $data);
    }

    public function depot_real_inventory_report()
    {
		auth('depot_real_inventory_report');
        $this->load->helper('perms_helper');
        $this->load->vars('perms' , get_depot_perm());
        $filter = $this->uri->uri_to_assoc(3);

        $category_id = $this->input->post("category_id");
        if(!empty($category_id)) $filter['category_id'] = $category_id;
        $brand_id = $this->input->post("brand_id");
        if(!empty($brand_id)) $filter['brand_id'] = $brand_id;

        $depot_id = $this->input->post("depot_id");
        if(!empty($depot_id)) $filter['depot_id'] = $depot_id;

        $location_name = $this->input->post("location_name");
        if(!empty($location_name)) $filter['location_name'] = $location_name;

        $onsale_id = ($this->input->post("onsale_id")!==FALSE)?$this->input->post("onsale_id"):-1;
        $filter['onsale_id'] = $onsale_id;

        $product_sn = $this->input->post("product_sn");
        if(!empty($product_sn)) $filter['product_sn'] = $product_sn;

        //$filter = get_pager_param($filter);
	if ( !empty($filter) )
        $data = $this->report_model->depot_real_inventory_report($filter);

		$data['brand_id']=$brand_id;
		$data['category_id']=$category_id;
		$data['location_name']=$location_name;
		$data['depot_id']=$depot_id;
		$data['onsale_id']=$onsale_id;
		$data['product_sn']=$product_sn;

        $this->load->vars('cat_list',$this->report_model->cat_list(0,$category_id));
        $this->load->vars('brand_list',$this->report_model->brand_list());
        $this->load->vars('depot_list',$this->report_model->depot_list());
        $this->load->vars('onsale_list',$this->report_model->onsale_list());
        $this->load->view('report/depot_real_inventory_report', $data);
    }

    public function merge_gather_gross_report()
    {
		auth('merge_gather_gross_report');
        $this->load->helper('perms_helper');
        $this->load->vars('perms' , get_finance_perm());
        $filter = $this->uri->uri_to_assoc(3);
        $category_id = $this->input->post("category_id");
        if(!empty($category_id)) $filter['category_id'] = $category_id;
        $brand_id = $this->input->post("brand_id");
        if(!empty($brand_id)) $filter['brand_id'] = $brand_id;

        $cooperation_id = $this->input->post("cooperation_id");
        if(!empty($cooperation_id)) $filter['cooperation_id'] = $cooperation_id;
        $provider_id = $this->input->post("provider_id");
        if(!empty($provider_id)) $filter['provider_id'] = $provider_id;

        $start_time = $this->input->post("start_time");
        if(!empty($start_time)) $filter['start_time'] = $start_time;
        $end_time = $this->input->post("end_time");
        if(!empty($end_time)) $filter['end_time'] = $end_time;

        //$filter = get_pager_param($filter);
        $data = $this->report_model->merge_gather_gross_report($filter);

		$data['brand_id']=$brand_id;
		$data['category_id']=$category_id;
		$data['start_time']=$start_time;
		$data['end_time']=$end_time;
		$data['cooperation_id']=$cooperation_id;
		$data['provider_id']=$provider_id;

        $this->load->vars('cat_list',$this->report_model->cat_list(0,$category_id));
        $this->load->vars('brand_list',$this->report_model->brand_list());
        $this->load->vars('color_list',$this->report_model->color_list());
        $this->load->vars('size_list',$this->report_model->size_list());
        $this->load->vars('coop_list',$this->report_model->coop_list());
        $this->load->vars('provider_list',$this->report_model->provider_list());
        $this->load->view('report/merge_gather_gross_report', $data);
    }
}
