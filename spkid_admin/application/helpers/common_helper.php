<?php

/*
 * 显示错误信息
 *
 */
if (!function_exists('sys_msg'))
{

	/**
	 *
	 * @param string $msg_detail 信息内容，已经过翻译的内容
	 * @param int $msg_type 0 是信息， 1 是警告 2 是确认
	 * @param type $links
	 * @param type $auto_redirect
	 */
	function sys_msg($msg_detail, $msg_type = 0, $links = array(), $auto_redirect = TRUE)
	{
		$CI = & get_instance();
		if (count($links) == 0)
		{
			$links[0]['text'] = '返回';
			$links[0]['href'] = 'javascript:history.go(-1)';
		}
		if ($CI->input->is_ajax_request()) {
			echo json_encode(array('err' => $msg_type==1?1:0, 'msg' => $msg_detail));
		}else {
			$data = array(
			'msg_detail' => $msg_detail,
			'msg_type' => $msg_type,
			'links' => $links,
			'default_url' => $links[0]['href'],
			'auto_redirect' => $auto_redirect
			);
			print $CI->load->view('index/message', $data, TRUE);
		}
		// 调用exit之前，关闭数据库链接。
		if (class_exists('CI_DB') AND isset($CI->db))
		{
			$CI->db->close();
		}
		exit;
	}

}

if (!function_exists('db_create_in'))
{

	/**
	 * 生成 IN ('') 的形式，用于SQL
	 * @param array $item_list
	 * @return string
	 */
	function db_create_in($item_list)
	{
		if (empty($item_list))
		{
			return " IN ('') ";
		} else
		{
			if (!is_array($item_list))
			{
				$item_list = explode(',', $item_list);
			}
			$item_list = array_unique($item_list);
			$item_list_tmp = '';
			foreach ($item_list AS $item)
			{
				if ($item !== '')
				{
					$item = addslashes($item);
					$item_list_tmp .= $item_list_tmp ? ",'$item'" : "'$item'";
				}
			}
			if (empty($item_list_tmp))
			{
				return " IN ('') ";
			} else
			{
				return ' IN (' . $item_list_tmp . ') ';
			}
		}
	}

}

if (!function_exists('mysql_like_quote'))
{
	/**
	 * 对 MYSQL LIKE 的内容进行转义
	 *
	 * @access      public
	 * @param       string      string  内容
	 * @return      string
	 */
	function mysql_like_quote($str)
	{
	    return strtr($str, array("\\\\" => "\\\\\\\\", '_' => '\_', '%' => '\%'));
	}
}

if (!function_exists('page_and_size'))
{

	/**
	 * 计算页数和当前页
	 * @param array $filter
	 * @return array
	 */
	function page_and_size($filter)
	{
		$filter['page_size'] = (isset($filter['page_size']) && intval($filter['page_size']) > 0) ? (int) $filter['page_size'] : 20;
		$filter['page_count'] = max(ceil($filter['record_count'] / $filter['page_size']), 1);
		$filter['page'] = (isset($filter['page']) && intval($filter['page']) > 0) ? (int) $filter['page'] : 1;
		if ($filter['page'] > $filter['page_count']) $filter['page'] = $filter['page_count'];
		//$filter['sort_flag'] = '<img border="0" align="absmiddle" src="public/images/' . ($filter['sort_order'] == "DESC" ? 'desc.gif' : 'asc.gif') . '"/>';
		$filter['sort_flag'] = '<span class="' . (isset($filter['sort_order'])&&$filter['sort_order'] == "DESC" ? 'sort_desc' : 'sort_asc') . '"></span>';
		return $filter;
	}

}



