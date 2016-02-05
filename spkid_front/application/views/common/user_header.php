<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta http-equiv=Content-Type content="text/html;charset=utf-8">
<title>个人中心-悦牙网</title>
<link href="<?php echo static_style_url('pc/css/bootstrap.css?v=version')?>" rel="stylesheet" type="text/css">
<link href="<?php echo static_style_url('pc/css/common.css?v=version')?>" rel="stylesheet" type="text/css">
<link href="<?php echo static_style_url('pc/css/main.css?v=version')?>" rel="stylesheet" type="text/css">
<link href="<?php echo static_style_url('pc/css/personal.css?v=version')?>" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<?php echo static_style_url('pc/js/common.js?v=version')?>"></script>
<script src="<?php echo static_style_url('pc/js/jquery.cookie.js?v=version')?>"></script>
<script src="<?php echo static_style_url('pc/js/bootstrap.min.js?v=version')?>"></script>
<script src="<?php echo static_style_url('pc/js/search.js?v=version')?>" type="text/javascript"></script>
<script>
  //页面加载完成执行
  $(function(){
    //给这个personal-center-left样式下的li 的a标签 绑定点击事件
    $('.personal-center-left li a').bind("click",function(){
      //移除personal-center-left样式下所有//
      $('.personal-center-left li a').removeClass('active');
      $(this).addClass('active');
    });
  });
</script>
</head>

<body>
    <!--nav start-->
    <div class="nav-wrap min-wrapper">
        <div class="nav clearfix">
            <a href="/" class="logo"><img src="<?php echo static_style_url('pc/images/logo2.png?v=version');?>" alt="悦牙网"></a>
            <div class="nav-right">
                <div class="textbox">
                    <div class="nav-search">
                        <input name="" type="search" class="nav-search-input" id="navtion-input">
                        <ul style="" class="autocomplete"></ul>
                        <span class="search-ico search_confirm"></span>
                    </div>
                  </div>
                <div class="nav-lb">
                    <div class="naver-login"></div>
                    <a href="#" class="nav-order">我的订单</a>
                    <a href="#" class="nav-cart">购物车</a>
                </div>
            </div>
        </div>
    </div>
    <!--nav end-->

    <div class="min-personal">
     <div class="home-wrapper">
          <div class="personal-center clearfix">
               <ul class="personal-center-left clearfix">
               <li><a href="/user/order_list" class="active">我的订单</a></li>
               <li><a href="/account/privilege.html">我的优惠</a></li>
               <li><a href="/account/integral.html">我的积分</a></li>
               <li><a href="/collect/index.html">我的关注</a></li>
               <li class="split"></li>
               <li><a href="/user/index.html">个人中心</a></li>
               <li><a href="#">我的评价</a></li>
               <li><a href="#">我的讨论</a></li>
               <li><a href="#">回复提醒</a></li>
               <li class="split"></li>
               <li><a href="/user/profile.html">个人信息</a></li>
               <li><a href="/user/address.html">收货地址</a></li>
               <li><a href="#">安全设置</a></li>
               </ul>  

