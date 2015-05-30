<?php
/**
 * 首页焦点图controller
 */
class Front_focus_image extends CI_Controller
{
    function __construct()
    {
        parent::__construct();
        $this->admin_id = $this->session->userdata('admin_id');
		if ( ! $this->admin_id )
		{
			redirect('index/login');
		}
        $this->load->model('front_focus_image_model');
    }

    function index($focus_type=1)
    {
        auth('focus_image_view');
        $list=$this->front_focus_image_model->all(array('focus_type'=>$focus_type));
        $data['list']=$list;
		$data['search_type']=$focus_type;
        //取定义的焦点图类别并执行
        eval(FOCUS_TYPE);
        $data['focus_type']=$focus_type;
        $data['full_page']=true;
        $this->load->view('front_ad/focus_image_list',$data);
    }

    function add()
    {
        auth('focus_image_add');
        //取定义的焦点图类别并执行
        eval(FOCUS_TYPE);
        $this->load->view('front_ad/focus_image_add',array('focus_type'=>$focus_type));
    }

    function proc_add()
    {
        auth('focus_image_add');
        $focus_type=$this->input->post('focus_type');
        $focus_name=$this->input->post('focus_name');
        $focus_url=$this->input->post('focus_url');
        if(empty($focus_type)||empty($focus_name)||empty($focus_url))
        {
            sys_msg('请填写完整信息',1);
        }
        //上传图片
        $this->load->library('upload');
        $base_path = FRONT_FOCUS_IMAGE_PATH;
        $sub_dir=date('Y');//以年份分目录
        if(!file_exists($base_path)) mkdir($base_path);
        if(!file_exists($base_path.'/'.$sub_dir)) mkdir($base_path.'/'.$sub_dir);

        $this->upload->initialize(array(
                'upload_path' => $base_path.'/'.$sub_dir,
                'allowed_types' => 'gif|jpg|png',
                'encrypt_name' => TRUE
        ));
        $update=array();
        if($this->upload->do_upload('focus_image')){
                $file = $this->upload->data();
                $update['focus_image'] = FRONT_FOCUS_IMAGE_DIR.'/'.$sub_dir.'/'.$file['file_name'];
        }
        if(!empty($update))
        {
            //添加
            $data=array('focus_name'=>$focus_name,'focus_url'=>$focus_url,'focus_img'=>$update['focus_image'],
                        'focus_order'=>0,'focus_type'=>$focus_type,'create_admin'=>$this->admin_id,
                        'create_date'=>date('Y-m-d H:i:s'));
            $this->front_focus_image_model->insert($data);
            sys_msg('操作成功', 0, array(array('text'=>'返回列表','href'=>base_url().'front_focus_image/index')));
        } 
    }

    function update_focus()
    {
        auth('focus_image_edit');
        $focus_id=$this->input->post("focus_id");
        $focus_order=$this->input->post("focus_order");
        $focus_start_time=$this->input->post("focus_start_time");
        $focus_end_time=$this->input->post("focus_end_time");
        foreach($focus_id as $key=>$val)
        {
            $this->front_focus_image_model->update(array('focus_order'=>$focus_order[$key],
                            'start_time'=>$focus_start_time[$key],
                            'end_time'=>$focus_end_time[$key]),$val);
        }
        redirect('front_focus_image/index');
    }

    /**
     * 更新首页焦点图静态html
     */
    function update_focus_image_html($focus_type=1)
    {
        auth('focus_image_edit');
        $list=$this->front_focus_image_model->filter(array('focus_order >'=>0,'focus_type'=>$focus_type,
                    'end_time >'=>date('Y-m-d H:i:s')));
        $html=$this->load->view('front_ad/front_focus_image_html',array('list'=>$list),true);
        //echo 'aaa'.$html;die();
        $this->load->helpers('file');
        if($focus_type==1){
            if(!write_file(FRONT_FOCUS_IMAGE_HTML,$html))
            {
                sys_msg('生成首页焦点图失败',1);
            }
            //生成成功则清除memcache缓存
            file_get_contents(FRONT_URL.'/clear/todo/2/'.FRONT_FOCUS_IMAGE_HTML_KEY);
        }
        else
        {
            if(!write_file(TUAN_FRONT_FOCUS_IMAGE_HTML,$html))
            {
                sys_msg('生成团购首页焦点图失败',1);
            }
            //生成成功则清除memcache缓存
            file_get_contents(FRONT_URL.'/clear/todo/2/'.TUAN_FRONT_FOCUS_IMAGE_HTML_KEY);
        }
        sys_msg('操作成功', 0, array(array('text'=>'返回列表','href'=>base_url().'front_focus_image/index')));
    }

    function delete($id=0)
    {
        $this->front_focus_image_model->delete($id);
        redirect('/front_focus_image/index');
    }
   }
?>
