<?php if($full_page): ?>
<?php include APPPATH . 'views/common/user_header.php'?>
<div class="personal-center-right">

                    <h1 class="page-title">我的订单</h1>
                    <ul class="order-status clearfix">
                    <li><a href="#" data-status="1" class="active">全部订单</a></li>
                    <li><a href="#" data-status="6">待付款<!--<i>11</i>--></a></li>
                    <li><a href="#" data-status="7">待发货<!--<i>11</i>--></a></li>
                    <li><a href="#">待评价<!--<i>11</i>--></a></li>
                    </ul>
                    
                    <ul class="my-order-list clearfix">
                        <li class="order-infor">订单信息</li>
                        <li class="unit-price">单价</li>
                        <li class="unit-price">数量</li>
                        <li class="total-order">订单总额</li>
                        <li class="order-statuss">
                                <a class="dingdanzt" href="javascript:void(0);">订单状态</a>
                                <ul class="dingdan-status" style="display:none;">
                                <li><a href="#" class="currt" data-status="1">全部订单</a></li>
                                <li><a href="#" data-status="7">待发货</a></li>
                                <li><a href="#" data-status="6">待付款</a></li>
                                <li><a href="#" data-status="4">已完成</a></li>
                                <li><a href="#" data-status="5">已取消</a></li>
                                </ul>
                        </li>
                        <li class="operation">操作</li>
                    </ul>
                 <?php if(empty($order_list)): ?>   
                 <div class="my-order-content" style="display:none;">
                         <p class="empty">还没有订单哦，赶紧去<a href="/">购物吧~</a></p>
                 </div>
                 <?php endif; ?>
                 <div id="listdiv">
                 <?php endif; ?>
                 <?php foreach($order_list as $order):?>
                 <table class="olist-table">
                      <thead class="serial-number">
                      <tr>
                        <th colspan="4" class="ordersn">订单编号：<?php echo $order->order_sn?><span>下单时间：<?php echo $order->format_create_date ?></span></th>
                        </tr>
                     </thead>
                     
                      <tr>
                        <td class="my-order-xx">
                           <?php foreach($order->order_goods as $k => $g): 
                               if ($k) continue;
                           ?>
                           <a href="#" target="_blank" class="my-order-of">
                              <div class="center-pic"><img src="<?php print img_url($g->img_url.".85x85.jpg"); ?>"></div>
                              <div class="center-title"><h3><?=$g->product_name?></h3><p><span>颜色：<?=$g->color_name?></span><span>规格：<?=$g->size_name?></span></p></div>
                           </a>
                           <div class="my-order-sprice">￥<?=$g->product_price?></div>
                           <div class="my-order-shuliang"><?=$g->product_num?></div>
                           <?php endforeach; ?>
                        </td>
                        
                        <td class="total">￥<?php echo $order->total_fee ?></td>
                        <td class="st">
                            <div><em><?=($order->order_amount > 0) ? '等待支付' : '已支付';?></em></div>
                            <div class="order-track">
                                <a class="track-order" href="javascript:void(0);" data-id="<?=$order->order_id?>">订单跟踪</a>
                                <div class="track-panel" id="track-panel<?=$order->order_id?>" style=" display:none;">
                                     <div class="t-summary" >
                                          <div class="t-title">
                                               <span>订单编号：<?php echo $order->order_sn?></span>
                                               <!--
                                               <span class="fl-right">等待支付</span>
                                               -->
                                          </div>
                                         <div class="chuli2"><?=$order->shipping_name?> <?=$order->invoice_no?></div>
                                    </div>
                                    <!--
                                   <div class="t-detail">
                                        <div class="t-title">订单跟踪</div>
                                        <p class="t-current"><span class="t-time">2016-01-28 13:24:34</span><span>您提交了订单，请等待系统确认</span></p>
                                   </div>
                                    -->
                               </div>
                         </div>
                         
                        </td>
                        <td class="operate">
                            <?php if ($order->can_pay): ?>
                            <div id="order-pay-<?=$order->order_id?>"><button class="btn lijifukuai" onclick="window.location.href='/order/pay/<?php print $order->order_id ?>';">立即付款</button></div>
                            <?php endif; ?>
                            <div><a href="/order/info/<?=$order->order_id?>">详情</a></div>
                            <?php if ( (empty($order->lock_admin) && $order->lock_admin == 0) && $order->order_status == 0 ): ?>
                            <div class="order_cancel" data-id="<?=$order->order_id?>">取消订单</div>
                            <?php endif ?>
                        </td>
                      </tr>
                      
                      <tfoot class="chuli">
                          <tr><td colspan="4" class="tip"><p>请在<em><?=date("Y-m-d H:i:s", ORDER_INVALID_TIME+strtotime($order->create_date)) ?></em>前完成支付，逾期订单将被系统做超时关闭处理</p></td></tr>
                      </tfoot>
                    
                    </table>
                    <?php endforeach?>
                     <?php if($full_page): ?> 
                    </div>
              </div>
                
          </div>    
     </div>
