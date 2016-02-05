<?php include APPPATH . 'views/common/user_header.php'?>           
               
               <div class="personal-center-right">
                   
                     <h1 class="order-details-bt">收货地址</h1>
                     <div class="shipping-address">
                          <div class="addr-title">
		                       <span class="add-addr btn-small btn-primary btn">添加新地址</span>
                               <p class="tips">您已添加<span class="own"><?php echo count($address_list)?></span>个地址，还可以添加<span class="surplus"><?php echo 20 - count($address_list)?></span>个</p>
	                     </div>
                         
<?php foreach ($address_list as $a) :?>
<?php if($a->is_used):?>                        
                         <div class="addr-list default">
			                  <ul class="clearfix">
                              <li><span class="addr-title2"><em class="space-name">收货</em>人：</span><span class="name"><?php echo $a->consignee?></span></li>
				              <li><span class="addr-title2">手机号码：</span><span class="tel"><?php echo $a->mobile?></span></li>
				              <li class="clearfix"><span class="addr-title2">收货地址：</span><span class="area"><?php echo $a->country.$a->province.'  '.$a->city.$a->district.$a->address;?></span></li>
			                  </ul>
			                  <div class="operate operate2">
                                  <a class="modify-addr">修改</a><span class="separate"></span>
                                  <a href="#del-box" class="del" address-id="<?php echo $a->address_id?>">删除</a>
                              </div>

                              <span class="current">默认地址</span>
</div>
<?php else:?>
<div class="addr-list">
			                  <ul class="clearfix">
                              <li><span class="addr-title2"><em class="space-name">收货</em>人：</span><span class="name"><?php echo $a->consignee?></span></li>
				              <li><span class="addr-title2">手机号码：</span><span class="tel"><?php echo $a->mobile?></span></li>
				              <li class="clearfix"><span class="addr-title2">收货地址：</span><span class="area"><?php echo $a->country.$a->province.'  '.$a->city.$a->district.$a->address;?></span></li>
			                  </ul>
			                  <div class="operate">
                                  <a class="modify-addr">修改</a><span class="separate"></span>
                                  <a href="#del-box" class="del" address-id="<?php echo $a->address_id?>">删除</a><span class="separate"></span>
                                  <a href="" class="setdefault base-addr">设为默认</a>
                              </div>


</div>
<?php endif;?>
                       
<?php endforeach;?>
            </div>         
          </div>
     </div>
</div>
<div id="del-box" class="modal v-pov" tabindex="-1" role="dialog" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header v-close">
              <button type="button" class="close triangle-topright" data-dismiss="modal" aria-label="Close" ><span aria-hidden="true">&times;</span></button>
              <h4 class="modal-title">你确定要删除所选地址吗?</h4>
          </div>
          <div class="modal-body v-button">
<button class="btn btn-lg btn-blue confirm" type="submit">确定</button>             
              <button class="btn cancel" type="submit" data-dismiss="modal">取消</button>
              
          </div>
        </div>
      </div>
</div>
<script>
$(function(){
    var address_id;
    $('a.del').click(function(e){
        e.preventDefault();
        var popbox = $($(this).attr('href'));
        popbox.modal('show');
        address_id = $(this).attr('address-id');
    })
    $('.btn.confirm').click(function(){
        $.getJSON('/address/address_delete', {address_id:address_id}, function(data){
            if (2 == data.mobile_check_err){
                location.reload();
            }
        });
    })
    $('.setdefault').click(function(e){
        e.preventDefault();
        address_id = $(this).siblings('.del').attr('address-id');
        //console.log(address_id);
        $.getJSON('/address/address_default', {address_id:address_id}, function(data){
            if (0 == data.error){
                location.reload();
            }
        });
    })
})
</script>
<?php include APPPATH . 'views/common/footer.php'?>
