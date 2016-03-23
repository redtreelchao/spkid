<?php function mark_product($p = NULL) {
  if (!$p) {
    echo '';    
  }
  $star = '<div class="recommend">';
  $end = '</div>';
  $mid = '';
  if ($p->is_hot ){
    $mid = '热品';
    echo $star . $mid . $end;
  } elseif($p->is_new) {
    $mid = '热品';
    echo $star . $mid . $end;
  } elseif($p->is_zhanpin) {
    $mid = '展品';
    echo $star . $mid . $end;
  } elseif($p->is_offcode) {
    $mid = '促销';
    echo $star . $mid . $end;
  }

  echo '';
}
?>
<?php include APPPATH . 'views/common/header.php'?>

<!--home-banner-out-start-->
<div class="wrap-mian">
   <div class="clearfix">
     <div class="home-banner-out clearfix">
          
           <div class="home-banner clearfix">
                <div class="home-big-banner-out">
                       <ul class="home-banner-wrapper clearfix">
                       <?php foreach($pc_top_carousel as $k => $v):?>
                          <li class="<?php echo $k == 0 ? 'show' : 'hidden'?>"><a href="<?php echo $v['href']?>" title="<?php echo $v['title']?>" tppabs="#" target="_blank"><img src="<?php echo img_url($v['img_src'])?>"></a></li>
                       <?php endforeach;?>
                      </ul>
                       <a class="home-banner-dir-left" href="#"></a>
                       <a class="home-banner-dir-right" href="#"></a>
                    <ul class="home-banner-ico">
                    <?php for($i = 0; $i < count($pc_top_carousel); $i++):?>
                    		
                       <li class="<?php echo $i == 0 ? 'cur' : 'normal'?>"><a href="javascript:void(0)"></a></li>
                       
                   <?php endfor;?>
                  </ul>
             </div>
          </div>
          <div class="home-banner-entry">
               <div class="home-banner-e-wp">
               
                   <a href="<?php echo $pc_top_ad[0]->ad_link?>" class="home-banner-big" target="_blank"><img src="<?php echo img_url($pc_top_ad[0]->pic_url)?>"></a>
                   <a href="<?php echo $pc_top_ad[1]->ad_link?>" class="home-banner-small" target="_blank"><span class="home-banner-s-wp"><img src="<?php echo img_url($pc_top_ad[1]->pic_url)?>"></span></a>
                   <a href="<?php echo $pc_top_ad[2]->ad_link?>" class="home-banner-small home-banner-small1" target="_blank"><span class="home-banner-s-wp"><img src="<?php echo img_url($pc_top_ad[2]->pic_url)?>"></span></a>                   
               </div>
         </div>
  </div>
 </div>
<!--home-banner-out-end-->
<script>
    void function(e,t){for(var n=t.getElementsByTagName("img"),a=+new Date,i=[],o=function(){this.removeEventListener&&this.removeEventListener("load",o,!1),i.push({img:this,time:+new Date})},s=0;s< n.length;s++)!function(){var e=n[s];e.addEventListener?!e.complete&&e.addEventListener("load",o,!1):e.attachEvent&&e.attachEvent("onreadystatechange",function(){"complete"==e.readyState&&o.call(e,o)})}();}(window,document);
