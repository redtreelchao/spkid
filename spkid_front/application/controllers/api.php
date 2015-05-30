<?
class Api extends CI_Controller
{
    function __construct()
    {
        parent::__construct();
    }

    /**
     * return header html
     */
    function get_header()
    {
        $this->load->view('common/header',true);
    }

    /**
     * return footer html
     */
    function get_footer()
    {
        $this->load->view('common/footer',true);
    }

    /**
     * return navigation html without <ul></ul>
     */
    function get_navigation(){
        $navigation_url=static_style_url().'/index/navigation.html';
        $navigation=get_static_content('navigation',$navigation_url);
        $navigation=str_replace('<ul id="mainMenuUl">','',$navigation);
        echo substr($navigation,0,strrpos($navigation,'</ul>'));
    }
}
?>
