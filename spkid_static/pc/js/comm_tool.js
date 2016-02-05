function get_liuyan(tagType, tagId, comment_type, container) {        
    if (!tagType || !tagId || !comment_type || !container) {
        alert('数据错误');
        return;
    };        
    var xhr_obj = $.ajax({
    url : '/liuyan/liuyan_list',
    type : 'POST',
    timeout:3000,
    dataType : "json",
    data : {
        comment_type : comment_type,
        tag_type : tagType,
        tag_id : tagId
    },
    success : function(data, status, xhr) {  
    	if (data.err == 0) {
    		container.html(data.html);
    	} else {
    		alert('未知错误');
    	} 
    },

    error : function(xhr, status) {
        alert('数据请求错误');
    },

    complete:function(xhr, status){
    	if (status == 'timeout') {
    		xhr_obj.abort();
    		//alert('连接超时');
    	};

    }

});
}

function checkLogin(page,app,callback_str){    
    var is_login;
    $.ajax({
        async:false,
        method:'POST',
        url:'/user/check_is_login',
        dataType:'json',
        success:function(res){
        if (!res.is_login){

            is_login=false;

        }else{
            is_login=true;
            window.user_id = res.user_id;
        } 
    } 
    });
    return is_login;
}


/**
 *  点击收藏
 *	@product_id		收藏的 商品id
 *	@product_type	收藏的 商品类型  0=商品、1=礼包、2=文章、3=课程、4=视频
 *  @b              当前要收藏的 元素 （this）
 */
function add_to_collect (product_id, product_type, b) {

    if(!checkLogin(false)){
        if ($("#login-box").length) {
            $("#login-box").modal('show');
        };
        return false;
    }

	$.ajax({
		url:'/product_api/add_to_collect',
		data:{product_id:product_id,product_type:product_type,rnd:new Date().getTime()},
		dataType:'json',
		type:'POST',
		success:function(result){
			if (result.msg) {
                alert(result.msg)
            };
			if (result.err) {return false};
            
            $(b).toggleClass('active');
            // TODO 显示收藏成功。
            // 
		}
	});
}

function getCookie(c_name) {
    if (document.cookie.length>0) {
        c_start=document.cookie.indexOf(c_name + "=");
        if (c_start!=-1) {
            c_start=c_start + c_name.length+1;
            c_end=document.cookie.indexOf(";",c_start);
            if (c_end==-1) c_end=document.cookie.length;
            return unescape(document.cookie.substring(c_start,c_end));
        }
    }
    return null;
}

function setCookie(objName,objValue,objHours){//添加cookie
    var str = objName + "=" + escape(objValue)+";path=/";
    if(objHours > 0){//为0时不设定过期时间，浏览器关闭时cookie自动消失
        var date = new Date();
        var ms = objHours*3600*1000;
        date.setTime(date.getTime() + ms);
        str += "; expires=" + date.toGMTString();
    }
    document.cookie = str;
}

//截取中英字符串 双字节字符长度为2 ASCLL字符长度为1
function cutStr(str, cutLen){
    var returnStr = '',    //返回的字符串
        reCN = /[^\x00-\xff]/,    //双字节字符
        strCNLen = str.replace(/[^\x00-\xff]/g,'**').length; 
    if(cutLen>=strCNLen){
        return str;
    }
    for(var i=0,len=0;len<cutLen;i++){
        returnStr += str.charAt(i);
        if(reCN.test(str.charAt(i))){
            len+=2;
        }else{
            len++;
        }
    }
    return returnStr + '...';
}