</script>
<!--home-rank-outer-start-->
<div class="home-rank-outer clearfix">
    <ul class="home-rank-wrapper clearfix">
    
    <li class="active">
        <div class="home-rank-title home-rank-hot"></div>
        <div class="home-rank-overflow">
             <ul class="home-rank-devlist clearfix">
                  <?php foreach($pc_hot_sale_product['first']['items'] as $k => $v):?>   

                    <li class="<?php $class = array('home-rank-ft', 'home-rank-second', 'home-rank-third');echo $class[$k];?>">
                       <a href="/pdetail-<?php echo $v[0]->product_id;?>" target="_blank">
                          <div class="home-rank-img-wp"><img src="<?php echo img_url($v[0]->img_url)?>"></div>
                          <div class="home-rank-pro-title"><?php echo $v[0]->product_name;?></div>
                          <div class="home-nr"><?php echo $v[0]->ad_code;?></div>
                          <div class="home-rank-price">爱牙优享:<span>￥<?php echo $v[0]->shop_price?></span><em><?php echo $v[0]->market_price?></em></div>
                       </a>
                    </li>
                  <?php endforeach;?>
            </ul>
       </div>
   </li>

   <li style="">
     <div class="home-rank-title home-rank-ev"></div>
     <div class="home-rank-overflow">
        <ul class="home-rank-devlist clearfix">
            <?php foreach($pc_hot_sale_course['first']['items'] as $k => $v):?>   

              <li class="<?php $class = array('home-rank-ft', 'home-rank-second', 'home-rank-third');echo $class[$k];?>">
                 <a href="/product-<?php echo $v[0]->product_id;?>" target="_blank">
                    <div class="home-rank-img-wp"><img src="<?php echo img_url($v[0]->img_url)?>"></div>
                    <div class="home-rank-pro-title"><?php echo $v[0]->product_name;?></div>
                    <div class="home-nr"><?php echo $v[0]->ad_code;?></div>
                    <div class="home-rank-price">爱牙优享:<span>￥<?php echo $v[0]->shop_price?></span><em><?php echo $v[0]->market_price?></em></div>
                 </a>
              </li>
            <?php endforeach;?>
        </ul>
    </div>
 </li>

</ul>

<ul class="home-rank-icon">
    <li class="active"></li>
    <li></li>
    <!--<li></li>-->
</ul>
</div>  
<!--home-rank-outer-end-->  

<!--oral-care-start-->
<div class="oral-care clearfix">
     <div class="oral-care-pic"><a href="/category-0-0-0-0-11.html" target="_blank"><img src="<?php echo img_url($pc_remcommand_pro['first']['col_pic_url'])?>"></a></div>
     <div class="oral-care-left">
          <a href="/pdetail-<?php echo $pc_remcommand_pro['first']['items'][0][0]->product_id?>" class="oral-care-team">
            <div class="oral-name"><?php echo $pc_remcommand_pro['first']['items'][0][0]->product_name?></div>
            <div class="oral-advantage"><?php echo $pc_remcommand_pro['first']['items'][0][0]->ad_code?></div>
            <div class="oral-price"><span>￥<?php echo $pc_remcommand_pro['first']['items'][0][0]->shop_price?></span><em><?php echo $pc_remcommand_pro['first']['items'][0][0]->market_price?></em></div>
            <?php mark_product($pc_remcommand_pro['first']['items'][0][0]);?>
            <img class="lazy" src="<?php echo img_url($pc_remcommand_pro['first']['items'][0][0]->img_url);?>">
          </a>
          <a href="/pdetail-<?php echo $pc_remcommand_pro['first']['items'][1][0]->product_id?>" class="oral-care-team">
          <div class="oral-name"><?php echo $pc_remcommand_pro['first']['items'][1][0]->product_name?></div>
          <div class="oral-advantage"><?php echo $pc_remcommand_pro['first']['items'][1][0]->ad_code?></div>
          <div class="oral-price"><span>￥<?php echo $pc_remcommand_pro['first']['items'][1][0]->shop_price?></span><em><?php echo $pc_remcommand_pro['first']['items'][1][0]->market_price?></em></div>
          <?php mark_product($pc_remcommand_pro['first']['items'][1][0]);?>
          <img class="lazy" src="<?php echo img_url($pc_remcommand_pro['first']['items'][1][0]->img_url);?>">
          </a>
     
     </div>
     <div class="oral-care-cen">
          <a href="/pdetail-<?php echo $pc_remcommand_pro['first']['items'][2][0]->product_id?>" class="oral-care-team oral-care-main">
            <div class="oral-name"><?php echo $pc_remcommand_pro['first']['items'][2][0]->product_name?></div>
            <div class="oral-advantage"><?php echo $pc_remcommand_pro['first']['items'][2][0]->ad_code?></div>
            <div class="oral-price"><span>￥<?php echo $pc_remcommand_pro['first']['items'][2][0]->shop_price?></span><em><?php echo $pc_remcommand_pro['first']['items'][2][0]->market_price?></em></div>
            <?php mark_product($pc_remcommand_pro['first']['items'][2][0]);?>
            <img class="lazy" src="<?php echo img_url($pc_remcommand_pro['first']['items'][2][0]->img_url);?>">           
          </a>
     
     </div>
     <div class="oral-care-right">
          <a href="/pdetail-<?php echo $pc_remcommand_pro['first']['items'][3][0]->product_id?>" class="oral-care-team  oral-care-main2">
            <div class="oral-name"><?php echo $pc_remcommand_pro['first']['items'][3][0]->product_name?></div>
            <div class="oral-advantage"><?php echo $pc_remcommand_pro['first']['items'][3][0]->ad_code?></div>
            <div class="oral-price"><span>￥<?php echo $pc_remcommand_pro['first']['items'][3][0]->shop_price?></span><em><?php echo $pc_remcommand_pro['first']['items'][3][0]->market_price?></em></div>
            <?php mark_product($pc_remcommand_pro['first']['items'][3][0]);?>
            <img class="lazy" src="<?php echo img_url($pc_remcommand_pro['first']['items'][3][0]->img_url);?>">
          </a>
          <a href="/pdetail-<?php echo $pc_remcommand_pro['first']['items'][4][0]->product_id?>" class="oral-care-team oral-care-main2">
            <div class="oral-name"><?php echo $pc_remcommand_pro['first']['items'][4][0]->product_name?></div>
            <div class="oral-advantage"><?php echo $pc_remcommand_pro['first']['items'][4][0]->ad_code?></div>
            <div class="oral-price"><span>￥<?php echo $pc_remcommand_pro['first']['items'][4][0]->shop_price?></span><em><?php echo $pc_remcommand_pro['first']['items'][4][0]->market_price?></em></div>
            <?php mark_product($pc_remcommand_pro['first']['items'][4][0]);?>
            <img class="lazy" src="<?php echo img_url($pc_remcommand_pro['first']['items'][4][0]->img_url);?>">
          </a>
     
     
     </div>
