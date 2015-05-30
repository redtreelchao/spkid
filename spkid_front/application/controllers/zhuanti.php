<?php

if (!defined('BASEPATH'))
    exit('No direct script access allowed');

/**
 * Class Zhuanti
 * 静态专题
 * @author william
 */
class Zhuanti extends CI_Controller {

    function __construct() {
        parent::__construct();
    }

    //显示静态专题,从后台html/zhuanti/读取的html页面
    function index($param) {
        //$name = str_replace('.php', '.html', $param);
        $name = $param . '.html';
        
        if (function_exists('file_get_contents')) {
            $zhuanti_html = file_get_contents(static_url('zhuanti/' . $name));
        } else {
            $ch = curl_init();
            curl_setopt($ch, CURLOPT_URL, static_url('zhuanti/' . $name));
            curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
            $zhuanti_html = curl_exec($ch);
            curl_close($ch);
        }

        if (strpos($zhuanti_html, '<!--success-->') !== false) {
            echo $zhuanti_html;
        } else {
            header('location: /');
            exit;
        }
    }

}

?>