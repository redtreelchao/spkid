var $$ = Dom7;
var isEnter = false;
var fv, ft, sv, ft;
window.search_interval = null;
function unique(arr) {
    var result = [], hash = {};
    for (var i = 0, elem; (elem = arr[i]) != null; i++) {
        if (!hash[elem]) {
            result.push(elem);
            hash[elem] = true;
        }
    }
    return result;
}

function jump2SearchResult(kw) {

  if (localStorage) {
    var history = localStorage.getItem('search_history');
    if(!history || history.indexOf(kw) == -1){
      var str = !history ? kw : history + '+++' + kw;

      localStorage.setItem('search_history', str);
    }
    
  };  
  location.href = '/searchResult?kw=' + kw;
  
}

//热搜 只针对产品搜索
$$(document).on('click', '.hotword .button', function (e) { 
  jump2SearchResult($$(this).text());
});

//将搜素历史展示出来$$

function showSearchHistory() {
  if (localStorage) {
    var hotWords = localStorage.getItem('search_history');
    if (!hotWords) {
      //当前没有搜索历史
      return;
    };
    hotWords = hotWords.split('+++');     
    var str = '';
    if (hotWords.length) {        
      for (var i = 0; i <= hotWords.length - 1; i++) {
        str += '<div class="order-details-rr historical-list"><a href="#" class="historical-wz">' + hotWords[i] + '</a><a href="#'+i+'" class="historical-search-close history-rm"></a></div>';
      };

      $$('.historical').html(str);
      // $$('.historical-lb li').on('click', function(e){
      //   jump2SearchResult($$(this).text());
      // });
    };
  };
}

showSearchHistory();

$$(document).on('click', '.history-rm', function(e){
    $$(this).parent().remove();
    var str = '';
    $$.each($$('.historical > ul > li > .history-kw'), function(index, value){
        if (index == 0) {
            str += (value.innerText ? value.innerText : '');
        } else {
            str += '+++' + (value.innerText ? value.innerText : '');
        }
    });
    if (localStorage) {
        localStorage.setItem('search_history', str);
    };

});

$$(document).on('click', '.searchResult li', function(e){
    jump2SearchResult($$(this).find('.keyword').text());
});

// $$(document).on('click', '.item-after', function(e){
//   alert($$(this).prev('.item-title').text());
// })

$$(document).on('click', 'a.historical-wz', function(e){
  jump2SearchResult($$(this).text());
});

$$('.clear-history-but').on('click', function(e){
  if (localStorage) {
    localStorage.setItem('search_history', '');
    $$('.historical').html('');
  };
});

function confirm_search() {
  if (!$$('input[type="search"]').val()) {
    myApp.alert('搜索内容不能为空');
    return;
  };
  isEnter = true;
  $$('input[type="search"]').blur();    
  jump2SearchResult($$('input[type="search"]').val());
}
$$(document).on('keydown', 'input[type="search"]', function(e){
  if (e && e.keyCode == 13) {
    confirm_search();
  } else {
    isEnter = false;
  }
});

var isFinished = false;

var searchAjax = function(kw){
  if (isEnter) {
    console.log('user entered');
    return;
  };

  
  isFinished = true;
  console.log('set isFinished to true');
  var pinyin = kw;//ConvertPinyin(kw);
  if (pinyin == '') {
   $$('.searchResult').html('');
    return;
  };

  var _this = this;
  $$.ajax({
    url:'/product/autoComplete',
    type:'POST',
    data:'data=' + pinyin,
    success:function(data){
      if (data == '') return;    
      
      $$('.searchResult').html(data); 
             
    },
    error:function() {
      console.log('no data');
    }
  });
}
var x = 0;
function checkInputFinished(kw) {
  if (x == 3) {x = 0} else {++x};
  console.log(x + ' ' + kw);
}


$$(document).on('input propertychange', 'input[type="search"]', function(e){ 
  if (isEnter) {return};
  $$('.searchResult').html('');  

  searchAjax($$(this).val())
  
});

$$('.search_confirm').on('click', function(e){
  confirm_search();
})
