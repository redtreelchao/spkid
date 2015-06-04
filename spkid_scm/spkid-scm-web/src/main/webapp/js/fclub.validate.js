Validate = function(formId) {
	this.formId = formId;
	this.errorClass = "error";
	this.ruleReg = /^(\w){3,10}/;
	this.requiredReg = /required$/;
	this.limitReg = /\[(.*)\]/ig;
	var validateFunction = this.validate;
};
Validate.prototype = {
	validate : function() {
		var flag = true;
		var validateElementFunction = this.validateElement;
		var regsRuleTmp = this.ruleReg;
		var regsRequiredTmp = this.requiredReg;
		var limitRegTmp = this.limitReg;
		var errorClassTmp = this.errorClass;
		$("#" + this.formId + " [validate]").each(
			function(i) {
				var validateStr = $(this).attr("validate");
				if (isNotEmpty(validateStr)) {
					var val = $(this).attr("value");
					if (flag) {
						flag = validateElementFunction(val, validateStr,
								this, regsRuleTmp, regsRequiredTmp,
								limitRegTmp, errorClassTmp);
					} else {
						validateElementFunction(val, validateStr, this,
								regsRuleTmp, regsRequiredTmp, limitRegTmp,
								errorClassTmp);
					}
				}
			});
		return flag;
	},
	validateElement : function(value, validateStr, dom, regsRuleTmp,
			regsRequiredTmp, limitRegTmp, errorClassTmp) {
		var flag = true;
		if (isNotEmpty(validateStr)) {
			$(dom).removeClass(errorClassTmp);
			$(dom).css("color", "");
			$(dom).css("border", "1px solid #AAAAAA");
			$(dom).removeAttr("title");
			var regsRule = regsRuleTmp.exec(validateStr);
			var rule = regsRule[0];
			var isRequired = regsRequiredTmp.test(validateStr);
			var limitRule = limitRegTmp.exec(validateStr);
			limitRegTmp.exec(validateStr);
			var min, max, scale, minValue;
			if (isNotEmpty(limitRule)) {
				var limitsTmp = limitRule[1];
				var arrayTmp = limitsTmp.split(",");
				min = arrayTmp[0];
				max = arrayTmp[1];
				if (arrayTmp.length >= 3) {
					scale = arrayTmp[2];
				}
				if (arrayTmp.length == 4) {
					minValue = arrayTmp[3];
				}
			}
			var msg = "";
			if (isRequired) {
				var checkedSize = $(dom).find("input[type='checkbox']").length;
				var checkedLength = $(dom).find("input[type='checkbox']:checked");
				if (checkedSize > 0 && checkedLength.length == 0) {
					flag = false;
				} else if (checkedSize == 0 && isEmpty($.trim(value))) {
					flag = false;
				}
				if (!flag) {
					msg = "必输项";
					//$(dom).addClass(errorClassTmp);
					//$(dom).removeClass('Wdate');
					/*$(dom).css("color", "red");*/
					$(dom).css("border", "2px solid red");
					$(dom).attr("title", msg);
					//$(dom).focus();
				}
			}
			if (rule == "num") {
				var flg = false;
				var v = $.trim(value);
				if (typeof (scale) === "undefined") {
					flg = !ChkUtil.isDigitLen(v, min, max);
				} else {
					flg = !ChkUtil.isFloat(v, min, max, scale);
				}
				if (!flg && isNotEmpty(minValue)) {
					flg = !(v > minValue);
				}
				if (flg) {
					msg = " 只能输入数字" + "，长度必须在" + min + "-" + max + "位之间;";
					if (isNotEmpty(scale)) {
						msg = msg + "最多" + scale + "位小数;";
					}
					if (isNotEmpty(minValue)) {
						msg = msg + "且必须大于" + minValue + ";";
					}
					flag = false;
					//$(dom).addClass(errorClassTmp);
					$(dom).css("color", "red");
					$(dom).css("border", "2px solid red");
					$(dom).attr("title", msg);
					$(dom).focus();
				}
			} else if (rule == "date") {
				if (!ChkUtil.isDate(value)) {
					msg = " 格式必须为日期";
					flag = false;
					//$(dom).addClass(errorClassTmp);
					$(dom).css("color", "red");
					$(dom).css("border", "2px solid red");
					$(dom).attr("title", msg);
				}
			} else if (rule == "len") {
				if (!ChkUtil.isStringLen(value, min, max)) {
					msg = "长度必须在" + min + "-" + max + "位之间;";
					flag = false;
					//$(dom).addClass(errorClassTmp);
					$(dom).css("color", "red");
					$(dom).css("border", "2px solid red");
					$(dom).attr("title", msg);
					$(dom).focus();
				}
			} else if (rule == "email") {
				if (!ChkUtil.isEmail(value)) {
					msg = "请输入正确的Email";
					flag = false;
					//$(dom).addClass(errorClassTmp);
					$(dom).css("color", "red");
					$(dom).css("border", "2px solid red");
					$(dom).attr("title", msg);
					$(dom).focus();
				}
			} else if (rule == "mobile") {
				if (!ChkUtil.isMobile(value)) {
					msg = "请输入正确的手机号";
					flag = false;
					//$(dom).addClass(errorClassTmp);
					$(dom).css("color", "red");
					$(dom).css("border", "2px solid red");
					$(dom).attr("title", msg);
					$(dom).focus();
				}
			} else if (rule == "password") {
				if(!ChkUtil.isPassword10(value)) {
					msg = "请输入10位以上的数字和字母";
					flag = false;
					//$(dom).addClass(errorClassTmp);
					$(dom).css("color", "red");
					$(dom).css("border", "2px solid red");
					$(dom).attr("title", msg);
					$(dom).focus();
				}
			}else if (rule == "password1") {
				if(!ChkUtil.isPassword10(value)) {
					msg = "请输入10位以上的数字和字母";
					flag = false;
					if (!flag && isEmpty(value)) {
						flag = true;
					}
					if (!flag) {
						//$(dom).addClass(errorClassTmp);
						$(dom).css("color", "red");
						$(dom).css("border", "2px solid red");
						$(dom).attr("title", msg);
						$(dom).focus();
					}
				}
			} else if (rule == "integer") {
				if (!ChkUtil.isInteger(value)) {
					msg = "请输入整数";
					flag = false;
					if (!flag && isEmpty(value)) {
						flag = true;
					}
					//$(dom).addClass(errorClassTmp);
					if (!flag) {
						$(dom).css("color", "red");
						$(dom).css("border", "2px solid red");
						$(dom).attr("title", msg);
						$(dom).focus();
					}
				}
			} else if (rule == "url") {
				if (!ChkUtil.isUrl(value)) {
					msg = "请输入合法的网址";
					flag = false;
					if (!flag && isEmpty(value)) {
						flag = true;
					}
					if (!flag) {
						//$(dom).addClass(errorClassTmp);
						$(dom).css("color", "red");
						$(dom).css("border", "2px solid red");
						$(dom).attr("title", msg);
						$(dom).focus();
					}
				}
			} else if (rule == "letter") {
				msg="";
				flag = true;
				if (!ChkUtil.isLetter(value)) {
					msg += "请输入字母;";
					flag=false;
				}
				if(!ChkUtil.isLetterLen(value,min,max)){
					msg +="长度必须在{"+min+","+max+"}之间;";
					flag=false;
				}
					if (!flag) {
					$(dom).css("color", "red");
					$(dom).css("border", "2px solid red");
					$(dom).attr("title", msg);
					$(dom).focus();
				}
			} 
		}
		return flag;
	}
};
function ChkUtil() {
}
// 校验是否为空(先删除二边空格再验证)
ChkUtil.isNull = function(str) {
	if (null == str || "" == str.trim()) {
		return true;
	} else {
		return false;
	}
};
// 校验是否全是数字
ChkUtil.isDigit = function(str) {
	var patrn = /^\d+$/;
	return patrn.test(str);
};
ChkUtil.isDigitLen = function(str, min, max) {
	var patrn = new RegExp('^(\\d)' + '{' + min + ',' + max + '}$');
	return patrn.test(str);
};
//校验是否是字母
ChkUtil.isLetter = function(str) {
	var patrn =new RegExp('^\[A-Za-z]+$');
	return patrn.test(str);
};
ChkUtil.isLetterLen = function(str, min, max) {
	var patrn = new RegExp('^\[A-Za-z]{' + min + ',' + max + '}$');
	return patrn.test(str);
};
// 校验是否是整数
ChkUtil.isInteger = function(str) {
	var patrn = /^([+-]?)(\d+)$/;
	return patrn.test(str);
};
// 校验是否为正整数
ChkUtil.isPlusInteger = function(str) {
	var patrn = /^([+]?)(\d+)$/;
	return patrn.test(str);
};
// 校验是否为负整数
ChkUtil.isMinusInteger = function(str) {
	var patrn = /^-(\d+)$/;
	return patrn.test(str);
};
// 校验是否为浮点数
ChkUtil.isFloat = function(str, min, max, scale) {
	// var patrn = /^([+-]?)\d*\.\d+$/;
	var patrn = new RegExp('^(\\d)' + '{' + min + ',' + max + '}'
			+ '\\.{0,1}\\d' + '{0,' + scale + '}' + '$');

	return patrn.test(str);
};
// 校验是否为正浮点数
ChkUtil.isPlusFloat = function(str) {
	var patrn = /^([+]?)\d*\.\d+$/;
	return patrn.test(str);
};
// 校验是否为负浮点数
ChkUtil.isMinusFloat = function(str) {
	var patrn = /^-\d*\.\d+$/;
	return patrn.test(str);
};
// 校验是否仅中文
ChkUtil.isChinese = function(str) {
	var patrn = /[\u4E00-\u9FA5\uF900-\uFA2D]+$/;
	return patrn.test(str);
};
// 校验是否仅ACSII字符
ChkUtil.isAcsii = function(str) {
	var patrn = /^[\x00-\xFF]+$/;
	return patrn.test(str);
};
// 校验手机号码
ChkUtil.isMobile = function(str) {
	var patrn = /^0?1((3[0-9]{1})|(59)){1}[0-9]{8}$/;
	return patrn.test(str);
};
// 校验电话号码
ChkUtil.isPhone = function(str) {
	var patrn = /^(0[\d]{2,3}-)?\d{6,8}(-\d{3,4})?$/;
	return patrn.test(str);
};
// 校验URL地址
ChkUtil.isUrl = function(str) {
	var patrn = /^http[s]?:\/\/[\w-]+(\.[\w-]+)+([\w-\.\/?%&=]*)?$/;
	return patrn.test(str);
};
// 校验电邮地址
ChkUtil.isEmail = function(str) {
	var patrn = /^[\w-\.]+@[\w-]+(\.[\w-]+)+$/;
	return patrn.test(str);
};
// 校验邮编
ChkUtil.isZipCode = function(str) {
	var patrn = /^\d{6}$/;
	return patrn.test(str);
};
// 校验合法时间
ChkUtil.isDate = function(str) {
	if (!/\d{4}(\.|\/|\-)\d{1,2}(\.|\/|\-)\d{1,2}/.test(str)) {
		return false;
	}
	var r = str.match(/\d{1,4}/g);
	if (r == null) {
		return false;
	}
	;
	var d = new Date(r[0], r[1] - 1, r[2]);
	return (d.getFullYear() == r[0] && (d.getMonth() + 1) == r[1] && d
			.getDate() == r[2]);
};
// 校验字符串：只能输入6-20个字母、数字、下划线(常用手校验用户名和密码)
ChkUtil.isString6_20 = function(str) {
	var patrn = /^(\w){6,20}$/;
	return patrn.test(str);
};
//fclub后台密码校验
ChkUtil.isPassword10 = function(str) {
	var patrn = /^[0-9a-zA-z]{10,}$/;
	return patrn.test(str);
};
ChkUtil.isStringLen = function(str, min, max) {
	//var length = str.cnLength();
	var length = str.length;
	return (length >= min && length <= max);
};
isEmpty = function (str) {
	return (typeof (str) === "undefined" || str === null || (str.length === 0));
};
isNotEmpty = function (str) {
	return (!isEmpty(str));
};

