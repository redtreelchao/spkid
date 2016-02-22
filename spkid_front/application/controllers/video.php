<?php

class Video extends CI_Controller
{

    public function index()
    {
        $this->load->model('wordpress_model');
        $data = array();
        $data['video1'] = $this->wordpress_model->fetch_videos(133);
        $data['video2'] = $this->wordpress_model->fetch_videos(206);
        $data['video3'] = $this->wordpress_model->fetch_videos(134);
        $data['video4'] = $this->wordpress_model->fetch_videos(135);
        $data['video5'] = $this->wordpress_model->fetch_videos(132);
        $data['hot'] = $this->wordpress_model->hot2new_videos('hot');
        $data['new'] = $this->wordpress_model->hot2new_videos('new');
        $data['categorys'] = array('口内牙周', '口外种植', '口腔正畸', '管理洽谈', '美学复位');
        $this->load->library('lib_ad');
        $data['focus_image'] = $this->lib_ad->get_focus_image(VIDEO_FOCUS_IMAGE_TAG, 2);

        $data['index'] = 4;
        // 这里获取动态的seo
        $this->load->library('lib_seo');
        $seo = $this->lib_seo->get_seo_by_pagetag('pc_video_index', array());
        $data = array_merge($data, $seo);        
        $this->load->view('video/index', $data);
    }
    public function upload(){
        $this->load->view('video/upload');
    }

    public function proc_upload(){
        $this->load->library('upload');
        $data = array();
        $data['post_title'] = $this->input->post('title');
        $data['post_content'] = $this->input->post('content');
        $data['post_author'] = 5;
        $base_path = VIDEO_COVER_PATH;

        // 上传到的路径
        $upload_path = $base_path . '/wp_img/'.date('Y/m');
        if (!is_dir($upload_path))
            mkdir($upload_path, 0777, true);

        $this->upload->initialize(array(
            'upload_path' => $upload_path,
            'allowed_types' => 'gif|jpg|png|jpeg',
            'encrypt_name' => TRUE
        ));
        $extra = array('intro' => $this->input->post('desc'));

        if($this->upload->do_upload('cover')){
            $file = $this->upload->data();
            $extra['cover'] = img_url($file['file_name']);
            $ext = substr(strrchr($file['file_name'], "."), 1);
            if ('jpg' == $ext || 'jpeg' == $ext) {
                $extra['mime_type'] = 'image/jpeg';
            } else {
                $extra['mime_type'] = 'image/'.$ext;
            }

            $this->load->model('wordpress_model');
            $res = $this->wordpress_model->insert_video($data, $extra);
        } else {
            $res = false;
        }        
        echo json_encode(array('uploaded' => $res));

    }

    public function detail($id) {

        $this->load->model('wordpress_model');
        $article_detail = $this->wordpress_model->get_article_detail($id);
        if (false === $article_detail)
        die('视频不存在!');


    // 处理视频,可以放大视频
	$article_detail->post_content = $this->deal_video_content( $article_detail->post_content );

        //获取文章的点赞数量
        $article_praise_num = $this->wordpress_model->article_praise_num($id);

        //print_r($article_detail);
        $tags = $article_detail->tags;
        $tagArr = explode('&', $tags);
        $arr = array();
        foreach($tagArr as $tag){
            $tag = explode('=', $tag);
            list($name, $value, $cid) = $tag;
            //echo $name, $cid, ' ';
            /*if (!isset($$name)){
                $$name = array($cid => $value);
            } else*/
                $arr[$name][$cid] = $value;
        }
        foreach($arr as $name => $value){
            $$name = $value;
        }
        if (empty($post_tag)){
            $post_tag = '';
        } else{
            $post_tag = implode('&nbsp;', $post_tag);
        }
        $views = $this->wordpress_model->get_article_views($id);
        $prev = $this->wordpress_model->get_sibing_id($id, '<');
        $next = $this->wordpress_model->get_sibing_id($id, '>');
        
        if (isset($category)){
            $cids = array_keys($category);
            $relative_articles = $this->wordpress_model->get_relative_articles($cids, $id);
        } else{
            $relative_articles = false;
        }
        $category = implode('&nbsp;', $category);
        //$sql = "SELECT term_id FROM wp_terms WHERE name = '$category'";

        //$tags = parse_str($tags, $tagArr);
        //print_r($post_tag);
        $this->load->library('lib_seo');
        $seo = $this->lib_seo->get_seo_by_pagetag('pc_video_detail', array(
                                'video_name' => $article_detail->post_title                         
                                ));

        //获取热门视频
        $this->load->model('wordpress_model');
        $hotvideos = $this->wordpress_model->get_hot_videos();

        $this->load->vars(
            array(
                'title'     => $seo['title'],           
                'description' => $seo['description'],
                'keywords'  => $seo['keywords'],
                'article' => $article_detail,
                'tag' => $post_tag, 
                'category' => $category,
                'views' => $views,
                'prev' => $prev,
                'next' => $next, 
                'relative_articles' => $relative_articles,                
                'title' => $seo['title'],
                'collect_data' => get_collect_data(),
                'praise_data' => get_praise_data(),
                'article_praise_num' => $article_praise_num->praise_num,
                //'user_name' => $this->session->userdata('user_name'),
                'hotvideos' => $hotvideos
                )
            );
        $this->load->view('video/detail');
    }
// 处理视频内容
	private function deal_video_content( $c ){
		$search = array( '/369/', '/276.75/');
		$replace= array( '882', '488' );
		$c = preg_replace( $search, $replace, $c );
		// 只保留iframe
		$search = '/<iframe.*iframe>/';
		if( preg_match( $search, $c, $matches ) ){
			$c = $matches[0];

		}
		return '<p>'.$c.'</p>';

	}

}
