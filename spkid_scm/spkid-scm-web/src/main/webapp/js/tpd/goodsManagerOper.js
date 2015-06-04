var color_option_flag=false;
var colorImg_option_flag=false;
var colorSize_option_flag=false;

/**
 * 增加颜色展示行
 * 
 * @returns
 */
function addCurrentRow(){
	if(!color_option_flag){
		try{
			color_option_flag=true;
			
			var colorId=$("#colorIdSelect").val();
			if(colorId==0){
				color_option_flag=false;
				return;
			}
			var colorName=$("#colorIdSelect").find("option:selected").html();
			var name="tm_"+colorId;
			//验证是否存在此Color
			if($("#"+name).length>0){
				alert("不能添加重复颜色");
			}else{
				$("#color_template").clone(true).attr("id",name ).appendTo("#allDatas tbody#tbody");
				$("#"+name).find("#colorId").val(colorId);
				$("#"+name).find("#colorName").html(colorName);
				$("#"+name).find("#sizeId").attr("id",name+"_sizeId");
				$("#"+name).find("a").attr("onclick","javascript:deleteCurrentRow('"+name+"');");
				//设置页面属性
				$("#"+name).find("input[flg='add']").attr("onclick","javascript:addSize('"+colorId+"');");
				$("#"+name).find("input[flg='upload']").attr("onclick","javascript:addImg('"+colorId+"');");
				$("#"+name).find("td[flg='size']").attr("id",colorId+"-size");
				$("#"+name).find("td[flg='img']").attr("id",colorId+"-img");
				$("#"+name).find("div[flg='color_opt']").attr("id",colorId+"-opt");
				$("#"+name).find("input[flg='img_count_flag']").attr("id","img_count_flag_"+colorId);
				$("#"+name).find("input[flg='img_count_flag']").attr("name","img_count_flag_"+colorId);
				//默认图标识
				$("#"+name).find("input[flg='img_def_flag']").attr("id","img_def_flag_"+colorId);
				$("#"+name).find("input[flg='img_def_flag']").attr("name","img_def_flag_"+colorId);
				//色片标识
				$("#"+name).find("input[flg='img_tonal_flag']").attr("id","img_tonal_flag_"+colorId);
				$("#"+name).find("input[flg='img_tonal_flag']").attr("name","img_tonal_flag_"+colorId);
				
				$("#"+name).find("input[name='image_category']").attr("name","image_category_"+colorId);
			} 
		}catch (e) {
			alert("增加颜色失败，请联系管理员！error："+e);
		}finally{
			color_option_flag=false;
		}
	}
}

/**
 * 增加Size展示
 * @param sizeId
 * @param sizeName
 * @returns
 */
function addSize(mach){
	if(!colorSize_option_flag){
		try{
			colorSize_option_flag=true;
			var sizeId=$("#tm_"+mach+"_sizeId").val();
			if(sizeId==0){
				colorSize_option_flag=false;
				return;
			}
			var sizeName=$("#tm_"+mach+"_sizeId").find("option:selected").html();
			var name="tm_"+mach+"_size_"+sizeId;
			//
			if($("#"+name).length>0){
				alert("不能添加重复尺码");
			}else{
				//clone
				$("#size_template").clone(true).attr("id",name ).appendTo("#"+mach+"-size");
				$("#"+name).find("input[flg='size_id']").attr("name","color_size_id_"+mach).val(sizeId);
				$("#"+name).find("#size_name").html(sizeName);
				$("#"+name).find("a").attr("onclick","javascript:deleteSize('"+name+"');");
				//重置Name
				$("#"+name).find("input[flg='size_code']").attr("name","color_"+mach+"size_"+sizeId+"code");
			}
		}catch (e) {
			alert("增加尺寸失败，请联系管理员！error："+e);
		}finally{
			colorSize_option_flag=false;
		}
	}
}

/**
 * 删除颜色
 * @param obj
 * @returns
 */
function deleteCurrentRow(id){
   var isDelete=confirm("真的要删除吗？"); 
   if(isDelete){
	   $("#"+id).remove();
   }
}

/**
 * 删除Size
 * @param obj
 * @returns
 */
function deleteSize(id){
	$("#"+id).remove();
}

/**
 * 增加商品图片
 */
