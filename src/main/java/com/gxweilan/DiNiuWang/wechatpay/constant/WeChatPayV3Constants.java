package com.gxweilan.diniuwang.wechatpay.constant;


/**
 * 常量
 * @author 86151
 */
public class WeChatPayV3Constants {

	public static final int UPDATE_INTERVAL_MINUTE = 60;

	public static final String SIGN_TYPE = "RSA";

	public static final String NOTIFY_SUCCESS = "{\"code\": \"SUCCESS\",\"message\": \"成功\"}";

	public static final String BASIC_URL = "https://api.mch.weixin.qq.com/v3";

	public static final String WECHAT_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";

	public static final String ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token";

	public static final String PHONE_NUMBER = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=";

	public static final String UNIFIED_ORDER = "/pay/transactions/jsapi";

	public static final String QUERY_ORDER_OUT_TRADE_NO = "/pay/transactions/out-trade-no/";

	public static final String QUERY_ORDER_TRANSACTION_ID = "/pay/transactions/id/";

	public static final String REFUND = "/refund/domestic/refunds";

	public static final String QUERY_REFUND = "/refund/domestic/refunds/";

	public static final String TRADE_BILL = "/bill/tradebill";

}