</div>
<!--oral-care-end-->


<!--oral-care-start-->
<div class="oral-care clearfix">
     <div class="oral-care-pic oral-care-pic2"><a href="/category-0-0-0-0-11.html" target="_blank"><img src="<?php echo img_url($pc_remcommand_pro['second']['col_pic_url'])?>"></a></div>
     <div class="oral-care-left">
          <a href="/pdetail-<?php echo $pc_remcommand_pro['second']['items'][0][0]->product_id?>" class="oral-care-team">
          <div class="oral-name"><?php echo $pc_remcommand_pro['second']['items'][0][0]->product_name?></div>
          <div class="oral-advantage"><?php echo $pc_remcommand_pro['second']['items'][0][0]->ad_code?></div>
          <div class="oral-price"><span>￥<?php echo $pc_remcommand_pro['second']['items'][0][0]->shop_price?></span><em><?php echo $pc_remcommand_pro['second']['items'][0][0]->market_price?></em></div>
          <?php mark_product($pc_remcommand_pro['second']['items'][0][0]);?>
          <img class="lazy" src="<?php echo img_url($pc_remcommand_pro['second']['items'][0][0]->img_url);?>">
          </a>
          <a href="/pdetail-<?php echo $pc_remcommand_pro['second']['items'][1][0]->product_id?>" class="oral-care-team">
          <div class="oral-name"><?php echo $pc_remcommand_pro['second']['items'][1][0]->product_name?></div>
          <div class="oral-advantage"><?php echo $pc_remcommand_pro['second']['items'][1][0]->ad_code?></div>
          <div class="oral-price"><span>￥<?php echo $pc_remcommand_pro['second']['items'][1][0]->shop_price?></span><em><?php echo $pc_remcommand_pro['second']['items'][1][0]->market_price?></em></div>
          <?php mark_product($pc_remcommand_pro['second']['items'][1][0]);?>
          <img class="lazy" src="<?php echo img_url($pc_remcommand_pro['second']['items'][1][0]->img_url);?>">
          </a>
     
     </div>
     <div class="oral-care-cen">
          <a href="/pdetail-<?php echo $pc_remcommand_pro['second']['items'][2][0]->product_id?>" class="oral-care-team oral-care-main">
          <div class="oral-name"><?php echo $pc_remcommand_pro['second']['items'][2][0]->product_name?></div>
          <div class="oral-advantage"><?php echo $pc_remcommand_pro['second']['items'][2][0]->ad_code?></div>
          <div class="oral-price"><span>￥<?php echo $pc_remcommand_pro['second']['items'][2][0]->shop_price?></span><em><?php echo $pc_remcommand_pro['second']['items'][2][0]->market_price?></em></div>
          <?php mark_product($pc_remcommand_pro['second']['items'][2][0]);?>
          <img class="lazy" src="<?php echo img_url($pc_remcommand_pro['second']['items'][2][0]->img_url);?>">           
          </a>     
     </div>
     <div class="oral-care-right">
          <a href="/pdetail-<?php echo $pc_remcommand_pro['second']['items'][3][0]->product_id?>" class="oral-care-team  oral-care-main2">
          <div class="oral-name"><?php echo $pc_remcommand_pro['second']['items'][3][0]->product_name?></div>
          <div class="oral-advantage"><?php echo $pc_remcommand_pro['second']['items'][3][0]->ad_code?></div>
          <div class="oral-price"><span>￥<?php echo $pc_remcommand_pro['second']['items'][3][0]->shop_price?></span><em><?php echo $pc_remcommand_pro['second']['items'][3][0]->market_price?></em></div>
          <?php mark_product($pc_remcommand_pro['second']['items'][3][0]);?>
          <img class="lazy" src="<?php echo img_url($pc_remcommand_pro['second']['items'][3][0]->img_url);?>">
          </a>
          <a href="/pdetail-<?php echo $pc_remcommand_pro['second']['items'][4][0]->product_id?>" class="oral-care-team oral-care-main2">
          <div class="oral-name"><?php echo $pc_remcommand_pro['second']['items'][4][0]->product_name?></div>
          <div class="oral-advantage"><?php echo $pc_remcommand_pro['second']['items'][4][0]->ad_code?></div>
          <div class="oral-price"><span>￥<?php echo $pc_remcommand_pro['second']['items'][4][0]->shop_price?></span><em><?php echo $pc_remcommand_pro['second']['items'][4][0]->market_price?></em></div>
          <?php mark_product($pc_remcommand_pro['second']['items'][4][0]);?>
          <img class="lazy" src="<?php echo img_url($pc_remcommand_pro['second']['items'][4][0]->img_url);?>">
          </a>
     </div>