</div>
<script>
var order_status = '<?php echo $order_status ?>';
var order_page_count = '<?php echo $filter["page_count"] ?>';
var order_page = '<?php echo $filter["page"] ?>';

	 // dingdan-status样式下li 的a标签绑定点击事件
	$('.dingdan-status li a').bind("click",function(){
		//移除dingdan-status样式下所有a标签currt样式
		$('.dingdan-status li a').removeClass('currt');
		//当前点击a标签添加currt样式
		$(this).addClass('currt');
                order_status = $(this).attr('data-status');
                filter_result(order_status, 1);
	});
        
	$(".order-status li a").click(function(){
            var v_order_status = $(this).attr('data-status');
            if (v_order_status == undefined) return;
            order_status = v_order_status;
            $('.order-status li a').removeClass('active');
            $(this).addClass('active');
            
            filter_result(order_status, 1);
        }); 
        
	  //track-order这个样式所在的元素鼠标移上去事件
	/*$(".track-order").mouseover(function(){
            var v_id = $(this).attr('data-id');
	  	$("#track-panel"+v_id).show();//显示面板
	 });
	 
	 $(".track-order").mouseout(function(){
             var v_id = $(this).attr('data-id');
             $("#track-panel"+v_id).hide();//隐藏面板
	 });*/	
	 
	 $(".dingdanzt").mouseover(function(){
	  	$(".dingdan-status").show();
	  	$(this).addClass("test");
	 });
	  
	 $(".dingdanzt").mouseout(function(){
	 	$(".dingdan-status").hide();
	  	$(this).removeClass("test");
	 });
	  
	  $(".dingdan-status").mouseover(function(){
		$(".dingdan-status").show();
		$(".dingdanzt").addClass("test");
	  });
	  $(".dingdan-status").mouseout(function(){
		  $(".dingdan-status").hide();
		  $(".dingdanzt").removeClass("test");
	  });
          
$(document).on({
    mouseenter: function() { 
        var v_id = $(this).attr('data-id');
	$("#track-panel"+v_id).show();//显示面板
    }, 
    mouseleave: function() {
        var v_id = $(this).attr('data-id');
        $("#track-panel"+v_id).hide();//隐藏面板
    }
}, '.track-order');

function filter_result(status,page)
{
    if (status == 0)
    {
        status = order_status;
    }

    if (page == 0)
    {
        page = order_page;
    }
    if(page < 1)
    {
        page = 1;
    }
    if(page > order_page_count)
    {
        page = order_page_count;
        return false;
    }
    order_page = page;
    order_status = status;
//alert(order_status+' - '+order_page);
    $.ajax({
            url:'/user/order_list',
            data:{is_ajax:true,status:status,page:page,rnd:new Date().getTime()},
            dataType:'json',
            type:'POST',
            success:function(result){
                    if(result.error==0){
                        if (order_page == 1){
                            order_page_count = result.page_count;
                            $('#listdiv').html(result.content);
                        } else {
                            $('#listdiv').append(result.content);
                        }

                        //order_status = result.order_status;
                        //if (result.content.length <= 0) order_page--;
                    }
            }
    });
}
//取消订单
$(document).on('click', '.order_cancel', function (e) {
    var _this = $(this);
    var v_data_id = _this.attr('data-id');
    
    $.ajax({
            url:'/order/invalid/'+v_data_id,
            data:{},
            dataType:'json',
            type:'GET',
            success:function(result){
                    if(result.error==0){
                        $("#order-pay-"+v_data_id).remove();
                        _this.remove();
                    }
            }
    });
});

//滚动加载
var range = 300;
$(document).bind("scroll", function(){
    var srollPos = $(window).scrollTop();
    var totalheight = parseFloat($(window).height()) + parseFloat(srollPos);  
    if(($(document).height()-range) <= totalheight) {
        order_page++;
        //alert(order_page);
        filter_result(order_status, order_page);//调用
    }
});
</script>           
<?php include APPPATH . 'views/common/footer.php'?>
<?php endif; ?>