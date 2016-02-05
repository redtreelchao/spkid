<?php include_once(APPPATH . "views/common/header.php");?>
<link rel="stylesheet" type="text/css" href="<?php echo static_style_url('pc/css/pdetail.css?v=version')?>">
<link rel="stylesheet" type="text/css" href="<?php echo static_style_url('pc/css/tank.css?v=version')?>">
<link rel="stylesheet" type="text/css" href="<?php echo static_style_url('pc/css/signin.css?v=version')?>">
<script src="<?php echo static_style_url('pc/js/comm_tool.js?v=version')?>"></script>
<link href="<?php echo static_style_url('pc/css/bootstrap.css?v=version')?>" rel="stylesheet" type="text/css" media="all">

<script src="<?php echo static_style_url('pc/js/bootstrap.min.js?v=version')?>" type="text/javascript">
</script>


<script>
	window.hostId = '3179';
</script>

<style>
	.product-wrapper {
		margin-top: -10px;
	}

	.video-play {
		width:100%;
		background:#242321;
	}
	.video-player {
		width:80%;
		margin:10px auto;
		text-align:center;		
	}

	.video-player > p {
		display:inline-block;
	}

	.video-info {
		padding-left:20px;
		padding-top:10px;
		color:gray;
		width: 900px;
		margin: 10px auto;
	}

	.video-info h5 {
		color:black;
	}

	.mgl1em {
		margin-left:1em;
	}

	.hot-video img {
		width: 40%;
	}

	.video-wrapper {
		border: 1px solid #E0DCDC;
		border-top: none;
	}

	.author_avator {
		display:inline-block;
		width:50px;
		height:50px;
		border-radius:50px;
		-moz-border-radius:50px;
		-o-border-radius:50px;
		-webkit-border-radius:50px;
		-ms-border-radius:50px;
		-khtml-border-radius:50px;
		background:black;
	}

	.video-info-detail > span {
		vertical-align:middle;
	}

	.product-icon-box {
		vertical-align:middle;
	}
	.hot-video ul li {
		margin-top:10px;
	}

</style>

<div class="product-wrapper">

	<div class="video-wrapper">
		<div class="video-play">
			<div class="video-player">
				<?php echo $article->post_content?>	
			</div>		
		</div>
		<div class="video-info-wrapper">
			<div class="video-info">
				<h5><?php echo $article->post_title?></h5>
				<div class="video-info-detail">
					<span class="author_avator"></span>
					<span><?php echo $article->display_name;?>(<?php echo $article->user_articles_num;?>)</span>

					<span class="mgl1em"><?php echo $article->post_date?>发布</span>

					<span class="mgl1em video-tubiao clearfix" style="display:inline-block"><span class="video-liul"><?php echo get_page_view('article',$article->post_id);?></span><span class="video-jt"><?php echo count($article->comments);?></span></span>

					<span class="product-icon-bar mgl1em">
						<span class="product-icon-box clearfix ">
							<span class="product-icon like-icon" onclick="add_to_collect (<?php echo $article->post_id;?>,4,this);"></span>
							<span class="product-icon share-icon">
								<span class="bdsharebuttonbox bdshare-button-style0-32" data-bd-bind=""><a href="javascript:void(0)" class="bds_more" data-cmd="more"><i></i>分享</a></span>
							</span>
						</span>
					</span> 
					
				</div>
			</div>
		</div>
		
	</div>
	
	<div class="product-content clearfix" style="min-height:900px">
		<div class="product-part product-comment grey-bg">
			<div class="product-part-box">
				
				<div class="product-line"></div>
				<div class="product-comment grey-bg video-comment">
					<form class="ct-form" name="ct-form" data-hostid="3179" data-committype="" data-hosttype="2">
						<div class="clearfix liuyan-content">
							<textarea name="liuyan" aria-required="true" placeholder="同学，你怎么看?"></textarea>
						</div>
						<div class="ct-submit">
							<a type="" class="btn btn-primary btn-liuyan">提交</a>
						</div>
					</form>
					<ul class="ct-list" id="ct-list-full">

						

					</ul>
				</div>
				<div class="hot-video">
					<h5>热门视频</h5>
					<ul>
						<?php foreach ($hotvideos as $k => $v):?>
							<li>
								<a href="/video/detail/<?=$v->ID?>">
									<img src="<?=$v->cover?>" alt="">
									<span style="float:right;margin-right:1em;width:50%">
									<div style="color:gray"><?php echo $v->post_title?></div>
									<div style="margin-top:25px">							
										<span class="mgl1em video-tubiao clearfix" style="display:inline-block"><span class="video-liul"><?php echo get_page_view('article',$v->ID);?></span><span class="video-jt"><?php echo $v->comment_count?></span></span>

									</div>
									</span>
								</a>
							</li>
						<?php endforeach;?>
						
					</ul>

				</div>
			</div>
		</div>
	</div>