function addImg(colorId){
	if(!colorImg_option_flag){
		try{
			colorImg_option_flag=true;
			var colorImg_count_flag=parseInt(genColorImgCount(colorId))+parseInt(1);
			var colorImgId=colorId+"_" + colorImg_count_flag;
			var divLa="#"+colorId+"-opt";
			
			var defFlag= getDefaultFlag(colorId); 
			var tonalFlag= getTonalFlag(colorId);
			//获取属性
			var name=$(divLa).find("#name").val();
			var type=$(divLa).find("input[type='radio']:checked").val();
			var typeName=$(divLa).find("input[type='radio']:checked").attr("alt");
			var order=$(divLa).find("#order").val();
			var file=$(divLa).find("#file")[0];
			if($(divLa).find("#file").val()==null || $(divLa).find("#file").val()==""){
				alert("请选择上传的图片!");
				colorImg_option_flag=false;
				return ;
			}
			if(!checkFiles($(divLa).find("#file").val(),".jpg|.png|.gif|.jpeg|.bmp")){
				colorImg_option_flag=false;
				return ;
			}
			//验证是否是默认图,且已经存在默认图
			if(type== null || type==''){
				type="default";
			}
			if(type=="default"){
				if(defFlag=="true"){
					alert("已经存在默认图，不允许添加多个默认图!");
					colorImg_option_flag=false;
					return ;
				}else{
					defFlag=true;
				}
			}else if(type =="tonal"){
				if(tonalFlag == "true"){
					alert("已经存在色片，不允许添加多个色片!");
					colorImg_option_flag=false;
					return ;
				}else{
					tonalFlag=true;
				}
			}
			//clone内容
			$("#img_template").clone(true).attr("id",colorImgId ).appendTo("#"+colorId+"-img");
			//设置变量
			$("#"+colorImgId).find("input[flg='color_img_type']").val(type);
			$("#"+colorImgId).find("input[flg='color_img_order']").val(order);
			$(divLa).find("#file").clone(true).appendTo("#"+colorImgId).hide();
			$("#"+colorImgId).find("a").attr("onclick","javascript:deleteView('"+colorImgId+"');");
			$("#"+colorImgId).find("input[flg='color_img_name']").val(name);
			//展示
			$("#"+colorImgId).find("#type_name").html(typeName);
			var previewId=colorImgId+"PreviewImage";
			$("#"+colorImgId).find("img").attr("id",previewId);
			PreviewImage(file,previewId,previewId);
			//重置Name
			$("#"+colorImgId).find("input[flg='color_img_type']").attr("name","color_img_type_"+colorImgId).attr("colorId",colorId);
			$("#"+colorImgId).find("input[flg='color_img_order']").attr("name","color_img_order_"+colorImgId);
			$("#"+colorImgId).find("input[flg='color_img_file']").attr("name","color_img_file_"+colorImgId);
			$("#"+colorImgId).find("input[flg='color_img_name']").attr("name","color_img_name_"+colorImgId);
			//清空
			$(divLa).find("#order").val("");
			$(divLa).find("#name").val("");
			$(divLa).find("#file").val("");
			
			initColorImgCount(colorId,colorImg_count_flag);
			initDefaultFlag(colorId, defFlag);
			initTonalFlag(colorId, tonalFlag);
		}catch (e) {
			alert("增加图片失败，请联系管理员！error："+e);
		}finally{
			colorImg_option_flag=false;
		}
	}
}

function show(file,img){
	var ip = new ImagePreview(file,img, {
		maxWidth: 200, maxHeight: 200, action: "viewImg.jsp"
	});
	ip.img.src = ImagePreview.TRANSPARENT;
	ip.preview();
}

function checkFiles(str, exStr) {
    if (typeof (exStr) == 'undefined')
        exStr = ".jpg|.png|.gif|.jpeg";
    var strRegex = "(" + exStr + ")$"; //用于验证图片扩展名的正则表达式
    var re = new RegExp(strRegex);
    if (re.test(str.toLowerCase())) {
        return true;
    }
    else {
        alert("文件名不合法,文件的扩展名必须为" + exStr + "格式");
        return false;
    }
}
/**
 * 删除某个视图
 * @param flagId
 */
function deleteView(flagId){
	var type = $("#"+flagId).find("input[flg='color_img_type']").val();
	var colorId = $("#"+flagId).find("input[flg='color_img_type']").attr("colorId");
	if(type == "default"){
		$("#"+flagId).remove();
		initDefaultFlag(colorId, false);
	}else if(type == "tonal"){
		$("#"+flagId).remove();
		initTonalFlag(colorId, false);
	}else{
		$("#"+flagId).remove();
	}
}
/**
 * 获取颜色图片个数
 * @param colorId
 * @returns
 */
function genColorImgCount(colorId){
	var count= $("#img_count_flag_"+colorId).val();
	if(count == null){
		return 0;
	}else{
		return count;
	}
}
/**
 * 反写颜色图片个数
 * @param colorId
 * @param colorImg_count_flag
 */
function initColorImgCount(colorId,colorImg_count_flag){
	$("#img_count_flag_"+colorId).val(colorImg_count_flag);
}
/**
 * 获取默认图片标识
 * @param colorId
 * @returns
 */
function getDefaultFlag(colorId){
	return $("#img_def_flag_"+colorId).val();
}
/**
 * 反写默认图片标识
 * @param colorId
 * @param val
 */
function initDefaultFlag(colorId,val){
	$("#img_def_flag_"+colorId).val(val);
}

/**
 * 获取色片图片标识
 * @param colorId
 * @returns
 */
function getTonalFlag(colorId){
	return $("#img_tonal_flag_"+colorId).val();
}
/**
 * 反写色片图片标识
 * @param colorId
 * @param val
 */
function initTonalFlag(colorId,val){
	$("#img_tonal_flag_"+colorId).val(val);
}

/**
 * 删除商品图
 * @param galleryId
 */
function deleteGallery(imgId) {
	if (confirm("真的要删除商品图吗？")) {
		var url = "/goods/deleteGallery.json";
		$.post(url, {imgId:imgId}, function(result) {
			if (result == "") {
				alert("删除商品图成功！");
				$("#img_template_"+imgId).remove();
			} else {
				alert(result);
			} 
		});
	}
}

/**
 * 删除尺寸对照图
 * @param galleryId
 */
function deleteBcs(goodsId) {
	if (confirm("真的要删除尺寸对照图吗？")) {
		var url = "/goods/deleteBcs.json";
		$.post(url, {goodsId:goodsId}, function(result) {
			if (result == "") {
				alert("删除尺寸对照图成功！");
				document.location.reload();
			} else {
				alert(result);
			} 
		});
	}
}