/** 金额格式*/
function formatMoney(s, n) {
	n = n > 0 && n <= 20 ? n : 2;
	s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
	var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];
	t = "";
	for (i = 0; i < l.length; i++) {
//		t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");//3位逗号隔开
		t += l[i] + "";
	}
	var rtn=t.split("").reverse().join("");
	if (n>0){
		rtn=rtn+ "." + r;
	}
	return rtn;
}
/** 格式化文本输入框输入的数字 */
function formatVal(srcEle) {
	var v = $.trim(srcEle.val());
	if (!parseFloat(v) && v!='0' && v!='0.00') {
		srcEle.val("");//原值0.00
	} else {
		srcEle.val(formatMoney(v, 2));
	}
}

/** jquery格式化td单元格中显示的数字 */
function formatValJquery(dom) {
	var v = $.trim($(dom).html());
	if (!parseFloat(v) && v!='0' && v!='0.00') {
		$(dom).html("");//原值0.00
	} else {
		$(dom).html(formatMoney(v, 2));
	}
}
/** 清除金额格式 */
function clearMoney(dom) {
	$(dom).val($(dom).val().replace(/,|\s/g,''));
}
/** 清除所有金额格式 */
function clearMoneySt() {
	$("input").filter(".money").each(function(i,dom){
		if($(dom).val() == '0.00') {
			$(dom).val('');
		}
		clearMoney(dom);
	});
}
/** 绑定金额输入框事件 */
function bindMoneyEv() {
	$("input").filter(".money").each(function(i,dom){
		//if(isEmpty($(dom).val())) {
		if($.trim($(dom).val())=='') {
			$(dom).val('0.00');
			$(dom).css({color: "#909090"});
		} else {
			formatVal($(dom));
			$(dom).val($(dom).val().replace("-,","-"));
		}
	});
	$("input").filter(".money").click(function(){
		if($(this).val() == '0.00') {
			$(this).val('');
			$(this).css({color:"#000000"});
		}
	});
	$("input").filter(".money").blur(function(){
		clearMoney(this);
		formatVal($(this));
		$(this).val($(this).val().replace("-,","-"));
		var value = $(this).val();
		if($.trim(value) == '') {
			$(this).val('0.00');
			$(this).css({color: "#909090"});
		} else {
			$(this).css({color:"#000000"});
		}
	});
}