</div>

<script>
	var tag_id = '<?php echo $article->post_id?>';
	var user_id = '<?php echo $user_id;?>';			
	$(function(){	
		
		get_liuyan(4, tag_id, 1, $('#ct-list-full'));

		$('.ct-form').find("textarea").focus(function() {
            "" === $(this).val() && ($(this).stop().animate({height: "90px"}), $(this).parent().next().show())
        });
        $('.ct-form').find("textarea").blur(function() {
            var t = $(this);
            setTimeout(function() {
                "" === t.val() && (t.stop().animate({height: "42px"}), t.parent().next().hide())
            }, 300)
        });



        $(".ct-list").delegate(".tb a", "click", function(t) {
            t.preventDefault(), t.stopPropagation();
            var i = $(this), n = i.closest("li");
            if (n.siblings().find(".ct-form textarea").val(""), n.siblings().find(".ct-form").slideUp("fast"), i.data("form"))
                i.data("form").slideToggle("fast");
            else {
                var a = $(".ct-form").first().clone(true).hide();
                a.find("textarea").css({height: "42px"}), a.find(".ct-submit").hide(), a.find("textarea").val(""), i.data("form", a), a.appendTo(n).slideDown("fast");
            }
    	})

    	$('.btn-liuyan').click(function(e){
    		var at_comment_id = 0;
    		var is_at = $(this).parents('form.ct-form').prev().find('.tb').length > 0;
    		if (is_at) {
    			at_comment_id = $(this).parents('form.ct-form').prev().find('.tb a').attr('at_comment_id');
    		};
    		
    		var form = $(this).closest('form');
    		var textarea = form.find('textarea');
    		var content, liuyan, content = liuyan = textarea.val();
    		var is_main_form = !!form.next('.ct-list').length;
		    if (!liuyan) {
		        alert('留言不能为空');
		        return false;
		    };
		    
		    $.ajax({
		        url : '/liuyan/proc_zixun',
		        type : 'POST',
		        dataType : "json",
		        data : {
		            comment_type : 1,//代表咨询
		            tag_type : 4,//代表视频
		            tag_id : tag_id,
		            comment_content : content,
		            at_comment_id: at_comment_id
		        },
		        success : function(data, status, xhr) { 
		            if (data.err == 0) {
		            	//4表示是视频
		            	//1表示是留言
		            	get_liuyan(4, tag_id, 1, $('#ct-list-full'));
		            };
					alert(data.msg,( data.err == '0' )?'恭喜':'抱歉'); 
					return false;
		        },

		        error : function(xhr, status) {
		            //alert('数据请求错误');
		            return false;
		        }
		    });
		    textarea.stop().animate({height: "42px"}), textarea.parent().next().hide();
		    textarea.val('');
		    e.preventDefault();
    		e.stopPropagation();

    	});
	});
</script>