if (!function_exists('get_pager_param'))
{

	function get_pager_param($filter)
	{
		$CI = & get_instance();
		$page = trim($CI->input->post('page'));
		if (!empty($page)) $filter['page'] = $page;
		$page_size = trim($CI->input->post('page_size'));
		if (!empty($page_size)) $filter['page_size'] = $page_size;
		$sort_by = trim($CI->input->post('sort_by'));
		if (!empty($sort_by)) $filter['sort_by'] = $sort_by;
		$sort_order = trim($CI->input->post('sort_order'));
		if (!empty($sort_order)) $filter['sort_order'] = $sort_order;
		$template = trim($CI->input->post('template'));
		if (!empty($template)) $filter['template'] = $template;
		return $filter;
	}

}

if (!function_exists('create_page'))
{

	function create_pages($page, $count)
	{
		if (empty($page))
		{
			$page = 1;
		}

		if (!empty($count))
		{
			$str = "<option value='1'>1</option>";
			$min = min($count - 1, $page + 3);
			for ($i = $page - 3; $i <= $min; $i++)
			{
				if ($i < 2)
				{
					continue;
				}
				$str .= "<option value='$i'";
				$str .= $page == $i ? " selected='true'" : '';
				$str .= ">$i</option>";
			}
			if ($count > 1)
			{
				$str .= "<option value='$count'";
				$str .= $page == $count ? " selected='true'" : '';
				$str .= ">$count</option>";
			}
		} else
		{
			$str = '';
		}
		return $str;
	}

}


if (!function_exists('get_pair'))
{

	/**
	 * 将二维数组 整合为一组key/value对
	 * @param array $arr
	 * @param string $k
	 * @param string $v
	 * @return array
	 */
	function get_pair($arr, $k, $v, $ext=array())
	{
		$result = $ext;
		foreach ($arr as $val)
		{
			if (is_object($val)) {
				$result[$val->$k] = $val->$v;
			}elseif (is_array($val)) {
				$result[$val[$k]] = $val[$v];
			}
		}
		return $result;
	}

}

if (!function_exists('index_array'))
{

	/**
	 * 为数组编制索引
	 * @param array $array
	 * @param string $key
	 * @return array
	 */
	function index_array($array, $key)
	{
		$result = array();
		foreach ($array as $k => $v)
		{
			if (is_object($v)) {
				$result[$v->$key] = $v;
			}elseif (is_array($v)) {
				$result[$v[$key]] = $v;
			}
		}
		return $result;
	}

}

if (!function_exists('json_encode'))
{

	/**
	 * json_encode的兼容性函数
	 * @param type $arg
	 * @param type $force
	 * @return string
	 */
	function json_encode($data)
	{
		if (is_array($data) || is_object($data))
		{
			$islist = is_array($data) && ( empty($data) || array_keys($data) === range(0, count($data) - 1) );

			if ($islist)
			{
				$json = '[' . implode(',', array_map('json_encode', $data)) . ']';
			} else
			{
				$items = Array();
				foreach ($data as $key => $value)
				{
					$items[] = json_encode("$key") . ':' . json_encode($value);
				}
				$json = '{' . implode(',', $items) . '}';
			}
		} elseif (is_string($data))
		{
			# Escape non-printable or Non-ASCII characters.
			# I also put the \\ character first, as suggested in comments on the 'addclashes' page.
			$string = '"' . addcslashes($data, "\\\"\n\r\t/" . chr(8) . chr(12)) . '"';
			$json = '';
			$len = strlen($string);
			# Convert UTF-8 to Hexadecimal Codepoints.
			for ($i = 0; $i < $len; $i++)
			{

				$char = $string[$i];
				$c1 = ord($char);

				# Single byte;
				if ($c1 < 128)
				{
					$json .= ( $c1 > 31) ? $char : sprintf("\\u%04x", $c1);
					continue;
				}

				# Double byte
				$c2 = ord($string[++$i]);
				if (($c1 & 32) === 0)
				{
					$json .= sprintf("\\u%04x", ($c1 - 192) * 64 + $c2 - 128);
					continue;
				}

				# Triple
				$c3 = ord($string[++$i]);
				if (($c1 & 16) === 0)
				{
					$json .= sprintf("\\u%04x", (($c1 - 224) << 12) + (($c2 - 128) << 6) + ($c3 - 128));
					continue;
				}

				# Quadruple
				$c4 = ord($string[++$i]);
				if (($c1 & 8 ) === 0)
				{
					$u = (($c1 & 15) << 2) + (($c2 >> 4) & 3) - 1;

					$w1 = (54 << 10) + ($u << 6) + (($c2 & 15) << 2) + (($c3 >> 4) & 3);
					$w2 = (55 << 10) + (($c3 & 15) << 6) + ($c4 - 128);
					$json .= sprintf("\\u%04x\\u%04x", $w1, $w2);
				}
			}
		} else
		{
			# int, floats, bools, null
			$json = strtolower(var_export($data, true));
		}
		return $json;
	}

}

