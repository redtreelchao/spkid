<?php if ($full_page): ?>
    <?php include(APPPATH . 'views/common/header.php'); ?>
    <script type="text/javascript" src="public/js/utils.js"></script>
    <script type="text/javascript" src="public/js/listtable.js"></script>

    <script type="text/javascript">
        //<![CDATA[
        listTable.filter.page_count = '<?php echo $filter['page_count']; ?>';
        listTable.filter.page = '<?php echo $filter['page']; ?>';
        listTable.url = 'action/index';
        function search(){
            listTable.filter['parent_id'] = $.trim($('input[name=parent_id]').val());
            listTable.filter['action_code'] = $.trim($('input[name=action_code]').val());
            listTable.filter['action_name'] = $.trim($('input[name=action_name]').val());
            listTable.loadList();
        }
        //]]>
    </script>
    <div class="main">
        <div class="main_title"><span class="l">权限列表</span><span class="r"><a href="action/add" class="add">新增</a></span></div>
        <div class="blank5"></div>
        <div class="search_row">
            <form name="search" action="javascript:search(); ">
                权限CODE：
                <input type="text" name="action_code" id="action_code" value=""/>
                或&nbsp;父权限ID：
                <input type="text" name="parent_id" id="parent_id" value=""/>
                或&nbsp;权限名称：
                <input type="text" name="action_name" id="action_name" value=""/>
                <input type="submit" class="button" value="搜索" />
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
                <th width="42">ID</th>
                <th width="100">父权限ID</th>
                <th width="180">权限CODE</th>
                <th width="140">权限名称</th>
                <th width="130">菜单名称</th>
                <th width="402">菜单链接</th>
                <th width="99">排序值</th>
                <th width="77">操作</th>
            </tr>
            <?php foreach ($list as $row): ?>
                <tr class="row">
                    <td align="center"><?php print $row->action_id; ?></td>
                    <td><?php print $row->parent_id; ?></td>
                    <td><?php print $row->action_code; ?></td>
                    <td><?php print $row->action_name; ?></td>
                    <td><?php print $row->menu_name; ?></td>
                    <td><?php print $row->url; ?></td>
                    <td><?php print $row->sort_order; ?></td>
                    <td>
                        <a href="action/edit/<?php print $row->action_id; ?>" title="编辑" class="edit"></a>
                        <a class="del" href="javascript:void(0);" rel="action/delete/<?php print $row->action_id; ?>" title="删除" onclick="do_delete(this)"></a>
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
        <?php if ($full_page): ?>
        </div>
    </div>
    <?php include_once(APPPATH . 'views/common/footer.php'); ?>
<?php endif; ?>
