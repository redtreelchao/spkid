$('#chilltip').remove();
$(document).ready(function(){
	$('.chill-tip').live("mouseover", function(e){
		var title = $(this).attr("title");
		$('body').append('<div id="chilltip" class="chilltip_title"></div>');
		$('#chilltip').html('<div>' + title + '</div>').hide();
		$('#chilltip').css({
			"background": "#53585e",
			"border": "2px solid #53585e",
			"display": "none",
			"font-family": "宋体",
			"font-size": "14px",
			"max-width": "400px",
			"position": "absolute",
			"top": "0",
			"line-height" : "20px",
			"z-index" : "11001"
		});
		$('#chilltip div').css({
			"color": "#fff",
			/*"float": "left",*/
			"margin": "0",
			"padding": "5px",
			"text-align": "justify",
			"width": "auto",
			"line-height" : "20px",
			"word-wrap":"break-word"
		});
		this.tip = this.title;
		this.title = "";
	});
	$('.chill-tip').live("mousemove", function(e){
		var html = $.trim($('#chilltip div').html());
		if (html == "") {
			return;
		}
		var border_top = $(window).scrollTop(),border_right = $(window).width(),offset = 15,left_pos,top_pos;
	    if(	border_right - (offset *2) >= $('#chilltip').width() + e.pageX){left_pos = e.pageX + offset;}
		else{left_pos = border_right - $('#chilltip').width() - offset;}
		if(border_top + (offset *2 ) >= e.pageY - $('#chilltip').height()){top_pos = border_top + offset;}
		else{top_pos = e.pageY-$('#chilltip').height( )- offset;}	
		$('#chilltip').css({left:left_pos, top:top_pos});
		$('#chilltip').fadeIn(200);
	});
	$('.chill-tip').live("mouseout", function(){
		$('#chilltip').remove();
		this.title = this.tip;
	});
});