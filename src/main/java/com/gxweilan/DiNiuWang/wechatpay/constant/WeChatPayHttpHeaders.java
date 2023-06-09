package com.gxweilan.diniuwang.wechatpay.constant;

/**
 * 微信支付HTTP请求头相关常量
 *
 * @author Eric.Lee
 */
public final class WeChatPayHttpHeaders {

    public static final String REQUEST_ID = "Request-ID";
    public static final String WECHAT_PAY_SERIAL = "Wechatpay-Serial";
    public static final String WECHAT_PAY_SIGNATURE = "Wechatpay-Signature";
    public static final String WECHAT_PAY_TIMESTAMP = "Wechatpay-Timestamp";
    public static final String WECHAT_PAY_NONCE = "Wechatpay-Nonce";

    private WeChatPayHttpHeaders() {
        // Don't allow instantiation
    }

}
