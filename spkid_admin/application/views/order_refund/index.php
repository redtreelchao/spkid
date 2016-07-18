<?php if ($full_page): ?>
    <?php include(APPPATH . 'views/common/header.php'); ?>
    <script type="text/javascript" src="public/js/utils.js"></script>
    <script type="text/javascript" src="public/js/listtable.js"></script>

    <script type="text/javascript">
        //<![CDATA[
        listTable.filter.page_count = '<?php echo $filter['page_count']; ?>';
        listTable.filter.page = '<?php echo $filter['page']; ?>';
        listTable.url = 'order_refund/index';
        function search(){
            listTable.filter['order_sn'] = $.trim($('input[name=order_sn]').val());
listTable.filter['r_type'] = $.trim($('select[name=r_type]').val());
//listTable.filter['amount'] = $.trim($('input[name=amount]').val());
listTable.filter['create_admin'] = $.trim($('input[name=create_admin]').val());
listTable.filter['create_date_start'] = $.trim($('input[name=create_date_start]').val());
listTable.filter['create_date_end'] = $.trim($('input[name=create_date_end]').val());
listTable.filter['finance_date_start'] = $.trim($('input[name=finance_date_start]').val());
listTable.filter['finance_date_end'] = $.trim($('input[name=finance_date_end]').val());
listTable.filter['finance_admin'] = $.trim($('input[name=finance_admin]').val());

            listTable.loadList();
        }
        //]]>
    </script>
<script type="text/javascript">
//<![CDATA[
$(function(){
    $('input[type=text][name=create_date_start]').datepicker({dateFormat: 'yy-mm-dd',changeMonth: true,changeYear: true, nextText:'', prevText:''});
    $('input[type=text][name=finance_date_start]').datepicker({dateFormat: 'yy-mm-dd',changeMonth: true,changeYear: true, nextText:'', prevText:''});
    $('input[type=text][name=create_date_end]').datepicker({dateFormat: 'yy-mm-dd',changeMonth: true,changeYear: true, nextText:'', prevText:''});
    $('input[type=text][name=finance_date_end]').datepicker({dateFormat: 'yy-mm-dd',changeMonth: true,changeYear: true, nextText:'', prevText:''});
});
//]]>
</script>
    <div class="main">
        <div class="main_title">
            <span class="l">订单退款列表</span><span class="r"><a href="order_refund/add" class="add">新增</a></span></div>
        <div class="blank5"></div>
        <div class="search_row">
            <form name="search" action="javascript:search(); ">
            订单号&nbsp;<input name="order_sn" class="textbox require" id="order_sn" value="" type="text"/>
退款类型&nbsp;<?php print form_dropdown("r_type",array_merge(array('0'=>'请选择'), $fields_source["r_type"]),array(""),"data-am-selected");?>
创建人&nbsp;<input name="create_admin" class="textbox require" id="create_admin" value="" type="text"/>
财审人&nbsp;<input name="finance_admin" class="textbox require" id="finance_admin" value="" type="text"/>
创建时间&nbsp;<input name="create_date_start" class="textbox require" id="create_date_start" value="" type="text"/> - <input name="create_date_end" class="textbox require" id="create_date_end" value="" type="text"/>
财审时间&nbsp;<input name="finance_date_start" class="textbox require" id="finance_date_start" value="" type="text"/> - <input name="finance_date_end" class="textbox require" id="finance_date_end" value="" type="text"/>
                <input type="submit" class="am-btn am-btn-primary" value="搜索" />
            </form>
        </div>
        <div class="blank5"></div>
        <div id="listDiv">
        <?php endif; ?>
        <table width="1172" cellpadding=0 cellspacing=0 class="dataTable" id="dataTable">
            <tr>
                <td colspan="9" class="topTd"> </td>
            </tr>
            <tr class="row">
        <th width="100">订单号</th>
<th width="100">退款类型</th>
<th width="100">退款金额</th>
<th width="100">创建时间</th>
<th width="100">创建人</th>
<th width="100">财审时间</th>
<th width="100">财审人</th>

                <th width="77">操作</th>
            </tr>
            <?php foreach ($list as $row): ?>
                <tr class="row">

        <td><span><?php if(!empty($fields_source)&&isset($fields_source["order_sn"])&&isset($fields_source["order_sn"]["$row->order_sn"]))echo $fields_source["order_sn"]["$row->order_sn"] ;else echo $row->order_sn; ?></span></td>
<td><span><?php if(!empty($fields_source)&&isset($fields_source["r_type"])&&isset($fields_source["r_type"]["$row->r_type"]))echo $fields_source["r_type"]["$row->r_type"] ;else echo $row->r_type; ?></span></td>
<td><span><?php if(!empty($fields_source)&&isset($fields_source["amount"])&&isset($fields_source["amount"]["$row->amount"]))echo $fields_source["amount"]["$row->amount"] ;else echo $row->amount; ?></span></td>
<td><span><?php if(!empty($fields_source)&&isset($fields_source["create_date"])&&isset($fields_source["create_date"]["$row->create_date"]))echo $fields_source["create_date"]["$row->create_date"] ;else echo $row->create_date; ?></span></td>
<td><span><?php if(!empty($fields_source)&&isset($fields_source["create_name"])&&isset($fields_source["create_name"]["$row->create_name"]))echo $fields_source["create_name"]["$row->create_name"] ;else echo $row->create_name; ?></span></td>
<td><span><?php if(!empty($fields_source)&&isset($fields_source["finance_date"])&&isset($fields_source["finance_date"]["$row->finance_date"]))echo $fields_source["finance_date"]["$row->finance_date"] ;else echo $row->finance_date; ?></span></td>
<td><span><?php if(!empty($fields_source)&&isset($fields_source["finance_name"])&&isset($fields_source["finance_name"]["$row->finance_name"]))echo $fields_source["finance_name"]["$row->finance_name"] ;else echo $row->finance_name; ?></span></td>

                    <td>
                        <a href="order_refund/edit/<?php print $row->id; ?>" title="编辑" class="edit"></a>
                        <?php if(empty($row->finance_admin)): ?>
                                <a class="del" href="javascript:void(0);" rel="order_refund/delete/<?php print $row->id; ?>" title="删除" onclick="do_delete(this)"></a>
                        <?php endif; ?>
                    </td>
                </tr>
            <?php endforeach; ?>
            <tr>
                <td colspan="9" class="bottomTd"> </td>
            </tr>
        </table>
        <div class="blank5"></div>
        <div class="page">
            <?php include(APPPATH . 'views/common/page.php') ?>
        </div>
<script>
// jquery editable 
function _editable(){
var r_type_ds = <?php echo $fields_source_data["r_type"];?>
$('.editable_select_r_type').editable({ 
    url: '/order_refund/editable',
    source: r_type_ds,
    success: function(response, newValue) {
        if(!response.success) return response.msg;
        if( response.value != newValue  ) return '操作失败';
    }
}); 

$('.editable').editable({ url: '/order_refund/editable', emptytext:'',
        success: function(response, newValue) {
            if(!response.success) return response.msg;
            if( response.value != newValue ) return '操作失败';
        }
    });
}
listTable.func = _editable; // 分页加载后调用的函数名
_editable();
</script>

        <?php if ($full_page): ?>
        </div>
    </div>
    <?php include_once(APPPATH . 'views/common/footer.php'); ?>
<?php endif; ?>