</div>
<!--oral-care-end-->

<!--dentist-start-->
<div class="dentist clearfix">
     <div class="dentist-pic"><a href="#" target="_blank"><img src="<?php echo img_url($pc_remcommand_course['first']['col_pic_url'])?>"></a></div>
     <ul class="dentist-list">
        <div class="dentist-chaochu">
            <?php foreach($pc_remcommand_course['first']['items'] as $k => $v):?>
              <li data-href="/product-<?php echo $v[0]->product_id?>">
                <a href="javascript:void(0)"><img src="<?php echo img_url($v[0]->img_url);?>"></a>
                <div class="dentist-hover">
                <a href="javascript:void(0)">
                <img src="<?php echo img_url($v[0]->img_url);?>">
                <h2><?php echo $v[0]->product_name;?></h2>
                <?php $product_desc_additional = (!empty($v[0]->product_desc_additional)) ? json_decode($v[0]->product_desc_additional, true) : array(); ?>
                <span class="dentist-kaike dentist-public">开课：<?php echo date("Y.m.d", strtotime($v[0]->package_name));?>
                                <?php if (isset($product_desc_additional['desc_waterproof'])) echo '-' . date("Y.m.d", strtotime($product_desc_additional['desc_waterproof']))?></span>
                <span class="dentist-shoucang dentist-public">收藏：<?php echo $v[0]->collect_num;?>人</span>
                <span class="dentist-jiangshi dentist-public">讲师：<?php echo $v[0]->subhead;?></span>
                <div class="dentist-xinx dentist-public clearfix">
                   <span class="dentist-bm">报名：<?php echo $v[0]->ps_num;?>人</span>
                   <span class="dentist-sprice">￥<?php echo $v[0]->product_price;?></span>
                </div>
                </a>
                </div>
              </li>
            <?php endforeach;?>
             
        </div>
     </ul>