if ( ! function_exists('check_perm') )
{
	function check_perm($perms)
	{
		static $action_list = NULL;
		static $admin_id = NULL;
		if ( $action_list === NULL )
		{
			$CI = & get_instance();
			$action_list = $CI->session->userdata('action_list');
			$admin_id = $CI->session->userdata('admin_id');
		}
		if(is_string($perms)) $perms = array($perms);
		foreach($perms as $perm)
		{
			if($admin_id == 1 || $action_list == '-1' || preg_match(','.$perm.',', ','.$action_list.','))
			return TRUE;
		}
		return FALSE;
	}
}

if ( ! function_exists('auth') )
{
	function auth ($perms)
	{
		if ( check_perm($perms) )
		{
			return TRUE;
		}
		sys_msg('没有权限', 1);
	}
}

if (! function_exists('img_tip')) {
	function img_tip($tip_path = '', $tip_img = '', $tip_width='', $tip_title = '')
	{
		if (! $tip_img) {
			return '无';
		}
                $tip_path = str_replace('public/upload/',PUBLIC_DATA_IMAGES,$tip_path);
		return "<span class='img_tip' title='{$tip_title}|<img src=\"{$tip_path}{$tip_img}\" ".($tip_width?"width=\"{$tip_width}\"":'').">'></span>";
	}
}

if (!function_exists('toggle_link')) {
	//function toggle_link($act='', $field='', $id='', $val='', $yes_exp='yes.gif', $no_exp='no.gif') 改为样式显示 By Rock 同下注释
	function toggle_link($act='', $field='', $id='', $val='', $yes_exp='', $no_exp='')
	{
		$html = '';
		$html .= "<span style=\"cursor:pointer;\" onclick=\"listTable.toggle(this,'{$act}','{$field}',{$id},'{$yes_exp}','{$no_exp}')\">";
		//if (in_array(substr($yes_exp,-4),array('.gif','.jpg','.png'))) $yes_exp = "<img src='public/images/{$yes_exp}' />";
		if(empty($yes_exp)){
		    $yes_exp = "<span class='yesForGif' ></span>";
		}else{
		    $yes_exp = "<span>{$yes_exp}</span>";
		}
		if(empty($no_exp)){
		    $no_exp ="<span class='noForGif' ></span>";
		}else{
		    $no_exp ="<span>{$no_exp}</span>";
		}
		//if (in_array(substr($no_exp,-4),array('.gif','.jpg','.png'))) $no_exp = "<img src='public/images/{$no_exp}' />";
		
		$html .= $val?$yes_exp:$no_exp;
		$html .= "</span>";
		return $html;
	}
}

