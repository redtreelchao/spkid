<!--copyright-start-->
<div class="copyright clearfix">
     <div class="copyright-show clearfix">
          <div class="copyright-lb">
               <ul class="f-regular-list clearfix">
               <li class="copyright-bz">严格准入标准</li>
               <li class="copyright-tuihuo">7天无理由退货</li>
               <li class="copyright-mianfei">15天免费换货</li>
               <li class="copyright-pinpai">品牌授权</li>
               <li class="copyright-rongyu">权威荣誉</li>
               </ul>
          </div>
     </div>
     
     <div class="f-product-info min-width clearfix">
          <div class="f-product-left">
                 <div class="copyright-tel"><img src="<?php echo static_style_url('pc/images/tel.png')?>"></div>
                 <div class="f-info-inner">
                      <a target="_blank" href="/about_us/about_us">关于爱牙网</a>
                      <a target="_blank" href="/about_us/service">服务条款</a>
                      <a target="_blank" href="/about_us/feedback">意见反馈</a>
                      <a target="_blank" href="/about_us/sales_policy">售后政策</a>
                      <a target="_blank" href="/about_us/team_work">合作咨询</a>
                      <a target="_blank" href="/about_us/join_us">加入我们</a>
                </div>
          </div>
          <div class="f-focus-us">
               <div class="copyright-txt">
                    关注我们:
                    <div class="f-focus-icon">
                         <img id="wechat_qrcode" style="display:none;position: absolute; width: 120px; z-index: 99999; margin-top: -120px;" src="<?php echo static_style_url('pc/images/wechat_qrcode.jpg')?>">

                         <a class="c-weixin" target="_blank" href="javascript:void(0)" onmouseover="var wechat_qrcode = document.getElementById('wechat_qrcode'); wechat_qrcode.style.display='block';" onmouseout="var wechat_qrcode = document.getElementById('wechat_qrcode'); wechat_qrcode.style.display='none';"
                         ></a>
                         <a class="c-weibo" target="_blank" href="http://weibo.com/oswelldental"></a>
                         
                   </div>
              </div>
              <div class="phone-wx"><p>手机爱牙网</p><img src="<?php echo static_style_url('pc/images/mobile_qrcode.jpg')?>"></div>
              
              
        </div>
    
    
    </div>
     
     

</div>    
</body>
<script>
  $(function(){
    var cookies = document.cookie ? document.cookie.split('; ') : [];
    var v_user_name = '';
    var user_advar = '<?php echo static_style_url("mobile/touxiang/")?>';

    for (var i = cookies.length - 1; i >= 0; i--) {
      var item = cookies[i].split('=');
      if (item[0]=='v_user_name') {
        v_user_name = decodeURIComponent(item[1]);
      };
      if (item[0]=='v_advar') {
        user_advar = user_advar + decodeURIComponent(item[1]);
      };
    };

    if (!v_user_name) {
      $('.naver-login').html('<a href="/user/login" class="nav-user">登录</a>');
    } else {
      var str = '<img src="' + user_advar + '" height="28">';

      str += '<a href="/user/index.html" class="nav-user">' + v_user_name + '<span class="menu_tips"></a>';
      str += '<ul class="menu_items" style="display:none">';
      str += '<li><a href="/user/index.html">个人中心</a></li>';
      str += '<li><a href="/account/privilege.html">我的优惠</a></li>';
      str += '<li><a href="/collect/index.html">我的关注</a></li>';
      str += '<li><a href="/user/my_response.html">我的回复</a></li>';
      str += '<li><a href="/user/logout.html">退出</a></li></ul>';

      $('.naver-login').html(str);
    }
  });
</script>
<script>

    var is_hover = false;
    function hide_menu() {
      if (is_hover) {
        return false;
      } else {
        $('.menu_tips').removeClass('arrow_down').addClass('arrow_up');
        $('.menu_items').hide('slow');
      }
    }
 
    $(function(){
      $('#response_num').load('/user/my_response');
      $('.menu_tips').addClass('arrow_up');
      $('.nav-user').hover(
        function(){
          $('.menu_tips').removeClass('arrow_up').addClass('arrow_down');
          
          $('.menu_items').show('slow');
        },
        function(e) {
          //console.log(e.target);
          
          setTimeout("hide_menu()", 5000);
        });
      $('.naver-login').mouseover(function(){
              $('.menu_items').show();
      	$('.menu_tips').removeClass('arrow_down').addClass('arrow_up');
      });


      $('.naver-login, .menu_items').mouseout(function(){
        $('.menu_items').hide();
        
        $('.menu_tips').removeClass('arrow_up').addClass('arrow_down');
      });

      $('.autocomplete,.nav-search').mouseleave(function(){
        $('.autocomplete').empty();
      });

        if ($('[data-toggle="popover"]')){
            $('[data-toggle="popover"]').popover();
        }

    })

    
          


</script>
<?php include_once(APPPATH . "views/common/tongji.php");?>
</html>

