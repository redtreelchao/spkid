//ul輪播
$.fn.oka_slider_model = function($setting){
		
	//設定預設值
	var $defaults = {
			'type': 1,
			'animateSpeed': 300, //動畫速度
			'speed': 4000 //輪播速度
		};

	//合併變數集
	var $vars = $.extend($defaults, $setting);

	//取所有輪播物件
	var $models = $(this);

	$models.each(function(){

		//取物件
		var $this = $(this),
			$slider_box = $this.find('.slider_model_box'), //取播放區域
			$slider_box_c = $slider_box.children(), //取要播放的子物件
			$slider_box_c_length = $slider_box_c.length, //取播放物件的數量
			$slider_box_img = $slider_box.find('img'), //取播放物件的img
			$slider_box_img_length = $slider_box_img.length; //取播放物件的數量

		if( $slider_box_c_length != $slider_box_img_length || $slider_box_c_length <= 1 ){ //如果有物件沒有圖或不是圖就停止程式
			console.error('輪播物件必須是img or a包img');
			return false;
		}

		// 製作附加模組
		var _dom =''; //存物件字串

		var _list_box = '<div class="slider_list_box"><ul>';//建立右播放清單
			for( i = 0; i < $slider_box_c.length; i++ ){
				_list_box += '<li><a href="#"><div style="background-image: url('+ $slider_box_img.eq(i).attr('src') +');"></div><h5>'+ $slider_box_img.eq(i).attr('title') +'</h5><p>'+  $slider_box_img.eq(i).attr('alt') +'</p></a></li>';
			}
			_list_box += '</ul></div>';

		var _ctrl_img_box = '<div class="ctrl_img_box"><ul>'; //建立影像點點
			for( i = 0; i < $slider_box_c.length; i++ ){
				_ctrl_img_box += '<li><a href="#" style="background-image: url('+ $slider_box_img.eq(i).attr('src') +');"></a></li>\n\r';
			}
			_ctrl_img_box += '</ul></div>';
		
		var _ctrl_dot_box = '<div class="ctrl_dot_box"><ul>'; //建立點點
			for( i = 0; i < $slider_box_c.length; i++ ){
				_ctrl_dot_box += '<li><a href="#"></a></li>\n\r';
			}
			_ctrl_dot_box += '</ul></div>';

		var _ctrl_tools = '<div class="model_ctrl_tools"><a href="#" class="ctrl_left">&lsaquo;</a><a href="#" class="ctrl_right">&rsaquo;</a></div>', //建立控制按鈕
			_introduce_box = '<div class="slider_introduce_box"><h5></h5><p></p>'+ _ctrl_dot_box +'</div>', //建立左側撥放方框
			_sec_box = '<a href="#" class="slider_sec_model_box"></a>'; //建立第二組輪播視窗

		//依type加入body
		switch( $vars.type ){

			case 1:
				_dom += _ctrl_tools + '<div class="slider_bottom_box">' + _ctrl_dot_box +'</div>';
			break;

			case 2:
				_dom += '<div class="slider_bottom_box">'+ _ctrl_dot_box +'<div class="bg_box"><h5></h5></div></div>';
			break;

			case 3:
				_dom += _ctrl_tools + '<div class="slider_bottom_box"><div class="bg_box">'+ _ctrl_dot_box +'<p></p></div></div>';
			break;

			case 4:
				_dom += '<div class="slider_bottom_box">' + _ctrl_dot_box +'</div>' + _list_box;
			break;

			case 5:
				_dom += '<div class="slider_bottom_box"><div class="bg_box">'+ _ctrl_dot_box +'<p></p></div></div>' + _list_box;
			break;

			case 6:
				_dom += '<div class="slider_bottom_box"><div class="bg_box">'+ _ctrl_img_box +'</div></div>';
			break;

			case 7:
				_dom += _ctrl_tools + '<div class="slider_bottom_box"><div class="bg_box">'+ _ctrl_img_box +'</div></div>';
			break;

			case 8:
				_dom += _introduce_box;
			break;

			case 9:
				_dom += _introduce_box + _sec_box;
			break;

			case 999:
				_dom +=  _ctrl_tools + _introduce_box + _sec_box + '<div class="slider_bottom_box">' + _ctrl_dot_box + '<div class="bg_box"><h5></h5>'+ _ctrl_dot_box +'<p></p></div></div>' + _list_box;
			break;

			default:
				_dom += '<div class="slider_bottom_box">' + _ctrl_dot_box +'</div>';
		}

		$this.append(_dom); //加入模組

		//控制附加模組
		var $ctrl_tools = $this.find('.model_ctrl_tools'), //取控制按鈕組
			$sec_box = $this.find('.slider_sec_model_box'), //取第二組輪播視窗
			$bottom_box = $this.find('.bg_box'), //取下方外層方框
			$introduce_box = $this.find('.slider_introduce_box'), //取左側播放方框
			$list_box = $this.find('.slider_list_box'), //取右播放清單
			$list_box_a = $list_box.find('a'), //&a
			$ctrl_dot_box = $this.find('.ctrl_dot_box'), //取點點
			$ctrl_dot_box_a = $ctrl_dot_box.find('a'), //&a
			$ctrl_img_box = $this.find('.ctrl_img_box'), //取影像點點
			$ctrl_img_box_a = $ctrl_img_box.find('a'); //&a

		var $ctrl_btns = $list_box_a.add($ctrl_dot_box_a).add($ctrl_img_box_a),
			$ctrl_title = $introduce_box.find('h5').add($bottom_box.find('h5')), //取要更新的標題
			$ctrl_text = $introduce_box.find('p').add($bottom_box.find('p')); //取要更新的內文

		var $ctrl_left = $ctrl_tools.find('.ctrl_left').data('dir', -1), //抓左右按鈕並設定方向參數
			$ctrl_right = $ctrl_tools.find('.ctrl_right').data('dir', 1),
			$away_btns = $ctrl_left.add($ctrl_right); //取按鈕集

		var $list_box_ul = $list_box.find('ul'),  //右側清單ul
			$list_box_li = $list_box.find('li'), //右側清單選項
			$list_box_a = $list_box_li.find('a'); //右側清單連結

		var _index = 0; //紀錄播放的順序

		//如果有第二組輪播視窗就隱藏第一組		
		if( $sec_box.length ){ //如果有輪播視窗
			$slider_box_c.css('visibility','hidden');
		}

		//按下點點或影像或右側清單的按鈕
		$ctrl_btns.click(function(event){
			event.preventDefault(); //停止預設動作

			var $this = $(this), //取點點
				$this_p = $this.parent('li');

			_index = $this_p.index(); //取點點的順序
			slider(_index); //播放

		}).eq(_index).click();

		//按下左右
		$away_btns.click(function(event){
			event.preventDefault(); //停止預設動作

			var $this = $(this),
				_dir = $this.data('dir'); //取方向參數

			_index = ( _index + _dir + $slider_box_c_length ) % $slider_box_c_length; //算出第幾個要被撥放
			slider(_index); //播放
		});

		//設定輪播涵式
		function slider(_index){

			var	$slider_dom = $slider_box_c.eq(_index), //存要播放的物件與資料
				$slider_img = $slider_box_img.eq(_index), //存要播放的img
				_href = $slider_dom.attr('href') || null;

			if( $list_box.length ){ //如果有$list_box
				var $list_box_h = $list_box.height() - $bottom_box.height(), //播放的眶
					$list_box_ul_h = $list_box_ul.height(), //清單的高
					$list_box_slider_h = $list_box_ul_h + $list_box_li.outerHeight(true) - $list_box_h, //右側清單的高
					$list_box_item = $list_box_li.eq(_index), ///右側清單的播放項目
					_top = $list_box_item.position().top; //&離父元素的高

				if( $list_box_h > $list_box_ul_h ){ //如果框比撥放清單還高
					_top = 0;
				}else if( _top > $list_box_slider_h ){
					for( i = 1; $list_box_li.eq(i).position().top <= $list_box_slider_h; i++ ) //算出最大的_top
					_top = $list_box_li.eq(i).position().top;
				}

				$list_box_ul.stop().animate({ //讓右側清單選項滑動
					'margin-top': -1 * _top
				},$vars.animateSpeed);
			}

			$ctrl_title.text($slider_img.attr('title')); //更新title
			$ctrl_text.text($slider_img.attr('alt')); //更新text
			$sec_box.hide().attr('href',_href).css('background-image','url("'+ $slider_img.attr('src') +'")').fadeIn($vars.animateSpeed);

			$slider_dom.fadeIn($vars.animateSpeed).siblings().hide(); //show出圖片並把其他隱藏

			$list_box_a.removeClass('is_active').eq(_index).addClass('is_active');
			$ctrl_dot_box_a.removeClass('is_active').eq(_index).addClass('is_active');
			$ctrl_img_box_a.removeClass('is_active').eq(_index).addClass('is_active');
		}
		
		//設定計時器
		var timer;

		//設定自動撥放涵式
		function auto(){
			_index = ( _index + 1 + $slider_box_c_length) % $slider_box_c_length; //算出第幾個要被撥放
			$ctrl_btns.eq(_index).click(); //播放

			timer = setTimeout(auto,$vars.speed);
		}

		//設定滑進滑出$this項目
		$this.mouseover(function(){
			clearTimeout(timer);
		});

		$this.mouseout(function(){
			timer = setTimeout(auto,$vars.speed);
		});

		//輪播開始
		timer = setTimeout(auto,$vars.speed);
	});
}