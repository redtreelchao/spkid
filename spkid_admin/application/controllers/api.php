<?php
class Api extends CI_Controller
{
    function __construct()
    {
        parent::__construct();
    }
    
    function do_sms(){
        if (!defined('CURRENT_SMS_SUPPLY')) exit;
    	$this->load->library("sms/".CURRENT_SMS_SUPPLY, NULL, 'sms');
    	$msg = $this->input->post('msg');
	$mob = $this->input->post('mob');
        $r = $this->sms->sendMsg($mob, $msg);
        echo $r;
	//return $r;
    }
}
?>