if (!function_exists('proc_toggle_link')) {
	function proc_toggle($model_name, $primary_key, $toggle_range=array(), $filter_func='filter', $update_func='update')
	{
		$CI = & get_instance();
		$id = intval($CI->input->post('id'));
		$field = trim($CI->input->post('field'));
		$yes_exp = trim($CI->input->post('yes_exp'));
		$no_exp = trim($CI->input->post('no_exp'));
//		if (in_array(substr($yes_exp,-4),array('.gif','.jpg','.png'))) $yes_exp = "<img src='public/images/{$yes_exp}' />";
//		if (in_array(substr($no_exp,-4),array('.gif','.jpg','.png'))) $no_exp = "<img src='public/images/{$no_exp}' />";
if(empty($yes_exp)){
		   $yes_exp = " <span class='yesForGif' ></span>";
		}else{
		    $yes_exp = " <span>".$yes_exp."</span>";
		}
		if(empty($no_exp)){
		   $no_exp = " <span class='noForGif' ></span>";
		}else{
		   $no_exp = " <span>".$no_exp."</span>";
		}
		if (!in_array($field,$toggle_range)) return array('err'=>1,'msg'=>'参数错误');

		$row = $CI->$model_name->$filter_func(array($primary_key=>$id));
		if (!$row) {
			return array('err'=>1,'msg'=>'记录不存在');
		}

		$update = array($field=>(1-$row->$field));
		$CI->$model_name->$update_func($update, $id);
		return array('err'=>0, 'msg'=>'','content'=>$update[$field]?$yes_exp:$no_exp);
	}
}

if (!function_exists('edit_link')) {
	function edit_link($act, $field, $id, $val)
	{
		$html = "<span onclick=\"javascript:listTable.edit(this, '{$act}', '{$field}', {$id})\" title=\"点击修改内容\" >{$val}</span>";
		return $html;
	}

}

if (!function_exists('proc_edit')) {
	function proc_edit($model_name, $primary_key, $edit_range = array(), $val = NULL, $filter_func='filter', $update_func='update', $placeholder='')
	{
		$CI = & get_instance();
		$id = intval($CI->input->post('id'));
		$field = trim($CI->input->post('field'));
		if ($val===NULL) {
			$val = trim($CI->input->post('val'));
		}
		if($val==$placeholder) $val='';
		if (!in_array($field, $edit_range)) {
			return array('err'=>1, 'msg'=>'参数错误');
		}
		$row = $CI->$model_name->$filter_func(array($primary_key=>$id));
		if (!$row) {
			return array('err'=>1, 'msg'=>'记录不存在');
		}
		$update = array($field=>$val);
		$CI->$model_name->$update_func($update,$id);
		$row = $CI->$model_name->$filter_func(array($primary_key=>$id));
		return array('err'=>0,'msg'=>'','content'=>strlen($row->$field)==0?$placeholder:$row->$field);
	}
}

/**
 * mcrypt加密
 *
 * @return  str
 */
 function m_encode($string)
 {
 	$td = mcrypt_module_open(MCRYPT_DES,'','ecb',''); //使用MCRYPT_DES算法,ecb模式
	$iv = mcrypt_create_iv(mcrypt_enc_get_iv_size($td), MCRYPT_RAND);
	$ks = mcrypt_enc_get_key_size($td);
	$key = MCRYPT_KEY; //自定义密钥
	$key = substr(md5($key), 0, $ks);
	$string = base64_encode($string);

	mcrypt_generic_init($td, $key, $iv); //初始处理

	//加密
	$encrypted = mcrypt_generic($td, $string);

	//结束处理
	mcrypt_generic_deinit($td);
	mcrypt_module_close($td);
	return  base64_encode($encrypted);

 }

 /**
 * mcrypt解密
 *
 * @return  str
 */
 function m_decode($string)
 {
 	$td = mcrypt_module_open(MCRYPT_DES,'','ecb',''); //使用MCRYPT_DES算法,ecb模式
	$iv = mcrypt_create_iv(mcrypt_enc_get_iv_size($td), MCRYPT_RAND);
	$ks = mcrypt_enc_get_key_size($td);
	$key = MCRYPT_KEY; //密钥
	$key = substr(md5($key), 0, $ks);

	mcrypt_generic_init($td, $key, $iv); //初始处理

	//解密
	$decrypted = mdecrypt_generic($td, base64_decode($string));

	//结束处理
	mcrypt_generic_deinit($td);
	mcrypt_module_close($td);
	//解密后,可能会有后续的\0,需去掉
	return trim(base64_decode($decrypted));

 }

