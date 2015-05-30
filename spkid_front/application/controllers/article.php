<?php

#doc
#	classname:	Article
#	scope:		PUBLIC
#
#/doc

class Article extends CI_Controller
{

	function __construct ()
	{
		parent::__construct();
		$this->time = date('Y-m-d H:i:s');
		$this->user_id = $this->session->userdata('user_id');
		$this->load->model('article_model');
	}
	public function index ()
	{
		redirect('index');
	}

	public function info($article_id)
	{
		$this->load->library('memcache');
		$article_id = intval($article_id);
		if(($article=$this->memcache->get('artilce_'.$article_id))==FALSE){
			$article = $this->article_model->filter(array('article_id'=>$article_id));
			if(!$article) sys_msg('文章不存在',1);
			$article->cat=$this->article_model->filter_cat(array('cat_id'=>$article->cat_id));
			//路径替换
			$article->content = adjust_path($article->content);
			$this->memcache->save('artilce_'.$article_id,$article,CACHE_TIME_ARTICLE);
		}
		if($article->url) {
			redirect($article->url);
			return;
		}
                if ( ($all_cat = $this->memcache->get('all_article_cat'))==FALSE )
		{
			$all_cat = $this->article_model->all_cat(array('parent_id'=>ARTICLE_CAT_SPEC,'is_use'=>1));
			$this->memcache->save('all_article_cat',$all_cat,CACHE_TIME_ARTICLE);
		}		
		$cat_ids = array();
		foreach( $all_cat as &$cat )
		{
			$cat_ids[] = $cat->cat_id;
			$cat->article_list = array();
		}
		$all_cat = index_array($all_cat,'cat_id');		
		if(!in_array($article->cat_id,$cat_ids)) sys_msg('文章不存在',1);
		
		if ( ($all_article = $this->memcache->get('all_article'))==FALSE )
		{
			$all_article = $this->article_model->all_article(array('cat_id'=>$cat_ids,'is_use'=>1));
			$this->memcache->save('all_article',$all_article,CACHE_TIME_ARTICLE);
		}
		
		foreach($all_article as $a)
		{
			if(!isset($all_cat[$a->cat_id])) continue;
			$all_cat[$a->cat_id]->article_list[] = $a;
		}
		$this->load->vars(array(
			'article' => $article,
			'all_cat' => $all_cat,
			'title' => $article->title,
			'keywords' => $article->keywords,
			'description' => "{$article->title} {$article->keywords}"
		));
                $this->load->view('article/index');       
//		if($article->cat->parent_id==ARTICLE_CAT_SPEC){
//			$this->load->view('article/info_spec');
//		}elseif($article->cat->parent_id==ARTICLE_CAT_HELP){
//			redirect("help-{$article_id}");
//		}else{
//			$this->load->view('article/info');
//		}
		
	}
	
	public function help ($article_id)
	{
		$this->load->library('memcache');
		$article_id = intval($article_id);
		if(($article=$this->memcache->get('artilce_'.$article_id))==FALSE){
			$article = $this->article_model->filter(array('article_id'=>$article_id));

			if(!$article||!$article->is_use) sys_msg('文章不存在',1);
			//路径替换
			$article->content = adjust_path($article->content);
			$this->memcache->save('artilce_'.$article_id,$article,CACHE_TIME_ARTICLE);
		}
		if ( ($all_cat = $this->memcache->get('all_help_cat'))==FALSE )
		{
			$all_cat = $this->article_model->all_cat(array('parent_id'=>ARTICLE_CAT_HELP,'is_use'=>1));
			$this->memcache->save('all_help_cat',$all_cat,CACHE_TIME_ARTICLE);
		}		
		$cat_ids = array();
		foreach( $all_cat as &$cat )
		{
			$cat_ids[] = $cat->cat_id;
			$cat->article_list = array();
		}
		$all_cat = index_array($all_cat,'cat_id');		
		if(!in_array($article->cat_id,$cat_ids)) sys_msg('文章不存在',1);

		if ( ($all_article = $this->memcache->get('all_help_article'))==FALSE )
		{
			$all_article = $this->article_model->all_article(array('cat_id'=>$cat_ids,'is_use'=>1));
			$this->memcache->save('all_help_article',$all_article,CACHE_TIME_ARTICLE);
		}
		
		foreach($all_article as $a)
		{
			if(!isset($all_cat[$a->cat_id])) continue;
			$all_cat[$a->cat_id]->article_list[] = $a;
		}
		$this->load->vars(array(
			'article' => $article,
			'all_cat' => $all_cat,
			'title' => $article->title,
			'keywords' => $article->keywords,
			'description' => "{$article->title} {$article->keywords}"
		));
		$this->load->view('article/help');
	}

}
