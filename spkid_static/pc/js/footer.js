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