if (!function_exists('fix_price')) {
	function fix_price($value)
	{
		return round(floatval($value),2);
	}
}

function is_date_string($str)
{
	return (boolean) preg_match("/^[0-9]{4}\-[0-9]{2}\-[0-9]{2}$/",$str);
}

//检查日期字符串及其年月日是否合法
function is_date_str_check($str ){
    if (is_date_string($str)) {
        //检查年月日
        if( checkdate(intval(mb_substr($str, 5, 2)), intval(mb_substr($str, 8, 2)), intval(mb_substr($str, 0, 4) )) ){
            return true;
        }
    }
    return false;
}

function is_datetime_string($str)
{
	return (boolean) preg_match("/^[0-9]{4}\-[0-9]{2}\-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}$/",$str);
}

//检查时间字符串及其年月日是否合法
function is_time_str_check($time_str ){
    if (is_datetime_string($time_str)) {
        //检查年月日
        $str = mb_substr($time_str, 0, 10);
        if( checkdate(intval(mb_substr($str, 6, 2)), intval(mb_substr($str, 8, 2)), intval(mb_substr($str, 0, 4) )) ){
            return true;
        }
    }
    return false;
}

function img_url($path='')
{
	return IMG_HOST.'/'.$path;
}

function front_url($path='')
{
	return FRONT_HOST.'/'.$path;
}

function adjust_path($content)
{
	return str_replace('/public/upload/',img_url('/data/'),$content);
}

function get_ghost ()
{
	$CI = &get_instance();
	$ip = $CI->input->ip_address();
	return strtoupper(md5($ip.'eab8bcae23fc'));
}
/**
 * 格式化filter对象
 * keys 填充　filter数组
 * 会将有数据的keys值以数组形式记录在search_keys中
 * return 填充后的$filter
 */
function fill_filter( $filter, $keys ){
    if( empty($keys) || !is_array($keys) ) return $filter;
	$CI = &get_instance();
    foreach( $keys AS $key ){
        $val = $CI->input->post($key);
        if (!empty($val)) {
            $filter['search_keys'][] = $key;
            $filter[$key] = $val;
        }   
    }
    return $filter;
}

/**
 * 根据filter中的search_keys生成SQL片段
 * return 中的第后个值是SQL片段
 */
function generate_where_by_filter( $filter, $useOr=false ){
    $param =array();
    $sqlConcat = $useOr?SQL_OR:SQL_AND;

    if( !empty($filter['search_keys']) ){
        $where ='';
        $tmpStr = Array();
        foreach( $filter['search_keys'] AS $key ){
            array_push( $tmpStr, $key ." = ? " );
            array_push( $param, $filter[$key] );
        }   
        $where .= ($useOr?"(":''). implode($tmpStr,' '.$sqlConcat.' '). ($useOr?")":'');
        array_push( $param, $where );
    }

    return $param;
}

if (!function_exists('get_sys_ctb_code')) {
    /**
     * 
     * @param String $depot_io_type DEPOT_IO_TYPE_IN | DEPOT_IO_TYPE_OUT
     * @param Integer $depot_io_generate_max DEFAULT:DEPOT_IO_GENERATE_MAX
     * @return array
     */
    function get_sys_ctb_code($depot_io_type, $depot_io_generate_max = DEPOT_IO_GENERATE_MAX) {
        $result = array();
        for ($i = 0x0000; $i < $depot_io_generate_max; $i++) {
            $result[date("Y-m-d", time() + ($i * 60 * 60 * 24))] = $depot_io_type . date("Ymd", time() + ($i * 60 * 60 * 24)) . DEPOT_IO_AFTEER_FIX;
        }
        return $result;
    }
}

/**
 * 获得当天的"代销转买断" 出库和入库的单号，并在ＤＢ中确认其存在 \
 * 若有一个不存在，返回FALSE；否则，返回：Array( key=>code,key=>code )
 */
