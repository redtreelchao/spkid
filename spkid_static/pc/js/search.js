
function confirm_search() {
  if (!$('input[type="search"]').val()) {
    alert('搜索内容不能为空');
    return;
  };
  jump2SearchResult($('input[type="search"]').val());
}

$(document).on('keyup', '#navtion-input', function (e){
  if (e && e.keyCode == 13) {
      confirm_search();
  }
});

$(document).on('click','.search_confirm', function(){
    confirm_search();
});

function jump2SearchResult(kw) {
    location.href = '/search/index?kw=' + kw;
}

//热搜 只针对产品搜索
$(document).on('click', '.autocomplete li', function (e) { 
  jump2SearchResult($(this).text());
});


var searchAjax = function(kw){
  if (kw == '') {
  $('.autocomplete').html('');
    return;
  };

  var _this = this;
  $.ajax({
    url:'/product/autoComplete',
    type:'POST',
    data:'data=' + kw,
    success:function(data){
      if (data == '') return;    
      
      $('.autocomplete').html(data); 
             
    },
    error:function() {
      console.log('no data');
    }
  });
}

$(document).on('input propertychange', 'input[type="search"]', function(e){ 
  
  $('.autocomplete').html('');  

  searchAjax($(this).val())
  
});


//当滚动条的位置处于距顶部100像素以下时，跳转链接出现，否则消失  
    $(function () {  
        $(document).scroll(function(){  
            if($(window).scrollTop()>500){   
                $("#back-to-top").fadeIn(1500);  
            }else{  
                $("#back-to-top").fadeOut(1500);  
            }  
        });  

        //当点击跳转链接后，回到页面顶部位置  

        $("#back-to-top").click(function(){  
            $('body,html').animate({scrollTop:0},1000);  
            return false;  
        });  
    });  