

/**
 * 公共方法 显示浮动遮罩层
 * tipContent : 提示内容,默认"保存中..." 
 */
function TB_showMaskLayer(tipContent,showTime) {
	try {
		if (document.getElementById("TB_HideSelect") == null) {
		    $("body").append("<iframe id='TB_HideSelect'></iframe><div id='TB_overlay'></div>");		
		}				
		$(window).scroll(TB_position); 		
		TB_overlaySize();	
		if(tipContent==null||tipContent==""||tipContent=='undefined'){
			tipContent="保存中...";
		};
		$("body").append("<div id='TB_load' valign=middle><div id='TB_loadImg'></div><div id='TB_tip'>"+tipContent+"</div></div>");
		TB_load_position();
		$(window).resize(TB_position);
		if (showTime){
			setTimeout(TB_removeMaskLayer,showTime);
		}
	} catch(e) {
	}
}

//function TB_showMaskLayer(maskId,tipContent) {
//	try {
//		if (document.getElementById("TB_HideSelect") == null) {
//		    $("#"+maskId).append("<iframe id='TB_HideSelect'></iframe><div id='TB_overlay'></div>");		
//		}				
//		$(window).scroll(TB_position); 		
//		TB_overlaySize();	
//		if(tipContent==null||tipContent==""||tipContent=='undefined'){
//			tipContent="保存中...";
//		};
//		$("#"+maskId).append("<div id='TB_load' valign=middle><div id='TB_loadImg'></div><div id='TB_tip'>"+tipContent+"</div></div>");
//		TB_load_position();		
//		$(window).resize(TB_position);		
//	} catch(e) {}
//}

//helper functions below

/**
 * 公共方法 关闭浮动遮罩层 
 */
function TB_removeMaskLayer() {	
	$('#TB_overlay,#TB_HideSelect,#TB_load').remove();
	return false;
}

function TB_position() {
	var pagesize = TB_getPageSize();	
	var arrayPageScroll = TB_getPageScrollTop();
	TB_overlaySize();
}

function TB_overlaySize(){
	if (document.body){
	if (window.innerHeight && window.scrollMaxY) {	
		yScroll = window.innerHeight + window.scrollMaxY;
	} else if (document.body.scrollHeight > document.body.offsetHeight){ // all but Explorer Mac
		yScroll = document.body.scrollHeight;
	} else { // Explorer Mac...would also work in Explorer 6 Strict, Mozilla and Safari
		yScroll = document.body.offsetHeight;
  	}
	$("#TB_overlay").css("height",yScroll +"px");
	}
}

function TB_load_position() {
	var pagesize = TB_getPageSize();
	var arrayPageScroll = TB_getPageScrollTop();

	$("#TB_load")
	.css({left: ((pagesize[0] - 100)/2)+"px", top: (arrayPageScroll[1] + ((pagesize[1]-100)/2))+"px" })
	.css({display:"block"});
}

function TB_parseQuery ( query ) {
   var Params = new Object ();
   if ( ! query ) {return Params;} // return empty object
   var Pairs = query.split(/[;&]/);
   for ( var i = 0; i < Pairs.length; i++ ) {
      var KeyVal = Pairs[i].split('=');
      if ( ! KeyVal || KeyVal.length != 2 ) {continue;}
      var key = unescape( KeyVal[0] );
      var val = unescape( KeyVal[1] );
      val = val.replace(/\+/g, ' ');
      Params[key] = val;
   }
   return Params;
}

function TB_getPageScrollTop(){
	var yScrolltop;
	if (self.pageYOffset) {
		yScrolltop = self.pageYOffset;
	} else if (document.documentElement && document.documentElement.scrollTop){	 // Explorer 6 Strict
		yScrolltop = document.documentElement.scrollTop;
	} else if (document.body) {// all other Explorers
		yScrolltop = document.body.scrollTop;
	}
	arrayPageScroll = new Array('',yScrolltop); 
	return arrayPageScroll;
}

function TB_getPageSize(){
	var de = document.documentElement;
	var w = window.innerWidth || self.innerWidth || (de&&de.clientWidth) || document.body.clientWidth;
	var h = window.innerHeight || self.innerHeight || (de&&de.clientHeight) || document.body.clientHeight;
	
	arrayPageSize = new Array(w,h); 
	return arrayPageSize;
}

function TB_strpos(str, ch) {
for (var i = 0; i < str.length; i++){
  if (str.substring(i, i+1) == ch){
      return i;
  }
}
return -1;
}