function get_today_sys_ctb_code()
{
	$CI = & get_instance();
	$result = Array();

	// 入库单号
	$ioCode = get_sys_ctb_code( DEPOT_IO_TYPE_IN , 1 );
	$result[DEPOT_IO_TYPE_IN] = $ioCode[date("Y-m-d")];

	// 出库单号
	$ioCode = get_sys_ctb_code( DEPOT_IO_TYPE_OUT, 1 );
	$result[DEPOT_IO_TYPE_OUT] = $ioCode[date("Y-m-d")];

	// check 入库单号 existance
	$sql =  "SELECT count(*) AS num FROM ty_depot_in_main WHERE depot_in_code ='".$result[DEPOT_IO_TYPE_IN]
		."' AND create_date > '".date('Y-m-d',time()-86400*DEPOT_IO_GENERATE_MAX)."'";
	$query= $CI->db_r->query( $sql );
	if( $query->num_rows() !== 1 ) return false;

	// check 出库单号 existance
	$sql =  "SELECT count(*) AS num FROM ty_depot_out_main WHERE depot_out_code ='".$result[DEPOT_IO_TYPE_OUT]
		."' AND create_date > '".date('Y-m-d',time()-86400*DEPOT_IO_GENERATE_MAX)."'";
	$query= $CI->db_r->query( $sql );
	if( $query->num_rows() !== 1 ) return false;

	return $result;
}

if(!function_exists('curl')){
    function curl($url){
        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, $url);
        curl_setopt($ch, CURLOPT_TIMEOUT, 5);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
        $r = curl_exec($ch);
        curl_close($ch);
        return $r;
    }
}

function front_img_url($path='')
{
    return STATIC_HOST.'/'.$path;
}

function front_listimg_url ( $path,$id )
{
    static $img_hosts=array();
    static $num=0;
    if($img_hosts==array()){
            $CI=&get_instance();
            $img_hosts=$CI->config->item('img_hosts');
            if(empty($img_hosts)) $img_hosts=array(STATIC_HOST);
            $num=count($img_hosts);
    }
    return $img_hosts[$id%$num].'/'.$path;
}
function sys_ctb_operation( $ctbArray )
{
	$CI = & get_instance();
	$CI->load->model('depotio_model');
	$CI->load->helper('product');
	$CI->load->model('return_model');

	$result = get_today_sys_ctb_code();
	foreach( $ctbArray AS $ctbRow )
	{
		$newProduct = create_ctb_product_with_product($ctbRow['product_id'], $ctbRow );//生成买断商品
		$newProductId = $newProduct['product_id'];
		$newBatchId = $newProduct['batch_id'];

		$rec_id = insert_depot_out_detail( $result[DEPOT_IO_TYPE_OUT], $ctbRow );
		//$CI->depotio_model->insert_depot_in_product( get_ctb_transaction_detail_out($result[DEPOT_IO_TYPE_OUT],$ctbRow,$rec_id) );//生成出库明细
		$CI->return_model->insert_transaction( get_ctb_transaction_detail_out($result[DEPOT_IO_TYPE_OUT],$ctbRow,$rec_id) );

		$rec_id = insert_depot_in_detail( $result[DEPOT_IO_TYPE_IN], $ctbRow, $newBatchId );
		//$CI->depotio_model->insert_depot_out_product( get_ctb_transaction_detail_in($result[DEPOT_IO_TYPE_IN],$ctbRow,$newProductId,$rec_id) );
		$CI->return_model->insert_transaction( get_ctb_transaction_detail_in($result[DEPOT_IO_TYPE_IN],$ctbRow,$newProductId,$newBatchId, $rec_id) );//生成入库明细
	}
}
/**
 * REQUIREMENT已经LOAD了depot_model
 */