<!-- 登陆弹层开始 -->
<div id="login-box" class="modal fade pop-box in" tabindex="-1" role="dialog" aria-hidden="true" style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
                <h4 class="modal-title">登录</h4>
            </div>
            <div class="modal-body">
            <div class="form-container">

      <form name="loginForm" class="form-signin">
        <p class="error"></p>
        <input type="tel" name="username" class="form-control" placeholder="请输入手机号码">
        <p class="error"></p>
        <input type="password" id="password" name="password" class="form-control" placeholder="请输入密码" required="">
        <div class="checkbox">
          <label for="auto_login" class="grey-text"><input class="input-checkbox" type="checkbox" value="1" name="checkout" id="auto_login">下次自动登录</label> <a class="pull-right" href="/user/forgot">忘记密码</a>
        </div>
        <div class="btn-block clearfix">
        <button class="btn btn-lg btn-blue btn-block" type="submit"><i class="fa fa-lock left"></i>登录</button>
        <p class="pull-right grey-text">还没账号?<a href="/user/register">注册</a></p>
        </div>


        <div class="horizontal"><span>可以使用以下方式登录</span></div>
        <div class="other">
          <a href="/user/qq_login" class="qq"></a>                  
          <a href="/user/weixin_login" class="weixin"></a>		  
          <a href="/user/alipay_login" class="alipay"></a>                 
          <a href="/user/xinlang_login" class="sina"></a>
        </div>
      </form>

    </div>
            </div>

        </div>
    </div>
</div>  
<script>
	$(function(){

		$('#password').on('input propertychange', function(){
		    var username = $('input[name="username"]'), psw = $('#password');
		    if (0 < username.length && 0 < psw.length){
		        $('button.disabled').removeClass('disabled').removeAttr('disabled');        
		    }
		})
		var username = $('input[name="username"]');
		username.blur(function(){
		    if ('' == username.val()){
		        username.prev().text('请输入账号');
		        $('button.disabled').attr('disabled', 'disabled');
		    } else {
		        username.prev().text('');
		    }
		})
		$('form[name="loginForm"]').on('submit', function(e){
		    e.preventDefault();
		    var psw = $('#password');    
		        
		    if ('' == psw.val()){
		        psw.prev().text('请输入密码');
		        $('button.disabled').attr('disabled', 'disabled');
		    } else if ('' != $('input[name="username"]').val()) {
		        $('button.disabled').removeClass('disabled').removeAttr('disabled');
		        //alert($(this).serialize());
		        $.ajax({url:'/user/proc_login', data:$(this).serialize(), method:'POST', dataType:'json', success:function(data){
		            if (1 == data.error){
		                //alert(data.name);
		                $('input[name='+data.name+']').prev().text(data.message);
		            } else {
		                location.reload();
		            }
		        }
		        })
		    }
		    //alert(username.val()+' '+psw.val());
		    return false;
		})
	});
</script>
<!-- 登陆弹层结束 -->

<script>
	window._bd_share_config = {
		"common": {
			"bdSnsKey": {},
			"bdText": "",
			"bdMini": "1",
			"bdMiniList": ["tsina", "qzone", "weixin", "renren", "tqq", "douban", "sqq"],
			"bdPic": "",
			"bdStyle": "0",
			"bdSize": "32",
		},
		"share": {}
	};
	with(document) 0[(getElementsByTagName('head')[0] || body).appendChild(createElement('script')).src = 'http://bdimg.share.baidu.com/static/api/js/share.js?v=86835285.js?cdnversion=' + ~(-new Date() / 36e5)];
</script>

<script>
	$(function(){
		function relayout() {
			var left = $('.video-comment').offset().left + $('.video-comment').width();
			var top2 = $('.video-comment').offset().top;
			$('.hot-video').css({
				position:'absolute',
				left: left + 'px',
				top:top2 + 'px',
				zIndex:999999,
				width:250 + 'px',
				marginLeft:'1em'
			});
		}
		
		relayout();

		$(window).resize(function(){
			relayout();
		});
	});	
</script>

<?php include_once(APPPATH . "views/common/footer.php");?>