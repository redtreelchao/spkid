package com.alipay.config;

import com.fclub.tpd.helper.ConstantsHelper;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。

 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

public class AlipayConfig {

	// ↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	public static String partner = "2088111953310312";
	// 商户的私钥
	public static String key = "enf4ib8aw513wvzucrky98dis3tew7kw";

	// ↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

	// 调试用，创建TXT日志文件夹路径
	public static String log_path = "D:\\";

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";

	// 签名方式 不需修改
	public static String sign_type = "MD5";
	public static String paygateway = "https://www.alipay.com/cooperate/gateway.do?"; // '支付接口
	public static String service = "create_direct_pay_by_user";// 快速付款交易服务
	public static String payment_type = "1";// 支付宝类型.1代表商品购买
	public static String seller_email = "chenwei@junjiwine.com"; // 卖家支付宝帐户
	public static String show_url = ConstantsHelper.getBackDomain()
			+ "/provider/account.htm";// 商品显示页
	public static String notify_url = ConstantsHelper.getBackDomain()
			+ "/provider/chongzhi/notify.htm"; // 通知接收URL
	public static String return_url = ConstantsHelper.getBackDomain()
			+ "/provider/chongzhi/return.htm"; // 支付完成后跳转返回的网址URL
}
