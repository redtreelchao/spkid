<?php 
/**
* Index Controller
*/
class Index extends CI_Controller
{
	
	function __construct()
	{
		parent::__construct();
		$this->user_id = $this->session->userdata('user_id');
		$this->time = date('Y-m-d H:i:s');
        $this->load->vars('page_title','');//这里设置空title即可
	}

    /**
     * @param:导航id
     */
    public function index($nav_id=0)
    {
       $this->output->cache(CACHE_HTML_INDEX);
         
        $this->load->library('memcache');
        $this->load->library('lib_ad');
        $this->load->model('product_model');
        $this->config->load('global', true);
        $data = array();
        $data['hot_brand_arr'] = array();        
        $data['cate_toptwentyfive_goods_arr'] = array();        
        $data['provider_goods'] = array();        
        
        //导航
        $nav_list = get_nav();
        
        //导航下方广告位
        $nav_footer_ad = $this->_get_ad('index_nav_footer_ad',INDEX_NAV_FOOTER_TAG);
        if(!empty($nav_footer_ad))$data['nav_footer_ad']=$nav_footer_ad[0];        
        
        // 轮播图
        $front_focus_image = $this->lib_ad->get_focus_image(INDEX_FOCUS_IMAGE_TAG);
        foreach ($front_focus_image as $k => $row) {
            $front_focus_image[$k]['img_src'] = img_url($row['img_src']);
        }
        
        //今日促销
        $onsale_last_goods = $this->memcache->get('onsale_last_goods');
        if ($onsale_last_goods != FALSE) {
            $onsale_last_goods_arr = unserialize($onsale_last_goods);
            $today_promotions = array();
            foreach ($onsale_last_goods_arr as $k => $val) {
                $today_promotions[$k] = array('index' => 0, 
                    'gd_nm' => $val['product_name'], 
                    'gd_no' => $val['product_id'], 
                    'div_price_strong' => $val['shop_price']."元", 
                    'img_src' => img_url($val['img_url'].".140x140.jpg"), 
                    'href' => "/product-".$val['product_id'].".html");                
            }
            $data['today_promotions'] = json_encode($today_promotions);
        }
        
        //麦麦团 最近热卖
        $all_topfive_goods = $this->memcache->get('all_topfive_goods');
        if ($all_topfive_goods != FALSE) {
            $all_topfive_goods_arr = unserialize($all_topfive_goods);
            $hot_sales = array();
            foreach ($all_topfive_goods_arr as $k => $val) {
                $hot_sales[$k] = array('index' => 0, 
                    'gd_nm' => $val['product_name'], 
                    'gd_no' => $val['product_id'], 
                    'div_price_strong' => $val['shop_price']."元", 
                    'img_src' => !empty($val['img_url']) ? img_url($val['img_url'].".140x140.jpg") : '', 
                    'href' => "/product-".$val['product_id'].".html");                
            }
            $data['hot_sales'] = json_encode($hot_sales);
        }       
        
        //商品分类
        
        //商品分类右下角广告位
        $category_top_ad = $this->_get_ad('index_category_top_ad',INDEX_CATEGORY_TOP_TAG);
        if(!empty($category_top_ad))$data['category_top_ad']=$category_top_ad[0];
        
        $category_footer_ad = $this->_get_ad('index_category_footer_ad',INDEX_CATEGORY_FOOTER_TAG);
        if(!empty($category_footer_ad))$data['category_footer_ad']=$category_footer_ad[0];
        
        
        //热卖品牌
        $hot_brand = $this->memcache->get('all_topeight_brand');
        if ($hot_brand != FALSE) {
            $data['hot_brand_arr'] = array_slice(unserialize($hot_brand), 0, 4);
        }
        //通知
        $notice=$this->cache->get('index-notice');
        if(empty($notice))
        {
            $this->load->model('article_model');
            $notice = $this->article_model->all_article(array('cat_id'=>INDEX_ARTICEL_CAT_ID,'is_use'=>1,'limit'=>3));
            $this->cache->save('index_notice',$notice,CACHE_TIME_ARTICLE);
        }
		
		$hot_goods = array();//畅销精品商品
		$recommend_goods = array();//热卖推荐商品
        foreach ($nav_list as $cate) {        
			//畅销精品
			$cate_toptwentyfive_goods = $this->memcache->get('cate_toptwentyfive_goods_'.$cate['category_id']);
			if ($cate_toptwentyfive_goods != FALSE) {
				$cate_toptwentyfive_goods_arr = unserialize($cate_toptwentyfive_goods);
				//print_r($cate_toptwentyfive_goods_arr);
                                $i = 0;
				$hot_goods_tmp = array();
				$recommend_goods_tmp = array();
				foreach ($cate_toptwentyfive_goods_arr as $row) { 
                                    $percent = 0;
                        
			            if ($row['market_price'] > $row['shop_price']) $percent = intval(($row['market_price'] - $row['shop_price'])/$row['market_price']*100);
                                    //echo  "(".$row['market_price']." - ".$row['shop_price'].")/".$row['market_price']." =  ". $percent."<br>";

				    if ($i < 20) {
					$rows = array('index' => $i, 
						  'gd_nm' => $row['product_name'], 
						  'gd_no' => $row['product_id'], 
						  'price' => $row['shop_price']."元", 
						  'img_src' => img_url($row['img_url'].".175x175.jpg"), 
						  'group_code' => 0, 
						  'href' => '/product-'.$row['product_id'].'.html', 
						  'percent' => $percent."% OFF");


				        $hot_goods_tmp[] = $rows;
			            } else {
					$rowts = array('index' => $i-20, 
						      'gd_nm' => $row['product_name'], 
						      'gd_no' => $row['product_id'],
                                                      'percent' => $percent."% OFF",
						      'currency_prise' => $row['shop_price']."元",
                                                      'goodsSid' => $cate['category_id'], 
						      'image' => img_url($row['img_url'].".175x175.jpg"), 
                                                      'img_contents_no' => '',
                                                      'gid' => '');

					$recommend_goods_tmp[] = $rowts;
				    }
                                    $i++;
				}
				$hot_goods[] = $hot_goods_tmp;
				$recommend_goods[] = $recommend_goods_tmp;
			}
		}
		if (!empty($hot_goods)) $data['hot_goods'] = json_encode($hot_goods);
		if (!empty($recommend_goods)) $data['recommend_goods'] = json_encode($recommend_goods);
		
        //热卖推荐类别
        $nav_alias = $this->config->item('nav_alias_list');
        foreach ($nav_list as $val){
            $nav_ids[] = $val['category_id'];
        }
        if (!empty($nav_ids)) @array_multisort($nav_ids, $nav_alias);
        //一级分类店铺
        $cate_topseven_provider = $this->memcache->get('cate_topseven_provider');//店铺
	$cate_topseven_provider_arr = Array();
        $cate_topfive_provider_goods = $this->memcache->get('cate_topfive_provider_goods');//店铺销量前五的商品
        $cate_topfive_provider_goods_arr = array();
        $provider_goods = array();
        $i = 1;
        if ($cate_topseven_provider != FALSE) {
            $cate_topseven_provider_arr = unserialize($cate_topseven_provider);
            if ($cate_topfive_provider_goods != FALSE) $cate_topfive_provider_goods_arr = unserialize($cate_topfive_provider_goods);
            $provider_list = array();
            foreach ($cate_topseven_provider_arr as $cat_id => $provider) {
                $provider_html = $this->_get_ad('index_nav_cat_'.$cat_id, 'index_nav_cat_'.$cat_id);
                if (!empty($provider_html)) $cate_topseven_provider_arr[$cat_id]['provider_html'] = $provider_html[0];
                if (!isset($cate_topfive_provider_goods_arr[$cat_id])) {
                    continue;
                }
                foreach ($cate_topfive_provider_goods_arr[$cat_id] as $pid => $goods) {
                    $goods_result = array();
                    foreach ($goods as $row) {
                        $goods_result[] = array('gd_nm' => $row['product_name'], 
                                                'gd_no' => $row['product_id'], 
                                                'price' => $row['shop_price'], 
                                                'img_src' => img_url($row['img_url'].".175x175.jpg"), 
                                                'href' => 'product-'.$row['product_id'].'.html',
                                                'seller_coupon' => 'N');
                    }
                    $provider_goods[$i][] = $goods_result;                   
                }
                $i++;
            }

            $data['provider_goods'] = $provider_goods;
        }
        $data['cate_topseven_provider_arr'] = $cate_topseven_provider_arr;
        
        //超值促销广告位
        $promotions_ad = $this->_get_ad('index_footer_promotions_ad',INDEX_FOOTER_PROMOTIONS_TAG);
        if(!empty($promotions_ad))$data['promotions_ad']=$promotions_ad[0];

        $this->load->vars(array(
            'notice'=>$notice, 
            'nav_alias' => json_encode($nav_alias), 
            'index_focus_image' => $front_focus_image, 
            'goods_type' => array()
        ));
 
        $this->load->view('index/m18',$data);
        
    }

    
    /**
     * 获取预售rush
     */
    function _get_pre_rush()
    {
        $pre_rush=$this->cache->get('pre_rush');
        if($pre_rush==false)
        {
            $this->load->model('rush_model');
            $pre_rush=$this->rush_model->pre_rush();
            if(!empty($pre_rush))
            {
                $this->cache->save('pre_rush',$pre_rush,CACHE_TIME_PRE_RUSH);
            }
        }
        if(empty($pre_rush)) 
            return array('pre_rush'=>array(),'pre_title'=>array());
        $pre_rush_arr=array();
        //按日期分组
        foreach($pre_rush as $key=>$val)
        {
            $now=date('Y-m-d H:i:s');
            //如果rush已开始 则continue
            if($val->start_date<$now){
                continue;
            }
            //rush图片处理
            $temp_img_arr=explode('.',$val->image_before_url);
            $val->image_before_url1=$temp_img_arr[0].'_1.'.$temp_img_arr[1];
            $val->image_before_url2=$temp_img_arr[0].'_2.'.$temp_img_arr[1];
            $val->image_before_url3=$temp_img_arr[0].'_3.'.$temp_img_arr[1];
            $pre_rush_arr[$val->date][]=$val;
        }
        //处理title 即明天 后天等
        $today=strtotime(date('Y-m-d'));
        $pre_rush_title=array();
        $week=array('1'=>'一','2'=>'二','3'=>'三','4'=>'四','5'=>'五','6'=>'六','0'=>'日',);
        foreach($pre_rush_arr as $key=>$val)
        {
            //计算rush date与今天时间差
            $rush_date=strtotime($key);
            $date_diff=round(($rush_date-$today)/3600/24);
            $temp=array();
            $time=strtotime($key);
            if($date_diff==0)
                $temp['title']="今天";
            elseif($date_diff==1)
                $temp['title']="明天";
            else if($date_diff==2)
                $temp['title']="后天";
            else
                $temp['title']='周'.$week[date('w',$time)];
            
            $temp['date']=date('m',$time).'/'.date('d',$time);
            $pre_rush_title[]=$temp;
        }
        $pre_rush_result=array('pre_rush'=>$pre_rush_arr,'pre_title'=>$pre_rush_title);
        return $pre_rush_result;
    }

