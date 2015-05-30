<?php if($full_page): ?>
<?php include(APPPATH.'views/common/header.php'); ?>
    <link type="text/css" href="public/style/jui/theme.css" rel="stylesheet" />
    <script src="public/js/My97DatePicker/WdatePicker.js"></script>
	<div class="main">
		<div class="main_title">
		<span class="l">焦点图管理</span>
		<?php if (check_perm('focus_image_add')): ?>
		<span class="r">
			<a href="front_focus_image/add" class="add">添加</a>
		</span>
		<?php endif; ?>
		</div>

		<div class="blank5"></div>
		<div class="search_row">
            <?foreach($focus_type as $key=>$val):?>
                <a href="front_focus_image/index/<?=$key?>"><?=$val?></a>
                &nbsp;&nbsp;&nbsp;&nbsp;
            <?endforeach?>
		</div>
		<div class="blank5"></div>
		<div id="listDiv">
<?php endif; ?>
            <form method="post" action="front_focus_image/update_focus">
			<table id="dataTable" class="dataTable" cellpadding=0 cellspacing=0>
				<tr>
					<td colspan="7" class="topTd"> </td>
				</tr>
				<tr class="row">
				  <th>名称</th>
				  <th>播放顺序</th>
			      <th>活动图片</th>
				  <th>开始时间</th>
				  <th>结束时间</th>
                  <th>操作</th>
				</tr>
				<?php foreach($list as $row): ?>
			    <tr class="row">
                    <td>
                        <?=$row->focus_name?>
                        <a href="<?=$row->focus_url?>">查看</a>
                        <input type="hidden" name="focus_id[]" value="<?=$row->id?>">
                    </td>
					<td>
                        <input type="text" name="focus_order[]" value="<?=$row->focus_order?>" style="width:50px;">
                    </td>
					<td>
                        <img src="<?='public/data/images/'.$row->focus_img?>" width=30 height=20>
                    </td>
					<td>
                        <input type="text" value="<?=$row->start_time?>" name="focus_start_time[]" id="fs_time_<?=$row->id?>" 
                            onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'fe_time_<?=$row->id?>\')}'})">
                    </td>
					<td>
                        <input type="text" value="<?=$row->end_time?>" name="focus_end_time[]" id="fe_time_<?=$row->id?>" 
                            onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'fs_time_<?=$row->id?>\')}'})">
                    </td>
                    <td>
                        <?if($row->focus_order==0){?>
                        <a href="/front_focus_image/delete/<?=$row->id?>">删除</a>
                        <?}?>
                    </td>
			    </tr>
				<?php endforeach; ?>
                <tr>
                    <td colspan="5">
                    </td>
                </tr>
			    <tr>
					<td colspan="5" class="bottomTd"></td>
				</tr>
			</table>
			<div class="blank5"></div>
		    <?php if (check_perm('focus_image_edit')): ?>
            <div style="text-align:center">
            <input type="submit" id="mysubmit" class="button" value="更新数据" onclick='return do_submit()'>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <input type="buttom" id="update_focus_image_html" class="button" value="更新首页播放页">
            </div>
            <?endif?>
           <?php if($full_page): ?>
		</div>
	</div>
    <script>
        $(function(){
            $('#update_focus_image_html').click(function(){
                location.href="front_focus_image/update_focus_image_html/<?=$search_type ?>";
            });
        });
        function do_submit(){
            var orders=document.getElementsByName('focus_order[]');
            if(orders.length>0){
                for(var i=0;i<orders.length;i++){
                    if(orders[i].value==""||isNaN(orders[i].value)){
                        alert('请输入正确的排序');
                        orders[i].focus();
                        return false;
                    }
                }
            }
            else{
                return false;
            }
            var start_time=document.getElementsByName('focus_start_time[]');
            for(var i=0;i<start_time.length;i++){
                if(start_time[i].value==""){
                    alert('请输入正确的开始时间');
                    start_time[i].focus();
                    return false;
                }
            }
            var end_time=document.getElementsByName('focus_end_time[]');
            for(var i=0;i<end_time.length;i++){
                if(end_time[i].value==""){
                    alert('请输入正确的结束时间');
                    end_time[i].focus();
                    return false;
                }
            }
            return true;
        }
    </script>
<?php include_once(APPPATH.'views/common/footer.php'); ?>
<?php endif; ?>
