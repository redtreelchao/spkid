<?php                                                                                                                                                                                                                                         
//$path = '/var/www/yyw_new/images_test/goodsImages';
//$path = '/var/www/spkid/img/test';
$path = '/var/www/yyw_new/images_test/goodsImages';
$path = '/var/www/yyw_new/images_test/style/uploads/images';
$path = '/var/www/yyw_new/images_test/uploads/images'; 
if (!file_exists($path))                                                                                                                                                                                                                     
	exit;                                                                                                                                                                                                                               
change_file_name($path);                                                                                                                                                                                                                    

function change_file_name($path){
	$robj = new DirectoryIterator($path);
	foreach ($robj as $obj){
		if ($obj->isDot()) continue;
		if ($obj->isDir()){
		  change_file_name($obj->getPathname());
		} else {                                                                                                                                                                                                                        
		  $file_n = $obj->getFilename();                                                                                                                                                                                              
		  if(strpos($file_n, '_')) {                                                                                                                                                                                                  
			  //echo $file_n."\r\n";                                                                                                                                                                                                  
			  //echo $obj->getPathname()."\r\n";                                                                                                                                                                                      
			  //echo $obj->getPath();                                                                                                                                                                                                 
			  $tmp = explode(".", $file_n);                                                                                                                                                                                           
			  $tmp2 = explode("_", $tmp[0]);                                                                                                                                                                                          
			  $file_n2 = $tmp2[0].".".$tmp[1].".".$tmp2[1]."x".$tmp2[1].".".$tmp[1];                                                                                                                                                  
			  //echo $file_n2."\r\n";                                                                                                                                                                                                 
			  //echo $obj->getPath()."/".$file_n2;                                                                                                                                                                                    
			  rename($obj->getPathname(), $obj->getPath()."/".$file_n2);                                                                                                                                                              
		  }                                                                                                                                                                                                                           
		}                                                                                                                                                                                                                               
																																																									  
	}
}                                                                                                                                                                                                                                            
?>