    /**
     * 获取正在进行的限抢
     */
    function _get_sale_rush($nav_id=0)
    {
        $rushes=$this->cache->get('sale_rush_'.$nav_id);
        if($rushes==false)
        {
            $this->load->model('rush_model');
            $rushes=$this->rush_model->get_sale_rush(array('nav_id'=>$nav_id));
            if(empty($rushes)) return array();
            $this->cache->save('sale_rush_'.$nav_id,$rushes,CACHE_TIME_SALE_RUSH);
        }
        foreach($rushes as $key=>$rush)
        {
            //计算还剩几天
            $time_diff=strtotime($rush->end_date)-time();
            if($time_diff<86400)
            {
                $rush->end_day=1;
            }
            else
            {
                $rush->end_day=ceil($time_diff/86400);
            }
            if(!empty($rush->image_before_url))
            {
                $img_arr=explode('.',$rush->image_before_url);
                $rush->image_before_url_1=$img_arr[0].'_1.'.$img_arr[1];
                $rush->image_before_url_2=$img_arr[0].'_2.'.$img_arr[1];
                $rush->image_before_url_3=$img_arr[0].'_3.'.$img_arr[1];
            }
        }
        return $rushes;
    }
    
    /**
     * 获取今日结束的rush
     */
    function _get_today_over_rush()
    {
        $today_over_rush=$this->cache->get('today_over_rush');
        if($today_over_rush===false)
        {
            $this->load->model('rush_model');
            $today_over_rush=$this->rush_model->get_sale_rush(array('today'=>true));
            $this->cache->save('today_over_rush',$today_over_rush,CACHE_TIME_TODAY_OVER_RUSH);
        }
        //计算距离结束时间 以最晚的rush end_date为准
        $last_end_date='1970-01-01';
        foreach($today_over_rush as $key=>$rush)
        {
            //如果结束时间不等于今天 则unset掉
            if(date('Y-m-d')!=date('Y-m-d',strtotime($rush->end_date)))
            {
                unset($today_over_rush[$key]);
                continue;
            }
            if($rush->end_date>$last_end_date)
                $last_end_date=$rush->end_date;
            if(!empty($rush->image_before_url))
            {
                $img_arr=explode('.',$rush->image_before_url);
                $rush->image_before_url_1=$img_arr[0].'_1.'.$img_arr[1];
                $rush->image_before_url_2=$img_arr[0].'_2.'.$img_arr[1];
                $rush->image_before_url_3=$img_arr[0].'_3.'.$img_arr[1];
            }
        }
        $time_diff=strtotime($last_end_date)-time();
        $time_diff=$time_diff<0?0:$time_diff;
        $this->load->vars(array(
                    'today_over_hour'=>floor($time_diff/3600),
                    'today_over_min'=>ceil(($time_diff%3600)/60)));
        return $today_over_rush;
    }

    /**
     * 检查rush时间是否有效
     */
    function _check_rush($rushes)
    {
        if(empty($rushes))
            return $rushes;
        $new_rush=array();
        foreach($rushes as $key=>$val)
        {
            if($val->end_date<date('Y-m-d H:i:s')||$val->start_date>date('Y-m-d H:i:s'))
            {
                //排除不符合时间的rush
                continue;
            }
            array_push($new_rush,$val);
        }
        return $new_rush;
    }


    /**
     * 根据key或position_id获取广告
     */
    function _get_ad($cache_key,$position_tag)
    {
        $this->load->library('lib_ad');
        return $this->lib_ad->get_ad_by_position_tag($cache_key,$position_tag);
    }
}