</div>
<!--dentist-end-->



<!--exhibition-goods-start-->
<div class="exhibition-goods">
     <div class="exhibition-goods-title"><span>展览商品</span></div>
     <ul class="exhibition-goods-show">
        <?php foreach ($pc_show_pro['first']['items'] as $k => $v):?>
          <li><a href="pdetail-<?php echo $v[0]->product_id?>"><img src="<?php echo img_url($v[0]->img_url)?>"><p><?php echo $v[0]->product_name?></p></a></li>
        <?php endforeach;?>         
        
     </ul>
  <a target="_blank" href="/brand/lists" class="more-exhibits">更多展品....</a>
</div>
<!--exhibition-goods-end-->


<!--cooperation-start-->
<div class="cooperation">
     <ul class="cooperation-list">
         <?php echo $brand_list[0]->ad_code?>
         
    </ul>
</div>
<!--cooperation-end-->
</div>

</div>
<!--carousel-figure-end-->


	

<script type="text/javascript">


!function(){  var banner = require('home:widget/banner/banner.js');
  banner.init({
      'dirLeft': '.home-banner-dir-left',
      'dirRight': '.home-banner-dir-right'
  }).init();
}();

!function(){    var banner = require('home:widget/banner/banner.js');
    $('.home-product-wrapper').each(function() {
        var self = this;
        banner.init({
            outerContainer: self,
            wrapper: '.home-product-out',
            lis: '.home-product-out li',
            icoWrapper: '.home-product-ico',
            timeInterval: 2000
        }).init();
    });
}();


!function(){    window.alogObjectConfig = {
        product: '300',
        page: '300_5',
        speed: {
            sample: '1'
        },
        monkey: {
            sample: '1'
        },
        exception: {
            sample: '1'
        },
        feature: {
            sample: '1'
        }
    };
    void function(e,t,n,a,r,o){function c(t){e.attachEvent?e.attachEvent("onload",t,!1):e.addEventListener&&e.addEventListener("load",t)}function i(e,n,a){a=a||15;var r=new Date;r.setTime((new Date).getTime()+1e3*a),t.cookie=e+"="+escape(n)+";path=/;expires="+r.toGMTString()}function s(e){var n=t.cookie.match(new RegExp("(^| )"+e+"=([^;]*)(;|$)"));return null!=n?unescape(n[2]):null}function d(){var e=s("PMS_JT");if(e){i("PMS_JT","",-1);try{e=eval(e)}catch(n){e={}}e.r&&t.referrer.replace(/#.*/,"")!=e.r||1}}c(function(){r=t.createElement(n),r.async=!0,r.src=a+"?v="+~(new Date/864e5),o=t.getElementsByTagName(n)[0],o.parentNode.insertBefore(r,o)}),d()}(window,document,"script","comm.js");
}();

$(function(){
  
  newSlide({container: ".home-rank-outer",show: {container: ".home-rank-wrapper"},operate: {container: ".home-rank-icon",hoverClass: "active",cur: "active",triggerType: "click"},control: {width: "",height: "",prev: "",next: "",interval: 5e3,animate: {type: "shadow","static": {},dynamic: {animateTime: 500,direction: 0},shadow: {animateTime: 800,zIndex: 1}}}})
});


$(function(){
  $('.dentist-chaochu li').click(function(e){
    var host = '<?php echo FRONT_HOST?>';    
    
    window.open(host + $(this).attr('data-href'));    
  });
});
</script>

<?php include APPPATH . 'views/common/footer.php'?>