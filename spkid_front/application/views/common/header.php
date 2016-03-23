<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta http-equiv=Content-Type content="text/html;charset=utf-8">

<title><?php echo isset($title) ? $title : '爱牙网'?></title>
<meta name="Keywords" content="<?php echo isset($keywords) ? $keywords : '';?>">
<meta name="Description" content="<?php echo isset($description) ? $description : '';?>">

<link href="<?php echo static_style_url('pc/css/common.css?v=version')?>" rel="stylesheet" type="text/css">
<link href="<?php echo static_style_url('pc/css/main.css?v=version')?>" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<?php echo static_style_url('pc/js/common.js?v=version')?>"></script>
<script src="<?php echo static_style_url('pc/js/jquery.cookie.js?v=version')?>"></script>

<script type="text/javascript" src="<?php echo static_style_url('pc/js/jquery-migrate-1.2.1.min.js?v=version')?>"></script>
<script src="<?php echo static_style_url('pc/js/search.js?v=version')?>" type="text/javascript"></script>
<script src="<?php echo static_style_url('pc/js/home.js?v=version')?>" type="text/javascript"></script>
<!--[if lte IE 7]>
<div class="goodbye-modal hide"></div>
<div class="goodbye-ie hide" id="goodbyeIE">
<p>您的浏览器太旧啦~为了获得更好的体验，强烈建议您使用以下浏览器：</p>
<ul class="browers clearfix">
<li class="chrome">
<a target="_blank" href="https://www.google.com/intl/en/chrome/browser/"></a>
<span>chrome</span>
</li>
<li class="firefox">
<a target="_blank" href="http://www.firefox.com.cn/download/"></a>
<span>firefox</span>
</li>
<li class="ie9">
<a target="_blank" href="http://windows.microsoft.com/zh-cn/internet-explorer/download-ie"></a>
<span>IE9+</span>
</li>
</ul>
<p class="no-tip"><a id="iknow" href="javascript:void(0);">知道啦</a></p>
</div>
<![endif]-->
</head>

<body>
<!--nav start-->
<div id="back-to-top"><a href="#top"><span></span></a></div>  
<div class="nav-wrap">
     <div class="nav clearfix">
          <a href="/" class="logo">
            <img src="<?php echo static_style_url('pc/images/logo.png?v=version')?>">
          </a>
          <div class="nav-right">
              <div class="textbox">
                  <div class="nav-search" style="position:relative">
                  
					  <input name="" type="search" class="nav-search-input" id="navtion-input" value="" style="">
					  <ul style="" class="autocomplete">
					    	
					  </ul>
                  	<span class="search-ico search_confirm"></span>
                  </div>
              </div>
              <div class="nav-lb">
                  <div class="naver-login">
                  
                  </div>
                  <a href="/user/order_list" class="nav-order">我的订单</a>
                  <a href="/cart" class="nav-cart">购物车</a>
              </div>
          </div>
    </div>
<!--header end-->
<?php 
if(!isset($index)) $index = 0;$menus = array(array('name' => '首页', 'href' => '/'), array('name' => '全部商品', 'href' => '/category-0-0-0-0-11.html'), array('name' => '全部展品', 'href' => '/brand/lists'), array('name' => '课程表', 'href' => '/index/course.html'), array('name' => '视频', 'href' => '/video.html')
//, array('name' => '新牙医同盟会', 'href' => '#')
);?>
<!--menu start-->
    <div class="menu">
    <ul class="menu-list">
<?php 
foreach($menus as $key => $m):
    if ($index == $key):?>
        <li class="active"><a href="<?php echo $m['href']?>"><?php echo $m['name']?></a></li>
<?php else:?>
        <li><a href="<?php echo $m['href']?>"><?php echo $m['name']?></a></li>
<?php endif;endforeach?>
         </ul>
    </div>
<!--menu end-->

</div>