function insert_depot_in_detail( $trans_sn, $row, $batch_id )
{
	$CI = & get_instance();
	$CI->load->model('depotio_model');

	$data['batch_id'] = $batch_id;
	$keys = Array( 'product_name','product_id','color_id','size_id'
			,'depot_id','location_id','shop_price','product_number'
			,'product_amount' );
	$keys2 = Array( 'depot_out_id','create_admin','create_date' );
	foreach( $keys AS $key ) $data[$key] = $row[$key];
	$depot_in_main = $CI->depotio_model->filter_depot_in( Array('depot_in_code'=>$trans_sn) );
	foreach( $keys2 AS $key ) $data[$key] = $depot_in_main[$key];
	return $CI->depotio_model->insert_depot_in_product($data);
}
/**
 * REQUIREMENT已经LOAD了depot_model
 */
function insert_depot_out_detail( $trans_sn, $row )
{
	$data['depot_id'] = RETURN_DEPOT_ID;
	$data['location_id'] = RETURN_DEPOT_LOCATION_ID;
	$keys = Array( 'product_name','product_id','color_id','size_id'
			,'shop_price','product_number'
			,'product_amount','batch_id' );
	$keys2 = Array( 'depot_out_id','create_admin','create_date' );
	foreach( $keys AS $key ) $data[$key] = $row[$key];
	$CI = & get_instance();
	$depot_out_main = $CI->depotio_model->filter_depot_out( Array('depot_out_code'=>$trans_sn) );
	foreach( $keys2 AS $key ) $data[$key] = $depot_out_main[$key];
	return $CI->depotio_model->insert_depot_out_product($data);
}
function get_ctb_transaction_detail_out($trans_sn, $ctbRow, $sub_id)
{
	unset($ctbRow['transaction_id']);
	$ctbRow['update_time']= date('Y-m-d')." ".CTB_DEPOT_IO_TIME;
	$ctbRow['depot_id']= RETURN_DEPOT_ID;
	$ctbRow['location_id']= RETURN_DEPOT_LOCATION_ID;
	$ctbRow['product_number'] = -abs($ctbRow['product_number']);
	$ctbRow['trans_sn'] = $trans_sn;
	$ctbRow['trans_type'] = TRANS_TYPE_DIRECT_OUT;
	$ctbRow['trans_status'] = TRANS_STAT_OUT;
	$ctbRow['sub_id'] = $sub_id;
    $ctbRow['trans_direction'] = 1;
	return $ctbRow;
} 
function get_ctb_transaction_detail_in($trans_sn, $ctbRow,$newProductId, $batch_id, $sub_id)
{
	unset($ctbRow['transaction_id']);
	$ctbRow['update_time']= date('Y-m-d')." ".CTB_DEPOT_IO_TIME;
	$ctbRow['product_number'] = abs($ctbRow['product_number']);
	$ctbRow['trans_sn'] = $trans_sn;
	$ctbRow['trans_type'] = TRANS_TYPE_DIRECT_IN;
	$ctbRow['trans_status'] = TRANS_STAT_IN;
	$ctbRow['sub_id'] = $sub_id;
    $ctbRow['trans_direction'] = 0;
    $ctbRow['product_id'] = $newProductId;
    $ctbRow['batch_id'] = $batch_id;
	return $ctbRow;
} 

function get_default_return_depot(){
    return Array(
        RETURN_DEPOT_ID=>RETURN_DEPOT_NAME,
        MD_RETURN_DEPOT_ID=>MD_RETURN_DEPOT_NAME
        );
}
function get_ctb_return_depot(){
    return Array(
        CTB_RETURN_DEPOT_ID=>CTB_RETURN_DEPOT_NAME
        );
}

    /**
     * 将GMT时间戳格式化为用户自定义时区日期
     * @param  string       $format
     * @param  integer      $time       该参数必须是一个GMT的时间戳
     * @return  string
     */
if (!function_exists('local_date'))
{
function local_date($format, $time = NULL) {
        $timezone = '8';
        if ($time === NULL) {
            $time = time() - date('Z');
        } elseif ($time <= 0) {
            return '';
        }
        $time += ($timezone * 3600);
        return date($format, $time);
    }
}